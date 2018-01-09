package com.jackzhang.bootes.controller;

import com.jackzhang.bootes.common.Constants;
import com.jackzhang.bootes.common.ResponseData;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

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

        if (!getResponse.isExists()){
            return new ResponseData().isOk(0).msg("未查询到相关信息");
        }
        return new ResponseData().isOk(1).data(getResponse.getSource()).msg("查询成功");
    }
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    public ResponseData add(@RequestParam(name="title") String title, @RequestParam(name = "author") String author,
                            @RequestParam(name = "word_count")int wordCount, @RequestParam(name = "publish_date")
                                        String publishDate){
        try {
            XContentBuilder contentBuilder= XContentFactory.jsonBuilder().startObject()
                    .field("title",title)
                    .field("author",author)
                    .field("word_count",wordCount)
                    .field("publish_date",publishDate)
                    .endObject();
            IndexResponse response=this.client.prepareIndex(Constants.INDEX_BOOK,Constants.TYPE_NOVEL).setSource(contentBuilder).get();
            return new ResponseData().isOk(1).msg("添加成功").data(response.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseData().isOk(0).msg("添加异常");
    }
}
