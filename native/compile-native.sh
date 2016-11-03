#!/bin/sh

#i586-mingw32msvc-g++ -o myApp.exe myApp.cpp
CXX="g++ -shared -fPIC -O3 -Wall -Werror -I$JAVA_HOME/include/ -I$JAVA_HOME/include/linux/"

WINDOWS_CXX="i586-mingw32msvc-c++ -shared -O3 -Wall -Werror -I$JAVA_HOME/include/ -I$JAVA_HOME/include/linux/ -I/home/wolverindev/Downloads/openssl/openssl-1.0.2j/include/"
WINDOWS_ZLIB="-I/home/wolverindev/Downloads/zlib/zlib-1.2.8/ -L/home/wolverindev/Downloads/zlib/zlib-1.2.8/libz.ddl.a"

#Linux x64
$CXX src/main/c/NativeCipherImpl.cpp -o src/main/resources/native-cipher_x64.so -lcrypto
$CXX src/main/c/NativeCompressImpl.cpp -o src/main/resources/native-compress_x64.so -lz

#Linux x32
$CXX -m32 src/main/c/NativeCipherImpl.cpp -o src/main/resources/native-cipher_x32.so -lcrypto
$CXX -m32 src/main/c/NativeCompressImpl.cpp -o src/main/resources/native-compress_x32.so -lz

#Windows x64 //TODO ... fuck undefined referebces....
#$WINDOWS_CXX src/main/c/NativeCipherImpl.cpp -o src/main/resources/native-cipher_Windows_x32.ddl
$WINDOWS_CXX $WINDOWS_ZLIB src/main/c/NativeCompressImpl.cpp -o src/main/resources/native-compress_Windows_x32.ddl