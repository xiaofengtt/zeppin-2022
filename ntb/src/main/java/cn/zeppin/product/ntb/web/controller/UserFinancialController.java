package cn.zeppin.product.ntb.web.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishDailyService;
import cn.zeppin.product.ntb.backadmin.service.api.IFundPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorBankcardService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorCouponHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorIdcardImgService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyAgreementService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement.InvestorProductBuyAgreementType;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.web.vo.InvestorAccountVO;
import cn.zeppin.product.ntb.web.vo.InvestorCouponHistoryVO;
import cn.zeppin.product.ntb.web.vo.InvestorProductBuyDetailsVO;
import cn.zeppin.product.ntb.web.vo.InvestorProductBuyHistoryVO;
import cn.zeppin.product.ntb.web.vo.InvestorProductBuyVO;
import cn.zeppin.product.utility.Utlity;

@Controller
@RequestMapping(value = "/web/financial")
public class UserFinancialController extends BaseController{
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IInvestorIdcardImgService investorIdcardImgService;
	
	@Autowired
	private IInvestorBankcardService investorBankcardService;
	
	@Autowired
	private IInvestorAccountHistoryService investorAccountHistoryService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IInvestorProductBuyAgreementService investorProductBuyAgreementService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IInvestorProductBuyService investorProductBuyService;
	
	@Autowired
	private IInvestorCouponHistoryService investorCouponHistoryService;
	
	@Autowired
	private IFundPublishService fundPublishService;
	
	@Autowired
	private IFundPublishDailyService fundPublishDailyService;
	
	/**
	 * 获取用户账户信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ResponseBody
	public Result get(String uuid){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		InvestorAccountVO ivo = new InvestorAccountVO(investor);
		String monthStr = "0.00";
		Double month = this.investorAccountHistoryService.getTotalReturnByMonth4Investor(uuid);
		if(month != null){
			monthStr = Utlity.numFormat4UnitDetail(BigDecimal.valueOf(month));
		}
		String yearStr = "0.00";
		Double year = this.investorAccountHistoryService.getTotalReturnByYear4Investor(uuid);
		if(year != null){
			yearStr = Utlity.numFormat4UnitDetail(BigDecimal.valueOf(year));
		}
		
		FundPublish fp = this.fundPublishService.get(FundPublishUuid.CURRENT);
		BigDecimal netValue = BigDecimal.ONE;
		if(fp != null && fp.getNetWorth() != null){
			netValue = fp.getNetWorth();
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		HashMap<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("endtime", Utlity.timeSpanToString(now));
		List<Entity> list = this.fundPublishDailyService.getListForPage(inputParams, 0, 2, null, FundPublishDaily.class);
		if(list.size() == 2){
			BigDecimal today = ((FundPublishDaily)list.get(0)).getNetValue();
			BigDecimal yesterday = ((FundPublishDaily)list.get(1)).getNetValue();
			ivo.setTotalReturnBuyDay(Utlity.numFormat4UnitDetail(investor.getCurrentAccountYesterday().multiply(today.subtract(yesterday))));
		}else{
			ivo.setTotalReturnBuyDay("0.00");
		}
		ivo.setCurrentAccount(Utlity.numFormat4UnitDetail(investor.getCurrentAccount().multiply(netValue)));
		
		ivo.setTotalReturnBuyMonth(monthStr);
		ivo.setTotalReturnBuyYear(yearStr);
		//20180807计算总额
		String totalAmount = Utlity.numFormat4UnitDetail(investor.getTotalInvest().add(investor.getAccountBalance()).add(investor.getCurrentAccount().multiply(netValue)));
		ivo.setTotalAmount(totalAmount);
		return ResultManager.createDataResult(ivo);
	}
	
	/**
	 * 获取用户持仓列表
	 * @param uuid
	 * @param stage 交易中-transaction
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "stage", type = DataType.STRING, message="持仓阶段", required = true)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sorts", type = DataType.STRING)
	@ResponseBody
	public Result getList(String uuid, String stage, Integer pageNum, Integer pageSize, String sorts){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("investor", uuid);
		inputParams.put("stage", stage);
		List<Entity> list = this.investorProductBuyService.getListForPage(inputParams, pageNum, pageSize, null, InvestorProductBuy.class);
		
		List<InvestorProductBuyVO> listVO = new ArrayList<InvestorProductBuyVO>();
		
		if(list != null && list.size() > 0){
			for(Entity entity : list){
				InvestorProductBuy ipbr = (InvestorProductBuy) entity;
				InvestorProductBuyVO ipbrvo = new InvestorProductBuyVO(ipbr);
				BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(ipbr.getProduct());
				if(bfpp == null){
					return ResultManager.createFailResult("银行理财产品信息不存在！");
				}
				Bank bank = this.bankService.get(bfpp.getCustodian());
				if(bank == null){
					return ResultManager.createFailResult("银行信息不存在！");
				}
				ipbrvo.setBankName(bank.getShortName());
				Resource iconColor = this.resourceService.get(bank.getIconColor());
				if(iconColor != null){
					ipbrvo.setIconColorUrl(iconColor.getUrl());
				} else {
					ipbrvo.setIconColorUrl("");
				}
				
				ipbrvo.setProductName(bfpp.getShortname());
				ipbrvo.setProductScode(bfpp.getScode());
				
				ipbrvo.setMaturityDate(Utlity.timeSpanToDateString(bfpp.getMaturityDate()));
				ipbrvo.setValueDate(Utlity.timeSpanToDateString(bfpp.getValueDate()));
				if(bfpp.getTargetAnnualizedReturnRate() != null){
					ipbrvo.setTargetAnnualizedReturnRate(bfpp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 
				}else{
					ipbrvo.setTargetAnnualizedReturnRate("0.00");
				}
				ipbrvo.setRealReturnRate(bfpp.getRealReturnRate() == null ? "" : bfpp.getRealReturnRate().toString());
				ipbrvo.setRealReturnRateCN(bfpp.getRealReturnRate() == null ? "" : bfpp.getRealReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
//				//计算收益(暂无)
//				ipbrvo.setTotalReturn("0.00");
				//计算预计到账时间--当前规则是到期日时间延后一天
				if(bfpp.getMaturityDate() != null){
					long oneday = 24*60*60*1000;
					Timestamp returntime = new Timestamp(bfpp.getMaturityDate().getTime()+oneday);
					ipbrvo.setIncomeDate(Utlity.timeSpanToDateString(returntime));
				} else {
					ipbrvo.setIncomeDate("--");
				}
				
				//20171220增加爱优惠券使用标识显示参数
				//需要查询是否使用过优惠券
				inputParams.clear();
				inputParams.put("investorProductBuy", ipbr.getUuid());
				List<Entity> listCoupon = this.investorCouponHistoryService.getListForPage(inputParams, -1, -1, null, InvestorCouponHistoryVO.class);
				if(listCoupon != null && !listCoupon.isEmpty()){
					for(Entity e : listCoupon){
						InvestorCouponHistoryVO ichvo = (InvestorCouponHistoryVO)e;
						if(CouponType.CASH.equals(ichvo.getCouponType())){
							ipbrvo.setFlagCashCoupon(true);
						} else if (CouponType.INTERESTS.equals(ichvo.getCouponType())) {
							ipbrvo.setFlagInterestsCoupon(true);
						}
					}
				}
				
				listVO.add(ipbrvo);
			}
		}
		
		return ResultManager.createDataResult(listVO, pageNum, pageSize, listVO.size());
	}
	
	/**
	 * 获取用户持仓详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="用户编号", required = true)
	@ActionParam(key = "financial", type = DataType.STRING, message="持仓记录编号", required = true)
	@ResponseBody
	public Result getInfo(String uuid, String financial){
		Investor investor = this.investorService.get(uuid);
		if(investor == null){
			return ResultManager.createFailResult("用户信息不存在！");
		}
		InvestorProductBuy ipbr = this.investorProductBuyService.get(financial);
		if(ipbr == null){
			return ResultManager.createFailResult("信息不存在");
		}
		InvestorProductBuyDetailsVO ipbrvo = new InvestorProductBuyDetailsVO(ipbr);
		BankFinancialProductPublish bfpp = this.bankFinancialProductPublishService.get(ipbr.getProduct());
		if(bfpp == null){
			return ResultManager.createFailResult("银行理财产品信息不存在！");
		}
		Bank bank = this.bankService.get(bfpp.getCustodian());
		if(bank == null){
			return ResultManager.createFailResult("银行信息不存在！");
		}
		ipbrvo.setBankName(bank.getShortName());
		ipbrvo.setProductName(bfpp.getShortname());
		ipbrvo.setProductScode(bfpp.getScode());
		ipbrvo.setFlagBuy(bfpp.getFlagBuy());
		if(bfpp.getTargetAnnualizedReturnRate() != null){
			ipbrvo.setTargetAnnualizedReturnRate(bfpp.getTargetAnnualizedReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 
		}else{
			ipbrvo.setTargetAnnualizedReturnRate("0.00");
		}
		ipbrvo.setTerm(bfpp.getTerm()+"");
		
		ipbrvo.setMaturityDate(Utlity.timeSpanToDateString(bfpp.getMaturityDate()));
		ipbrvo.setValueDate(Utlity.timeSpanToDateString(bfpp.getValueDate()));
		ipbrvo.setRealReturnRate(bfpp.getRealReturnRate() == null ? "" : bfpp.getRealReturnRate().toString());
		ipbrvo.setRealReturnRateCN(bfpp.getRealReturnRate() == null ? "" : bfpp.getRealReturnRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
//		//计算收益(暂无)
//		ipbrvo.setTotalReturn("0.00");
//		//添加订单信息
//		InvestorAccountHistory iah = this.investorAccountHistoryService.get(ipbrvo.getBill());
//		if(iah == null){
//			return ResultManager.createFailResult("用户订单信息不存在！");
//		}
//		ipbrvo.setOrderNum(iah.getOrderNum());
//		ipbrvo.setOrderType(iah.getOrderType());
//		if(InvestorAccountHistoryOrderType.PAY_TYPE_WECHART.equals(iah.getOrderType())){
//			ipbrvo.setOrderTypeCN("微信支付");
//		} else if (InvestorAccountHistoryOrderType.PAY_TYPE_ALIPAY.equals(iah.getOrderType())) {
//			ipbrvo.setOrderTypeCN("支付宝支付");
//		} else if (InvestorAccountHistoryOrderType.PAY_TYPE_BALANCE.equals(iah.getOrderType())) {
//			ipbrvo.setOrderTypeCN("余额支付");
//		} else if (InvestorAccountHistoryOrderType.PAY_TYPE_BANKCARD.equals(iah.getOrderType())) {
//			ipbrvo.setOrderTypeCN("银行卡支付");
//		} else {
//			ipbrvo.setOrderTypeCN("");
//		}
		//PDF
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("investor", investor.getUuid());
		inputParams.put("records", ipbr.getProduct());
		inputParams.put("type", InvestorProductBuyAgreementType.BANKPRODUCT);
		List<Entity> list = this.investorProductBuyAgreementService.getListForPage(inputParams, -1, -1, null, InvestorProductBuyAgreement.class);
		if (list != null && list.size() > 0) {
			InvestorProductBuyAgreement ipba = (InvestorProductBuyAgreement) list.get(0);
			ipbrvo.setAgreementName("《"+ipba.getName()+"》");
			ipbrvo.setAgreementUrl(ipba.getUrl());
		} else {
			ipbrvo.setAgreementName("生成中...");
			ipbrvo.setAgreementUrl("");
		}
		//计算预计到账时间--当前规则是到期日时间延后一天
		if(bfpp.getMaturityDate() != null){
			long oneday = 24*60*60*1000;
			Timestamp returntime = new Timestamp(bfpp.getMaturityDate().getTime()+oneday);
			ipbrvo.setIncomeDate(Utlity.timeSpanToDateString(returntime));
		} else {
			ipbrvo.setIncomeDate("--");
		}
		
		//查询交易记录列表 若使用优惠券则增加优惠券使用信息
		inputParams.clear();
		inputParams.put("investor", investor.getUuid());
		inputParams.put("product", ipbr.getProduct());
		inputParams.put("type", InvestorAccountHistoryType.BUY);
		List<Entity> listIah = this.investorAccountHistoryService.getListForPage(inputParams, -1, -1, "createtime asc", InvestorAccountHistory.class);
		List<InvestorProductBuyHistoryVO> listIpbhvo = new ArrayList<InvestorProductBuyHistoryVO>();
		if(listIah != null && !listIah.isEmpty()){
			for(Entity e : listIah){
				InvestorAccountHistory iah = (InvestorAccountHistory)e;
				InvestorProductBuyHistoryVO ipbhvo = new InvestorProductBuyHistoryVO(iah);
				inputParams.clear();
				inputParams.put("investorProductBuy", ipbr.getUuid());
				inputParams.put("investorAccountHistory", iah.getUuid());
				List<Entity> listCoupon = this.investorCouponHistoryService.getListForPage(inputParams, -1, -1, null, InvestorCouponHistoryVO.class);
				if(listCoupon != null && !listCoupon.isEmpty()){
					InvestorCouponHistoryVO ichvo = (InvestorCouponHistoryVO)listCoupon.get(0);
					ipbhvo.setCoupon(ichvo.getCoupon());
					ipbhvo.setCouponName(ichvo.getCouponName());
					ipbhvo.setCouponPrice(ichvo.getCouponPrice());
					ipbhvo.setCouponType(ichvo.getCouponType());
				}
				listIpbhvo.add(ipbhvo);
			}
		}
		ipbrvo.setAccountHistory(listIpbhvo);
		return ResultManager.createDataResult(ipbrvo);
	}
	
}
