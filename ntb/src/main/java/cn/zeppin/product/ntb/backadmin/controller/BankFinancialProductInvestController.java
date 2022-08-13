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

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductInvestOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductInvestVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest.BankFinancialProductInvestStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest.BankFinancialProductInvestStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateType;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 后台银行理财产品投资管理
 */

@Controller
@RequestMapping(value = "/backadmin/bankFinancialProductInvest")
public class BankFinancialProductInvestController extends BaseController {

	@Autowired
	private IBankFinancialProductInvestService bankFinancialProductInvestService;
	
	@Autowired
	private IBankFinancialProductService bankFinancialProductService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IBankFinancialProductInvestOperateService bankFinancialProductInvestOperateService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	
	/**
	 * 根据条件查询银行理财产品投资信息 
	 * @param status
	 * @param stage
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING)//状态
	@ActionParam(key = "stage", type = DataType.STRING)//阶段
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, String stage, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		if(!"all".equals(stage)){
			searchMap.put("stage", stage);
		}
		//查询符合条件的银行理财产品投资信息的总数
		Integer totalResultCount = bankFinancialProductInvestService.getCount(searchMap);
		//查询符合条件的银行理财产品投资信息列表
		List<Entity> list = bankFinancialProductInvestService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductInvest.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductInvest bfpi = (BankFinancialProductInvest)e;
				BankFinancialProductInvestVO bfpiVO = new BankFinancialProductInvestVO(bfpi);
				if(bfpiVO.getBankFinancialProductPublish() != null && !"".equals(bfpiVO.getBankFinancialProductPublish())){
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(bfpi.getBankFinancialProductPublish());
					if(bfpp != null){
						bfpiVO.setBankFinancialProductPublishName(bfpp.getName());
						if(bfpp.getTargetAnnualizedReturnRate() != null){
							bfpiVO.setTargetAnnualizedReturnRate(bfpp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
						}else{
							bfpiVO.setTargetAnnualizedReturnRate("0.00");
						}
						if(bfpp.getValueDate() != null && !"".equals(bfpp.getValueDate())){
							bfpiVO.setValueDate(Utlity.timeSpanToDateString(bfpp.getValueDate()));
						}else{
							bfpiVO.setValueDate("");
						}
						if(bfpp.getMaturityDate() != null && !"".equals(bfpp.getMaturityDate())){
							bfpiVO.setMaturityDate(Utlity.timeSpanToDateString(bfpp.getMaturityDate()));
						}else{
							bfpiVO.setMaturityDate("");
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(bfpiVO.getCreator());
				if(creator != null){
					bfpiVO.setCreatorName(creator.getRealname());
				}
				listVO.add(bfpiVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	

	/**
	 * 获取银行理财产品投资分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = bankFinancialProductInvestService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品投资分阶段列表
	 * @return
	 */
	@RequestMapping(value = "/stageList", method = RequestMethod.GET)
	@ResponseBody
	public Result stageList(String stage) {
		List<Entity> list = bankFinancialProductInvestService.getStageList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取一条银行理财产品投资信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取银行理财产品投资信息
		BankFinancialProductInvest bfpi = bankFinancialProductInvestService.get(uuid);
		if (bfpi == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		return ResultManager.createDataResult(bfpi);
	}
	
	/**
	 * 添加一条银行理财产品投资信息
	 * @param bankFinancialProductPublish
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bankFinancialProductPublish", type = DataType.STRING)
	@ActionParam(key = "amount", type = DataType.NUMBER)
	
	@ResponseBody
	public Result add(String bankFinancialProductPublish, BigDecimal amount) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建对象
		BankFinancialProductInvest bfpi = new BankFinancialProductInvest();
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(bankFinancialProductPublish);
		if(bfpp != null){
			bfpi.setBankFinancialProductPublish(bankFinancialProductPublish);
			bfpi.setBankFinancialProduct(bfpp.getBankFinancialProduct());
		}else{
			return ResultManager.createFailResult("银行理财产品发布信息不能为空！");
		}
		bfpi.setUuid(UUID.randomUUID().toString());
		bfpi.setAmount(amount);
		bfpi.setStage(BankFinancialProductInvestStage.UNSTART);
		bfpi.setStatus(BankFinancialProductInvestStatus.UNCHECKED);
		bfpi.setCreator(currentOperator.getUuid());
		bfpi.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//添加待审核记录
		BankFinancialProductInvestOperate bfpio = new BankFinancialProductInvestOperate();
		bfpio.setUuid(UUID.randomUUID().toString());
		bfpio.setType(BankFinancialProductInvestOperateType.ADD);
		bfpio.setValue(JSONUtils.obj2json(bfpi));
		bfpio.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
		bfpio.setCreator(currentOperator.getUuid());
		bfpio.setCreatetime(new Timestamp(System.currentTimeMillis()));
		bankFinancialProductInvestOperateService.insert(bfpio);
		return ResultManager.createDataResult(bfpi, "添加待审记录成功！");
	}
	
	/**
	 * 编辑一条银行理财产品投资信息
	 * @param uuid
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", type = DataType.NUMBER)
	@ResponseBody
	public Result edit(String uuid, BigDecimal amount) {
		if(uuid == null){
			return ResultManager.createFailResult("uuid不能为空！");
		}
		
		BankFinancialProductInvest bfpi = bankFinancialProductInvestService.get(uuid);
		if (bfpi != null) {
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			bfpi.setAmount(amount);
			
			//添加待审核记录
			BankFinancialProductInvestOperate bfpio = new BankFinancialProductInvestOperate();
			bfpio.setUuid(UUID.randomUUID().toString());
			bfpio.setBankFinancialProductInvest(bfpi.getUuid());
			bfpio.setType(BankFinancialProductInvestOperateType.EDIT);
			bfpio.setValue(JSONUtils.obj2json(bfpi));
			bfpio.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
			bfpio.setCreator(currentOperator.getUuid());
			bfpio.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductInvestOperateService.insert(bfpio);
			return ResultManager.createDataResult(bfpi, "添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 赎回一条银行理财产品投资信息
	 * @param uuid
	 * @param redeemAmount
	 * @param investIncome
	 * @param returnCapital
	 * @param returnInterest
	 * @param platfomIncome
	 * @return
	 */
	@RequestMapping(value = "/redeem", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "redeemAmount", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investIncome", type = DataType.NUMBER, required = true)
	@ActionParam(key = "returnCapital", type = DataType.NUMBER, required = true)
	@ActionParam(key = "returnInterest", type = DataType.NUMBER, required = true)
	@ActionParam(key = "platfomIncome", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result redeem(String uuid, BigDecimal redeemAmount, BigDecimal investIncome, BigDecimal returnCapital,
			BigDecimal returnInterest, BigDecimal platfomIncome) {
		if(uuid == null){
			return ResultManager.createFailResult("uuid不能为空！");
		}
		
		BankFinancialProductInvest bfpi = bankFinancialProductInvestService.get(uuid);
		if (bfpi != null) {
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			bfpi.setRedeemAmount(redeemAmount);
			bfpi.setInvestIncome(investIncome);
			bfpi.setReturnCapital(returnCapital);
			bfpi.setReturnInterest(returnInterest);
			bfpi.setPlatfomIncome(platfomIncome);
			bfpi.setStage(BankFinancialProductInvestStage.FINISHED);
			
			//添加待审核记录
			BankFinancialProductInvestOperate bfpio = new BankFinancialProductInvestOperate();
			bfpio.setUuid(UUID.randomUUID().toString());
			bfpio.setBankFinancialProductInvest(bfpi.getUuid());
			bfpio.setType(BankFinancialProductInvestOperateType.REDEEM);
			bfpio.setValue(JSONUtils.obj2json(bfpi));
			bfpio.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
			bfpio.setCreator(currentOperator.getUuid());
			bfpio.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductInvestOperateService.insert(bfpio);
			return ResultManager.createDataResult(bfpi, "添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 变更银行理财产品投资信息审核状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result check(String uuid, String status) {
		if(!BankFinancialProductInvestStatus.CHECKED.equals(status) && !BankFinancialProductInvestStatus.UNCHECKED.equals(status) && !BankFinancialProductInvestStatus.DELETED.equals(status)){
			return ResultManager.createFailResult("审核状态错误！");
		}
		
		//获取银行理财产品投资信息
		BankFinancialProductInvest bfpi = bankFinancialProductInvestService.get(uuid);
		if(bfpi != null){
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//添加待审记录
			BankFinancialProductInvestOperate bfpio = new BankFinancialProductInvestOperate();
			bfpio.setUuid(UUID.randomUUID().toString());
			bfpio.setBankFinancialProductInvest(bfpi.getUuid());
			bfpio.setType(BankFinancialProductInvestOperateType.CHECK);
			bfpio.setValue(status);
			bfpio.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
			bfpio.setCreator(currentOperator.getUuid());
			bfpio.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductInvestOperateService.insert(bfpio);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	
	/**
	 * 删除一条银行理财产品投资信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取银行理财产品投资信息
		BankFinancialProductInvest bfpi = bankFinancialProductInvestService.get(uuid);
		if(bfpi != null){
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//添加待审记录
			BankFinancialProductInvestOperate bfpio = new BankFinancialProductInvestOperate();
			bfpio.setUuid(UUID.randomUUID().toString());
			bfpio.setBankFinancialProductInvest(bfpi.getUuid());
			bfpio.setType(BankFinancialProductInvestOperateType.DELETE);
			bfpio.setValue("");
			bfpio.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
			bfpio.setCreator(currentOperator.getUuid());
			bfpio.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductInvestOperateService.insert(bfpio);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取一条银行理财产品投资审核信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {		
		//获取银行理财产品投资信息
		BankFinancialProductInvestOperate bfpio = bankFinancialProductInvestOperateService.get(uuid);
		if (bfpio == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		BankFinancialProductInvest bfpi = JSONUtils.json2obj(bfpio.getValue(), BankFinancialProductInvest.class);	
		return ResultManager.createDataResult(bfpi);
	}
	
	/**
	 * 银行理财产品投资信息修改操作列表
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "type", type = DataType.STRING)
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
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
		//查询符合条件的银行理财产品信息的总数
		Integer totalResultCount = bankFinancialProductInvestOperateService.getCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = bankFinancialProductInvestOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductInvestOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductInvestOperate bfpio = (BankFinancialProductInvestOperate)e;
				BankFinancialProductInvestOperateVO bfpioVO = new BankFinancialProductInvestOperateVO(bfpio);
				if(BankFinancialProductInvestOperateType.ADD.equals(bfpio.getType()) || BankFinancialProductInvestOperateType.EDIT.equals(bfpio.getType()) 
						|| BankFinancialProductInvestOperateType.REDEEM.equals(bfpio.getType())){
					BankFinancialProductInvest bfpi = JSONUtils.json2obj(bfpio.getValue(), BankFinancialProductInvest.class);
					bfpioVO.setNewData(bfpi);
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(bfpi.getBankFinancialProductPublish());
					if(bfpp != null){
						bfpioVO.setBankFinancialProductInvestName(bfpp.getName());
					}
				}
				if(bfpioVO.getBankFinancialProductInvest() != null && !"".equals(bfpioVO.getBankFinancialProductInvest())){
					BankFinancialProductInvest bfpi = this.bankFinancialProductInvestService.get(bfpioVO.getBankFinancialProductInvest());
					bfpioVO.setOldData(bfpi);
					if(bfpi != null){
						BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(bfpi.getBankFinancialProductPublish());
						if(bfpp != null){
							bfpioVO.setBankFinancialProductInvestName(bfpp.getName());
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(bfpioVO.getCreator());
				if(creator != null){
					bfpioVO.setCreatorName(creator.getRealname());
				}
				if(bfpioVO.getChecker() != null && !"".equals(bfpioVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfpioVO.getChecker());
					if(checker != null){
						bfpioVO.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(bfpioVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 审核银行理财产品投资信息修改操作
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!BankFinancialProductInvestOperateStatus.CHECKED.equals(status) && !BankFinancialProductInvestOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		//获取银行理财产品投资信息
		BankFinancialProductInvestOperate bfpio = bankFinancialProductInvestOperateService.get(uuid);
		if(bfpio != null){
			if(!BankFinancialProductInvestOperateStatus.UNCHECKED.equals(bfpio.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			bfpio.setChecker(currentOperator.getUuid());
			bfpio.setChecktime(new Timestamp(System.currentTimeMillis()));
			bfpio.setStatus(status);
			bfpio.setReason(reason);
			HashMap<String, Object> resultMap = bankFinancialProductInvestOperateService.check(bfpio);
			if((Boolean)resultMap.get("result")){
				return ResultManager.createSuccessResult("审核记录成功！");
			}else{
				return ResultManager.createFailResult(resultMap.get("message").toString());
			}
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取银行理财产品投资操作分状态列表
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateStatusList() {		
		//获取银行理财产品投资信息
		List<Entity> list = bankFinancialProductInvestOperateService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品投资操作分类型列表
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		List<Entity> list = bankFinancialProductInvestOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
