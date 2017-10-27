package content;


import process.ReplaceFile;
import util.FileUtil;
import util.Log;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Environment {
    public static void main(String[] args) {
        try {
            new ReplaceFile()
                    .init()
                    .run(Config.REPLACE_PATH_ROOT, Config.TARGET_STR, Config.RESPLACE_STR);
        } catch (Exception e) {
            Log.e("运行出现异常，e="+e.getMessage());
            e.printStackTrace();
        }

    }
}
