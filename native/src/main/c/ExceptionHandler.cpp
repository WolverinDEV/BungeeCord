//
// Created by wolverindev on 24.11.16.
//

#include "ExceptionHandler.h"

jint throwException(JNIEnv *env, const char* message, int err) {
    // These can't be static for some unknown reason
    jclass exceptionClass = env->FindClass("net/md_5/bungee/jni/NativeCodeException");
    jmethodID exceptionInitID = env->GetMethodID(exceptionClass, "<init>", "(Ljava/lang/String;I)V");

    jstring jMessage = env->NewStringUTF(message);

    jthrowable throwable = (jthrowable) env->NewObject(exceptionClass, exceptionInitID, jMessage, err);
    return env->Throw(throwable);
}