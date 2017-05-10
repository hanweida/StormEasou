package hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-12-16
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class FileWrite {
    /**
     * Wite file.
     *
     * @param str the str
     * @throws IOException the io exception
     */
    public static void witeFile(String str) throws IOException {
        File file = new File("D:\\easouLog\\cdplog/click_data.20161116");
        if(!file.exists()){
            file.mkdir();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(str);
        fileWriter.flush();
        printWriter.flush();
    }

    /**
     * Wite files.
     *
     * @param list the list
     * @throws IOException the io exception
     */
    public static void witeFiles(List<String> list) throws IOException {
        for(String strings : list){
            FileWriter fileWriter = new FileWriter("D:\\easouLog\\cdplog\\click_data.20170109Log", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            FileReader fileReader = new FileReader(strings);
            BufferedReader reader = new BufferedReader(fileReader);
            String str = "";
            while (null != (str = reader.readLine())){
                System.out.println(str);
                printWriter.println(str);
            }
            fileWriter.flush();
            printWriter.flush();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        List<String> lists = new ArrayList<String>();

        List<String> list = readfile("D:\\easouLog\\cdplog\\click_data.20170109", lists);
        //witeFile("ddcccccccccccc");
        witeFiles(list);
    }

    /**
     * Readfile list.
     *
     * @param filepath the filepath
     * @param list     the list
     * @return the list
     * @throws FileNotFoundException the file not found exception
     * @throws IOException           the io exception
     */
    public static List<String> readfile(String filepath, List<String> list) throws FileNotFoundException, IOException {

        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println(file.getAbsolutePath());
                list.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        list.add(readfile.getAbsolutePath());
                        System.out.println("absolutepath="
                                + readfile.getAbsolutePath());
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i], list);
                    }
                }
            }
            System.out.println(list.size());
        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return list;
    }
}
