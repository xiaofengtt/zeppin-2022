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
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductPublishDetailsVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductPublishOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductPublishOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductPublishVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublishOperate.BankFinancialProductPublishOperateType;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.HtmlHelper;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 后台银行理财产品发布管理
 */

@Controller
@RequestMapping(value = "/backadmin/bankFinancialProductPublish")
public class BankFinancialProductPublishController extends BaseController {

	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IBankFinancialProductPublishOperateService bankFinancialProductPublishOperateService;
	
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
	@ActionParam(key = "status", type = DataType.STRING)//状态
	@ActionParam(key = "stage", type = DataType.STRING)//阶段
	@ActionParam(key = "income", type = DataType.STRING)//期限
	@ActionParam(key = "term", type = DataType.STRING)//期限
	@ActionParam(key = "type", type = DataType.STRING)//类型
	@ActionParam(key = "custodian", type = DataType.STRING)//管理银行
	@ActionParam(key = "riskLevel", type = DataType.STRING)//风险等级
	@ActionParam(key = "redeem", type = DataType.STRING)//赎回状态
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status,String stage, String income, String term, String type, String riskLevel, 
			String custodian, String redeem, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(stage) && ("all".equals(status) || BankFinancialProductStatus.CHECKED.equals(status))){
			searchMap.put("stage", stage);
		}
		if(!"all".equals(income)){
			searchMap.put("income", income);
		}
		if(!"all".equals(term)){
			searchMap.put("term", term);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(!"all".equals(riskLevel)){
			searchMap.put("riskLevel", riskLevel);
		}
		if(!"all".equals(custodian)){
			searchMap.put("custodian", custodian);
		}
		if(!"all".equals(redeem)){
			searchMap.put("redeem", redeem);
		}
		
		//查询符合条件的银行理财产品发布信息的总数
		Integer totalResultCount = bankFinancialProductPublishService.getCount(searchMap);
		//查询符合条件的银行理财产品发布信息列表
		List<Entity> list = bankFinancialProductPublishService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductPublish.class);
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
	 * 获取银行理财产品发布分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = bankFinancialProductPublishService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品发布分阶段列表
	 * @return
	 */
	@RequestMapping(value = "/stageList", method = RequestMethod.GET)
	@ResponseBody
	public Result stageList(String stage) {
		List<Entity> list = bankFinancialProductPublishService.getStageList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
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
			Bank bank = this.bankService.get(bfpp.getCustodian());
			if(bank != null){
				bfppvo.setCustodianCN(bank.getName());
				if(bank.getLogo() != null && !"".equals(bank.getLogo())){
					Resource resource = resourceService.get(bank.getLogo());
					if (resource != null) {
						bfppvo.setCustodianLogo(resource.getUrl());
					}else{
						bfppvo.setCustodianLogo("");
					}
				}else{
					bfppvo.setCustodianLogo("");
				}
			} else {
				bfppvo.setCustodianCN("未选择");
				bfppvo.setCustodianLogo("");
			}
			
		}else{
			bfppvo.setCustodianCN("未选择");
			bfppvo.setCustodianLogo("");
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
	
	/**
	 * 添加一条银行理财产品发布信息
	 * @param bankFinancialProduct
	 * @param name
	 * @param series
	 * @param scode
	 * @param shortname
	 * @param type
	 * @param url
	 * @param target
	 * @param targetAnnualizedReturnRate
	 * @param minInvestAmount
	 * @param minInvestAmountAdd
	 * @param maxInvestAmount
	 * @param totalAmount
	 * @param currencyType
	 * @param term
	 * @param custodian
	 * @param riskLevel
	 * @param flagPurchase
	 * @param flagRedemption
	 * @param flagFlexible
	 * @param minAnnualizedReturnRate
	 * @param recordDate
	 * @param guaranteeStatus
	 * @param area
	 * @param subscribeFee
	 * @param purchaseFee
	 * @param redemingFee
	 * @param managementFee
	 * @param custodyFee
	 * @param networkFee
	 * @param collectStarttime
	 * @param collectEndtime
	 * @param valueDate
	 * @param maturityDate
	 * @param investScope
	 * @param revenueFeature
	 * @param remark
	 * @param document
	 * @return
	 * @throws ParserException 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bankFinancialProduct", type = DataType.STRING, required = true)
	@ActionParam(key = "custodian", type = DataType.STRING, required = true)
	@ActionParam(key = "name", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "series", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "shortname", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "scode", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "type", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "target", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalAmount", type = DataType.NUMBER)
	@ActionParam(key = "paymentType", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyType", type = DataType.STRING, required = true)
	@ActionParam(key = "riskLevel", type = DataType.STRING, required = true)
	@ActionParam(key = "minAnnualizedReturnRate", type = DataType.NUMBER)
	@ActionParam(key = "area", type = DataType.STRING, required = true)
	@ActionParam(key = "flagFlexible", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagPurchase", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagRedemption", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "collectStarttime", type = DataType.STRING, required = true)
	@ActionParam(key = "collectEndtime", type = DataType.STRING, required = true)
	@ActionParam(key = "recordDate", type = DataType.DATE, required = true)
	@ActionParam(key = "valueDate", type = DataType.DATE, required = true)
	@ActionParam(key = "maturityDate", type = DataType.DATE, required = true)
	@ActionParam(key = "term", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmount", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmountAdd", type = DataType.NUMBER, required = true)
	@ActionParam(key = "maxInvestAmount", type = DataType.NUMBER)
	@ActionParam(key = "subscribeFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "purchaseFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "redemingFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "managementFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "custodyFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "networkFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investScope", type = DataType.STRING, required = true, maxLength = 5000)
	@ActionParam(key = "revenueFeature", type = DataType.STRING, required = true,maxLength = 5000)
	@ActionParam(key = "remark", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", type = DataType.STRING)
	@ActionParam(key = "guaranteeStatus", type = DataType.STRING)
	@ResponseBody
	public Result add(String bankFinancialProduct, String name, String series, String scode, String shortname, String type, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount,String paymentType, String url, String currencyType, 
			Integer term, String custodian, String riskLevel, String flagPurchase, String flagRedemption, String flagFlexible, BigDecimal minAnnualizedReturnRate, 
			String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, BigDecimal purchaseFee, BigDecimal redemingFee, BigDecimal managementFee, 
			BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, String valueDate, String maturityDate, String investScope, 
			String revenueFeature, String remark, String document) throws ParserException {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//创建对象
		BankFinancialProductPublish bfpp = new BankFinancialProductPublish();
		BankFinancialProduct bfp = bankFinancialProductService.get(bankFinancialProduct);
		if(bfp != null){
			bfpp.setBankFinancialProduct(bankFinancialProduct);
		}else{
			return ResultManager.createFailResult("所继承的银行理财产品信息不能为空！");
		}
		bfpp.setUuid(UUID.randomUUID().toString());
		if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByName(name,null)){
			return ResultManager.createFailResult("银行理财产品发布名称已存在！");
		}
		bfpp.setName(name);
		bfpp.setSeries(series);
		if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByScode(scode,null)){
			return ResultManager.createFailResult("银行理财产品发布编号已存在！");
		}
		bfpp.setScode(scode);
		bfpp.setShortname(shortname);
		bfpp.setType(type);
		bfpp.setUrl(url);
		bfpp.setTarget(target);
		bfpp.setTargetAnnualizedReturnRate(targetAnnualizedReturnRate);
		bfpp.setMinInvestAmount(minInvestAmount);
		bfpp.setMinInvestAmountAdd(minInvestAmountAdd);
		bfpp.setMaxInvestAmount(maxInvestAmount);
		bfpp.setStage(BankFinancialProductPublishStage.UNSTART);
		bfpp.setStatus(BankFinancialProductPublishStatus.CHECKED);
		bfpp.setCreator(currentOperator.getUuid());
		bfpp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		bfpp.setTotalAmount(totalAmount.multiply(BigDecimal.valueOf(100000000)));
		Bank bank = bankService.get(custodian);
		if(bank != null){
			bfpp.setCustodian(custodian);
		}else{
			return ResultManager.createFailResult("资金托管方选择错误！");
		}
		bfpp.setRiskLevel(riskLevel);
		bfpp.setCurrencyType(currencyType);
		bfpp.setGuaranteeStatus(guaranteeStatus);
		bfpp.setFlagPurchase(Boolean.valueOf(flagPurchase));
		bfpp.setFlagRedemption(Boolean.valueOf(flagRedemption));
		bfpp.setFlagFlexible(Boolean.valueOf(flagFlexible));
		bfpp.setMinAnnualizedReturnRate(minAnnualizedReturnRate);
		bfpp.setPaymentType(paymentType);
		bfpp.setRecordDate(Timestamp.valueOf(Utlity.getFullTime(recordDate)));
		
		if(area!=null && !area.equals("")){
			BkArea bkArea = this.areaService.get(area);
			if(bkArea != null){
				bfpp.setArea(area);
			} else {
				return ResultManager.createFailResult("发行地区选择错误！");
			}
		}
		bfpp.setSubscribeFee(subscribeFee);
		bfpp.setPurchaseFee(purchaseFee);
		bfpp.setRedemingFee(redemingFee);
		bfpp.setManagementFee(managementFee);
		bfpp.setNetworkFee(networkFee);
		bfpp.setCollectStarttime(Timestamp.valueOf(collectStarttime));
		bfpp.setCollectEndtime(Timestamp.valueOf(collectEndtime));
		bfpp.setValueDate(Timestamp.valueOf(Utlity.getFullTime(valueDate)));
		bfpp.setMaturityDate(Timestamp.valueOf(Utlity.getFullTime(maturityDate)));
		bfpp.setTerm(term);
		bfpp.setInvestScope(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(investScope)));
		bfpp.setRevenueFeature(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(revenueFeature)));
		bfpp.setRemark(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(remark)));
		
		Resource resource = resourceService.get(document);
		if(resource != null){
			resource.setFilename(bfpp.getName()+"("+bfpp.getScode()+")");
			resource = resourceService.updateName(resource);
			if(resource == null){
				return ResultManager.createFailResult("产品说明书替换失败！");
			}
			bfpp.setDocument(document);
		}
		
		//添加待审核记录
		BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
		bfppo.setUuid(UUID.randomUUID().toString());
		bfppo.setType(BankFinancialProductPublishOperateType.ADD);
		bfppo.setValue(JSONUtils.obj2json(bfpp));
		bfppo.setStatus(BankFinancialProductPublishOperateStatus.DRAFT);//草稿
		bfppo.setCreator(currentOperator.getUuid());
		bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		bankFinancialProductPublishOperateService.insert(bfppo);
		return ResultManager.createDataResult(bfpp, "添加待审记录成功！");
	
	}
	
	/**
	 * 编辑一条银行理财产品发布信息
	 * @param uuid
	 * @param name
	 * @param series
	 * @param scode
	 * @param shortname
	 * @param type
	 * @param url
	 * @param target
	 * @param targetAnnualizedReturnRate
	 * @param minInvestAmount
	 * @param minInvestAmountAdd
	 * @param maxInvestAmount
	 * @param status
	 * @param totalAmount
	 * @param style
	 * @param creditLevel
	 * @param flagCloseend
	 * @param currencyType
	 * @param term
	 * @param custodian
	 * @param riskLevel
	 * @param flagPurchase
	 * @param flagRedemption
	 * @param flagFlexible
	 * @param minAnnualizedReturnRate
	 * @param recordDate
	 * @param guaranteeStatus
	 * @param area
	 * @param subscribeFee
	 * @param purchaseFee
	 * @param redemingFee
	 * @param managementFee
	 * @param custodyFee
	 * @param networkFee
	 * @param collectStarttime
	 * @param collectEndtime
	 * @param valueDate
	 * @param maturityDate
	 * @param investScope
	 * @param revenueFeature
	 * @param remark
	 * @param document
	 * @return
	 * @throws ParserException 
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "custodian", type = DataType.STRING, required = true)
	@ActionParam(key = "name", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "series", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "shortname", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "scode", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "type", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "target", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalAmount", type = DataType.NUMBER)
	@ActionParam(key = "paymentType", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyType", type = DataType.STRING, required = true)
	@ActionParam(key = "riskLevel", type = DataType.STRING, required = true)
	@ActionParam(key = "minAnnualizedReturnRate", type = DataType.NUMBER)
	@ActionParam(key = "area", type = DataType.STRING, required = true)
	@ActionParam(key = "flagFlexible", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagPurchase", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagRedemption", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "collectStarttime", type = DataType.STRING, required = true)
	@ActionParam(key = "collectEndtime", type = DataType.STRING, required = true)
	@ActionParam(key = "recordDate", type = DataType.DATE, required = true)
	@ActionParam(key = "valueDate", type = DataType.DATE, required = true)
	@ActionParam(key = "maturityDate", type = DataType.DATE, required = true)
	@ActionParam(key = "term", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmount", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmountAdd", type = DataType.NUMBER, required = true)
	@ActionParam(key = "maxInvestAmount", type = DataType.NUMBER)
	@ActionParam(key = "subscribeFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "purchaseFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "redemingFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "managementFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "custodyFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "networkFee", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investScope", type = DataType.STRING, required = true, maxLength = 5000)
	@ActionParam(key = "revenueFeature", type = DataType.STRING, required = true,maxLength = 5000)
	@ActionParam(key = "remark", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", type = DataType.STRING)
	@ActionParam(key = "guaranteeStatus", type = DataType.STRING)
	@ResponseBody
	public Result edit(String uuid, String name, String series, String scode, String shortname, String type, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount,String paymentType, String url,
			String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, String flagRedemption, String flagFlexible, 
			BigDecimal minAnnualizedReturnRate, String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, BigDecimal purchaseFee, 
			BigDecimal redemingFee, BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, 
			String valueDate, String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if (bfpp != null) {
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByName(name,uuid)){
				return ResultManager.createFailResult("银行理财产品发布名称已存在！");
			}
			bfpp.setName(name);
			bfpp.setSeries(series);
			if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByScode(scode,uuid)){
				return ResultManager.createFailResult("银行理财产品发布编号已存在！");
			}
			bfpp.setScode(scode);
			bfpp.setShortname(shortname);
			bfpp.setType(type);
			bfpp.setUrl(url);
			bfpp.setTarget(target);
			bfpp.setTargetAnnualizedReturnRate(targetAnnualizedReturnRate);
			bfpp.setMinInvestAmount(minInvestAmount);
			bfpp.setMinInvestAmountAdd(minInvestAmountAdd);
			bfpp.setMaxInvestAmount(maxInvestAmount);
			bfpp.setTotalAmount(totalAmount.multiply(BigDecimal.valueOf(100000000)));
			Bank bank = bankService.get(custodian);
			if(bank != null){
				bfpp.setCustodian(custodian);
			}else{
				return ResultManager.createFailResult("资金托管方选择错误！");
			}
			bfpp.setRiskLevel(riskLevel);
			bfpp.setCurrencyType(currencyType);
			bfpp.setGuaranteeStatus(guaranteeStatus);
			bfpp.setFlagPurchase(Boolean.valueOf(flagPurchase));
			bfpp.setFlagRedemption(Boolean.valueOf(flagRedemption));
			bfpp.setFlagFlexible(Boolean.valueOf(flagFlexible));
			bfpp.setMinAnnualizedReturnRate(minAnnualizedReturnRate);
			bfpp.setPaymentType(paymentType);
			bfpp.setRecordDate(Timestamp.valueOf(Utlity.getFullTime(recordDate)));
			if(area!=null && !area.equals("")){
				BkArea bkArea = this.areaService.get(area);
				if(bkArea != null){
					bfpp.setArea(area);
				} else {
					return ResultManager.createFailResult("发行地区选择错误！");
				}
			}
			bfpp.setSubscribeFee(subscribeFee);
			bfpp.setPurchaseFee(purchaseFee);
			bfpp.setRedemingFee(redemingFee);
			bfpp.setManagementFee(managementFee);
			bfpp.setCustodyFee(custodyFee);
			bfpp.setNetworkFee(networkFee);
			bfpp.setCollectStarttime(Timestamp.valueOf(collectStarttime));
			bfpp.setCollectEndtime(Timestamp.valueOf(collectEndtime));
			bfpp.setValueDate(Timestamp.valueOf(Utlity.getFullTime(valueDate)));
			bfpp.setMaturityDate(Timestamp.valueOf(Utlity.getFullTime(maturityDate)));
			bfpp.setTerm(term);
			bfpp.setInvestScope(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(investScope)));
			bfpp.setRevenueFeature(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(revenueFeature)));
			bfpp.setRemark(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(remark)));
			Resource resource = resourceService.get(document);
			if(resource != null){
				resource.setFilename(bfpp.getName()+"("+bfpp.getScode()+")");
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("产品说明书替换失败！");
				}
				bfpp.setDocument(document);
			}
			
			//添加待审核记录
			BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
			bfppo.setUuid(UUID.randomUUID().toString());
			bfppo.setBankFinancialProductPublish(bfpp.getUuid());
			bfppo.setType(BankFinancialProductPublishOperateType.EDIT);
			bfppo.setValue(JSONUtils.obj2json(bfpp));
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.DRAFT);
			bfppo.setCreator(currentOperator.getUuid());
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.insert(bfppo);
			return ResultManager.createDataResult(bfpp, "添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 异常下线一条银行理财产品发布信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result exception(String uuid) {
		
		//获取银行理财产品发布信息
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//添加待审记录
			BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
			bfppo.setUuid(UUID.randomUUID().toString());
			bfppo.setBankFinancialProductPublish(bfpp.getUuid());
			bfppo.setType(BankFinancialProductPublishOperateType.EXCEPTION);
			bfppo.setValue("");
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.UNCHECKED);
			bfppo.setCreator(currentOperator.getUuid());
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.insert(bfppo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条银行理财产品发布信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//获取银行理财产品发布信息
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//添加待审记录
			BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
			bfppo.setUuid(UUID.randomUUID().toString());
			bfppo.setBankFinancialProductPublish(bfpp.getUuid());
			bfppo.setType(BankFinancialProductPublishOperateType.DELETE);
			bfppo.setValue("");
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.UNCHECKED);
			bfppo.setCreator(currentOperator.getUuid());
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.insert(bfppo);
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取一条银行理财产品发布审核信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//获取银行理财产品发布信息
		BankFinancialProductPublishOperate bfppo = bankFinancialProductPublishOperateService.get(uuid);
		if (bfppo == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		BankFinancialProductPublishOperateDetailVO bfppodVO = new BankFinancialProductPublishOperateDetailVO(bfppo);
		BkOperator creator = this.bkOperatorService.get(bfppodVO.getCreator());
		if(creator != null){
			bfppodVO.setCreatorName(creator.getRealname());
		}
		
		if(!BankFinancialProductPublishOperateType.ADD.equals(bfppo.getType())){
			BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(bfppo.getBankFinancialProductPublish());
			BankFinancialProductPublishDetailsVO bfppvo = new BankFinancialProductPublishDetailsVO(bfpp);
			if(bfpp.getDocument() != null && !"".equals(bfpp.getDocument())){
				Resource res = this.resourceService.get(bfpp.getDocument());
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
				Bank bank = this.bankService.get(bfpp.getCustodian());
				if(bank != null){
					bfppvo.setCustodianCN(bank.getName());
					if(bank.getLogo() != null && !"".equals(bank.getLogo())){
						Resource resource = resourceService.get(bank.getLogo());
						if (resource != null) {
							bfppvo.setCustodianLogo(resource.getUrl());
						}else{
							bfppvo.setCustodianLogo("");
						}
					}else{
						bfppvo.setCustodianLogo("");
					}
				} else {
					bfppvo.setCustodianCN("未选择");
					bfppvo.setCustodianLogo("");
				}
				
			}else{
				bfppvo.setCustodianCN("未选择");
				bfppvo.setCustodianLogo("");
			}
			
			if(bfpp.getArea() != null && !"".equals(bfpp.getArea())){
				BkArea area = this.areaService.get(bfpp.getArea());
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
					bfppvo.setCreatorName("无");
				}
				
			}else{
				bfppvo.setCreatorName("无");
			}
			bfppodVO.setOldData(bfppvo);
		}
		if(BankFinancialProductPublishOperateType.ADD.equals(bfppo.getType()) || BankFinancialProductPublishOperateType.EDIT.equals(bfppo.getType())){
			BankFinancialProductPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);	
			BankFinancialProductPublishDetailsVO bfppvo = new BankFinancialProductPublishDetailsVO(bfpp);
			if(bfpp.getDocument() != null && !"".equals(bfpp.getDocument())){
				Resource res = this.resourceService.get(bfpp.getDocument());
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
				Bank bank = this.bankService.get(bfpp.getCustodian());
				if(bank != null){
					bfppvo.setCustodianCN(bank.getName());
					if(bank.getLogo() != null && !"".equals(bank.getLogo())){
						Resource resource = resourceService.get(bank.getLogo());
						if (resource != null) {
							bfppvo.setCustodianLogo(resource.getUrl());
						}else{
							bfppvo.setCustodianLogo("");
						}
					}else{
						bfppvo.setCustodianLogo("");
					}
				} else {
					bfppvo.setCustodianCN("未选择");
					bfppvo.setCustodianLogo("");
				}
				
			}else{
				bfppvo.setCustodianCN("未选择");
				bfppvo.setCustodianLogo("");
			}
			
			if(bfpp.getArea() != null && !"".equals(bfpp.getArea())){
				BkArea area = this.areaService.get(bfpp.getArea());
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
					bfppvo.setCreatorName("无");
				}
				
			}else{
				bfppvo.setCreatorName("无");
			}
			bfppodVO.setNewData(bfppvo);
		}
		return ResultManager.createDataResult(bfppodVO);
	}
	
	/**
	 * 编辑一条银行理财产品发布操作信息
	 * @param uuid
	 * @param name
	 * @param series
	 * @param scode
	 * @param shortname
	 * @param type
	 * @param target
	 * @param targetAnnualizedReturnRate
	 * @param minInvestAmount
	 * @param minInvestAmountAdd
	 * @param maxInvestAmount
	 * @param status
	 * @param totalAmount
	 * @param style
	 * @param creditLevel
	 * @param flagCloseend
	 * @param currencyType
	 * @param term
	 * @param custodian
	 * @param riskLevel
	 * @param flagPurchase
	 * @param flagRedemption
	 * @param flagFlexible
	 * @param minAnnualizedReturnRate
	 * @param recordDate
	 * @param guaranteeStatus
	 * @param area
	 * @param subscribeFee
	 * @param purchaseFee
	 * @param redemingFee
	 * @param managementFee
	 * @param custodyFee
	 * @param networkFee
	 * @param collectStarttime
	 * @param collectEndtime
	 * @param valueDate
	 * @param maturityDate
	 * @param investScope
	 * @param revenueFeature
	 * @param remark
	 * @param document
	 * @return
	 * @throws ParserException 
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "series", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "scode", type = DataType.STRING, required = true, minLength = 1, maxLength = 100)
	@ActionParam(key = "shortname", type = DataType.STRING, required = true, minLength = 1, maxLength = 50)
	@ActionParam(key = "type", type = DataType.STRING, required = true, minLength = 1, maxLength = 20)
	@ActionParam(key = "url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "target", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", required = true, type = DataType.NUMBER)
	@ActionParam(key = "minInvestAmount", required = true, type = DataType.NUMBER)
	@ActionParam(key = "minInvestAmountAdd", required = true, type = DataType.NUMBER)
	@ActionParam(key = "maxInvestAmount", required = true, type = DataType.NUMBER)
	@ActionParam(key = "totalAmount", required = true, type = DataType.NUMBER)//p1
	@ActionParam(key = "paymentType", type = DataType.STRING)
	@ActionParam(key = "style", type = DataType.STRING)
	@ActionParam(key = "creditLevel", type = DataType.STRING)
	@ActionParam(key = "flagCloseend", type = DataType.BOOLEAN)
	@ActionParam(key = "currencyType", type = DataType.STRING)
	@ActionParam(key = "term", type = DataType.NUMBER)//custodian
	@ActionParam(key = "custodian", type = DataType.STRING)
	@ActionParam(key = "riskLevel", type = DataType.STRING)
	@ActionParam(key = "flagPurchase", type = DataType.BOOLEAN)
	@ActionParam(key = "flagRedemption", type = DataType.BOOLEAN)
	@ActionParam(key = "flagFlexible", type = DataType.BOOLEAN)
	@ActionParam(key = "minAnnualizedReturnRate", type = DataType.NUMBER)
	@ActionParam(key = "recordDate", type = DataType.DATE)
	@ActionParam(key = "guaranteeStatus", type = DataType.STRING)
	@ActionParam(key = "area", type = DataType.STRING)
	@ActionParam(key = "subscribeFee", type = DataType.NUMBER)
	@ActionParam(key = "purchaseFee", type = DataType.NUMBER)
	@ActionParam(key = "redemingFee", type = DataType.NUMBER)
	@ActionParam(key = "managementFee", type = DataType.NUMBER)
	@ActionParam(key = "custodyFee", type = DataType.NUMBER)
	@ActionParam(key = "networkFee", type = DataType.NUMBER)
	@ActionParam(key = "collectStarttime", type = DataType.STRING)
	@ActionParam(key = "collectEndtime", type = DataType.STRING)
	@ActionParam(key = "valueDate", type = DataType.DATE)
	@ActionParam(key = "maturityDate", type = DataType.DATE)
	@ActionParam(key = "investScope", type = DataType.STRING, maxLength = 1000)
	@ActionParam(key = "revenueFeature", type = DataType.STRING, maxLength = 2000)
	@ActionParam(key = "remark", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", type = DataType.STRING)
	@ResponseBody
	public Result operateEdit(String uuid, String name, String series, String scode, String shortname, String type, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount,String paymentType, String style, String url, 
			String creditLevel, String flagCloseend, String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, 
			String flagRedemption, String flagFlexible, BigDecimal minAnnualizedReturnRate, String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, 
			BigDecimal purchaseFee, BigDecimal redemingFee, BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, 
			String valueDate, String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		BankFinancialProductPublishOperate bfppo = bankFinancialProductPublishOperateService.get(uuid);
		if (bfppo != null) {
			BankFinancialProductPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);
			
			if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByName(name,bfppo.getBankFinancialProductPublish())){
				return ResultManager.createFailResult("银行理财产品发布名称已存在！");
			}
			bfpp.setName(name);
			bfpp.setSeries(series);
			if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByScode(scode,bfppo.getBankFinancialProductPublish())){
				return ResultManager.createFailResult("银行理财产品发布编号已存在！");
			}
			bfpp.setScode(scode);
			bfpp.setShortname(shortname);
			bfpp.setType(type);
			bfpp.setUrl(url);
			bfpp.setTarget(target);
			bfpp.setTargetAnnualizedReturnRate(targetAnnualizedReturnRate);
			bfpp.setMinInvestAmount(minInvestAmount);
			bfpp.setMinInvestAmountAdd(minInvestAmountAdd);
			bfpp.setMaxInvestAmount(maxInvestAmount);
			bfpp.setTotalAmount(totalAmount.multiply(BigDecimal.valueOf(100000000)));
			Bank bank = bankService.get(custodian);
			if(bank != null){
				bfpp.setCustodian(custodian);
			}else{
				return ResultManager.createFailResult("资金托管方选择错误！");
			}
			bfpp.setStyle(style);
			bfpp.setRiskLevel(riskLevel);
			bfpp.setCreditLevel(creditLevel);
			bfpp.setCurrencyType(currencyType);
			bfpp.setGuaranteeStatus(guaranteeStatus);
			bfpp.setFlagCloseend(Boolean.valueOf(flagCloseend));
			bfpp.setFlagPurchase(Boolean.valueOf(flagPurchase));
			bfpp.setFlagRedemption(Boolean.valueOf(flagRedemption));
			bfpp.setFlagFlexible(Boolean.valueOf(flagFlexible));
			bfpp.setMinAnnualizedReturnRate(minAnnualizedReturnRate);
			bfpp.setPaymentType(paymentType);
			bfpp.setRecordDate(Timestamp.valueOf(Utlity.getFullTime(recordDate)));
			if(area!=null && !area.equals("")){
				BkArea bkArea = this.areaService.get(area);
				if(bkArea != null){
					bfpp.setArea(area);
				} else {
					return ResultManager.createFailResult("发行地区选择错误！");
				}
			}
			bfpp.setSubscribeFee(subscribeFee);
			bfpp.setPurchaseFee(purchaseFee);
			bfpp.setRedemingFee(redemingFee);
			bfpp.setManagementFee(managementFee);
			bfpp.setCustodyFee(custodyFee);
			bfpp.setNetworkFee(networkFee);
			bfpp.setCollectStarttime(Timestamp.valueOf(collectStarttime));
			bfpp.setCollectEndtime(Timestamp.valueOf(collectEndtime));
			bfpp.setValueDate(Timestamp.valueOf(Utlity.getFullTime(valueDate)));
			bfpp.setMaturityDate(Timestamp.valueOf(Utlity.getFullTime(maturityDate)));
			bfpp.setTerm(term);
			bfpp.setInvestScope(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(investScope)));
			bfpp.setRevenueFeature(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(revenueFeature)));
			bfpp.setRemark(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(remark)));
			Resource resource = resourceService.get(document);
			if(resource != null){
				resource.setFilename(bfpp.getName()+"("+bfpp.getScode()+")");
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("产品说明书替换失败！");
				}
				bfpp.setDocument(document);
			}
			
			//修改待审核记录
			bfppo.setValue(JSONUtils.obj2json(bfpp));
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.update(bfppo);
			return ResultManager.createDataResult("修改待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条银行理财产品操作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取银行理财产品操作信息
		BankFinancialProductPublishOperate bfppo = bankFinancialProductPublishOperateService.get(uuid);
		if(bfppo != null){
			if(!BankFinancialProductPublishOperateStatus.DRAFT.equals(bfppo.getStatus()) && !BankFinancialProductPublishOperateStatus.UNPASSED.equals(bfppo.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.DELETED);
			bankFinancialProductPublishOperateService.update(bfppo);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 银行理财产品发布信息修改操作列表
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "type", type = DataType.STRING)
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//查询符合条件的银行理财产品信息的总数
		Integer totalResultCount = bankFinancialProductPublishOperateService.getCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = bankFinancialProductPublishOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductPublishOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductPublishOperate bfppo = (BankFinancialProductPublishOperate)e;
				BankFinancialProductPublishOperateVO bfppoVO = new BankFinancialProductPublishOperateVO(bfppo);
				if(BankFinancialProductPublishOperateType.ADD.equals(bfppo.getType())){
					BankFinancialProductPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);
					bfppoVO.setBankFinancialProductPublishName(bfpp.getName());
					bfppoVO.setScode(bfpp.getScode());
					bfppoVO.setCustodian(bfpp.getCustodian());
					if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
						Bank bank = this.bankService.get(bfpp.getCustodian());
						if(bank != null){
							bfppoVO.setCustodianName(bank.getName());
						}else{
							bfppoVO.setCustodianName("未选择");
						}
					}else{
						bfppoVO.setCustodianName("未选择");
					}
				}
				if(bfppoVO.getBankFinancialProductPublish() != null && !"".equals(bfppoVO.getBankFinancialProductPublish())){
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(bfppoVO.getBankFinancialProductPublish());
					if(bfpp != null){
						bfppoVO.setBankFinancialProductPublishName(bfpp.getName());
						bfppoVO.setScode(bfpp.getScode());
						bfppoVO.setCustodian(bfpp.getCustodian());
						if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
							Bank bank = this.bankService.get(bfpp.getCustodian());
							if(bank != null){
								bfppoVO.setCustodianName(bank.getName());
							}else{
								bfppoVO.setCustodianName("未选择");
							}
						}else{
							bfppoVO.setCustodianName("未选择");
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(bfppoVO.getCreator());
				if(creator != null){
					bfppoVO.setCreatorName(creator.getRealname());
				}
				if(bfppoVO.getChecker() != null && !"".equals(bfppoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfppoVO.getChecker());
					if(checker != null){
						bfppoVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(bfppoVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 银行理财产品发布信息修改操作列表（管理员）
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING)
	@ActionParam(key = "type", type = DataType.STRING)
	@ActionParam(key = "name", type = DataType.STRING)
	@ActionParam(key = "pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
//		if(!"all".equals(status)){
//			searchMap.put("status", status);
//		}
		searchMap.put("status", status);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//查询符合条件的银行理财产品信息的总数
		Integer totalResultCount = bankFinancialProductPublishOperateService.getCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = bankFinancialProductPublishOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductPublishOperate.class);
		
		//封装可用信息到前台List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductPublishOperate bfppo = (BankFinancialProductPublishOperate)e;
				BankFinancialProductPublishOperateVO bfppoVO = new BankFinancialProductPublishOperateVO(bfppo);
				if(BankFinancialProductPublishOperateType.ADD.equals(bfppo.getType())){
					BankFinancialProductPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);
					bfppoVO.setBankFinancialProductPublishName(bfpp.getName());
					bfppoVO.setScode(bfpp.getScode());
					bfppoVO.setCustodian(bfpp.getCustodian());
					if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
						Bank bank = this.bankService.get(bfpp.getCustodian());
						if(bank != null){
							bfppoVO.setCustodianName(bank.getName());
						}else{
							bfppoVO.setCustodianName("未选择");
						}
					}else{
						bfppoVO.setCustodianName("未选择");
					}
				}
				if(bfppoVO.getBankFinancialProductPublish() != null && !"".equals(bfppoVO.getBankFinancialProductPublish())){
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(bfppoVO.getBankFinancialProductPublish());
					if(bfpp != null){
						bfppoVO.setBankFinancialProductPublishName(bfpp.getName());
						bfppoVO.setScode(bfpp.getScode());
						bfppoVO.setCustodian(bfpp.getCustodian());
						if(bfpp.getCustodian()!=null && !"".equals(bfpp.getCustodian())){
							Bank bank = this.bankService.get(bfpp.getCustodian());
							if(bank != null){
								bfppoVO.setCustodianName(bank.getName());
							}else{
								bfppoVO.setCustodianName("未选择");
							}
						}else{
							bfppoVO.setCustodianName("未选择");
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(bfppoVO.getCreator());
				if(creator != null){
					bfppoVO.setCreatorName(creator.getRealname());
				}
				if(bfppoVO.getChecker() != null && !"".equals(bfppoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfppoVO.getChecker());
					if(checker != null){
						bfppoVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(bfppoVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 *待审核草稿-提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//获取银行理财产品操作信息
		BankFinancialProductPublishOperate bfpo = bankFinancialProductPublishOperateService.get(uuid);
		if(bfpo != null){
			if(BankFinancialProductInvestOperateStatus.CHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
			bankFinancialProductPublishOperateService.update(bfpo);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核银行理财产品发布信息修改操作
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
		if(!BankFinancialProductPublishOperateStatus.CHECKED.equals(status) && !BankFinancialProductPublishOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("发布状态错误");
		}
		
		//获取银行理财产品发布信息
		BankFinancialProductPublishOperate bfppo = bankFinancialProductPublishOperateService.get(uuid);
		if(bfppo != null){
			if(!BankFinancialProductPublishOperateStatus.UNCHECKED.equals(bfppo.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			bfppo.setChecker(currentOperator.getUuid());
			bfppo.setChecktime(new Timestamp(System.currentTimeMillis()));
			bfppo.setStatus(status);
			bfppo.setReason(reason);
			HashMap<String, Object> resultMap = bankFinancialProductPublishOperateService.check(bfppo);
			if((Boolean)resultMap.get("result")){
				return ResultManager.createSuccessResult("审核记录成功！");
			}else{
				return ResultManager.createFailResult(resultMap.get("message").toString());
			}
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取银行理财产品发布操作分状态列表
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//获取银行理财产品发布信息
		List<Entity> list = bankFinancialProductPublishOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品发布操作分状态列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取银行理财产品发布信息
		List<Entity> list = bankFinancialProductPublishOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品发布操作分类型列表
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = bankFinancialProductPublishOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品发布操作分类型列表(管理员)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
//		if(!"all".equals(status)){
//			searchMap.put("status", status);
//		}
		searchMap.put("status", status);
		
		List<Entity> list = bankFinancialProductPublishOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
