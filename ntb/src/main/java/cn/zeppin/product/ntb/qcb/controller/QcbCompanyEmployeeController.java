/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zeppin.product.ntb.backadmin.service.api.IAliCertificationService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.controller.base.WarningData;
import cn.zeppin.product.ntb.core.entity.AliCertification;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin.QcbCompanyAdminStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee;
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee.QcbCompanyEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployee.QcbEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminMenuService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyBankcardService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbMenuService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyEmployeeVO;
import cn.zeppin.product.utility.IDCardUtil;
import cn.zeppin.product.utility.JuHeUtility;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.PasswordCreator;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 企财宝员工信息管理
 */

@Controller
@RequestMapping(value = "/qcb/employee")
public class QcbCompanyEmployeeController extends BaseController {
	
	@Autowired
	private IQcbCompanyBankcardService qcbCompanyBankcardService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbCompanyAdminService qcbCompanyAdminService;
	
	@Autowired
	private IQcbAdminService qcbAdminService; 
	
	@Autowired
	private IQcbMenuService qcbMenuService;
	
	@Autowired
	private IQcbCompanyAdminMenuService qcbCompanyAdminMenuService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IQcbCompanyEmployeeService qcbCompanyEmployeeService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IAliCertificationService aliCertificationService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 根据条件查询企业员工信息列表
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		//取员工信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业员工信息！");
		}
		
		QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
		if(ca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompany", company);
		searchMap.put("name", name);
		searchMap.put("status", status);
		
		//查询总数
		Integer totalResultCount = this.qcbCompanyEmployeeService.getCountForList(searchMap);
		//查询列表
		List<Entity> list = this.qcbCompanyEmployeeService.getListForListPage(searchMap, pageNum, pageSize, sorts, QcbCompanyEmployeeVO.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取一条企业员工信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		
		//取员工信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业员工信息！");
		}
		
		QcbCompanyAccount ca = this.qcbCompanyAccountService.get(company);
		if(ca == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		QcbCompanyEmployee qca = this.qcbCompanyEmployeeService.get(uuid);
		if(qca == null){
			return ResultManager.createFailResult("该企业员工信息不存在！");
		}
		
		if(QcbCompanyEmployeeStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("该企业员工信息不存在！");
		}
		
		if(!company.equals(qca.getQcbCompany())){
			return ResultManager.createFailResult("无权查看该企业员工信息！");
		}
		
		QcbEmployee qa = this.qcbEmployeeService.get(qca.getQcbEmployee());
		if(qa == null){
			return ResultManager.createFailResult("该企业员工信息不存在！");
		}
		
		if(QcbEmployeeStatus.DELETED.equals(qa.getStatus())){
			return ResultManager.createFailResult("该企业员工信息不存在！");
		}
		
		QcbCompanyEmployeeVO vo = new QcbCompanyEmployeeVO(qca);
		vo.setRealname(qa.getRealname());
		vo.setIdcard(qa.getIdcard());
		vo.setMobile(qa.getMobile());
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 添加一条企业员工信息
	 * 验证员工是否存在
	 * 验证员工与企业关系是否存在
	 * 
	 * @param name
	 * @param mobile
	 * @param role
	 * @param authority
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="姓名", type = DataType.STRING, required = true)
	@ActionParam(key = "idcard", message="身份证号", type = DataType.STRING, required = true)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING, required = true)
	@ActionParam(key = "department", message="所在部门", type = DataType.STRING, required = true)
	@ActionParam(key = "duty", message="职位", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="在职情况", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String name, String idcard, String mobile, String department, String duty, String status) {
		
		idcard = idcard.toUpperCase();
		//取员工信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权操作该企业员工信息！");
		}
		
		QcbCompanyAccount companyAccount = this.qcbCompanyAccountService.get(company);
		if(companyAccount == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		//验证手机号正确
		if(!Utlity.isMobileNO(mobile)){
			return ResultManager.createFailResult("手机号码非法！");
		}
		
		//验证身份证号
		if(!Utlity.checkIdCard(idcard)){
			return ResultManager.createFailResult("身份证号非法！");
		}
		
		
		//验证手机号码是否存在
		boolean flag = false;
		if(this.qcbEmployeeService.isExistQcbEmployeeByMobile(mobile, null)){//存在
			flag = true;
		}
		
		QcbEmployee qe = new QcbEmployee();
		String content = "";
		AliCertification ac = null;
		if(flag){//如果存在 判断是否错在企业与账户关联关系
			qe = this.qcbEmployeeService.getByMobile(mobile);
			if(!qe.getIdcard().equals(idcard)){
				return ResultManager.createFailResult("员工身份信息与手机号不匹配！");
			}
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("qcbEmployee", qe.getUuid());
			inputParams.put("qcbCompany", company);
			Integer count = this.qcbCompanyEmployeeService.getCount(inputParams);
			if(count > 0){
				return ResultManager.createFailResult("该员工账号已存在！");
			}
			content = "您已被企财宝企业" + qcbAdmin.getQcbCompanyName() + "添加为新员工，请您微信搜索并关注“QCB服务号”公众号，使用本手机号码绑定后登录查看。";
		} else {
			if(this.qcbEmployeeService.isExistQcbEmployeeByIdcard(idcard, null)){//存在
				return ResultManager.createFailResult("员工身份信息与手机号不匹配！");
			}
			//调用支付宝欺诈信息验证接口 进行实名认证
			Map<String, Object> result = JuHeUtility.certification(idcard, name);
			if(result.get("request") != null && (Boolean)result.get("request")){
				//信息入库
				Map<String, Object> response = (Map<String, Object>) result.get("response");
				if(response != null && !response.isEmpty()){
					ac = new AliCertification();
					ac.setUuid(UUID.randomUUID().toString());
					ac.setCreatetime(new Timestamp(System.currentTimeMillis()));
					ac.setCreator(qcbAdmin.getUuid());
					
					ac.setBinNo(response.get("bizNo")==null ? "无":response.get("bizNo").toString());
					ac.setVerifyCode(response.get("verifyCode")==null ? "无":response.get("verifyCode").toString());
					ac.setProductCode(response.get("product_code")==null ? "无":response.get("product_code").toString());
					ac.setTranscationid(response.get("transAutoIncIdx")==null ? "无":response.get("transAutoIncIdx").toString());
					ac.setInscription("支付宝实名认证—欺诈信息验证");
					ac.setCode(response.get("code")==null ? "无":response.get("code").toString());
					ac.setMsg(response.get("msg")==null ? "无":response.get("msg").toString());
					ac.setSubCode(response.get("sub_code")==null ? "无":response.get("sub_code").toString());
					ac.setSubMsg(response.get("sub_msg")==null ? "无":response.get("sub_msg").toString());
				}
			} else {
				return ResultManager.createFailResult("实名认证失败，" + result.get("message").toString());
			}
			
			if(!(Boolean)result.get("result")){
				if(ac != null){
					this.aliCertificationService.insert(ac);
				}
				return ResultManager.createFailResult("实名认证失败，" + result.get("message").toString());
			}
			
			//不存在 添加新号
			qe.setUuid(UUID.randomUUID().toString());
			qe.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qe.setMobile(mobile);
			qe.setRealname(name);
			qe.setIdcard(idcard);
			if(IDCardUtil.getSex(idcard) == 1){
				qe.setSex(Utlity.SEX_MAN);
			} else {
				qe.setSex(Utlity.SEX_WOMAN);
			}
			qe.setRealname(name);
			qe.setStatus(QcbEmployeeStatus.NORMAL);
			
			//密码加密-密码可空
//			String password = PasswordCreator.createPassword(8);
			content = "您已被企财宝企业" + qcbAdmin.getQcbCompanyName() + "添加为新员工，请您微信搜索并关注“QCB服务号”公众号，使用本手机号码绑定后登录查看。";
//			String encryptPassword = MD5.getMD5(password);//员工端登陆密码MD5加密
//			qe.setLoginPassword(encryptPassword);
			qe.setStatus(QcbEmployeeStatus.NORMAL);
			qe.setCurrentAccount(BigDecimal.ZERO);
			qe.setCurrentAccountYesterday(BigDecimal.ZERO);
			qe.setFlagCurrent(false);
			qe.setSecretPasswordFlag(false);
			qe.setTotalInvest(BigDecimal.valueOf(0));
			qe.setTotalReturn(BigDecimal.valueOf(0));
			qe.setAccountBalance(BigDecimal.valueOf(0));
		}
		MobileCode mc = new MobileCode();
		String codeInfo = Utlity.getCaptcha();
		mc.setCode(codeInfo);
		mc.setContent(content);
		mc.setMobile(mobile);
		mc.setCreator(qcbAdmin.getUuid());
		mc.setStatus(MobileCodeStatus.DISABLE);
		mc.setType(MobileCodeTypes.NOTICE);
		mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		mc.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
		mc.setUuid(UUID.randomUUID().toString());
		
		//添加一个企业与员工关系
		QcbCompanyEmployee qcEmp = new QcbCompanyEmployee();
		qcEmp.setUuid(UUID.randomUUID().toString());
		qcEmp.setQcbEmployee(qe.getUuid());
		qcEmp.setQcbCompany(companyAccount.getUuid());
		qcEmp.setDuty(duty);
		qcEmp.setDepartment(department);
		if(!QcbCompanyEmployeeStatus.NORMAL.equals(status) && !QcbCompanyEmployeeStatus.DISABLE.equals(status)){
			return ResultManager.createFailResult("在职情况错误！");
		}
		qcEmp.setStatus(status);
		qcEmp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcEmp.setCreator(qcbAdmin.getUuid());
		
		try {
			this.qcbCompanyEmployeeService.insert(flag, qe, qcEmp, ac, mc);
			
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常");
		}
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 修改编辑
	 * @param uuid
	 * @param role
	 * @param authority
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "mobile", message="手机号码", type = DataType.STRING, required = true)
	@ActionParam(key = "department", message="所在部门", type = DataType.STRING, required = true)
	@ActionParam(key = "duty", message="职位", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="在职情况", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String uuid, String mobile, String department, String duty, String status) {
		//取员工信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业员工信息！");
		}
		
		//验证手机号是否合法
		if (!Utlity.isMobileNO(mobile)){
			return ResultManager.createFailResult("手机号码有误！");
		}
		
		//校验企业是否存在
		QcbCompanyAccount qcbCom = this.qcbCompanyAccountService.get(company);
		if(qcbCom == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		
		QcbCompanyEmployee qca = this.qcbCompanyEmployeeService.get(uuid);
		if(qca == null){
			return ResultManager.createFailResult("该企业员工信息不存在！");
		}
		
		if(QcbCompanyEmployeeStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("该企业员工信息不存在！");
		}
		
		if(!company.equals(qca.getQcbCompany())){
			return ResultManager.createFailResult("无权操作该企业员工信息！");
		}
		
		if(!QcbCompanyEmployeeStatus.NORMAL.equals(status) && !QcbCompanyEmployeeStatus.DISABLE.equals(status)){
			return ResultManager.createFailResult("在职情况错误！");
		}
		
		QcbEmployee qe = this.qcbEmployeeService.get(qca.getQcbEmployee());
		if(qe == null){
			return ResultManager.createFailResult("员工不存在！");
		}
		
		if(this.qcbEmployeeService.isExistQcbEmployeeByMobile(mobile, qe.getUuid())){
			return ResultManager.createFailResult("手机号码已存在！");
		}
		
		qe.setMobile(mobile);
		qca.setDepartment(department);
		qca.setDuty(duty);
		qca.setStatus(status);
		
		try {
			this.qcbCompanyEmployeeService.update(qca, qe);
			return ResultManager.createSuccessResult("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常");
		}
	}
	
	/**
	 * 删除一条企业员工信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result delete(String uuid) {
		
		//取员工信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(qcbAdmin == null){
			return ResultManager.createFailResult("用户未登录！");
		}
		
		String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
		if("".equals(company)){
			return ResultManager.createFailResult("无权查看该企业员工信息！");
		}
		
		//校验企业是否存在
		QcbCompanyAccount qcbCom = this.qcbCompanyAccountService.get(company);
		if(qcbCom == null){
			return ResultManager.createFailResult("企业信息错误！");
		}
		
		QcbCompanyEmployee qca = this.qcbCompanyEmployeeService.get(uuid);
		if(qca == null){
			return ResultManager.createFailResult("该企业员工信息不存在！");
		}
		
		if(QcbCompanyEmployeeStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("该企业员工信息不存在！");
		}
		
		if(!company.equals(qca.getQcbCompany())){
			return ResultManager.createFailResult("无权操作该企业员工信息！");
		}
		
		try {
			this.qcbCompanyEmployeeService.delete(qca);
			return ResultManager.createSuccessResult("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("操作异常！");
		}
	}
	
	/**
	 * 批量导出员工
	 * @param name
	 * @param status
	 * @param uuids
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "uuids", message="员工ID", type = DataType.NUMBER)
	@ResponseBody
	public ModelAndView export(String name, String status, String uuids, HttpServletRequest request, HttpServletResponse response) {
		
		//取员工信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			if(qcbAdmin == null){
//				return ResultManager.createFailResult("用户未登录！");
				response.getWriter().write("用户未登录！");
				return null;
			}
			
			String company = qcbAdmin.getQcbCompany() == null ? "" : qcbAdmin.getQcbCompany();
			if("".equals(company)){
				response.getWriter().write("无权查看该企业员工信息！");
				return null;
			}
			
			//校验企业是否存在
			QcbCompanyAccount qcbCom = this.qcbCompanyAccountService.get(company);
			if(qcbCom == null){
				response.getWriter().write("企业信息错误！");
				return null;
			}
			List<QcbCompanyEmployeeVO> listvo = new ArrayList<QcbCompanyEmployeeVO>();
			if(!Utlity.checkStringNull(uuids)){
				String[] strArr = uuids.split(",");
				for(String str : strArr){
					QcbCompanyEmployee qce = this.qcbCompanyEmployeeService.get(str);
					if(qce != null && !QcbCompanyEmployeeStatus.DELETED.equals(qce.getStatus())){
						QcbEmployee qe = this.qcbEmployeeService.get(qce.getQcbEmployee());
						if(qe != null && !QcbEmployeeStatus.DELETED.equals(qe.getStatus())){
							QcbCompanyEmployeeVO vo = new QcbCompanyEmployeeVO(qce);
							vo.setRealname(qe.getRealname());
							vo.setMobile(qe.getMobile());
							vo.setIdcard(qe.getIdcard());
							listvo.add(vo);
						}
					}
				}
			} else {
				//查询条件
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("qcbCompany", company);
				searchMap.put("name", name);
				searchMap.put("status", status);
				
				//查询列表
				List<Entity> list = this.qcbCompanyEmployeeService.getListForListPage(searchMap, -1, -1, null, QcbCompanyEmployeeVO.class);
				for(Entity entity : list){
					QcbCompanyEmployeeVO vo = (QcbCompanyEmployeeVO)entity;
					listvo.add(vo);
				}
			}
			
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "企业员工");
			XSSFRow row = s.createRow(0);
			String title[] = { "序号", "姓名", "身份证号", "手机号", "所在部门", "职位", "在职情况"};
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(QcbCompanyEmployeeVO vo : listvo){
				
				t++;
				row = s.createRow(t);
				
				String realname = vo.getRealname();
				String idcard = vo.getIdcard();
				String mobile = vo.getMobile();
				String department = vo.getDepartment();
				String duty = vo.getDuty();
				String statusCN = vo.getStatusCN();
				
				String tInfo[] = { String.valueOf(t), realname , idcard, mobile, department, duty, statusCN};
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
				
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=employees.xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("操作异常,导出失败！");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally {
			if(ouputStream != null){
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
			if(wb != null){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return null;
	}
	
	/**
	 * 批量导入员工
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ActionParam(key = "file", message = "上传文件", type = DataType.STRING, required = true)
	@ResponseBody
	public Result upload(String file, HttpServletRequest request) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("企财宝企业不存在！");
		}
		
		Resource r = this.resourceService.get(file);
		if(r == null){
			return ResultManager.createFailResult("文件不存在，请重新上传");
		}
		
		String message = "导入成功！";
		try {
			String resourcePath = r.getUrl();
			String serverPath = request.getServletContext().getRealPath("/").replace("\\", "/");
			File realfile = new File(serverPath + resourcePath);
			InputStream is = null;
			boolean isE2007 = false; // 判断是否是excel2007格式
			if (resourcePath.endsWith("xlsx")) {
				isE2007 = true;
			}
			if (realfile.exists()) {
				try {
					is = new FileInputStream(realfile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			Workbook wb = null;
			if (is == null) {
				return ResultManager.createFailResult("文件不存在，请重新上传");
			}
			if (isE2007) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			Sheet s = wb.getSheetAt(0);
			Row row;
			int t = s.getLastRowNum();
			
			if(s.getLastRowNum() < 1){
				message = "文件中没有数据！";
				return ResultManager.createFailResult(message);
			}
			
			row = s.getRow(0);
			
			if(row.getCell(0) == null || !"姓名".equals(row.getCell(0).getStringCellValue())){
				message = "标题必须按照模板格式填写，第一列内容必须是姓名！";
				return ResultManager.createFailResult(message);
			}
			
			if(row.getCell(1) == null || !"身份证号".equals(row.getCell(1).getStringCellValue())){
				message = "标题必须按照模板格式填写，第二列内容必须是身份证号！";
				return ResultManager.createFailResult(message);
			}
			
			if(row.getCell(2) == null || !"手机号".equals(row.getCell(2).getStringCellValue())){
				message = "标题必须按照模板格式填写，第三列内容必须是手机号！";
				return ResultManager.createFailResult(message);
			}
			
			if(row.getCell(3) == null || !"所在部门".equals(row.getCell(3).getStringCellValue())){
				message = "标题必须按照模板格式填写，第四列内容必须是所在部门！";
				return ResultManager.createFailResult(message);
			}
			
			if(row.getCell(4) == null || !"所在职位".equals(row.getCell(4).getStringCellValue())){
				message = "标题必须按照模板格式填写，第四列内容必须是所在职位！";
				return ResultManager.createFailResult(message);
			}
			
			row.getCell(0).setCellType(CellType.STRING);
			row.getCell(1).setCellType(CellType.STRING);
			row.getCell(2).setCellType(CellType.STRING);
			row.getCell(3).setCellType(CellType.STRING);
			row.getCell(4).setCellType(CellType.STRING);
			
			List<Map<String, Object>> datasList = new ArrayList<Map<String, Object>>();
			
			List<WarningData> errorList = new ArrayList<WarningData>();	
			for(int i = 1; i <= t; i++){
				row = s.getRow(i);
				if(row == null){
					continue;
				}
				String realname = "";
				String idcard = "";
				String mobile = "";
				String department = "";
				String duty = "";
				
				if (row.getCell(0) != null) {
					row.getCell(0).setCellType(CellType.STRING);
					realname = row.getCell(0).getStringCellValue().trim();
				}
				
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(CellType.STRING);
					idcard = row.getCell(1).getStringCellValue().trim();
				}
				
				if (row.getCell(2) != null) {
					row.getCell(2).setCellType(CellType.STRING);
					mobile = row.getCell(2).getStringCellValue().trim();
				}
				
				if (row.getCell(3) != null) {
					row.getCell(3).setCellType(CellType.STRING);
					department = row.getCell(3).getStringCellValue().trim();
				}
				
				if (row.getCell(4) != null) {
					row.getCell(4).setCellType(CellType.STRING);
					duty = row.getCell(4).getStringCellValue().trim();
				}
				
				if(Utlity.checkStringNull(realname) && Utlity.checkStringNull(idcard) && Utlity.checkStringNull(mobile)){
					continue;
				}
				
				if(realname == null || "".equals(realname)){
					errorList.add(new WarningData(i+"", "未填写姓名！"));
				}
				
				if(idcard == null || "".equals(idcard)){
					errorList.add(new WarningData(i+"", "未填写身份证号！"));
				}else if(!Utlity.checkIdCard(idcard)){
					errorList.add(new WarningData(i+"", "身份证号填写有误！"));
				}
				
				if(mobile == null || "".equals(mobile)){
					errorList.add(new WarningData(i+"", "未填写手机号！"));
				}else if(!Utlity.isMobileNO(mobile)){
					errorList.add(new WarningData(i+"", "手机号填写有误！"));
				}
				
				for(Map<String, Object> data : datasList){
					if(mobile.equals(data.get("mobile"))){
						errorList.add(new WarningData(i+"", "手机号为的员工，存在多条记录！"));
						break;
					}
				}
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("row", i + "");
				dataMap.put("realname", realname);
				dataMap.put("idcard", idcard);
				dataMap.put("mobile", mobile);
				dataMap.put("department", department);
				dataMap.put("duty", duty);
				datasList.add(dataMap);
			}
			
			
			Map<String, Object> totalMap = new HashMap<String, Object>();
			Map<String, Object> totalDataMap = new HashMap<String, Object>();
			for(Map<String,Object> dataMap : datasList){
				totalDataMap.put(dataMap.get("row").toString(), dataMap);
			}
			
			totalMap.put("datasMap", totalDataMap);
			
			if(errorList.size() > 0){
				return ResultManager.createWarningResult(totalMap, errorList);
			}
			
			for(Map<String,Object> dataMap : datasList){
				String rowIndex = dataMap.get("row").toString();
				String mobile = dataMap.get("mobile").toString();
				String idcard = dataMap.get("idcard").toString();
				String name = dataMap.get("realname").toString();
				String department = dataMap.get("department").toString();
				String duty = dataMap.get("duty").toString();
				
				idcard = idcard.toUpperCase();
				//验证手机号码是否存在
				boolean flag = false;
				if(this.qcbEmployeeService.isExistQcbEmployeeByMobile(mobile, null)){//存在
					flag = true;
				}
				
				QcbEmployee qe = new QcbEmployee();
				String content = "";
				AliCertification ac = null;
				
				if(flag){//如果存在 判断是否错在企业与账户关联关系
					qe = this.qcbEmployeeService.getByMobile(mobile);
					if(!qe.getIdcard().equals(idcard)){
						errorList.add(new WarningData(rowIndex, "员工身份信息与手机号不匹配！"));
						continue;
					}
				} else {
					if(this.qcbEmployeeService.isExistQcbEmployeeByIdcard(idcard, null)){//存在
						errorList.add(new WarningData(rowIndex, "员工身份信息与手机号不匹配！"));
						continue;
					}
					//调用支付宝欺诈信息验证接口 进行实名认证
					Map<String, Object> result = JuHeUtility.certification(idcard, name);
					if(result.get("request") != null && (Boolean)result.get("request")){
						//信息入库
						Map<String, Object> response = (Map<String, Object>) result.get("response");
						if(response != null && !response.isEmpty()){
							ac = new AliCertification();
							ac.setUuid(UUID.randomUUID().toString());
							ac.setCreatetime(new Timestamp(System.currentTimeMillis()));
							ac.setCreator(admin.getUuid());
							
							ac.setBinNo(response.get("bizNo")==null ? "无":response.get("bizNo").toString());
							ac.setVerifyCode(response.get("verifyCode")==null ? "无":response.get("verifyCode").toString());
							ac.setProductCode(response.get("product_code")==null ? "无":response.get("product_code").toString());
							ac.setTranscationid(response.get("transAutoIncIdx")==null ? "无":response.get("transAutoIncIdx").toString());                 
							ac.setInscription("支付宝实名认证—欺诈信息验证");
							ac.setCode(response.get("code")==null ? "无":response.get("code").toString());
							ac.setMsg(response.get("msg")==null ? "无":response.get("msg").toString());
							ac.setSubCode(response.get("sub_code")==null ? "无":response.get("sub_code").toString());
							ac.setSubMsg(response.get("sub_msg")==null ? "无":response.get("sub_msg").toString());
						}
					} else {
						errorList.add(new WarningData(rowIndex, "实名认证失败，" + result.get("message").toString()));
						continue;
					}
					
					if(!(Boolean)result.get("result")){
						if(ac != null){
							this.aliCertificationService.insert(ac);
						}
						errorList.add(new WarningData(rowIndex, "实名认证失败，" + result.get("message").toString()));
						continue;
					}
					
					//不存在 添加新号
					qe.setUuid(UUID.randomUUID().toString());
					qe.setCreatetime(new Timestamp(System.currentTimeMillis()));
					qe.setMobile(mobile);
					qe.setRealname(name);
					qe.setIdcard(idcard);
					if(IDCardUtil.getSex(idcard) == 1){
						qe.setSex(Utlity.SEX_MAN);
					} else {
						qe.setSex(Utlity.SEX_WOMAN);
					}
					qe.setRealname(name);
					qe.setStatus(QcbEmployeeStatus.NORMAL);
					
					//密码加密
					String password = PasswordCreator.createPassword(8);
					content = "您已被企财宝企业" + admin.getQcbCompanyName() + "添加为新员工，请您微信搜索并关注“QCB服务号”公众号，使用本手机号码绑定后登录查看。";
					String encryptPassword = MD5.getMD5(password);//员工端登陆密码MD5加密
					qe.setLoginPassword(encryptPassword);
					qe.setStatus(QcbEmployeeStatus.NORMAL);
					
					qe.setSecretPasswordFlag(false);
					qe.setCurrentAccount(BigDecimal.ZERO);
					qe.setCurrentAccountYesterday(BigDecimal.ZERO);
					qe.setFlagCurrent(false);
					qe.setTotalInvest(BigDecimal.valueOf(0));
					qe.setTotalReturn(BigDecimal.valueOf(0));
					qe.setAccountBalance(BigDecimal.valueOf(0));
				}
				
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("qcbEmployee", qe.getUuid());
				inputParams.put("qcbCompany", qca.getUuid());
				List<Entity> listQce = this.qcbCompanyEmployeeService.getListForPage(inputParams, -1, -1, null, QcbCompanyEmployee.class);
				if(listQce.size() == 0){
					content = "您已被企财宝企业" + admin.getQcbCompanyName() + "添加为新员工，请您微信搜索并关注“QCB服务号”公众号，使用本手机号码绑定后登录查看。";
					
					MobileCode mc = new MobileCode();
					String codeInfo = Utlity.getCaptcha();
					mc.setCode(codeInfo);
					mc.setContent(content);
					mc.setMobile(mobile);
					mc.setCreator(admin.getUuid());
					mc.setStatus(MobileCodeStatus.DISABLE);
					mc.setType(MobileCodeTypes.NOTICE);
					mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
					mc.setCreatorType(MobileCodeCreatorType.QCB_ADMIN);
					mc.setUuid(UUID.randomUUID().toString());
					
					//添加一个企业与员工关系
					QcbCompanyEmployee qcEmp = new QcbCompanyEmployee();
					qcEmp.setUuid(UUID.randomUUID().toString());
					qcEmp.setQcbEmployee(qe.getUuid());
					qcEmp.setQcbCompany(qca.getUuid());
					qcEmp.setDepartment(department);
					qcEmp.setDuty(duty);
					qcEmp.setStatus(QcbCompanyAdminStatus.NORMAL);
					qcEmp.setCreatetime(new Timestamp(System.currentTimeMillis()));
					qcEmp.setCreator(admin.getUuid());
					
					try {
						this.qcbCompanyEmployeeService.insert(flag, qe, qcEmp, ac, mc);
					} catch (TransactionException e) {
						e.printStackTrace();
						super.flushAll();
						return ResultManager.createFailResult(e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
						super.flushAll();
						return ResultManager.createFailResult("操作异常");
					}
				}else{
					Entity e = listQce.get(0);
					QcbCompanyEmployee qce = (QcbCompanyEmployee) e;
					qce.setDepartment(department);
					qce.setDuty(duty);
					this.qcbCompanyEmployeeService.update(qce);
				}
			}
			
			if(errorList.size() > 0){
				return ResultManager.createWarningResult(totalMap, errorList);
			}
			
			return ResultManager.createDataResult(totalMap);
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("服务器繁忙，请稍后再试！");
		}
	}
}
