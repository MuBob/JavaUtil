package struct;

import event.Config;
import utils.FileUtil;

import java.io.File;
import java.util.List;

public class FileProcess {
    private String rootOld;
    private String rootNew;
    private Modify modify;

    public FileProcess(String rootOld, String rootNew) {
        this.rootOld = rootOld;
        this.rootNew = rootNew;
        modify=new Modify();
    }

    public void start(){
        run(rootOld);
    }
    private void run(String rootPath) {
        List<String> subDirList = FileUtil.getSubDirList(rootPath);
        for (int i = 0; i < subDirList.size(); i++) {
            String filePath = subDirList.get(i);
            File file = new File(filePath);
            String fileName = file.getName();
            if (file.isDirectory()) {
                run(file.getAbsolutePath());
            } else if (fileName.equals(Config.STR_FILE_NAME)){
                String parentPathOld = file.getParent();
                String parentPathNew = parentPathOld.replace(rootOld, rootNew);
                File parentFileNew=new File(parentPathNew);
                if (!parentFileNew.exists()) parentFileNew.mkdirs();
                String filePathOld=parentPathOld+File.separator+fileName;
                String filePathNew=parentPathNew+File.separator+fileName;
                modify.modifyFile(filePathOld, filePathNew);
                System.out.println("source file <"+filePathOld+"> has changed to <"+filePathNew+">!");
            }
        }
    }
}
