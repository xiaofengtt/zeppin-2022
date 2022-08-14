/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
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
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.BankVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author elegantclack
 *
 * 后台银行信息管理
 */

@Controller
@RequestMapping(value = "/backadmin/bank")
public class BankController extends BaseController {
 
	@Autowired
	private IBankService bankService;
	
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
		
		Resource icon = resourceService.get(bank.getIcon());
		if(icon != null){
			bankVO.setIconUrl(icon.getUrl());
		}
		
		Resource iconColor = resourceService.get(bank.getIconColor());
		if(iconColor != null){
			bankVO.setIconColorUrl(iconColor.getUrl());
		}

		return ResultManager.createDataResult(bankVO);
	}
	
	/**
	 * 添加一条银行信息
	 * 20170824新增每日限额和单笔限额
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="银行名称", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "logo", message="LOGO", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "singleLimit", message="单笔限额", type = DataType.NUMBER, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "dailyLimit", message="每日限额", type = DataType.NUMBER, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "creditInquiryPhone", message="信用卡还款服务电话", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "creditInquiryCommand", message="信用卡还款服务命令", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "icon", message="ICON", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "color", message="显示色值", type = DataType.STRING, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "flagBinding", message="是否绑定", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "shortName", message="简称", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "iconColor", message="彩色ICON", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "code", message="英文编码", type = DataType.STRING, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "codeNum", message="数字编码", type = DataType.STRING, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "flagBank", message="是否为银行", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result add(String name, String logo, String status, BigDecimal singleLimit, BigDecimal dailyLimit, String creditInquiryPhone, String creditInquiryCommand, 
			String icon, String color, Boolean flagBinding, String shortName, String iconColor, String code, String codeNum, Boolean flagBank) {
		
		//验证是否有重名的情况
		if (bankService.isExistBankByName(name,null)) {
			return ResultManager.createFailResult("该名称的银行已存在！");
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建银行信息
		Bank bank = new Bank();
		bank.setUuid(UUID.randomUUID().toString());
		bank.setName(name);
		bank.setStatus(status);
		bank.setLogo(logo);
		bank.setCreator(currentOperator.getUuid());
		bank.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//20170824新增每日限额和单笔限额
		bank.setSingleLimit(singleLimit);
		bank.setDailyLimit(dailyLimit);
		bank.setCreditInquiryPhone(creditInquiryPhone);
		bank.setCreditInquiryCommand(creditInquiryCommand);
		
		//20170906增加色值和icon
		bank.setColor(color);
		bank.setIcon(icon);
		
		bank.setShortName(shortName);
		bank.setFlagBinding(flagBinding);
		
		bank.setIconColor(iconColor);
		
		bank.setCode(code);
		
		//20171219增加数字编码和是否为银行标识
		bank.setCodeNum(codeNum);
		bank.setFlagBank(flagBank);
		
		bank = bankService.insert(bank);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 编辑一条银行信息
	 * @param uuid
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "name", message="银行名称", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "logo", message="LOGO", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "singleLimit", message="单笔限额", type = DataType.NUMBER, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "dailyLimit", message="每日限额", type = DataType.NUMBER, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "creditInquiryPhone", message="信用卡还款服务电话", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "creditInquiryCommand", message="信用卡还款服务命令", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "icon", message="ICON", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "color", message="显示色值", type = DataType.STRING, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "flagBinding", message="是否绑定", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "shortName", message="简称", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "iconColor", message="彩色ICON", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "code", message="英文编码", type = DataType.STRING, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "codeNum", message="数字编码", type = DataType.STRING, required = true, minLength = 1, maxLength = 10)
	@ActionParam(key = "flagBank", message="是否为银行", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result edit(String uuid, String name, String logo, String status, BigDecimal singleLimit, BigDecimal dailyLimit, String creditInquiryPhone, String creditInquiryCommand,
			String icon, String color, Boolean flagBinding, String shortName, String iconColor, String code, String codeNum, Boolean flagBank) {
		
		//获取银行信息
		Bank bank = bankService.get(uuid);
		if(bank != null && uuid.equals(bank.getUuid())){
			//验证是否有重名的情况
			if (bankService.isExistBankByName(name,uuid)) {
				return ResultManager.createFailResult("该名称的银行已存在！");
			}
			
			//修改银行信息
			bank.setName(name);
			bank.setLogo(logo);
			bank.setStatus(status);
			
			//20170824新增每日限额和单笔限额
			bank.setSingleLimit(singleLimit);
			bank.setDailyLimit(dailyLimit);
			bank.setCreditInquiryPhone(creditInquiryPhone);
			bank.setCreditInquiryCommand(creditInquiryCommand);
			
			//20170906增加色值和icon
			bank.setColor(color);
			bank.setIcon(icon);
			
			bank.setShortName(shortName);
			bank.setFlagBinding(flagBinding);
			
			bank.setIconColor(iconColor);
			
			bank.setCode(code);
			
			//20171219增加数字编码和是否为银行标识
			bank.setCodeNum(codeNum);
			bank.setFlagBank(flagBank);
			
			bank = bankService.update(bank);
			
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
			bankService.delete(bank);
			return ResultManager.createSuccessResult("删除成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
}
