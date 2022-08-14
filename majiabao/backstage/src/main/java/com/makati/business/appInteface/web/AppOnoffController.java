package com.makati.business.appInteface.web;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.makati.business.app.entity.Appversion;
import com.makati.business.app.service.IAppversionService;
import com.makati.business.blacklist.entity.Blacklist;
import com.makati.business.blacklist.service.IBlacklistService;
import com.makati.common.response.Response;
import com.makati.common.util.IpUtil;
import com.makati.common.util.StaticJedisUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/apponoff")
@Api(value = "版本接口",description = "版本号接口")
@Slf4j
public class AppOnoffController {


    @Autowired
    private IAppversionService IAppversionService;

    @Autowired
    private IBlacklistService IBlacklistService;

    @Autowired
    private IpUtil ipUtil;




    @RequestMapping(value = "getnewversion",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="查询版本号列表", notes="根据APP-ID查询最新版本号,按版本号倒叙排列")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "appid", value = "(入参：APP-ID)", required = true, dataType = "String")
    })
//    @Cacheable(value = "emp" ,key = "#appid+#version")
    public Response<Object> getnewversion(@RequestParam(value="appid",required=true) String appid , HttpServletRequest req) {
        List<Object> auditorData = StaticJedisUtils.getList (appid+"_versions");

        if(auditorData.size ()==0){
            List<Appversion> versionlist = IAppversionService.selectList (new EntityWrapper<Appversion> ().where ("appid={0}", appid).orderBy ("version", false));
            for (Appversion appversion1: versionlist) {
                HashMap<String,String> map1=new HashMap<> ();
                map1.put ("appid",appversion1.getAppid ());
                map1.put ("version",appversion1.getVersion ());
                StaticJedisUtils.listObjectAdd (appid+"_versions",map1);
            }



            auditorData = StaticJedisUtils.getList (appid+"_versions");
            HashMap<String,String> map1=new HashMap<> ();
            return   Response.successResponce(auditorData);
        }else{
            return Response.successResponce(auditorData);

        }





    }



    @RequestMapping(value = "getversion",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="查询版本号", notes="根据APP-ID返回对应版本号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "appid", value = "(入参：APP-ID)", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "version", value = "(入参：版本号)", required = true, dataType = "String")
    })
//    @Cacheable(value = "emp" ,key = "#appid+#version")
    public Response<Appversion> getversion(@RequestParam(value="appid",required=true) String appid ,@RequestParam(value="version",required=true) String version , HttpServletRequest req) {
        Object appidversion = StaticJedisUtils.getObject (appid + version);
        String ip=getRemoteHost(req);
        StaticJedisUtils.addhyperLog ("OnlineToday",ip);
        StaticJedisUtils.addhyperLog ("OnlineAmount",ip);
        if(appidversion==null||appidversion.equals ("")){
            EntityWrapper ew=new EntityWrapper();
            Appversion appversion = IAppversionService.selectOne(ew.where("appid={0} and version={1}",appid,version));
            if(appversion!=null)
            StaticJedisUtils.set (appid+version,appversion,0);
            log.info ("getversionIp="+ip);

            return Response.successResponce(istrue(appversion,ip));
        }else{
            log.info ("getversionIp="+ip);
            return Response.successResponce(istrue((Appversion)appidversion,ip));
        }


    }



    private boolean ipistrue(Blacklist blacklist,String ipAddr){
        //请求ip拆分
        String[] split = ipAddr.split ("\\.");

        //审核员ip拆分
        String[] shsplit = blacklist.getIp ().split ("\\.");
        int count=0;
        for (int i = 0; i <4 ; i++) {

            //如果是ip段
            if(shsplit[i].indexOf ("/")>=0) {
                String[] split1 = shsplit[i].split ("/");
                if(Integer.parseInt (split[i])>=Integer.parseInt (split1[0])&&Integer.parseInt (split[i])<=Integer.parseInt (split1[1])){
                    count++;
                }else{
                    break;
                }
                //不是ip段，指定ip地址
            }else {
                if(shsplit[i].equals (split[i])){
                    count++;
                }else{
                    break;
                }
            }
        }
        //请求ip与审核ip相等
        if(count==4){
            return false;
        }

        return true;


    }


    private boolean isauditor(String ipAddr){

        List<Object> auditorData = StaticJedisUtils.getList ("auditorData");
        if(auditorData.size ()==0){
            List<Blacklist> blacklists = IBlacklistService.selectList (new EntityWrapper<> ());
            for (Blacklist bl: blacklists) {
                StaticJedisUtils.listObjectAdd ("auditorData",bl);
            }


            for (Blacklist blacklist: blacklists) {
                boolean ipistrue = ipistrue (blacklist, ipAddr);
                if(!ipistrue){
                    return false;
                }
            }
        }else{
            for (Object list: auditorData) {
                Blacklist blacklist=(Blacklist) list;
                boolean ipistrue = ipistrue (blacklist, ipAddr);
                if(!ipistrue){
                    return false;
                }
            }

        }

        return true;

    }

    /**
     * 20200624 新增判断是否为美国IP，若是，则显示马甲
     * @param ipAddr
     * @return
     */
    private boolean isusa(String ipAddr) {
    	String countryStr = ipUtil.getAreaByIp(ipAddr);
    	if(countryStr.indexOf("美国") > -1) {
    		return true;
    	}
    	return false;
    }


    private Appversion istrue(Appversion appversion,String ip){

        try{
            boolean isauditor = isauditor (ip);
            if(!isauditor){
                appversion.setState (2);
            }
            if(isusa(ip)) {//判断美国ip
            	appversion.setState (2);
            }
        }catch (Exception e){
            e.printStackTrace ();
            log.info ("分析审核员ip异常······");
        }
        return appversion;

    }





    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }


}
