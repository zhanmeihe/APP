package com.test.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * downvideo utils
 * @author zhanmeihe
 *
 */
public class UrlVideoDownloaad {

	public UrlVideoDownloaad() {
		// TODO Auto-generated constructor stub
	}
	
	  public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{  
	        URL url = new URL(urlStr);    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
	                //设置超时间为3秒  
	        conn.setConnectTimeout(3*1000);  
		// 防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	  
	     
	        InputStream inputStream = conn.getInputStream();    
	         
	        byte[] getData = readInputStream(inputStream);      
	   
	        File saveDir = new File(savePath);  
	        if(!saveDir.exists()){  
	            saveDir.mkdir();  
	        }  
	        File file = new File(saveDir+File.separator+fileName);      
	        FileOutputStream fos = new FileOutputStream(file);       
	        fos.write(getData);   
	        if(fos!=null){  
	            fos.close();    
	        }  
	        if(inputStream!=null){  
	            inputStream.close();  
	        }  
	  
	  
	        System.out.println("info:"+url+" download success");   
	  
	    }  
	  
	  
	  
	    /** 
	     * 从输入流中获取字节数组 
	     * @param inputStream 
	     * @return 
	     * @throws IOException 
	     */  
	    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
	        byte[] buffer = new byte[1024];    
	        int len = 0;    
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
	        while((len = inputStream.read(buffer)) != -1) {    
	            bos.write(buffer, 0, len);    
	        }    
	        bos.close();    
	        return bos.toByteArray();    
	    }    
	  
	    public static void main(String[] args) {  
	        try{  
	            downLoadFromUrl("http://flv4.bn.netease.com/videolib3/1706/10/cQHEY4460/SD/cQHEY4460.flv",
						"cQHEY4460.flv", "F:/app-video/");  
	        }catch (Exception e) {  
	            // TODO: handle exception  
	        }  
	    }

}
