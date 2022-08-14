package com.makati.business.appInteface.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.makati.business.redenvelopes.entity.Redenvelopes;
import com.makati.business.redenvelopes.service.IRedenvelopesService;
import com.makati.common.response.Response;
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
@RequestMapping("/redenvelopes/")
@Api(value = "红包接口",description = "红包接口")
@ApiIgnore
@Slf4j
public class RedenvelopesController {


    @Autowired
    private IRedenvelopesService IRedenvelopesService;

    @RequestMapping(value = "addredel",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="新增红包", notes="新增红包")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "redelmoney", value = "红包金额", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "redelicon", value = "红包图片", required = false, dataType = "String",defaultValue = ""),
            @ApiImplicitParam(paramType="query", name = "redeltype", value = "平台 1:购物红包", required = true, dataType = "String")
    })
    public Response addredenvelope(String userId,String redelmoney,String redelicon, @RequestParam(value="redeltype",required=true,defaultValue = "1") String redeltype) {

        Redenvelopes red=new Redenvelopes();
        red.setRedelicon(redelicon);
        red.setUserid(userId);
        red.setRedeltype(redeltype);
        red.setRedelmoney(redelmoney);
        red.setCreatetime (new Date ());

        boolean insert = IRedenvelopesService.insert(red);

        
//        EntityWrapper ew=new EntityWrapper();
//
//        Appversion appversion = IAppversionService.selectOne(ew.where("appid={0} and version={1}",appid,version));

        return Response.successResponce(insert);

    }



    @RequestMapping(value = "editredel",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="使用红包", notes="使用红包")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "redelId", value = "红包id", required = true, dataType = "Int")
    })
    public Response editredel(Integer redelId) {


        Redenvelopes redenvelopes = IRedenvelopesService.selectById(redelId);
        redenvelopes.setIsuse(1);
        redenvelopes.setEdittime (new Date ());

        boolean insert = IRedenvelopesService.updateById(redenvelopes);
//
        if(insert)
            log.info ("使用紅包id="+redelId);

        return Response.successResponce(insert);

    }




    @RequestMapping(value = "redellist",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="查询红包", notes="查询红包")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "redeltype", value = "平台 1:购物红包", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "type", value = "1：查已使用 0：查未使用,不传查所有", required = false, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name = "pageNo", value = "当前页", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "分页数", required = true, dataType = "Int")
    })
    public Response redellist( String userId,String redeltype,Integer type,Integer pageNo,Integer pageSize) {
        EntityWrapper ew= new EntityWrapper<Redenvelopes> ();
        if(type==null){
            ew.where ("userid={0} and redeltype={1}", userId, redeltype);
        }else{
            ew.where ("userid={0} and redeltype={1} and isuse={2}", userId, redeltype,type);

        }
        ew.orderBy ("createtime",false);
        ew.last("limit "+(pageNo-1)*pageSize+","+pageSize);


        List<Redenvelopes> redenvelopes = IRedenvelopesService.selectList (ew);


        return Response.successResponce(redenvelopes);

    }




}
