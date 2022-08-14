package com.makati.business.app.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
 * @since 2019-09-06
 */
@Controller
@RequestMapping("/app/appversion")
public class AppversionController {

    @Autowired
    private IAppversionService IAppversionService;
    @Autowired
    private IAppsService IAppsService;




    @RequestMapping("/save")
    @ResponseBody
//    @CacheEvict(value = "emp", key = "#appid+#version")
    public int validatorCode(Integer id,String appid,String version ,Integer onoff,Integer qzonoff,Integer isshow,String url,String qd,String parentid,String text,String zburl,String mjurl,Integer state,String downurl) {

        Appversion appversion=new Appversion(id,appid,version,onoff,qzonoff,isshow,url,qd,parentid,text,zburl,mjurl,state,downurl);

        int isave=1;
        try{

            boolean b = IAppversionService.insertOrUpdate (appversion);

//            Appversion appversion11 = IAppversionService.selectById(appversion.getId ());
            if(b){
                StaticJedisUtils.set (appversion.getAppid ()+appversion.getVersion (),appversion,0);


                //最新版本号
                List<Appversion> versionlist = IAppversionService.selectList (new EntityWrapper<Appversion> ().where ("appid={0}", appversion.getAppid ()).orderBy ("version", false));
                StaticJedisUtils.del (appid+"_versions");
                for (Appversion appversion1: versionlist) {
                    HashMap<String,String> map1=new HashMap<> ();
                    map1.put ("appid",appversion1.getAppid ());
                    map1.put ("version",appversion1.getVersion ());
                    StaticJedisUtils.listObjectAdd (appid+"_versions",map1);
                }


                return 1;
            }else{
                return 0;
            }

        }catch (DataAccessException e){
            isave=2;
        }catch (Exception e){
            e.printStackTrace ();
            isave=0;
        }

        return isave;

    }



    @RequestMapping("mjbinfo")
    public String huawei(String id,String apid,String qd,String version,HttpServletRequest req, ModelMap map) {

        EntityWrapper  ew=new EntityWrapper();

        ew.where("parentid={0}", id);
        if(apid!=null&&!apid.equals(""))
            ew.like("appid",apid);
        if(qd!=null&&!qd.equals(""))
            ew.like("qd",qd);
        if(version!=null&&!version.equals(""))
            ew.like("version",version);

        ew.orderBy("version",false);
        List list = IAppversionService.selectList(ew);
        Apps apps = IAppsService.selectById(id);

        map.addAttribute("appid",id);
        map.addAttribute("app",list);
        map.addAttribute("appname",apps.getAppname());


        map.addAttribute("apid",apid==null? "":apid);
        map.addAttribute("qd",qd==null? "":qd);
        map.addAttribute("version",version==null? "":version);
        return "apps/mjbinfo";

    }

    /**
     * 马甲包列表
     * @param req
     * @return
     */
    @RequestMapping("/mjblist")
    public String mjblist(HttpServletRequest req, ModelMap map) {

        EntityWrapper ew=new EntityWrapper();

        List list = IAppsService.selectList(ew);

        ArrayList<Map> mjglist=new ArrayList<Map>();
        Apps apps=new Apps();
        apps.setAppname("");

        int  x=list.size()/4;
        if(list.size()%4!=0){
            x++;
        }

        for (int i = 1; i <= x; i++) {
            int index =(i-1)*4;
            HashMap<String,Object> maps=new HashMap<String,Object>();
            int tempint=index++;

            maps.put("mjbinfo1",tempint>= list.size()? apps:list.get(tempint));
            tempint=index++;
            maps.put("mjbinfo2",tempint>= list.size()? apps:list.get(tempint));
            tempint=index++;
            maps.put("mjbinfo3",tempint>= list.size()? apps:list.get(tempint));
            tempint=index++;
            maps.put("mjbinfo4",tempint>= list.size()? apps:list.get(tempint));

            mjglist.add(maps);
        }

//        if(list.size()%4!=0){
//            Apps ap= new Apps();
//            ap.setAppname("");
//            maps.put("mjbinfo2",ap);
//            mjglist.add(maps);
//        }

        map.addAttribute("mjbs",mjglist);

        return "apps/mjblist";

    }




    @RequestMapping("edit-mjbinfo")
    public String oppo(String id,String appname,HttpServletRequest req, ModelMap map) {

        EntityWrapper  ew=new EntityWrapper();
        Appversion appversion=new Appversion();
        Apps apps = IAppsService.selectOne(ew.where("appname={0}",appname));
        if(id!=null){
            ew=new EntityWrapper();
            appversion = IAppversionService.selectOne(ew.where("id={0}", id));
        }

        if(appversion==null)
            appversion=new Appversion();

        map.addAttribute("appname",appname);
        map.addAttribute("appid",apps.getId());
        map.addAttribute("app",appversion);
        return "apps/mjbedit";

    }


    @RequestMapping("changeonoff")
    @ResponseBody
//    @CacheEvict(value = "emp", key = "#appid+#version")
    public int changeonoff(String id,Integer state,String appid,String version,HttpServletRequest req, ModelMap map) {
        try{
            EntityWrapper  ew=new EntityWrapper();

            Appversion appversion1 = IAppversionService.selectById(id);
            appversion1.setState(state);

            boolean b = IAppversionService.updateById(appversion1);

            if(b){
                StaticJedisUtils.set (appid+version,appversion1,0);
                return 1;
            }else{
                return 0;
            }

        }catch (Exception e){
            e.printStackTrace ();
            return 0;
        }


    }
    @RequestMapping("onoff")
    @ResponseBody
//    @CacheEvict(value = "emp", key = "#appid+#version")
    public int onoff(boolean istrue,String appid,String version,String id,HttpServletRequest req, ModelMap map) {

        try {
            EntityWrapper  ew=new EntityWrapper();

            Appversion appversion = IAppversionService.selectById(Integer.parseInt(id));

            if(istrue){
                appversion.setOnoff(1);

            }else{
                appversion.setOnoff(0);

            }
            boolean b = IAppversionService.updateById(appversion);

            if(b){
                StaticJedisUtils.set (appid+version,appversion,0);
                return 1;
            }else{
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace ();
            return 0;
        }

    }

    @RequestMapping("qzonoff")
    @ResponseBody
//    @CacheEvict(value = "emp", key = "#appid+#version")
    public int qzonoff(boolean istrue,String appid,String version,String id,HttpServletRequest req, ModelMap map) {

        try{
            EntityWrapper  ew=new EntityWrapper();

            Appversion appversion = IAppversionService.selectById(Integer.parseInt(id));

            if(istrue){
                appversion.setQzonoff(1);

            }else{
                appversion.setQzonoff(0);

            }
            boolean b = IAppversionService.updateById(appversion);


            if(b){
                StaticJedisUtils.set (appid+version,appversion,0);
                return 1;
            }else{
                return 0;
            }

        }catch (Exception e){
            e.printStackTrace ();
            return 0;
        }

    }


    @RequestMapping("delmjbinfo")
    @ResponseBody
    public int delmjbinfo(String id,HttpServletRequest req, ModelMap map) {

        try{
            Appversion appversion = IAppversionService.selectById(Integer.parseInt(id));

            boolean isdel = IAppversionService.deleteById(Integer.parseInt(id));


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


                return 1;
            }else{
                return 0;
            }

        }catch (Exception e){
            e.printStackTrace ();
            return 0;
        }


    }

	@RequestMapping("editcache")
	@ResponseBody
	public int editcache(String id, String cacheStr, HttpServletRequest req, ModelMap map) {

		try {
			// 查询所有马甲包信息，并删除
			EntityWrapper ew = new EntityWrapper();

			ew.where("parentid={0}", id);
			List<Appversion> list = IAppversionService.selectList(ew);
			boolean flag = true;
			if (list != null && list.size() > 0) {
				for (Appversion appversion : list) {
					String mainUrl = appversion.getZburl();
					StringBuilder sb = new StringBuilder();
					if (mainUrl != null && !"".equals(mainUrl)) {
						if (mainUrl.indexOf("cache") > -1) {
							String[] url = mainUrl.split("cache");
							sb.append(url[0]);
							sb.append("cache=" + cacheStr);
						} else {
							sb.append(mainUrl);
							sb.append("&cache=" + cacheStr);
						}
					}
					appversion.setZburl(sb.toString());
					boolean b = IAppversionService.insertOrUpdate(appversion);

//                  Appversion appversion11 = IAppversionService.selectById(appversion.getId ());
					if (b) {
						StaticJedisUtils.set(appversion.getAppid() + appversion.getVersion(), appversion, 0);
					} else {
						flag = false;
						break;
					}
				}
			}
			if(flag) {
    			return 1;
    		} else {
    			return 0;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}
}
