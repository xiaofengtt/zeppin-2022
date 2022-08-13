/**
 * 
 */
package cn.zeppin.action.teacher;
 
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherMobileCode;
import cn.zeppin.entity.TeacherReviewRecords;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.ISysUserService;
import cn.zeppin.service.ITeacherMobileCodeService;
import cn.zeppin.service.ITeacherReviewRecordsService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.utility.IdCardUtil;
import cn.zeppin.utility.SendEmail;
import cn.zeppin.utility.SendSms;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 *
 */ 
public class TeacherLoginAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8266142466333833634L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private String message;
	private String status;
	private String teacherId;
	
	private String userName;

	private ISysUserService iSysUserService;
	private IOrganizationService iOrganization;
	private ITeacherReviewRecordsService iTeacherReviewRecordsService;
	
	private ITeacherMobileCodeService iTeacherMobileCodeService;//手机验证码
	private ITeacherService iTeacherService;// 教师操作

	public TeacherLoginAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 登陆接口
	 * 
	 * @author Administrator
	 * @category 登陆接口可以用身份证、邮件和手机登陆
	 * @param username
	 * @param passwd
	 * @param code
	 */
	@SuppressWarnings("rawtypes")
	public void login() {
		
		String username = request.getParameter("username");
		String passwd = request.getParameter("passwd");
//		String roleId = "3";
		String rememberMe = "";
		if(request.getParameter("rememberMe") != null && !"".equals(request.getParameter("rememberMe"))){
			rememberMe = request.getParameter("rememberMe");
		}
		
		try {
			//首先验证教师是否存在
			//通过身份证号查询教师信息,用来验证教师是否存在过
			Object[] idcardPars = { username};
			List li = this.iTeacherService.getTeacherForLogin(idcardPars);
			if(li == null || li.isEmpty()){
				String message = "您还未注册过，请先注册";
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
//			Object[] pars = { username, passwd, roleId };
//			List li = this.iSysUserService.findByAll(pars);
//			if (li != null && li.size() > 0) {
			UserSession us = new UserSession();
			Object[] obj = (Object[]) li.get(0);
			// 添加到 session中
			
			us.setId(obj[0] != null ? Integer.valueOf(obj[0].toString()) : 0);
			us.setIdcard(obj[2] != null ? obj[2].toString() : null);
			us.setMobile(obj[3] != null ? obj[3].toString() : null);
			us.setEmail(obj[5] != null ? obj[5].toString() : null);
			us.setRole((short)3);
			us.setName(obj[1] != null ? obj[1].toString() : null);
			us.setOrganization(obj[11] != null ? Integer.parseInt(obj[11].toString()) : 0);
			us.setPassword(obj[4] != null ? obj[4].toString() : null);
			us.setCreateuser(obj[21] != null ? Integer.parseInt(obj[21].toString()) : 0);
			us.setLevel(1);
			us.setStatus(obj[23] != null ? Short.parseShort(obj[23].toString()) : 0);
			
			if(!obj[4].equals(passwd)){
				String message = "登陆失败,用户名密码错误";
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			if (us.getStatus() == 2) {
				String message = "登陆失败,用户名状态已停用";
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			} else if(us.getStatus() == -1) {
				String message = "您的信息还在审核中...";
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			} else if(us.getStatus() == 3) {
				String message = "您正处于转出状态，请等待管理员转入后再登录";
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			} else if(us.getStatus() == 4) {
				String message = "教师已退休";
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			} else if(us.getStatus() == 5) {
				String message = "教师已死亡";
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			} else if (us.getStatus() == 0) {
				List<TeacherReviewRecords> trrs = this.iTeacherReviewRecordsService.getTeacherReviewRecordsByTeacherId(us.getId());
				String reason = "";
				for(TeacherReviewRecords trr: trrs){
					if(trr.getType() == 0 && trr.getReason() != null && !"".equals(trr.getReason())){
						reason = trr.getReason();
					}
				}
				this.teacherId = us.getId().toString();
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"审核未通过，原因是"+reason+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			} else {
				String isFirstLogin = obj[26].toString();//obj[26]
				String message = "登录成功";
				StringBuilder sb = new StringBuilder();
				
				/*
				 * 判断是否是首次登陆，如果是，跳转到教师信息确认补充页，补充教师信息
				 */
				if("0".equals(isFirstLogin)){//是首次登陆
					sb.append("{");
					sb.append("\"Result\":\"FIRST\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					session.removeAttribute("teacherId");
					session.setAttribute("teacherId", us.getId());//首次登陆 完善信息后才能写入session
					return;
				}
				
				if(!rememberMe.equals("")){//自动登录功能 回传用户ID 与用户的用户名。密码加密后存入Cookie中，为下次自动登录做准备
					sb.append("{");
					sb.append("\"Result\":\"OKEY\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append(",");
					sb.append("\"TeacherID\":\""+us.getId()+"\"");
					sb.append(",");
					sb.append("\"IdCard\":\""+us.getIdcard()+"\"");
					sb.append(",");
					sb.append("\"Password\":\""+us.getPassword()+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					session.setAttribute("teachersession", us);
					return;
				}
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				session.setAttribute("teachersession", us);
				
				return;
			}

//			} else {
//				String message = "登陆失败,用户名密码错误";
//				StringBuilder sb = new StringBuilder();
//				sb.append("{");
//				sb.append("\"Result\":\"ERROR\"");
//				sb.append(",");
//				sb.append("\"Message\":\""+message+"\"");
//				sb.append("}");
//				Utlity.ResponseWrite(sb.toString(), "application/json", response);
//				return;
//			}

		} catch (Exception ex) {
			ex.printStackTrace();
			String message = "登陆失败，发生异常";
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

//		}
	}

	public void logout() {
		session.removeAttribute("teachersession");
		Cookie[] cookie = request.getCookies();
		if(cookie != null){
			for(Cookie coo:cookie){
				if(coo.getName().equals("loginStr")){
					coo = new Cookie("loginStr", null);
					coo.setMaxAge(0);
//					coo.setPath("/");
					response.addCookie(coo);
				}
			}
			
		}
		String message = "成功登出";
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\"OK\"");
		sb.append(",");
		sb.append("\"Message\":\""+message+"\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
		return;
	}
	
	/**
	 * 发送短信验证码接口
	 * 
	 */
	public void sendSms(){
		
		String pageStatus = "";
		String message = "";
		String phone = "";
		String idCard = "";
		Teacher teacher = null;
		StringBuilder sb = new StringBuilder();
		if(request.getParameter("phone")!=null){
			phone = request.getParameter("phone");
		}
		String authCode = request.getParameter("authCode");
		
		if(request.getParameter("idCard")!=null && !"".equals(request.getParameter("idCard"))){
		
			idCard = request.getParameter("idCard");
			if (IdCardUtil.IDCardValidate(idCard).equals("")){
				teacher = this.iTeacherService.getTeacherByIdCard(idCard);
			}else{
				pageStatus = "ERROR";
				message = "身份证信息不合法！";
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			
		}
		
		if (!Utlity.isMobileNO(phone.trim())) {
			pageStatus = "ERROR";
			message = "手机号码非法！";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String authCodes = session.getAttribute("authCode") == null ? "" : session.getAttribute("authCode").toString();
		if (!authCodes.equals(authCode)) {
			pageStatus = "ERROR";
			message = "图形验证码输入错误！";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		if(teacher == null){
			pageStatus = "ERROR";
			message = "用户不存在，请先注册！";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		if(!phone.equals(teacher.getMobile())){
			pageStatus = "ERROR";
			message = "输入的手机号，不是您注册时的手机号！";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		//获取6位数字验证码
		String captcha = Utlity.getCaptcha();
		TeacherMobileCode tmobileCode = new TeacherMobileCode();
		String uuid = Utlity.getUUID();
		tmobileCode.setCode(captcha+"");
		tmobileCode.setUuid(uuid);
		tmobileCode.setCreattime(new Timestamp(System.currentTimeMillis()));
		tmobileCode.setTeacher(teacher.getId());
		
		sb.append("{");
		
		try {
			//组成短信验证消息
//			String sms = "您本次操作的验证码为："+captcha+",本次验证码20分钟后失效！请不要轻易告诉他人。";
//			String code = SendSms.sendSms(sms, phone);
			String code = SendSms.sendSms(session, captcha, phone);
			String[] strs = code.split("_");
			String status = strs[0];
			String msg = code;
			if("0".equals(status)){
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"成功\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				tmobileCode.setStatus(Short.parseShort(status));
				tmobileCode.setMsg(msg);
				this.iTeacherMobileCodeService.add(tmobileCode);
				session.removeAttribute("code");
				session.setAttribute("code", uuid);
			}else{
				sb.append("\"Result\":\"ERROR\"");
				sb.append(",");
				sb.append("\"Message\":\"失败\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				tmobileCode.setStatus(Short.parseShort(status));
				tmobileCode.setMsg(msg);
				this.iTeacherMobileCodeService.add(tmobileCode);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			sb.append("\"Result\":\"ERROR\"");
			sb.append(",");
			sb.append("\"Message\":\"发送失败\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	
	/**
	 * 找回密码
	 * 其实就是重置新密码
	 * 两种验证方法：一种是按照手机号 找回
	 * 			一种是按照注册的邮箱找回
	 * 验证通过 跳转到修改密码页面
	 * 
	 */
	public void getPassword(){
//		session.removeAttribute("code");
//		session.setAttribute("code", "27a8c5724e804aab88ec2eaaf882b247");
		String pageStatus = "";
		StringBuilder sb = new StringBuilder();
		//校验验证码
		String code = "";
		if(request.getParameter("code")!=null && !"".equals(request.getParameter("code"))){
			code = request.getParameter("code");
		}
		String uuid = (String)session.getAttribute("code");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		TeacherMobileCode tmc = null;
		if(uuid != null){
			tmc = this.iTeacherMobileCodeService.getRecordByUuid(uuid);
			if(tmc != null){
				int seconds = (int)Math.ceil((time.getTime()-tmc.getCreattime().getTime())/(60*1000));
				if(!tmc.getCode().equals(code)){
					pageStatus = "ERROR";
					message = "验证码不正确，请重新输入！";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}else if(seconds>20){
					pageStatus = "ERROR";
					message = "验证码超时，请重新申请验证码！";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
			}
			session.setAttribute("userID", tmc.getTeacher());
		}else{
			pageStatus = "ERROR";
			message = "请先获取手机验证码！";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		pageStatus = "OK";
		message = "验证成功";
		sb.append("{");
		sb.append("\"Result\":\""+pageStatus+"\"");
		sb.append(",");
		sb.append("\"Message\":\""+message+"\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	/**
	 * 根据邮箱找回密码
	 * @throws Exception 
	 */
	public void getPasswordByEmail() throws Exception {
		
		StringBuilder sb = new StringBuilder();
		String pageStatus = "";
		String messages = "";
		String mailTo = "";
		if(request.getParameter("email")!= null && !"".equals(request.getParameter("email"))){
			mailTo = URLDecoder.decode(request.getParameter("email").trim(),"UTF-8");
		}
		
		if (!Utlity.isEmail(mailTo)) {
			pageStatus = "ERROR";
			message = "邮箱地址不正确";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+message+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		} 
		
		List<Teacher> lstTeacher = this.iTeacherService.getTeacherByEmail(mailTo);
		if(lstTeacher.size()<=0){
			pageStatus = "ERROR";
			messages = "用户不存在，请先注册";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+messages+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		String uuid = Utlity.getUUID();
		
		Teacher teacher = lstTeacher.get(0);
		
		StringBuilder content = new StringBuilder();
		content.append("<h2>请点击下面的链接修改密码，链接只能使用一次，请尽快完成密码重置！</h2>");
		
		content.append("<a target='_blank' style='font-size:16px;' href='http://jspxgl.zeppin.cn/teacher/emailLinkVerify.html?");
		content.append("checkId="+uuid+"&userID="+teacher.getId()+"'>");
		content.append("http://jspxgl.zeppin.cn/teacher/emailLinkVerify.html?");
		content.append("checkId="+uuid+"&userID="+teacher.getId()+"</a>");
		
		String status = SendEmail.sendEmail(content.toString(),mailTo);//发送邮件
		
		if("OK".equals(status)){//发送成功
			session.removeAttribute("uid");
			session.setAttribute("uid", uuid);
			this.getEmailURL(mailTo);
			pageStatus = "OK";
			messages = "成功";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+messages+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}else{
			pageStatus = "ERROR";
			messages = "发送失败";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+messages+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
	}
	
	/**
	 * 根据邮箱地址，获取邮箱登陆地址
	 * @param mailTo
	 * @return
	 */
	public String getEmailURL(String mailTo){
		String loginURL = "";
		if(mailTo != null && !"".equals(mailTo)){
			String[] mail = mailTo.split("@");
			
			String domain = mail[1].substring(0, mail[1].indexOf("."));
			 
			switch (domain) {
			case "qq":
				loginURL = "http://mail.qq.com";
				break;
			case "QQ":
				loginURL = "http://mail.qq.com";
				break;
			case "zeppin":
				loginURL = "http://exmail.qq.com/login";
				break;
			case "126":
				loginURL = "http://mail.126.com";
				break;
			case "163":
				loginURL = "http://mail.163.com";
				break;

			default:
				break;
			}
			for(String str: mail){
				System.out.println(str);
			}
		}
		
		
		return loginURL;
	}
	
	/**
	 * 校验邮箱链接
	 * @throws Exception 
	 */
	public void checkEmail() throws Exception{
		
		String pageStatus = "";
		String messages = "";
		StringBuilder sb = new StringBuilder();
		
		String userID = "0";
		if(request.getParameter("userID")!=null && !"".equals(request.getParameter("userID"))){
			userID = request.getParameter("userID");
		}
		Integer teacherId = Integer.parseInt(userID);
		String checkId = "";
		if(request.getParameter("checkId")!=null && !"".equals(request.getParameter("checkId"))){
			checkId = request.getParameter("checkId");
		}
		String uuid = (String)session.getAttribute("uid");
		if(!"".equals(checkId) && uuid != null && !"".equals(uuid)){
			if(checkId.equals(uuid)){
				session.setAttribute("userID", teacherId);
				pageStatus = "OK";
				messages = "成功";
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+messages+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else{
				pageStatus = "ERROR";
				messages = "验证失败";
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+messages+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
		}else{
			pageStatus = "ERROR";
			messages = "超时";
			sb.append("{");
			sb.append("\"Result\":\""+pageStatus+"\"");
			sb.append(",");
			sb.append("\"Message\":\""+messages+"\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
		
		
	}

	/**
	 * 直接修改密码
	 */
	public void changePassword(){
		String pageStatus = "";
		String message = "";
		
		String password = request.getParameter("password");
		String pwd = request.getParameter("pwd");
		StringBuilder sb = new StringBuilder();
		if(pwd!=null && password!=null && !pwd.equals("") && !password.equals("")){
			if(!pwd.equals(password)){
				pageStatus = "ERROR";
				message = "两次输入的密码不一致！";
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
				return;
			}
			Integer teacherID = (Integer)session.getAttribute("userID");
			try {
				Teacher teacher = null;
				if(teacherID != null && !teacherID.equals("")){
					teacher = this.iTeacherService.get(teacherID);
				}else{
					pageStatus = "ERROR";
					message = "修改失败！";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
				if(teacher != null){
					teacher.setPassword(password);
					String updater = 3+"_"+teacherID;
					teacher.setUpdater(updater);
					this.iTeacherService.update(teacher);
					pageStatus = "OK";
					message = "密码修改成功，请重新登陆！";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				}else{
					pageStatus = "ERROR";
					message = "修改失败！";
					sb.append("{");
					sb.append("\"Result\":\""+pageStatus+"\"");
					sb.append(",");
					sb.append("\"Message\":\""+message+"\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				pageStatus = "ERROR";
				message = "过程异常，修改失败！";
				sb.append("{");
				sb.append("\"Result\":\""+pageStatus+"\"");
				sb.append(",");
				sb.append("\"Message\":\""+message+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
			
		}
	}

	/**
	 * 检验当前是否已登录
	 */
	public void checkLogin(){
		UserSession us = (UserSession)session.getAttribute("teachersession");
		String message = "";
		String status = "";
		if(us != null){
			message = "已登录";
			status = "OK";
		}else{
			message = "未登录";
			status = "FAILED";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"Result\":\""+status+"\"");
		sb.append(",");
		sb.append("\"Message\":\""+message+"\"");
		sb.append("}");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ISysUserService getiSysUserService() {
		return iSysUserService;
	}

	public void setiSysUserService(ISysUserService iSysUserService) {
		this.iSysUserService = iSysUserService;
	}

	public IOrganizationService getiOrganization() {
		return iOrganization;
	}

	public void setiOrganization(IOrganizationService iOrganization) {
		this.iOrganization = iOrganization;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ITeacherReviewRecordsService getiTeacherReviewRecordsService() {
		return iTeacherReviewRecordsService;
	}

	public void setiTeacherReviewRecordsService(
			ITeacherReviewRecordsService iTeacherReviewRecordsService) {
		this.iTeacherReviewRecordsService = iTeacherReviewRecordsService;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public ITeacherMobileCodeService getiTeacherMobileCodeService() {
		return iTeacherMobileCodeService;
	}

	public void setiTeacherMobileCodeService(
			ITeacherMobileCodeService iTeacherMobileCodeService) {
		this.iTeacherMobileCodeService = iTeacherMobileCodeService;
	}

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

}
