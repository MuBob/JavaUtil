package com.replace.test;

import com.replace.util.ReplaceUtil;

/**
 * Created by Administrator on 2017/3/30.
 */
public class TestCopyUtil extends BaseTest {

    private ReplaceUtil test;
    @Override
    public void beforeTest() {
        test= ReplaceUtil.getInstance();
    }

    @Override
    public void runTest() {
        test.replaceDirFiles("C:\\Users\\Administrator\\Desktop\\登录和注册\\drawable-hdpi");
    }

    @Override
    public void afterTest() {

    }
}
