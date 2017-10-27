package com.util;

import com.replace.content.Config;

/**
 * Created by Administrator on 2017/3/30.
 */
public class StringUtil {
    /**
     * 将文件名“abc.def.9”分割为“abc”“def.9”
     * @param file
     * @return 二维数组
     */
    public static String[] splitFileNameAndLast(String file){
        Log.d("当前分割文件:"+file+"!");
        String[] result=new String[2];
        String[] split = file.split(Config.FILE_POINT_MATCHE);
        result[0]=split[0];
        if (split.length>2) {
            StringBuffer last = new StringBuffer();
            for (int i = 1; i < split.length; i++) {
                last.append(split[i]);
            }
            result[1]=last.toString();
        }else if (split.length==2){
            result[1]=split[1];
        }else {
            Log.e("分隔时出现异常，当前分隔串="+file);
        }
        return result;
    }

    public static boolean isEmpty(String str){
        if (str==null) return true;
        if (str.equals("")) return true;
        return false;
    }
}
