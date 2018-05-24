package util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class FileUtil {
    public static List<String> getSubDirList(String path) {
        File rootFile = new File(path);
        Log.d("根文件path=" + rootFile.getAbsolutePath());
        if (!rootFile.exists()) {
            Log.e("根目录不存在,file=" + rootFile);
            return null;
        }
        String[] list = rootFile.list();
        ArrayList<String> results = new ArrayList<String>();
        for (int i = 0; list!=null&&i < list.length; i++) {
            String fileName = list[i];
            File file = new File(rootFile, fileName);
                Log.d("当前position=" + i + ", path=" + fileName);
                results.add(rootFile+File.separator+fileName);
        }
        return results;
    }

    public static int findStrInFile(File file, String targetStr){
        if (file==null){
            Log.e("警告：传入的文件参数为空！");
            return -1;
        }
        try {
            StringBuilder fileContent=new StringBuilder();
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String lineStr=null;
            while ((lineStr=br.readLine())!=null){
                fileContent.append(lineStr).append("\n");
            }
            br.close();
            reader.close();
            return fileContent.indexOf(targetStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(String.format("在文件(%s)中寻找str(%s)时出现异常:%s", file.getAbsolutePath(), targetStr, e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(String.format("读取文件(%s)时出现异常:%s", file.getAbsolutePath(), e.getMessage()));
        }
        return -2;
    }


    public static int replaceFileCount=0;
    public static int replaceFileSuccessCount=0;
    public static int replaceFileFailCount=0;

    public static void init(){
        replaceFileCount=0;
         int replaceFileSuccessCount=0;
          int replaceFileFailCount=0;
    }
    public static boolean replaceStrInFile(File file, String oldStr, String newStr){
        replaceFileCount++;
        if (file==null){
            Log.e("警告：传入的文件参数为空！");
            return false;
        }
        try {
            StringBuilder fileContent=new StringBuilder();
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String lineStr=null;
            while ((lineStr=br.readLine())!=null){
                fileContent.append(lineStr).append("\n");
            }
            br.close();
            reader.close();
            String replace = fileContent.toString().replace(oldStr, newStr);
            FileWriter fw=new FileWriter(file);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(replace);
            bw.close();
            fw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(String.format("在文件(%s)中寻找str(%s)时出现异常:%s", file.getAbsolutePath(), oldStr, e.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(String.format("读取文件(%s)时出现异常:%s", file.getAbsolutePath(), e.getMessage()));
        }
        return false;
    }

}
