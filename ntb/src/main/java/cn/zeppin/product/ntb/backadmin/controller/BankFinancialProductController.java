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
 * 后台银行理财产品信息管理
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
	 * 根据条件查询银行理财产品信息 
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
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)//状态
	@ActionParam(key = "stage", message="阶段", type = DataType.STRING)//阶段
	@ActionParam(key = "income", message="预期收益", type = DataType.STRING)//期限
	@ActionParam(key = "term", message="期限", type = DataType.STRING)//期限
	@ActionParam(key = "type", message="类型", type = DataType.STRING)//类型
	@ActionParam(key = "custodian", message="管理银行", type = DataType.STRING)//管理银行
	@ActionParam(key = "riskLevel", message="风险等级", type = DataType.STRING)//风险等级
	@ActionParam(key = "isRedeem", message="是否已赎回", type = DataType.STRING)//是否已赎回
	@ActionParam(key = "redeem", message="赎回状态", type = DataType.STRING)//赎回状态
	@ActionParam(key = "invested", message="投资状态", type = DataType.STRING)//已投资
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="分页参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status,String stage, String income, String term, String type, String riskLevel, 
			String custodian, String redeem, String invested, String isRedeem, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
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
		//查询符合条件的银行理财产品信息的总数
		Integer totalResultCount = bankFinancialProductService.getCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = bankFinancialProductService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProduct.class);
		//封装可用信息到前台List
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
						bfpvo.setCustodianName("未选择");
					}
				}else{
					bfpvo.setCustodianName("未选择");
				}
				if(bfp.getArea()!=null){
					BkArea ba = this.areaService.get(bfp.getArea());
					if(ba != null){
						bfpvo.setArea(ba.getName());
					}else{
						bfpvo.setArea("未选择");
					}
				}else{
					bfpvo.setArea("未选择");
				}
				Map<String,String> search = new HashMap<String, String>();
				search.put("bankFinancialProduct", bfp.getUuid());
				
				List<Entity> networkList = bankFinancialProductDailyService.getListForPage(search, 0, 1, "statistime desc", BankFinancialProductDaily.class);
				if(networkList.size()>0){
					BankFinancialProductDaily bfpd = (BankFinancialProductDaily) networkList.get(0);
					bfpvo.setNetWorth(bfpd.getNetValue().setScale(4, BigDecimal.ROUND_HALF_UP).toString());
					bfpvo.setNetWorthTime(Utlity.timeSpanToDateString(bfpd.getStatistime()));
				}else{
					bfpvo.setNetWorth("未录入");
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
	 * 获取可发布银行理财产品列表
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/publishList", method = RequestMethod.GET)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="分页参数", type = DataType.STRING)
	@ResponseBody
	public Result publishList(String name, Integer pageNum, Integer pageSize, String sorts) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		//查询符合条件的银行理财产品信息的总数
		Integer totalResultCount = bankFinancialProductService.getPublishCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = bankFinancialProductService.getPublishListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProduct.class);
		//封装可用信息到前台List
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
						bfpvo.setCustodianName("未选择");
					}
				}else{
					bfpvo.setCustodianName("未选择");
				}
				if(bfp.getArea()!=null){
					BkArea ba = this.areaService.get(bfp.getArea());
					if(ba != null){
						bfpvo.setArea(ba.getName());
					}else{
						bfpvo.setArea("未选择");
					}
				}else{
					bfpvo.setArea("未选择");
				}
				Map<String,String> search = new HashMap<String, String>();
				search.put("bankFinancialProduct", bfp.getUuid());
				
				List<Entity> networkList = bankFinancialProductDailyService.getListForPage(search, 0, 1, "statistime desc", BankFinancialProductDaily.class);
				if(networkList.size()>0){
					BankFinancialProductDaily bfpd = (BankFinancialProductDaily) networkList.get(0);
					bfpvo.setNetWorth(bfpd.getNetValue().setScale(4, BigDecimal.ROUND_HALF_UP).toString());
					bfpvo.setNetWorthTime(Utlity.timeSpanToDateString(bfpd.getStatistime()));
				}else{
					bfpvo.setNetWorth("未录入");
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
	 * 获取银行理财产品分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		List<Entity> list = bankFinancialProductService.getStatusList(StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品分阶段列表
	 * @return
	 */
	@RequestMapping(value = "/stageList", method = RequestMethod.GET)
	@ActionParam(key = "invested", message="投资状态", type = DataType.STRING)//已投资
	@ResponseBody
	public Result stageList(String invested) {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("invested", invested);
		List<Entity> list = bankFinancialProductService.getStageList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品分投资状态列表
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
	 * 获取一条银行理财产品信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		//获取银行理财产品信息
		BankFinancialProduct bankFinancialProduct = bankFinancialProductService.get(uuid);
		if (bankFinancialProduct == null) {
			return ResultManager.createFailResult("该条数据不存在！");
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
			bfpvo.setDocumentCN("未上传");
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
				bfpvo.setCustodianCN("未选择");
				bfpvo.setCustodianLogo("");
			}
			
		}else{
			bfpvo.setCustodianCN("未选择");
			bfpvo.setCustodianLogo("");
		}
		
		if(bankFinancialProduct.getArea() != null && !"".equals(bankFinancialProduct.getArea())){
			BkArea area = this.areaService.get(bankFinancialProduct.getArea());
			if(area != null){
				bfpvo.setAreaCN(area.getName());
			} else {
				bfpvo.setAreaCN("未选择");
			}
		}else{
			bfpvo.setAreaCN("未选择");
		}
		
		if(bankFinancialProduct.getCreator() != null && !"".equals(bankFinancialProduct.getCreator())){
			BkOperator operator = this.bkOperatorService.get(bankFinancialProduct.getCreator());
			if(operator != null){
				bfpvo.setCreatorName(operator.getRealname());
			} else {
				bfpvo.setCreatorName("无");
			}
			
		}else{
			bfpvo.setCreatorName("无");
		}
		
		return ResultManager.createDataResult(bfpvo);
	}
	
	/**
	 * 添加一条银行理财产品信息
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
	@ActionParam(key = "custodian", message="管理银行", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="产品名称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "url", message="url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "series", message="产品系列", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "shortname", message="产品简称", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "scode", message="产品编号", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "type", message="投资类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "target", message="投资目标", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", message="目标年化收益率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalAmount", message="募集总额", type = DataType.NUMBER)
	@ActionParam(key = "paymentType", message="收益支付方式", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyType", message="理财币种", type = DataType.STRING, required = true)
	@ActionParam(key = "riskLevel", message="风险等级", type = DataType.STRING, required = true)
	@ActionParam(key = "minAnnualizedReturnRate", message="最小投资收益", type = DataType.NUMBER)
	@ActionParam(key = "area", message="地区", type = DataType.STRING, required = true)
	@ActionParam(key = "flagFlexible", message="灵活期限", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagPurchase", message="申购状态", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagRedemption", message="赎回状态", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "collectStarttime", message="认购起始时间", type = DataType.STRING, required = true)
	@ActionParam(key = "collectEndtime", message="认购截止时间", type = DataType.STRING, required = true)
	@ActionParam(key = "recordDate", message="登记日", type = DataType.DATE, required = true)
	@ActionParam(key = "valueDate", message="起息日", type = DataType.DATE, required = true)
	@ActionParam(key = "maturityDate", message="到期日", type = DataType.DATE, required = true)
	@ActionParam(key = "term", message="灵活期限", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmount", message="最小投资金额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmountAdd", message="最下投资递增", type = DataType.NUMBER, required = true)
	@ActionParam(key = "maxInvestAmount", message="最大投资金额", type = DataType.NUMBER)
	@ActionParam(key = "subscribeFee", message="认购费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "purchaseFee", message="申购费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "redemingFee", message="赎回费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "managementFee", message="管理费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "custodyFee", message="托管费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "networkFee", message="销售费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investScope", message="投资范围", type = DataType.STRING, required = true, maxLength = 5000)
	@ActionParam(key = "revenueFeature", message="收益说明", type = DataType.STRING, required = true,maxLength = 5000)
	@ActionParam(key = "remark", message="更多描述", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", message="产品说明书", type = DataType.STRING)
	@ResponseBody
	public Result add(String name, String series, String scode, String shortname, String type, String url, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount,String paymentType, String style, 
			String creditLevel, String flagCloseend, String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, 
			String flagRedemption, String flagFlexible, BigDecimal minAnnualizedReturnRate, String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, 
			BigDecimal purchaseFee, BigDecimal redemingFee, BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, 
			String valueDate, String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		//取管理员信息
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
			return ResultManager.createFailResult("银行理财产品名称已存在！");
		}
		bfp.setName(name);
		bfp.setSeries(series);
		if(bankFinancialProductService.isExistBankFinancialProductByScode(scode,null)){
			return ResultManager.createFailResult("银行理财产品编号已存在！");
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
			return ResultManager.createFailResult("资金托管方选择错误！");
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
				return ResultManager.createFailResult("发行地区选择错误！");
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
			resource.setFilename("【"+bank.getName()+"】"+bfp.getName()+"("+bfp.getScode()+")");
			resource = resourceService.updateName(resource);
			if(resource == null){
				return ResultManager.createFailResult("产品说明书替换失败！");
			}
			bfp.setDocument(document);
		}
		//添加待审核记录
		BankFinancialProductOperate bfpo = new BankFinancialProductOperate();
		bfpo.setUuid(UUID.randomUUID().toString());
		bfpo.setType(BankFinancialProductOperateType.ADD);
		bfpo.setValue(JSONUtils.obj2json(bfp));
		bfpo.setStatus(BankFinancialProductOperateStatus.DRAFT);
		bfpo.setCreator(currentOperator.getUuid());
		bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
		bankFinancialProductOperateService.insert(bfpo);
		return ResultManager.createDataResult(bfp, "添加待审记录成功！");
		
	}
	
	/**
	 * 编辑一条银行理财产品信息
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
	@ActionParam(key = "custodian", message="管理银行", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="产品名称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "url", message="url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "series", message="产品系列", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "shortname", message="产品简称", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "scode", message="产品编号", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "type", message="投资类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "target", message="投资目标", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", message="目标年化收益率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalAmount", message="募集总额", type = DataType.NUMBER)
	@ActionParam(key = "paymentType", message="收益支付方式", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyType", message="理财币种", type = DataType.STRING, required = true)
	@ActionParam(key = "riskLevel", message="风险等级", type = DataType.STRING, required = true)
	@ActionParam(key = "minAnnualizedReturnRate", message="最小投资收益", type = DataType.NUMBER)
	@ActionParam(key = "area", message="地区", type = DataType.STRING, required = true)
	@ActionParam(key = "flagFlexible", message="灵活期限", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagPurchase", message="申购状态", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagRedemption", message="赎回状态", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "collectStarttime", message="认购起始时间", type = DataType.STRING, required = true)
	@ActionParam(key = "collectEndtime", message="认购截止时间", type = DataType.STRING, required = true)
	@ActionParam(key = "recordDate", message="登记日", type = DataType.DATE, required = true)
	@ActionParam(key = "valueDate", message="起息日", type = DataType.DATE, required = true)
	@ActionParam(key = "maturityDate", message="到期日", type = DataType.DATE, required = true)
	@ActionParam(key = "term", message="灵活期限", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmount", message="最小投资金额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmountAdd", message="最下投资递增", type = DataType.NUMBER, required = true)
	@ActionParam(key = "maxInvestAmount", message="最大投资金额", type = DataType.NUMBER)
	@ActionParam(key = "subscribeFee", message="认购费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "purchaseFee", message="申购费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "redemingFee", message="赎回费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "managementFee", message="管理费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "custodyFee", message="托管费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "networkFee", message="销售费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investScope", message="投资范围", type = DataType.STRING, required = true, maxLength = 5000)
	@ActionParam(key = "revenueFeature", message="收益说明", type = DataType.STRING, required = true,maxLength = 5000)
	@ActionParam(key = "remark", message="更多描述", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", message="产品说明书", type = DataType.STRING)
	@ResponseBody
	public Result edit(String uuid, String name, String series, String scode, String shortname, String type, String url, String target, BigDecimal targetAnnualizedReturnRate, 
			BigDecimal minInvestAmount, BigDecimal minInvestAmountAdd, BigDecimal maxInvestAmount, BigDecimal totalAmount,String paymentType, String style, 
			String creditLevel, String flagCloseend, String currencyType, Integer term, String custodian, String riskLevel, String flagPurchase, 
			String flagRedemption, String flagFlexible, BigDecimal minAnnualizedReturnRate, String recordDate, String guaranteeStatus, String area, BigDecimal subscribeFee, 
			BigDecimal purchaseFee, BigDecimal redemingFee, BigDecimal managementFee, BigDecimal custodyFee, BigDecimal networkFee, String collectStarttime, String collectEndtime, 
			String valueDate, String maturityDate, String investScope, String revenueFeature, String remark, String document) throws ParserException {
		
		BankFinancialProduct bfp = bankFinancialProductService.get(uuid);
		if (bfp != null) {
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			if(bankFinancialProductService.isExistBankFinancialProductByName(name,uuid)){
				return ResultManager.createFailResult("银行理财产品名称已存在！");
			}
			bfp.setName(name);
			bfp.setSeries(series);
			if(bankFinancialProductService.isExistBankFinancialProductByScode(scode,uuid)){
				return ResultManager.createFailResult("银行理财产品编号已存在！");
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
				return ResultManager.createFailResult("资金托管方选择错误！");
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
					return ResultManager.createFailResult("发行地区选择错误！");
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
				resource.setFilename("【"+bank.getName()+"】"+bfp.getName()+"("+bfp.getScode()+")");
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("产品说明书替换失败！");
				}
				bfp.setDocument(document);
			}
			//添加待审核记录
			BankFinancialProductOperate bfpo = new BankFinancialProductOperate();
			bfpo.setUuid(UUID.randomUUID().toString());
			bfpo.setBankFinancialProduct(bfp.getUuid());
			bfpo.setType(BankFinancialProductOperateType.EDIT);
			bfpo.setValue(JSONUtils.obj2json(bfp));
			bfpo.setStatus(BankFinancialProductOperateStatus.DRAFT);
			bfpo.setCreator(currentOperator.getUuid());
			bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductOperateService.insert(bfpo);
			return ResultManager.createDataResult(bfp, "添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 删除一条银行理财产品信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		//获取银行理财产品投资信息
		BankFinancialProduct bfp = bankFinancialProductService.get(uuid);
		if(bfp != null){
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			//添加待审记录
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
			return ResultManager.createSuccessResult("添加待审记录成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 查询净值列表
	 * @param uuid
	 * @param starttime 起始查询时间
	 * @param endtime	终止查询时间
	 * @param deadline 1-前1个月 2-前3个月 3-前6个月 4-前1年
	 * @return
	 */
	@RequestMapping(value = "/netvaluelist", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.DATE)
	@ActionParam(key = "endtime", message="结束时间", type = DataType.DATE)
	@ActionParam(key = "deadline", message="最终期限", type = DataType.STRING, required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result netvaluelist(String uuid, String starttime, String endtime, String deadline, Integer pageNum, Integer pageSize) {
		
		//获取银行理财产品信息
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
			//查询符合条件的银行理财产品净值信息
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
				return ResultManager.createFailResult("查询失败！");
			}
			
		}else{
			return ResultManager.createFailResult("对应理财产品不存在！");
		}
	}
	
	/**
	 * 获取一条净值信息
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
			return ResultManager.createFailResult("理财产品净值信息不存在！");
		}
	}
	
	/**
	 * 录入一条净值信息
	 * 需要检查对应时间内是否已存在净值信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/netvalueadd", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "statistime", message="日期", type = DataType.DATE, required = true)
	@ActionParam(key = "netValue", message="净值", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result netvalueadd(String uuid, String statistime, BigDecimal netValue) {
		
		//获取用户登录信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		//获取银行理财产品信息
		BankFinancialProduct bankFinancialProduct = bankFinancialProductService.get(uuid);
		if(bankFinancialProduct != null && uuid.equals(bankFinancialProduct.getUuid())){
			
			BankFinancialProductDaily bankFinancialProductDaily = new BankFinancialProductDaily();
			
			/*
			 * 判断是否已经存在过相同时间内的净值信息
			 */
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("bankFinancialProduct", bankFinancialProduct.getUuid());
			searchMap.put("statistime", Utlity.getFullTime(statistime));
			
			List<Entity> list = this.bankFinancialProductDailyService.getListForPage(searchMap, 0, 1, null, BankFinancialProductDaily.class);
			if(list != null && list.size() > 0){
				return ResultManager.createFailResult("当前统计时间已存在净值信息，不能重复添加！");
			}
			
			//构造
			bankFinancialProductDaily.setUuid(UUID.randomUUID().toString());
			bankFinancialProductDaily.setBankFinancialProduct(bankFinancialProduct.getUuid());
			bankFinancialProductDaily.setCreator(currentOperator.getUuid());
			bankFinancialProductDaily.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductDaily.setNetValue(netValue);
			bankFinancialProductDaily.setStatistime(Timestamp.valueOf(Utlity.getFullTime(statistime)));
			
			//添加待审记录
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
			return ResultManager.createSuccessResult("保存成功！");
		} else {
			return ResultManager.createFailResult("对应理财产品不存在！");
		}
	}
	
	/**
	 * 编辑净值信息
	 * @param uuid
	 * @param statistime
	 * @param netValue
	 * @return
	 */
	@RequestMapping(value = "/netvalueEdit", method = RequestMethod.POST)
	@ActionParam(key = "data", message="数据", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result netvalueEdit(String[] data, String uuid) {

		BankFinancialProduct bfp = bankFinancialProductService.get(uuid);
		if(bfp == null){
			return ResultManager.createFailResult("银行理财产品信息不存在！");
		}
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		if(data != null && data.length > 0){
//			bankFinancialProductOperateService
			List<BankFinancialProductOperateDailyVO> list = new ArrayList<BankFinancialProductOperateDailyVO>();
			//解析过程
			for(String netValues : data){
				String[] netValue = netValues.split("_");
				String type = netValue[0];
				String netuuid = netValue[1];
				String statistime = netValue[2];
				String netValueStr = netValue[3];
				if(BankFinancialProductOperateType.ADD.equals(type)){//添加
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
						return ResultManager.createFailResult("净值ID信息传入有误！");
					}
					
				} else if(BankFinancialProductOperateType.DELETE.equals(type)) {
					if(netuuid != null && !"".equals(netuuid)){
						BankFinancialProductDaily bankFinancialProductDaily = bankFinancialProductDailyService.get(netuuid);
						BankFinancialProductOperateDailyVO bfpod = new BankFinancialProductOperateDailyVO(bankFinancialProductDaily, BankFinancialProductOperateType.DELETE);
						list.add(bfpod);
					} else {
						return ResultManager.createFailResult("净值ID信息传入有误！");
					}
					
				} else {
					return ResultManager.createFailResult("净值操作类型有误！");
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
			return ResultManager.createSuccessResult("添加待审记录成功！");
		} else {
			return ResultManager.createSuccessResult("操作成功！");
		}
	}
	
	/**
	 * 获取净值信息
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
				return ResultManager.createFailResult("操作信息类型不正确！");
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
			return ResultManager.createFailResult("操作信息不存在！");
		}
	}
	
	/**
	 * 获取一条银行理财产品信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {		
		//获取银行理财产品信息
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if (bfpo == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		if (!BankFinancialProductOperateType.ADD.equals(bfpo.getType()) && !BankFinancialProductOperateType.EDIT.equals(bfpo.getType())
				&& !BankFinancialProductOperateType.DELETE.equals(bfpo.getType()) ){
			return ResultManager.createFailResult("操作信息类型不正确！");
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
					bfpvo.setDocumentCN("未上传");
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
						bfpvo.setCustodianCN("未选择");
						bfpvo.setCustodianLogo("");
					}
					
				}else{
					bfpvo.setCustodianCN("未选择");
					bfpvo.setCustodianLogo("");
				}
				
				if(bfp.getArea() != null && !"".equals(bfp.getArea())){
					BkArea area = this.areaService.get(bfp.getArea());
					if(area != null){
						bfpvo.setAreaCN(area.getName());
					} else {
						bfpvo.setAreaCN("未选择");
					}
				}else{
					bfpvo.setAreaCN("未选择");
				}
				
				if(bfp.getCreator() != null && !"".equals(bfp.getCreator())){
					BkOperator operator = this.bkOperatorService.get(bfp.getCreator());
					if(operator != null){
						bfpvo.setCreatorName(operator.getRealname());
					} else {
						bfpvo.setCreatorName("无");
					}
					
				}else{
					bfpvo.setCreatorName("无");
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
					bfpvo.setDocumentCN("未上传");
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
						bfpvo.setCustodianCN("未选择");
						bfpvo.setCustodianLogo("");
					}
					
				}else{
					bfpvo.setCustodianCN("未选择");
					bfpvo.setCustodianLogo("");
				}
				
				if(bfp.getArea() != null && !"".equals(bfp.getArea())){
					BkArea area = this.areaService.get(bfp.getArea());
					if(area != null){
						bfpvo.setAreaCN(area.getName());
					} else {
						bfpvo.setAreaCN("未选择");
					}
				}else{
					bfpvo.setAreaCN("未选择");
				}
				
				if(bfp.getCreator() != null && !"".equals(bfp.getCreator())){
					BkOperator operator = this.bkOperatorService.get(bfp.getCreator());
					if(operator != null){
						bfpvo.setCreatorName(operator.getRealname());
					} else {
						bfpvo.setCreatorName("无");
					}
					
				}else{
					bfpvo.setCreatorName("无");
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
				bfpvo.setDocumentCN("未上传");
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
					bfpvo.setCustodianCN("未选择");
					bfpvo.setCustodianLogo("");
				}
				
			}else{
				bfpvo.setCustodianCN("未选择");
				bfpvo.setCustodianLogo("");
			}
			
			if(bfp.getArea() != null && !"".equals(bfp.getArea())){
				BkArea area = this.areaService.get(bfp.getArea());
				if(area != null){
					bfpvo.setAreaCN(area.getName());
				} else {
					bfpvo.setAreaCN("未选择");
				}
			}else{
				bfpvo.setAreaCN("未选择");
			}
			
			if(bfp.getCreator() != null && !"".equals(bfp.getCreator())){
				BkOperator operator = this.bkOperatorService.get(bfp.getCreator());
				if(operator != null){
					bfpvo.setCreatorName(operator.getRealname());
				} else {
					bfpvo.setCreatorName("无");
				}
				
			}else{
				bfpvo.setCreatorName("无");
			}
			bfpodVO.setNewData(bfpvo);
		}
		return ResultManager.createDataResult(bfpodVO);
	}
	
	/**
	 * 银行理财产品投资信息修改操作列表
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
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
		Integer totalResultCount = bankFinancialProductOperateService.getCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = bankFinancialProductOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductOperate.class);
		
		//封装可用信息到前台List
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
							bfpoVO.setCustodianName("未选择");
						}
					}else{
						bfpoVO.setCustodianName("未选择");
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
								bfpoVO.setCustodianName("未选择");
							}
						}else{
							bfpoVO.setCustodianName("未选择");
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
	 * 银行理财产品投资信息修改操作列表(管理员)
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "name", message="搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
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
		
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		
		//查询符合条件的银行理财产品信息的总数
		Integer totalResultCount = bankFinancialProductOperateService.getCount(searchMap);
		//查询符合条件的银行理财产品信息列表
		List<Entity> list = bankFinancialProductOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductOperate.class);
		
		//封装可用信息到前台List
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
							bfpoVO.setCustodianName("未选择");
						}
					}else{
						bfpoVO.setCustodianName("未选择");
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
								bfpoVO.setCustodianName("未选择");
							}
						}else{
							bfpoVO.setCustodianName("未选择");
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
	 * 编辑一条银行理财产品审核信息
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
	@ActionParam(key = "custodian", message="管理银行", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="产品名称", type = DataType.STRING, required = true, maxLength = 200)
	@ActionParam(key = "url", message="url", type = DataType.STRING, maxLength = 200)
	@ActionParam(key = "series", message="产品系列", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "shortname", message="产品简称", type = DataType.STRING, maxLength = 50)
	@ActionParam(key = "scode", message="产品编号", type = DataType.STRING, required = true, maxLength = 100)
	@ActionParam(key = "type", message="投资类型", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "target", message="投资目标", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "targetAnnualizedReturnRate", message="目标年化收益率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "totalAmount", message="募集总额", type = DataType.NUMBER)
	@ActionParam(key = "paymentType", message="收益支付方式", type = DataType.STRING, required = true)
	@ActionParam(key = "currencyType", message="理财币种", type = DataType.STRING, required = true)
	@ActionParam(key = "riskLevel", message="风险等级", type = DataType.STRING, required = true)
	@ActionParam(key = "minAnnualizedReturnRate", message="最小投资收益", type = DataType.NUMBER)
	@ActionParam(key = "area", message="地区", type = DataType.STRING, required = true)
	@ActionParam(key = "flagFlexible", message="灵活期限", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagPurchase", message="申购状态", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "flagRedemption", message="赎回状态", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "collectStarttime", message="认购起始时间", type = DataType.STRING, required = true)
	@ActionParam(key = "collectEndtime", message="认购截止时间", type = DataType.STRING, required = true)
	@ActionParam(key = "recordDate", message="登记日", type = DataType.DATE, required = true)
	@ActionParam(key = "valueDate", message="起息日", type = DataType.DATE, required = true)
	@ActionParam(key = "maturityDate", message="到期日", type = DataType.DATE, required = true)
	@ActionParam(key = "term", message="灵活期限", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmount", message="最小投资金额", type = DataType.NUMBER, required = true)
	@ActionParam(key = "minInvestAmountAdd", message="最下投资递增", type = DataType.NUMBER, required = true)
	@ActionParam(key = "maxInvestAmount", message="最大投资金额", type = DataType.NUMBER)
	@ActionParam(key = "subscribeFee", message="认购费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "purchaseFee", message="申购费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "redemingFee", message="赎回费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "managementFee", message="管理费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "custodyFee", message="托管费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "networkFee", message="销售费率", type = DataType.NUMBER, required = true)
	@ActionParam(key = "investScope", message="投资范围", type = DataType.STRING, required = true, maxLength = 5000)
	@ActionParam(key = "revenueFeature", message="收益说明", type = DataType.STRING, required = true,maxLength = 5000)
	@ActionParam(key = "remark", message="更多描述", type = DataType.STRING, maxLength = 5000)
	@ActionParam(key = "document", message="产品说明书", type = DataType.STRING)
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
				return ResultManager.createFailResult("银行理财产品名称已存在！");
			}
			bfp.setName(name);
			bfp.setSeries(series);
			if(bankFinancialProductService.isExistBankFinancialProductByScode(scode,bfpo.getBankFinancialProduct())){
				return ResultManager.createFailResult("银行理财产品编号已存在！");
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
				return ResultManager.createFailResult("资金托管方选择错误！");
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
					return ResultManager.createFailResult("发行地区选择错误！");
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
				resource.setFilename("【"+bank.getName()+"】"+bfp.getName()+"("+bfp.getScode()+")");
				resource = resourceService.updateName(resource);
				if(resource == null){
					return ResultManager.createFailResult("产品说明书替换失败！");
				}
				bfp.setDocument(document);
			}
			//修改待审核记录
			bfpo.setValue(JSONUtils.obj2json(bfp));
			bfpo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			bankFinancialProductOperateService.update(bfpo);
			return ResultManager.createDataResult("修改待审核记录成功！");
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
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//获取银行理财产品操作信息
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if(bfpo != null){
			if(!BankFinancialProductOperateStatus.DRAFT.equals(bfpo.getStatus()) && !BankFinancialProductOperateStatus.UNPASSED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("审核状态错误");
			}
			bfpo.setStatus(BankFinancialProductOperateStatus.DELETED);
			bankFinancialProductOperateService.update(bfpo);
			return ResultManager.createSuccessResult("操作成功！");
		}else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 *待审核草稿-提交审核
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//获取银行理财产品操作信息
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if(bfpo != null){
			if(BankFinancialProductOperateStatus.CHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("该记录已审核完毕");
			}
			
			if(BankFinancialProductOperateType.EDIT.equals(bfpo.getType()) || BankFinancialProductOperateType.NETVALUE.equals(bfpo.getType())){
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("bankFinancialProduct", bfpo.getBankFinancialProduct());
				searchMap.put("type", bfpo.getType());
				searchMap.put("status", BankFinancialProductOperateStatus.UNCHECKED);
				
				Integer count = this.bankFinancialProductOperateService.getCount(searchMap);
				if(count > 0 ){
					return ResultManager.createFailResult("该条数据有其他修改操作正在等待审核！");
				}
			}
			
			bfpo.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpo.setStatus(BankFinancialProductOperateStatus.UNCHECKED);
			bankFinancialProductOperateService.update(bfpo);
			return ResultManager.createSuccessResult("提交审核成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 审核银行理财产品修改操作
	 * @param uuid
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/operateCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "reason", message="审核原因", type = DataType.STRING, maxLength = 500)
	@ResponseBody
	public Result operateCheck(String uuid, String status, String reason) {
		if(!BankFinancialProductOperateStatus.CHECKED.equals(status) && !BankFinancialProductOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		
		//获取银行理财产品投资信息
		BankFinancialProductOperate bfpo = bankFinancialProductOperateService.get(uuid);
		if(bfpo != null){
			if(!BankFinancialProductOperateStatus.UNCHECKED.equals(bfpo.getStatus())){
				return ResultManager.createFailResult("该记录审核状态错误");
			}
			//取管理员信息
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(bfpo.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("无法审核自己提交的操作记录");
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
				return ResultManager.createFailResult("数据操作出错！");
			}
			return ResultManager.createSuccessResult("审核记录成功！");
		}
		else{
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
	
	/**
	 * 获取银行理财产品操作分状态列表
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
		//获取银行理财产品投资信息
		List<Entity> list = bankFinancialProductOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品操作分状态列表
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//获取银行理财产品投资信息
		List<Entity> list = bankFinancialProductOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品投资操作分类型列表
	 * @return
	 */
	@RequestMapping(value = "/operateTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
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
		
		List<Entity> list = bankFinancialProductOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * 获取银行理财产品投资操作分类型列表（管理员）
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="审核状态", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
//		if(!"all".equals(status)){
//			searchMap.put("status", status);
//		}
		searchMap.put("status", status);
		
		List<Entity> list = bankFinancialProductOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
