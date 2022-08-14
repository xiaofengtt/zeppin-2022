package com.bbl.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/*定时降价*/
@EnableScheduling
@Component
@Slf4j
public class auctionTask1 {


    //每一分钟
    @Scheduled(cron = "0 */1 * * * ? ")
    private void configureTasks11()  {
        String s = StaticJedisUtils.get ("10minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        Integer auctionstatus=jsonObject.getInteger ("auctionstatus");
        if(auctionstatus==0){
            return;
        }
        JSONArray list = jsonObject.getJSONArray ("list");
        JSONArray ja=new JSONArray ();
        for (Object json: list) {
            JSONObject js= (JSONObject)json;
            Double retailPrice = js.getDouble ("retailPrice");
            Double retailPricedown= retailPrice/2/10;
            Integer money=retailPrice.intValue ()-retailPricedown.intValue ();
            if( js.get ("retailPrice_down")==null){
                js.put ("retailPrice_down",money);
                ja.add (js);
            }else{
                js.put ("retailPrice_down",js.getInteger ("retailPrice_down")-retailPricedown.intValue ());
                ja.add (js);
            }

        }
        jsonObject.put ("list",ja);
        StaticJedisUtils.set ("10minute_down",jsonObject,0);

        log.info ("执行[趣拍卖]定时1分钟降价: " + LocalDateTime.now());
    }





    @Scheduled(cron = "0 */3 * * * ? ")
    private void configureTasks3()  {
        String s = StaticJedisUtils.get ("25minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        Date end=DateUtils.parseDate (jsonObject.getString ("endtime"));
        if(DateUtils.isExpire (end)){
            return;
        }
        JSONArray list = jsonObject.getJSONArray ("list");
        JSONArray ja=new JSONArray ();
        for (Object json: list) {
            JSONObject js= (JSONObject)json;
            Double retailPrice = js.getDouble ("retailPrice");
            Double retailPricedown= retailPrice/2/10;
            Integer money=retailPrice.intValue ()-retailPricedown.intValue ();
            if( js.get ("retailPrice_down")==null){
                js.put ("retailPrice_down",money);
                ja.add (js);
            }else{
                js.put ("retailPrice_down",js.getInteger ("retailPrice_down")-retailPricedown.intValue ());
                ja.add (js);
            }

        }
        jsonObject.put ("list",ja);
        StaticJedisUtils.set ("25minute_down",jsonObject,0);

        log.info ("执行[趣拍卖]定时3分钟降价: " + LocalDateTime.now());
    }



    //每一分钟
    @Scheduled(cron = "0 */5 * * * ? ")
    private void configureTasks55()  {

        String s = StaticJedisUtils.get ("55minute_down");
        JSONObject jsonObject = JSONObject.parseObject (s);
        Date end=DateUtils.parseDate (jsonObject.getString ("endtime"));
        if(DateUtils.isExpire (end)){
            return;
        }
        JSONArray list = jsonObject.getJSONArray ("list");
        JSONArray ja=new JSONArray ();
        for (Object json: list) {
            JSONObject js= (JSONObject)json;
            Double retailPrice = js.getDouble ("retailPrice");
            Double retailPricedown= retailPrice/2/10;
            Integer money=retailPrice.intValue ()-retailPricedown.intValue ();
            if( js.get ("retailPrice_down")==null){
                js.put ("retailPrice_down",money);
                ja.add (js);
            }else{
                js.put ("retailPrice_down",js.getInteger ("retailPrice_down")-retailPricedown.intValue ());
                ja.add (js);
            }

        }
        jsonObject.put ("list",ja);
        StaticJedisUtils.set ("55minute_down",jsonObject,0);

        log.info ("执行[趣拍卖]定时5分钟降价: " + LocalDateTime.now());
    }

}
