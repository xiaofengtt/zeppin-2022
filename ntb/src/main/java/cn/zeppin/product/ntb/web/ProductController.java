/**
 * 
 */
package cn.zeppin.product.ntb.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductPublishDetailsVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductPublishVO;
import cn.zeppin.product.ntb.backadmin.vo.BankVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;

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
	private IBankFinancialProductService bankFinancialProductService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkAreaService areaService;
	
	@Autowired
	private IBankService bankService;
	
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
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result bankList(String name, String status, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		
		//查询符合条件的银行信息的总数
		Integer totalResultCount = bankService.getCount(searchMap);
		//查询符合条件的银行信息列表
		List<Entity> list = bankService.getListForPage(searchMap, pageNum, pageSize, sorts, BankVO.class);
		
		return ResultManager.createDataResult(list, pageNum, pageSize, totalResultCount);
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
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "custodian", type = DataType.STRING)//管理银行
	@ActionParam(key = "currencyType", type = DataType.STRING)//币种
	@ActionParam(key = "guaranteeStatus", type = DataType.STRING)//保本性质
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String custodian, String currencyType, String guaranteeStatus, Integer pageNum, Integer pageSize, String sorts) {
		
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
		
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		//查询符合条件的银行理财产品发布信息的总数
		Integer totalResultCount = bankFinancialProductPublishService.getWebCount(searchMap);
		//查询符合条件的银行理财产品发布信息列表
		List<Entity> list = bankFinancialProductPublishService.getWebListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductPublish.class);
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				BankFinancialProductPublish bfpp = (BankFinancialProductPublish) entity;
				BankFinancialProductPublishVO bfppvo = new BankFinancialProductPublishVO(bfpp);
				BkOperator bko = bkOperatorService.get(bfpp.getCreator());
				if(bko != null){
					bfppvo.setCreator(bko.getRealname());
				}
				if(bfpp.getCustodian() != null && !"".equals(bfpp.getCustodian())){
					Bank bank = bankService.get(bfpp.getCustodian());
					if(bank != null){
						bfppvo.setCustodianName(bank.getName());
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
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
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
		if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
			BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
			if(operator != null){
				bfppvo.setCreatorName(operator.getName());
			} else {
				bfppvo.setCreatorName("未选择");
			}
			
		}else{
			bfppvo.setCreatorName("未选择");
		}
		return ResultManager.createDataResult(bfppvo);
	}
}
