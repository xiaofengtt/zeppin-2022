/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

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
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishDailyService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.FundPublishDailyVO;
import cn.zeppin.product.ntb.backadmin.vo.FundPublishDetailsVO;
import cn.zeppin.product.ntb.backadmin.vo.FundPublishOperateDailyVO;
import cn.zeppin.product.ntb.backadmin.vo.FundPublishOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.FundPublishOperateNetvalueVO;
import cn.zeppin.product.ntb.backadmin.vo.FundPublishOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.Fund.FundStatus;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.FundPublishOperate.FundPublishOperateType;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 后台活期理财管理
 */

@Controller
@RequestMapping(value = "/backadmin/fundPublish")
public class FundPublishController extends BaseController {

	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IFundPublishOperateService fundPublishOperateService;
	
	@Autowired
	private IFundPublishDailyService fundPublishDailyService;
	
	@Autowired
	private IFundService fundService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkAreaService areaService;
	
	@Autowired
	private IBankService bankService;
	
	/**
	 * 获取活期理财持有记录
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/investList", method = RequestMethod.GET)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result investList(String name, Integer pageNum, Integer pageSize) {
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("realname", name);
		inputParams.put("invested", "1");
		
		Integer count = this.qcbEmployeeService.getCount(inputParams);
		List<Entity> list = this.qcbEmployeeService.getListForPage(inputParams, pageNum, pageSize, null, QcbEmployee.class);
		
		FundPublish fp = this.fundPublishService.get(FundPublishUuid.CURRENT);
		BigDecimal netValue = BigDecimal.ONE;
		if(fp != null && fp.getNetWorth() != null){
			netValue = fp.getNetWorth();
		}
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(Entity e : list){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			QcbEmployee qe = (QcbEmployee) e;
			dataMap.put("realname", qe.getRealname());
			dataMap.put("mobile", qe.getMobile());
			dataMap.put("accountBalance", qe.getAccountBalance());
			BigDecimal currentBalance = qe.getCurrentAccount().multiply(netValue);
			dataMap.put("currentBalance", Utlity.numFormat4UnitDetail(currentBalance));
			dataList.add(dataMap);
		}
		
		return ResultManager.createDataResult(dataList, count);
	}
	
	/**
	 * 获取活期理财交易记录
	 * @param uuid
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/historyList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result historyList(Integer pageNum, Integer pageSize) {
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("currentType", "1");
		
		Integer count = this.qcbEmployeeHistoryService.getCount(inputParams);
		List<Entity> list = this.qcbEmployeeHistoryService.getListForPage(inputParams, pageNum, pageSize, null, QcbEmployeeHistory.class);
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(list != null && !list.isEmpty()){
			for(Entity entity : list){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				QcbEmployeeHistory qeh = (QcbEmployeeHistory)entity;
				QcbEmployee qe = this.qcbEmployeeService.get(qeh.getQcbEmployee());
				dataMap.put("realname", qe.getRealname());
				dataMap.put("mobile", qe.getMobile());
				dataMap.put("type", qeh.getType());
				dataMap.put("pay", Utlity.numFormat4UnitDetail(qeh.getPay()));
				dataMap.put("income", Utlity.numFormat4UnitDetail(qeh.getIncome()));
				dataMap.put("createtime", qeh.getCreatetime());
				dataMap.put("createtimeCN", Utlity.timeSpanToChinaString(qeh.getCreatetime()));
				dataList.add(dataMap);
			}
		}
		return ResultManager.createDataResult(dataList, count);
	}
	
	/**
	 * 获取活期理财账户信息
	 * @return
	 */
	@RequestMapping(value = "/getAccount", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result getAccount() {
		Map<String, Object> result = new HashMap<String, Object>();
		
		BigDecimal totalAmount = this.fundPublishService.getTotalAmount();
		BigDecimal accountBalance = this.fundPublishService.getAccountBalance();
		
		result.put("totalAmount", totalAmount);
		result.put("accountBalance", accountBalance);
		return ResultManager.createDataResult(result);
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
		FundPublish fp = fundPublishService.get(uuid);
		if (fp == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		FundPublishDetailsVO fpvo = new FundPublishDetailsVO(fp);
		
		
		if(fp.getCreator() != null && !"".equals(fp.getCreator())){
			BkOperator operator = this.bkOperatorService.get(fp.getCreator());
			if(operator != null){
				fpvo.setCreatorName(operator.getRealname());
			} else {
				fpvo.setCreatorName("未选择");
			}
			
		}else{
			fpvo.setCreatorName("未选择");
		}
		return ResultManager.createDataResult(fpvo);
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
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING)
	@ActionParam(key = "name", message="活期理财全程", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", message="活期理财编号", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", message="活期理财简称", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", message="活期理财类型", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", message="管理方", type = DataType.STRING, maxLength = 50)
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
	public Result add(String uuid, String name, String scode, String shortname, String type, String gp, String flagStructured, 
			String structuredType, String structuredRemark, String style, String riskLevel, String creditLevel, String performanceLevel,
			String planingScale, String setuptime, String collectStarttime, String collectEndtime, String purchaseStarttime, 
			String purchaseEndtime, String goal, String investIdea, String investScope, String investStaregy, String investStandard, 
			String revenueFeature, String riskManagement) {
	
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		FundPublish fund = new FundPublish();
		fund.setUuid(UUID.randomUUID().toString());
		fund.setName(name);
		fund.setScode(scode);
		fund.setShortname(shortname);
		fund.setType(type);
		fund.setStatus(FundStatus.CHECKED);
		fund.setCreator(currentOperator.getUuid());
		fund.setCreatetime(new Timestamp(System.currentTimeMillis()));

		fund.setSetuptime(Timestamp.valueOf(Utlity.getFullTime(setuptime)));
		fund.setPerformanceLevel(performanceLevel);
		fund.setGp(gp);
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
		FundPublishOperate fo = new FundPublishOperate();
		fo.setUuid(UUID.randomUUID().toString());
		fo.setType(FundPublishOperateType.ADD);
		fo.setValue(JSONUtils.obj2json(fund));
		fo.setStatus(FundPublishOperateStatus.DRAFT);
		fo.setCreator(currentOperator.getUuid());
		fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.fundPublishOperateService.insert(fo);
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
	@ActionParam(key = "gp", message="管理方", type = DataType.STRING, maxLength = 50)
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
		
		FundPublish fund = this.fundPublishService.get(uuid);
		if(fund == null){
			return ResultManager.createFailResult("活期理财信息不存在！");
		}
		
		fund.setName(name);
		fund.setScode(scode);
		fund.setShortname(shortname);
		
		fund.setType(type);
		fund.setGp(gp);
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
		FundPublishOperate fo = new FundPublishOperate();
		fo.setUuid(UUID.randomUUID().toString());
		fo.setFundPublish(fund.getUuid());
		fo.setType(FundPublishOperateType.EDIT);
		fo.setValue(JSONUtils.obj2json(fund));
		fo.setStatus(FundPublishOperateStatus.DRAFT);
		fo.setCreator(currentOperator.getUuid());
		fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.fundPublishOperateService.insert(fo);
		return ResultManager.createSuccessResult("保存成功！");
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
			FundPublishOperate fo = new FundPublishOperate();
			fo.setUuid(UUID.randomUUID().toString());
			fo.setFundPublish(fund.getUuid());
			fo.setType(FundPublishOperateType.DELETE);
			fo.setValue("");
			fo.setStatus(FundPublishOperateStatus.UNCHECKED);
			fo.setCreator(currentOperator.getUuid());
			fo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			this.fundPublishOperateService.insert(fo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 查询净值列表
	 * @param uuid
	 * @param starttime 起始查询时间
	 * @param endtime	终止查询时间
	 * @param deadline 1-前1个月 2-前3个月 3-前6个月 4-前1年
	 * @return
	 */
	@RequestMapping(value = "/netvaluelist", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.DATE)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.DATE)
	@ActionParam(key = "deadline", message="最终期限", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result netvaluelist(String uuid, String starttime, String endtime, String deadline, Integer pageNum, Integer pageSize) {
		
		//获取活期理财信息
		FundPublish fp = fundPublishService.get(uuid);
		if(fp != null && uuid.equals(fp.getUuid())){
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("fundPublish", uuid);
			Calendar c = Calendar.getInstance();
			if(!"all".equals(deadline)){
				switch (deadline) {
				case "1":
					c.setTime(new Date());
			        c.add(Calendar.MONTH, -1);
			        Date m = c.getTime();
					starttime = Utlity.timeSpanToDateString(m);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "2":
					c.setTime(new Date());
			        c.add(Calendar.MONTH, -3);
			        Date m2 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m2);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "3":
					c.setTime(new Date());
			        c.add(Calendar.MONTH, -6);
			        Date m3 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m3);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "4":
					c.setTime(new Date());
			        c.add(Calendar.YEAR, -1);
			        Date m4 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m4);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;

				default:
					break;
				}
			}
			
			if(starttime != null && !"".equals(starttime)){
				searchMap.put("starttime", Utlity.getFullTime(starttime));
			}
			
			if(endtime != null && !"".equals(endtime)){
				searchMap.put("endtime", Utlity.getFullTime(endtime));
			}
			
			Integer count = fundPublishDailyService.getCount(searchMap);
			//查询符合条件的活期理财净值信息
			List<Entity> list = fundPublishDailyService.getListForPage(searchMap, pageNum, pageSize, "statistime asc", FundPublishDaily.class);
			if(list != null){
				List<FundPublishDailyVO> listvo = new ArrayList<FundPublishDailyVO>();
				for(Entity entity : list){
					FundPublishDaily bfd = (FundPublishDaily)entity;
					FundPublishDailyVO bfdvo = new FundPublishDailyVO(bfd);
					bfdvo.setFundPublish(fp.getName());
					listvo.add(bfdvo);
				}
				return ResultManager.createDataResult(listvo,fp.getName(), pageNum, pageSize, count);
			}else{
				return ResultManager.createFailResult("查询失败！");
			}
			
		}else{
			return ResultManager.createFailResult("对应活期理财不存在！");
		}
	}
	
	/**
	 * 获取一条净值信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/netvalueGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result netvalueGet(String uuid) {
		FundPublishDaily fpd = fundPublishDailyService.get(uuid);
		if(fpd != null && uuid.equals(fpd.getUuid())){
			FundPublishDailyVO vo = new FundPublishDailyVO(fpd);
			return ResultManager.createDataResult(vo);
		} else {
			return ResultManager.createFailResult("活期理财净值信息不存在！");
		}
	}
	
	/**
	 * 编辑净值信息
	 * @param uuid
	 * @param statistime
	 * @param netValue
	 * @return
	 */
	@RequestMapping(value = "/netvalueEdit", method = RequestMethod.POST)
	@ActionParam(key = "data", message="数据", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result netvalueEdit(String[] data, String uuid) {

		FundPublish bfp = fundPublishService.get(uuid);
		if(bfp == null){
			return ResultManager.createFailResult("活期理财信息不存在！");
		}
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(data != null && data.length > 0){
//			bankFinancialProductOperateService
			List<FundPublishOperateDailyVO> list = new ArrayList<FundPublishOperateDailyVO>();
			//解析过程
			for(String netValues : data){
				String[] netValue = netValues.split("_");
				String type = netValue[0];
				String netuuid = netValue[1];
				String statistime = netValue[2];
				String netValueStr = netValue[3];
				if(FundPublishOperateType.ADD.equals(type)){//添加
					FundPublishDaily fpd = new FundPublishDaily();
					fpd.setUuid(UUID.randomUUID().toString());
					fpd.setFundPublish(uuid);
					fpd.setCreator(currentOperator.getUuid());
					fpd.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fpd.setNetValue(BigDecimal.valueOf(Double.valueOf(netValueStr)));
					fpd.setStatistime(Timestamp.valueOf(Utlity.getFullTime(statistime)));
					FundPublishOperateDailyVO bfpod = new FundPublishOperateDailyVO(fpd, FundPublishOperateType.ADD);
					list.add(bfpod);
				} else if(FundPublishOperateType.EDIT.equals(type)) {
					if(netuuid != null && !"".equals(netuuid)){
						FundPublishDaily fpd = fundPublishDailyService.get(netuuid);
						BigDecimal oldValue = fpd.getNetValue();
						fpd.setNetValue(BigDecimal.valueOf(Double.valueOf(netValueStr)));
						fpd.setStatistime(Timestamp.valueOf(Utlity.getFullTime(statistime)));
						fpd.setCreator(currentOperator.getUuid());
						FundPublishOperateDailyVO bfpod = new FundPublishOperateDailyVO(fpd, FundPublishOperateType.EDIT);
						bfpod.setOldValue(oldValue);
						list.add(bfpod);
					} else {
						return ResultManager.createFailResult("净值ID信息传入有误！");
					}
					
				} else if(FundPublishOperateType.DELETE.equals(type)) {
					if(netuuid != null && !"".equals(netuuid)){
						FundPublishDaily fpd = fundPublishDailyService.get(netuuid);
						FundPublishOperateDailyVO bfpod = new FundPublishOperateDailyVO(fpd, FundPublishOperateType.DELETE);
						list.add(bfpod);
					} else {
						return ResultManager.createFailResult("净值ID信息传入有误！");
					}
					
				} else {
					return ResultManager.createFailResult("净值操作类型有误！");
				}
				
			}
			FundPublishOperate bfpo = new FundPublishOperate();
			bfpo.setUuid(UUID.randomUUID().toString());
			bfpo.setFundPublish(bfp.getUuid());
			bfpo.setType(FundPublishOperateType.NETVALUE);
			bfpo.setValue(JSONUtils.obj2json(list));
			bfpo.setStatus(FundPublishOperateStatus.DRAFT);
			bfpo.setCreator(currentOperator.getUuid());
			bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fundPublishOperateService.insert(bfpo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		} else {
			return ResultManager.createSuccessResult("操作成功！");
		}
	}
	
	/**
	 * 获取净值信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateNetvalueGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateNetvalueGet(String uuid) {
		FundPublishOperate bfpo = fundPublishOperateService.get(uuid);
		if(bfpo != null){
			if(!FundPublishOperateType.NETVALUE.equals(bfpo.getType())){
				return ResultManager.createFailResult("操作信息类型不正确！");
			}
			FundPublishOperateNetvalueVO bfponVO = new FundPublishOperateNetvalueVO(bfpo);
			if(bfponVO.getFundPublish() != null){
				FundPublish bfp = fundPublishService.get(bfponVO.getFundPublish());
				if(bfp != null){
					bfponVO.setFundPublishName(bfp.getName());
					bfponVO.setScode(bfp.getScode());
					if(bfp.getCustodian() != null){
						Bank bank = bankService.get(bfp.getCustodian());
						if(bank != null){
							bfponVO.setCustodian(bank.getName());
						}
					}
				}
			}
			if(bfpo.getCreator() != null){
				BkOperator creator = bkOperatorService.get(bfpo.getCreator());
				if(creator != null){
					bfponVO.setCreatorName(creator.getRealname());
				}
			}
			return ResultManager.createDataResult(bfponVO);
		} else {
			return ResultManager.createFailResult("操作信息不存在！");
		}
	}
	
	/**
	 * 获取一条活期理财审核信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//获取活期理财信息
		FundPublishOperate bfppo = fundPublishOperateService.get(uuid);
		if (bfppo == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		FundPublishOperateDetailVO bfppodVO = new FundPublishOperateDetailVO(bfppo);
		BkOperator creator = this.bkOperatorService.get(bfppodVO.getCreator());
		if(creator != null){
			bfppodVO.setCreatorName(creator.getRealname());
		}
		
		if(!FundPublishOperateType.ADD.equals(bfppo.getType())){
			if(FundPublishOperateType.EDIT.equals(bfppo.getType()) && FundPublishOperateStatus.CHECKED.equals(bfppo.getStatus()) 
					&& bfppo.getOld() != null && !"".equals(bfppo.getOld())){
				FundPublish bfpp = JSONUtils.json2obj(bfppo.getOld(), FundPublish.class);	
				FundPublishDetailsVO bfppvo = new FundPublishDetailsVO(bfpp);
				
				if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
					BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
					if(operator != null){
						bfppvo.setCreatorName(operator.getRealname());
					} else {
						bfppvo.setCreatorName("无");
					}
					
				}else{
					bfppvo.setCreatorName("无");
				}
				bfppodVO.setOldData(bfppvo);
			}else{
				FundPublish bfpp = fundPublishService.get(bfppo.getFundPublish());
				FundPublishDetailsVO bfppvo = new FundPublishDetailsVO(bfpp);
				
				if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
					BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
					if(operator != null){
						bfppvo.setCreatorName(operator.getRealname());
					} else {
						bfppvo.setCreatorName("无");
					}
					
				}else{
					bfppvo.setCreatorName("无");
				}
				bfppodVO.setOldData(bfppvo);
			}
		}
		if(FundPublishOperateType.ADD.equals(bfppo.getType()) || FundPublishOperateType.EDIT.equals(bfppo.getType())){
			FundPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), FundPublish.class);	
			FundPublishDetailsVO bfppvo = new FundPublishDetailsVO(bfpp);
			if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
				BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
				if(operator != null){
					bfppvo.setCreatorName(operator.getRealname());
				} else {
					bfppvo.setCreatorName("无");
				}
				
			}else{
				bfppvo.setCreatorName("无");
			}
			bfppodVO.setNewData(bfppvo);
		}
		return ResultManager.createDataResult(bfppodVO);
	}
	
	/**
	 * 编辑一条活期理财操作信息
	 * @param uuid
	 * @param name
	 * @param series
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
	 * @throws ParserException
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="活期理财全程", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", message="活期理财编号", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", message="活期理财简称", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", message="活期理财类型", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", message="管理方", type = DataType.STRING, maxLength = 50)
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
			String investStandard, String revenueFeature, String riskManagement) throws ParserException {
		
		FundPublishOperate bfppo = fundPublishOperateService.get(uuid);
		if (bfppo != null) {
			FundPublish fund = JSONUtils.json2obj(bfppo.getValue(), FundPublish.class);
			
			fund.setName(name);
			fund.setScode(scode);
			fund.setShortname(shortname);
			
			fund.setType(type);
			fund.setGp(gp);
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
			bfppo.setValue(JSONUtils.obj2json(fund));
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fundPublishOperateService.update(bfppo);
			return ResultManager.createDataResult("修改待审记录成功！");
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
		FundPublishOperate bfppo = fundPublishOperateService.get(uuid);
		if(bfppo != null){
			if(!FundPublishOperateStatus.DRAFT.equals(bfppo.getStatus()) && !FundPublishOperateStatus.UNPASSED.equals(bfppo.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			bfppo.setStatus(FundPublishOperateStatus.DELETED);
			fundPublishOperateService.update(bfppo);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 活期理财信息修改操作列表
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
		Integer totalResultCount = fundPublishOperateService.getCount(searchMap);
		//查询符合条件的活期理财信息列表
		List<Entity> list = fundPublishOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, FundPublishOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				FundPublishOperate bfppo = (FundPublishOperate)e;
				FundPublishOperateVO bfppoVO = new FundPublishOperateVO(bfppo);
				if(FundPublishOperateType.ADD.equals(bfppo.getType())){
					FundPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), FundPublish.class);
					bfppoVO.setFundPublishName(bfpp.getName());
					bfppoVO.setScode(bfpp.getScode());
					bfppoVO.setCustodian(bfpp.getCustodian());
					if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
						Bank bank = this.bankService.get(bfpp.getCustodian());
						if(bank != null){
							bfppoVO.setCustodianName(bank.getName());
						}else{
							bfppoVO.setCustodianName("未选择");
						}
					}else{
						bfppoVO.setCustodianName("未选择");
					}
				}
				if(bfppoVO.getFundPublish() != null && !"".equals(bfppoVO.getFundPublish())){
					FundPublish bfpp = this.fundPublishService.get(bfppoVO.getFundPublish());
					if(bfpp != null){
						bfppoVO.setFundPublishName(bfpp.getName());
						bfppoVO.setScode(bfpp.getScode());
						bfppoVO.setCustodian(bfpp.getCustodian());
						if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
							Bank bank = this.bankService.get(bfpp.getCustodian());
							if(bank != null){
								bfppoVO.setCustodianName(bank.getName());
							}else{
								bfppoVO.setCustodianName("未选择");
							}
						}else{
							bfppoVO.setCustodianName("未选择");
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(bfppoVO.getCreator());
				if(creator != null){
					bfppoVO.setCreatorName(creator.getRealname());
				}
				if(bfppoVO.getChecker() != null && !"".equals(bfppoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfppoVO.getChecker());
					if(checker != null){
						bfppoVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(bfppoVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 活期理财信息修改操作列表（管理员）
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
		Integer totalResultCount = fundPublishOperateService.getCount(searchMap);
		//查询符合条件的活期理财信息列表
		List<Entity> list = fundPublishOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, FundPublishOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				FundPublishOperate bfppo = (FundPublishOperate)e;
				FundPublishOperateVO bfppoVO = new FundPublishOperateVO(bfppo);
				if(FundPublishOperateType.ADD.equals(bfppo.getType())){
					FundPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), FundPublish.class);
					bfppoVO.setFundPublishName(bfpp.getName());
					bfppoVO.setScode(bfpp.getScode());
					bfppoVO.setCustodian(bfpp.getCustodian());
					if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
						Bank bank = this.bankService.get(bfpp.getCustodian());
						if(bank != null){
							bfppoVO.setCustodianName(bank.getName());
						}else{
							bfppoVO.setCustodianName("未选择");
						}
					}else{
						bfppoVO.setCustodianName("未选择");
					}
				}
				if(bfppoVO.getFundPublish() != null && !"".equals(bfppoVO.getFundPublish())){
					FundPublish bfpp = this.fundPublishService.get(bfppoVO.getFundPublish());
					if(bfpp != null){
						bfppoVO.setFundPublishName(bfpp.getName());
						bfppoVO.setScode(bfpp.getScode());
						bfppoVO.setCustodian(bfpp.getCustodian());
						if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
							Bank bank = this.bankService.get(bfpp.getCustodian());
							if(bank != null){
								bfppoVO.setCustodianName(bank.getName());
							}else{
								bfppoVO.setCustodianName("未选择");
							}
						}else{
							bfppoVO.setCustodianName("未选择");
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(bfppoVO.getCreator());
				if(creator != null){
					bfppoVO.setCreatorName(creator.getRealname());
				}
				if(bfppoVO.getChecker() != null && !"".equals(bfppoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfppoVO.getChecker());
					if(checker != null){
						bfppoVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(bfppoVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
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
		FundPublishOperate bfpo = fundPublishOperateService.get(uuid);
		if(bfpo != null){
			if(FundPublishOperateStatus.CHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			
			if(FundPublishOperateType.EDIT.equals(bfpo.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("fundPublish", bfpo.getFundPublish());
				searchMap.put("type", bfpo.getType());
				searchMap.put("status", FundPublishOperateStatus.UNCHECKED);
				
				Integer count = this.fundPublishOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
				}
			}
			
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(FundPublishOperateStatus.UNCHECKED);
			fundPublishOperateService.update(bfpo);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核活期理财信息修改操作
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="审核类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="审核原因", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!FundPublishOperateStatus.CHECKED.equals(status) && !FundPublishOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("发布状态错误");
		}
		
		//获取活期理财信息
		FundPublishOperate bfppo = fundPublishOperateService.get(uuid);
		if(bfppo != null){
			if(!FundPublishOperateStatus.UNCHECKED.equals(bfppo.getStatus())){
				return ResultManager.createFailResult("该记录审核状态错误");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(bfppo.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("无法审核自己提交的操作记录");
			}
			
			bfppo.setChecker(currentOperator.getUuid());
			bfppo.setChecktime(new Timestamp(System.currentTimeMillis()));
			bfppo.setStatus(status);
			bfppo.setReason(reason);
			try {
				fundPublishOperateService.check(bfppo);
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
		//获取活期理财信息
		List<Entity> list = fundPublishOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取活期理财操作分状态列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取活期理财信息
		List<Entity> list = fundPublishOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取活期理财操作分类型列表
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核类型", type = DataType.STRING, required = true, maxLength = 20)
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
		
		List<Entity> list = fundPublishOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取活期理财操作分类型列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核类型", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
//		if(!"all".equals(status)){
//			searchMap.put("status", status);
//		}
		searchMap.put("status", status);
		
		List<Entity> list = fundPublishOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
