package com.test.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.unis.computer.info.domain.utils.FileUtils;

public class XX {

	public XX() {
		// TODO Auto-generated constructor stub
	}

	
	 
	
	public static void main(String[] args) {
		 FileUtils.TxtString("", 1, "");
		
	 String yu = "INSERT INTO TBL_IBP_CATEGORY_PROGRAM_MAP (ID,SERIES_ID,OPERATE_TYPE,ID,CATEGORY_ID,VALID_START,VALID_END,INSERT_TIME,ORIGINAL_TIME,LOG_ID) VALUES "
	 		+ "(SEQ_IBP_CATEGORY_PROGRAM_MAP.NEXTVAL ,'ff8080815d109030015d120348450052','REGIST','ff8080815d4f5fe4015d4f6d45310002','ff8080815d1ab172015d1ad5d3f30007','20170705172001','20470717000000',SYSDATE-350,'20170717','404746')";
	String qo = yu.substring(yu.indexOf(" (")+2, yu.lastIndexOf("ID)")+2);
	//ID,PROGRAM_ID,OPERATE_TYPE,CATEGORY_ID,VALID_START,VALID_END,INSERT_TIME,ORIGINAL_TIME,LOG_ID
	String[] arr = qo.split(",");
	String pe =  arrray(arr); 
	
	
	
	String ao = yu.substring(yu.lastIndexOf("(S")+1, yu.lastIndexOf("')")+1);
	 String[] su = ao.split(",");
	 su =  (String[]) ArrayUtils.remove(su, 3);
	 String sus = Arrays.toString(su).replace(" ", "").replace("[", "(").replace("]", ")");
	 
	
	String in = "INSERT INTO TBL_IBP_CATEGORY_PROGRAM_MAP "+pe+" "+"VALUES "+sus;
	
	 
	 System.err.println(in.replace("SERIES_ID", "PROGRAM_ID"));
	
	//System.err.println(in);
	
	}
	
	public static String  arrray(String[] lists){
		
		 
		List<String> result = new ArrayList<String>();  
		boolean flag;  
		for(int i=0;i<lists.length;i++){  
		    flag = false;  
		    for(int j=0;j<result.size();j++){  
		        if(lists[i].equals(result.get(j))){  
		            flag = true;  
		            break;  
		        }  
		    }  
		    if(!flag){  
		        result.add(lists[i]);  
		    }  
		}  
		String[] arrayResult = (String[]) result.toArray(new String[result.size()]);  
		String results = Arrays.toString(arrayResult).replace(" ", "").replace("[", "(").replace("]", ")");
		 
		 return results;
		 
	}
}
