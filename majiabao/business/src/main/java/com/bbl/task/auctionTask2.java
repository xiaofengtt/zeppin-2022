package com.bbl.task;

import com.alibaba.fastjson.JSONObject;
import com.bbl.cache.redis.StaticJedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/*生成数据*/
@EnableScheduling
@Component
@Slf4j
public class auctionTask2 {



    //每二分钟添加关注度和加价人数
    @Scheduled(cron = "0 */2 * * * ? ")
    private void configureTasks11()  {
        Random r=new Random ();
        String s = StaticJedisUtils.get ("10minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.put ("onlookers", jsonObject.getInteger ("onlookers")+r.nextInt (4)); //添加关注人数
            StaticJedisUtils.set ("10minute_down",jsonObject,0);
        }


        s = StaticJedisUtils.get ("25minute_down");
        jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.put ("onlookers", jsonObject.getInteger ("onlookers")+r.nextInt (4)); //添加关注人数
            StaticJedisUtils.set ("25minute_down",jsonObject,0);
        }


        s = StaticJedisUtils.get ("55minute_down");
        jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.put ("onlookers", jsonObject.getInteger ("onlookers")+r.nextInt (4)); //添加关注人数
            StaticJedisUtils.set ("55minute_down",jsonObject,0);
        }


        /*升*/
        s = StaticJedisUtils.get ("10minute_up");
        jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.put ("onlookers", jsonObject.getInteger ("onlookers")+r.nextInt (4)); //添加关注人数
            Integer i=r.nextInt (20);
            jsonObject.getJSONArray ("list").getJSONObject (i).put ("buyerup",jsonObject.getJSONArray ("list").getJSONObject (i).getInteger ("buyerup")+1); //加价人数
            StaticJedisUtils.set ("10minute_down",jsonObject,0);
        }

        s = StaticJedisUtils.get ("25minute_up");
        jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.put ("onlookers", jsonObject.getInteger ("onlookers")+r.nextInt (4)); //添加关注人数
            Integer i=r.nextInt (20);
            jsonObject.getJSONArray ("list").getJSONObject (i).put ("buyerup",jsonObject.getJSONArray ("list").getJSONObject (i).getInteger ("buyerup")+1); //加价人数
            StaticJedisUtils.set ("25minute_down",jsonObject,0);
        }

        s = StaticJedisUtils.get ("55minute_up");
        jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.put ("onlookers", jsonObject.getInteger ("onlookers")+r.nextInt (4)); //添加关注人数
            Integer i=r.nextInt (20);
            jsonObject.getJSONArray ("list").getJSONObject (i).put ("buyerup",jsonObject.getJSONArray ("list").getJSONObject (i).getInteger ("buyerup")+1); //加价人数
            StaticJedisUtils.set ("55minute_down",jsonObject,0);
        }


        log.info ("执行[趣拍卖]定时2分钟生成数据: " + LocalDateTime.now());
    }





    //每三分钟生成购买数据
    @Scheduled(cron = "0 */3 * * * ? ")
    private void configureTasks3()  {
        Random r=new Random ();

        String s = StaticJedisUtils.get ("10minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.getJSONArray ("list").getJSONObject (r.nextInt (20)).put ("buyerdown",1);
        }
        StaticJedisUtils.set ("10minute_down",jsonObject,0);


         s = StaticJedisUtils.get ("25minute_down");
         jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.getJSONArray ("list").getJSONObject (r.nextInt (20)).put ("buyerdown",1);
        }
        StaticJedisUtils.set ("25minute_down",jsonObject,0);

        s = StaticJedisUtils.get ("55minute_down");
        jsonObject = JSONObject.parseObject (s);
        if(jsonObject.getInteger ("auctionstatus")!=0){
            jsonObject.getJSONArray ("list").getJSONObject (r.nextInt (20)).put ("buyerdown",1);
        }
        StaticJedisUtils.set ("55minute_down",jsonObject,0);


        log.info ("执行[趣拍卖]定时3分钟生成数据: " + LocalDateTime.now());
    }




}
