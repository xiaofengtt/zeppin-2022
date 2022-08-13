package com.wangjie.androidbucket.services.network;

import com.wangjie.androidbucket.log.Logger;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-24 14:02
 */
public class HippoRequestQueue {

    private static final String TAG = HippoRequestQueue.class.getName();

    private AtomicInteger seqGenerator = new AtomicInteger();

    /**
     * 正在等待队列
     */
    private PriorityBlockingQueue<HippoRequest> waitingQueue;

    /**
     * 网络访问类
     */
    private HippoNetworkDispatcher[] hippoNetworkDispatchers;

    /**
     * 默认最大线程数量
     */
    private static final int MAX_THREAD_POOL_SIZE = 10;

    /**
     * 线程池大小
     */
    private final int threadPoolSize;

    /**
     * 网络请求执行
     */
    private Network network;

    public HippoRequestQueue(Network network) {
        this(network, MAX_THREAD_POOL_SIZE);
    }

    /**
     * 构造方法，初始化Queue
     */
    public HippoRequestQueue(Network network, int threadPoolSize) {
        waitingQueue = new PriorityBlockingQueue<>();
        this.threadPoolSize = threadPoolSize;
        this.network = network;
        start();
    }

    /**
     * @param request
     * @return
     */
    public <T> void add(HippoRequest<T> request) {
        request.seq = seqGenerator.getAndIncrement();
        waitingQueue.add(request);
    }

    /**
     * @return
     */
    public void stop() {
        if (hippoNetworkDispatchers != null) {
            for (HippoNetworkDispatcher dispatcher : hippoNetworkDispatchers) {
                dispatcher.quit();
            }
        }
    }

    public void start() {
        stop();
        try {
            hippoNetworkDispatchers = new HippoNetworkDispatcher[threadPoolSize];
            for (int i = 0; i < hippoNetworkDispatchers.length; i++) {
                hippoNetworkDispatchers[i] = new HippoNetworkDispatcher(waitingQueue, network);
                hippoNetworkDispatchers[i].start();
            }
        } catch (Exception e) {
            Logger.e(TAG, e);
        }
    }

    public static HippoRequestQueue newHippoRequestQueue(Network network) {
        return new HippoRequestQueue(network);
    }

    public static HippoRequestQueue newHippoRequestQueue(Network network,
                                                         int threadPoolSize) {
        return new HippoRequestQueue(network, threadPoolSize);
    }
}
