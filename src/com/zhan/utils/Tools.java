package com.zhan.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tools {

	public static String source(String input, String charset) {
		String source = "";
		// 生成一个URL对象
		try {
			URL url = new URL(input);
			// 打开URL
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			// 得到输入流，即获得了网页的内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), charset));
			String line;
			// 读取输入流的数据，并显示
			while ((line = reader.readLine()) != null) {
				source = source + line + "\n";

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return source;

	}
}
