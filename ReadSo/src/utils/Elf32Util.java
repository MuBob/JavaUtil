package utils;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import struct.ElfType32;

import java.util.*;

public class Elf32Util {

    public static void printPhdrList(List<ElfType32.ELF32_phdr> phdrList){
        for(int i=0;i<phdrList.size();i++){
            System.out.println();
            System.out.println("The "+(i+1)+" Program Header:");
            printPhdr(phdrList.get(i));
        }
    }

    public static void printPhdr(ElfType32.ELF32_phdr phdr){
        System.out.println(phdr);
    }

    public static void printShdrList(List<ElfType32.ELF32_shdr> shdrList){
        for(int i=0;i<shdrList.size();i++){
            System.out.println();
            System.out.println("The "+(i+1)+" Section Header:");
            printShdr(shdrList.get(i));
        }
    }
    public static void printShdr(ElfType32.ELF32_shdr shdr){
        System.out.println(shdr);
    }
    public static void printStrTabList(List<ElfType32.ELF32_strtb> list){
        for(int i=0;i<list.size();i++){
            System.out.println();
            System.out.println("The " + (i + 1) + " String Table:");
            printStrtb(list.get(i));
        }
    }
    public static void printStrtb(ElfType32.ELF32_strtb strtb){
        System.out.println(strtb);
    }
    public static void printSymList(List<ElfType32.Elf32_sym> symList){
        for(int i=0;i<symList.size();i++){
            System.out.println();
            System.out.println("The "+(i+1)+" Symbol Table:");
            printSym(symList.get(i));
        }
    }
    public static void printSym(ElfType32.Elf32_sym sym){
        System.out.println(sym);
    }

    @Deprecated
    public static ElfType32.ELF32_strtb getStrByIndex(List<ElfType32.ELF32_strtb> list, int index){
        for (ElfType32.ELF32_strtb strtb : list) {
            if (index == strtb.str_index) {
                return strtb;
            }
        }
        return null;
    }

    public static ElfType32.ELF32_strtb getStrByIndex(Map<Integer, List<ElfType32.ELF32_strtb>> maps, int index){
        Set<Integer> integers = maps.keySet();
//        System.out.println("getStrByIndex():index="+index);
        for (Integer integer : integers) {
//            int i=15;
            for (ElfType32.ELF32_strtb strtb : maps.get(integer)) {
//                if (i==0)
//                    System.out.println("getStrByIndex():strtb="+strtb);
//                i--;
                if (index == strtb.str_index) {
                    return strtb;
                }
            }
        }
        return null;
    }

    public static void printHashTable(ElfType32.ELF32_hashtb hashtb) {
        System.out.println(hashtb);
    }

    public static int findStrtbIndexWithStr(ElfType32 type32, byte[] strBytes){
        Set<Integer> integers = type32.strtbMap.keySet();
//        System.out.println("findStrtbIndexWithStr():str byte="+StringUtil.bytes2HexString(strBytes));
        for (Integer integer : integers) {
//            int i=15;
            for (ElfType32.ELF32_strtb strtb : type32.strtbMap.get(integer)) {
//                if (i==0){
//                    System.out.println("findStrtbIndexWithStr():find name str="+StringUtil.bytes2HexString(strtb.str_name)+", str name="+StringUtil.bytes2UTF8String(strtb.str_name));
//                }
//                i--;
                if (StringUtil.equalBytes(strBytes, strtb.str_name)) {
                    return strtb.str_index;
                }
            }
        }
        return -1;
    }


    public static int findStrtbOffsetWithStr(ElfType32 type32, byte[] strBytes){
        Set<Integer> integers = type32.strtbMap.keySet();
//        System.out.println("findStrtbOffsetWithStr():str byte="+StringUtil.bytes2HexString(strBytes));
        for (Integer integer : integers) {
//            int i=15;
            for (ElfType32.ELF32_strtb strtb : type32.strtbMap.get(integer)) {
//                if (i==0){
//                    System.out.println("findStrtbOffsetWithStr():find name str="+StringUtil.bytes2HexString(strtb.str_name));
//                }
//                i--;
                if (StringUtil.equalBytes(strBytes, strtb.str_name)) {
                    return integer+strtb.str_index;
                }
            }
        }
        return -1;
    }

    public static int findSymIndexWithName(ElfType32 type32, String name){
        for (int i = 0; i < type32.symList.size(); i++) {
            ElfType32.ELF32_strtb strByIndex = getStrByIndex(type32.strtbMap, StringUtil.byte2Int(type32.symList.get(i).st_name));
            if (StringUtil.bytes2UTF8String(strByIndex.str_name).trim().equals(name.trim())){
                return i;
            }
        }

        return -1;
    }


    public static ElfType32.Elf32_sym findSymNameWithStrtbIndex(ElfType32 type32, int index){
//        System.out.println("findSymNameWithStrtbIndex():index="+index);
        for (ElfType32.Elf32_sym sym:type32.symList) {
            if (StringUtil.byte2Int(sym.st_name)==index){
                return sym;
            }
        }
        return null;
    }

    public static long findHashOffsetWithSymName(ElfType32 type32, byte[] symName){
        int strtbIndexWithStr = findStrtbIndexWithStr(type32, symName);
        ElfType32.Elf32_sym symNameWithStrtbIndex = findSymNameWithStrtbIndex(type32, strtbIndexWithStr);
        if (symNameWithStrtbIndex!=null){
            String name = StringUtil.bytes2UTF8String(symName);
            System.out.println("findHashIndexWithSymName():index str name="+name+", symName="+StringUtil.byte2Int(symName));
            long hashN=StringUtil.ELFHash(name.trim());
            int y= (int) (hashN%StringUtil.byte2Int(type32.hashtb.nbucket));
            int value=StringUtil.byte2Int(type32.hashtb.bucket.get(y));
            int offset=type32.hashtb.elf_offset;
            ElfType32.Elf32_sym elf32_sym = type32.symList.get(value);
            System.out.println("findHashIndexWithSymName():query bucket byte="+StringUtil.bytes2HexString(elf32_sym.st_name)+", get y="+y+", with value="+value);
            offset+=type32.hashtb.nbucket.length;
            offset+=type32.hashtb.nchain.length;
            if (StringUtil.byte2Int(symNameWithStrtbIndex.st_name)==StringUtil.byte2Int(elf32_sym.st_name)){
                return offset+y*type32.hashtb.getOneBucketLength();
            }else{
                offset+=type32.hashtb.getOneBucketLength()*StringUtil.byte2Int(type32.hashtb.nbucket);
                System.out.println("findHashIndexWithSymName():offset="+offset);
                y=value;
                value=StringUtil.byte2Int(type32.hashtb.chain.get(y));
                elf32_sym = type32.symList.get(value);
                System.out.println("findHashIndexWithSymName():query chain first byte="+StringUtil.bytes2HexString(elf32_sym.st_name)+", get y="+y+", with value="+value);
                while (StringUtil.byte2Int(elf32_sym.st_name)!=0){
                    if (StringUtil.byte2Int(symNameWithStrtbIndex.st_name)==StringUtil.byte2Int(elf32_sym.st_name)){
                        return offset+y*type32.hashtb.getOneChainLength();
                    }else {
                        y=value;
                        value=StringUtil.byte2Int(type32.hashtb.chain.get(value));
                        elf32_sym = type32.symList.get(value);
                        System.out.println("findHashIndexWithSymName():query byte="+StringUtil.bytes2HexString(elf32_sym.st_name)+", get y="+y+", with value="+value);
                    }
                }
            }


        }
        return -1;
    }

    public static long findEmptyHashOffsetWithSymName(ElfType32 type32, byte[] symName){
        String name = StringUtil.bytes2UTF8String(symName);
        long hashN=StringUtil.ELFHash(name.trim());
        int y= (int) (hashN%StringUtil.byte2Int(type32.hashtb.nbucket));
        int value=StringUtil.byte2Int(type32.hashtb.bucket.get(y));
        int offset=type32.hashtb.elf_offset;
//        ElfType32.Elf32_sym elf32_sym = type32.symList.get(value);
        offset+=type32.hashtb.nbucket.length;
        offset+=type32.hashtb.nchain.length;
        if (value==ElfType32.STN_UNDEF){
            return offset+y*type32.hashtb.getOneBucketLength();
        }else{
            offset+=type32.hashtb.getOneBucketLength()*StringUtil.byte2Int(type32.hashtb.nbucket);
            y=value;
            value=StringUtil.byte2Int(type32.hashtb.chain.get(y));
//            elf32_sym = type32.symList.get(value);
            while (true){
                if (value==ElfType32.STN_UNDEF){
                    return offset+y*type32.hashtb.getOneChainLength();
                }else {
                    y=value;
                    value=StringUtil.byte2Int(type32.hashtb.chain.get(value));
//                    elf32_sym = type32.symList.get(value);
                }
            }
        }
    }

}
