cmake_minimum_required(VERSION 3.2.2)

project(NativeCode)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/../resources/)
set(CMAKE_SHARED_LIBRARY_PREFIX  )

set(SHARED_FILES ExceptionHandler.cpp ExceptionHandler.h)

set(CIPHER_SOURCES NativeCipherImpl.cpp)
set(CIPHER_HEADERS net_md_5_bungee_jni_cipher_NativeCipherImpl.h)

set(COMPRESS_SOURCES NativeCompressImpl.cpp)
set(COMPRESS_HEADERS net_md_5_bungee_jni_zlib_NativeCompressImpl.h)


##### JNI ######
find_package(JNI)
#message("JNI inclide dir: ${JNI_INCLUDE_DIRS} AND ${JAVA_INCLUDE_PATH2}")
include_directories(${JNI_INCLUDE_DIRS})
##### END ######

macro(createBuild SYS_TYPE SYS_ARCH)
    add_library(${PROJECT_NAME}_cipher_${SYS_TYPE}_x${SYS_ARCH} SHARED ${SHARED_FILES} ${CIPHER_SOURCES} ${CIPHER_HEADERS})
    target_link_libraries(${PROJECT_NAME}_cipher_${SYS_TYPE}_x${SYS_ARCH} crypto)
    set_target_properties(${PROJECT_NAME}_cipher_${SYS_TYPE}_x${SYS_ARCH} PROPERTIES COMPILE_FLAGS "-m${SYS_ARCH}" LINK_FLAGS "-m${SYS_ARCH}")


    add_library(${PROJECT_NAME}_compress_${SYS_TYPE}_x${SYS_ARCH} SHARED ${SHARED_FILES} ${COMPRESS_SOURCES} ${COMPRESS_HEADERS})
    set_target_properties(${PROJECT_NAME}_compress_${SYS_TYPE}_x${SYS_ARCH} PROPERTIES COMPILE_FLAGS "-m${SYS_ARCH}" LINK_FLAGS "-m${SYS_ARCH}")
    message("Properties: ${CMAKE_DEBUG_TARGET_PROPERTIES}")
endmacro()

if(${TARGET_SYSTEM} MATCHES "windows")
    message("Building natived for windows")
    INCLUDE(platforms/windows.cmake)
elseif(${TARGET_SYSTEM} MATCHES "linux")
    message("Building natived for linux")
    INCLUDE(platforms/linux.cmake)
else()
    message(FATAL_ERROR "Cant find system ${TARGET_SYSTEM}")
endif()