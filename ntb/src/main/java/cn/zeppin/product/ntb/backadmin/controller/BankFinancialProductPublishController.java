/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
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

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductDailyService;
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
import cn.zeppin.product.ntb.core.entity.BankFinancialProductDaily;
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
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.utility.HtmlHelper;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * ????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/bankFinancialProductPublish")
public class BankFinancialProductPublishController extends BaseController {

	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IBankFinancialProductPublishOperateService bankFinancialProductPublishOperateService;
	
	@Autowired
	private IBankFinancialProductDailyService bankFinancialProductDailyService;
	
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
	 * ???????????????????????????????????? 
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
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)//??????
	@ActionParam(key = "stage", message="??????", type = DataType.STRING)//??????
	@ActionParam(key = "income", message="???????????????", type = DataType.STRING)//??????
	@ActionParam(key = "term", message="??????", type = DataType.STRING)//??????
	@ActionParam(key = "type", message="??????", type = DataType.STRING)//??????
	@ActionParam(key = "balanceType", message="????????????", type = DataType.STRING)//????????????
	@ActionParam(key = "custodian", message="????????????", type = DataType.STRING)//????????????
	@ActionParam(key = "riskLevel", message="????????????", type = DataType.STRING)//????????????
	@ActionParam(key = "redeem", message="????????????", type = DataType.STRING)//????????????
	@ActionParam(key = "investType", message="????????????", type = DataType.STRING)//????????????
	@ActionParam(key = "invested", message="????????????", type = DataType.STRING)//?????????
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status,String stage, String income, String term, String type, String balanceType, 
			String riskLevel, String custodian, String redeem,String investType, String invested, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("investType", investType);
		searchMap.put("invested", invested);
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
		if(!"all".equals(balanceType)){
			searchMap.put("balanceType", balanceType);
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
		
		//????????????????????????????????????????????????
		Integer totalResultCount = bankFinancialProductPublishService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = bankFinancialProductPublishService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductPublish.class);
		//???????????????????????????List
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
						bfppvo.setCustodianName("?????????");
					}
				}else{
					bfppvo.setCustodianName("?????????");
				}
				if(bfpp.getArea()!=null){
					BkArea ba = areaService.get(bfpp.getArea());
					if(ba != null){
						bfppvo.setArea(ba.getName());
					}else{
						bfppvo.setArea("?????????");
					}
				}else{
					bfppvo.setArea("?????????");
				}
				Map<String,String> search = new HashMap<String, String>();
				search.put("bankFinancialProduct", bfpp.getBankFinancialProduct());
				
				List<Entity> networkList = bankFinancialProductDailyService.getListForPage(search, 0, 1, "statistime desc", BankFinancialProductDaily.class);
				if(networkList.size()>0){
					BankFinancialProductDaily bfpd = (BankFinancialProductDaily) networkList.get(0);
					bfppvo.setNetWorth(bfpd.getNetValue().setScale(4, BigDecimal.ROUND_HALF_UP).toString());
					bfppvo.setNetWorthTime(Utlity.timeSpanToDateString(bfpd.getStatistime()));
				}else{
					bfppvo.setNetWorth("?????????");
				}
				listVO.add(bfppvo);
			}
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = bankFinancialProductPublishService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/balanceList", method = RequestMethod.GET)
	@ActionParam(key = "invested", message="????????????", type = DataType.STRING)//?????????
	@ResponseBody
	public Result balanceList(String invested) {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("invested", invested);
		List<Entity> list = bankFinancialProductPublishService.getStageList(searchMap, StstusCountVO.class);
		List<StstusCountVO> dataList = new ArrayList<StstusCountVO>();
		
		BigInteger balanceCount = BigInteger.ZERO;
		for(Entity e : list){
			StstusCountVO scvo = (StstusCountVO) e;
			if(BankFinancialProductPublishStage.FINISHED.equals(scvo.getStatus())){
				dataList.add(scvo);
			}else if(BankFinancialProductPublishStage.BALANCE.equals(scvo.getStatus()) || BankFinancialProductPublishStage.PROFIT.equals(scvo.getStatus())){
				balanceCount = balanceCount.add(scvo.getCount());
			}
		}
		
		StstusCountVO balanceVO = new StstusCountVO(BankFinancialProductPublishStage.BALANCE ,balanceCount);
		dataList.add(balanceVO);
		
		return ResultManager.createDataResult(dataList,list.size());
	}
	
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/stageList", method = RequestMethod.GET)
	@ResponseBody
	public Result stageList() {
		List<Entity> list = bankFinancialProductPublishService.getStageList(new HashMap<String, String>(), StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//????????????????????????
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if (bfpp == null) {
			return ResultManager.createFailResult("????????????????????????");
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
			bfppvo.setDocumentCN("?????????");
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
				bfppvo.setCustodianCN("?????????");
				bfppvo.setCustodianLogo("");
			}
			
		}else{
			bfppvo.setCustodianCN("?????????");
			bfppvo.setCustodianLogo("");
		}
		
		if(bfpp.getArea() != null && !"".equals(bfpp.getArea())){
			BkArea area = areaService.get(bfpp.getArea());
			if(area != null){
				bfppvo.setAreaCN(area.getName());
			} else {
				bfppvo.setAreaCN("?????????");
			}
		}else{
			bfppvo.setAreaCN("?????????");
		}
		if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
			BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
			if(operator != null){
				bfppvo.setCreatorName(operator.getRealname());
			} else {
				bfppvo.setCreatorName("?????????");
			}
			
		}else{
			bfppvo.setCreatorName("?????????");
		}
		return ResultManager.createDataResult(bfppvo);
	}
	
	/**
	 * ??????????????????????????????
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
	 * @param collectAmount
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
	@ActionParam(key = "bankFinancialProduct", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "custodian", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "url", message="url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "series", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "shortname", message="????????????", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "scode", message="????????????", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "type", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "target", message="????????????", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", message="?????????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.NUMBER)
	@ActionParam(key = "collectAmount", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "paymentType", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyType", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "riskLevel", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "minAnnualizedReturnRate", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "area", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "flagFlexible", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagPurchase", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagRedemption", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "collectStarttime", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "collectEndtime", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "recordDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "valueDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "maturityDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "term", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmount", message="??????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmountAdd", message="??????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "maxInvestAmount", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "subscribeFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "purchaseFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "redemingFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "managementFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "custodyFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "networkFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investScope", message="????????????", type = DataType.STRING, required = true, maxLength = 5000)
	@ActionParam(key = "revenueFeature", message="????????????", type = DataType.STRING, required = true,maxLength = 5000)
	@ActionParam(key = "remark", message="????????????", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "guaranteeStatus", message="??????????????????", type = DataType.STRING)
	@ResponseBody
	public Result add(String bankFinancialProduct, String name, String series, String scode, String shortname, String type, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount, BigDecimal collectAmount,String paymentType, String url, 
			String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, String flagRedemption, String flagFlexible, BigDecimal minAnnualizedReturnRate, 
			String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, BigDecimal purchaseFee, BigDecimal redemingFee, BigDecimal managementFee, 
			BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, String valueDate, String maturityDate, String investScope, 
			String revenueFeature, String remark, String document) throws ParserException {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//????????????
		BankFinancialProductPublish bfpp = new BankFinancialProductPublish();
		BankFinancialProduct bfp = bankFinancialProductService.get(bankFinancialProduct);
		if(bfp != null){
			bfpp.setBankFinancialProduct(bankFinancialProduct);
		}else{
			return ResultManager.createFailResult("???????????????????????????????????????????????????");
		}
		bfpp.setUuid(UUID.randomUUID().toString());
		if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByName(name,null)){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		bfpp.setName(name);
		bfpp.setSeries(series);
		if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByScode(scode,null)){
			return ResultManager.createFailResult("??????????????????????????????");
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
		bfpp.setCollectAmount(collectAmount.multiply(BigDecimal.valueOf(10000)));
		Bank bank = bankService.get(custodian);
		if(bank != null){
			bfpp.setCustodian(custodian);
		}else{
			return ResultManager.createFailResult("??????????????????????????????");
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
				return ResultManager.createFailResult("???????????????????????????");
			}
		}
		bfpp.setSubscribeFee(subscribeFee);
		bfpp.setPurchaseFee(purchaseFee);
		bfpp.setRedemingFee(redemingFee);
		bfpp.setManagementFee(managementFee);
		bfpp.setNetworkFee(networkFee);
		bfpp.setCustodyFee(custodyFee);
		bfpp.setCollectStarttime(Timestamp.valueOf(collectStarttime));
		bfpp.setCollectEndtime(Timestamp.valueOf(collectEndtime));
		bfpp.setValueDate(Timestamp.valueOf(Utlity.getFullTime(valueDate)));
		bfpp.setMaturityDate(Timestamp.valueOf(Utlity.getFullTime(maturityDate)));
		bfpp.setTerm(term);
		bfpp.setInvestScope(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(investScope)));
		bfpp.setRevenueFeature(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(revenueFeature)));
		bfpp.setRemark(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(remark)));
		
		bfpp.setAccountBalance(BigDecimal.ZERO);
		bfpp.setTotalRedeem(BigDecimal.ZERO);
		bfpp.setTotalReturn(BigDecimal.ZERO);
		bfpp.setInvestment(BigDecimal.ZERO);
		bfpp.setRealCollect(BigDecimal.ZERO);
		bfpp.setRealReturn(BigDecimal.ZERO);
		bfpp.setRealReturnRate(BigDecimal.ZERO);
		
		Resource resource = resourceService.get(document);
		if(resource != null){
			resource.setFilename("???"+bank.getName()+"???"+bfpp.getName()+"("+bfpp.getScode()+")");
			resource = resourceService.updateName(resource);
			if(resource == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			bfpp.setDocument(document);
		}
		
		//?????????????????????
		BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
		bfppo.setUuid(UUID.randomUUID().toString());
		bfppo.setType(BankFinancialProductPublishOperateType.ADD);
		bfppo.setValue(JSONUtils.obj2json(bfpp));
		bfppo.setStatus(BankFinancialProductPublishOperateStatus.DRAFT);//??????
		bfppo.setCreator(currentOperator.getUuid());
		bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		bankFinancialProductPublishOperateService.insert(bfppo);
		return ResultManager.createDataResult(bfpp, "???????????????????????????");
	
	}
	
	/**
	 * ??????????????????????????????
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
	 * @param collectAmount
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
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "custodian", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "url", message="url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "series", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "shortname", message="????????????", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "scode", message="????????????", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "type", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "target", message="????????????", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", message="?????????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.NUMBER)
	@ActionParam(key = "collectAmount", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "paymentType", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyType", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "riskLevel", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "minAnnualizedReturnRate", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "area", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "flagFlexible", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagPurchase", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagRedemption", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "collectStarttime", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "collectEndtime", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "recordDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "valueDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "maturityDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "term", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmount", message="??????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmountAdd", message="??????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "maxInvestAmount", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "subscribeFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "purchaseFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "redemingFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "managementFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "custodyFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "networkFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investScope", message="????????????", type = DataType.STRING, required = true, maxLength = 5000)
	@ActionParam(key = "revenueFeature", message="????????????", type = DataType.STRING, required = true,maxLength = 5000)
	@ActionParam(key = "remark", message="????????????", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "guaranteeStatus", message="??????????????????", type = DataType.STRING)
	@ResponseBody
	public Result edit(String uuid, String name, String series, String scode, String shortname, String type, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount, BigDecimal collectAmount, String paymentType, 
			String url, String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, String flagRedemption, String flagFlexible, 
			BigDecimal minAnnualizedReturnRate, String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, BigDecimal purchaseFee, 
			BigDecimal redemingFee, BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, 
			String valueDate, String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if (bfpp != null) {
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByName(name,uuid)){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			bfpp.setName(name);
			bfpp.setSeries(series);
			if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByScode(scode,uuid)){
				return ResultManager.createFailResult("??????????????????????????????");
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
			bfpp.setCollectAmount(collectAmount.multiply(BigDecimal.valueOf(10000)));
			Bank bank = bankService.get(custodian);
			if(bank != null){
				bfpp.setCustodian(custodian);
			}else{
				return ResultManager.createFailResult("??????????????????????????????");
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
					return ResultManager.createFailResult("???????????????????????????");
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
				resource.setFilename("???"+bank.getName()+"???"+bfpp.getName()+"("+bfpp.getScode()+")");
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				bfpp.setDocument(document);
			}
			
			//?????????????????????
			BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
			bfppo.setUuid(UUID.randomUUID().toString());
			bfppo.setBankFinancialProductPublish(bfpp.getUuid());
			bfppo.setType(BankFinancialProductPublishOperateType.EDIT);
			bfppo.setValue(JSONUtils.obj2json(bfpp));
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.DRAFT);
			bfppo.setCreator(currentOperator.getUuid());
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.insert(bfppo);
			return ResultManager.createDataResult(bfpp, "???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ?????????????????????????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/synchro", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result synchro(String uuid) {
		
		//????????????????????????
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			BankFinancialProduct bfp = null;
			if(bfpp.getBankFinancialProduct() != null){
				bfp = bankFinancialProductService.get(bfpp.getBankFinancialProduct());
			}
			//?????????????????????????????????????????????????????????
			if(bfp != null){
				//??????????????????
				bfpp.setName(bfp.getName());
				bfpp.setUrl(bfp.getUrl());
				bfpp.setCustodian(bfp.getCustodian());
				
				//??????????????????
				bfpp.setSeries(bfp.getSeries());
				bfpp.setShortname(bfp.getShortname());
				bfpp.setScode(bfp.getScode());
				bfpp.setTotalAmount(bfp.getTotalAmount());
				bfpp.setType(bfp.getType());
				bfpp.setTargetAnnualizedReturnRate(bfp.getTargetAnnualizedReturnRate());
				bfpp.setCurrencyType(bfp.getCurrencyType());
				bfpp.setMinAnnualizedReturnRate(bfp.getMinAnnualizedReturnRate());
				bfpp.setRiskLevel(bfp.getRiskLevel());
				bfpp.setPaymentType(bfp.getPaymentType());
				bfpp.setTarget(bfp.getTarget());
				bfpp.setArea(bfp.getArea());
				
				//??????????????????
				bfpp.setFlagFlexible(bfp.getFlagFlexible());
				bfpp.setValueDate(bfp.getValueDate());
				bfpp.setMaturityDate(bfp.getMaturityDate());
				bfpp.setTerm(bfp.getTerm());
				
				this.bankFinancialProductPublishService.update(bfpp);
				return ResultManager.createSuccessResult("?????????????????????");
			} else {
				return ResultManager.createFailResult("??????????????????");
			}
			
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result exception(String uuid) {
		
		//????????????????????????
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//??????????????????
			BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
			bfppo.setUuid(UUID.randomUUID().toString());
			bfppo.setBankFinancialProductPublish(bfpp.getUuid());
			bfppo.setType(BankFinancialProductPublishOperateType.EXCEPTION);
			bfppo.setValue("");
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.UNCHECKED);
			bfppo.setCreator(currentOperator.getUuid());
			bfppo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.insert(bfppo);
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????--????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/collect", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result collect(String uuid) {
		
		//????????????????????????
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//??????????????????
			BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
			bfppo.setUuid(UUID.randomUUID().toString());
			bfppo.setBankFinancialProductPublish(bfpp.getUuid());
			bfppo.setType(BankFinancialProductPublishOperateType.COLLECT);
			bfppo.setValue("");
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.UNCHECKED);
			bfppo.setCreator(currentOperator.getUuid());
			bfppo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.insert(bfppo);
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????--????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/uninvest", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result uninvest(String uuid) {
		
		//????????????????????????
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//??????????????????
			BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
			bfppo.setUuid(UUID.randomUUID().toString());
			bfppo.setBankFinancialProductPublish(bfpp.getUuid());
			bfppo.setType(BankFinancialProductPublishOperateType.UNINVEST);
			bfppo.setValue("");
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.UNCHECKED);
			bfppo.setCreator(currentOperator.getUuid());
			bfppo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.insert(bfppo);
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		
		//????????????????????????
		BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(uuid);
		if(bfpp != null){
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//??????????????????
			BankFinancialProductPublishOperate bfppo = new BankFinancialProductPublishOperate();
			bfppo.setUuid(UUID.randomUUID().toString());
			bfppo.setBankFinancialProductPublish(bfpp.getUuid());
			bfppo.setType(BankFinancialProductPublishOperateType.DELETE);
			bfppo.setValue("");
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.UNCHECKED);
			bfppo.setCreator(currentOperator.getUuid());
			bfppo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.insert(bfppo);
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {
		//????????????????????????
		BankFinancialProductPublishOperate bfppo = bankFinancialProductPublishOperateService.get(uuid);
		if (bfppo == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		BankFinancialProductPublishOperateDetailVO bfppodVO = new BankFinancialProductPublishOperateDetailVO(bfppo);
		BkOperator creator = this.bkOperatorService.get(bfppodVO.getCreator());
		if(creator != null){
			bfppodVO.setCreatorName(creator.getRealname());
		}
		
		if(!BankFinancialProductPublishOperateType.ADD.equals(bfppo.getType())){
			if(BankFinancialProductPublishOperateType.EDIT.equals(bfppo.getType()) && BankFinancialProductPublishOperateStatus.CHECKED.equals(bfppo.getStatus()) 
					&& bfppo.getOld() != null && !"".equals(bfppo.getOld())){
				BankFinancialProductPublish bfpp = JSONUtils.json2obj(bfppo.getOld(), BankFinancialProductPublish.class);	
				BankFinancialProductPublishDetailsVO bfppvo = new BankFinancialProductPublishDetailsVO(bfpp);
				if(bfpp.getDocument() != null && !"".equals(bfpp.getDocument())){
					Resource res = this.resourceService.get(bfpp.getDocument());
					if(res != null){
						bfppvo.setDocumentCN(res.getFilename());
						bfppvo.setDocumentType(res.getFiletype());
						bfppvo.setDocumentURL(res.getUrl());
					}
				}else{
					bfppvo.setDocumentCN("?????????");
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
						bfppvo.setCustodianCN("?????????");
						bfppvo.setCustodianLogo("");
					}
					
				}else{
					bfppvo.setCustodianCN("?????????");
					bfppvo.setCustodianLogo("");
				}
				
				if(bfpp.getArea() != null && !"".equals(bfpp.getArea())){
					BkArea area = this.areaService.get(bfpp.getArea());
					if(area != null){
						bfppvo.setAreaCN(area.getName());
					} else {
						bfppvo.setAreaCN("?????????");
					}
				}else{
					bfppvo.setAreaCN("?????????");
				}
				
				if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
					BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
					if(operator != null){
						bfppvo.setCreatorName(operator.getRealname());
					} else {
						bfppvo.setCreatorName("???");
					}
					
				}else{
					bfppvo.setCreatorName("???");
				}
				bfppodVO.setOldData(bfppvo);
			}else{
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
					bfppvo.setDocumentCN("?????????");
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
						bfppvo.setCustodianCN("?????????");
						bfppvo.setCustodianLogo("");
					}
					
				}else{
					bfppvo.setCustodianCN("?????????");
					bfppvo.setCustodianLogo("");
				}
				
				if(bfpp.getArea() != null && !"".equals(bfpp.getArea())){
					BkArea area = this.areaService.get(bfpp.getArea());
					if(area != null){
						bfppvo.setAreaCN(area.getName());
					} else {
						bfppvo.setAreaCN("?????????");
					}
				}else{
					bfppvo.setAreaCN("?????????");
				}
				
				if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
					BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
					if(operator != null){
						bfppvo.setCreatorName(operator.getRealname());
					} else {
						bfppvo.setCreatorName("???");
					}
					
				}else{
					bfppvo.setCreatorName("???");
				}
				bfppodVO.setOldData(bfppvo);
			}
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
				bfppvo.setDocumentCN("?????????");
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
					bfppvo.setCustodianCN("?????????");
					bfppvo.setCustodianLogo("");
				}
				
			}else{
				bfppvo.setCustodianCN("?????????");
				bfppvo.setCustodianLogo("");
			}
			
			if(bfpp.getArea() != null && !"".equals(bfpp.getArea())){
				BkArea area = this.areaService.get(bfpp.getArea());
				if(area != null){
					bfppvo.setAreaCN(area.getName());
				} else {
					bfppvo.setAreaCN("?????????");
				}
			}else{
				bfppvo.setAreaCN("?????????");
			}
			
			if(bfpp.getCreator() != null && !"".equals(bfpp.getCreator())){
				BkOperator operator = this.bkOperatorService.get(bfpp.getCreator());
				if(operator != null){
					bfppvo.setCreatorName(operator.getRealname());
				} else {
					bfppvo.setCreatorName("???");
				}
				
			}else{
				bfppvo.setCreatorName("???");
			}
			bfppodVO.setNewData(bfppvo);
		}
		return ResultManager.createDataResult(bfppodVO);
	}
	
	/**
	 * ????????????????????????????????????
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
	 * @param collectAmount
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
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "custodian", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "url", message="url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "series", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "shortname", message="????????????", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "scode", message="????????????", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "type", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "target", message="????????????", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", message="?????????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalAmount", message="????????????", type = DataType.NUMBER)
	@ActionParam(key = "collectAmount", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "paymentType", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyType", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "riskLevel", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "minAnnualizedReturnRate", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "area", message="??????", type = DataType.STRING, required = true)
	@ActionParam(key = "flagFlexible", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagPurchase", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagRedemption", message="????????????", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "collectStarttime", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "collectEndtime", message="??????????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "recordDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "valueDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "maturityDate", message="?????????", type = DataType.DATE, required = true)
	@ActionParam(key = "term", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmount", message="??????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmountAdd", message="??????????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "maxInvestAmount", message="??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "subscribeFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "purchaseFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "redemingFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "managementFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "custodyFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "networkFee", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investScope", message="????????????", type = DataType.STRING, required = true, maxLength = 5000)
	@ActionParam(key = "revenueFeature", message="????????????", type = DataType.STRING, required = true,maxLength = 5000)
	@ActionParam(key = "remark", message="????????????", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", message="???????????????", type = DataType.STRING)
	@ActionParam(key = "guaranteeStatus", message="??????????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateEdit(String uuid, String name, String series, String scode, String shortname, String type, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount, BigDecimal collectAmount, String paymentType, 
			String url, String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, String flagRedemption, String flagFlexible, 
			BigDecimal minAnnualizedReturnRate, String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, BigDecimal purchaseFee, 
			BigDecimal redemingFee, BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, 
			String valueDate, String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		BankFinancialProductPublishOperate bfppo = bankFinancialProductPublishOperateService.get(uuid);
		if (bfppo != null) {
			BankFinancialProductPublish bfpp = JSONUtils.json2obj(bfppo.getValue(), BankFinancialProductPublish.class);
			
			if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByName(name,bfppo.getBankFinancialProductPublish())){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			bfpp.setName(name);
			bfpp.setSeries(series);
			if(bankFinancialProductPublishService.isExistBankFinancialProductPublishByScode(scode,bfppo.getBankFinancialProductPublish())){
				return ResultManager.createFailResult("??????????????????????????????");
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
			bfpp.setCollectAmount(collectAmount.multiply(BigDecimal.valueOf(10000)));
			Bank bank = bankService.get(custodian);
			if(bank != null){
				bfpp.setCustodian(custodian);
			}else{
				return ResultManager.createFailResult("??????????????????????????????");
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
					return ResultManager.createFailResult("???????????????????????????");
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
				resource.setFilename("???"+bank.getName()+"???"+bfpp.getName()+"("+bfpp.getScode()+")");
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				bfpp.setDocument(document);
			}
			
			//?????????????????????
			bfppo.setValue(JSONUtils.obj2json(bfpp));
			bfppo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductPublishOperateService.update(bfppo);
			return ResultManager.createDataResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//????????????????????????????????????
		BankFinancialProductPublishOperate bfppo = bankFinancialProductPublishOperateService.get(uuid);
		if(bfppo != null){
			if(!BankFinancialProductPublishOperateStatus.DRAFT.equals(bfppo.getStatus()) && !BankFinancialProductPublishOperateStatus.UNPASSED.equals(bfppo.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			bfppo.setStatus(BankFinancialProductPublishOperateStatus.DELETED);
			bankFinancialProductPublishOperateService.update(bfppo);
			return ResultManager.createSuccessResult("???????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		//??????????????????????????????????????????????????????
		Integer totalResultCount = bankFinancialProductPublishOperateService.getCount(searchMap);
		//???????????????????????????????????????????????????
		List<Entity> list = bankFinancialProductPublishOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductPublishOperate.class);
		
		//???????????????????????????List
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
							bfppoVO.setCustodianName("?????????");
						}
					}else{
						bfppoVO.setCustodianName("?????????");
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
								bfppoVO.setCustodianName("?????????");
							}
						}else{
							bfppoVO.setCustodianName("?????????");
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
	 * ???????????????????????????????????????????????????
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
//		if(!"all".equals(status)){
//			searchMap.put("status", status);
//		}
		searchMap.put("status", status);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		//??????????????????????????????????????????????????????
		Integer totalResultCount = bankFinancialProductPublishOperateService.getCount(searchMap);
		//???????????????????????????????????????????????????
		List<Entity> list = bankFinancialProductPublishOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductPublishOperate.class);
		
		//???????????????????????????List
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
							bfppoVO.setCustodianName("?????????");
						}
					}else{
						bfppoVO.setCustodianName("?????????");
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
								bfppoVO.setCustodianName("?????????");
							}
						}else{
							bfppoVO.setCustodianName("?????????");
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
	 *???????????????-????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//????????????????????????????????????
		BankFinancialProductPublishOperate bfpo = bankFinancialProductPublishOperateService.get(uuid);
		if(bfpo != null){
			if(BankFinancialProductPublishOperateStatus.CHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			if(BankFinancialProductPublishOperateType.EDIT.equals(bfpo.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bankFinancialProductPublish", bfpo.getBankFinancialProductPublish());
				searchMap.put("type", bfpo.getType());
				searchMap.put("status", BankFinancialProductPublishOperateStatus.UNCHECKED);
				
				Integer count = this.bankFinancialProductPublishOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("??????????????????????????????????????????????????????");
				}
			}
			
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(BankFinancialProductPublishOperateStatus.UNCHECKED);
			bankFinancialProductPublishOperateService.update(bfpo);
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="????????????", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!BankFinancialProductPublishOperateStatus.CHECKED.equals(status) && !BankFinancialProductPublishOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//????????????????????????
		BankFinancialProductPublishOperate bfppo = bankFinancialProductPublishOperateService.get(uuid);
		if(bfppo != null){
			if(!BankFinancialProductPublishOperateStatus.UNCHECKED.equals(bfppo.getStatus())){
				return ResultManager.createFailResult("???????????????????????????");
			}
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(bfppo.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("???????????????????????????????????????");
			}
			
			bfppo.setChecker(currentOperator.getUuid());
			bfppo.setChecktime(new Timestamp(System.currentTimeMillis()));
			bfppo.setStatus(status);
			bfppo.setReason(reason);
			try {
				bankFinancialProductPublishOperateService.check(bfppo);
			} catch (TransactionException e) {
				super.flushAll();
				return ResultManager.createFailResult(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				super.flushAll();
				return ResultManager.createFailResult("?????????????????????");
			}
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/operateStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		//????????????????????????
		List<Entity> list = bankFinancialProductPublishOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//????????????????????????
		List<Entity> list = bankFinancialProductPublishOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateTypeList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
		
		List<Entity> list = bankFinancialProductPublishOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
//		if(!"all".equals(status)){
//			searchMap.put("status", status);
//		}
		searchMap.put("status", status);
		
		List<Entity> list = bankFinancialProductPublishOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
