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
 * 后台企业账户信息管理
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
	 * 获取企业账户资金流水详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//获取企业账户资金流水信息
		CompanyAccountHistory cah = companyAccountHistoryService.get(uuid);
		if (cah == null) {
			return ResultManager.createFailResult("该条数据不存在！");
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
	 * 企业账户充值
	 * @param companyAccountIn
	 * @param totalAmount
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@ActionParam(key = "companyAccountIn", message="企业账户", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="充值金额", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "purpose", message="资金用途", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "receipt", message="上传凭证", type = DataType.STRING)
	@ResponseBody
	public Result recharge(String companyAccountIn, BigDecimal totalAmount, String purpose, String remark, String receipt){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("充值金额应大于0！");
		}
		
		//获取企业账户信息
		CompanyAccount companyAccount = companyAccountService.get(companyAccountIn);
		if (companyAccount == null) {
			return ResultManager.createFailResult("账户信息不存在！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//封装充值交易记录
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

		//20180622增加记录本次余额信息
		cah.setAccountBalance(companyAccount.getAccountBalance().add(totalAmount));
		
		//生成充值交易待审核记录
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
		
		return ResultManager.createDataResult(cato, "添加待审核记录成功");
	}
	
	/**
	 * 企业账户费用录入
	 * @param companyAccountOut
	 * @param totalAmount
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/expend", method = RequestMethod.POST)
	@ActionParam(key = "companyAccountOut", message="企业账户", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="金额", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "purpose", message="资金用途", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "receipt", message="上传凭证", type = DataType.STRING)
	@ResponseBody
	public Result expend(String companyAccountOut, BigDecimal totalAmount, String purpose, String remark, String receipt){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("支出金额应大于0！");
		}
		
		//获取企业账户信息
		CompanyAccount companyAccount = companyAccountService.get(companyAccountOut);
		if (companyAccount == null) {
			return ResultManager.createFailResult("账户信息不存在！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//封装充值交易记录
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
		//20180622增加记录本次余额信息
		cah.setAccountBalance(companyAccount.getAccountBalance().subtract(totalAmount));

		//生成充值交易待审核记录
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
		
		return ResultManager.createDataResult(cato, "添加待审核记录成功");
	}
	
	/**
	 * 企业账户转账
	 * @param companyAccountOut
	 * @param companyAccountIn
	 * @param totalAmount
	 * @param poundage
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	@ActionParam(key = "companyAccountOut", message="转出企业账户", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "companyAccountIn", message="转入企业账户", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "totalAmount", message="转账金额", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "poundage", message="手续费", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "purpose", message="资金用途", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "receipt", message="上传凭证", type = DataType.STRING)
	@ResponseBody
	public Result transfer(String companyAccountOut, String companyAccountIn, BigDecimal totalAmount, BigDecimal poundage, String purpose, 
			String remark, String receipt){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("转账金额应大于0！");
		}
		
		//获取企业账户信息
		CompanyAccount companyAccount = companyAccountService.get(companyAccountOut);
		if (companyAccount == null) {
			return ResultManager.createFailResult("转出账户信息不存在！");
		}
		
		CompanyAccount companyAccountin = companyAccountService.get(companyAccountIn);
		if (companyAccountin == null) {
			return ResultManager.createFailResult("转入账户信息不存在！");
		}
		if(poundage.compareTo(BigDecimal.ZERO) < 0){
			return ResultManager.createFailResult("手续费不能为负数！");
		}
		if(totalAmount.add(poundage).compareTo(companyAccount.getAccountBalance()) > 0){
			return ResultManager.createFailResult("转出账户余额不足！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//封装充值交易记录
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
		
		//20180622增加记录本次余额信息
		cah.setAccountBalance(companyAccount.getAccountBalance().subtract(totalAmount));
		cah.setAccountBalanceIn(companyAccountin.getAccountBalance().add(totalAmount));//入账账户余额记录

		//生成充值交易待审核记录
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
		
		return ResultManager.createDataResult(cato, "添加待审核记录成功");
	}
	
	/** 获取企业账户交易操作审核信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//获取企业账户信息
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if (cato == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		CompanyAccountTransferOperateDetailVO catodVO = new CompanyAccountTransferOperateDetailVO(cato);
		BkOperator creator = this.bkOperatorService.get(cato.getCreator());
		if(creator != null){
			catodVO.setCreatorName(creator.getRealname());
		}
		
		//上传的凭证图片信息
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
	 * 企业账户交易操作列表(编辑)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String transferType, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("transferType", transferType);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//查询符合条件的企业账户信息的总数
		Integer totalResultCount = companyAccountTransferOperateService.getCount(searchMap);
		//查询符合条件的企业账户信息列表
		List<Entity> list = companyAccountTransferOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccountTransferOperate.class);
		
		//封装可用信息到前台List
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
	 * 企业账户交易操作列表(管理员)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String transferType, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
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
		//查询符合条件的企业账户信息的总数
		Integer totalResultCount = companyAccountTransferOperateService.getCount(searchMap);
		//查询符合条件的企业账户信息列表
		List<Entity> list = companyAccountTransferOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, CompanyAccountTransferOperate.class);
		
		//封装可用信息到前台List
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
	 * 编辑企业账户交易操作信息
	 * @param uuid
	 * @param totalAmount
	 * @param poundage
	 * @param purpose
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "totalAmount", message="金额", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "poundage", message="手续费", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "purpose", message="资金用途", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "receipt", message="上传凭证", type = DataType.STRING)
	@ResponseBody
	public Result operateEdit(String uuid, BigDecimal totalAmount, BigDecimal poundage, String purpose, String remark, String receipt){
		if(totalAmount.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("交易金额应大于0！");
		}
		
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if (cato != null) {
			CompanyAccountHistory cah = JSONUtils.json2obj(cato.getValue(), CompanyAccountHistory.class);
			
			if(CompanyAccountTransferOperateType.TRANSFER.equals(cato.getType())){
				if(poundage.compareTo(BigDecimal.ZERO) < 0){
					return ResultManager.createFailResult("手续费不能为负数！");
				}
			}
			
			if(!CompanyAccountTransferOperateType.RECHARGE.equals(cato.getType())){
				CompanyAccount ca = this.companyAccountService.get(cah.getCompanyAccountOut());
				if(ca != null){
					if(totalAmount.compareTo(ca.getAccountBalance()) > 0){
						return ResultManager.createFailResult("转出账户余额不足！");
					}
				}else{
					return ResultManager.createFailResult("该条数据有误，请删除记录重新填报！");
				}
			}
			
			cah.setTotalAmount(totalAmount);
			cah.setPoundage(poundage);
			cah.setPurpose(purpose);
			cah.setRemark(remark);
			
			//修改待审核记录
			cato.setValue(JSONUtils.obj2json(cah));
			cato.setCreatetime(new Timestamp(System.currentTimeMillis()));

			cato.setReceipt(receipt);
			
			companyAccountTransferOperateService.update(cato);
			return ResultManager.createDataResult("修改待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除企业账户交易操作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取企业账户交易操作信息
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if(cato != null){
			if(!CompanyAccountTransferOperateStatus.DRAFT.equals(cato.getStatus()) && !CompanyAccountTransferOperateStatus.UNPASSED.equals(cato.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			cato.setStatus(CompanyAccountTransferOperateStatus.DELETED);
			companyAccountTransferOperateService.update(cato);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 企业账户交易提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//获取企业账户交易操作信息
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if(cato != null){
			if(CompanyAccountTransferOperateStatus.CHECKED.equals(cato.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			cato.setSubmittime(new Timestamp(System.currentTimeMillis()));
			cato.setStatus(CompanyAccountTransferOperateStatus.UNCHECKED);
			companyAccountTransferOperateService.update(cato);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核企业账户交易操作
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="审核原因", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!CompanyAccountTransferOperateStatus.CHECKED.equals(status) && !CompanyAccountTransferOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		//获取企业账户交易信息
		CompanyAccountTransferOperate cato = companyAccountTransferOperateService.get(uuid);
		if(cato != null){
			if(!CompanyAccountTransferOperateStatus.UNCHECKED.equals(cato.getStatus())){
				return ResultManager.createFailResult("该记录审核状态错误");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(cato.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("无法审核自己提交的操作记录");
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
				return ResultManager.createFailResult("数据操作出错！");
			}
			return ResultManager.createSuccessResult("审核记录成功！");
			
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取企业账户交易分状态列表(编辑)
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateStatusList(String transferType) {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("transferType", transferType);
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//获取企业账户交易信息
		List<Entity> list = companyAccountTransferOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取企业账户交易分状态列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckStatusList(String transferType) {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("transferType", transferType);
		searchMap.put("status", "all");
		//获取企业账户交易信息
		List<Entity> list = companyAccountTransferOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取企业账户交易分类型列表
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String transferType, String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("transferType", transferType);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = companyAccountTransferOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取企业账户交易分类型列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "transferType", message="业务类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String transferType, String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("transferType", transferType);
		searchMap.put("status", status);
		
		List<Entity> list = companyAccountTransferOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
