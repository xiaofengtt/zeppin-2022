/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBankDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IBankFinancialProductPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorCouponHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorInformationDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorProductBuyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IPlatformAccountDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IProductPublishBalanceOperateDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IProductPublishBalanceOperateService;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
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
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.PlatformAccount.PlatformAccountUuid;
import cn.zeppin.product.ntb.core.entity.ProductPublishBalanceOperate;
import cn.zeppin.product.ntb.core.entity.ProductPublishBalanceOperate.ProductPublishBalanceOperateStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy.QcbEmployeeProductBuyProductType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy.QcbEmployeeProductBuyStage;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeCouponHistoryDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeHistoryDAO;
import cn.zeppin.product.ntb.qcb.dao.api.IQcbEmployeeProductBuyDAO;
import cn.zeppin.product.ntb.qcb.vo.QcbEmployeeCouponHistoryVO;
import cn.zeppin.product.ntb.web.vo.InvestorCouponHistoryVO;
import cn.zeppin.product.utility.ObjectUtlity;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class ProductPublishBalanceOperateService extends BaseService implements IProductPublishBalanceOperateService {

	@Autowired
	private IProductPublishBalanceOperateDAO productPublishBalanceOperateDAO;
	
	@Autowired
	private IPlatformAccountDAO platformAccountDAO;
	
	@Autowired
	private IBankFinancialProductPublishDAO bankFinancialProductPublishDAO;
	
	@Autowired
	private IInvestorDAO investorDAO;
	
	@Autowired
	private IInvestorAccountHistoryDAO investorAccountHistoryDAO;
	
	@Autowired
	private IInvestorProductBuyDAO investorProductBuyDAO;
	
	@Autowired
	private IInvestorCouponHistoryDAO investorCouponHistoryDAO;
	
	@Autowired
	private IQcbEmployeeDAO qcbEmployeeDAO;
	
	@Autowired
	private IQcbEmployeeHistoryDAO qcbEmployeeHistoryDAO;
	
	@Autowired
	private IQcbEmployeeProductBuyDAO qcbEmployeeProductBuyDAO;
	
	@Autowired
	private IQcbEmployeeCouponHistoryDAO qcbEmployeeCouponHistoryDAO;
	
	@Autowired
	private IBankDAO bankDAO;
	
	@Autowired
	private IInvestorInformationDAO investorInformationDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Override
	public ProductPublishBalanceOperate insert(ProductPublishBalanceOperate productPublishBalanceOperate) {
		return productPublishBalanceOperateDAO.insert(productPublishBalanceOperate);
	}

	@Override
	public ProductPublishBalanceOperate delete(ProductPublishBalanceOperate productPublishBalanceOperate) {
		productPublishBalanceOperate.setStatus(ProductPublishBalanceOperateStatus.DELETED);
		return productPublishBalanceOperateDAO.update(productPublishBalanceOperate);
	}

	@Override
	public ProductPublishBalanceOperate update(ProductPublishBalanceOperate productPublishBalanceOperate) {
		return productPublishBalanceOperateDAO.update(productPublishBalanceOperate);
	}

	@Override
	public ProductPublishBalanceOperate get(String uuid) {
		return productPublishBalanceOperateDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return productPublishBalanceOperateDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return productPublishBalanceOperateDAO.getCount(inputParams);
	}
	
	/**
	 * 审核
	 * @param ppbo
	 * @return result
	 * @throws TransactionException 
	 */
	public void check(ProductPublishBalanceOperate ppbo) throws TransactionException {
		
		//审核通过更新操作数据
		if(ProductPublishBalanceOperateStatus.CHECKED.equals(ppbo.getStatus())){
			//结算
			BankFinancialProductPublish bfpp = this.bankFinancialProductPublishDAO.get(ppbo.getBankFinancialProductPublish());
			if(bfpp != null){
				if(!BankFinancialProductPublishStage.BALANCE.equals(bfpp.getStage())){
					throw new TransactionException("募集产品当前无法结算！");
				}
				Bank bank = this.bankDAO.get(bfpp.getCustodian());
				
				PlatformAccount pai =this.platformAccountDAO.get(PlatformAccountUuid.INVESTOR);
				
				//实际返还收益率
				BigDecimal realReturnRate = BigDecimal.valueOf(Double.valueOf(ppbo.getValue())).divide(BigDecimal.valueOf(100));
				//总返还金额
				BigDecimal totalAmount = BigDecimal.ZERO;
				//实际返还收益
				BigDecimal totalRealReturn = BigDecimal.ZERO;
				
				Map<String, String> searchMap = new HashMap<String, String>();
				searchMap.put("product", bfpp.getUuid());
				List<Entity> dataList = this.investorProductBuyDAO.getList(searchMap, null, InvestorProductBuy.class);
				List<Object[]> paras = new ArrayList<Object[]>();
				List<Object[]> codeList = new ArrayList<Object[]>();
				List<Object[]> infoList = new ArrayList<Object[]>();
				List<MobileCode> listCode = new ArrayList<MobileCode>();
				List<Object[]> listCouponHistory = new ArrayList<Object[]>();
				for(Entity e : dataList){
					InvestorProductBuy ipb = (InvestorProductBuy) e;
					Investor i = this.investorDAO.get(ipb.getInvestor());
					
					if(i == null){
						throw new TransactionException(ipb.getInvestor() + "用户不存在！");
					}
					
					BigDecimal termRate = BigDecimal.valueOf(Double.valueOf(bfpp.getTerm())).divide(BigDecimal.valueOf(Double.valueOf(365)),100,BigDecimal.ROUND_FLOOR);
					BigDecimal accountBalance = ipb.getAccountBalance();
					//优惠券返还收益
					BigDecimal totalCouponReturn = BigDecimal.ZERO;
					
					//判断是否使用过优惠券，如果使用过 单独计算使用优惠券的那笔交易额 和优惠券收益
					searchMap.clear();
					searchMap.put("investorProductBuy", ipb.getUuid());
					List<Entity> listCoupon = this.investorCouponHistoryDAO.getListForPage(searchMap, -1, -1, null, InvestorCouponHistoryVO.class);
					if(listCoupon != null && !listCoupon.isEmpty()){
						BigDecimal price = BigDecimal.ZERO;
						
						for(Entity entity : listCoupon){
							InvestorCouponHistoryVO ichvo = (InvestorCouponHistoryVO)entity;
							BigDecimal couponReturn = BigDecimal.ZERO;
							if(CouponType.CASH.equals(ichvo.getCouponType())){//现金增值券
								price = price.add(ichvo.getPrice());
								//计算收益
								couponReturn = ichvo.getPrice().add(ichvo.getCouponPrice()).multiply(realReturnRate).multiply(termRate);
								
							} else if (CouponType.INTERESTS.equals(ichvo.getCouponType())){//加息券
								BigDecimal ouponTermRate = ichvo.getCouponPrice().divide(BigDecimal.valueOf(100));
								couponReturn = ichvo.getPrice().multiply(ouponTermRate).multiply(termRate);
							} else {
								continue;
							}
							Object[] obj = {couponReturn, ichvo.getUuid()};
							totalCouponReturn = totalCouponReturn.add(couponReturn);
							listCouponHistory.add(obj);
						}
						accountBalance = ipb.getAccountBalance().subtract(price);
//						totalAmount = totalAmount.add(price);
					}
					
					
					BigDecimal realReturn = accountBalance.multiply(realReturnRate).multiply(termRate);
					//计算收益 计入优惠券收益
					realReturn = realReturn.add(totalCouponReturn).setScale(2, BigDecimal.ROUND_FLOOR);
					
					totalAmount = totalAmount.add(ipb.getAccountBalance());
					totalRealReturn = totalRealReturn.add(realReturn);
					
					//用户资金流水
					InvestorAccountHistory iah = new InvestorAccountHistory();
					iah.setUuid(UUID.randomUUID().toString());
					iah.setInvestor(ipb.getInvestor());
					iah.setProduct(bfpp.getUuid());
					iah.setProductType(InvestorProductBuyProductType.BANK_PRODUCT);
					iah.setType(InvestorAccountHistoryType.RETURN);
					iah.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_COMPANY);
					iah.setOrderId(iah.getUuid());
					iah.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_COMPANY,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_RETURN));
					iah.setAccountBalance(i.getAccountBalance().add(ipb.getAccountBalance()));
					iah.setIncome(ipb.getAccountBalance());
					iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
					iah.setPoundage(BigDecimal.ZERO);
					iah.setPay(BigDecimal.ZERO);
//					this.investorAccountHistoryDAO.insert(iah);
					Object[] obj1 = ObjectUtlity.getFiledValues(iah);
					
					InvestorAccountHistory iahr = new InvestorAccountHistory();
					iahr.setUuid(UUID.randomUUID().toString());
					iahr.setInvestor(ipb.getInvestor());
					iahr.setProduct(bfpp.getUuid());
					iahr.setProductType(InvestorProductBuyProductType.BANK_PRODUCT);
					iahr.setType(InvestorAccountHistoryType.DIVIDEND);
					iahr.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_COMPANY);
					iahr.setOrderId(iahr.getUuid());
					iahr.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_COMPANY,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_DIVIDEND));
					iahr.setAccountBalance(i.getAccountBalance().add(ipb.getAccountBalance()).add(realReturn));
					iahr.setIncome(realReturn);
					iahr.setStatus(InvestorAccountHistoryStatus.SUCCESS);
					iahr.setCreatetime(new Timestamp(System.currentTimeMillis()));
					iahr.setPoundage(BigDecimal.ZERO);
					iahr.setPay(BigDecimal.ZERO);
//					this.investorAccountHistoryDAO.insert(iahr);
					Object[] obj2 = ObjectUtlity.getFiledValues(iahr);
					paras.add(obj1);
					paras.add(obj2);
					//用户账户
					i.setAccountBalance(i.getAccountBalance().add(ipb.getAccountBalance()).add(realReturn));
					i.setTotalInvest(i.getTotalInvest().subtract(ipb.getAccountBalance()));
					i.setTotalReturn(i.getTotalReturn().add(realReturn));
					this.investorDAO.update(i);
					
					//用户总余额变动
					pai.setTotalAmount(pai.getTotalAmount().add(ipb.getAccountBalance()).add(realReturn));
					pai.setTotalRedeem(pai.getTotalRedeem().add(ipb.getAccountBalance()));
					pai.setTotalReturn(pai.getTotalReturn().add(realReturn));
					
					//用户投资记录
					ipb.setTotalRedeem(ipb.getTotalRedeem().add(ipb.getAccountBalance()));
					ipb.setAccountBalance(BigDecimal.ZERO);
					ipb.setTotalReturn(ipb.getTotalReturn().add(realReturn));
					ipb.setStage(InvestorProductBuyStage.FINISHED);
					this.investorProductBuyDAO.update(ipb);
					
					//发送提示短信并生成应用内消息（注意批量操作）
					MobileCode mc = new MobileCode();
					String content = "";
					if(bank != null){
						content = "尊敬的用户您好：您购买的"+bank.getShortName()+bfpp.getName()
								+"理财产品本金收益已兑付，请登录牛投理财查询详情。";
					} else {
						content = "尊敬的用户您好：您购买的"+bfpp.getName()
								+"理财产品本金收益已兑付，请登录牛投理财查询详情。";
					}
					String mobile = i.getMobile();
					String codeInfo = Utlity.getCaptcha();
					mc.setCode(codeInfo);
					mc.setContent(content);
					mc.setMobile(mobile);
					mc.setCreator(i.getUuid());
					mc.setStatus(MobileCodeStatus.DISABLE);
					mc.setType(MobileCodeTypes.NOTICE);
					mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
					mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
					mc.setUuid(UUID.randomUUID().toString());
//					this.insertSmsInfo(mc);
					Object[] obj3 = ObjectUtlity.getFiledValues(mc);
					codeList.add(obj3);
					listCode.add(mc);
					
//					content = "【牛投理财】"+content;
					InvestorInformation ii = new InvestorInformation();
					ii.setCreator(i.getUuid());
					ii.setStatus(InvestorInformationStatus.UNREAD);
					ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
					ii.setUuid(UUID.randomUUID().toString());
					if(bank != null){
						content = "尊敬的用户您好：您购买的"+bank.getShortName()+bfpp.getName()
								+"理财产品本金收益已兑付，请注意查询。";
					} else {
						content = "尊敬的用户您好：您购买的"+bfpp.getName()
								+"理财产品本金收益已兑付，请注意查询。";
					}
					ii.setInfoText(content);
					ii.setInfoTitle(InvestorInformationTitle.REDEEM);
					ii.setInvestor(i.getUuid());
					Object[] obj4 = ObjectUtlity.getFiledValues(ii);
					infoList.add(obj4);
				}
				this.investorAccountHistoryDAO.insert(paras);
				this.platformAccountDAO.update(pai);
				
				List<Entity> qcbDataList = this.qcbEmployeeProductBuyDAO.getList(searchMap, null, QcbEmployeeProductBuy.class);
				List<Object[]> qcbParas = new ArrayList<Object[]>();
				List<Object[]> listQcbCouponHistory = new ArrayList<Object[]>();
				for(Entity e : qcbDataList){
					QcbEmployeeProductBuy qepb = (QcbEmployeeProductBuy) e;
					QcbEmployee qe = this.qcbEmployeeDAO.get(qepb.getQcbEmployee());
					
					if(qe == null){
						throw new TransactionException(qepb.getQcbEmployee() + "用户不存在！");
					}
					
					BigDecimal termRate = BigDecimal.valueOf(Double.valueOf(bfpp.getTerm())).divide(BigDecimal.valueOf(Double.valueOf(365)),100,BigDecimal.ROUND_FLOOR);
					BigDecimal accountBalance = qepb.getAccountBalance();
					//优惠券返还收益
					BigDecimal totalCouponReturn = BigDecimal.ZERO;
					
					//判断是否使用过优惠券，如果使用过 单独计算使用优惠券的那笔交易额 和优惠券收益
					searchMap.clear();
					searchMap.put("qcbEmployeeProductBuy", qepb.getUuid());
					List<Entity> listQcbCoupon = this.qcbEmployeeCouponHistoryDAO.getListForPage(searchMap, -1, -1, null, QcbEmployeeCouponHistoryVO.class);
					if(listQcbCoupon != null && !listQcbCoupon.isEmpty()){
						BigDecimal price = BigDecimal.ZERO;
						
						for(Entity entity : listQcbCoupon){
							QcbEmployeeCouponHistoryVO qechvo = (QcbEmployeeCouponHistoryVO)entity;
							BigDecimal couponReturn = BigDecimal.ZERO;
							if(CouponType.CASH.equals(qechvo.getCouponType())){//现金增值券
								price = price.add(qechvo.getPrice());
								//计算收益
								couponReturn = qechvo.getPrice().add(qechvo.getCouponPrice()).multiply(realReturnRate).multiply(termRate);
								
							} else if (CouponType.INTERESTS.equals(qechvo.getCouponType())){//加息券
								BigDecimal ouponTermRate = qechvo.getCouponPrice().divide(BigDecimal.valueOf(100));
								couponReturn = qechvo.getPrice().multiply(ouponTermRate).multiply(termRate);
							} else {
								continue;
							}
							Object[] obj = {couponReturn, qechvo.getUuid()};
							totalCouponReturn = totalCouponReturn.add(couponReturn);
							listQcbCouponHistory.add(obj);
						}
						accountBalance = qepb.getAccountBalance().subtract(price);
					}
					
					
					BigDecimal realReturn = accountBalance.multiply(realReturnRate).multiply(termRate);
					//计算收益 计入优惠券收益
					realReturn = realReturn.add(totalCouponReturn).setScale(2, BigDecimal.ROUND_FLOOR);
					
					totalAmount = totalAmount.add(qepb.getAccountBalance());
					totalRealReturn = totalRealReturn.add(realReturn);
					
					//用户资金流水
					QcbEmployeeHistory qeh = new QcbEmployeeHistory();
					qeh.setUuid(UUID.randomUUID().toString());
					qeh.setQcbEmployee(qepb.getQcbEmployee());
					qeh.setProduct(bfpp.getUuid());
					qeh.setProductType(QcbEmployeeProductBuyProductType.BANK_PRODUCT);
					qeh.setType(QcbEmployeeHistoryType.RETURN);
					qeh.setOrderType(QcbEmployeeHistoryOrderType.PAY_TYPE_COMPANY);
					qeh.setOrderId(qeh.getUuid());
					qeh.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_COMPANY,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_RETURN));
					qeh.setAccountBalance(qe.getAccountBalance().add(qepb.getAccountBalance()));
					qeh.setIncome(qepb.getAccountBalance());
					qeh.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
					qeh.setCreatetime(new Timestamp(System.currentTimeMillis()));
					qeh.setPoundage(BigDecimal.ZERO);
					qeh.setPay(BigDecimal.ZERO);
					Object[] obj1 = ObjectUtlity.getFiledValues(qeh);
					
					QcbEmployeeHistory qehr = new QcbEmployeeHistory();
					qehr.setUuid(UUID.randomUUID().toString());
					qehr.setQcbEmployee(qepb.getQcbEmployee());
					qehr.setProduct(bfpp.getUuid());
					qehr.setProductType(QcbEmployeeProductBuyProductType.BANK_PRODUCT);
					qehr.setType(QcbEmployeeHistoryType.DIVIDEND);
					qehr.setOrderType(QcbEmployeeHistoryOrderType.PAY_TYPE_COMPANY);
					qehr.setOrderId(qehr.getUuid());
					qehr.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_COMPANY,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_DIVIDEND));
					qehr.setAccountBalance(qe.getAccountBalance().add(qepb.getAccountBalance()).add(realReturn));
					qehr.setIncome(realReturn);
					qehr.setStatus(QcbEmployeeHistoryStatus.SUCCESS);
					qehr.setCreatetime(new Timestamp(System.currentTimeMillis()));
					qehr.setPoundage(BigDecimal.ZERO);
					qehr.setPay(BigDecimal.ZERO);
					Object[] obj2 = ObjectUtlity.getFiledValues(qehr);
					qcbParas.add(obj1);
					qcbParas.add(obj2);
					//用户账户
					qe.setAccountBalance(qe.getAccountBalance().add(qepb.getAccountBalance()).add(realReturn));
					qe.setTotalInvest(qe.getTotalInvest().subtract(qepb.getAccountBalance()));
					qe.setTotalReturn(qe.getTotalReturn().add(realReturn));
					this.qcbEmployeeDAO.update(qe);
					
					//用户投资记录
					qepb.setTotalRedeem(qepb.getTotalRedeem().add(qepb.getAccountBalance()));
					qepb.setAccountBalance(BigDecimal.ZERO);
					qepb.setTotalReturn(qepb.getTotalReturn().add(realReturn));
					qepb.setStage(QcbEmployeeProductBuyStage.FINISHED);
					this.qcbEmployeeProductBuyDAO.update(qepb);
					
					//发送提示短信并生成应用内消息（注意批量操作）
					MobileCode mc = new MobileCode();
					String content = "";
					if(bank != null){
						content = "尊敬的用户您好：您购买的"+bank.getShortName()+bfpp.getName()
								+"理财产品本金收益已兑付，请登录企财宝微信端查询详情。";
					} else {
						content = "尊敬的用户您好：您购买的"+bfpp.getName()
								+"理财产品本金收益已兑付，请登录企财宝微信端查询详情。";
					}
					String mobile = qe.getMobile();
					String codeInfo = Utlity.getCaptcha();
					mc.setCode(codeInfo);
					mc.setContent(content);
					mc.setMobile(mobile);
					mc.setCreator(qe.getUuid());
					mc.setStatus(MobileCodeStatus.DISABLE);
					mc.setType(MobileCodeTypes.NOTICE);
					mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
					mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
					mc.setUuid(UUID.randomUUID().toString());
//					this.insertSmsInfo(mc);
					Object[] obj3 = ObjectUtlity.getFiledValues(mc);
					codeList.add(obj3);
					listCode.add(mc);
					
//					content = "【牛投理财】"+content;
//					QcbEmployeeInformation ii = new QcbEmployeeInformation();
//					ii.setCreator(i.getUuid());
//					ii.setStatus(QcbEmployeeInformationStatus.UNREAD);
//					ii.setCreatetime(new Timestamp(System.currentTimeMillis()));
//					ii.setUuid(UUID.randomUUID().toString());
//					if(bank != null){
//						content = "尊敬的用户您好：您购买的"+bank.getShortName()+bfpp.getName()
//								+"理财产品本金收益已兑付，请注意查询。";
//					} else {
//						content = "尊敬的用户您好：您购买的"+bfpp.getName()
//								+"理财产品本金收益已兑付，请注意查询。";
//					}
//					ii.setInfoText(content);
//					ii.setInfoTitle(QcbEmployeeInformationTitle.REDEEM);
//					ii.setQcbEmployee(i.getUuid());
//					Object[] obj4 = ObjectUtlity.getFiledValues(ii);
//					infoList.add(obj4);
				}
				this.qcbEmployeeHistoryDAO.insert(qcbParas);
				
				//募集产品结算
				bfpp.setAccountBalance(bfpp.getAccountBalance().subtract(totalAmount).subtract(totalRealReturn));
				bfpp.setRealReturnRate(realReturnRate.multiply(BigDecimal.valueOf(Double.valueOf(100))));
				bfpp.setRealReturn(totalRealReturn);
				bfpp.setFlagBuy(false);
				
				PlatformAccount pab =this.platformAccountDAO.get(PlatformAccountUuid.BALANCE);
				pab.setTotalAmount(pab.getTotalAmount().add(bfpp.getAccountBalance()));
				this.platformAccountDAO.update(pab);
				
				bfpp.setStage(BankFinancialProductPublishStage.FINISHED);
				this.bankFinancialProductPublishDAO.update(bfpp);
				this.investorInformationDAO.insert(infoList);
				this.investorCouponHistoryDAO.update(listCouponHistory);
				this.qcbEmployeeCouponHistoryDAO.update(listQcbCouponHistory);
				boolean flag = true;
				for(MobileCode mc : listCode){//发送短信
					String phone = mc.getMobile();
					String content = mc.getContent();
					String result = SendSms.sendSms(content, phone);
					if (result != null && !"0".equals(result.split("_")[0])) {
						flag = false;
					}
				}
				if(flag){
					this.mobileCodeDAO.insert(codeList);
				}
			}else{
				throw new TransactionException("募集产品信息不存在");
			}
		}
		if(ProductPublishBalanceOperateStatus.CHECKED.equals(ppbo.getStatus()) && (ppbo.getReason() == null || "".equals(ppbo.getReason()))){
			ppbo.setReason("审核通过！");
		}else if(ProductPublishBalanceOperateStatus.UNPASSED.equals(ppbo.getStatus()) && (ppbo.getReason() == null || "".equals(ppbo.getReason()))){
			ppbo.setReason("审核不通过！");
		}
		productPublishBalanceOperateDAO.update(ppbo);
	}
	
	/**
	 * 获取募集产品操作分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Map<String, String> inputParams, Class<? extends Entity> resultClass) {
		return productPublishBalanceOperateDAO.getStatusList(inputParams, resultClass);
	}
}
