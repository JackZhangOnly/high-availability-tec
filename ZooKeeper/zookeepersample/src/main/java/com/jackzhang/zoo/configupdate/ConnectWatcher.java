package com.jackzhang.zoo.configupdate;

import com.jackzhang.zoo.ZkContants;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Jack
 */
public class ConnectWatcher implements Watcher {
    protected ZooKeeper zooKeeper;
    CountDownLatch countDownLatch=new CountDownLatch(1);
    public void connect(String host) throws IOException, InterruptedException {
        zooKeeper=new ZooKeeper(host, ZkContants.sessionTimeout,this
        );
        countDownLatch.await();
    }
    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    public void process(WatchedEvent event) {
        //与zk建立连接
        if (event.getState()== Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
    }
}
