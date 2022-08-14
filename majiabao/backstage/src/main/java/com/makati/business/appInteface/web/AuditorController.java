package com.makati.business.appInteface.web;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.makati.business.blacklist.entity.Blacklist;
import com.makati.business.blacklist.service.IBlacklistService;
import com.makati.business.logindata.entity.Logindata;
import com.makati.business.logindata.service.ILogindataService;
import com.makati.common.response.Response;
import com.makati.common.util.DateUtil;
import com.makati.common.util.DateUtils;
import com.makati.common.util.StaticJedisUtils;
import com.makati.oldJedis.JedisUtils2;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-10-24
 */
@Controller
@RequestMapping("/auditor")
@Api(value = "审核人",description = "审核人接口")
//@ApiIgnore
@Slf4j
public class AuditorController {


    @Autowired
    IBlacklistService IBlacklistService;

    @RequestMapping(value = "index",method =RequestMethod.GET )
    public String index(String channel,String platformtype,String text,HttpServletRequest request) {
        return "auditor/index";

    }


    @RequestMapping(value = "addauditor",method =RequestMethod.GET )
    public String addauditor(String channel,String platformtype,String text,HttpServletRequest request) {
        return "auditor/addauditor";

    }



    /**
     * 添加
     * @return
     */
    @GetMapping("add")
    @ResponseBody
    public int  add( String remaker,String ip) {
        try {
            Blacklist bl=new Blacklist ();
            bl.setIp (ip);
            bl.setRemaker (remaker);
            bl.setCreatetime (DateUtils.formatDateTime (new Date ()));
            IBlacklistService.insert (bl);

            StaticJedisUtils.listObjectAdd ("auditorData",bl);
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    /**
     * 删除
     * @return
     */
    @PostMapping("del")
    @ResponseBody
    public int del(String id) {
        try {
            boolean b = IBlacklistService.deleteById (Integer.parseInt (id));
            if(b){
                 List<Blacklist> blacklists = IBlacklistService.selectList (new EntityWrapper<> ());

                StaticJedisUtils.del ("auditorData");

                for (Blacklist blacklist: blacklists) {

                    StaticJedisUtils.listObjectAdd ("auditorData",blacklist);
                }
                return 1;
            }else{
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace ();
            return 0;
        }

    }



    /**
     * 数据
     * @return
     */
    @RequestMapping("data")
    @ResponseBody
    public HashMap<String,Object>  data(Integer page, Integer limit,
                                        @RequestParam(value="ip",defaultValue = "",required=true)  String ip,
                                        @RequestParam(value="remaker",defaultValue = "",required=true) String remaker,
                                        @RequestParam(value="createtime",defaultValue = "",required=true) String createtime) {

        HashMap<String,Object> maps=new HashMap<> ();
        List<Object> auditorData=null;
//        List<Object> auditorData = StaticJedisUtils.getList ("auditorData");
        if(auditorData==null){
            EntityWrapper ew=  new EntityWrapper<> ();
            if(!ip.equals (""))
                ew.like ("ip",ip);
            if(!remaker.equals (""))
                ew.like ("remaker",remaker);
            if(!createtime.equals (""))
                ew.like ("createtime",createtime);


            int size= IBlacklistService.selectList (ew).size ();
            ew.last ("limit "+(page-1)*limit+","+limit);
            List<Blacklist> blacklists = IBlacklistService.selectList (ew);

            maps.put ("code","0");
            maps.put ("msg","");
            maps.put ("count",size);
            maps.put ("data",blacklists);

        }else{
            List<HashMap<String,Object>> auditor=new ArrayList<> ();

            for (Object list: auditorData) {
                HashMap<String,Object>  map11= (HashMap<String,Object>)list;
                if(ip!=null&&!ip.equals ("")){
                    if(map11.get ("ip").toString ().indexOf (ip)>=0){
                        auditor.add (map11);
                        continue;
                    }
                }
                if(remaker!=null&&!remaker.equals ("")){
                    if(map11.get ("remaker").toString ().indexOf (remaker)>=0){
                        auditor.add (map11);
                        continue;
                    }
                }
                if(createtime!=null&&!createtime.equals ("")){
                    if(map11.get ("createtime").toString ().indexOf (createtime)>=0){
                        auditor.add (map11);
                        continue;
                    }
                }

            }
            maps.put ("code","0");
            maps.put ("msg","");
            if(ip.equals ("")&&remaker.equals ("")&&createtime.equals ("")){
                maps.put ("count",auditorData.size ());
                maps.put ("data",auditorData);
            }else{
                maps.put ("count",auditor.size ());
                maps.put ("data",auditor);
            }
        }

        return maps;

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
