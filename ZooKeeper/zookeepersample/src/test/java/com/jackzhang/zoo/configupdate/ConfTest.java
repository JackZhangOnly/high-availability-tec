package com.jackzhang.zoo.configupdate;

import com.jackzhang.zoo.ZkContants;
import org.apache.zookeeper.KeeperException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Jack
 */
public class ConfTest {
    @Before
    public void confUpdate(){
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
    @Test
    public void testConfUpdate(){
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
