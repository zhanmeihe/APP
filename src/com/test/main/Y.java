package com.test.main;

public class Y {

	public Y() {
		// TODO Auto-generated constructor stub
	}

	
	public static void main(String[] args) {
		
		
String ss = "http://10.82.16.175:10075";

ss = ss.substring(ss.indexOf("//")+2,ss.lastIndexOf(":"));
System.err.println(ss);
	}
}
