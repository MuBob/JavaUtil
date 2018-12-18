package work;
import util.FileWriterTXT;
import util.Log;

import java.io.IOException;

public class LogWrite {
    private FileWriterTXT fileWriterTXT;

    public LogWrite(String outName) {
        fileWriterTXT=new FileWriterTXT(outName);
    }

    public LogWrite(String outName, boolean isAppend) {
        fileWriterTXT=new FileWriterTXT(outName, isAppend);
    }

    public void writeLine(String str){
        try {
            fileWriterTXT.write(str);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("写入文件时出错，文件名："+getOutName());
        }
    }

    public void afterWrite(){
        fileWriterTXT.clear();
    }

    public String getOutName(){
        return fileWriterTXT.getOutFileName();
    }

}
