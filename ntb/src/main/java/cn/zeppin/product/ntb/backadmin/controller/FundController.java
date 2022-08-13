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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IFundDailyService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundRateService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundService;
import cn.zeppin.product.ntb.backadmin.vo.FundDailyVO;
import cn.zeppin.product.ntb.backadmin.vo.FundDetailsVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.backadmin.vo.FundVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Fund;
import cn.zeppin.product.ntb.core.entity.FundDaily;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Fund.FundStatus;
import cn.zeppin.product.ntb.core.entity.FundRate;
import cn.zeppin.product.ntb.core.entity.FundRate.FundRateTypes;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 后台基金信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/fund")
public class FundController extends BaseController {

	@Autowired
	private IFundService fundService;
	
	@Autowired
	private IFundDailyService fundDailyService;
	
	@Autowired
	private IFundRateService fundRateService;
	
	/**
	 * 根据条件查询基金信息 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		
		//查询符合条件的基金信息的总数
		Integer totalResultCount = fundService.getCount(searchMap);
		//查询符合条件的基金信息列表
		List<Entity> list = fundService.getListForPage(searchMap, pageNum, pageSize, sorts, FundVO.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取基金分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {		
		//获取基金信息
		List<Entity> list = fundService.getStatusList(StstusCountVO.class);

		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取一条基金信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取基金信息
		Fund fund = fundService.get(uuid);
		if (fund == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		FundDetailsVO fundDetailsVo = new FundDetailsVO(fund);
		//fund
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("fund", fund.getUuid());
		List<Entity> list = this.fundRateService.getListForPage(searchMap, -1, -1, null, FundRate.class);
		List<FundRate> listFundRate = new ArrayList<FundRate>();
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				FundRate fundRate = (FundRate)entity;
				listFundRate.add(fundRate);
			}
		}
		fundDetailsVo.setFundRateList(listFundRate);
		return ResultManager.createDataResult(fundDetailsVo);
	}
	
	/**
	 * 添加一条基金信息
	 * @param name
	 * @param scode
	 * @param shortname
	 * @param type
	 * @param gp
	 * @param custodian
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
	@ActionParam(key = "step", type = DataType.STRING, required = true)
	@ActionParam(key = "uuid", type = DataType.STRING)
	@ActionParam(key = "name", type = DataType.STRING, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", type = DataType.STRING, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", type = DataType.STRING, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", type = DataType.STRING, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "custodian", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "flagStructured", type = DataType.BOOLEAN)
	@ActionParam(key = "structuredType", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "structuredRemark", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "style", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "riskLevel", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "creditLevel", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "performanceLevel", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "planingScale", type = DataType.CURRENCY, maxLength = 20)
//	@ActionParam(key = "actualScale", type = DataType.CURRENCY, maxLength = 20)
	@ActionParam(key = "setuptime", type = DataType.DATE)
	@ActionParam(key = "collectStarttime", type = DataType.DATE)
	@ActionParam(key = "collectEndtime", type = DataType.DATE)
	@ActionParam(key = "purchaseStarttime", type = DataType.DATE)
	@ActionParam(key = "purchaseEndtime", type = DataType.DATE)
	@ActionParam(key = "goal", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investIdea", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investScope", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStaregy", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStandard", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "riskManagement", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "fundRates", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result add(String step, String uuid, String name, String scode, String shortname, String type, String gp, String custodian, String flagStructured, 
			String structuredType, String structuredRemark, String style, String riskLevel, String creditLevel, String performanceLevel,
			String planingScale, String setuptime, String collectStarttime, String collectEndtime, 
			String purchaseStarttime, String purchaseEndtime, String goal, String investIdea, String investScope, String investStaregy,
			String investStandard, String revenueFeature, String riskManagement, String[] fundRates) {
		
		if(step == null){
			return ResultManager.createFailResult("步骤参数不能为空！");
		}
		
		if("1".equals(step)){
			if(name == null){
				return ResultManager.createFailResult("基金名称不能为空！");
			}
			if(scode == null){
				return ResultManager.createFailResult("基金编号不能为空！");
			}
			if(shortname == null){
				return ResultManager.createFailResult("基金简称不能为空！");
			}
			if(type == null){
				return ResultManager.createFailResult("基金类型不能为空！");
			}
			
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			Fund fund = new Fund();
			fund.setUuid(UUID.randomUUID().toString());
			fund.setName(name);
			fund.setScode(scode);
			fund.setShortname(shortname);
			fund.setType(type);
			fund.setStatus(FundStatus.UNCHECKED);
			fund.setCreator(currentOperator.getUuid());
			fund.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			if(uuid != null && !"".equals(uuid)){//编辑
				Fund funds = this.fundService.get(uuid);
				if(funds == null){
					return ResultManager.createFailResult("基金信息错误，请刷新后重新录入！");
				}
				fund.setUuid(uuid);
				fund = this.fundService.update(fund);
			}else{
				fund = this.fundService.insert(fund);
			}
			return ResultManager.createDataResult(fund, "保存成功！");
		} else {
			
			if(uuid == null || "".equals(uuid)){
				return ResultManager.createFailResult("uuid不能为空！");
			}
			
			Fund fund = this.fundService.get(uuid);
			if(fund == null){
				return ResultManager.createFailResult("该条数据不存在！");
			}
			
			if ("2".equals(step)) {
				fund.setSetuptime(Timestamp.valueOf(Utlity.getFullTime(setuptime)));
				fund.setPerformanceLevel(performanceLevel);
				fund.setGp(gp);
				fund.setCustodian(custodian);
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
				
				fund = this.fundService.update(fund);
			} else if ("3".equals(step)) {
				fund.setGoal(goal);
				fund.setInvestStaregy(investStaregy);
				fund.setInvestStandard(investStandard);
				fund.setInvestIdea(investIdea);
				fund.setInvestScope(investScope);
				fund.setRevenueFeature(revenueFeature);
				fund.setRiskManagement(riskManagement);
				
				fund = this.fundService.update(fund);
			} else if ("4".equals(step)) {
				List<FundRate> fundRateList = new ArrayList<FundRate>();
				
				if(fundRates!=null && fundRates.length > 0){
					for(String fundRate: fundRates){
						String[] params = fundRate.split("_");
						if (params != null && params.length == 5){
							if(params[0].length() < 1){
								return ResultManager.createFailResult("基金费率有误！");
							}
							if(!Utlity.isCurrency(params[1])){
								return ResultManager.createFailResult("基金费率下限资金应为货币数值！");
							}
							if(!Utlity.isCurrency(params[2])){
								return ResultManager.createFailResult("基金费率上限资金应为货币数值！");
							}
							if(Double.valueOf(params[1]) > Double.valueOf(params[2])){
								return ResultManager.createFailResult("基金费率下限不应大于基金费率上限！");
							}
							if(!Utlity.isNumeric(params[3])){
								return ResultManager.createFailResult("基金费率应为数字类型！");
							}
							if(!Utlity.isNumeric(params[4])){
								return ResultManager.createFailResult("基金优惠费率应为数字类型！");
							}
							if(Double.valueOf(params[4]) > Double.valueOf(params[3])){
								return ResultManager.createFailResult("基金优惠费率不应大于基金费率！");
							}
							FundRate fr = new FundRate();
							fr.setUuid(UUID.randomUUID().toString());
							fr.setFund(fund.getUuid());
							fr.setType(params[0]);
							fr.setLowlimit(BigDecimal.valueOf(Double.valueOf(params[1])));
							fr.setUpperlimit(BigDecimal.valueOf(Double.valueOf(params[2])));
							fr.setRate(BigDecimal.valueOf(Double.valueOf(params[3])));
							fr.setOpenrate(BigDecimal.valueOf(Double.valueOf(params[4])));
							fundRateList.add(fr);
						}else{
							return ResultManager.createFailResult("基金费率有误！");
						}
					}
					fundService.add(fund,fundRateList);
				}else{
					return ResultManager.createFailResult("请添加费率信息基金费率！");
				}
			}
			return ResultManager.createDataResult(fund, "保存成功！");
		}
	}
	
	/**
	 * 编辑一条基金信息
	 * @param uuid
	 * @param name
	 * @param scode
	 * @param shortname
	 * @param type
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "step", type = DataType.STRING, required = true)
	@ActionParam(key = "name", type = DataType.STRING, minLength = 1, maxLength = 200)
	@ActionParam(key = "scode", type = DataType.STRING, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", type = DataType.STRING, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", type = DataType.STRING, minLength = 1, maxLength = 20)
	@ActionParam(key = "gp", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "custodian", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "flagStructured", type = DataType.BOOLEAN)
	@ActionParam(key = "structuredType", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "structuredRemark", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "style", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "riskLevel", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "creditLevel", type = DataType.STRING, maxLength = 20)
	@ActionParam(key = "performanceLevel", type = DataType.STRING, maxLength = 30)
	@ActionParam(key = "planingScale", type = DataType.CURRENCY, maxLength = 20)
	@ActionParam(key = "setuptime", type = DataType.DATE)
	@ActionParam(key = "collectStarttime", type = DataType.DATE)
	@ActionParam(key = "collectEndtime", type = DataType.DATE)
	@ActionParam(key = "purchaseStarttime", type = DataType.DATE)
	@ActionParam(key = "purchaseEndtime", type = DataType.DATE)
	@ActionParam(key = "goal", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investIdea", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investScope", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStaregy", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "investStandard", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "riskManagement", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "fundRates", type = DataType.STRING_ARRAY)
	@ActionParam(key = "rateType", type = DataType.STRING)
	@ResponseBody
	public Result edit(String step, String uuid, String name, String series, String scode, String shortname, String type, String gp, String custodian, String flagStructured, 
			String structuredType, String structuredRemark, String style, String riskLevel, String creditLevel, String performanceLevel,
			String planingScale, String setuptime, String collectStarttime, String collectEndtime, 
			String purchaseStarttime, String purchaseEndtime, String goal, String investIdea, String investScope, String investStaregy,
			String investStandard, String revenueFeature, String riskManagement, String[] fundRates, String rateType) {
		
		if(step == null){
			return ResultManager.createFailResult("步骤参数不能为空！");
		}
		
		if(uuid == null){
			return ResultManager.createFailResult("uuid不能为空！");
		}
		
		Fund fund = this.fundService.get(uuid);
		if(fund == null){
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if("1".equals(step)){
			if(name == null){
				return ResultManager.createFailResult("基金名称不能为空！");
			}
			if(scode == null){
				return ResultManager.createFailResult("基金编号不能为空！");
			}
			if(shortname == null){
				return ResultManager.createFailResult("基金简称不能为空！");
			}
			
			fund.setName(name);
			fund.setScode(scode);
			fund.setShortname(shortname);
			fund = this.fundService.update(fund);
			
		} else if ("2".equals(step)) {

			if(type == null){
				return ResultManager.createFailResult("基金类型不能为空！");
			}
			fund.setType(type);
			fund.setGp(gp);
			fund.setSetuptime(Timestamp.valueOf(Utlity.getFullTime(setuptime)));
			fund.setPerformanceLevel(performanceLevel);
			fund.setCustodian(custodian);
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
			
			fund = this.fundService.update(fund);
		} else if ("3".equals(step)) {
			fund.setGoal(goal);
			fund.setInvestScope(investScope);
			fund.setInvestStaregy(investStaregy);
			fund.setInvestStandard(investStandard);
			fund.setInvestIdea(investIdea);
			fund.setRevenueFeature(revenueFeature);
			fund.setRiskManagement(riskManagement);
			
			fund = this.fundService.update(fund);
		} else if ("4".equals(step)) {
			if(!FundRateTypes.APPLY.equals(rateType)&&!FundRateTypes.BUY.equals(rateType)&&!FundRateTypes.REDEEM.equals(rateType)){
				return ResultManager.createFailResult("基金费率类型有误！");
			}
			
			List<FundRate> fundRateList = new ArrayList<FundRate>();
			
			if(fundRates!=null && fundRates.length > 0){
				for(String fundRate: fundRates){
					String[] params = fundRate.split("_");
					if (params != null && params.length == 5){
						if(params[0].length() < 1){
							return ResultManager.createFailResult("基金费率有误！");
						}
						if(!params[0].equals(rateType)){
							return ResultManager.createFailResult("基金费率类型有误！");
						}
						if(!Utlity.isCurrency(params[1])){
							return ResultManager.createFailResult("基金费率下限资金应为货币数值！");
						}
						if(!Utlity.isCurrency(params[2])){
							return ResultManager.createFailResult("基金费率上限资金应为货币数值！");
						}
						if(Double.valueOf(params[1]) > Double.valueOf(params[2])){
							return ResultManager.createFailResult("基金费率下限不应大于基金费率上限！");
						}
						if(!Utlity.isNumeric(params[3])){
							return ResultManager.createFailResult("基金费率应为数字类型！");
						}
						if(!Utlity.isNumeric(params[4])){
							return ResultManager.createFailResult("基金优惠费率应为数字类型！");
						}
						if(Double.valueOf(params[4]) > Double.valueOf(params[3])){
							return ResultManager.createFailResult("基金优惠费率不应大于基金费率！");
						}
						FundRate fr = new FundRate();
						fr.setUuid(UUID.randomUUID().toString());
						fr.setFund(fund.getUuid());
						fr.setType(params[0]);
						fr.setLowlimit(BigDecimal.valueOf(Double.valueOf(params[1])));
						fr.setUpperlimit(BigDecimal.valueOf(Double.valueOf(params[2])));
						fr.setRate(BigDecimal.valueOf(Double.valueOf(params[3])));
						fr.setOpenrate(BigDecimal.valueOf(Double.valueOf(params[4])));
						fundService.updateFundRate(fund,rateType,fundRateList);
					}else{
						return ResultManager.createFailResult("基金费率有误！");
					}
				}
			}else{
				return ResultManager.createFailResult("请添加费率信息基金费率！");
			}
		}
		return ResultManager.createDataResult(fund, "保存成功！");
	}
	
	
	/**
	 * 变更基金审核状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ResponseBody
	public Result check(String uuid, String status) {
		if(!FundStatus.CHECKED.equals(status) && !FundStatus.UNCHECKED.equals(status) && !FundStatus.UNPASSED.equals(status) && !FundStatus.DELETED.equals(status)){
			return ResultManager.createFailResult("审核状态错误！");
		}
		
		//获取基金信息
		Fund fund = fundService.get(uuid);
		if(fund != null && uuid.equals(fund.getUuid())){
			//修改基金审核状态
			fund.setStatus(status);
			fund = fundService.update(fund);
			
			return ResultManager.createSuccessResult("操作成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条基金信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取基金信息
		Fund fund = fundService.get(uuid);
		if(fund != null && uuid.equals(fund.getUuid())){
			//删除基金信息
			fundService.delete(fund);
			return ResultManager.createSuccessResult("删除成功！");
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
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "starttime", type = DataType.DATE)
	@ActionParam(key = "endtime", type = DataType.DATE)
	@ActionParam(key = "deadline", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ResponseBody
	public Result netvaluelist(String uuid, String starttime, String endtime, String deadline, Integer pageNum, Integer pageSize) {
		
		//获取银行理财产品信息
		Fund fund = fundService.get(uuid);
		if(fund != null && uuid.equals(fund.getUuid())){
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("fund", uuid);
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
			
			Integer count = fundDailyService.getCount(searchMap);
			//查询符合条件的银行理财产品净值信息
			List<Entity> list = fundDailyService.getListForPage(searchMap, pageNum, pageSize, "", FundDaily.class);
			if(list != null){
				List<FundDailyVO> listvo = new ArrayList<FundDailyVO>();
				for(Entity entity : list){
					FundDaily bfd = (FundDaily)entity;
					FundDailyVO bfdvo = new FundDailyVO(bfd);
					bfdvo.setFund(fund.getName());
					listvo.add(bfdvo);
				}
				return ResultManager.createDataResult(listvo,fund.getName(), pageNum, pageSize, count);
			}else{
				return ResultManager.createFailResult("查询失败！");
			}
			
		}else{
			return ResultManager.createFailResult("对应理财产品不存在！");
		}
	}
	
	/**
	 * 获取一条净值信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/netvalueGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result netvalueGet(String uuid) {
		FundDaily fundDaily = fundDailyService.get(uuid);
		if(fundDaily != null && uuid.equals(fundDaily.getUuid())){
			FundDailyVO vo = new FundDailyVO(fundDaily);
			return ResultManager.createDataResult(vo);
		} else {
			return ResultManager.createFailResult("基金净值信息不存在！");
		}
	}
	
	/**
	 * 录入一条净值信息
	 * 需要检查对应时间内是否已存在净值信息
	 * @param uuid
	 * @param statistime
	 * @param netValue
	 * @return
	 */
	@RequestMapping(value = "/netvalueadd", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "statistime", type = DataType.DATE, required = true)
	@ActionParam(key = "netValue", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result netvalueadd(String uuid, String statistime, BigDecimal netValue) {
		
		//获取用户登录信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//获取银行理财产品信息
		Fund fund = fundService.get(uuid);
		if(fund != null && uuid.equals(fund.getUuid())){
			
			FundDaily fundDaily = new FundDaily();
			
			/*
			 * 判断是否已经存在过相同时间内的净值信息
			 */
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("fund", fund.getUuid());
			searchMap.put("statistime", Utlity.getFullTime(statistime));
			
			List<Entity> list = this.fundDailyService.getListForPage(searchMap, 0, 1, null, FundDaily.class);
			if(list != null && list.size() > 0){
				return ResultManager.createFailResult("当前统计时间已存在净值信息，不能重复添加！");
			}
			
			/*
			 * 入库
			 */
			fundDaily.setUuid(UUID.randomUUID().toString());
			fundDaily.setFund(fund.getUuid());
			fundDaily.setCreator(currentOperator.getUuid());
			fundDaily.setCreatetime(new Timestamp(System.currentTimeMillis()));
			fundDaily.setNetValue(netValue);
			fundDaily.setStatistime(Timestamp.valueOf(Utlity.getFullTime(statistime)));
			this.fundDailyService.addDaily(fundDaily);
			return ResultManager.createSuccessResult("保存成功！");
		} else {
			return ResultManager.createFailResult("对应基金不存在！");
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
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "statistime", type = DataType.DATE, required = true)
	@ActionParam(key = "netValue", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result netvalueEdit(String uuid, String statistime, BigDecimal netValue) {
		FundDaily fundDaily = fundDailyService.get(uuid);
		if(fundDaily != null && uuid.equals(fundDaily.getUuid())){
			if(Timestamp.valueOf(Utlity.getFullTime(statistime)).equals(fundDaily.getStatistime())){
				fundDaily.setNetValue(netValue);
				this.fundDailyService.update(fundDaily);
				return ResultManager.createSuccessResult("修改成功！");
			}else{
				return ResultManager.createFailResult("净值时间错误，非法操作！");
			}
		} else {
			return ResultManager.createFailResult("基金净值信息不存在！");
		}
	}
	
	/**
	 * 删除一条净值信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/netvalueDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result netvalueDelete(String uuid) {
		FundDaily fundDaily = fundDailyService.get(uuid);
		if(fundDaily != null && uuid.equals(fundDaily.getUuid())){
			this.fundDailyService.delete(fundDaily);
			return ResultManager.createSuccessResult("删除成功！");
		} else {
			return ResultManager.createFailResult("基金净值信息不存在！");
		}
	}
}
