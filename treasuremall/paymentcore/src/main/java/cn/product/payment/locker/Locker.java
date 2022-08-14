package cn.product.payment.locker;

public interface Locker {
    void lock(String key, Runnable command);
}
