import entity.ClassEntity;
import entity.XmlEntity;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Arrays;
import java.io.*;


public class MainMultipleThread {


    public static void main(String[] args) throws Exception {
//        String fileExtension = "java";
//        String secondFileExtension = "xml";
//
//        File rootDirectory = new File("G:\\Android\\Apps");
//
//        FileWalker fw = new FileWalker();
//        List<List<Path>> files = fw.getFiles(rootDirectory.getPath(),true,fileExtension,secondFileExtension);
//
//        System.out.println(files.get(0).size());


        File[] dictList = Main.getDirectoriesList();

        int len = dictList.length;
        int diffByFive = len /4;

        int starRange = 0;
        int endRange = diffByFive;
        for (int i = 0; i <4; i++) {


            System.out.println("run process start"+starRange+" end "+endRange);

            runFileDetector( String.valueOf(starRange), String.valueOf(endRange));

            Thread.sleep(400);

            starRange = endRange+1;
            endRange = endRange+diffByFive;


        }





    }

    private static void runFileDetector(String start, String end) {
        String jarURl = "E:\\Khalid\\GitHub\\FileDetector\\classes\\artifacts\\FileDetector_jar\\FileDetector.jar";
        Thread t = new Thread(() -> {  // override the run() to specify the running behavior

            Process p = null;
            try {
                p = Runtime.getRuntime().exec("java -jar "+jarURl+" "+start+" "+end+"");
                BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;

                while ((line = is.readLine()) != null)
                    System.out.println("Process  "+start+"  end "+end+" output  ");

            } catch (IOException e) {
                e.printStackTrace();
            }



        });
        t.start();  // call back run()
    }


}
