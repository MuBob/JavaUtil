package com.delete.util;

import com.replace.bean.FileBean;
import com.replace.content.Config;
import com.util.FileUtil;
import com.util.Log;
import com.util.StringUtil;

import javax.swing.plaf.ButtonUI;
import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2017/3/30.
 */
public class DeleteUtil {
    public static DeleteUtil util;
    public static DeleteUtil getInstance(Builder builder){
        if (util==null){
            util=new DeleteUtil(builder);
        }
        return util;
    }
    private DeleteUtil(Builder builder){
        this.builder=builder;
    }
    private Builder builder;

    public boolean run(){
        String parent=builder.parentPath;
        boolean isDeleteParent = searchInDir(parent);
        List<String> subDirList = FileUtil.getSubDirList(parent);
        boolean isDeleteChild=true;
        for (int i = 0; i < subDirList.size(); i++) {
            String path = subDirList.get(i);
            isDeleteChild&=searchInDir(path);
        }
        Log.i("delete Child="+isDeleteChild+", deleteParent="+isDeleteParent);
        return isDeleteChild&&isDeleteParent;
    }

    public boolean searchInDir(String dir){
        List<String> fileInDir = FileUtil.getFileInDir(dir);
        if (fileInDir==null)
            return false;
        for (int i = 0; i <fileInDir.size(); i++) {
            String filePath = fileInDir.get(i);
            String fileName = FileUtil.getFileName(filePath);
            if (fileName!=null&&fileName.matches(builder.formatReg)){
                boolean delete = FileUtil.delete(filePath);
                return delete;
            }
        }
        return false;

    }





    public static class Builder{
        private String formatReg;
        private String parentPath;
        public Builder setParentPath(String parentPath){
            this.parentPath=parentPath;
            return this;
        }
        public Builder setDeleteFileFormat(String reg){
            formatReg=reg;
            return this;
        }

        public DeleteUtil build(){
            return DeleteUtil.getInstance(this);
        }
    }
}
