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

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IProductPublishBalanceOperateService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductPublishDetailsVO;
import cn.zeppin.product.ntb.backadmin.vo.ProductPublishBalanceOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.ProductPublishBalanceOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.ProductPublishBalanceOperate;
import cn.zeppin.product.ntb.core.entity.ProductPublishBalanceOperate.ProductPublishBalanceOperateStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 后台募集产品管理
 */

@Controller
@RequestMapping(value = "/backadmin/productPublishBalance")
public class ProductPublishBalanceController extends BaseController {

	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IProductPublishBalanceOperateService productPublishBalanceOperateService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IBankService bankService;
	
	/**
	 * 结算还款
	 * @param uuid
	 * @param realRate
	 * @return
	 */
	@RequestMapping(value = "/balance", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "realRate", type = DataType.NUMBER, required = true, maxLength = 10)
	@ResponseBody
	public Result balance(String uuid, BigDecimal realRate) {
		
		//获取募集产品信息
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			if(!BankFinancialProductPublishStage.BALANCE.equals(bfpp.getStage())){
				return ResultManager.createFailResult("募集产品当前无法结算！");
			}
			
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//添加待审记录
			ProductPublishBalanceOperate ppbo = new ProductPublishBalanceOperate();
			ppbo.setUuid(UUID.randomUUID().toString());
			ppbo.setBankFinancialProductPublish(bfpp.getUuid());
			ppbo.setValue(realRate.toString());
			ppbo.setStatus(ProductPublishBalanceOperateStatus.UNCHECKED);
			ppbo.setCreator(currentOperator.getUuid());
			ppbo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			ppbo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			productPublishBalanceOperateService.insert(ppbo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取募集产品结算还款审核信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//获取募集产品信息
		ProductPublishBalanceOperate ppbo = productPublishBalanceOperateService.get(uuid);
		if (ppbo == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		ProductPublishBalanceOperateDetailVO ppbodVO = new ProductPublishBalanceOperateDetailVO(ppbo);
		BkOperator creator = this.bkOperatorService.get(ppbodVO.getCreator());
		if(creator != null){
			ppbodVO.setCreatorName(creator.getRealname());
		}
		
		BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(ppbo.getBankFinancialProductPublish());
		BankFinancialProductPublishDetailsVO bfppvo = new BankFinancialProductPublishDetailsVO(bfpp);
		
		
		if(bfpp.getCustodian() != null && !"".equals(bfpp.getCustodian())){
			Bank bank = this.bankService.get(bfpp.getCustodian());
			if(bank != null){
				bfppvo.setCustodianCN(bank.getName());
			}
		}
		
		if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
			BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
			if(operator != null){
				bfppvo.setCreatorName(operator.getRealname());
			}
		}
		ppbodVO.setBankFinancialProductPublishInfo(bfppvo);
		return ResultManager.createDataResult(ppbodVO);
	}
	
	/**
	 * 编辑募集产品结算还款信息
	 * @param uuid
	 * @param realRate
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "realRate", type = DataType.NUMBER, required = true, maxLength = 10)
	@ResponseBody
	public Result operateEdit(String uuid, BigDecimal realRate) {
		
		ProductPublishBalanceOperate ppbo = productPublishBalanceOperateService.get(uuid);
		if (ppbo != null) {
			ppbo.setValue(realRate.toString());
			ppbo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			productPublishBalanceOperateService.update(ppbo);
			return ResultManager.createDataResult("修改待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除结算还款信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取银行理财产品操作信息
		ProductPublishBalanceOperate ppbo = productPublishBalanceOperateService.get(uuid);
		if(ppbo != null){
			if(!ProductPublishBalanceOperateStatus.DRAFT.equals(ppbo.getStatus()) && !ProductPublishBalanceOperateStatus.UNPASSED.equals(ppbo.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			ppbo.setStatus(ProductPublishBalanceOperateStatus.DELETED);
			productPublishBalanceOperateService.update(ppbo);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 募集产品结算还款操作列表
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//查询符合条件的银行理财产品信息的总数
		Integer totalResultCount = productPublishBalanceOperateService.getCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = productPublishBalanceOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, ProductPublishBalanceOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				ProductPublishBalanceOperate ppbo = (ProductPublishBalanceOperate)e;
				ProductPublishBalanceOperateVO ppboVO = new ProductPublishBalanceOperateVO(ppbo);
				if(ppboVO.getBankFinancialProductPublish() != null && !"".equals(ppboVO.getBankFinancialProductPublish())){
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(ppboVO.getBankFinancialProductPublish());
					if(bfpp != null){
						if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
							Bank b = this.bankService.get(bfpp.getCustodian());
							if(b != null){
								ppboVO.setBankFinancialProductPublishName(b.getName() + bfpp.getName() + "(" + bfpp.getScode() + ")");
							}
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(ppboVO.getCreator());
				if(creator != null){
					ppboVO.setCreatorName(creator.getRealname());
				}
				if(ppboVO.getChecker() != null && !"".equals(ppboVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(ppboVO.getChecker());
					if(checker != null){
						ppboVO.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(ppboVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 募集产品结算还款列表（管理员）
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		//查询符合条件的银行理财产品信息的总数
		Integer totalResultCount = productPublishBalanceOperateService.getCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = productPublishBalanceOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, ProductPublishBalanceOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				ProductPublishBalanceOperate ppbo = (ProductPublishBalanceOperate)e;
				ProductPublishBalanceOperateVO ppboVO = new ProductPublishBalanceOperateVO(ppbo);
				if(ppboVO.getBankFinancialProductPublish() != null && !"".equals(ppboVO.getBankFinancialProductPublish())){
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(ppboVO.getBankFinancialProductPublish());
					if(bfpp != null){
						if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
							Bank b = this.bankService.get(bfpp.getCustodian());
							if(b != null){
								ppboVO.setBankFinancialProductPublishName(b.getName() + bfpp.getName() + "(" + bfpp.getScode() + ")");
							}
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(ppboVO.getCreator());
				if(creator != null){
					ppboVO.setCreatorName(creator.getRealname());
				}
				if(ppboVO.getChecker() != null && !"".equals(ppboVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(ppboVO.getChecker());
					if(checker != null){
						ppboVO.setCheckerName(checker.getRealname());
					}
				}
				listVO.add(ppboVO);
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
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//获取银行理财产品操作信息
		ProductPublishBalanceOperate bfpo = productPublishBalanceOperateService.get(uuid);
		if(bfpo != null){
			if(ProductPublishBalanceOperateStatus.CHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(ProductPublishBalanceOperateStatus.UNCHECKED);
			productPublishBalanceOperateService.update(bfpo);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核结算还款
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
		if(!ProductPublishBalanceOperateStatus.CHECKED.equals(status) && !ProductPublishBalanceOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("发布状态错误");
		}
		
		//获取募集产品信息
		ProductPublishBalanceOperate ppbo = productPublishBalanceOperateService.get(uuid);
		if(ppbo != null){
			if(!ProductPublishBalanceOperateStatus.UNCHECKED.equals(ppbo.getStatus())){
				return ResultManager.createFailResult("该记录审核状态错误");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(ppbo.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("无法审核自己提交的操作记录");
			}
			
			ppbo.setChecker(currentOperator.getUuid());
			ppbo.setChecktime(new Timestamp(System.currentTimeMillis()));
			ppbo.setStatus(status);
			ppbo.setReason(reason);
			try {
				productPublishBalanceOperateService.check(ppbo);
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
	 * 获取募集产品结算还款分状态列表
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
		//获取募集产品信息
		List<Entity> list = productPublishBalanceOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取募集产品结算还款分状态列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取募集产品信息
		List<Entity> list = productPublishBalanceOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
}
