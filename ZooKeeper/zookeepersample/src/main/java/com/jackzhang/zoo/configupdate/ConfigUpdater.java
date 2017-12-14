package com.jackzhang.zoo.configupdate;

import com.jackzhang.zoo.ZkContants;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jack
 */
public class ConfigUpdater {
    private ZooStore store;
    private Random random=new Random();

    public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        store=new ZooStore();
        store.connect(hosts);
    }
    public void run() throws KeeperException, InterruptedException {
        while (true){
            String value=random.nextInt(1000)+"";
            store.write(ZkContants.config,value);

            TimeUnit.SECONDS.sleep(2);
        }
    }

    public static void main(String[] args) {
        try {
            ConfigUpdater updater=new ConfigUpdater(ZkContants.connectString);
            updater.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (KeeperException e){
            e.printStackTrace();
        }
    }

}
