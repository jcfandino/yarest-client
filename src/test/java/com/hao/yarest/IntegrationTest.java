package com.hao.yarest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.hao.yarest.mock.RestServer;



@RunWith(Suite.class)
@Suite.SuiteClasses({
	ClientIntegrationIT.class,
	ProxyFactoryIT.class,
	MarshallingIT.class,
	EncodingIT.class
})
public class IntegrationTest {

	private static RestServer restServer;
	
	@BeforeClass
	public static void startUp() throws InterruptedException {
		restServer = new RestServer();
		restServer.start();
		Thread.sleep(1000);
	}
	
	@AfterClass
	public static void tearDown() {
		restServer.stop();
	}
}
