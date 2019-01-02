package com.simple.server.service;

import java.io.IOException;

import com.simple.server.service.start.NioServer;

public class Services {
	
	private NioServer nioServer = null;
	private String name = null;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void start() {
		System.out.println(this.getName() + " Services Start");
		try {
			this.nioServer.initServer();
			this.nioServer.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NioServer getNioServer() {
		return nioServer;
	}

	public void setNioServer(NioServer nioServer) {
		this.nioServer = nioServer;
	}

}
