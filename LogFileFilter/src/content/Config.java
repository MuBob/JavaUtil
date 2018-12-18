package content;

import java.io.File;

public class Config {
    public final static String LAST_NAME_LOG_FILE=".txt";
    public  final static String ROOT_PATH_LOG="E:\\work\\当前事宜\\日志\\1216\\test";
    public final static String NAME_LOG_FILE="outLog";
    public static String WORD_FILTER;
    public final static String getLogPath(){
        return Config.ROOT_PATH_LOG+ File.separator+Config.NAME_LOG_FILE+Config.LAST_NAME_LOG_FILE;
    }
    public final static String getOutPath(){
        return Config.ROOT_PATH_LOG+ File.separator+Config.NAME_LOG_FILE+"_"+WORD_FILTER+Config.LAST_NAME_LOG_FILE;
    }
    public final static String getOutPath(long i){
        return Config.ROOT_PATH_LOG+ File.separator+Config.NAME_LOG_FILE+"_"+WORD_FILTER+"_"+i+Config.LAST_NAME_LOG_FILE;
    }
}
