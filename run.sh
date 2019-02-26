make
./compile $1.wacc
arm-linux-gnueabi-gcc -o $1 -mcpu=arm1176jzf-s -mtune=arm1176jzf-s $1.s
qemu-arm -L /usr/arm-linux-gnueabi/ $1