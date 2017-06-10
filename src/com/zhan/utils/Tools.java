package com.zhan.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import cn.nusof.common.io.util.IOUtils;

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
	
	public static String getSource(String input, String charset) {
		String source = "";
		try {
			URL url = new URL(input);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
			conn.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("X-CSRF-TOKEN", "c803b337792b77cb22ba0680c116d848f4d06dad");
			conn.setRequestProperty(
					"Cookie",
					"u_id=405545043; _zpdtk=c803b337792b77cb22ba0680c116d848f4d06dad; __ysuid=1496809682233zCM; cna=0G6+ESamlmICATz95bakA6U8; isg=Avj4FgOI1WzhwjnoYoxwKfJ8yKBKyV2ZiHn3VjJpUzPnTZg32nEsew7rc36D; P_pck_rm=NiIqsoGvY0u9itXWJHqjcKbQsiEamZhNrLTiw5vIUH4WhRok7j0Vsx0umtRjIzsBYAs7qUtIKghZbO1y56LyVOrzR87nDi8eeUf8a1PrgY9DKDROG8toUImEpo54PMY4; P_sck=Upz2cLCCdXpFI5C9%2BtuHCyTSwwP7md84b6oShbVBxOnKthvQzsE7CClG%2F1u3OtZySnml9WQ%2BR1UfCUdyYa114ORlgGZaYe5YLeJXuWACWZhbZOOaM%2BQuTXjlGrGTXPfq; P_j_scl=hadCheckLogin; P_gck=NA%7CGHB0%2FNO94esnw%2FPgteyKsw%3D%3D%7CNA%7C1496810292422; u_id=405545043; HasLoaded=true");
			InputStream stream = conn.getInputStream();
			source = IOUtils.toString(stream, charset);
			System.out.println(source);
		} catch (Exception e) {
			return source;
		}
		return source;
	}

}
