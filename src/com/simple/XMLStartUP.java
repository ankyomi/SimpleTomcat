package com.simple;

import java.io.IOException;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;

import com.simple.server.Server;

public class XMLStartUP {

	public void load() {
		//1、创建Digester对象实例
        Digester digester = new Digester();

        //2、配置属性值
        digester.setValidating(false);
        
        //3、设置匹配模式、规则
        digester.addObjectCreate("Server", "com.simple.server.Server");
        digester.addSetProperties("Server");
        digester.addObjectCreate("Server/Service", "com.simple.server.service.Services");
        digester.addSetProperties("Server/Service");
        digester.addObjectCreate("Server/Service/NioServer", "com.simple.server.service.start.NioServer");
        digester.addSetProperties("Server/Service/NioServer");
        digester.addObjectCreate("Server/Service/NioServer/BaseApps", "com.simple.server.service.start.BaseApps");
        digester.addSetProperties("Server/Service/NioServer/BaseApps");
        digester.addSetNext("Server/Service", "setServices", "com.simple.server.service.Services");
        digester.addSetNext("Server/Service/NioServer", "setNioServer", "com.simple.server.service.start.NioServer");
        digester.addSetNext("Server/Service/NioServer/BaseApps", "setBa", "com.simple.server.service.start.BaseApps");
        //4、开始解析
        try {
			Server server = digester.parse(XMLStartUP.class.getClassLoader().getResourceAsStream("web.xml"));
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
