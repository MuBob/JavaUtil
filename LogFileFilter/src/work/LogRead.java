package work;

import util.FileReaderCallback;
import util.FileReaderTXT;
import work.inter.LogLineCallback;

import java.util.concurrent.atomic.AtomicInteger;

import static work.ReadState.*;


public class LogRead implements FileReaderCallback{
    protected String fileName;
    FileReaderTXT fileReaderTXT;
    @STATE
    public AtomicInteger readState;

    public LogRead(String fileName) {
        this.fileName = fileName;
        fileReaderTXT=new FileReaderTXT(fileName);
        readState=new AtomicInteger(STATE_DEFAULT);
    }

    public long getLines(){
        long lines = fileReaderTXT.getLines();
        return lines;
    }

    public String getLineStr(long row){
        String lines = fileReaderTXT.readFileByLines(row);
        return lines;
    }

    public void readLines(long from, long length, LogLineCallback callback){
        this.callback=callback;
        fileReaderTXT.readFileByLines(from, length, this);
    }

    private LogLineCallback callback;

    @Override
    public void onFileReadLine(int line, String content) {
        callback.onReadLine(fileName, line, content);
    }

    @Override
    public void afterFileReadLine(String fileName, int lines) {
        callback.afterReadLine(fileName, lines);
    }
}
