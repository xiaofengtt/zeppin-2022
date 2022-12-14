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

import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IQcbCompanyHistoryOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyAccountHistoryVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyHistoryOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbCompanyHistoryOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.QcbVirtualAccountsDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountVirtualAccountType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate.QcbCompanyHistoryOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyHistoryOperate.QcbCompanyHistoryOperateType;
import cn.zeppin.product.ntb.core.entity.QcbVirtualAccounts;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbVirtualAccountsService;
import cn.zeppin.product.utility.DataTimeConvert;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 * ??????????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/qcbCompanyTransfer")
public class QcbCompanyHistoryController extends BaseController {

	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IQcbCompanyHistoryOperateService qcbCompanyHistoryOperateService;
	
	@Autowired
	private IQcbVirtualAccountsService qcbVirtualAccountsService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	
	/**
	 * ??????????????????
	 * @param qcbCompany
	 * @param totalAmount
	 * @param remark
	 * @param receipt
	 * @param paytime
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@ActionParam(key = "qcbCompany", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "paytime", message="????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result recharge(String qcbCompany, BigDecimal totalAmount, String remark, String receipt, String paytime){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("?????????????????????0???");
		}
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbCompany);
		if(qca == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//???????????????????????????
		CompanyAccount ca = null;
		if(QcbCompanyAccountVirtualAccountType.VIRTUAL.equals(qca.getVirtualAccountType())){
			QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
			if(qva == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			
			ca = this.companyAccountService.get(qva.getCompanyAccount());
			if(ca == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
		}else if(QcbCompanyAccountVirtualAccountType.REALLY.equals(qca.getVirtualAccountType())){
			ca = this.companyAccountService.get(qca.getQcbVirtualAccounts());
			if(ca == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//????????????????????????
		QcbCompanyAccountHistory qcah = new QcbCompanyAccountHistory();
		qcah.setUuid(UUID.randomUUID().toString());
		qcah.setQcbCompany(qca.getUuid());
		
		//???????????????
		//???????????????????????????
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_NTLC,Utlity.BILL_PURPOSE_INCOME);
		if(this.qcbCompanyAccountHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("???????????????,??????????????????");
		}
		qcah.setOrderNum(orderNumStr);
		qcah.setOrderType(QcbCompanyAccountHistoryOrderType.PAY_TYPE_BANK_TRANSFER);
		qcah.setAccountBalance(qca.getAccountBalance().add(totalAmount));
		qcah.setIncome(totalAmount);
		qcah.setPay(BigDecimal.ZERO);
		qcah.setType(QcbCompanyAccountHistoryType.INCOME);
		qcah.setCompanyAccount(ca.getUuid());
		qcah.setPoundage(BigDecimal.ZERO);
		qcah.setStatus(QcbCompanyAccountHistoryStatus.SUCCESS);
		qcah.setCreatetime(DataTimeConvert.stringToTimeStamp(paytime));
		qcah.setCreator(currentOperator.getUuid());
		qcah.setRemark(remark);

		//?????????????????????????????????
		QcbCompanyHistoryOperate qcho = new QcbCompanyHistoryOperate();
		qcho.setUuid(UUID.randomUUID().toString());
		qcho.setType(QcbCompanyHistoryOperateType.RECHARGE);
		qcho.setValue(JSONUtils.obj2json(qcah));
		qcho.setStatus(QcbCompanyHistoryOperateStatus.UNCHECKED);
		qcho.setCreator(currentOperator.getUuid());
		qcho.setSubmittime(new Timestamp(System.currentTimeMillis()));
		qcho.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		qcho.setReceipt(receipt);
		
		this.qcbCompanyHistoryOperateService.insert(qcho);
		
		return ResultManager.createDataResult(qcho, "???????????????????????????");
	}
	
	/**
	 * ??????????????????
	 * @param qcbCompany
	 * @param totalAmount
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/expend", method = RequestMethod.POST)
	@ActionParam(key = "qcbCompany", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "flag", message="?????????????????????", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result expend(String qcbCompany, BigDecimal totalAmount, String remark, Boolean flag){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("?????????????????????0???");
		}
		
		//????????????????????????
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbCompany);
		if(qca == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
//		//???????????????????????????
//		QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
//		if(qva == null){
//			return ResultManager.createFailResult("??????????????????????????????");
//		}
//		
//		CompanyAccount ca = this.companyAccountService.get(qva.getCompanyAccount());
//		if(ca == null){
//			return ResultManager.createFailResult("??????????????????????????????");
//		}
		
		//????????????????????????????????????
		BigDecimal price = BigDecimal.ZERO;
		if(flag){
			price = totalAmount.subtract(totalAmount.divide(qca.getFeeTicket().add(BigDecimal.ONE), 8, BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);
		} else {
			price = totalAmount;
		}
//		BigDecimal price = totalAmount.subtract(totalAmount.divide(qca.getFeeTicket().add(BigDecimal.ONE), 8, BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);
		if(qca.getAccountPay().compareTo(price) == -1){
			return ResultManager.createFailResult("???????????????????????????????????????????????????");
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//????????????????????????
		QcbCompanyAccountHistory qcah = new QcbCompanyAccountHistory();
		qcah.setUuid(UUID.randomUUID().toString());
		qcah.setQcbCompany(qca.getUuid());
		
		//???????????????
		//???????????????????????????
		String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_QCB,Utlity.BILL_PAYTYPE_NTLC,Utlity.BILL_PURPOSE_NTLC);
		if(this.qcbCompanyAccountHistoryService.checkOrderNum(orderNumStr)){
			return ResultManager.createFailResult("???????????????,??????????????????");
		}
		qcah.setOrderNum(orderNumStr);
		qcah.setOrderType(QcbCompanyAccountHistoryOrderType.PAY_TYPE_BALANCE);
		qcah.setAccountBalance(qca.getAccountBalance().subtract(price));
		qcah.setIncome(BigDecimal.ZERO);
		qcah.setPay(price);
		qcah.setType(QcbCompanyAccountHistoryType.EXPEND);
//		qcah.setCompanyAccount(ca.getUuid());
		qcah.setPoundage(BigDecimal.ZERO);
		qcah.setStatus(QcbCompanyAccountHistoryStatus.SUCCESS);
		qcah.setCreatetime(new Timestamp(System.currentTimeMillis()));
		qcah.setCreator(currentOperator.getUuid());
		qcah.setRemark(remark);
		qcah.setOtherPrice(totalAmount);

		//?????????????????????????????????
		QcbCompanyHistoryOperate qcho = new QcbCompanyHistoryOperate();
		qcho.setUuid(UUID.randomUUID().toString());
		qcho.setType(QcbCompanyHistoryOperateType.EXPEND);
		qcho.setValue(JSONUtils.obj2json(qcah));
		qcho.setStatus(QcbCompanyHistoryOperateStatus.UNCHECKED);
		qcho.setCreator(currentOperator.getUuid());
		qcho.setSubmittime(new Timestamp(System.currentTimeMillis()));
		qcho.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.qcbCompanyHistoryOperateService.insert(qcho);
		
		return ResultManager.createDataResult(qcho, "???????????????????????????");
	}
	
	/** ??????????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//??????????????????
		QcbCompanyHistoryOperate qcho = this.qcbCompanyHistoryOperateService.get(uuid);
		if (qcho == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		QcbCompanyHistoryOperateDetailVO qchovo = new QcbCompanyHistoryOperateDetailVO(qcho);
		BkOperator creator = this.bkOperatorService.get(qcho.getCreator());
		if(creator != null){
			qchovo.setCreatorName(creator.getRealname());
		}
		
		//???????????????????????????
		if(qchovo.getReceipt() != null && !"".equals(qchovo.getReceipt())){
			List<Resource> reList = new ArrayList<Resource>();
			String[] receiptArr = qchovo.getReceipt().split(",");
			String str = "";
			for(String receipt : receiptArr){
				Resource re = this.resourceService.get(receipt);
				if(re != null){
					str += receipt+",";
					reList.add(re);
				}
			}
			if(str.length() > 0){
				str = str.substring(0, str.length() - 1);
			}
			qchovo.setReceipt(str);
			qchovo.setListReceipt(reList);
		}
		
		QcbCompanyAccountHistory qcah = JSONUtils.json2obj(qcho.getValue(), QcbCompanyAccountHistory.class);
		QcbCompanyAccountHistoryVO qcahvo = new QcbCompanyAccountHistoryVO(qcah);
		
		
		BkOperator c = this.bkOperatorService.get(qcahvo.getCreator());
		if(c != null){
			qcahvo.setCreatorName(c.getRealname());
		}
		QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
		if(qca == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		if(qcahvo.getPriceflag()){
			qcahvo.setAccountBalance(qca.getAccountBalance().add(qcah.getIncome()));//???????????????
		} else {
			qcahvo.setAccountBalance(qca.getAccountBalance().subtract(qcah.getPay()));//???????????????
		}
		
		qcahvo.setFeeTicketCN(Utlity.numFormat4UnitDetail(qca.getFeeTicket().multiply(BigDecimal.valueOf(100))));
		
		qchovo.setNewData(qcahvo);
		
		
		
		//???????????????????????????
		if(QcbCompanyAccountVirtualAccountType.VIRTUAL.equals(qca.getVirtualAccountType())){
			QcbVirtualAccounts qva = this.qcbVirtualAccountsService.get(qca.getQcbVirtualAccounts());
			if(qva != null){
				QcbVirtualAccountsDetailVO qvadVO = new QcbVirtualAccountsDetailVO(qva);
				
				CompanyAccount ca = this.companyAccountService.get(qvadVO.getCompanyAccount());
				if(ca == null){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				qvadVO.setCompanyAccountName(ca.getCompanyName());
				qvadVO.setCompanyAccountNum(ca.getAccountNum());
				
				if(!Utlity.checkStringNull(ca.getBranchBank())){
					BranchBank bb = this.branchBankService.get(ca.getBranchBank());
					if(bb != null){
						qvadVO.setBranchBankName(bb.getName());
						qvadVO.setBranchBankAddress(bb.getAddress());
					}
				}
		
				qvadVO.setQcbCompanyId(qca.getMerchantId());
				qvadVO.setQcbCompanyName(qca.getName());
				
				qchovo.setQcbVirtualAccountInfo(qvadVO);
			}
		}else if(QcbCompanyAccountVirtualAccountType.REALLY.equals(qca.getVirtualAccountType())){
			CompanyAccount ca = this.companyAccountService.get(qca.getQcbVirtualAccounts());
			if(ca != null){
				QcbVirtualAccountsDetailVO qvadVO = new QcbVirtualAccountsDetailVO();
				qvadVO.setQcbCompany(qca.getUuid());
				qvadVO.setQcbCompanyId(qca.getMerchantId());
				qvadVO.setQcbCompanyName(qca.getName());
				
				qvadVO.setCompanyAccount(ca.getUuid());
				qvadVO.setCompanyAccountName(ca.getCompanyName());
				qvadVO.setCompanyAccountNum(ca.getAccountNum());
				qvadVO.setAccountNum("");
				if(!Utlity.checkStringNull(ca.getBranchBank())){
					BranchBank bb = this.branchBankService.get(ca.getBranchBank());
					if(bb != null){
						qvadVO.setBranchBankName(bb.getName());
						qvadVO.setBranchBankAddress(bb.getAddress());
					}
				}
				
				qvadVO.setCreatetime(ca.getCreatetime());
				qvadVO.setCreatetimeCN(Utlity.timeSpanToString(ca.getCreatetime()));
				qvadVO.setCreator(ca.getCreator());
				qchovo.setQcbVirtualAccountInfo(qvadVO);
			}
		}
		
		return ResultManager.createDataResult(qchovo);
	}
	
	/**
	 * ??????????????????????????????(??????)
	 * @param status
	 * @param type = income
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
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
		Integer totalResultCount = qcbCompanyHistoryOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = qcbCompanyHistoryOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyHistoryOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				QcbCompanyHistoryOperate qcho = (QcbCompanyHistoryOperate)e;
				QcbCompanyHistoryOperateVO qchovo = new QcbCompanyHistoryOperateVO(qcho);
				
				QcbCompanyAccountHistory qcah = JSONUtils.json2obj(qcho.getValue(), QcbCompanyAccountHistory.class);
				
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
				if(qca == null){
					return ResultManager.createFailResult("????????????????????????");
				}
				qchovo.setQcbCompany(qca.getName());
				if(qcah.getIncome().compareTo(BigDecimal.ZERO) == 1) {
					qchovo.setPrice(Utlity.numFormat4UnitDetail(qcah.getIncome()));
				}
				if (qcah.getPay().compareTo(BigDecimal.ZERO) == 1) {
					qchovo.setPrice(Utlity.numFormat4UnitDetail(qcah.getPay()));
				}
				
				BkOperator creator = this.bkOperatorService.get(qchovo.getCreator());
				if(creator != null){
					qchovo.setCreatorName(creator.getRealname());
				}
				if(qchovo.getChecker() != null && !"".equals(qchovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(qchovo.getChecker());
					if(checker != null){
						qchovo.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(qchovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????????????????????????????(?????????)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
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
		Integer totalResultCount = qcbCompanyHistoryOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = qcbCompanyHistoryOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyHistoryOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				QcbCompanyHistoryOperate qcho = (QcbCompanyHistoryOperate)e;
				QcbCompanyHistoryOperateVO qchovo = new QcbCompanyHistoryOperateVO(qcho);
				
				QcbCompanyAccountHistory qcah = JSONUtils.json2obj(qcho.getValue(), QcbCompanyAccountHistory.class);
				
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
				if(qca == null){
					return ResultManager.createFailResult("????????????????????????");
				}
				qchovo.setQcbCompany(qca.getName());
				if(qcah.getIncome().compareTo(BigDecimal.ZERO) == 1) {
					qchovo.setPrice(Utlity.numFormat4UnitDetail(qcah.getIncome()));
				}
				if (qcah.getPay().compareTo(BigDecimal.ZERO) == 1) {
					qchovo.setPrice(Utlity.numFormat4UnitDetail(qcah.getPay()));
				}
				
				BkOperator creator = this.bkOperatorService.get(qchovo.getCreator());
				if(creator != null){
					qchovo.setCreatorName(creator.getRealname());
				}
				if(qchovo.getChecker() != null && !"".equals(qchovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(qchovo.getChecker());
					if(checker != null){
						qchovo.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(qchovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @param totalAmount
	 * @param poundage
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "paytime", message="????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateEdit(String uuid, BigDecimal totalAmount, String remark, String receipt, String paytime){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("?????????????????????0???");
		}
		
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if (cato != null) {
			QcbCompanyAccountHistory qcah = JSONUtils.json2obj(cato.getValue(), QcbCompanyAccountHistory.class);
			
			//????????????????????????
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
			if(qca == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
				return ResultManager.createFailResult("?????????????????????0???");
			}
			
			qcah.setAccountBalance(qca.getAccountBalance().add(totalAmount));
			qcah.setIncome(totalAmount);
			qcah.setCreatetime(DataTimeConvert.stringToTimeStamp(paytime));
			qcah.setRemark(remark);
			
			//?????????????????????
			cato.setValue(JSONUtils.obj2json(qcah));
			cato.setCreatetime(new Timestamp(System.currentTimeMillis()));

			cato.setReceipt(receipt);
			
			qcbCompanyHistoryOperateService.update(cato);
			return ResultManager.createDataResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @param totalAmount
	 * @param poundage
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/operateExpendEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ResponseBody
	public Result operateExpendEdit(String uuid, BigDecimal totalAmount, String remark){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("?????????????????????0???");
		}
		
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if (cato != null) {
			QcbCompanyAccountHistory qcah = JSONUtils.json2obj(cato.getValue(), QcbCompanyAccountHistory.class);
			
			//????????????????????????
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcah.getQcbCompany());
			if(qca == null){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			//????????????????????????????????????
			BigDecimal price = totalAmount.multiply(qca.getFeeTicket()).setScale(2, BigDecimal.ROUND_HALF_UP);
			if(qca.getAccountPay().compareTo(price) == -1){
				return ResultManager.createFailResult("???????????????????????????????????????????????????");
			}
			
			qcah.setAccountBalance(qca.getAccountBalance().subtract(price));
			qcah.setPay(price);
			qcah.setRemark(remark);
			qcah.setCreatetime(new Timestamp(System.currentTimeMillis()));
			qcah.setOtherPrice(totalAmount);
			
			//?????????????????????
			cato.setValue(JSONUtils.obj2json(qcah));
			cato.setCreatetime(new Timestamp(System.currentTimeMillis()));

			qcbCompanyHistoryOperateService.update(cato);
			return ResultManager.createDataResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//????????????????????????????????????
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if(cato != null){
			if(!QcbCompanyHistoryOperateStatus.DRAFT.equals(cato.getStatus()) && !QcbCompanyHistoryOperateStatus.UNPASSED.equals(cato.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			cato.setStatus(QcbCompanyHistoryOperateStatus.DELETED);
			qcbCompanyHistoryOperateService.update(cato);
			return ResultManager.createSuccessResult("???????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//????????????????????????????????????
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if(cato != null){
			if(QcbCompanyHistoryOperateStatus.CHECKED.equals(cato.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			cato.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cato.setStatus(QcbCompanyHistoryOperateStatus.UNCHECKED);
			qcbCompanyHistoryOperateService.update(cato);
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="????????????", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!QcbCompanyHistoryOperateStatus.CHECKED.equals(status) && !QcbCompanyHistoryOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//??????????????????????????????
		QcbCompanyHistoryOperate cato = this.qcbCompanyHistoryOperateService.get(uuid);
		if(cato != null){
			if(!QcbCompanyHistoryOperateStatus.UNCHECKED.equals(cato.getStatus())){
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
				qcbCompanyHistoryOperateService.check(cato);
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
	 * ???????????????????????????????????????(??????)
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateStatusList(String transferType) {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//??????????????????????????????
		List<Entity> list = qcbCompanyHistoryOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckStatusList(String transferType) {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//??????????????????????????????
		List<Entity> list = qcbCompanyHistoryOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String transferType, String status) {
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
		
		List<Entity> list = qcbCompanyHistoryOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String transferType, String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = qcbCompanyHistoryOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
