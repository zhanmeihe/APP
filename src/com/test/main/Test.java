package com.test.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhan.utils.PropertiesFactory;
import com.zhan.utils.Tools;

public class Test {

	public Test() {

	}

	public static void main(String[] args) {

		String sos = Tools.getSource(
				"http://c.m.163.com/news/a/CMQBIF29000380D0.html", "utf-8");

		Matcher m = Pattern.compile(
				PropertiesFactory.getProperties("NeteaseVideo").getProperty(
						"script")).matcher(sos);

		while (m.find()) {

			System.out.println(m.group(1).replace("<b>", "")
					.replace("</p>", "").replace("<p>", "").replace("<b>", "")
					.replace("</b>", ""));
		}
	}

}
