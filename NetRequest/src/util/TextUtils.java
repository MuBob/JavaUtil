package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/30.
 */
public class TextUtils {
    public static boolean isEmpty(String str){
        if (str==null) return true;
        if (str.equals("")) return true;
        return false;
    }

    /**
     * 判断字符是否是中文
     *
     * @param c 字符
     * @return 是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isChineseStr(String str, int startIndex, int endIndex) {
        if (str.length() < startIndex || str.length() > endIndex) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]");
        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(c[i]));
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        try {
            Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
            Matcher m = p.matcher(strName);
            String after = m.replaceAll("");
            String temp = after.replaceAll("\\p{P}", "");
            char[] ch = temp.toCharArray();
            float chLength = ch.length;
            float count = 0;
            for (int i = 0; i < ch.length; i++) {
                char c = ch[i];
                if (!Character.isLetterOrDigit(c)) {
                    if (!isChinese(c)) {
                        count = count + 1;
                    }
                }
            }
            float result = count / chLength;
            if (result > 0.4) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;
    }

}
