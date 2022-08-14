package com.makati.business.app.web;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.makati.business.app.entity.Apps;
import com.makati.business.app.entity.Appversion;
import com.makati.business.app.service.IAppsService;
import com.makati.business.app.service.IAppversionService;
import com.makati.common.util.StaticJedisUtils;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-09-13
 */
@Controller
@RequestMapping("/app/apps")
public class AppsController {

    @Autowired
    private IAppsService IAppsService;
    
    @Autowired
    private IAppversionService IAppversionService;


    @RequestMapping("/save")
    @ResponseBody
    public int save(Apps Apps, HttpServletRequest req, HttpServletResponse response) {

        boolean isave=true;
        try{

            isave=  IAppsService.insertOrUpdate(Apps);

        }catch (Exception e){
            isave=false;
        }

        return isave? 1:0;

    }



    @RequestMapping("/addmjb")
    public String addmjb(HttpServletRequest req, HttpServletResponse response) {

        return "apps/addmjb";

    }
	


    @RequestMapping("delmjb")
    @ResponseBody
    public int delmjb(String id,HttpServletRequest req, ModelMap map) {

        try{
        	Apps apps = IAppsService.selectById(Integer.parseInt(id));

        	//查询所有马甲包信息，并删除
        	EntityWrapper  ew=new EntityWrapper();

            ew.where("parentid={0}", id);
            List<Appversion> list = IAppversionService.selectList(ew);
            boolean flag = true;
            if(list != null && list.size() > 0) {
            	for(Appversion appversion : list) {
            		boolean isdel = IAppversionService.deleteById(appversion.getId());
            		if(isdel){
                        StaticJedisUtils.del (appversion.getAppid ()+appversion.getVersion ());


                        //最新版本号
                        List<Appversion> versionlist = IAppversionService.selectList (new EntityWrapper<Appversion> ().where ("appid={0}", appversion.getAppid ()).orderBy ("version", false));
                        StaticJedisUtils.del (appversion.getAppid ()+"_versions");
                        for (Appversion appversion1: versionlist) {
                            HashMap<String,String> map1=new HashMap<> ();
                            map1.put ("appid",appversion1.getAppid ());
                            map1.put ("version",appversion1.getVersion ());
                            StaticJedisUtils.listObjectAdd (appversion.getAppid ()+"_versions",map1);
                        }
                    }else{
                    	flag = false;
                    	break;
                    }
            	}
            }
        	if(flag) {
        		boolean isdel = IAppsService.deleteById(Integer.parseInt(id));
        		if(isdel) {
        			return 1;
        		} else {
        			return 0;
        		}
        	}
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }


    }
}
