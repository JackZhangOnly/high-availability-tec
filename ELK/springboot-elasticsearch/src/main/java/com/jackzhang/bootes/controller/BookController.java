package com.jackzhang.bootes.controller;

import com.jackzhang.bootes.common.Constants;
import com.jackzhang.bootes.common.ResponseData;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jack
 */
@RestController
@RequestMapping("/book/")
public class BookController {
    @Autowired
    private TransportClient client;

    @RequestMapping(value = "novel.do",method = RequestMethod.GET)
    public ResponseData get(@RequestParam(name="id",defaultValue = "") String id){

        GetResponse getResponse=this.client.prepareGet(Constants.INDEX_BOOK,Constants.TYPE_NOVEL,id).get();
        return new ResponseData().isOk(1).data(getResponse.getSource());
    }
}
