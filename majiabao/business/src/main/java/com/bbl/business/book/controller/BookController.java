package com.bbl.business.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbl.business.book.service.IBookService;
import com.bbl.business.journalism.service.IJournalismService;
import com.bbl.interceptor.AuthInterceptor;
import com.bbl.thread.Runner;
import com.bbl.thread.Threadpool;
import com.bbl.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/book/book")
@Api(value = "书籍",description = "书籍接口")
@Slf4j
public class BookController {

    @Autowired
    IBookService IBookService;


    /**
     * 书籍
     */
    @PostMapping("booklist")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="page" ,value = "", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="pagesize" ,value = "", required = true, dataType = "Int")
    })
    @ApiOperation(value="书籍列表", notes="书籍列表")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object booklist(Integer page,Integer pagesize) {
        QueryWrapper qw=new QueryWrapper ();
        qw.last ("limit "+(page-1)*pagesize+","+pagesize);
        List list = IBookService.list(qw);


        return Response.successResponce (list);
    }



}
