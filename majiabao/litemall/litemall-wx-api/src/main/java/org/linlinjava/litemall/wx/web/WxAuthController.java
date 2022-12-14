package org.linlinjava.litemall.wx.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.util.CharUtil;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.CouponAssignService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.config.sign.AuthInterceptor;
import org.linlinjava.litemall.wx.config.sign.MD5Util;
import org.linlinjava.litemall.wx.config.sign.RequestUtil;
import org.linlinjava.litemall.wx.dto.UserInfo;
import org.linlinjava.litemall.wx.dto.WxLoginInfo;
import org.linlinjava.litemall.wx.service.CaptchaCodeManager;
import org.linlinjava.litemall.wx.service.UserTokenManager;
import org.linlinjava.litemall.core.util.IpUtil;
import org.linlinjava.litemall.wx.util.phoneyzm.IdGen;
import org.linlinjava.litemall.wx.util.phoneyzm.SMSUtils;
import org.linlinjava.litemall.wx.util.phoneyzm.SmsUtil;
import org.linlinjava.litemall.wx.util.phoneyzm.StaticJedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.wx.util.WxResponseCode.*;

/**
 * ????????????
 */
@RestController
@RequestMapping("/wx/auth")
@Validated
@Api(value = "????????????",description = "??????????????????")
public class WxAuthController {
    private final Log logger = LogFactory.getLog(WxAuthController.class);

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private WxMaService wxService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private CouponAssignService couponAssignService;
    @Autowired
    private SmsUtil SmsUtil;

    /**
     * ????????????
     *
     * @param request ????????????
     * @return ????????????
     */
    @PostMapping("login_v1")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="username" ,value = "??????", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="password" ,value = "??????", required = true, dataType = "String")
    })
    @ApiOperation(value="??????", notes="??????")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object login_v1(String username,String password ,HttpServletRequest request) {
//        String username = JacksonUtil.parseString(body, "mobile");
//        String username = JacksonUtil.parseString(body, "username");
//        String password = JacksonUtil.parseString(body, "password");
        if (username == null || password == null) {
            return ResponseUtil.badArgument();
        }

        List<LitemallUser> userList = userService.queryByUsername(username);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "" +
                    "");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "??????????????????");
        }

        // ??????????????????
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());
        userInfo.setUserId (user.getId());

        // token
        String token = UserTokenManager.generateToken(user.getId());
        token= MD5Util.string2MD5(token,null);
        userInfo.setMd5Salt(IdGen.uuid());

        //?????????????????????
        String temp = StaticJedisUtils.get("user_" + username);
        StaticJedisUtils.del(temp);
        StaticJedisUtils.patternDel("user_"+username);

        //????????????
        StaticJedisUtils.set("user_"+username,"user_"+token,60 * 60 * 24 * 7);
        StaticJedisUtils.set("user_"+token,userInfo,60 * 60 * 24 * 7);



        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }



    /**
     * ????????????
     *
     * @param body    ???????????????{ username: xxx, password: xxx }
     * @param request ????????????
     * @return ????????????
     */
    @PostMapping("login")
    @ApiOperation(value = "", hidden = true)
    public Object login(@RequestBody String body, HttpServletRequest request) {
//        String username = JacksonUtil.parseString(body, "mobile");
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        if (username == null || password == null) {
            return ResponseUtil.badArgument();
        }

        List<LitemallUser> userList = userService.queryByUsername(username);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "???????????????");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "??????????????????");
        }

        // ??????????????????
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        // userInfo

        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());
        userInfo.setUserId(user.getId());

        // token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }


    /**
     * ????????????
     *
     * @param wxLoginInfo ???????????????{ code: xxx, userInfo: xxx }
     * @param request     ????????????
     * @return ????????????
     */
    @PostMapping("login_by_weixin")
    @ApiOperation(value = "", hidden = true)
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument();
        }

        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail();
        }

        LitemallUser user = userService.queryByOid(openId);
        if (user == null) {
            user = new LitemallUser();
            user.setUsername(openId);
            user.setPassword(openId);
            user.setWeixinOpenid(openId);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUserLevel((byte) 0);
            user.setStatus((byte) 0);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);

            userService.add(user);

            // ??????????????????????????????
            couponAssignService.assignForRegister(user.getId());
        } else {
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        // token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }


    /**
     * ?????????????????????
     *
     * TODO
     * ??????????????????????????????????????????????????????
     *
     * @return
     */
    @PostMapping("regCaptcha")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="mobile" ,value = "?????????", required = true, dataType = "String")
    })
    @ApiOperation(value="???????????????", notes="???????????????")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object registerCaptcha(String mobile, HttpServletRequest req) {
//        String phoneNumber = JacksonUtil.parseString(body, "mobile");
        String phoneNumber = mobile;
        if (StringUtils.isEmpty(phoneNumber)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(phoneNumber)) {
            return ResponseUtil.badArgumentValue();
        }

        String ipAddr = RequestUtil.getIpAddr(req);
        Map<String, Object> stringObjectMap = SmsUtil.sendCode(ipAddr, phoneNumber);

        int status=Integer.parseInt(stringObjectMap.get("status").toString());

        if (status==1) {
            return ResponseUtil.ok();
        }else{
            int errno=Integer.parseInt(stringObjectMap.get("status").toString());
            String errmsg=stringObjectMap.get("errmsg").toString();
            return ResponseUtil.fail(errno,errmsg);
        }
//        String code = CharUtil.getRandomNum(6);
//        notifyService.notifySmsTemplate(phoneNumber, NotifyType.CAPTCHA, new String[]{code});
//
//        boolean successful = CaptchaCodeManager.addToCache(phoneNumber, code);
//        if (!successful) {
//            return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "??????????????????1?????????????????????");
//        }
//
//        return ResponseUtil.ok();
    }

    /**
     * ????????????
     *
     *                {
     *                username: xxx,
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                ??????code????????????????????????????????????????????????????????????
     * @param request ????????????
     * @return ????????????
     * ?????????
     * {
     * errno: 0,
     * errmsg: '??????',
     * data:
     * {
     * token: xxx,
     * tokenExpire: xxx,
     * userInfo: xxx
     * }
     * }
     * ????????? { errno: XXX, errmsg: XXX }
     */
    @PostMapping("register")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="username" ,value = "??????", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="password" ,value = "??????", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="mobile" ,value = "?????????", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="code" ,value = "?????????", required = false, dataType = "String")
    })
    @ApiOperation(value="??????", notes="??????")
    @AuthInterceptor(needAuthTokenVerify = false)
    public Object register(String username,String password,String mobile,String code, HttpServletRequest request) {


//        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)
//                || StringUtils.isEmpty(code)) {
//            return ResponseUtil.badArgument();
//        }

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ){
            return ResponseUtil.badArgument();
        }

        List<LitemallUser> userList = userService.queryByUsername(username);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_NAME_REGISTERED, "??????????????????");
        }

//        userList = userService.queryByMobile(mobile);
//        if (userList.size() > 0) {
//            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "??????????????????");
//        }
//        if (!RegexUtil.isMobileExact(mobile)) {
//            return ResponseUtil.fail(AUTH_INVALID_MOBILE, "????????????????????????");
//        }


//        String cacheCode= StaticJedisUtils.get(SMSUtils.SMS_PREFIX+"86"+mobile);
//        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
//            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "???????????????");
//        }

        String openId = "";
        // ??????????????????????????????
        // ????????????openid
//        if(!StringUtils.isEmpty(wxCode)) {
//            try {
//                WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(wxCode);
//                openId = result.getOpenid();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseUtil.fail(AUTH_OPENID_UNACCESS, "openid ????????????");
//            }
//            userList = userService.queryByOpenid(openId);
//            if (userList.size() > 1) {
//                return ResponseUtil.serious();
//            }
//            if (userList.size() == 1) {
//                LitemallUser checkUser = userList.get(0);
//                String checkUsername = checkUser.getUsername();
//                String checkPassword = checkUser.getPassword();
//                if (!checkUsername.equals(openId) || !checkPassword.equals(openId)) {
//                    return ResponseUtil.fail(AUTH_OPENID_BINDED, "openid???????????????");
//                }
//            }
//        }

        LitemallUser user = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user = new LitemallUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setWeixinOpenid(openId);
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setNickname(username);
        user.setGender((byte) 0);
        user.setUserLevel((byte) 0);
        user.setStatus((byte) 0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        userService.add(user);

        // ?????????????????????????????????
        couponAssignService.assignForRegister(user.getId());

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());
        userInfo.setUserId (user.getId ());

        // token
        String token = UserTokenManager.generateToken(user.getId());
        token= MD5Util.string2MD5(token,null);
        userInfo.setMd5Salt(IdGen.uuid());

        //????????????
        StaticJedisUtils.set("user_"+username,"user_"+token,60 * 60 * 24 * 7);
        StaticJedisUtils.set("user_"+token,userInfo,60 * 60 * 24 * 7);


        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }


    /**
     * ???????????????
     *
     * TODO
     * ??????????????????????????????????????????????????????
     *
     * @param body ???????????? { mobile: xxx, type: xxx }
     * @return
     */
    @PostMapping("captcha")
    @ApiOperation(value = "", hidden = true)
    public Object captcha(@LoginUser Integer userId, @RequestBody String body) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String phoneNumber = JacksonUtil.parseString(body, "mobile");
        String captchaType = JacksonUtil.parseString(body, "type");
        if (StringUtils.isEmpty(phoneNumber)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(phoneNumber)) {
            return ResponseUtil.badArgumentValue();
        }
        if (StringUtils.isEmpty(captchaType)) {
            return ResponseUtil.badArgument();
        }



        if (!notifyService.isSmsEnable()) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "???????????????????????????????????????");
        }
        String code = CharUtil.getRandomNum(6);
        // TODO
        // ??????type????????????????????????
        notifyService.notifySmsTemplate(phoneNumber, NotifyType.CAPTCHA, new String[]{code});

        boolean successful = CaptchaCodeManager.addToCache(phoneNumber, code);
        if (!successful) {
            return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "??????????????????1?????????????????????");
        }

        return ResponseUtil.ok();
    }

    /**
     * ??????????????????
     *
     * @param body    ????????????
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                ??????code????????????????????????????????????????????????????????????
     * @param request ????????????
     * @return ????????????
     * ????????? { errno: 0, errmsg: '??????' }
     * ????????? { errno: XXX, errmsg: XXX }
     */
    @PostMapping("reset")
    @ApiOperation(value = "", hidden = true)
    public Object reset(@RequestBody String body, HttpServletRequest request) {
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (mobile == null || code == null || password == null) {
            return ResponseUtil.badArgument();
        }

        //???????????????????????????
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "???????????????");

        List<LitemallUser> userList = userService.queryByMobile(mobile);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_MOBILE_UNREGISTERED, "??????????????????");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * ????????????????????????
     *
     * @param body    ????????????
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                ??????code????????????????????????????????????????????????????????????
     * @param request ????????????
     * @return ????????????
     * ????????? { errno: 0, errmsg: '??????' }
     * ????????? { errno: XXX, errmsg: XXX }
     */
    @PostMapping("resetPhone")
    @ApiOperation(value = "", hidden = true)
    public Object resetPhone(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (mobile == null || code == null || password == null) {
            return ResponseUtil.badArgument();
        }

        //???????????????????????????
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "???????????????");

        List<LitemallUser> userList = userService.queryByMobile(mobile);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "??????????????????");
        }
        user = userService.findById(userId);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "??????????????????");
        }

        user.setMobile(mobile);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * ??????????????????
     *
     * @param body    ????????????
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                ??????code????????????????????????????????????????????????????????????
     * @param request ????????????
     * @return ????????????
     * ????????? { errno: 0, errmsg: '??????' }
     * ????????? { errno: XXX, errmsg: XXX }
     */
    @PostMapping("profile")
    @ApiOperation(value = "", hidden = true)
    public Object profile(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String avatar = JacksonUtil.parseString(body, "avatar");
        Byte gender = JacksonUtil.parseByte(body, "gender");
        String nickname = JacksonUtil.parseString(body, "nickname");

        LitemallUser user = userService.findById(userId);
        if(!StringUtils.isEmpty(avatar)){
            user.setAvatar(avatar);
        }
        if(gender != null){
            user.setGender(gender);
        }
        if(!StringUtils.isEmpty(nickname)){
            user.setNickname(nickname);
        }

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * ????????????????????????
     *
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("bindPhone")
    @ApiOperation(value = "", hidden = true)
    public Object bindPhone(@LoginUser Integer userId, @RequestBody String body) {
    	if (userId == null) {
            return ResponseUtil.unlogin();
        }
    	LitemallUser user = userService.findById(userId);
        String encryptedData = JacksonUtil.parseString(body, "encryptedData");
        String iv = JacksonUtil.parseString(body, "iv");
        WxMaPhoneNumberInfo phoneNumberInfo = this.wxService.getUserService().getPhoneNoInfo(user.getSessionKey(), encryptedData, iv);
        String phone = phoneNumberInfo.getPhoneNumber();
        user.setMobile(phone);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @PostMapping("logout")
    @AuthInterceptor(needAuthTokenVerify = true)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="username" ,value = "????????????", required = true, dataType = "String")
    })
    @ApiOperation(value="????????????", notes="????????????(????????????)")
    public Object logout(@LoginUser String username) {
        //?????????????????????
        String temp = StaticJedisUtils.get("user_" + username);
        StaticJedisUtils.del(temp);
        StaticJedisUtils.patternDel("user_"+username);

        return ResponseUtil.ok();
    }

    @GetMapping("info")
    @AuthInterceptor(needAuthTokenVerify = true)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "??????id", required = true, dataType = "Int")
    })
    @ApiOperation(value="??????????????????", notes="??????????????????(????????????)")
    public Object info(@LoginUser Integer userId) {
//        userId=1;
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        LitemallUser user = userService.findById(userId);
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("nickName", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("gender", user.getGender());
        data.put("mobile", user.getMobile());

        return ResponseUtil.ok(data);
    }

    @PostMapping("clear")
    @AuthInterceptor(needAuthTokenVerify = true)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "??????id", required = true, dataType = "Int")
    })
    @ApiOperation(value="????????????????????????", notes="????????????????????????")
    public Object clear(@LoginUser Integer userId) {
//        userId=1;
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        
        try {
        	LitemallUser user = userService.findById(userId);
            userService.deleteById(userId);

          //?????????????????????
            String temp = StaticJedisUtils.get("user_" + user.getUsername());
            StaticJedisUtils.del(temp);
            StaticJedisUtils.patternDel("user_"+user.getUsername());
            return ResponseUtil.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.fail();
		}
        
    }

    @PostMapping("resetNickname")
    @AuthInterceptor(needAuthTokenVerify = true)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "??????id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="nickname" ,value = "??????????????????", required = true, dataType = "String"),
    })
    @ApiOperation(value="????????????", notes="????????????")
    public Object resetNickname(@LoginUser Integer userId, String nickname, HttpServletRequest request) {
//        userId=1;
    	if(userId == null){
            return ResponseUtil.unlogin();
        }
    	
    	if(StringUtils.isEmpty(nickname)) {
    		 return ResponseUtil.badArgument();
    	}

        
        
        try {
        	LitemallUser user = userService.findById(userId);
            if(!StringUtils.isEmpty(nickname)){
                user.setNickname(nickname);
            }

            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }

//            return ResponseUtil.ok();

            //?????????????????????
            String temp = StaticJedisUtils.get("user_" + user.getUsername());
            StaticJedisUtils.del(temp);
            StaticJedisUtils.patternDel("user_"+user.getUsername());
            
            //????????????
            UserInfo userInfo = new UserInfo();
            userInfo.setNickName(user.getNickname());
            userInfo.setAvatarUrl(user.getAvatar());
            userInfo.setUserId (user.getId());

            // token
            String token = UserTokenManager.generateToken(user.getId());
            token= MD5Util.string2MD5(token,null);
            userInfo.setMd5Salt(IdGen.uuid());
            StaticJedisUtils.set("user_"+user.getUsername(),"user_"+token,60 * 60 * 24 * 7);
            StaticJedisUtils.set("user_"+token,userInfo,60 * 60 * 24 * 7);
            
            return ResponseUtil.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.fail();
		}
        
    }
}
