package com.simple.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * 自定义类加载器
 */
public class MyLoader extends ClassLoader{

	private String dir = "web";
	public MyLoader(String dir) {
		this.dir = dir;
	}
//	private final String FILE_PATH= "D:/eclipse_workspace/SimpleTomcat/src/";
	@Override
	public Class<?> findClass(String name){
		
		String classPath = dir+"/"+name.replace(".","/") + ".class";
		//URL url =  MyLoader.class.getResource(classPath);
		byte[] classBytes = null;
		Path path = null;
		
		try {
			path = Paths.get(classPath);
			classBytes = Files.readAllBytes(path);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String[] names = name.split("\\.");
		Class<?> clazz = defineClass(name,classBytes,0,classBytes.length);
		
		return clazz;
	}
}
