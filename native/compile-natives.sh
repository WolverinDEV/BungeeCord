#!/bin/sh

cd src/main/c/
CMAKE_PREFIX="cmake ."

$CMAKE_PREFIX -DTARGET_SYSTEM=windows
make
$CMAKE_PREFIX -DTARGET_SYSTEM=linux
make
