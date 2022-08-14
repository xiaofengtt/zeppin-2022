	/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundService;
import cn.zeppin.product.ntb.backadmin.vo.FundDetailsVO;
import cn.zeppin.product.ntb.backadmin.vo.FundOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.FundOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.CompanyAccountOperate.CompanyAccountOperateStatus;
import cn.zeppin.product.ntb.core.entity.Fund.FundStatus;
import cn.zeppin.product.ntb.core.entity.FundOperate;
import cn.zeppin.product.ntb.core.entity.FundOperate.FundOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundOperate.FundOperateType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 后台活期理财信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/fund")
public class FundController extends BaseController {

	@Autowired
	private IFundService fundService;
	
	@Autowired
	private IFundOperateService fundOperateService;
	
	@Autowired
	private IFundPublishOperateService fundPublishOperateService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;

	@Autowired
	private IBankService bankService;
	
	/**
	 * 活期理财待审核数据数量
	 * @return
	 */
	@RequestMapping(value = "/operateTotalTypeList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateTotalTypeList() {
		List<StstusCountVO> dataList = new ArrayList<StstusCountVO>();
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		searchMap.put("status", "editor");
		
		//活期理财
		BigInteger fundNum = BigInteger.valueOf(this.fundOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("fund",fundNum));
		
		//发布的活期理财
		BigInteger fundPublishNum = BigInteger.valueOf(this.fundPublishOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("fundPublish",fundPublishNum));
		
		return ResultManager.createDataResult(dataList);
	}
	
	/**
	 * 活期理财待审核数据数量
	 * @return
	 */
	@RequestMapping(value = "/operateTotalCheckTypeList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateTotalCheckTypeList() {
		List<StstusCountVO> dataList = new ArrayList<StstusCountVO>();
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", CompanyAccountOperateStatus.UNCHECKED);
		
		//活期理财
		BigInteger fundNum = BigInteger.valueOf(this.fundOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("fund",fundNum));
		
		//发布的活期理财
		BigInteger fundPublishNum = BigInteger.valueOf(this.fundPublishOperateService.getCount(searchMap));
		dataList.add(new StstusCountVO("fundPublish",fundPublishNum));
		
		return ResultManager.createDataResult(dataList);
	}
	
	/**
	 * 根据条件查询活期理财信息 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "gp", message="管理银行", type = DataType.STRING)//管理银行
	@ActionParam(key = "invested", message="投资状态", type = DataType.STRING)//已投资
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String gp, String invested, String status, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("gp", gp);
		searchMap.put("invested", invested);
		searchMap.put("status", status);
		
		//查询符合条件的活期理财信息的总数
		Integer totalResultCount = fundService.getCount(searchMap);
		//查询符合条件的活期理财信息列表
		List<Entity> list = fundService.getListForPage(searchMap, pageNum, pageSize, sorts, Fund.class);
		List<FundDetailsVO> dataList = new ArrayList<FundDetailsVO>();
		for(Entity e : list){
			Fund fund = (Fund) e;
			FundDetailsVO fdVO = new FundDetailsVO(fund);
			
			BkOperator creator = this.bkOperatorService.get(fund.getCreator());
			if(creator != null){
				fdVO.setCreatorName(creator.getName());
			}
			if(fund.getGp()!=null && !"".equals(fund.getGp())){
				Bank bank = this.bankService.get(fund.getGp());
				if(bank != null){
					fdVO.setGpName(bank.getName());
				}else{
					fdVO.setGpName("未选择");
				}
			}else{
				fdVO.setGpName("未选择");
			}
			dataList.add(fdVO);
		}
		
		return ResultManager.createDataResult(dataList, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取一条活期理财信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取活期理财信息
		Fund fund = fundService.get(uuid);
		if (fund == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		FundDetailsVO fdVO = new FundDetailsVO(fund);
		
		BkOperator creator = this.bkOperatorService.get(fund.getCreator());
		if(creator != null){
			fdVO.setCreatorName(creator.getName());
		}
		
		if(fund.getGp()!=null && !"".equals(fund.getGp())){
			Bank bank = this.bankService.get(fund.getGp());
			if(bank != null){
				fdVO.setGpName(bank.getName());
			}else{
				fdVO.setGpName("未选择");
			}
		}else{
			fdVO.setGpName("未选择");
		}
		return ResultManager.createDataResult(fdVO);
	}
	
	/**
	 * 添加一条活期理财信息
	 * @param name
	 * @param scode
	 * @param shortname
	 * @param type
	 * @param gp
	 * @param flagStructured
	 * @param structuredType
	 * @param structuredRemark
	 * @param style
	 * @param riskLevel
	 * @param creditLevel
	 * @param performanceLevel
	 * @param planingScale
	 * @param actualScale
	 * @param setuptime
	 * @param collectStarttime
	 * @param collectEndtime
	 * @param purchaseStarttime
	 * @param purchaseEndtime
	 * @param goal
	 * @param investIdea
	 * @param investScope
	 * @param investStaregy
	 * @param investStandard
	 * @param revenueFeature
	 * @param riskManagement
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="活期理财全程", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", message="活期理财编号", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", message="活期理财简称", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", message="活期理财类型", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", message="管理方", type = DataType.STRING, required = true)
	@ActionParam(key = "flagStructured", message="分级状态", type = DataType.BOOLEAN)
	@ActionParam(key = "structuredType", message="分级类别", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "structuredRemark", message="分级描述", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "style", message="投资风格", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "riskLevel", message="风险等级", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "creditLevel", message="信用等级", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "performanceLevel", message="业务等级", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "planingScale", message="总募集规模", type = DataType.CURRENCY, maxLength = 20)
	@ActionParam(key = "setuptime", message="成立日期", type = DataType.DATE)
	@ActionParam(key = "collectStarttime", message="募集起始日", type = DataType.DATE)
	@ActionParam(key = "collectEndtime", message="募集截止日", type = DataType.DATE)
	@ActionParam(key = "purchaseStarttime", message="日常申购起始日", type = DataType.DATE)
	@ActionParam(key = "purchaseEndtime", message="日常申购截止日", type = DataType.DATE)
	@ActionParam(key = "goal", message="投资目标", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investIdea", message="投资理念", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investScope", message="投资范围", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStaregy", message="投资策略", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStandard", message="投资标准", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", message="风险收益特征", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "riskManagement", message="具体目标", type = DataType.STRING, maxLength = 1000)
	@ResponseBody
	public Result add(String name, String scode, String shortname, String type, String gp, String flagStructured, 
			String structuredType, String structuredRemark, String style, String riskLevel, String creditLevel, String performanceLevel,
			String planingScale, String setuptime, String collectStarttime, String collectEndtime, String purchaseStarttime, 
			String purchaseEndtime, String goal, String investIdea, String investScope, String investStaregy, String investStandard, 
			String revenueFeature, String riskManagement) {
	
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(fundService.isExistFundByName(name,null)){
			return ResultManager.createFailResult("活期理财名称已存在！");
		}
		if(fundService.isExistFundByScode(scode,null)){
			return ResultManager.createFailResult("活期理财编号已存在！");
		}
		
		Fund fund = new Fund();
		fund.setUuid(UUID.randomUUID().toString());
		fund.setName(name);
		fund.setScode(scode);
		fund.setShortname(shortname);
		fund.setType(type);
		fund.setStatus(FundStatus.CHECKED);
		fund.setAccountBalance(BigDecimal.ZERO);
		fund.setCreator(currentOperator.getUuid());
		fund.setCreatetime(new Timestamp(System.currentTimeMillis()));

		fund.setSetuptime(Timestamp.valueOf(Utlity.getFullTime(setuptime)));
		fund.setPerformanceLevel(performanceLevel);
		Bank bank = bankService.get(gp);
		if(bank != null){
			fund.setGp(gp);
		}else{
			return ResultManager.createFailResult("资金托管方选择错误！");
		}
		fund.setFlagStructured(Boolean.valueOf(flagStructured));
		fund.setStructuredType(structuredType);
		fund.setStructuredRemark(structuredRemark);
		fund.setStyle(style);
		fund.setRiskLevel(riskLevel);
		fund.setCreditLevel(creditLevel);
		fund.setPlaningScale(BigDecimal.valueOf(Double.valueOf(planingScale)));
		fund.setCollectStarttime(Timestamp.valueOf(Utlity.getFullTime(collectStarttime)));
		fund.setCollectEndtime(Timestamp.valueOf(Utlity.getFullTime(collectEndtime)));
		fund.setPurchaseStarttime(Timestamp.valueOf(Utlity.getFullTime(purchaseStarttime)));
		fund.setPurchaseEndtime(Timestamp.valueOf(Utlity.getFullTime(purchaseEndtime)));
			
		fund.setGoal(goal);
		fund.setInvestStaregy(investStaregy);
		fund.setInvestStandard(investStandard);
		fund.setInvestIdea(investIdea);
		fund.setInvestScope(investScope);
		fund.setRevenueFeature(revenueFeature);
		fund.setRiskManagement(riskManagement);
		
		//添加待审核记录
		FundOperate fo = new FundOperate();
		fo.setUuid(UUID.randomUUID().toString());
		fo.setType(FundOperateType.ADD);
		fo.setValue(JSONUtils.obj2json(fund));
		fo.setStatus(FundOperateStatus.DRAFT);
		fo.setCreator(currentOperator.getUuid());
		fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.fundOperateService.insert(fo);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条活期理财信息
	 * @param uuid
	 * @param name
	 * @param scode
	 * @param shortname
	 * @param type
	 * @param gp
	 * @param flagStructured
	 * @param structuredType
	 * @param structuredRemark
	 * @param style
	 * @param riskLevel
	 * @param creditLevel
	 * @param performanceLevel
	 * @param planingScale
	 * @param actualScale
	 * @param setuptime
	 * @param collectStarttime
	 * @param collectEndtime
	 * @param purchaseStarttime
	 * @param purchaseEndtime
	 * @param goal
	 * @param investIdea
	 * @param investScope
	 * @param investStaregy
	 * @param investStandard
	 * @param revenueFeature
	 * @param riskManagement
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="活期理财全程", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", message="活期理财编号", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", message="活期理财简称", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", message="活期理财类型", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", message="管理方", type = DataType.STRING, required = true)
	@ActionParam(key = "flagStructured", message="分级状态", type = DataType.BOOLEAN)
	@ActionParam(key = "structuredType", message="分级类型", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "structuredRemark", message="分级描述", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "style", message="投资风格", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "riskLevel", message="风险等级", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "creditLevel", message="信用等级", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "performanceLevel", message="业务等级", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "planingScale", message="总募集规模", type = DataType.CURRENCY, maxLength = 20)
	@ActionParam(key = "setuptime", message="成立日期", type = DataType.DATE)
	@ActionParam(key = "collectStarttime", message="募集起始日", type = DataType.DATE)
	@ActionParam(key = "collectEndtime", message="募集截止日", type = DataType.DATE)
	@ActionParam(key = "purchaseStarttime", message="日常申购起始日", type = DataType.DATE)
	@ActionParam(key = "purchaseEndtime", message="日常申购截止日", type = DataType.DATE)
	@ActionParam(key = "goal", message="投资目标", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investIdea", message="投资理念", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investScope", message="投资范围", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStaregy", message="投资策略", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStandard", message="投资标准", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", message="风险收益特征", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "riskManagement", message="具体目标", type = DataType.STRING, maxLength = 1000)
	@ResponseBody
	public Result edit(String uuid, String name, String series, String scode, String shortname, String type, String gp, 
			String flagStructured, String structuredType, String structuredRemark, String style, String riskLevel, String creditLevel,
			String performanceLevel, String planingScale, String setuptime, String collectStarttime, String collectEndtime, 
			String purchaseStarttime, String purchaseEndtime, String goal, String investIdea, String investScope, String investStaregy,
			String investStandard, String revenueFeature, String riskManagement) {
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		Fund fund = this.fundService.get(uuid);
		if(fund == null){
			return ResultManager.createFailResult("活期理财信息不存在！");
		}
		
		if(fundService.isExistFundByName(name,fund.getUuid())){
			return ResultManager.createFailResult("活期理财名称已存在！");
		}
		if(fundService.isExistFundByScode(scode,fund.getUuid())){
			return ResultManager.createFailResult("活期理财编号已存在！");
		}
		
		fund.setName(name);
		fund.setScode(scode);
		fund.setShortname(shortname);
		
		fund.setType(type);
		Bank bank = bankService.get(gp);
		if(bank != null){
			fund.setGp(gp);
		}else{
			return ResultManager.createFailResult("资金托管方选择错误！");
		}
		fund.setSetuptime(Timestamp.valueOf(Utlity.getFullTime(setuptime)));
		fund.setPerformanceLevel(performanceLevel);
		fund.setFlagStructured(Boolean.valueOf(flagStructured));
		fund.setStructuredType(structuredType);
		fund.setStructuredRemark(structuredRemark);
		fund.setStyle(style);
		fund.setRiskLevel(riskLevel);
		fund.setCreditLevel(creditLevel);
		fund.setPlaningScale(BigDecimal.valueOf(Double.valueOf(planingScale)));
		fund.setCollectStarttime(Timestamp.valueOf(Utlity.getFullTime(collectStarttime)));
		fund.setCollectEndtime(Timestamp.valueOf(Utlity.getFullTime(collectEndtime)));
		fund.setPurchaseStarttime(Timestamp.valueOf(Utlity.getFullTime(purchaseStarttime)));
		fund.setPurchaseEndtime(Timestamp.valueOf(Utlity.getFullTime(purchaseEndtime)));
		
		fund.setGoal(goal);
		fund.setInvestScope(investScope);
		fund.setInvestStaregy(investStaregy);
		fund.setInvestStandard(investStandard);
		fund.setInvestIdea(investIdea);
		fund.setRevenueFeature(revenueFeature);
		fund.setRiskManagement(riskManagement);
		
		//添加待审核记录
		FundOperate fo = new FundOperate();
		fo.setUuid(UUID.randomUUID().toString());
		fo.setFund(fund.getUuid());
		fo.setType(FundOperateType.EDIT);
		fo.setValue(JSONUtils.obj2json(fund));
		fo.setStatus(FundOperateStatus.DRAFT);
		fo.setCreator(currentOperator.getUuid());
		fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.fundOperateService.insert(fo);
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 变更活期理财审核状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ResponseBody
	public Result check(String uuid, String status) {
		if(!FundStatus.CHECKED.equals(status) && !FundStatus.DELETED.equals(status)){
			return ResultManager.createFailResult("审核状态错误！");
		}
		
		//获取活期理财信息
		Fund fund = fundService.get(uuid);
		if(fund != null && uuid.equals(fund.getUuid())){
			//修改活期理财审核状态
			fund.setStatus(status);
			fund = fundService.update(fund);
			
			return ResultManager.createSuccessResult("操作成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条活期理财信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		//获取活期理财信息
		Fund fund = fundService.get(uuid);
		if(fund != null){
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//添加待审记录
			FundOperate fo = new FundOperate();
			fo.setUuid(UUID.randomUUID().toString());
			fo.setFund(fund.getUuid());
			fo.setType(FundOperateType.DELETE);
			fo.setValue("");
			fo.setStatus(FundOperateStatus.UNCHECKED);
			fo.setCreator(currentOperator.getUuid());
			fo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			this.fundOperateService.insert(fo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取一条活期理财信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {		
		//获取活期理财信息
		FundOperate fo = fundOperateService.get(uuid);
		if (fo == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		if (!FundOperateType.ADD.equals(fo.getType()) && !FundOperateType.EDIT.equals(fo.getType())
				&& !FundOperateType.DELETE.equals(fo.getType()) ){
			return ResultManager.createFailResult("操作信息类型不正确！");
		}
		FundOperateDetailVO fodVO = new FundOperateDetailVO(fo);
		BkOperator creator = this.bkOperatorService.get(fodVO.getCreator());
		if(creator != null){
			fodVO.setCreatorName(creator.getRealname());
		}
		
		if(!FundOperateType.ADD.equals(fo.getType())){
			if(FundOperateType.EDIT.equals(fo.getType()) && FundOperateStatus.CHECKED.equals(fo.getStatus()) 
					&& fo.getOld() != null && !"".equals(fo.getOld())){
				Fund fund = JSONUtils.json2obj(fo.getOld(), Fund.class);	
				FundDetailsVO fdvo = new FundDetailsVO(fund);
				
				BkOperator operator = this.bkOperatorService.get(fund.getCreator());
				if(operator != null){
					fdvo.setCreatorName(operator.getRealname());
				}
				
				if(fund.getGp()!=null && !"".equals(fund.getGp())){
					Bank bank = this.bankService.get(fund.getGp());
					if(bank != null){
						fdvo.setGpName(bank.getName());
					}else{
						fdvo.setGpName("未选择");
					}
				}else{
					fdvo.setGpName("未选择");
				}
				fodVO.setOldData(fdvo);
			}else{
				Fund fund = fundService.get(fo.getFund());
				FundDetailsVO fdvo = new FundDetailsVO(fund);
				
				BkOperator operator = this.bkOperatorService.get(fund.getCreator());
				if(operator != null){
					fdvo.setCreatorName(operator.getRealname());
				}
				
				if(fund.getGp()!=null && !"".equals(fund.getGp())){
					Bank bank = this.bankService.get(fund.getGp());
					if(bank != null){
						fdvo.setGpName(bank.getName());
					}else{
						fdvo.setGpName("未选择");
					}
				}else{
					fdvo.setGpName("未选择");
				}
				fodVO.setOldData(fdvo);
			}
		}
		if(FundOperateType.ADD.equals(fo.getType()) || FundOperateType.EDIT.equals(fo.getType())){
			Fund fund = JSONUtils.json2obj(fo.getValue(), Fund.class);	
			FundDetailsVO fdvo = new FundDetailsVO(fund);
			
			BkOperator operator = this.bkOperatorService.get(fund.getCreator());
			if(operator != null){
				fdvo.setCreatorName(operator.getRealname());
			}
			
			if(fund.getGp()!=null && !"".equals(fund.getGp())){
				Bank bank = this.bankService.get(fund.getGp());
				if(bank != null){
					fdvo.setGpName(bank.getName());
				}else{
					fdvo.setGpName("未选择");
				}
			}else{
				fdvo.setGpName("未选择");
			}
			fodVO.setNewData(fdvo);
		}
		return ResultManager.createDataResult(fodVO);
	}
	
	/**
	 * 活期理财投资信息修改操作列表
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
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
		
		//查询符合条件的活期理财信息的总数
		Integer totalResultCount = fundOperateService.getCount(searchMap);
		//查询符合条件的活期理财信息列表
		List<Entity> list = fundOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, FundOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				FundOperate fo = (FundOperate)e;
				FundOperateVO foVO = new FundOperateVO(fo);
				if(FundOperateType.ADD.equals(fo.getType())){
					Fund fund = JSONUtils.json2obj(fo.getValue(), Fund.class);
					foVO.setFundName(fund.getName());
					foVO.setScode(fund.getScode());

					if(fund.getGp()!=null && !"".equals(fund.getGp())){
						Bank bank = this.bankService.get(fund.getGp());
						if(bank != null){
							foVO.setGp(fund.getGp());
							foVO.setGpName(bank.getName());
						}else{
							foVO.setGp("");
							foVO.setGpName("未选择");
						}
					}else{
						foVO.setGp("");
						foVO.setGpName("未选择");
					}
				}
				if(foVO.getFund() != null && !"".equals(foVO.getFund())){
					Fund fund = this.fundService.get(foVO.getFund());
					if(fund != null){
						foVO.setFundName(fund.getName());
						foVO.setScode(fund.getScode());
					}

					if(fund.getGp()!=null && !"".equals(fund.getGp())){
						Bank bank = this.bankService.get(fund.getGp());
						if(bank != null){
							foVO.setGp(fund.getGp());
							foVO.setGpName(bank.getName());
						}else{
							foVO.setGp("");
							foVO.setGpName("未选择");
						}
					}else{
						foVO.setGp("");
						foVO.setGpName("未选择");
					}
				}
				BkOperator creator = this.bkOperatorService.get(foVO.getCreator());
				if(creator != null){
					foVO.setCreatorName(creator.getRealname());
				}
				if(foVO.getChecker() != null && !"".equals(foVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(foVO.getChecker());
					if(checker != null){
						foVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(foVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 活期理财投资信息修改操作列表(管理员)
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
//		if(!"all".equals(status)){
//			searchMap.put("status", status);
//		}
		searchMap.put("status", status);
		
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		
		//查询符合条件的活期理财信息的总数
		Integer totalResultCount = fundOperateService.getCount(searchMap);
		//查询符合条件的活期理财信息列表
		List<Entity> list = fundOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, FundOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				FundOperate fo = (FundOperate)e;
				FundOperateVO foVO = new FundOperateVO(fo);
				if(FundOperateType.ADD.equals(fo.getType())){
					Fund fund = JSONUtils.json2obj(fo.getValue(), Fund.class);
					foVO.setFundName(fund.getName());
					foVO.setScode(fund.getScode());
					foVO.setCustodian(fund.getCustodian());
				}
				if(foVO.getFund() != null && !"".equals(foVO.getFund())){
					Fund fund = this.fundService.get(foVO.getFund());
					if(fund != null){
						foVO.setFundName(fund.getName());
						foVO.setScode(fund.getScode());
						foVO.setCustodian(fund.getCustodian());
					}
				}
				BkOperator creator = this.bkOperatorService.get(foVO.getCreator());
				if(creator != null){
					foVO.setCreatorName(creator.getRealname());
				}
				if(foVO.getChecker() != null && !"".equals(foVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(foVO.getChecker());
					if(checker != null){
						foVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(foVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 编辑一条活期理财审核信息
	 * @param uuid
	 * @param name
	 * @param scode
	 * @param shortname
	 * @param type
	 * @param gp
	 * @param flagStructured
	 * @param structuredType
	 * @param structuredRemark
	 * @param style
	 * @param riskLevel
	 * @param creditLevel
	 * @param performanceLevel
	 * @param planingScale
	 * @param actualScale
	 * @param setuptime
	 * @param collectStarttime
	 * @param collectEndtime
	 * @param purchaseStarttime
	 * @param purchaseEndtime
	 * @param goal
	 * @param investIdea
	 * @param investScope
	 * @param investStaregy
	 * @param investStandard
	 * @param revenueFeature
	 * @param riskManagement
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "name", message="活期理财全程", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", message="活期理财编号", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", message="活期理财简称", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", message="活期理财类型", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", message="管理方", type = DataType.STRING, required = true)
	@ActionParam(key = "flagStructured", message="分级状态", type = DataType.BOOLEAN)
	@ActionParam(key = "structuredType", message="分级类型", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "structuredRemark", message="分级描述", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "style", message="投资风格", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "riskLevel", message="风险等级", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "creditLevel", message="信用等级", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "performanceLevel", message="业务等级", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "planingScale", message="总募集规模", type = DataType.CURRENCY, maxLength = 20)
	@ActionParam(key = "setuptime", message="成立日期", type = DataType.DATE)
	@ActionParam(key = "collectStarttime", message="募集起始日", type = DataType.DATE)
	@ActionParam(key = "collectEndtime", message="募集截止日", type = DataType.DATE)
	@ActionParam(key = "purchaseStarttime", message="日常申购起始日", type = DataType.DATE)
	@ActionParam(key = "purchaseEndtime", message="日常申购截止日", type = DataType.DATE)
	@ActionParam(key = "goal", message="投资目标", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investIdea", message="投资理念", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investScope", message="投资范围", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStaregy", message="投资策略", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStandard", message="投资标准", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", message="风险收益特征", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "riskManagement", message="具体目标", type = DataType.STRING, maxLength = 1000)
	@ResponseBody
	public Result operateEdit(String uuid, String name, String series, String scode, String shortname, String type, String gp, 
			String flagStructured, String structuredType, String structuredRemark, String style, String riskLevel, String creditLevel,
			String performanceLevel, String planingScale, String setuptime, String collectStarttime, String collectEndtime, 
			String purchaseStarttime, String purchaseEndtime, String goal, String investIdea, String investScope, String investStaregy,
			String investStandard, String revenueFeature, String riskManagement){
		
		FundOperate fo = fundOperateService.get(uuid);
		if (fo != null) {
			Fund fund = JSONUtils.json2obj(fo.getValue(), Fund.class);
			
			if(fundService.isExistFundByName(name,fo.getFund())){
				return ResultManager.createFailResult("活期理财名称已存在！");
			}
			if(fundService.isExistFundByScode(scode,fo.getFund())){
				return ResultManager.createFailResult("活期理财编号已存在！");
			}
			
			fund.setName(name);
			fund.setScode(scode);
			fund.setShortname(shortname);
			
			fund.setType(type);
			Bank bank = bankService.get(gp);
			if(bank != null){
				fund.setGp(gp);
			}else{
				return ResultManager.createFailResult("资金托管方选择错误！");
			}
			fund.setSetuptime(Timestamp.valueOf(Utlity.getFullTime(setuptime)));
			fund.setPerformanceLevel(performanceLevel);
			fund.setFlagStructured(Boolean.valueOf(flagStructured));
			fund.setStructuredType(structuredType);
			fund.setStructuredRemark(structuredRemark);
			fund.setStyle(style);
			fund.setRiskLevel(riskLevel);
			fund.setCreditLevel(creditLevel);
			fund.setPlaningScale(BigDecimal.valueOf(Double.valueOf(planingScale)));
			fund.setCollectStarttime(Timestamp.valueOf(Utlity.getFullTime(collectStarttime)));
			fund.setCollectEndtime(Timestamp.valueOf(Utlity.getFullTime(collectEndtime)));
			fund.setPurchaseStarttime(Timestamp.valueOf(Utlity.getFullTime(purchaseStarttime)));
			fund.setPurchaseEndtime(Timestamp.valueOf(Utlity.getFullTime(purchaseEndtime)));
			
			fund.setGoal(goal);
			fund.setInvestScope(investScope);
			fund.setInvestStaregy(investStaregy);
			fund.setInvestStandard(investStandard);
			fund.setInvestIdea(investIdea);
			fund.setRevenueFeature(revenueFeature);
			fund.setRiskManagement(riskManagement);
			
			//修改待审核记录
			fo.setValue(JSONUtils.obj2json(fund));
			fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fundOperateService.update(fo);
			return ResultManager.createSuccessResult("修改待审核记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条活期理财操作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取活期理财操作信息
		FundOperate fo = fundOperateService.get(uuid);
		if(fo != null){
			if(!FundOperateStatus.DRAFT.equals(fo.getStatus()) && !FundOperateStatus.UNPASSED.equals(fo.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			fo.setStatus(FundOperateStatus.DELETED);
			fundOperateService.update(fo);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 *待审核草稿-提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//获取活期理财操作信息
		FundOperate fo = fundOperateService.get(uuid);
		if(fo != null){
			if(FundOperateStatus.CHECKED.equals(fo.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			
			if(FundOperateType.EDIT.equals(fo.getType()) || FundOperateType.NETVALUE.equals(fo.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("fund", fo.getFund());
				searchMap.put("type", fo.getType());
				searchMap.put("status", FundOperateStatus.UNCHECKED);
				
				Integer count = this.fundOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
				}
			}
			
			fo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			fo.setStatus(FundOperateStatus.UNCHECKED);
			fundOperateService.update(fo);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核活期理财修改操作
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
		if(!FundOperateStatus.CHECKED.equals(status) && !FundOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		//获取活期理财投资信息
		FundOperate fo = fundOperateService.get(uuid);
		if(fo != null){
			if(!FundOperateStatus.UNCHECKED.equals(fo.getStatus())){
				return ResultManager.createFailResult("该记录审核状态错误");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(fo.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("无法审核自己提交的操作记录");
			}
			
			fo.setChecker(currentOperator.getUuid());
			fo.setChecktime(new Timestamp(System.currentTimeMillis()));
			fo.setStatus(status);
			fo.setReason(reason);
			try {
				fundOperateService.check(fo);
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
	 * 获取活期理财操作分状态列表
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
		//获取活期理财投资信息
		List<Entity> list = fundOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取活期理财操作分状态列表
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取活期理财投资信息
		List<Entity> list = fundOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取活期理财投资操作分类型列表
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
		
		List<Entity> list = fundOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取活期理财操作分类型列表（管理员）
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		
		searchMap.put("status", status);
		
		List<Entity> list = fundOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
