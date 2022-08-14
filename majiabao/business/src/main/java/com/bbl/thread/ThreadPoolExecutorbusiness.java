package com.bbl.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolExecutorbusiness extends ThreadPoolExecutor {
    public ThreadPoolExecutorbusiness(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super (corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolExecutorbusiness(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super (corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolExecutorbusiness(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super (corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolExecutorbusiness(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super (corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }



    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);

//        log.info("before " + ((Runner) r).getThreadname() + " run");
        log.info("before " + t.getName () + " run");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
//        log.info("after " + ((Runner) r).getThreadname () + " run");
    }

    //线程池终止时执行
    @Override
    protected void terminated() {
        super.terminated();
        log.info("ThreadPoolExecutor is terminated");
    }
}
