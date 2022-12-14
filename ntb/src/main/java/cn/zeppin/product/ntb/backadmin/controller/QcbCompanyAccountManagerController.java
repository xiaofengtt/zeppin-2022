/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import cn.zeppin.product.ntb.backadmin.service.api.IQcbCompanyFeeOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyAccountFeeVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyAccountFullVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyAccountHistoryVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyAccountVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyFeeOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountVirtualAccountType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate.QcbCompanyFeeOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyFeeOperate.QcbCompanyFeeOperateType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate.QcbCompanyHistoryOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyOperate.QcbCompanyOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts.QcbVirtualAccountsStatus;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyOperateService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbVirtualAccountsService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * ???????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/qcbcompany")
public class QcbCompanyAccountManagerController extends BaseController {
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbVirtualAccountsService qcbVirtualAccountsService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	@Autowired
	private IQcbCompanyOperateService qcbCompanyOperateService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IBkAreaService bkAreaService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IQcbCompanyFeeOperateService qcbCompanyFeeOperateService;
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = qcbCompanyAccountService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ????????????
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="??????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		Integer totalResultCount = this.qcbCompanyAccountService.getCount(searchMap);
		List<Entity> list = this.qcbCompanyAccountService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyAccount.class);
		List<QcbCompanyAccountVO> listvo = new ArrayList<QcbCompanyAccountVO>();
		
		for(Entity entity : list){
			QcbCompanyAccount qca = (QcbCompanyAccount)entity;
			QcbCompanyAccountVO qcaVO = new QcbCompanyAccountVO(qca);
			
			//??????logo??????
			if(qca.getLogo() != null){
				Resource logo = this.resourceService.get(qca.getLogo());
				if(logo != null){
					qcaVO.setLogoUrl(logo.getUrl());
				}
			}
			
			//20180724???????????????????????????
			searchMap.clear();
			searchMap.put("qcbCompany", qca.getUuid());
			BigDecimal sum = this.qcbCompanyAccountService.getTotalBalacneByEmp(searchMap);
			//Utlity.numFormat4UnitDetail(company.getAccountPay());
			qcaVO.setQcbEmpTotalBalance(sum);
			qcaVO.setQcbEmpTotalBalanceCN(Utlity.numFormat4UnitDetail(sum));
			
			listvo.add(qcaVO);
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//????????????????????????
		QcbCompanyAccount qca = qcbCompanyAccountService.get(uuid);
		if (qca == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		QcbCompanyAccountFullVO qcafVO = new QcbCompanyAccountFullVO(qca);
		
		//????????????????????????
		if(!Utlity.checkStringNull(qcafVO.getQcbVirtualAccounts())){
			if(QcbCompanyAccountVirtualAccountType.VIRTUAL.equals(qca.getVirtualAccountType())){
				QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qcafVO.getQcbVirtualAccounts());
				if(qva != null){
					qcafVO.setAccountNum(qva.getAccountNum());
					
					CompanyAccount ca = this.companyAccountService.get(qva.getCompanyAccount());
					if(ca != null){
						qcafVO.setCompanyAccount(ca.getUuid());
						qcafVO.setCompanyAccountName(ca.getCompanyName());
						qcafVO.setCompanyAccountNum(ca.getAccountNum());
						
						if(!Utlity.checkStringNull(ca.getBranchBank())){
							BranchBank bb = this.branchBankService.get(ca.getBranchBank());
							if(bb != null){
								qcafVO.setBranchBankName(bb.getName());
							}
						}
					}
				}
			}else if(QcbCompanyAccountVirtualAccountType.REALLY.equals(qca.getVirtualAccountType())){
				CompanyAccount ca = this.companyAccountService.get(qca.getQcbVirtualAccounts());
				if(ca != null){
					qcafVO.setAccountNum("");
					qcafVO.setCompanyAccount(ca.getUuid());
					qcafVO.setCompanyAccountName(ca.getCompanyName());
					qcafVO.setCompanyAccountNum(ca.getAccountNum());
					
					if(!Utlity.checkStringNull(ca.getBranchBank())){
						BranchBank bb = this.branchBankService.get(ca.getBranchBank());
						if(bb != null){
							qcafVO.setBranchBankName(bb.getName());
						}
					}
				}
			}
		}
		
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
		qcafVO.setBkAreaCN(bkAreaCN);
		qcafVO.setAreaInfo(areaInfo);
		
		//??????logo??????
		if(qca.getLogo() != null){
			Resource logo = this.resourceService.get(qca.getLogo());
			if(logo != null){
				qcafVO.setLogoUrl(logo.getUrl());
			}
		}
		
		//????????????
		if(!Utlity.checkStringNull(qca.getBusinessLicence())){
			Resource bl = this.resourceService.get(qca.getBusinessLicence());
			if(bl != null){
				qcafVO.setBusinessLicenceURL(bl.getUrl());
			}
		}
		
		if(!Utlity.checkStringNull(qca.getEvidence())){
			Resource ev = this.resourceService.get(qca.getEvidence());
			if(ev != null){
				qcafVO.setEvidenceURL(ev.getUrl());
			}
		}
		
		if(!Utlity.checkStringNull(qca.getIdcardFace())){
			Resource idf = this.resourceService.get(qca.getIdcardFace());
			if(idf != null){
				qcafVO.setIdcardFaceURL(idf.getUrl());
			}
		}

		if(!Utlity.checkStringNull(qca.getIdcardBack())){
			Resource idb = this.resourceService.get(qca.getIdcardBack());
			if(idb != null){
				qcafVO.setIdcardBackURL(idb.getUrl());
			}
		}
		
		//??????????????????
		if(QcbCompanyAccountStatus.NORMAL.equals(qcafVO.getStatus())){
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("status", QcbCompanyOperateStatus.CHECKED);
			searchMap.put("qcbCompany", qca.getUuid());
			List<Entity> qcocList = this.qcbCompanyOperateService.getListForPage(searchMap, -1, -1, null, QcbCompanyOperate.class);
			
			if(qcocList.size() > 0){
				QcbCompanyOperate qcoc = (QcbCompanyOperate) qcocList.get(0);
				
				if(QcbCompanyOperateStatus.CHECKED.equals(qcoc.getStatus())){
					qcafVO.setCheckStatusCN("????????????");
				}else if(QcbCompanyOperateStatus.UNPASSED.equals(qcoc.getStatus())){
					qcafVO.setCheckStatusCN("???????????????");
				}
				qcafVO.setChecktimeCN(Utlity.timeSpanToChinaString(qcoc.getCreatetime()));
				BkOperator checker = this.bkOperatorService.get(qcoc.getChecker());
				if(qcoc != null){
					qcafVO.setCheckerName(checker.getRealname());	
				}
			}
			
		}
		return ResultManager.createDataResult(qcafVO);
	}
	
	/**
	 * ??????????????????
	 * @param type redeem--??????/??????
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/historyList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result historyList(String uuid, String type, Integer pageNum, Integer pageSize, String sorts) {
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(uuid);
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", QcbCompanyAccountHistoryStatus.SUCCESS);
		searchMap.put("type", type);
		searchMap.put("qcbCompany", qca.getUuid());
		
		Integer totalRecordsCount = this.qcbCompanyAccountHistoryService.getCount(searchMap);
		List<Entity> list = this.qcbCompanyAccountHistoryService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyAccountHistory.class);
		
		List<QcbCompanyAccountHistoryVO> listvo = new ArrayList<QcbCompanyAccountHistoryVO>();
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				QcbCompanyAccountHistory qcah = (QcbCompanyAccountHistory)entity;
				QcbCompanyAccountHistoryVO vo = new QcbCompanyAccountHistoryVO(qcah);
				listvo.add(vo);
			}
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalRecordsCount);
	}
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/historyGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result historyGet(String uuid) {
		
		QcbCompanyAccountHistory qcah = this.qcbCompanyAccountHistoryService.get(uuid);
		if(qcah != null){
			QcbCompanyAccountHistoryVO vo = new QcbCompanyAccountHistoryVO(qcah);
			if(!Utlity.checkStringNull(qcah.getCompanyAccount())){
				CompanyAccount ca = this.companyAccountService.get(qcah.getCompanyAccount());
				if(ca == null){
					return ResultManager.createFailResult("?????????????????????");
				}
				Bank bank = this.bankService.get(ca.getBank());
				if(bank == null){
					return ResultManager.createFailResult("?????????????????????");
				}
				String cardNum = ca.getAccountNum().substring(ca.getAccountNum().length() - 4, ca.getAccountNum().length());
				String bankName = bank.getShortName();
				String caName = ca.getCompanyName();
				vo.setCardNum(cardNum);
				vo.setBankName(bankName);
				vo.setCompanyAccount(caName);
			} else {
				vo.setCardNum("");
				vo.setBankName("");
				vo.setCompanyAccount("");
			}
			
			return ResultManager.createDataResult(vo);
		} else {
			return ResultManager.createFailResult("??????????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????(??????)
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateStatusList() {	
		//??????
		List<Entity> list = qcbCompanyAccountService.getCheckStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "name", message="??????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		
		if(!"all".equals(status)){
			searchMap.put("authStatus", status);
		}
		Integer totalResultCount = this.qcbCompanyAccountService.getCount(searchMap);
		List<Entity> list = this.qcbCompanyAccountService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyAccount.class);
		List<QcbCompanyAccountVO> listvo = new ArrayList<QcbCompanyAccountVO>();
		
		for(Entity entity : list){
			QcbCompanyAccount qca = (QcbCompanyAccount)entity;
			QcbCompanyAccountVO qcaVO = new QcbCompanyAccountVO(qca);
			
			//??????logo??????
			if(qca.getLogo() != null){
				Resource logo = this.resourceService.get(qca.getLogo());
				if(logo != null){
					qcaVO.setLogoUrl(logo.getUrl());
				}
			}
			
			listvo.add(qcaVO);
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "qcbCompany", message="???????????????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result operateGet(String qcbCompany) {	
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbCompany);
		if(qca == null){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//????????????????????????
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompany", qca.getUuid());
//		searchMap.put("status", QcbCompanyOperateStatus.UNCHECKED);
		QcbCompanyOperate qco = null;
		List<Entity> list = this.qcbCompanyOperateService.getListForPage(searchMap, -1, -1, null, QcbCompanyOperate.class);
		if(list != null && !list.isEmpty()){
			qco = (QcbCompanyOperate)list.get(0);
		}
		if (qco == null) {
			return ResultManager.createFailResult("???????????????????????????????????????");
		}
//		if(!QcbCompanyOperateStatus.UNCHECKED.equals(qco.getStatus())){
//			return ResultManager.createFailResult("???????????????????????????????????????");
//		}
		//????????????????????????
		QcbCompanyOperateVO qcoVO = new QcbCompanyOperateVO(qco);
		if(!Utlity.checkStringNull(qco.getChecker())){
			BkOperator checker = this.bkOperatorService.get(qco.getChecker());
			if(checker != null){
				qcoVO.setCheckerName(checker.getRealname());
			} else {
				qcoVO.setCheckerName(currentOperator.getRealname());
			}
		} else {
			qcoVO.setCheckerName(currentOperator.getRealname());
		}
		
		QcbCompanyAccountFullVO oldData = null;
		if(!Utlity.checkStringNull(qco.getOld())){
			QcbCompanyAccount qcaOld = JSONUtils.json2obj(qco.getOld(), QcbCompanyAccount.class);
			oldData = new QcbCompanyAccountFullVO(qcaOld);
			
			//??????????????????
			String bkAreaCN = "";
			Map<String, Object> areaInfo = new HashMap<String, Object>();
			if( qcaOld.getBkArea() != null) {
				BkArea area = this.bkAreaService.get(qcaOld.getBkArea());
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
			oldData.setBkAreaCN(bkAreaCN);
			oldData.setAreaInfo(areaInfo);
			
			//??????logo??????
			if(qcaOld.getLogo() != null){
				Resource logo = this.resourceService.get(qcaOld.getLogo());
				if(logo != null){
					oldData.setLogoUrl(logo.getUrl());
				}
			}
			
			//????????????
			if(!Utlity.checkStringNull(qcaOld.getBusinessLicence())){
				Resource bl = this.resourceService.get(qcaOld.getBusinessLicence());
				if(bl != null){
					oldData.setBusinessLicenceURL(bl.getUrl());
				}
			}
			
			if(!Utlity.checkStringNull(qcaOld.getEvidence())){
				Resource ev = this.resourceService.get(qcaOld.getEvidence());
				if(ev != null){
					oldData.setEvidenceURL(ev.getUrl());
				}
			}
			
			if(!Utlity.checkStringNull(qcaOld.getIdcardFace())){
				Resource idf = this.resourceService.get(qcaOld.getIdcardFace());
				if(idf != null){
					oldData.setIdcardFaceURL(idf.getUrl());
				}
			}

			if(!Utlity.checkStringNull(qcaOld.getIdcardBack())){
				Resource idb = this.resourceService.get(qcaOld.getIdcardBack());
				if(idb != null){
					oldData.setIdcardBackURL(idb.getUrl());
				}
			}
			
		}
		if(oldData != null){
			qcoVO.setOldData(oldData);
		}
		QcbCompanyAccount qcaNew = JSONUtils.json2obj(qco.getValue(), QcbCompanyAccount.class);
		QcbCompanyAccountFullVO newData = new QcbCompanyAccountFullVO(qcaNew);
		
		//??????????????????
		String bkAreaCN = "";
		Map<String, Object> areaInfo = new HashMap<String, Object>();
		if( qcaNew.getBkArea() != null) {
			BkArea area = this.bkAreaService.get(qcaNew.getBkArea());
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
		newData.setBkAreaCN(bkAreaCN);
		newData.setAreaInfo(areaInfo);
		
		//??????logo??????
		if(qcaNew.getLogo() != null){
			Resource logo = this.resourceService.get(qcaNew.getLogo());
			if(logo != null){
				newData.setLogoUrl(logo.getUrl());
			}
		}
		
		//????????????
		if(!Utlity.checkStringNull(qcaNew.getBusinessLicence())){
			Resource bl = this.resourceService.get(newData.getBusinessLicence());
			if(bl != null){
				newData.setBusinessLicenceURL(bl.getUrl());
			}
		}
		
		if(!Utlity.checkStringNull(qcaNew.getEvidence())){
			Resource ev = this.resourceService.get(newData.getEvidence());
			if(ev != null){
				newData.setEvidenceURL(ev.getUrl());
			}
		}
		
		if(!Utlity.checkStringNull(qcaNew.getIdcardFace())){
			Resource idf = this.resourceService.get(newData.getIdcardFace());
			if(idf != null){
				newData.setIdcardFaceURL(idf.getUrl());
			}
		}

		if(!Utlity.checkStringNull(qcaNew.getIdcardBack())){
			Resource idb = this.resourceService.get(newData.getIdcardBack());
			if(idb != null){
				newData.setIdcardBackURL(idb.getUrl());
			}
		}
		
		qcoVO.setNewData(newData);
		
		QcbAdmin qa = this.qcbAdminService.get(qcoVO.getCreator());
		if(qa != null){
			qcoVO.setCreatorName(qa.getName());
		}
		
		return ResultManager.createDataResult(qcoVO);
	}
	
	/**
	 * ?????????????????????
	 * @param qcbCompanyOperate
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.POST)
	@ActionParam(key = "qcbCompanyOperate", message="????????????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "reason", message="????????????", type = DataType.STRING, maxLength = 200)
	@ResponseBody
	public Result operateCheck(String qcbCompanyOperate, String status, String reason) {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		QcbCompanyOperate qco = this.qcbCompanyOperateService.get(qcbCompanyOperate);
		if(qco == null){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!QcbCompanyOperateStatus.UNCHECKED.equals(qco.getStatus()) && !QcbCompanyOperateStatus.UNPASSED.equals(qco.getStatus())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		qco.setStatus(status);
		if(Utlity.checkStringNull(reason)){
			if(QcbCompanyOperateStatus.CHECKED.equals(status)){
				reason = "???????????????";
			} else if (QcbCompanyOperateStatus.UNPASSED.equals(status)) {
				reason = "??????????????????";
			} else {
				reason = "--";
			}
		}
		qco.setReason(reason);
		qco.setChecker(currentOperator.getUuid());
		qco.setChecktime(new Timestamp(System.currentTimeMillis()));
		
		try {
			this.qcbCompanyOperateService.check(qco);
		} catch (TransactionException e) {
			return ResultManager.createFailResult(e.getMessage());
		}
		
		return ResultManager.createSuccessResult("???????????????");
	}
	
	/**
	 * ??????????????????
	 * @param qcbCompany
	 * @param companyAccount
	 * @param qcbVirtualAccount
	 * @return
	 */
	@RequestMapping(value = "/virtualAccountBind", method = RequestMethod.POST)
	@ActionParam(key = "qcbCompany", message="???????????????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "qcbVirtualAccount", message="??????????????????", type = DataType.STRING, maxLength = 36)
	@ResponseBody
	public Result virtualAccountBind(String qcbCompany, String companyAccount, String qcbVirtualAccount) {
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbCompany);
		if(qca == null){
			return ResultManager.createFailResult("???????????????????????????");
		}
		
		if(!Utlity.checkStringNull(companyAccount) && Utlity.checkStringNull(qcbVirtualAccount)){
			CompanyAccount ca = this.companyAccountService.get(companyAccount);
			if(ca == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			qca.setVirtualAccountType(QcbCompanyAccountVirtualAccountType.REALLY);
			qca.setQcbVirtualAccounts(companyAccount);
			this.qcbCompanyAccountService.update(qca);
		} else if(!Utlity.checkStringNull(qcbVirtualAccount)){
			QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qcbVirtualAccount);
			if(qva == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			
			if(!QcbVirtualAccountsStatus.NORMAL.equals(qva.getStatus())){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			qca.setVirtualAccountType(QcbCompanyAccountVirtualAccountType.VIRTUAL);
			qca.setQcbVirtualAccounts(qcbVirtualAccount);
			qva.setQcbCompany(qcbCompany);
			qva.setStatus(QcbVirtualAccountsStatus.USED);
			this.qcbCompanyAccountService.updateVirtualAccount(qca, qva);
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
		return ResultManager.createSuccessResult("???????????????");
	}

	/**
	 * ????????????
	 * @param uuid
	 * @param fee
	 * @return
	 */
	@RequestMapping(value = "/changeFee", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "fee", message="??????", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result changeFee(String uuid, BigDecimal fee) {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//????????????????????????
		QcbCompanyAccount qca = qcbCompanyAccountService.get(uuid);
		if (qca == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompany", uuid);
		searchMap.put("type", QcbCompanyFeeOperateType.FEE_TICKET);
		searchMap.put("status", QcbCompanyFeeOperateStatus.UNCHECKED);
		
		Integer count = this.qcbCompanyFeeOperateService.getCount(searchMap);
		if(count > 0 ){
			return ResultManager.createFailResult("??????????????????????????????????????????????????????");
		}
		
		QcbCompanyAccountFeeVO qcafvoOld = new QcbCompanyAccountFeeVO(qca);
		String old = JSONUtils.obj2json(qcafvoOld);
		qca.setFeeTicket(fee);
		QcbCompanyAccountFeeVO qcafvo = new QcbCompanyAccountFeeVO(qca);
		
		QcbCompanyFeeOperate qcfo = new QcbCompanyFeeOperate();
		qcfo.setUuid(UUID.randomUUID().toString());
		qcfo.setQcbCompanyAccount(qca.getUuid());
		qcfo.setType(QcbCompanyFeeOperateType.FEE_TICKET);
		qcfo.setValue(JSONUtils.obj2json(qcafvo));
		qcfo.setStatus(QcbCompanyFeeOperateStatus.UNCHECKED);
		qcfo.setCreator(currentOperator.getUuid());
		qcfo.setSubmittime(new Timestamp(System.currentTimeMillis()));
		qcfo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcfo.setOld(old);
		
		this.qcbCompanyFeeOperateService.insert(qcfo);
		return ResultManager.createDataResult(qcfo, "???????????????????????????");
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping(value = "/operateFeeStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateFeeStatusList() {
		
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = qcbCompanyFeeOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????(??????)
	 * @return
	 */
	@RequestMapping(value = "/operateFeeCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateFeeCheckStatusList() {
		
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		
		List<Entity> list = qcbCompanyFeeOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ????????????????????????(??????)
	 * @param status
	 * @param type = fee_ticket
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateFeeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateFeeList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//????????????????????????????????????????????????
		Integer totalResultCount = qcbCompanyFeeOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = qcbCompanyFeeOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyFeeOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				QcbCompanyFeeOperate qcfo = (QcbCompanyFeeOperate)e;
				QcbCompanyFeeOperateVO qcfovo = new QcbCompanyFeeOperateVO(qcfo);
				
				QcbCompanyAccountFeeVO qcafvo = JSONUtils.json2obj(qcfo.getValue(), QcbCompanyAccountFeeVO.class);
				
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcfo.getQcbCompanyAccount());
				if(qca == null){
					return ResultManager.createFailResult("????????????????????????");
				}
				qcfovo.setQcbCompany(qca.getName());
				qcfovo.setFee(Utlity.numFormat4UnitDetail(qcafvo.getFeeTicket().multiply(BigDecimal.valueOf(100))));
				
				BkOperator creator = this.bkOperatorService.get(qcfovo.getCreator());
				if(creator != null){
					qcfovo.setCreatorName(creator.getRealname());
				}
				if(qcfovo.getChecker() != null && !"".equals(qcfovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(qcfovo.getChecker());
					if(checker != null){
						qcfovo.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(qcfovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????????????????(??????)
	 * @param status
	 * @param type = fee_ticket
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateFeeCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateFeeCheckList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		
		//????????????????????????????????????????????????
		Integer totalResultCount = qcbCompanyFeeOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = qcbCompanyFeeOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyFeeOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				QcbCompanyFeeOperate qcfo = (QcbCompanyFeeOperate)e;
				QcbCompanyFeeOperateVO qcfovo = new QcbCompanyFeeOperateVO(qcfo);
				
				QcbCompanyAccountFeeVO qcafvo = JSONUtils.json2obj(qcfo.getValue(), QcbCompanyAccountFeeVO.class);
				
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcfo.getQcbCompanyAccount());
				if(qca == null){
					return ResultManager.createFailResult("????????????????????????");
				}
				qcfovo.setQcbCompany(qca.getName());
				qcfovo.setFee(Utlity.numFormat4UnitDetail(qcafvo.getFeeTicket().multiply(BigDecimal.valueOf(100))));
				
				BkOperator creator = this.bkOperatorService.get(qcfovo.getCreator());
				if(creator != null){
					qcfovo.setCreatorName(creator.getRealname());
				}
				if(qcfovo.getChecker() != null && !"".equals(qcfovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(qcfovo.getChecker());
					if(checker != null){
						qcfovo.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(qcfovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/** ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateFeeGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateFeeGet(String uuid) {
		//??????????????????
		QcbCompanyFeeOperate qcfo = this.qcbCompanyFeeOperateService.get(uuid);
		if (qcfo == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		QcbCompanyFeeOperateVO qcfovo = new QcbCompanyFeeOperateVO(qcfo);
		
		QcbCompanyAccountFeeVO qcafvo = JSONUtils.json2obj(qcfo.getValue(), QcbCompanyAccountFeeVO.class);
		
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcfo.getQcbCompanyAccount());
		if(qca == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		qcfovo.setQcbCompany(qca.getName());
		qcfovo.setFee(Utlity.numFormat4UnitDetail(qcafvo.getFeeTicket().multiply(BigDecimal.valueOf(100))));
		
		BkOperator creator = this.bkOperatorService.get(qcfovo.getCreator());
		if(creator != null){
			qcfovo.setCreatorName(creator.getRealname());
		}
		if(qcfovo.getChecker() != null && !"".equals(qcfovo.getChecker())){
			BkOperator checker = this.bkOperatorService.get(qcfovo.getChecker());
			if(checker != null){
				qcfovo.setCheckerName(checker.getRealname());
			}
		}
		qcfovo.setNewData(qcafvo);
		if(!Utlity.checkStringNull(qcfo.getOld())){
			QcbCompanyAccountFeeVO qcafvoOld = JSONUtils.json2obj(qcfo.getOld(), QcbCompanyAccountFeeVO.class);
			qcfovo.setOldData(qcafvoOld);
		}
		return ResultManager.createDataResult(qcfovo);
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @param fee
	 * @return
	 */
	@RequestMapping(value = "/operateFeeEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message="??????", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result operateFeeEdit(String uuid, BigDecimal fee, String remark, String receipt, String paytime){
		if(fee.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("???????????????0???");
		}
		
		QcbCompanyFeeOperate qcfo = this.qcbCompanyFeeOperateService.get(uuid);
		if (qcfo != null) {
			QcbCompanyAccountFeeVO qcafvo = JSONUtils.json2obj(qcfo.getValue(), QcbCompanyAccountFeeVO.class);
			qcafvo.setFeeTicket(fee);
			qcafvo.setFeeTicketCN(Utlity.numFormat4UnitDetail(fee.multiply(BigDecimal.valueOf(100))));
			qcfo.setValue(JSONUtils.obj2json(qcafvo));
			
			qcbCompanyFeeOperateService.update(qcfo);
			return ResultManager.createDataResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateFeeDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateFeeDelete(String uuid) {
		//????????????????????????????????????
		QcbCompanyFeeOperate cato = this.qcbCompanyFeeOperateService.get(uuid);
		if(cato != null){
			if(!QcbCompanyFeeOperateStatus.DRAFT.equals(cato.getStatus()) && !QcbCompanyFeeOperateStatus.UNPASSED.equals(cato.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			cato.setStatus(QcbCompanyHistoryOperateStatus.DELETED);
			qcbCompanyFeeOperateService.update(cato);
			return ResultManager.createSuccessResult("???????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateFeeSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateFeeSubmitCheck(String uuid) {
		//????????????????????????????????????
		QcbCompanyFeeOperate cato = this.qcbCompanyFeeOperateService.get(uuid);
		if(cato != null){
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("qcbCompany", cato.getQcbCompanyAccount());
			searchMap.put("type", QcbCompanyFeeOperateType.FEE_TICKET);
			searchMap.put("status", QcbCompanyFeeOperateStatus.UNCHECKED);
			
			Integer count = this.qcbCompanyFeeOperateService.getCount(searchMap);
			if(count > 0 ){
				return ResultManager.createFailResult("??????????????????????????????????????????????????????");
			}
			
			if(QcbCompanyFeeOperateStatus.CHECKED.equals(cato.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			cato.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cato.setStatus(QcbCompanyFeeOperateStatus.UNCHECKED);
			qcbCompanyFeeOperateService.update(cato);
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateFeeCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="????????????", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateFeeCheck(String uuid, String status, String reason) {
		if(!QcbCompanyFeeOperateStatus.CHECKED.equals(status) && !QcbCompanyFeeOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//??????????????????????????????
		QcbCompanyFeeOperate cato = this.qcbCompanyFeeOperateService.get(uuid);
		if(cato != null){
			if(!QcbCompanyFeeOperateStatus.UNCHECKED.equals(cato.getStatus())){
				return ResultManager.createFailResult("???????????????????????????");
			}
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(cato.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("???????????????????????????????????????");
			}
			
			cato.setChecker(currentOperator.getUuid());
			cato.setChecktime(new Timestamp(System.currentTimeMillis()));
			cato.setStatus(status);
			cato.setReason(reason);
			try {
				qcbCompanyFeeOperateService.check(cato);
			} catch (TransactionException e) {
				super.flushAll();
				return ResultManager.createFailResult(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("?????????????????????");
			}
			return ResultManager.createSuccessResult("?????????????????????");
			
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/operateFeeTypeList", method = RequestMethod.GET)
	@ActionParam(key = "type", message="??????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateFeeTypeList(String type, String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = qcbCompanyFeeOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateFeeCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "type", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateFeeCheckTypeList(String type, String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = qcbCompanyFeeOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
