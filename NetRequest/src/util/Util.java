package util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Util {

    /**
     * 判断对象是否为null
     *
     * @param obj
     *            要判断的对象
     * @return true 为空
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return isNullStr((String) obj);
        } else if (obj instanceof List) {
            return isNullSet((List) obj);
        } else if (obj instanceof Object[]) {
            return isNullArray((Object[]) obj);
        } else {
            return isNullObject(obj);
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     *            要判断的字符串
     * @return 空就返回true
     */
    private static boolean isNullStr(String str) {
        if (str == null)
            return true;
        if (str.length() == 0)
            return true;
        if ("".equals(str))
            return true;
        return false;
    }

    /**
     * 判断对象是否为空
     *
     * @param obj
     *            要判断的对象
     * @return 空就返回true
     */
    private static boolean isNullObject(Object obj) {
        if (obj == null)
            return true;
        return false;
    }

    /**
     * 判断集合是否为空
     *
     * @param arr
     *            要判断的集合
     * @return 空就或者size=0，返回true
     */
    private static boolean isNullSet(@SuppressWarnings("rawtypes") List arr) {
        if (arr == null || arr.size() == 0)
            return true;
        return false;
    }

    /**
     * 判断数组是否为空
     *
     * @param array
     *            要判断的数组
     * @return 空就或者length=0，返回true
     */
    private static boolean isNullArray(Object[] array) {
        if (array == null || array.length == 0)
            return true;
        return false;
    }

    /**
     * 替换URL的通传参数
     *
     * @param url         要替换的URL字符串
     * @param begin       begin是指"md=", "rhv=", 后面包括"="
     * @param end         end是指"&"
     * @param replacement 要替换的字符串
     * @return 取到begin的"="之后, end的"&"之前的字符串替换后的结果
     */
    public static String replaceURL(String url, String begin, String end,
                                    String replacement) {
        try {
            String temp = url;
            int len = begin.length();
            int index1 = temp.indexOf(begin);
            temp = temp.substring(index1) + len;
            int index2 = index1 + temp.indexOf(end);
            String sub = url.substring(index1 + begin.length(), index2);
            String replaced = url.replace(sub, replacement);
            return replaced;
        } catch (Exception e) {
            return url;
        }
    }

    /**
     * 将int转换成byte数组
     *
     * @param a 整型数
     * @return byte数组
     */
    public static byte[] intToByteArray(int a) {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    /**
     * 复制ByteBuffer
     *
     * @param src    源buffer
     * @param pos    起始位置
     * @param length 复制长度
     * @return 复制结果ByteBuffer
     */
    public static ByteBuffer cloneByteBuffer(ByteBuffer src, int pos, int length) {
        if (pos + length > src.limit()) {
            return null;
        }
        /** 获得开始位置 */
        int oldPos = src.position();

        /** 设置新位置 */
        src.position(pos);

        /** new length长度的 byte[] */
        byte[] subBytes = new byte[length];

        /** 拷贝数据 */
        src.get(subBytes);

        /** 还原原来位置 */
        src.position(oldPos);

        return ByteBuffer.wrap(subBytes);
    }

    /**
     * 复制 byte[]
     *
     * @param src    源buffer
     * @param pos    起始位置
     * @param length 复制长度
     * @return
     */
    public static byte[] cloneByte(byte[] src, int pos, int length) {
        byte[] dest = new byte[length];
        for (int i = 0; i < length; i++) {
            dest[i] = src[pos + i];
        }
        return dest;
    }

    /**
     * 转换byte数组到int
     *
     * @param bytes 1到4个字节的byte数组
     * @param pos   从byte数组的哪个位置开始转换
     * @return
     */
    public static int bytesToInt(byte[] bytes, int pos) {
        if (bytes == null) {
            throw new IllegalArgumentException();
        }
        int num = 0;
        int len = bytes.length;
        if (len == 0 || len > 4) {
            // return -1;
            throw new IllegalArgumentException();
        }
        if (len == 1) {
            num |= bytes[0] & 0xFF;
        } else if (len == 2) {
            num |= bytes[1] & 0xFF;
            num |= ((bytes[0] << 8) & 0xFF00);
        } else if (len == 3) {
            num |= bytes[2] & 0xFF;
            num |= ((bytes[1] << 8) & 0xFF00);
            num |= ((bytes[0] << 16) & 0xFF0000);
        } else if (len == 4) {
            num |= bytes[3] & 0xFF;
            num |= ((bytes[2] << 8) & 0xFF00);
            num |= ((bytes[1] << 16) & 0xFF0000);
            num |= ((bytes[0] << 24) & 0xFF000000);
        }
        return num;
    }

    /**
     * 转换byte数组到long
     *
     * @param bytes 1到8个字节的byte数组
     * @return
     */
    public static long bytesToLong(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException();
        }
        long value = 0;
        int len = bytes.length;
        if (len == 0 || len > 8) {
            // return -1;
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < len; i++) {
            value |= (((long) (bytes[i] & 0xFF) << (len - i - 1) * 8));
        }
        return value;
    }

    private static SoftReference<Date> date_sof;
    private static SoftReference<SimpleDateFormat> dateFotmate_sof;

    /**
     * 格式化时间
     *
     * @param time
     * @param formate 需要格式化的样式 eg: yyyy-MM-dd HH:mm:ss
     * @return 转换后的字符串
     */
    public static String transforTime(long time, String formate) {

        Date date = null;
        if (date_sof != null) {
            date = date_sof.get();
        }
        if (date == null) {
            date = new Date();
            date_sof = null;
            date_sof = new SoftReference<Date>(date);
        }
        date.setTime(time);
        SimpleDateFormat formatter = getFormat();
        formatter.applyPattern(formate);
        // new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 根据传入参数, 格式化当前时间
     *
     * @param formate 时间格式的字符串
     * @return
     */
    public static String transforCurTime(String formate) {
        return transforTime(System.currentTimeMillis(), formate);
    }

    /**
     * 得到一个时间格式化实例
     *
     * @return
     */
    public static SimpleDateFormat getFormat() {
        SimpleDateFormat formatter = null;
        if (dateFotmate_sof != null) {
            formatter = dateFotmate_sof.get();
        }
        if (formatter == null) {
            formatter = new SimpleDateFormat();
            dateFotmate_sof = null;
            dateFotmate_sof = new SoftReference<SimpleDateFormat>(formatter);
        }
        return formatter;
    }

    private static SoftReference<StringBuilder> STR_BUILDER_sof;

    /**
     * 连接字符串
     *
     * @param str
     * @return
     */
    public static String appendString(CharSequence... str) {
        String result = null;
        if (str != null && str.length > 0) {
            StringBuilder builder = null;
            if (STR_BUILDER_sof != null)
                builder = STR_BUILDER_sof.get();
            if (builder == null) {
                builder = new StringBuilder();
                STR_BUILDER_sof = null;
                STR_BUILDER_sof = new SoftReference<StringBuilder>(builder);
            }

            for (CharSequence s : str) {
                builder.append(s);
            }
            result = builder.toString();
            builder.delete(0, builder.length());
        }
        return result;
    }

    private static float scale = -1f;

    public static String birthdaylong2String(long birtyday, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                TextUtils.isEmpty(format) ? "yyyy-MM-dd" : format);
        return sdf.format(new Date(birtyday));
    }



    /**
     * 下载一个文件，不能放主线程里面做
     *
     * @param url
     * @param path
     * @return 失败返回0，成功返回1, 其他返回错误码
     */
    public static int downloadFile(String url, String path) {
        try {
            try {
                String dir = path
                        .substring(0, path.lastIndexOf(File.separator));
                new File(dir).mkdirs();
            } catch (Exception e) {
            }

            URL Url = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) Url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 404) {
                return 404;
            }

            InputStream is = conn.getInputStream();
            if (is == null) { // 没有下载流
                return 0;
            }
            FileOutputStream os = new FileOutputStream(path);

            byte buf[] = new byte[1024];
            int size;
            while ((size = is.read(buf)) != -1) {
                os.write(buf, 0, size);
            }
            is.close();
            os.getFD().sync();
            os.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            new File(path).delete();
            LogUtil.LogShow("Util", "url=" + url + "thumbnail = " + path);
            return 0;
        }
        return 1;
    }


    public static boolean isValidLinkFormat(String strLink) {
        if (TextUtils.isEmpty(strLink)) {
            return false;
        }
        if (strLink.contains("http") || strLink.contains("HTTP")
                || strLink.contains("WWW") || strLink.contains("www")) {
            return true;
        }
        return false;
    }

    public static void parseSignature(byte[] signature) {
        try {
            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            System.out.println("pubKey:" + pubKey);
            System.out.println("signNumber:" + signNumber);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return "-1";
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        LogUtil.LogShow("FileMD5", file.getPath() + ":" + bigInt.toString(16));
        return bigInt.toString(16);
    }


    // 通传参数加上包名
    public static String getSimpleParams() {
       StringBuffer comParam=new StringBuffer();
        comParam.append("imei=").append(getImei()).append("&")
                .append("imsi=").append(getImsi()).append("&")
                .append("pf=").append("Android").append("&")
                .append("brand=").append("Android_MODE").append("&")
                .append("rhv=").append("27").append("&")
                .append("ver=").append("8.1").append("&")
                .append("lan=zn");
        return comParam.toString();
    }

    private static String getImei() {
        return "123435423";
    }

    private static String getImsi() {
        return "341453";
    }

    public static String ensureParamsValid(String params) {
        return params.replaceAll(" ", "_").replaceAll("\\+", "_").toString();
    }

    public static String appendParam(String url, String param) {
        if (url != null) {
            if (url.contains("?")) {
                if (url.endsWith("?")) {
                    url += param;
                } else {
                    url = url + "&" + param;
                }
            } else {
                url = url + "?" + param;
            }
        }
        return url;
    }


    /**
     * 获得格式化的"万"大小
     *
     * @return 格式化的"亿"大小
     */
    public static String getSize(long nBytes) {
        String mSizeFormat = "";
        if (nBytes < 0)
            return mSizeFormat;

        // 格式化
        // 小于1万
        if (nBytes < 10000) {
            mSizeFormat = "";
            mSizeFormat += nBytes;
        }
        // 小于1亿
        else if (nBytes < 100000000) {
            double fKB = nBytes;
            fKB /= 10000;
            fKB = (int) (fKB * 10);
            fKB /= 10;

            mSizeFormat = Double.valueOf(fKB).toString();
            mSizeFormat += "万";
        }
        // 大于1亿,保留小数2位
        else {
            double lfGB = nBytes / 100000000;
            lfGB = (int) (lfGB * 10);
            lfGB /= 10;

            mSizeFormat = Double.valueOf(lfGB).toString();
            mSizeFormat += "亿";
        }
        return mSizeFormat;
    }

    /**
     * 解压缩一个文件
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static void unZipFile(File zipFile, String folderPath)
            throws ZipException, IOException {
        int BUFF_SIZE = 4096;
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            String str = folderPath + File.separator + entry.getName();

            str = new String(str.getBytes("8859_1"), "GB2312");
            File desFile = new File(str);
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
            }
            if (entry.isDirectory()) {
                desFile.mkdirs();
            } else {
                desFile.createNewFile();
                InputStream in = zf.getInputStream(entry);
                OutputStream out = new FileOutputStream(desFile);
                byte buffer[] = new byte[BUFF_SIZE];
                int realLength;
                while ((realLength = in.read(buffer)) > 0) {
                    out.write(buffer, 0, realLength);
                }
                in.close();
                out.close();
            }
        }
    }

    /**
     * 解压缩一个文件
     *
     * @param zipFile 压缩文件
     * @throws IOException 当解压缩过程出错时抛出
     */

    public static void unZipFile(File zipFile) throws ZipException, IOException {
        String folderPath = zipFile.getParent();
        unZipFile(zipFile, folderPath);
    }

    /***
     * 压缩GZip
     *
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压GZip
     *
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public static String countUnitConvert(int count) {
        if (count < 10000) {
            return String.valueOf(count);
        } else if (count < 100000) {
            int v = count / 10000;
            String str = String.valueOf(v);
            if (count % 10000 != 0) {
                v = (count - v * 10000) / 1000;
                str += "." + v;
            }
            str += "万";
            return str;
        } else if (count < 100000000) {
            return (count / 10000 + "万");
        } else if (count < 1000000000) {
            int v = count / 100000000;
            String str = String.valueOf(v);
            if (count % 100000000 != 0) {
                v = (count - v * 100000000) / 10000000;
                str += "." + v;
            }
            str += "亿";
            return str;
        } else {
            return (count / 100000000 + "亿");
        }
    }


    @Deprecated
    public static boolean match(String regx, String str) {
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    @Deprecated
    public static boolean isMobileNum(String mobiles) {
        if (mobiles == null || mobiles.length() != 11) {
            return false;
        }
        final String REGX = "^1[3|5|8][0-9]\\d{8}$";
        return match(REGX, mobiles);
    }

}
