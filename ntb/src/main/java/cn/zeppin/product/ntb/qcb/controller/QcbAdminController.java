package cn.zeppin.product.ntb.qcb.controller;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zeppin.product.ntb.backadmin.security.CustomizedToken;
import cn.zeppin.product.ntb.backadmin.security.LoginType;
import cn.zeppin.product.ntb.backadmin.security.RoleSign;
import cn.zeppin.product.ntb.backadmin.security.SecurityByQcbRealm;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbAdmin.QcbAdminStatus;
import cn.zeppin.product.ntb.core.entity.QcbAdminWechatAuth;
import cn.zeppin.product.ntb.core.entity.QcbAdminWechatAuth.QcbAdminWechatAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbAdminWechatAuth.QcbAdminWechatAuthType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountFinanceStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin.QcbCompanyAdminStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdminMenu;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.QcbMenu;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminWechatAuthService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminMenuService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbMenuService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAccountLessVO;
import cn.zeppin.product.ntb.qcb.vo.QcbMenuLessVO;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.weixin.ConfigUtil;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 后台用户接口
 * 
 * @author Clark.R
 * @since 2016年3月29日 下午3:54:00
 **/
@Controller
@RequestMapping(value = "/qcb/admin")
public class QcbAdminController extends BaseController{
	
    
	@Autowired
	private IQcbAdminService qcbAdminService; 
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbCompanyPayrollService qcbCompanyPayrollService;
	
	@Autowired
	private IQcbMenuService qcbMenuService;
	
	@Autowired
	private IQcbCompanyAdminService qcbCompanyAdminService;
	
	@Autowired
	private IQcbCompanyAdminMenuService qcbCompanyAdminMenuService;
	
	@Autowired
	private IQcbAdminWechatAuthService qcbAdminWechatAuthService; 
	
	@Autowired
    private Producer captchaProducer;  
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IBkAreaService bkAreaService;

	
	/**
	 * 用户登录
	 * 验证登录管理员信息
	 * 验证登录管理员绑定企业信息
	 * 验证登录管理员绑定企业操作权限
	 * 
	 * @param loginname base64编码
	 * @param password base64编码
	 * @param kaptcha
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "loginname", message="用户名", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ActionParam(key = "kaptcha", message="验证码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result login(String loginname, String password, String kaptcha) {
		loginname = Base64Util.getFromBase64(loginname);
		password = Base64Util.getFromBase64(password);
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		QcbAdmin bkOperator = null;
		try {
			
			if(session.getAttribute(Constants.KAPTCHA_SESSION_KEY) == null){
				return ResultManager.createErrorResult("001", "验证码超时！");
			}
			
			if("".equals((String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY))){
				return ResultManager.createErrorResult("001", "验证码超时！");
			}
			
			// 验证码验证
			String sessionAuthCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 
			
			if (!kaptcha.equalsIgnoreCase(sessionAuthCode)) {
				return ResultManager.createErrorResult("001", "验证码输入错误！");
			}
			
	        // 通过数据库进行验证
	        bkOperator =  qcbAdminService.getByMobile(loginname);
	        
	        if (bkOperator == null) {
	            throw new UnknownAccountException();
	        }  
	        
	        else if (QcbAdminStatus.DISABLE.equals(bkOperator.getStatus())) {  
	            throw new AuthenticationException("该账户已停用！");  
	        }
			
			// 身份验证
	        CustomizedToken usernamePasswordToken = new CustomizedToken(loginname, password, LoginType.QCB.toString());
			subject.login(usernamePasswordToken);
			
		} catch (UnknownAccountException e) {  
			//没有此账户
			return ResultManager.createErrorResult("001", "没有此账户信息！");
		} catch (LockedAccountException e) {
			//账户已锁定
			return ResultManager.createErrorResult("002", "密码输入错误超过次数限制，账户已锁定！");
		} catch (IncorrectCredentialsException e) {  
			//用户名,密码错误  
			return ResultManager.createErrorResult("003", e.getMessage());
        } catch (ExcessiveAttemptsException e) {
            //登录失败多次，账户锁定10分钟
//			if (bkOperator.getStatus().equals(BkOperatorStatus.NORMAL)) {
//				bkOperator.setStatus(BkOperatorStatus.LOCKED);
//	        	bkOperator.setLockedtime(new Timestamp(System.currentTimeMillis()));
//	        	qcbAdminService.update(bkOperator);
//			}
			return ResultManager.createErrorResult("004", "密码输入错误超过次数限制，账户已锁定！");
		}
		catch (AuthenticationException e) {
			// 身份验证失败
			e.printStackTrace();
			return ResultManager.createErrorResult("005", e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//实际实现的时候只将用户的基本信息返回，不要返回用户带密码、联系方式等的全信息对象
		AdminVO vo = new AdminVO(bkOperator);
		
		/*
		 * 验证用户绑定的企业是否存在
		 * */
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbAdmin", bkOperator.getUuid());
		inputParams.put("status", QcbCompanyAdminStatus.NORMAL);
		List<Entity> list = this.qcbCompanyAdminService.getListForPage(inputParams, -1, -1, null, QcbCompanyAdmin.class);
		if(list != null && list.size() > 0){
			QcbCompanyAdmin qca = null;
			QcbCompanyAccount company = null;
			QcbCompanyAccount companyCurrent = null;
			Map<String, QcbMenuLessVO> mapMenuVO = new HashMap<String, QcbMenuLessVO>();
			Map<String, QcbMenuLessVO> mapMenuVOAll = new HashMap<String, QcbMenuLessVO>();
			boolean flag = false;
			List<QcbCompanyAccountLessVO> listCompanyAccount = new ArrayList<QcbCompanyAccountLessVO>();
			for(Entity entity : list){
				qca = (QcbCompanyAdmin) entity;
				company = this.qcbCompanyAccountService.get(qca.getQcbCompany());
				if(company == null){
//					return ResultManager.createFailResult("该账户注册企业不存在！");
					continue;
				}
				if(QcbCompanyAccountStatus.DELETED.equals(company.getStatus())){
//					return ResultManager.createFailResult("该账户关联企业已被注销！");
					continue;
				}
				QcbCompanyAccountLessVO cvo = new QcbCompanyAccountLessVO(company);
				listCompanyAccount.add(cvo);
				if(company.getAccreditMobile().equals(bkOperator.getMobile())){//是主账户
					flag = true;
					companyCurrent = company;
				}
				/*
				 * 验证权限 如果权限为空 那么取下一个
				 */
				inputParams.clear();
				inputParams.put("qcbAdmin", bkOperator.getUuid());
				inputParams.put("qcbCompany", vo.getQcbCompany());
				
				List<Entity> listMenu = this.qcbCompanyAdminMenuService.getListForPage(inputParams, -1, -1, null, QcbCompanyAdminMenu.class);
				if(listMenu != null && listMenu.size() > 0){
//					List<QcbMenu> listMenuInfo = new ArrayList<QcbMenu>();
					Map<String, QcbMenuLessVO> mapMenu = new HashMap<String, QcbMenuLessVO>();
					for(Entity e : listMenu){
						QcbCompanyAdminMenu qcam = (QcbCompanyAdminMenu)e;
						QcbMenu qm = this.qcbMenuService.get(qcam.getQcbMenu());
						if(qm != null){
//							listMenuInfo.add(qm);
							QcbMenuLessVO qmvo = new QcbMenuLessVO(qm);
							qmvo.setFlagOwn(true);
							mapMenu.put(qm.getUuid(), qmvo);
						}
					}
//					if(listMenuInfo.size() == 0){
//						continue;
//					}
					for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//遍历出1级菜单
						QcbMenuLessVO qmvo = entry.getValue();
						if(qmvo.getLevel() == 1){
							mapMenuVOAll.put(qmvo.getUuid(), qmvo);
							if(flag){
								mapMenuVO.put(qmvo.getUuid(), qmvo);
							}
						}
					}
					for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//遍历2级菜单并加入树
						QcbMenuLessVO qmvo = entry.getValue();
						if(qmvo.getLevel() == 2){
							QcbMenuLessVO pvo = mapMenuVOAll.get(qmvo.getPid());
							Map<String, QcbMenuLessVO> childMenu = pvo.getChildMenu();
							childMenu.put(qmvo.getUuid(), qmvo);
							pvo.setChildMenu(childMenu);
							mapMenuVOAll.put(pvo.getUuid(), pvo);
							if(flag){
								mapMenuVO.put(pvo.getUuid(), pvo);
							}
						}
					}
				}
			}
			if(flag){//如果是管理员
				vo.setListMenu(mapMenuVO);
				vo.setQcbCompany(companyCurrent.getUuid());
				vo.setQcbCompanyName(companyCurrent.getName());
				vo.setQcbCompanyStatus(companyCurrent.getStatus());
			} else {
				vo.setListMenu(mapMenuVOAll);
				vo.setQcbCompany(company.getUuid());
				vo.setQcbCompanyName(company.getName());
				vo.setQcbCompanyStatus(company.getStatus());
			}
			vo.setListCompanyAccount(listCompanyAccount);
		} else {
			return ResultManager.createFailResult("该账户已被关联企业注销！");
		}
		
		if(Utlity.checkStringNull(vo.getQcbCompany())){
			return ResultManager.createFailResult("该账户已被关联企业注销！");
		}
		
		if(vo.getWechatFlag()){
			session.setAttribute("currentQcbOperatorTmp", vo);
		}else{
			session.setAttribute("currentQcbOperator", vo);
		}
		return ResultManager.createDataResult(vo.getWechatFlag());
	}

	/**
	 * 用户登出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		// 登出操作
		Subject subject = SecurityUtils.getSubject();
		Session shiroSession = subject.getSession();
		shiroSession.removeAttribute("currentQcbOperator");
		subject.logout();
		return "redirect:../../../qcb/login.jsp";
	}
	
	/**
	 * 校验短信验证码--找回密码
	 * @param mobile base64编码
	 * @param code base64编码
	 * @return
	 */
	@RequestMapping(value = "/rePwdcheck", method = RequestMethod.POST)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING, required = true)
	@ActionParam(key = "code", message="短信验证码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result rePwdcheck(String mobile, String code) {
		
		mobile = Base64Util.getFromBase64(mobile);
		code = Base64Util.getFromBase64(code);
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		// 通过数据库进行验证
		QcbAdmin bkOperator =  qcbAdminService.getByMobile(mobile);
        
        if (bkOperator == null) {
        	return ResultManager.createFailResult("此账户不存在！");
        }  
        
        else if (QcbAdminStatus.DISABLE.equals(bkOperator.getStatus())) {  
        	return ResultManager.createFailResult("该账户已停用！");  
        }
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.RESETPWD);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(mobile)){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		session.setAttribute("resetQcbPwd", bkOperator.getUuid());
		this.mobileCodeService.update(mc);
		return ResultManager.createSuccessResult("短信验证通过！");
	}
	
	/**
	 * 修改密码
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/rePwd", method = RequestMethod.POST)
	@ActionParam(key = "passwordNew", message="新密码", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNewCheck", message="确认密码", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordLevel", message="安全等级", type = DataType.NUMBER)
	@ResponseBody
	public Result rePwd(String passwordNew, String passwordNewCheck, Integer passwordLevel) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String qcb = (String)session.getAttribute("resetQcbPwd");
		if(Utlity.checkStringNull(qcb)){
			return ResultManager.createFailResult("操作超时！");
		}
		//获取管理员信息60b474258188bbf0b48249e04bc8f4ef
		QcbAdmin operator = qcbAdminService.get(qcb);
		if(operator != null){
			//修改密码
			if(!passwordNew.equals(passwordNewCheck)){
				return ResultManager.createFailResult("确认密码与新密码不一致，请修改！");
			}
			String encryptPassword = SecurityByQcbRealm.encrypt(passwordNew, ByteSource.Util.bytes(operator.getUuid()));
			operator.setPassword(encryptPassword);
			operator.setPasswordLevel(passwordLevel);
			operator = qcbAdminService.update(operator);
			
			//删除缓存
			session.removeAttribute("resetQcbPwd");
			
			return ResultManager.createSuccessResult("重置密码成功，请重新登录");
		}else{
			return ResultManager.createFailResult("账户数据不存在！");
		}
	}
	
	/**
	 * 校验短信验证码
	 * @param mobile base64编码
	 * @param code base64编码
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING, required = true)
	@ActionParam(key = "code", message="短信验证码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String mobile, String code) {
		
		mobile = Base64Util.getFromBase64(mobile);
		code = Base64Util.getFromBase64(code);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.REGISTER);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(mobile)){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
//		if(!MobileCodeTypes.CODE.equals(mc.getType())){
//			return ResultManager.createFailResult("短信验证码输入错误！");
//		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeService.update(mc);
		return ResultManager.createSuccessResult("短信验证通过！");
	}

	/**
	 * 企业注册
	 * 如果判断账号已经注册过，是否需要提示“已在其他企业开通账号，是否继续？”
	 * @param mobile base64编码
	 * @param company urlecode
	 * @param area
	 * @param password base64编码
	 * @param contacts urlecode
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="企业名称", type = DataType.STRING, required = true)
	@ActionParam(key = "area", message="所在地区", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="管理密码", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordLevel", message="密码安全等级", type = DataType.NUMBER)
	@ActionParam(key = "contacts", message="联系人姓名", type = DataType.STRING, required = true)
	@ResponseBody
	public Result register(String mobile, String company, String area, String password, Integer passwordLevel, String contacts) {
		mobile = Base64Util.getFromBase64(mobile);
		password = Base64Util.getFromBase64(password);
		try {
			company = URLDecoder.decode(company, "UTF-8");
			contacts = URLDecoder.decode(contacts, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("操作异常");
		}
		
		//判断企业名称是否重复
		if(this.qcbCompanyAccountService.isExistQcbCompanyAccountByName(company, null)){
			return ResultManager.createFailResult("该企业已经注册过！");
		}
		
		//判断地区是否存在
		BkArea bkArea = this.bkAreaService.get(area);
		if(bkArea == null){
			return ResultManager.createFailResult("所在地区信息错误！");
		}
		
		//添加一个管理员
		QcbAdmin qa = new QcbAdmin();
		//再次判断是否已注册过
		boolean flag = true;
		if(this.qcbAdminService.isExistQcbAdminByMobile(mobile, null)){//如果注册过直接添加企业与管理员关系
//			String encryptPassword = SecurityByQcbRealm.encrypt(password, ByteSource.Util.bytes(qa.getUuid()));
//			qa.setPassword(encryptPassword);
			qa = this.qcbAdminService.getByMobile(mobile);
			flag = false;
//			return ResultManager.createFailResult("该手机号已经注册过！");
		} else {
			qa.setUuid(UUID.randomUUID().toString());
			qa.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qa.setMobile(mobile);
			qa.setName(contacts);
			
			String encryptPassword = SecurityByQcbRealm.encrypt(password, ByteSource.Util.bytes(qa.getUuid()));
			qa.setPassword(encryptPassword);
			qa.setPasswordLevel(passwordLevel);
			qa.setStatus(QcbAdminStatus.NORMAL);
			qa.setWechatFlag(false);
		}
		
		//添加一个企业
		QcbCompanyAccount qca = new QcbCompanyAccount();
		qca.setUuid(UUID.randomUUID().toString());
		
		//企业ID算法
		Map<String, String> searchParams = new HashMap<String, String>();
		Integer count = this.qcbCompanyAccountService.getCount(searchParams);
		String merchantId = Utlity.getMerchantId(count);
		if(this.qcbCompanyAccountService.isExistQcbCompanyAccountByMerchantId(merchantId, null)){
			merchantId = Utlity.getMerchantId(count);
		}
		qca.setMerchantId(merchantId);//需企业ID算法
		qca.setName(company);
		qca.setBkArea(area);
		qca.setContacts(contacts);
		qca.setAccredit(qa.getName());
		qca.setAccreditMobile(qa.getMobile());
		qca.setAccountBalance(BigDecimal.ZERO);
		qca.setAccountPay(BigDecimal.ZERO);
		qca.setTotalInvest(BigDecimal.ZERO);
		qca.setTotalReturn(BigDecimal.ZERO);
		qca.setFeeTicket(BigDecimal.ZERO);
		qca.setFinanceStatus(QcbCompanyAccountFinanceStatus.UNAUTH);
		qca.setStatus(QcbCompanyAccountStatus.UNAUTH);
		qca.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qca.setAuthStatus(QcbCompanyAccountAuthStatus.UNAUTH);
		
		//添加一个企业与管理员关系
		QcbCompanyAdmin qcAdmin = new QcbCompanyAdmin();
		qcAdmin.setUuid(UUID.randomUUID().toString());
		qcAdmin.setQcbAdmin(qa.getUuid());
		qcAdmin.setQcbCompany(qca.getUuid());
		qcAdmin.setRole("管理员");
		qcAdmin.setStatus(QcbCompanyAdminStatus.NORMAL);
		qcAdmin.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcAdmin.setCreator(qa.getUuid());
		
		//添加一组企业与管理员的关系权限
		List<QcbCompanyAdminMenu> listQcaMenu = new ArrayList<QcbCompanyAdminMenu>();
		Map<String, String> inputParams = new HashMap<String, String>();
		List<Entity> listMenu = this.qcbMenuService.getListForPage(inputParams, -1, -1, null, QcbMenu.class);
		if(listMenu != null && listMenu.size() > 0){
			for(Entity entity : listMenu){
				QcbMenu menu = (QcbMenu)entity;
				QcbCompanyAdminMenu qcam = new QcbCompanyAdminMenu();
				qcam.setUuid(UUID.randomUUID().toString());
				qcam.setCreator(qa.getUuid());
				qcam.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qcam.setQcbAdmin(qa.getUuid());
				qcam.setQcbCompany(qca.getUuid());
				qcam.setQcbCompanyAdmin(qcAdmin.getUuid());
				qcam.setQcbMenu(menu.getUuid());
				listQcaMenu.add(qcam);
			}
		}
		
		try {
			this.qcbAdminService.insertRegisterInfo(flag, qa, qca, qcAdmin, listQcaMenu);
			return ResultManager.createSuccessResult("注册成功！");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("注册信息异常");
		}
	}
	
	/**
	 * 基于角色 标识的权限控制案例
	 */
	@RequestMapping(value = "/admin")
	@ResponseBody
	@RequiresRoles(value = RoleSign.ADMIN)
	public String admin() {
		return "拥有admin角色,能访问";
	}  
	
    @RequestMapping("/kaptchaImage")  
    public ModelAndView captchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        response.setDateHeader("Expires", 0);  
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
        // return a jpeg  
        response.setContentType("image/jpeg");  
        // create the text for the image  
        String capText = captchaProducer.createText();  
        // store the text in the session  
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } 
        finally {  
            out.close();  
        }  
        return null;  
    }  
	
	/**
	 * 编辑管理员信息
	 * @param uuid
	 * @param name
	 * @param realname
	 * @param mobile
	 * @param email
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true, minLength = 6, maxLength = 22)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "mobile", message="手机号", type = DataType.PHONE, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "email", message="邮箱", type = DataType.EMAIL)
	@ResponseBody
	public Result edit(String uuid, String name, String realname, String mobile, String email) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		//获取管理员信息
		QcbAdmin operator = qcbAdminService.get(uuid);
		if(operator != null && uuid.equals(operator.getUuid())){
			
			//验证是否有重名的情况
			if (qcbAdminService.isExistQcbAdminByMobile(mobile, uuid)) {
				return ResultManager.createFailResult("用户名已存在！");
			}
			
//			//验证是否有重手机号的情况
//			if (qcbAdminService.isExistOperatorByMobile(mobile, uuid)) {
//				return ResultManager.createFailResult("手机号码已存在！");
//			}
			
			//修改属性值
//			operator.setEmail(email);
//			operator.setMobile(mobile);
//			operator.setName(name);
//			operator.setRealname(realname);
			
			operator = qcbAdminService.update(operator);
			session.setAttribute("currentOperator", operator);
			return ResultManager.createSuccessResult("修改个人信息成功");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 修改密码
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	@ActionParam(key = "password", message="原密码", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNew", message="新密码", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNewCheck", message="确认密码", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordLevel", message="安全等级", type = DataType.NUMBER)
	@ResponseBody
	public Result password(String password, String passwordNew, String passwordNewCheck, Integer passwordLevel) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		//获取管理员信息60b474258188bbf0b48249e04bc8f4ef
		QcbAdmin operator = qcbAdminService.get(qcbAdmin.getUuid());
		if(operator != null){
			
			//密码加密
			String encryptPassword = SecurityByQcbRealm.encrypt(password, ByteSource.Util.bytes(operator.getUuid()));
			if(!operator.getPassword().equals(encryptPassword)){
				return ResultManager.createFailResult("原密码输入错误！");
			}
			if(!passwordNew.equals(passwordNewCheck)){
				return ResultManager.createFailResult("确认密码与新密码不一致，请修改！");
			}
			encryptPassword = SecurityByQcbRealm.encrypt(passwordNew, ByteSource.Util.bytes(operator.getUuid()));
			operator.setPassword(encryptPassword);
			operator.setPasswordLevel(passwordLevel);
			operator = qcbAdminService.update(operator);
			
			//登出
			session.removeAttribute("currentQcbOperator");
			subject.logout();
			
			return ResultManager.createSuccessResult("重置密码成功，请重新登录");
		}else{
			return ResultManager.createFailResult("账户数据不存在！");
		}
	}
	
	/**
	 * 修改手机号
	 * @param code
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/changeMobile", method = RequestMethod.POST)
	@ActionParam(key = "code", message="短信验证码", type = DataType.STRING, required = true)
	@ActionParam(key = "mobile", message="新手机号", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeMobile(String code, String mobile) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(company);
		if(qca == null){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", qcbAdmin.getMobile());
		inputParams.put("status", MobileCodeStatus.NORMAL);
		inputParams.put("type", MobileCodeTypes.QCB_MOBILE_MODIFY);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(qcbAdmin.getMobile())){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
		if(Utlity.checkCodeTime(mc.getCreatetime().getTime(), System.currentTimeMillis())){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		QcbAdmin operator = qcbAdminService.get(qcbAdmin.getUuid());
		if(operator != null){
			
			if(this.qcbAdminService.isExistQcbAdminByMobile(mobile, operator.getUuid())){//
				return ResultManager.createFailResult("该手机号已经注册过！");
			}
			
			//判断是否是总管理员 如果是 不让删除
			QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
			if(ca == null){
				return ResultManager.createFailResult("企业信息错误！");
			}
			boolean flag = false;
			if(ca.getAccreditMobile().equals(operator.getMobile())){
				ca.setAccreditMobile(mobile);
				ca.setAccredit(operator.getName());
				flag = true;
			}
			try {
				operator.setMobile(mobile);
				this.qcbAdminService.update(flag, operator, ca);
				
				//登出
				session.removeAttribute("currentQcbOperator");
				subject.logout();
				
				return ResultManager.createSuccessResult("修改手机号成功，请用新手机号重新登录！");
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("操作异常！");
			}
			
		}
		return ResultManager.createFailResult("账户数据不存在！");
	}
	
	/**
	 * 切换企业
	 * @param company
	 * @return
	 */
	@RequestMapping(value = "/changeCompany", method = RequestMethod.GET)
	@ActionParam(key = "company", message="企业唯一编码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeCompany(String company) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String companyStr = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(companyStr)){
			return ResultManager.createFailResult("无权查看该企业管理员信息！");
		}
		
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(company);
		if(qca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		if(QcbCompanyAccountStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("企业信息不存在！");
		}
		
		/*
		 * 验证用户绑定的企业是否存在
		 * */
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbAdmin", qcbAdmin.getUuid());
		inputParams.put("qcbCompany", qca.getUuid());
		inputParams.put("status", QcbCompanyAdminStatus.NORMAL);
		List<Entity> list = this.qcbCompanyAdminService.getListForPage(inputParams, -1, -1, null, QcbCompanyAdmin.class);
		if(list != null && !list.isEmpty()){
			QcbCompanyAdmin qcAdmin = (QcbCompanyAdmin)list.get(0);
			
			/*
			 * 验证并获取权限 qcbCompanyAdmin
			 */
			inputParams.clear();
			inputParams.put("qcbCompanyAdmin", qcAdmin.getUuid());
			Map<String, QcbMenuLessVO> mapMenuVO = new HashMap<String, QcbMenuLessVO>();
			List<Entity> listMenu = this.qcbCompanyAdminMenuService.getListForPage(inputParams, -1, -1, null, QcbCompanyAdminMenu.class);
			if(listMenu != null && listMenu.size() > 0){
				Map<String, QcbMenuLessVO> mapMenu = new HashMap<String, QcbMenuLessVO>();
				for(Entity e : listMenu){
					QcbCompanyAdminMenu qcam = (QcbCompanyAdminMenu)e;
					QcbMenu qm = this.qcbMenuService.get(qcam.getQcbMenu());
					if(qm != null){
						QcbMenuLessVO qmvo = new QcbMenuLessVO(qm);
						qmvo.setFlagOwn(true);
						mapMenu.put(qm.getUuid(), qmvo);
					}
				}
				
				for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//遍历出1级菜单
					QcbMenuLessVO qmvo = entry.getValue();
					if(qmvo.getLevel() == 1){
						mapMenuVO.put(qmvo.getUuid(), qmvo);
					}
				}
				for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//遍历2级菜单并加入树
					QcbMenuLessVO qmvo = entry.getValue();
					if(qmvo.getLevel() == 2){
						QcbMenuLessVO pvo = mapMenuVO.get(qmvo.getPid());
						Map<String, QcbMenuLessVO> childMenu = pvo.getChildMenu();
						childMenu.put(qmvo.getUuid(), qmvo);
						pvo.setChildMenu(childMenu);
						mapMenuVO.put(pvo.getUuid(), pvo);
					}
				}
			}
			qcbAdmin.setListMenu(mapMenuVO);
			qcbAdmin.setQcbCompany(qca.getUuid());
			qcbAdmin.setQcbCompanyName(qca.getName());
		} else {
			return ResultManager.createFailResult("该账户已被关联企业注销！");
		}
		//重置session
		session.removeAttribute("currentQcbOperator");
		session.setAttribute("currentQcbOperator", qcbAdmin);
		
		return ResultManager.createDataResult(qcbAdmin);
	}
	
	/**
	 * 获取绑定微信Token
	 * @param type
	 * @param qcbCompanyPayroll
	 * @return
	 */
	@RequestMapping(value = "/bindWechatToken", method = RequestMethod.GET)
	@ActionParam(key = "type", message="token类型", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "qcbCompanyPayroll", message="薪资记录编号", type = DataType.STRING, maxLength = 36)
	@ResponseBody
	public Result bindWechatToken(String type, String qcbCompanyPayroll) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(!QcbAdminWechatAuthType.BIND.equals(type) && !QcbAdminWechatAuthType.PAYROLL.equals(type) 
				&& !QcbAdminWechatAuthType.REMOVE.equals(type) && !QcbAdminWechatAuthType.LOGIN.equals(type)){
			return ResultManager.createFailResult("token类型不正确！");
		}
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		long timestamp = System.currentTimeMillis();
		
		
		QcbAdminWechatAuth qawa = new QcbAdminWechatAuth();
		qawa.setUuid(UUID.randomUUID().toString());
		qawa.setQcbAdmin(qcbAdmin.getUuid());
		qawa.setType(type);
		qawa.setToken(UUID.randomUUID().toString().substring(0,8));
		qawa.setStatus(QcbAdminWechatAuthStatus.NORMAL);
		qawa.setCreatetime(new Timestamp(timestamp));
		
		if(QcbAdminWechatAuthType.PAYROLL.equals(type)){
			if(Utlity.checkStringNull(qcbCompanyPayroll)){
				return ResultManager.createFailResult("薪资记录编号不能为空！");
			}
			
			QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(qcbCompanyPayroll);
			if(qcp == null){
				return ResultManager.createFailResult("薪资记录不存在！");
			}
			
			qawa.setQcbCompanyPayroll(qcbCompanyPayroll);
		}
		
		String token = Base64Util.getBase64(qawa.getToken() + qawa.getUuid());
		
		this.qcbAdminWechatAuthService.insert(qawa);
		return ResultManager.createDataResult(token);
	}
	
	/**
	 * 微信鉴权
	 * @param token
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/bindWechatAuth")
	@ActionParam(key = "token", message="token", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "code", message="code", type = DataType.STRING, required = true, maxLength = 200)
	@ResponseBody
	public Result bindWechatAuth(String token, String code){
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		token = Base64Util.getFromBase64(token);
		String uuid = token.substring(8,token.length());
		
		QcbAdminWechatAuth qawa = this.qcbAdminWechatAuthService.get(uuid);
		if(qawa == null){
			return ResultManager.createFailResult("token有误！");
		}
		
		if(!qcbAdmin.getUuid().equals(qawa.getQcbAdmin())){
			return ResultManager.createFailResult("管理员信息不符！");
		}
		
		if(!QcbAdminWechatAuthStatus.NORMAL.equals(qawa.getStatus())){
			return ResultManager.createFailResult("鉴权信息状态不正确！");
		}
		
		if(!qawa.getToken().equals(token.substring(0,8))){
			qawa.setStatus(QcbAdminWechatAuthStatus.FAILED);
			this.qcbAdminWechatAuthService.update(qawa);
			return ResultManager.createFailResult("token有误！");
		}
		
		String openID = ConfigUtil.getOauthAccessOpenId(code,ConfigUtil.QCB_AUTH_APPID,ConfigUtil.QCB_AUTH_SECRECT);
		if (openID == null || "".equals(openID)) {
			qawa.setStatus(QcbAdminWechatAuthStatus.FAILED);
			this.qcbAdminWechatAuthService.update(qawa);
			return ResultManager.createFailResult("微信鉴权失败！");
		}
		
		qawa.setOpenid(openID);
		try {
			this.qcbAdminWechatAuthService.check(qawa);
		} catch (TransactionException e) {
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("数据操作出错！");
		}
		
		QcbAdmin qa = this.qcbAdminService.get(qcbAdmin.getUuid());
		qcbAdmin.setWechatFlag(qa.getWechatFlag());
		qcbAdmin.setWechatNum(qa.getWechatNum());
		session.setAttribute("currentQcbOperator", qcbAdmin);
		return ResultManager.createSuccessResult("微信鉴权成功！");
	}
	
	/**
	 * 获取微信鉴权信息
	 * @return
	 */
	@RequestMapping(value = "/getWechatFlag")
	@ResponseBody
	public Result getWechatFlag(){
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		return ResultManager.createDataResult(qcbAdmin.getWechatFlag());
	}
	
	/**
	 * 获取微信登录Token
	 * @return
	 */
	@RequestMapping(value = "/loginWechatToken", method = RequestMethod.GET)
	@ResponseBody
	public Result loginWechatToken() {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperatorTmp");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		long timestamp = System.currentTimeMillis();
		
		
		QcbAdminWechatAuth qawa = new QcbAdminWechatAuth();
		qawa.setUuid(UUID.randomUUID().toString());
		qawa.setQcbAdmin(qcbAdmin.getUuid());
		qawa.setType(QcbAdminWechatAuthType.LOGIN);
		qawa.setToken(UUID.randomUUID().toString().substring(0,8));
		qawa.setStatus(QcbAdminWechatAuthStatus.NORMAL);
		qawa.setCreatetime(new Timestamp(timestamp));
		
		String token = Base64Util.getBase64(qawa.getToken() + qawa.getUuid());
		this.qcbAdminWechatAuthService.insert(qawa);
		return ResultManager.createDataResult(token);
	}
	
	/**
	 * 微信登录
	 * @param token
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/loginWechatAuth")
	@ActionParam(key = "token", message="token", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "code", message="code", type = DataType.STRING, required = true, maxLength = 200)
	@ResponseBody
	public Result loginWechatAuth(String token, String code){
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperatorTmp");
		
		token = Base64Util.getFromBase64(token);
		String uuid = token.substring(8,token.length());
		
		QcbAdminWechatAuth qawa = this.qcbAdminWechatAuthService.get(uuid);
		if(qawa == null){
			return ResultManager.createFailResult("token有误！");
		}
		
		if(!qcbAdmin.getUuid().equals(qawa.getQcbAdmin())){
			return ResultManager.createFailResult("管理员信息不符！");
		}
		
		if(!QcbAdminWechatAuthStatus.NORMAL.equals(qawa.getStatus())){
			return ResultManager.createFailResult("鉴权信息状态不正确！");
		}
		
		if(!qawa.getToken().equals(token.substring(0,8))){
			qawa.setStatus(QcbAdminWechatAuthStatus.FAILED);
			this.qcbAdminWechatAuthService.update(qawa);
			return ResultManager.createFailResult("token有误！");
		}
		
		String openID = ConfigUtil.getOauthAccessOpenId(code,ConfigUtil.QCB_AUTH_APPID,ConfigUtil.QCB_AUTH_SECRECT);
		if (openID == null || "".equals(openID)) {
			qawa.setStatus(QcbAdminWechatAuthStatus.FAILED);
			this.qcbAdminWechatAuthService.update(qawa);
			return ResultManager.createFailResult("微信鉴权失败！");
		}
		
		qawa.setOpenid(openID);
		try {
			this.qcbAdminWechatAuthService.check(qawa);
		} catch (TransactionException e) {
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("数据操作出错！");
		}
		
		session.setAttribute("currentQcbOperator", qcbAdmin);
		return ResultManager.createSuccessResult("微信登录成功！");
	}
	
	/**
	 * 获取微信登录结果
	 * @return
	 */
	@RequestMapping(value = "/loginWechatResult")
	@ResponseBody
	public Result loginWechatResult(){
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return ResultManager.createDataResult(session.getAttribute("currentQcbOperator") != null);
	}
}
