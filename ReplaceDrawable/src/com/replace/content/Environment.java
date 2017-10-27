package com.replace.content;

import com.replace.util.ReplaceUtil;
import com.util.FileUtil;
import com.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Environment {
    public static void main(String[] args) {
        try {
            List<String> subDirList = FileUtil.getSubDirList(Config.REPLACE_PATH_ROOT);
            Log.i("subDirList=" + subDirList);
            if (subDirList == null) {
                return;
            }
            File file = FileUtil.getTempleteFileName(subDirList);
            boolean b = ReplaceUtil.getInstance()
                    .setCopyFile(file)
                    .setReplaceTargetPath(file.getParent(), subDirList)
                    .runReplace();
            Log.i("替换结束,是否成功？"+b);

        } catch (Exception e) {
            Log.e("运行出现异常，e="+e.getMessage());
            e.printStackTrace();
        }

    }
}
