package com.jackzhang.zoo.configupdate;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

/**
 * Created by Jack
 */
public class ZooStore extends ConnectWatcher{
    /**
     * 写入节点数据
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void write(String path,String data) throws KeeperException, InterruptedException {
        Stat stat=zooKeeper.exists(path,false);
        if (stat==null){
            zooKeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }else{
            zooKeeper.setData(path,data.getBytes(),-1);
        }
    }

    /**
     * 获取节点数据
     * @param path
     * @param watcher
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String read(String path, Watcher watcher) throws KeeperException, InterruptedException {
        byte[] bytes=zooKeeper.getData(path,watcher,null);
        return new String(bytes);
    }
}
