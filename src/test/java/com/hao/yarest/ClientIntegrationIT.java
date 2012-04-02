package com.hao.yarest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hao.yarest.ProxyFactory;
import com.hao.yarest.mock.RestService;
import com.hao.yarest.mock.Result;



public class ClientIntegrationIT {
	protected static final Result THE_FIRST_STORED_CONTENT = new Result("yaddayaddayadda", 20);
	protected static final Result THE_SECOND_STORED_CONTENT = new Result("blahblahblahblah", 20);
	protected static final String SERVICE_URL = "http://localhost:8080/";
	protected static final long REPLICATION_TIMEOUT = 3000;
	protected static DefaultHttpClient client;
	static RestService proxy;

	@BeforeClass
	public static void setUp() {
		proxy = new ProxyFactory().getProxy(ClientIntegrationIT.SERVICE_URL, RestService.class);
	}
	
	@Test
	public void testPUT() throws ClientProtocolException, IOException {
		//Test empty
		String key = "key-put" + System.currentTimeMillis();
		Result theResult = proxy.searchByKeyword(key);
		assertThat(theResult.getName(), is((nullValue())));
		
		//Test 1st put
		proxy.save(key, THE_FIRST_STORED_CONTENT);
		
		//Test get
		theResult = proxy.searchByKeyword(key);
		assertThat(theResult, is(THE_FIRST_STORED_CONTENT));
		
		//Test 2st put
		proxy.save(key, THE_SECOND_STORED_CONTENT);
		
		//Test get
		theResult = proxy.searchByKeyword(key);
		assertThat(theResult, is(not(THE_FIRST_STORED_CONTENT)));
		assertThat(theResult, is(THE_SECOND_STORED_CONTENT));
	}
	
	@Test
	public void testPOST() throws ClientProtocolException, IOException {
		String key = THE_FIRST_STORED_CONTENT.getName()  + System.currentTimeMillis();
		Result obj = new Result(key, key.length());
		//Test empty
		Result theResult = proxy.searchByKeyword(key);
		assertThat(theResult.getName(), is((nullValue())));
		
		//Test 1st put
		proxy.save(obj);
		
		//Test get
		theResult = proxy.searchByKeyword(key);
		assertThat(theResult, is(obj));
	}
	
	@Test
	public void testDELETE() throws ClientProtocolException, IOException {
		String key = "key-delete" + System.currentTimeMillis();
		//Test empty
		Result theResult = proxy.searchByKeyword(key);
		assertThat(theResult.getName(), is((nullValue())));
		
		//Test 1st put
		proxy.save(key, THE_FIRST_STORED_CONTENT);
		
		//Test get
		theResult = proxy.searchByKeyword(key);
		assertThat(theResult, is(THE_FIRST_STORED_CONTENT));
		
		//Test delete
		proxy.delete(key);
		
		//Test get
		theResult = proxy.searchByKeyword(key);
		assertThat(theResult.getName(), is((nullValue())));
	}
	
//	@Test
	public void testHEAD() {
		//TODO Missing feature
	}
}
