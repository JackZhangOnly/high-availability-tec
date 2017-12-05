package com.jackzhang.bootdubbo.provider;

import com.jackzhang.bootdubboapi.DemoService;

public class DemoServiceImpl implements DemoService {

	public String sayHello(String name) {

		return "Here is JackZhang, Hello " + name;
	}

}