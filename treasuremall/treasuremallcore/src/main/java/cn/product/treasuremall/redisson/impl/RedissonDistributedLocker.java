package cn.product.treasuremall.redisson.impl;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import cn.product.treasuremall.redisson.DistributedLocker;

/**
 * redisson接口实现
 * @author Administrator
 *
 */
public class RedissonDistributedLocker implements DistributedLocker {
	
	@Autowired
	private RedissonClient redissonClient; // RedissonClient已经由配置类生成，这里自动装配即可

	// lock(), 拿不到lock就不罢休，不然线程就一直block
	@Override
	public RLock lock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock();
		return lock;
	}

	// leaseTime为加锁时间，单位为秒
	@Override
	public RLock lock(String lockKey, long leaseTime) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(leaseTime, TimeUnit.SECONDS);
		return null;
	}

	// timeout为加锁时间，时间单位由unit确定
	@Override
	public RLock lock(String lockKey, TimeUnit unit, long timeout) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.lock(timeout, unit);
		return lock;
	}

	@Override
	public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
		RLock lock = redissonClient.getLock(lockKey);
		try {
			return lock.tryLock(waitTime, leaseTime, unit);
		} catch (InterruptedException e) {
			return false;
		}
	}

	@Override
	public void unlock(String lockKey) {
		RLock lock = redissonClient.getLock(lockKey);
		lock.unlock();
	}

	@Override
	public void unlock(RLock lock) {
		lock.unlock();
	}
}
