package com.makati.business.appInteface.web;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.makati.common.response.Response;
import com.makati.common.util.StaticJedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-10-24
 */
@Controller
@RequestMapping("/crazycj/")
@Api(value = "中奖记录接口",description = "中奖记录接口")
//@ApiIgnore
@Slf4j
public class CarzycjCountController {



    @RequestMapping(value = "addzj",method =RequestMethod.GET )
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "zjname", value = "中奖名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "zjimg", value = "中奖图片", required = true, dataType = "String")
    })
    @ApiOperation(value="添加中奖记录", notes="添加中奖记录")
    public Response addzj(String userId,String zjname,String zjimg) {
        String  jsonmaps = StaticJedisUtils.get (userId + "_zj");

        if(jsonmaps==null||jsonmaps.equals ("")){
            JSONArray ja=new JSONArray ();
            JSONObject jo=new JSONObject ();
            jo.put ("zjname",zjname);
            jo.put ("zjimg",zjimg);
            ja.add (jo);
            String json = JSONArray.toJSONString (ja);
            StaticJedisUtils.set (userId + "_zj", json, 0);
        }else{
            JSONArray objects = JSONArray.parseArray (jsonmaps);
            JSONObject jo=new JSONObject ();
            jo.put ("zjname",zjname);

            jo.put ("zjimg",zjimg);
            objects.add (jo);
            String json = JSONArray.toJSONString (objects);
            StaticJedisUtils.set (userId + "_zj", json, 0);


        }

        return Response.successResponce("成功");

    }


    @RequestMapping(value = "findzj",method =RequestMethod.GET )
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String")
    })
    @ApiOperation(value="查询中奖记录", notes="查询中奖记录")
    public Response findzj(String userId) {
        String  jsonmaps = StaticJedisUtils.get (userId + "_zj");

//
//        if(jsonmaps==null||jsonmaps.equals ("")){
//            ArrayList<Object> list=new ArrayList ();
//            StaticJedisUtils.setObject (userId + "_zj", list, 0);
//        }

        return Response.successResponce(jsonmaps);

    }



}
