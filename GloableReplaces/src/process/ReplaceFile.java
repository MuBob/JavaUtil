package process;

import content.Config;
import util.FileUtil;
import util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public class ReplaceFile {
    public ReplaceFile init(){
        FileUtil.init();
        return this;
    }
    public boolean run(String path, String oldStr, String newStr){
        List<String> subDirList = FileUtil.getSubDirList(path);
        Log.i("subDirList=" + subDirList);
        if (subDirList == null) {
            return false;
        }
        for (int i=0; i<subDirList.size();i++){
            String filePath = subDirList.get(i);
            File file=new File(filePath);
            if (file.isDirectory()){
                //todo 递归调用
                run(file.getAbsolutePath(), oldStr, newStr);
            }else {
                boolean b = FileUtil.replaceStrInFile(file,oldStr, newStr);
                if(b) {
                    Log.i(String.format("文件%s修改成功！", file.getAbsolutePath()));
                    FileUtil.replaceFileSuccessCount++;
                }else{
                    Log.i(String.format("警告：文件%s修改失败！！！", file.getAbsolutePath()));
                    FileUtil.replaceFileFailCount++;
                }
            }
        }
        return true;
    }
}
