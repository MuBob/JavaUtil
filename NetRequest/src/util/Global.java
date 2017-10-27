package util;

import java.io.File;

/**
 * Created by test on 2016/4/1.
 */
public class Global {
    public static final boolean AUTO_LOGIN=false;  //service启动时是否自动登录
    public static final boolean RUN_VOIP=true;  //运行VOIP小项目
    public static boolean LOGON = true;        // 是否显示log
    public static boolean LOG_FILE = false;     // 是否保存文件log
    public static final boolean RECORD_TRASH_EX = Boolean.TRUE; // 开关是否记录异常崩溃错误记录
    public static final boolean RECORD_NORMAL_EX = Boolean.FALSE; // 开关是否记录异常错误记录

    //这里忽略，服务器下发了
    public static String IM_CHAT_SERVER = "0.0.0.0";   // 聊天服务器地址
    public static int IM_CHAT_PORT = 0;                 // 聊天服务器端口

    public static String SERVER_ADDR = "https://118.192.66.3/";  // https业务服务器地址
    public static String UP_SERVER_ADDR = "http://118.192.66.3:8004/";  // 文件上传地址

    public static final String CER_FILE_NAME="api.im.crt";


    public static final String CRASH_EX = "crash_exception.txt"; // 记录异常崩溃文件名
    public static final String NORMAL_EX = "normal_exception.txt"; // 记录正常异常文件名

    public final static String KEY_CHAT_ID = "chat_id";
    public final static String KEY_CHAT_USER = "chat_user";
    public final static String KEY_CHAT_GROUP = "chat_group";
    public final static String KEY_CHAT_TYPE = "chat_type";

    public final static int LOGIN_TIME_OUT=5 * 60 * 1000;

    public static String getRequestUrl(String url_last){
        return SERVER_ADDR+url_last;
    }
    public static String getPostFileUrl(String url_last){
        return UP_SERVER_ADDR+url_last;
    }
    static {
        try {
        } catch (Exception e) {

        }
    }
}
