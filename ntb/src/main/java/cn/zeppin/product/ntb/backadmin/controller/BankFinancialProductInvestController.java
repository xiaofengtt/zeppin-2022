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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestOperateService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductInvestService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductInvestOperateDetailVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductInvestOperateVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductInvestRecordsVO;
import cn.zeppin.product.ntb.backadmin.vo.BankFinancialProductInvestVO;
import cn.zeppin.product.ntb.backadmin.vo.StstusCountVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest.BankFinancialProductInvestStage;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateStatus;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestOperate.BankFinancialProductInvestOperateType;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestRecords;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestRecords.BankFinancialProductInvestRecordsType;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * ????????????????????????????????????
 */

@Controller
@RequestMapping(value = "/backadmin/bankFinancialProductInvest")
public class BankFinancialProductInvestController extends BaseController {

	@Autowired
	private IBankFinancialProductInvestService bankFinancialProductInvestService;

	@Autowired
	private IBankService bankService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private IBankFinancialProductService bankFinancialProductService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IBankFinancialProductInvestOperateService bankFinancialProductInvestOperateService;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IBkOperatorService bkOperatorService;
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * ?????????????????????????????? 
	 * @param status
	 * @param stage
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "stage", message="????????????", type = DataType.STRING)
	@ActionParam(key = "companyAccount", message="????????????uuid", type = DataType.STRING)
	@ActionParam(key = "bankFinancialProduct", message="????????????uuid", type = DataType.STRING)//????????????
	@ActionParam(key = "bankFinancialProductPublish", message="????????????uuid", type = DataType.STRING)//??????????????????
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String stage, String bankFinancialProduct, String bankFinancialProductPublish, String companyAccount, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		if(!"all".equals(stage)){
			searchMap.put("stage", stage);
		}
		searchMap.put("companyAccount", companyAccount);
		searchMap.put("bankFinancialProduct", bankFinancialProduct);
		searchMap.put("bankFinancialProductPublish", bankFinancialProductPublish);
		
		//????????????????????????????????????????????????????????????
		Integer totalResultCount = bankFinancialProductInvestService.getCount(searchMap);
		//?????????????????????????????????????????????????????????
		List<Entity> list = bankFinancialProductInvestService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductInvest.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductInvest bfpi = (BankFinancialProductInvest)e;
				BankFinancialProductInvestVO bfpiVO = new BankFinancialProductInvestVO(bfpi);
				
				BankFinancialProduct bfp = this.bankFinancialProductService.get(bfpi.getBankFinancialProduct());
				if(bfp != null){
					Bank bank = this.bankService.get(bfp.getCustodian());
					if(bank != null){
						bfpiVO.setProductName("???" + bank.getShortName() + "???" + bfp.getName() + "(" + bfp.getScode()+ ")");
					}
					bfpiVO.setProductMaturityDate(Utlity.timeSpanToDateString(bfp.getMaturityDate()));
					bfpiVO.setProductTargetReturn(bfp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}
				if(FundPublishUuid.CURRENT.equals(bfpi.getBankFinancialProductPublish())){
					bfpiVO.setProductPublishName("??????????????????");
					bfpiVO.setProductPublishTargetReturn("-");
					bfpiVO.setProductPublishMaturityDate("-");
				}if(PlatformAccountUuid.BALANCE.equals(bfpi.getBankFinancialProductPublish())){
					bfpiVO.setProductPublishName("??????????????????");
					bfpiVO.setProductPublishTargetReturn("-");
					bfpiVO.setProductPublishMaturityDate("-");
				}else{
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(bfpi.getBankFinancialProductPublish());
					if(bfpp != null){
						bfpiVO.setProductPublishTargetReturn(bfpp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
						bfpiVO.setProductPublishMaturityDate(Utlity.timeSpanToDateString(bfpp.getMaturityDate()));
						Bank bank = this.bankService.get(bfpp.getCustodian());
						if(bank != null){
							bfpiVO.setProductPublishName("???" + bank.getShortName() + "???" + bfpp.getName() + "(" + bfpp.getScode()+ ")");
						}
					}
				}
				
				CompanyAccount ca = this.companyAccountService.get(bfpi.getCompanyAccount());
				if(ca != null){
					bfpiVO.setCompanyAccountName(ca.getAccountName());
				}
				
				BkOperator creator = this.bkOperatorService.get(bfpi.getCreator());
				if(creator != null){
					bfpiVO.setCreatorName(creator.getRealname());
				}
				listVO.add(bfpiVO);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????
	 * @param bankFinancialProduct
	 * @param companyAccount
	 * @param data
	 * @param totalAmount
	 * @param poundage
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/invest", method = RequestMethod.POST)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "bankFinancialProduct", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "data", message="????????????", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "poundage", message="?????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="????????????", type = DataType.STRING)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result invest(String companyAccount, String bankFinancialProduct, String[] data, BigDecimal poundage, String remark, String receipt) {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		BankFinancialProduct bfp = this.bankFinancialProductService.get(bankFinancialProduct);
		if(bfp == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!BankFinancialProductStage.COLLECT.equals(bfp.getStage())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		CompanyAccount ca = this.companyAccountService.get(companyAccount);
		if(ca == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		List<BankFinancialProductInvestRecords> bfpirList = new ArrayList<BankFinancialProductInvestRecords>();
		BigDecimal sum = BigDecimal.ZERO;
		for(String values : data){
			String[] value = values.split("_");
			String bankFinancialProductPublish = value[0];
			String amountString = value[1];
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
			
			if(amount.compareTo(BigDecimal.ZERO) > 0){
				BankFinancialProductInvestRecords bfpir = new BankFinancialProductInvestRecords();
				bfpir.setUuid(UUID.randomUUID().toString());
				bfpir.setBankFinancialProduct(bankFinancialProduct);
				bfpir.setCompanyAccount(companyAccount);
				if(!Utlity.isPositiveCurrency4Web(amountString)){
					return ResultManager.createFailResult("???????????????????????????");
				}
				if(FundPublishUuid.CURRENT.equals(bankFinancialProductPublish)){
					BigDecimal currentBalance = this.fundPublishService.getAccountBalance();
					if(amount.compareTo(currentBalance) > 0){
						return ResultManager.createFailResult("?????????????????????????????????");
					}
					bfpir.setBankFinancialProductPublish(bankFinancialProductPublish);
				}else if(PlatformAccountUuid.BALANCE.equals(bankFinancialProductPublish)){
					PlatformAccount pab = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
					PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
					if(amount.compareTo(pab.getTotalAmount().add(pai.getTotalAmount())) > 0){
						return ResultManager.createFailResult("???????????????????????????????????????");
					}
					bfpir.setBankFinancialProductPublish(bankFinancialProductPublish);
				}else{
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(bankFinancialProductPublish);
					if(bfpp != null){
						bfpir.setBankFinancialProductPublish(bankFinancialProductPublish);
					}else{
						return ResultManager.createFailResult("???????????????????????????");
					}
					if(BankFinancialProductPublishStage.FINISHED.equals(bfpp.getStage()) || BankFinancialProductPublishStage.EXCEPTION.equals(bfpp.getStage())){
						return ResultManager.createFailResult(bfpp.getName() + "???????????????????????????");
					}
					if(amount.compareTo(bfpp.getAccountBalance()) > 0){
						return ResultManager.createFailResult(bfpp.getName() + "???????????????");
					}
				}
				bfpir.setTotalAmount(amount);
				bfpir.setAccountBalace(BigDecimal.ZERO);
				bfpir.setType(BankFinancialProductInvestRecordsType.INVEST);
				bfpir.setRemark(remark);
				bfpir.setCreator(currentOperator.getUuid());
				bfpir.setCreatetime(new Timestamp(System.currentTimeMillis()));
				bfpirList.add(bfpir);
				sum = sum.add(amount);
			}
		}
		
		if(sum.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(sum.add(poundage).compareTo(ca.getAccountBalance()) > 0){
			return ResultManager.createFailResult("???????????????????????????");
		}
		
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("dataList", JSONUtils.obj2json(bfpirList));
		valueMap.put("poundage", poundage.toString());
		valueMap.put("totalAmount", sum.toString());
		valueMap.put("remark", remark);
		
		//?????????????????????
		BankFinancialProductInvestOperate bfpio = new BankFinancialProductInvestOperate();
		bfpio.setUuid(UUID.randomUUID().toString());
		bfpio.setBankFinancialProduct(bankFinancialProduct);
		bfpio.setType(BankFinancialProductInvestOperateType.INVEST);
		bfpio.setValue(JSONUtils.obj2json(valueMap));
		bfpio.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
		bfpio.setCreator(currentOperator.getUuid());
		bfpio.setSubmittime(new Timestamp(System.currentTimeMillis()));
		bfpio.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		bfpio.setReceipt(receipt);
		
		bankFinancialProductInvestOperateService.insert(bfpio);
		return ResultManager.createDataResult(bfpio, "???????????????????????????");
	}
	
	/**
	 * ??????
	 * @param bankFinancialProduct
	 * @param companyAccount
	 * @param data
	 * @param totalAmount
	 * @param totalReturn
	 * @param remark
	 * @param redeemTime
	 * @return
	 */
	@RequestMapping(value = "/redeem", method = RequestMethod.POST)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "bankFinancialProduct", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "data", message="??????", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "totalReturn", message="?????????", type = DataType.POSITIVE_CURRENCY, required = true, maxLength = 10)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "redeemTime", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result redeem(String companyAccount, String bankFinancialProduct, String[] data, BigDecimal totalReturn, String remark, 
			String redeemTime, String receipt) {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		BankFinancialProduct bfp = this.bankFinancialProductService.get(bankFinancialProduct);
		if(bfp == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		CompanyAccount ca = this.companyAccountService.get(companyAccount);
		if(ca == null){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
			return ResultManager.createFailResult("?????????????????????");
		}
		
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("stage", BankFinancialProductInvestStage.NORMAL);
		searchMap.put("companyAccount", companyAccount);
		searchMap.put("bankFinancialProduct", bankFinancialProduct);
		//?????????????????????????????????????????????????????????
		List<Entity> bfpiList = bankFinancialProductInvestService.getListForPage(searchMap, 1, Integer.MAX_VALUE, null, BankFinancialProductInvest.class);
		if(bfpiList.size() < 1){
			return ResultManager.createFailResult("????????????????????????????????????");
		}
		
		List<BankFinancialProductInvestRecords> bfpirList = new ArrayList<BankFinancialProductInvestRecords>();
		BigDecimal sum = BigDecimal.ZERO;
		for(String values : data){
			String[] value = values.split("_");
			String bankFinancialProductPublish = value[0];
			String amountString = value[1];
			
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
			
			if(amount.compareTo(BigDecimal.ZERO) > 0){
				BankFinancialProductInvestRecords bfpir = new BankFinancialProductInvestRecords();
				bfpir.setUuid(UUID.randomUUID().toString());
				bfpir.setBankFinancialProduct(bankFinancialProduct);
				bfpir.setCompanyAccount(companyAccount);
				if(!Utlity.isPositiveCurrency4Web(amountString)){
					return ResultManager.createFailResult("???????????????????????????");
				}
				bfpir.setBankFinancialProductPublish(bankFinancialProductPublish);
				
				for(Entity e : bfpiList){
					BankFinancialProductInvest bfpi = (BankFinancialProductInvest) e;
					if(bankFinancialProductPublish.equals(bfpi.getBankFinancialProductPublish())){
						if(amount.compareTo(bfpi.getAccountBalance()) > 0){
							return ResultManager.createFailResult("???????????????????????????????????????");
						}else{
							bfpir.setTotalAmount(amount);
						}
					}
				}
				
				bfpir.setAccountBalace(BigDecimal.ZERO);
				bfpir.setType(BankFinancialProductInvestRecordsType.REDEEM);
				bfpir.setRemark(remark);
				bfpir.setCreator(currentOperator.getUuid());
				bfpir.setCreatetime(Timestamp.valueOf(redeemTime));
				bfpirList.add(bfpir);
				sum = sum.add(amount);
			}
		}
		
		if(sum.compareTo(BigDecimal.ZERO) <= 0){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("dataList", JSONUtils.obj2json(bfpirList));
		valueMap.put("totalReturn", totalReturn.toString());
		valueMap.put("totalAmount", sum.toString());
		valueMap.put("remark", remark);
		//?????????????????????
		BankFinancialProductInvestOperate bfpio = new BankFinancialProductInvestOperate();
		bfpio.setUuid(UUID.randomUUID().toString());
		bfpio.setBankFinancialProduct(bankFinancialProduct);
		bfpio.setType(BankFinancialProductInvestOperateType.REDEEM);
		bfpio.setValue(JSONUtils.obj2json(valueMap));
		bfpio.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
		bfpio.setCreator(currentOperator.getUuid());
		bfpio.setSubmittime(new Timestamp(System.currentTimeMillis()));
		bfpio.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		bfpio.setReceipt(receipt);
		
		bankFinancialProductInvestOperateService.insert(bfpio);
		return ResultManager.createDataResult(bfpio, "???????????????????????????");
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result operateGet(String uuid) {		
		//????????????????????????????????????
		BankFinancialProductInvestOperate bfpio = bankFinancialProductInvestOperateService.get(uuid);
		if (bfpio == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		BankFinancialProductInvestOperateDetailVO bfpidVO = new BankFinancialProductInvestOperateDetailVO(bfpio);
		
		//???????????????????????????
		if(bfpidVO.getReceipt() != null && !"".equals(bfpidVO.getReceipt())){
			List<Resource> reList = new ArrayList<Resource>();
			String[] receiptArr = bfpidVO.getReceipt().split(",");
			String str = "";
			for(String receipt : receiptArr){
				Resource re = this.resourceService.get(receipt);
				if(re != null){
					str += receipt+",";
					reList.add(re);
				}
			}
			if(str.length() > 0){
				str = str.substring(0, str.length() - 1);
			}
			bfpidVO.setReceipt(str);
			bfpidVO.setListReceipt(reList);
		}
		
		BankFinancialProduct bfp = bankFinancialProductService.get(bfpio.getBankFinancialProduct());
		if (bfp == null) {
			return ResultManager.createFailResult("???????????????");
		}
		String productName = "";
		Bank b = this.bankService.get(bfp.getCustodian());
		if(b != null){
			productName = "???" + b.getShortName() + "???" + bfp.getName() + "???" + bfp.getScode()+ "???";
		}
		
		BkOperator operate = bkOperatorService.get(bfpio.getCreator());
		if (operate != null) {
			bfpidVO.setCreatorName(operate.getRealname());
		}
		
		Map<String, Object> dataMap = JSONUtils.json2map(bfpio.getValue());
		bfpidVO.setTotalAmount(BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalAmount").toString())));
		if(BankFinancialProductInvestOperateType.INVEST.equals(bfpio.getType())){
			bfpidVO.setPoundage(BigDecimal.valueOf(Double.parseDouble(dataMap.get("poundage").toString())));
		}else if(BankFinancialProductInvestOperateType.REDEEM.equals(bfpio.getType())){
			bfpidVO.setTotalReturn(BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalReturn").toString())));
		}
		
		List<BankFinancialProductInvestRecordsVO> dataList = new ArrayList<BankFinancialProductInvestRecordsVO>();
		List<BankFinancialProductInvestRecords> bfpirList = JSONUtils.json2list(dataMap.get("dataList").toString(), BankFinancialProductInvestRecords.class);
		for(BankFinancialProductInvestRecords bfpir : bfpirList){
			BankFinancialProductInvestRecordsVO bfpirvo = new BankFinancialProductInvestRecordsVO(bfpir);
			bfpirvo.setProductName(productName);
			if(FundPublishUuid.CURRENT.equals(bfpir.getBankFinancialProductPublish())){
				bfpirvo.setProductPublishName("??????????????????");
				bfpirvo.setProductPublishMaturityDate("-");
				bfpirvo.setProductPublishTargetReturn("-");
			}else if(PlatformAccountUuid.BALANCE.equals(bfpir.getBankFinancialProductPublish())){
				bfpirvo.setProductPublishName("??????????????????");
				bfpirvo.setProductPublishMaturityDate("-");
				bfpirvo.setProductPublishTargetReturn("-");
			}else{
				BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(bfpir.getBankFinancialProductPublish());
				if (bfpp != null) {
					bfpirvo.setProductPublishTargetReturn(bfpp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					bfpirvo.setProductPublishMaturityDate(Utlity.timeSpanToDateString(bfpp.getValueDate()));
					Bank bank = this.bankService.get(bfpp.getCustodian());
					if(bank != null){
						bfpirvo.setProductPublishName("???" + bank.getShortName() + "???" + bfpp.getName() + "???" + bfpp.getScode()+ "???");
					}
				}
			}
			
			CompanyAccount ca = companyAccountService.get(bfpir.getCompanyAccount());
			if (ca != null) {
				bfpirvo.setCompanyAccountName(ca.getAccountName());
			}
			
			BkOperator o = bkOperatorService.get(bfpir.getCreator());
			if (o != null) {
				bfpirvo.setCreatorName(o.getRealname());
			}
			dataList.add(bfpirvo);
		}
		bfpidVO.setDataList(dataList);
		
		return ResultManager.createDataResult(bfpidVO);
	}
	
	/**
	 * ??????????????????(??????)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "bankFinancialProduct", message="????????????uuid", type = DataType.STRING)//????????????
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateList(String status, String bankFinancialProduct, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		if(!"all".equals(status)){
			searchMap.put("status", status);
		}
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		searchMap.put("bankFinancialProduct", bankFinancialProduct);
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		searchMap.put("creator", currentOperator.getUuid());
			
		//????????????????????????????????????????????????
		Integer totalResultCount = bankFinancialProductInvestOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = bankFinancialProductInvestOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductInvestOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductInvestOperate bfpio = (BankFinancialProductInvestOperate)e;
				BankFinancialProductInvestOperateVO bfpiovo = new BankFinancialProductInvestOperateVO(bfpio);
				
				
				BankFinancialProduct bfp = bankFinancialProductService.get(bfpio.getBankFinancialProduct());
				if (bfp != null){
					Bank b = this.bankService.get(bfp.getCustodian());
					if(b != null){
						bfpiovo.setBankFinancialProductName(b.getName() + bfp.getName() + "(" + bfp.getScode() + ")");
					}
				}
				
				BkOperator creator = this.bkOperatorService.get(bfpiovo.getCreator());
				if(creator != null){
					bfpiovo.setCreatorName(creator.getRealname());
				}
				
				if(bfpiovo.getChecker() != null && !"".equals(bfpiovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfpiovo.getChecker());
					if(checker != null){
						bfpiovo.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(bfpiovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ??????????????????(?????????)
	 * @param status
	 * @param type
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/operateCheckList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "type", message="??????", type = DataType.STRING)
	@ActionParam(key = "name", message="????????????", type = DataType.STRING)
	@ActionParam(key = "bankFinancialProduct", message="????????????uuid", type = DataType.STRING)//????????????
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateCheckList(String status, String bankFinancialProduct, String type, String name, Integer pageNum, Integer pageSize, String sorts) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("name", name);
		searchMap.put("status", status);
		if(!"all".equals(type)){
			searchMap.put("type", type);
		}
		if(Utlity.checkStringNull(sorts)){
			sorts = "submittime-desc";
		}
		searchMap.put("bankFinancialProduct", bankFinancialProduct);
		//????????????????????????????????????????????????
		Integer totalResultCount = bankFinancialProductInvestOperateService.getCount(searchMap);
		//?????????????????????????????????????????????
		List<Entity> list = bankFinancialProductInvestOperateService.getListForPage(searchMap, pageNum, pageSize, sorts, BankFinancialProductInvestOperate.class);
		
		//???????????????????????????List
		List<Object> listVO = new ArrayList<Object>();
		if(list != null && list.size() > 0){
			for(Entity e: list){
				BankFinancialProductInvestOperate bfpio = (BankFinancialProductInvestOperate)e;
				BankFinancialProductInvestOperateVO bfpiovo = new BankFinancialProductInvestOperateVO(bfpio);
				
				Map<String, Object> dataMap = JSONUtils.json2map(bfpio.getValue());
				bfpiovo.setTotalAmount(BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalAmount").toString())));
				if(BankFinancialProductInvestOperateType.INVEST.equals(bfpio.getType())){
					bfpiovo.setPoundage(BigDecimal.valueOf(Double.parseDouble(dataMap.get("poundage").toString())));
				}else if(BankFinancialProductInvestOperateType.REDEEM.equals(bfpio.getType())){
					bfpiovo.setTotalReturn(BigDecimal.valueOf(Double.parseDouble(dataMap.get("totalReturn").toString())));
				}
				
				BankFinancialProduct bfp = bankFinancialProductService.get(bfpio.getBankFinancialProduct());
				if (bfp != null){
					Bank b = this.bankService.get(bfp.getCustodian());
					if(b != null){
						bfpiovo.setBankFinancialProductName(b.getName() + bfp.getName() + "(" + bfp.getScode() + ")");
					}
				}
				
				BkOperator creator = this.bkOperatorService.get(bfpiovo.getCreator());
				if(creator != null){
					bfpiovo.setCreatorName(creator.getRealname());
				}
				
				if(bfpiovo.getChecker() != null && !"".equals(bfpiovo.getChecker())){
					BkOperator checker = this.bkOperatorService.get(bfpiovo.getChecker());
					if(checker != null){
						bfpiovo.setCheckerName(checker.getRealname());
					}
				}
				
				listVO.add(bfpiovo);
			}
		}
		return ResultManager.createDataResult(listVO, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @param companyAccount
	 * @param data
	 * @param totalAmount
	 * @param totalReturn
	 * @param poundage
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/operateEdit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "companyAccount", message="????????????", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "data", message="??????", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "totalReturn", message="?????????", type = DataType.POSITIVE_CURRENCY, maxLength = 10)
	@ActionParam(key = "poundage", message="?????????", type = DataType.POSITIVE_CURRENCY, maxLength = 10)
	@ActionParam(key = "remark", message="??????", type = DataType.STRING)
	@ActionParam(key = "redeemTime", type = DataType.STRING)
	@ActionParam(key = "receipt", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result operateEdit(String uuid, String companyAccount, String[] data, BigDecimal poundage, BigDecimal totalReturn, String remark, 
			String redeemTime, String receipt){
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		
		BankFinancialProductInvestOperate bfpio = bankFinancialProductInvestOperateService.get(uuid);
		if (bfpio != null) {
			BankFinancialProduct bfp = this.bankFinancialProductService.get(bfpio.getBankFinancialProduct());
			if(bfp == null){
				return ResultManager.createFailResult("?????????????????????");
			}
			
			if(!BankFinancialProductStatus.CHECKED.equals(bfp.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			CompanyAccount ca = this.companyAccountService.get(companyAccount);
			if(ca == null){
				return ResultManager.createFailResult("?????????????????????");
			}
			
			if(!CompanyAccountStatus.NORMAL.equals(ca.getStatus())){
				return ResultManager.createFailResult("?????????????????????");
			}
			
			List<BankFinancialProductInvestRecords> bfpirList = new ArrayList<BankFinancialProductInvestRecords>();
			Map<String, String> valueMap = new HashMap<String, String>();
			if(BankFinancialProductInvestOperateType.INVEST.equals(bfpio.getType())){
				if(!BankFinancialProductStage.COLLECT.equals(bfp.getStage())){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				
				if(poundage == null){
					return ResultManager.createFailResult("?????????????????????");
				}
				
				BigDecimal sum = BigDecimal.ZERO;
				for(String values : data){
					String[] value = values.split("_");
					String bankFinancialProductPublish = value[0];
					String amountString = value[1];
					BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
					
					if(amount.compareTo(BigDecimal.ZERO) > 0){
						BankFinancialProductInvestRecords bfpir = new BankFinancialProductInvestRecords();
						bfpir.setUuid(UUID.randomUUID().toString());
						bfpir.setBankFinancialProduct(bfp.getUuid());
						bfpir.setCompanyAccount(companyAccount);
						if(!Utlity.isPositiveCurrency4Web(amountString)){
							return ResultManager.createFailResult("???????????????????????????");
						}
						
						if(FundPublishUuid.CURRENT.equals(bankFinancialProductPublish)){
							BigDecimal currentBalance = this.fundPublishService.getAccountBalance();
							if(amount.compareTo(currentBalance) > 0){
								return ResultManager.createFailResult("?????????????????????????????????");
							}
							bfpir.setBankFinancialProductPublish(bankFinancialProductPublish);
						}else if(PlatformAccountUuid.BALANCE.equals(bankFinancialProductPublish)){
							bfpir.setBankFinancialProductPublish(bankFinancialProductPublish);
							PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
							PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
							if(amount.compareTo(pa.getTotalAmount().add(pai.getTotalAmount())) > 0){
								return ResultManager.createFailResult("????????????????????????????????????");
							}
						}else{
							BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(bankFinancialProductPublish);
							if(bfpp != null){
								bfpir.setBankFinancialProductPublish(bankFinancialProductPublish);
							}else{
								return ResultManager.createFailResult("???????????????????????????");
							}
							if(BankFinancialProductPublishStage.FINISHED.equals(bfpp.getStage()) || BankFinancialProductPublishStage.EXCEPTION.equals(bfpp.getStage())){
								return ResultManager.createFailResult(bfpp.getName() + "???????????????????????????");
							}
							if(amount.compareTo(bfpp.getAccountBalance()) > 0){
								return ResultManager.createFailResult("?????????????????????????????????");
							}
						}
						bfpir.setTotalAmount(amount);
						
						bfpir.setType(BankFinancialProductInvestRecordsType.INVEST);
						bfpir.setRemark(remark);
						bfpir.setCreator(currentOperator.getUuid());
						bfpir.setCreatetime(new Timestamp(System.currentTimeMillis()));
						bfpirList.add(bfpir);
						sum = sum.add(amount);
					}
				}
				
				if(sum.compareTo(BigDecimal.ZERO) <= 0){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				
				if(sum.add(poundage).compareTo(ca.getAccountBalance()) > 0){
					return ResultManager.createFailResult("???????????????????????????");
				}
				
				valueMap.put("dataList", JSONUtils.obj2json(bfpirList));
				valueMap.put("poundage", poundage.toString());
				valueMap.put("totalAmount", sum.toString());
				valueMap.put("remark", remark);
				
			}else if(BankFinancialProductInvestOperateType.REDEEM.equals(bfpio.getType())){		
				if(redeemTime == null){
					return ResultManager.createFailResult("????????????????????????");
				}
				if(totalReturn == null){
					return ResultManager.createFailResult("????????????????????????");
				}
				//????????????
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("stage", BankFinancialProductInvestStage.NORMAL);
				searchMap.put("companyAccount", companyAccount);
				searchMap.put("bankFinancialProduct", bfp.getUuid());
				//?????????????????????????????????????????????????????????
				List<Entity> bfpiList = bankFinancialProductInvestService.getListForPage(searchMap, 1, Integer.MAX_VALUE, null, BankFinancialProductInvest.class);
				if(bfpiList.size() < 1){
					return ResultManager.createFailResult("????????????????????????????????????");
				}
				
				BigDecimal sum = BigDecimal.ZERO;
				for(String values : data){
					String[] value = values.split("_");
					String bankFinancialProductPublish = value[0];
					String amountString = value[1];
					
					BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountString));
					
					if(amount.compareTo(BigDecimal.ZERO) > 0){
						BankFinancialProductInvestRecords bfpir = new BankFinancialProductInvestRecords();
						bfpir.setUuid(UUID.randomUUID().toString());
						bfpir.setBankFinancialProduct(bfp.getUuid());
						bfpir.setCompanyAccount(companyAccount);
						bfpir.setCreatetime(Timestamp.valueOf(redeemTime));
						if(!Utlity.isPositiveCurrency4Web(amountString)){
							return ResultManager.createFailResult("???????????????????????????");
						}
						bfpir.setBankFinancialProductPublish(bankFinancialProductPublish);
						
						for(Entity e : bfpiList){
							BankFinancialProductInvest bfpi = (BankFinancialProductInvest) e;
							if(bankFinancialProductPublish.equals(bfpi.getBankFinancialProductPublish())){
								if(amount.compareTo(bfpi.getAccountBalance()) > 0){
									return ResultManager.createFailResult("???????????????????????????????????????");
								}else{
									bfpir.setTotalAmount(amount);
								}
							}
						}
						
						bfpir.setType(BankFinancialProductInvestRecordsType.REDEEM);
						bfpir.setRemark(remark);
						bfpir.setCreator(currentOperator.getUuid());
						bfpir.setCreatetime(new Timestamp(System.currentTimeMillis()));
						bfpirList.add(bfpir);
						sum = sum.add(amount);
					}
				}
				
				if(sum.compareTo(BigDecimal.ZERO) <= 0){
					return ResultManager.createFailResult("??????????????????????????????");
				}
				
				valueMap.put("dataList", JSONUtils.obj2json(bfpirList));
				valueMap.put("totalReturn", totalReturn.toString());
				valueMap.put("totalAmount", sum.toString());
				valueMap.put("remark", remark);
			}else{
				return ResultManager.createDataResult("??????????????????????????????????????????");
			}
			
			//?????????????????????
			bfpio.setValue(JSONUtils.obj2json(valueMap));
			bfpio.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			bfpio.setReceipt(receipt);
			
			bankFinancialProductInvestOperateService.update(bfpio);
			return ResultManager.createDataResult("???????????????????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateDelete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateDelete(String uuid) {
		//????????????????????????????????????
		BankFinancialProductInvestOperate bfpio = bankFinancialProductInvestOperateService.get(uuid);
		if(bfpio != null){
			if(!BankFinancialProductInvestOperateStatus.DRAFT.equals(bfpio.getStatus()) && !BankFinancialProductInvestOperateStatus.UNPASSED.equals(bfpio.getStatus())){
				return ResultManager.createFailResult("??????????????????");
			}
			bfpio.setStatus(BankFinancialProductInvestOperateStatus.DELETED);
			bankFinancialProductInvestOperateService.update(bfpio);
			return ResultManager.createSuccessResult("???????????????");
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/operateSubmitCheck", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result operateSubmitCheck(String uuid) {
		//????????????????????????????????????
		BankFinancialProductInvestOperate bfpio = bankFinancialProductInvestOperateService.get(uuid);
		if(bfpio != null){
			if(BankFinancialProductInvestOperateStatus.CHECKED.equals(bfpio.getStatus())){
				return ResultManager.createFailResult("????????????????????????");
			}
			bfpio.setSubmittime(new Timestamp(System.currentTimeMillis()));
			bfpio.setStatus(BankFinancialProductInvestOperateStatus.UNCHECKED);
			bankFinancialProductInvestOperateService.update(bfpio);
			return ResultManager.createSuccessResult("?????????????????????");
		}
		else{
			return ResultManager.createFailResult("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
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
		if(!BankFinancialProductInvestOperateStatus.CHECKED.equals(status) && !BankFinancialProductInvestOperateStatus.UNPASSED.equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		
		//??????????????????
		BankFinancialProductInvestOperate bfpio = bankFinancialProductInvestOperateService.get(uuid);
		if(bfpio != null){
			if(!BankFinancialProductInvestOperateStatus.UNCHECKED.equals(bfpio.getStatus())){
				return ResultManager.createFailResult("???????????????????????????");
			}
			//??????????????????
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
			
			if(bfpio.getCreator().equals(currentOperator.getUuid())){
				return ResultManager.createFailResult("???????????????????????????????????????");
			}
			
			bfpio.setChecker(currentOperator.getUuid());
			bfpio.setChecktime(new Timestamp(System.currentTimeMillis()));
			bfpio.setStatus(status);
			bfpio.setReason(reason);
			try {
				bankFinancialProductInvestOperateService.check(bfpio);
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
	 * ???????????????????????????(??????)
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
		//??????????????????
		List<Entity> list = bankFinancialProductInvestOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????(?????????)
	 * @return
	 */
	@RequestMapping(value = "/operateCheckStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result operateCheckStatusList() {	
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", "all");
		//??????????????????
		List<Entity> list = bankFinancialProductInvestOperateService.getStatusList(searchMap, StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????(??????)
	 * @param status
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
		
		List<Entity> list = bankFinancialProductInvestOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
	
	/**
	 * ???????????????????????????(?????????)
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/operateCheckTypeList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ResponseBody
	public Result operateCheckTypeList(String status) {
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", status);
		
		List<Entity> list = bankFinancialProductInvestOperateService.getTypeList(searchMap,StstusCountVO.class);
		return ResultManager.createDataResult(list,list.size());
	}
}
