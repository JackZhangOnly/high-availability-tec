package com.jackzhang.bootes.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by gx
 * Date: 2017-09-13
 * Time: 21:41
 */
//@Configuration
public class ElasticsearchConfig {

    //@Bean
    public TransportClient client() throws UnknownHostException {
        //设置端口名字
        InetSocketTransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName("192.168.60.129"),
                9300
        );
        //设置名字
        Settings settings = Settings.builder().put("cluster.name","jackzhang").build();

        TransportClient client =  new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);
        return client;
    }



}
