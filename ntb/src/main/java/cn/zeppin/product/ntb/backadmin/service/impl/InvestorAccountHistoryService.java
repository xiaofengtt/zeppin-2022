/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IAutoAliTransferProcessDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICompanyAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICouponStrategyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorBankcardDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorCouponDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorCouponHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorInformationDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyAgreementDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorRedPacketDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.vo.CouponLessVO;
import cn.zeppin.product.ntb.core.entity.AutoAliTransferProcess;
import cn.zeppin.product.ntb.core.entity.AutoAliTransferProcess.AutoAliTransferProcessStatus;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountUuid;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.CouponStrategy.CouponStrategyStatus;
import cn.zeppin.product.ntb.core.entity.CouponStrategy.CouponStrategyUuid;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon.InvestorCouponStatus;
import cn.zeppin.product.ntb.core.entity.InvestorCouponHistory;
import cn.zeppin.product.ntb.core.entity.InvestorCouponHistory.InvestorCouponHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationStatus;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationTitle;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyProductType;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyStage;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement.InvestorProductBuyAgreementStatus;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement.InvestorProductBuyAgreementType;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyResultStatus;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeHistoryDAO;
import cn.zeppin.product.utility.AliUtlity;
import cn.zeppin.product.utility.DataTimeConvert;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.PDFUtlity;
import cn.zeppin.product.utility.RMBUtlity;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.ali.Alipay;
import cn.zeppin.product.utility.ali.Alipay.AccountLogList;
import cn.zeppin.product.utility.ali.Alipay.AccountPageQueryResult;
import cn.zeppin.product.utility.ali.Alipay.AccountQueryAccountLogVO;
import cn.zeppin.product.utility.ali.Alipay.Response;
import cn.zeppin.product.utility.reapal.ReapalUtlity;
import cn.zeppin.product.utility.reapal.data.WithdrawData;
import cn.zeppin.product.utility.reapal.data.WithdrawData.WithdrawDateCurrency;
import cn.zeppin.product.utility.reapal.data.WithdrawData.WithdrawDateProperties;
import cn.zeppin.product.utility.reapal.data.WithdrawDataArray;

/**
 * @author hehe
 *
 */
@Service
public class InvestorAccountHistoryService extends BaseService implements IInvestorAccountHistoryService {

	@Autowired
	private IInvestorAccountHistoryDAO investorAccountHistoryDAO;
	
	@Autowired
	private IQcbEmployeeHistoryDAO qcbEmployeeHistoryDAO;
	
	@Autowired
	private IOrderinfoByThirdpartyDAO orderinfoByThirdpartyDAO;
	
	@Autowired
	private IInvestorDAO investorDAO;
	
	@Autowired
	private IInvestorProductBuyAgreementDAO investorProductBuyAgreementDAO;
	
	@Autowired
	private IInvestorProductBuyDAO investorProductBuyDAO;
	
	@Autowired
	private IBankFinancialProductPublishDAO bankFinancialProductPublishDAO;
	
	@Autowired
	private IBankDAO bankDAO;
	
	@Autowired
	private ICompanyAccountDAO companyAccountDAO;
	
	@Autowired
	private ICompanyAccountHistoryDAO companyAccountHistoryDAO;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Autowired
	private IInvestorBankcardDAO investorBankcardDAO;
	
	@Autowired
	private IAutoAliTransferProcessDAO autoAliTransferProcessDao;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Autowired
	private IInvestorInformationDAO investorInformationDAO;
	
	@Autowired
	private IInvestorCouponDAO investorCouponDAO;
	
	@Autowired
	private IInvestorCouponHistoryDAO investorCouponHistoryDAO;
	
	@Autowired
	private ICouponStrategyDAO couponStrategyDAO;
	
	@Autowired
	private IInvestorRedPacketDAO investorRedPacketDAO;
	
	@Override
	public InvestorAccountHistory insert(InvestorAccountHistory investorAccountHistory) {
		return investorAccountHistoryDAO.insert(investorAccountHistory);
	}

	@Override
	public InvestorAccountHistory delete(InvestorAccountHistory investorAccountHistory) {
		return investorAccountHistoryDAO.delete(investorAccountHistory);
	}

	@Override
	public InvestorAccountHistory update(InvestorAccountHistory investorAccountHistory) {
		return investorAccountHistoryDAO.update(investorAccountHistory);
	}

	@Override
	public InvestorAccountHistory get(String uuid) {
		return investorAccountHistoryDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询investorAccountHistory结果列表(带分页、排序)
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return investorAccountHistoryDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return investorAccountHistoryDAO.getCount(inputParams);
	}

	@Override
	public void insertwechart(OrderinfoByThirdparty obt, InvestorAccountHistory iah) {
		this.orderinfoByThirdpartyDAO.insert(obt);
		this.investorAccountHistoryDAO.insert(iah);
	}

	@Override
	public HashMap<String, Object> insertWechartNotice(Map<String, Object> map) throws ParseException, TransactionException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String out_trade_no = map.get("out_trade_no") == null ? "" : map.get("out_trade_no").toString();
		String transaction_id = map.get("transaction_id") == null ? "" : map.get("transaction_id").toString();
		String time_end = map.get("time_end") == null ? "" : map.get("time_end").toString();
		String result_code = map.get("result_code") == null ? "" : map.get("result_code").toString();
		String err_code = map.get("err_code") == null ? "" : map.get("err_code").toString();
		String err_code_des = map.get("err_code_des") == null ? "" : map.get("err_code_des").toString();
		String return_msg = map.get("return_msg") == null ? "" : map.get("return_msg").toString();
		String fee = map.get("total_fee") == null ? "" : map.get("total_fee").toString();
		BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("orderNum", out_trade_no);
		List<Entity> list = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
		OrderinfoByThirdparty obt = null;
		if(list != null && list.size() > 0){
			obt = (OrderinfoByThirdparty) list.get(0);
			obt.setPayNum(transaction_id);
			obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
			obt.setStatus(result_code);
			obt.setErrCode(err_code == null ? "" : err_code);
			obt.setErrCodeDes(err_code_des == null ? "" : err_code_des);
			obt.setMessage(return_msg == null ? "" : return_msg);
			this.orderinfoByThirdpartyDAO.update(obt);
		} else {
			message = "订单未找到";
			throw new TransactionException(message);
		}
		if(!OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(result_code)){//不是成功状态 直接返回信息
			message = "OK";
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
		}
		inputParams.clear();
		inputParams.put("order", obt.getUuid());
		inputParams.put("investor", obt.getInvestor());
		inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
		List<Entity> listHistory = this.investorAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
		if(listHistory != null && listHistory.size() > 0){
			InvestorAccountHistory iah = (InvestorAccountHistory) listHistory.get(0);
			if(!InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
				if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(result_code)){
					iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					Investor investor = this.investorDAO.get(iah.getInvestor());
					if(InvestorAccountHistoryType.INCOME.equals(iah.getType())){
						//充值、利息、返还本金进个人账号
						BigDecimal total = investor.getAccountBalance();
//							BigDecimal totalAmount = investor.getTotalAmount();
						total = total.add(total_fee.divide(BigDecimal.valueOf(100)));
//							totalAmount = totalAmount.add(total_fee.divide(BigDecimal.valueOf(100)));
						investor.setAccountBalance(total);//更新余额
//							investor.setTotalAmount(totalAmount);//更新账户总资产
//						iah.setAccountBalance(total);
						
						//更新企业账户余额
						CompanyAccount ca = this.companyAccountDAO.get(iah.getCompanyAccount());
						if(ca == null){
							message = "账户信息错误";
							throw new TransactionException(message);
						}
						BigDecimal accountTotal = ca.getAccountBalance();
						accountTotal = accountTotal.add(total_fee.divide(BigDecimal.valueOf(100)));
						//更新系统总帐户余额
						PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
						BigDecimal totalAmount = pa.getTotalAmount();
						totalAmount = totalAmount.add(total_fee.divide(BigDecimal.valueOf(100)));
						
						//更新系统可用余额
						PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
						BigDecimal totalAmountf = paf.getTotalAmount();
//						totalAmountf = totalAmountf.add(total_fee.divide(BigDecimal.valueOf(100)));
						
						//更新系统用户总帐户余额(只更新金额 不算入手续费)
						PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
						BigDecimal totalAmounti = pai.getTotalAmount();
						totalAmounti = totalAmounti.add(total_fee.divide(BigDecimal.valueOf(100)));
						
						if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
							accountTotal = accountTotal.subtract(iah.getPoundage());
							totalAmount = totalAmount.subtract(iah.getPoundage());
							totalAmountf = totalAmountf.subtract(iah.getPoundage());
						}
						pa.setTotalAmount(totalAmount);
						paf.setTotalAmount(totalAmountf);
						pai.setTotalAmount(totalAmounti);
						ca.setAccountBalance(accountTotal);
						this.companyAccountDAO.update(ca);
						this.platformAccountDAO.update(pa);
						this.platformAccountDAO.update(paf);
						this.platformAccountDAO.update(pai);
						//增加公司账户交易记录--用户充值
						CompanyAccountHistory cat = new CompanyAccountHistory();
						cat.setUuid(UUID.randomUUID().toString());
						cat.setCompanyAccountIn(iah.getCompanyAccount());//用户充值
						cat.setType(CompanyAccountHistoryType.FILLIN);
						cat.setInvestor(investor.getUuid());
						cat.setTotalAmount(total_fee.divide(BigDecimal.valueOf(100)));
						cat.setPoundage(iah.getPoundage());
						cat.setPurpose("用户充值");
						cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
						
						cat.setCreator(investor.getUuid());
						cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
						
						cat.setInvestorAccountHistory(iah.getUuid());
						
						//20180622增加记录本次余额信息
						cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
						
//						this.companyAccountHistoryDAO.insert(cat);
						if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
							//增加公司账户交易记录--费用录入（扣除交易手续费）
							CompanyAccountHistory catp = new CompanyAccountHistory();
							catp.setUuid(UUID.randomUUID().toString());
							catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
							catp.setType(CompanyAccountHistoryType.EXPEND);
							catp.setInvestor(investor.getUuid());
							catp.setTotalAmount(iah.getPoundage());
							catp.setPoundage(BigDecimal.ZERO);
							catp.setPurpose("用户充值--手续费扣除");
							catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
							
							catp.setCreator(investor.getUuid());
							catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
							catp.setInvestorAccountHistory(iah.getUuid());
							catp.setRelated(cat.getUuid());
							cat.setRelated(catp.getUuid());
							
							//20180622增加记录本次余额信息
							catp.setAccountBalance(ca.getAccountBalance());
							
							this.companyAccountHistoryDAO.insert(cat);
							this.companyAccountHistoryDAO.insert(catp);
						} else {
							this.companyAccountHistoryDAO.insert(cat);
						}
						
						//充值成功，发送通知短信
						MobileCode mc = new MobileCode();
						String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(iah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(iah.getIncome())+"元充值申请已处理成功，请注意查询余额。";
						String mobile = investor.getMobile();
						String codeInfo = Utlity.getCaptcha();
						mc.setCode(codeInfo);
						mc.setContent(content);
						mc.setMobile(mobile);
						mc.setCreator(investor.getUuid());
						mc.setStatus(MobileCodeStatus.DISABLE);
						mc.setType(MobileCodeTypes.NOTICE);
						mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
						mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
						mc.setUuid(UUID.randomUUID().toString());
						this.insertSmsInfo(mc);
						
//						content = "【牛投理财】"+content;
						InvestorInformation ii = new InvestorInformation();
						ii.setCreator(investor.getUuid());
						ii.setStatus(InvestorInformationStatus.UNREAD);
						ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
						ii.setUuid(UUID.randomUUID().toString());
						ii.setInfoText(content);
						ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
						ii.setInvestor(investor.getUuid());
						this.investorInformationDAO.insert(ii);
					} 
//						else if (InvestorAccountHistoryType.DIVIDEND.equals(iah.getType())) {//利息 增加历史总收益 余额 账户总资产
//							BigDecimal total = investor.getAccountBalance();
//							BigDecimal totalAmount = investor.getTotalAmount();
//							BigDecimal income = investor.getTotalReturn();
//							total = total.add(total_fee.divide(BigDecimal.valueOf(100)));
//							totalAmount = totalAmount.add(total_fee.divide(BigDecimal.valueOf(100)));
//							income = income.add(total_fee.divide(BigDecimal.valueOf(100)));
//							investor.setAccountBalance(total);//更新余额
//							investor.setTotalAmount(totalAmount);//更新账户总资产
//							investor.setTotalReturn(income);//更新历史总收入
//							iah.setAccountBalance(total);
//						} else if (InvestorAccountHistoryType.RETURN.equals(iah.getType())) {//返还本金 增加余额
//							BigDecimal total = investor.getAccountBalance();
//							total = total.add(total_fee.divide(BigDecimal.valueOf(100)));
//							investor.setAccountBalance(total);//更新余额
//							iah.setAccountBalance(total);
//						} else if (InvestorAccountHistoryType.BUY.equals(iah.getType())) {//购买理财 扣除余额
//							//购买、提现，需要从个人账户里扣钱
//							BigDecimal total = investor.getAccountBalance();
//							total = total.subtract(total_fee.divide(BigDecimal.valueOf(100)));
//							investor.setAccountBalance(total);
//							iah.setAccountBalance(total);
//						} else if (InvestorAccountHistoryType.WITHDRAW.equals(iah.getType())) {//提现 扣除账户总资产
//							//购买、提现，需要从个人账户里扣钱
//							BigDecimal total = investor.getAccountBalance();
//							BigDecimal totalAmount = investor.getTotalAmount();
//							total = total.subtract(total_fee.divide(BigDecimal.valueOf(100)));
//							totalAmount = totalAmount.subtract(total_fee.divide(BigDecimal.valueOf(100)));
//							investor.setAccountBalance(total);
//							investor.setTotalAmount(totalAmount);//更新账户总资产
//							iah.setAccountBalance(total);
//						} 
					else {
						message = "账单类型错误";
						throw new TransactionException(message);
					}
					this.investorDAO.update(investor);
				} else {
					iah.setStatus(err_code);
				}
				this.investorAccountHistoryDAO.update(iah);
				
				System.out.println("成功");
				result.put("result", resultFlag);
				result.put("message", message);
				return result;
			}else{
				message = "账单错误";
				throw new TransactionException(message);
			}
			
			
		} else {
			message = "账单错误";
			throw new TransactionException(message);
//				resultFlag = false;
//				result.put("result", resultFlag);
//				result.put("message", message);
//				return result;
		}
		
	}

	@Override
	public void insertProductBuy4Balance(MobileCode omc, BankFinancialProductPublish bfpp, Investor investor, InvestorAccountHistory iah, InvestorProductBuyAgreement ipba, InvestorProductBuy ipb, Boolean isUpdate, Boolean isUpdate2, PlatformAccount pai, MobileCode mc, InvestorInformation iii, InvestorCoupon ic) throws TransactionException {
		omc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeDAO.update(omc);
		
		this.bankFinancialProductPublishDAO.update(bfpp);
		this.investorDAO.update(investor);
		this.investorAccountHistoryDAO.insert(iah);
		
		if(isUpdate){
			this.investorProductBuyDAO.update(ipb);
			
		} else {
			this.investorProductBuyDAO.insert(ipb);
		}
		if(investorProductBuyAgreementDAO.getCheckScode(ipba.getScode())){
			throw new TransactionException("手速太快，服务器未响应！");
		}
		if(isUpdate2){
			this.investorProductBuyAgreementDAO.update(ipba);
		} else {
			this.investorProductBuyAgreementDAO.insert(ipba);
		}
		this.platformAccountDAO.update(pai);
		
		this.investorInformationDAO.insert(iii);
		
		if(ic != null){
			this.investorCouponDAO.update(ic);
			InvestorCouponHistory ich = new InvestorCouponHistory();
			ich.setCoupon(ic.getCoupon());
			ich.setCreatetime(new Timestamp(System.currentTimeMillis()));
			ich.setCreator(investor.getUuid());
			ich.setPrice(iah.getPay());
			ich.setStatus(InvestorCouponHistoryStatus.NORMAL);
			ich.setUuid(UUID.randomUUID().toString());
			ich.setInvestorProductBuy(ipb.getUuid());
			ich.setInvestorAccountHistory(iah.getUuid());
			ich.setDividend(BigDecimal.ZERO);
			this.investorCouponHistoryDAO.insert(ich);
		}
		this.checkCouponStrategy(investor);
		this.insertSmsInfo(mc);
	}

	@Override
	public void insertProductBuy4Wechart(BankFinancialProductPublish bfpp, OrderinfoByThirdparty obt, InvestorAccountHistory iah, InvestorAccountHistory iahBuy) {
		this.bankFinancialProductPublishDAO.update(bfpp);
		this.orderinfoByThirdpartyDAO.insert(obt);
		this.investorAccountHistoryDAO.insert(iah);
		this.investorAccountHistoryDAO.insert(iahBuy);
	}

	@Override
	public HashMap<String, Object> insertWechartNotice4Buy(Map<String, Object> map) throws ParseException, TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String out_trade_no = map.get("out_trade_no") == null ? "" : map.get("out_trade_no").toString();
		String transaction_id = map.get("transaction_id") == null ? "" : map.get("transaction_id").toString();
		String time_end = map.get("time_end") == null ? "" : map.get("time_end").toString();
		String result_code = map.get("result_code") == null ? "" : map.get("result_code").toString();
		String err_code = map.get("err_code") == null ? "" : map.get("err_code").toString();
		String err_code_des = map.get("err_code_des") == null ? "" : map.get("err_code_des").toString();
		String return_msg = map.get("return_msg") == null ? "" : map.get("return_msg").toString();
		String fee = map.get("total_fee") == null ? "" : map.get("total_fee").toString();
		BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("orderNum", out_trade_no);
		List<Entity> list = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
		OrderinfoByThirdparty obt = null;
		if(list != null && list.size() > 0){
			obt = (OrderinfoByThirdparty) list.get(0);
			if(!OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
				obt.setPayNum(transaction_id);
				obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
				obt.setStatus(result_code);
				obt.setErrCode(err_code == null ? "" : err_code);
				obt.setErrCodeDes(err_code_des == null ? "" : err_code_des);
				obt.setMessage(return_msg == null ? "" : return_msg);
				this.orderinfoByThirdpartyDAO.update(obt);
			}
		} else {
			message = "订单未找到";
			throw new TransactionException(message);
		}

		inputParams.clear();
		inputParams.put("order", obt.getUuid());
		inputParams.put("investor", obt.getInvestor());
		inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
		List<Entity> listHistory = this.investorAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
		if(listHistory != null && listHistory.size() > 0){
			InvestorAccountHistory iah = null;
			InvestorAccountHistory iahBuy = null;
			for(Entity entity: listHistory){
				InvestorAccountHistory ii = (InvestorAccountHistory)entity;
				if(InvestorAccountHistoryType.INCOME.equals(ii.getType())){
					iah = ii;
				}
				if(InvestorAccountHistoryType.BUY.equals(ii.getType())){
					iahBuy = ii;
				}
			}
			if(iah == null || iahBuy == null){
				message = "账单错误";
				throw new TransactionException(message);
			}
			
			if(!OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(result_code)){//不是成功状态 算支付失败 并返回信息
				iah.setStatus(InvestorAccountHistoryStatus.FAIL);
				iahBuy.setStatus(InvestorAccountHistoryStatus.FAIL);
				if(InvestorProductBuyProductType.BANK_PRODUCT.equals(iahBuy.getProductType())){
					BankFinancialProductPublish bfpp = this.bankFinancialProductPublishDAO.get(iahBuy.getProduct());
					if (bfpp == null) {
						message = "银行理财产品信息不存在！";
						throw new TransactionException(message);
					}
					bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(iahBuy.getPay()));//支付失败 募集余额减少
					this.bankFinancialProductPublishDAO.update(bfpp);
				}
				this.investorAccountHistoryDAO.update(iah);
				this.investorAccountHistoryDAO.update(iahBuy);
				
				message = "OK";
				resultFlag = true;
				result.put("result", resultFlag);
				result.put("message", message);
				return result;
			}
			Investor investor = this.investorDAO.get(iah.getInvestor());
			//更新系统用户总帐户余额(只更新金额 不算入手续费)
			PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
			if(!InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
				
				if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(result_code)){
					iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					//只更新账户总资产
					BigDecimal total = investor.getAccountBalance();
//						BigDecimal totalInvest = investor.getTotalInvest();
					total = total.add(total_fee.divide(BigDecimal.valueOf(100)));
//						totalInvest = totalInvest.add(total_fee.divide(BigDecimal.valueOf(100)));
					investor.setAccountBalance(total);//更新余额
//						investor.setTotalInvest(totalInvest);//更新账户投资
//					iah.setAccountBalance(total);
					
					//更新企业账户余额
					CompanyAccount ca = this.companyAccountDAO.get(iah.getCompanyAccount());
					if(ca == null){
						message = "账户信息错误";
						throw new TransactionException(message);
					}
					
					BigDecimal accountTotal = ca.getAccountBalance();
					accountTotal = accountTotal.add(total_fee.divide(BigDecimal.valueOf(100)));
					//更新系统总帐户余额
					PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
					BigDecimal totalAmount = pa.getTotalAmount();
					totalAmount = totalAmount.add(total_fee.divide(BigDecimal.valueOf(100)));
					
					//更新系统可用余额
					PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
					BigDecimal totalAmountf = paf.getTotalAmount();
//					totalAmountf = totalAmountf.add(total_fee.divide(BigDecimal.valueOf(100)));
					
					
					BigDecimal totalAmounti = pai.getTotalAmount();
					totalAmounti = totalAmounti.add(total_fee.divide(BigDecimal.valueOf(100)));
					
					if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
						accountTotal = accountTotal.subtract(iah.getPoundage());
						totalAmount = totalAmount.subtract(iah.getPoundage());
						totalAmountf = totalAmountf.subtract(iah.getPoundage());
					}
					pa.setTotalAmount(totalAmount);
					paf.setTotalAmount(totalAmountf);
					pai.setTotalAmount(totalAmounti);
					ca.setAccountBalance(accountTotal);
					this.companyAccountDAO.update(ca);
					this.platformAccountDAO.update(pa);
					this.platformAccountDAO.update(paf);
					
					//增加公司账户交易记录--用户充值
					CompanyAccountHistory cat = new CompanyAccountHistory();
					cat.setUuid(UUID.randomUUID().toString());
					cat.setCompanyAccountIn(iah.getCompanyAccount());//用户充值
					cat.setType(CompanyAccountHistoryType.FILLIN);
					cat.setInvestor(investor.getUuid());
					cat.setTotalAmount(total_fee.divide(BigDecimal.valueOf(100)));
					cat.setPoundage(iah.getPoundage());
					cat.setPurpose("用户充值（购买）");
					cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
					
					cat.setCreator(investor.getUuid());
					cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
					cat.setInvestorAccountHistory(iah.getUuid());
					
					//20180622增加记录本次余额信息
					cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
					
//					this.companyAccountHistoryDAO.insert(cat);
					if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
						//增加公司账户交易记录--费用录入（扣除交易手续费）
						CompanyAccountHistory catp = new CompanyAccountHistory();
						catp.setUuid(UUID.randomUUID().toString());
						catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
						catp.setType(CompanyAccountHistoryType.EXPEND);
						catp.setInvestor(investor.getUuid());
						catp.setTotalAmount(iah.getPoundage());
						catp.setPoundage(BigDecimal.ZERO);
						catp.setPurpose("用户充值--手续费扣除");
						catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
						
						catp.setCreator(investor.getUuid());
						catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
						catp.setInvestorAccountHistory(iah.getUuid());
						catp.setRelated(cat.getUuid());
						cat.setRelated(catp.getUuid());
						
						//20180622增加记录本次余额信息
						catp.setAccountBalance(ca.getAccountBalance());
						
						this.companyAccountHistoryDAO.insert(cat);
						this.companyAccountHistoryDAO.insert(catp);
					}else{
						this.companyAccountHistoryDAO.insert(cat);
					}
				} else {
					iah.setStatus(err_code);
				}
				this.investorAccountHistoryDAO.update(iah);
			}else{
				message = "账单错误"; 
				throw new TransactionException(message);
			}
			
			if(!InvestorAccountHistoryStatus.SUCCESS.equals(iahBuy.getStatus())){
				if(InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
					iahBuy.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					//购买、提现，需要从个人账户里扣钱
					BigDecimal total = investor.getAccountBalance();
					BigDecimal totalInvest = investor.getTotalInvest();
					total = total.subtract(total_fee.divide(BigDecimal.valueOf(100)));
					totalInvest = totalInvest.add(total_fee.divide(BigDecimal.valueOf(100)));
					investor.setAccountBalance(total);
					investor.setTotalInvest(totalInvest);//更新账户投资
//					iahBuy.setAccountBalance(total);
					
					//更新用户总投资额
					BigDecimal totalnvestment = pai.getInvestment();
					totalnvestment = totalnvestment.add(iahBuy.getPay());
					pai.setInvestment(totalnvestment);
					
					//主表更新/新建操作
					InvestorProductBuy ipb = new InvestorProductBuy();
					inputParams.clear();
					inputParams.put("investor", investor.getUuid());
					inputParams.put("product", iahBuy.getProduct());
					List<Entity> listBuy = this.investorProductBuyDAO.getListForPage(inputParams, -1, -1, null, InvestorProductBuy.class);
					Boolean isUpdate = true;
					if(listBuy != null && !listBuy.isEmpty()){
						ipb = (InvestorProductBuy) listBuy.get(0);
						BigDecimal totalAmount = ipb.getTotalAmount();
						ipb.setTotalAmount(totalAmount.add(iahBuy.getPay()));
						ipb.setAccountBalance(ipb.getAccountBalance().add(iahBuy.getPay()));
						ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
					} else {
						isUpdate = false;
						ipb.setUuid(UUID.randomUUID().toString());
						ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
						ipb.setInvestor(investor.getUuid());
						ipb.setProduct(iahBuy.getProduct());
						ipb.setStage(InvestorProductBuyStage.CONFIRMING);
						ipb.setTotalAmount(iahBuy.getPay());
						ipb.setAccountBalance(iahBuy.getPay());
						ipb.setTotalRedeem(BigDecimal.ZERO);
						ipb.setTotalReturn(BigDecimal.ZERO);
					}
					ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
					if(isUpdate){
						this.investorProductBuyDAO.update(ipb);
					} else {
						this.investorProductBuyDAO.insert(ipb);
					}
//						ipbr.setInvestorProductBuy(ipb.getUuid());
					
					//电子合同生成操作
					//获取银行理财产品发布信息
					BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(iahBuy.getProduct());
					if (bfpp == null) {
						message = "银行理财产品信息不存在！";
						throw new TransactionException(message);
					}
					Bank bank = this.bankDAO.get(bfpp.getCustodian());
					if(bank == null){
						message = "银行理财产品信息错误！";
						throw new TransactionException(message);
					}
					
					inputParams.clear();
					inputParams.put("investor", investor.getUuid());
					inputParams.put("records", iahBuy.getProduct());
					InvestorProductBuyAgreement ipba = new InvestorProductBuyAgreement();
					List<Entity> listAgreement = this.investorProductBuyAgreementDAO.getListForPage(inputParams, -1, -1, null, InvestorProductBuyAgreement.class);
					if(listAgreement != null && !listAgreement.isEmpty()){
						isUpdate = true;
						ipba = (InvestorProductBuyAgreement) listAgreement.get(0);
					} else {
						isUpdate = false;
						ipba.setUuid(UUID.randomUUID().toString());
					}
					ipba.setCreatetime(new Timestamp(System.currentTimeMillis()));
					ipba.setInvestor(investor.getUuid());
					ipba.setType(InvestorProductBuyAgreementType.BANKPRODUCT);
					ipba.setRecords(ipb.getProduct());
					ipba.setScode(iah.getOrderNum());
					ipba.setName("牛投理财定向委托投资管理协议");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("scode", iah.getOrderNum());
			    	params.put("realname", investor.getRealname());
			    	params.put("phone", Utlity.getStarMobile(investor.getMobile()));
			    	params.put("idcard", Utlity.getStarIdcard(investor.getIdcard()));
			    	params.put("productName", bank.getShortName()+bfpp.getName());
			    	String priceRMB = RMBUtlity.arabNumToChineseRMB(Double.parseDouble(ipb.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
			    	String priceStr = "人民币："+Utlity.numFormat4UnitDetailLess(ipb.getTotalAmount())+"元 (大写："+priceRMB+"整)";
			    	params.put("price", priceStr);
			    	params.put("uuid", ipba.getUuid());
			    	params.put("fileName", "牛投理财定向委托投资管理协议"+iah.getOrderNum());
					Map<String, Object> resultPDF = PDFUtlity.ToPdf(params);
					if((Boolean) resultPDF.get("result")){
						ipba.setStatus(InvestorProductBuyAgreementStatus.SUCCESS);
						ipba.setUrl(resultPDF.get("url")==null?"":resultPDF.get("url").toString());
					} else {
						ipba.setStatus(InvestorProductBuyAgreementStatus.FAIL);
						ipba.setUrl("");
					}
					if(investorProductBuyAgreementDAO.getCheckScode(ipba.getScode())){
						message = "手速太快，服务器未响应！";
						throw new TransactionException(message);
					}
					if(isUpdate){
						this.investorProductBuyAgreementDAO.update(ipba);
					} else {
						this.investorProductBuyAgreementDAO.insert(ipba);
					}
					
				} else {
					iahBuy.setStatus(err_code);
				}
				this.investorAccountHistoryDAO.update(iahBuy);
				this.investorDAO.update(investor);
				this.platformAccountDAO.update(pai);
			}else{
				message = "账单错误";
				throw new TransactionException(message);
			}
			System.out.println("成功");
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
			
		} else {
			message = "账单错误";
			throw new TransactionException(message);
		}
	}

	@Override
	public Double getTotalReturnByMonth4Investor(String uuid) {
		return this.investorAccountHistoryDAO.getTotalReturnByMonth4Investor(uuid);
	}

	@Override
	public Double getTotalReturnByYear4Investor(String uuid) {
		return this.investorAccountHistoryDAO.getTotalReturnByYear4Investor(uuid);
	}

	@Override
	public Boolean getCheckOrderNum(String orderNum) {
		return this.investorAccountHistoryDAO.getCheckOrderNum(orderNum);
	}

	/**
	 * 批量更新操作
	 */
	@Override
	public void updateBatch(List<InvestorAccountHistory> listUpdate) {
		for(InvestorAccountHistory iah : listUpdate){
			this.investorAccountHistoryDAO.update(iah);
		}
	}
	
	/**
	 * 批量添加操作
	 */
	@Override
	public void insertBatch(List<InvestorAccountHistory> listHistory) {
		for(InvestorAccountHistory iah : listHistory){
			this.investorAccountHistoryDAO.insert(iah);
		}
	}

	@Override
	public void insertWithdraw(Investor investor, OrderinfoByThirdparty obt, InvestorAccountHistory iah) {
		this.orderinfoByThirdpartyDAO.insert(obt);
		this.investorAccountHistoryDAO.insert(iah);
		this.investorDAO.update(investor);
	}

	@Override
	public HashMap<String, Object> insertChanPayNotice4Buy(
			Map<String, String> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String out_trade_no = map.get("outer_trade_no") == null ? "" : map.get("outer_trade_no").toString();
		String transaction_id = map.get("inner_trade_no") == null ? "" : map.get("inner_trade_no").toString();
		String time_end = map.get("gmt_create") == null ? "" : map.get("gmt_create").toString();
		String result_code = map.get("trade_status") == null ? "" : map.get("trade_status").toString();
		String err_code = map.get("err_code") == null ? "" : map.get("err_code").toString();
		String err_code_des = map.get("err_code_des") == null ? "" : map.get("err_code_des").toString();
		String return_msg = map.get("return_msg") == null ? "" : map.get("return_msg").toString();
		String fee = map.get("trade_amount") == null ? "" : map.get("trade_amount").toString();
		BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("orderNum", out_trade_no);
		List<Entity> list = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
		OrderinfoByThirdparty obt = null;
		if(list != null && list.size() > 0){
			obt = (OrderinfoByThirdparty) list.get(0);
			if(!OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
				obt.setPayNum(transaction_id);
				obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
				obt.setStatus(result_code);
				obt.setErrCode(err_code == null ? "" : err_code);
				obt.setErrCodeDes(err_code_des == null ? "" : err_code_des);
				obt.setMessage(return_msg == null ? "" : return_msg);
				this.orderinfoByThirdpartyDAO.update(obt);
			}
		} else {
			message = "订单未找到";
			throw new TransactionException(message);
		}

		inputParams.clear();
		inputParams.put("order", obt.getUuid());
		inputParams.put("investor", obt.getInvestor());
		inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
		List<Entity> listHistory = this.investorAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
		if(listHistory != null && listHistory.size() > 0){
			InvestorAccountHistory iah = null;
			InvestorAccountHistory iahBuy = null;
			for(Entity entity: listHistory){
				InvestorAccountHistory ii = (InvestorAccountHistory)entity;
				if(InvestorAccountHistoryType.INCOME.equals(ii.getType())){
					iah = ii;
				}
				if(InvestorAccountHistoryType.BUY.equals(ii.getType())){
					iahBuy = ii;
				}
			}
			if(iah == null || iahBuy == null){
				message = "账单错误";
				throw new TransactionException(message);
			}
			
			Investor investor = this.investorDAO.get(iah.getInvestor());
			//更新系统用户总帐户余额(只更新金额 不算入手续费)
			PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
			if(!InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
				
				if(OrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(result_code) || OrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(result_code)){
					iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					//只更新账户总资产
					BigDecimal total = investor.getAccountBalance();
//						BigDecimal totalInvest = investor.getTotalInvest();
					total = total.add(total_fee);
//						totalInvest = totalInvest.add(total_fee.divide(BigDecimal.valueOf(100)));
					investor.setAccountBalance(total);//更新余额
//						investor.setTotalInvest(totalInvest);//更新账户投资
//					iah.setAccountBalance(total);
					
					//更新企业账户余额
					CompanyAccount ca = this.companyAccountDAO.get(iah.getCompanyAccount());
					PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
					if(ca == null){
						message = "账户信息错误";
						throw new TransactionException(message);
					}
					//更新系统可用余额
					PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
					BigDecimal totalAmountf = paf.getTotalAmount();
//					totalAmountf = totalAmountf.add(total_fee);
					
					BigDecimal totalAmounti = pai.getTotalAmount();
					totalAmounti = totalAmounti.add(total_fee);
					
					BigDecimal accountTotal = ca.getAccountBalance();
					accountTotal = accountTotal.add(total_fee);
					BigDecimal totalAmount = pa.getTotalAmount();
					totalAmount = totalAmount.add(total_fee);
					if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
						accountTotal = accountTotal.subtract(iah.getPoundage());
						totalAmount = totalAmount.subtract(iah.getPoundage());
						totalAmountf = totalAmountf.subtract(iah.getPoundage());
					}
					ca.setAccountBalance(accountTotal);
					//更新系统总帐户余额
					pa.setTotalAmount(totalAmount);
					paf.setTotalAmount(totalAmountf);
					pai.setTotalAmount(totalAmounti);
					
					//增加公司账户交易记录--用户充值
					CompanyAccountHistory cat = new CompanyAccountHistory();
					cat.setUuid(UUID.randomUUID().toString());
					cat.setCompanyAccountIn(iah.getCompanyAccount());//用户充值
					cat.setType(CompanyAccountHistoryType.FILLIN);
					cat.setInvestor(investor.getUuid());
					cat.setTotalAmount(total_fee);
					cat.setPoundage(iah.getPoundage());
					cat.setPurpose("用户充值（购买）");
					cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
					
					cat.setCreator(investor.getUuid());
					cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
					cat.setInvestorAccountHistory(iah.getUuid());
					
					//20180622增加记录本次余额信息
					cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
					
					this.companyAccountDAO.update(ca);
					this.platformAccountDAO.update(pa);
					this.platformAccountDAO.update(paf);
					
//					this.companyAccountHistoryDAO.insert(cat);
					
					if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
						//增加公司账户交易记录--费用录入（扣除交易手续费）
						CompanyAccountHistory catp = new CompanyAccountHistory();
						catp.setUuid(UUID.randomUUID().toString());
						catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
						catp.setType(CompanyAccountHistoryType.EXPEND);
						catp.setInvestor(investor.getUuid());
						catp.setTotalAmount(iah.getPoundage());
						catp.setPoundage(BigDecimal.ZERO);
						catp.setPurpose("用户充值（购买）--手续费扣除");
						catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
						
						catp.setCreator(investor.getUuid());
						catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
						catp.setInvestorAccountHistory(iah.getUuid());
						catp.setRelated(cat.getUuid());
						cat.setRelated(catp.getUuid());
						
						//20180622增加记录本次余额信息
						catp.setAccountBalance(ca.getAccountBalance());
						
						this.companyAccountHistoryDAO.insert(cat);
						this.companyAccountHistoryDAO.insert(catp);
					}else{
						this.companyAccountHistoryDAO.insert(cat);
					}
				} else {
					if(OrderinfoByThirdpartyReturnStatus.TRADE_CLOSED.equals(result_code)){
						iah.setStatus(InvestorAccountHistoryStatus.CLOSED);
					} else {
						iah.setStatus(InvestorAccountHistoryStatus.FAIL);
					}
				}
				this.investorAccountHistoryDAO.update(iah);
			}else{
				message = "账单错误"; 
				throw new TransactionException(message);
			}
			
			if(!InvestorAccountHistoryStatus.SUCCESS.equals(iahBuy.getStatus())){
//				Investor investor = this.investorDAO.get(iahBuy.getInvestor());
				if(InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
					iahBuy.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					//购买、提现，需要从个人账户里扣钱
					BigDecimal total = investor.getAccountBalance();
					BigDecimal totalInvest = investor.getTotalInvest();
					total = total.subtract(total_fee);
					totalInvest = totalInvest.add(total_fee);
					investor.setAccountBalance(total);
					investor.setTotalInvest(totalInvest);//更新账户投资
//					iahBuy.setAccountBalance(total);
					
					//更新用户总投资额
					BigDecimal totalnvestment = pai.getInvestment();
					totalnvestment = totalnvestment.add(iahBuy.getPay());
					pai.setInvestment(totalnvestment);
					
					//主表更新/新建操作
					InvestorProductBuy ipb = new InvestorProductBuy();
					inputParams.clear();
					inputParams.put("investor", investor.getUuid());
					inputParams.put("product", iahBuy.getProduct());
					List<Entity> listBuy = this.investorProductBuyDAO.getListForPage(inputParams, -1, -1, null, InvestorProductBuy.class);
					Boolean isUpdate = true;
					if(listBuy != null && !listBuy.isEmpty()){
						ipb = (InvestorProductBuy) listBuy.get(0);
						BigDecimal totalAmount = ipb.getTotalAmount();
						ipb.setTotalAmount(totalAmount.add(iahBuy.getPay()));
						ipb.setAccountBalance(ipb.getAccountBalance().add(iahBuy.getPay()));
						ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
					} else {
						isUpdate = false;
						ipb.setUuid(UUID.randomUUID().toString());
						ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
						ipb.setInvestor(investor.getUuid());
						ipb.setProduct(iahBuy.getProduct());
						ipb.setStage(InvestorProductBuyStage.CONFIRMING);
						ipb.setTotalAmount(iahBuy.getPay());
						ipb.setAccountBalance(iahBuy.getPay());
						ipb.setTotalRedeem(BigDecimal.ZERO);
						ipb.setTotalReturn(BigDecimal.ZERO);
					}
					ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
					if(isUpdate){
						this.investorProductBuyDAO.update(ipb);
					} else {
						this.investorProductBuyDAO.insert(ipb);
					}
//								ipbr.setInvestorProductBuy(ipb.getUuid());
					
					//电子合同生成操作
					//获取银行理财产品发布信息
					BankFinancialProductPublish bfpp = bankFinancialProductPublishDAO.get(iahBuy.getProduct());
					if (bfpp == null) {
						message = "银行理财产品信息不存在！";
						throw new TransactionException(message);
					}
					Bank bank = this.bankDAO.get(bfpp.getCustodian());
					if(bank == null){
						message = "银行理财产品信息错误！";
						throw new TransactionException(message);
					}
					
					inputParams.clear();
					inputParams.put("investor", investor.getUuid());
					inputParams.put("records", iahBuy.getProduct());
					InvestorProductBuyAgreement ipba = new InvestorProductBuyAgreement();
					List<Entity> listAgreement = this.investorProductBuyAgreementDAO.getListForPage(inputParams, -1, -1, null, InvestorProductBuyAgreement.class);
					if(listAgreement != null && !listAgreement.isEmpty()){
						isUpdate = true;
						ipba = (InvestorProductBuyAgreement) listAgreement.get(0);
					} else {
						isUpdate = false;
						ipba.setUuid(UUID.randomUUID().toString());
					}
					ipba.setCreatetime(new Timestamp(System.currentTimeMillis()));
					ipba.setInvestor(investor.getUuid());
					ipba.setType(InvestorProductBuyAgreementType.BANKPRODUCT);
					ipba.setRecords(ipb.getProduct());
					ipba.setScode(iah.getOrderNum());
					ipba.setName("牛投理财定向委托投资管理协议");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("scode", iah.getOrderNum());
			    	params.put("realname", investor.getRealname());
			    	params.put("phone", Utlity.getStarMobile(investor.getMobile()));
			    	params.put("idcard", Utlity.getStarIdcard(investor.getIdcard()));
			    	params.put("productName", bank.getShortName()+bfpp.getName());
			    	String priceRMB = RMBUtlity.arabNumToChineseRMB(Double.parseDouble(ipb.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
			    	String priceStr = "人民币："+Utlity.numFormat4UnitDetailLess(ipb.getTotalAmount())+"元 (大写："+priceRMB+"整)";
			    	params.put("price", priceStr);
			    	params.put("uuid", ipba.getUuid());
			    	params.put("fileName", "牛投理财定向委托投资管理协议"+iah.getOrderNum());
					Map<String, Object> resultPDF = PDFUtlity.ToPdf(params);
					if((Boolean) resultPDF.get("result")){
						ipba.setStatus(InvestorProductBuyAgreementStatus.SUCCESS);
						ipba.setUrl(resultPDF.get("url")==null?"":resultPDF.get("url").toString());
					} else {
						ipba.setStatus(InvestorProductBuyAgreementStatus.FAIL);
						ipba.setUrl("");
					}
					if(investorProductBuyAgreementDAO.getCheckScode(ipba.getScode())){
						message = "手速太快，服务器未响应！";
						throw new TransactionException(message);
					}
					if(isUpdate){
						this.investorProductBuyAgreementDAO.update(ipba);
					} else {
						this.investorProductBuyAgreementDAO.insert(ipba);
					}
				} else {
					if(InvestorAccountHistoryStatus.CLOSED.equals(iah.getStatus())){
						iahBuy.setStatus(InvestorAccountHistoryStatus.CLOSED);
					} else {
						iahBuy.setStatus(InvestorAccountHistoryStatus.FAIL);
					}
					this.investorAccountHistoryDAO.update(iahBuy);
					message = "OK";
					resultFlag = true;
					result.put("result", resultFlag);
					result.put("message", message);
					return result;
				}
				this.investorAccountHistoryDAO.update(iahBuy);
				this.investorDAO.update(investor);
				this.platformAccountDAO.update(pai);
			}else{
				message = "账单错误";
				throw new TransactionException(message);
			}
			System.out.println("成功");
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
			
		} else {
			message = "账单错误";
			throw new TransactionException(message);
		}
	}
	
	@Override
	public HashMap<String, Object> insertChanPayNotice4Pay(
			Map<String, String> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String out_trade_no = map.get("outer_trade_no") == null ? "" : map.get("outer_trade_no").toString();
		String transaction_id = map.get("inner_trade_no") == null ? "" : map.get("inner_trade_no").toString();
		String time_end = map.get("gmt_withdrawal") == null ? "" : map.get("gmt_withdrawal").toString();
		String result_code = map.get("withdrawal_status") == null ? "" : map.get("withdrawal_status").toString();
		String err_code = map.get("return_code") == null ? "" : map.get("return_code").toString();
		String err_code_des = map.get("fail_reason") == null ? "" : map.get("fail_reason").toString();
		String return_msg = map.get("fail_reason") == null ? "" : map.get("fail_reason").toString();
		String fee = map.get("withdrawal_amount") == null ? "" : map.get("withdrawal_amount").toString();
		BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("orderNum", out_trade_no);
		List<Entity> list = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
		OrderinfoByThirdparty obt = null;
		if(list != null && list.size() > 0){
			obt = (OrderinfoByThirdparty) list.get(0);
			if(!OrderinfoByThirdpartyReturnStatus.WITHDRAW_SUCCESS.equals(obt.getStatus())){
				obt.setPayNum(transaction_id);
				obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
				obt.setStatus(result_code);
				obt.setErrCode(err_code == null ? "" : err_code);
				obt.setErrCodeDes(err_code_des == null ? "" : err_code_des);
				obt.setMessage(return_msg == null ? "" : return_msg);
				this.orderinfoByThirdpartyDAO.update(obt);
			}
		} else {
			message = "订单未找到";
			throw new TransactionException(message);
		}
		
		inputParams.clear();
		inputParams.put("order", obt.getUuid());
		inputParams.put("investor", obt.getInvestor());
		inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
		List<Entity> listHistory = this.investorAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
		if(listHistory != null && listHistory.size() > 0){
			InvestorAccountHistory iah = (InvestorAccountHistory) listHistory.get(0);
			if(iah == null){
				message = "订单不存在！"; 
				throw new TransactionException(message);
			}
			Investor investor = this.investorDAO.get(iah.getInvestor());
			if(!OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUCCESS.equals(result_code)){//不是成功状态 算支付失败 进入人工付款流程
				if(OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_FAIL.equals(result_code) || OrderinfoByThirdpartyReturnStatus.RETURN_TICKET.equals(result_code)){
					iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
//					investor.setAccountBalance(investor.getAccountBalance().add(iah.getPay()));//给用户余额返还金额（人工付款流程，不用减）
//					message = "交易失败"; 
					iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
//					throw new TransactionException(message);
					this.investorAccountHistoryDAO.update(iah);
//					this.investorDAO.update(investor);
					message = "OK";
					resultFlag = true;
					result.put("result", resultFlag);
					result.put("message", message);
					return result;
				} else if (OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUBMITTED.equals(result_code)) {
					iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
					message = "交易进行中"; 
					throw new TransactionException(message);
				}
				
				
			}
			
			if(!InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
				if(OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUCCESS.equals(result_code)){
					iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
//					Investor investor = this.investorDAO.get(iah.getInvestor());
					if(InvestorAccountHistoryType.WITHDRAW.equals(iah.getType())){
						//充值、利息、返还本金进个人账号
//						BigDecimal total = investor.getAccountBalance();
//							BigDecimal totalAmount = investor.getTotalAmount();
//						//减账户余额 和账户总资金
//						total = total.subtract(total_fee);
//						investor.setAccountBalance(total);
//							investor.setTotalAmount(totalAmount);//更新账户总资产
//						iah.setAccountBalance(total);
						
						//更新企业账户余额
						CompanyAccount ca = this.companyAccountDAO.get(iah.getCompanyAccount());
						PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
						if(ca == null){
							message = "账户信息错误";
							throw new TransactionException(message);
						}
						//更新系统可用余额
						PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
						
						BigDecimal totalAmountf = paf.getTotalAmount();
//						totalAmountf = totalAmountf.subtract(total_fee);
						
						BigDecimal accountTotal = ca.getAccountBalance();
						accountTotal = accountTotal.subtract(total_fee);
						
						BigDecimal totalAmount = pa.getTotalAmount();
						totalAmount = totalAmount.subtract(total_fee);
						
						//更新系统用户总帐户余额(只更新金额 不算入手续费)
						PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
						BigDecimal totalAmounti = pai.getTotalAmount();
						totalAmounti = totalAmounti.subtract(total_fee);
						
						if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
							accountTotal = accountTotal.subtract(iah.getPoundage());
							totalAmount = totalAmount.subtract(iah.getPoundage());
							totalAmountf = totalAmountf.subtract(iah.getPoundage());
						}
						
						ca.setAccountBalance(accountTotal);
						//更新系统总帐户余额
						pa.setTotalAmount(totalAmount);
						paf.setTotalAmount(totalAmountf);
						pai.setTotalAmount(totalAmounti);
						
						//增加公司账户交易记录--用户提现
						CompanyAccountHistory cat = new CompanyAccountHistory();
						cat.setUuid(UUID.randomUUID().toString());
						cat.setCompanyAccountOut(iah.getCompanyAccount());//用户提现
						cat.setType(CompanyAccountHistoryType.TAKEOUT);
						cat.setInvestor(investor.getUuid());
						cat.setTotalAmount(total_fee);
						cat.setPoundage(iah.getPoundage());
						cat.setPurpose("用户提现");
						cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
						
						cat.setCreator(investor.getUuid());
						cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
						
						cat.setInvestorAccountHistory(iah.getUuid());
						
						//20180622增加记录本次余额信息
						cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
						
						this.companyAccountDAO.update(ca);
						this.platformAccountDAO.update(pa);
						this.platformAccountDAO.update(paf);
						this.platformAccountDAO.update(pai);
//						this.companyAccountHistoryDAO.insert(cat);
						if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
							//增加公司账户交易记录--费用录入（扣除交易手续费）
							CompanyAccountHistory catp = new CompanyAccountHistory();
							catp.setUuid(UUID.randomUUID().toString());
							catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
							catp.setType(CompanyAccountHistoryType.EXPEND);
							catp.setInvestor(investor.getUuid());
							catp.setTotalAmount(iah.getPoundage());
							catp.setPoundage(BigDecimal.ZERO);
							catp.setPurpose("用户提现--手续费扣除");
							catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
							
							catp.setCreator(investor.getUuid());
							catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
							catp.setInvestorAccountHistory(iah.getUuid());
							catp.setRelated(cat.getUuid());
							cat.setRelated(catp.getUuid());
							
							//20180622增加记录本次余额信息
							catp.setAccountBalance(ca.getAccountBalance());
							
							this.companyAccountHistoryDAO.insert(cat);
							this.companyAccountHistoryDAO.insert(catp);
						}else{
							this.companyAccountHistoryDAO.insert(cat);
						}
						//提现成功，发送通知短信
						MobileCode mc = new MobileCode();
						String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(iah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(iah.getPay())+"元提现申请已处理成功，请注意查询。";
						String mobile = investor.getMobile();
						String codeInfo = Utlity.getCaptcha();
						mc.setCode(codeInfo);
						mc.setContent(content);
						mc.setMobile(mobile);
						mc.setCreator(investor.getUuid());
						mc.setStatus(MobileCodeStatus.DISABLE);
						mc.setType(MobileCodeTypes.NOTICE);
						mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
						mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
						mc.setUuid(UUID.randomUUID().toString());
						this.insertSmsInfo(mc);
						
//						content = "【牛投理财】"+content;
						InvestorInformation ii = new InvestorInformation();
						ii.setCreator(investor.getUuid());
						ii.setStatus(InvestorInformationStatus.UNREAD);
						ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
						ii.setUuid(UUID.randomUUID().toString());
						ii.setInfoText(content);
						ii.setInfoTitle(InvestorInformationTitle.WITHDRAW);
						ii.setInvestor(investor.getUuid());
						this.investorInformationDAO.insert(ii);
					} else {
						message = "账单类型错误";
						throw new TransactionException(message);
					}
//					this.investorDAO.update(investor);
				} else {
					investor.setAccountBalance(investor.getAccountBalance().add(iah.getPay()));//给用户余额返还金额
//					message = "交易失败"; 
					iah.setStatus(InvestorAccountHistoryStatus.FAIL);
//					throw new TransactionException(message);
					this.investorDAO.update(investor);
				}
				this.investorAccountHistoryDAO.update(iah);
				
				System.out.println("成功");
				result.put("result", resultFlag);
				result.put("message", message);
				return result;
			}else{
				message = "账单错误";
				throw new TransactionException(message);
			}
			
			
		} else {
			message = "账单错误";
			throw new TransactionException(message);
		}
	}

	@Override
	public HashMap<String, Object> insertChanPayNotice4Recharge(
			Map<String, String> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String out_trade_no = map.get("outer_trade_no") == null ? "" : map.get("outer_trade_no").toString();
		String transaction_id = map.get("inner_trade_no") == null ? "" : map.get("inner_trade_no").toString();
		String time_end = map.get("gmt_create") == null ? "" : map.get("gmt_create").toString();
		String result_code = map.get("trade_status") == null ? "" : map.get("trade_status").toString();
		String err_code = map.get("err_code") == null ? "" : map.get("err_code").toString();
		String err_code_des = map.get("err_code_des") == null ? "" : map.get("err_code_des").toString();
		String return_msg = map.get("return_msg") == null ? "" : map.get("return_msg").toString();
		String fee = map.get("trade_amount") == null ? "" : map.get("trade_amount").toString();
		BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("orderNum", out_trade_no);
		List<Entity> list = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
		OrderinfoByThirdparty obt = null;
		if(list != null && list.size() > 0){
			obt = (OrderinfoByThirdparty) list.get(0);
			if(!OrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(obt.getStatus())){
				obt.setPayNum(transaction_id);
				obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
				obt.setStatus(result_code);
				obt.setErrCode(err_code == null ? "" : err_code);
				obt.setErrCodeDes(err_code_des == null ? "" : err_code_des);
				obt.setMessage(return_msg == null ? "" : return_msg);
				this.orderinfoByThirdpartyDAO.update(obt);
			}
		} else {
			message = "订单未找到";
			throw new TransactionException(message);
		}

		inputParams.clear();
		inputParams.put("order", obt.getUuid());
		inputParams.put("investor", obt.getInvestor());
		inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
		List<Entity> listHistory = this.investorAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
		if(listHistory != null && listHistory.size() > 0){
			InvestorAccountHistory iah = (InvestorAccountHistory) listHistory.get(0);
			if(iah == null){
				message = "账单错误";
				throw new TransactionException(message);
			}
			
//			if(!OrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(result_code)){//不是成功状态 算支付失败 并返回信息
//				if(OrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(result_code)){
//					message = "交易已结束"; 
//					throw new TransactionException(message);
//				}
//				iah.setStatus(InvestorAccountHistoryStatus.FAIL);
//				this.investorAccountHistoryDAO.update(iah);
//				
//				message = "OK";
//				resultFlag = true;
//				result.put("result", resultFlag);
//				result.put("message", message);
//				return result;
//			}
			if(!InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
				Investor investor = this.investorDAO.get(iah.getInvestor());
				if(OrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(result_code) || OrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(result_code)){
					iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					//只更新账户总资产
					BigDecimal total = investor.getAccountBalance();
//						BigDecimal totalInvest = investor.getTotalInvest();
					total = total.add(total_fee);
//						totalInvest = totalInvest.add(total_fee.divide(BigDecimal.valueOf(100)));
					investor.setAccountBalance(total);//更新余额
//						investor.setTotalInvest(totalInvest);//更新账户投资
//					iah.setAccountBalance(total);
					
					//更新企业账户余额
					CompanyAccount ca = this.companyAccountDAO.get(iah.getCompanyAccount());
					PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
					if(ca == null){
						message = "账户信息错误";
						throw new TransactionException(message);
					}
					//更新系统可用余额
					PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
					BigDecimal totalAmountf = paf.getTotalAmount();
//					totalAmountf = totalAmountf.add(total_fee);
					
					BigDecimal accountTotal = ca.getAccountBalance();
					accountTotal = accountTotal.add(total_fee);
					
					BigDecimal totalAmount = pa.getTotalAmount();
					totalAmount = totalAmount.add(total_fee);
					
					//更新系统用户总帐户余额(只更新金额 不算入手续费)
					PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
					BigDecimal totalAmounti = pai.getTotalAmount();
					totalAmounti = totalAmounti.add(total_fee);
					
					if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
						accountTotal = accountTotal.subtract(iah.getPoundage());
						totalAmount = totalAmount.subtract(iah.getPoundage());
						totalAmountf = totalAmountf.subtract(iah.getPoundage());
					}
					ca.setAccountBalance(accountTotal);
					//更新系统总帐户余额
					pa.setTotalAmount(totalAmount);
					paf.setTotalAmount(totalAmountf);
					pai.setTotalAmount(totalAmounti);
					
					//增加公司账户交易记录--用户充值
					CompanyAccountHistory cat = new CompanyAccountHistory();
					cat.setUuid(UUID.randomUUID().toString());
					cat.setCompanyAccountIn(iah.getCompanyAccount());//用户充值
					cat.setType(CompanyAccountHistoryType.FILLIN);
					cat.setInvestor(investor.getUuid());
					cat.setTotalAmount(total_fee);
					cat.setPoundage(iah.getPoundage());
					cat.setPurpose("用户充值");
					cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
					
					cat.setCreator(investor.getUuid());
					cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
					cat.setInvestorAccountHistory(iah.getUuid());
					
					//20180622增加记录本次余额信息
					cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
					
					this.investorDAO.update(investor);
					this.companyAccountDAO.update(ca);
					this.platformAccountDAO.update(pa);
					this.platformAccountDAO.update(paf);
					this.platformAccountDAO.update(pai);
					this.companyAccountHistoryDAO.insert(cat);
					
					if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
						//增加公司账户交易记录--费用录入（扣除交易手续费）
						CompanyAccountHistory catp = new CompanyAccountHistory();
						catp.setUuid(UUID.randomUUID().toString());
						catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
						catp.setType(CompanyAccountHistoryType.EXPEND);
						catp.setInvestor(investor.getUuid());
						catp.setTotalAmount(iah.getPoundage());
						catp.setPoundage(BigDecimal.ZERO);
						catp.setPurpose("用户充值--手续费扣除");
						catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
						
						catp.setCreator(investor.getUuid());
						catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
						catp.setInvestorAccountHistory(iah.getUuid());
						catp.setRelated(cat.getUuid());
						cat.setRelated(catp.getUuid());
						
						//20180622增加记录本次余额信息
						catp.setAccountBalance(ca.getAccountBalance());
						
						this.companyAccountHistoryDAO.insert(cat);
						this.companyAccountHistoryDAO.insert(catp);
					}else{
						this.companyAccountHistoryDAO.insert(cat);
					}
					
					//充值成功，发送通知短信
					MobileCode mc = new MobileCode();
					String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(iah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(iah.getIncome())+"元充值申请已处理成功，请注意查询余额。";
					String mobile = investor.getMobile();
					String codeInfo = Utlity.getCaptcha();
					mc.setCode(codeInfo);
					mc.setContent(content);
					mc.setMobile(mobile);
					mc.setCreator(investor.getUuid());
					mc.setStatus(MobileCodeStatus.DISABLE);
					mc.setType(MobileCodeTypes.NOTICE);
					mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
					mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
					mc.setUuid(UUID.randomUUID().toString());
					this.insertSmsInfo(mc);
					
//					content = "【牛投理财】"+content;
					InvestorInformation ii = new InvestorInformation();
					ii.setCreator(investor.getUuid());
					ii.setStatus(InvestorInformationStatus.UNREAD);
					ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
					ii.setUuid(UUID.randomUUID().toString());
					ii.setInfoText(content);
					ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
					ii.setInvestor(investor.getUuid());
					this.investorInformationDAO.insert(ii);
				} else {
					if(OrderinfoByThirdpartyReturnStatus.TRADE_CLOSED.equals(result_code)){
						iah.setStatus(InvestorAccountHistoryStatus.CLOSED);
					} else {
						iah.setStatus(InvestorAccountHistoryStatus.FAIL);
					}
				}
				this.investorAccountHistoryDAO.update(iah);
			}else{
				message = "账单错误"; 
				throw new TransactionException(message);
			}
			System.out.println("成功");
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
			
		} else {
			message = "账单错误";
			throw new TransactionException(message);
		}
	}

	@Override
	public void insertTestRecharge(OrderinfoByThirdparty obt,
			InvestorAccountHistory iah, Investor investor, CompanyAccount ca,
			CompanyAccountHistory cah, PlatformAccount pa, PlatformAccount paf, PlatformAccount pai) {
		this.orderinfoByThirdpartyDAO.insert(obt);
		this.investorAccountHistoryDAO.insert(iah);
		this.investorDAO.update(investor);
		this.companyAccountDAO.update(ca);
		this.platformAccountDAO.update(pa);
		this.platformAccountDAO.update(paf);
		this.platformAccountDAO.update(pai);
		this.companyAccountHistoryDAO.insert(cah);
	}

	@Override
	public void insertTestWithdraw(OrderinfoByThirdparty obt,
			InvestorAccountHistory iah, Investor investor, CompanyAccount ca,
			CompanyAccountHistory cah, CompanyAccountHistory cahp,
			PlatformAccount pa, PlatformAccount paf, PlatformAccount pai) {
		this.orderinfoByThirdpartyDAO.insert(obt);
		this.investorAccountHistoryDAO.insert(iah);
		this.investorDAO.update(investor);
		this.companyAccountDAO.update(ca);
		this.platformAccountDAO.update(pa);
		this.platformAccountDAO.update(paf);
		this.platformAccountDAO.update(pai);
		this.companyAccountHistoryDAO.insert(cah);
		this.companyAccountHistoryDAO.insert(cahp);
	}

	@Override
	public List<Entity> getListForWithdrawPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return this.investorAccountHistoryDAO.getListForWithdrawPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		return this.investorAccountHistoryDAO.getStatusList(inputParams, resultClass);
	}

	@Override
	public HashMap<String, Object> updateWithdrawBatch(
			List<InvestorAccountHistory> iahList) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		Integer count = 0;
		for(InvestorAccountHistory iah : iahList){
			InvestorBankcard ib = this.investorBankcardDAO.get(iah.getBankcard());
			String bankcardnum = ib.getBindingBankCard().split("_#")[0];
			Bank bank = this.bankDAO.get(ib.getBank());
			Investor investor = this.investorDAO.get(iah.getInvestor());
			String orderNumStr = Utlity.getOrderNumStr(iah.getOrderNum().substring(0, 1),Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_WITHDRAW);
			if(this.investorAccountHistoryDAO.getCheckOrderNum(orderNumStr)){
				message = "订单编号生成失败，请稍后重试！";
				throw new TransactionException(message);
			}
			BigDecimal pay = iah.getPay();
			OrderinfoByThirdparty obtN = new OrderinfoByThirdparty();
			obtN.setUuid(UUID.randomUUID().toString());
			obtN.setType(OrderinfoByThirdpartyType.REAPAL);
			obtN.setInvestor(iah.getInvestor());
			obtN.setOrderNum(orderNumStr);
			obtN.setBody("融宝支付--用户提现");
			obtN.setTotalFee(pay);
			obtN.setPaySource(MD5.getMD5(iah.getInvestor()));
			
			Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
			String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
			BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
			if(pay.add(iah.getPoundage()).compareTo(accountTotal) == 1){
				obtN.setStatus(OrderinfoByThirdpartyReturnStatus.FAIL);
				obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				obtN.setErrCode("");
				obtN.setErrCodeDes("提现账户余额不足！");
				obtN.setMessage("提现账户余额不足！");
			} else {
				//银行付款接口调用 返回成功后
				WithdrawData wd = new WithdrawData(1, bankcardnum, ib.getBindingCardCardholder(), bank.getName(), "", "", WithdrawDateProperties.PRIVATE, pay.setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, "", "", ib.getBindingCardPhone(), "身份证", investor.getIdcard(), "", orderNumStr, "提现");
				
				List<WithdrawData> content = new ArrayList<WithdrawData>();
				content.add(wd);
				WithdrawDataArray wda = new WithdrawDataArray(content);
				wda.setBatch_no(orderNumStr);
				wda.setTrans_time(Utlity.timeSpanToString(iah.getCreatetime()));
				Map<String, Object> result2 = ReapalUtlity.withdrawBatchSubmit(wda);
//				if(!"T".equals(result.get("is_success").toString())){
//					return ResultManager.createFailResult("提现失败,请重新发起！");
//				}
				obtN.setStatus(result2.get("result_code").toString());
				obtN.setCreatetime(new Timestamp(System.currentTimeMillis()));
				obtN.setErrCode(result2.get("result_code") == null ? "" : result2.get("result_code").toString());
				obtN.setErrCodeDes(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
				obtN.setMessage(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
			}
			iah.setOrderId(obtN.getUuid());
			iah.setOrderNum(orderNumStr);
			iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
			if(!OrderinfoByThirdpartyReturnStatus.REAPAL_CHECKED.equals(obtN.getStatus()) && !OrderinfoByThirdpartyReturnStatus.REAPAL_SUCCESS.equals(obtN.getStatus())){
				iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
				count++;
//				//减账户余额 和账户总资金
//				total = total.subtract(pay);
//				investor.setAccountBalance(total);
//				this.investorAccountHistoryService.insertWithdraw(investor, obtN, iah);
//				return ResultManager.createFailResult("提现失败！");
			} else {
				iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.PROCESSED);
//				//减账户余额 和账户总资金
//				total = total.subtract(pay);
//				investor.setAccountBalance(total);
//				this.investorAccountHistoryService.insertWithdraw(investor, obtN, iah);
			}
			this.orderinfoByThirdpartyDAO.insert(obtN);
			this.investorAccountHistoryDAO.update(iah);
		}
		result.put("result", resultFlag);
		result.put("message", message);
		result.put("count", count);
		return result;
	}

	@Override
	public InvestorAccountHistory getByOrderNum(String orderNum,
			Class<? extends Entity> resultClass) {
		return this.investorAccountHistoryDAO.getByOrderNum(orderNum, resultClass);
	}

	@Override
	public void updateWithdraw(List<InvestorAccountHistory> iahList) throws TransactionException, Exception {
		
		List<CompanyAccount> caList = new ArrayList<CompanyAccount>();
		List<CompanyAccountHistory> cahList = new ArrayList<CompanyAccountHistory>();
		Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
		PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
		PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
		PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
		List<MobileCode> codeList = new ArrayList<MobileCode>();
		List<InvestorInformation> infoList = new ArrayList<InvestorInformation>();
		
		for(InvestorAccountHistory iah : iahList){
			Investor investor = this.investorDAO.get(iah.getInvestor());
			if(investor == null){
				throw new TransactionException("该用户不存在！");
			}
			CompanyAccount ca = null;
			if(companyAccountMap.containsKey(iah.getProcessCompanyAccount())){
				ca = companyAccountMap.get(iah.getProcessCompanyAccount());
			} else {
				ca = this.companyAccountDAO.get(iah.getProcessCompanyAccount());
			}
			
			BigDecimal totalAmountf = paf.getTotalAmount();
			
			BigDecimal accountTotal = ca.getAccountBalance();
			accountTotal = accountTotal.subtract(iah.getPay());
			
			BigDecimal totalAmount = pa.getTotalAmount();
			totalAmount = totalAmount.subtract(iah.getPay());
			
			BigDecimal totalAmounti = pai.getTotalAmount();
			totalAmounti = totalAmounti.subtract(iah.getPay());
			
			if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
				accountTotal = accountTotal.subtract(iah.getPoundage());
				totalAmount = totalAmount.subtract(iah.getPoundage());
				totalAmountf = totalAmountf.subtract(iah.getPoundage());
			}
			
			ca.setAccountBalance(accountTotal);
			//更新系统总帐户余额
			pa.setTotalAmount(totalAmount);
			paf.setTotalAmount(totalAmountf);
			pai.setTotalAmount(totalAmounti);
			
			//增加公司账户交易记录--用户提现
			CompanyAccountHistory cat = new CompanyAccountHistory();
			cat.setUuid(UUID.randomUUID().toString());
			cat.setCompanyAccountOut(iah.getProcessCompanyAccount());//用户提现
			cat.setType(CompanyAccountHistoryType.TAKEOUT);
			cat.setInvestor(iah.getInvestor());
			cat.setTotalAmount(iah.getPay());
			cat.setPoundage(iah.getPoundage());
			cat.setPurpose("用户提现");
			cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
			
			cat.setCreator(iah.getInvestor());
			cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			cat.setInvestorAccountHistory(iah.getUuid());
			
			//20180622增加记录本次余额信息
			cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
			
			if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
				//增加公司账户交易记录--费用录入（扣除交易手续费）
				CompanyAccountHistory catp = new CompanyAccountHistory();
				catp.setUuid(UUID.randomUUID().toString());
				catp.setCompanyAccountOut(iah.getProcessCompanyAccount());//用户充值
				catp.setType(CompanyAccountHistoryType.EXPEND);
				catp.setInvestor(iah.getInvestor());
				catp.setTotalAmount(iah.getPoundage());
				catp.setPoundage(BigDecimal.ZERO);
				catp.setPurpose("用户提现--手续费扣除");
				catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
				
				catp.setCreator(iah.getInvestor());
				catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
				catp.setInvestorAccountHistory(iah.getUuid());
				catp.setRelated(cat.getUuid());
				cat.setRelated(catp.getUuid());
				
				//20180622增加记录本次余额信息
				catp.setAccountBalance(ca.getAccountBalance());
				
				cahList.add(catp);
			}
			companyAccountMap.put(ca.getUuid(), ca);
			cahList.add(cat);
			
			//处理成功，发送通知短信，并生成应用内消息
			MobileCode mc = new MobileCode();
			String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(iah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(iah.getPay())+"元提现申请已处理成功，请注意查询。";
			String mobile = investor.getMobile();
			String codeInfo = Utlity.getCaptcha();
			mc.setCode(codeInfo);
			mc.setContent(content);
			mc.setMobile(mobile);
			mc.setCreator(investor.getUuid());
			mc.setStatus(MobileCodeStatus.DISABLE);
			mc.setType(MobileCodeTypes.NOTICE);
			mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
			mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
			mc.setUuid(UUID.randomUUID().toString());
			codeList.add(mc);
			
			InvestorInformation ii = new InvestorInformation();
			ii.setCreator(investor.getUuid());
			ii.setStatus(InvestorInformationStatus.UNREAD);
			ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
			ii.setUuid(UUID.randomUUID().toString());
			ii.setInfoText(content);
			ii.setInfoTitle(InvestorInformationTitle.WITHDRAW);
			ii.setInvestor(investor.getUuid());
			infoList.add(ii);
		}
		
		if(!companyAccountMap.isEmpty()){
			for(CompanyAccount ca : companyAccountMap.values()){
				caList.add(ca);
			}
		}
		
		this.platformAccountDAO.update(pa);
		this.platformAccountDAO.update(pai);
		this.platformAccountDAO.update(paf);
		if(!iahList.isEmpty()){
			for(InvestorAccountHistory iah: iahList){
				this.investorAccountHistoryDAO.update(iah);
			}
		}
		if(!caList.isEmpty()){
			for(CompanyAccount ca: caList){
				this.companyAccountDAO.update(ca);
			}
		}
		
		if(!cahList.isEmpty()){
			for(CompanyAccountHistory cah: cahList){
				this.companyAccountHistoryDAO.insert(cah);
			}
		}
		if(!codeList.isEmpty()){
			for(MobileCode mc: codeList){
				Thread.sleep(10);
				this.insertSmsInfo(mc);
			}
		}
		if(!infoList.isEmpty()){
			for(InvestorInformation ii: infoList){
				this.investorInformationDAO.insert(ii);
			}
		}
	}

	@Override
	public List<Entity> getListForCountPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return this.investorAccountHistoryDAO.getListForCountPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountByInvestor(Map<String, String> inputParams) {
		return this.investorAccountHistoryDAO.getCountByInvestor(inputParams);
	}

	@Override
	public List<Entity> getListForPageByInvestor(
			Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		return this.investorAccountHistoryDAO.getListForPageByInvestor(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public void updateOrderinfoAliTransferStatus(){
		try {
//			long startl = System.currentTimeMillis();
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("status", AutoAliTransferProcessStatus.NORMAL);
			List<Entity> list = this.autoAliTransferProcessDao.getListForPage(searchMap, -1, -1, null, AutoAliTransferProcess.class);
			if(list != null && !list.isEmpty()){
				AutoAliTransferProcess atp = (AutoAliTransferProcess) list.get(0);
//				if(list.size() == 1){
//					
//				}
				String processStarttime = Utlity.timeSpanToStringLess(atp.getProcesstime())+":00";
				Timestamp now = new Timestamp(System.currentTimeMillis());
				String processEndtime = Utlity.timeSpanToString(now);
				
				//判断如果起止时间超过一天，就拆分成N天来处理
				Timestamp start = DataTimeConvert.stringToTimeStamp(processStarttime);
				long between = now.getTime() - start.getTime();
				long day = 1000*60*60*24;
				int dayCount = 0;
				if(between > day){
					dayCount = (int) (between/day);
				}
				AutoAliTransferProcess processRecords = new AutoAliTransferProcess();
				processRecords.setUuid(UUID.randomUUID().toString());
				processRecords.setStatus(AutoAliTransferProcessStatus.NORMAL);
				processRecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
				processRecords.setProcesstime(now);
				processRecords.setProcessindex(atp.getProcessindex()+1);
				Integer count = 0;
				
				List<InvestorAccountHistory> listHistory= new ArrayList<InvestorAccountHistory>();
				List<OrderinfoByThirdparty> listOrder = new ArrayList<OrderinfoByThirdparty>();

				List<CompanyAccountHistory> listCompanyAccountHistory = new ArrayList<CompanyAccountHistory>();
				PlatformAccount pa = this.platformAccountDAO.get(PlatformAccountUuid.TOTAL);
				PlatformAccount paf = this.platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				PlatformAccount pai = this.platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
//				List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
				Map<String, Investor> investMap = new HashMap<String, Investor>();
				Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
				List<MobileCode> codeList = new ArrayList<MobileCode>();
				List<InvestorInformation> infoList = new ArrayList<InvestorInformation>();
				
				for(int ii = 0; ii <= dayCount; ii++){
					processStarttime = Utlity.timeSpanToString(start);
					if(dayCount == 0){
						processEndtime = Utlity.timeSpanToString(now);
					} else {
						if(ii == dayCount){
							processEndtime = Utlity.timeSpanToString(now);
						} else {
							processEndtime = Utlity.timeSpanToString(new Timestamp(start.getTime()+day));
						}
						start = new Timestamp(start.getTime()+day-1000);
					}
//					processStarttime = "2017-11-23 00:00:00";
//					processEndtime = "2017-11-23 23:59:59";
					Alipay result = AliUtlity.getBillList(processStarttime, processEndtime, "1");
					
					if("T".equals(result.getIsSuccess())){//请求成功
//						Request re = result.getRequest();
//						List<Param> paList = re.getParam();
//						for(Param pa: paList){
//							
//						}

						
						Response response = result.getResponse();
						AccountPageQueryResult apq =  response.getAccountPageQueryResult();
						AccountLogList all = apq.getAccountLogList();
						List<AccountQueryAccountLogVO> aqaList = all.getAccountQueryAccountLogVO();
						
						if(aqaList != null && !aqaList.isEmpty()){
							for(AccountQueryAccountLogVO aqa : aqaList){
								String userid = aqa.getBuyer_account() == null ? "" : aqa.getBuyer_account().trim();
								String income = aqa.getIncome();
								BigDecimal price = BigDecimal.valueOf(Double.parseDouble(income));
//								String outcome = aqa.getOutcome();
								String orderNum = aqa.getMerchant_out_order_no() == null ? "" : aqa.getMerchant_out_order_no().trim();
								String tradeNum = aqa.getIw_account_log_id() == null ? "" : aqa.getIw_account_log_id().trim();
								String transCode = aqa.getTrans_code_msg() == null ? "" : aqa.getTrans_code_msg().trim();
								String transData = aqa.getTrans_date() == null ? "" : aqa.getTrans_date().trim();
								if(AliUtlity.trans_code_msg_olpayment.equals(transCode)){//只有在线支付
									if(!Utlity.checkStringNull(orderNum)){//有商户订单号，代表是商户接口支付-不予处理
										continue;
									}
									if(price.compareTo(BigDecimal.ZERO) <= 0){//收入金额小于等于0 表示不是用户转账记录 不予处理
										continue;
									}
									//查询userid是否已绑定
									Investor investor = this.investorDAO.getByAliUserid(userid, Investor.class);
									if(investor != null){
										if(investMap.containsKey(investor.getUuid())){
											investor = investMap.get(investor.getUuid());
										} else {
											investor = this.investorDAO.get(investor.getUuid());
										}
									} else {//当前账户已解绑
										continue;
									}
									
									//通过userid跟tradeNum查询该条记录是否已处理过
									Map<String, String> inputParams = new HashMap<String, String>();
									inputParams.put("payNum", tradeNum);
									inputParams.put("paySource", userid);
									inputParams.put("type", OrderinfoByThirdpartyType.ZHIFUBAO_TRANSFER);
//									inputParams.put("status", OrderinfoByThirdpartyType.ZHIFUBAO_TRANSFER);
									List<Entity> orderList = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
									if(orderList != null && !orderList.isEmpty()){//存在 代表已处理过 不再处理
										continue;
									}
									//处理记录入库 新增一条 order记录 一条history记录 
									String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_ALIPAY,Utlity.BILL_PAYTYPE_ALIPAY,Utlity.BILL_PURPOSE_INCOME);
									if(this.investorAccountHistoryDAO.getCheckOrderNum(orderNumStr)){
//										return ResultManager.createFailResult("手速太快，服务器未响应！");
										orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_ALIPAY,Utlity.BILL_PAYTYPE_ALIPAY,Utlity.BILL_PURPOSE_INCOME);
									}
									OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
									obt.setUuid(UUID.randomUUID().toString());
									obt.setType(OrderinfoByThirdpartyType.ZHIFUBAO_TRANSFER);
									obt.setInvestor(investor.getUuid());
									obt.setOrderNum(orderNumStr);
									obt.setBody("用户支付宝转账");
									obt.setTotalFee(price);
									obt.setPaySource(userid);
									obt.setStatus(OrderinfoByThirdpartyResultStatus.SUCCESS);
									obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
									obt.setPayNum(tradeNum);
									if(!"".equals(transData)){
										obt.setPaytime(DataTimeConvert.stringToTimeStamp(transData));
									} else {
										obt.setPaytime(new Timestamp(System.currentTimeMillis()));
									}
									obt.setErrCode("");
									obt.setErrCodeDes("");
									obt.setMessage("充值成功");
									listOrder.add(obt);
									
									InvestorAccountHistory iah = new InvestorAccountHistory();
									iah.setUuid(UUID.randomUUID().toString());
									iah.setInvestor(investor.getUuid());
									iah.setIncome(price);
									iah.setPay(BigDecimal.ZERO);
									iah.setAccountBalance(investor.getAccountBalance().add(price));
									iah.setOrderId(obt.getUuid());
									iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_ALIPAY_TRANSFER);
									iah.setOrderNum(orderNumStr);
									iah.setType(InvestorAccountHistoryType.INCOME);
									iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
									iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
									iah.setCompanyAccount(CompanyAccountUuid.ALIPAY);
									iah.setPoundage(BigDecimal.ZERO);
									listHistory.add(iah);
									//变更相关账户金额
									//更新状态和处理余额和理财金额
//									Investor investor = null;
									
//									if(investor == null){
//										investor = this.investorService.get(iah.getInvestor());
//									}
									BigDecimal total = investor.getAccountBalance();
									total = total.add(obt.getTotalFee());
									investor.setAccountBalance(total);//更新余额
									
									//更新企业账户余额
									CompanyAccount ca = companyAccountMap.get(iah.getCompanyAccount());
									if(ca == null){
										ca = this.companyAccountDAO.get(iah.getCompanyAccount());
									}
											
									if(ca == null){
										continue;
									}
									BigDecimal accountTotal = ca.getAccountBalance();
									accountTotal = accountTotal.add(obt.getTotalFee());
									
									//更新系统总帐户余额
									BigDecimal totalAmountp = pa.getTotalAmount();
									totalAmountp = totalAmountp.add(obt.getTotalFee());
									
									//更新系统总帐户余额
									BigDecimal totalAmountf = paf.getTotalAmount();
//									totalAmountf = totalAmountf.add(obt.getTotalFee());
									
									BigDecimal totalAmounti = pai.getTotalAmount();
									totalAmounti = totalAmounti.add(obt.getTotalFee());
									
									if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
										accountTotal = accountTotal.subtract(iah.getPoundage());
										totalAmountp = totalAmountp.subtract(iah.getPoundage());
										totalAmountf = totalAmountf.subtract(iah.getPoundage());
									}
									pa.setTotalAmount(totalAmountp);
									paf.setTotalAmount(totalAmountf);
									pai.setTotalAmount(totalAmounti);
									ca.setAccountBalance(accountTotal);
									companyAccountMap.put(iah.getCompanyAccount(), ca);
									
									//增加公司账户交易记录--用户充值
									CompanyAccountHistory cat = new CompanyAccountHistory();
									cat.setUuid(UUID.randomUUID().toString());
									cat.setCompanyAccountIn(iah.getCompanyAccount());//用户充值
									cat.setType(CompanyAccountHistoryType.FILLIN);
									
									cat.setTotalAmount(obt.getTotalFee());
									cat.setPoundage(iah.getPoundage());
									cat.setPurpose("用户充值");
									cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
									cat.setInvestor(investor.getUuid());
									cat.setCreator(investor.getUuid());
									cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
									cat.setInvestorAccountHistory(iah.getUuid());
									
									//20180622增加记录本次余额信息
									cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
									
									if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
										//增加公司账户交易记录--费用录入（扣除交易手续费）
										CompanyAccountHistory catp = new CompanyAccountHistory();
										catp.setUuid(UUID.randomUUID().toString());
										catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
										catp.setType(CompanyAccountHistoryType.EXPEND);
										catp.setInvestor(investor.getUuid());
										catp.setTotalAmount(iah.getPoundage());
										catp.setPoundage(BigDecimal.ZERO);
										catp.setPurpose("用户充值--手续费扣除");
										catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
										catp.setRelated(cat.getUuid());
										catp.setCreator(investor.getUuid());
										catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
										catp.setInvestorAccountHistory(iah.getUuid());
										cat.setRelated(catp.getUuid());
										
										//20180622增加记录本次余额信息
										catp.setAccountBalance(ca.getAccountBalance());
										
										listCompanyAccountHistory.add(catp);
									}
									investMap.put(iah.getInvestor(), investor);
									listCompanyAccountHistory.add(cat);
									count++;
									
									//处理成功，发送通知短信
									MobileCode mc = new MobileCode();
									String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(iah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(iah.getIncome())+"元充值申请已处理成功，请注意查询。";
									String mobile = investor.getMobile();
									String codeInfo = Utlity.getCaptcha();
									mc.setCode(codeInfo);
									mc.setContent(content);
									mc.setMobile(mobile);
									mc.setCreator(investor.getUuid());
									mc.setStatus(MobileCodeStatus.DISABLE);
									mc.setType(MobileCodeTypes.NOTICE);
									mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
									mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
									mc.setUuid(UUID.randomUUID().toString());
//									this.insertSmsInfo(mc);
									codeList.add(mc);
									
//									content = "【牛投理财】"+content;
									InvestorInformation iii = new InvestorInformation();
									iii.setCreator(investor.getUuid());
									iii.setStatus(InvestorInformationStatus.UNREAD);
									iii.setCreatetime(new Timestamp(System.currentTimeMillis()));
									iii.setUuid(UUID.randomUUID().toString());
									iii.setInfoText(content);
									iii.setInfoTitle(InvestorInformationTitle.RECHARGE);
									iii.setInvestor(investor.getUuid());
									infoList.add(iii);
									
								} else {
									continue;
								}
							}
						}
//						processRecords.setProcesscount(count);
//						processRecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
//						processRecords.setProcesstime(now);
//						atp.setStatus(AutoAliTransferProcessStatus.DISABLE);
//						this.autoAliTransferProcessDao.update(atp);
//						this.autoAliTransferProcessDao.insert(processRecords);
//						aqaList.get
						
						atp.setStatus(AutoAliTransferProcessStatus.DISABLE);
					} else {//请求失败
						atp.setStatus(AutoAliTransferProcessStatus.FAIL);
						System.out.println("支付宝账单处理失败");
						System.out.println("error:"+result.getError()+"------message:"+AliUtlity.getResultMessage(result.getError()));
					}
				}
				processRecords.setProcesscount(count);
				
				if(!listOrder.isEmpty()){
					for(OrderinfoByThirdparty obt : listOrder){
						this.orderinfoByThirdpartyDAO.insert(obt);
					}
				}
				if(!listHistory.isEmpty()){
					for(InvestorAccountHistory iah : listHistory){
						this.investorAccountHistoryDAO.insert(iah);
					}
//					this.investorAccountHistoryDAO.insertBatch(listHistory);
				}
				if(!investMap.isEmpty()){
					List<Investor> listInvestorUpdate = new ArrayList<Investor>();
					for(Investor i : investMap.values()){
						listInvestorUpdate.add(i);
					}
					if(!listInvestorUpdate.isEmpty()){
						for(Investor i : listInvestorUpdate){
							this.investorDAO.update(i);	
						}
//						this.investorDAO.updateBatch(listInvestorUpdate);
					}
				}
			
				if(!companyAccountMap.isEmpty()){
					List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
					for(CompanyAccount ca : companyAccountMap.values()){
						listCompanyAccount.add(ca);
					}
					//批处理管理公司账户信息
					if(!listCompanyAccount.isEmpty()){
						for(CompanyAccount ca : listCompanyAccount){
							this.companyAccountDAO.update(ca);
						}
//						this.companyAccountDAO.updateBatch(listCompanyAccount);
					}
				}
				this.platformAccountDAO.update(pa);
				this.platformAccountDAO.update(paf);
				this.platformAccountDAO.update(pai);
				if(!listCompanyAccountHistory.isEmpty()){
					for(CompanyAccountHistory cah : listCompanyAccountHistory){
						this.companyAccountHistoryDAO.insert(cah);
					}
//					this.companyAccountHistoryDAO.insertBatch(listCompanyAccountHistory);
				}
				
				this.autoAliTransferProcessDao.update(atp);
				this.autoAliTransferProcessDao.insert(processRecords);
				
				if(!codeList.isEmpty()){
					for(MobileCode mc : codeList){
						this.insertSmsInfo(mc);
					}
				}
				
				if(!infoList.isEmpty()){
					for(InvestorInformation ii : infoList){
						this.investorInformationDAO.insert(ii);
					}
				}
			}
			
//			long endl = System.currentTimeMillis();
//			System.out.println("three-"+(endl-startl));
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
		}
	}

	@Override
	public void insertSmsInfo(MobileCode mc) throws TransactionException {
		String phone = mc.getMobile();
		String content = mc.getContent();
		String result = SendSms.sendSms(content, phone);
		if (result != null && "0".equals(result.split("_")[0])) {
			this.mobileCodeDAO.insert(mc);
//			Map<String, String> inputParams = new HashMap<String, String>();
//			inputParams.put("mobile", phone);
//			inputParams.put("status", MobileCodeStatus.NORMAL);
//			List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);
//
//			// 如果之前存在验证码，设置验证码失效
//			if (lstMobileCode != null && lstMobileCode.size() > 0) {
//				for(Entity entity: lstMobileCode){
//					MobileCode code = (MobileCode)entity;
//					code.setStatus(MobileCodeStatus.DISABLE);
//					this.mobileCodeDAO.update(code);
//				}
//			}
//			
//			MobileCode code = new MobileCode();
//			code.setUuid(UUID.randomUUID().toString());
//			code.setCode(codestr);
//			code.setContent(content);
//			code.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			code.setCreatorType(MobileCodeCreatorType.INVESTOR);
//			code.setMobile(phone);
//			code.setStatus(MobileCodeStatus.NORMAL);
//			code.setType(codeType);
//			this.mobileCodeDAO.insert(code);
//			return ResultManager.createSuccessResult("短信验证码发送成功");
		} else {
			throw new TransactionException("短信验证码发送失败！");
		}
	}
	
	private void checkCouponStrategy(Investor investor){
		//注册成功检查此处优惠券策略是否生效，将优惠券添加到用户账户中 同步生成应用内消息
		CouponStrategy cs = this.couponStrategyDAO.get(CouponStrategyUuid.INVESTED);
		if(cs != null && CouponStrategyStatus.OPEN.equals(cs.getStatus())){
			Timestamp now = new Timestamp(System.currentTimeMillis());
			Map<String, Object> coupon = JSONUtils.json2map(cs.getCoupon());
			List<CouponLessVO> list = JSONUtils.json2list(coupon.get("couponList").toString(), CouponLessVO.class);
			for(CouponLessVO vo : list){
				InvestorCoupon ic = new InvestorCoupon();
				ic.setUuid(UUID.randomUUID().toString());
				ic.setCoupon(vo.getUuid());
				ic.setCreator(investor.getUuid());
				ic.setCreatetime(now);
				ic.setStatus(InvestorCouponStatus.NOTACTIVE);
				//计算优惠券截止日期
				long day = 1000*60*60*24;
				if(vo.getExpiryDate() != null){//有截止日期
					if((now.getTime()-vo.getExpiryDate().getTime()) > 0){//超出截止日期
						//优惠券失效 不发放给用户
						continue;
					}
					//未超出截止日期，判断是否有使用期限限制
					if(vo.getDeadline() > 0){
						long addTime = day*vo.getDeadline();
						if((now.getTime()+addTime-vo.getExpiryDate().getTime()) > 0){
							ic.setExpiryDate(vo.getExpiryDate());
						} else {
							Timestamp exp = DataTimeConvert.stringToTimeStamp(Utlity.timeSpanToDateString(now)+" 00:00:00");
							ic.setExpiryDate(new Timestamp(exp.getTime()+addTime));
						}
					} else {
						ic.setExpiryDate(vo.getExpiryDate());
					}
				} else {
					//没有截止日期 按照期限计算
					long addTime = day*vo.getDeadline();
					Timestamp exp = DataTimeConvert.stringToTimeStamp(Utlity.timeSpanToDateString(now)+" 00:00:00");
					ic.setExpiryDate(new Timestamp(exp.getTime()+addTime));
				}
				ic.setInvestor(investor.getUuid());
				this.investorCouponDAO.insert(ic);
				
				InvestorInformation iiii = new InvestorInformation();
				String price = "";
				if(CouponType.CASH.equals(vo.getCouponType())){
					price =Utlity.numFormat4UnitDetailLess(vo.getCouponPrice())+"元的现金增值券";
				} else if (CouponType.INTERESTS.equals(vo.getCouponType())) {
					price =Utlity.numFormat4UnitDetailLess(vo.getCouponPrice())+"%的加息券";
				}
				String content = "尊敬的用户您好：您于"+Utlity.timeSpanToDateString(now)+"获得1张"+price+"，您可在我的优惠券内查询。";
				iiii.setCreator(investor.getUuid());
				iiii.setStatus(InvestorInformationStatus.UNREAD);
				iiii.setCreatetime(new Timestamp(System.currentTimeMillis()));
				iiii.setUuid(UUID.randomUUID().toString());
				iiii.setInfoText(content);
				iiii.setInfoTitle(InvestorInformationTitle.COUPONADD);
				iiii.setInvestor(investor.getUuid());
				this.investorInformationDAO.insert(iiii);
			}
		}
	}

	/**
	 * 红包金额入账
	 */
	@Override
	public void insertActive(List<InvestorCoupon> listCoupon, List<InvestorRedPacket> listRed, Investor investor)
			throws TransactionException {
		for(InvestorCoupon ic : listCoupon){
			this.investorCouponDAO.update(ic);
		}
		PlatformAccount pab =this.platformAccountDAO.get(PlatformAccountUuid.BALANCE);
		PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
		for(InvestorRedPacket irp : listRed) {
			BigDecimal price = irp.getPirce();
			
			pab.setTotalAmount(pab.getTotalAmount().subtract(price));
			//更新系统用户总帐户余额(只更新金额 不算入手续费)
			
			pab.setTotalAmount(pai.getTotalAmount().add(price));
			//用户账户
			investor.setAccountBalance(investor.getAccountBalance().add(price));
			
			InvestorAccountHistory iahr = new InvestorAccountHistory();
			iahr.setUuid(UUID.randomUUID().toString());
			iahr.setInvestor(investor.getUuid());
			iahr.setType(InvestorAccountHistoryType.REDPACKET);
			iahr.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_COMPANY);
			iahr.setOrderId(iahr.getUuid());
			iahr.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_REDPACKET,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_REDPACKET));
			iahr.setAccountBalance(investor.getAccountBalance());
			iahr.setIncome(price);
			iahr.setStatus(InvestorAccountHistoryStatus.SUCCESS);
			iahr.setCreatetime(new Timestamp(System.currentTimeMillis()));
			iahr.setPoundage(BigDecimal.ZERO);
			iahr.setPay(BigDecimal.ZERO);
			
			this.investorAccountHistoryDAO.insert(iahr);
			this.investorRedPacketDAO.update(irp);
		}
		this.investorDAO.update(investor);
		this.platformAccountDAO.update(pab);
		this.platformAccountDAO.update(pai);
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4Pay(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
//		String order_no = map.get("order_no") == null ? "" : map.get("order_no").toString();
		String data = map.get("data") == null ? "" : map.get("data").toString();
//		String sign = map.get("sign") == null ? "" : map.get("sign").toString();
//		String notify_id = map.get("notify_id") == null ? "" : map.get("notify_id").toString();
		if(!Utlity.checkStringNull(data)){
			String resultArr[] = data.split(",");
			String status = "";
			if("成功".equals(resultArr[13])){
				status = OrderinfoByThirdpartyReturnStatus.SUCCESS;
			} else {
				status = OrderinfoByThirdpartyReturnStatus.FAIL;
			}
			String reason = "";
			if(resultArr.length > 14){
				reason = resultArr[14];
			}
			
			String orderNum = resultArr[12];
			String fee = resultArr[9];
			
			BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("orderNum", orderNum);
			List<Entity> list = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
			OrderinfoByThirdparty obt = null;
			if(list != null && list.size() > 0){
				obt = (OrderinfoByThirdparty) list.get(0);
				if(!OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
//							obt.setPayNum(transaction_id);
					obt.setPaytime(new Timestamp(System.currentTimeMillis()));
					obt.setStatus(status);
//							obt.setErrCode(err_code == null ? "" : err_code);
					obt.setErrCodeDes(reason == null ? "" : reason);
					obt.setMessage(reason == null ? "" : reason);
					this.orderinfoByThirdpartyDAO.update(obt);
				}
			} else {
				message = "订单未找到";
				throw new TransactionException(message);
			}
			
			inputParams.clear();
			inputParams.put("order", obt.getUuid());
			inputParams.put("investor", obt.getInvestor());
//			inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
			inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
			inputParams.put("type", InvestorAccountHistoryType.WITHDRAW);
			List<Entity> listHistory = this.investorAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
			if(listHistory != null && listHistory.size() > 0){
				InvestorAccountHistory iah = (InvestorAccountHistory) listHistory.get(0);
				if(iah == null){
					message = "订单不存在！"; 
					throw new TransactionException(message);
				}
				iah = this.investorAccountHistoryDAO.get(iah.getUuid());
				
				Investor investor = this.investorDAO.get(iah.getInvestor());
				if(!OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){//不是成功状态 算支付失败 进入人工付款流程
					if(OrderinfoByThirdpartyReturnStatus.FAIL.equals(status) || OrderinfoByThirdpartyReturnStatus.RETURN_TICKET.equals(status)){
						iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
//								investor.setAccountBalance(investor.getAccountBalance().add(iah.getPay()));//给用户余额返还金额（人工付款流程，不用减）
//								message = "交易失败"; 
						iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
//								throw new TransactionException(message);
						this.investorAccountHistoryDAO.update(iah);
//								this.investorDAO.update(investor);
						message = "OK";
						resultFlag = true;
						result.put("result", resultFlag);
						result.put("message", message);
						return result;
					} else if (OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUBMITTED.equals(status)) {
						iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
						message = "交易进行中"; 
						throw new TransactionException(message);
					}
					
					
				}
				
				if(!InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
					if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(status)){
						iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
//								Investor investor = this.investorDAO.get(iah.getInvestor());
						if(InvestorAccountHistoryType.WITHDRAW.equals(iah.getType())){
							//充值、利息、返还本金进个人账号
//									BigDecimal total = investor.getAccountBalance();
//										BigDecimal totalAmount = investor.getTotalAmount();
//									//减账户余额 和账户总资金
//									total = total.subtract(total_fee);
//									investor.setAccountBalance(total);
//										investor.setTotalAmount(totalAmount);//更新账户总资产
//									iah.setAccountBalance(total);
							
							//更新企业账户余额
							CompanyAccount ca = this.companyAccountDAO.get(iah.getCompanyAccount());
							PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
							if(ca == null){
								message = "账户信息错误";
								throw new TransactionException(message);
							}
							//更新系统可用余额
							PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
							
							BigDecimal totalAmountf = paf.getTotalAmount();
//									totalAmountf = totalAmountf.subtract(total_fee);
							
							BigDecimal accountTotal = ca.getAccountBalance();
							accountTotal = accountTotal.subtract(total_fee);
							
							BigDecimal totalAmount = pa.getTotalAmount();
							totalAmount = totalAmount.subtract(total_fee);
							
							//更新系统用户总帐户余额(只更新金额 不算入手续费)
							PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
							BigDecimal totalAmounti = pai.getTotalAmount();
							totalAmounti = totalAmounti.subtract(total_fee);
							
							if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								accountTotal = accountTotal.subtract(iah.getPoundage());
								totalAmount = totalAmount.subtract(iah.getPoundage());
								totalAmountf = totalAmountf.subtract(iah.getPoundage());
							}
							
							ca.setAccountBalance(accountTotal);
							//更新系统总帐户余额
							pa.setTotalAmount(totalAmount);
							paf.setTotalAmount(totalAmountf);
							pai.setTotalAmount(totalAmounti);
							
							//增加公司账户交易记录--用户提现
							CompanyAccountHistory cat = new CompanyAccountHistory();
							cat.setUuid(UUID.randomUUID().toString());
							cat.setCompanyAccountOut(iah.getCompanyAccount());//用户提现
							cat.setType(CompanyAccountHistoryType.TAKEOUT);
							cat.setInvestor(investor.getUuid());
							cat.setTotalAmount(total_fee);
							cat.setPoundage(iah.getPoundage());
							cat.setPurpose("用户提现");
							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
							
							cat.setCreator(investor.getUuid());
							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
							
							cat.setInvestorAccountHistory(iah.getUuid());
							
							//20180622增加记录本次余额信息
							cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
							
							this.companyAccountDAO.update(ca);
							this.platformAccountDAO.update(pa);
							this.platformAccountDAO.update(paf);
							this.platformAccountDAO.update(pai);
//									this.companyAccountHistoryDAO.insert(cat);
							if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								//增加公司账户交易记录--费用录入（扣除交易手续费）
								CompanyAccountHistory catp = new CompanyAccountHistory();
								catp.setUuid(UUID.randomUUID().toString());
								catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
								catp.setType(CompanyAccountHistoryType.EXPEND);
								catp.setInvestor(investor.getUuid());
								catp.setTotalAmount(iah.getPoundage());
								catp.setPoundage(BigDecimal.ZERO);
								catp.setPurpose("用户提现--手续费扣除");
								catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
								
								catp.setCreator(investor.getUuid());
								catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
								catp.setInvestorAccountHistory(iah.getUuid());
								catp.setRelated(cat.getUuid());
								cat.setRelated(catp.getUuid());
								
								//20180622增加记录本次余额信息
								catp.setAccountBalance(ca.getAccountBalance());
								
								this.companyAccountHistoryDAO.insert(cat);
								this.companyAccountHistoryDAO.insert(catp);
							}else{
								this.companyAccountHistoryDAO.insert(cat);
							}
							//提现成功，发送通知短信
							MobileCode mc = new MobileCode();
							String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(iah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(iah.getPay())+"元提现申请已处理成功，请注意查询。";
							String mobile = investor.getMobile();
							String codeInfo = Utlity.getCaptcha();
							mc.setCode(codeInfo);
							mc.setContent(content);
							mc.setMobile(mobile);
							mc.setCreator(investor.getUuid());
							mc.setStatus(MobileCodeStatus.DISABLE);
							mc.setType(MobileCodeTypes.NOTICE);
							mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
							mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
							mc.setUuid(UUID.randomUUID().toString());
							this.insertSmsInfo(mc);
							
//									content = "【牛投理财】"+content;
							InvestorInformation ii = new InvestorInformation();
							ii.setCreator(investor.getUuid());
							ii.setStatus(InvestorInformationStatus.UNREAD);
							ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
							ii.setUuid(UUID.randomUUID().toString());
							ii.setInfoText(content);
							ii.setInfoTitle(InvestorInformationTitle.WITHDRAW);
							ii.setInvestor(investor.getUuid());
							this.investorInformationDAO.insert(ii);
						} else {
							message = "账单类型错误";
							throw new TransactionException(message);
						}
//								this.investorDAO.update(investor);
					} else {
						message = "状态错误";
						throw new TransactionException(message);
					}
					this.investorAccountHistoryDAO.update(iah);
//							System.out.println("成功");
//							result.put("result", resultFlag);
//							result.put("message", message);
//							return result;
					
				}else{
					message = "账单错误";
					throw new TransactionException(message);
				}
				
				
			} else {
				message = "账单错误";
				throw new TransactionException(message);
			}
				
			System.out.println("成功");
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
		} else {
			message = "发生错误";
			throw new TransactionException(message);
		}
		
		
		
//		String out_trade_no = map.get("outer_trade_no") == null ? "" : map.get("outer_trade_no").toString();
//		String transaction_id = map.get("inner_trade_no") == null ? "" : map.get("inner_trade_no").toString();
//		String time_end = map.get("gmt_withdrawal") == null ? "" : map.get("gmt_withdrawal").toString();
//		String result_code = map.get("withdrawal_status") == null ? "" : map.get("withdrawal_status").toString();
//		String err_code = map.get("return_code") == null ? "" : map.get("return_code").toString();
//		String err_code_des = map.get("fail_reason") == null ? "" : map.get("fail_reason").toString();
//		String return_msg = map.get("fail_reason") == null ? "" : map.get("fail_reason").toString();
//		String fee = map.get("withdrawal_amount") == null ? "" : map.get("withdrawal_amount").toString();
//		BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
//		Map<String, String> inputParams = new HashMap<String, String>();
//		inputParams.put("orderNum", out_trade_no);
//		List<Entity> list = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
//		OrderinfoByThirdparty obt = null;
//		if(list != null && list.size() > 0){
//			obt = (OrderinfoByThirdparty) list.get(0);
//			if(!OrderinfoByThirdpartyReturnStatus.WITHDRAW_SUCCESS.equals(obt.getStatus())){
//				obt.setPayNum(transaction_id);
//				obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
//				obt.setStatus(result_code);
//				obt.setErrCode(err_code == null ? "" : err_code);
//				obt.setErrCodeDes(err_code_des == null ? "" : err_code_des);
//				obt.setMessage(return_msg == null ? "" : return_msg);
//				this.orderinfoByThirdpartyDAO.update(obt);
//			}
//		} else {
//			message = "订单未找到";
//			throw new TransactionException(message);
//		}
//		
//		inputParams.clear();
//		inputParams.put("order", obt.getUuid());
//		inputParams.put("investor", obt.getInvestor());
//		inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
//		List<Entity> listHistory = this.investorAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
//		if(listHistory != null && listHistory.size() > 0){
//			InvestorAccountHistory iah = (InvestorAccountHistory) listHistory.get(0);
//			if(iah == null){
//				message = "订单不存在！"; 
//				throw new TransactionException(message);
//			}
//			Investor investor = this.investorDAO.get(iah.getInvestor());
//			if(!OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUCCESS.equals(result_code)){//不是成功状态 算支付失败 进入人工付款流程
//				if(OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_FAIL.equals(result_code) || OrderinfoByThirdpartyReturnStatus.RETURN_TICKET.equals(result_code)){
//					iah.setProcessingStatus(InvestorAccountHistoryProcessStatus.UNPROCESS);//接口调用失败 进入人工处理流程，设置处理状态为未处理
////					investor.setAccountBalance(investor.getAccountBalance().add(iah.getPay()));//给用户余额返还金额（人工付款流程，不用减）
////					message = "交易失败"; 
//					iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
////					throw new TransactionException(message);
//					this.investorAccountHistoryDAO.update(iah);
////					this.investorDAO.update(investor);
//					message = "OK";
//					resultFlag = true;
//					result.put("result", resultFlag);
//					result.put("message", message);
//					return result;
//				} else if (OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUBMITTED.equals(result_code)) {
//					iah.setStatus(InvestorAccountHistoryStatus.TRANSACTING);
//					message = "交易进行中"; 
//					throw new TransactionException(message);
//				}
//				
//				
//			}
//			
//			if(!InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
//				if(OrderinfoByThirdpartyReturnStatus.WITHDRAWAL_SUCCESS.equals(result_code)){
//					iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
////					Investor investor = this.investorDAO.get(iah.getInvestor());
//					if(InvestorAccountHistoryType.WITHDRAW.equals(iah.getType())){
//						//充值、利息、返还本金进个人账号
////						BigDecimal total = investor.getAccountBalance();
////							BigDecimal totalAmount = investor.getTotalAmount();
////						//减账户余额 和账户总资金
////						total = total.subtract(total_fee);
////						investor.setAccountBalance(total);
////							investor.setTotalAmount(totalAmount);//更新账户总资产
////						iah.setAccountBalance(total);
//						
//						//更新企业账户余额
//						CompanyAccount ca = this.companyAccountDAO.get(iah.getCompanyAccount());
//						PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
//						if(ca == null){
//							message = "账户信息错误";
//							throw new TransactionException(message);
//						}
//						//更新系统可用余额
//						PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
//						
//						BigDecimal totalAmountf = paf.getTotalAmount();
////						totalAmountf = totalAmountf.subtract(total_fee);
//						
//						BigDecimal accountTotal = ca.getAccountBalance();
//						accountTotal = accountTotal.subtract(total_fee);
//						
//						BigDecimal totalAmount = pa.getTotalAmount();
//						totalAmount = totalAmount.subtract(total_fee);
//						
//						//更新系统用户总帐户余额(只更新金额 不算入手续费)
//						PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
//						BigDecimal totalAmounti = pai.getTotalAmount();
//						totalAmounti = totalAmounti.subtract(total_fee);
//						
//						if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//							accountTotal = accountTotal.subtract(iah.getPoundage());
//							totalAmount = totalAmount.subtract(iah.getPoundage());
//							totalAmountf = totalAmountf.subtract(iah.getPoundage());
//						}
//						
//						ca.setAccountBalance(accountTotal);
//						//更新系统总帐户余额
//						pa.setTotalAmount(totalAmount);
//						paf.setTotalAmount(totalAmountf);
//						pai.setTotalAmount(totalAmounti);
//						
//						//增加公司账户交易记录--用户提现
//						CompanyAccountHistory cat = new CompanyAccountHistory();
//						cat.setUuid(UUID.randomUUID().toString());
//						cat.setCompanyAccountOut(iah.getCompanyAccount());//用户提现
//						cat.setType(CompanyAccountHistoryType.TAKEOUT);
//						cat.setInvestor(investor.getUuid());
//						cat.setTotalAmount(total_fee);
//						cat.setPoundage(iah.getPoundage());
//						cat.setPurpose("用户提现");
//						cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
//						
//						cat.setCreator(investor.getUuid());
//						cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
//						
//						cat.setInvestorAccountHistory(iah.getUuid());
//						this.companyAccountDAO.update(ca);
//						this.platformAccountDAO.update(pa);
//						this.platformAccountDAO.update(paf);
//						this.platformAccountDAO.update(pai);
////						this.companyAccountHistoryDAO.insert(cat);
//						if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//							//增加公司账户交易记录--费用录入（扣除交易手续费）
//							CompanyAccountHistory catp = new CompanyAccountHistory();
//							catp.setUuid(UUID.randomUUID().toString());
//							catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
//							catp.setType(CompanyAccountHistoryType.EXPEND);
//							catp.setInvestor(investor.getUuid());
//							catp.setTotalAmount(iah.getPoundage());
//							catp.setPoundage(BigDecimal.ZERO);
//							catp.setPurpose("用户提现--手续费扣除");
//							catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
//							
//							catp.setCreator(investor.getUuid());
//							catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
//							catp.setInvestorAccountHistory(iah.getUuid());
//							catp.setRelated(cat.getUuid());
//							cat.setRelated(catp.getUuid());
//							this.companyAccountHistoryDAO.insert(cat);
//							this.companyAccountHistoryDAO.insert(catp);
//						}else{
//							this.companyAccountHistoryDAO.insert(cat);
//						}
//						//提现成功，发送通知短信
//						MobileCode mc = new MobileCode();
//						String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(iah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(iah.getPay())+"元提现申请已处理成功，请注意查询。";
//						String mobile = investor.getMobile();
//						String codeInfo = Utlity.getCaptcha();
//						mc.setCode(codeInfo);
//						mc.setContent(content);
//						mc.setMobile(mobile);
//						mc.setCreator(investor.getUuid());
//						mc.setStatus(MobileCodeStatus.DISABLE);
//						mc.setType(MobileCodeTypes.NOTICE);
//						mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
//						mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
//						mc.setUuid(UUID.randomUUID().toString());
//						this.insertSmsInfo(mc);
//						
////						content = "【牛投理财】"+content;
//						InvestorInformation ii = new InvestorInformation();
//						ii.setCreator(investor.getUuid());
//						ii.setStatus(InvestorInformationStatus.UNREAD);
//						ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
//						ii.setUuid(UUID.randomUUID().toString());
//						ii.setInfoText(content);
//						ii.setInfoTitle(InvestorInformationTitle.WITHDRAW);
//						ii.setInvestor(investor.getUuid());
//						this.investorInformationDAO.insert(ii);
//					} else {
//						message = "账单类型错误";
//						throw new TransactionException(message);
//					}
////					this.investorDAO.update(investor);
//				} else {
//					investor.setAccountBalance(investor.getAccountBalance().add(iah.getPay()));//给用户余额返还金额
////					message = "交易失败"; 
//					iah.setStatus(InvestorAccountHistoryStatus.FAIL);
////					throw new TransactionException(message);
//					this.investorDAO.update(investor);
//				}
//				this.investorAccountHistoryDAO.update(iah);
//				
//				System.out.println("成功");
//				result.put("result", resultFlag);
//				result.put("message", message);
//				return result;
//			}else{
//				message = "账单错误";
//				throw new TransactionException(message);
//			}
//			
//			
//		} else {
//			message = "账单错误";
//			throw new TransactionException(message);
//		}
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4Recharge(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
				HashMap<String, Object> result = new HashMap<String, Object>();
				String message = "OK";
				Boolean resultFlag = true;
				String out_trade_no = map.get("order_no") == null ? "" : map.get("order_no").toString();
				String transaction_id = map.get("trade_no") == null ? "" : map.get("trade_no").toString();
				String result_status = map.get("status") == null ? "" : map.get("status").toString();
				String result_code = map.get("result_code") == null ? "" : map.get("result_code").toString();
				String return_msg = map.get("result_msg") == null ? "" : map.get("result_msg").toString();
				String fee = map.get("total_fee") == null ? "" : map.get("total_fee").toString();//单位是分
				BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee)).divide(BigDecimal.valueOf(100));
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("orderNum", out_trade_no);
				List<Entity> list = this.orderinfoByThirdpartyDAO.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
				OrderinfoByThirdparty obt = null;
				if(list != null && list.size() > 0){
					obt = (OrderinfoByThirdparty) list.get(0);
					if(!OrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(obt.getStatus())){
						obt.setPayNum(transaction_id);
//						obt.setPaytime(new Timestamp(Utlity.stringToDateFull(time_end).getTime()));
						obt.setStatus(result_status);
						obt.setErrCode(result_code);
						obt.setErrCodeDes(return_msg);
						obt.setMessage(return_msg == null ? "" : return_msg);
						this.orderinfoByThirdpartyDAO.update(obt);
					}
				} else {
					message = "订单未找到";
					throw new TransactionException(message);
				}

				inputParams.clear();
				inputParams.put("order", obt.getUuid());
				inputParams.put("investor", obt.getInvestor());
				inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
				inputParams.put("type", InvestorAccountHistoryType.INCOME);
				List<Entity> listHistory = this.investorAccountHistoryDAO.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
				if(listHistory != null && listHistory.size() > 0){
					InvestorAccountHistory iah = (InvestorAccountHistory) listHistory.get(0);
					if(iah == null){
						message = "账单错误";
						throw new TransactionException(message);
					}
					
//					if(!OrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(result_code)){//不是成功状态 算支付失败 并返回信息
//						if(OrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(result_code)){
//							message = "交易已结束"; 
//							throw new TransactionException(message);
//						}
//						iah.setStatus(InvestorAccountHistoryStatus.FAIL);
//						this.investorAccountHistoryDAO.update(iah);
//						
//						message = "OK";
//						resultFlag = true;
//						result.put("result", resultFlag);
//						result.put("message", message);
//						return result;
//					}
					if(!InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
						Investor investor = this.investorDAO.get(iah.getInvestor());
						if(OrderinfoByThirdpartyReturnStatus.TRADE_SUCCESS.equals(result_status) || 
								OrderinfoByThirdpartyReturnStatus.TRADE_FINISHED.equals(result_status)){
							iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
							//只更新账户总资产
							BigDecimal total = investor.getAccountBalance();
//								BigDecimal totalInvest = investor.getTotalInvest();
							total = total.add(total_fee);
//								totalInvest = totalInvest.add(total_fee.divide(BigDecimal.valueOf(100)));
							investor.setAccountBalance(total);//更新余额
//								investor.setTotalInvest(totalInvest);//更新账户投资
//							iah.setAccountBalance(total);
							
							//更新企业账户余额
							CompanyAccount ca = this.companyAccountDAO.get(iah.getCompanyAccount());
							PlatformAccount pa = platformAccountDAO.get(PlatformAccountUuid.TOTAL);
							if(ca == null){
								message = "账户信息错误";
								throw new TransactionException(message);
							}
							//更新系统可用余额
							PlatformAccount paf = platformAccountDAO.get(PlatformAccountUuid.BALANCE);
							BigDecimal totalAmountf = paf.getTotalAmount();
//							totalAmountf = totalAmountf.add(total_fee);
							
							BigDecimal accountTotal = ca.getAccountBalance();
							accountTotal = accountTotal.add(total_fee);
							
							BigDecimal totalAmount = pa.getTotalAmount();
							totalAmount = totalAmount.add(total_fee);
							
							//更新系统用户总帐户余额(只更新金额 不算入手续费)
							PlatformAccount pai = platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
							BigDecimal totalAmounti = pai.getTotalAmount();
							totalAmounti = totalAmounti.add(total_fee);
							
							if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								accountTotal = accountTotal.subtract(iah.getPoundage());
								totalAmount = totalAmount.subtract(iah.getPoundage());
								totalAmountf = totalAmountf.subtract(iah.getPoundage());
							}
							ca.setAccountBalance(accountTotal);
							//更新系统总帐户余额
							pa.setTotalAmount(totalAmount);
							paf.setTotalAmount(totalAmountf);
							pai.setTotalAmount(totalAmounti);
							
							//增加公司账户交易记录--用户充值
							CompanyAccountHistory cat = new CompanyAccountHistory();
							cat.setUuid(UUID.randomUUID().toString());
							cat.setCompanyAccountIn(iah.getCompanyAccount());//用户充值
							cat.setType(CompanyAccountHistoryType.FILLIN);
							cat.setInvestor(investor.getUuid());
							cat.setTotalAmount(total_fee);
							cat.setPoundage(iah.getPoundage());
							cat.setPurpose("用户充值");
							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
							
							cat.setCreator(investor.getUuid());
							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
							cat.setInvestorAccountHistory(iah.getUuid());
							
							//20180622增加记录本次余额信息
							cat.setAccountBalance(ca.getAccountBalance().add(iah.getPoundage()));
							
							this.investorDAO.update(investor);
							this.companyAccountDAO.update(ca);
							this.platformAccountDAO.update(pa);
							this.platformAccountDAO.update(paf);
							this.platformAccountDAO.update(pai);
							this.companyAccountHistoryDAO.insert(cat);
							
							if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								//增加公司账户交易记录--费用录入（扣除交易手续费）
								CompanyAccountHistory catp = new CompanyAccountHistory();
								catp.setUuid(UUID.randomUUID().toString());
								catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
								catp.setType(CompanyAccountHistoryType.EXPEND);
								catp.setInvestor(investor.getUuid());
								catp.setTotalAmount(iah.getPoundage());
								catp.setPoundage(BigDecimal.ZERO);
								catp.setPurpose("用户充值--手续费扣除");
								catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
								
								catp.setCreator(investor.getUuid());
								catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
								catp.setInvestorAccountHistory(iah.getUuid());
								catp.setRelated(cat.getUuid());
								cat.setRelated(catp.getUuid());
								
								//20180622增加记录本次余额信息
								catp.setAccountBalance(ca.getAccountBalance());
								
								this.companyAccountHistoryDAO.insert(cat);
								this.companyAccountHistoryDAO.insert(catp);
							}else{
								this.companyAccountHistoryDAO.insert(cat);
							}
							
							//充值成功，发送通知短信
							MobileCode mc = new MobileCode();
							String content = "尊敬的用户您好：您于"+Utlity.timeSpanToChinaDateString(iah.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(iah.getIncome())+"元充值申请已处理成功，请注意查询余额。";
							String mobile = investor.getMobile();
							String codeInfo = Utlity.getCaptcha();
							mc.setCode(codeInfo);
							mc.setContent(content);
							mc.setMobile(mobile);
							mc.setCreator(investor.getUuid());
							mc.setStatus(MobileCodeStatus.DISABLE);
							mc.setType(MobileCodeTypes.NOTICE);
							mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
							mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
							mc.setUuid(UUID.randomUUID().toString());
							this.insertSmsInfo(mc);
							
//							content = "【牛投理财】"+content;
							InvestorInformation ii = new InvestorInformation();
							ii.setCreator(investor.getUuid());
							ii.setStatus(InvestorInformationStatus.UNREAD);
							ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
							ii.setUuid(UUID.randomUUID().toString());
							ii.setInfoText(content);
							ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
							ii.setInvestor(investor.getUuid());
							this.investorInformationDAO.insert(ii);
						} else {
							if(OrderinfoByThirdpartyReturnStatus.TRADE_CLOSED.equals(result_status)){
								iah.setStatus(InvestorAccountHistoryStatus.CLOSED);
							} else {
								iah.setStatus(InvestorAccountHistoryStatus.FAIL);
							}
						}
						this.investorAccountHistoryDAO.update(iah);
					}else{
						message = "账单错误"; 
						throw new TransactionException(message);
					}
					System.out.println("成功");
					result.put("result", resultFlag);
					result.put("message", message);
					return result;
					
				} else {
					message = "账单错误";
					throw new TransactionException(message);
				}
	}

	@Override
	public void insertwechart(OrderinfoByThirdparty obt, InvestorAccountHistory iah, InvestorBankcard ib) {
		this.orderinfoByThirdpartyDAO.insert(obt);
		this.investorAccountHistoryDAO.insert(iah);
		this.investorBankcardDAO.update(ib);
	}
	
	@Override
	public Integer getCountByUser(Map<String, String> inputParams) {
		Integer iCount = this.investorAccountHistoryDAO.getCountByInvestor(inputParams);
		Integer qeCount = this.qcbEmployeeHistoryDAO.getCountByQcbEmployee(inputParams);
		return iCount + qeCount;
	}

	@Override
	public List<Entity> getListForPageByUser(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return this.investorAccountHistoryDAO.getListForPageByUser(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public void updatewechart(OrderinfoByThirdparty obt, OrderinfoByThirdparty obtN,
			InvestorAccountHistory iah) {
		// TODO Auto-generated method stub
		this.orderinfoByThirdpartyDAO.update(obt);
		this.orderinfoByThirdpartyDAO.insert(obtN);
		if(iah != null){
			this.investorAccountHistoryDAO.update(iah);
		}
	}
}
