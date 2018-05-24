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
        this.file = new File(filePath);
        String parent = file.getParent();
        File parentFile=new File(parent);
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        if (file.exists()){
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
}
