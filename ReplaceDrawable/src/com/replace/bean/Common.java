package com.replace.bean;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Common {
    private static long idNumber;
    public static long generateBeanId(){
        return idNumber++;
    }

}
