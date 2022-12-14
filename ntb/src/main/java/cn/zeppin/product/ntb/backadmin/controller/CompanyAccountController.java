/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountInvestService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountTransferOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundInvestOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IProductPublishBalanceOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountHistoryVO;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountInvestVO;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.CompanyAccountVO;
import cn.zeppin.product.ntb.backadmin.vo.PlatformAccountVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountType;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountUuid;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate.CompanyAccountOperateStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate.CompanyAccountOperateType;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.Resource.ResourceUrl;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbAdminService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author elegantclack
 * ??????????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/companyAccount")
public class CompanyAccountController extends BaseController {

	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private ICompanyAccountOperateService companyAccountOperateService;
	
	@Autowired
	private ICompanyAccountTransferOperateService companyAccountTransferOperateService;
	
	@Autowired
	private IBankFinancialProductInvestOperateService bankFinancialProductInvestOperateService;
	
	@Autowired
	private IFundInvestOperateService fundInvestOperateService;
	
	@Autowired
	private IProductPublishBalanceOperateService productPublishBalanceOperateService;
	
	@Autowired
	private ICompanyAccountHistoryService companyAccountHistoryService;
	
	@Autowired
	private ICompanyAccountInvestService companyAccountInvestService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IQcbAdminService qcbAdminService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IFundService fundService;
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IBankFinancialProductService bankFinancialProductService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IShbxUserService shbxUserService; 
	
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/operateTotalTypeList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateTotalTypeList(String status) {
		List<StstusCountVO> dataList = new ArrayList<StstusCountVO>();
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		searchMap.put("status", "editor");
		//????????????
		BigInteger infoNum = BigInteger.valueOf(this.companyAccountOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("info",infoNum));
		
		//????????????
		BigInteger investNum = BigInteger.valueOf(this.bankFinancialProductInvestOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("invest",investNum));
		
		//??????
		BigInteger balanceNum = BigInteger.valueOf(this.productPublishBalanceOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("balance",balanceNum));
		
		//??????????????????
		BigInteger currentNum = BigInteger.valueOf(this.fundInvestOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("current",currentNum));
		
		//??????????????????
		searchMap.put("transferType", "outside");
		BigInteger rechargeNum = BigInteger.valueOf(this.companyAccountTransferOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("recharge",rechargeNum));
		
		//??????????????????
		searchMap.put("transferType", "inside");
		BigInteger transferNum = BigInteger.valueOf(this.companyAccountTransferOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("transfer",transferNum));
		
		return ResultManager.createDataResult(dataList);
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTotalTypeList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckTotalTypeList(String status) {
		List<StstusCountVO> dataList = new ArrayList<StstusCountVO>();
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", CompanyAccountOperateStatus.UNCHECKED);
		
		//????????????
		BigInteger infoNum = BigInteger.valueOf(this.companyAccountOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("info",infoNum));
		
		//????????????
		BigInteger investNum = BigInteger.valueOf(this.bankFinancialProductInvestOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("invest",investNum));
		
		//??????
		BigInteger balanceNum = BigInteger.valueOf(this.productPublishBalanceOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("balance",balanceNum));
		
		//??????????????????
		BigInteger currentNum = BigInteger.valueOf(this.fundInvestOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("current",currentNum));
				
		//??????????????????
		searchMap.put("transferType", "outside");
		BigInteger rechargeNum = BigInteger.valueOf(this.companyAccountTransferOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("recharge",rechargeNum));
		
		//??????????????????
		searchMap.put("transferType", "inside");
		BigInteger transferNum = BigInteger.valueOf(this.companyAccountTransferOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("transfer",transferNum));
		
		return ResultManager.createDataResult(dataList);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/typeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, maxLength = 20)
	@ResponseBody
	public Result typeList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		List<Entity> list = companyAccountService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????? 
	 * @param name
	 * @param bank
	 * @param isBank
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "bank", message="?????????", type = DataType.STRING)
	@ActionParam(key = "isBank", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String bank, String isBank, String status, String type, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("bank", bank);
		searchMap.put("isBank", isBank);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//????????????????????????????????????????????????
		Integer totalResultCount = companyAccountService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = companyAccountService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccount.class);
		
		List<CompanyAccountVO> listvo = new ArrayList<CompanyAccountVO>();
		
		for(Entity e : list){
			CompanyAccount ca = (CompanyAccount) e;
			CompanyAccountVO cavo = new CompanyAccountVO(ca);
			
			if(CompanyAccountType.THIRD.equals(cavo.getType())){
				cavo.setBankName(cavo.getAccountName());
			}else{
				Bank b = this.bankService.get(ca.getBank());
				if(b != null){
					cavo.setBankName(b.getShortName());
					Resource resource = resourceService.get(b.getLogo());
					if (resource != null) {
						cavo.setBankIconUrl(resource.getUrl());
					}else{
						cavo.setBankIconUrl("");
					}
				}
				
				if(!Utlity.checkStringNull(ca.getBranchBank())){
					BranchBank bb = branchBankService.get(ca.getBranchBank());
					if(bb != null){
						cavo.setBranchBankName(bb.getName());
					}
				}
			}
			listvo.add(cavo);
		}
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/historyTypeList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result historyTypeList(String uuid) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", CompanyAccountHistoryStatus.NORMAL);
		searchMap.put("companyAccount", uuid);
		
		List<Entity> list = companyAccountHistoryService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param companyAccount
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/historyList", method = RequestMethod.GET)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result historyList(String companyAccount, String status, String type, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("companyAccount", companyAccount);
		searchMap.put("status", CompanyAccountHistoryStatus.NORMAL);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//????????????
		Integer totalResultCount = companyAccountHistoryService.getCount(searchMap);
		
		//????????????
		List<CompanyAccountHistoryVO> historyList = new ArrayList<CompanyAccountHistoryVO>();
		List<Entity> catList = this.companyAccountHistoryService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccountHistory.class);
		for(Entity e : catList){
			
			CompanyAccountHistory cah = (CompanyAccountHistory) e;
			CompanyAccountHistoryVO cahvo = new CompanyAccountHistoryVO(cah);
			
			if(CompanyAccountHistoryType.TRANSFER.equals(cahvo.getType()) || CompanyAccountHistoryType.RECHARGE.equals(cahvo.getType()) ||
					CompanyAccountHistoryType.REDEEM.equals(cahvo.getType()) || CompanyAccountHistoryType.RETURN.equals(cahvo.getType()) ||
					CompanyAccountHistoryType.TRANSFER.equals(cahvo.getType()) || CompanyAccountHistoryType.EXPEND.equals(cahvo.getType()) ||
					CompanyAccountHistoryType.QCB_RECHARGE.equals(cahvo.getType()) || CompanyAccountHistoryType.CUR_INVEST.equals(cahvo.getType()) || 
					CompanyAccountHistoryType.CUR_REDEEM.equals(cahvo.getType())){
				BkOperator o =this.bkOperatorService.get(cah.getCreator());
				if(o != null){
					cahvo.setCreatorName(o.getRealname());
				}
			}else if(CompanyAccountHistoryType.EMP_FILLIN.equals(cahvo.getType()) || CompanyAccountHistoryType.EMP_TAKEOUT.equals(cahvo.getType())){
				QcbEmployee qe = this.qcbEmployeeService.get(cahvo.getCreator());
				if(qe != null){
					cahvo.setCreatorName(qe.getRealname());
				}
			}else if(CompanyAccountHistoryType.SHBX_PAY_SHEBAO.equals(cahvo.getType())){
				ShbxUser su = this.shbxUserService.get(cahvo.getCreator());
				if(su != null){
					cahvo.setCreatorName(su.getRealname());
				}
			}else if(CompanyAccountHistoryType.QCB_TAKEOUT.equals(cahvo.getType())){
				QcbAdmin qa = this.qcbAdminService.get(cahvo.getCreator());
				if(qa != null){
					cahvo.setCreatorName(qa.getName());
				}
			}
			
			if(companyAccount.equals(cahvo.getCompanyAccountIn())){
				if(CompanyAccountHistoryType.TRANSFER.equals(cahvo.getType())){
					cahvo.setTypeCN("??????");
					CompanyAccount cao = this.companyAccountService.get(cahvo.getCompanyAccountOut());
					if(cao != null){
						cahvo.setAbountName(cao.getAccountName());
					}
				}else if(CompanyAccountHistoryType.RECHARGE.equals(cahvo.getType())){
					cahvo.setTypeCN("??????");
					cahvo.setAbountName("-");
				}else if(CompanyAccountHistoryType.REDEEM.equals(cahvo.getType())){
					cahvo.setTypeCN("??????");
					BankFinancialProduct bfp = this.bankFinancialProductService.get(cahvo.getBankFinancialProduct());
					if(bfp != null){
						cahvo.setAbountName(bfp.getName());
					}
				}else if(CompanyAccountHistoryType.RETURN.equals(cahvo.getType())){
					cahvo.setTypeCN("????????????");
					BankFinancialProduct bfp = this.bankFinancialProductService.get(cahvo.getBankFinancialProduct());
					if(bfp != null){
						cahvo.setAbountName(bfp.getName());
					}
				}else if(CompanyAccountHistoryType.FILLIN.equals(cahvo.getType())){
					cahvo.setTypeCN("????????????");
					Investor i = this.investorService.get(cahvo.getInvestor());
					if(i != null){
						cahvo.setAbountName(i.getRealname());
					}
				}else if(CompanyAccountHistoryType.QCB_RECHARGE.equals(cahvo.getType())){
					cahvo.setTypeCN("?????????????????????");
					QcbCompanyAccount qca = this.qcbCompanyAccountService.get(cahvo.getQcbCompanyAccount());
					if(qca != null){
						cahvo.setAbountName(qca.getName());
					}
				}else if(CompanyAccountHistoryType.EMP_FILLIN.equals(cahvo.getType())){
					cahvo.setTypeCN("?????????????????????");
					QcbEmployee qe = this.qcbEmployeeService.get(cahvo.getQcbEmployee());
					if(qe != null){
						cahvo.setAbountName(qe.getRealname());
					}
				}else if(CompanyAccountHistoryType.SHBX_PAY_SHEBAO.equals(cahvo.getType())){
					cahvo.setTypeCN("?????????????????????");
					ShbxUser su = this.shbxUserService.get(cahvo.getShbxUser());
					if(su != null){
						cahvo.setAbountName(su.getRealname());
					}
				}else if(CompanyAccountHistoryType.CUR_REDEEM.equals(cahvo.getType())){
					cahvo.setTypeCN("??????????????????");
					Fund fund = this.fundService.get(cahvo.getFund());
					if(fund != null){
						cahvo.setAbountName(fund.getName());
					}
				}
			}else{
				if(CompanyAccountHistoryType.TRANSFER.equals(cahvo.getType())){
					cahvo.setTypeCN("??????");
					CompanyAccount cai = this.companyAccountService.get(cahvo.getCompanyAccountIn());
					if(cai != null){
						cahvo.setAbountName(cai.getAccountName());
					}
				}else if(CompanyAccountHistoryType.EXPEND.equals(cahvo.getType())){
					cahvo.setTypeCN("???????????????");
					cahvo.setAbountName("-");
				}else if(CompanyAccountHistoryType.INVEST.equals(cahvo.getType())){
					cahvo.setTypeCN("??????");
					BankFinancialProduct bfp = this.bankFinancialProductService.get(cahvo.getBankFinancialProduct());
					if(bfp != null){
						cahvo.setAbountName(bfp.getName());
					}
				}else if(CompanyAccountHistoryType.TAKEOUT.equals(cahvo.getType())){
					cahvo.setTypeCN("????????????");
					Investor i = this.investorService.get(cahvo.getInvestor());
					if(i != null){
						cahvo.setAbountName(i.getRealname());
					}
				}else if(CompanyAccountHistoryType.QCB_TAKEOUT.equals(cahvo.getType())){
					cahvo.setTypeCN("?????????????????????");
					QcbCompanyAccount qca = this.qcbCompanyAccountService.get(cahvo.getQcbCompanyAccount());
					if(qca != null){
						cahvo.setAbountName(qca.getName());
					}
				}else if(CompanyAccountHistoryType.EMP_TAKEOUT.equals(cahvo.getType())){
					cahvo.setTypeCN("?????????????????????");
					QcbEmployee qe = this.qcbEmployeeService.get(cahvo.getQcbEmployee());
					if(qe != null){
						cahvo.setAbountName(qe.getRealname());
					}
				}else if(CompanyAccountHistoryType.CUR_INVEST.equals(cahvo.getType())){
					cahvo.setTypeCN("??????????????????");
					Fund fund = this.fundService.get(cahvo.getFund());
					if(fund != null){
						cahvo.setAbountName(fund.getName());
					}
				}
			}
			historyList.add(cahvo);
		}
		return ResultManager.createDataResult(historyList, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param companyAccount
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/investList", method = RequestMethod.GET)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING)
	@ActionParam(key = "stage", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result investList(String companyAccount, String stage, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("companyAccount", companyAccount);
		if(!"all".equals(stage)){
			searchMap.put("stage", stage);
		}
		
		//????????????
		Integer totalResultCount = companyAccountInvestService.getCount(searchMap);
		
		//????????????
		List<CompanyAccountInvestVO> investList = new ArrayList<CompanyAccountInvestVO>();
		List<Entity> dataList = this.companyAccountInvestService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccountInvestVO.class);
		for(Entity e : dataList){
			CompanyAccountInvestVO caivo = (CompanyAccountInvestVO) e;
			
			CompanyAccount ca = this.companyAccountService.get(caivo.getCompanyAccount());
			caivo.setCompanyAccountName(ca.getAccountName());
			
			BankFinancialProduct bfp = this.bankFinancialProductService.get(caivo.getBankFinancialProduct());
			caivo.setBankFinancialProductName(bfp.getName());
			caivo.setReturnRate(bfp.getTargetAnnualizedReturnRate());
			caivo.setMaturityDate(bfp.getMaturityDate());
			investList.add(caivo);
		}
		return ResultManager.createDataResult(investList, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//????????????????????????
		CompanyAccount companyAccount = companyAccountService.get(uuid);
		if (companyAccount == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//????????????
		CompanyAccountDetailVO companyAccountVO = new CompanyAccountDetailVO(companyAccount);
		
		//????????????
		if(CompanyAccountType.THIRD.equals(companyAccount.getType())){
			companyAccountVO.setBankName(companyAccount.getAccountName());
			if(CompanyAccountUuid.ALIPAY.equals(companyAccount.getUuid())){
				companyAccountVO.setBankIconUrl(ResourceUrl.ALIPAY);
			}else if(CompanyAccountUuid.WECHART.equals(companyAccount.getUuid())){
				companyAccountVO.setBankIconUrl(ResourceUrl.WECHART);
			}
		}else{
			Bank bank = bankService.get(companyAccount.getBank());
			if (bank != null) {
				companyAccountVO.setBankName(bank.getShortName());
				Resource resource = resourceService.get(bank.getLogo());
				if (resource != null) {
					companyAccountVO.setBankIconUrl(resource.getUrl());
				}else{
					companyAccountVO.setBankIconUrl("");
				}
			}
			
			if(!Utlity.checkStringNull(companyAccount.getBranchBank())){
				BranchBank bb = branchBankService.get(companyAccount.getBranchBank());
				if(bb != null){
					companyAccountVO.setBranchBankName(bb.getName());
				}
			}
		}
		
		return ResultManager.createDataResult(companyAccountVO);
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/getPlatform", method = RequestMethod.GET)
	@ResponseBody
	public Result getPlatform(){
		PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.TOTAL);
		PlatformAccount pab = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
		PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
		PlatformAccountVO pavo = new PlatformAccountVO(pa);
		pavo.setTotalReturn(pai.getTotalReturn());
		pavo.setTotalReturnCN(Utlity.numFormat4UnitDetail(pai.getTotalReturn()));
		pavo.setInvestment(pa.getInvestment().subtract(pa.getTotalRedeem()));
		pavo.setInvestmentCN(Utlity.numFormat4UnitDetail(pa.getInvestment().subtract(pa.getTotalRedeem()).setScale(2, RoundingMode.FLOOR)));
		pavo.setAccountBalance(pab.getTotalAmount().add(pab.getInvestment()).subtract(pab.getTotalRedeem()));
		pavo.setAccountBalanceCN(Utlity.numFormat4UnitDetail(pab.getTotalAmount().add(pab.getInvestment()).subtract(pab.getTotalRedeem()).setScale(2, RoundingMode.FLOOR)));
		pavo.setTotalBalance(pab.getTotalAmount().add(pai.getTotalAmount()));
		pavo.setTotalBalanceCN(Utlity.numFormat4UnitDetail(pab.getTotalAmount().add(pai.getTotalAmount()).setScale(2, RoundingMode.FLOOR)));
		pavo.setInvestorInvestment(pai.getInvestment().subtract(pai.getTotalRedeem()));
		pavo.setInvestorInvestmentCN(Utlity.numFormat4UnitDetail(pai.getInvestment().subtract(pai.getTotalRedeem()).setScale(2, RoundingMode.FLOOR)));
		return ResultManager.createDataResult(pavo);
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/getPlatformBalance", method = RequestMethod.GET)
	@ResponseBody
	public Result getPlatformBalance(){
		PlatformAccount pab = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
		PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
		PlatformAccountVO pavo = new PlatformAccountVO(pab);
		pavo.setTotalAmount(pab.getTotalAmount().add(pai.getTotalAmount()));
		pavo.setTotalAmountCN(Utlity.numFormat4UnitDetail(pab.getTotalAmount().add(pai.getTotalAmount()).setScale(2, RoundingMode.FLOOR)));
		return ResultManager.createDataResult(pavo);
	}
	
	/**
	 * ????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/getQcbEmpBalance", method = RequestMethod.GET)
	@ResponseBody
	public Result getQcbEmpBalance(){
		BigDecimal totalBalance = this.qcbEmployeeService.getTotalBalacne();
		PlatformAccountVO pavo = new PlatformAccountVO();
		pavo.setQcbEmpTotalBalance(totalBalance);
		pavo.setQcbEmpTotalBalanceCN(Utlity.numFormat4UnitDetail(totalBalance));
		return ResultManager.createDataResult(pavo);
	}
	
	/**
	 * ??????????????????????????????
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param type
	 * @param bank
	 * @param branchBank
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "accountName", message="????????????", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "accountNum", message="??????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "companyName", message="?????????", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "type", message="??????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "bank", message="????????????", type = DataType.STRING)
	@ActionParam(key = "branchBank", message="????????????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result add(String accountName, String accountNum, String companyName, String type, String bank, String branchBank, String status) {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		CompanyAccount ca = new CompanyAccount();
		ca.setUuid(UUID.randomUUID().toString());
		if(!Utlity.checkStringNull(bank)){
			Bank b = bankService.get(bank);
			if(b != null){
				ca.setBank(bank);
			}else{
				return ResultManager.createFailResult("???????????????????????????");
			}
		}
		if(!Utlity.checkStringNull(branchBank)){
			BranchBank bb = branchBankService.get(branchBank);
			if(bb != null && bb.getBank().equals(bank)){
				ca.setBranchBank(branchBank);
			}else{
				return ResultManager.createFailResult("???????????????????????????");
			}
		}
		ca.setAccountName(accountName);
		ca.setAccountNum(accountNum);
		ca.setCompanyName(companyName);
		ca.setType(type);
		ca.setStatus(status);
		ca.setAccountBalance(BigDecimal.ZERO);
		ca.setInvestment(BigDecimal.ZERO);
		ca.setTotalRedeem(BigDecimal.ZERO);
		ca.setTotalReturn(BigDecimal.ZERO);
		ca.setCreator(currentOperator.getUuid());
		ca.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		
		//?????????????????????
		CompanyAccountOperate cao = new CompanyAccountOperate();
		cao.setUuid(UUID.randomUUID().toString());
		cao.setType(CompanyAccountOperateType.ADD);
		cao.setValue(JSONUtils.obj2json(ca));
		cao.setStatus(CompanyAccountOperateStatus.UNCHECKED);
		cao.setCreator(currentOperator.getUuid());
		cao.setSubmittime(new Timestamp(System.currentTimeMillis()));
		cao.setCreatetime(new Timestamp(System.currentTimeMillis()));
		companyAccountOperateService.insert(cao);
		return ResultManager.createDataResult(ca, "???????????????????????????");
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param branchBank
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "accountName", message="????????????", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "accountNum", message="??????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "companyName", message="?????????", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "branchBank", message="????????????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result edit(String uuid, String accountName, String accountNum, String companyName, String branchBank, String status) {
		
		//????????????????????????
		CompanyAccount ca = companyAccountService.get(uuid);
		if(ca != null && uuid.equals(ca.getUuid())){
			
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("companyAccount", uuid);
			searchMap.put("type", CompanyAccountOperateType.EDIT);
			searchMap.put("status", CompanyAccountOperateStatus.UNCHECKED);
			
			Integer count = this.companyAccountOperateService.getCount(searchMap);
			if(count > 0 ){
				return ResultManager.createFailResult("??????????????????????????????????????????????????????");
			}
			
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			ca.setAccountName(accountName);
			ca.setAccountNum(accountNum);
			ca.setCompanyName(companyName);
			if(!Utlity.checkStringNull(branchBank)){
				BranchBank bb = branchBankService.get(branchBank);
				if(bb != null && bb.getBank().equals(ca.getBank())){
					ca.setBranchBank(branchBank);
				}else{
					return ResultManager.createFailResult("???????????????????????????");
				}
			}
			ca.setStatus(status);
			
			//?????????????????????
			CompanyAccountOperate cao = new CompanyAccountOperate();
			cao.setUuid(UUID.randomUUID().toString());
			cao.setCompanyAccount(ca.getUuid());
			cao.setType(CompanyAccountOperateType.EDIT);
			cao.setValue(JSONUtils.obj2json(ca));
			cao.setStatus(CompanyAccountOperateStatus.UNCHECKED);
			cao.setCreator(currentOperator.getUuid());
			cao.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cao.setCreatetime(new Timestamp(System.currentTimeMillis()));
			companyAccountOperateService.insert(cao);
			return ResultManager.createDataResult(ca, "???????????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????(??????)
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
		Integer totalResultCount = companyAccountOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = companyAccountOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccountOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				CompanyAccountOperate cao = (CompanyAccountOperate)e;
				CompanyAccountOperateVO caoVO = new CompanyAccountOperateVO(cao);
				if(CompanyAccountOperateType.ADD.equals(cao.getType())){
					CompanyAccount ca = JSONUtils.json2obj(cao.getValue(), CompanyAccount.class);
					caoVO.setCompanyAccountName(ca.getAccountName());
					caoVO.setCompanyAccountNum(ca.getAccountNum());
					caoVO.setCompanyAccountCompany(ca.getCompanyName());
					if(CompanyAccountType.THIRD.equals(ca.getType())){
						caoVO.setCompanyAccountTypeCN("?????????");
					}else if(CompanyAccountType.COLLECT.equals(ca.getType())){
						caoVO.setCompanyAccountTypeCN("?????????");
					}else if(CompanyAccountType.INVEST.equals(ca.getType())){
						caoVO.setCompanyAccountTypeCN("?????????");
					}else if(CompanyAccountType.REDEEM.equals(ca.getType())){
						caoVO.setCompanyAccountTypeCN("?????????");
					}
				}
				if(caoVO.getCompanyAccount() != null && !"".equals(caoVO.getCompanyAccount())){
					CompanyAccount ca = this.companyAccountService.get(caoVO.getCompanyAccount());
					if(ca != null){
						caoVO.setCompanyAccountName(ca.getAccountName());
						caoVO.setCompanyAccountNum(ca.getAccountNum());
						caoVO.setCompanyAccountCompany(ca.getCompanyName());
						if(CompanyAccountType.THIRD.equals(ca.getType())){
							caoVO.setCompanyAccountTypeCN("?????????");
						}else if(CompanyAccountType.COLLECT.equals(ca.getType())){
							caoVO.setCompanyAccountTypeCN("?????????");
						}else if(CompanyAccountType.INVEST.equals(ca.getType())){
							caoVO.setCompanyAccountTypeCN("?????????");
						}else if(CompanyAccountType.REDEEM.equals(ca.getType())){
							caoVO.setCompanyAccountTypeCN("?????????");
						}
					}
				}
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
	 * ????????????????????????(?????????)
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
		Integer totalResultCount = companyAccountOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = companyAccountOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccountOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				CompanyAccountOperate cao = (CompanyAccountOperate)e;
				CompanyAccountOperateVO caoVO = new CompanyAccountOperateVO(cao);
				if(CompanyAccountOperateType.ADD.equals(cao.getType())){
					CompanyAccount ca = JSONUtils.json2obj(cao.getValue(), CompanyAccount.class);
					caoVO.setCompanyAccountName(ca.getAccountName());
					caoVO.setCompanyAccountNum(ca.getAccountNum());
					caoVO.setCompanyAccountCompany(ca.getCompanyName());
					if(CompanyAccountType.THIRD.equals(ca.getType())){
						caoVO.setCompanyAccountTypeCN("?????????");
					}else if(CompanyAccountType.COLLECT.equals(ca.getType())){
						caoVO.setCompanyAccountTypeCN("?????????");
					}else if(CompanyAccountType.INVEST.equals(ca.getType())){
						caoVO.setCompanyAccountTypeCN("?????????");
					}else if(CompanyAccountType.REDEEM.equals(ca.getType())){
						caoVO.setCompanyAccountTypeCN("?????????");
					}
				}
				if(caoVO.getCompanyAccount() != null && !"".equals(caoVO.getCompanyAccount())){
					CompanyAccount ca = this.companyAccountService.get(caoVO.getCompanyAccount());
					if(ca != null){
						caoVO.setCompanyAccountName(ca.getAccountName());
						caoVO.setCompanyAccountNum(ca.getAccountNum());
						caoVO.setCompanyAccountCompany(ca.getCompanyName());
						if(CompanyAccountType.THIRD.equals(ca.getType())){
							caoVO.setCompanyAccountTypeCN("?????????");
						}else if(CompanyAccountType.COLLECT.equals(ca.getType())){
							caoVO.setCompanyAccountTypeCN("?????????");
						}else if(CompanyAccountType.INVEST.equals(ca.getType())){
							caoVO.setCompanyAccountTypeCN("?????????");
						}else if(CompanyAccountType.REDEEM.equals(ca.getType())){
							caoVO.setCompanyAccountTypeCN("?????????");
						}
					}
				}
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
	
	/** ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//????????????????????????
		CompanyAccountOperate cao = companyAccountOperateService.get(uuid);
		if (cao == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		CompanyAccountOperateDetailVO caodVO = new CompanyAccountOperateDetailVO(cao);
		BkOperator creator = this.bkOperatorService.get(cao.getCreator());
		if(creator != null){
			caodVO.setCreatorName(creator.getRealname());
		}
		
		if(!CompanyAccountOperateType.ADD.equals(cao.getType())){
			if(CompanyAccountOperateType.EDIT.equals(cao.getType()) && CompanyAccountOperateStatus.CHECKED.equals(cao.getStatus()) 
					&& cao.getOld() != null && !"".equals(cao.getOld())){
				
				CompanyAccount ca = JSONUtils.json2obj(cao.getOld(), CompanyAccount.class);	
				CompanyAccountDetailVO cadVO = new CompanyAccountDetailVO(ca);
				
				//????????????
				if(CompanyAccountType.THIRD.equals(ca.getType())){
					cadVO.setBankName(ca.getAccountName());
				}else{
					Bank bank = bankService.get(ca.getBank());
					if (bank != null) {
						cadVO.setBankName(bank.getShortName());
						Resource resource = resourceService.get(bank.getLogo());
						if (resource != null) {
							cadVO.setBankIconUrl(resource.getUrl());
						}else{
							cadVO.setBankIconUrl("");
						}
					}
					
					if(!Utlity.checkStringNull(ca.getBranchBank())){
						BranchBank bb = branchBankService.get(ca.getBranchBank());
						if(bb != null){
							cadVO.setBranchBankName(bb.getName());
						}
					}
				}
				caodVO.setOldData(cadVO);
			}else{
				CompanyAccount ca = companyAccountService.get(cao.getCompanyAccount());
				CompanyAccountDetailVO cadVO = new CompanyAccountDetailVO(ca);
				
				//????????????
				if(CompanyAccountType.THIRD.equals(ca.getType())){
					cadVO.setBankName(ca.getAccountName());
					if(CompanyAccountUuid.ALIPAY.equals(ca.getUuid())){
						cadVO.setBankIconUrl(ResourceUrl.ALIPAY);
					}else if(CompanyAccountUuid.WECHART.equals(ca.getUuid())){
						cadVO.setBankIconUrl(ResourceUrl.WECHART);
					}
				}else{
					Bank bank = bankService.get(ca.getBank());
					if (bank != null) {
						cadVO.setBankName(bank.getShortName());
						Resource resource = resourceService.get(bank.getLogo());
						if (resource != null) {
							cadVO.setBankIconUrl(resource.getUrl());
						}else{
							cadVO.setBankIconUrl("");
						}
					}
					
					if(!Utlity.checkStringNull(ca.getBranchBank())){
						BranchBank bb = branchBankService.get(ca.getBranchBank());
						if(bb != null){
							cadVO.setBranchBankName(bb.getName());
						}
					}
				}
				caodVO.setOldData(cadVO);
			}
		}
		if(CompanyAccountOperateType.ADD.equals(cao.getType()) || CompanyAccountOperateType.EDIT.equals(cao.getType())){
			CompanyAccount ca = JSONUtils.json2obj(cao.getValue(), CompanyAccount.class);	
			CompanyAccountDetailVO cadVO = new CompanyAccountDetailVO(ca);
			
			//????????????
			if(CompanyAccountType.THIRD.equals(ca.getType())){
				cadVO.setBankName(ca.getAccountName());
			}else{
				Bank bank = bankService.get(ca.getBank());
				if (bank != null) {
					cadVO.setBankName(bank.getShortName());
					Resource resource = resourceService.get(bank.getLogo());
					if (resource != null) {
						cadVO.setBankIconUrl(resource.getUrl());
					}else{
						cadVO.setBankIconUrl("");
					}
				}
				
				if(!Utlity.checkStringNull(ca.getBranchBank())){
					BranchBank bb = branchBankService.get(ca.getBranchBank());
					if(bb != null){
						cadVO.setBranchBankName(bb.getName());
					}
				}
			}
			caodVO.setNewData(cadVO);
		}
		return ResultManager.createDataResult(caodVO);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @param accountName
	 * @param accountNum
	 * @param companyName
	 * @param branchBank
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "accountName", message="????????????", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "accountNum", message="??????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "companyName", message="?????????", type = DataType.STRING, required = true, maxLength = 50)
	@ActionParam(key = "branchBank", message="????????????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateEdit(String uuid, String accountName, String accountNum, String companyName, String branchBank, String status){
		//??????????????????????????????
		CompanyAccountOperate cao = companyAccountOperateService.get(uuid);
		if (cao != null) {
			CompanyAccount ca = JSONUtils.json2obj(cao.getValue(), CompanyAccount.class);
			
			if(!Utlity.checkStringNull(branchBank)){
				BranchBank bb = branchBankService.get(branchBank);
				if(bb != null && bb.getBank().equals(ca.getBank())){
					ca.setBranchBank(branchBank);
				}else{
					return ResultManager.createFailResult("???????????????????????????");
				}
			}
			ca.setAccountName(accountName);
			ca.setAccountNum(accountNum);
			ca.setCompanyName(companyName);
			ca.setStatus(status);
			
			//?????????????????????
			cao.setValue(JSONUtils.obj2json(ca));
			cao.setCreatetime(new Timestamp(System.currentTimeMillis()));
			companyAccountOperateService.update(cao);
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
		//??????????????????????????????
		CompanyAccountOperate cao = companyAccountOperateService.get(uuid);
		if(cao != null){
			if(!CompanyAccountOperateStatus.DRAFT.equals(cao.getStatus()) && !CompanyAccountOperateStatus.UNPASSED.equals(cao.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			cao.setStatus(CompanyAccountOperateStatus.DELETED);
			companyAccountOperateService.update(cao);
			return ResultManager.createSuccessResult("???????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}

	/**
	 *???????????????-????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//??????????????????????????????
		CompanyAccountOperate cao = companyAccountOperateService.get(uuid);
		if(cao != null){
			if(CompanyAccountOperateStatus.CHECKED.equals(cao.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			if(CompanyAccountOperateType.EDIT.equals(cao.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("companyAccount", cao.getCompanyAccount());
				searchMap.put("type", cao.getType());
				searchMap.put("status", CompanyAccountOperateStatus.UNCHECKED);
				
				Integer count = this.companyAccountOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("??????????????????????????????????????????????????????");
				}
			}
			cao.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cao.setStatus(CompanyAccountOperateStatus.UNCHECKED);
			companyAccountOperateService.update(cao);
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
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
		if(!CompanyAccountOperateStatus.CHECKED.equals(status) && !CompanyAccountOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//??????????????????????????????
		CompanyAccountOperate cao = companyAccountOperateService.get(uuid);
		if(cao != null){
			if(!CompanyAccountOperateStatus.UNCHECKED.equals(cao.getStatus())){
				return ResultManager.createFailResult("???????????????????????????");
			}
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(cao.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("???????????????????????????????????????");
			}
			
			cao.setChecker(currentOperator.getUuid());
			cao.setChecktime(new Timestamp(System.currentTimeMillis()));
			cao.setStatus(status);
			cao.setReason(reason);
			try {
				companyAccountOperateService.check(cao);
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
	@ResponseBody
	public Result operateStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//?????????????????????????????????
		List<Entity> list = companyAccountOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//?????????????????????????????????
		List<Entity> list = companyAccountOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(??????)
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String status) {
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
		
		List<Entity> list = companyAccountOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = companyAccountOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
