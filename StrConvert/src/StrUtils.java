import java.math.BigInteger;

public class StrUtils {

    public static String hex2Dec(String hexStr, boolean isSimble){
        if (hexStr==null||hexStr.isEmpty()) return null;
        String[] split = hexStr.split(",");
        StringBuffer result=new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            String s = split[i].trim();
            boolean isD=false;
            if (isSimble){
                if (s.startsWith("-")){
                    s=s.substring(1, s.length());
                    isD=true;
                }
            }
            if (s.startsWith("0x")||s.startsWith("0X")){
                s=s.substring(2, s.length());
            }
            int rInt =Integer.parseInt(s, 16);
            if (!isD){
                int head=rInt>>7;
                if (isSimble&&head>0){
                    rInt=complement2Original(rInt);
                    result.append("-");
                }
            }else{
                result.append("-");
            }
            result.append(String.valueOf(rInt));
            result.append(", ");
        }
        result.delete(result.length()-1, result.length());
        return result.toString();
    }

    /*
    8bit符号二进制补码转原码
     */
    private static int complement2Original(int code){
        if (code>255) return -1;
        code-=1;
        code^=255;
        return code;
    }


    public static String hex2DecFloat(String hexStr, boolean isSimble){
        if (hexStr==null||hexStr.isEmpty()) return null;
        String[] split = hexStr.split(",");
        StringBuffer result=new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            String s = split[i].trim();
            boolean isD=false;
            if (isSimble){
                if (s.startsWith("-")){
                    s=s.substring(1, s.length());
                    isD=true;
                }
            }
            if (s.startsWith("0x")||s.startsWith("0X")){
                s=s.substring(2, s.length());
            }

            float rFloat=hexToFloat(s);
            result.append(String.valueOf(rFloat));
            result.append(", ");
        }
        result.delete(result.length()-1, result.length());
        return result.toString();
    }

    /**
     * 将 4字节的16进制字符串，转换为32位带符号的十进制浮点型
     * @param str 4字节 16进制字符
     * @return
     */
    public  static  float hexToFloat(String str){
//        return  Float.intBitsToFloat(new BigInteger(str, 16).intValue());
        return Float.intBitsToFloat(Integer.valueOf(str.trim(), 16));
//        return new Float(str);
    }

    /**
     * 将带符号的32位浮点数装换为16进制
     * @param value
     * @return
     */
    public  static  String folatToHexString(Float value){
        return  Integer.toHexString(Float.floatToIntBits(value));
    }

    public static int hexToInt(String str){

        int rInt =Integer.parseInt(str, 16);
        int head=rInt>>7;
        if (head>0){
            rInt=complement2Original(rInt);
        }
        return rInt;
    }


}
