package util;


import content.Config;

/**
 * Created by Administrator on 2017/3/30.
 */
public class StringUtil {

    public static boolean isEmpty(String str){
        if (str==null) return true;
        if (str.equals("")) return true;
        return false;
    }


    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }


    public static boolean isNumeric(String str) {
        if (str==null) return false;
        boolean isN=true;
        for (int i = 0; i < str.length(); i++) {
            if ('.'==str.charAt(i))
                break;
            if (!Character.isDigit(str.charAt(i))) {
                isN = false;
                break;
            }
        }
        return isN;
    }

}
