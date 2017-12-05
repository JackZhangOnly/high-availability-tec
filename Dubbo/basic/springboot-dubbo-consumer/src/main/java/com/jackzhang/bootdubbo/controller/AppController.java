package com.jackzhang.bootdubbo.controller;

import com.jackzhang.bootdubboapi.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jackzhang
 */
@RestController
public class AppController {
    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(String name){
        String hello = demoService.sayHello(name);
        return hello;
    }
}
