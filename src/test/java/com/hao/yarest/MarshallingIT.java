package com.hao.yarest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.hao.yarest.ProxyFactory;
import com.hao.yarest.mock.RestService;
import com.hao.yarest.mock.Result;


public class MarshallingIT {

	@Test
	public void testMany() {
		RestService proxy = new ProxyFactory().getProxy(ClientIntegrationIT.SERVICE_URL, RestService.class);
		List<Result> list = proxy.getMany();
		assertThat(list.size(), is(2));
		assertThat(list.get(0).getName(), is("Hola"));
		assertThat(list.get(1).getName(), is("Chau"));
		
		assertThat(list.get(0).getAge(), is(6));
		assertThat(list.get(1).getAge(), is(7));
	}
	
}
