package com.simple.server;

import com.simple.server.service.Services;

public class Server {
	
	private Services services = null;
	private String name = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void start() {
		System.out.println(this.getName() + " Server Start");
		this.services.start();
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
	
	
}
