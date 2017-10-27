package com.replace.test;

import com.util.FileUtil;

/**
 * Created by Administrator on 2017/3/30.
 */
public class TestFileUtil extends BaseTest {
    private String path;
    private String oldName, newName;
    @Override
    public void beforeTest() {
        path="C:\\Users\\Administrator\\Desktop\\da";
        oldName="aaa.txt";
        newName="b.txt";

    }

    @Override
    public void runTest() {
        FileUtil.renameFile(path, oldName, newName);
    }

    @Override
    public void afterTest() {

    }
}
