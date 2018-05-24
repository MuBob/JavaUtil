package test;

import process.ReadChineseComment;
import util.Log;

/**
 * Created by Administrator on 2018/5/24.
 */
public class TestReadChineaseComment {
    public static void main(String[] args){
        ReadChineseComment readChineseComment=new ReadChineseComment();
        int count = readChineseComment.readFile("E:\\CProject\\pjproject-2.5\\pjproject-2.4\\MicroSIP-3.12.1-src\\mainDlg.cpp");
        Log.i("count="+count);
    }
}
