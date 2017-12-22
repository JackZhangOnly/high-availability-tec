package com.jackzhang.session.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack.
 */
@RestController
public class SessionController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Object login(HttpServletRequest request){
        request.getSession().setAttribute("message",request.getRequestURL());
        Map<String,Object> map=new HashMap<>();
        map.put("url",request.getRequestURL());
        return map;
    }
    @RequestMapping(value = "/session",method = RequestMethod.GET)
    public Object session(HttpSession session){
        Map<String,Object> map=new HashMap<>();
        map.put("sessionId",session.getId());
        map.put("message",session.getAttribute("message"));

        return map;
    }
}
