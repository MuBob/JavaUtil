package com.replace.util;

import com.replace.bean.FileBean;
import com.replace.content.Config;
import com.util.FileUtil;
import com.util.Log;
import com.util.StringUtil;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2017/3/30.
 */
public class ReplaceUtil {
    public static ReplaceUtil copyUtil;
    public static ReplaceUtil getInstance(){
        if (copyUtil==null){
            copyUtil=new ReplaceUtil();
        }
        return copyUtil;
    }

    private ReplaceUtil(){
        replaceMap=new HashMap<>();
        replaceFileBeans=new ArrayList<>();
    }

    private Map<String, String> replaceMap;
    private List<FileBean> replaceFileBeans;
    private String rootPath;
    private List<String> replaceFiles;

    public ReplaceUtil clearReplaceMap(){
        replaceMap.clear();
        replaceFileBeans.clear();
        return this;
    }
    public Map<String, String> getReplaceMap(){
        return replaceMap;
    }

    public List<FileBean> getReplaceFileBeans(){
        return replaceFileBeans;
    }
    public ReplaceUtil setCopyFile(File file){
        String[] list = file.list();
        for (String aPngFile : list) {
            Log.d("当前分割文件="+aPngFile);
            String[] aPngArray = StringUtil.splitFileNameAndLast(aPngFile);
            FileBean fileBean=new FileBean(aPngArray[0], aPngArray[1]);
            Log.d("获取去掉后缀的文件名="+fileBean.getHead()+"!");
            String[] split = fileBean.getHead().split(Config.TEMPLETE_SPLIT_CHAR);
            Log.d("分割正常文件名后的替换与被替换长度="+split.length+", 其中第一部分="+split[0]);
            if (split.length!=2){
                Log.e("分隔符获取失败！当前文件名="+aPngFile);
                continue;
            }
            replaceMap.put(split[0], split[1]);
            fileBean.setOldName(split[0]);
            fileBean.setNewName(split[1]);
            replaceFileBeans.add(fileBean);
        }

        return this;
    }

    /**
     * 设置替换文件的根目录
     * @param rootPath
     * @return
     */
    public ReplaceUtil setReplaceTargetPath(String rootPath, List<String> fileNames){
        this.rootPath=rootPath;
        this.replaceFiles=fileNames;
        return this;
    }

    public boolean runReplace(){

//        if (replaceMap.size()<=0||StringUtil.isEmpty(rootPath)||replaceFiles==null||replaceFiles.size()<=0)
        boolean replace=true;
        if (replaceFileBeans.size()<=0||StringUtil.isEmpty(rootPath)) {
            Log.e("要替换的文件为空");
            return false;
        }
        if (replaceFiles==null||replaceFiles.size()<=0){
            Log.e("目标文件夹不存在或里边没有文件");
            replace=false;
        }
        Iterator<FileBean> iterator = replaceFileBeans.iterator();
       try {
            for (String file : replaceFiles) {
                String curReplaceDir = rootPath + File.separator + file;
                Log.i("当前文件夹="+curReplaceDir);
                if (replace)
                    replaceDirFiles(curReplaceDir);
                List<String> chineaseFiles = FileUtil.getChineaseFileInDir(curReplaceDir);
                int size = chineaseFiles.size();
                Log.i("当前文件夹="+curReplaceDir+", 有汉字文件数量="+size);
                for (int i = 0; i < size; i++) {
                    FileUtil.delete(curReplaceDir, chineaseFiles.get(i));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("出现异常e="+e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 替换当前路径下的子文件名称
     * @param path
     * @return
     */
    public Map<String, Boolean> replaceDirFiles(String path){
        File file=new File(path);
        Log.d("当前执行替换文件夹="+file);
        if (!file.exists()||file.isFile()) {
            Log.e("当前文件不存在或不是文件夹="+file);
            return null;
        }
        String[] list = file.list();
        if (list==null) {
            Log.e("当前文件夹下没有子文件="+file);
            return null;
        }
        Map<String, Boolean> resultMap=new HashMap<>();
        Log.d("得到的列表长度="+list.length);
        int i=0;
        for (String aList : list) {
            Log.d("当前文件p="+i+", 名称="+aList);
            i++;
            File pngFile=new File(file, aList);
            Log.d("pngFile="+pngFile);
            String oldName = pngFile.getName();
            FileBean beanFromListByName=null;
            if (Config.TEMPLETE_FILE_NAME.equals(file.getName())){
                //模板文件中查找方案
                beanFromListByName=findBeanFromListByNameTemplete(oldName);
            }else {
                beanFromListByName=findBeanFromListByName(oldName);
            }
            if (beanFromListByName==null){
                Log.e("查询失败，查询键="+oldName);
                continue;
            }
            String newName=beanFromListByName.getNewName()+Config.FILE_POINT+beanFromListByName.getLast();
            Log.d("父层文件夹名称="+pngFile.getParent()+", oldName="+oldName+", newName="+newName);
            boolean isRenameSuccess = FileUtil.renameFile(pngFile.getParent(), oldName, newName);
            resultMap.put(beanFromListByName.getId(), isRenameSuccess);
        }
        return resultMap;
    }

    /**
     * 根据传入的文件名（带后缀）查找是否是要替换的文件，如果不是则返回空
     * @param name
     * @return
     */
    private FileBean findBeanFromListByName(String name){
        for (FileBean oneBean:replaceFileBeans) {
            String fileName = oneBean.getOldName() + Config.FILE_POINT + oneBean.getLast();
            Log.d("当前对比keyName="+name+", fileName="+fileName);
            if (fileName.equals(name))
                return oneBean;
        }
        return null;
    }

    /**
     * 模板文件名匹配规则，根据传入的文件名（带后缀）查找是否是要替换的文件，如果不是则返回空
     * @param name
     * @return
     */
    private FileBean findBeanFromListByNameTemplete(String name){
        for (FileBean oneBean:replaceFileBeans) {
            String fileName = oneBean.getHead() + Config.FILE_POINT + oneBean.getLast();
            Log.d("当前对比keyName="+name+", fileName="+fileName);
            if (fileName.equals(name))
                return oneBean;
        }
        return null;
    }

}
