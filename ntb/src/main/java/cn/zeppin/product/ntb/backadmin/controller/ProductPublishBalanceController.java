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
 * ????????????????????????
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
	 * ????????????
	 * @param uuid
	 * @param realRate
	 * @return
	 */
	@RequestMapping(value = "/balance", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "realRate", type = DataType.NUMBER, required = true, maxLength = 10)
	@ResponseBody
	public Result balance(String uuid, BigDecimal realRate) {
		
		//????????????????????????
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			if(!BankFinancialProductPublishStage.BALANCE.equals(bfpp.getStage())){
				return ResultManager.createFailResult("?????????????????????????????????");
			}
			
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//??????????????????
			ProductPublishBalanceOperate ppbo = new ProductPublishBalanceOperate();
			ppbo.setUuid(UUID.randomUUID().toString());
			ppbo.setBankFinancialProductPublish(bfpp.getUuid());
			ppbo.setValue(realRate.toString());
			ppbo.setStatus(ProductPublishBalanceOperateStatus.UNCHECKED);
			ppbo.setCreator(currentOperator.getUuid());
			ppbo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			ppbo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			productPublishBalanceOperateService.insert(ppbo);
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//????????????????????????
		ProductPublishBalanceOperate ppbo = productPublishBalanceOperateService.get(uuid);
		if (ppbo == null) {
			return ResultManager.createFailResult("????????????????????????");
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
	 * ????????????????????????????????????
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
			return ResultManager.createDataResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//????????????????????????????????????
		ProductPublishBalanceOperate ppbo = productPublishBalanceOperateService.get(uuid);
		if(ppbo != null){
			if(!ProductPublishBalanceOperateStatus.DRAFT.equals(ppbo.getStatus()) && !ProductPublishBalanceOperateStatus.UNPASSED.equals(ppbo.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			ppbo.setStatus(ProductPublishBalanceOperateStatus.DELETED);
			productPublishBalanceOperateService.update(ppbo);
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
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//??????????????????????????????????????????????????????
		Integer totalResultCount = productPublishBalanceOperateService.getCount(searchMap);
		//???????????????????????????????????????????????????
		List<Entity> list = productPublishBalanceOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, ProductPublishBalanceOperate.class);
		
		//???????????????????????????List
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
	 * ?????????????????????????????????????????????
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		//??????????????????????????????????????????????????????
		Integer totalResultCount = productPublishBalanceOperateService.getCount(searchMap);
		//???????????????????????????????????????????????????
		List<Entity> list = productPublishBalanceOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, ProductPublishBalanceOperate.class);
		
		//???????????????????????????List
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
	 *???????????????-????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//????????????????????????????????????
		ProductPublishBalanceOperate bfpo = productPublishBalanceOperateService.get(uuid);
		if(bfpo != null){
			if(ProductPublishBalanceOperateStatus.CHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(ProductPublishBalanceOperateStatus.UNCHECKED);
			productPublishBalanceOperateService.update(bfpo);
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
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
			return ResultManager.createFailResult("??????????????????");
		}
		
		//????????????????????????
		ProductPublishBalanceOperate ppbo = productPublishBalanceOperateService.get(uuid);
		if(ppbo != null){
			if(!ProductPublishBalanceOperateStatus.UNCHECKED.equals(ppbo.getStatus())){
				return ResultManager.createFailResult("???????????????????????????");
			}
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(ppbo.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("???????????????????????????????????????");
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
				return ResultManager.createFailResult("?????????????????????");
			}
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????????????????????????????
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
		List<Entity> list = productPublishBalanceOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ?????????????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//????????????????????????
		List<Entity> list = productPublishBalanceOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
}
