package com.test.main;

public class Sor {

	public Sor() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		/**
		 * INSERT INTO TBL_IBP_CATEGORY_CHANNEL_MAP
		 * (ID,ELEMENT_ID,ACTION,PARENT_CODE
		 * ,ID,ELEMENT_CODE,PARENT_ID,SEQUENCE,VALID_START
		 * ,VALID_END,INSERT_TIME,ORIGINAL_TIME,LOG_ID) VALUES
		 * (SEQ_IBP_CATEGORY_CHANNEL_MAP.NEXTVAL ,
		 * 'ff8080814581e8a00145884958ea722c','REGIST','77777777880000092017071900000004',
		 * 'ff8080815d4f5fe4015d59e6ef3a01df','00000021000000022014042200000004',
		 * 'ff8080815d4f5fe4015d589d42b90072','9992','20140422000000',
		 * '20440422000000',SYSDATE-350,'20170719','404841'
		 * )
		 */
		String rus = "INSERT INTO TBL_IBP_CATEGORY_CHANNEL_MAP(ID,ELEMENT_ID,ACTION,PARENT_CODE,ID,ELEMENT_CODE,"
				+ "PARENT_ID,SEQUENCE,VALID_START,VALID_END,INSERT_TIME,ORIGINAL_TIME,LOG_ID) "
				+ "VALUES(SEQ_IBP_CATEGORY_CHANNEL_MAP.NEXTVAL ,'ff8080814581e8a00145884958ea722c',"
				+ "'REGIST','77777777880000092017071900000004','ff8080815d4f5fe4015d59e6ef3a01df',"
				+ "'00000021000000022014042200000004','ff8080815d4f5fe4015d589d42b90072','9992','20140422000000',"
				+ "'20440422000000',SYSDATE-350,'20170719','404841')";
		
		String start = "INSERT INTO TBL_IBP_CATEGORY_CHANNEL_MAP "
				+ "(ID,CHANNEL_ID,CATEGORY_ID,ELEMENT_ID,"
				+ "ACTION,PARENT_CODE,ELEMENT_CODE,PARENT_ID,"
				+ "SEQUENCE,VALID_START,VALID_END,INSERT_TIME,"
				+ "ORIGINAL_TIME,LOG_ID) VALUES ";
		 
		
		String[] ss = rus.substring(rus.indexOf("(SEQ")+1, rus.lastIndexOf("')")+1).replace(" ", "").split(",");
		String[] dos ={"("+ss[0]+",",ss[1]+",",ss[1]+",",ss[2]+",",ss[3]+",",ss[4]+",",ss[5]+",",ss[6]+",",ss[7]+",",ss[8]+",",ss[9]+",",
				ss[10]+",",ss[11]+",",ss[12]+")"};
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dos.length; i++) {
			sb.append(dos[i]);
		}
		
		String cd  =start + sb.toString();
		System.err.println(cd);
		
		
		/**
		 * 
						 
		 */
	}

}
