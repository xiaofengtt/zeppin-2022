package com.bbl.business.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbl.async.AsyncImg;
import com.bbl.business.good.entity.LitemallGoods;
import com.bbl.business.good.mapper.LitemallGoodsMapper;
import com.bbl.business.good.service.ILitemallGoodsService;
import com.bbl.cache.redis.JedisUtils;
import com.bbl.cache.redis.RedisMsgSubListener;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.thread.Runner;
import com.bbl.thread.Threadpool;
import com.bbl.thread.Userpays;
import com.bbl.util.RandomValidateCodeUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-09-06
 */
@Controller
@CrossOrigin
@RequestMapping("")
@Slf4j
public class indexController {

    @Resource
    LitemallGoodsMapper litemallGoodsMapper;
    @Autowired
    AsyncImg asyncImg;

    /**
     * 登入
     * @param req
     * @param response
     * @return
     */
    @GetMapping("")
    public String login(HttpServletRequest req, HttpServletResponse response) {
        return "login";
    }


    /**
     * 上传文件
     * @return
     */
    @PostMapping("addfile")
    public String addfile(HttpServletRequest req, ModelMap map) {
        return "addfile";

    }


    /**
     * 生成验证码
     */
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil ();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            log.error("获取验证码失败>>>>   ", e);
        }
    }



    @PostMapping("/savefile")
    @ResponseBody
    public String savefile(MultipartFile file) throws Exception {
        // 首先校验图片格式
//        List<String> imageType = Lists.newArrayList("jpg","jpeg", "png", "bmp", "gif");
        // 获取文件名，带后缀
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀格式
//        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        try  {
            // 只有当满足图片格式时才进来，重新赋图片名，防止出现名称重复的情况
//            String newFileName = UUIDTypeHandler.createUUID() + originalFilename;
            // 该方法返回的为当前项目的工作目录，即在哪个地方启动的java线程
            String dirPath = System.getProperty("user.dir");
            String path = File.separator + "static" + File.separator + originalFilename;
            File destFile = new File(dirPath + path);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
            // 将相对路径返回给前端
            return "1";
//            return path;

        } catch (Exception x){
            log.error("the picture's suffix is illegal");
            return "0";
        }
    }


    /**
     * layui  table数据
     * @return
     */
    @GetMapping("data")
    @ResponseBody
    public HashMap<String,Object> data(ModelMap map, Integer page, Integer limit, String channel, String platformtype, String createtime) {
//
//        EntityWrapper ew= new EntityWrapper<> ();
//
//        if(channel!=null&&!channel.equals ("")){
//
//            ew.like ("channel",channel);
//        }
//        if(platformtype!=null&&!platformtype.equals ("")){
//
//            ew.like ("platformtype",platformtype);
//        }
//        if(createtime!=null&&!createtime.equals ("")){
//            ew.like ("createtime",createtime);
//        }
//
//        ew.orderBy ("createtime",false);
//
//        int size= iLogindataServicee.selectList (ew).size ();
//        ew.last ("limit "+(page-1)*limit+","+limit);
//        List<Logindata> logindata = iLogindataServicee.selectList (ew);
//        map.addAttribute("logindata",logindata);
//        map.addAttribute("count",logindata.size ());
//
        HashMap<String,Object> maps=new HashMap<> ();
//        maps.put ("code","0");
//        maps.put ("msg","");
//
//
//        maps.put ("count",size);
//        maps.put ("data",logindata);
//
//
        return maps;

    }




    @GetMapping("downimg")
    @ResponseBody
    public void downimg(Integer onoff) throws Exception {
        QueryWrapper qw=new QueryWrapper ();
        qw.apply ("update_time is  NULL");
        if(onoff==1){
            List<LitemallGoods> list = litemallGoodsMapper.selectList (qw);
            log.info ("listsize="+list.size ());

            //按每50个一组分割
            List<List<LitemallGoods>> parts = Lists.partition(list, 5);

            for (List<LitemallGoods> lists:parts) {
                asyncImg.parlist (lists);
            }

        }


    }





    @GetMapping("Userpays")
    @ResponseBody
    public List<String>  Userpays() throws Exception {
        long begin = System.currentTimeMillis();

        List<String> temp =Userpays.userAllpay ("张三");
//        List<String> temp =userAllpay1 ("张三");
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - begin) + "毫秒");
        System.out.println("打印" + temp.toString ());

        return  temp;
    }



    @GetMapping("runnertest")
    @ResponseBody
    public void  runnertest123() throws Exception {
        runnertest as=new runnertest ("qqw");
        Threadpool.getThreadpool ().execute (as);


    }




    @Data
    public  class runnertest extends Runner {

        public runnertest(String threadname) {
            super (threadname);
        }


        @Override
        public void run() {
            log.info ("runnertest~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }


    }


}
