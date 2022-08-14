/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.vo.BankVO;
import cn.zeppin.product.ntb.qcb.vo.BranchBankVO;

/**
 * @author hehe
 *
 * 企财宝银行支行信息获取
 */

@Controller
@RequestMapping(value = "/qcb/bank")
public class QcbBankController extends BaseController {
 
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 根据条件查询银行信息 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("flagBank", "1");
		
		//查询符合条件的银行信息的总数
		Integer totalResultCount = bankService.getCount(searchMap);
		//查询符合条件的银行信息列表
		List<Entity> list = bankService.getListForPage(searchMap, pageNum, pageSize, sorts, BankVO.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取一条银行信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取银行信息
		Bank bank = bankService.get(uuid);
		if (bank == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//界面返回封装对象
		BankVO bankVO = new BankVO(bank);
		
		//资源信息
		Resource resource = resourceService.get(bank.getLogo());
		if (resource != null) {
			bankVO.setLogoUrl(resource.getUrl());
		}

		return ResultManager.createDataResult(bankVO);
	}
	
	/**
	 * 根据条件查询支行信息 
	 * @param bank
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/branchList", method = RequestMethod.GET)
	@ActionParam(key = "bank", message="所属银行", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result branchList(String bank, String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("bank", bank);
		searchMap.put("name", name);
		searchMap.put("status", status);
		
		//查询符合条件的支行信息的总数
		Integer totalResultCount = branchBankService.getCount(searchMap);
		//查询符合条件的支行信息列表
		List<Entity> list = branchBankService.getListForPage(searchMap, pageNum, pageSize, sorts, BranchBankVO.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取一条支行信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/branchGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result branchGet(String uuid) {		
		//获取支行信息
		BranchBank branchBank = branchBankService.get(uuid);
		if (branchBank == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//界面返回封装对象
		BranchBankVO branchBankVO = new BranchBankVO(branchBank);
		
		//支行信息
		Bank bank = bankService.get(branchBankVO.getBank());
		if (bank != null) {
			branchBankVO.setBankName(bank.getName());
		}

		return ResultManager.createDataResult(branchBankVO);
	}
}
