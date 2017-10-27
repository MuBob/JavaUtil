package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Log {
    public static boolean isShow_i=true;
    public static boolean isShow_e=false;


    public final static boolean isShow_d=false;
//    public static boolean isShow_d=true;



    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String getCurrentTime(){
        return simpleDateFormat.format(new Date(System.currentTimeMillis()))+" Frome ";
    }

    private static String getCallName(){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        int level=3;
        if (stack.length<level){
            level=stack.length-1;
        }
        StackTraceElement element = stack[level];
        String callName=element.getClassName()+"-"+element.getMethodName();
        return callName;
    }
    public static void i(String msg) {
        if (!isShow_i) return;
        System.out.println(getCurrentTime() +" "+getCallName()+ " Info:" + msg);
    }
    public static void e(String msg){
        if (!isShow_e)return;
        System.out.println(getCurrentTime()+" "+getCallName()+" Error:"+msg);
    }
    public static void d(String msg){
        if (!isShow_d)return;
        System.out.println(getCurrentTime()+" "+getCallName()+" Debug:"+msg);
    }

}
