package com.product.worldpay.locker;

public interface Locker {
    void lock(String key, Runnable command);
}
