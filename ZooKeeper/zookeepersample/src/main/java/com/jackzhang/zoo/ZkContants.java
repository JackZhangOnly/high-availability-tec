package com.jackzhang.zoo;

/**
 * Created by Jack
 */
public class ZkContants {

    /** zookeeper服务器地址 */
    //public static final String connectString = "192.168.0.101:2181,192.168.0.102:2181,192.168.0.104:2181";
    public static final String connectString = "127.0.0.1:2181";

    /** 定义session失效时间 */
    public static final int sessionTimeout = 5000;
    public static final String path = "/root";
    public static final String config = "/config";

}
