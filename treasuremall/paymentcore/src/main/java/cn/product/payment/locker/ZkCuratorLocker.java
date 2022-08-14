package cn.product.payment.locker;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZkCuratorLocker implements Locker{
    public static final String LOCKER_ROOT = "/locker";
    public static final String ACCOUNT_UPDATE_LOCK = "account_update_lock";

    @Autowired
    private CuratorFramework curatorFramework;
    
    //同步锁上锁
    public void lock(String key, Runnable command){
        String path = LOCKER_ROOT + "/" + key;
        InterProcessLock lock = new InterProcessMutex(curatorFramework, path);
        try {
            lock.acquire();
            command.run();
        } catch (Exception e) {
            throw new RuntimeException("get lock error", e);
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                throw new RuntimeException("release lock error", e);
            }
        }
    }
}
