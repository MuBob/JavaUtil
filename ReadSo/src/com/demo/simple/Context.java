package com.demo.simple;

import struct.FileProcess;

public class Context {
    public static final String PATH_OLD="E:\\JavaProject\\JavaUtil\\ReadSo\\src\\libs_old - 副本";
    public static final String PATH_NEW=PATH_OLD+"_modify";
    public static void main(String[] args) {
        FileProcess fileProcess=new FileProcess(PATH_OLD, PATH_NEW);
        fileProcess.start();
    }
}
