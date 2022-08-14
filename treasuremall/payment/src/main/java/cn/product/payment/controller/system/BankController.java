/**
 * 
 */
package cn.product.payment.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Bank;
import cn.product.payment.service.BankService;


/**
 * 银行查询
 */

@Controller
@RequestMapping(value = "/system/bank")
public class BankController extends BaseController {
 
	@Autowired
	private BankService bankService;
	
	/**
	 * 根据条件查询
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sort) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		
		Integer totalResultCount = bankService.getCountByParams(searchMap);
		List<Bank> list = bankService.getListByParams(searchMap);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		Bank bank = bankService.get(uuid);
		if (bank == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		return ResultManager.createDataResult(bank);
	}
}
