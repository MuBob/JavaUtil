package process;

import content.Config;
import util.FileUtil;

import java.io.File;
import java.util.List;

public class RenameProcess {
    public void run(){
        List<String> subDirList = FileUtil.getSubDirList(Config.OLD_PATH);
        for (int i=0; i<subDirList.size(); i++){
            String filePath = subDirList.get(i);
            File file=new File(filePath);
            String fileName=file.getName();
            FileUtil.copyFile(filePath, Config.NEW_PATH+File.separator+fileName+".jpg");
        }
    }
}
