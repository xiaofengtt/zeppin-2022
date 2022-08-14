/**
 * 
 */
package cn.zeppin.product.score.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Bank;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.entity.Bank.BankStatus;
import cn.zeppin.product.score.service.BankService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.vo.front.BankVO;

/**
 * 前端银行列表
 */

@Controller
@RequestMapping(value = "/front/bank")
public class FrontBankController extends BaseController {
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", BankStatus.NORMAL);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		Integer totalResultCount = bankService.getCountByParams(searchMap);
		List<Bank> list = bankService.getListByParams(searchMap);
		
		List<BankVO> listvo = new ArrayList<BankVO>();
		for(Bank bank : list) {
			//界面返回封装对象
			BankVO bankVO = new BankVO(bank);
			
			//资源信息
			Resource resource = resourceService.get(bank.getLogo());
			if (resource != null) {
				bankVO.setLogoUrl(resource.getUrl());
			}
			listvo.add(bankVO);
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
}
