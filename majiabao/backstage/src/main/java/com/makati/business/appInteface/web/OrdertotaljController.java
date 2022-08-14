package com.makati.business.appInteface.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.makati.business.redenvelopes.entity.Redenvelopes;
import com.makati.business.redenvelopes.service.IRedenvelopesService;
import com.makati.common.response.Response;
import com.makati.common.util.StaticJedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-10-24
 */
@Controller
@RequestMapping("/ordertotal/")
@Api(value = "下单统计接口",description = "下单统计接口")
//@ApiIgnore
@Slf4j
public class OrdertotaljController {



    @RequestMapping(value = "findorders",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="查询下单次数", notes="查询下单次数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String")
    })
    public Response findorders(String userId) {
        String s = StaticJedisUtils.get (userId+"_applyordernum");
        if(s==null||s.equals ("")){
            return Response.successResponce(0);
        }

        return Response.successResponce(Integer.parseInt (s));

    }



    @RequestMapping(value = "addorder",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="下单", notes="下单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String")
    })
    public Response addorder(String userId) {

        String s = StaticJedisUtils.get (userId+"_applyordernum");
        if(s==null||s.equals ("")){
            StaticJedisUtils.set (userId+"_applyordernum",1,60*60*24*7);
            return Response.successResponce(0);
        }else{
            StaticJedisUtils.set (userId+"_applyordernum",Integer.parseInt (s)+1,60*60*24*7);
            return Response.successResponce(Integer.parseInt (s)+1);

        }


    }




    @RequestMapping(value = "applyorder",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="减少下单次数", notes="减少下单次数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "applyNum", value = "使用次数", required = true, dataType = "Int")
    })
    public Response applyorder(String userId,Integer applyNum) {
        String s = StaticJedisUtils.get (userId+"_applyordernum");
        if(s==null||s.equals ("")){
            return Response.failResponce ("201","用户未下单");
        }else if(applyNum>Integer.parseInt (s)){
            return Response.failResponce("201","当前拥有资格:"+Integer.parseInt (s));
        }else{
            StaticJedisUtils.set (userId+"_applyordernum",Integer.parseInt (s)-applyNum,60*60*24*7);
            return Response.successResponce(Integer.parseInt (s)+1);
        }

    }




}
