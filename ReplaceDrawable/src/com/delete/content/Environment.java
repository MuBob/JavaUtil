package com.delete.content;

import com.delete.util.DeleteUtil;
import com.util.FileUtil;
import com.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Environment {
    public static void main(String[] args) {
        DeleteUtil.Builder builder = new DeleteUtil.Builder();
        boolean run = builder
                .setDeleteFileFormat(Config.REG_FILE_NAME)
                .setParentPath(Config.PATH_ROOT)
                .build()
                .run();
        Log.i("！！！！！！！！！！！！！！！！删除完成！！！， run="+run);
    }
}
