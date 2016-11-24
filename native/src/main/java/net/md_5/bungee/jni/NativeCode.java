package net.md_5.bungee.jni;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.md_5.bungee.jni.cipher.BungeeCipher;

public final class NativeCode<T>
{

    private final String name;
    private final Class<T> javaImpl;
    private final Class<T> nativeImpl;
    //
    private boolean loaded;

    public NativeCode(String name, Class<T> javaImpl, Class<T> nativeImpl)
    {
        this.name = name;
        this.javaImpl = javaImpl;
        this.nativeImpl = nativeImpl;
    }

    public T newInstance()
    {
        try
        {
            return ( loaded ) ? nativeImpl.newInstance() : javaImpl.newInstance();
        } catch ( IllegalAccessException | InstantiationException ex )
        {
            throw new RuntimeException( "Error getting instance", ex );
        }
    }

    public boolean load()
    {
        if ( !loaded && isSupported())
        {
            String fullName = getLibaryName(name);
            System.out.println("Using so file "+fullName);
            try
            {
                System.loadLibrary( fullName );
                loaded = true;
            } catch ( Throwable t )
            {
            }

            if ( !loaded )
            {
                try ( InputStream soFile = BungeeCipher.class.getClassLoader().getResourceAsStream( name + ".so" ) )
                {
                    // Else we will create and copy it to a temp file
                    File temp = File.createTempFile( fullName, ".so" );
                    // Don't leave cruft on filesystem
                    temp.deleteOnExit();

                    try ( OutputStream outputStream = new FileOutputStream( temp ) )
                    {
                        ByteStreams.copy( soFile, outputStream );
                    }

                    System.load( temp.getPath() );
                    loaded = true;
                } catch ( IOException ex )
                {
                    // Can't write to tmp?
                } catch ( UnsatisfiedLinkError ex )
                {
                    System.out.println( "Could not load native library: " + ex.getMessage() );
                }
            }
        }

        return loaded;
    }


    private static int checkState = -1;
    public static boolean isSupported()
    {
        if(checkState == -1){
            String tmp = getSystemName();
            if(tmp.equalsIgnoreCase("unknown")){
                checkState = 0;
                return false;
            }

            if(BungeeCipher.class.getClassLoader().getResource(getLibaryName("cipher")) != null){
                checkState = 1;
                return true;
            }
        }
        return checkState == 0;
    }

    private static String getArch(){
        switch (System.getProperty( "os.arch" ).toLowerCase()){
            case "amd64":
                return "64";
            default:
                return "32";
        }
    }

    private static String getSystemName(){
        String name = System.getProperty( "os.name" ).toLowerCase();
        if(name.contains("windows"))
            return "windows";
        else if(name.contains("linux"))
            return "linux";
        //TODO implement mac os
        //else if(name.contains("mac"))
        //    return "mac";
        return "unknown";
    }

    public static String getLibaryName(String name){
        return "NativeCode_"+name+"_"+getSystemName().toLowerCase()+"_x"+getArch();
    }
}
