package cn.zeppin.action.sms;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.entity.MobileCode;
import cn.zeppin.service.api.IInvigilationTeacherService;
import cn.zeppin.service.api.IMobileCodeService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Java2D;
import cn.zeppin.utility.SendSms;
import cn.zeppin.utility.Utlity;

public class SendSmsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4688622107756085930L;

	private IInvigilationTeacherService invigilationTeacherService;
	private IMobileCodeService mobileCodeService;

	public IMobileCodeService getMobileCodeService() {
		return mobileCodeService;
	}

	public void setMobileCodeService(IMobileCodeService mobileCodeService) {
		this.mobileCodeService = mobileCodeService;
	}
	
	public IInvigilationTeacherService getInvigilationTeacherService() {
		return invigilationTeacherService;
	}

	public void setInvigilationTeacherService(IInvigilationTeacherService invigilationTeacherService) {
		this.invigilationTeacherService = invigilationTeacherService;
	}

	/**
	 * @category 生成验证码
	 */
	public void AuthImg() {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("image/jpeg");
			response.addHeader("expires", "0");

			char[] random = {'A','B','C','D','E','F','G',
							'H','I','J','K','L','M','N',
							'O','P','Q','R','S','T',
							'U','V','W','X','Y','Z',
							'1','2','3','4','5','6','7','8','9','0'};
			
			String authCode = RandomStringUtils.random(5,random);
			OutputStream os = response.getOutputStream();

			// 指定图形验证码图片的大小
			BufferedImage image = Java2D.drawNewAuthCodeImg(authCode);

			// 将验证码保存到seesion中
			session.setAttribute("authcode", authCode);

			ImageIO.write(image, "jpeg", os);
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	/**
	 * 发送验证码接口 接受移动端发送过来的手机号
	 */
	@ActionParam(key = "uid", type = ValueType.NUMBER)
	@ActionParam(key = "mobile", type = ValueType.STRING, emptyable = false, nullable = false)
	@ActionParam(key = "authcode", type = ValueType.STRING, emptyable = false, nullable = false)
	@ActionParam(key = "check", type = ValueType.NUMBER, emptyable = false, nullable = false)
	public void SendSms() {
		ActionResult result = new ActionResult();

		String split = request.getParameter("split");
		split = split == null ? "." : split;

		int uid = this.getIntValue(request.getParameter("uid"));
		String mobile = this.getStrValue(request.getParameter("mobile"));
		String authCode = this.getStrValue(request.getParameter("authcode"));
		int check = this.getIntValue(request.getParameter("check"));
		String sessionCode = session.getAttribute("authcode") == null ? "" : session.getAttribute("authcode").toString();
		if (mobile != null && !"".equals(mobile)) {

			if (!Utlity.isMobileNO(mobile)) {
				result.init(FAIL_STATUS, "请输入正确的手机号码", null);
				Utlity.ResponseWrite(result, dataType, response);
				return;
			}
			
			//20191112新增图形验证码校验
			if (authCode == null || authCode.equals("")) {
				result.init(FAIL_STATUS, "请输入图形验证码！", null);
				Utlity.ResponseWrite(result, dataType, response);
				return;
			}
			if (sessionCode == null || !authCode.toLowerCase().equals(sessionCode.toLowerCase())) {
				result.init(FAIL_STATUS, "图形验证码输入不正确！", null);
				Utlity.ResponseWrite(result, dataType, response);
				return;
			}
			if (check == 1) {
				Map<String, Object> searchParams = new HashMap<String, Object>();
				searchParams.put("mobile", mobile);
				Integer count = this.invigilationTeacherService.searchInvigilationTeacherCount(searchParams);
				if (count > 0) {
					result.init(FAIL_STATUS, "该手机号已注册", null);
					Utlity.ResponseWrite(result, dataType, response);
					return;
				}
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", mobile);
			params.put("starttime", Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis() - 60000)));
			Integer count = this.mobileCodeService.getMobileCodeCount(params);
			if(count > 1){
				result.init(FAIL_STATUS, "验证码发送过于频繁，请稍后再发！", null);
				Utlity.ResponseWrite(result, dataType, response);
				return;
			}
			
			params.clear();
			params.put("mobile", mobile);
			params.put("status", Dictionary.MOBILECODE_STATUS_VALID);
			List<MobileCode> lstMobileCode = this.mobileCodeService.getMobileCodeByParams(params);

			// 如果之前存在验证码，设置验证码失效
			if (lstMobileCode != null && lstMobileCode.size() > 0) {
				for (MobileCode mobileCode : lstMobileCode) {
					this.mobileCodeService.deleteMobileCode(mobileCode);
				}
			}

			// 获取6位数字验证码
			String captcha = Utlity.getCaptcha();

			String sms = "您的验证码为：" + captcha + ",验证码将在20分钟后失效!";
			try {
				String code = SendSms.sendSms(sms, mobile);

				if ("100".equals(code)) {// 发送成功

					String uuid = Utlity.getUUID();
					Timestamp time = new Timestamp(System.currentTimeMillis());
					MobileCode mc = new MobileCode();
					mc.setCode(captcha);
					mc.setUuid(uuid);
					mc.setMobile(mobile);
					mc.setCreatetime(time);
					mc.setStatus(Dictionary.MOBILECODE_STATUS_VALID);
					mc.setSsoUser(uid);

					this.mobileCodeService.addMobileCode(mc);// 保存验证码

					session.removeAttribute("code");
					session.setAttribute("code", uuid);
					result.init(SUCCESS_STATUS, "短信发送成功", null);

				} else {// 发送失败
					result.init(FAIL_STATUS, "短信发送失败", null);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				result.init(FAIL_STATUS, "发送验证码异常", null);
			}

		} else {
			result.init(FAIL_STATUS, "手机号码不能为空", null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}

}
