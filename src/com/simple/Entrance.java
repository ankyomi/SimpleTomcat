package com.simple;

import java.lang.reflect.Method;


public class Entrance {

	public static void main(String[] args) {
		Entrance en = new Entrance();
		try {
			en.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() throws Exception {
		// 利用反射启动Server的start()方法
		Method method = XMLStartUP.class.getMethod("load", (Class [] )null);
        method.invoke(XMLStartUP.class.getConstructor().newInstance(), (Object [])null);
	}
}
