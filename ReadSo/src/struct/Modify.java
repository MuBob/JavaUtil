package struct;

import utils.Elf32Util;
import utils.FileUtil;
import utils.StringUtil;

import static event.Config.*;


public class Modify {
    public void modifyFile(String srcFileName, String dstFileName){
        byte[] fileByteArys = getFileBytes(srcFileName);
        ElfType32  elfType32 = parseBytes(fileByteArys);
        int strtbOffsetWithName=getStrtbOffset(elfType32, NAME_STR_OLD);
        int hashOffsetWithName=getEmptyHashOffset(elfType32, NAME_STR_NEW);
        System.out.println("query strtb index="+strtbOffsetWithName+", hex index="+ StringUtil.int2HexStringAddress(strtbOffsetWithName)+", query hash index="+hashOffsetWithName+", hex index="+StringUtil.int2HexStringAddress(hashOffsetWithName));
        String hexIndex=getOldStrHexIndex(elfType32, NAME_STR_OLD);
        byte[] newBytes =StringUtil.copyBytes(fileByteArys, 0, fileByteArys.length);
        replaceStrtb(newBytes, strtbOffsetWithName, NAME_STR_NEW);
        replaceHashtb(newBytes, hashOffsetWithName, hexIndex);
        FileUtil.saveFile(dstFileName, newBytes);
    }

    private byte[] getFileBytes(String fileName){
        byte[] fileByteArys = FileUtil.readFile(fileName);
        return fileByteArys;
    }

    private ElfType32 parseBytes(byte[] bytes){
        ElfType32 type_32= ParseSo.parseSo(bytes);
        return type_32;
    }

    private int getStrtbOffset(ElfType32 elfType32, String str){
        int offset = Elf32Util.findStrtbOffsetWithStr(elfType32, str.getBytes());
        return offset;
    }

    private int getEmptyHashOffset(ElfType32 elfType32, String str){
        int offset = (int) Elf32Util.findEmptyHashOffsetWithSymName(elfType32, str.getBytes());
        return offset;

    }

    private String getOldStrHexIndex(ElfType32 elfType32, String oldStr){
        int index = Elf32Util.findSymIndexWithName(elfType32, oldStr);
        String hexIndex = StringUtil.int2HexString(index);
        System.out.println("old name="+oldStr+", hex index="+hexIndex);
        return hexIndex;
    }

    private void replaceStrtb(byte[] bytes, int strtbOffset, String strNew){
        StringUtil.replaceByteAry(bytes, strtbOffset, strNew.getBytes());
    }

    private void replaceHashtb(byte[] bytes, int hashtbOffset, String hashHexIndex){
        StringUtil.replaceByteAry(bytes, hashtbOffset,StringUtil.hexString2Bytes(hashHexIndex));
    }
}
