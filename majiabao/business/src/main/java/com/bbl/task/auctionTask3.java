package com.bbl.task;

import com.alibaba.fastjson.JSONObject;
import com.bbl.cache.redis.StaticJedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*定时结束*/
@EnableScheduling
@Component
@Slf4j
public class auctionTask3 {



    @Scheduled(cron = "0 10 * * * ? ")
    private void configureTasks11()  {
        String s = StaticJedisUtils.get ("10minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("10minute_down",jsonObject,0);

         s = StaticJedisUtils.get ("10minute_up");
         jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("10minute_up",jsonObject,0);

        log.info ("执行[趣拍卖]定时到10分钟结束拍卖: " + LocalDateTime.now());
    }




    @Scheduled(cron = "0 25 * * * ? ")
    private void configureTasks25()  {
        String s = StaticJedisUtils.get ("10minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("10minute_down",jsonObject,0);


         s = StaticJedisUtils.get ("25minute_down");
         jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("25minute_down",jsonObject,0);


        s = StaticJedisUtils.get ("10minute_up");
        jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("10minute_up",jsonObject,0);

        s = StaticJedisUtils.get ("25minute_up");
        jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("25minute_up",jsonObject,0);

        log.info ("执行[趣拍卖]定时到25分钟结束拍卖: " + LocalDateTime.now());
    }


    @Scheduled(cron = "0 40 * * * ? ")
    private void configureTasks45()  {
        String s = StaticJedisUtils.get ("10minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("10minute_down",jsonObject,0);

        s = StaticJedisUtils.get ("10minute_up");
        jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("10minute_up",jsonObject,0);

        log.info ("执行[趣拍卖]定时到40分钟结束拍卖: " + LocalDateTime.now());
    }


    @Scheduled(cron = "0 55 * * * ? ")
    private void configureTasks55()  {
        String s = StaticJedisUtils.get ("10minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("10minute_down",jsonObject,0);

        s = StaticJedisUtils.get ("25minute_down");
        jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("25minute_down",jsonObject,0);

        s = StaticJedisUtils.get ("55minute_down");
        jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("55minute_down",jsonObject,0);

        s = StaticJedisUtils.get ("10minute_up");
        jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("10minute_up",jsonObject,0);

        s = StaticJedisUtils.get ("25minute_up");
        jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("25minute_up",jsonObject,0);

        s = StaticJedisUtils.get ("55minute_up");
        jsonObject = JSONObject.parseObject (s);
        jsonObject.put ("auctionstatus",0);
        StaticJedisUtils.set ("55minute_up",jsonObject,0);

        log.info ("执行[趣拍卖]定时到40分钟结束拍卖: " + LocalDateTime.now());
    }





}
