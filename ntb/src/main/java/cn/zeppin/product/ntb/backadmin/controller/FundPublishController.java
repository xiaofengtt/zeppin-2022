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
 * ????????????????????????
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
	 * ??????????????????????????????
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/investList", method = RequestMethod.GET)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
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
	 * ??????????????????????????????
	 * @param uuid
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/historyList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
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
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/getAccount", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
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
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//????????????????????????
		FundPublish fp = fundPublishService.get(uuid);
		if (fp == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		FundPublishDetailsVO fpvo = new FundPublishDetailsVO(fp);
		
		
		if(fp.getCreator() != null && !"".equals(fp.getCreator())){
			BkOperator operator = this.bkOperatorService.get(fp.getCreator());
			if(operator != null){
				fpvo.setCreatorName(operator.getRealname());
			} else {
				fpvo.setCreatorName("?????????");
			}
			
		}else{
			fpvo.setCreatorName("?????????");
		}
		return ResultManager.createDataResult(fpvo);
	}
	
	/**
	 * ??????????????????????????????
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
	@ActionParam(key = "name", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", message="?????????", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "flagStructured", message="????????????", type = DataType.BOOLEAN)
	@ActionParam(key = "structuredType", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "structuredRemark", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "style", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "riskLevel", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "creditLevel", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "performanceLevel", message="????????????", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "planingScale", message="???????????????", type = DataType.CURRENCY, maxLength = 20)
	@ActionParam(key = "setuptime", message="????????????", type = DataType.DATE)
	@ActionParam(key = "collectStarttime", message="???????????????", type = DataType.DATE)
	@ActionParam(key = "collectEndtime", message="???????????????", type = DataType.DATE)
	@ActionParam(key = "purchaseStarttime", message="?????????????????????", type = DataType.DATE)
	@ActionParam(key = "purchaseEndtime", message="?????????????????????", type = DataType.DATE)
	@ActionParam(key = "goal", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investIdea", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investScope", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStaregy", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStandard", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", message="??????????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "riskManagement", message="????????????", type = DataType.STRING, maxLength = 1000)
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
		
		//?????????????????????
		FundPublishOperate fo = new FundPublishOperate();
		fo.setUuid(UUID.randomUUID().toString());
		fo.setType(FundPublishOperateType.ADD);
		fo.setValue(JSONUtils.obj2json(fund));
		fo.setStatus(FundPublishOperateStatus.DRAFT);
		fo.setCreator(currentOperator.getUuid());
		fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.fundPublishOperateService.insert(fo);
		return ResultManager.createSuccessResult("???????????????");
	}
	
	/**
	 * ??????????????????????????????
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
	@ActionParam(key = "name", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", message="?????????", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "flagStructured", message="????????????", type = DataType.BOOLEAN)
	@ActionParam(key = "structuredType", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "structuredRemark", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "style", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "riskLevel", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "creditLevel", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "performanceLevel", message="????????????", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "planingScale", message="???????????????", type = DataType.CURRENCY, maxLength = 20)
	@ActionParam(key = "setuptime", message="????????????", type = DataType.DATE)
	@ActionParam(key = "collectStarttime", message="???????????????", type = DataType.DATE)
	@ActionParam(key = "collectEndtime", message="???????????????", type = DataType.DATE)
	@ActionParam(key = "purchaseStarttime", message="?????????????????????", type = DataType.DATE)
	@ActionParam(key = "purchaseEndtime", message="?????????????????????", type = DataType.DATE)
	@ActionParam(key = "goal", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investIdea", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investScope", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStaregy", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStandard", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", message="??????????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "riskManagement", message="????????????", type = DataType.STRING, maxLength = 1000)
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
			return ResultManager.createFailResult("??????????????????????????????");
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
		
		//?????????????????????
		FundPublishOperate fo = new FundPublishOperate();
		fo.setUuid(UUID.randomUUID().toString());
		fo.setFundPublish(fund.getUuid());
		fo.setType(FundPublishOperateType.EDIT);
		fo.setValue(JSONUtils.obj2json(fund));
		fo.setStatus(FundPublishOperateStatus.DRAFT);
		fo.setCreator(currentOperator.getUuid());
		fo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		this.fundPublishOperateService.insert(fo);
		return ResultManager.createSuccessResult("???????????????");
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		//????????????????????????
		Fund fund = fundService.get(uuid);
		if(fund != null){
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//??????????????????
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
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @param starttime ??????????????????
	 * @param endtime	??????????????????
	 * @param deadline 1-???1?????? 2-???3?????? 3-???6?????? 4-???1???
	 * @return
	 */
	@RequestMapping(value = "/netvaluelist", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "starttime", message="????????????", type = DataType.DATE)
	@ActionParam(key = "endtime", message="????????????", type = DataType.DATE)
	@ActionParam(key = "deadline", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result netvaluelist(String uuid, String starttime, String endtime, String deadline, Integer pageNum, Integer pageSize) {
		
		//????????????????????????
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
			//?????????????????????????????????????????????
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
				return ResultManager.createFailResult("???????????????");
			}
			
		}else{
			return ResultManager.createFailResult("??????????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
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
			return ResultManager.createFailResult("????????????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @param statistime
	 * @param netValue
	 * @return
	 */
	@RequestMapping(value = "/netvalueEdit", method = RequestMethod.POST)
	@ActionParam(key = "data", message="??????", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result netvalueEdit(String[] data, String uuid) {

		FundPublish bfp = fundPublishService.get(uuid);
		if(bfp == null){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(data != null && data.length > 0){
//			bankFinancialProductOperateService
			List<FundPublishOperateDailyVO> list = new ArrayList<FundPublishOperateDailyVO>();
			//????????????
			for(String netValues : data){
				String[] netValue = netValues.split("_");
				String type = netValue[0];
				String netuuid = netValue[1];
				String statistime = netValue[2];
				String netValueStr = netValue[3];
				if(FundPublishOperateType.ADD.equals(type)){//??????
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
						return ResultManager.createFailResult("??????ID?????????????????????");
					}
					
				} else if(FundPublishOperateType.DELETE.equals(type)) {
					if(netuuid != null && !"".equals(netuuid)){
						FundPublishDaily fpd = fundPublishDailyService.get(netuuid);
						FundPublishOperateDailyVO bfpod = new FundPublishOperateDailyVO(fpd, FundPublishOperateType.DELETE);
						list.add(bfpod);
					} else {
						return ResultManager.createFailResult("??????ID?????????????????????");
					}
					
				} else {
					return ResultManager.createFailResult("???????????????????????????");
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
			return ResultManager.createSuccessResult("???????????????????????????");
		} else {
			return ResultManager.createSuccessResult("???????????????");
		}
	}
	
	/**
	 * ??????????????????
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
				return ResultManager.createFailResult("??????????????????????????????");
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
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//????????????????????????
		FundPublishOperate bfppo = fundPublishOperateService.get(uuid);
		if (bfppo == null) {
			return ResultManager.createFailResult("????????????????????????");
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
						bfppvo.setCreatorName("???");
					}
					
				}else{
					bfppvo.setCreatorName("???");
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
						bfppvo.setCreatorName("???");
					}
					
				}else{
					bfppvo.setCreatorName("???");
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
					bfppvo.setCreatorName("???");
				}
				
			}else{
				bfppvo.setCreatorName("???");
			}
			bfppodVO.setNewData(bfppvo);
		}
		return ResultManager.createDataResult(bfppodVO);
	}
	
	/**
	 * ????????????????????????????????????
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
	@ActionParam(key = "name", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", message="??????????????????", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", message="?????????", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "flagStructured", message="????????????", type = DataType.BOOLEAN)
	@ActionParam(key = "structuredType", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "structuredRemark", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "style", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "riskLevel", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "creditLevel", message="????????????", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "performanceLevel", message="????????????", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "planingScale", message="???????????????", type = DataType.CURRENCY, maxLength = 20)
	@ActionParam(key = "setuptime", message="????????????", type = DataType.DATE)
	@ActionParam(key = "collectStarttime", message="???????????????", type = DataType.DATE)
	@ActionParam(key = "collectEndtime", message="???????????????", type = DataType.DATE)
	@ActionParam(key = "purchaseStarttime", message="?????????????????????", type = DataType.DATE)
	@ActionParam(key = "purchaseEndtime", message="?????????????????????", type = DataType.DATE)
	@ActionParam(key = "goal", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investIdea", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investScope", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStaregy", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStandard", message="????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", message="??????????????????", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "riskManagement", message="????????????", type = DataType.STRING, maxLength = 1000)
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
			
			//?????????????????????
			bfppo.setValue(JSONUtils.obj2json(fund));
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fundPublishOperateService.update(bfppo);
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
		FundPublishOperate bfppo = fundPublishOperateService.get(uuid);
		if(bfppo != null){
			if(!FundPublishOperateStatus.DRAFT.equals(bfppo.getStatus()) && !FundPublishOperateStatus.UNPASSED.equals(bfppo.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			bfppo.setStatus(FundPublishOperateStatus.DELETED);
			fundPublishOperateService.update(bfppo);
			return ResultManager.createSuccessResult("???????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param status
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
		Integer totalResultCount = fundPublishOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = fundPublishOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, FundPublishOperate.class);
		
		//???????????????????????????List
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
							bfppoVO.setCustodianName("?????????");
						}
					}else{
						bfppoVO.setCustodianName("?????????");
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
								bfppoVO.setCustodianName("?????????");
							}
						}else{
							bfppoVO.setCustodianName("?????????");
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
	 * ???????????????????????????????????????????????????
	 * @param status
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
		//????????????????????????????????????????????????
		Integer totalResultCount = fundPublishOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = fundPublishOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, FundPublishOperate.class);
		
		//???????????????????????????List
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
							bfppoVO.setCustodianName("?????????");
						}
					}else{
						bfppoVO.setCustodianName("?????????");
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
								bfppoVO.setCustodianName("?????????");
							}
						}else{
							bfppoVO.setCustodianName("?????????");
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
	 *???????????????-????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//??????????????????????????????
		FundPublishOperate bfpo = fundPublishOperateService.get(uuid);
		if(bfpo != null){
			if(FundPublishOperateStatus.CHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			if(FundPublishOperateType.EDIT.equals(bfpo.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("fundPublish", bfpo.getFundPublish());
				searchMap.put("type", bfpo.getType());
				searchMap.put("status", FundPublishOperateStatus.UNCHECKED);
				
				Integer count = this.fundPublishOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("??????????????????????????????????????????????????????");
				}
			}
			
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(FundPublishOperateStatus.UNCHECKED);
			fundPublishOperateService.update(bfpo);
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
		if(!FundPublishOperateStatus.CHECKED.equals(status) && !FundPublishOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//????????????????????????
		FundPublishOperate bfppo = fundPublishOperateService.get(uuid);
		if(bfppo != null){
			if(!FundPublishOperateStatus.UNCHECKED.equals(bfppo.getStatus())){
				return ResultManager.createFailResult("???????????????????????????");
			}
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(bfppo.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("???????????????????????????????????????");
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
		//????????????????????????
		List<Entity> list = fundPublishOperateService.getStatusList(searchMap, StstusCountVO.class);
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
		//????????????????????????
		List<Entity> list = fundPublishOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????
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
		
		List<Entity> list = fundPublishOperateService.getTypeList(searchMap,StstusCountVO.class);
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
//		if(!"all".equals(status)){
//			searchMap.put("status", status);
//		}
		searchMap.put("status", status);
		
		List<Entity> list = fundPublishOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
