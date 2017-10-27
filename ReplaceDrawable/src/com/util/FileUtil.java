package com.util;

import com.replace.content.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class FileUtil {
    public static List<String> getSubDirList(String path) {
        File rootFile = new File(path);
        Log.i("根文件path=" + rootFile.getAbsolutePath());
        if (!rootFile.exists()) {
            Log.e("根目录不存在,file=" + rootFile);
            return null;
        }
        if (rootFile.isFile()) {
            Log.e("传入的目录为文件,file=" + rootFile);
            return null;
        }
        String[] list = rootFile.list();
        List<String> results = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            String fileName = list[i];
            File file = new File(rootFile, fileName);
            if (!file.isFile()) {
                Log.d("当前position=" + i + ", path=" + fileName);
                results.add(fileName);
            }
        }
        return results;
    }

    public static File getTempleteFileName(List<String> subDirList) {
        if (subDirList == null)
            return null;
        for (int i = 0; i < subDirList.size(); i++) {

            String templeteFileName = subDirList.get(i);
            if (Config.TEMPLETE_FILE_NAME.equals(templeteFileName)) {
                File file = new File(Config.REPLACE_PATH_ROOT, templeteFileName);
                return file;
            }
        }
        Log.e("模板文件不存在" + Config.TEMPLETE_FILE_NAME);
        return null;
    }

    /** */
    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public static boolean renameFile(String path, String oldname, String newname) {
        if (!oldname.equals(newname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + File.separator + oldname);
            File newfile = new File(path + File.separator + newname);
            Log.d("新文件=" + newfile + ",  旧文件=" + oldfile);
            if (!oldfile.exists()) {
                Log.e(oldname + "源文件不存在！");
                return false;//重命名文件不存在
            }
            if (newfile.exists()) {//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                Log.e("新文件已经存在！File=" + newfile);
                return false;
            } else {
                oldfile.renameTo(newfile);
                return true;
            }
        } else {
            Log.e("新文件名和旧文件名相同,name=" + oldname);
            return true;
        }
    }

    public static boolean delete(String path){
        File delFile=new File(path);
        if (delFile.exists() && delFile.isFile()) {
            delFile.delete();
            return true;
        }
        return false;
    }
    public static boolean delete(String parentPath, String fileName) {
        File delFile = new File(parentPath, fileName);
        if (delFile.exists() && delFile.isFile()) {
            delFile.delete();
            return true;
        }
        return false;
    }

    /**
     * 获取当前文件夹里边的文件
     *
     * @param dirPath
     * @return
     */
    public static List<String> getFileInDir(String dirPath) {
        List<String> list = new ArrayList<>();
        File file = new File(dirPath);
        if (file.isFile() || !file.exists()) {
            Log.e("当前路径是文件或文件夹不存在：" + file);
            return list;
        }
        String[] list1 = file.list();
        for (String aList1 : list1) {
            File file1=new File(aList1);
            if (file1.isFile()){
                list.add(aList1);
            }
        }
        return list;
    }

    public static String getFileName(String path){
        File file=new File(path);
        if (file.exists() && file.isFile()) {
            String name = file.getName();
            return name;
        }
        return null;
    }
    /**
     * 获取当前文件夹里边的中文名文件
     *
     * @param dirPath
     * @return
     */
    public static List<String> getChineaseFileInDir(String dirPath) {
        List<String> list = new ArrayList<>();
        File file = new File(dirPath);
        if (file.isFile() || !file.exists()) {
            Log.e("当前路径是文件或文件夹不存在：" + file);
            return list;
        }
        String[] list1 = file.list();
        for (String aList1 : list1) {
            if (isChinese(aList1)) {
                list.add(aList1);
            }
            if (isNumeric(aList1)){
                list.add(aList1);
            }
        }
        return list;
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
