/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.sql.Timestamp;
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
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.vo.BranchBankVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;

/**
 * @author elegantclack
 *
 * 后台支行信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/branchBank")
public class BranchBankController extends BaseController {

	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IBankService bankService;
	
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
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "bank", message="所属银行", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String bank, String name, String status, Integer pageNum, Integer pageSize, String sorts) {
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
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
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
	
	/**
	 * 添加一条支行信息
	 * @param bank
	 * @param name
	 * @param address
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bank", message="所属银行", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "address", message="地址", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ResponseBody
	public Result add(String bank, String name, String address, String status) {
		
		//验证是否有重名的情况
		if (branchBankService.isExistBranchBank(bank,name,null)) {
			return ResultManager.createFailResult("该名称的支行已存在！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建支行信息
		BranchBank branchBank = new BranchBank();
		branchBank.setUuid(UUID.randomUUID().toString());
		branchBank.setBank(bank);
		branchBank.setName(name);
		branchBank.setStatus(status);
		branchBank.setAddress(address);
		branchBank.setCreator(currentOperator.getUuid());
		branchBank.setCreatetime(new Timestamp(System.currentTimeMillis()));
		branchBank = branchBankService.insert(branchBank);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条支行信息
	 * @param uuid
	 * @param name
	 * @param address
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "address", message="地址", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ResponseBody
	public Result edit(String uuid, String name, String address, String logo, String status) {
		
		//获取支行信息
		BranchBank branchBank = branchBankService.get(uuid);
		if(branchBank != null && uuid.equals(branchBank.getUuid())){
			//验证是否有重名的情况
			if (branchBankService.isExistBranchBank(branchBank.getBank(),name,uuid)) {
				return ResultManager.createFailResult("该名称的支行已存在！");
			}
			
			//修改支行信息
			branchBank.setName(name);
			branchBank.setAddress(address);
			branchBank.setStatus(status);
			branchBank = branchBankService.update(branchBank);
			
			return ResultManager.createSuccessResult("保存成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条支行信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取支行信息
		BranchBank branchBank = branchBankService.get(uuid);
		if(branchBank != null && uuid.equals(branchBank.getUuid())){
			//删除支行信息
			branchBankService.delete(branchBank);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
}
