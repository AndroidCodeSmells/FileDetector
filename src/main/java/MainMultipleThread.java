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


        Thread t = new Thread() {
            @Override
            public void run() {  // override the run() to specify the running behavior

                Process p = null;
                try {
                    p = Runtime.getRuntime().exec("java -jar  C:\\Users\\khalid\\Documents\\GitHub\\FileDetector\\classes\\artifacts\\FileDetector_jar\\FileDetector.jar 0 662");
                    BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;

                    while ((line = is.readLine()) != null)
                        System.out.println("p "+line);

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        };
        t.start();  // call back run()


        Thread t1 = new Thread() {
            @Override
            public void run() {  // override the run() to specify the running behavior
                try {
                    Process p1 = Runtime.getRuntime().exec("java -jar  C:\\Users\\khalid\\Documents\\GitHub\\FileDetector\\classes\\artifacts\\FileDetector_jar\\FileDetector.jar 663 1324");
                    BufferedReader is1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                    String line1;

                    while ((line1 = is1.readLine()) != null)
                        System.out.println("p1  "+line1);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        };
        t1.start();  // call back run()

        Thread t2 = new Thread() {
            @Override
            public void run() {  // override the run() to specify the running behavior

                try {
                    Process p2 = Runtime.getRuntime().exec("java -jar  C:\\Users\\khalid\\Documents\\GitHub\\FileDetector\\classes\\artifacts\\FileDetector_jar\\FileDetector.jar 1325 1986");
                    BufferedReader is2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
                    String line2;

                    while ((line2 = is2.readLine()) != null)
                        System.out.println("p2 "+line2);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        };
        t2.start();  // call back run()







    }


}
