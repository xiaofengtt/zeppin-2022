/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountVirtualAccountType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate.QcbCompanyOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate.QcbCompanyOperateType;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyOperateService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbVirtualAccountsService;
import cn.zeppin.product.ntb.qcb.utility.QcbMessageUtil;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAccountDetailVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAccountIndexVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyAccountInfoVO;
import cn.zeppin.product.ntb.qcb.vo.QcbTransferCountVO;
import cn.zeppin.product.ntb.qcb.vo.QcbVirtualAccountsVO;
import cn.zeppin.product.utility.BankCardUtlity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * ???????????????????????????
 */

@Controller
@RequestMapping(value = "/qcb/companyAccount")
public class QcbCompanyAccountController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbVirtualAccountsService qcbVirtualAccountsService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IBkAreaService bkAreaService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IResourceService resourceService;

	@Autowired
	private IQcbCompanyEmployeeService qcbCompanyEmployeeService;
	
	@Autowired
	private IQcbCompanyOperateService qcbCompanyOperateService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	/**
	 * ??????session
	 * @return
	 */
	@RequestMapping(value = "/flush", method = RequestMethod.GET)
	@ResponseBody
	public Result flush(){
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		if(admin != null){
			//????????????????????????
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
			if(qca == null){
				return ResultManager.createFailResult("??????????????????");
			}	
			admin.setQcbCompanyName(qca.getName());
			admin.setQcbCompanyStatus(qca.getStatus());
			session.setAttribute("currentQcbOperator", admin);
			return ResultManager.createSuccessResult("success???");
		}else{
			return ResultManager.createSuccessResult("failed???");
		}
	}
	
	/**
	 * ????????????
	 * @return
	 */
	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getInfo(){
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		QcbCompanyAccountIndexVO qcaivo = new QcbCompanyAccountIndexVO(qca);
		
		//?????????????????????????????????
		//?????????????????????????????????????????????????????????xInfo
		Calendar c = Calendar.getInstance();
		Date end = c.getTime();
		c.setTime(end);
		c.add(Calendar.YEAR, -1);
		Date start = c.getTime();
		
		List<Date> listDate = Utlity.findMonthDates(start, end);
		List<String> listDateStr = new ArrayList<String>();
		Map<String, String> mapPay = new HashMap<String, String>();
		for(Date date : listDate){
			listDateStr.add(Utlity.timeSpanToChinaStringesLess(date));
			mapPay.put(Utlity.timeSpanToChinaStringesLess(date), "0.00"); 
		}
		String starttime = Utlity.timeSpanToDateString(start);
		String endtime = Utlity.timeSpanToDateString(end);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("qcbCompany", qca.getUuid());
		inputParams.put("status", QcbCompanyAccountHistoryStatus.SUCCESS);
		inputParams.put("starttime", starttime);
		inputParams.put("endtime", endtime);
		inputParams.put("countType", "month");
		List<Entity> list = this.qcbCompanyAccountHistoryService.getListForCountPage(inputParams, -1, -1, null, QcbTransferCountVO.class);
		for(Entity entity : list){//???????????????
			QcbTransferCountVO qtc = (QcbTransferCountVO)entity;
			mapPay.put(qtc.getCreatetime(), qtc.getPay().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		List<Double> listPay = new ArrayList<Double>();
		for(String dateStr : listDateStr){
			listPay.add(Double.parseDouble(mapPay.get(dateStr)));
		}
		//????????????????????????
		Map<String, Object> mapPayInfo = new HashMap<String, Object>();
		mapPayInfo.put("xInfo", listDateStr);
		mapPayInfo.put("yInfo", listPay);
		qcaivo.setMapPayInfo(mapPayInfo);
		
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompany", qca.getUuid());
		
		//????????????
		Integer totalResultCount = this.qcbCompanyEmployeeService.getCountForList(searchMap);
		qcaivo.setCountEmployee(totalResultCount);
		
		return ResultManager.createDataResult(qcaivo);
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value = "/getAccountInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getAccountInfo(){
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		QcbCompanyAccountInfoVO qcaiVO = new QcbCompanyAccountInfoVO(qca);
		
		return ResultManager.createDataResult(qcaiVO);
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/getVirtualAccount", method = RequestMethod.GET)
	@ResponseBody
	public Result getVirtualAccount() {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(Utlity.checkStringNull(qca.getQcbVirtualAccounts())){
			return ResultManager.createFailResult("???????????????????????????????????????????????????");
		}
		if(QcbCompanyAccountVirtualAccountType.VIRTUAL.equals(qca.getVirtualAccountType())){
			QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
			if(qva == null){
				return ResultManager.createFailResult("???????????????????????????");
			}
			
			QcbVirtualAccountsVO qvaVO = new QcbVirtualAccountsVO(qva);
		
			CompanyAccount ca = this.companyAccountService.get(qvaVO.getCompanyAccount());
			if(ca == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			qvaVO.setCompanyAccountName(ca.getCompanyName());
			qvaVO.setCompanyAccountNum(ca.getAccountNum());
			
			if(!Utlity.checkStringNull(ca.getBranchBank())){
				BranchBank bb = this.branchBankService.get(ca.getBranchBank());
				if(bb != null){
					qvaVO.setBranchBankName(bb.getName());
					qvaVO.setBranchBankAddress(bb.getAddress());
				}
			}
			
			return ResultManager.createDataResult(qvaVO);
		}else if(QcbCompanyAccountVirtualAccountType.REALLY.equals(qca.getVirtualAccountType())){
			CompanyAccount ca = this.companyAccountService.get(qca.getQcbVirtualAccounts());
			if(ca == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			QcbVirtualAccountsVO qvaVO = new QcbVirtualAccountsVO();
			qvaVO.setQcbCompany(qca.getUuid());
			
			qvaVO.setCompanyAccount(ca.getUuid());
			qvaVO.setCompanyAccountName(ca.getCompanyName());
			qvaVO.setCompanyAccountNum(ca.getAccountNum());
			qvaVO.setAccountNum("");
			if(!Utlity.checkStringNull(ca.getBranchBank())){
				BranchBank bb = this.branchBankService.get(ca.getBranchBank());
				if(bb != null){
					qvaVO.setBranchBankName(bb.getName());
					qvaVO.setBranchBankAddress(bb.getAddress());
				}
			}
			return ResultManager.createDataResult(qvaVO);
		}else{
			return ResultManager.createFailResult("??????????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get() {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		QcbCompanyAccountDetailVO vo = new QcbCompanyAccountDetailVO(qca);
		//??????????????????
		String bkAreaCN = "";
		Map<String, Object> areaInfo = new HashMap<String, Object>();
		if( qca.getBkArea() != null) {
			BkArea area = this.bkAreaService.get(qca.getBkArea());
			if(area.getLevel() == 3){
				BkArea city = this.bkAreaService.get(area.getPid());
				BkArea province = this.bkAreaService.get(city.getPid());
				areaInfo.put("province", province);
				areaInfo.put("city", city);
				areaInfo.put("county", area);
				bkAreaCN+=province.getName()+city.getName()+area.getName();
			} else if(area.getLevel() == 2){
				BkArea province = this.bkAreaService.get(area.getPid());
				areaInfo.put("province", province);
				areaInfo.put("city", area);
				areaInfo.put("county", null);
				bkAreaCN+=province.getName()+area.getName();
			} else if(area.getLevel() == 1){
				areaInfo.put("province", area);
				areaInfo.put("city", null);
				areaInfo.put("county", null);
				bkAreaCN+=area.getName();
			} else {
				areaInfo.put("province", null);
				areaInfo.put("city", null);
				areaInfo.put("county", null);
			}
		}
		vo.setBkAreaCN(bkAreaCN);
		vo.setAreaInfo(areaInfo);
		
		//??????logo??????
		String logoUrl = "";
		if(qca.getLogo() != null){
			Resource logo = this.resourceService.get(qca.getLogo());
			if(logo != null){
				logoUrl = logo.getUrl();
			}
		}
		vo.setLogoUrl(logoUrl);
		
		//????????????
		if(!Utlity.checkStringNull(qca.getBusinessLicence())){
			Resource bl = this.resourceService.get(qca.getBusinessLicence());
			if(bl != null){
				vo.setBusinessLicenceURL(bl.getUrl());
			}
		}
		
		if(!Utlity.checkStringNull(qca.getEvidence())){
			Resource ev = this.resourceService.get(qca.getEvidence());
			if(ev != null){
				vo.setEvidenceURL(ev.getUrl());
			}
		}
		
		if(!Utlity.checkStringNull(qca.getIdcardFace())){
			Resource idf = this.resourceService.get(qca.getIdcardFace());
			if(idf != null){
				vo.setIdcardFaceURL(idf.getUrl());
			}
		}

		if(!Utlity.checkStringNull(qca.getIdcardBack())){
			Resource idb = this.resourceService.get(qca.getIdcardBack());
			if(idb != null){
				vo.setIdcardBackURL(idb.getUrl());
			}
		}
		
		//??????????????????
		if(!QcbCompanyAccountStatus.UNAUTH.equals(vo.getStatus())){
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("status", QcbCompanyOperateStatus.CHECKED);
			searchMap.put("qcbCompany", qca.getUuid());
			List<Entity> qcocList = this.qcbCompanyOperateService.getListForPage(searchMap, -1, -1, null, QcbCompanyOperate.class);
			
			if(qcocList.size() > 0){
				QcbCompanyOperate qcoc = (QcbCompanyOperate) qcocList.get(0);
				
				if(QcbCompanyOperateStatus.CHECKED.equals(qcoc.getStatus())){
					vo.setCheckStatusCN("????????????");
				}else if(QcbCompanyOperateStatus.UNPASSED.equals(qcoc.getStatus())){
					vo.setCheckStatusCN("???????????????");
				}else if(QcbCompanyOperateStatus.UNCHECKED.equals(qcoc.getStatus())){
					vo.setCheckStatusCN("?????????");
				}
				vo.setCheckReason(qcoc.getReason());
				vo.setChecktimeCN(Utlity.timeSpanToChinaString(qcoc.getCreatetime()));
				BkOperator checker = this.bkOperatorService.get(qcoc.getChecker());
				if(qcoc != null){
					vo.setCheckerName(checker.getRealname());	
				}
			}
			
		}
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * ????????????
	 * @param company
	 * @param area
	 * @param address
	 * @param phone
	 * @param corporation
	 * @param connectionCompany
	 * @param logo
	 * @param taxIdentificationNum
	 * @param taxCompany
	 * @param taxAddress
	 * @param taxPhone
	 * @param openBank
	 * @param openBankCardnum
	 * @param name
	 * @param mobile
	 * @param idcard
	 * @param email
	 * @param businessLicence
	 * @param evidence
	 * @param idcardFace
	 * @param idcardBack
	 * @return
	 */
	@RequestMapping(value = "/editBase", method = RequestMethod.POST)
	@ActionParam(key = "company", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "area", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "address", message="??????", type = DataType.STRING)
	@ActionParam(key = "phone", message="??????", type = DataType.STRING)
	@ActionParam(key = "corporation", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "connectionCompany", message="????????????", type = DataType.STRING)
	@ActionParam(key = "logo", message="??????LOGO", type = DataType.STRING)
	@ActionParam(key = "taxIdentificationNum", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "taxCompany", message="????????????", type = DataType.STRING)
	@ActionParam(key = "taxAddress", message="????????????", type = DataType.STRING)
	@ActionParam(key = "taxPhone", message="??????", type = DataType.STRING)
	@ActionParam(key = "openBank", message="?????????", type = DataType.STRING)
	@ActionParam(key = "openBankCardnum", message="????????????", type = DataType.STRING)
	@ActionParam(key = "name", message="?????????", type = DataType.STRING)
	@ActionParam(key = "mobile", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "idcard", message="????????????", type = DataType.STRING)
	@ActionParam(key = "email", message="????????????", type = DataType.STRING)
	@ActionParam(key = "businessLicence", message="????????????", type = DataType.STRING)
	@ActionParam(key = "evidence", message="????????????", type = DataType.STRING)
	@ActionParam(key = "idcardFace", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "idcardBack", message="???????????????", type = DataType.STRING)
	@ResponseBody
	public Result editBase(String company, String area, String address, String phone, String corporation, String connectionCompany, String logo, 
			String taxIdentificationNum, String taxCompany, String taxAddress, String taxPhone, String openBank, String openBankCardnum, 
			String name, String mobile, String idcard, String email, String businessLicence, String evidence, String idcardFace, String idcardBack){
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(QcbCompanyAccountStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		if(this.qcbCompanyAccountService.isExistQcbCompanyAccountByName(company, qca.getUuid())){
			return ResultManager.createFailResult("????????????????????????????????????????????????????????????");
		}
		QcbCompanyAccount qcaNew = new QcbCompanyAccount();
		qcaNew.setName(company);
		qcaNew.setBkArea(area);
		qcaNew.setAddress(address);
		qcaNew.setPhone(phone);
		qcaNew.setCorporation(corporation);
		qcaNew.setConnectionCompany(connectionCompany);
		qcaNew.setLogo(logo);
		
		qcaNew.setAccountBalance(qca.getAccountBalance());
		qcaNew.setAccountPay(qca.getAccountPay());
		qcaNew.setTotalInvest(qca.getTotalInvest());
		qcaNew.setTotalReturn(qca.getTotalReturn());
		qcaNew.setCreatetime(qca.getCreatetime());
		
		//????????????????????????
		qcaNew.setTaxIdentificationNum(taxIdentificationNum);
		qcaNew.setTaxAddress(taxAddress);
		qcaNew.setTaxCompany(taxCompany);
		qcaNew.setTaxPhone(taxPhone);
		qcaNew.setOpenBank(openBank);
		qcaNew.setOpenBankCardnum(openBankCardnum);
		
		//???????????????????????????
		if(!Utlity.checkIdCard(idcard)) {
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
		if(!Utlity.isEmail(email)) {
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		qcaNew.setContacts(name);
		qcaNew.setContactsMobile(mobile);
		qcaNew.setContactsEmail(email);
		qcaNew.setContactsIdcard(idcard);
		
		if(!Utlity.checkStringNull(businessLicence) && this.resourceService.get(businessLicence) == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		if(!Utlity.checkStringNull(evidence) && this.resourceService.get(evidence) == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		if(!Utlity.checkStringNull(idcardFace) && this.resourceService.get(idcardFace) == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!Utlity.checkStringNull(idcardBack) && this.resourceService.get(idcardBack) == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		qcaNew.setBusinessLicence(businessLicence);
		qcaNew.setEvidence(evidence);
		qcaNew.setIdcardFace(idcardFace);
		qcaNew.setIdcardBack(idcardBack);
		
		qca.setAuthStatus(QcbCompanyAccountAuthStatus.UNCHECK);
		
//		admin.setQcbCompanyName(company);
//		List<QcbCompanyAccountLessVO> listvo = admin.getListCompanyAccount();
//		for(QcbCompanyAccountLessVO vo : listvo){
//			if(vo.getUuid().equals(qca.getUuid())){
//				listvo.remove(vo);
//				vo.setName(company);
//				listvo.add(vo);
//			}
//		}
//		admin.setListCompanyAccount(listvo);
//		session.setAttribute("currentQcbOperator", admin);
		
		//??????????????????????????????
		QcbCompanyOperate qco = new QcbCompanyOperate();
		qco.setUuid(UUID.randomUUID().toString());
		qco.setQcbCompany(qca.getUuid());
		qco.setType(QcbCompanyOperateType.EDIT);
		qco.setStatus(QcbCompanyOperateStatus.UNCHECKED);
		//???????????????
		String oldData = JSONUtils.obj2json(qca);
		qco.setOld(oldData);
		//????????????
		String newData = JSONUtils.obj2json(qcaNew);
		qco.setValue(newData);
		qco.setCreator(admin.getUuid());
		qco.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.qcbCompanyAccountService.update(qca,qco);
		QcbMessageUtil.commpanyCerToAdmin(qca.getName());
		return ResultManager.createSuccessResult("????????????????????????????????????");
	}
	
	/**
	 * ????????????????????????
	 * @param taxIdentificationNum
	 * @param openBank
	 * @param openBankCardnum
	 * @return
	 */
	@RequestMapping(value = "/editTicket", method = RequestMethod.POST)
	@ActionParam(key = "taxIdentificationNum", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "taxCompany", message="????????????", type = DataType.STRING)
	@ActionParam(key = "taxAddress", message="????????????", type = DataType.STRING)
	@ActionParam(key = "taxPhone", message="??????", type = DataType.STRING)
	@ActionParam(key = "openBank", message="?????????", type = DataType.STRING)
	@ActionParam(key = "openBankCardnum", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result editTicket(String taxIdentificationNum, String taxCompany, String taxAddress, String taxPhone, String openBank, String openBankCardnum){
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(QcbCompanyAccountStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("?????????????????????");
		}
		
//		if(QcbCompanyAccountStatus.NORMAL.equals(qca.getStatus())){
//			return ResultManager.createFailResult("?????????????????????????????????");
//		}
		
		//????????????????????????
		if(!Utlity.checkStringNull(openBankCardnum) && !BankCardUtlity.checkBankCard(openBankCardnum)) {
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		
		qca.setTaxIdentificationNum(taxIdentificationNum);
		qca.setTaxAddress(taxAddress);
		qca.setTaxCompany(taxCompany);
		qca.setTaxPhone(taxPhone);
		qca.setOpenBank(openBank);
		qca.setOpenBankCardnum(openBankCardnum);
		
		this.qcbCompanyAccountService.update(qca);
		
		return ResultManager.createSuccessResult("???????????????");
	}
	
	/**
	 * ???????????????????????????
	 * @param idcard
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/editConnection", method = RequestMethod.POST)
	@ActionParam(key = "name", message="?????????", type = DataType.STRING)
	@ActionParam(key = "mobile", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "idcard", message="????????????", type = DataType.STRING)
	@ActionParam(key = "email", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result editConnection(String name, String mobile, String idcard, String email){
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO admin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(admin.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(QcbCompanyAccountStatus.DELETED.equals(qca.getStatus())){
			return ResultManager.createFailResult("?????????????????????");
		}
		
//		if(QcbCompanyAccountStatus.NORMAL.equals(qca.getStatus())){
//			return ResultManager.createFailResult("?????????????????????????????????");
//		}
		
		//????????????????????????
		if(!Utlity.checkIdCard(idcard)) {
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
		if(!Utlity.isEmail(email)) {
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		qca.setContacts(name);
		qca.setContactsMobile(mobile);
		qca.setContactsEmail(email);
		qca.setContactsIdcard(idcard);
		
		this.qcbCompanyAccountService.update(qca);
		
		return ResultManager.createSuccessResult("???????????????");
	}
}
