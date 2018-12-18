package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/24.
 */
public class FileWriterTXT {
    private File file;

    public FileWriterTXT(String filePath) {
        this(filePath, false);
    }

    public FileWriterTXT(String filePath, boolean isAppend) {
        this.file = new File(filePath);
        String parent = file.getParent();
        File parentFile=new File(parent);
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        if (!isAppend&&file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String msg) throws IOException {
        FileWriter writer=new FileWriter(file, true);
        writer.write(msg);
        writer.write("\r\n");
        writer.flush();
        writer.close();
    }

    public void clear(){
        if (file!=null&&file.exists()){
            file.delete();
        }
    }

    public String getOutFileName(){
        return file.getAbsolutePath();
    }
}
