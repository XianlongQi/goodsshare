package com.qixl.goodsshare.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    
        private static Properties p =null;
       
        static {
            InputStream in = null;
           
            try {
                in = PropertiesUtils.class.getClassLoader().getResourceAsStream("app.properties");
                p = new Properties();
                p.load(in);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        public static String getProperty(String key) {
            return p.getProperty(key);
        } 
   
}
