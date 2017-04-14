package hbase;

import com.easou.let.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 17-1-13
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public class Statics {
    public static void main(String[] args) throws IOException {
        List<String> pathList = new ArrayList<String>();
        pathList = FileUtils.readfile("D:\\join0116\\join.0116", pathList);
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String path : pathList){
            File file= new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str = "";
            while ((str = bufferedReader.readLine()) != null){
                if(null == map.get(str)){
                    map.put(str, 1);
                } else {
                     int i = map.get(str);
                     map.put(str, i+1);
                }
            }
        }
        System.out.println(map.toString());
       /* String str = "24557,24547,24562,24498,24445,24400,24380,14403,14422,14533,14657,14750,14510,14508,14514,14667,14692,14592,64,2575277,2579343,4,2578795,299004,997804";
        String strArray [] = str.split(",");
        int count = 0;
        for(String s : strArray){
            if(map.containsKey(s)){
                count +=map.get(s);
            }
            if(!map.containsValue(Integer.parseInt(s))){
                System.out.println(s);
            }
        }
        System.out.println(count);*/
    }
}
