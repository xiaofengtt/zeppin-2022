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
 * ??????????????????
 * 
 * @author Clark.R
 * @since 2016???3???29??? ??????3:54:00
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
	 * ????????????
	 * ???????????????????????????
	 * ???????????????????????????????????????
	 * ?????????????????????????????????????????????
	 * 
	 * @param loginname base64??????
	 * @param password base64??????
	 * @param kaptcha
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "loginname", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "kaptcha", message="?????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result login(String loginname, String password, String kaptcha) {
		loginname = Base64Util.getFromBase64(loginname);
		password = Base64Util.getFromBase64(password);
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		QcbAdmin bkOperator = null;
		try {
			
			if(session.getAttribute(Constants.KAPTCHA_SESSION_KEY) == null){
				return ResultManager.createErrorResult("001", "??????????????????");
			}
			
			if("".equals((String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY))){
				return ResultManager.createErrorResult("001", "??????????????????");
			}
			
			// ???????????????
			String sessionAuthCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 
			
			if (!kaptcha.equalsIgnoreCase(sessionAuthCode)) {
				return ResultManager.createErrorResult("001", "????????????????????????");
			}
			
	        // ???????????????????????????
	        bkOperator =  qcbAdminService.getByMobile(loginname);
	        
	        if (bkOperator == null) {
	            throw new UnknownAccountException();
	        }  
	        
	        else if (QcbAdminStatus.DISABLE.equals(bkOperator.getStatus())) {  
	            throw new AuthenticationException("?????????????????????");  
	        }
			
			// ????????????
	        CustomizedToken usernamePasswordToken = new CustomizedToken(loginname, password, LoginType.QCB.toString());
			subject.login(usernamePasswordToken);
			
		} catch (UnknownAccountException e) {  
			//???????????????
			return ResultManager.createErrorResult("001", "????????????????????????");
		} catch (LockedAccountException e) {
			//???????????????
			return ResultManager.createErrorResult("002", "?????????????????????????????????????????????????????????");
		} catch (IncorrectCredentialsException e) {  
			//?????????,????????????  
			return ResultManager.createErrorResult("003", e.getMessage());
        } catch (ExcessiveAttemptsException e) {
            //?????????????????????????????????10??????
//			if (bkOperator.getStatus().equals(BkOperatorStatus.NORMAL)) {
//				bkOperator.setStatus(BkOperatorStatus.LOCKED);
//	        	bkOperator.setLockedtime(new Timestamp(System.currentTimeMillis()));
//	        	qcbAdminService.update(bkOperator);
//			}
			return ResultManager.createErrorResult("004", "?????????????????????????????????????????????????????????");
		}
		catch (AuthenticationException e) {
			// ??????????????????
			e.printStackTrace();
			return ResultManager.createErrorResult("005", e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		AdminVO vo = new AdminVO(bkOperator);
		
		/*
		 * ???????????????????????????????????????
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
//					return ResultManager.createFailResult("?????????????????????????????????");
					continue;
				}
				if(QcbCompanyAccountStatus.DELETED.equals(company.getStatus())){
//					return ResultManager.createFailResult("????????????????????????????????????");
					continue;
				}
				QcbCompanyAccountLessVO cvo = new QcbCompanyAccountLessVO(company);
				listCompanyAccount.add(cvo);
				if(company.getAccreditMobile().equals(bkOperator.getMobile())){//????????????
					flag = true;
					companyCurrent = company;
				}
				/*
				 * ???????????? ?????????????????? ??????????????????
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
					for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//?????????1?????????
						QcbMenuLessVO qmvo = entry.getValue();
						if(qmvo.getLevel() == 1){
							mapMenuVOAll.put(qmvo.getUuid(), qmvo);
							if(flag){
								mapMenuVO.put(qmvo.getUuid(), qmvo);
							}
						}
					}
					for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//??????2?????????????????????
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
			if(flag){//??????????????????
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
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		
		if(Utlity.checkStringNull(vo.getQcbCompany())){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		
		if(vo.getWechatFlag()){
			session.setAttribute("currentQcbOperatorTmp", vo);
		}else{
			session.setAttribute("currentQcbOperator", vo);
		}
		return ResultManager.createDataResult(vo.getWechatFlag());
	}

	/**
	 * ????????????
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		// ????????????
		Subject subject = SecurityUtils.getSubject();
		Session shiroSession = subject.getSession();
		shiroSession.removeAttribute("currentQcbOperator");
		subject.logout();
		return "redirect:../../../qcb/login.jsp";
	}
	
	/**
	 * ?????????????????????--????????????
	 * @param mobile base64??????
	 * @param code base64??????
	 * @return
	 */
	@RequestMapping(value = "/rePwdcheck", method = RequestMethod.POST)
	@ActionParam(key = "mobile", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "code", message="???????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result rePwdcheck(String mobile, String code) {
		
		mobile = Base64Util.getFromBase64(mobile);
		code = Base64Util.getFromBase64(code);
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		// ???????????????????????????
		QcbAdmin bkOperator =  qcbAdminService.getByMobile(mobile);
        
        if (bkOperator == null) {
        	return ResultManager.createFailResult("?????????????????????");
        }  
        
        else if (QcbAdminStatus.DISABLE.equals(bkOperator.getStatus())) {  
        	return ResultManager.createFailResult("?????????????????????");  
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
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!mc.getMobile().equals(mobile)){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		session.setAttribute("resetQcbPwd", bkOperator.getUuid());
		this.mobileCodeService.update(mc);
		return ResultManager.createSuccessResult("?????????????????????");
	}
	
	/**
	 * ????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/rePwd", method = RequestMethod.POST)
	@ActionParam(key = "passwordNew", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNewCheck", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordLevel", message="????????????", type = DataType.NUMBER)
	@ResponseBody
	public Result rePwd(String passwordNew, String passwordNewCheck, Integer passwordLevel) {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String qcb = (String)session.getAttribute("resetQcbPwd");
		if(Utlity.checkStringNull(qcb)){
			return ResultManager.createFailResult("???????????????");
		}
		//?????????????????????60b474258188bbf0b48249e04bc8f4ef
		QcbAdmin operator = qcbAdminService.get(qcb);
		if(operator != null){
			//????????????
			if(!passwordNew.equals(passwordNewCheck)){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			String encryptPassword = SecurityByQcbRealm.encrypt(passwordNew, ByteSource.Util.bytes(operator.getUuid()));
			operator.setPassword(encryptPassword);
			operator.setPasswordLevel(passwordLevel);
			operator = qcbAdminService.update(operator);
			
			//????????????
			session.removeAttribute("resetQcbPwd");
			
			return ResultManager.createSuccessResult("????????????????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????
	 * @param mobile base64??????
	 * @param code base64??????
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ActionParam(key = "mobile", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "code", message="???????????????", type = DataType.STRING, required = true)
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
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!mc.getMobile().equals(mobile)){
			return ResultManager.createFailResult("????????????????????????");
		}
		
//		if(!MobileCodeTypes.CODE.equals(mc.getType())){
//			return ResultManager.createFailResult("??????????????????????????????");
//		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeService.update(mc);
		return ResultManager.createSuccessResult("?????????????????????");
	}

	/**
	 * ????????????
	 * ????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 * @param mobile base64??????
	 * @param company urlecode
	 * @param area
	 * @param password base64??????
	 * @param contacts urlecode
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ActionParam(key = "mobile", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "company", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "area", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordLevel", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "contacts", message="???????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result register(String mobile, String company, String area, String password, Integer passwordLevel, String contacts) {
		mobile = Base64Util.getFromBase64(mobile);
		password = Base64Util.getFromBase64(password);
		try {
			company = URLDecoder.decode(company, "UTF-8");
			contacts = URLDecoder.decode(contacts, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("????????????");
		}
		
		//??????????????????????????????
		if(this.qcbCompanyAccountService.isExistQcbCompanyAccountByName(company, null)){
			return ResultManager.createFailResult("???????????????????????????");
		}
		
		//????????????????????????
		BkArea bkArea = this.bkAreaService.get(area);
		if(bkArea == null){
			return ResultManager.createFailResult("???????????????????????????");
		}
		
		//?????????????????????
		QcbAdmin qa = new QcbAdmin();
		//??????????????????????????????
		boolean flag = true;
		if(this.qcbAdminService.isExistQcbAdminByMobile(mobile, null)){//???????????????????????????????????????????????????
//			String encryptPassword = SecurityByQcbRealm.encrypt(password, ByteSource.Util.bytes(qa.getUuid()));
//			qa.setPassword(encryptPassword);
			qa = this.qcbAdminService.getByMobile(mobile);
			flag = false;
//			return ResultManager.createFailResult("??????????????????????????????");
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
		
		//??????????????????
		QcbCompanyAccount qca = new QcbCompanyAccount();
		qca.setUuid(UUID.randomUUID().toString());
		
		//??????ID??????
		Map<String, String> searchParams = new HashMap<String, String>();
		Integer count = this.qcbCompanyAccountService.getCount(searchParams);
		String merchantId = Utlity.getMerchantId(count);
		if(this.qcbCompanyAccountService.isExistQcbCompanyAccountByMerchantId(merchantId, null)){
			merchantId = Utlity.getMerchantId(count);
		}
		qca.setMerchantId(merchantId);//?????????ID??????
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
		
		//????????????????????????????????????
		QcbCompanyAdmin qcAdmin = new QcbCompanyAdmin();
		qcAdmin.setUuid(UUID.randomUUID().toString());
		qcAdmin.setQcbAdmin(qa.getUuid());
		qcAdmin.setQcbCompany(qca.getUuid());
		qcAdmin.setRole("?????????");
		qcAdmin.setStatus(QcbCompanyAdminStatus.NORMAL);
		qcAdmin.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcAdmin.setCreator(qa.getUuid());
		
		//?????????????????????????????????????????????
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
			return ResultManager.createSuccessResult("???????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????");
		}
	}
	
	/**
	 * ???????????? ???????????????????????????
	 */
	@RequestMapping(value = "/admin")
	@ResponseBody
	@RequiresRoles(value = RoleSign.ADMIN)
	public String admin() {
		return "??????admin??????,?????????";
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
	 * ?????????????????????
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
	@ActionParam(key = "name", message="??????", type = DataType.STRING, required = true, minLength = 6, maxLength = 22)
	@ActionParam(key = "realname", message="????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "mobile", message="?????????", type = DataType.PHONE, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "email", message="??????", type = DataType.EMAIL)
	@ResponseBody
	public Result edit(String uuid, String name, String realname, String mobile, String email) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		//?????????????????????
		QcbAdmin operator = qcbAdminService.get(uuid);
		if(operator != null && uuid.equals(operator.getUuid())){
			
			//??????????????????????????????
			if (qcbAdminService.isExistQcbAdminByMobile(mobile, uuid)) {
				return ResultManager.createFailResult("?????????????????????");
			}
			
//			//????????????????????????????????????
//			if (qcbAdminService.isExistOperatorByMobile(mobile, uuid)) {
//				return ResultManager.createFailResult("????????????????????????");
//			}
			
			//???????????????
//			operator.setEmail(email);
//			operator.setMobile(mobile);
//			operator.setName(name);
//			operator.setRealname(realname);
			
			operator = qcbAdminService.update(operator);
			session.setAttribute("currentOperator", operator);
			return ResultManager.createSuccessResult("????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	@ActionParam(key = "password", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNew", message="?????????", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordNewCheck", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "passwordLevel", message="????????????", type = DataType.NUMBER)
	@ResponseBody
	public Result password(String password, String passwordNew, String passwordNewCheck, Integer passwordLevel) {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		if(qcbAdmin == null){
			return ResultManager.createFailResult("??????????????????");
		}
		//?????????????????????60b474258188bbf0b48249e04bc8f4ef
		QcbAdmin operator = qcbAdminService.get(qcbAdmin.getUuid());
		if(operator != null){
			
			//????????????
			String encryptPassword = SecurityByQcbRealm.encrypt(password, ByteSource.Util.bytes(operator.getUuid()));
			if(!operator.getPassword().equals(encryptPassword)){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(!passwordNew.equals(passwordNewCheck)){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			encryptPassword = SecurityByQcbRealm.encrypt(passwordNew, ByteSource.Util.bytes(operator.getUuid()));
			operator.setPassword(encryptPassword);
			operator.setPasswordLevel(passwordLevel);
			operator = qcbAdminService.update(operator);
			
			//??????
			session.removeAttribute("currentQcbOperator");
			subject.logout();
			
			return ResultManager.createSuccessResult("????????????????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ???????????????
	 * @param code
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/changeMobile", method = RequestMethod.POST)
	@ActionParam(key = "code", message="???????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "mobile", message="????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeMobile(String code, String mobile) {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(company);
		if(qca == null){
			return ResultManager.createFailResult("???????????????????????????????????????");
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
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!mc.getMobile().equals(qcbAdmin.getMobile())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(Utlity.checkCodeTime(mc.getCreatetime().getTime(), System.currentTimeMillis())){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		QcbAdmin operator = qcbAdminService.get(qcbAdmin.getUuid());
		if(operator != null){
			
			if(this.qcbAdminService.isExistQcbAdminByMobile(mobile, operator.getUuid())){//
				return ResultManager.createFailResult("??????????????????????????????");
			}
			
			//??????????????????????????? ????????? ????????????
			QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
			if(ca == null){
				return ResultManager.createFailResult("?????????????????????");
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
				
				//??????
				session.removeAttribute("currentQcbOperator");
				subject.logout();
				
				return ResultManager.createSuccessResult("?????????????????????????????????????????????????????????");
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("???????????????");
			}
			
		}
		return ResultManager.createFailResult("????????????????????????");
	}
	
	/**
	 * ????????????
	 * @param company
	 * @return
	 */
	@RequestMapping(value = "/changeCompany", method = RequestMethod.GET)
	@ActionParam(key = "company", message="??????????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeCompany(String company) {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		String companyStr = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(companyStr)){
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
		
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(company);
		if(qca == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		if(QcbCompanyAccountStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		/*
		 * ???????????????????????????????????????
		 * */
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbAdmin", qcbAdmin.getUuid());
		inputParams.put("qcbCompany", qca.getUuid());
		inputParams.put("status", QcbCompanyAdminStatus.NORMAL);
		List<Entity> list = this.qcbCompanyAdminService.getListForPage(inputParams, -1, -1, null, QcbCompanyAdmin.class);
		if(list != null && !list.isEmpty()){
			QcbCompanyAdmin qcAdmin = (QcbCompanyAdmin)list.get(0);
			
			/*
			 * ????????????????????? qcbCompanyAdmin
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
				
				for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//?????????1?????????
					QcbMenuLessVO qmvo = entry.getValue();
					if(qmvo.getLevel() == 1){
						mapMenuVO.put(qmvo.getUuid(), qmvo);
					}
				}
				for (Map.Entry<String, QcbMenuLessVO> entry : mapMenu.entrySet()) {//??????2?????????????????????
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
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		//??????session
		session.removeAttribute("currentQcbOperator");
		session.setAttribute("currentQcbOperator", qcbAdmin);
		
		return ResultManager.createDataResult(qcbAdmin);
	}
	
	/**
	 * ??????????????????Token
	 * @param type
	 * @param qcbCompanyPayroll
	 * @return
	 */
	@RequestMapping(value = "/bindWechatToken", method = RequestMethod.GET)
	@ActionParam(key = "type", message="token??????", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "qcbCompanyPayroll", message="??????????????????", type = DataType.STRING, maxLength = 36)
	@ResponseBody
	public Result bindWechatToken(String type, String qcbCompanyPayroll) {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(!QcbAdminWechatAuthType.BIND.equals(type) && !QcbAdminWechatAuthType.PAYROLL.equals(type) 
				&& !QcbAdminWechatAuthType.REMOVE.equals(type) && !QcbAdminWechatAuthType.LOGIN.equals(type)){
			return ResultManager.createFailResult("token??????????????????");
		}
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("??????????????????");
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
				return ResultManager.createFailResult("?????????????????????????????????");
			}
			
			QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(qcbCompanyPayroll);
			if(qcp == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			qawa.setQcbCompanyPayroll(qcbCompanyPayroll);
		}
		
		String token = Base64Util.getBase64(qawa.getToken() + qawa.getUuid());
		
		this.qcbAdminWechatAuthService.insert(qawa);
		return ResultManager.createDataResult(token);
	}
	
	/**
	 * ????????????
	 * @param token
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/bindWechatAuth")
	@ActionParam(key = "token", message="token", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "code", message="code", type = DataType.STRING, required = true, maxLength = 200)
	@ResponseBody
	public Result bindWechatAuth(String token, String code){
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		token = Base64Util.getFromBase64(token);
		String uuid = token.substring(8,token.length());
		
		QcbAdminWechatAuth qawa = this.qcbAdminWechatAuthService.get(uuid);
		if(qawa == null){
			return ResultManager.createFailResult("token?????????");
		}
		
		if(!qcbAdmin.getUuid().equals(qawa.getQcbAdmin())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!QcbAdminWechatAuthStatus.NORMAL.equals(qawa.getStatus())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!qawa.getToken().equals(token.substring(0,8))){
			qawa.setStatus(QcbAdminWechatAuthStatus.FAILED);
			this.qcbAdminWechatAuthService.update(qawa);
			return ResultManager.createFailResult("token?????????");
		}
		
		String openID = ConfigUtil.getOauthAccessOpenId(code,ConfigUtil.QCB_AUTH_APPID,ConfigUtil.QCB_AUTH_SECRECT);
		if (openID == null || "".equals(openID)) {
			qawa.setStatus(QcbAdminWechatAuthStatus.FAILED);
			this.qcbAdminWechatAuthService.update(qawa);
			return ResultManager.createFailResult("?????????????????????");
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
			return ResultManager.createFailResult("?????????????????????");
		}
		
		QcbAdmin qa = this.qcbAdminService.get(qcbAdmin.getUuid());
		qcbAdmin.setWechatFlag(qa.getWechatFlag());
		qcbAdmin.setWechatNum(qa.getWechatNum());
		session.setAttribute("currentQcbOperator", qcbAdmin);
		return ResultManager.createSuccessResult("?????????????????????");
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/getWechatFlag")
	@ResponseBody
	public Result getWechatFlag(){
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		return ResultManager.createDataResult(qcbAdmin.getWechatFlag());
	}
	
	/**
	 * ??????????????????Token
	 * @return
	 */
	@RequestMapping(value = "/loginWechatToken", method = RequestMethod.GET)
	@ResponseBody
	public Result loginWechatToken() {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperatorTmp");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("??????????????????");
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
	 * ????????????
	 * @param token
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/loginWechatAuth")
	@ActionParam(key = "token", message="token", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "code", message="code", type = DataType.STRING, required = true, maxLength = 200)
	@ResponseBody
	public Result loginWechatAuth(String token, String code){
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperatorTmp");
		
		token = Base64Util.getFromBase64(token);
		String uuid = token.substring(8,token.length());
		
		QcbAdminWechatAuth qawa = this.qcbAdminWechatAuthService.get(uuid);
		if(qawa == null){
			return ResultManager.createFailResult("token?????????");
		}
		
		if(!qcbAdmin.getUuid().equals(qawa.getQcbAdmin())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!QcbAdminWechatAuthStatus.NORMAL.equals(qawa.getStatus())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!qawa.getToken().equals(token.substring(0,8))){
			qawa.setStatus(QcbAdminWechatAuthStatus.FAILED);
			this.qcbAdminWechatAuthService.update(qawa);
			return ResultManager.createFailResult("token?????????");
		}
		
		String openID = ConfigUtil.getOauthAccessOpenId(code,ConfigUtil.QCB_AUTH_APPID,ConfigUtil.QCB_AUTH_SECRECT);
		if (openID == null || "".equals(openID)) {
			qawa.setStatus(QcbAdminWechatAuthStatus.FAILED);
			this.qcbAdminWechatAuthService.update(qawa);
			return ResultManager.createFailResult("?????????????????????");
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
			return ResultManager.createFailResult("?????????????????????");
		}
		
		session.setAttribute("currentQcbOperator", qcbAdmin);
		return ResultManager.createSuccessResult("?????????????????????");
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/loginWechatResult")
	@ResponseBody
	public Result loginWechatResult(){
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return ResultManager.createDataResult(session.getAttribute("currentQcbOperator") != null);
	}
}
