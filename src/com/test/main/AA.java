package com.test.main;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AA {

	public AA() {
		// TODO Auto-generated constructor stub
	}

	public   static   void  removeDuplicate(List list)  {
		   for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
		    for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
		      if  (list.get(j).equals(list.get(i)))  {
		        list.remove(j);
		      } 
		    } 
		  } 
		  System.out.println(list);
		}
	
	public static Timestamp formTime(String timePeriod, Timestamp startTime) {
		long hh = -1L;
		long mm = -1L;
		long ss = -1L;
		long totalTimeLong = -1L;
		if (!timePeriod.equals("")) {
			if (timePeriod.substring(0, 1).equals(0))
				hh = Integer.parseInt(timePeriod.substring(1, 2)) * 3600;
			else
				hh = Integer.parseInt(timePeriod.substring(0, 2)) * 3600;
			if (timePeriod.substring(3, 4).equals(0))
				mm = Integer.parseInt(timePeriod.substring(4, 5)) * 60;
			else

				mm = Integer.parseInt(timePeriod.substring(3, 5)) * 60;
			if (timePeriod.substring(6, 7).equals(0))
				ss = Integer.parseInt(timePeriod.substring(7));
			else
				ss = Integer.parseInt(timePeriod.substring(6));
			totalTimeLong = (hh + mm + ss) * 1000;
			System.err.println(startTime.getTime() + totalTimeLong);
			return new Timestamp(startTime.getTime() + totalTimeLong);

		} else {
			return null;
		}
	}
	public  static <T> List<T> compare(T[] t1, T[] t2) {
	    List<T> list1 = Arrays.asList(t1);
	    List<T> list2 = new ArrayList<T>();
	    for (T t : t2) {
	      if (!list1.contains(t)) {
	        list2.add(t);
	      }
	    }
	    System.out.println(list2.get(0));
	    return list2;
	}
	 
	public static void main(String[] args) throws IOException {
	 

   String tu = "ftp://poster:123456@172.16.80.23:21/images/BST/2017/7/7/77777777880000092017070700000001/f01aaffe-1862-4c52-b2e9-dccfdf8895bd.png";
	
   int s = tu.indexOf("@");
   int e = tu.lastIndexOf(":");
   String ti = tu.substring(s+1, e);
    
   System.out.println(ti);
   
//	 
//			String str="3333ABC_001"; 
//			if(str.indexOf("ABC")!=-1){  
//				String f = "1033;1032;";
//				f = f.substring(0, f.lastIndexOf(";"));
//			System.out.println("包含"+f); 
//			}
//			else{ 
//				System.out.println("不包含"); 
//			} 
//		 
//		String strSource = "你想要转码的字符串";  
//		String strSomeEncoding = "GB2312";   //例如utf-8  
//		strSource = new String (strSource.getBytes(Charset.forName(strSomeEncoding)), strSomeEncoding);  
//if (strSource.equals(new String(strSource.getBytes("GB2312"),"GB2312"))) {
//	System.err.println("xxxxxx");
//}else {
//	System.err.println(strSource);
//}
		
		System.out.println(randomString(-229985452)+' '+randomString(-147909649));
	}
	
	
	
	public static String randomString(int seed) {
	Random rand = new Random(seed);
	StringBuilder sb = new StringBuilder();
	while(true) {
	int n = rand.nextInt(27);
	if (n == 0) break;
	sb.append((char) ('`' + n));
	}
	return sb.toString();
		
    }

    /**
     * Gbk2utf8.
     * 
     * @param chenese the chenese
     * 
     * @return the byte[]
     */
    public byte[] gbk2utf8(String chenese) {
        
        // Step 1: 得到GBK编码下的字符数组，一个中文字符对应这里的一个c[i]
        char c[] = chenese.toCharArray();
        
        // Step 2: UTF-8使用3个字节存放一个中文字符，所以长度必须为字符的3倍
        byte[] fullByte = new byte[3 * c.length];
        
        // Step 3: 循环将字符的GBK编码转换成UTF-8编码
        for (int i = 0; i < c.length; i++) {
            
            // Step 3-1：将字符的ASCII编码转换成2进制值
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);
            System.out.println(word);

            // Step 3-2：将2进制值补足16位(2个字节的长度) 
            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            for (int j = 0; j < len; j++) {
                sb.append("0");
            }
            // Step 3-3：得到该字符最终的2进制GBK编码
            // 形似：1000 0010 0111 1010
            sb.append(word);
            
            // Step 3-4：最关键的步骤，根据UTF-8的汉字编码规则，首字节
            // 以1110开头，次字节以10开头，第3字节以10开头。在原始的2进制
            // 字符串中插入标志位。最终的长度从16--->16+3+2+2=24。
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");
            System.out.println(sb.toString());

            // Step 3-5：将新的字符串进行分段截取，截为3个字节
            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            // Step 3-6：最后的步骤，把代表3个字节的字符串按2进制的方式
            // 进行转换，变成2进制的整数，再转换成16进制值
            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            
            // Step 3-7：把转换后的3个字节按顺序存放到字节数组的对应位置
            byte[] bf = new byte[3];
            bf[0] = b0;
            bf[1] = b1;
            bf[2] = b2;
            
            fullByte[i * 3] = bf[0];            
            fullByte[i * 3 + 1] = bf[1];            
            fullByte[i * 3 + 2] = bf[2];
            
            // Step 3-8：返回继续解析下一个中文字符
        }
        return fullByte;
    }

}
