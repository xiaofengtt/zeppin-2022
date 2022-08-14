package cn.zeppin.product.ntb.qcb.controller.wechat;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.ServletRequest;

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
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

@Controller
@RequestMapping(value = "/qcbWechat/sms")
public class WechatSendSmsController extends BaseController{
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	/**
	 * 
	 * @param mobile base64编码
	 * @param codeType 值=code--登录   base64编码
	 * @param token  base64编码 base64(device+timestamps+md5(key+timestamps+mobile+codeType))
	 * @return
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号码", required = true)
	@ActionParam(key = "codeType", type = DataType.STRING, message = "验证码类型", required = true)
	@ActionParam(key = "token", type = DataType.STRING, message = "校验令牌", required = true)
	@ResponseBody
	public Result sendCode(String mobile, String codeType, String token){
		
		token = Base64Util.getFromBase64(token);
		
		if(token != null && !"".equals(token)){
			String deviceNumber = token.substring(0,2);
			if(deviceNumber == null || "".equals(deviceNumber)){
				return ResultManager.createFailResult("未识别的设备号！");
			}
			
			mobile = Base64Util.getFromBase64(mobile);
			
			if (!Utlity.isMobileNO(mobile)) {
				return ResultManager.createFailResult("手机号非法！");
			}
			
			
			codeType = Base64Util.getFromBase64(codeType);
			
			QcbEmployee investor = this.qcbEmployeeService.getByMobile(mobile);
			
			
			String codestr = Utlity.getCaptcha();
			String content = "验证码："+codestr+"，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）";
			
			if(MobileCodeTypes.REGISTER.equals(codeType)){//注册验证手机号是否已注册
				content = "验证码："+codestr+"，用于本次登录/注册，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）";
				if(investor != null){
					return ResultManager.createFailResult("用户已注册！");
				}
			} else if (MobileCodeTypes.RESETPWD.equals(codeType)) {//找回密码 验证手机号是否已注册
				content = "验证码："+codestr+"，用于密码修改，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
				if(investor == null){
					return ResultManager.createFailResult("用户未注册！");
				}
			} else if (MobileCodeTypes.CODE.equals(codeType)) {
				if(investor == null){
					return ResultManager.createFailResult("用户未开通！");
				}
			} else {
				return ResultManager.createFailResult("短信验证码发送失败！");
			}
			
			if (Utlity.DEVICE_NUMBER_QCB_WECHAT.equals(deviceNumber)) {
				if(token.length() != 47){
					return ResultManager.createFailResult("验证失败！");
				}
				String timestamp = token.substring(2,15);
				if(timestamp == null || "".equals(timestamp)){
					return ResultManager.createFailResult("验证失败！");
				}
				long time = Long.parseLong(timestamp);
				long nowTime = System.currentTimeMillis();
				if(time <= nowTime){
					if(Utlity.checkLessTime(time, nowTime)){
						return ResultManager.createFailResult("验证失败，请求超时！");
					}
				} else {
					return ResultManager.createFailResult("验证失败，请求超时！");
				}
				
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					return ResultManager.createFailResult("验证失败！");
				}
				
				String realMD5Str = MD5.getMD5(Utlity.KEY_QCB+timestamp+mobile+codeType);
				if(md5Str.equals(realMD5Str)){
					try {
						String result = SendSms.sendSms4Qcb(content, mobile);
						if ("0".equals(result.split("_")[0])) {
							
							MobileCode code = new MobileCode();
							code.setUuid(UUID.randomUUID().toString());
							code.setCode(codestr);
							code.setContent(content);
							code.setCreatetime(new Timestamp(System.currentTimeMillis()));
							code.setCreatorType(MobileCodeCreatorType.QCB_EMP);
							code.setMobile(mobile);
							code.setStatus(MobileCodeStatus.NORMAL);
							code.setType(codeType);
							this.mobileCodeService.insertMobileCode(code);
							return ResultManager.createSuccessResult("短信验证码发送成功");
						}
						
						return ResultManager.createFailResult("短信验证码发送失败！");
					} catch (Exception e) {
						e.printStackTrace();
						return ResultManager.createFailResult("验证码发送失败！");
					}
				}
			}
		}
		return ResultManager.createFailResult("验证码发送失败！");
	}
	
	/**
	 * 发送手机验证码认证用户身份
	 * @return
	 */
	@RequestMapping(value = "/sendCodeToCheck", method = RequestMethod.POST)
	@ActionParam(key = "codeType", type = DataType.STRING, message = "验证类型", required = true)
	@ResponseBody
	public Result sendCodeToCheck(String codeType, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		codeType = Base64Util.getFromBase64(codeType);
		if(!MobileCodeTypes.EMP_BANKCARD_DELETE.equals(codeType) && !MobileCodeTypes.EMP_WITHDRAW.equals(codeType) 
				&& !MobileCodeTypes.FUNDCODE.equals(codeType)){
			return ResultManager.createFailResult("验证类型错误！");
		}
		
		String phone = qe.getMobile();
		
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号非法！");
		}
		
		String codestr = Utlity.getCaptcha();
		String content = "验证码："+codestr+"，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
		
		try {
			String result = SendSms.sendSms4Qcb(content, phone);
			if ("0".equals(result.split("_")[0])) {
				
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatorType(MobileCodeCreatorType.QCB_EMP);
				code.setCreator(qe.getUuid());
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(codeType);
				this.mobileCodeService.insertMobileCode(code);
				return ResultManager.createSuccessResult("短信验证码发送成功");
			}
			
			return ResultManager.createFailResult("短信验证码发送失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("验证码发送失败！");
		}
	}
	
	/**
	 * 发送手机验证码
	 * @return
	 */
	@RequestMapping(value = "/sendCodeByMobile", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号", required = true)
	@ActionParam(key = "codeType", type = DataType.STRING, message = "验证类型", required = true)
	@ResponseBody
	public Result sendCodeByMobile(String mobile, String codeType, ServletRequest request){
		if(request.getAttribute("employee") == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		QcbEmployee qe = (QcbEmployee)request.getAttribute("employee");
		
		codeType = Base64Util.getFromBase64(codeType);
		if(!MobileCodeTypes.EMP_BANKCARD_ADD.equals(codeType)){
			return ResultManager.createFailResult("验证类型错误！");
		}
		
		String phone = Base64Util.getFromBase64(mobile);
		if (!Utlity.isMobileNO(phone)) {
			return ResultManager.createFailResult("手机号非法！");
		}
		
		String codestr = Utlity.getCaptcha();
		String content = "验证码："+codestr+"，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
		
		try {
			String result = SendSms.sendSms4Qcb(content, phone);
			if ("0".equals(result.split("_")[0])) {
				
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatorType(MobileCodeCreatorType.QCB_EMP);
				code.setCreator(qe.getUuid());
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(codeType);
				this.mobileCodeService.insertMobileCode(code);
				return ResultManager.createSuccessResult("短信验证码发送成功");
			}
			
			return ResultManager.createFailResult("短信验证码发送失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("验证码发送失败！");
		}
	}
}
