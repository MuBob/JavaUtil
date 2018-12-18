package com.demo.simple;

public class StringUtil {

    public static String bytes2HexString(byte[] bytes){
        StringBuilder result = new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString(bytes[i]);
            if(hex.length() < 2){
                result.append("0"+hex);
            }else{
                result.append(hex);
            }
            result.append(" ");
        }
        return result.toString();
    }

    public static byte[] hexString2Bytes(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }
    public static byte toByte(char c) {
        int b = "0123456789abcdef".indexOf(c);
        if (b<0){
            b = "0123456789ABCDEF".indexOf(c);
        }
        return  (byte)b;
    }
    public static byte[] copyBytes(byte[] res, int start, int count){
        if(res == null){
            return null;
        }
        byte[] result = new byte[count];
        for(int i=0;i<count;i++){
            result[i] = res[start+i];
        }
        return result;
    }

    public static int byte2Int(byte[] res) {
        int targets = (res[0] & 0xff)
                | ((res[1] << 8) & 0xff00)
                | ((res[2] << 24) >>> 8)
                | (res[3] << 24);
        return targets;
    }

    public static long byte2Long(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;

        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    public static short byte2Short(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    public static byte[] long2ByteAry(long number) {
        long temp = number;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return b;
    }

    public static byte[] int2Byte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return b;
    }
    public static byte[] short2Byte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return b;
    }

    public static byte[] replaceByteAry(byte[] src, int rep_index, byte[] copyByte){
        for(int i=rep_index;i<rep_index+copyByte.length;i++){
            try {
                src[i] = copyByte[i - rep_index];
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("exception when i="+i+", rep="+ rep_index+", copyByteLen="+copyByte.length+", srcLen="+src.length);
                return src;
            }
        }
        return src;
    }

    public static byte[] reverseBytes(byte[] bytes){
        if(bytes == null || (bytes.length % 2) != 0){
            return bytes;
        }
        int i = 0;
        int offset = bytes.length/2;
        while(i < (bytes.length/2)){
            byte tmp = bytes[i];
            bytes[i] = bytes[offset+i];
            bytes[offset+i] = tmp;
            i++;
        }
        return bytes;
    }
    public static long elfhash(String strUri) {
        char[] name=strUri.toCharArray();
        long h = 0, g=0;
        for (char aName : name) {
            h = (h << 4) + aName;
            g = h & 0xF0000000;
            h ^= g;
            h ^= g >> 24;
        }
        return h;
    }

    public static long ELFHash(String strUri) {
        long h = 0, g=0;
        for(int i=0;i<strUri.length();i++)
        {
            h = (h<<4)+strUri.charAt(i);
            if((g=h & 0xF0000000L) != 0)
            {
                h^=(g>>24);
                h &=~g;
            }
        }
        return (h & 0x7FFFFFFFL);
    }
}
