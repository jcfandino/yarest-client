package com.hao.yarest.mock;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;


public class RestServer {

	private Server server;

	public RestServer() {
		server = new Server(8080);
		Context root = new Context(server,"/service",Context.SESSIONS);
		root.addServlet(new ServletHolder(new RestServlet()), "/*");
	}
	
	public void start() {
		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}