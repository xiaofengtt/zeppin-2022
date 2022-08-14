package cn.product.worldmall.redisson;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

/**
 * redisson接口
 * @author Administrator
 *
 */
public interface DistributedLocker {
	
	RLock lock(String lockKey);

	RLock lock(String lockKey, long timeout);

	RLock lock(String lockKey, TimeUnit unit, long timeout);

	boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

	void unlock(String lockKey);

	void unlock(RLock lock);
}
