package com.bbl.business.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbl.async.AsyncImg;
import com.bbl.business.good.entity.LitemallGoods;
import com.bbl.business.good.mapper.LitemallGoodsMapper;
import com.bbl.cache.redis.JedisUtils;
import com.bbl.cache.redis.RedisMsgSubListener;
import com.bbl.util.RandomValidateCodeUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-09-06
 */
@Controller
@CrossOrigin
@RequestMapping("")
@Slf4j
public class RedisController {


    @GetMapping("LPUSH")
    @ResponseBody
    public void LPUSH (String  key ,String value)  {
        JedisUtils.LPUSH (key,value);

    }

    @GetMapping("BRPOP")
    @ResponseBody
    public List<String>  BRPOP  (String key)  {
        return  JedisUtils.BRPOP (key);

    }





    @GetMapping("redisStreamXadd")
    @ResponseBody
    public void redisStreamXadd (String  key,String mapkey,String mapvalue)  {

        Map<String, String> map = new HashMap<>();
        map.put (mapkey,mapvalue);
        JedisUtils.setStream (key,map);

    }

    @GetMapping("redisStreamXread")
    @ResponseBody
    public void redisStreamXread (String key)  {
        Map<String, StreamEntryID> map = new HashMap<>();
        //key为需要订阅的key, StreamEntryID().LAST_ENTRY = $ , 代表最后一条消息
        map .putIfAbsent(key, new StreamEntryID().LAST_ENTRY);
        Map.Entry[] rooms =  map.entrySet().toArray(new Map.Entry[0]);
        //取出1条，阻塞1000ms
        List<Map.Entry<String, List<StreamEntry>>> list = JedisUtils.getStream(1, 1000, rooms);
    }




    @GetMapping("redispublish")
    @ResponseBody
    public void redispublish () throws Exception {

        JedisUtils.publishMsg ("test","hello world!");

    }



    @GetMapping("redissubscribe")
    @ResponseBody
    public void redissubscribe () throws Exception {

        RedisMsgSubListener pubsub = new RedisMsgSubListener();

        JedisUtils.subscribeMsg(pubsub,"test");

    }





}
