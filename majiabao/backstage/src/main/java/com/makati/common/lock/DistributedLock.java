package com.makati.common.lock;

public interface DistributedLock {
    Boolean tryGetDistributedLock(String lockKey, String requestId, long seconds);

    Boolean releaseDistributedLock(String lockKey, String requestId);
}
