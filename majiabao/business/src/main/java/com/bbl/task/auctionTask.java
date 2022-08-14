package com.bbl.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.util.DateUtils;
import com.bbl.util.HttpUtil2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/*生成拍卖数据*/
@EnableScheduling
@Component
@Slf4j
public class auctionTask {

    private JSONObject auction(JSONObject json,Integer minute,String type,Date date) throws ParseException {
        String nowtime= DateUtils.formatDateTime (date);
        json.put ("starttime",nowtime);
        json.put ("endtime",DateUtils.formatDateTime (DateUtils.pastDays1 (date,minute)));
        json.put ("type",type);
        json.put ("auctionstatus","1"); //拍卖状态 1拍卖中 0拍卖结束
        json.put ("onlookers",0); //围观

        return json;
    }

    @Scheduled(cron = "0 0 * * * ?")
//    或直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate=30000)
    private void configureTasks() throws InterruptedException, ParseException {
        Date date= new Date ();
        JSONObject sheng10 =get10minute();
        JSONObject sheng25 =get25minute();
        JSONObject sheng55 =get55minute();
        sheng10=auction (sheng10,10,"升拍卖",date);
        sheng25=auction (sheng25,25,"升拍卖",date);
        sheng55=auction (sheng55,55,"升拍卖",date);
        StaticJedisUtils.set ("10minute_up",sheng10,0);
        StaticJedisUtils.set ("25minute_up",sheng25,0);
        StaticJedisUtils.set ("55minute_up",sheng55,0);


        sheng10 =get10minute();
        sheng25 =get25minute();
        sheng55 =get55minute();
        sheng10=auction (sheng10,10,"降拍卖",date);
        sheng25=auction (sheng25,25,"降拍卖",date);
        sheng55=auction (sheng55,55,"降拍卖",date);
        StaticJedisUtils.set ("10minute_down",sheng10,0);
        StaticJedisUtils.set ("25minute_down",sheng25,0);
        StaticJedisUtils.set ("55minute_down",sheng55,0);

        log.info ("执行[趣拍卖]0点任务: " + LocalDateTime.now());
    }




    @Scheduled(cron = "0 15 * * * ?")
    private void configureTasks15() throws InterruptedException, ParseException {
        Date date= new Date ();
        JSONObject sheng10 =get10minute();
        sheng10=auction (sheng10,10,"升拍卖",date);
        StaticJedisUtils.set ("10minute_up",sheng10,0);


        sheng10 =get10minute();
        sheng10=auction (sheng10,10,"降拍卖",date);
        StaticJedisUtils.set ("10minute_down",sheng10,0);
        log.info ("执行[趣拍卖]15点任务: " + LocalDateTime.now());
    }

    @Scheduled(cron = "0 30 * * * ?")
    private void configureTasks30() throws InterruptedException, ParseException {
        Date date= new Date ();
        JSONObject sheng10 =get10minute();
        JSONObject sheng25 =get25minute();
        sheng10=auction (sheng10,10,"升拍卖",date);
        sheng25=auction (sheng25,25,"升拍卖",date);
        StaticJedisUtils.set ("10minute_up",sheng10,0);
        StaticJedisUtils.set ("25minute_up",sheng25,0);

        sheng10 =get10minute();
        sheng25 =get25minute();
        sheng10=auction (sheng10,10,"降拍卖",date);
        sheng25=auction (sheng25,25,"降拍卖",date);
        StaticJedisUtils.set ("10minute_down",sheng10,0);
        StaticJedisUtils.set ("25minute_down",sheng25,0);
        log.info ("执行[趣拍卖]30点任务: " + LocalDateTime.now());
    }

    @Scheduled(cron = "0 45 * * * ?")
    private void configureTasks45() throws InterruptedException, ParseException {
        Date date= new Date ();
        JSONObject sheng10 =get10minute();
        sheng10=auction (sheng10,10,"升拍卖",date);
        StaticJedisUtils.set ("10minute_up",sheng10,0);

        sheng10 =get10minute();
        sheng10=auction (sheng10,10,"降拍卖",date);
        StaticJedisUtils.set ("10minute_down",sheng10,0);
        log.info ("执行[趣拍卖]45点任务: " + LocalDateTime.now());
    }


    private JSONObject get10minute() throws InterruptedException {
        String s = HttpUtil2.doGet ("http://47.106.209.243:18081/wx/goods/list?page=1&limit=20&maxprice=80&minprice=30&israndom=1");
        int ss=JSONObject.parseObject (s).getJSONObject ("data").getJSONArray ("list").size ();
        int i=1;
        while (ss==0){
//            Thread.sleep (500);
            s = HttpUtil2.doGet ("http://47.106.209.243:18081/wx/goods/list?page=1&limit=20&maxprice=80&minprice=30&israndom=1");
            ss=JSONObject.parseObject (s).getJSONObject ("data").getJSONArray ("list").size ();
            i++;
            if(i==8){
                break;
            }
        }


        JSONObject parse = JSONObject.parseObject (s);
        JSONObject data = parse.getJSONObject ("data");
        JSONArray list = data.getJSONArray ("list");
        JSONArray ja=new JSONArray ();
        for (Object job: list) {
            JSONObject jsonObject=(JSONObject)job;
            jsonObject.put ("buyerup",0); //加价人数   升场
            jsonObject.put ("buyerdown",0); //购买人数  降场
            ja.add (jsonObject);
        }
        data.put ("list",ja);

        data.remove ("total");
        data.remove ("pages");
        data.remove ("page");

        return data;
    }
    private JSONObject get25minute() throws InterruptedException {
        String s =   HttpUtil2.doGet ("http://47.106.209.243:18081/wx/goods/list?page=1&limit=20&maxprice=120&minprice=60&israndom=1");
        int ss=JSONObject.parseObject (s).getJSONObject ("data").getJSONArray ("list").size ();

        int i=1;
        while (ss==0){
//            Thread.sleep (500);
            s = HttpUtil2.doGet ("http://47.106.209.243:18081/wx/goods/list?page=1&limit=20&maxprice=120&minprice=60&israndom=1");
            ss=JSONObject.parseObject (s).getJSONObject ("data").getJSONArray ("list").size ();

            i++;
            if(i==8){
                break;
            }
        }

        JSONObject parse = JSONObject.parseObject (s);
        JSONObject data = parse.getJSONObject ("data");

        JSONArray list = data.getJSONArray ("list");
        JSONArray ja=new JSONArray ();
        for (Object job: list) {
            JSONObject jsonObject=(JSONObject)job;
            jsonObject.put ("buyerup",0); //加价人数   升场
            jsonObject.put ("buyerdown",0); //购买人数  降场
            ja.add (jsonObject);
        }
        data.put ("list",ja);

        data.remove ("total");
        data.remove ("pages");
        data.remove ("page");

        return data;


    }
    private JSONObject get55minute() throws InterruptedException {
        String s = HttpUtil2.doGet ("http://47.106.209.243:18081/wx/goods/list?page=1&limit=20&maxprice=500&minprice=100&israndom=1");
        int ss=JSONObject.parseObject (s).getJSONObject ("data").getJSONArray ("list").size ();

        int i=1;
        while (ss==0){
//            Thread.sleep (50);
            s = HttpUtil2.doGet ("http://47.106.209.243:18081/wx/goods/list?page=1&limit=20&maxprice=500&minprice=100&israndom=1");
            ss=JSONObject.parseObject (s).getJSONObject ("data").getJSONArray ("list").size ();

            i++;
            if(i==8){
                break;
            }
        }

        JSONObject parse = JSONObject.parseObject (s);
        JSONObject data = parse.getJSONObject ("data");

        JSONArray list = data.getJSONArray ("list");
        JSONArray ja=new JSONArray ();
        for (Object job: list) {
            JSONObject jsonObject=(JSONObject)job;
            jsonObject.put ("buyerup",0); //加价人数   升场
            jsonObject.put ("buyerdown",0); //购买人数  降场
            ja.add (jsonObject);
        }
        data.put ("list",ja);

        data.remove ("total");
        data.remove ("pages");
        data.remove ("page");

        return data;

    }
}
