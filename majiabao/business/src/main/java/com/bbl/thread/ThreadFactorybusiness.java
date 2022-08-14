package com.bbl.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;

@Slf4j
public class ThreadFactorybusiness implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
//        thread.setName("task"+System.currentTimeMillis());

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {   //捕获线程异常，注意 threadPoolExecutor.execute ，submit会失效
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.error(t.getName()+" exception " + e.getMessage());
            }
        });

        return thread;
    }
}
