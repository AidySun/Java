package com.aidy;

import org.junit.Test;

import com.aidy.controller.HomeController;

import static org.junit.Assert.assertEquals;

public class AppTest {

	@Test
	public void testApp() {
		HomeController hc = new HomeController();
		String result = hc.home();
		assertEquals(result, "Hello");
	}
}
