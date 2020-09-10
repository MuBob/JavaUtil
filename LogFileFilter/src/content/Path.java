package content;

import java.io.File;

import static content.Config.*;

public class Path {
    public final static String getLogPath(){
        return ROOT_PATH_LOG+ File.separator+NAME_LOG_FILE+LAST_NAME_LOG_FILE;
    }
    public final static String getOutPath(){
        return ROOT_PATH_LOG+ File.separator+NAME_LOG_FILE+"_"+WORD_FILTER+LAST_NAME_LOG_FILE;
    }
    public final static String getOutPath(long i){
        return ROOT_PATH_LOG+ File.separator+NAME_LOG_FILE+"_"+WORD_FILTER+"_"+i+LAST_NAME_LOG_FILE;
    }
}
