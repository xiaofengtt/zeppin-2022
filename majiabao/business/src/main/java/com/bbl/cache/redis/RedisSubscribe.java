package com.bbl.cache.redis;


import com.bbl.thread.ThreadPoolExecutorbusiness;
import com.bbl.thread.Threadpool;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public  class RedisSubscribe  implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Thread thread=new Thread (){
//            @Override
//            public void run() {
//                try {
//                    RedisMsgSubListener pubsub = new RedisMsgSubListener();
//                    JedisUtils.subscribeMsg(pubsub,"test");
//                }catch (Exception e){
//                    e.printStackTrace ();
//                }
//            }
//        };
//        thread.run ();
    }
}
