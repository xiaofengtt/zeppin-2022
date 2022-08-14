/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAdmin.QcbCompanyAdminStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyEmployee;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayroll.QcbCompanyPayrollStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollFeedback;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords;
import cn.zeppin.product.ntb.core.entity.QcbCompanyPayrollRecords.QcbCompanyPayrollRecordsStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployee.QcbEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollFeedbackService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollRecordsService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyPayrollService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyPayrollFeedbackVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyPayrollVO;
import cn.zeppin.product.utility.IDCardUtil;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.JuHeUtility;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.PasswordCreator;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 企财宝企业发放薪资
 */

@Controller
@RequestMapping(value = "/qcb/companyPayroll")
public class QcbCompanyPayrollController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbCompanyEmployeeService qcbCompanyEmployeeService;
	
	@Autowired
	private IQcbCompanyPayrollService qcbCompanyPayrollService;
	
	@Autowired
	private IQcbCompanyPayrollRecordsService qcbCompanyPayrollRecordsService;
	
	@Autowired
	private IQcbCompanyPayrollFeedbackService qcbCompanyPayrollFeedbackService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IAliCertificationService aliCertificationService;
	
	/**
	 * 查询企财宝企业薪资发放记录列表
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(Integer pageNum, Integer pageSize, String sorts) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompany", admin.getQcbCompany());
		
		//查询符合条件的信息的总数
		Integer totalResultCount = qcbCompanyPayrollService.getCount(searchMap);
		//查询符合条件的信息列表
		List<Entity> list = qcbCompanyPayrollService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyPayroll.class);
		
		List<QcbCompanyPayrollVO> listVO = new ArrayList<QcbCompanyPayrollVO>();
		
		for(Entity e : list){
			QcbCompanyPayroll qcp = (QcbCompanyPayroll) e;
			QcbCompanyPayrollVO qcpVO = new QcbCompanyPayrollVO(qcp);
			
			if(!Utlity.checkStringNull(qcpVO.getQcbCompany())){
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcpVO.getQcbCompany());
				if(qca != null){
					qcpVO.setQcbCompanyName(qca.getName());
				}
			}
			
			if(!Utlity.checkStringNull(qcpVO.getSourcefile())){
				Resource r = this.resourceService.get(qcpVO.getSourcefile());
				if(r != null){
					qcpVO.setSourcefileName(r.getFilename()+ "." + r.getFiletype());
					qcpVO.setSourcefileUrl(r.getUrl());
				}
			}

			if(!Utlity.checkStringNull(qcpVO.getCreator())){
				QcbAdmin qa = this.qcbAdminService.get(qcpVO.getCreator());
				if(qa != null){
					qcpVO.setCreatorName(qa.getName());
				}
			}
			
			Map<String, String> searchCountMap = new HashMap<String, String>();
			searchCountMap.put("qcbCompanyPayroll", qcpVO.getUuid());
			
			Integer payrollCount = this.qcbCompanyPayrollRecordsService.getCount(searchCountMap);
			qcpVO.setPayrollCount(payrollCount);
			
			Integer feedbackCount = this.qcbCompanyPayrollFeedbackService.getCount(searchCountMap);
			qcpVO.setFeedbackCount(feedbackCount);
			
			searchCountMap.put("status", QcbCompanyPayrollRecordsStatus.CONFIRM);
			Integer comfirmCount = this.qcbCompanyPayrollRecordsService.getCount(searchCountMap);
			qcpVO.setComfirmCount(comfirmCount);
			
			listVO.add(qcpVO);
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取企财宝企业薪资发放记录
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//获取企业福利金发放记录
		QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(uuid);
		if (qcp == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!qcp.getQcbCompany().equals(admin.getQcbCompany())){
			return ResultManager.createFailResult("您无权查看此记录！");
		}
		
		//界面返回封装对象
		QcbCompanyPayrollVO qcpVO = new QcbCompanyPayrollVO(qcp);
		
		if(!Utlity.checkStringNull(qcpVO.getQcbCompany())){
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcpVO.getQcbCompany());
			if(qca != null){
				qcpVO.setQcbCompanyName(qca.getName());
			}
		}
		
		if(!Utlity.checkStringNull(qcpVO.getSourcefile())){
			Resource r = this.resourceService.get(qcpVO.getSourcefile());
			if(r != null){
				qcpVO.setSourcefileName(r.getFilename()+ "." + r.getFiletype());
				qcpVO.setSourcefileUrl(r.getUrl());
			}
		}

		if(!Utlity.checkStringNull(qcpVO.getCreator())){
			QcbAdmin qa = this.qcbAdminService.get(qcpVO.getCreator());
			if(qa != null){
				qcpVO.setCreatorName(qa.getName());
			}
		}
		
		Map<String, String> searchCountMap = new HashMap<String, String>();
		searchCountMap.put("qcbCompanyPayroll", qcpVO.getUuid());
		
		Integer payrollCount = this.qcbCompanyPayrollRecordsService.getCount(searchCountMap);
		qcpVO.setPayrollCount(payrollCount);
		
		Integer feedbackCount = this.qcbCompanyPayrollFeedbackService.getCount(searchCountMap);
		qcpVO.setFeedbackCount(feedbackCount);
		
		searchCountMap.put("status", QcbCompanyPayrollRecordsStatus.CONFIRM);
		Integer comfirmCount = this.qcbCompanyPayrollRecordsService.getCount(searchCountMap);
		qcpVO.setComfirmCount(comfirmCount);
		
		return ResultManager.createDataResult(qcpVO);
	}
	
	/**
	 * 上传薪资发放文件
	 * @param title
	 * @param payTime
	 * @param type
	 * @param remark
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ActionParam(key = "title", message="标题", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "payTime", message="发放时间", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="发放类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "remark", message="激励话语", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "file", message = "上传文件", type = DataType.STRING, required = true)
	@ResponseBody
	public Result upload(String title, String payTime, String type, String remark, String file, HttpServletRequest request) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("企财宝企业不存在！");
		}
		
		if(!QcbCompanyAccountStatus.NORMAL.equals(qca.getStatus())){
			return ResultManager.createFailResult("企业尚未通过认证，无法进行薪资发放!");
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
				message = "薪资发放文件中没有发放数据！";
				return ResultManager.createFailResult(message);
			}
			
			row = s.getRow(0);
			
			if(row.getCell(0) == null || !"姓名".equals(row.getCell(0).getStringCellValue())){
				message = "前四列标题必须按照模板格式填写，第一列内容必须是姓名！";
				return ResultManager.createFailResult(message);
			}
			
			if(row.getCell(1) == null || !"身份证号".equals(row.getCell(1).getStringCellValue())){
				message = "前四列标题必须按照模板格式填写，第二列内容必须是身份证号！";
				return ResultManager.createFailResult(message);
			}
			
			if(row.getCell(2) == null || !"手机号".equals(row.getCell(2).getStringCellValue())){
				message = "前四列标题必须按照模板格式填写，第三列内容必须是手机号！";
				return ResultManager.createFailResult(message);
			}
			
			if(row.getCell(3) == null || !"实发金额".equals(row.getCell(3).getStringCellValue())){
				message = "前四列标题必须按照模板格式填写，第四列内容必须是实发金额！";
				return ResultManager.createFailResult(message);
			}
			row.getCell(0).setCellType(CellType.STRING);
			row.getCell(1).setCellType(CellType.STRING);
			row.getCell(2).setCellType(CellType.STRING);
			row.getCell(3).setCellType(CellType.STRING);
			
			QcbCompanyPayroll qcp = new QcbCompanyPayroll();
			qcp.setUuid(UUID.randomUUID().toString());
			qcp.setQcbCompany(qca.getUuid());
			qcp.setTitle(title);
			qcp.setType(type);
			qcp.setSourcefile(file);
			Resource resource = this.resourceService.get(file);
			if(resource != null){
				resource.setFilename(title);
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("上传文件失败，请重新上传");
				}
				qcp.setSourcefile(file);
			}
			qcp.setPayTime(Timestamp.valueOf(payTime));
			qcp.setRemark(remark);
			qcp.setStatus(QcbCompanyPayrollStatus.DRAFT);
			qcp.setCreator(admin.getUuid());
			qcp.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			ArrayList<String> coloumList = new ArrayList<String>();
			int lastColoum = s.getRow(0).getLastCellNum();
			if(lastColoum > 3){
				for(int i = 4; i<lastColoum; i++){
					if (row.getCell(i) != null) {
						row.getCell(i).setCellType(CellType.STRING);
						coloumList.add(row.getCell(i).getStringCellValue().trim());
					}else{
						coloumList.add("");
					}
				}
			}
			Boolean flagC = true;
			for(int i = coloumList.size() - 1; i>=0; i--){
				if("".equals(coloumList.get(i))){
					coloumList.remove(i);
				}else{
					flagC = false;
					lastColoum = i + 5;
					break;
				}
			}
			if(flagC){
				lastColoum = 4;
			}
			qcp.setColumnData(JSONUtils.obj2json(coloumList));
			
			List<Map<String, Object>> datasList = new ArrayList<Map<String, Object>>();
			
			List<WarningData> errorList = new ArrayList<WarningData>();																			
			BigDecimal totalPrice = BigDecimal.ZERO;
			for(int i = 1; i <= t; i++){
				row = s.getRow(i);
				if(row == null){
					continue;
				}
				String realname = "";
				String idcard = "";
				String mobile = "";
				String price = "";
				List<String> valueList = new ArrayList<String>();
				
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
					price = row.getCell(3).getStringCellValue().trim();
				}
				
				if(Utlity.checkStringNull(realname) && Utlity.checkStringNull(idcard) && Utlity.checkStringNull(mobile)  && Utlity.checkStringNull(price)){
					continue;
				}
				
				if(lastColoum > 3){
					for(int j = 4; j<lastColoum; j++){
						if (row.getCell(j) != null) {
							row.getCell(j).setCellType(CellType.STRING);
							String value = row.getCell(j).getStringCellValue();
							if(Utlity.isNumeric(value) && value.indexOf(".") > -1){
								value = Utlity.numFormat4UnitDetail(BigDecimal.valueOf(Double.valueOf(value)));
							}
							valueList.add(value);
						}else{
							valueList.add("");
						}
					}
				}
				
				if(realname == null || "".equals(realname)){
					errorList.add(new WarningData(i+"", "未填写姓名！"));
				}
				
				if(idcard == null || "".equals(idcard)){
					errorList.add(new WarningData(i+"", "未填写身份证号！"));
				}
//				else if(!Utlity.checkIdCard(idcard)){
//					errorList.add(new WarningData(i+"", "身份证号填写有误！"));
//				}
				
				if(mobile == null || "".equals(mobile)){
					errorList.add(new WarningData(i+"", "未填写手机号！"));
				}else if(!Utlity.isMobileNO(mobile)){
					errorList.add(new WarningData(i+"", "手机号填写有误！"));
				}
				
				if(price == null || "".equals(price)){
					errorList.add(new WarningData(i+"", "未填写实发金额！"));
				}else if (!Utlity.isNumeric(price) || Double.parseDouble(price) < 0) {
					errorList.add(new WarningData(i+"", "实发金额填写有误！"));
				}else{
					price = BigDecimal.valueOf(Double.parseDouble(price)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				}
				
				for(Map<String, Object> data : datasList){
					if(mobile.equals(data.get("mobile"))){
						errorList.add(new WarningData(i+"", "手机号为"+mobile+"的员工，发放存在多条发放记录！"));
						break;
					}
				}
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("row", i + "");
				dataMap.put("realname", realname);
				dataMap.put("idcard", idcard);
				dataMap.put("mobile", mobile);
				dataMap.put("price", price);
				dataMap.put("value", valueList);
				if(!Utlity.checkStringNull(price)){
					totalPrice = totalPrice.add(BigDecimal.valueOf(Double.parseDouble(price)));
				}
				datasList.add(dataMap);
			}
			
			if(totalPrice.compareTo(qca.getAccountPay()) > 0){
				message = "企业账户余额不足！";
				return ResultManager.createFailResult(message);
			}
			
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("cloumn", coloumList);
			
			Map<String, Object> totalDataMap = new HashMap<String, Object>();
			for(Map<String,Object> dataMap : datasList){
				totalDataMap.put(dataMap.get("row").toString(), dataMap);
			}
			
			totalMap.put("datasMap", totalDataMap);
			
			if(errorList.size() > 0){
				return ResultManager.createWarningResult(totalMap, errorList);
			}
			
			List<QcbCompanyPayrollRecords> qcprList = new ArrayList<QcbCompanyPayrollRecords>();
			for(Map<String,Object> dataMap : datasList){
				String rowIndex = dataMap.get("row").toString();
				String mobile = dataMap.get("mobile").toString();
				String idcard = dataMap.get("idcard").toString();
				String name = dataMap.get("realname").toString();
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
				Integer count = this.qcbCompanyEmployeeService.getCount(inputParams);
				if(count == 0){
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
				}
				
				QcbCompanyPayrollRecords qcpr = new QcbCompanyPayrollRecords();
				qcpr.setUuid(UUID.randomUUID().toString());
				qcpr.setQcbCompanyPayroll(qcp.getUuid());
				qcpr.setQcbCompany(qcp.getQcbCompany());
				qcpr.setQcbEmployee(qe.getUuid());
				qcpr.setPrice(BigDecimal.valueOf(Double.parseDouble(dataMap.get("price").toString())));
				qcpr.setValue(JSONUtils.obj2json(dataMap.get("value")));
				qcpr.setData(dataMap.get("row").toString());
				qcpr.setPayTime(qcp.getPayTime());
				qcpr.setStatus(QcbCompanyPayrollRecordsStatus.DRAFT);
				qcpr.setCreator(admin.getUuid());
				qcpr.setCreatetime(new Timestamp(System.currentTimeMillis()));
				qcprList.add(qcpr);
			}
			
			if(errorList.size() > 0){
				return ResultManager.createWarningResult(totalMap, errorList);
			}
			
			this.qcbCompanyPayrollService.insert(qcp, qcprList);
			totalDataMap.put("uuid", qcp.getUuid());
			return ResultManager.createDataResult(totalMap);
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("服务器繁忙，请稍后再试！");
		}
	}
	
	/**
	 * 补全薪资发放额外信息
	 * @param uuid
	 * @param reward
	 * @param flagSms
	 * @param flagHide
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "reward", message="特殊奖励", type = DataType.NUMBER)
	@ActionParam(key = "flagSms", message="是否短信通知", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagHide", message="是否隐藏空数据", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result update(String uuid, Integer reward, Boolean flagSms, Boolean flagHide) {
		//取员工信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//获取企业福利金发放记录
		QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(uuid);
		if (qcp == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!qcp.getQcbCompany().equals(admin.getQcbCompany())){
			return ResultManager.createFailResult("您无权查看此记录！");
		}
		
		List<String> columnList = JSONUtils.json2list(qcp.getColumnData(), String.class);
		if(reward != null && reward >= columnList.size()){
			return ResultManager.createFailResult("特殊奖励有误！");
		}
		
		qcp.setReward(reward);
		qcp.setFlagSms(flagSms);
		qcp.setFlagHide(flagHide);
		
		this.qcbCompanyPayrollService.update(qcp);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 确认薪资发放
	 * @param uuid
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "code", message="短信验证码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result submit(String uuid, String code) {
		//取员工信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//获取企业福利金发放记录
		QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(uuid);
		if (qcp == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!qcp.getQcbCompany().equals(admin.getQcbCompany())){
			return ResultManager.createFailResult("您无权查看此记录！");
		}
		
		//验证短信验证码
		Map<String, String> codeParams = new HashMap<String, String>();
		codeParams.put("mobile", admin.getMobile());
		codeParams.put("status", MobileCodeStatus.NORMAL);
		codeParams.put("type", MobileCodeTypes.QCB_PAYROLL);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(codeParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(admin.getMobile())){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
		if(Utlity.checkCodeTime(mc.getCreatetime().getTime(), System.currentTimeMillis())){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		qcp.setStatus(QcbCompanyPayrollStatus.SUBMIT);
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeService.update(mc);
		
		this.qcbCompanyPayrollService.update(qcp);
		
		return ResultManager.createSuccessResult("提交成功！");
	}
	
	/**
	 * 查询企财宝企业薪资发放反馈列表
	 * @param qcbCompanyPayroll
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/feedbackList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String uuid, Integer pageNum, Integer pageSize, String sorts) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//获取企业福利金发放记录
		QcbCompanyPayroll qcp = this.qcbCompanyPayrollService.get(uuid);
		if (qcp == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!qcp.getQcbCompany().equals(admin.getQcbCompany())){
			return ResultManager.createFailResult("您无权查看此记录！");
		}
				
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompanyPayroll", qcp.getUuid());
		
		//查询符合条件的信息的总数
		Integer totalResultCount = qcbCompanyPayrollFeedbackService.getCount(searchMap);
		//查询符合条件的信息列表
		List<Entity> list = qcbCompanyPayrollFeedbackService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyPayrollFeedback.class);
		
		List<QcbCompanyPayrollFeedbackVO> listVO = new ArrayList<QcbCompanyPayrollFeedbackVO>();
		
		for(Entity e : list){
			QcbCompanyPayrollFeedback qcpf = (QcbCompanyPayrollFeedback) e;
			QcbCompanyPayrollFeedbackVO qcpfVO = new QcbCompanyPayrollFeedbackVO(qcpf);
			
			QcbEmployee qe = this.qcbEmployeeService.get(qcpf.getCreator());
			if(qe != null){
				qcpfVO.setCreatorName(qe.getRealname());
			}
			
			listVO.add(qcpfVO);
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	public static void main(String[] args) {
		String value = "";
		System.out.println(Utlity.isNumeric(value) && value.indexOf(".") > -1);
	}
}
