package cn.zeppin.product.ntb.qcb.controller.wechat;

import java.sql.Timestamp;
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
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishDailyService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.web.vo.FundPublishDetailsVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.web.vo.BankFinancialProductPublish4WebVO;
import cn.zeppin.product.ntb.web.vo.BankFinancialProductPublishDetailsVO;

/**
 * @author hehe
 *
 * 微信企财宝员工理财产品接口
 */

@Controller
@RequestMapping(value = "/qcbWechat/product")
public class WechatProductController extends BaseController {
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IFundPublishDailyService fundPublishDailyService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkAreaService areaService;
	
	@Autowired
	private IBankService bankService;
	
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
	@ResponseBody
	public Result list(String name, String custodian, String currencyType, String guaranteeStatus, String term, Integer pageNum, Integer pageSize, String sorts) {
		
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
	@ActionParam(key = "uuid", type = DataType.STRING, message = "银行理财产品编号", required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
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
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("fundPublish", fp.getUuid());
		inputParams.put("endtime", new Timestamp(System.currentTimeMillis()).toString());
		
		List<Entity> dailyList = this.fundPublishDailyService.getListForPage(inputParams, 0, 10, null, FundPublishDaily.class);
		
		for(Entity e :dailyList){
			fpvo.getDailyList().add((FundPublishDaily)e);
		}
		
		return ResultManager.createDataResult(fpvo);
	}
}
