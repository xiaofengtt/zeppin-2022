package com.makati.business.logindata.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.makati.business.logindata.entity.Logindata;
import com.makati.business.logindata.service.ILogindataService;
import com.makati.common.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-10-25
 */
@Controller
@RequestMapping("/logindata/index")
public class LogindataController {


    @Autowired
    ILogindataService iLogindataServicee;

    /**
     * 审核数据页面
     * @return
     */
    @RequestMapping("")
    public String login(HttpServletRequest req, HttpServletResponse response,ModelMap map) {



        return "logindata/index";

    }

    /**
     * 审核数据
     * @return
     */
    @RequestMapping("data")
    @ResponseBody
    public HashMap<String,Object>  data(ModelMap map,Integer page,Integer limit,String channel,String platformtype,String createtime,String isauditor) {

        EntityWrapper ew= new EntityWrapper<> ();

        if(channel!=null&&!channel.equals ("")){

            ew.like ("channel",channel);
        }
        if(platformtype!=null&&!platformtype.equals ("")){

            ew.like ("platformtype",platformtype);
        }
        if(createtime!=null&&!createtime.equals ("")){

            ew.like ("createtime",createtime);
        }
        if(isauditor!=null&&!isauditor.equals ("")){

            ew.like ("isauditor",isauditor);
        }


        ew.orderBy ("createtime",false);

        int size= iLogindataServicee.selectList (ew).size ();
        ew.last ("limit "+(page-1)*limit+","+limit);
        List<Logindata> logindata = iLogindataServicee.selectList (ew);
        for (Logindata lo: logindata) {
            if(lo.getIsauditor ()!=null){
                lo.setRemaker (lo.getIsauditor ()==1? "审核账户":"普通账户");
            }
        }


        map.addAttribute("logindata",logindata);
        map.addAttribute("count",logindata.size ());

        HashMap<String,Object> maps=new HashMap<> ();
        maps.put ("code","0");
        maps.put ("msg","");


        maps.put ("count",size);
        maps.put ("data",logindata);


        return maps;

    }
	
}
