package com.bbl.business.teacher.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbl.business.coinlog.entity.Coinlog;
import com.bbl.business.coinlog.service.ICoinlogService;
import com.bbl.business.teacher.entity.Teacher;
import com.bbl.business.teacher.service.ITeacherService;
import com.bbl.business.user.entity.User;
import com.bbl.business.user.service.IUserService;
import com.bbl.interceptor.AuthInterceptor;
import com.bbl.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2019-11-18
 */
@RestController
@RequestMapping("/teacher/")
@Api(value = "导师",description = "导师接口")
@Slf4j
public class TeacherController {
    @Autowired
    private ITeacherService ITeacherService;

    @Autowired
    private ICoinlogService ICoinlogService;

    @Autowired
    private IUserService iUserService;


    Map<String,String> kaymap=new HashMap<> ();
    Map<String,Thread> kaymap1=new HashMap<> ();


    /**
     * 导师列表
     */
    @PostMapping("tracherlist")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="type" ,value = "1:排行 2:评分 3:星级 4:随机", required = true, dataType = "Int")
    })
    @ApiOperation(value="导师列表", notes="导师列表")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object tracherlist(Integer type) {
        QueryWrapper qw=new QueryWrapper ();
        if(type!=1&&type!=2&&type!=3){
            int random=(int)(Math.random()*10+1);
            if(random<=3){
                qw.orderByAsc ("sort");
            }else if(random>=4&&random<=6){
                qw.orderByAsc ("id");
            }else if(random>=7&&random<=9){
                qw.orderByAsc ("bak2");
            }else{
                qw.orderByAsc ("bak1");
            }
        }

        if(type==1){
            qw.orderByDesc ("bak3");
        }
        if(type==2){
            qw.orderByDesc ("bak1");
        }
        if(type==3){
            qw.orderByDesc ("bak2");
        }
        List list = ITeacherService.list (qw);
        return Response.successResponce (list);
    }


    @PostMapping("tracherawait")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="teacherId" ,value = "导师id", required = true, dataType = "String")
    })
    @ApiOperation(value="开始计费", notes="开始计费")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object tracherawait(String userId,String teacherId)  {
        kaymap.put (userId+teacherId,userId+teacherId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (kaymap.get (userId+teacherId)){
                        System.out.println (userId+teacherId+"计费开始");
                        Coinlog cl=new Coinlog ();
                        LocalDateTime locstart= LocalDateTime.now ();

                        Instant now = Instant.now();
                        kaymap.get (userId+teacherId).wait(1000*60*120);
                        long used = ChronoUnit.MILLIS.between(now, Instant.now());

                        Integer as=Long.hashCode (used/1000/60);
                        as++;
                        Teacher teacher = ITeacherService.getById (teacherId);
                        teacher.setBak3 (teacher.getBak3 ()+1);
                        ITeacherService.updateById (teacher);
                        User user = iUserService.getById (userId);
                        Double money=Double.parseDouble (teacher.getMoney ()) * as;
                        cl.setCoin (money.toString ());
                        money= Double.parseDouble (user.getGoldcoin ())-money;
                        user.setGoldcoin (money.toString ());
                        iUserService.updateById (user);

                        LocalDateTime locend= LocalDateTime.now ();
                        cl.setStarttime (com.bbl.util.DateUtils.formatDate (new Date (),"yyyy-MM-dd HH:mm:ss"));
                        cl.setEndtime (com.bbl.util.DateUtils.formatDate (new Date (),"yyyy-MM-dd HH:mm:ss"));
                        cl.setUserid (user.getId ());
                        cl.setTeacherid (teacher.getId ());
                        cl.setTeachername (teacher.getName ());
                        cl.setMin (as);
                        cl.setType (1);
                        ICoinlogService.save (cl);

                        System.out.println (userId+teacherId+"计费结束，秒："+used);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
            }
        },"t1").start();


        return Response.successResponce ("用户"+userId+"开始计费");
    }


    @PostMapping("trachersignal")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="teacherId" ,value = "导师id", required = true, dataType = "String")
    })
    @ApiOperation(value="结束计费", notes="结束计费")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object trachersignal(String userId,String teacherId)  {

            synchronized (kaymap.get (userId+teacherId)){
                kaymap.get (userId+teacherId).notify();
            }

        return Response.successResponce ("用户"+userId+"结束计费");
    }



    @PostMapping("test1")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "String")
    })
    @ApiOperation(value="test1", notes="test1")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object test1(String userId)  {

        Thread t =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println ("用户"+userId+"开始休眠");
                    LockSupport.park();
                    System.out.println ("用户"+userId+"结束休眠");
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        });
         t.start();

        kaymap1.put (userId,t);
        return Response.successResponce ();
    }


    @PostMapping("test2")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "String")
    })
    @ApiOperation(value="test2", notes="test2")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object test2(String userId)  {


        Thread thread = kaymap1.get (userId);
        LockSupport.unpark(thread);


        return Response.successResponce ();
    }




}
