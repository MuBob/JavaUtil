package com.demo.simple;

import static com.demo.simple.Config.*;

public class Modify {
    public void modifyFile(String srcFileName, String dstFileName){

        byte[] old = getBytesFromFile(srcFileName);
        byte[] newBytes = StringUtil.copyBytes(old, 0, old.length);
        StringUtil.replaceByteAry(newBytes, NAEM_START, NAME_STR_NEW.getBytes());
        StringUtil.replaceByteAry(newBytes, HASH_START, StringUtil.hexString2Bytes(HASH_STR_NEW));
//        StringUtil.replaceByteAry(newBytes, NAEM_START, name_encrypt.getBytes());
//        StringUtil.replaceByteAry(newBytes, HASH_START, StringUtil.hexString2Bytes(hashBytesOld));
        FileUtil.saveFile(dstFileName, newBytes);

    }


    private byte[] getBytesFromFile(String fileName){
        byte[] fileByteArys = FileUtil.readFile(fileName);
        byte[] nameBytes = StringUtil.copyBytes(fileByteArys, NAEM_START, NAME_LENGTH);
        String copyNameStr=new String(nameBytes);
        byte[] hashBytes = StringUtil.copyBytes(fileByteArys, HASH_START, HASH_LENGTH);
        String copyHashStr=StringUtil.bytes2HexString(hashBytes);
        System.out.println("copy name str="+copyNameStr+", copy hash str="+copyHashStr);
        return fileByteArys;
    }
}
