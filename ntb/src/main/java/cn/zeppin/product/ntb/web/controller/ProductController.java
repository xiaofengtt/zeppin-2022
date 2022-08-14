/**
 * 
 */
package cn.zeppin.product.ntb.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkWebmarketSwitchService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.service.api.IVersionService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.Version;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.web.vo.BankFinancialProductPublish4WebVO;
import cn.zeppin.product.ntb.web.vo.BankFinancialProductPublishDetailsVO;
import cn.zeppin.product.ntb.web.vo.BankFinancialProductPublishVO;
import cn.zeppin.product.ntb.web.vo.BankVO;
import cn.zeppin.product.ntb.web.vo.BkWebmarketSwitchVO;
import cn.zeppin.product.ntb.web.vo.FundPublishDetailsVO;
import cn.zeppin.product.utility.Base64Util;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.reapal.ReapalUtlity;

/**
 * @author hehe
 *
 * 银行理财产品展示
 */

@Controller
@RequestMapping(value = "/web/product")
public class ProductController extends BaseController {

	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkAreaService areaService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBkWebmarketSwitchService bkWebmarketSwitchService;
	
	@Autowired
	private IVersionService versionService;
	
	/**
	 * 根据条件查询银行信息 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/bankList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序规则", type = DataType.STRING)
	@ActionParam(key = "flagBinding", message = "是否前台显示", type = DataType.BOOLEAN)
	@ActionParam(key = "version", message = "app版本号", type = DataType.STRING)
	@ActionParam(key = "device", message = "", type = DataType.STRING)
	@ResponseBody
	public Result bankList(String name, String status, Integer pageNum, Integer pageSize, String sorts, Boolean flagBinding, 
			String device, String version) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		
		device = Base64Util.getFromBase64(device);
		Boolean flag = false;
		if(device != null && !"".equals(device)){
			
			if (Utlity.DEVICE_NUMBER_IOS.equals(device)){
				if(!Utlity.checkStringNull(version)){
					version = Base64Util.getFromBase64(version);
					Map<String, String> inputParams = new HashMap<String, String>();
//					inputParams.put("status", VersionStatus.PUBLISHED);
					inputParams.put("version", version);
					inputParams.put("device", device);
					List<Entity> listCurrent = this.versionService.getListForPage(inputParams, -1, -1, null, Version.class);
					if(listCurrent != null && !listCurrent.isEmpty()){//当前版本存在 继续
						Version ver = (Version)listCurrent.get(0);
						inputParams.clear();
						inputParams.put("webMarket", "appstore");
						inputParams.put("version", ver.getUuid());
						List<Entity> list = bkWebmarketSwitchService.getListForPage(inputParams, -1, -1, null, BkWebmarketSwitchVO.class);
						if(list != null && !list.isEmpty()){
							BkWebmarketSwitchVO vo = (BkWebmarketSwitchVO)list.get(0);
							flag = vo.getFlagSwitch();
						}
					}
				}
				
			} else {
				flag = true;
			}
		} else {
			flag = true;
		}
		if(flag){//返回正常数据
			//查询条件flagBinding
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("name", name);
			searchMap.put("status", status);
			
			if(flagBinding != null && !"".equals(flagBinding)){
				if(flagBinding){
					searchMap.put("flagBinding", "1");
				} else {
					searchMap.put("flagBinding", "0");
				}
			}
			
			
//			//查询符合条件的银行信息的总数
			Integer totalResultCount = 0;
			//查询符合条件的银行信息列表
			
			List<Entity> list = null;
			if(flagBinding != null && flagBinding){
				totalResultCount = bankService.getCount(searchMap);
				list = bankService.getListForWebPage(searchMap, pageNum, pageSize, sorts, BankVO.class);
			} else {
				searchMap.put("flagBuy", "1");
				list = bankFinancialProductPublishService.getBankList(searchMap, pageNum, pageSize, sorts, BankVO.class);
				if(list != null && list.size() > 0){
					totalResultCount = list.size();
				}
			}
			
			return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
		} else {//返回预定义的数据
			List<BankVO> list = new ArrayList<BankVO>();
			Bank bank = new Bank("1");
			BankVO vo = new BankVO(bank);
			Resource iconColor = this.resourceService.get(bank.getIconColor());
			if(iconColor != null){
				vo.setIconColorUrl(iconColor.getUrl());
			} else {
				vo.setIconColorUrl("");
			}
			list.add(vo);
			if(flagBinding != null && flagBinding){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("name", name);
				searchMap.put("status", status);
				
				if(flagBinding != null && !"".equals(flagBinding)){
					if(flagBinding){
						searchMap.put("flagBinding", "1");
					} else {
						searchMap.put("flagBinding", "0");
					}
				}
				Integer totalResultCount = bankService.getCount(searchMap);
				List<Entity> list2 = bankService.getListForWebPage(searchMap, pageNum, pageSize, sorts, BankVO.class);
				return ResultManager.createDataResult(list2, pageNum, pageSize, totalResultCount);
			}
			return ResultManager.createDataResult(list, pageNum, pageSize, list.size());
		}
		
	}
	
	/**
	 * 根据条件查询银行理财产品发布信息 
	 * @param name
	 * @param status
	 * @param stage
	 * @param term
	 * @param type
	 * @param riskLevel
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "所有参数", type = DataType.STRING)
	@ActionParam(key = "custodian", message = "管理银行", type = DataType.STRING)//管理银行
	@ActionParam(key = "currencyType", message = "币种", type = DataType.STRING)//币种
	@ActionParam(key = "guaranteeStatus", message = "保本性质", type = DataType.STRING)//保本性质
	@ActionParam(key = "term", message = "产品期限", type = DataType.STRING)//
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序规则", type = DataType.STRING)
	@ActionParam(key = "version", message = "app版本号", type = DataType.STRING)
	@ActionParam(key = "device", message = "", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String custodian, String currencyType, String guaranteeStatus, String term, Integer pageNum, 
			Integer pageSize, String sorts, String device, String version) {
		
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		
		device = Base64Util.getFromBase64(device);
		Boolean flag = false;
		if(device != null && !"".equals(device)){
			
			if (Utlity.DEVICE_NUMBER_IOS.equals(device)){
				if(!Utlity.checkStringNull(version)){
					version = Base64Util.getFromBase64(version);
					Map<String, String> inputParams = new HashMap<String, String>();
//					inputParams.put("status", VersionStatus.PUBLISHED);
					inputParams.put("version", version);
					inputParams.put("device", device);
					List<Entity> listCurrent = this.versionService.getListForPage(inputParams, -1, -1, null, Version.class);
					if(listCurrent != null && !listCurrent.isEmpty()){//当前版本存在 继续
						Version ver = (Version)listCurrent.get(0);
						inputParams.clear();
						inputParams.put("webMarket", "appstore");
						inputParams.put("version", ver.getUuid());
						List<Entity> list = bkWebmarketSwitchService.getListForPage(inputParams, -1, -1, null, BkWebmarketSwitchVO.class);
						if(list != null && !list.isEmpty()){
							BkWebmarketSwitchVO vo = (BkWebmarketSwitchVO)list.get(0);
							flag = vo.getFlagSwitch();
						}
					}
				}
			} else {
				flag = true;
			}
		} else {
			flag = true;
		}
		
		if(flag){
			//排序规则
			/*
			 * 1.默认排序 flag_buy desc,target_annualized_return_rate desc ,term desc, min_invest_amount desc
			 * 2.选中的排序 排序权重放第一位LinkedList中放first位
			 */
			LinkedList<String> sortList = new LinkedList<String>();
//			sortList.add("flag_buy-desc");
			sortList.add("target_annualized_return_rate-desc");
			sortList.add("term-asc");
			sortList.add("min_invest_amount-asc");
			if(sorts != null && !"".equals(sorts)){
				if("target_annualized_return_rate".equals(sorts.split("-")[0])){
					sortList.remove(0);
					sortList.addFirst(sorts);
				} else if ("term".equals(sorts.split("-")[0])) {
					sortList.remove(1);
					sortList.addFirst(sorts);
				} else if ("min_invest_amount".equals(sorts.split("-")[0])) {
					sortList.remove(2);
					sortList.addFirst(sorts);
				}
			}
			//查询条件
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("name", name);
			if(custodian != null && !"all".equals(custodian)){
				String[] custodians = custodian.split(",");
				String custodianStr;
				if(custodians.length > 0){
					custodianStr = "(";
					for(String str : custodians){
						custodianStr += "\"" + str + "\",";
					}
					custodianStr = custodianStr.substring(0, custodianStr.length() - 1);
					custodianStr += ")";
				}else{
					custodianStr = "()";
				}
				searchMap.put("custodian", custodianStr);
			}
			if(currencyType != null && !"all".equals(currencyType)){
				String[] currencyTypes = currencyType.split(",");
				String currencyTypeStr;
				if(currencyTypes.length > 0){
					currencyTypeStr = "(";
					for(String str : currencyTypes){
						currencyTypeStr += "\"" + str + "\",";
					}
					currencyTypeStr = currencyTypeStr.substring(0, currencyTypeStr.length() - 1);
					currencyTypeStr += ")";
				}else{
					currencyTypeStr = "()";
				}
				searchMap.put("currencyType", currencyTypeStr);
			}
			if(guaranteeStatus != null && !"all".equals(guaranteeStatus)){
				String[] guaranteeStatuss = guaranteeStatus.split(",");
				String guaranteeStatusStr;
				if(guaranteeStatuss.length > 0){
					guaranteeStatusStr = "(";
					for(String str : guaranteeStatuss){
						guaranteeStatusStr += "\"" + str + "\",";
					}
					guaranteeStatusStr = guaranteeStatusStr.substring(0, guaranteeStatusStr.length() - 1);
					guaranteeStatusStr += ")";
				}else{
					guaranteeStatusStr = "()";
				}
				searchMap.put("guaranteeStatus", guaranteeStatusStr);
			}
			
			if(term != null && !"all".equals(term)){
				searchMap.put("term", term);
			}
			
			searchMap.put("flagBuy", "1");
			//查询符合条件的银行理财产品发布信息的总数
			Integer totalResultCount = bankFinancialProductPublishService.getWebCount(searchMap);
			//查询符合条件的银行理财产品发布信息列表
			List<Entity> list = bankFinancialProductPublishService.getWebListForPage(searchMap, pageNum, pageSize, sortList, BankFinancialProductPublish.class);
			//封装可用信息到前台List
			List<Object> listVO = new ArrayList<Object>();
			if(list != null && list.size() > 0){
				for(Entity entity : list){
					BankFinancialProductPublish bfpp = (BankFinancialProductPublish) entity;
					BankFinancialProductPublishVO bfppvo = new BankFinancialProductPublishVO(bfpp);
					if(bfpp.getCustodian() != null && !"".equals(bfpp.getCustodian())){
						Bank bank = bankService.get(bfpp.getCustodian());
						if(bank != null){
							bfppvo.setCustodianName(bank.getName());
							Resource iconColor = this.resourceService.get(bank.getIconColor());
							if(iconColor != null){
								bfppvo.setIconColorUrl(iconColor.getUrl());
							} else {
								bfppvo.setIconColorUrl("");
							}
						} else {
							bfppvo.setCustodianName("未选择");
						}
					}else{
						bfppvo.setCustodianName("未选择");
					}
					if(bfpp.getArea()!=null){
						BkArea ba = areaService.get(bfpp.getArea());
						if(ba != null){
							bfppvo.setArea(ba.getName());
						}else{
							bfppvo.setArea("未选择");
						}
					}else{
						bfppvo.setArea("未选择");
					}
					listVO.add(bfppvo);
				}
			}
			
			return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
		} else {
			//排序规则
			/*
			 * 1.默认排序 flag_buy desc,target_annualized_return_rate desc ,term desc, min_invest_amount desc
			 * 2.选中的排序 排序权重放第一位LinkedList中放first位
			 */
			List<BankFinancialProductPublish> list = BankFinancialProductPublish.getInstance();
			
			LinkedList<String> sortList = new LinkedList<String>();
//			sortList.add("flag_buy-desc");
			sortList.add("target_annualized_return_rate-desc");
			sortList.add("term-asc");
			sortList.add("min_invest_amount-asc");
			if(sorts != null && !"".equals(sorts)){
				if ("term-desc".equals(sorts)){
					list.clear();
					list.add(new BankFinancialProductPublish("2"));
					list.add(new BankFinancialProductPublish("1"));
				}
			}
			
			if(term != null && !"all".equals(term)){
//				searchMap.put("term", term);
				list.clear();
				if(term.indexOf("1") > -1){
					list.add(new BankFinancialProductPublish("1"));
				}
				
				if (term.indexOf("3") > -1) {
					list.add(new BankFinancialProductPublish("2"));
				}
			}
			
			//封装可用信息到前台List
			List<Object> listVO = new ArrayList<Object>();
			if(list != null && list.size() > 0){
				for(BankFinancialProductPublish bfpp : list){
					BankFinancialProductPublishVO bfppvo = new BankFinancialProductPublishVO(bfpp);
					if(bfpp.getCustodian() != null && !"".equals(bfpp.getCustodian())){
						Bank bank = new Bank("1");
						bfppvo.setCustodianName(bank.getName());
						Resource iconColor = this.resourceService.get(bank.getIconColor());
						if(iconColor != null){
							bfppvo.setIconColorUrl(iconColor.getUrl());
						} else {
							bfppvo.setIconColorUrl("");
						}
					}else{
						bfppvo.setCustodianName("未选择");
					}
					if(bfpp.getArea()!=null){
						BkArea ba = areaService.get(bfpp.getArea());
						if(ba != null){
							bfppvo.setArea(ba.getName());
						}else{
							bfppvo.setArea("未选择");
						}
					}else{
						bfppvo.setArea("未选择");
					}
					listVO.add(bfppvo);
				}
			}
			return ResultManager.createDataResult(listVO, pageNum, pageSize, listVO.size());
		}
		
	}
	
	/**
	 * 根据条件查询银行理财产品发布信息 
	 * @param name
	 * @param status
	 * @param stage
	 * @param term
	 * @param type
	 * @param riskLevel
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/webList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "所有参数", type = DataType.STRING)
	@ActionParam(key = "custodian", message = "管理银行", type = DataType.STRING)//管理银行
	@ActionParam(key = "currencyType", message = "币种", type = DataType.STRING)//币种
	@ActionParam(key = "guaranteeStatus", message = "保本性质", type = DataType.STRING)//保本性质
	@ActionParam(key = "term", message = "产品期限", type = DataType.STRING)//
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result webList(String name, String custodian, String currencyType, String guaranteeStatus, String term, Integer pageNum, Integer pageSize, String sorts) {
		
		//排序规则
		/*
		 * 1.默认排序 flag_buy desc,target_annualized_return_rate desc ,term desc, min_invest_amount desc
		 * 2.选中的排序 排序权重放第一位LinkedList中放first位
		 */
		LinkedList<String> sortList = new LinkedList<String>();
//		sortList.add("flag_buy-desc");
		sortList.add("target_annualized_return_rate-desc");
		sortList.add("term-asc");
		sortList.add("min_invest_amount-asc");
		if(sorts != null && !"".equals(sorts)){
			if("target_annualized_return_rate".equals(sorts.split("-")[0])){
				sortList.remove(0);
				sortList.addFirst(sorts);
			} else if ("term".equals(sorts.split("-")[0])) {
				sortList.remove(1);
				sortList.addFirst(sorts);
			} else if ("min_invest_amount".equals(sorts.split("-")[0])) {
				sortList.remove(2);
				sortList.addFirst(sorts);
			}
		}
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(custodian != null && !"all".equals(custodian)){
			String[] custodians = custodian.split(",");
			String custodianStr;
			if(custodians.length > 0){
				custodianStr = "(";
				for(String str : custodians){
					custodianStr += "\"" + str + "\",";
				}
				custodianStr = custodianStr.substring(0, custodianStr.length() - 1);
				custodianStr += ")";
			}else{
				custodianStr = "()";
			}
			searchMap.put("custodian", custodianStr);
		}
		if(currencyType != null && !"all".equals(currencyType)){
			String[] currencyTypes = currencyType.split(",");
			String currencyTypeStr;
			if(currencyTypes.length > 0){
				currencyTypeStr = "(";
				for(String str : currencyTypes){
					currencyTypeStr += "\"" + str + "\",";
				}
				currencyTypeStr = currencyTypeStr.substring(0, currencyTypeStr.length() - 1);
				currencyTypeStr += ")";
			}else{
				currencyTypeStr = "()";
			}
			searchMap.put("currencyType", currencyTypeStr);
		}
		if(guaranteeStatus != null && !"all".equals(guaranteeStatus)){
			String[] guaranteeStatuss = guaranteeStatus.split(",");
			String guaranteeStatusStr;
			if(guaranteeStatuss.length > 0){
				guaranteeStatusStr = "(";
				for(String str : guaranteeStatuss){
					guaranteeStatusStr += "\"" + str + "\",";
				}
				guaranteeStatusStr = guaranteeStatusStr.substring(0, guaranteeStatusStr.length() - 1);
				guaranteeStatusStr += ")";
			}else{
				guaranteeStatusStr = "()";
			}
			searchMap.put("guaranteeStatus", guaranteeStatusStr);
		}
		
		if(term != null && !"all".equals(term)){
			searchMap.put("term", term);
		}
		
		searchMap.put("flagBuy", "1");
		
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		//查询符合条件的银行理财产品发布信息的总数
		Integer totalResultCount = bankFinancialProductPublishService.getWebCount(searchMap);
		//查询符合条件的银行理财产品发布信息列表
		List<Entity> list = bankFinancialProductPublishService.getWebListForPage(searchMap, pageNum, pageSize, sortList, BankFinancialProductPublish.class);
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				BankFinancialProductPublish bfpp = (BankFinancialProductPublish) entity;
				BankFinancialProductPublish4WebVO bfppvo = new BankFinancialProductPublish4WebVO(bfpp);
				if(bfpp.getCustodian() != null && !"".equals(bfpp.getCustodian())){
					Bank bank = bankService.get(bfpp.getCustodian());
					if(bank != null){
						bfppvo.setCustodianName(bank.getShortName());
						Resource iconColor = this.resourceService.get(bank.getIconColor());
						if(iconColor != null){
							bfppvo.setIconColorUrl(iconColor.getUrl());
						} else {
							bfppvo.setIconColorUrl("");
						}
						Resource icon = this.resourceService.get(bank.getLogo());
						if(icon != null){
							bfppvo.setIconUrl(icon.getUrl());
						} else {
							bfppvo.setIconUrl("");
						}
					} else {
						bfppvo.setCustodianName("未选择");
					}
				}else{
					bfppvo.setCustodianName("未选择");
				}
				if(bfpp.getArea()!=null){
					BkArea ba = areaService.get(bfpp.getArea());
					if(ba != null){
						bfppvo.setArea(ba.getName());
					}else{
						bfppvo.setArea("未选择");
					}
				}else{
					bfppvo.setArea("未选择");
				}
				listVO.add(bfppvo);
			}
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取一条银行理财产品发布信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message = "用户编号", required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "version", message = "app版本号", type = DataType.STRING)
	@ActionParam(key = "device", message = "", type = DataType.STRING)
	@ResponseBody
	public Result get(String uuid, String device, String version) {
		
		device = Base64Util.getFromBase64(device);
		Boolean flag = false;
		if(device != null && !"".equals(device)){
			
			if (Utlity.DEVICE_NUMBER_IOS.equals(device)){
				if(!Utlity.checkStringNull(version)){
					version = Base64Util.getFromBase64(version);
					Map<String, String> inputParams = new HashMap<String, String>();
//					inputParams.put("status", VersionStatus.PUBLISHED);
					inputParams.put("version", version);
					inputParams.put("device", device);
					List<Entity> listCurrent = this.versionService.getListForPage(inputParams, -1, -1, null, Version.class);
					if(listCurrent != null && !listCurrent.isEmpty()){//当前版本存在 继续
						Version ver = (Version)listCurrent.get(0);
						inputParams.clear();
						inputParams.put("webMarket", "appstore");
						inputParams.put("version", ver.getUuid());
						List<Entity> list = bkWebmarketSwitchService.getListForPage(inputParams, -1, -1, null, BkWebmarketSwitchVO.class);
						if(list != null && !list.isEmpty()){
							BkWebmarketSwitchVO vo = (BkWebmarketSwitchVO)list.get(0);
							flag = vo.getFlagSwitch();
						}
					}
				}
			} else {
				flag = true;
			}
		} else {
			flag = true;
		}
		
		if(flag){
			//获取银行理财产品发布信息
			BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
			if (bfpp == null) {
				return ResultManager.createFailResult("该条数据不存在！");
			}
			BankFinancialProductPublishDetailsVO bfppvo = new BankFinancialProductPublishDetailsVO(bfpp);
			if(bfpp.getDocument() != null && !"".equals(bfpp.getDocument())){
				Resource res = resourceService.get(bfpp.getDocument());
				if(res != null){
					bfppvo.setDocumentCN(res.getFilename());
					bfppvo.setDocumentType(res.getFiletype());
					bfppvo.setDocumentURL(res.getUrl());
				}
			}else{
				bfppvo.setDocumentCN("未上传");
				bfppvo.setDocumentType("");
				bfppvo.setDocumentURL("");
			}
			
			if(bfpp.getCustodian() != null && !"".equals(bfpp.getCustodian())){
				Bank bank = bankService.get(bfpp.getCustodian());
				if(bank != null){
					bfppvo.setCustodianCN(bank.getName());
					Resource iconColor = this.resourceService.get(bank.getIconColor());
					if(iconColor != null){
						bfppvo.setIconColorUrl(iconColor.getUrl());
					} else {
						bfppvo.setIconColorUrl("");
					}
				} else {
					bfppvo.setCustodianCN("未选择");
				}
				
			}else{
				bfppvo.setCustodianCN("未选择");
			}
			
			if(bfpp.getArea() != null && !"".equals(bfpp.getArea())){
				BkArea area = areaService.get(bfpp.getArea());
				if(area != null){
					bfppvo.setAreaCN(area.getName());
				} else {
					bfppvo.setAreaCN("未选择");
				}
			}else{
				bfppvo.setAreaCN("未选择");
			}
			return ResultManager.createDataResult(bfppvo);
		} else {
			BankFinancialProductPublish bfpp = null;
			if(uuid.substring(uuid.length()-1, uuid.length()).equals("1")){
				bfpp = new BankFinancialProductPublish("1");
			} else if (uuid.substring(uuid.length()-1, uuid.length()).equals("2")) {
				bfpp = new BankFinancialProductPublish("2");
			} else {
				return ResultManager.createFailResult("该条数据不存在！");
			}
			BankFinancialProductPublishDetailsVO bfppvo = new BankFinancialProductPublishDetailsVO(bfpp);
			if(bfpp.getDocument() != null && !"".equals(bfpp.getDocument())){
				Resource res = resourceService.get(bfpp.getDocument());
				if(res != null){
					bfppvo.setDocumentCN(res.getFilename());
					bfppvo.setDocumentType(res.getFiletype());
					bfppvo.setDocumentURL(res.getUrl());
				}
			}else{
				bfppvo.setDocumentCN("未上传");
				bfppvo.setDocumentType("");
				bfppvo.setDocumentURL("");
			}
			
			if(bfpp.getCustodian() != null && !"".equals(bfpp.getCustodian())){
				Bank bank = new Bank("1");
				bfppvo.setCustodianCN(bank.getName());
				Resource iconColor = this.resourceService.get(bank.getIconColor());
				if(iconColor != null){
					bfppvo.setIconColorUrl(iconColor.getUrl());
				} else {
					bfppvo.setIconColorUrl("");
				}				
			}else{
				bfppvo.setCustodianCN("未选择");
			}
			
			if(bfpp.getArea() != null && !"".equals(bfpp.getArea())){
				BkArea area = areaService.get(bfpp.getArea());
				if(area != null){
					bfppvo.setAreaCN(area.getName());
				} else {
					bfppvo.setAreaCN("未选择");
				}
			}else{
				bfppvo.setAreaCN("未选择");
			}
			return ResultManager.createDataResult(bfppvo);
		}
		
	}

	/**
	 * 获取服务器当前时间
	 * @return
	 */
	@RequestMapping(value = "/getTime", method = RequestMethod.GET)
	@ResponseBody
	public Result getTime(){
		return ResultManager.createDataResult(System.currentTimeMillis());
	}
	
	/**
	 * 获取活期理财信息
	 * @return
	 */
	@RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
	@ResponseBody
	public Result getCurrent() {
		FundPublish fp = fundPublishService.get(FundPublishUuid.CURRENT);
		if (fp == null) {
			return ResultManager.createFailResult("该功能暂未开通！");
		}
		FundPublishDetailsVO fpvo = new FundPublishDetailsVO(fp);
		return ResultManager.createDataResult(fpvo);
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ActionParam(key = "str1", message = "app版本号", type = DataType.STRING)
	@ActionParam(key = "str2", message = "", type = DataType.STRING)
	@ResponseBody
	public Result check(String str1, String str2){
		try {
			Map<String, Object> map = ReapalUtlity.paysingleQuery(str1, str2);
			return ResultManager.createDataResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("异常");	
		}
	}
}
