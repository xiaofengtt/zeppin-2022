package com.bbl.thread;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public  class Userpays {
    static String [] pays={"银行卡","余额","积分","优惠券","现金"};




    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long begin = System.currentTimeMillis();

        List<String> temp =userAllpay ("张三");
//        List<String> temp =userAllpay1 ("张三");
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - begin) + "毫秒");
        System.out.println("打印" + temp.toString ());
    }




    @ResponseBody
    public static List<String> userAllpay(String userId) throws ExecutionException, InterruptedException {
        List<FutureTask<String>> futures=new ArrayList<FutureTask<String>> ();
        List<String> userPaylist=new LinkedList<String> ();


        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (String paytype: pays) {
            FutureTask<String> future = new FutureTask<String>(new Future (userId,paytype));
            java.util.concurrent.Future f1 = executor.submit(future);
//            java.util.concurrent.Future f1=Threadpool.getThreadpool ().submit (future);
            futures.add (future);
        }

        for (java.util.concurrent.Future futrue : futures) {
            userPaylist.add (futrue.get ().toString ());
        }
        executor.shutdown();

        return userPaylist;
    }

    @ResponseBody
    public static List<String> userAllpay1(String userId) throws ExecutionException, InterruptedException {
        List<String> userPaylist=new ArrayList<> ();

        for (String paytype: pays) {
            userPaylist.add (canUserThisPayMethod(userId,paytype));
        }

        return userPaylist;
    }




    public static class Future implements Callable<String> {
        private String payMethod;
        private String userId;

        public Future(String payMethod,String  userId) {
            this.userId = userId;
            this.payMethod = payMethod;
        }


        @Override
        public String call() throws Exception {
            String result = canUserThisPayMethod(userId,payMethod);
            return result;
        }


    }

    private static String canUserThisPayMethod(String userId, String payMethod) throws InterruptedException {

        //单次查询花费一秒
        Thread.sleep (1000L);
        return userId+payMethod;
    }

}
