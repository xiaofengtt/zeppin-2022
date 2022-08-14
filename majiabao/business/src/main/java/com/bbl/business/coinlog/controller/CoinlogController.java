package com.bbl.business.coinlog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbl.business.coinlog.entity.Coinlog;
import com.bbl.business.coinlog.service.ICoinlogService;
import com.bbl.business.teacher.service.ITeacherService;
import com.bbl.business.user.entity.User;
import com.bbl.business.user.service.IUserService;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.interceptor.AuthInterceptor;
import com.bbl.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2019-11-27
 */
@RestController
@RequestMapping("/coinlog/")
@Api(value = "金币",description = "金币接口")
public class CoinlogController {

    @Autowired
    IUserService iUserService;

    @Autowired
    ICoinlogService ICoinlogService;


    @Autowired
    ITeacherService ITeacherService;


    /**
     * 用户金币
     */
    @GetMapping("goldcoin")
    @ApiOperation(value="用户金币", notes="用户金币")
    @AuthInterceptor(needAuthTokenVerify = false)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="cztype" ,value = "1:查询 2:新增", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="coin" ,value = "金币", required = false, dataType = "Int"),
    })
    public Object goldcoin(String userId,Integer cztype,String coin) {
        User byId = iUserService.getById (userId);
        if(cztype==1){
            return Response.successResponce (byId.getGoldcoin ());
        }
        if(cztype==2){
            Double goldcoin= Double.parseDouble (coin)+Double.parseDouble (byId.getGoldcoin ());
            byId.setGoldcoin (goldcoin.toString ());
            Coinlog cl=new Coinlog ();
            cl.setType (0);
            cl.setCoin (coin);
            cl.setStarttime (com.bbl.util.DateUtils.formatDate (new Date (),"yyyy-MM-dd HH:mm:ss"));
            cl.setUserid (Integer.parseInt (userId));
            ICoinlogService.save (cl);
            iUserService.updateById (byId);
            return Response.successResponce ("成功");

        }
        String qhlist = StaticJedisUtils.get ("qhlist");

        return Response.successResponce (qhlist);
    }


    /**
     * 金币记录
     */
    @GetMapping("coin")
    @ApiOperation(value="金币记录", notes="金币记录(0充值金币1消费金币)")
    @AuthInterceptor(needAuthTokenVerify = false)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="page" ,value = "分页", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="pagesize" ,value = "分页数", required = true, dataType = "Int")
    })
    public Object coin(String userId,Integer page,Integer pagesize) {
        QueryWrapper qw=new QueryWrapper ();
        qw.apply ("userid={0}",userId);
        qw.last ("limit "+(page-1)*pagesize+","+pagesize);
        qw.orderByDesc ("starttime");

         List<Coinlog>  coinlist= ICoinlogService.list (qw);
        for (Coinlog coinlog: coinlist) {
            if(coinlog.getTeacherid ()!=0){
                coinlog.setTeacher (ITeacherService.getById (coinlog.getTeacherid ()));
            }

        }
        return Response.successResponce (coinlist);
    }



}
