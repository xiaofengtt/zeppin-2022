package cn.zeppin.product.ntb.backadmin.controller;

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

import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyHistoryWithdrawVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbEmployeeHistoryWithdrawVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeBankcard;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyBankcardService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeBankcardService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbOrderinfoByThirdpartyService;
import cn.zeppin.product.utility.Utlity;


/**
 * @author hehe
 *
 * ???????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/qcbWithdraw")
public class QcbWithdrawController extends BaseController {
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbEmployeeBankcardService qcbEmployeeBankcardService;
	
	@Autowired
	private IQcbCompanyBankcardService qcbCompanyBankcardService;
	
	@Autowired
	private IQcbOrderinfoByThirdpartyService qcbOrderinfoByThirdpartyService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * ???????????????company????????????????????????
	 * @param name
	 * @param status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/employeeList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result employeeList(String name, String status, Integer pageNum, Integer pageSize, String sorts){
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		if(status != null && !"".equals(status) && !"all".equals(status)){
			searchMap.put("processingStatus", status);
		}
		searchMap.put("type", QcbEmployeeHistoryType.WITHDRAW);
		Integer totalResultCount = this.qcbEmployeeHistoryService.getCountForWithdraw(searchMap);
		List<Entity> list = this.qcbEmployeeHistoryService.getListForWithdrawPage(searchMap, pageNum, pageSize, sorts, QcbEmployeeHistory.class);
		List<QcbEmployeeHistoryWithdrawVO> listVO = new ArrayList<QcbEmployeeHistoryWithdrawVO>();
		for(Entity e : list){
			QcbEmployeeHistory qeh = (QcbEmployeeHistory)e;
			QcbEmployeeHistoryWithdrawVO qehvo = new QcbEmployeeHistoryWithdrawVO(qeh);
			
			QcbEmployee qe = this.qcbEmployeeService.get(qeh.getQcbEmployee());
			if(qe != null){
				qehvo.setQcbEmployeeName(qe.getRealname());
			}
			
			if(!Utlity.checkStringNull(qehvo.getProcessCompanyAccount())){
				CompanyAccount ca = this.companyAccountService.get(qehvo.getProcessCompanyAccount());
				if(ca != null){
					qehvo.setProcessCompanyAccountName(ca.getAccountName());
				} else {
					qehvo.setProcessCompanyAccountName("-");
				}
			}
			
			if(!Utlity.checkStringNull(qehvo.getProcessCreator())){
				BkOperator op = this.bkOperatorService.get(qehvo.getProcessCreator());
				if(op != null){
					qehvo.setProcessCreatorName(op.getRealname());
				} else {
					qehvo.setProcessCreatorName("-");
				}
			}
			
			if(!Utlity.checkStringNull(qehvo.getOrder())){
				QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(qehvo.getOrder());
				if(qobt != null){
					qehvo.setOrderMessage(qobt.getMessage());
				} else {
					qehvo.setOrderMessage("");
				}
			}
			
			listVO.add(qehvo);
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/employeeStatusList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result employeeStatusList(String name){
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		List<Entity> list = this.qcbEmployeeHistoryService.getWithdrawStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????????????????????????????(??????)
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/employeeEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result employeeEdit(String uuid){
		String[] uuidArr = uuid.split(",");
		List<QcbEmployeeHistory> qehList = new ArrayList<QcbEmployeeHistory>();
		for(String uid : uuidArr){
			QcbEmployeeHistory qeh = this.qcbEmployeeHistoryService.get(uid);
			if(qeh == null){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			qehList.add(qeh);
		}
		//??????????????????
		try {
			HashMap<String, Object> result = this.qcbEmployeeHistoryService.updateWithdrawBatch(qehList);
			if((Boolean)result.get("result")){
				Integer count = Integer.parseInt(result.get("count").toString());
				return ResultManager.createSuccessResult("????????????????????????"+count+"???????????????");
			} else {
				return ResultManager.createFailResult("??????");
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????(??????)
	 * @param uuid
	 * @param status ????????????   processing--?????????
	 * @return
	 */
	@RequestMapping(value = "/employeeProcess", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result employeeProcess(String uuid, String status){
		String[] uuidArr = uuid.split(",");
		List<QcbEmployeeHistory> qehList = new ArrayList<QcbEmployeeHistory>();
		for(String uid : uuidArr){
			QcbEmployeeHistory qeh = this.qcbEmployeeHistoryService.get(uid);
			if(qeh == null){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			if(QcbEmployeeHistoryProcessStatus.PROCESSING.equals(status)){
				qeh.setProcessingStatus(status);
			} else {
				qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.PROCESSING);
			}
			qehList.add(qeh);
		}
		try {
			this.qcbEmployeeHistoryService.updateBatch(qehList);
			return ResultManager.createSuccessResult("????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????");
		}
		
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @return
	 */
	@RequestMapping(value = "/employeeExport", method = RequestMethod.GET)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING)
	@ResponseBody
	public ModelAndView employeeExport(String status, HttpServletRequest request, HttpServletResponse response){
		Map<String, String> searchMap = new HashMap<String, String>();
		if(status != null && !"".equals(status) && !"all".equals(status)){
			searchMap.put("processingStatus", status);
		}
		searchMap.put("type", QcbEmployeeHistoryType.WITHDRAW);
		List<Entity> list = this.qcbEmployeeHistoryService.getListForWithdrawPage(searchMap, -1, -1, null, QcbEmployeeHistory.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "????????????????????????");
			XSSFRow row = s.createRow(0);
			String title[] = { "??????", "????????????", "??????", "????????????", "????????????", "??????????????????", "??????????????????", "???????????????", "??????", "??????", "????????????", "????????????", "????????????????????????", "????????????????????????" };
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(Entity entity : list){
				t++;
				row = s.createRow(t);
				QcbEmployeeHistory qeh = (QcbEmployeeHistory)entity;
				QcbEmployeeHistoryWithdrawVO qehvo = new QcbEmployeeHistoryWithdrawVO(qeh);
				QcbEmployee qe = this.qcbEmployeeService.get(qeh.getQcbEmployee());
				
				String name = qe.getRealname();
				String price = qehvo.getPrice();
				QcbEmployeeBankcard qeb = this.qcbEmployeeBankcardService.get(qeh.getBankcard());
				String bankcardnum = qeb.getBindingBankCard().split("_#")[0];
				Bank bank = this.bankService.get(qeb.getBank());
				String bankName = bank.getName();
				String bankCode = bank.getCode();
				String orderTypeCN = qehvo.getOrderTypeCN();
				String application = qehvo.getApplication();
				String reason = "";
				QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(qeh.getOrderId());
				if(qobt != null){
					reason = qobt.getMessage();
				}
				String time = qehvo.getCreatetimeCN();
				String processingStatus = qehvo.getProcessingStatusCN();
				String companyAccountNum = "";
				String companyAccountName = "";
				
				String tInfo[] = { t+"", qeh.getUuid(), name, price, bankName, bankCode, bankcardnum, orderTypeCN, application, reason, time, processingStatus, companyAccountName, companyAccountNum };
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=withdrawList.xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (IOException e) {
			e.printStackTrace();
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
	 * ????????????????????????????????????????????????
	 * status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/employeeImport", method = RequestMethod.POST)
	@ActionParam(key = "file", message = "????????????UUID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result employeeImport(String file, HttpServletRequest request, HttpServletResponse response){
		String message = "???????????????";
		Resource r = this.resourceService.get(file);
		if(r == null){
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		try {
			String resourcePath = r.getUrl();
			String serverPath = request.getServletContext().getRealPath("/").replace("\\", "/");
			File realfile = new File(serverPath + resourcePath);
			InputStream is = null;
			boolean isE2007 = false; // ???????????????excel2007??????
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
				return ResultManager.createFailResult("?????????????????????????????????");
			}
			if (isE2007) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			Sheet s = wb.getSheetAt(0);
			Row row;
			int t = s.getLastRowNum();
			List<QcbEmployeeHistory> qehList = new ArrayList<QcbEmployeeHistory>();
			Map<String, QcbEmployeeHistory> qehMap = new HashMap<String, QcbEmployeeHistory>();
			
			for(int i = 1; i <= t; i++){
				row = s.getRow(i);
				String orderNum = "";
				String companyAccountName = "";
				String companyAccountNum = "";
				
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(CellType.STRING);
					orderNum = row.getCell(1).getStringCellValue();
				}
				
				if (row.getCell(12) != null) {
					row.getCell(12).setCellType(CellType.STRING);
					companyAccountName = row.getCell(12).getStringCellValue();
				}
				
				if (row.getCell(13) != null) {
					row.getCell(13).setCellType(CellType.STRING);
					companyAccountNum = row.getCell(13).getStringCellValue();
				}
				
				if(orderNum == null || "".equals(orderNum)){
					message = "???"+i+"??????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(companyAccountName == null || "".equals(companyAccountName)){
					message = "???"+i+"??????????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(companyAccountNum == null || "".equals(companyAccountNum)){
					message = "???"+i+"??????????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(qehMap.containsKey(orderNum)){
					message = "???"+i+"????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				QcbEmployeeHistory qeh = this.qcbEmployeeHistoryService.get(orderNum);
				
				if(qeh == null){
					return ResultManager.createFailResult("??????????????????");
				}
				if(!QcbEmployeeHistoryType.WITHDRAW.equals(qeh.getType())){
					return ResultManager.createFailResult("?????????????????????");
				}
				if(QcbEmployeeHistoryStatus.SUCCESS.equals(qeh.getStatus())){
					continue;
				}
				qeh.setPoundage(BigDecimal.ZERO);
				qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
				qeh.setProcessCreator(currentOperator.getUuid());
				qeh.setProcessCreatetime(new Timestamp(System.currentTimeMillis()));
				qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.SUCCESS);
				
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("accountName", companyAccountName);
				inputParams.put("accountNum", companyAccountNum);
				List<Entity> listCa = this.companyAccountService.getListForPage(inputParams, -1, -1, null, CompanyAccount.class);
				if(listCa != null && !listCa.isEmpty()){
					CompanyAccount ca = (CompanyAccount) listCa.get(0);
					qeh.setProcessCompanyAccount(ca.getUuid());
				} else {
					return ResultManager.createFailResult("?????????????????????");
				}
			}
			if(!qehMap.isEmpty()){
				for(QcbEmployeeHistory qeh : qehMap.values()){
					qehList.add(qeh);
				}
			}
			this.qcbEmployeeHistoryService.updateWithdraw(qehList);
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????");
		}
		
		return ResultManager.createSuccessResult("????????????");
	}

	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/employeeRevoke", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result employeeRevoke(String uuid) {
		String[] uuidArr = uuid.split(",");
		List<QcbEmployeeHistory> qehList = new ArrayList<QcbEmployeeHistory>();
		for(String uid : uuidArr){
			QcbEmployeeHistory qeh = this.qcbEmployeeHistoryService.get(uid);
			if(qeh == null){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			qehList.add(qeh);
		}
		
		//??????????????????
		try {
			
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			List<QcbEmployeeHistory> qehListProcess = new ArrayList<QcbEmployeeHistory>();
			for(QcbEmployeeHistory qeh : qehList){
				qeh.setStatus(QcbEmployeeHistoryStatus.FAIL);
				qeh.setProcessingStatus(QcbEmployeeHistoryProcessStatus.SUCCESS);
				qeh.setProcessCreator(currentOperator.getUuid());
				qeh.setProcessCreatetime(new Timestamp(System.currentTimeMillis()));
				qehListProcess.add(qeh);
			}
			this.qcbEmployeeHistoryService.updateWithdrawBatchRevoke(qehListProcess);
			return ResultManager.createSuccessResult("???????????????");
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????");
		}
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param name
	 * @param status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/companyList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result companyList(String name, String status, Integer pageNum, Integer pageSize, String sorts){
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		if(status != null && !"".equals(status) && !"all".equals(status)){
			searchMap.put("processingStatus", status);
		}
		searchMap.put("type", QcbCompanyAccountHistoryType.WITHDRAW);
		Integer totalResultCount = this.qcbCompanyAccountHistoryService.getCount(searchMap);
		List<Entity> list = this.qcbCompanyAccountHistoryService.getListForWithdrawPage(searchMap, pageNum, pageSize, sorts, QcbCompanyAccountHistory.class);
		List<QcbCompanyHistoryWithdrawVO> listVO = new ArrayList<QcbCompanyHistoryWithdrawVO>();
		for(Entity e : list){
			QcbCompanyAccountHistory qcah = (QcbCompanyAccountHistory)e;
			QcbCompanyHistoryWithdrawVO qcahvo = new QcbCompanyHistoryWithdrawVO(qcah);
			
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
			if(qca != null){
				qcahvo.setQcbCompanyAccountName(qca.getName());
			}
			
			if(!Utlity.checkStringNull(qcahvo.getProcessCompanyAccount())){
				CompanyAccount ca = this.companyAccountService.get(qcahvo.getProcessCompanyAccount());
				if(ca != null){
					qcahvo.setProcessCompanyAccountName(ca.getAccountName());
				} else {
					qcahvo.setProcessCompanyAccountName("-");
				}
			}
			
			if(!Utlity.checkStringNull(qcahvo.getProcessCreator())){
				BkOperator op = this.bkOperatorService.get(qcahvo.getProcessCreator());
				if(op != null){
					qcahvo.setProcessCreatorName(op.getRealname());
				} else {
					qcahvo.setProcessCreatorName("-");
				}
			}
			
			if(!Utlity.checkStringNull(qcahvo.getOrder())){
				QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(qcahvo.getOrder());
				if(qobt != null){
					qcahvo.setOrderMessage(qobt.getMessage());
				} else {
					qcahvo.setOrderMessage("");
				}
			}
			
			listVO.add(qcahvo);
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/companyStatusList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result companyStatusList(String name){
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(name != null && !"".equals(name)){
			searchMap.put("name", name);
		}
		
		List<Entity> list = this.qcbCompanyAccountHistoryService.getWithdrawStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????????????????????????????(??????)
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/companyEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result companyEdit(String uuid){
		String[] uuidArr = uuid.split(",");
		List<QcbCompanyAccountHistory> qcahList = new ArrayList<QcbCompanyAccountHistory>();
		for(String uid : uuidArr){
			QcbCompanyAccountHistory qcah = this.qcbCompanyAccountHistoryService.get(uid);
			if(qcah == null){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			qcahList.add(qcah);
		}
		//??????????????????
		try {
			HashMap<String, Object> result = this.qcbCompanyAccountHistoryService.updateWithdrawBatch(qcahList);
			if((Boolean)result.get("result")){
				Integer count = Integer.parseInt(result.get("count").toString());
				return ResultManager.createSuccessResult("????????????????????????"+count+"???????????????");
			} else {
				return ResultManager.createFailResult("??????");
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????(??????)
	 * @param uuid
	 * @param status ????????????   processing--?????????
	 * @return
	 */
	@RequestMapping(value = "/companyProcess", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result companyProcess(String uuid, String status){
		String[] uuidArr = uuid.split(",");
		List<QcbCompanyAccountHistory> qcahList = new ArrayList<QcbCompanyAccountHistory>();
		for(String uid : uuidArr){
			QcbCompanyAccountHistory qcah = this.qcbCompanyAccountHistoryService.get(uid);
			if(qcah == null){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			if(QcbCompanyAccountHistoryProcessStatus.PROCESSING.equals(status)){
				qcah.setProcessingStatus(status);
			} else {
				qcah.setProcessingStatus(QcbCompanyAccountHistoryProcessStatus.PROCESSING);
			}
			qcahList.add(qcah);
		}
		try {
			this.qcbCompanyAccountHistoryService.updateBatch(qcahList);
			return ResultManager.createSuccessResult("????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????");
		}
		
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @return
	 */
	@RequestMapping(value = "/companyExport", method = RequestMethod.GET)
	@ActionParam(key = "status", message = "??????", type = DataType.STRING)
	@ResponseBody
	public ModelAndView companyExport(String status, HttpServletRequest request, HttpServletResponse response){
		Map<String, String> searchMap = new HashMap<String, String>();
		if(status != null && !"".equals(status) && !"all".equals(status)){
			searchMap.put("processingStatus", status);
		}
		searchMap.put("type", QcbCompanyAccountHistoryType.WITHDRAW);
		List<Entity> list = this.qcbCompanyAccountHistoryService.getListForWithdrawPage(searchMap, -1, -1, null, QcbCompanyAccountHistory.class);
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "????????????????????????");
			XSSFRow row = s.createRow(0);
			String title[] = { "??????", "????????????", "??????", "????????????", "????????????", "??????????????????", "??????????????????", "???????????????", "??????", "??????", "????????????", "????????????", "????????????????????????", "????????????????????????" };
			for (int j = 0; j < title.length; j++) {
				row.createCell(j).setCellValue(title[j]);
			}
			int t = 0;
			for(Entity entity : list){
				t++;
				row = s.createRow(t);
				QcbCompanyAccountHistory qcah = (QcbCompanyAccountHistory)entity;
				QcbCompanyHistoryWithdrawVO qcahvo = new QcbCompanyHistoryWithdrawVO(qcah);
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
				
				String name = qca.getName();
				String price = qcahvo.getPrice();
				QcbCompanyBankcard qcab = this.qcbCompanyBankcardService.get(qcah.getBankcard());
				String bankcardnum = qcab.getBindingBankCard().split("_#")[0];
				Bank bank = this.bankService.get(qcab.getBank());
				String bankName = bank.getName();
				String bankCode = bank.getCode();
				String orderTypeCN = qcahvo.getOrderTypeCN();
				String application = qcahvo.getApplication();
				String reason = "";
				QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(qcah.getOrderId());
				if(qobt != null){
					reason = qobt.getMessage();
				}
				String time = qcahvo.getCreatetimeCN();
				String processingStatus = qcahvo.getProcessingStatusCN();
				String companyAccountNum = "";
				String companyAccountName = "";
				
				String tInfo[] = { t+"", qcah.getUuid(), name, price, bankName, bankCode, bankcardnum, orderTypeCN, application, reason, time, processingStatus, companyAccountName, companyAccountNum };
				for (int j = 0; j < tInfo.length; j++) {
					row.createCell(j).setCellValue(tInfo[j]);
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=withdrawList.xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (IOException e) {
			e.printStackTrace();
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
	 * ????????????????????????????????????????????????
	 * status ????????????   processing--????????? unprocess--????????? success--???????????? fail--????????????
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/companyImport", method = RequestMethod.POST)
	@ActionParam(key = "file", message = "????????????UUID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result companyImport(String file, HttpServletRequest request, HttpServletResponse response){
		String message = "???????????????";
		Resource r = this.resourceService.get(file);
		if(r == null){
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		try {
			String resourcePath = r.getUrl();
			String serverPath = request.getServletContext().getRealPath("/").replace("\\", "/");
			File realfile = new File(serverPath + resourcePath);
			InputStream is = null;
			boolean isE2007 = false; // ???????????????excel2007??????
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
				return ResultManager.createFailResult("?????????????????????????????????");
			}
			if (isE2007) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			Sheet s = wb.getSheetAt(0);
			Row row;
			int t = s.getLastRowNum();
			List<QcbCompanyAccountHistory> qcahList = new ArrayList<QcbCompanyAccountHistory>();
			Map<String, QcbCompanyAccountHistory> qcahMap = new HashMap<String, QcbCompanyAccountHistory>();
			
			for(int i = 1; i <= t; i++){
				row = s.getRow(i);
				String orderNum = "";
				String companyAccountName = "";
				String companyAccountNum = "";
				
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(CellType.STRING);
					orderNum = row.getCell(1).getStringCellValue();
				}
				
				if (row.getCell(12) != null) {
					row.getCell(12).setCellType(CellType.STRING);
					companyAccountName = row.getCell(12).getStringCellValue();
				}
				
				if (row.getCell(13) != null) {
					row.getCell(13).setCellType(CellType.STRING);
					companyAccountNum = row.getCell(13).getStringCellValue();
				}
				
				if(orderNum == null || "".equals(orderNum)){
					message = "???"+i+"??????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(companyAccountName == null || "".equals(companyAccountName)){
					message = "???"+i+"??????????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(companyAccountNum == null || "".equals(companyAccountNum)){
					message = "???"+i+"??????????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				
				if(qcahMap.containsKey(orderNum)){
					message = "???"+i+"????????????????????????????????????";
					return ResultManager.createFailResult(message);
				}
				QcbCompanyAccountHistory qcah = this.qcbCompanyAccountHistoryService.get(orderNum);
				
				if(qcah == null){
					return ResultManager.createFailResult("??????????????????");
				}
				if(!QcbCompanyAccountHistoryType.WITHDRAW.equals(qcah.getType())){
					return ResultManager.createFailResult("?????????????????????");
				}
				if(QcbCompanyAccountHistoryStatus.SUCCESS.equals(qcah.getStatus())){
					continue;
				}
				qcah.setPoundage(BigDecimal.ZERO);
				qcah.setStatus(QcbCompanyAccountHistoryStatus.SUCCESS);
				qcah.setProcessCreator(currentOperator.getUuid());
				qcah.setProcessCreatetime(new Timestamp(System.currentTimeMillis()));
				qcah.setProcessingStatus(QcbCompanyAccountHistoryProcessStatus.SUCCESS);
				
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("accountName", companyAccountName);
				inputParams.put("accountNum", companyAccountNum);
				List<Entity> listCa = this.companyAccountService.getListForPage(inputParams, -1, -1, null, CompanyAccount.class);
				if(listCa != null && !listCa.isEmpty()){
					CompanyAccount ca = (CompanyAccount) listCa.get(0);
					qcah.setProcessCompanyAccount(ca.getUuid());
				} else {
					return ResultManager.createFailResult("?????????????????????");
				}
			}
			if(!qcahMap.isEmpty()){
				for(QcbCompanyAccountHistory qcah : qcahMap.values()){
					qcahList.add(qcah);
				}
			}
			this.qcbCompanyAccountHistoryService.updateWithdraw(qcahList);
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????");
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("??????????????????");
		}
		
		return ResultManager.createSuccessResult("????????????");
	}

	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/companyRevoke", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result companyRevoke(String uuid) {
		String[] uuidArr = uuid.split(",");
		List<QcbCompanyAccountHistory> qcahList = new ArrayList<QcbCompanyAccountHistory>();
		for(String uid : uuidArr){
			QcbCompanyAccountHistory qcah = this.qcbCompanyAccountHistoryService.get(uid);
			if(qcah == null){
				return ResultManager.createFailResult("????????????????????????????????????????????????");
			}
			qcahList.add(qcah);
		}
		//??????????????????
		try {
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			List<QcbCompanyAccountHistory> qcahListProcess = new ArrayList<QcbCompanyAccountHistory>();
			for(QcbCompanyAccountHistory qcah : qcahList){
				qcah.setStatus(QcbEmployeeHistoryStatus.FAIL);
				qcah.setProcessingStatus(QcbEmployeeHistoryProcessStatus.SUCCESS);
				qcah.setProcessCreator(currentOperator.getUuid());
				qcah.setProcessCreatetime(new Timestamp(System.currentTimeMillis()));
				qcahListProcess.add(qcah);
			}
			this.qcbCompanyAccountHistoryService.updateWithdrawBatchRevoke(qcahList);
			return ResultManager.createSuccessResult("???????????????");
		} catch (TransactionException e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
			return ResultManager.createFailResult("?????????????????????");
		}
	}
}
