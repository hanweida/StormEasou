package com.easou.let.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-4
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesUtils{
    private static String SYSTEM_CONFIG_FILE = "application.properties";

    //单例
    private static PropertiesUtils stormLetConfiguration;
    private PropertiesUtils(){}

    /**
     * Get instance properties utils.
     *
     * @return the properties utils
     */
    public  static PropertiesUtils getInstance(){
        stormLetConfiguration = new PropertiesUtils();
        return stormLetConfiguration;
    }

    /**
     * The constant properties.
     */
    public static Properties properties = null;
    static {
        InputStream is = null;
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(SYSTEM_CONFIG_FILE);
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Get properties string.
     *
     * @param parameterName the parameter name
     * @return the string
     */
    public String getProperties(String parameterName){
        String value = properties.getProperty(parameterName);
        return value;
    }

}
