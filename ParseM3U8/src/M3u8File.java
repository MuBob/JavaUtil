import java.io.*;
import java.util.Scanner;

public class M3u8File {

    public static void runAll(String path) {
        File file=new File(path);
        File[] filesM3U8=file.listFiles(pathname -> pathname.getName().endsWith(".m3u8"));
        for (int i = 0; i < filesM3U8.length; i++) {
            File file1 = filesM3U8[i];
            parseOne(file1.getAbsolutePath());
        }
    }

    public static synchronized void parseOne(String m3u8Path){
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
            String[] cmdA = { "cmd", "/c", "start", workPath+"\\ParseM3U8\\res\\merge.bat", dir, ""+size, outPath};
            Process ps = Runtime.getRuntime().exec(cmdA);
            Scanner scanner=new Scanner(ps.getInputStream());
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
