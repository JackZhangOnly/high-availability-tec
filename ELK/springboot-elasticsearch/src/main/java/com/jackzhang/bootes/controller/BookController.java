package com.jackzhang.bootes.controller;

import com.jackzhang.bootes.common.Constants;
import com.jackzhang.bootes.common.ResponseData;
import com.jackzhang.bootes.model.Book;
import com.jackzhang.bootes.model.BookQueryCondition;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            return new ResponseData().isOk(0).msg("添加异常");

        }

    }
    @RequestMapping(value = "delete.do",method = RequestMethod.POST)
    public ResponseData delete(@RequestParam(name="id",defaultValue = "") String id){

        DeleteResponse response=this.client.prepareDelete(Constants.INDEX_BOOK,Constants.TYPE_NOVEL,id).get();
        if (!response.getResult().equals(DocWriteResponse.Result.DELETED)){
            return new ResponseData().isOk(0).msg("删除失败");
        }
        return new ResponseData().isOk(1).msg("删除成功");

    }
    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    public ResponseData update(Book book){

        try {
            XContentBuilder contentBuilder= XContentFactory.jsonBuilder().startObject();
            if (book.getTitle()!=null) {
                contentBuilder.field("title",book.getTitle());
            }
            if (book.getAuthor()!=null){
                contentBuilder.field("author",book.getAuthor());
            }
            if (book.getWordCount()>0){
                contentBuilder.field("word_count",book.getWordCount());
            }
            if (book.getPublicDate()!=null){
                contentBuilder.field("publish_date",book.getPublicDate());
            }
            contentBuilder.endObject();

            UpdateResponse response=this.client.prepareUpdate(Constants.INDEX_BOOK,Constants.TYPE_NOVEL,book.getId()).setDoc(contentBuilder).get();
            return new ResponseData().isOk(1).msg("更新成功").data(response.getResult().toString());

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData().isOk(0).msg("更新异常");

        }

    }

    @RequestMapping(value = "query.do",method = RequestMethod.GET)
    public ResponseData query(BookQueryCondition condition){
        try {
            BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
            if (condition!=null&&condition.getAuthor()!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("author",condition.getAuthor()));
            }
            if (condition!=null&&condition.getTitle()!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("title",condition.getTitle()));
            }
            RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("word_count").from(condition.getGt_word_count());

            if (condition!=null&&condition.getLt_word_count()>0){
                rangeQueryBuilder.to(condition.getLt_word_count());
            }
            boolQueryBuilder.filter(rangeQueryBuilder);

            SearchRequestBuilder builder=this.client.prepareSearch(Constants.INDEX_BOOK)
                .setTypes(Constants.TYPE_NOVEL)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setFrom(0)
                .setSize(10);
            SearchResponse response=builder.get();

            List<Map<String,Object>> result=new ArrayList<>();
            for (SearchHit hit:response.getHits()){
                result.add(hit.getSource());
            }
            return new ResponseData().isOk(1).msg("查询成功").data(result);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData().isOk(0).msg("查询失败");

        }

    }
}
