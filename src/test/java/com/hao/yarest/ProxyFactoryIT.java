package com.hao.yarest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hao.yarest.ProxyFactory;
import com.hao.yarest.mock.RestService;
import com.hao.yarest.mock.Result;



public class ProxyFactoryIT {

	@Test
	public void testGet() {
		ProxyFactory proxyFactory = new ProxyFactory();
		String url = "http://localhost:8080/";
		
		Object proxy = proxyFactory.getProxy(url, RestService.class);
		assertTrue(proxy instanceof RestService);
		RestService service = (RestService) proxy;
		
		Result res = service.searchNothing();
		assertFalse(res == null);
		
		res = service.searchByKeyword("something");
		assertFalse(res == null);
	}
	
}
