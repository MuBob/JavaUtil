package process;

import util.Log;
import util.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/24.
 */
public class ReadChineseComment {
    public int readFile(String path){
        try {
            int count=0;
            char[] buffer1=new char[1];
            char[] buffer2=new char[1];
            char[] buffer3=new char[1];
            FileReader reader=new FileReader(path);
            while(reader.read(buffer3)>0) {
                if (StringUtil.isChinese(buffer1[0])&&buffer2[0]=='*'&&buffer3[0]=='/'){
                    ++count;
                }
                buffer1[0]=buffer2[0];
                buffer2[0]=buffer3[0];
            }

            reader.close();
            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
