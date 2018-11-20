package event;

import struct.FileProcess;

import static event.Config.*;

public class Context {

    public static void main(String[] args) {
        FileProcess fileProcess=new FileProcess(PATH_OLD, PATH_NEW);
        fileProcess.start();
    }
}
