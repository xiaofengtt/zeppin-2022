package cn.zeppin.product.ntb.backadmin.service.autotask;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.ntb.backadmin.dao.api.IAutoAliTransferProcessDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBankFinancialProductPublishService;
import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.ICompanyAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorAccountHistoryService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorInformationService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyAgreementService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorProductBuyService;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.service.api.IOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.backadmin.service.api.IPlatformAccountService;
import cn.zeppin.product.ntb.backadmin.service.api.IRealpalNoticeInfoService;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccountHistory.CompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationStatus;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationTitle;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyProductType;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyStage;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement.InvestorProductBuyAgreementStatus;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuyAgreement.InvestorProductBuyAgreementType;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy.QcbEmployeeProductBuyProductType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy.QcbEmployeeProductBuyStage;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement.QcbEmployeeProductBuyAgreementStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuyAgreement.QcbEmployeeProductBuyAgreementType;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.QcbOrderinfoByThirdparty.QcbOrderinfoByThirdpartyReturnStatus;
import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo;
import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo.RealpalNoticeInfoPayType;
import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo.RealpalNoticeInfoStatus;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder;
import cn.zeppin.product.ntb.core.entity.ShbxSecurityOrder.ShbxSecurityOrderStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUserHistory.ShbxUserHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeHistoryService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyAgreementService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeProductBuyService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbEmployeeService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbOrderinfoByThirdpartyService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxSecurityOrderService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserHistoryService;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;
import cn.zeppin.product.utility.HttpUtility;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.PDFUtlity;
import cn.zeppin.product.utility.RMBUtlity;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.XMLUtils;
import cn.zeppin.product.utility.chanpay.ChanPayUtil;
import cn.zeppin.product.utility.reapal.ReapalUtil;
import cn.zeppin.product.utility.reapal.ReapalUtlity;

@Component
public class PayTask extends BaseService{
	
	@Autowired
	private IOrderinfoByThirdpartyService orderinfoByThirdpartyService;
	
	@Autowired
	private IQcbOrderinfoByThirdpartyService qcbOrderinfoByThirdpartyService;
	
	@Autowired
	private IInvestorAccountHistoryService investorAccountHistoryService;
	
	@Autowired
	private IQcbEmployeeHistoryService qcbEmployeeHistoryService;
	
	@Autowired
	private IInvestorService investorService;
	
	@Autowired
	private IQcbEmployeeService qcbEmployeeService;
	
	@Autowired
	private IBankFinancialProductPublishService bankFinancialProductPublishService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IInvestorProductBuyService investorProductBuyService;
	
	@Autowired
	private IQcbEmployeeProductBuyService qcbEmployeeProductBuyService;
	
	@Autowired
	private IInvestorProductBuyAgreementService investorProductBuyAgreementService;
	
	@Autowired
	private IQcbEmployeeProductBuyAgreementService qcbEmployeeProductBuyAgreementService;
	
	@Autowired
	private ICompanyAccountService companyAccountService;
	
	@Autowired
	private ICompanyAccountHistoryService companyAccountHistoryService;
	
	@Autowired
	private IPlatformAccountService platformAccountService;
	
	@Autowired
	private IAutoAliTransferProcessDAO autoAliTransferProcessDao;
	
	@Autowired
	private IInvestorInformationService investorInformationService;
	
	@Autowired
	private IRealpalNoticeInfoService realpalNoticeInfoService;
	
	@Autowired
	private IQcbCompanyAccountHistoryService qcbCompanyAccountHistoryService;
	
	@Autowired
	private IShbxUserHistoryService shbxUserHistoryService;
	
	@Autowired
	private IShbxUserService shbxUserService;
	
	@Autowired
	private IShbxSecurityOrderService shbxSecurityOrderService;
	
	/**
	 * 定时处理订单信息
	 * 并同步购买记录状态
	 * @param <T>
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@Scheduled(cron="1 0/5 *  * * ? ")
	public <T> void updateOrderinforWechartStatus() {
		try {
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("status", InvestorAccountHistoryStatus.TRANSACTING);
			inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
			inputParams.put("type", InvestorAccountHistoryType.INCOME);
			List<Entity> listHistory = this.investorAccountHistoryService.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
			if(listHistory != null && !listHistory.isEmpty()){
				Map<String, String> inputParamsBuy = new HashMap<String, String>();
				List<InvestorAccountHistory> listUpdate = new ArrayList<InvestorAccountHistory>();

				Map<String, InvestorProductBuy> ipbUpdate = new HashMap<String, InvestorProductBuy>();
				Map<String, InvestorProductBuy> ipbInsert = new HashMap<String, InvestorProductBuy>();
				
				Map<String, InvestorProductBuyAgreement> ipbaUpdate = new HashMap<String, InvestorProductBuyAgreement>();
				Map<String, InvestorProductBuyAgreement> ipbaInsert = new HashMap<String, InvestorProductBuyAgreement>();
				
//				List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
				List<CompanyAccountHistory> listCompanyAccountHistory = new ArrayList<CompanyAccountHistory>();
				PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.TOTAL);
				PlatformAccount paf = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
				PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
//				List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
				Map<String, Investor> investMap = new HashMap<String, Investor>();
				Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
				Map<String, BankFinancialProductPublish> bfppMap = new HashMap<String, BankFinancialProductPublish>();
//				platformAccountDAO.update(pa);
				List<MobileCode> codeList = new ArrayList<MobileCode>();
				List<InvestorInformation> infoList = new ArrayList<InvestorInformation>();
				for(Entity entity : listHistory){
					inputParamsBuy.clear();
					inputParamsBuy.put("status", InvestorAccountHistoryStatus.TRANSACTING);
					inputParamsBuy.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
					InvestorAccountHistory iah = (InvestorAccountHistory)entity;
					OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(iah.getOrderId());
					if(obt != null && OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
						//掉用微信查询订单接口查询订单信息
						String nonceStr = UUID.randomUUID().toString().replace("-", "");
						StringBuffer signString = new StringBuffer();
						signString.append("appid=").append(Utlity.WX_APPID);
						signString.append("&mch_id=").append(Utlity.WX_MCH_ID);
						signString.append("&nonce_str=").append(nonceStr);
						signString.append("&out_trade_no=").append(obt.getOrderNum());
						signString.append("&sign_type=").append("MD5");
						signString.append("&key=").append(Utlity.WX_KEY);
						
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("appid", Utlity.WX_APPID);
						params.put("mch_id", Utlity.WX_MCH_ID);
						params.put("nonce_str", nonceStr);
						params.put("sign", MD5.MD5Encode(signString.toString(), "utf-8").toUpperCase());
						params.put("out_trade_no", obt.getOrderNum());
						params.put("sign_type", "MD5");
						String xmls = XMLUtils.getRequestXml(params);
						System.out.println(xmls);
						String url = Utlity.WX_PAYCHECK_URL;
						String xml = HttpUtility.postXml(url, xmls);
						System.out.println(xml);
						Map<String, Object> dataMap = XMLUtils.doXMLParse(xml);
						System.out.println(dataMap);
						long currenttime = System.currentTimeMillis();
						if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(dataMap.get("return_code").toString())){
							if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(dataMap.get("result_code").toString())){
								//检查订单创建时间 超过五分钟的 调用微信关闭订单接口把未支付的订单关闭
								/* trade_state
								 * SUCCESS—支付成功
								 * REFUND—转入退款
								 * NOTPAY—未支付
								 * CLOSED—已关闭
								 * REVOKED—已撤销（刷卡支付）
								 * USERPAYING--用户支付中
								 * PAYERROR--支付失败(其他原因，如银行返回失败)
								 */
								if("USERPAYING".equals(dataMap.get("trade_state"))){//支付中不处理
									continue;
								}
								if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(dataMap.get("trade_state"))){//支付成功 相关操作
									if(InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
										continue;
									}
									//更新状态和处理余额和理财金额
									Investor investor = null;
									if(investMap.containsKey(iah.getInvestor())){
										investor = investMap.get(iah.getInvestor());
									}
									if(investor == null){
										investor = this.investorService.get(iah.getInvestor());
									}
									BigDecimal total = investor.getAccountBalance();
									total = total.add(obt.getTotalFee());
									investor.setAccountBalance(total);//更新余额
									iah.setAccountBalance(total);
									iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
									
									//更新企业账户余额
									CompanyAccount ca = companyAccountMap.get(iah.getCompanyAccount());
									if(ca == null){
										ca = this.companyAccountService.get(iah.getCompanyAccount());
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
									cat.setPurpose("用户充值（购买）");
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
									listCompanyAccountHistory.add(cat);
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
									InvestorInformation ii = new InvestorInformation();
									ii.setCreator(investor.getUuid());
									ii.setStatus(InvestorInformationStatus.UNREAD);
									ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
									ii.setUuid(UUID.randomUUID().toString());
									ii.setInfoText(content);
									ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
									ii.setInvestor(investor.getUuid());
									infoList.add(ii);
									
									//继续成功操作
									inputParamsBuy.put("order", iah.getOrderId());
									inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
									List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
									if(listBuy != null && !listBuy.isEmpty()){
										InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
										iahBuy.setStatus(InvestorAccountHistoryStatus.SUCCESS);
										//购买、提现，需要从个人账户里扣钱
										total = investor.getAccountBalance();
										BigDecimal totalInvest = investor.getTotalInvest();
										total = total.subtract(obt.getTotalFee());
										totalInvest = totalInvest.add(obt.getTotalFee());
										investor.setAccountBalance(total);
										investor.setTotalInvest(totalInvest);//更新账户投资
										iahBuy.setAccountBalance(total);
										listUpdate.add(iahBuy);
										
										//更新用户总投资额
										BigDecimal totalnvestment = pai.getInvestment();
										totalnvestment = totalnvestment.add(iahBuy.getPay());
										pai.setInvestment(totalnvestment);
												
										//主表更新/新建操作
										InvestorProductBuy ipb = null;
										if(ipbUpdate.containsKey(investor.getUuid()+iahBuy.getProduct())){//如果存在则赋值
											ipb = ipbUpdate.get(investor.getUuid()+iahBuy.getProduct());
										}
										if(ipb == null){//否则查询数据库
											inputParams.clear();
											inputParams.put("investor", investor.getUuid());
											inputParams.put("product", iahBuy.getProduct());
											List<Entity> listBuyr = this.investorProductBuyService.getListForPage(inputParams, -1, -1, null, InvestorProductBuy.class);
											if(listBuyr != null && !listBuyr.isEmpty()){//存在则赋值
												ipb = (InvestorProductBuy) listBuyr.get(0);
												BigDecimal totalAmount = ipb.getTotalAmount();
												ipb.setTotalAmount(totalAmount.add(iahBuy.getPay()));
												ipb.setAccountBalance(ipb.getAccountBalance().add(iahBuy.getPay()));
												ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
												ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
												ipbUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipb);
											} else {//否则查询新建实例MAP 存在则赋值 否则新建实例
												if(ipbInsert.containsKey(investor.getUuid()+iahBuy.getProduct())){
													ipb = ipbInsert.get(investor.getUuid()+iahBuy.getProduct());
												}
												if(ipb == null){
													ipb = new InvestorProductBuy();
												}
												ipb.setUuid(UUID.randomUUID().toString());
												ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
												ipb.setInvestor(investor.getUuid());
												ipb.setProduct(iahBuy.getProduct());
												ipb.setStage(InvestorProductBuyStage.CONFIRMING);
												ipb.setTotalAmount(iahBuy.getPay());
												ipb.setAccountBalance(iahBuy.getPay());
												ipb.setTotalRedeem(BigDecimal.ZERO);
												ipb.setTotalReturn(BigDecimal.ZERO);
												ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
												ipbInsert.put(investor.getUuid()+iahBuy.getProduct(), ipb);
											}
											
										} else {
											BigDecimal totalAmount = ipb.getTotalAmount();
											ipb.setTotalAmount(totalAmount.add(iahBuy.getPay()));
											ipb.setAccountBalance(ipb.getAccountBalance().add(iahBuy.getPay()));
											ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
											ipbUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipb);
										}
										
										//电子合同生成操作
										//获取银行理财产品发布信息
										BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(iahBuy.getProduct());
										if (bfpp == null) {
											continue;
										}
										Bank bank = this.bankService.get(bfpp.getCustodian());
										if(bank == null){
											continue;
										}
										
										InvestorProductBuyAgreement ipba = null;
										Boolean isUpdate = true;
										if(ipbaUpdate.containsKey(investor.getUuid()+iahBuy.getProduct())){
											ipba = ipbaUpdate.get(investor.getUuid()+iahBuy.getProduct());
										}
										if(ipba == null){
											inputParams.clear();
											inputParams.put("investor", investor.getUuid());
											inputParams.put("records", iahBuy.getProduct());
											List<Entity> listAgreement = this.investorProductBuyAgreementService.getListForPage(inputParams, -1, -1, null, InvestorProductBuyAgreement.class);
											if(listAgreement != null && !listAgreement.isEmpty()){
												ipba = (InvestorProductBuyAgreement) listAgreement.get(0);
											} else {
												isUpdate = false;
												if(ipbaInsert.containsKey(investor.getUuid()+iahBuy.getProduct())){
													ipba = ipbaInsert.get(investor.getUuid()+iahBuy.getProduct());
												} else {
													ipba = new InvestorProductBuyAgreement();
													ipba.setUuid(UUID.randomUUID().toString());
												}
											}
										} else {
											
										}
										ipba.setCreatetime(new Timestamp(System.currentTimeMillis()));
										ipba.setInvestor(investor.getUuid());
										ipba.setType(InvestorProductBuyAgreementType.BANKPRODUCT);
										ipba.setRecords(ipb.getProduct());
										ipba.setScode(iah.getOrderNum());
										ipba.setName("牛投理财定向委托投资管理协议");
										params = new HashMap<String, Object>();
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
										if(investorProductBuyAgreementService.getCheckScode(ipba.getScode())){
											continue;
										}
										if(isUpdate){
											ipbaUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipba);
										} else {
											ipbaInsert.put(investor.getUuid()+iahBuy.getProduct(), ipba);
										}
									}
//									listInvestorUpdate.add(investor);
									investMap.put(iah.getInvestor(), investor);
								} else if ("REFUND".equals(dataMap.get("trade_state"))) {//转入退款 暂不处理
									continue;
								} else if ("NOTPAY".equals(dataMap.get("trade_state"))) {//超过五分钟的 调用微信关闭订单接口把未支付的订单关闭
									if(InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
										continue;
									}
									if((currenttime-iah.getCreatetime().getTime())>(5*60*1000)){//超过五分钟
										//调用微信关闭订单接口
										String urlClose = Utlity.WX_PAYCLOSE_URL;
										String xmlClose = HttpUtility.postXml(urlClose, xmls);
										System.out.println(xml);
										Map<String, Object> dataMapClose = XMLUtils.doXMLParse(xmlClose);
										System.out.println(dataMapClose);
										if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(dataMapClose.get("return_code").toString())){
											if(OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(dataMapClose.get("result_code").toString())){
												iah.setStatus(InvestorAccountHistoryStatus.CLOSED);
												//继续成功操作
												inputParamsBuy.put("order", iah.getOrderId());
												inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
												List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
												if(listBuy != null && !listBuy.isEmpty()){
													InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
													iahBuy.setStatus(InvestorAccountHistoryStatus.CLOSED);
													listUpdate.add(iahBuy);
													if(InvestorProductBuyProductType.BANK_PRODUCT.equals(iahBuy.getProductType())){
														BankFinancialProductPublish bfpp = null;
														if(bfppMap.containsKey(iahBuy.getProduct())){
															bfpp = bfppMap.get(iahBuy.getProduct());
														}
														if(bfpp == null){
															bfpp = this.bankFinancialProductPublishService.get(iahBuy.getProduct());
														}
														bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(iahBuy.getPay()));
//														listBfpp.add(bfpp);
//														this.bankFinancialProductPublishDAO.update(bfpp);
														bfppMap.put(iahBuy.getProduct(), bfpp);
													}
												} else {//否则不处理
													continue;
												}
											} else {//否则不处理
												continue;
											}
										} else {//否则不处理
											continue;
										}
									} else {//否则不处理
										continue;
									}
								} else if ("CLOSED".equals(dataMap.get("trade_state"))) {//已关闭
									if(InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
										continue;
									}
									iah.setStatus(InvestorAccountHistoryStatus.CLOSED);
									//关闭相关订单
									inputParamsBuy.put("order", iah.getOrderId());
									inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
									List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
									if(listBuy != null && !listBuy.isEmpty()){
										InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
										iahBuy.setStatus(InvestorAccountHistoryStatus.CLOSED);
										listUpdate.add(iahBuy);
										if(InvestorProductBuyProductType.BANK_PRODUCT.equals(iahBuy.getProductType())){
											BankFinancialProductPublish bfpp = null;
											if(bfppMap.containsKey(iahBuy.getProduct())){
												bfpp = bfppMap.get(iahBuy.getProduct());
											}
											if(bfpp == null){
												bfpp = this.bankFinancialProductPublishService.get(iahBuy.getProduct());
											}
											bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(iahBuy.getPay()));
//											listBfpp.add(bfpp);
//											this.bankFinancialProductPublishDAO.update(bfpp);
											bfppMap.put(iahBuy.getProduct(), bfpp);
										}
									}
								} else if ("REVOKED".equals(dataMap.get("trade_state"))) {//已撤销
									if(InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
										continue;
									}
									iah.setStatus(InvestorAccountHistoryStatus.CANCELLED);
									//撤销相关订单
									inputParamsBuy.put("order", iah.getOrderId());
									inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
									List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
									if(listBuy != null && !listBuy.isEmpty()){
										InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
										iahBuy.setStatus(InvestorAccountHistoryStatus.CANCELLED);
										listUpdate.add(iahBuy);
										if(InvestorProductBuyProductType.BANK_PRODUCT.equals(iahBuy.getProductType())){
											BankFinancialProductPublish bfpp = null;
											if(bfppMap.containsKey(iahBuy.getProduct())){
												bfpp = bfppMap.get(iahBuy.getProduct());
											}
											if(bfpp == null){
												bfpp = this.bankFinancialProductPublishService.get(iahBuy.getProduct());
											}
											bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(iahBuy.getPay()));
//											listBfpp.add(bfpp);
//											this.bankFinancialProductPublishDAO.update(bfpp);
											bfppMap.put(iahBuy.getProduct(), bfpp);
										}
									}
								} else if ("PAYERROR".equals(dataMap.get("trade_state"))) {//支付失败
									if(InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())){
										continue;
									}
									iah.setStatus(InvestorAccountHistoryStatus.FAIL);
									//处理相关订单
									inputParamsBuy.put("order", iah.getOrderId());
									inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
									List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
									if(listBuy != null && !listBuy.isEmpty()){
										InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
										iahBuy.setStatus(InvestorAccountHistoryStatus.FAIL);
										listUpdate.add(iahBuy);
										if(InvestorProductBuyProductType.BANK_PRODUCT.equals(iahBuy.getProductType())){
											BankFinancialProductPublish bfpp = null;
											if(bfppMap.containsKey(iahBuy.getProduct())){
												bfpp = bfppMap.get(iahBuy.getProduct());
											}
											if(bfpp == null){
												bfpp = this.bankFinancialProductPublishService.get(iahBuy.getProduct());
											}
											bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(iahBuy.getPay()));
//											listBfpp.add(bfpp);
//											this.bankFinancialProductPublishDAO.update(bfpp);
											bfppMap.put(iahBuy.getProduct(), bfpp);
										}
									}
								}
								listUpdate.add(iah);
							}
						}
					}
				}
				if(!listUpdate.isEmpty()){
					this.investorAccountHistoryService.updateBatch(listUpdate);
				}
				if(!investMap.isEmpty()){
					List<Investor> listInvestorUpdate = new ArrayList<Investor>();
					for(Investor i : investMap.values()){
						listInvestorUpdate.add(i);
					}
					if(!listInvestorUpdate.isEmpty()){
						this.investorService.updateBatch(listInvestorUpdate);
					}
				}
			
				if(!companyAccountMap.isEmpty()){
					List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
					for(CompanyAccount ca : companyAccountMap.values()){
						listCompanyAccount.add(ca);
					}
					//批处理管理公司账户信息
					if(!listCompanyAccount.isEmpty()){
						this.companyAccountService.updateBatch(listCompanyAccount);
					}
				}
				this.platformAccountService.update(pa);
				this.platformAccountService.update(paf);
				this.platformAccountService.update(pai);
				if(!listCompanyAccountHistory.isEmpty()){
					this.companyAccountHistoryService.insertBatch(listCompanyAccountHistory);
				}
				if(!bfppMap.isEmpty()){
					List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
					for(BankFinancialProductPublish bfpp : bfppMap.values()){
						listBfpp.add(bfpp);
					}
					if(!listBfpp.isEmpty()){
						this.bankFinancialProductPublishService.updateBatch(listBfpp);
					}
				}
				
				List<InvestorProductBuy> listInvestorBuyUpdate = new ArrayList<InvestorProductBuy>();
				List<InvestorProductBuy> listInvestorBuyInsert = new ArrayList<InvestorProductBuy>();
				if(!ipbUpdate.isEmpty()){
					for(InvestorProductBuy ipb : ipbUpdate.values()){
						listInvestorBuyUpdate.add(ipb);
					}
				}
				if(!ipbInsert.isEmpty()){
					for(InvestorProductBuy ipb : ipbInsert.values()){
						listInvestorBuyInsert.add(ipb);
					}
				}
				List<InvestorProductBuyAgreement> listInvestorBuyAgreementUpdate = new ArrayList<InvestorProductBuyAgreement>();
				List<InvestorProductBuyAgreement> listInvestorBuyAgreementInsert = new ArrayList<InvestorProductBuyAgreement>();
				if(!ipbaUpdate.isEmpty()){
					for(InvestorProductBuyAgreement ipb : ipbaUpdate.values()){
						listInvestorBuyAgreementUpdate.add(ipb);
					}
				}
				if(!ipbaInsert.isEmpty()){
					for(InvestorProductBuyAgreement ipb : ipbaInsert.values()){
						listInvestorBuyAgreementInsert.add(ipb);
					}
				}

				this.investorProductBuyService.updateBatch(listInvestorBuyUpdate, listInvestorBuyInsert);
				this.investorProductBuyAgreementService.updateBatch(listInvestorBuyAgreementUpdate, listInvestorBuyAgreementInsert);
				
				if(!codeList.isEmpty()){
					for(MobileCode mc : codeList){
						this.investorAccountHistoryService.insertSmsInfo(mc);
					}
				}
				
				if(!infoList.isEmpty()){
					for(InvestorInformation ii : infoList){
						this.investorInformationService.insert(ii);
					}
				}
			}
		} catch (Exception e) {//发生异常刷新缓存
			e.printStackTrace();
			super.flushAll();
		}
//		System.out.println("one-"+System.currentTimeMillis());
	}
	
	/**
	 * 定时处理订单信息--畅捷支付（快捷支付请求订单）
	 * 并同步购买记录状态
	 * @param <T>
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@Scheduled(cron="2 0/5 *  * * ? ")
	public <T> void updateOrderinforChanpayStatus() {
		try {
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("status", InvestorAccountHistoryStatus.TRANSACTING);
			inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY);
			inputParams.put("type", InvestorAccountHistoryType.INCOME);
			List<Entity> listHistory = this.investorAccountHistoryService.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
			if(listHistory != null && !listHistory.isEmpty()){
				Map<String, String> inputParamsBuy = new HashMap<String, String>();
				List<InvestorAccountHistory> listUpdate = new ArrayList<InvestorAccountHistory>();

				Map<String, InvestorProductBuy> ipbUpdate = new HashMap<String, InvestorProductBuy>();
				Map<String, InvestorProductBuy> ipbInsert = new HashMap<String, InvestorProductBuy>();
				
				Map<String, InvestorProductBuyAgreement> ipbaUpdate = new HashMap<String, InvestorProductBuyAgreement>();
				Map<String, InvestorProductBuyAgreement> ipbaInsert = new HashMap<String, InvestorProductBuyAgreement>();
				
//				List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
				List<CompanyAccountHistory> listCompanyAccountHistory = new ArrayList<CompanyAccountHistory>();
				PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.TOTAL);
				PlatformAccount paf = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
				PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
//				List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
				Map<String, Investor> investMap = new HashMap<String, Investor>();
				Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
				Map<String, BankFinancialProductPublish> bfppMap = new HashMap<String, BankFinancialProductPublish>();
//				platformAccountDAO.update(pa);
				List<MobileCode> codeList = new ArrayList<MobileCode>();
				List<InvestorInformation> infoList = new ArrayList<InvestorInformation>();
				for(Entity entity : listHistory){
					inputParamsBuy.clear();
					inputParamsBuy.put("status", InvestorAccountHistoryStatus.TRANSACTING);
					inputParamsBuy.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_WECHART);
					InvestorAccountHistory iah = (InvestorAccountHistory)entity;
					OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(iah.getOrderId());
					if(obt != null && OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
						//掉用畅捷支付查询订单接口查询订单信息
						Map<String,Object> params = new HashMap<String,Object>();
						String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_CHANPAY,Utlity.BILL_PURPOSE_INCOME);
						if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
							orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_CHANPAY,Utlity.BILL_PURPOSE_INCOME);
						}
						Map<String, Object> resultMap = ChanPayUtil.nmg_api_query_trade(obt.getOrderNum(), ChanPayUtil.TYPE_PAY_ORDER, orderNumStr);
						String acceptStatus = resultMap.get("AcceptStatus") == null ? "" : resultMap.get("AcceptStatus").toString();
//						String retCode = resultMap.get("RetCode") == null ? "" : resultMap.get("RetCode").toString();
//						String retMsg = resultMap.get("RetMsg") == null ? "" : resultMap.get("RetMsg").toString();
						String status = resultMap.get("Status") == null ? "" : resultMap.get("Status").toString();
//						String trxId = resultMap.get("TrxId") == null ? "" : resultMap.get("TrxId").toString();
						if(ChanPayUtil.RETACCEPTSTATUS_SUCCESS.equals(acceptStatus)){//接口调用成功
							if(ChanPayUtil.RETSTATUS_SUCCESS.equals(status)){//成功
								if(!InvestorAccountHistoryStatus.TRANSACTING.equals(iah.getStatus())){
									continue;
								}
								//更新状态和处理余额和理财金额
								Investor investor = null;
								if(investMap.containsKey(iah.getInvestor())){
									investor = investMap.get(iah.getInvestor());
								}
								if(investor == null){
									investor = this.investorService.get(iah.getInvestor());
								}
								BigDecimal total = investor.getAccountBalance();
								total = total.add(obt.getTotalFee());
								investor.setAccountBalance(total);//更新余额
								iah.setAccountBalance(total);
								iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
								
								//更新企业账户余额
								CompanyAccount ca = companyAccountMap.get(iah.getCompanyAccount());
								if(ca == null){
									ca = this.companyAccountService.get(iah.getCompanyAccount());
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
//								totalAmountf = totalAmountf.add(obt.getTotalFee());
								
								//更新系统用户总帐户余额
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
								cat.setPurpose("用户充值（购买）");
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
									
									catp.setCreator(investor.getUuid());
									catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
									catp.setInvestorAccountHistory(iah.getUuid());
									catp.setRelated(cat.getUuid());
									cat.setRelated(catp.getUuid());
									
									//20180622增加记录本次余额信息
									catp.setAccountBalance(ca.getAccountBalance());
									
									listCompanyAccountHistory.add(catp);
								}
								listCompanyAccountHistory.add(cat);
								
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
//								this.insertSmsInfo(mc);
								codeList.add(mc);
								
//								content = "【牛投理财】"+content;
								InvestorInformation ii = new InvestorInformation();
								ii.setCreator(investor.getUuid());
								ii.setStatus(InvestorInformationStatus.UNREAD);
								ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
								ii.setUuid(UUID.randomUUID().toString());
								ii.setInfoText(content);
								ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
								ii.setInvestor(investor.getUuid());
								infoList.add(ii);
								
								//继续成功操作
								inputParamsBuy.put("order", iah.getOrderId());
								inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
								List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
								if(listBuy != null && !listBuy.isEmpty()){
									InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
									iahBuy.setStatus(InvestorAccountHistoryStatus.SUCCESS);
									//购买、提现，需要从个人账户里扣钱
									total = investor.getAccountBalance();
									BigDecimal totalInvest = investor.getTotalInvest();
									total = total.subtract(obt.getTotalFee());
									totalInvest = totalInvest.add(obt.getTotalFee());
									investor.setAccountBalance(total);
									investor.setTotalInvest(totalInvest);//更新账户投资
									iahBuy.setAccountBalance(total);
									listUpdate.add(iahBuy);

									//更新用户总投资额
									BigDecimal totalnvestment = pai.getInvestment();
									totalnvestment = totalnvestment.add(iahBuy.getPay());
									pai.setInvestment(totalnvestment);
									
									//主表更新/新建操作
									InvestorProductBuy ipb = null;
									if(ipbUpdate.containsKey(investor.getUuid()+iahBuy.getProduct())){//如果存在则赋值
										ipb = ipbUpdate.get(investor.getUuid()+iahBuy.getProduct());
									}
									if(ipb == null){//否则查询数据库
										inputParams.clear();
										inputParams.put("investor", investor.getUuid());
										inputParams.put("product", iahBuy.getProduct());
										List<Entity> listBuyr = this.investorProductBuyService.getListForPage(inputParams, -1, -1, null, InvestorProductBuy.class);
										if(listBuyr != null && !listBuyr.isEmpty()){//存在则赋值
											ipb = (InvestorProductBuy) listBuyr.get(0);
											BigDecimal totalAmount = ipb.getTotalAmount();
											ipb.setTotalAmount(totalAmount.add(iahBuy.getPay()));
											ipb.setAccountBalance(ipb.getAccountBalance().add(iahBuy.getPay()));
											ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
											ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
											ipbUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipb);
										} else {//否则查询新建实例MAP 存在则赋值 否则新建实例
											if(ipbInsert.containsKey(investor.getUuid()+iahBuy.getProduct())){
												ipb = ipbInsert.get(investor.getUuid()+iahBuy.getProduct());
											}
											if(ipb == null){
												ipb = new InvestorProductBuy();
											}
											ipb.setUuid(UUID.randomUUID().toString());
											ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
											ipb.setInvestor(investor.getUuid());
											ipb.setProduct(iahBuy.getProduct());
											ipb.setStage(InvestorProductBuyStage.CONFIRMING);
											ipb.setTotalAmount(iahBuy.getPay());
											ipb.setAccountBalance(iahBuy.getPay());
											ipb.setTotalRedeem(BigDecimal.ZERO);
											ipb.setTotalReturn(BigDecimal.ZERO);
											ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
											ipbInsert.put(investor.getUuid()+iahBuy.getProduct(), ipb);
										}
										
									} else {
										BigDecimal totalAmount = ipb.getTotalAmount();
										ipb.setTotalAmount(totalAmount.add(iahBuy.getPay()));
										ipb.setAccountBalance(ipb.getAccountBalance().add(iahBuy.getPay()));
										ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
										ipbUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipb);
									}
									
									//电子合同生成操作
									//获取银行理财产品发布信息
									BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(iahBuy.getProduct());
									if (bfpp == null) {
										continue;
									}
									Bank bank = this.bankService.get(bfpp.getCustodian());
									if(bank == null){
										continue;
									}
									
									InvestorProductBuyAgreement ipba = null;
									Boolean isUpdate = true;
									if(ipbaUpdate.containsKey(investor.getUuid()+iahBuy.getProduct())){
										ipba = ipbaUpdate.get(investor.getUuid()+iahBuy.getProduct());
									}
									if(ipba == null){
										inputParams.clear();
										inputParams.put("investor", investor.getUuid());
										inputParams.put("records", iahBuy.getProduct());
										List<Entity> listAgreement = this.investorProductBuyAgreementService.getListForPage(inputParams, -1, -1, null, InvestorProductBuyAgreement.class);
										if(listAgreement != null && !listAgreement.isEmpty()){
											ipba = (InvestorProductBuyAgreement) listAgreement.get(0);
										} else {
											isUpdate = false;
											if(ipbaInsert.containsKey(investor.getUuid()+iahBuy.getProduct())){
												ipba = ipbaInsert.get(investor.getUuid()+iahBuy.getProduct());
											} else {
												ipba = new InvestorProductBuyAgreement();
												ipba.setUuid(UUID.randomUUID().toString());
											}
										}
									} else {
										
									}
									ipba.setCreatetime(new Timestamp(System.currentTimeMillis()));
									ipba.setInvestor(investor.getUuid());
									ipba.setType(InvestorProductBuyAgreementType.BANKPRODUCT);
									ipba.setRecords(ipb.getProduct());
									ipba.setScode(iah.getOrderNum());
									ipba.setName("牛投理财定向委托投资管理协议");
									params = new HashMap<String, Object>();
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
									if(investorProductBuyAgreementService.getCheckScode(ipba.getScode())){
										continue;
									}
									if(isUpdate){
										ipbaUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipba);
									} else {
										ipbaInsert.put(investor.getUuid()+iahBuy.getProduct(), ipba);
									}
								}
//								listInvestorUpdate.add(investor);
								investMap.put(iah.getInvestor(), investor);
							} else if (ChanPayUtil.RETSTATUS_FAIL.equals(status)) {//失败
								if(!InvestorAccountHistoryStatus.TRANSACTING.equals(iah.getStatus())){
									continue;
								}
								iah.setStatus(InvestorAccountHistoryStatus.FAIL);
								//处理相关订单
								inputParamsBuy.put("order", iah.getOrderId());
								inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
								List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
								if(listBuy != null && !listBuy.isEmpty()){
									InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
									iahBuy.setStatus(InvestorAccountHistoryStatus.FAIL);
									listUpdate.add(iahBuy);
									if(InvestorProductBuyProductType.BANK_PRODUCT.equals(iahBuy.getProductType())){
										BankFinancialProductPublish bfpp = null;
										if(bfppMap.containsKey(iahBuy.getProduct())){
											bfpp = bfppMap.get(iahBuy.getProduct());
										}
										if(bfpp == null){
											bfpp = this.bankFinancialProductPublishService.get(iahBuy.getProduct());
										}
										bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(iahBuy.getPay()));
//										listBfpp.add(bfpp);
//										this.bankFinancialProductPublishDAO.update(bfpp);
										bfppMap.put(iahBuy.getProduct(), bfpp);
									}
								}
							} else if (ChanPayUtil.RETSTATUS_RROSESS.equals(status)) {//处理中 不予处理
								continue;
							}
							listUpdate.add(iah);
						} else {//调用失败 不予处理（下次继续）
							continue;
						}
					}
				}
				if(!listUpdate.isEmpty()){
					this.investorAccountHistoryService.updateBatch(listUpdate);
				}
				if(!investMap.isEmpty()){
					List<Investor> listInvestorUpdate = new ArrayList<Investor>();
					for(Investor i : investMap.values()){
						listInvestorUpdate.add(i);
					}
					if(!listInvestorUpdate.isEmpty()){
						this.investorService.updateBatch(listInvestorUpdate);
					}
				}
			
				if(!companyAccountMap.isEmpty()){
					List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
					for(CompanyAccount ca : companyAccountMap.values()){
						listCompanyAccount.add(ca);
					}
					//批处理管理公司账户信息
					if(!listCompanyAccount.isEmpty()){
						this.companyAccountService.updateBatch(listCompanyAccount);
					}
				}
				this.platformAccountService.update(pa);
				this.platformAccountService.update(paf);
				this.platformAccountService.update(pai);
				if(!listCompanyAccountHistory.isEmpty()){
					this.companyAccountHistoryService.insertBatch(listCompanyAccountHistory);
				}
				if(!bfppMap.isEmpty()){
					List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
					for(BankFinancialProductPublish bfpp : bfppMap.values()){
						listBfpp.add(bfpp);
					}
					if(!listBfpp.isEmpty()){
						this.bankFinancialProductPublishService.updateBatch(listBfpp);
					}
				}
				
				List<InvestorProductBuy> listInvestorBuyUpdate = new ArrayList<InvestorProductBuy>();
				List<InvestorProductBuy> listInvestorBuyInsert = new ArrayList<InvestorProductBuy>();
				if(!ipbUpdate.isEmpty()){
					for(InvestorProductBuy ipb : ipbUpdate.values()){
						listInvestorBuyUpdate.add(ipb);
					}
				}
				if(!ipbInsert.isEmpty()){
					for(InvestorProductBuy ipb : ipbInsert.values()){
						listInvestorBuyInsert.add(ipb);
					}
				}
				List<InvestorProductBuyAgreement> listInvestorBuyAgreementUpdate = new ArrayList<InvestorProductBuyAgreement>();
				List<InvestorProductBuyAgreement> listInvestorBuyAgreementInsert = new ArrayList<InvestorProductBuyAgreement>();
				if(!ipbaUpdate.isEmpty()){
					for(InvestorProductBuyAgreement ipb : ipbaUpdate.values()){
						listInvestorBuyAgreementUpdate.add(ipb);
					}
				}
				if(!ipbaInsert.isEmpty()){
					for(InvestorProductBuyAgreement ipb : ipbaInsert.values()){
						listInvestorBuyAgreementInsert.add(ipb);
					}
				}

				this.investorProductBuyService.updateBatch(listInvestorBuyUpdate, listInvestorBuyInsert);
				this.investorProductBuyAgreementService.updateBatch(listInvestorBuyAgreementUpdate, listInvestorBuyAgreementInsert);
				
				if(!codeList.isEmpty()){
					for(MobileCode mc : codeList){
						this.investorAccountHistoryService.insertSmsInfo(mc);
					}
				}
				if(!infoList.isEmpty()){
					for(InvestorInformation ii : infoList){
						this.investorInformationService.insert(ii);
					}
				}
			}
		} catch (Exception e) {
			super.flushAll();
			e.printStackTrace();
		}
//		System.out.println("two-"+System.currentTimeMillis());
	}
	
	/**
	 * 定时处理订单信息--融宝支付（快捷支付请求订单）
	 * 并同步购买记录状态
	 * @param <T>
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
//	@Scheduled(cron="2 0/5 *  * * ? ")
	public <T> void updateOrderinforReapalStatus() {
		try {
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("status", InvestorAccountHistoryStatus.TRANSACTING);
			inputParams.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
			inputParams.put("type", InvestorAccountHistoryType.INCOME);
			List<Entity> listHistory = this.investorAccountHistoryService.getListForPage(inputParams, -1, -1, null, InvestorAccountHistory.class);
			if(listHistory != null && !listHistory.isEmpty()){
				Map<String, String> inputParamsBuy = new HashMap<String, String>();
				List<InvestorAccountHistory> listUpdate = new ArrayList<InvestorAccountHistory>();

				Map<String, InvestorProductBuy> ipbUpdate = new HashMap<String, InvestorProductBuy>();
				Map<String, InvestorProductBuy> ipbInsert = new HashMap<String, InvestorProductBuy>();
				
				Map<String, InvestorProductBuyAgreement> ipbaUpdate = new HashMap<String, InvestorProductBuyAgreement>();
				Map<String, InvestorProductBuyAgreement> ipbaInsert = new HashMap<String, InvestorProductBuyAgreement>();
				
//				List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
				List<CompanyAccountHistory> listCompanyAccountHistory = new ArrayList<CompanyAccountHistory>();
				PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.TOTAL);
				PlatformAccount paf = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
				PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
//				List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
				Map<String, Investor> investMap = new HashMap<String, Investor>();
				Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
				Map<String, BankFinancialProductPublish> bfppMap = new HashMap<String, BankFinancialProductPublish>();
//				platformAccountDAO.update(pa);
				List<MobileCode> codeList = new ArrayList<MobileCode>();
				List<InvestorInformation> infoList = new ArrayList<InvestorInformation>();
				for(Entity entity : listHistory){
					inputParamsBuy.clear();
					inputParamsBuy.put("status", InvestorAccountHistoryStatus.TRANSACTING);
					inputParamsBuy.put("orderType", InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL);
					InvestorAccountHistory iah = (InvestorAccountHistory)entity;
					OrderinfoByThirdparty obt = this.orderinfoByThirdpartyService.get(iah.getOrderId());
					if(obt != null && OrderinfoByThirdpartyReturnStatus.SUCCESS.equals(obt.getStatus())){
						//掉用融宝支付查询订单接口查询订单信息
						Map<String,Object> params = new HashMap<String,Object>();
						String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_INCOME);
						if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
							Thread.sleep(10);
							orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_INCOME);
						}
						Map<String, Object> resultMap = ReapalUtlity.orderQuery(obt.getOrderNum());
//						String acceptStatus = resultMap.get("AcceptStatus") == null ? "" : resultMap.get("AcceptStatus").toString();
//						String retCode = resultMap.get("RetCode") == null ? "" : resultMap.get("RetCode").toString();
//						String retMsg = resultMap.get("RetMsg") == null ? "" : resultMap.get("RetMsg").toString();
						String status = resultMap.get("status") == null ? "" : resultMap.get("status").toString();
//						String trxId = resultMap.get("TrxId") == null ? "" : resultMap.get("TrxId").toString();
//						if(ChanPayUtil.RETACCEPTSTATUS_SUCCESS.equals(acceptStatus)){//接口调用成功
						if(ReapalUtil.RETSTATUS_SUCCESS.equals(status)){//成功
							if(!InvestorAccountHistoryStatus.TRANSACTING.equals(iah.getStatus())){
								continue;
							}
							//更新状态和处理余额和理财金额
							Investor investor = null;
							if(investMap.containsKey(iah.getInvestor())){
								investor = investMap.get(iah.getInvestor());
							}
							if(investor == null){
								investor = this.investorService.get(iah.getInvestor());
							}
							BigDecimal total = investor.getAccountBalance();
							total = total.add(obt.getTotalFee());
							investor.setAccountBalance(total);//更新余额
							iah.setAccountBalance(total);
							iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
							
							//更新企业账户余额
							CompanyAccount ca = companyAccountMap.get(iah.getCompanyAccount());
							if(ca == null){
								ca = this.companyAccountService.get(iah.getCompanyAccount());
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
//								totalAmountf = totalAmountf.add(obt.getTotalFee());
							
							//更新系统用户总帐户余额
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
							cat.setPurpose("用户充值（购买）");
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
								
								catp.setCreator(investor.getUuid());
								catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
								catp.setInvestorAccountHistory(iah.getUuid());
								catp.setRelated(cat.getUuid());
								cat.setRelated(catp.getUuid());
								
								//20180622增加记录本次余额信息
								catp.setAccountBalance(ca.getAccountBalance());
								
								listCompanyAccountHistory.add(catp);
							}
							listCompanyAccountHistory.add(cat);
							
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
//								this.insertSmsInfo(mc);
							codeList.add(mc);
							
//								content = "【牛投理财】"+content;
							InvestorInformation ii = new InvestorInformation();
							ii.setCreator(investor.getUuid());
							ii.setStatus(InvestorInformationStatus.UNREAD);
							ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
							ii.setUuid(UUID.randomUUID().toString());
							ii.setInfoText(content);
							ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
							ii.setInvestor(investor.getUuid());
							infoList.add(ii);
							
							//继续成功操作
							inputParamsBuy.put("order", iah.getOrderId());
							inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
							List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
							if(listBuy != null && !listBuy.isEmpty()){
								InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
								iahBuy.setStatus(InvestorAccountHistoryStatus.SUCCESS);
								//购买、提现，需要从个人账户里扣钱
								total = investor.getAccountBalance();
								BigDecimal totalInvest = investor.getTotalInvest();
								total = total.subtract(obt.getTotalFee());
								totalInvest = totalInvest.add(obt.getTotalFee());
								investor.setAccountBalance(total);
								investor.setTotalInvest(totalInvest);//更新账户投资
								iahBuy.setAccountBalance(total);
								listUpdate.add(iahBuy);

								//更新用户总投资额
								BigDecimal totalnvestment = pai.getInvestment();
								totalnvestment = totalnvestment.add(iahBuy.getPay());
								pai.setInvestment(totalnvestment);
								
								//主表更新/新建操作
								InvestorProductBuy ipb = null;
								if(ipbUpdate.containsKey(investor.getUuid()+iahBuy.getProduct())){//如果存在则赋值
									ipb = ipbUpdate.get(investor.getUuid()+iahBuy.getProduct());
								}
								if(ipb == null){//否则查询数据库
									inputParams.clear();
									inputParams.put("investor", investor.getUuid());
									inputParams.put("product", iahBuy.getProduct());
									List<Entity> listBuyr = this.investorProductBuyService.getListForPage(inputParams, -1, -1, null, InvestorProductBuy.class);
									if(listBuyr != null && !listBuyr.isEmpty()){//存在则赋值
										ipb = (InvestorProductBuy) listBuyr.get(0);
										BigDecimal totalAmount = ipb.getTotalAmount();
										ipb.setTotalAmount(totalAmount.add(iahBuy.getPay()));
										ipb.setAccountBalance(ipb.getAccountBalance().add(iahBuy.getPay()));
										ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
										ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
										ipbUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipb);
									} else {//否则查询新建实例MAP 存在则赋值 否则新建实例
										if(ipbInsert.containsKey(investor.getUuid()+iahBuy.getProduct())){
											ipb = ipbInsert.get(investor.getUuid()+iahBuy.getProduct());
										}
										if(ipb == null){
											ipb = new InvestorProductBuy();
										}
										ipb.setUuid(UUID.randomUUID().toString());
										ipb.setCreatetime(new Timestamp(System.currentTimeMillis()));
										ipb.setInvestor(investor.getUuid());
										ipb.setProduct(iahBuy.getProduct());
										ipb.setStage(InvestorProductBuyStage.CONFIRMING);
										ipb.setTotalAmount(iahBuy.getPay());
										ipb.setAccountBalance(iahBuy.getPay());
										ipb.setTotalRedeem(BigDecimal.ZERO);
										ipb.setTotalReturn(BigDecimal.ZERO);
										ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
										ipbInsert.put(investor.getUuid()+iahBuy.getProduct(), ipb);
									}
									
								} else {
									BigDecimal totalAmount = ipb.getTotalAmount();
									ipb.setTotalAmount(totalAmount.add(iahBuy.getPay()));
									ipb.setAccountBalance(ipb.getAccountBalance().add(iahBuy.getPay()));
									ipb.setType(InvestorProductBuyProductType.BANK_PRODUCT);
									ipbUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipb);
								}
								
								//电子合同生成操作
								//获取银行理财产品发布信息
								BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(iahBuy.getProduct());
								if (bfpp == null) {
									continue;
								}
								Bank bank = this.bankService.get(bfpp.getCustodian());
								if(bank == null){
									continue;
								}
								
								InvestorProductBuyAgreement ipba = null;
								Boolean isUpdate = true;
								if(ipbaUpdate.containsKey(investor.getUuid()+iahBuy.getProduct())){
									ipba = ipbaUpdate.get(investor.getUuid()+iahBuy.getProduct());
								}
								if(ipba == null){
									inputParams.clear();
									inputParams.put("investor", investor.getUuid());
									inputParams.put("records", iahBuy.getProduct());
									List<Entity> listAgreement = this.investorProductBuyAgreementService.getListForPage(inputParams, -1, -1, null, InvestorProductBuyAgreement.class);
									if(listAgreement != null && !listAgreement.isEmpty()){
										ipba = (InvestorProductBuyAgreement) listAgreement.get(0);
									} else {
										isUpdate = false;
										if(ipbaInsert.containsKey(investor.getUuid()+iahBuy.getProduct())){
											ipba = ipbaInsert.get(investor.getUuid()+iahBuy.getProduct());
										} else {
											ipba = new InvestorProductBuyAgreement();
											ipba.setUuid(UUID.randomUUID().toString());
										}
									}
								} else {
									
								}
								ipba.setCreatetime(new Timestamp(System.currentTimeMillis()));
								ipba.setInvestor(investor.getUuid());
								ipba.setType(InvestorProductBuyAgreementType.BANKPRODUCT);
								ipba.setRecords(ipb.getProduct());
								ipba.setScode(iah.getOrderNum());
								ipba.setName("牛投理财定向委托投资管理协议");
								params = new HashMap<String, Object>();
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
								if(investorProductBuyAgreementService.getCheckScode(ipba.getScode())){
									continue;
								}
								if(isUpdate){
									ipbaUpdate.put(investor.getUuid()+iahBuy.getProduct(), ipba);
								} else {
									ipbaInsert.put(investor.getUuid()+iahBuy.getProduct(), ipba);
								}
							}
//								listInvestorUpdate.add(investor);
							investMap.put(iah.getInvestor(), investor);
						} else if (ReapalUtil.RETSTATUS_FAIL.equals(status)) {//失败
							if(!InvestorAccountHistoryStatus.TRANSACTING.equals(iah.getStatus())){
								continue;
							}
							iah.setStatus(InvestorAccountHistoryStatus.FAIL);
							//处理相关订单
							inputParamsBuy.put("order", iah.getOrderId());
							inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
							List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
							if(listBuy != null && !listBuy.isEmpty()){
								InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
								iahBuy.setStatus(InvestorAccountHistoryStatus.FAIL);
								listUpdate.add(iahBuy);
								if(InvestorProductBuyProductType.BANK_PRODUCT.equals(iahBuy.getProductType())){
									BankFinancialProductPublish bfpp = null;
									if(bfppMap.containsKey(iahBuy.getProduct())){
										bfpp = bfppMap.get(iahBuy.getProduct());
									}
									if(bfpp == null){
										bfpp = this.bankFinancialProductPublishService.get(iahBuy.getProduct());
									}
									bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(iahBuy.getPay()));
//										listBfpp.add(bfpp);
//										this.bankFinancialProductPublishDAO.update(bfpp);
									bfppMap.put(iahBuy.getProduct(), bfpp);
								}
							}
						} else if (ReapalUtil.RETSTATUS_RROSESS.equals(status)) {//处理中 不予处理
							continue;
						} else if (ReapalUtil.RETSTATUS_WAIT.equals(status)) {
							continue;
						} else if (ReapalUtil.RETSTATUS_CLOSE.equals(status)) {
							if(!InvestorAccountHistoryStatus.TRANSACTING.equals(iah.getStatus())){
								continue;
							}
							iah.setStatus(InvestorAccountHistoryStatus.CLOSED);
							//处理相关订单
							inputParamsBuy.put("order", iah.getOrderId());
							inputParamsBuy.put("type", InvestorAccountHistoryType.BUY);
							List<Entity> listBuy = this.investorAccountHistoryService.getListForPage(inputParamsBuy, -1, -1, null, InvestorAccountHistory.class);
							if(listBuy != null && !listBuy.isEmpty()){
								InvestorAccountHistory iahBuy = (InvestorAccountHistory) listBuy.get(0);
								iahBuy.setStatus(InvestorAccountHistoryStatus.CLOSED);
								listUpdate.add(iahBuy);
								if(InvestorProductBuyProductType.BANK_PRODUCT.equals(iahBuy.getProductType())){
									BankFinancialProductPublish bfpp = null;
									if(bfppMap.containsKey(iahBuy.getProduct())){
										bfpp = bfppMap.get(iahBuy.getProduct());
									}
									if(bfpp == null){
										bfpp = this.bankFinancialProductPublishService.get(iahBuy.getProduct());
									}
									bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(iahBuy.getPay()));
//										listBfpp.add(bfpp);
//										this.bankFinancialProductPublishDAO.update(bfpp);
									bfppMap.put(iahBuy.getProduct(), bfpp);
								}
							}
						}
						listUpdate.add(iah);
//						} else {//调用失败 不予处理（下次继续）
//							continue;
//						}
					}
				}
				if(!listUpdate.isEmpty()){
					this.investorAccountHistoryService.updateBatch(listUpdate);
				}
				if(!investMap.isEmpty()){
					List<Investor> listInvestorUpdate = new ArrayList<Investor>();
					for(Investor i : investMap.values()){
						listInvestorUpdate.add(i);
					}
					if(!listInvestorUpdate.isEmpty()){
						this.investorService.updateBatch(listInvestorUpdate);
					}
				}
			
				if(!companyAccountMap.isEmpty()){
					List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
					for(CompanyAccount ca : companyAccountMap.values()){
						listCompanyAccount.add(ca);
					}
					//批处理管理公司账户信息
					if(!listCompanyAccount.isEmpty()){
						this.companyAccountService.updateBatch(listCompanyAccount);
					}
				}
				this.platformAccountService.update(pa);
				this.platformAccountService.update(paf);
				this.platformAccountService.update(pai);
				if(!listCompanyAccountHistory.isEmpty()){
					this.companyAccountHistoryService.insertBatch(listCompanyAccountHistory);
				}
				if(!bfppMap.isEmpty()){
					List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
					for(BankFinancialProductPublish bfpp : bfppMap.values()){
						listBfpp.add(bfpp);
					}
					if(!listBfpp.isEmpty()){
						this.bankFinancialProductPublishService.updateBatch(listBfpp);
					}
				}
				
				List<InvestorProductBuy> listInvestorBuyUpdate = new ArrayList<InvestorProductBuy>();
				List<InvestorProductBuy> listInvestorBuyInsert = new ArrayList<InvestorProductBuy>();
				if(!ipbUpdate.isEmpty()){
					for(InvestorProductBuy ipb : ipbUpdate.values()){
						listInvestorBuyUpdate.add(ipb);
					}
				}
				if(!ipbInsert.isEmpty()){
					for(InvestorProductBuy ipb : ipbInsert.values()){
						listInvestorBuyInsert.add(ipb);
					}
				}
				List<InvestorProductBuyAgreement> listInvestorBuyAgreementUpdate = new ArrayList<InvestorProductBuyAgreement>();
				List<InvestorProductBuyAgreement> listInvestorBuyAgreementInsert = new ArrayList<InvestorProductBuyAgreement>();
				if(!ipbaUpdate.isEmpty()){
					for(InvestorProductBuyAgreement ipb : ipbaUpdate.values()){
						listInvestorBuyAgreementUpdate.add(ipb);
					}
				}
				if(!ipbaInsert.isEmpty()){
					for(InvestorProductBuyAgreement ipb : ipbaInsert.values()){
						listInvestorBuyAgreementInsert.add(ipb);
					}
				}

				this.investorProductBuyService.updateBatch(listInvestorBuyUpdate, listInvestorBuyInsert);
				this.investorProductBuyAgreementService.updateBatch(listInvestorBuyAgreementUpdate, listInvestorBuyAgreementInsert);
				
				if(!codeList.isEmpty()){
					for(MobileCode mc : codeList){
						this.investorAccountHistoryService.insertSmsInfo(mc);
					}
				}
				if(!infoList.isEmpty()){
					for(InvestorInformation ii : infoList){
						this.investorInformationService.insert(ii);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
		}
//		System.out.println("two-"+System.currentTimeMillis());
	}
	
	
	/**
	 * 定时处理企财宝订单信息--融宝支付（快捷支付请求订单）
	 * 并同步购买记录状态
	 * @param <T>
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
//	@Scheduled(cron="2 0/5 *  * * ? ")
	public <T> void updateQcbOrderinforReapalStatus() {
		try {
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("status", QcbEmployeeHistoryStatus.TRANSACTING);
			inputParams.put("orderType", QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
			inputParams.put("type", QcbEmployeeHistoryType.INCOME);
			List<Entity> listHistory = this.qcbEmployeeHistoryService.getListForPage(inputParams, -1, -1, null, QcbEmployeeHistory.class);
			if(listHistory != null && !listHistory.isEmpty()){
				Map<String, String> inputParamsBuy = new HashMap<String, String>();
				List<QcbEmployeeHistory> listUpdate = new ArrayList<QcbEmployeeHistory>();

				Map<String, QcbEmployeeProductBuy> qepbUpdate = new HashMap<String, QcbEmployeeProductBuy>();
				Map<String, QcbEmployeeProductBuy> qepbInsert = new HashMap<String, QcbEmployeeProductBuy>();
				
				Map<String, QcbEmployeeProductBuyAgreement> qepbaUpdate = new HashMap<String, QcbEmployeeProductBuyAgreement>();
				Map<String, QcbEmployeeProductBuyAgreement> qepbaInsert = new HashMap<String, QcbEmployeeProductBuyAgreement>();
				
				List<CompanyAccountHistory> listCompanyAccountHistory = new ArrayList<CompanyAccountHistory>();
				PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.TOTAL);
				PlatformAccount paf = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
				Map<String, QcbEmployee> investMap = new HashMap<String, QcbEmployee>();
				Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
				Map<String, BankFinancialProductPublish> bfppMap = new HashMap<String, BankFinancialProductPublish>();
				List<MobileCode> codeList = new ArrayList<MobileCode>();
//				List<InvestorInformation> infoList = new ArrayList<InvestorInformation>();
				for(Entity entity : listHistory){
					inputParamsBuy.clear();
					inputParamsBuy.put("status", QcbEmployeeHistoryStatus.TRANSACTING);
					inputParamsBuy.put("orderType", QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL);
					QcbEmployeeHistory qeh = (QcbEmployeeHistory)entity;
					QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(qeh.getOrderId());
					if(qobt != null && QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(qobt.getStatus())){
						//掉用融宝支付查询订单接口查询订单信息
						Map<String,Object> params = new HashMap<String,Object>();
						String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_INCOME);
						if(this.qcbEmployeeHistoryService.checkOrderNum(orderNumStr)){
							Thread.sleep(10);
							orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_INCOME);
						}
						Map<String, Object> resultMap = ReapalUtlity.orderQuery(qobt.getOrderNum());
						String status = resultMap.get("status") == null ? "" : resultMap.get("status").toString();
						if(ReapalUtil.RETSTATUS_SUCCESS.equals(status)){//成功
							if(!QcbEmployeeHistoryStatus.TRANSACTING.equals(qeh.getStatus())){
								continue;
							}
							//更新状态和处理余额和理财金额
							QcbEmployee qe = null;
							if(investMap.containsKey(qeh.getQcbEmployee())){
								qe = investMap.get(qeh.getQcbEmployee());
							}
							if(qe == null){
								qe = this.qcbEmployeeService.get(qeh.getQcbEmployee());
							}
							BigDecimal total = qe.getAccountBalance();
							total = total.add(qobt.getTotalFee());
							qe.setAccountBalance(total);//更新余额
							qeh.setAccountBalance(total);
							qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
							
							//更新企业账户余额
							CompanyAccount ca = companyAccountMap.get(qeh.getCompanyAccount());
							if(ca == null){
								ca = this.companyAccountService.get(qeh.getCompanyAccount());
							}
									
							if(ca == null){
								continue;
							}
							BigDecimal accountTotal = ca.getAccountBalance();
							accountTotal = accountTotal.add(qobt.getTotalFee());
							
							//更新系统总帐户余额
							BigDecimal totalAmountp = pa.getTotalAmount();
							totalAmountp = totalAmountp.add(qobt.getTotalFee());
							
							//更新系统总帐户余额
							BigDecimal totalAmountf = paf.getTotalAmount();
							
							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								accountTotal = accountTotal.subtract(qeh.getPoundage());
								totalAmountp = totalAmountp.subtract(qeh.getPoundage());
								totalAmountf = totalAmountf.subtract(qeh.getPoundage());
							}
							pa.setTotalAmount(totalAmountp);
							paf.setTotalAmount(totalAmountf);
							ca.setAccountBalance(accountTotal);
							companyAccountMap.put(qeh.getCompanyAccount(), ca);
							
							//增加公司账户交易记录--用户充值
							CompanyAccountHistory cat = new CompanyAccountHistory();
							cat.setUuid(UUID.randomUUID().toString());
							cat.setCompanyAccountIn(qeh.getCompanyAccount());//用户充值
							cat.setType(CompanyAccountHistoryType.FILLIN);

							cat.setTotalAmount(qobt.getTotalFee());
							cat.setPoundage(qeh.getPoundage());
							cat.setPurpose("企财宝员工充值（购买）");
							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
							cat.setQcbEmployee(qe.getUuid());
							cat.setCreator(qe.getUuid());
							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
							cat.setQcbEmployeeHistory(qeh.getUuid());
							
							//20180622增加记录本次余额信息
							cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
							
							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								//增加公司账户交易记录--费用录入（扣除交易手续费）
								CompanyAccountHistory catp = new CompanyAccountHistory();
								catp.setUuid(UUID.randomUUID().toString());
								catp.setCompanyAccountOut(qeh.getCompanyAccount());//用户充值
								catp.setType(CompanyAccountHistoryType.EXPEND);
								catp.setQcbEmployee(qe.getUuid());
								catp.setTotalAmount(qeh.getPoundage());
								catp.setPoundage(BigDecimal.ZERO);
								catp.setPurpose("企财宝员工充值--手续费扣除");
								catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
								
								catp.setCreator(qe.getUuid());
								catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
								catp.setQcbEmployeeHistory(qeh.getUuid());
								catp.setRelated(cat.getUuid());
								cat.setRelated(catp.getUuid());
								
								//20180622增加记录本次余额信息
								catp.setAccountBalance(ca.getAccountBalance());
								
								listCompanyAccountHistory.add(catp);
							}
							listCompanyAccountHistory.add(cat);
							
							//处理成功，发送通知短信
							MobileCode mc = new MobileCode();
							String content = "尊敬的企财宝用户您好：您于"+Utlity.timeSpanToChinaDateString(qeh.getCreatetime())+"提交的"+Utlity.numFormat4UnitDetailLess(qeh.getIncome())+"元充值申请已处理成功，请注意查询。";
							String mobile = qe.getMobile();
							String codeInfo = Utlity.getCaptcha();
							mc.setCode(codeInfo);
							mc.setContent(content);
							mc.setMobile(mobile);
							mc.setCreator(qe.getUuid());
							mc.setStatus(MobileCodeStatus.DISABLE);
							mc.setType(MobileCodeTypes.NOTICE);
							mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
							mc.setCreatorType(MobileCodeCreatorType.QCB_EMP);
							mc.setUuid(UUID.randomUUID().toString());
							codeList.add(mc);
							
//								content = "【牛投理财】"+content;
//							InvestorInformation ii = new InvestorInformation();
//							ii.setCreator(investor.getUuid());
//							ii.setStatus(InvestorInformationStatus.UNREAD);
//							ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
//							ii.setUuid(UUID.randomUUID().toString());
//							ii.setInfoText(content);
//							ii.setInfoTitle(InvestorInformationTitle.RECHARGE);
//							ii.setInvestor(investor.getUuid());
//							infoList.add(ii);
							
							//继续成功操作
							inputParamsBuy.put("order", qeh.getOrderId());
							inputParamsBuy.put("type", QcbEmployeeHistoryType.BUY);
							List<Entity> listBuy = this.qcbEmployeeHistoryService.getListForPage(inputParamsBuy, -1, -1, null, QcbEmployeeHistory.class);
							if(listBuy != null && !listBuy.isEmpty()){
								QcbEmployeeHistory qehBuy = (QcbEmployeeHistory) listBuy.get(0);
								qehBuy.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
								//购买、提现，需要从个人账户里扣钱
								total = qe.getAccountBalance();
								BigDecimal totalInvest = qe.getTotalInvest();
								total = total.subtract(qobt.getTotalFee());
								totalInvest = totalInvest.add(qobt.getTotalFee());
								qe.setAccountBalance(total);
								qe.setTotalInvest(totalInvest);//更新账户投资
								qehBuy.setAccountBalance(total);
								listUpdate.add(qehBuy);
								
								//主表更新/新建操作
								QcbEmployeeProductBuy qepb = null;
								if(qepbUpdate.containsKey(qe.getUuid()+qehBuy.getProduct())){//如果存在则赋值
									qepb = qepbUpdate.get(qe.getUuid()+qehBuy.getProduct());
								}
								if(qepb == null){//否则查询数据库
									inputParams.clear();
									inputParams.put("qcbEmployee", qe.getUuid());
									inputParams.put("product", qehBuy.getProduct());
									List<Entity> listBuyr = this.qcbEmployeeProductBuyService.getListForPage(inputParams, -1, -1, null, QcbEmployeeProductBuy.class);
									if(listBuyr != null && !listBuyr.isEmpty()){//存在则赋值
										qepb = (QcbEmployeeProductBuy) listBuyr.get(0);
										BigDecimal totalAmount = qepb.getTotalAmount();
										qepb.setTotalAmount(totalAmount.add(qehBuy.getPay()));
										qepb.setAccountBalance(qepb.getAccountBalance().add(qehBuy.getPay()));
										qepb.setType(QcbEmployeeProductBuyProductType.BANK_PRODUCT);
										qepb.setCreatetime(new Timestamp(System.currentTimeMillis()));
										qepbUpdate.put(qe.getUuid()+qehBuy.getProduct(), qepb);
									} else {//否则查询新建实例MAP 存在则赋值 否则新建实例
										if(qepbInsert.containsKey(qe.getUuid()+qehBuy.getProduct())){
											qepb = qepbInsert.get(qe.getUuid()+qehBuy.getProduct());
										}
										if(qepb == null){
											qepb = new QcbEmployeeProductBuy();
										}
										qepb.setUuid(UUID.randomUUID().toString());
										qepb.setCreatetime(new Timestamp(System.currentTimeMillis()));
										qepb.setQcbEmployee(qe.getUuid());
										qepb.setProduct(qehBuy.getProduct());
										qepb.setStage(QcbEmployeeProductBuyStage.CONFIRMING);
										qepb.setTotalAmount(qehBuy.getPay());
										qepb.setAccountBalance(qehBuy.getPay());
										qepb.setTotalRedeem(BigDecimal.ZERO);
										qepb.setTotalReturn(BigDecimal.ZERO);
										qepb.setType(QcbEmployeeProductBuyProductType.BANK_PRODUCT);
										qepbInsert.put(qe.getUuid()+qehBuy.getProduct(), qepb);
									}
									
								} else {
									BigDecimal totalAmount = qepb.getTotalAmount();
									qepb.setTotalAmount(totalAmount.add(qehBuy.getPay()));
									qepb.setAccountBalance(qepb.getAccountBalance().add(qehBuy.getPay()));
									qepb.setType(QcbEmployeeProductBuyProductType.BANK_PRODUCT);
									qepbUpdate.put(qe.getUuid()+qehBuy.getProduct(), qepb);
								}
								
								//电子合同生成操作
								//获取银行理财产品发布信息
								BankFinancialProductPublish bfpp = bankFinancialProductPublishService.get(qehBuy.getProduct());
								if (bfpp == null) {
									continue;
								}
								Bank bank = this.bankService.get(bfpp.getCustodian());
								if(bank == null){
									continue;
								}
								
								QcbEmployeeProductBuyAgreement qepba = null;
								Boolean isUpdate = true;
								if(qepbaUpdate.containsKey(qe.getUuid()+qehBuy.getProduct())){
									qepba = qepbaUpdate.get(qe.getUuid()+qehBuy.getProduct());
								}
								if(qepba == null){
									inputParams.clear();
									inputParams.put("qcbEmployee", qe.getUuid());
									inputParams.put("records", qehBuy.getProduct());
									List<Entity> listAgreement = this.qcbEmployeeProductBuyAgreementService.getListForPage(inputParams, -1, -1, null, QcbEmployeeProductBuyAgreement.class);
									if(listAgreement != null && !listAgreement.isEmpty()){
										qepba = (QcbEmployeeProductBuyAgreement) listAgreement.get(0);
									} else {
										isUpdate = false;
										if(qepbaInsert.containsKey(qe.getUuid()+qehBuy.getProduct())){
											qepba = qepbaInsert.get(qe.getUuid()+qehBuy.getProduct());
										} else {
											qepba = new QcbEmployeeProductBuyAgreement();
											qepba.setUuid(UUID.randomUUID().toString());
										}
									}
								} else {
									
								}
								qepba.setCreatetime(new Timestamp(System.currentTimeMillis()));
								qepba.setQcbEmployee(qe.getUuid());
								qepba.setType(QcbEmployeeProductBuyAgreementType.BANKPRODUCT);
								qepba.setRecords(qepb.getProduct());
								qepba.setScode(qeh.getOrderNum());
								qepba.setName("牛投理财定向委托投资管理协议");
								params = new HashMap<String, Object>();
								params.put("scode", qeh.getOrderNum());
						    	params.put("realname", qe.getRealname());
						    	params.put("phone", Utlity.getStarMobile(qe.getMobile()));
						    	params.put("idcard", Utlity.getStarIdcard(qe.getIdcard()));
						    	params.put("productName", bank.getShortName()+bfpp.getName());
						    	String priceRMB = RMBUtlity.arabNumToChineseRMB(Double.parseDouble(qepb.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
						    	String priceStr = "人民币："+Utlity.numFormat4UnitDetailLess(qepb.getTotalAmount())+"元 (大写："+priceRMB+"整)";
						    	params.put("price", priceStr);
						    	params.put("uuid", qepba.getUuid());
						    	params.put("fileName", "牛投理财定向委托投资管理协议"+qeh.getOrderNum());
								Map<String, Object> resultPDF = PDFUtlity.ToPdf(params);
								if((Boolean) resultPDF.get("result")){
									qepba.setStatus(QcbEmployeeProductBuyAgreementStatus.SUCCESS);
									qepba.setUrl(resultPDF.get("url")==null?"":resultPDF.get("url").toString());
								} else {
									qepba.setStatus(QcbEmployeeProductBuyAgreementStatus.FAIL);
									qepba.setUrl("");
								}
								if(qcbEmployeeProductBuyAgreementService.getCheckScode(qepba.getScode())){
									continue;
								}
								if(isUpdate){
									qepbaUpdate.put(qe.getUuid()+qehBuy.getProduct(), qepba);
								} else {
									qepbaInsert.put(qe.getUuid()+qehBuy.getProduct(), qepba);
								}
							}
							investMap.put(qeh.getQcbEmployee(), qe);
						} else if (ReapalUtil.RETSTATUS_FAIL.equals(status)) {//失败
							if(!QcbEmployeeHistoryStatus.TRANSACTING.equals(qeh.getStatus())){
								continue;
							}
							qeh.setStatus(QcbEmployeeHistoryStatus.FAIL);
							//处理相关订单
							inputParamsBuy.put("order", qeh.getOrderId());
							inputParamsBuy.put("type", QcbEmployeeHistoryType.BUY);
							List<Entity> listBuy = this.qcbEmployeeHistoryService.getListForPage(inputParamsBuy, -1, -1, null, QcbEmployeeHistory.class);
							if(listBuy != null && !listBuy.isEmpty()){
								QcbEmployeeHistory qehBuy = (QcbEmployeeHistory) listBuy.get(0);
								qehBuy.setStatus(QcbEmployeeHistoryStatus.FAIL);
								listUpdate.add(qehBuy);
								if(QcbEmployeeProductBuyProductType.BANK_PRODUCT.equals(qehBuy.getProductType())){
									BankFinancialProductPublish bfpp = null;
									if(bfppMap.containsKey(qehBuy.getProduct())){
										bfpp = bfppMap.get(qehBuy.getProduct());
									}
									if(bfpp == null){
										bfpp = this.bankFinancialProductPublishService.get(qehBuy.getProduct());
									}
									bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(qehBuy.getPay()));
									bfppMap.put(qehBuy.getProduct(), bfpp);
								}
							}
						} else if (ReapalUtil.RETSTATUS_RROSESS.equals(status)) {//处理中 不予处理
							continue;
						} else if (ReapalUtil.RETSTATUS_WAIT.equals(status)) {
							continue;
						} else if (ReapalUtil.RETSTATUS_CLOSE.equals(status)) {
							if(!QcbEmployeeHistoryStatus.TRANSACTING.equals(qeh.getStatus())){
								continue;
							}
							qeh.setStatus(QcbEmployeeHistoryStatus.CLOSED);
							//处理相关订单
							inputParamsBuy.put("order", qeh.getOrderId());
							inputParamsBuy.put("type", QcbEmployeeHistoryType.BUY);
							List<Entity> listBuy = this.qcbEmployeeHistoryService.getListForPage(inputParamsBuy, -1, -1, null, QcbEmployeeHistory.class);
							if(listBuy != null && !listBuy.isEmpty()){
								QcbEmployeeHistory qehBuy = (QcbEmployeeHistory) listBuy.get(0);
								qehBuy.setStatus(QcbEmployeeHistoryStatus.CLOSED);
								listUpdate.add(qehBuy);
								if(InvestorProductBuyProductType.BANK_PRODUCT.equals(qehBuy.getProductType())){
									BankFinancialProductPublish bfpp = null;
									if(bfppMap.containsKey(qehBuy.getProduct())){
										bfpp = bfppMap.get(qehBuy.getProduct());
									}
									if(bfpp == null){
										bfpp = this.bankFinancialProductPublishService.get(qehBuy.getProduct());
									}
									bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(qehBuy.getPay()));
									bfppMap.put(qehBuy.getProduct(), bfpp);
								}
							}
						}
						listUpdate.add(qeh);
					}
				}
				if(!listUpdate.isEmpty()){
					this.qcbEmployeeHistoryService.updateBatch(listUpdate);
				}
				if(!investMap.isEmpty()){
					List<QcbEmployee> listQcbEmployeeUpdate = new ArrayList<QcbEmployee>();
					for(QcbEmployee qe : investMap.values()){
						listQcbEmployeeUpdate.add(qe);
					}
					if(!listQcbEmployeeUpdate.isEmpty()){
						this.qcbEmployeeService.updateBatch(listQcbEmployeeUpdate);
					}
				}
			
				if(!companyAccountMap.isEmpty()){
					List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
					for(CompanyAccount ca : companyAccountMap.values()){
						listCompanyAccount.add(ca);
					}
					//批处理管理公司账户信息
					if(!listCompanyAccount.isEmpty()){
						this.companyAccountService.updateBatch(listCompanyAccount);
					}
				}
				this.platformAccountService.update(pa);
				this.platformAccountService.update(paf);
				if(!listCompanyAccountHistory.isEmpty()){
					this.companyAccountHistoryService.insertBatch(listCompanyAccountHistory);
				}
				if(!bfppMap.isEmpty()){
					List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
					for(BankFinancialProductPublish bfpp : bfppMap.values()){
						listBfpp.add(bfpp);
					}
					if(!listBfpp.isEmpty()){
						this.bankFinancialProductPublishService.updateBatch(listBfpp);
					}
				}
				
				List<QcbEmployeeProductBuy> listQcbEmployeeBuyUpdate = new ArrayList<QcbEmployeeProductBuy>();
				List<QcbEmployeeProductBuy> listQcbEmployeeBuyInsert = new ArrayList<QcbEmployeeProductBuy>();
				if(!qepbUpdate.isEmpty()){
					for(QcbEmployeeProductBuy qepb : qepbUpdate.values()){
						listQcbEmployeeBuyUpdate.add(qepb);
					}
				}
				if(!qepbInsert.isEmpty()){
					for(QcbEmployeeProductBuy qepb : qepbInsert.values()){
						listQcbEmployeeBuyInsert.add(qepb);
					}
				}
				List<QcbEmployeeProductBuyAgreement> listQcbEmployeeBuyAgreementUpdate = new ArrayList<QcbEmployeeProductBuyAgreement>();
				List<QcbEmployeeProductBuyAgreement> listQcbEmployeeBuyAgreementInsert = new ArrayList<QcbEmployeeProductBuyAgreement>();
				if(!qepbaUpdate.isEmpty()){
					for(QcbEmployeeProductBuyAgreement qepb : qepbaUpdate.values()){
						listQcbEmployeeBuyAgreementUpdate.add(qepb);
					}
				}
				if(!qepbaInsert.isEmpty()){
					for(QcbEmployeeProductBuyAgreement qepb : qepbaInsert.values()){
						listQcbEmployeeBuyAgreementInsert.add(qepb);
					}
				}

				this.qcbEmployeeProductBuyService.updateBatch(listQcbEmployeeBuyUpdate, listQcbEmployeeBuyInsert);
				this.qcbEmployeeProductBuyAgreementService.updateBatch(listQcbEmployeeBuyAgreementUpdate, listQcbEmployeeBuyAgreementInsert);
				
				if(!codeList.isEmpty()){
					for(MobileCode mc : codeList){
						this.qcbEmployeeHistoryService.insertSmsInfo(mc);
					}
				}
//				if(!infoList.isEmpty()){
//					for(InvestorInformation ii : infoList){
//						this.investorInformationService.insert(ii);
//					}
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
		}
	}
	
	/**
	 * 定时处理社保熊订单信息--融宝支付（快捷支付请求订单）
	 * 并同步缴费记录状态
	 * @param <T>
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@Scheduled(cron="2 0/5 *  * * ? ")
	public <T> void updateShbxOrderinforReapalStatus() {
		try {
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("status", ShbxUserHistoryStatus.TRANSACTING);
			inputParams.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
			inputParams.put("type", ShbxUserHistoryType.BUY_SHBX);
			List<Entity> listHistory = this.shbxUserHistoryService.getListForPage(inputParams, -1, -1, null, ShbxUserHistory.class);
			if(listHistory != null && !listHistory.isEmpty()){
				Map<String, String> inputParamsBuy = new HashMap<String, String>();
				List<ShbxUserHistory> listUpdate = new ArrayList<ShbxUserHistory>();

//				Map<String, ShbxUserProductBuy> qepbUpdate = new HashMap<String, ShbxUserProductBuy>();
//				Map<String, ShbxUserProductBuy> qepbInsert = new HashMap<String, ShbxUserProductBuy>();
//				
//				Map<String, ShbxUserProductBuyAgreement> qepbaUpdate = new HashMap<String, ShbxUserProductBuyAgreement>();
//				Map<String, ShbxUserProductBuyAgreement> qepbaInsert = new HashMap<String, ShbxUserProductBuyAgreement>();
				List<ShbxSecurityOrder> listUpdateSso = new ArrayList<ShbxSecurityOrder>();
				List<CompanyAccountHistory> listCompanyAccountHistory = new ArrayList<CompanyAccountHistory>();
				PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.TOTAL);
				PlatformAccount paf = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
				Map<String, ShbxUser> investMap = new HashMap<String, ShbxUser>();
				Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
				List<MobileCode> codeList = new ArrayList<MobileCode>();
//				List<InvestorInformation> infoList = new ArrayList<InvestorInformation>();
				for(Entity entity : listHistory){
					inputParamsBuy.clear();
					inputParamsBuy.put("status", ShbxUserHistoryStatus.TRANSACTING);
					inputParamsBuy.put("orderType", ShbxUserHistoryOrderType.PAY_TYPE_REAPAL);
					ShbxUserHistory qeh = (ShbxUserHistory)entity;
					QcbOrderinfoByThirdparty qobt = this.qcbOrderinfoByThirdpartyService.get(qeh.getOrderId());
					if(qobt != null && QcbOrderinfoByThirdpartyReturnStatus.SUCCESS.equals(qobt.getStatus())){
						//掉用融宝支付查询订单接口查询订单信息
						String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_INCOME);
						if(this.shbxUserHistoryService.checkOrderNum(orderNumStr)){
							Thread.sleep(10);
							orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_REAPAL,Utlity.BILL_PURPOSE_INCOME);
						}
						Map<String, Object> resultMap = ReapalUtlity.orderQuery(qobt.getOrderNum());
						String status = resultMap.get("status") == null ? "" : resultMap.get("status").toString();
						if(ReapalUtil.RETSTATUS_SUCCESS.equals(status)){//成功
							if(!ShbxUserHistoryStatus.TRANSACTING.equals(qeh.getStatus())){
								continue;
							}
							//更新状态和处理余额和理财金额
							ShbxUser qe = null;
							if(investMap.containsKey(qeh.getShbxUser())){
								qe = investMap.get(qeh.getShbxUser());
							}
							if(qe == null){
								qe = this.shbxUserService.get(qeh.getShbxUser());
							}
							BigDecimal total = qe.getAccountBalance();
							total = total.add(qobt.getTotalFee());
//							qe.setAccountBalance(total);//更新余额
//							qeh.setAccountBalance(total);
							qeh.setStatus(ShbxUserHistoryStatus.SUCCESS);
							
							//更新企业账户余额
							CompanyAccount ca = companyAccountMap.get(qeh.getCompanyAccount());
							if(ca == null){
								ca = this.companyAccountService.get(qeh.getCompanyAccount());
							}
									
							if(ca == null){
								continue;
							}
							BigDecimal accountTotal = ca.getAccountBalance();
							accountTotal = accountTotal.add(qobt.getTotalFee());
							
							//更新系统总帐户余额
							BigDecimal totalAmountp = pa.getTotalAmount();
							totalAmountp = totalAmountp.add(qobt.getTotalFee());
							
							//更新系统总帐户余额
							BigDecimal totalAmountf = paf.getTotalAmount();
							
							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								accountTotal = accountTotal.subtract(qeh.getPoundage());
								totalAmountp = totalAmountp.subtract(qeh.getPoundage());
								totalAmountf = totalAmountf.subtract(qeh.getPoundage());
							}
							pa.setTotalAmount(totalAmountp);
							paf.setTotalAmount(totalAmountf);
							ca.setAccountBalance(accountTotal);
							companyAccountMap.put(qeh.getCompanyAccount(), ca);
							
							//增加公司账户交易记录--用户充值
							CompanyAccountHistory cat = new CompanyAccountHistory();
							cat.setUuid(UUID.randomUUID().toString());
							cat.setCompanyAccountIn(qeh.getCompanyAccount());//用户充值
							cat.setType(CompanyAccountHistoryType.SHBX_PAY_SHEBAO);

							cat.setTotalAmount(qobt.getTotalFee());
							cat.setPoundage(qeh.getPoundage());
							cat.setPurpose("社保熊用户缴费");
							cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
							cat.setShbxUser(qe.getUuid());
							cat.setCreator(qe.getUuid());
							cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
							cat.setShbxUserHistory(qeh.getUuid());
							
							//20180622增加记录本次余额信息
							cat.setAccountBalance(ca.getAccountBalance().add(qeh.getPoundage()));
							
							//更新缴费信息状态
							ShbxSecurityOrder sso = this.shbxSecurityOrderService.get(qeh.getShbxSecurityOrder());
							if(sso != null){
								sso.setStatus(ShbxSecurityOrderStatus.UNPROCESS);
//								this.shbxSecurityOrderService.update(sso);
								listUpdateSso.add(sso);
							}
							
							if(qeh.getPoundage().compareTo(BigDecimal.ZERO) == 1){
								//增加公司账户交易记录--费用录入（扣除交易手续费）
								CompanyAccountHistory catp = new CompanyAccountHistory();
								catp.setUuid(UUID.randomUUID().toString());
								catp.setCompanyAccountOut(qeh.getCompanyAccount());//用户充值
								catp.setType(CompanyAccountHistoryType.EXPEND);
								catp.setShbxUser(qe.getUuid());
								catp.setTotalAmount(qeh.getPoundage());
								catp.setPoundage(BigDecimal.ZERO);
								catp.setPurpose("社保熊用户充值--手续费扣除");
								catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
								
								catp.setCreator(qe.getUuid());
								catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
								catp.setShbxUserHistory(qeh.getUuid());
								catp.setRelated(cat.getUuid());
								cat.setRelated(catp.getUuid());
								
								//20180622增加记录本次余额信息
								catp.setAccountBalance(ca.getAccountBalance());
								
								listCompanyAccountHistory.add(catp);
							}
							listCompanyAccountHistory.add(cat);
							investMap.put(qeh.getShbxUser(), qe);
						} else if (ReapalUtil.RETSTATUS_FAIL.equals(status)) {//失败
							if(!ShbxUserHistoryStatus.TRANSACTING.equals(qeh.getStatus())){
								continue;
							}
							qeh.setStatus(ShbxUserHistoryStatus.FAIL);
							//更新缴费信息状态
							ShbxSecurityOrder sso = this.shbxSecurityOrderService.get(qeh.getShbxSecurityOrder());
							if(sso != null){
								sso.setStatus(ShbxSecurityOrderStatus.FAIL);
								listUpdateSso.add(sso);
							}
						} else if (ReapalUtil.RETSTATUS_RROSESS.equals(status)) {//处理中 不予处理
							continue;
						} else if (ReapalUtil.RETSTATUS_WAIT.equals(status)) {
							continue;
						} else if (ReapalUtil.RETSTATUS_CLOSE.equals(status)) {
							if(!ShbxUserHistoryStatus.TRANSACTING.equals(qeh.getStatus())){
								continue;
							}
							qeh.setStatus(ShbxUserHistoryStatus.CLOSED);
							//更新缴费信息状态
							ShbxSecurityOrder sso = this.shbxSecurityOrderService.get(qeh.getShbxSecurityOrder());
							if(sso != null){
								sso.setStatus(ShbxSecurityOrderStatus.DELETED);
								listUpdateSso.add(sso);
							}
						}
						listUpdate.add(qeh);
					}
				}
				if(!listUpdate.isEmpty()){
					this.shbxUserHistoryService.updateBatch(listUpdate);
				}
				if(!listUpdateSso.isEmpty()){
					this.shbxSecurityOrderService.updateBatch(listUpdateSso);
				}
				if(!investMap.isEmpty()){
					List<ShbxUser> listShbxUserUpdate = new ArrayList<ShbxUser>();
					for(ShbxUser qe : investMap.values()){
						listShbxUserUpdate.add(qe);
					}
					if(!listShbxUserUpdate.isEmpty()){
						this.shbxUserService.updateBatch(listShbxUserUpdate);
					}
				}
			
				if(!companyAccountMap.isEmpty()){
					List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
					for(CompanyAccount ca : companyAccountMap.values()){
						listCompanyAccount.add(ca);
					}
					//批处理管理公司账户信息
					if(!listCompanyAccount.isEmpty()){
						this.companyAccountService.updateBatch(listCompanyAccount);
					}
				}
				this.platformAccountService.update(pa);
				this.platformAccountService.update(paf);
				if(!listCompanyAccountHistory.isEmpty()){
					this.companyAccountHistoryService.insertBatch(listCompanyAccountHistory);
				}
				
				if(!codeList.isEmpty()){
					for(MobileCode mc : codeList){
						this.shbxUserHistoryService.insertSmsInfo(mc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
		}
	}
	
	/**
	 * 定时处理支付宝转账信息
	 */
//	@Scheduled(cron="5/10 * *  * * ? ")
	public <T> void updateOrderinfoAliTransferStatus(){
		try {
			this.investorAccountHistoryService.updateOrderinfoAliTransferStatus();
		} catch (Exception e) {
			e.printStackTrace();
			super.flushAll();
		}
//		try {
////			long startl = System.currentTimeMillis();
//			Map<String, String> searchMap = new HashMap<String, String>();
//			searchMap.put("status", AutoAliTransferProcessStatus.NORMAL);
//			List<Entity> list = this.autoAliTransferProcessDao.getListForPage(searchMap, -1, -1, null, AutoAliTransferProcess.class);
//			if(list != null && !list.isEmpty()){
//				AutoAliTransferProcess atp = (AutoAliTransferProcess) list.get(0);
////				if(list.size() == 1){
////					
////				}
//				String processStarttime = Utlity.timeSpanToStringLess(atp.getProcesstime())+":00";
//				Timestamp now = new Timestamp(System.currentTimeMillis());
//				String processEndtime = Utlity.timeSpanToString(now);
//				
//				//判断如果起止时间超过一天，就拆分成N天来处理
//				Timestamp start = DataTimeConvert.stringToTimeStamp(processStarttime);
//				long between = now.getTime() - start.getTime();
//				long day = 1000*60*60*24;
//				int dayCount = 0;
//				if(between > day){
//					dayCount = (int) (between/day);
//				}
//				AutoAliTransferProcess processRecords = new AutoAliTransferProcess();
//				processRecords.setUuid(UUID.randomUUID().toString());
//				processRecords.setStatus(AutoAliTransferProcessStatus.NORMAL);
//				processRecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				processRecords.setProcesstime(now);
//				processRecords.setProcessindex(atp.getProcessindex()+1);
//				Integer count = 0;
//				
//				List<InvestorAccountHistory> listHistory= new ArrayList<InvestorAccountHistory>();
//				List<OrderinfoByThirdparty> listOrder = new ArrayList<OrderinfoByThirdparty>();
//
//				List<CompanyAccountHistory> listCompanyAccountHistory = new ArrayList<CompanyAccountHistory>();
//				PlatformAccount pa = this.platformAccountService.get(PlatformAccountUuid.TOTAL);
//				PlatformAccount paf = this.platformAccountService.get(PlatformAccountUuid.BALANCE);
//				PlatformAccount pai = this.platformAccountService.get(PlatformAccountUuid.INVESTOR);
////				List<BankFinancialProductPublish> listBfpp = new ArrayList<BankFinancialProductPublish>();
//				Map<String, Investor> investMap = new HashMap<String, Investor>();
//				Map<String, CompanyAccount> companyAccountMap = new HashMap<String, CompanyAccount>();
//				
//				for(int ii = 0; ii <= dayCount; ii++){
//					processStarttime = Utlity.timeSpanToString(start);
//					if(dayCount == 0){
//						processEndtime = Utlity.timeSpanToString(now);
//					} else {
//						if(ii == dayCount){
//							processEndtime = Utlity.timeSpanToString(now);
//						} else {
//							processEndtime = Utlity.timeSpanToString(new Timestamp(start.getTime()+day));
//						}
//						start = new Timestamp(start.getTime()+day-1000);
//					}
////					processStarttime = "2017-11-23 00:00:00";
////					processEndtime = "2017-11-23 23:59:59";
//					Alipay result = AliUtlity.getBillList(processStarttime, processEndtime, "1");
//					
//					if("T".equals(result.getIsSuccess())){//请求成功
////						Request re = result.getRequest();
////						List<Param> paList = re.getParam();
////						for(Param pa: paList){
////							
////						}
//
//						
//						Response response = result.getResponse();
//						AccountPageQueryResult apq =  response.getAccountPageQueryResult();
//						AccountLogList all = apq.getAccountLogList();
//						List<AccountQueryAccountLogVO> aqaList = all.getAccountQueryAccountLogVO();
//						
//						if(aqaList != null && !aqaList.isEmpty()){
//							for(AccountQueryAccountLogVO aqa : aqaList){
//								String userid = aqa.getBuyer_account() == null ? "" : aqa.getBuyer_account().trim();
//								String income = aqa.getIncome();
//								BigDecimal price = BigDecimal.valueOf(Double.parseDouble(income));
////								String outcome = aqa.getOutcome();
//								String orderNum = aqa.getMerchant_out_order_no() == null ? "" : aqa.getMerchant_out_order_no().trim();
//								String tradeNum = aqa.getIw_account_log_id() == null ? "" : aqa.getIw_account_log_id().trim();
//								String transCode = aqa.getTrans_code_msg() == null ? "" : aqa.getTrans_code_msg().trim();
//								String transData = aqa.getTrans_date() == null ? "" : aqa.getTrans_date().trim();
//								if(AliUtlity.trans_code_msg_olpayment.equals(transCode)){//只有在线支付
//									if(!Utlity.checkStringNull(orderNum)){//有商户订单号，代表是商户接口支付-不予处理
//										continue;
//									}
//									if(price.compareTo(BigDecimal.ZERO) <= 0){//收入金额小于等于0 表示不是用户转账记录 不予处理
//										continue;
//									}
//									//查询userid是否已绑定
//									Investor investor = this.investorService.getByAliUserid(userid, Investor.class);
//									if(investor != null){
//										if(investMap.containsKey(investor.getUuid())){
//											investor = investMap.get(investor.getUuid());
//										} else {
//											investor = this.investorService.get(investor.getUuid());
//										}
//									} else {//当前账户已解绑
//										continue;
//									}
//									
//									//通过userid跟tradeNum查询该条记录是否已处理过
//									Map<String, String> inputParams = new HashMap<String, String>();
//									inputParams.put("payNum", tradeNum);
//									inputParams.put("paySource", userid);
//									inputParams.put("type", OrderinfoByThirdpartyType.ZHIFUBAO_TRANSFER);
////									inputParams.put("status", OrderinfoByThirdpartyType.ZHIFUBAO_TRANSFER);
//									List<Entity> orderList = this.orderinfoByThirdpartyService.getListForPage(inputParams, -1, -1, null, OrderinfoByThirdparty.class);
//									if(orderList != null && !orderList.isEmpty()){//存在 代表已处理过 不再处理
//										continue;
//									}
//									//处理记录入库 新增一条 order记录 一条history记录 
//									String orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_ALIPAY,Utlity.BILL_PAYTYPE_ALIPAY,Utlity.BILL_PURPOSE_INCOME);
//									if(this.investorAccountHistoryService.getCheckOrderNum(orderNumStr)){
////										return ResultManager.createFailResult("手速太快，服务器未响应！");
//										orderNumStr = Utlity.getOrderNumStr(Utlity.BILL_DEVICE_ALIPAY,Utlity.BILL_PAYTYPE_ALIPAY,Utlity.BILL_PURPOSE_INCOME);
//									}
//									OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
//									obt.setUuid(UUID.randomUUID().toString());
//									obt.setType(OrderinfoByThirdpartyType.ZHIFUBAO_TRANSFER);
//									obt.setInvestor(investor.getUuid());
//									obt.setOrderNum(orderNumStr);
//									obt.setBody("用户支付宝转账");
//									obt.setTotalFee(price);
//									obt.setPaySource(userid);
//									obt.setStatus(OrderinfoByThirdpartyResultStatus.SUCCESS);
//									obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
//									obt.setPayNum(tradeNum);
//									if(!"".equals(transData)){
//										obt.setPaytime(DataTimeConvert.stringToTimeStamp(transData));
//									} else {
//										obt.setPaytime(new Timestamp(System.currentTimeMillis()));
//									}
//									obt.setErrCode("");
//									obt.setErrCodeDes("");
//									obt.setMessage("充值成功");
//									listOrder.add(obt);
//									
//									InvestorAccountHistory iah = new InvestorAccountHistory();
//									iah.setUuid(UUID.randomUUID().toString());
//									iah.setInvestor(investor.getUuid());
//									iah.setIncome(price);
//									iah.setPay(BigDecimal.ZERO);
//									iah.setAccountBalance(investor.getAccountBalance().add(price));
//									iah.setOrderId(obt.getUuid());
//									iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_ALIPAY_TRANSFER);
//									iah.setOrderNum(orderNumStr);
//									iah.setType(InvestorAccountHistoryType.INCOME);
//									iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
//									iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
//									iah.setCompanyAccount(CompanyAccountUuid.ALIPAY);
//									iah.setPoundage(BigDecimal.ZERO);
//									listHistory.add(iah);
//									//变更相关账户金额
//									//更新状态和处理余额和理财金额
////									Investor investor = null;
//									
////									if(investor == null){
////										investor = this.investorService.get(iah.getInvestor());
////									}
//									BigDecimal total = investor.getAccountBalance();
//									total = total.add(obt.getTotalFee());
//									investor.setAccountBalance(total);//更新余额
//									
//									//更新企业账户余额
//									CompanyAccount ca = companyAccountMap.get(iah.getCompanyAccount());
//									if(ca == null){
//										ca = this.companyAccountService.get(iah.getCompanyAccount());
//									}
//											
//									if(ca == null){
//										continue;
//									}
//									BigDecimal accountTotal = ca.getAccountBalance();
//									accountTotal = accountTotal.add(obt.getTotalFee());
//									
//									//更新系统总帐户余额
//									BigDecimal totalAmountp = pa.getTotalAmount();
//									totalAmountp = totalAmountp.add(obt.getTotalFee());
//									
//									//更新系统总帐户余额
//									BigDecimal totalAmountf = paf.getTotalAmount();
////									totalAmountf = totalAmountf.add(obt.getTotalFee());
//									
//									BigDecimal totalAmounti = pai.getTotalAmount();
//									totalAmounti = totalAmounti.add(obt.getTotalFee());
//									
//									if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//										accountTotal = accountTotal.subtract(iah.getPoundage());
//										totalAmountp = totalAmountp.subtract(iah.getPoundage());
//										totalAmountf = totalAmountf.subtract(iah.getPoundage());
//									}
//									pa.setTotalAmount(totalAmountp);
//									paf.setTotalAmount(totalAmountf);
//									pai.setTotalAmount(totalAmounti);
//									ca.setAccountBalance(accountTotal);
//									companyAccountMap.put(iah.getCompanyAccount(), ca);
//									
//									//增加公司账户交易记录--用户充值
//									CompanyAccountHistory cat = new CompanyAccountHistory();
//									cat.setUuid(UUID.randomUUID().toString());
//									cat.setCompanyAccountIn(iah.getCompanyAccount());//用户充值
//									cat.setType(CompanyAccountHistoryType.FILLIN);
//									
//									cat.setTotalAmount(obt.getTotalFee());
//									cat.setPoundage(iah.getPoundage());
//									cat.setPurpose("用户充值");
//									cat.setStatus(CompanyAccountHistoryStatus.NORMAL);
//									cat.setInvestor(investor.getUuid());
//									cat.setCreator(investor.getUuid());
//									cat.setCreatetime(new Timestamp(System.currentTimeMillis()));
//									cat.setInvestorAccountHistory(iah.getUuid());
//									
//									if(iah.getPoundage().compareTo(BigDecimal.ZERO) == 1){
//										//增加公司账户交易记录--费用录入（扣除交易手续费）
//										CompanyAccountHistory catp = new CompanyAccountHistory();
//										catp.setUuid(UUID.randomUUID().toString());
//										catp.setCompanyAccountOut(iah.getCompanyAccount());//用户充值
//										catp.setType(CompanyAccountHistoryType.EXPEND);
//										catp.setInvestor(investor.getUuid());
//										catp.setTotalAmount(iah.getPoundage());
//										catp.setPoundage(BigDecimal.ZERO);
//										catp.setPurpose("用户充值--手续费扣除");
//										catp.setStatus(CompanyAccountHistoryStatus.NORMAL);
//										catp.setRelated(cat.getUuid());
//										catp.setCreator(investor.getUuid());
//										catp.setCreatetime(new Timestamp(System.currentTimeMillis()));
//										catp.setInvestorAccountHistory(iah.getUuid());
//										cat.setRelated(catp.getUuid());
//										listCompanyAccountHistory.add(catp);
//									}
//									investMap.put(iah.getInvestor(), investor);
//									listCompanyAccountHistory.add(cat);
//									count++;
//								} else {
//									continue;
//								}
//							}
//						}
////						processRecords.setProcesscount(count);
////						processRecords.setCreatetime(new Timestamp(System.currentTimeMillis()));
////						processRecords.setProcesstime(now);
////						atp.setStatus(AutoAliTransferProcessStatus.DISABLE);
////						this.autoAliTransferProcessDao.update(atp);
////						this.autoAliTransferProcessDao.insert(processRecords);
////						aqaList.get
//						
//						atp.setStatus(AutoAliTransferProcessStatus.DISABLE);
//					} else {//请求失败
//						atp.setStatus(AutoAliTransferProcessStatus.FAIL);
//						System.out.println("支付宝账单处理失败");
//						System.out.println("error:"+result.getError()+"------message:"+AliUtlity.getResultMessage(result.getError()));
//					}
//				}
//				processRecords.setProcesscount(count);
//				
//				if(!listOrder.isEmpty()){
//					this.orderinfoByThirdpartyService.insertBatch(listOrder);
//				}
//				if(!listHistory.isEmpty()){
//					this.investorAccountHistoryService.insertBatch(listHistory);
//				}
//				if(!investMap.isEmpty()){
//					List<Investor> listInvestorUpdate = new ArrayList<Investor>();
//					for(Investor i : investMap.values()){
//						listInvestorUpdate.add(i);
//					}
//					if(!listInvestorUpdate.isEmpty()){
//						this.investorService.updateBatch(listInvestorUpdate);
//					}
//				}
//			
//				if(!companyAccountMap.isEmpty()){
//					List<CompanyAccount> listCompanyAccount = new ArrayList<CompanyAccount>();
//					for(CompanyAccount ca : companyAccountMap.values()){
//						listCompanyAccount.add(ca);
//					}
//					//批处理管理公司账户信息
//					if(!listCompanyAccount.isEmpty()){
//						this.companyAccountService.updateBatch(listCompanyAccount);
//					}
//				}
//				this.platformAccountService.update(pa);
//				this.platformAccountService.update(paf);
//				this.platformAccountService.update(pai);
//				if(!listCompanyAccountHistory.isEmpty()){
//					this.companyAccountHistoryService.insertBatch(listCompanyAccountHistory);
//				}
//				
//				this.autoAliTransferProcessDao.update(atp);
//				this.autoAliTransferProcessDao.insert(processRecords);
//			}
//			
////			long endl = System.currentTimeMillis();
////			System.out.println("three-"+(endl-startl));
//		} catch (Exception e) {
//			e.printStackTrace();
//			super.flushAll();
//		}
	}

	/**
	 * 定期处理融宝异步通知发送过来的信息(非事务)
	 */
	@Scheduled(cron="2 0/2 *  * * ? ")
	public <T> void relaseOrderinforReapalNoticeInfo(){
		try {
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("status", RealpalNoticeInfoStatus.UNPRO);
			List<Entity> list = this.realpalNoticeInfoService.getListForPage(inputParams, -1, -1, null, RealpalNoticeInfo.class);
			List<RealpalNoticeInfo> listUpdate = new ArrayList<RealpalNoticeInfo>();
			if(list != null && !list.isEmpty()){
				for(Entity e : list){
					RealpalNoticeInfo rni = (RealpalNoticeInfo)e;
					String data = rni.getSource();
					Map<String, Object> result = new HashMap<String, Object>();
					HashMap<String, Object> resultMap = null;
					if(RealpalNoticeInfoPayType.TAKEOUT.equals(rni.getPayType())){//牛投理财用户提现
						result.put("data", data);
						resultMap = this.investorAccountHistoryService.insertReapalNotice4Pay(result);
					} else if(RealpalNoticeInfoPayType.QCB_TAKEOUT.equals(rni.getPayType())){
						result.put("data", data);
						resultMap = this.qcbCompanyAccountHistoryService.insertReapalNotice4Pay(result);
					} else if(RealpalNoticeInfoPayType.EMP_TAKEOUT.equals(rni.getPayType())){
						result.put("data", data);
						resultMap = this.qcbEmployeeHistoryService.insertReapalNotice4Pay(result);
					} else if(RealpalNoticeInfoPayType.FILLIN.equals(rni.getPayType())){
						result = JSONUtils.json2map(data);
						resultMap = this.investorAccountHistoryService.insertReapalNotice4Recharge(result);
					} else if(RealpalNoticeInfoPayType.EMP_FILLIN.equals(rni.getPayType())){
						result = JSONUtils.json2map(data);
						resultMap = this.qcbEmployeeHistoryService.insertReapalNotice4Recharge(result);
					} else if(RealpalNoticeInfoPayType.SHBX_TAKEOUT.equals(rni.getPayType())){
						result = JSONUtils.json2map(data);
						resultMap = this.shbxUserHistoryService.insertReapalNotice4RePay(result);
					} else if(RealpalNoticeInfoPayType.SHBX_FILLIN.equals(rni.getPayType())){
						result = JSONUtils.json2map(data);
						resultMap = this.shbxUserHistoryService.insertReapalNotice4Recharge(result);
					} else if(RealpalNoticeInfoPayType.SHBX_PAY_SHEBAO.equals(rni.getPayType())){
						result = JSONUtils.json2map(data);
						resultMap = this.shbxUserHistoryService.insertReapalNotice4RechargeShbx(result);
					} else {//其他类型不予处理
						continue;
					}
					
					if((Boolean)resultMap.get("result")){
						rni.setStatus(RealpalNoticeInfoStatus.SUCCESS);
	    			} else {
	    				rni.setStatus(RealpalNoticeInfoStatus.FAIL);
	    			}
					listUpdate.add(rni);
//					Thread.sleep(100);//静默等待0.1秒
				}
			}
			if(listUpdate != null && listUpdate.size() > 0){
				this.realpalNoticeInfoService.updateAll(listUpdate);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			super.flushAll();
		}
	}
}