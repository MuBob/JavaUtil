package content;

import process.RenameProcess;

public class Context {
    public static void main(String[] args){
        RenameProcess process=new RenameProcess();
        System.out.print("_____________开始复制\r\n");
        process.run();
        System.out.print("!!!!!!!!!!!!!复制结束！");
    }
}
