package content;

import process.SearchFile;
import util.Log;

/**
 * Created by Administrator on 2018/5/24.
 */
public class Environment {
    public static void main(String[] args){
        SearchFile searchFile=new SearchFile();
        searchFile.init().run(Config.SEARCH_PATH_ROOT);
        Log.i("-----------------------SUCCESS!--------------------------------------------");
    }
}
