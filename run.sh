#!/usr/bin/env bash
rm -f $1.s $1
echo "======================================================="
if ./compile $1.wacc; then
    arm-linux-gnueabi-gcc -o $1 -mcpu=arm1176jzf-s -mtune=arm1176jzf-s $1.s
    qemu-arm -L /usr/arm-linux-gnueabi/ $1.out
else
    echo "FAILED TO COMPILE"
fi