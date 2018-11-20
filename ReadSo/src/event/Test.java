package event;

import utils.StringUtil;

public class Test {
    public static void main(String[] args){
        testInt2HexString();
    }

    private static void testBytes2HexString(){
        byte[] b={-0x80,0x62,0x7f, (byte) 0x91};
        String s = StringUtil.bytes2HexString(b);
        System.out.println("out s="+s);
//        byte[] cb = (byte[]) StringUtil.copyBytes((byte[4]) b, 0, 3);
//        System.out.println("out cb="+cb);
        byte[] strBytes={0x63, 0x78, 0x61, 0x5f};
        String s1 = StringUtil.bytes2UTF8String(strBytes);
        System.out.println("out s1="+s1);
    }

    private static void testHexString2Bytes(){
        String str="4a000000";
        byte[] bytes = StringUtil.hexString2Bytes(str);
        System.out.println("bytes: ");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(+i+":"+bytes[i]+"\t");
        }
        System.out.println();
        String bytes_string=StringUtil.bytes2HexString(bytes);
        System.out.println("bytes_string="+bytes_string);
        int toInt = StringUtil.byte2Int(bytes);
        System.out.println("toInt="+toInt);


        byte[] revert_bytes=StringUtil.bytesRevert(bytes, 0, bytes.length);
        System.out.println("revert_bytes: ");
        for (int i = 0; i < revert_bytes.length; i++) {
            System.out.print(+i+":"+revert_bytes[i]+"\t");
        }
        System.out.println();

        String bytes_string_revert=StringUtil.bytes2HexString(revert_bytes);
        System.out.println("bytes_string_revert="+bytes_string_revert);
        int toInt_revert = StringUtil.byte2Int(revert_bytes);
        System.out.println("toInt_revert="+toInt_revert);
    }

    private static void testInt2HexString(){

        int index = 3084;
        System.out.println("index="+index);
        String hexIndex = StringUtil.int2HexStringHigh(index);
        System.out.println("hex="+hexIndex);
        byte[] bytes = StringUtil.hexString2Bytes(hexIndex);
        StringUtil.printBytes(bytes);
        String bytes_string=StringUtil.bytes2HexString(bytes);
        System.out.println("bytes_string="+bytes_string);
        int i = StringUtil.byte2Int(bytes);
        System.out.println("i="+i);
    }
}
