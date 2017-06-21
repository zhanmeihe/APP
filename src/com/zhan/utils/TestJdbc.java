package com.zhan.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 

public class TestJdbc {

	public static void jdbc() {
		long start = System.currentTimeMillis();
		Connection con = null;
		PreparedStatement pre = null;
		ResultSet result = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			System.out.println("��ʼ�����������ݿ⣡");
			String url = "jdbc:mysql://localhost:3306/big_data?useUnicode=true";
			String user = "root"; 
			String password = "123456789"; 
			con = DriverManager.getConnection(url, user, password);// ��ȡ����
			System.out.println("���ӳɹ���");
			String sql = "SELECT TP.PROGNAME FROM TBL_APP_PROGINFO TP WHERE TP.ORIGINID=?"; 
			pre = con.prepareStatement(sql);
			pre.setString(1, "VYL80DMQO");
			pre.setMaxRows(1);
			result = pre.executeQuery();
			while (result.next())
				System.out.println("����:" + result.getString("PROGNAME"));
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// ע��رյ�˳�����ʹ�õ����ȹر�
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				
				long end = System.currentTimeMillis();
				System.err.println("jdbc��ʱΪ" + (end - start));
				System.out.println("���ݿ������ѹرգ�");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void testxml(String url){
		
		String data = Tools.getSource(url, "GBK");
		Matcher m = Pattern.compile("<flv>([^<]+)</flv>").matcher(data);
		if (m.find()) {
			String url2 = m.group(1);
			System.out.println(url2);
		}
	}

	public static void main(String[] args) {
		jdbc();
		 System.out.println(new Date());

	}
}
