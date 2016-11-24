cmake_minimum_required(VERSION 3.2.2)

project(NativeCipher)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/lib)

file(GLOB SOURCES *.cpp)
file(GLOB HEADERS *.h)


##### JNI ######
find_package(JNI)
message("JNI inclide dir: ${JNI_INCLUDE_DIRS} AND ${JAVA_INCLUDE_PATH2}")
include_directories(${JNI_INCLUDE_DIRS})

##### END ######

add_library(${PROJECT_NAME} SHARED ${SOURCES} ${HEADERS} ErrorHandler.h ErrorHandler.cpp)