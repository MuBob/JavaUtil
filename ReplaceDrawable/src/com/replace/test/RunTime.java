package com.replace.test;

/**
 * Created by Administrator on 2017/3/30.
 */
public class RunTime {
    private static BaseTest testCase;
    public static void main(String[] args){
        try {
            testCase=getInstance();
            testCase.beforeTest();
            try {
                testCase.runTest();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("运行测试时出现异常，e="+e.getMessage());
            }
            testCase.afterTest();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static BaseTest getInstance(){
        return new TestCopyUtil();
    }

}
