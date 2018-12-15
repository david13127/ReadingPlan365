package com.thirdleave.readingplan.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @配置
 * @author david
 * @date 2018年12月15日 下午6:58:18
 */
public class PropertiesConfig {

    public static Map<String, String> propertiesMap = new HashMap<>();

    private static void processProperties(Properties props)
        throws BeansException {
        propertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            try {
                // PropertiesLoaderUtils的默认编码是ISO-8859-1,在这里转码一下
                propertiesMap.put(keyStr, new String(props.getProperty(keyStr).getBytes("ISO-8859-1"), "utf-8"));
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadAllProperties(String propertyFileName) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertyFileName);
            processProperties(properties);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String name) {
        return propertiesMap.get(name).toString();
    }

    public static Integer getIntProperties(String name) {
        return Integer.valueOf(propertiesMap.get(name).toString());
    }

    public static Map<String, String> getAllProperty() {
        return propertiesMap;
    }
}
