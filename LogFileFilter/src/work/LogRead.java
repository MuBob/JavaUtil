package work;

import util.FileReaderTXT;

import java.util.concurrent.atomic.AtomicInteger;


public class LogRead{
    protected String fileName;
    FileReaderTXT fileReaderTXT;
    protected AtomicInteger readState;

    public LogRead(String fileName) {
        this.fileName = fileName;
        fileReaderTXT=new FileReaderTXT(fileName);
        readState=new AtomicInteger(0);
    }

    public long getLines(){
        long lines = fileReaderTXT.getLines();
        return lines;
    }

    public String getLineStr(long row){
        String lines = fileReaderTXT.readFileByLines(row);
        return lines;
    }
}
