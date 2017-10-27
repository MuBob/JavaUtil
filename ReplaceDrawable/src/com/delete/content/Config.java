package com.delete.content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Config {
    //要删除的根路径
    public static String PATH_ROOT = "E:\\Project\\app\\src\\main\\res\\";
    //正则表达式匹配要删除的文件名
    public static String REG_FILE_NAME = "^.*?\\(ActivityMVP.class|Fragment.class)$";
}
