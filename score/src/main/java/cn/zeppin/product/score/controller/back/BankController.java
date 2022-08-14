/**
 * 
 */
package cn.zeppin.product.score.controller.back;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.Bank;
import cn.zeppin.product.score.entity.Bank.BankStatus;
import cn.zeppin.product.score.entity.Resource;
import cn.zeppin.product.score.service.BankService;
import cn.zeppin.product.score.service.ResourceService;
import cn.zeppin.product.score.vo.back.BankVO;


/**
 * 银行信息管理
 */

@Controller
@RequestMapping(value = "/back/bank")
public class BankController extends BaseController {
 
	@Autowired
	private BankService bankService;
	
	@Autowired
	private ResourceService resourceService;
	
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
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		
		//查询符合条件的银行信息的总数
		Integer totalResultCount = bankService.getCountByParams(searchMap);
		//查询符合条件的银行信息列表
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
	 * 添加一条银行信息
	 * @param name
	 * @param shortName
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="银行名称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "shortName", message="简称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "logo", message="图标", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "icon", message="ICON", type = DataType.STRING, minLength = 36, maxLength = 36)
	@ActionParam(key = "color", message="显示色值", type = DataType.STRING, maxLength = 10)
	@ActionParam(key = "iconColor", message="彩色ICON", type = DataType.STRING, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result add(String name, String logo, String status, String icon, String color, String shortName, String iconColor) {
		
		//验证是否有重名的情况
		if (bankService.isExistBankByName(name,null)) {
			return ResultManager.createFailResult("该名称的银行已存在！");
		}
		
		//取管理员信息
		Admin admin = this.getCurrentOperator();
		
		//创建银行信息
		Bank bank = new Bank();
		bank.setUuid(UUID.randomUUID().toString());
		bank.setName(name);
		bank.setShortName(shortName);
		bank.setStatus(status);
		bank.setLogo(logo);
		bank.setCreator(admin.getUuid());
		bank.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		bank.setColor(color);
		bank.setIcon(icon);
		bank.setIconColor(iconColor);
		
		bankService.insert(bank);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条银行信息
	 * @param uuid
	 * @param name
	 * @param shortName
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="银行名称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "shortName", message="简称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "logo", message="图标", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "icon", message="ICON", type = DataType.STRING, minLength = 36, maxLength = 36)
	@ActionParam(key = "color", message="显示色值", type = DataType.STRING, maxLength = 10)
	@ActionParam(key = "iconColor", message="彩色ICON", type = DataType.STRING, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result edit(String uuid, String name, String logo, String status,String icon, String color, String shortName, String iconColor) {
		
		//获取银行信息
		Bank bank = bankService.get(uuid);
		if(bank != null && uuid.equals(bank.getUuid())){
			//验证是否有重名的情况
			if (bankService.isExistBankByName(name,uuid)) {
				return ResultManager.createFailResult("该名称的银行已存在！");
			}
			
			//修改银行信息
			bank.setName(name);
			bank.setShortName(shortName);
			bank.setLogo(logo);
			bank.setStatus(status);
			
			bank.setColor(color);
			bank.setIcon(icon);
			bank.setIconColor(iconColor);
			
			bankService.update(bank);
			
			return ResultManager.createSuccessResult("保存成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条银行信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取银行信息
		Bank bank = bankService.get(uuid);
		if(bank != null && uuid.equals(bank.getUuid())){
			//删除银行信息
			bank.setStatus(BankStatus.DELETE);
			bank.setName(bank.getName() + "_#" + bank.getUuid());
			bankService.update(bank);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
}
