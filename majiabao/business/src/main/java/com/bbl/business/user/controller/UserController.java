package com.bbl.business.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbl.business.user.entity.User;
import com.bbl.business.user.service.IUserService;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.dto.UserInfo;
import com.bbl.interceptor.AuthInterceptor;
import com.bbl.util.IdGen;
import com.bbl.util.IpUtil;
import com.bbl.util.Response;
import com.bbl.util.encrypt.BCryptPasswordEncoder;
import com.bbl.util.encrypt.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author bobo
 * @since 2019-11-13
 */
@RestController
@RequestMapping("/user/")
@Api(value = "登入验证",description = "登入验证接口")
public class UserController {

    @Autowired
    private IUserService iUserService;



    /**
     * 账号注册
     * @param request 请求对象
     * @return 登录结果

     */
    @PostMapping("register")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="username" ,value = "账户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="password" ,value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="mobile" ,value = "手机号", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="code" ,value = "验证码", required = false, dataType = "String")
    })
    @ApiOperation(value="注册", notes="注册")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object register(String username,String password,String mobile,String code, HttpServletRequest request) {


        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ){
            return Response.badArgument();
        }

        QueryWrapper qw=new QueryWrapper ();
        qw.apply ("username={0}",username);
        User one = iUserService.getOne (qw);
        if (one!=null) {
            return Response.failResponce ("101", "用户名已注册");
        }


//        String cacheCode= StaticJedisUtils.get(SMSUtils.SMS_PREFIX+"86"+mobile);
//        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
//            return Response.failResponce("104", "验证码错误");
//        }

        User user = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setNickname(username);
        user.setGender( 0);
        user.setUserLevel( 0);
        user.setStatus(0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        iUserService.save (user);



        user = iUserService.getOne (qw);
        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());
        userInfo.setUserId (user.getId ());

        // token
        String token = MD5Util.string2MD5 (String.valueOf (user.getId ()),null);
        token= MD5Util.string2MD5(token,null);
        userInfo.setMd5Salt(IdGen.uuid());

        //插入缓存
        StaticJedisUtils.set("user_"+username,"user_"+token,60 * 60 * 24 * 7);
        StaticJedisUtils.set("user_"+token,userInfo,60 * 60 * 24 * 7);


        Map<Object, Object> result = new HashMap<Object, Object> ();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return Response.successResponce (result);
    }



    /**
     * 账号登录
     *
     * @param request 请求对象
     * @return 登录结果
     */
    @PostMapping("login_v1")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="username" ,value = "账户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="password" ,value = "密码", required = true, dataType = "String")
    })
    @ApiOperation(value="登入", notes="登入")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object login_v1(String username,String password ,HttpServletRequest request) {
        if (username == null || password == null) {
            return Response.badArgument();
        }

        QueryWrapper qw=new QueryWrapper ();
        qw.apply ("username={0}",username);
        User user = iUserService.getOne (qw);
        if (user==null) {
            return Response.failResponce ("102", "账号不存在");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return Response.failResponce("103", "账号密码不对");
        }

        // 更新登录情况
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (!iUserService.updateById (user)) {
            return Response.updatedDataFailed();
        }

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());
        userInfo.setUserId (user.getId());

        // token
        String token = MD5Util.string2MD5 (String.valueOf (user.getId ()),null);
        token= MD5Util.string2MD5(token,null);
        userInfo.setMd5Salt(IdGen.uuid());

        //清除之前的缓存
        String temp = StaticJedisUtils.get("user_" + username);
        StaticJedisUtils.del(temp);
        StaticJedisUtils.patternDel("user_"+username);

        //插入缓存
        StaticJedisUtils.set("user_"+username,"user_"+token,60 * 60 * 24 * 7);
        StaticJedisUtils.set("user_"+token,userInfo,60 * 60 * 24 * 7);



        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return Response.successResponce(result);
    }


//    /**
//     * 请求注册验证码
//     *
//     * TODO
//     * 这里需要一定机制防止短信验证码被滥用
//     *
//     * @return
//     */
//    @PostMapping("regCaptcha")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name="mobile" ,value = "手机号", required = true, dataType = "String")
//    })
//    @ApiOperation(value="发送验证码", notes="发送验证码")
//    @AuthInterceptor(needAuthTokenVerify = false)
//    public Object registerCaptcha(String mobile, HttpServletRequest req) {
////        String phoneNumber = JacksonUtil.parseString(body, "mobile");
//        String phoneNumber = mobile;
//        if (StringUtils.isEmpty(phoneNumber)) {
//            return Response.badArgument();
//        }
//        if (!RegexUtil.isMobileExact(phoneNumber)) {
//            return ResponseUtil.badArgumentValue();
//        }
//
//        String ipAddr = RequestUtil.getIpAddr(req);
//        Map<String, Object> stringObjectMap = SmsUtil.sendCode(ipAddr, phoneNumber);
//
//        int status=Integer.parseInt(stringObjectMap.get("status").toString());
//
//        if (status==1) {
//            return ResponseUtil.ok();
//        }else{
//            int errno=Integer.parseInt(stringObjectMap.get("status").toString());
//            String errmsg=stringObjectMap.get("errmsg").toString();
//            return ResponseUtil.fail(errno,errmsg);
//        }
////        String code = CharUtil.getRandomNum(6);
////        notifyService.notifySmsTemplate(phoneNumber, NotifyType.CAPTCHA, new String[]{code});
////
////        boolean successful = CaptchaCodeManager.addToCache(phoneNumber, code);
////        if (!successful) {
////            return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
////        }
////
////        return ResponseUtil.ok();
//    }
//




    /**
     * 删除账号
     *
     * @param request 请求对象
     * @return 删除账号
     */
    @PostMapping("deluser")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "账户", required = true, dataType = "Int")
    })
    @ApiOperation(value="删除用户", notes="删除用户")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object deluser(String userId,HttpServletRequest request) {
        if (userId == null ) {
            return Response.badArgument();
        }

        boolean b = iUserService.removeById (userId);

        return Response.successResponce(b);
    }



}
