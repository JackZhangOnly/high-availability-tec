package com.jackzhang.zoo.crud;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Jack
 */
public class ZkClientTest {
    private ZookeeperClient zkClient;
    private String path="/jackzhang";

    @Before
    public void prepareZk(){
        try {
            zkClient=new ZookeeperClient(new Watcher() {
                public void process(WatchedEvent event) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testAdd(){
        User user=new User();
        user.setUserId(111);
        user.setUserName("Jack");

        try {
            zkClient.addPersistent(path+"/haha",NativeSerializable.objectToByte(user));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getChildren(){
        try {
            List<String> children=zkClient.getChildren(path);
            for (String child:children){
                System.out.println(child);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGet(){
        User user=null;
        try {
            user=(User) NativeSerializable.byteToObject(zkClient.getByteData(path));
            System.out.println(user.getUserName());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testExist(){
        try {
            Stat stat=zkClient.exist(path);

            Assert.assertTrue(stat!=null);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @After
    public void close(){
        zkClient.close();
    }
}
