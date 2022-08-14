package com.bbl.thread;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Threadpool {
    private static class ThreadPoolExecutorbusinessstasic {
        private static Integer core=10;
        private static Integer max=20;
        private static Integer timeout=60;
        private static LinkedBlockingQueue<Runnable> queue=new LinkedBlockingQueue<Runnable>();
        private static ThreadFactorybusiness rfb=new ThreadFactorybusiness ();
        private static RejectedExecutionHandlerBusiness rehb=new RejectedExecutionHandlerBusiness ();
        private static ThreadPoolExecutorbusiness threadPool = new ThreadPoolExecutorbusiness(core,max,timeout,TimeUnit.SECONDS, queue,rfb,rehb);


        private ThreadPoolExecutorbusinessstasic(){

            for (int i = 1; i < core; i++) {
                if(i>=core/2){
                    break;
                }
                threadPool.prestartCoreThread();
            }

        }


    }


    public static ThreadPoolExecutorbusiness getThreadpool(){
        return ThreadPoolExecutorbusinessstasic.threadPool;
    }

}
