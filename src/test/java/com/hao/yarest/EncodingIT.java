package com.hao.yarest;

import org.junit.Test;

import com.hao.yarest.ProxyFactory;
import com.hao.yarest.mock.RestService;


public class EncodingIT {
	
	@Test
	public void testEncodingPath() {
		RestService proxy = new ProxyFactory().getProxy(ClientIntegrationIT.SERVICE_URL, RestService.class);
		proxy.searchByKeyword("foo bar");
	}
}
