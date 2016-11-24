//
// Created by wolverindev on 24.11.16.
//

#ifndef NATIVECIPHER_EXCEPTIONHANDLER_H
#define NATIVECIPHER_EXCEPTIONHANDLER_H

#include "jni.h"

extern jint throwException(JNIEnv *env, const char* message, int err);

#endif //NATIVECIPHER_EXCEPTIONHANDLER_H
