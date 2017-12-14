package com.jackzhang.zoo.crud;

import com.jackzhang.zoo.ZkContants;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;


/**
 * Created by Jack
 */
public class ZookeeperClient {
    private ZooKeeper zooKeeper=null;

    public ZookeeperClient(Watcher watcher)throws Exception{
        zooKeeper=new ZooKeeper(ZkContants.connectString,ZkContants.sessionTimeout,watcher);
    }

    /**
     * 创建持久类型Node
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void addPersistent(String path,String data) throws KeeperException, InterruptedException {
        zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void addPersistent(String path,byte[] data) throws KeeperException, InterruptedException {
        zooKeeper.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取指定节点信息（字节数组）
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] getByteData(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getData(path,false,null);
    }

    /**
     * 获取指定节点信息(字符串）
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getData(String path) throws KeeperException, InterruptedException {
        byte[] bytes=getByteData(path);
        bytes=(bytes==null)?"null".getBytes():bytes;
        return new String(bytes);
    }

    /**
     * 获取指定路径子节点
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getChildren(path,false);
    }
    /**
     * 是否存在
     * Stat--节点本身一些状态信息
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat exist(String path) throws KeeperException, InterruptedException {
        return zooKeeper.exists(path,false);
    }

    /**
     * 删除（-1任何版本）
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void delete(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path,-1);
    }

    /**
     * 删除
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void deleteRecursive(String path) throws KeeperException, InterruptedException {
        ZKUtil.deleteRecursive(zooKeeper, path);
    }
    public void close(){
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
