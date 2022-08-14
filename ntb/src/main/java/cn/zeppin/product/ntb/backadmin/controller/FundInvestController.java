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
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundInvestOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundInvestService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.FundInvestOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.FundInvestOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.FundInvestVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.Fund.FundStatus;
import cn.zeppin.product.ntb.core.entity.FundInvest;
import cn.zeppin.product.ntb.core.entity.FundInvest.FundInvestType;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate.FundInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundInvestOperate.FundInvestOperateType;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 后台银行理财产品投资管理
 */

@Controller
@RequestMapping(value = "/backadmin/fundInvest")
public class FundInvestController extends BaseController {

	@Autowired
	private IFundInvestService fundInvestService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private IFundService fundService;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IFundInvestOperateService fundInvestOperateService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	

	/**
	 * 根据条件查询投资信息
	 * @param fund
	 * @return
	 */
	@RequestMapping(value = "/investList", method = RequestMethod.GET)
	@ActionParam(key = "fund", message="投资产品", type = DataType.STRING)//投资产品
	@ResponseBody
	public Result investList(String fund) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("fund", fund);
		
		//查询符合条件的银行理财产品投资信息列表
		List<Entity> list = fundInvestService.getListForPage(searchMap, -1, -1, null, FundInvest.class);
		
		Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();
		resultMap.put(PlatformAccountUuid.BALANCE, BigDecimal.ZERO);
		resultMap.put(FundPublishUuid.CURRENT, BigDecimal.ZERO);
		
		if(list != null && list.size() > 0){
			for(Entity e: list){
				FundInvest fi = (FundInvest)e;
				
				BigDecimal old = resultMap.get(fi.getFundPublish());
				BigDecimal amount = fi.getTotalAmount();
				if(FundInvestType.INVEST.equals(fi.getType())){
					resultMap.put(fi.getFundPublish(), old.add(amount));
				}else if(FundInvestType.REDEEM.equals(fi.getType())){
					resultMap.put(fi.getFundPublish(), old.subtract(amount));
				}
			}
		}
		return ResultManager.createDataResult(resultMap);
	}
	
	/**
	 * 根据条件查询投资信息 
	 * @param status
	 * @param stage
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "stage", message="投资阶段", type = DataType.STRING)
	@ActionParam(key = "companyAccount", message="企业账户uuid", type = DataType.STRING)
	@ActionParam(key = "fund", message="投资账户uuid", type = DataType.STRING)//投资产品
	@ActionParam(key = "fundPublish", message="募集账户uuid", type = DataType.STRING)//募集产品产品
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String stage, String fund, String fundPublish, String companyAccount, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(stage)){
			searchMap.put("stage", stage);
		}
		searchMap.put("companyAccount", companyAccount);
		searchMap.put("fund", fund);
		searchMap.put("fundPublish", fundPublish);
		
		//查询符合条件的银行理财产品投资信息的总数
		Integer totalResultCount = fundInvestService.getCount(searchMap);
		//查询符合条件的银行理财产品投资信息列表
		List<Entity> list = fundInvestService.getListForPage(searchMap, pageNum, pageSize, sorts, FundInvest.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				FundInvest fi = (FundInvest)e;
				FundInvestVO fiVO = new FundInvestVO(fi);
				
				Fund f = this.fundService.get(fi.getFund());
				if(f != null){
					fiVO.setFundName(f.getName() + "(" + f.getScode()+ ")");
				}
				if(FundPublishUuid.CURRENT.equals(fi.getFundPublish())){
					fiVO.setFundPublishName("活期理财产品");
				}else{
					FundPublish fp = this.fundPublishService.get(fi.getFundPublish());
					if(fp != null){
						fiVO.setFundPublishName(fp.getName() + "(" + fp.getScode()+ ")");
					}
				}
				
				CompanyAccount ca = this.companyAccountService.get(fi.getCompanyAccount());
				if(ca != null){
					fiVO.setCompanyAccountName(ca.getAccountName());
				}
				
				BkOperator creator = this.bkOperatorService.get(fi.getCreator());
				if(creator != null){
					fiVO.setCreatorName(creator.getRealname());
				}
				listVO.add(fiVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 投资
	 * @param companyAccount
	 * @param fund
	 * @param data
	 * @param accountBalance
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/invest", method = RequestMethod.POST)
	@ActionParam(key = "companyAccount", message="企业账户", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "fund", message="投资产品", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "data", message="投资数据", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "accountBalance", message="产品总余额", type = DataType.POSITIVE_CURRENCY, maxLength = 10, required = true)
	@ActionParam(key = "remark", message="备注信息", type = DataType.STRING)
	@ResponseBody
	public Result invest(String companyAccount, String fund, String[] data, BigDecimal accountBalance, String remark) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		Fund f = this.fundService.get(fund);
		if(f == null){
			return ResultManager.createFailResult("投资产品不存在");
		}
		
		if(!FundStatus.CHECKED.equals(f.getStatus())){
			return ResultManager.createFailResult("投资产品已被删除");
		}
		
		CompanyAccount ca = this.companyAccountService.get(companyAccount);
		if(ca == null){
			return ResultManager.createFailResult("企业账户不存在");
		}
		
		if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
			return ResultManager.createFailResult("企业账户已停用");
		}
		
		List<FundInvest> fiList = new ArrayList<FundInvest>();
		BigDecimal sum = BigDecimal.ZERO;
		for(String values : data){
			String[] value = values.split("_");
			String fundPublish = value[0];
			String amountString = value[1];
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
			
			if(amount.compareTo(BigDecimal.ZERO) > 0){
				FundInvest fi = new FundInvest();
				fi.setUuid(UUID.randomUUID().toString());
				fi.setFund(fund);
				fi.setCompanyAccount(companyAccount);
				fi.setFundPublish(fundPublish);
				if(!Utlity.isPositiveCurrency4Web(amountString)){
					return ResultManager.createFailResult("投资金额输入有误！");
				}
				if(FundPublishUuid.CURRENT.equals(fundPublish)){
					BigDecimal total = this.fundPublishService.getAccountBalance();
					if(amount.compareTo(total) > 0){
						return ResultManager.createFailResult("活期理财账户资金不足！");
					}
				}else if(PlatformAccountUuid.BALANCE.equals(fundPublish)){
					PlatformAccount pa = this.platformAccountService.get(fundPublish);
					if(amount.compareTo(pa.getTotalAmount()) > 0){
						return ResultManager.createFailResult("平台余额账户资金不足！");
					}
				}else{
					return ResultManager.createFailResult("募集产品不存在！");
				}
				fi.setTotalAmount(amount);
				fi.setAccountBalance(accountBalance);
				fi.setType(FundInvestType.INVEST);
				fi.setCreator(currentOperator.getUuid());
				fi.setCreatetime(new Timestamp(System.currentTimeMillis()));
				fiList.add(fi);
				sum = sum.add(amount);
			}
		}
		
		if(sum.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("未进行任何资金操作！");
		}
		
		if(sum.compareTo(ca.getAccountBalance()) > 0){
			return ResultManager.createFailResult("企业账户余额不足！");
		}
		
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("dataList", JSONUtils.obj2json(fiList));
		valueMap.put("totalAmount", sum.toString());
		valueMap.put("remark", remark);
		
		//添加待审核记录
		FundInvestOperate fio = new FundInvestOperate();
		fio.setUuid(UUID.randomUUID().toString());
		fio.setFund(fund);
		fio.setType(FundInvestOperateType.INVEST);
		fio.setValue(JSONUtils.obj2json(valueMap));
		fio.setStatus(FundInvestOperateStatus.UNCHECKED);
		fio.setCreator(currentOperator.getUuid());
		fio.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fio.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		fundInvestOperateService.insert(fio);
		return ResultManager.createDataResult(fio, "添加待审记录成功！");
	}
	
	/**
	 * 赎回
	 * @param companyAccount
	 * @param fund
	 * @param data
	 * @param accountBalance
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/redeem", method = RequestMethod.POST)
	@ActionParam(key = "companyAccount", message="企业账户", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "fund", message="投资产品", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "data", message="数据", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "accountBalance", message="产品总余额", type = DataType.POSITIVE_CURRENCY, maxLength = 10, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ResponseBody
	public Result redeem(String companyAccount, String fund, String[] data, BigDecimal accountBalance, String remark) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		Fund f = this.fundService.get(fund);
		if(f == null){
			return ResultManager.createFailResult("投资产品不存在");
		}
		
		if(!FundStatus.CHECKED.equals(f.getStatus())){
			return ResultManager.createFailResult("投资产品已被删除");
		}
		
		CompanyAccount ca = this.companyAccountService.get(companyAccount);
		if(ca == null){
			return ResultManager.createFailResult("企业账户不存在");
		}
		
		if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
			return ResultManager.createFailResult("企业账户已停用");
		}
		
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("companyAccount", companyAccount);
		searchMap.put("fund", fund);
		//查询符合条件的银行理财产品投资信息列表
		Integer count = fundInvestService.getCount(searchMap);
		if(count == 0){
			return ResultManager.createFailResult("不存在投资信息，无法赎回");
		}
		
		List<FundInvest> fiList = new ArrayList<FundInvest>();
		BigDecimal sum = BigDecimal.ZERO;
		for(String values : data){
			String[] value = values.split("_");
			String fundPublish = value[0];
			String amountString = value[1];
			
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
			
			if(amount.compareTo(BigDecimal.ZERO) > 0){
				if(!FundPublishUuid.CURRENT.equals(fundPublish) && !PlatformAccountUuid.BALANCE.equals(fundPublish)){
					return ResultManager.createFailResult("募集产品不存在！");
				}
				FundInvest fi = new FundInvest();
				fi.setUuid(UUID.randomUUID().toString());
				fi.setFund(fund);
				fi.setCompanyAccount(companyAccount);
				if(!Utlity.isPositiveCurrency4Web(amountString)){
					return ResultManager.createFailResult("赎回金额输入有误！");
				}
				fi.setFundPublish(fundPublish);
				fi.setTotalAmount(amount);
				fi.setAccountBalance(accountBalance);
				fi.setType(FundInvestType.REDEEM);
				fi.setCreator(currentOperator.getUuid());
				fi.setCreatetime(new Timestamp(System.currentTimeMillis()));
				fiList.add(fi);
				sum = sum.add(amount);
			}
		}
		
		if(sum.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("未进行任何资金操作！");
		}
		
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("dataList", JSONUtils.obj2json(fiList));
		valueMap.put("totalAmount", sum.toString());
		valueMap.put("remark", remark);
		//添加待审核记录
		FundInvestOperate fio = new FundInvestOperate();
		fio.setUuid(UUID.randomUUID().toString());
		fio.setFund(fund);
		fio.setType(FundInvestOperateType.REDEEM);
		fio.setValue(JSONUtils.obj2json(valueMap));
		fio.setStatus(FundInvestOperateStatus.UNCHECKED);
		fio.setCreator(currentOperator.getUuid());
		fio.setSubmittime(new Timestamp(System.currentTimeMillis()));
		fio.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		fundInvestOperateService.insert(fio);
		return ResultManager.createDataResult(fio, "添加待审记录成功！");
	}
	
	/**
	 * 获取投资操作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {		
		//获取银行理财产品投资信息
		FundInvestOperate fio = fundInvestOperateService.get(uuid);
		if (fio == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		FundInvestOperateDetailVO fidVO = new FundInvestOperateDetailVO(fio);
		
		Fund f = fundService.get(fio.getFund());
		if (f == null) {
			return ResultManager.createFailResult("数据错误！");
		}
		
		BkOperator operate = bkOperatorService.get(fio.getCreator());
		if (operate != null) {
			fidVO.setCreatorName(operate.getRealname());
		}
		
		Map<String, Object> dataMap = JSONUtils.json2map(fio.getValue());
		fidVO.setTotalAmount(BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalAmount").toString())));
		fidVO.setFundName(f.getName() + "（" + f.getScode()+ "）");
		
		List<FundInvestVO> dataList = new ArrayList<FundInvestVO>();
		List<FundInvest> fiList = JSONUtils.json2list(dataMap.get("dataList").toString(), FundInvest.class);
		for(FundInvest fi : fiList){
			FundInvestVO fivo = new FundInvestVO(fi);
			fivo.setFundName(f.getName());
			if(FundPublishUuid.CURRENT.equals(fivo.getFundPublish())){
				fivo.setFundPublishName("活期理财产品");
			}else if(PlatformAccountUuid.BALANCE.equals(fivo.getFundPublish())){
				fivo.setFundPublishName("平台可用余额");
			}
			
			CompanyAccount ca = companyAccountService.get(fi.getCompanyAccount());
			if (ca != null) {
				fivo.setCompanyAccountName(ca.getAccountName());
			}
			
			BkOperator o = bkOperatorService.get(fi.getCreator());
			if (o != null) {
				fivo.setCreatorName(o.getRealname());
			}
			dataList.add(fivo);
		}
		fidVO.setDataList(dataList);
		return ResultManager.createDataResult(fidVO);
	}
	
	/**
	 * 投资操作列表(编辑)
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
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "fund", message="投资账户uuid", type = DataType.STRING)//投资产品
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String fund, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		searchMap.put("fund", fund);
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
			
		//查询符合条件的企业账户信息的总数
		Integer totalResultCount = fundInvestOperateService.getCount(searchMap);
		//查询符合条件的企业账户信息列表
		List<Entity> list = fundInvestOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, FundInvestOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				FundInvestOperate fio = (FundInvestOperate)e;
				FundInvestOperateVO fiovo = new FundInvestOperateVO(fio);
				
				
				Fund f = fundService.get(fio.getFund());
				if (f != null){
					fiovo.setFund(f.getName() + "(" + f.getScode() + ")");
				}
				
				BkOperator creator = this.bkOperatorService.get(fiovo.getCreator());
				if(creator != null){
					fiovo.setCreatorName(creator.getRealname());
				}
				
				if(fiovo.getChecker() != null && !"".equals(fiovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(fiovo.getChecker());
					if(checker != null){
						fiovo.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(fiovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 投资操作列表(管理员)
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
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "fund", message="投资账户uuid", type = DataType.STRING)//投资产品
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String fund, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		searchMap.put("fund", fund);
		//查询符合条件的企业账户信息的总数
		Integer totalResultCount = fundInvestOperateService.getCount(searchMap);
		//查询符合条件的企业账户信息列表
		List<Entity> list = fundInvestOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, FundInvestOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				FundInvestOperate fio = (FundInvestOperate)e;
				FundInvestOperateVO fiovo = new FundInvestOperateVO(fio);
				
				Map<String, Object> dataMap = JSONUtils.json2map(fio.getValue());
				fiovo.setTotalAmount(BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalAmount").toString())));
				
				Fund f = fundService.get(fio.getFund());
				if (f != null){
					fiovo.setFund(f.getName() + "(" + f.getScode() + ")");
				}
				
				BkOperator creator = this.bkOperatorService.get(fiovo.getCreator());
				if(creator != null){
					fiovo.setCreatorName(creator.getRealname());
				}
				
				if(fiovo.getChecker() != null && !"".equals(fiovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(fiovo.getChecker());
					if(checker != null){
						fiovo.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(fiovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 删除投资操作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取企业账户交易操作信息
		FundInvestOperate fio = fundInvestOperateService.get(uuid);
		if(fio != null){
			if(!FundInvestOperateStatus.DRAFT.equals(fio.getStatus()) && !FundInvestOperateStatus.UNPASSED.equals(fio.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			fio.setStatus(FundInvestOperateStatus.DELETED);
			fundInvestOperateService.update(fio);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 投资提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//获取企业账户交易操作信息
		FundInvestOperate fio = fundInvestOperateService.get(uuid);
		if(fio != null){
			if(FundInvestOperateStatus.CHECKED.equals(fio.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			fio.setSubmittime(new Timestamp(System.currentTimeMillis()));
			fio.setStatus(FundInvestOperateStatus.UNCHECKED);
			fundInvestOperateService.update(fio);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核投资操作
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
		if(!FundInvestOperateStatus.CHECKED.equals(status) && !FundInvestOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		//获取投资信息
		FundInvestOperate fio = fundInvestOperateService.get(uuid);
		if(fio != null){
			if(!FundInvestOperateStatus.UNCHECKED.equals(fio.getStatus())){
				return ResultManager.createFailResult("该记录审核状态错误");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(fio.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("无法审核自己提交的操作记录");
			}
			
			fio.setChecker(currentOperator.getUuid());
			fio.setChecktime(new Timestamp(System.currentTimeMillis()));
			fio.setStatus(status);
			fio.setReason(reason);
			try {
				fundInvestOperateService.check(fio);
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
	 * 获取投资分状态列表(编辑)
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//获取投资信息
		List<Entity> list = fundInvestOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取投资分状态列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取投资信息
		List<Entity> list = fundInvestOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取投资分类型列表(编辑)
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = fundInvestOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取投资分类型列表(管理员)
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = fundInvestOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
