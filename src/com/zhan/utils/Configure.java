package com.zhan.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class Configure {
	 
	  //    private static final Log log = LogFactory.getLog(ServerConfig.class);
	     private static Properties config = null;    
	     
	    public Configure() {
	          config = new Properties();
	    }
	    
//	    static{
//	    	
//	    }
//	    
//	     
	     public Configure(String filePath) {
	         config = new Properties();
	        try {
	              ClassLoader CL = this.getClass().getClassLoader();
	             InputStream in;
	             if (CL != null) {
	                  in = CL.getResourceAsStream(filePath);
	            }else {
	              in = ClassLoader.getSystemResourceAsStream(filePath);
	            }
            config.load(in);
       //    in.close();
	        } catch (FileNotFoundException e) {
	        //    log.error("服务器配置文件没有找到");
	           System.out.println("服务器配置文件没有找到");
	       } catch (Exception e) {
	       //    log.error("服务器配置信息读取错误");
	             System.out.println("服务器配置信息读取错误");
	        }
	     }
	    
	     public String getValue(String key) {
	    	 
	         if (config.containsKey(key)) {
	             String value = config.getProperty(key);
	              return value;
	         }else {
	             return "";
	         }
	     }
	      
	     public int getValueInt(String key) {
	          String value = getValue(key);
	         int valueInt = 0;
	          try {
	             valueInt = Integer.parseInt(value);
          } catch (NumberFormatException e) {
	             e.printStackTrace();
	              return valueInt;
	         }
	       return valueInt;
	    }  
	      
	     
	     public String getproperties(String key){
	    	 Configure con = new Configure("WeixinKey.ants.properties");
	    	 key = con.getValue(key);
			return key;
	    	 
	    	 
	     }
	  }