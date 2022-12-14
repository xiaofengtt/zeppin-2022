/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductDailyVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductDetailsVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductOperateDailyVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductOperateNetvalueVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductDaily;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductOperate.BankFinancialProductOperateType;
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
 * ????????????????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/bankFinancialProduct")
public class BankFinancialProductController extends BaseController {

	@Autowired
	private IBankFinancialProductService bankFinancialProductService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IBankFinancialProductDailyService bankFinancialProductDailyService;
	
	@Autowired
	private IBankFinancialProductOperateService bankFinancialProductOperateService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IBkAreaService areaService;
	
	@Autowired
	private IBankService bankService;
	
	/**
	 * ?????????????????????????????????????????? 
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
	@ActionParam(key = "income", message="????????????", type = DataType.STRING)//??????
	@ActionParam(key = "term", message="??????", type = DataType.STRING)//??????
	@ActionParam(key = "type", message="??????", type = DataType.STRING)//??????
	@ActionParam(key = "custodian", message="????????????", type = DataType.STRING)//????????????
	@ActionParam(key = "riskLevel", message="????????????", type = DataType.STRING)//????????????
	@ActionParam(key = "isRedeem", message="???????????????", type = DataType.STRING)//???????????????
	@ActionParam(key = "redeem", message="????????????", type = DataType.STRING)//????????????
	@ActionParam(key = "invested", message="????????????", type = DataType.STRING)//?????????
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status,String stage, String income, String term, String type, String riskLevel, 
			String custodian, String redeem, String invested, String isRedeem, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
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
		if(!"all".equals(riskLevel)){
			searchMap.put("riskLevel", riskLevel);
		}
		if(!"all".equals(custodian)){
			searchMap.put("custodian", custodian);
		}
		if(!"all".equals(redeem)){
			searchMap.put("redeem", redeem);
		}
		if(!"all".equals(isRedeem)){
			searchMap.put("isRedeem", isRedeem);
		}
		//??????????????????????????????????????????????????????
		Integer totalResultCount = bankFinancialProductService.getCount(searchMap);
		//???????????????????????????????????????????????????
		List<Entity> list = bankFinancialProductService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProduct.class);
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				BankFinancialProduct bfp = (BankFinancialProduct) entity;
				BankFinancialProductVO bfpvo = new BankFinancialProductVO(bfp);
				bfpvo.setCollectStarttime(bfpvo.getCollectStarttime().replace("-", "."));
				bfpvo.setCollectEndtime(bfpvo.getCollectEndtime().replace("-", "."));
				BkOperator bko = this.bkOperatorService.get(bfp.getCreator());
				if(bko != null){
					bfpvo.setCreator(bko.getRealname());
				}
				if(bfp.getCustodian()!=null && !"".equals(bfp.getCustodian())){
					Bank bank = this.bankService.get(bfp.getCustodian());
					if(bank != null){
						bfpvo.setCustodianName(bank.getName());
					}else{
						bfpvo.setCustodianName("?????????");
					}
				}else{
					bfpvo.setCustodianName("?????????");
				}
				if(bfp.getArea()!=null){
					BkArea ba = this.areaService.get(bfp.getArea());
					if(ba != null){
						bfpvo.setArea(ba.getName());
					}else{
						bfpvo.setArea("?????????");
					}
				}else{
					bfpvo.setArea("?????????");
				}
				Map<String,String> search = new HashMap<String, String>();
				search.put("bankFinancialProduct", bfp.getUuid());
				
				List<Entity> networkList = bankFinancialProductDailyService.getListForPage(search, 0, 1, "statistime desc", BankFinancialProductDaily.class);
				if(networkList.size()>0){
					BankFinancialProductDaily bfpd = (BankFinancialProductDaily) networkList.get(0);
					bfpvo.setNetWorth(bfpd.getNetValue().setScale(4, BigDecimal.ROUND_HALF_UP).toString());
					bfpvo.setNetWorthTime(Utlity.timeSpanToDateString(bfpd.getStatistime()));
				}else{
					bfpvo.setNetWorth("?????????");
				}
				search.put("status", BankFinancialProductStatus.CHECKED);
				Integer count = bankFinancialProductPublishService.getCount(search);
				if(count != null && count > 0){
					bfpvo.setIsPublish(true);
				}else{
					bfpvo.setIsPublish(false);
				}
				listVO.add(bfpvo);
			}
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/publishList", method = RequestMethod.GET)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result publishList(String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		//??????????????????????????????????????????????????????
		Integer totalResultCount = bankFinancialProductService.getPublishCount(searchMap);
		//???????????????????????????????????????????????????
		List<Entity> list = bankFinancialProductService.getPublishListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProduct.class);
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				BankFinancialProduct bfp = (BankFinancialProduct) entity;
				BankFinancialProductVO bfpvo = new BankFinancialProductVO(bfp);
				bfpvo.setCollectStarttime(bfpvo.getCollectStarttime().replace("-", "."));
				bfpvo.setCollectEndtime(bfpvo.getCollectEndtime().replace("-", "."));
				BkOperator bko = this.bkOperatorService.get(bfp.getCreator());
				if(bko != null){
					bfpvo.setCreator(bko.getRealname());
				}
				if(bfp.getCustodian()!=null && !"".equals(bfp.getCustodian())){
					Bank bank = this.bankService.get(bfp.getCustodian());
					if(bank != null){
						bfpvo.setCustodianName(bank.getName());
					}else{
						bfpvo.setCustodianName("?????????");
					}
				}else{
					bfpvo.setCustodianName("?????????");
				}
				if(bfp.getArea()!=null){
					BkArea ba = this.areaService.get(bfp.getArea());
					if(ba != null){
						bfpvo.setArea(ba.getName());
					}else{
						bfpvo.setArea("?????????");
					}
				}else{
					bfpvo.setArea("?????????");
				}
				Map<String,String> search = new HashMap<String, String>();
				search.put("bankFinancialProduct", bfp.getUuid());
				
				List<Entity> networkList = bankFinancialProductDailyService.getListForPage(search, 0, 1, "statistime desc", BankFinancialProductDaily.class);
				if(networkList.size()>0){
					BankFinancialProductDaily bfpd = (BankFinancialProductDaily) networkList.get(0);
					bfpvo.setNetWorth(bfpd.getNetValue().setScale(4, BigDecimal.ROUND_HALF_UP).toString());
					bfpvo.setNetWorthTime(Utlity.timeSpanToDateString(bfpd.getStatistime()));
				}else{
					bfpvo.setNetWorth("?????????");
				}
				search.put("status", BankFinancialProductStatus.CHECKED);
				Integer count = bankFinancialProductPublishService.getCount(search);
				if(count != null && count > 0){
					bfpvo.setIsPublish(true);
				}else{
					bfpvo.setIsPublish(false);
				}
				listVO.add(bfpvo);
			}
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = bankFinancialProductService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/stageList", method = RequestMethod.GET)
	@ActionParam(key = "invested", message="????????????", type = DataType.STRING)//?????????
	@ResponseBody
	public Result stageList(String invested) {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("invested", invested);
		List<Entity> list = bankFinancialProductService.getStageList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/investList", method = RequestMethod.GET)
	@ResponseBody
	public Result investList() {
		List<Entity> list = bankFinancialProductService.getStageList(new HashMap<String, String>(), StstusCountVO.class);
		List<StstusCountVO> dataList = new ArrayList<StstusCountVO>();
		
		BigInteger collectCount = BigInteger.ZERO;
		for(Entity e : list){
			StstusCountVO scvo = (StstusCountVO) e;
			if(BankFinancialProductStage.FINISHED.equals(scvo.getStatus()) || BankFinancialProductStage.INCOME.equals(scvo.getStatus())){
				dataList.add(scvo);
			}else if(BankFinancialProductStage.COLLECT.equals(scvo.getStatus()) || BankFinancialProductStage.UNSTART.equals(scvo.getStatus())){
				collectCount = collectCount.add(scvo.getCount());
			}
		}
		
		StstusCountVO collectVO = new StstusCountVO(BankFinancialProductStage.COLLECT ,collectCount);
		dataList.add(collectVO);
		
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//??????????????????????????????
		BankFinancialProduct bankFinancialProduct = bankFinancialProductService.get(uuid);
		if (bankFinancialProduct == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		BankFinancialProductDetailsVO bfpvo = new BankFinancialProductDetailsVO(bankFinancialProduct);
		if(bankFinancialProduct.getDocument() != null && !"".equals(bankFinancialProduct.getDocument())){
			Resource res = this.resourceService.get(bankFinancialProduct.getDocument());
			if(res != null){
				bfpvo.setDocumentCN(res.getFilename());
				bfpvo.setDocumentType(res.getFiletype());
				bfpvo.setDocumentURL(res.getUrl());
			}
		}else{
			bfpvo.setDocumentCN("?????????");
			bfpvo.setDocumentType("");
			bfpvo.setDocumentURL("");
		}
		
		if(bankFinancialProduct.getCustodian() != null && !"".equals(bankFinancialProduct.getCustodian())){
			Bank bank = this.bankService.get(bankFinancialProduct.getCustodian());
			if(bank != null){
				bfpvo.setCustodianCN(bank.getName());
				if(bank.getLogo() != null && !"".equals(bank.getLogo())){
					Resource resource = resourceService.get(bank.getLogo());
					if (resource != null) {
						bfpvo.setCustodianLogo(resource.getUrl());
					}else{
						bfpvo.setCustodianLogo("");
					}
				}else{
					bfpvo.setCustodianLogo("");
				}
			} else {
				bfpvo.setCustodianCN("?????????");
				bfpvo.setCustodianLogo("");
			}
			
		}else{
			bfpvo.setCustodianCN("?????????");
			bfpvo.setCustodianLogo("");
		}
		
		if(bankFinancialProduct.getArea() != null && !"".equals(bankFinancialProduct.getArea())){
			BkArea area = this.areaService.get(bankFinancialProduct.getArea());
			if(area != null){
				bfpvo.setAreaCN(area.getName());
			} else {
				bfpvo.setAreaCN("?????????");
			}
		}else{
			bfpvo.setAreaCN("?????????");
		}
		
		if(bankFinancialProduct.getCreator() != null && !"".equals(bankFinancialProduct.getCreator())){
			BkOperator operator = this.bkOperatorService.get(bankFinancialProduct.getCreator());
			if(operator != null){
				bfpvo.setCreatorName(operator.getRealname());
			} else {
				bfpvo.setCreatorName("???");
			}
			
		}else{
			bfpvo.setCreatorName("???");
		}
		
		return ResultManager.createDataResult(bfpvo);
	}
	
	/**
	 * ????????????????????????????????????
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
	@ResponseBody
	public Result add(String name, String series, String scode, String shortname, String type, String url, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount,String paymentType, String style, 
			String creditLevel, String flagCloseend, String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, 
			String flagRedemption, String flagFlexible, BigDecimal minAnnualizedReturnRate, String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, 
			BigDecimal purchaseFee, BigDecimal redemingFee, BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, 
			String valueDate, String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		BankFinancialProduct bfp = new BankFinancialProduct();
		bfp.setUuid(UUID.randomUUID().toString());
		bfp.setStage(BankFinancialProductStage.UNSTART);
		bfp.setStatus(BankFinancialProductStatus.CHECKED);
		bfp.setCreator(currentOperator.getUuid());
		bfp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		if(bankFinancialProductService.isExistBankFinancialProductByName(name,null)){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		bfp.setName(name);
		bfp.setSeries(series);
		if(bankFinancialProductService.isExistBankFinancialProductByScode(scode,null)){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		bfp.setScode(scode);
		bfp.setShortname(shortname);
		bfp.setType(type);
		bfp.setUrl(url);
		bfp.setTarget(target);
		bfp.setTargetAnnualizedReturnRate(targetAnnualizedReturnRate);
		bfp.setMinInvestAmount(minInvestAmount);
		bfp.setMinInvestAmountAdd(minInvestAmountAdd);
		bfp.setMaxInvestAmount(maxInvestAmount);
		bfp.setTotalAmount(totalAmount.multiply(BigDecimal.valueOf(100000000)));
		Bank bank = bankService.get(custodian);
		if(bank != null){
			bfp.setCustodian(custodian);
		}else{
			return ResultManager.createFailResult("??????????????????????????????");
		}
		bfp.setStyle(style);
		bfp.setRiskLevel(riskLevel);
		bfp.setCreditLevel(creditLevel);
		bfp.setCurrencyType(currencyType);
		bfp.setGuaranteeStatus(guaranteeStatus);
		bfp.setFlagCloseend(Boolean.valueOf(flagCloseend));
		bfp.setFlagPurchase(Boolean.valueOf(flagPurchase));
		bfp.setFlagRedemption(Boolean.valueOf(flagRedemption));
		bfp.setFlagFlexible(Boolean.valueOf(flagFlexible));
		bfp.setMinAnnualizedReturnRate(minAnnualizedReturnRate);
		bfp.setPaymentType(paymentType);
		bfp.setRecordDate(Timestamp.valueOf(Utlity.getFullTime(recordDate)));
		if(area!=null && !area.equals("")){
			BkArea bkArea = this.areaService.get(area);
			if(bkArea != null){
				bfp.setArea(area);
			} else {
				return ResultManager.createFailResult("???????????????????????????");
			}
		}
		bfp.setSubscribeFee(subscribeFee);
		bfp.setPurchaseFee(purchaseFee);
		bfp.setRedemingFee(redemingFee);
		bfp.setManagementFee(managementFee);
		bfp.setCustodyFee(custodyFee);
		bfp.setNetworkFee(networkFee);
		bfp.setCollectStarttime(Timestamp.valueOf(collectStarttime));
		bfp.setCollectEndtime(Timestamp.valueOf(collectEndtime));
		bfp.setValueDate(Timestamp.valueOf(Utlity.getFullTime(valueDate)));
		bfp.setMaturityDate(Timestamp.valueOf(Utlity.getFullTime(maturityDate)));
		bfp.setTerm(term);
		bfp.setInvestScope(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(investScope)));
		bfp.setRevenueFeature(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(revenueFeature)));
		bfp.setRemark(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(remark)));
		
		bfp.setAccountBalance(BigDecimal.ZERO);
		bfp.setTotalRedeem(BigDecimal.ZERO);
		bfp.setTotalReturn(BigDecimal.ZERO);
		bfp.setInvestment(BigDecimal.ZERO);
		Resource resource = resourceService.get(document);
		if(resource != null){
			resource.setFilename("???"+bank.getName()+"???"+bfp.getName()+"("+bfp.getScode()+")");
			resource = resourceService.updateName(resource);
			if(resource == null){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			bfp.setDocument(document);
		}
		//?????????????????????
		BankFinancialProductOperate bfpo = new BankFinancialProductOperate();
		bfpo.setUuid(UUID.randomUUID().toString());
		bfpo.setType(BankFinancialProductOperateType.ADD);
		bfpo.setValue(JSONUtils.obj2json(bfp));
		bfpo.setStatus(BankFinancialProductOperateStatus.DRAFT);
		bfpo.setCreator(currentOperator.getUuid());
		bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		bankFinancialProductOperateService.insert(bfpo);
		return ResultManager.createDataResult(bfp, "???????????????????????????");
		
	}
	
	/**
	 * ????????????????????????????????????
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
	 * @param currencyType
	 * @param term
	 * @param custodian
	 * @param riskLevel
	 * @param flagPurchase
	 * @param flagRedemption
	 * @param flagFlexible
	 * @param minAnnualizedReturnRate
	 * @param recordDate
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
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
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
	@ResponseBody
	public Result edit(String uuid, String name, String series, String scode, String shortname, String type, String url, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount,String paymentType, String style, 
			String creditLevel, String flagCloseend, String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, 
			String flagRedemption, String flagFlexible, BigDecimal minAnnualizedReturnRate, String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, 
			BigDecimal purchaseFee, BigDecimal redemingFee, BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, 
			String valueDate, String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		BankFinancialProduct bfp = bankFinancialProductService.get(uuid);
		if (bfp != null) {
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			if(bankFinancialProductService.isExistBankFinancialProductByName(name,uuid)){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			bfp.setName(name);
			bfp.setSeries(series);
			if(bankFinancialProductService.isExistBankFinancialProductByScode(scode,uuid)){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			bfp.setScode(scode);
			bfp.setShortname(shortname);
			bfp.setType(type);
			bfp.setUrl(url);
			bfp.setTarget(target);
			bfp.setTargetAnnualizedReturnRate(targetAnnualizedReturnRate);
			bfp.setMinInvestAmount(minInvestAmount);
			bfp.setMinInvestAmountAdd(minInvestAmountAdd);
			bfp.setMaxInvestAmount(maxInvestAmount);
			bfp.setTotalAmount(totalAmount.multiply(BigDecimal.valueOf(100000000)));
			Bank bank = bankService.get(custodian);
			if(bank != null){
				bfp.setCustodian(custodian);
			}else{
				return ResultManager.createFailResult("??????????????????????????????");
			}
			bfp.setStyle(style);
			bfp.setRiskLevel(riskLevel);
			bfp.setCreditLevel(creditLevel);
			bfp.setCurrencyType(currencyType);
			bfp.setGuaranteeStatus(guaranteeStatus);
			bfp.setFlagCloseend(Boolean.valueOf(flagCloseend));
			bfp.setFlagPurchase(Boolean.valueOf(flagPurchase));
			bfp.setFlagRedemption(Boolean.valueOf(flagRedemption));
			bfp.setFlagFlexible(Boolean.valueOf(flagFlexible));
			bfp.setMinAnnualizedReturnRate(minAnnualizedReturnRate);
			bfp.setPaymentType(paymentType);
			bfp.setRecordDate(Timestamp.valueOf(Utlity.getFullTime(recordDate)));
			if(area!=null && !area.equals("")){
				BkArea bkArea = this.areaService.get(area);
				if(bkArea != null){
					bfp.setArea(area);
				} else {
					return ResultManager.createFailResult("???????????????????????????");
				}
			}
			bfp.setSubscribeFee(subscribeFee);
			bfp.setPurchaseFee(purchaseFee);
			bfp.setRedemingFee(redemingFee);
			bfp.setManagementFee(managementFee);
			bfp.setCustodyFee(custodyFee);
			bfp.setNetworkFee(networkFee);
			bfp.setCollectStarttime(Timestamp.valueOf(collectStarttime));
			bfp.setCollectEndtime(Timestamp.valueOf(collectEndtime));
			bfp.setValueDate(Timestamp.valueOf(Utlity.getFullTime(valueDate)));
			bfp.setMaturityDate(Timestamp.valueOf(Utlity.getFullTime(maturityDate)));
			bfp.setTerm(term);
			bfp.setInvestScope(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(investScope)));
			bfp.setRevenueFeature(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(revenueFeature)));
			bfp.setRemark(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(remark)));
			Resource resource = resourceService.get(document);
			if(resource != null){
				resource.setFilename("???"+bank.getName()+"???"+bfp.getName()+"("+bfp.getScode()+")");
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				bfp.setDocument(document);
			}
			//?????????????????????
			BankFinancialProductOperate bfpo = new BankFinancialProductOperate();
			bfpo.setUuid(UUID.randomUUID().toString());
			bfpo.setBankFinancialProduct(bfp.getUuid());
			bfpo.setType(BankFinancialProductOperateType.EDIT);
			bfpo.setValue(JSONUtils.obj2json(bfp));
			bfpo.setStatus(BankFinancialProductOperateStatus.DRAFT);
			bfpo.setCreator(currentOperator.getUuid());
			bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductOperateService.insert(bfpo);
			return ResultManager.createDataResult(bfp, "???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		//????????????????????????????????????
		BankFinancialProduct bfp = bankFinancialProductService.get(uuid);
		if(bfp != null){
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//??????????????????
			BankFinancialProductOperate bfpo = new BankFinancialProductOperate();
			bfpo.setUuid(UUID.randomUUID().toString());
			bfpo.setBankFinancialProduct(bfp.getUuid());
			bfpo.setType(BankFinancialProductOperateType.DELETE);
			bfpo.setValue("");
			bfpo.setStatus(BankFinancialProductOperateStatus.UNCHECKED);
			bfpo.setCreator(currentOperator.getUuid());
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductOperateService.insert(bfpo);
			return ResultManager.createSuccessResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @param starttime ??????????????????
	 * @param endtime	??????????????????
	 * @param deadline 1-???1?????? 2-???3?????? 3-???6?????? 4-???1???
	 * @return
	 */
	@RequestMapping(value = "/netvaluelist", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "starttime", message="????????????", type = DataType.DATE)
	@ActionParam(key = "endtime", message="????????????", type = DataType.DATE)
	@ActionParam(key = "deadline", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result netvaluelist(String uuid, String starttime, String endtime, String deadline, Integer pageNum, Integer pageSize) {
		
		//??????????????????????????????
		BankFinancialProduct bankFinancialProduct = bankFinancialProductService.get(uuid);
		if(bankFinancialProduct != null && uuid.equals(bankFinancialProduct.getUuid())){
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("bankFinancialProduct", uuid);
			Calendar c = Calendar.getInstance();
			if(!"all".equals(deadline)){
				switch (deadline) {
				case "1":
					c.setTime(new Date());
			        c.add(Calendar.MONTH, -1);
			        Date m = c.getTime();
					starttime = Utlity.timeSpanToDateString(m);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "2":
					c.setTime(new Date());
			        c.add(Calendar.MONTH, -3);
			        Date m2 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m2);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "3":
					c.setTime(new Date());
			        c.add(Calendar.MONTH, -6);
			        Date m3 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m3);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;
				case "4":
					c.setTime(new Date());
			        c.add(Calendar.YEAR, -1);
			        Date m4 = c.getTime();
					starttime = Utlity.timeSpanToDateString(m4);
					endtime = Utlity.timeSpanToDateString(new Timestamp(System.currentTimeMillis()));
					break;

				default:
					break;
				}
			}
			
			if(starttime != null && !"".equals(starttime)){
				searchMap.put("starttime", Utlity.getFullTime(starttime));
			}
			
			if(endtime != null && !"".equals(endtime)){
				searchMap.put("endtime", Utlity.getFullTime(endtime));
			}
			
			Integer count = bankFinancialProductDailyService.getCount(searchMap);
			//???????????????????????????????????????????????????
			List<Entity> list = bankFinancialProductDailyService.getListForPage(searchMap, pageNum, pageSize, "", BankFinancialProductDaily.class);
			if(list != null){
				List<BankFinancialProductDailyVO> listvo = new ArrayList<BankFinancialProductDailyVO>();
				for(Entity entity : list){
					BankFinancialProductDaily bfd = (BankFinancialProductDaily)entity;
					BankFinancialProductDailyVO bfdvo = new BankFinancialProductDailyVO(bfd);
					bfdvo.setBankFinancialProduct(bankFinancialProduct.getName());
					listvo.add(bfdvo);
				}
				return ResultManager.createDataResult(listvo,bankFinancialProduct.getName(), pageNum, pageSize, count);
			}else{
				return ResultManager.createFailResult("???????????????");
			}
			
		}else{
			return ResultManager.createFailResult("??????????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/netvalueGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result netvalueGet(String uuid) {
		BankFinancialProductDaily bankFinancialProductDaily = bankFinancialProductDailyService.get(uuid);
		if(bankFinancialProductDaily != null && uuid.equals(bankFinancialProductDaily.getUuid())){
			BankFinancialProductDailyVO vo = new BankFinancialProductDailyVO(bankFinancialProductDaily);
			if(bankFinancialProductDaily.getCreator() != null){
				BkOperator creator = bkOperatorService.get(bankFinancialProductDaily.getCreator());
				if(creator != null){
					vo.setCreatorName(creator.getRealname());
				}
			}
			return ResultManager.createDataResult(vo);
		} else {
			return ResultManager.createFailResult("????????????????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * ??????????????????????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/netvalueadd", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "statistime", message="??????", type = DataType.DATE, required = true)
	@ActionParam(key = "netValue", message="??????", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result netvalueadd(String uuid, String statistime, BigDecimal netValue) {
		
		//????????????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//??????????????????????????????
		BankFinancialProduct bankFinancialProduct = bankFinancialProductService.get(uuid);
		if(bankFinancialProduct != null && uuid.equals(bankFinancialProduct.getUuid())){
			
			BankFinancialProductDaily bankFinancialProductDaily = new BankFinancialProductDaily();
			
			/*
			 * ?????????????????????????????????????????????????????????
			 */
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("bankFinancialProduct", bankFinancialProduct.getUuid());
			searchMap.put("statistime", Utlity.getFullTime(statistime));
			
			List<Entity> list = this.bankFinancialProductDailyService.getListForPage(searchMap, 0, 1, null, BankFinancialProductDaily.class);
			if(list != null && list.size() > 0){
				return ResultManager.createFailResult("???????????????????????????????????????????????????????????????");
			}
			
			//??????
			bankFinancialProductDaily.setUuid(UUID.randomUUID().toString());
			bankFinancialProductDaily.setBankFinancialProduct(bankFinancialProduct.getUuid());
			bankFinancialProductDaily.setCreator(currentOperator.getUuid());
			bankFinancialProductDaily.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductDaily.setNetValue(netValue);
			bankFinancialProductDaily.setStatistime(Timestamp.valueOf(Utlity.getFullTime(statistime)));
			
			//??????????????????
			BankFinancialProductOperate bfpo = new BankFinancialProductOperate();
			bfpo.setUuid(UUID.randomUUID().toString());
			bfpo.setBankFinancialProduct(bankFinancialProduct.getUuid());
//			bfpo.setType(BankFinancialProductOperateType.ADDNETVALUE);
			bfpo.setValue(JSONUtils.obj2json(bankFinancialProductDaily));
			bfpo.setStatus(BankFinancialProductOperateStatus.UNCHECKED);
			bfpo.setCreator(currentOperator.getUuid());
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductOperateService.insert(bfpo);
			return ResultManager.createSuccessResult("???????????????");
		} else {
			return ResultManager.createFailResult("??????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @param statistime
	 * @param netValue
	 * @return
	 */
	@RequestMapping(value = "/netvalueEdit", method = RequestMethod.POST)
	@ActionParam(key = "data", message="??????", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result netvalueEdit(String[] data, String uuid) {

		BankFinancialProduct bfp = bankFinancialProductService.get(uuid);
		if(bfp == null){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(data != null && data.length > 0){
//			bankFinancialProductOperateService
			List<BankFinancialProductOperateDailyVO> list = new ArrayList<BankFinancialProductOperateDailyVO>();
			//????????????
			for(String netValues : data){
				String[] netValue = netValues.split("_");
				String type = netValue[0];
				String netuuid = netValue[1];
				String statistime = netValue[2];
				String netValueStr = netValue[3];
				if(BankFinancialProductOperateType.ADD.equals(type)){//??????
					BankFinancialProductDaily bankFinancialProductDaily = new BankFinancialProductDaily();
					bankFinancialProductDaily.setUuid(UUID.randomUUID().toString());
					bankFinancialProductDaily.setBankFinancialProduct(uuid);
					bankFinancialProductDaily.setCreator(currentOperator.getUuid());
					bankFinancialProductDaily.setCreatetime(new Timestamp(System.currentTimeMillis()));
					bankFinancialProductDaily.setNetValue(BigDecimal.valueOf(Double.valueOf(netValueStr)));
					bankFinancialProductDaily.setStatistime(Timestamp.valueOf(Utlity.getFullTime(statistime)));
					BankFinancialProductOperateDailyVO bfpod = new BankFinancialProductOperateDailyVO(bankFinancialProductDaily, BankFinancialProductOperateType.ADD);
					list.add(bfpod);
				} else if(BankFinancialProductOperateType.EDIT.equals(type)) {
					if(netuuid != null && !"".equals(netuuid)){
						BankFinancialProductDaily bankFinancialProductDaily = bankFinancialProductDailyService.get(netuuid);
						BigDecimal oldValue = bankFinancialProductDaily.getNetValue();
						bankFinancialProductDaily.setNetValue(BigDecimal.valueOf(Double.valueOf(netValueStr)));
						bankFinancialProductDaily.setStatistime(Timestamp.valueOf(Utlity.getFullTime(statistime)));
						bankFinancialProductDaily.setCreator(currentOperator.getUuid());
						BankFinancialProductOperateDailyVO bfpod = new BankFinancialProductOperateDailyVO(bankFinancialProductDaily, BankFinancialProductOperateType.EDIT);
						bfpod.setOldValue(oldValue);
						list.add(bfpod);
					} else {
						return ResultManager.createFailResult("??????ID?????????????????????");
					}
					
				} else if(BankFinancialProductOperateType.DELETE.equals(type)) {
					if(netuuid != null && !"".equals(netuuid)){
						BankFinancialProductDaily bankFinancialProductDaily = bankFinancialProductDailyService.get(netuuid);
						BankFinancialProductOperateDailyVO bfpod = new BankFinancialProductOperateDailyVO(bankFinancialProductDaily, BankFinancialProductOperateType.DELETE);
						list.add(bfpod);
					} else {
						return ResultManager.createFailResult("??????ID?????????????????????");
					}
					
				} else {
					return ResultManager.createFailResult("???????????????????????????");
				}
				
			}
			BankFinancialProductOperate bfpo = new BankFinancialProductOperate();
			bfpo.setUuid(UUID.randomUUID().toString());
			bfpo.setBankFinancialProduct(bfp.getUuid());
			bfpo.setType(BankFinancialProductOperateType.NETVALUE);
			bfpo.setValue(JSONUtils.obj2json(list));
			bfpo.setStatus(BankFinancialProductOperateStatus.DRAFT);
			bfpo.setCreator(currentOperator.getUuid());
			bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductOperateService.insert(bfpo);
			return ResultManager.createSuccessResult("???????????????????????????");
		} else {
			return ResultManager.createSuccessResult("???????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateNetvalueGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateNetvalueGet(String uuid) {
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if(bfpo != null){
			if(!BankFinancialProductOperateType.NETVALUE.equals(bfpo.getType())){
				return ResultManager.createFailResult("??????????????????????????????");
			}
			BankFinancialProductOperateNetvalueVO bfponVO = new BankFinancialProductOperateNetvalueVO(bfpo);
			if(bfponVO.getBankFinancialProduct() != null){
				BankFinancialProduct bfp = bankFinancialProductService.get(bfponVO.getBankFinancialProduct());
				if(bfp != null){
					bfponVO.setBankFinancialProductName(bfp.getName());
					bfponVO.setScode(bfp.getScode());
					if(bfp.getCustodian() != null){
						Bank bank = bankService.get(bfp.getCustodian());
						if(bank != null){
							bfponVO.setCustodian(bank.getName());
						}
					}
				}
			}
			return ResultManager.createDataResult(bfponVO);
		} else {
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {		
		//??????????????????????????????
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if (bfpo == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		if (!BankFinancialProductOperateType.ADD.equals(bfpo.getType()) && !BankFinancialProductOperateType.EDIT.equals(bfpo.getType())
				&& !BankFinancialProductOperateType.DELETE.equals(bfpo.getType()) ){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		BankFinancialProductOperateDetailVO bfpodVO = new BankFinancialProductOperateDetailVO(bfpo);
		BkOperator creator = this.bkOperatorService.get(bfpodVO.getCreator());
		if(creator != null){
			bfpodVO.setCreatorName(creator.getRealname());
		}
		
		if(!BankFinancialProductOperateType.ADD.equals(bfpo.getType())){
			if(BankFinancialProductOperateType.EDIT.equals(bfpo.getType()) && BankFinancialProductOperateStatus.CHECKED.equals(bfpo.getStatus()) 
					&& bfpo.getOld() != null && !"".equals(bfpo.getOld())){
				BankFinancialProduct bfp = JSONUtils.json2obj(bfpo.getOld(), BankFinancialProduct.class);	
				BankFinancialProductDetailsVO bfpvo = new BankFinancialProductDetailsVO(bfp);
				
				if(bfp.getDocument() != null && !"".equals(bfp.getDocument())){
					Resource res = this.resourceService.get(bfp.getDocument());
					if(res != null){
						bfpvo.setDocumentCN(res.getFilename());
						bfpvo.setDocumentType(res.getFiletype());
						bfpvo.setDocumentURL(res.getUrl());
					}
				}else{
					bfpvo.setDocumentCN("?????????");
					bfpvo.setDocumentType("");
					bfpvo.setDocumentURL("");
				}
				
				if(bfp.getCustodian() != null && !"".equals(bfp.getCustodian())){
					Bank bank = this.bankService.get(bfp.getCustodian());
					if(bank != null){
						bfpvo.setCustodianCN(bank.getName());
						if(bank.getLogo() != null && !"".equals(bank.getLogo())){
							Resource resource = resourceService.get(bank.getLogo());
							if (resource != null) {
								bfpvo.setCustodianLogo(resource.getUrl());
							}else{
								bfpvo.setCustodianLogo("");
							}
						}else{
							bfpvo.setCustodianLogo("");
						}
					} else {
						bfpvo.setCustodianCN("?????????");
						bfpvo.setCustodianLogo("");
					}
					
				}else{
					bfpvo.setCustodianCN("?????????");
					bfpvo.setCustodianLogo("");
				}
				
				if(bfp.getArea() != null && !"".equals(bfp.getArea())){
					BkArea area = this.areaService.get(bfp.getArea());
					if(area != null){
						bfpvo.setAreaCN(area.getName());
					} else {
						bfpvo.setAreaCN("?????????");
					}
				}else{
					bfpvo.setAreaCN("?????????");
				}
				
				if(bfp.getCreator() != null && !"".equals(bfp.getCreator())){
					BkOperator operator = this.bkOperatorService.get(bfp.getCreator());
					if(operator != null){
						bfpvo.setCreatorName(operator.getRealname());
					} else {
						bfpvo.setCreatorName("???");
					}
					
				}else{
					bfpvo.setCreatorName("???");
				}
				bfpodVO.setOldData(bfpvo);
			}else{
				BankFinancialProduct bfp = bankFinancialProductService.get(bfpo.getBankFinancialProduct());
				BankFinancialProductDetailsVO bfpvo = new BankFinancialProductDetailsVO(bfp);
				if(bfp.getDocument() != null && !"".equals(bfp.getDocument())){
					Resource res = this.resourceService.get(bfp.getDocument());
					if(res != null){
						bfpvo.setDocumentCN(res.getFilename());
						bfpvo.setDocumentType(res.getFiletype());
						bfpvo.setDocumentURL(res.getUrl());
					}
				}else{
					bfpvo.setDocumentCN("?????????");
					bfpvo.setDocumentType("");
					bfpvo.setDocumentURL("");
				}
				
				if(bfp.getCustodian() != null && !"".equals(bfp.getCustodian())){
					Bank bank = this.bankService.get(bfp.getCustodian());
					if(bank != null){
						bfpvo.setCustodianCN(bank.getName());
						if(bank.getLogo() != null && !"".equals(bank.getLogo())){
							Resource resource = resourceService.get(bank.getLogo());
							if (resource != null) {
								bfpvo.setCustodianLogo(resource.getUrl());
							}else{
								bfpvo.setCustodianLogo("");
							}
						}else{
							bfpvo.setCustodianLogo("");
						}
					} else {
						bfpvo.setCustodianCN("?????????");
						bfpvo.setCustodianLogo("");
					}
					
				}else{
					bfpvo.setCustodianCN("?????????");
					bfpvo.setCustodianLogo("");
				}
				
				if(bfp.getArea() != null && !"".equals(bfp.getArea())){
					BkArea area = this.areaService.get(bfp.getArea());
					if(area != null){
						bfpvo.setAreaCN(area.getName());
					} else {
						bfpvo.setAreaCN("?????????");
					}
				}else{
					bfpvo.setAreaCN("?????????");
				}
				
				if(bfp.getCreator() != null && !"".equals(bfp.getCreator())){
					BkOperator operator = this.bkOperatorService.get(bfp.getCreator());
					if(operator != null){
						bfpvo.setCreatorName(operator.getRealname());
					} else {
						bfpvo.setCreatorName("???");
					}
					
				}else{
					bfpvo.setCreatorName("???");
				}
				bfpodVO.setOldData(bfpvo);
			}
		}
		if(BankFinancialProductOperateType.ADD.equals(bfpo.getType()) || BankFinancialProductOperateType.EDIT.equals(bfpo.getType())){
			BankFinancialProduct bfp = JSONUtils.json2obj(bfpo.getValue(), BankFinancialProduct.class);	
			BankFinancialProductDetailsVO bfpvo = new BankFinancialProductDetailsVO(bfp);
			if(bfp.getDocument() != null && !"".equals(bfp.getDocument())){
				Resource res = this.resourceService.get(bfp.getDocument());
				if(res != null){
					bfpvo.setDocumentCN(res.getFilename());
					bfpvo.setDocumentType(res.getFiletype());
					bfpvo.setDocumentURL(res.getUrl());
				}
			}else{
				bfpvo.setDocumentCN("?????????");
				bfpvo.setDocumentType("");
				bfpvo.setDocumentURL("");
			}
			
			if(bfp.getCustodian() != null && !"".equals(bfp.getCustodian())){
				Bank bank = this.bankService.get(bfp.getCustodian());
				if(bank != null){
					bfpvo.setCustodianCN(bank.getName());
					if(bank.getLogo() != null && !"".equals(bank.getLogo())){
						Resource resource = resourceService.get(bank.getLogo());
						if (resource != null) {
							bfpvo.setCustodianLogo(resource.getUrl());
						}else{
							bfpvo.setCustodianLogo("");
						}
					}else{
						bfpvo.setCustodianLogo("");
					}
				} else {
					bfpvo.setCustodianCN("?????????");
					bfpvo.setCustodianLogo("");
				}
				
			}else{
				bfpvo.setCustodianCN("?????????");
				bfpvo.setCustodianLogo("");
			}
			
			if(bfp.getArea() != null && !"".equals(bfp.getArea())){
				BkArea area = this.areaService.get(bfp.getArea());
				if(area != null){
					bfpvo.setAreaCN(area.getName());
				} else {
					bfpvo.setAreaCN("?????????");
				}
			}else{
				bfpvo.setAreaCN("?????????");
			}
			
			if(bfp.getCreator() != null && !"".equals(bfp.getCreator())){
				BkOperator operator = this.bkOperatorService.get(bfp.getCreator());
				if(operator != null){
					bfpvo.setCreatorName(operator.getRealname());
				} else {
					bfpvo.setCreatorName("???");
				}
				
			}else{
				bfpvo.setCreatorName("???");
			}
			bfpodVO.setNewData(bfpvo);
		}
		return ResultManager.createDataResult(bfpodVO);
	}
	
	/**
	 * ????????????????????????????????????????????????
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
		Integer totalResultCount = bankFinancialProductOperateService.getCount(searchMap);
		//???????????????????????????????????????????????????
		List<Entity> list = bankFinancialProductOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductOperate bfpo = (BankFinancialProductOperate)e;
				BankFinancialProductOperateVO bfpoVO = new BankFinancialProductOperateVO(bfpo);
				if(BankFinancialProductOperateType.ADD.equals(bfpo.getType())){
					BankFinancialProduct bfp = JSONUtils.json2obj(bfpo.getValue(), BankFinancialProduct.class);
					bfpoVO.setBankFinancialProductName(bfp.getName());
					bfpoVO.setScode(bfp.getScode());
					bfpoVO.setCustodian(bfp.getCustodian());
					if(bfp.getCustodian()!=null && !"".equals(bfp.getCustodian())){
						Bank bank = this.bankService.get(bfp.getCustodian());
						if(bank != null){
							bfpoVO.setCustodianName(bank.getName());
						}else{
							bfpoVO.setCustodianName("?????????");
						}
					}else{
						bfpoVO.setCustodianName("?????????");
					}
				}
				if(bfpoVO.getBankFinancialProduct() != null && !"".equals(bfpoVO.getBankFinancialProduct())){
					BankFinancialProduct bfp = this.bankFinancialProductService.get(bfpoVO.getBankFinancialProduct());
					if(bfp != null){
						bfpoVO.setBankFinancialProductName(bfp.getName());
						bfpoVO.setScode(bfp.getScode());
						bfpoVO.setCustodian(bfp.getCustodian());
						if(bfp.getCustodian()!=null && !"".equals(bfp.getCustodian())){
							Bank bank = this.bankService.get(bfp.getCustodian());
							if(bank != null){
								bfpoVO.setCustodianName(bank.getName());
							}else{
								bfpoVO.setCustodianName("?????????");
							}
						}else{
							bfpoVO.setCustodianName("?????????");
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(bfpoVO.getCreator());
				if(creator != null){
					bfpoVO.setCreatorName(creator.getRealname());
				}
				if(bfpoVO.getChecker() != null && !"".equals(bfpoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfpoVO.getChecker());
					if(checker != null){
						bfpoVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(bfpoVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????????????????????????????????????????(?????????)
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
		Integer totalResultCount = bankFinancialProductOperateService.getCount(searchMap);
		//???????????????????????????????????????????????????
		List<Entity> list = bankFinancialProductOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductOperate bfpo = (BankFinancialProductOperate)e;
				BankFinancialProductOperateVO bfpoVO = new BankFinancialProductOperateVO(bfpo);
				if(BankFinancialProductOperateType.ADD.equals(bfpo.getType())){
					BankFinancialProduct bfp = JSONUtils.json2obj(bfpo.getValue(), BankFinancialProduct.class);
					bfpoVO.setBankFinancialProductName(bfp.getName());
					bfpoVO.setScode(bfp.getScode());
					bfpoVO.setCustodian(bfp.getCustodian());
					if(bfp.getCustodian()!=null && !"".equals(bfp.getCustodian())){
						Bank bank = this.bankService.get(bfp.getCustodian());
						if(bank != null){
							bfpoVO.setCustodianName(bank.getName());
						}else{
							bfpoVO.setCustodianName("?????????");
						}
					}else{
						bfpoVO.setCustodianName("?????????");
					}
				}
				if(bfpoVO.getBankFinancialProduct() != null && !"".equals(bfpoVO.getBankFinancialProduct())){
					BankFinancialProduct bfp = this.bankFinancialProductService.get(bfpoVO.getBankFinancialProduct());
					if(bfp != null){
						bfpoVO.setBankFinancialProductName(bfp.getName());
						bfpoVO.setScode(bfp.getScode());
						bfpoVO.setCustodian(bfp.getCustodian());
						if(bfp.getCustodian()!=null && !"".equals(bfp.getCustodian())){
							Bank bank = this.bankService.get(bfp.getCustodian());
							if(bank != null){
								bfpoVO.setCustodianName(bank.getName());
							}else{
								bfpoVO.setCustodianName("?????????");
							}
						}else{
							bfpoVO.setCustodianName("?????????");
						}
					}
				}
				BkOperator creator = this.bkOperatorService.get(bfpoVO.getCreator());
				if(creator != null){
					bfpoVO.setCreatorName(creator.getRealname());
				}
				if(bfpoVO.getChecker() != null && !"".equals(bfpoVO.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfpoVO.getChecker());
					if(checker != null){
						bfpoVO.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(bfpoVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????????????????????????????????????????
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
	 * @param currencyType
	 * @param term
	 * @param custodian
	 * @param riskLevel
	 * @param flagPurchase
	 * @param flagRedemption
	 * @param flagFlexible
	 * @param minAnnualizedReturnRate
	 * @param recordDate
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
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
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
	@ResponseBody
	public Result operateEdit(String uuid, String name, String series, String scode, String shortname, String type, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount,String paymentType, String url, 
			String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, String flagRedemption, String flagFlexible, 
			BigDecimal minAnnualizedReturnRate, String recordDate, String area, BigDecimal subscribeFee, BigDecimal purchaseFee, BigDecimal redemingFee, 
			BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, String valueDate,
			String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if (bfpo != null) {
			BankFinancialProduct bfp = JSONUtils.json2obj(bfpo.getValue(), BankFinancialProduct.class);
			
			if(bankFinancialProductService.isExistBankFinancialProductByName(name,bfpo.getBankFinancialProduct())){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			bfp.setName(name);
			bfp.setSeries(series);
			if(bankFinancialProductService.isExistBankFinancialProductByScode(scode,bfpo.getBankFinancialProduct())){
				return ResultManager.createFailResult("????????????????????????????????????");
			}
			bfp.setScode(scode);
			bfp.setShortname(shortname);
			bfp.setType(type);
			bfp.setUrl(url);
			bfp.setTarget(target);
			bfp.setTargetAnnualizedReturnRate(targetAnnualizedReturnRate);
			bfp.setMinInvestAmount(minInvestAmount);
			bfp.setMinInvestAmountAdd(minInvestAmountAdd);
			bfp.setMaxInvestAmount(maxInvestAmount);
			bfp.setTotalAmount(totalAmount.multiply(BigDecimal.valueOf(100000000)));
			Bank bank = bankService.get(custodian);
			if(bank != null){
				bfp.setCustodian(custodian);
			}else{
				return ResultManager.createFailResult("??????????????????????????????");
			}
			bfp.setRiskLevel(riskLevel);
			bfp.setCurrencyType(currencyType);
			bfp.setFlagPurchase(Boolean.valueOf(flagPurchase));
			bfp.setFlagRedemption(Boolean.valueOf(flagRedemption));
			bfp.setFlagFlexible(Boolean.valueOf(flagFlexible));
			bfp.setMinAnnualizedReturnRate(minAnnualizedReturnRate);
			bfp.setPaymentType(paymentType);
			bfp.setRecordDate(Timestamp.valueOf(Utlity.getFullTime(recordDate)));
			if(area!=null && !area.equals("")){
				BkArea bkArea = this.areaService.get(area);
				if(bkArea != null){
					bfp.setArea(area);
				} else {
					return ResultManager.createFailResult("???????????????????????????");
				}
			}
			bfp.setSubscribeFee(subscribeFee);
			bfp.setPurchaseFee(purchaseFee);
			bfp.setRedemingFee(redemingFee);
			bfp.setManagementFee(managementFee);
			bfp.setCustodyFee(custodyFee);
			bfp.setNetworkFee(networkFee);
			bfp.setCollectStarttime(Timestamp.valueOf(collectStarttime));
			bfp.setCollectEndtime(Timestamp.valueOf(collectEndtime));
			bfp.setValueDate(Timestamp.valueOf(Utlity.getFullTime(valueDate)));
			bfp.setMaturityDate(Timestamp.valueOf(Utlity.getFullTime(maturityDate)));
			bfp.setTerm(term);
			bfp.setInvestScope(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(investScope)));
			bfp.setRevenueFeature(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(revenueFeature)));
			bfp.setRemark(HtmlHelper.parseHtmlMark(HtmlHelper.parseString2Html(remark)));
			Resource resource = resourceService.get(document);
			if(resource != null){
				resource.setFilename("???"+bank.getName()+"???"+bfp.getName()+"("+bfp.getScode()+")");
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				bfp.setDocument(document);
			}
			//?????????????????????
			bfpo.setValue(JSONUtils.obj2json(bfp));
			bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductOperateService.update(bfpo);
			return ResultManager.createDataResult("??????????????????????????????");
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
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if(bfpo != null){
			if(!BankFinancialProductOperateStatus.DRAFT.equals(bfpo.getStatus()) && !BankFinancialProductOperateStatus.UNPASSED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			bfpo.setStatus(BankFinancialProductOperateStatus.DELETED);
			bankFinancialProductOperateService.update(bfpo);
			return ResultManager.createSuccessResult("???????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
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
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if(bfpo != null){
			if(BankFinancialProductOperateStatus.CHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			if(BankFinancialProductOperateType.EDIT.equals(bfpo.getType()) || BankFinancialProductOperateType.NETVALUE.equals(bfpo.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bankFinancialProduct", bfpo.getBankFinancialProduct());
				searchMap.put("type", bfpo.getType());
				searchMap.put("status", BankFinancialProductOperateStatus.UNCHECKED);
				
				Integer count = this.bankFinancialProductOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("??????????????????????????????????????????????????????");
				}
			}
			
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(BankFinancialProductOperateStatus.UNCHECKED);
			bankFinancialProductOperateService.update(bfpo);
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
		if(!BankFinancialProductOperateStatus.CHECKED.equals(status) && !BankFinancialProductOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//????????????????????????????????????
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if(bfpo != null){
			if(!BankFinancialProductOperateStatus.UNCHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("???????????????????????????");
			}
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(bfpo.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("???????????????????????????????????????");
			}
			
			bfpo.setChecker(currentOperator.getUuid());
			bfpo.setChecktime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(status);
			bfpo.setReason(reason);
			try {
				bankFinancialProductOperateService.check(bfpo);
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
	 * ?????????????????????????????????????????????
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
		//????????????????????????????????????
		List<Entity> list = bankFinancialProductOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//????????????????????????????????????
		List<Entity> list = bankFinancialProductOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????????????????????????????
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
		
		List<Entity> list = bankFinancialProductOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ??????????????????????????????????????????????????????????????????
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
		
		List<Entity> list = bankFinancialProductOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
