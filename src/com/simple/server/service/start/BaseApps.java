package com.simple.server.service.start;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.simple.util.MyLoader;

public class BaseApps {
	private String webDir = "web";

	public String getFile(String url) {
		// System.out.println("SYSTEM DIR :" + System.getProperty("user.dir"));
		FileInputStream fin = null;
		String context = "";
		StringBuilder sendString = new StringBuilder();
		sendString.append("HTTP/1.1 200 OK\r\n");
		sendString.append("Content-Type:text/html;Charset=UTF-8\r\n");
		sendString.append("\r\n");
		sendString.append("<html><head><title>Tomcat Demo</title></head></body>");
		sendString.append("<br/>");
		try {
			if (url.endsWith(".html")) {
				File file = new File(webDir + "/" + url);
				System.out.println(file.getPath());
				if (file.exists()) {
					fin = new FileInputStream(file);
					FileChannel channel = fin.getChannel();
					int capacity = 1024;
					ByteBuffer bf = ByteBuffer.allocate(capacity);
					int length = -1;
					while ((length = channel.read(bf)) != -1) {
						bf.clear();
						byte[] bytes = bf.array();
						context = new String(bytes, 0, length);
					}
				}
			} else if (!url.endsWith(".ico")) {
				// System.out.println("URL: " + url);
				MyLoader ml = new MyLoader(webDir);
				Class<?> clazz = ml.findClass("com.simple.test." + url);
				Method[] methods = clazz.getDeclaredMethods();
				try {
					for (Method m : methods) {
						// System.out.println(m.getName());
						if ((m.getReturnType().getName()).equalsIgnoreCase("void")) {
							m.invoke(clazz.newInstance());
						} else {
							context = (String) m.invoke(clazz.newInstance());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		sendString.append(context);
		return sendString.toString();
	}

	public String getWebDir() {
		return webDir;
	}

	public void setWebDir(String webDir) {
		System.out.println("Web Dir : " + webDir);
		this.webDir = webDir;
	}

}
