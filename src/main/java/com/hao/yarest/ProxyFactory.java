package com.hao.yarest;

import java.lang.reflect.Proxy;


public class ProxyFactory {
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(String url, Class<T> clazz) {
		
		Class<?>[] classes;
		if(clazz.isInterface()) {
			classes = new Class[]{clazz};
		} else {
			classes = clazz.getInterfaces();
		}
		return (T) Proxy.newProxyInstance(
			clazz.getClassLoader(),
			classes,
			new ProxyHandler(url, clazz));
	}
	
}
