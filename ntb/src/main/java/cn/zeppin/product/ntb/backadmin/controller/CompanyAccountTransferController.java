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

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountTransferOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountHistoryDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountTransferOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountTransferOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountType;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate.CompanyAccountTransferOperateStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountTransferOperate.CompanyAccountTransferOperateType;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 * ??????????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/companyAccountTransfer")
public class CompanyAccountTransferController extends BaseController {

	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private ICompanyAccountOperateService companyAccountOperateService;
	
	@Autowired
	private ICompanyAccountTransferOperateService companyAccountTransferOperateService;
	
	@Autowired
	private ICompanyAccountHistoryService companyAccountHistoryService;
	
	@Autowired
	private IBankFinancialProductService bankFinancialProductService;
	
	@Autowired
	private IFundService fundService;
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//????????????????????????????????????
		CompanyAccountHistory cah = companyAccountHistoryService.get(uuid);
		if (cah == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		CompanyAccountHistoryDetailVO cahdVO = new CompanyAccountHistoryDetailVO(cah);
		
		BkOperator c = this.bkOperatorService.get(cahdVO.getCreator());
		if(c != null){
			cahdVO.setCreatorName(c.getRealname());
		}
		
		if(!Utlity.checkStringNull(cah.getCompanyAccountIn())){
			CompanyAccount cai = this.companyAccountService.get(cah.getCompanyAccountIn());
			CompanyAccountVO caiVO = new CompanyAccountVO(cai);
			if(CompanyAccountType.THIRD.equals(caiVO.getType())){
				caiVO.setBankName(caiVO.getAccountName());
			}
			cahdVO.setCompanyAccountInInfo(caiVO);
		}
		if(!Utlity.checkStringNull(cah.getCompanyAccountOut())){
			CompanyAccount caout = this.companyAccountService.get(cah.getCompanyAccountOut());
			CompanyAccountVO caoutVO = new CompanyAccountVO(caout);
			if(CompanyAccountType.THIRD.equals(caoutVO.getType())){
				caoutVO.setBankName(caoutVO.getAccountName());
			}
			cahdVO.setCompanyAccountOutInfo(caoutVO);
		}
		if(!Utlity.checkStringNull(cah.getCompanyAccountOut())){
			CompanyAccount caout = this.companyAccountService.get(cah.getCompanyAccountOut());
			CompanyAccountVO caoutVO = new CompanyAccountVO(caout);
			if(CompanyAccountType.THIRD.equals(caoutVO.getType())){
				caoutVO.setBankName(caoutVO.getAccountName());
			}
			cahdVO.setCompanyAccountOutInfo(caoutVO);
		}
		if(!Utlity.checkStringNull(cah.getInvestor())){
			Investor i = this.investorService.get(cah.getInvestor());
			cahdVO.setInvestorInfo(i);
		}
		if(!Utlity.checkStringNull(cah.getQcbCompanyAccount())){
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(cah.getQcbCompanyAccount());
			cahdVO.setQcbCompanyAccountInfo(qca);
		}
		if(!Utlity.checkStringNull(cah.getQcbEmployee())){
			QcbEmployee qe = this.qcbEmployeeService.get(cah.getQcbEmployee());
			cahdVO.setQcbEmployeeInfo(qe);
		}
		if(!Utlity.checkStringNull(cah.getBankFinancialProduct())){
			BankFinancialProduct bfp = this.bankFinancialProductService.get(cah.getBankFinancialProduct());
			cahdVO.setBankFinancialProductInfo(bfp);
		}
		if(!Utlity.checkStringNull(cah.getFund())){
			Fund fund = this.fundService.get(cah.getFund());
			cahdVO.setFundInfo(fund);
		}
		return ResultManager.createDataResult(cahdVO);
	}
	/**
	 * ??????????????????
	 * @param companyAccountIn
	 * @param totalAmount
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@ActionParam(key = "companyAccountIn", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "purpose", message="????????????", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result recharge(String companyAccountIn, BigDecimal totalAmount, String purpose, String remark, String receipt){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("?????????????????????0???");
		}
		
		//????????????????????????
		CompanyAccount companyAccount = companyAccountService.get(companyAccountIn);
		if (companyAccount == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//????????????????????????
		CompanyAccountHistory cah = new CompanyAccountHistory();
		cah.setUuid(UUID.randomUUID().toString());
		cah.setCompanyAccountIn(companyAccountIn);
		cah.setType(CompanyAccountHistoryType.RECHARGE);
		cah.setTotalAmount(totalAmount);
		cah.setPoundage(BigDecimal.ZERO);
		cah.setPurpose(purpose);
		cah.setRemark(remark);
		cah.setStatus(CompanyAccountHistoryStatus.NORMAL);
		
		cah.setCreator(currentOperator.getUuid());
		cah.setCreatetime(new Timestamp(System.currentTimeMillis()));

		//20180622??????????????????????????????
		cah.setAccountBalance(companyAccount.getAccountBalance().add(totalAmount));
		
		//?????????????????????????????????
		CompanyAccountTransferOperate cato = new CompanyAccountTransferOperate();
		cato.setUuid(UUID.randomUUID().toString());
		cato.setType(CompanyAccountTransferOperateType.RECHARGE);
		cato.setValue(JSONUtils.obj2json(cah));
		cato.setStatus(CompanyAccountTransferOperateStatus.UNCHECKED);
		cato.setCreator(currentOperator.getUuid());
		cato.setSubmittime(new Timestamp(System.currentTimeMillis()));
		cato.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		cato.setReceipt(receipt);
		
		this.companyAccountTransferOperateService.insert(cato);
		
		return ResultManager.createDataResult(cato, "???????????????????????????");
	}
	
	/**
	 * ????????????????????????
	 * @param companyAccountOut
	 * @param totalAmount
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/expend", method = RequestMethod.POST)
	@ActionParam(key = "companyAccountOut", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="??????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "purpose", message="????????????", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result expend(String companyAccountOut, BigDecimal totalAmount, String purpose, String remark, String receipt){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("?????????????????????0???");
		}
		
		//????????????????????????
		CompanyAccount companyAccount = companyAccountService.get(companyAccountOut);
		if (companyAccount == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//????????????????????????
		CompanyAccountHistory cah = new CompanyAccountHistory();
		cah.setUuid(UUID.randomUUID().toString());
		cah.setCompanyAccountOut(companyAccountOut);
		cah.setType(CompanyAccountHistoryType.EXPEND);
		cah.setPoundage(BigDecimal.ZERO);
		cah.setTotalAmount(totalAmount);
		cah.setPurpose(purpose);
		cah.setRemark(remark);
		cah.setStatus(CompanyAccountHistoryStatus.NORMAL);
		
		cah.setCreator(currentOperator.getUuid());
		cah.setCreatetime(new Timestamp(System.currentTimeMillis()));
		//20180622??????????????????????????????
		cah.setAccountBalance(companyAccount.getAccountBalance().subtract(totalAmount));

		//?????????????????????????????????
		CompanyAccountTransferOperate cato = new CompanyAccountTransferOperate();
		cato.setUuid(UUID.randomUUID().toString());
		cato.setType(CompanyAccountTransferOperateType.EXPEND);
		cato.setValue(JSONUtils.obj2json(cah));
		cato.setStatus(CompanyAccountTransferOperateStatus.UNCHECKED);
		cato.setCreator(currentOperator.getUuid());
		cato.setSubmittime(new Timestamp(System.currentTimeMillis()));
		cato.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		cato.setReceipt(receipt);
		
		this.companyAccountTransferOperateService.insert(cato);
		
		return ResultManager.createDataResult(cato, "???????????????????????????");
	}
	
	/**
	 * ??????????????????
	 * @param companyAccountOut
	 * @param companyAccountIn
	 * @param totalAmount
	 * @param poundage
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	@ActionParam(key = "companyAccountOut", message="??????????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "companyAccountIn", message="??????????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "poundage", message="?????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "purpose", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result transfer(String companyAccountOut, String companyAccountIn, BigDecimal totalAmount, BigDecimal poundage, String purpose, 
			String remark, String receipt){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("?????????????????????0???");
		}
		
		//????????????????????????
		CompanyAccount companyAccount = companyAccountService.get(companyAccountOut);
		if (companyAccount == null) {
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		CompanyAccount companyAccountin = companyAccountService.get(companyAccountIn);
		if (companyAccountin == null) {
			return ResultManager.createFailResult("??????????????????????????????");
		}
		if(poundage.compareTo(BigDecimal.ZERO) < 0){
			return ResultManager.createFailResult("???????????????????????????");
		}
		if(totalAmount.add(poundage).compareTo(companyAccount.getAccountBalance()) > 0){
			return ResultManager.createFailResult("???????????????????????????");
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//????????????????????????
		CompanyAccountHistory cah = new CompanyAccountHistory();
		cah.setUuid(UUID.randomUUID().toString());
		cah.setCompanyAccountOut(companyAccountOut);
		cah.setCompanyAccountIn(companyAccountIn);
		cah.setType(CompanyAccountHistoryType.TRANSFER);

		cah.setTotalAmount(totalAmount);
		cah.setPoundage(poundage);
		cah.setPurpose(purpose);
		cah.setRemark(remark);
		cah.setStatus(CompanyAccountHistoryStatus.NORMAL);
		
		cah.setCreator(currentOperator.getUuid());
		cah.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//20180622??????????????????????????????
		cah.setAccountBalance(companyAccount.getAccountBalance().subtract(totalAmount));
		cah.setAccountBalanceIn(companyAccountin.getAccountBalance().add(totalAmount));//????????????????????????

		//?????????????????????????????????
		CompanyAccountTransferOperate cato = new CompanyAccountTransferOperate();
		cato.setUuid(UUID.randomUUID().toString());
		cato.setType(CompanyAccountTransferOperateType.TRANSFER);
		cato.setValue(JSONUtils.obj2json(cah));
		cato.setStatus(CompanyAccountTransferOperateStatus.UNCHECKED);
		cato.setCreator(currentOperator.getUuid());
		cato.setSubmittime(new Timestamp(System.currentTimeMillis()));
		cato.setCreatetime(new Timestamp(System.currentTimeMillis()));

		cato.setReceipt(receipt);
		
		this.companyAccountTransferOperateService.insert(cato);
		
		return ResultManager.createDataResult(cato, "???????????????????????????");
	}
	
	/** ??????????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//????????????????????????
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if (cato == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		CompanyAccountTransferOperateDetailVO catodVO = new CompanyAccountTransferOperateDetailVO(cato);
		BkOperator creator = this.bkOperatorService.get(cato.getCreator());
		if(creator != null){
			catodVO.setCreatorName(creator.getRealname());
		}
		
		//???????????????????????????
		if(catodVO.getReceipt() != null && !"".equals(catodVO.getReceipt())){
			List<Resource> reList = new ArrayList<Resource>();
			String[] receiptArr = catodVO.getReceipt().split(",");
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
			catodVO.setReceipt(str);
			catodVO.setListReceipt(reList);
		}
		
		CompanyAccountHistory cah = JSONUtils.json2obj(cato.getValue(), CompanyAccountHistory.class);
		CompanyAccountHistoryDetailVO cahdVO = new CompanyAccountHistoryDetailVO(cah);
		
		BkOperator c = this.bkOperatorService.get(cahdVO.getCreator());
		if(c != null){
			cahdVO.setCreatorName(c.getRealname());
		}
		
		if(!Utlity.checkStringNull(cah.getCompanyAccountIn())){
			CompanyAccount cai = this.companyAccountService.get(cah.getCompanyAccountIn());
			CompanyAccountVO caiVO = new CompanyAccountVO(cai);
			if(CompanyAccountType.THIRD.equals(caiVO.getType())){
				caiVO.setBankName(caiVO.getAccountName());
			}
			cahdVO.setCompanyAccountInInfo(caiVO);
		}
		if(!Utlity.checkStringNull(cah.getCompanyAccountOut())){
			CompanyAccount caout = this.companyAccountService.get(cah.getCompanyAccountOut());
			CompanyAccountVO caoutVO = new CompanyAccountVO(caout);
			if(CompanyAccountType.THIRD.equals(caoutVO.getType())){
				caoutVO.setBankName(caoutVO.getAccountName());
			}
			cahdVO.setCompanyAccountOutInfo(caoutVO);
		}
		catodVO.setNewData(cahdVO);
		return ResultManager.createDataResult(catodVO);
	}
	
	/**
	 * ??????????????????????????????(??????)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "transferType", message="????????????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String transferType, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("transferType", transferType);
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
		Integer totalResultCount = companyAccountTransferOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = companyAccountTransferOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccountTransferOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				CompanyAccountTransferOperate cao = (CompanyAccountTransferOperate)e;
				CompanyAccountTransferOperateVO caoVO = new CompanyAccountTransferOperateVO(cao);
				CompanyAccountHistory cah = JSONUtils.json2obj(cao.getValue(), CompanyAccountHistory.class);
				CompanyAccountHistoryDetailVO cahdVO = new CompanyAccountHistoryDetailVO(cah);
				
				BkOperator c = this.bkOperatorService.get(cahdVO.getCreator());
				if(c != null){
					cahdVO.setCreatorName(c.getRealname());
				}
				
				if(!Utlity.checkStringNull(cah.getCompanyAccountIn())){
					CompanyAccount cai = this.companyAccountService.get(cah.getCompanyAccountIn());
					CompanyAccountVO caiVO = new CompanyAccountVO(cai);
					cahdVO.setCompanyAccountInInfo(caiVO);
				}
				if(!Utlity.checkStringNull(cah.getCompanyAccountOut())){
					CompanyAccount caout = this.companyAccountService.get(cah.getCompanyAccountOut());
					CompanyAccountVO caoutVO = new CompanyAccountVO(caout);
					cahdVO.setCompanyAccountOutInfo(caoutVO);
				}
				caoVO.setCompanyAccountHistoryInfo(cahdVO);
				
				BkOperator creator = this.bkOperatorService.get(caoVO.getCreator());
				if(creator != null){
					caoVO.setCreatorName(creator.getRealname());
				}
				if(caoVO.getChecker() != null && !"".equals(caoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(caoVO.getChecker());
					if(checker != null){
						caoVO.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(caoVO);
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
	@ActionParam(key = "transferType", message="????????????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String transferType, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("transferType", transferType);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		//????????????????????????????????????????????????
		Integer totalResultCount = companyAccountTransferOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = companyAccountTransferOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccountTransferOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				CompanyAccountTransferOperate cao = (CompanyAccountTransferOperate)e;
				CompanyAccountTransferOperateVO caoVO = new CompanyAccountTransferOperateVO(cao);
				CompanyAccountHistory cah = JSONUtils.json2obj(cao.getValue(), CompanyAccountHistory.class);
				CompanyAccountHistoryDetailVO cahdVO = new CompanyAccountHistoryDetailVO(cah);
				
				BkOperator c = this.bkOperatorService.get(cahdVO.getCreator());
				if(c != null){
					cahdVO.setCreatorName(c.getRealname());
				}
				
				if(!Utlity.checkStringNull(cah.getCompanyAccountIn())){
					CompanyAccount cai = this.companyAccountService.get(cah.getCompanyAccountIn());
					CompanyAccountVO caiVO = new CompanyAccountVO(cai);
					cahdVO.setCompanyAccountInInfo(caiVO);
				}
				if(!Utlity.checkStringNull(cah.getCompanyAccountOut())){
					CompanyAccount caout = this.companyAccountService.get(cah.getCompanyAccountOut());
					CompanyAccountVO caoutVO = new CompanyAccountVO(caout);
					cahdVO.setCompanyAccountOutInfo(caoutVO);
				}
				caoVO.setCompanyAccountHistoryInfo(cahdVO);
				
				BkOperator creator = this.bkOperatorService.get(caoVO.getCreator());
				if(creator != null){
					caoVO.setCreatorName(creator.getRealname());
				}
				if(caoVO.getChecker() != null && !"".equals(caoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(caoVO.getChecker());
					if(checker != null){
						caoVO.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(caoVO);
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
	@ActionParam(key = "totalAmount", message="??????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "poundage", message="?????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "purpose", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateEdit(String uuid, BigDecimal totalAmount, BigDecimal poundage, String purpose, String remark, String receipt){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("?????????????????????0???");
		}
		
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if (cato != null) {
			CompanyAccountHistory cah = JSONUtils.json2obj(cato.getValue(), CompanyAccountHistory.class);
			
			if(CompanyAccountTransferOperateType.TRANSFER.equals(cato.getType())){
				if(poundage.compareTo(BigDecimal.ZERO) < 0){
					return ResultManager.createFailResult("???????????????????????????");
				}
			}
			
			if(!CompanyAccountTransferOperateType.RECHARGE.equals(cato.getType())){
				CompanyAccount ca = this.companyAccountService.get(cah.getCompanyAccountOut());
				if(ca != null){
					if(totalAmount.compareTo(ca.getAccountBalance()) > 0){
						return ResultManager.createFailResult("???????????????????????????");
					}
				}else{
					return ResultManager.createFailResult("???????????????????????????????????????????????????");
				}
			}
			
			cah.setTotalAmount(totalAmount);
			cah.setPoundage(poundage);
			cah.setPurpose(purpose);
			cah.setRemark(remark);
			
			//?????????????????????
			cato.setValue(JSONUtils.obj2json(cah));
			cato.setCreatetime(new Timestamp(System.currentTimeMillis()));

			cato.setReceipt(receipt);
			
			companyAccountTransferOperateService.update(cato);
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
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if(cato != null){
			if(!CompanyAccountTransferOperateStatus.DRAFT.equals(cato.getStatus()) && !CompanyAccountTransferOperateStatus.UNPASSED.equals(cato.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			cato.setStatus(CompanyAccountTransferOperateStatus.DELETED);
			companyAccountTransferOperateService.update(cato);
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
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if(cato != null){
			if(CompanyAccountTransferOperateStatus.CHECKED.equals(cato.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			cato.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cato.setStatus(CompanyAccountTransferOperateStatus.UNCHECKED);
			companyAccountTransferOperateService.update(cato);
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
		if(!CompanyAccountTransferOperateStatus.CHECKED.equals(status) && !CompanyAccountTransferOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//??????????????????????????????
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if(cato != null){
			if(!CompanyAccountTransferOperateStatus.UNCHECKED.equals(cato.getStatus())){
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
				companyAccountTransferOperateService.check(cato);
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
		searchMap.put("transferType", transferType);
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//??????????????????????????????
		List<Entity> list = companyAccountTransferOperateService.getStatusList(searchMap, StstusCountVO.class);
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
		searchMap.put("transferType", transferType);
		searchMap.put("status", "all");
		//??????????????????????????????
		List<Entity> list = companyAccountTransferOperateService.getStatusList(searchMap, StstusCountVO.class);
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
		searchMap.put("transferType", transferType);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = companyAccountTransferOperateService.getTypeList(searchMap,StstusCountVO.class);
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
		searchMap.put("transferType", transferType);
		searchMap.put("status", status);
		
		List<Entity> list = companyAccountTransferOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
