import java.io.*;
import java.util.stream.Stream;

public class ParseM3U8Context {
    public static void main(String[] args){
        String path="F:\\browser";
        runAll(path);
//        String onePath="F:\\browser\\国产教室.m3u8";
//        parseOne(onePath);
    }

    private static void runAll(String path) {
        File file=new File(path);
        File[] filesM3U8=file.listFiles(pathname -> pathname.getName().endsWith(".m3u8"));
        for (int i = 0; i < filesM3U8.length; i++) {
            File file1 = filesM3U8[i];
            parseOne(file1.getAbsolutePath());
        }
    }

    private static synchronized void parseOne(String m3u8Path){
        try {
            File file=new File(m3u8Path);
            BufferedReader breader=new BufferedReader(new FileReader(file));
            String childPath=null;
            do{
                String s = breader.readLine();
                if (s.endsWith(".ts")){
                    String[] split = s.split("/");
                    for (String aSplit : split) {
                        if (aSplit.startsWith(".")) {
                            childPath = aSplit;
                            break;
                        }
                    }
                    if (childPath!=null){
                        System.out.println(childPath);
                        break;
                    }
                }
            }while(breader.read()!=-1);
            String dir = file.getParent() + File.separator + childPath;
            String outPath=file.getAbsolutePath();
            outPath=outPath.replace("m3u8","mp4");

            File childDir=new File(dir);
            File[] files = childDir.listFiles(pathname -> pathname.getName().endsWith("ts"));
            int size=files.length-1;
            String workPath=System.getProperty("user.dir");
            StringBuffer sb=new StringBuffer("cmd /c start ");
            sb.append(workPath+"\\ParseM3U8\\res\\merge.bat ");
            sb.append(dir);
            sb.append(" " +size+" ");
            sb.append(outPath);
            System.out.println(sb.toString());
            Process ps = Runtime.getRuntime().exec(sb.toString());
            System.out.println(ps.getInputStream());



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
