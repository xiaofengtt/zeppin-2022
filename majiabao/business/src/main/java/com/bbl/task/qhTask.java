package com.bbl.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.util.HttpUtil2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@EnableScheduling
@Component
@Slf4j
public class qhTask {

    @Scheduled(cron = "0/30 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        String s = HttpUtil2.doGet ("https://hq.sinajs.cn/?_=1574056259437/&list=nf_RB2001,nf_FU2001,nf_P2001,nf_MA2001,nf_I2001,nf_NI2002,nf_M2001,nf_AG2002,nf_C2001,nf_TA2001");

        List<String> qhlist= Arrays.asList (s.split (";"));
        JSONArray jarrsy=new JSONArray ();

        for (int i = 0; i <qhlist.size () ; i++) {
            List<String> strings = Arrays.asList (qhlist.get (i).split (","));
            if(strings.size ()<=1)
                break;
            JSONObject jobj=new JSONObject ();
            String name="";
            if(i==0){
                name="螺纹钢2001";
            }
            if(i==1){
                name="燃料油2001";
            }
            if(i==2){
                name="棕榈油2001";
            }
            if(i==3){
                name="甲醇2001";
            }
            if(i==4){
                name="铁矿石2001";
            }
            if(i==5){
                name="镍2001";
            }
            if(i==6){
                name="豆粕2001";
            }
            if(i==7){
                name="白银2001";
            }
            if(i==8){
                name="玉米2001";
            }
            if(i==9){
                name="PTA2001";
            }


            jobj.put ("name",name);

            jobj.put ("value",strings.get (8));
            jarrsy.add (jobj);
        }
//        for (String qh: qhlist) {
//            List<String> strings = Arrays.asList (qh.split (","));
//            if(strings.size ()<=1)
//                break;
//            JSONObject jobj=new JSONObject ();
//            jobj.put ("name",strings.get (0));
//            jobj.put ("value",strings.get (10));
//            jarrsy.add (jobj);
//
//        }
        StaticJedisUtils.set ("qhlist",jarrsy,60*60*24*7);
        log.info ("执行[闪电期指]抓取任务: " + LocalDateTime.now());
    }

//    @Scheduled(cron = "0/5 * * * * ?")
//    //或直接指定时间间隔，例如：5秒
//    //@Scheduled(fixedRate=5000)
//    private void configureTasks1() {
//        String s = HttpUtil2.doGet ("http://finance.sina.com.cn/roll/index.d.html?cid=56988");
//
////        StaticJedisUtils.set ("qhlist",jarrsy,60*60*24*7);
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//    }



}
