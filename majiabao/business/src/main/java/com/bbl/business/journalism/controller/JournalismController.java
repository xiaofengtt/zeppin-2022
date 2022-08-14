package com.bbl.business.journalism.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbl.business.journalism.entity.Journalism;
import com.bbl.business.journalism.service.IJournalismService;
import com.bbl.business.teacher.service.ITeacherService;
import com.bbl.interceptor.AuthInterceptor;
import com.bbl.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/journalism/journalism")
@Api(value = "新闻、科普",description = "新闻、科普接口")
public class JournalismController {

    @Autowired
    IJournalismService IJournalismService;


    /**
     * 新闻、科普列表
     */
    @PostMapping("journalismlist")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="page" ,value = "", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="pagesize" ,value = "", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="type" ,value = "1新闻 2法律法规 3行政法规 4管理制度 5考试制度", required = true, dataType = "Int")
    })
    @ApiOperation(value="新闻、科普列表", notes="新闻、科普列表")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object tracherlist(Integer page,Integer pagesize,Integer type) {
        QueryWrapper qw=new QueryWrapper ();
        qw.apply ("type={0}",type);
        qw.last ("limit "+(page-1)*pagesize+","+pagesize);
        List<Journalism> list = IJournalismService.list (qw);
       Random random = new Random ();
        for (Journalism journalism: list) {
            journalism.setLook (random.nextInt(100));
        }

        return Response.successResponce (list);
    }


}
