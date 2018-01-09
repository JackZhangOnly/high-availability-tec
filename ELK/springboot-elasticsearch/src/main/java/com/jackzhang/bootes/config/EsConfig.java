package com.jackzhang.bootes.config;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.elasticsearch.client.transport.TransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Jack
 */
@Configuration
@PropertySource(value = "classpath:/es.properties")
public class EsConfig {

    @Value("${spring.elasticsearch.host}")
    private String host;
    @Value("${spring.elasticsearch.port}")
    private int port;

    private static final Logger LOG = LogManager.getLogger(EsConfig.class);

    @Bean
    public TransportClient client(){
        try {

            Settings settings = Settings.builder()
                    .put("cluster.name", "jackzhang").build();

            TransportClient client=new PreBuiltTransportClient(settings);
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),port));

            return client;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
