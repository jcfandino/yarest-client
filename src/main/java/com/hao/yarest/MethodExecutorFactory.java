package com.hao.yarest;

import org.apache.http.client.methods.HttpUriRequest;

public class MethodExecutorFactory {
	
	private ProxyHandler proxyHandler;
	public MethodExecutorFactory(ProxyHandler proxyHandler) {
		this.proxyHandler = proxyHandler;
	}
	public MethodExecutor create(MethodDefinition def, Object[] args) {
		HttpUriRequest httpRequest = new HttpRequestFactory(proxyHandler.getClazz(), def, args, proxyHandler.getUrl()).create();
		return new MethodExecutor(proxyHandler.getHttpClient(), def, httpRequest);
	}
}