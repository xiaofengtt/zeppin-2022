package cn.zeppin.action.sms;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.MobileCode;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.service.api.IMobileCodeService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.SendSms;
import cn.zeppin.utility.Utlity;

public class SendSmsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4688622107756085930L;

	private ISsoUserService ssoUserService;
	private IMobileCodeService mobileCodeService;
	
	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public IMobileCodeService getMobileCodeService() {
		return mobileCodeService;
	}

	public void setMobileCodeService(IMobileCodeService mobileCodeService) {
		this.mobileCodeService = mobileCodeService;
	}

	/**
	 * 发送验证码接口
	 * 接受移动端发送过来的手机号
	 */
	@ActionParam(key = "user.id", type = ValueType.NUMBER)
	@ActionParam(key = "mobile", type = ValueType.STRING , emptyable = false, nullable = false)
	@ActionParam(key = "check", type = ValueType.NUMBER , emptyable = false, nullable = false)
	public void sendSms(){
		ActionResult result = new ActionResult();
		
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		
		SsoUser currentUser = (SsoUser)session.getAttribute("user");
		if(currentUser == null){
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);
		}
		
		String mobile = this.getStrValue(request.getParameter("mobile"));
		int check = this.getIntValue(request.getParameter("check"));
		
		if(mobile != null){
			
			if(!Utlity.isMobileNO(mobile)){
				result.init(FAIL_STATUS, "手机号码非法", null);
				Utlity.ResponseWrite(result, dataType, response);
				return;
			}
			
			if(check == 1){
				Map<String , Object> searchParams = new HashMap<String, Object>();
				searchParams.put("phone", mobile);
				Integer count = this.getSsoUserService().getSsoUserCountByMap(searchParams);
				if(count > 0){
					result.init(FAIL_STATUS, "该手机号已注册", null);
					Utlity.ResponseWrite(result, dataType, response);
					return;
				}
			}
			
			Map<String , Object> params = new HashMap<String, Object>();
			params.put("mobile", mobile);
			params.put("status", Dictionary.MOBILECODE_STATUS_VALID);
			List<MobileCode> lstMobileCode = this.mobileCodeService.getMobileCodeByParams(params);
			
			//如果之前存在验证码，设置验证码失效
			if(lstMobileCode != null && lstMobileCode.size()>0){
				for(MobileCode mobileCode: lstMobileCode){
					this.mobileCodeService.deleteMobileCode(mobileCode);
				}
			}
			
			
			//获取6位数字验证码
			String captcha = Utlity.getCaptcha();
			
			String sms = "您的验证码为："+captcha+",验证码将在20分钟后失效!";
			try {
				String code = SendSms.sendSms(sms, mobile);
				
				if("100".equals(code)){//发送成功
					
					String uuid = Utlity.getUUID();
					Timestamp time = new Timestamp(System.currentTimeMillis());
					MobileCode mc = new MobileCode();
					mc.setCode(captcha);
					mc.setUuid(uuid);
					mc.setMobile(mobile);
					mc.setCreatetime(time);
					mc.setStatus(Dictionary.MOBILECODE_STATUS_VALID);
					if(currentUser != null){
						mc.setSsoUser(currentUser.getId());
					}
					
					this.mobileCodeService.addMobileCode(mc);//保存验证码
					
					session.removeAttribute("code");
					session.setAttribute("code", uuid);
					result.init(SUCCESS_STATUS, "短信发送成功", null);
					
				}else{//发送失败
					result.init(FAIL_STATUS, "短信发送失败", null);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				result.init(FAIL_STATUS, "发送验证码异常", null);
			}
			
		}else{
			result.init(FAIL_STATUS, "手机号码不能为空", null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
}
