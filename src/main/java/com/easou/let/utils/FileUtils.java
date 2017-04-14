package com.easou.let.utils;

import com.easou.let.filters.AspLogFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-7
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {
    private static AspLogFilter aspLogFilter = new AspLogFilter();

    /**
     * 验证asp日志
     * @param line
     * @return
     */
    public static boolean validateAspLog(String line){
        return aspLogFilter.validate(line);
    }

    public static int lineAnalise2Int(String line, String beginFlag){
        String temp = line.substring(line.indexOf(beginFlag) + beginFlag.length(), line.indexOf(" ", line.indexOf(beginFlag) + beginFlag.length()));
        return Integer.parseInt(temp);
    }

    /**
     * 截取字符串从开始位置
     * 如果有空格截取到空格字符串
     * 如果没有有空格截取到行末
     * @param line
     * @param beginFlag
     * @return
     */
    public static String lineAnalise(String line, String beginFlag){
        int index = line.indexOf(" ", line.indexOf(beginFlag) + beginFlag.length());
        String temp = "";
        if(index > 0){
            temp = line.substring(line.indexOf(beginFlag) + beginFlag.length(), index);
        } else {
            temp = line.substring(line.indexOf(beginFlag) + beginFlag.length());
        }
        return temp;
    }

    public static void witeFile(String str, String path) throws IOException {
        File file = new File(path);
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
     * 循环写入
     * @param list
     * @param path
     * @throws IOException
     */
    public static void witeFiles(List<String> list, String path) throws IOException {
        for(String strings : list){
            FileWriter fileWriter = new FileWriter(path, true);
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

    public static String readFile(String filepath) throws IOException {
        File file= new File(filepath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str = "";
        return null;
    }

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
