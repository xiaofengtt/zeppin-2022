package com.makati.business.appInteface.web;


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
@Api(value = "抽奖接口",description = "抽奖接口")
//@ApiIgnore
@Slf4j
public class CarzycjController {



    @RequestMapping(value = "findlimit",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="有门槛抽奖", notes="有门槛抽奖")
    public Response crazycjlimit() {
        List<Object> crazycjlimit = StaticJedisUtils.getList ("crazycjlimit");
        if(crazycjlimit==null||crazycjlimit.size () == 0){
            HashMap<String,String> map=new HashMap<String,String> ();
            map.put ("couponid","16");
            map.put ("couponname","5元门槛优惠券");
            map.put ("couponimg","http://47.106.209.243/wx/storage/fetch/52@2x.png");
            HashMap<String,String> map1=new HashMap<String,String> ();
            map1.put ("couponid","12");
            map1.put ("couponname","10元门槛优惠券");
            map1.put ("couponimg","http://47.106.209.243/wx/storage/fetch/102@2x.png");
            HashMap<String,String> map2=new HashMap<String,String> ();
            map2.put ("couponid","13");
            map2.put ("couponname","30元门槛优惠券");
            map2.put ("couponimg","http://47.106.209.243/wx/storage/fetch/302@2x.png");
            HashMap<String,String> map3=new HashMap<String,String> ();
            map2.put ("couponid","14");
            map2.put ("couponname","100元门槛优惠券");
            map2.put ("couponimg","http://47.106.209.243/wx/storage/fetch/1002@2x.png");
            ArrayList<HashMap<String,String>> list=new ArrayList<> ();
            list.add (map);
            list.add (map1);
            list.add (map2);
            list.add (map3);



//
//            HashMap<String,String> map3=new HashMap<String,String> ();
//            map3.put ("goodname","Apple Watch5");
//            map3.put ("goodimg","http://47.106.209.243/wx/storage/fetch/b33d0c31818c1a4b7.jpg");
//            HashMap<String,String> map4=new HashMap<String,String> ();
//            map4.put ("goodname","Ipad 11pro");
//            map4.put ("goodimg","http://47.106.209.243/wx/storage/fetch/b33d0c31818c1a4b8.jpg");
//            ArrayList<HashMap<String,String>> list1=new ArrayList<> ();
//            list1.add (map3);
//            list1.add (map4);

            HashMap<String,Object> carzymap=new HashMap<String,Object> ();
            carzymap.put ("coupon",list);
//            carzymap.put ("goods",list1);
            StaticJedisUtils.setObjectMap ("crazycjlimit",carzymap,0);
            return Response.successResponce(carzymap);
        }

        return Response.successResponce(crazycjlimit);

    }



    @RequestMapping(value = "findnolimit",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="无门槛抽奖", notes="无门槛抽奖")
    public Response findnolimit() {

        List<Object> crazycjlimit = StaticJedisUtils.getList ("crazycjnolimit");
        if(crazycjlimit==null||crazycjlimit.size () == 0){
            HashMap<String,String> map=new HashMap<String,String> ();
            map.put ("couponid","15");
            map.put ("couponname","5元无门槛优惠券");
            map.put ("couponimg","http://47.106.209.243/wx/storage/fetch/51@2x.png");
            HashMap<String,String> map1=new HashMap<String,String> ();
            map1.put ("couponid","9");
            map1.put ("couponname","10元无门槛优惠券");
            map1.put ("couponimg","http://47.106.209.243/wx/storage/fetch/101@2x.png");
            HashMap<String,String> map2=new HashMap<String,String> ();
            map2.put ("couponid","11");
            map2.put ("couponname","30元无门槛优惠券");
            map2.put ("couponimg","http://47.106.209.243/wx/storage/fetch/301@2x.png");
            HashMap<String,String> map3=new HashMap<String,String> ();
            map3.put ("couponid","10");
            map3.put ("couponname","100元无门槛优惠券");
            map3.put ("couponimg","http://47.106.209.243/wx/storage/fetch/1001@2x.png");
            ArrayList<HashMap<String,String>> list=new ArrayList<> ();
            list.add (map);
            list.add (map1);
            list.add (map2);
            list.add (map3);



//
//            HashMap<String,String> map3=new HashMap<String,String> ();
//            map3.put ("goodname","Apple Watch5");
//            map3.put ("goodimg","http://47.106.209.243/wx/storage/fetch/b33d0c31818c1a4b7.jpg");
//            HashMap<String,String> map4=new HashMap<String,String> ();
//            map4.put ("goodname","Ipad 11pro");
//            map4.put ("goodimg","http://47.106.209.243/wx/storage/fetch/b33d0c31818c1a4b8.jpg");
//            ArrayList<HashMap<String,String>> list1=new ArrayList<> ();
//            list1.add (map3);
//            list1.add (map4);


            HashMap<String,Object> carzymap=new HashMap<String,Object> ();
            carzymap.put ("coupon",list);
//            carzymap.put ("goods",list1);
            StaticJedisUtils.setObjectMap ("crazycjlimit",carzymap,0);
            return Response.successResponce(carzymap);
        }

        return Response.successResponce(crazycjlimit);


    }




}
