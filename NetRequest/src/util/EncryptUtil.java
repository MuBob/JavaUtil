/**
 *
 */
package util;

import java.io.ByteArrayOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Administrator
 */
public class EncryptUtil {

    private static String PASSWORD_KEY = "im@#xiao&%4tao";    // 用户密码加密用到的key
    private static String PUBLIC_KEY = "xttech&$2016@im";    // 公共密钥，用于生成signkey
    private static final String TEXT_ENC_KEY = "XtYl2016";    // 数据库加密key

    private static final char[] base64EncodeChars = new char[]{'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
            'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,
            60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
            -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
            38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
            -1, -1};

    /**
     * 将字节数组编码为字符串
     *
     * @param data
     */
    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;

        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4)
                        | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4)
                    | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2)
                    | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * 将base64字符串解码为字节数组
     *
     * @param str
     */
    public static byte[] base64Decode(String str) {
        byte[] data = str.getBytes();
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1, b2, b3, b4;

        while (i < len) {

			/* b1 */
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }

			/* b2 */
            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            buf.write((b1 << 2) | ((b2 & 0x30) >>> 4));

			/* b3 */
            do {
                b3 = data[i++];
                if (b3 == 61) {
                    return buf.toByteArray();
                }
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) {
                break;
            }
            buf.write(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2));

			/* b4 */
            do {
                b4 = data[i++];
                if (b4 == 61) {
                    return buf.toByteArray();
                }
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) {
                break;
            }
            buf.write(((b3 & 0x03) << 6) | b4);
        }
        return buf.toByteArray();
    }

    public static String toMd5(String instr) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(instr.getBytes());
            byte tmp[] = md.digest();

            char str[] = new char[16 * 2];

            int k = 0;
            for (int i = 0; i < 16; i++) {

                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];

                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str).toUpperCase();
//			s = new String(str);
        } catch (Exception e) {

        }
        return s;
    }

    public static String getEncrypPassword(String pwd) {
        //md5(公钥+明文密码)
        return EncryptUtil.toMd5(pwd + PASSWORD_KEY);
    }

    public static String getSignKey(int uid, long time, String token) {
        // md5(用户uid +公钥+当前时间戳（10位的）+用户token)
        return EncryptUtil.toMd5(uid + PUBLIC_KEY + time + token);
    }

    private final static String DES_TRANSFORMATION = "DES/CBC/PKCS5Padding";//DES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private final static String DES_IVPARAMETERSPEC = "01020304";////初始化向量参数，AES 为16bytes. DES 为8bytes.
    private final static String DES_ALGORITHM = "DES";//DES是加密方式

    public static String encodeText(String text) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }
        return dbEncode(TEXT_ENC_KEY, text);
    }

    public static String decodeText(String text) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }
        return dbDecode(TEXT_ENC_KEY, text);
    }

    /**
     * DB加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String dbEncode(String key, String data) {
        return dbEncode(key, data.getBytes());
    }

    /**
     * DB加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String dbEncode(String key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(DES_TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(DES_IVPARAMETERSPEC.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, getDesRawKey(key), iv);
            byte[] bytes = cipher.doFinal(data);
            return base64Encode(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    // 对密钥进行处理
    private static Key getDesRawKey(String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    /**
     * 获取编码后的值
     *
     * @param key
     * @param data
     * @return
     */
    public static String dbDecode(String key, String data) {
        return dbDecode(key, base64Decode(data));
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     */
    public static String dbDecode(String key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(DES_TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(DES_IVPARAMETERSPEC.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, getDesRawKey(key), iv);
            byte[] original = cipher.doFinal(data);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            return null;
        }
    }

    public final static String AES_TRANSFORMATION = "AES/CBC/PKCS7Padding";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    public final static String AES_IVPARAMETERSPEC = "AreyoumySnowman?";
    public final static String AES_ALGORITHM = "AES";

    // 网络加密
    public static String netDecode(String key, String data) {
        return netDecode(key, base64Decode(data));
    }

    public static String netDecode(String key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(AES_IVPARAMETERSPEC.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, getAESRawKey(key), iv);
            byte[] original = cipher.doFinal(data);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] netDecode(String key, byte[] data, int pos) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(AES_IVPARAMETERSPEC.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, getAESRawKey(key), iv);
            byte[] original = cipher.doFinal(data, pos, data.length - pos);
            return original;
        } catch (Exception e) {
            return null;
        }
    }

    // 对密钥进行处理
    public static Key getAESRawKey(String key) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
        return skeySpec;
    }

    /**
     * 协议加密
     * @param key
     * @param data
     * @return
     */
    public static String netEncode(String key, String data) {
        return netEncode(key, data.getBytes());
    }

    /**
     * 协议加密
     * @param key
     * @param data
     * @return
     */
    public static String netEncode(String key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(AES_IVPARAMETERSPEC.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, getAESRawKey(key), iv);
            byte[] bytes = cipher.doFinal(data);
            return base64Encode(bytes);
        } catch (Exception e) {
            return null;
        }
    }
}
