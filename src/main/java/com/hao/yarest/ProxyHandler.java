package com.hao.yarest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class ProxyHandler implements InvocationHandler {

	private String url;
	private Class<?> clazz;
	private HttpClient httpClient;

	public ProxyHandler(String url, Class<?> clazz) {
		this.setUrl(url);
		this.setClazz(clazz);
		this.setHttpClient(new DefaultHttpClient());
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		if (method.getDeclaringClass().equals(Object.class)) {
			return method.invoke(this, args);
		}
		MethodDefinition definition = new MethodDefinitionFactory().create(method);
		MethodExecutor executor = new MethodExecutorFactory(this).create(definition, args);
		return executor.execute();
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}
	
}