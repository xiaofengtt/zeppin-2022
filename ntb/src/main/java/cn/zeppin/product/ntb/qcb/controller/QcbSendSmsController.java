package cn.zeppin.product.ntb.qcb.controller;

import java.sql.Timestamp;
import java.util.UUID;

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
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

import com.google.code.kaptcha.Constants;

@Controller
@RequestMapping(value = "/qcb/sms")
public class QcbSendSmsController extends BaseController{
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	/**
	 * 
	 * @param phone
	 * @param type register--注册  resetpwd--重置密码
	 * @param token base64(device+time+md5(key+time+phone+codeType))
	 * @return
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	@ActionParam(key = "mobile", type = DataType.STRING, message = "手机号码", required = true)
	@ActionParam(key = "codeType", type = DataType.STRING, message = "验证码类型", required = true)
	@ActionParam(key = "kaptcha", type = DataType.STRING, message = "图形验证码", required = true)
	@ResponseBody
	public Result sendCode(String mobile, String codeType, String kaptcha){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		kaptcha = Base64Util.getFromBase64(kaptcha);
		
		mobile = Base64Util.getFromBase64(mobile);
		
		if (!Utlity.isMobileNO(mobile)) {
			return ResultManager.createFailResult("手机号非法！");
		}
		
		// 验证码验证
		String sessionAuthCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 

		if (!kaptcha.equalsIgnoreCase(sessionAuthCode)) {
			return ResultManager.createFailResult("图形验证码输入错误！");
		}
		
		codeType = Base64Util.getFromBase64(codeType);
		
		QcbAdmin investor = this.qcbAdminService.getByMobile(mobile);
		
		
		String codestr = Utlity.getCaptcha();
		String content = "验证码："+codestr+"，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）";
		
		if(MobileCodeTypes.REGISTER.equals(codeType)){//注册验证手机号是否已注册
			content = "验证码："+codestr+"，用于本次登录/注册，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）";
			if(investor != null){
//				return ResultManager.createFailResult("用户已注册！");
			}
		} else if (MobileCodeTypes.RESETPWD.equals(codeType)) {//找回密码 验证手机号是否已注册
			content = "验证码："+codestr+"，用于密码修改，工作人员不会向您索取，千万不要告诉其他人，验证码将在5分钟后失效。";
			if(investor == null){
				return ResultManager.createFailResult("用户未注册！");
			}
		} else if (MobileCodeTypes.CODE.equals(codeType)) {
			if(investor == null){
				return ResultManager.createFailResult("用户未注册！");
			}
		} else {
			return ResultManager.createFailResult("短信验证码发送失败！");
		}
		
		try {
			String result = SendSms.sendSms4Qcb(content, mobile);
			if ("0".equals(result.split("_")[0])) {
				
				MobileCode code = new MobileCode();
				code.setUuid(UUID.randomUUID().toString());
				code.setCode(codestr);
				code.setContent(content);
				code.setCreatetime(new Timestamp(System.currentTimeMillis()));
				code.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
				code.setMobile(mobile);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(codeType);
				this.mobileCodeService.insertMobileCode(code);
				return ResultManager.createSuccessResult("短信验证码发送成功");
			}
			
			return ResultManager.createFailResult("短信验证码发送失败！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResultManager.createFailResult("验证码发送失败！");
		}
	}
	
	/**
	 * @param type qcb_mobile_modify--修改手机号
	 * @return
	 */
	@RequestMapping(value = "/sendCodeToCheck", method = RequestMethod.POST)
	@ActionParam(key = "type", type = DataType.STRING, message = "验证类型", required = true)
	@ResponseBody
	public Result sendCodeToCheck(String type){
		if(!MobileCodeTypes.QCB_BANKCARD_ADD.equals(type) && !MobileCodeTypes.QCB_BANKCARD_DELETE.equals(type) && !MobileCodeTypes.QCB_WITHDRAW.equals(type) 
				&& !MobileCodeTypes.QCB_PAYROLL.equals(type) && !MobileCodeTypes.QCB_MOBILE_MODIFY.equals(type)){
			return ResultManager.createFailResult("验证类型错误！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		String phone = qcbAdmin.getMobile();
		
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
				code.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
				code.setCreator(qcbAdmin.getUuid());
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(type);
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
	 * @return
	 */
	@RequestMapping(value = "/sendCodeById", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "用户编号", required = true)
	@ResponseBody
	public Result sendCodeById(String uuid){
		
		QcbAdmin investor = this.qcbAdminService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		
		String phone = investor.getMobile();
		
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
				code.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
				code.setMobile(phone);
				code.setStatus(MobileCodeStatus.NORMAL);
				code.setType(MobileCodeTypes.FUNDCODE);
//				this.mobileCodeService.insert(code);
				this.mobileCodeService.insertMobileCode(code);
				return ResultManager.createSuccessResult("短信验证码发送成功");
			}
			
			return ResultManager.createFailResult("短信验证码发送失败！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("验证码发送失败！");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Base64Util.getBase64("18612033494"));
	}
}
