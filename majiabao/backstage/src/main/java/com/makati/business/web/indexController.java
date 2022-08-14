package com.makati.business.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.makati.business.app.entity.Appversion;
import com.makati.business.app.service.IAppversionService;
import com.makati.common.entity.Entity;
import com.makati.common.util.RandomValidateCodeUtil;
import com.makati.common.util.StaticJedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-09-06
 */
@Controller
@RequestMapping("")
@Slf4j
public class indexController {

    @Autowired
    private IAppversionService iAppversionService;


    /**
     * 登入
     * @param req
     * @param response
     * @return
     */
    @RequestMapping("")
    public String login(HttpServletRequest req, HttpServletResponse response, ModelMap map) {


        return "login";

    }

    /**
     * 注册
     * @param req
     * @param response
     * @return
     */
    @RequestMapping("register")
    public String register(HttpServletRequest req, HttpServletResponse response) {

        return "register";

    }

    /**
     * 首页
     * @param req
     * @return
     */
    @RequestMapping("menu")
    public String index(HttpServletRequest req, ModelMap map) {

        EntityWrapper  ew=new EntityWrapper();

        List list = iAppversionService.selectList(ew);

        Long onlineAmount = StaticJedisUtils.gethyperLog ("OnlineAmount");
        Long onlineToday = StaticJedisUtils.gethyperLog ("OnlineToday");
        log.info ("总访问开关人数："+onlineAmount);

        map.addAttribute("onlineAmount",onlineAmount);
        map.addAttribute("onlineToday",onlineToday);

        map.addAttribute("apps",list);

        return "menu";

    }
    @RequestMapping("clear")
    @ResponseBody
    public String clear(HttpServletRequest req, ModelMap map) {
        req.getSession().removeAttribute("USER");
        return "1";

    }

    @RequestMapping("addfile")
    public String addfile(HttpServletRequest req, ModelMap map) {
        return "addfile";

    }


    /**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
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








}
