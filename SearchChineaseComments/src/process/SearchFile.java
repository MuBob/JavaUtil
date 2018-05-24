package process;

import content.Config;
import util.FileUtil;
import util.FileWriterTXT;
import util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */
public class SearchFile {

    private ReadChineseComment reader;
    private FileWriterTXT writerTXT;
    public SearchFile init(){
        FileUtil.init();
        reader=new ReadChineseComment();
        writerTXT=new FileWriterTXT(Config.MSG_FILE_PATH);
        return this;
    }
    public boolean run(String path){
        List<String> subDirList = FileUtil.getSubDirList(path);
        if (subDirList == null) {
            return false;
        }
        for (int i=0; i<subDirList.size();i++){
            String filePath = subDirList.get(i);
            File file=new File(filePath);
            if (file.isDirectory()){
                //todo 递归调用
                run(file.getAbsolutePath());
            }else {
                //文件读取判断
                int count = reader.readFile(filePath);
                if (count>0){
                    String msg = filePath + " have counts=" + count;
                    try {
                        writerTXT.write(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
