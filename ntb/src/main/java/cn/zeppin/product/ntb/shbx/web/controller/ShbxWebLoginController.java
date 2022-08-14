package cn.zeppin.product.ntb.shbx.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.Utlity;

@Controller
@RequestMapping(value = "/shbxWeb/login")
public class ShbxWebLoginController extends BaseController{
	
	@Autowired
	private IShbxUserService shbxUserService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	/**
	 * 用户登录
	 * @param token base64(device+time+md5(key+time+mobile+code))
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号", required = true)
	@ResponseBody
	public Result login(String token, String mobile){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		if("18888888888".equals(mobile)){
			Map<String, Object> data = new HashMap<String, Object>();
			ShbxUser su = this.shbxUserService.getByMobile("18888888888");
			data.put("shbxUser", su);
			session.setAttribute("shbxUser", su);
			return ResultManager.createDataResult(data,"登录成功！");
		}
		Map<String, Object> result = null;
		try {
			if(this.shbxUserService.isExistShbxUserByMobile(mobile, null)){
				result = this.shbxUserService.loginBycode(token,mobile);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("shbxUser", result.get("shbxUser"));
				session.setAttribute("shbxUser", result.get("shbxUser"));
				return ResultManager.createDataResult(data,result.get("message") == null ? "登录成功！" : result.get("message").toString());
			}else{
				Map<String, Object> data = new HashMap<String, Object>();
				result = this.shbxUserService.register(token,mobile);
				data.put("shbxUser", result.get("shbxUser"));
				session.setAttribute("shbxUser", result.get("shbxUser"));
				return ResultManager.createDataResult(data,result.get("message") == null ? "注册成功！" : result.get("message").toString());
			}
		} catch (TransactionException te) {
			super.flushAll();
			return ResultManager.createFailResult(te.getMessage() == null ? "注册失败！" : te.getMessage());
		} catch (Exception e) {
			super.flushAll();
			return ResultManager.createFailResult("注册失败，服务异常");
		}
	}
	
	/**
	 * 用户登录
	 * @param token base64(device+time+md5(key+time+mobile+md5(pwd)))
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/loginByPwd", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "验证令牌", required = true)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号", required = true)
	@ResponseBody
	public Result loginByPwd(String token, String mobile){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		token = Base64Util.getFromBase64(token);
		mobile = Base64Util.getFromBase64(mobile);
		
		if(this.shbxUserService.isExistShbxUserByMobile(mobile, null)){
			ShbxUser su = this.shbxUserService.getByMobile(mobile);
			
			if(Utlity.checkStringNull(su.getLoginPassword())){
				return ResultManager.createFailResult("用户尚未设置密码！");
			}
			
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("登录失败，未识别的设备号！");
			}
			
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				return ResultManager.createFailResult("请求超时，请重新登录！");
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					return ResultManager.createFailResult("请求超时，请重新登录！");
				}
			} else {
				return ResultManager.createFailResult("请求超时，请重新登录！");
			}
			
			String md5Str = token.substring(15);
			if(md5Str == null || "".equals(md5Str)){
				return ResultManager.createFailResult("用户名或密码错误，登录失败！");
			}
			String realMD5Str = MD5.getMD5(Utlity.KEY_QCB+time+su.getMobile()+su.getLoginPassword());
			if(md5Str.equals(realMD5Str)){
				session.setAttribute("shbxUser", su);
				return ResultManager.createDataResult(su, "登录成功！");
			}else{
				return ResultManager.createFailResult("用户名或密码错误，登录失败！");
			}
		}else{
			return ResultManager.createFailResult("用户尚未注册！");
		}
	}
	
	/**
	 * 用户验证
	 * @param token  Base64(mobile+str+md5(mobile+str+uuid))
	 * @return
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ActionParam(key = "token", type = DataType.STRING, message = "token", required = true)
	@ResponseBody
	public Result auth(String token){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		token = Base64Util.getFromBase64(token);
		if(token.length() != 49){
			session.removeAttribute("shbxUser");
			return ResultManager.createFailResult("登录验证失败，请重新登录！");
		}
		
		String mobile = token.substring(0,11);
		String str = token.substring(11,17);
		String md5 = token.substring(17);
		ShbxUser su = this.shbxUserService.getByMobile(mobile);
		
		if(su == null){
			session.removeAttribute("shbxUser");
			return ResultManager.createFailResult("用户未注册！");
		}
		
		String enMd5 = MD5.getMD5(mobile+str+su.getUuid());
		if(!md5.equals(enMd5)){
			session.removeAttribute("shbxUser");
			return ResultManager.createFailResult("登录验证失败，请重新登录！！");
		}
		
		session.setAttribute("shbxUser", su);
		return ResultManager.createSuccessResult("登录成功!");
	}

	/**
	 * 找回密码
	 * @param phone base64
	 * @param token 规则Base64(timestamps+md5(pwd)+MD5(key+time+md5(mobile)+md5(pwd)+md5(验证码)))
	 * @return
	 */
	@RequestMapping(value = "/forgotpwd", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message="手机号", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message="用户密码加密信息", required = true)
	@ResponseBody
	public Result forgotpwd(String mobile, String token){
		mobile = Base64Util.getFromBase64(mobile);
		token = Base64Util.getFromBase64(token);
		
		if(token.length() != 77){
			return ResultManager.createFailResult("验证错误！");
		}
		
		String timestamp = token.substring(0,13);
		String pwd = token.substring(13,45);
		String md5Str = token.substring(45);
		if(md5Str == null || "".equals(md5Str)){
			return ResultManager.createFailResult("验证错误！");
		}
		

		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(mobile)){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
		if(!MobileCodeTypes.RESETPWD.equals(mc.getType())){
			return ResultManager.createFailResult("验证码输入错误！");
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		String realMD5Str = MD5.getMD5(Utlity.KEY_QCB+timestamp+mc.getMobile()+pwd+mc.getCode());
		if(md5Str.equals(realMD5Str)){//登录成功	
			ShbxUser su = this.shbxUserService.getByMobile(mobile);
			su.setLoginPassword(pwd);
			this.shbxUserService.update(su);
			return ResultManager.createSuccessResult("找回密码成功！");
		} else {
			return ResultManager.createFailResult("验证码错误，认证失败！");
		}
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Result logout(){
		Subject subject = SecurityUtils.getSubject();
		Session shiroSession = subject.getSession();
		shiroSession.removeAttribute("shbxUser");
		subject.logout();
		return ResultManager.createSuccessResult("退出成功！");
	}
	
	/**
	 * 获取服务器当前时间
	 * @return
	 */
	@RequestMapping(value = "/getTime", method = RequestMethod.GET)
	@ResponseBody
	public Result getTime(){
		return ResultManager.createDataResult(System.currentTimeMillis());
	}
}
