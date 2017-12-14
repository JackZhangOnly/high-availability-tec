package com.jackzhang.zoo.configupdate;

import com.jackzhang.zoo.ZkContants;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

/**
 * Created by Jack
 */
public class ConfigWatcher implements Watcher{
    private ZooStore store;


    public void process(WatchedEvent event) {
        if(event.getType()== Watcher.Event.EventType.NodeDataChanged){
            try{
                dispalyConfig();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }catch(KeeperException e){
            }

        }

    }
    public ConfigWatcher(String hosts) throws IOException, InterruptedException {
        store=new ZooStore();
        store.connect(hosts);
    }
    public void dispalyConfig() throws KeeperException, InterruptedException{
        String value=store.read(ZkContants.config, this);
        System.out.println(ZkContants.config+" has been updated to :"+value);
    }

    public static void main(String[] args) {
        ConfigWatcher configWatcher = null;
        try {
            configWatcher = new ConfigWatcher(ZkContants.connectString);
            configWatcher.dispalyConfig();
            //阻塞等待
            Thread.sleep(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (KeeperException e){
            e.printStackTrace();
        }
    }

}
