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
     *
     * @param line the line
     * @return boolean
     */
    public static boolean validateAspLog(String line){
        return aspLogFilter.validate(line);
    }

    /**
     * Line analise 2 int int.
     *
     * @param line      the line
     * @param beginFlag the begin flag
     * @return the int
     */
    public static int lineAnalise2Int(String line, String beginFlag){
        String temp = line.substring(line.indexOf(beginFlag) + beginFlag.length(), line.indexOf(" ", line.indexOf(beginFlag) + beginFlag.length()));
        return Integer.parseInt(temp);
    }

    /**
     * 截取字符串从开始位置
     * 如果有空格截取到空格字符串
     * 如果没有有空格截取到行末
     *
     * @param line      the line
     * @param beginFlag the begin flag
     * @return string
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

    /**
     * Wite file.
     *
     * @param str  the str
     * @param path the path
     * @throws IOException the io exception
     */
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
     *
     * @param list the list
     * @param path the path
     * @throws IOException the io exception
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

    /**
     * Read file string.
     *
     * @param filepath the filepath
     * @return the string
     * @throws IOException the io exception
     */
    public static String readFile(String filepath) throws IOException {
        File file= new File(filepath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str = "";
        return null;
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
