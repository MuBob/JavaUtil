import util.FileReaderTXT;
import util.FileUtil;
import util.FileWriterTXT;
import util.StringUtil;

import java.io.File;
import java.io.IOException;

public class Context {
    public static void main(String[] args){
        FileReaderTXT readerTXT=new FileReaderTXT(Config.FILE_PATH+ File.separator+Config.FILE_NAME);
        FileWriterTXT writerTXT=new FileWriterTXT(Config.FILE_PATH+ File.separator+Config.NEW_FILE_NAME);

        long lines = readerTXT.getLines();
        for (long i = 0; i < lines; i++) {
            String s = readerTXT.readFileByLines(i);
            try {
                long l = StringUtil.getHeadNumber(s);
                s=s.replace(l+"", "");
                System.out.println("long="+l+", replace str="+s);
                writerTXT.write(s);
            } catch (Exception e) {
                try {
                    writerTXT.write(s);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
}
