package com.bbl.auction.web;


import com.alibaba.fastjson.JSONObject;
import com.bbl.business.book.service.IBookService;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.interceptor.AuthInterceptor;
import com.bbl.util.DateUtils;
import com.bbl.util.HttpUtil2;
import com.bbl.util.Response;
import com.bbl.util.RestTemplateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/auction")
@Api(value = "趣拍卖",description = "趣拍卖接口")
@Slf4j
public class AuctionController {

    @Autowired
    IBookService IBookService;


    /**
     * 拍卖场
     */
    @PostMapping("auctionlist")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="page" ,value = "当前页", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="pagesize" ,value = "分页数", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="type" ,value = "10:（10分钟场），25:（25分钟场），55：（55分钟场）", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="fenlei" ,value = "up:升 down:降", required = true, dataType = "String")
    })
    @ApiOperation(value="拍卖场", notes="拍卖场")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object auctionlist(Integer page,Integer pagesize,Integer type,String fenlei) {
        String s = StaticJedisUtils.get (type + "minute_"+fenlei);
        JSONObject objects = JSONObject.parseObject (s);
        objects.put ("list",objects.getJSONArray ("list").subList ((page-1)*pagesize,pagesize*page));


        return Response.successResponce (objects);
    }

    /**
     *
     */
    @PostMapping("qpmgood")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="page" ,value = "当前页", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="pagesize" ,value = "分页数", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="keyword" ,value = "关键字", required = true, dataType = "String")
    })
    @ApiOperation(value="拍卖商品", notes="拍卖商品")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object qpmgood(Integer page,Integer pagesize,String keyword) throws ParseException {
        JSONObject jobj=new JSONObject ();
        jobj.put ("page",page);
        jobj.put ("limit",pagesize);
        jobj.put ("maxprice",5000);
        jobj.put ("minprice",0);
        jobj.put ("israndom",0);
        jobj.put ("keyword",keyword);
        String http = RestTemplateUtils.getHttp ("http://47.106.209.243:18081/wx/goods/list", jobj, 3000, 3000, 1);


        JSONObject data = JSONObject.parseObject (http).getJSONObject ("data");

        Date date= new Date ();
        String nowtime= DateUtils.formatDateTime (DateUtils.pastDays1 (date,-2));
        data.put ("starttime",nowtime);
        data.put ("endtime",DateUtils.formatDateTime (DateUtils.pastDays1 (date,15)));
        data.put ("auctionstatus","1"); //拍卖状态 1拍卖中 0拍卖结束
        data.put ("onlookers",0); //围观

        return Response.successResponce (data);
    }




}
