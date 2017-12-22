package com.jackzhang.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 * Created by Jack.
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {  
} 