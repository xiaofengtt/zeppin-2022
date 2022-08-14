/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IAliCertificationDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICouponDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.ICouponStrategyDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IFundPublishDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorAccountHistoryDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorBankcardDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorCouponDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorIdcardImgDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorInformationDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IInvestorRedPacketDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IOrderinfoByThirdpartyDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IInvestorService;
import cn.zeppin.product.ntb.backadmin.vo.CouponLessVO;
import cn.zeppin.product.ntb.core.entity.AliCertification;
import cn.zeppin.product.ntb.core.entity.BankFinancialProduct.BankFinancialProductStatus;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.CouponStrategy.CouponStrategyStatus;
import cn.zeppin.product.ntb.core.entity.CouponStrategy.CouponStrategyUuid;
import cn.zeppin.product.ntb.core.entity.FundPublish;
import cn.zeppin.product.ntb.core.entity.FundPublish.FundPublishUuid;
import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.Investor.InvestorRegistSource;
import cn.zeppin.product.ntb.core.entity.Investor.InvestorStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard;
import cn.zeppin.product.ntb.core.entity.InvestorBankcard.InvestorBankcardStatus;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon;
import cn.zeppin.product.ntb.core.entity.InvestorCoupon.InvestorCouponStatus;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg;
import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg.InvestorIdcardImgStatus;
import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationModel;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationStatus;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationTitle;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket.InvestorRedPacketStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty;
import cn.zeppin.product.ntb.core.entity.OrderinfoByThirdparty.OrderinfoByThirdpartyType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.AliUtlity;
import cn.zeppin.product.utility.DataTimeConvert;
import cn.zeppin.product.utility.IDCardUtil;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.JuHeUtility;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.SendSms;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.chanpay.ChanPayUtil;

/**
 * @author hehe
 *
 */
@Service
public class InvestorService extends BaseService implements IInvestorService {

	@Autowired
	private IInvestorDAO investorDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Autowired
	private IInvestorIdcardImgDAO investorIdcardImgDAO;
	
	@Autowired
	private IAliCertificationDAO aliCertificationDao;
	
	@Autowired
	private IOrderinfoByThirdpartyDAO orderinfoByThirdpartyDAO;
	
	@Autowired
	private IInvestorBankcardDAO investorBankcardDAO;
	
	@Autowired
	private IInvestorAccountHistoryDAO investorAccountHistoryDAO;
	
	@Autowired
	private IInvestorInformationDAO investorInformationDAO;
	
	@Autowired
	private IInvestorCouponDAO investorCouponDAO;
	
	@Autowired
	private ICouponDAO couponDAO;
	
	@Autowired
	private ICouponStrategyDAO couponStrategyDAO;
	
	@Autowired
	private IInvestorRedPacketDAO investorRedPacketDAO;
	
	@Autowired
	private IFundPublishDAO fundPublishDAO;
	
	@Override
	public Investor insert(Investor investor) {
		return investorDAO.insert(investor);
	}

	@Override
	public Investor delete(Investor investor) {
		investor.setStatus(BankFinancialProductStatus.DELETED);
		return investorDAO.update(investor);
	}

	@Override
	public Investor update(Investor investor) {
		return investorDAO.update(investor);
	}

	@Override
	public Investor get(String uuid) {
		return investorDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询Bank结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return investorDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return investorDAO.getCount(inputParams);
	}
	
	/**
	 * 获取银行理财产品分状态列表
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getStatusList(Class<? extends Entity> resultClass) {
		return investorDAO.getStatusList(resultClass);
	}

	@Override
	public Investor getByOpenID(String openID, Class<? extends Entity> resultClass) {
		return investorDAO.getByOpenID(openID, resultClass);
	}

	@Override
	public Investor getByMobile(String mobile, Class<? extends Entity> resultClass) {
		return investorDAO.getByMobile(mobile, resultClass);
	}

	@Override
	public boolean isExistInvestorByMobile(String mobile, String uuid) {
		return investorDAO.isExistInvestorByMobile(mobile, uuid);
	}
	
	public HashMap<String, Object> register(String token,String phone, Map<String, Object> other) throws TransactionException{
		HashMap<String,Object> result = new HashMap<String,Object>();
		String message = "注册失败！";
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			message = "注册失败，未识别设备号";
			throw new TransactionException(message);
		}
		
		if (!Utlity.isMobileNO(phone)) {
			message = "手机号非法！";
			throw new TransactionException(message);
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", phone);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			message = "验证码输入错误！";
			throw new TransactionException(message);
		}
		
		if(!mc.getMobile().equals(phone)){
			message = "手机号输入错误！";
			throw new TransactionException(message);
		}
		
		if(!MobileCodeTypes.REGISTER.equals(mc.getType())){
			message = "验证码输入错误！";
			throw new TransactionException(message);
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			message = "验证码超时！";
			throw new TransactionException(message);
		}
		
		
		if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){//微信小程序
			
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkTime(time, nowTime)){
					message = "注册失败，注册时间超时";
					throw new TransactionException(message);
				}
			} else {
				message = "注册失败，注册时间超时";
				throw new TransactionException(message);
			}
			
			String openID = token.substring(15, 43);
			if(openID == null || "".equals(openID)){
				throw new TransactionException(message);
			}
			
			if(this.investorDAO.isExistInvestorByMobile(phone, null)){
				message = "用户已注册！";
				throw new TransactionException(message);
			}
			
			String pwd = token.substring(43, 75);
			if(pwd == null || "".equals(pwd)){
				message = "注册失败，密码输入错误";
				throw new TransactionException(message);
			}
			
			String md5Str = token.substring(75);
			if(md5Str == null || "".equals(md5Str)){
				throw new TransactionException(message);
			}
			String realMD5Str = MD5.getMD5(Utlity.KEY+time+mc.getMobile()+mc.getCode());
			if(realMD5Str.equals(md5Str)){//成功
				Investor in = this.investorDAO.getByOpenID(openID, Investor.class);
				if(in != null){
					in = this.investorDAO.get(in.getUuid());
					in.setOpenid(null);
					this.investorDAO.update(in);
				}
				
				Investor investor = new Investor();
				investor.setUuid(UUID.randomUUID().toString());
				investor.setMobile(phone);
				investor.setLoginPassword(pwd);
				investor.setCreatetime(new Timestamp(System.currentTimeMillis()));
				investor.setStatus(InvestorStatus.NORMAL);
				investor.setNickname("游客："+phone);
				investor.setOpenid(openID);
				investor.setBindingMobileFlag(true);
				investor.setBindingEmailFlag(false);
				investor.setRealnameAuthFlag(false);
				investor.setSecretPasswordFlag(false);
				investor.setTotalInvest(BigDecimal.valueOf(0));
				investor.setTotalReturn(BigDecimal.valueOf(0));
				investor.setAccountBalance(BigDecimal.valueOf(0));
				investor.setRegistSource(InvestorRegistSource.WEIXIN);
				
				this.investorDAO.insert(investor);
				mc.setStatus(MobileCodeStatus.DISABLE);
				this.mobileCodeDAO.update(mc);
				
				InvestorInformation iii = new InvestorInformation();
				iii.setCreator(investor.getUuid());
				iii.setStatus(InvestorInformationStatus.UNREAD);
				iii.setCreatetime(new Timestamp(System.currentTimeMillis()));
				iii.setUuid(UUID.randomUUID().toString());
				iii.setInfoText(InvestorInformationModel.INFO_MODEL_REGISTER);
				iii.setInfoTitle(InvestorInformationTitle.REGISTER);
				iii.setInvestor(investor.getUuid());
				this.investorInformationDAO.insert(iii);
				message = "注册成功!";
				result.put("result", false);
				result.put("investor", investor.getUuid());
				result.put("message", message);
				this.checkCouponStrategy(investor,InvestorCouponStatus.NOTACTIVE);
//				//注册成功检查此处优惠券策略是否生效，将优惠券添加到用户账户中 同步生成应用内消息
//				CouponStrategy cs = this.couponStrategyDAO.get(CouponStrategyUuid.REGISTERED);
//				if(cs != null && CouponStrategyStatus.OPEN.equals(cs.getStatus())){
//					Timestamp now = new Timestamp(System.currentTimeMillis());
//					Map<String, Object> coupon = JSONUtils.json2map(cs.getCoupon());
//					List<CouponLessVO> list = (List<CouponLessVO>) coupon.get("couponList");
//					for(CouponLessVO vo : list){
//						InvestorCoupon ic = new InvestorCoupon();
//						ic.setUuid(UUID.randomUUID().toString());
//						ic.setCoupon(vo.getUuid());
//						ic.setCreator(investor.getUuid());
//						ic.setCreatetime(now);
//						ic.setStatus(InvestorCouponStatus.UNUSE);
//						//计算优惠券截止日期
//						if(vo.getExpiryDate() != null){//有截止日期
//							if((now.getTime()-vo.getExpiryDate().getTime()) > 0){//超出截止日期
//								//优惠券失效 不发放给用户
//								continue;
//							}
//							//未超出截止日期，判断是否有使用期限限制
//							if(vo.getDeadline() > 0){
//								long addTime = 1000*60*60*24*vo.getDeadline();
//								if((now.getTime()+addTime-vo.getExpiryDate().getTime()) > 0){
//									ic.setExpiryDate(vo.getExpiryDate());
//								} else {
//									Timestamp exp = DataTimeConvert.stringToTimeStamp(Utlity.timeSpanToDateString(now)+" 00:00:00");
//									ic.setExpiryDate(new Timestamp(exp.getTime()+addTime));
//								}
//							} else {
//								ic.setExpiryDate(vo.getExpiryDate());
//							}
//						} else {
//							//没有截止日期 按照期限计算
//							long addTime = 1000*60*60*24*vo.getDeadline();
//							Timestamp exp = DataTimeConvert.stringToTimeStamp(Utlity.timeSpanToDateString(now)+" 00:00:00");
//							ic.setExpiryDate(new Timestamp(exp.getTime()+addTime));
//						}
//						ic.setInvestor(investor.getUuid());
//						this.investorCouponDAO.insert(ic);
//						
//						InvestorInformation iiii = new InvestorInformation();
//						String price = "";
//						if(CouponType.CASH.equals(vo.getCouponType())){
//							price =Utlity.numFormat4UnitDetailLess(vo.getCouponPrice())+"元的现金增值券";
//						} else if (CouponType.INTERESTS.equals(vo.getCouponType())) {
//							price =Utlity.numFormat4UnitDetailLess(vo.getCouponPrice())+"%的现金加息券";
//						}
//						String content = "尊敬的用户您好：您于"+Utlity.timeSpanToDateString(now)+"获得1张"+price+"，您可在我的优惠券内查询。";
//						iiii.setCreator(investor.getUuid());
//						iiii.setStatus(InvestorInformationStatus.UNREAD);
//						iiii.setCreatetime(new Timestamp(System.currentTimeMillis()));
//						iiii.setUuid(UUID.randomUUID().toString());
//						iiii.setInfoText(content);
//						iiii.setInfoTitle(InvestorInformationTitle.COUPONADD);
//						iiii.setInvestor(investor.getUuid());
//						this.investorInformationDAO.insert(iiii);
//					}
//				}
				return result;
			} else {
				message = "注册失败，验证码输入错误！";
				throw new TransactionException(message);
			}
			
		} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkTime(time, nowTime)){
					message = "注册失败，注册时间超时";
					throw new TransactionException(message);
				}
			} else {
				message = "注册失败，注册时间超时";
				throw new TransactionException(message);
			}
			
			if(this.investorDAO.isExistInvestorByMobile(phone, null)){
				message = "用户已注册！";
				throw new TransactionException(message);
			}
			
			String pwd = token.substring(15, 47);
			if(pwd == null || "".equals(pwd)){
				message = "注册失败，密码输入错误";
				throw new TransactionException(message);
			}
			
			String md5Str = token.substring(47);
			if(md5Str == null || "".equals(md5Str)){
				throw new TransactionException(message);
			}
			String realMD5Str = MD5.getMD5(Utlity.KEY+time+mc.getMobile()+mc.getCode());
			if(realMD5Str.equals(md5Str)){//成功
				
				Investor investor = new Investor();
				investor.setUuid(UUID.randomUUID().toString());
				investor.setMobile(phone);
				investor.setLoginPassword(pwd);
				investor.setCreatetime(new Timestamp(System.currentTimeMillis()));
				investor.setStatus(InvestorStatus.NORMAL);
				investor.setNickname("游客："+phone);
				investor.setOpenid("");
				investor.setBindingMobileFlag(true);
				investor.setBindingEmailFlag(false);
				investor.setRealnameAuthFlag(false);
				investor.setSecretPasswordFlag(false);
				investor.setTotalInvest(BigDecimal.valueOf(0));
				investor.setTotalReturn(BigDecimal.valueOf(0));
				investor.setAccountBalance(BigDecimal.valueOf(0));
				if(Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber)){
					investor.setRegistSource(InvestorRegistSource.ANDROID);
				}else{
					investor.setRegistSource(InvestorRegistSource.IOS);
				}
				
				
				this.investorDAO.insert(investor);
				mc.setStatus(MobileCodeStatus.DISABLE);
				this.mobileCodeDAO.update(mc);
				InvestorInformation iii = new InvestorInformation();
				iii.setCreator(investor.getUuid());
				iii.setStatus(InvestorInformationStatus.UNREAD);
				iii.setCreatetime(new Timestamp(System.currentTimeMillis()));
				iii.setUuid(UUID.randomUUID().toString());
				iii.setInfoText(InvestorInformationModel.INFO_MODEL_REGISTER);
				iii.setInfoTitle(InvestorInformationTitle.REGISTER);
				iii.setInvestor(investor.getUuid());
				this.investorInformationDAO.insert(iii);
				message = "注册成功!";
				result.put("result", true);
				result.put("investor", investor.getUuid());
				result.put("message", message);
				this.checkCouponStrategy(investor,InvestorCouponStatus.NOTACTIVE);
				return result;
			} else {
				message = "注册失败，验证码输入错误！";
				throw new TransactionException(message);
			}
		} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
			String price = "";
			if(other.containsKey("price")){
				price = other.get("price").toString();
			}
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkTime(time, nowTime)){
					message = "注册失败，注册时间超时";
					throw new TransactionException(message);
				}
			} else {
				message = "注册失败，注册时间超时";
				throw new TransactionException(message);
			}
			
			if(this.investorDAO.isExistInvestorByMobile(phone, null)){
				message = "用户已注册！";
				throw new TransactionException(message);
			}
			
			String md5Str = token.substring(15);
			if(md5Str == null || "".equals(md5Str)){
				throw new TransactionException(message);
			}
			String realMD5Str = MD5.getMD5(MD5.getMD5(Utlity.DEVICE_NUMBER_WEB+Utlity.KEY)+time+mc.getMobile()+mc.getCode()+price);
			if(realMD5Str.equals(md5Str)){//成功
				
				Investor investor = new Investor();
				investor.setUuid(UUID.randomUUID().toString());
				investor.setMobile(phone);
				investor.setLoginPassword("");
				investor.setCreatetime(new Timestamp(System.currentTimeMillis()));
				investor.setStatus(InvestorStatus.NORMAL);
				investor.setNickname("用户："+phone);
				investor.setOpenid("");
				investor.setBindingMobileFlag(true);
				investor.setBindingEmailFlag(false);
				investor.setRealnameAuthFlag(false);
				investor.setSecretPasswordFlag(false);
				investor.setTotalInvest(BigDecimal.valueOf(0));
				investor.setTotalReturn(BigDecimal.valueOf(0));
				investor.setAccountBalance(BigDecimal.valueOf(0));
				investor.setRegistSource(InvestorRegistSource.WEB);
				
				this.investorDAO.insert(investor);
				
				//判断是否有获得现金红包
				if(!Utlity.checkStringNull(price)){
					BigDecimal cash = BigDecimal.valueOf(Double.valueOf(price)).setScale(2,BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100));
					if(cash.compareTo(Utlity.MAX_RED_PACKET) > 0){
						message = "红包服务器异常！";
						throw new TransactionException(message);
					}
					InvestorRedPacket irp = new InvestorRedPacket();
					irp.setUuid(UUID.randomUUID().toString());
					irp.setPirce(cash);
					irp.setCreator(investor.getUuid());
					irp.setCreatetime(new Timestamp(System.currentTimeMillis()));
					irp.setStatus(InvestorRedPacketStatus.NOTACTIVE);
					irp.setExpiryDate(null);
					irp.setInvestor(investor.getUuid());
					this.investorRedPacketDAO.insert(irp);
				}
				
				mc.setStatus(MobileCodeStatus.DISABLE);
				this.mobileCodeDAO.update(mc);
				InvestorInformation iii = new InvestorInformation();
				iii.setCreator(investor.getUuid());
				iii.setStatus(InvestorInformationStatus.UNREAD);
				iii.setCreatetime(new Timestamp(System.currentTimeMillis()));
				iii.setUuid(UUID.randomUUID().toString());
				iii.setInfoText(InvestorInformationModel.INFO_MODEL_REGISTER);
				iii.setInfoTitle(InvestorInformationTitle.REGISTER);
				iii.setInvestor(investor.getUuid());
				this.investorInformationDAO.insert(iii);
				message = "注册成功!";
				result.put("result", true);
				result.put("investor", investor.getUuid());
				result.put("message", message);
				this.checkCouponStrategy(investor,InvestorCouponStatus.NOTACTIVE);
				return result;
			} else {
				message = "注册失败，验证码输入错误！";
				throw new TransactionException(message);
			}
		}
		
		result.put("result", true);
		result.put("message", message);
		return result;

	}

	@Override
	public HashMap<String, Object> login(String token) throws TransactionException {
		HashMap<String,Object> result = new HashMap<String,Object>();
		String message = "用户名密码不正确";
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			message = "登录失败，未识别的设备号";
			throw new TransactionException(message);
		}
		
		if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){//微信小程序
			
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					throw new TransactionException(message);
				}
			} else {
				throw new TransactionException(message);
			}
			String openID = token.substring(15, 43);
			if(openID == null || "".equals(openID)){
				throw new TransactionException(message);
			}
			
			String username = token.substring(43,54);//11位手机号
			if(username == null || "".equals(username)){
				throw new TransactionException(message);
			}
			Investor invertor = this.investorDAO.getByMobile(username, Investor.class);
			String pwd = "";
			if(invertor != null){
				pwd = invertor.getLoginPassword();
			}else{
				message = "用户未注册";
				throw new TransactionException(message);
			}
			String md5Str = token.substring(54);
			if(md5Str == null || "".equals(md5Str)){
				throw new TransactionException(message);
			}
			
			String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+pwd);
			if(md5Str.equals(realMD5Str)){//登录成功
				Investor in = this.investorDAO.getByOpenID(openID, Investor.class);
				if(in != null && !in.getUuid().equals(invertor.getUuid())){
					in = this.investorDAO.get(in.getUuid());
					in.setOpenid(null);
					this.investorDAO.update(in);
				}
				
				invertor = this.investorDAO.get(invertor.getUuid());
				invertor.setOpenid(openID);
				invertor.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
				this.investorDAO.update(invertor);
				message = "登录成功";
				result.put("result", true);
				result.put("message", message);
				result.put("uuid", invertor.getUuid());
				return result;
			} else {
				throw new TransactionException(message);
			}
			
		} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
			
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					throw new TransactionException(message);
				}
			} else {
				throw new TransactionException(message);
			}
			
			String username = token.substring(15,26);//11位手机号
			if(username == null || "".equals(username)){
				throw new TransactionException(message);
			}
			Investor invertor = this.investorDAO.getByMobile(username, Investor.class);
			String pwd = "";
			if(invertor != null){
				pwd = invertor.getLoginPassword();
			}else{
				message = "用户未注册";
				throw new TransactionException(message);
			}
			String md5Str = token.substring(26);
			if(md5Str == null || "".equals(md5Str)){
				throw new TransactionException(message);
			}
			
			String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+pwd);
			if(md5Str.equals(realMD5Str)){//登录成功
				invertor = this.investorDAO.get(invertor.getUuid());
				invertor.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
				this.investorDAO.update(invertor);
				message = "登录成功";
				result.put("result", true);
				result.put("message", message);
				result.put("uuid", invertor.getUuid());
				return result;
			} else {
				throw new TransactionException(message);
			}
			
		} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
			
		}
		message = "登录失败";
		throw new TransactionException(message);
	}

	@Override
	public void loginResetpwd(String token, String phone, String step) throws TransactionException {
		String message = "重置密码失败！";
		if("first".equals(step)){
			message = "验证失败！";
		}
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			message = message+"未识别设备号";
			throw new TransactionException(message);
		}
		
		if (!Utlity.isMobileNO(phone)) {
			message = "手机号非法！";
			throw new TransactionException(message);
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", phone);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			message = "验证码输入错误！";
			throw new TransactionException(message);
		}
		
		if(!mc.getMobile().equals(phone)){
			message = "手机号输入错误！";
			throw new TransactionException(message);
		}
		
		if(!MobileCodeTypes.RESETPWD.equals(mc.getType())){
			message = "验证码输入错误！";
			throw new TransactionException(message);
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			message = "验证码超时！";
			throw new TransactionException(message);
		}
		
		if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){//微信小程序
			
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkTime(time, nowTime)){
					message = message+"时间超时";
					throw new TransactionException(message);
				}
			} else {
				message = message+"时间超时";
				throw new TransactionException(message);
			}
			
			String openID = token.substring(15, 43);
			if(openID == null || "".equals(openID)){
				throw new TransactionException(message);
			}
			if("first".equals(step)){
				String md5Str = token.substring(43);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException(message);
				}
				String realMD5Str = MD5.getMD5(Utlity.KEY+time+mc.getMobile()+mc.getCode());
				if(realMD5Str.equals(md5Str)){
					Investor investor = this.investorDAO.getByMobile(phone, Investor.class);
					if(investor == null){
						message = "验证失败，用户未注册！";
						throw new TransactionException(message);
					}
					
					
				} else {
					message = message+"验证码输入错误！";
					throw new TransactionException(message);
				}
			} else if ("second".equals(step)) {
				String pwd = token.substring(43, 75);
				if(pwd == null || "".equals(pwd)){
					message = "重置密码失败，请检查新密码";
					throw new TransactionException(message);
				}
				
				String md5Str = token.substring(75);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException(message);
				}
				String realMD5Str = MD5.getMD5(Utlity.KEY+time+mc.getMobile()+mc.getCode());
				if(realMD5Str.equals(md5Str)){//成功
					Investor investor = this.investorDAO.getByMobile(phone, Investor.class);
					if(investor == null){
						message = "重置密码失败，用户未注册！";
						throw new TransactionException(message);
					}
					
					investor = this.investorDAO.get(investor.getUuid());
					
					investor.setLoginPassword(pwd);
					this.investorDAO.update(investor);
					
					mc.setStatus(MobileCodeStatus.DISABLE);
					this.mobileCodeDAO.update(mc);
					
				} else {
					message = "重置密码失败，验证码输入错误！";
					throw new TransactionException(message);
				}
				
			} else {
			}

		} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkTime(time, nowTime)){
					message = message+"时间超时";
					throw new TransactionException(message);
				}
			} else {
				message = message+"时间超时";
				throw new TransactionException(message);
			}
			if("first".equals(step)){
				String md5Str = token.substring(15);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException(message);
				}
				String realMD5Str = MD5.getMD5(Utlity.KEY+time+mc.getMobile()+mc.getCode());
				if(realMD5Str.equals(md5Str)){
					Investor investor = this.investorDAO.getByMobile(phone, Investor.class);
					if(investor == null){
						message = "验证失败，用户未注册！";
						throw new TransactionException(message);
					}
					
				} else {
					message = message+"验证码输入错误！";
					throw new TransactionException(message);
				}
			} else if ("second".equals(step)) {
				String pwd = token.substring(15, 47);
				if(pwd == null || "".equals(pwd)){
					message = "重置密码失败，请检查新密码";
					throw new TransactionException(message);
				}
				
				String md5Str = token.substring(47);
				if(md5Str == null || "".equals(md5Str)){
					throw new TransactionException(message);
				}
				String realMD5Str = MD5.getMD5(Utlity.KEY+time+mc.getMobile()+mc.getCode());
				if(realMD5Str.equals(md5Str)){//成功
					Investor investor = this.investorDAO.getByMobile(phone, Investor.class);
					if(investor == null){
						message = "重置密码失败，用户未注册！";
						throw new TransactionException(message);
					}
					
					investor = this.investorDAO.get(investor.getUuid());
					
					investor.setLoginPassword(pwd);
					this.investorDAO.update(investor);
					
					mc.setStatus(MobileCodeStatus.DISABLE);
					this.mobileCodeDAO.update(mc);
					
				} else {
					message = "重置密码失败，验证码输入错误！";
					throw new TransactionException(message);
				}
				
			} else {
				message = "操作错误";
				throw new TransactionException(message);
			}
		} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void logincertification(String idcard, String username, Investor investor, String imgface, String imgback) throws TransactionException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(!investor.getRealnameAuthFlag()){
			result = JuHeUtility.certification(idcard, username);
			if(result.get("request") != null && (Boolean)result.get("request")){
				//信息入库
				Map<String, Object> response = (Map<String, Object>) result.get("response");
				if(response != null && !response.isEmpty()){
					AliCertification ac = new AliCertification();
					ac.setUuid(UUID.randomUUID().toString());
					ac.setCreatetime(new Timestamp(System.currentTimeMillis()));
					ac.setCreator(investor.getUuid());
					
					ac.setBinNo(response.get("bizNo")==null ? "无":response.get("bizNo").toString());
					ac.setVerifyCode(response.get("verifyCode")==null ? "无":response.get("verifyCode").toString());
					ac.setProductCode(response.get("product_code")==null ? "无":response.get("product_code").toString());
					ac.setTranscationid(response.get("transAutoIncIdx")==null ? "无":response.get("transAutoIncIdx").toString());
					ac.setInscription("支付宝实名认证—欺诈信息验证");
					ac.setCode(response.get("code")==null ? "无":response.get("code").toString());
					ac.setMsg(response.get("msg")==null ? "无":response.get("msg").toString());
					ac.setSubCode(response.get("sub_code")==null ? "无":response.get("sub_code").toString());
					ac.setSubMsg(response.get("sub_msg")==null ? "无":response.get("sub_msg").toString());
					this.aliCertificationDao.insert(ac);
				}
			} else {
				throw new TransactionException(result.get("message").toString());
			}
			if((Boolean)result.get("result")){
				//信息更新+入库
				if(!Utlity.checkStringNull(imgface) && !Utlity.checkStringNull(imgback)){
					InvestorIdcardImg idimg = new InvestorIdcardImg();
					if(investor.getIdcardImg() != null){
						idimg = this.investorIdcardImgDAO.get(investor.getIdcardImg());
						if(!InvestorIdcardImgStatus.CHECKED.equals(idimg.getStatus())){
							idimg.setImgface(imgface);
							idimg.setImgback(imgback);
							idimg.setStatus(InvestorIdcardImgStatus.UNCHECKED);
							this.investorIdcardImgDAO.update(idimg);
						} else {
							throw new TransactionException("证件照片已通过审核！");
//								result.put("result", false);
//								result.put("message", "证件照片已通过审核！");
//								return result;
						}
					} else {
						idimg.setUuid(UUID.randomUUID().toString());
						idimg.setCreatetime(new Timestamp(System.currentTimeMillis()));
						idimg.setCreator(investor.getUuid());
						idimg.setStatus(InvestorIdcardImgStatus.UNCHECKED);
						idimg.setImgface(imgface);
						idimg.setImgback(imgback);
						this.investorIdcardImgDAO.insert(idimg);
						investor.setIdcardImg(idimg.getUuid());
					}
				}
				investor.setIdcard(idcard);
				investor.setRealname(username);
				investor.setRealnameAuthFlag(true);
				//增加性别
				if(IDCardUtil.getSex(idcard) == 1){
					investor.setSex(Utlity.SEX_MAN);
				} else {
					investor.setSex(Utlity.SEX_WOMAN);
				}
				this.investorDAO.update(investor);
			} else {
				throw new TransactionException(result.get("message").toString());
			}
		} else {
			result = new HashMap<String, Object>();
			String message = "";
			//信息更新+入库
			if(!Utlity.checkStringNull(imgface) && !Utlity.checkStringNull(imgback)){
				InvestorIdcardImg idimg = new InvestorIdcardImg();
				if(!Utlity.checkStringNull(investor.getIdcardImg())){
					idimg = this.investorIdcardImgDAO.get(investor.getIdcardImg());
					if(!InvestorIdcardImgStatus.CHECKED.equals(idimg.getStatus())){
						idimg.setImgface(imgface);
						idimg.setImgback(imgback);
						idimg.setStatus(InvestorIdcardImgStatus.UNCHECKED);
						this.investorIdcardImgDAO.update(idimg);
					} else {
						message = "证件照片已通过审核！";
						throw new TransactionException(message);
					}
				} else {
					idimg.setUuid(UUID.randomUUID().toString());
					idimg.setCreatetime(new Timestamp(System.currentTimeMillis()));
					idimg.setCreator(investor.getUuid());
					idimg.setStatus(InvestorIdcardImgStatus.UNCHECKED);
					idimg.setImgface(imgface);
					idimg.setImgback(imgback);
					this.investorIdcardImgDAO.insert(idimg);
					investor.setIdcardImg(idimg.getUuid());
					this.investorDAO.update(investor);
				}
			} else {
				throw new TransactionException("请补全证件照信息！");
			}
		}
	}

	@Override
	public void updateBatch(List<Investor> listUpdate) {
		try {
			for(Investor i : listUpdate){
				this.investorDAO.update(i);	
			}
		} catch (Exception e) {
			super.flushAll();
		}
	}

	@Override
	public List<Entity> getNotuploadCount(Class<? extends Entity> resultClass) {
		return this.investorDAO.getNotuploadCount(resultClass);
	}

	@Override
	public HashMap<String, Object> loginBycode(String token) throws TransactionException {
		HashMap<String,Object> result = new HashMap<String,Object>();
		String message = "手机验证码不正确";
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			message = "登录失败，未识别的设备号";
			throw new TransactionException(message);
//				result.put("result", false);
//				result.put("message", message);
//				return result;
		}
		
		if(Utlity.DEVICE_NUMBER_WECHAT.equals(deviceNumber)){//微信小程序
			
//				if(token.length() != 83){
//					return ResultManager.createFailResult("用户名密码不正确");
//				}
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					throw new TransactionException(message);
				}
			} else {
				throw new TransactionException(message);
			}
			String openID = token.substring(15, 43);
			if(openID == null || "".equals(openID)){
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			
			String username = token.substring(43,54);//11位手机号
			if(username == null || "".equals(username)){
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			Investor invertor = this.investorDAO.getByMobile(username, Investor.class);
			if(invertor == null){
				message = "用户未注册";
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("mobile", username);
			inputParams.put("status", MobileCodeStatus.NORMAL);
			List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);
			MobileCode mc = null;
			if(lstMobileCode != null && lstMobileCode.size() > 0){
				mc = (MobileCode) lstMobileCode.get(0);
			}
			if(mc == null){
				message = "验证码输入错误！";
				throw new TransactionException(message);
//						result.put("result", false);
//						result.put("message", message);
//						return result;
			}
			
			if(!mc.getMobile().equals(username)){
				message = "手机号输入错误！";
				throw new TransactionException(message);
//						result.put("result", false);
//						result.put("message", message);
//						return result;
			}
			
			if(!MobileCodeTypes.CODE.equals(mc.getType())){
				message = "验证码输入错误！";
				throw new TransactionException(message);
//						result.put("result", false);
//						result.put("message", message);
//						return result;
			}
			
			if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
				message = "验证码超时！";
				throw new TransactionException(message);
//						result.put("result", false);
//						result.put("message", message);
//						return result;
			}
			
			String md5Str = token.substring(54);
			if(md5Str == null || "".equals(md5Str)){
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			
			String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+mc.getCode());
			if(md5Str.equals(realMD5Str)){//登录成功
				Investor in = this.investorDAO.getByOpenID(openID, Investor.class);
				if(in != null && !in.getUuid().equals(invertor.getUuid())){
					in = this.investorDAO.get(in.getUuid());
					in.setOpenid(null);
					this.investorDAO.update(in);
				}
				
				invertor = this.investorDAO.get(invertor.getUuid());
				invertor.setOpenid(openID);
				invertor.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
				this.investorDAO.update(invertor);
				message = "登录成功";
				result.put("result", true);
				result.put("message", message);
				result.put("uuid", invertor.getUuid());
				return result;
			} else {
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			
		} else if (Utlity.DEVICE_NUMBER_ANDROID.equals(deviceNumber) || Utlity.DEVICE_NUMBER_IOS.equals(deviceNumber)) {
			
//				if(token.length() != 83){
//					return ResultManager.createFailResult("用户名密码不正确");
//				}
			String timestamp = token.substring(2,15);
			if(timestamp == null || "".equals(timestamp)){
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			long time = Long.parseLong(timestamp);
			long nowTime = System.currentTimeMillis();
			if(time <= nowTime){
				if(Utlity.checkLessTime(time, nowTime)){
					throw new TransactionException(message);
				}
			} else {
				throw new TransactionException(message);
			}
			String username = token.substring(15,26);//11位手机号
			if(username == null || "".equals(username)){
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			Investor invertor = this.investorDAO.getByMobile(username, Investor.class);
			if(invertor == null){
				message = "用户未注册";
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			String md5Str = token.substring(26);
			if(md5Str == null || "".equals(md5Str)){
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("mobile", username);
			inputParams.put("status", MobileCodeStatus.NORMAL);
			List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);
			MobileCode mc = null;
			if(lstMobileCode != null && lstMobileCode.size() > 0){
				mc = (MobileCode) lstMobileCode.get(0);
			}
			if(mc == null){
				message = "验证码输入错误！";
				throw new TransactionException(message);
//						result.put("result", false);
//						result.put("message", message);
//						return result;
			}
			
			if(!mc.getMobile().equals(username)){
				message = "手机号输入错误！";
				throw new TransactionException(message);
//						result.put("result", false);
//						result.put("message", message);
//						return result;
			}
			
			if(!MobileCodeTypes.CODE.equals(mc.getType())){
				message = "验证码输入错误！";
				throw new TransactionException(message);
//						result.put("result", false);
//						result.put("message", message);
//						return result;
			}
			
			if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
				message = "验证码超时！";
				throw new TransactionException(message);
//						result.put("result", false);
//						result.put("message", message);
//						return result;
			}
			
			
			String realMD5Str = MD5.getMD5(Utlity.KEY+timestamp+mc.getCode());
			if(md5Str.equals(realMD5Str)){//登录成功
				invertor = this.investorDAO.get(invertor.getUuid());
				invertor.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
				this.investorDAO.update(invertor);
				message = "登录成功";
				result.put("result", true);
				result.put("message", message);
				result.put("uuid", invertor.getUuid());
				return result;
			} else {
				throw new TransactionException(message);
//					result.put("result", false);
//					result.put("message", message);
//					return result;
			}
			
		} else if (Utlity.DEVICE_NUMBER_WEB.equals(deviceNumber)) {
			
		}
		message = "登录失败";
		throw new TransactionException(message);

	}

//	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> updateBindingBankcard(String phone,
			String bankcard, String idcard, String username, Investor investor, String billDevice) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		//调用支付宝欺诈信息校验接口校验手机号、持卡人是否正确
//		result = AliUtlity.certification(investor.getIdcard(), username, phone, bankcard);
//		if(result.get("request") != null && (Boolean)result.get("request")){
//			//信息入库
//			Map<String, Object> response = (Map<String, Object>) result.get("response");
//			if(response != null && !response.isEmpty()){
//				AliCertification ac = new AliCertification();
//				ac.setUuid(UUID.randomUUID().toString());
//				ac.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				ac.setCreator(investor.getUuid());
//				
//				ac.setBinNo(response.get("bizNo")==null ? "无":response.get("bizNo").toString());
//				ac.setVerifyCode(response.get("verifyCode")==null ? "无":response.get("verifyCode").toString());
//				ac.setProductCode(response.get("product_code")==null ? "无":response.get("product_code").toString());
//				ac.setTranscationid(response.get("transAutoIncIdx")==null ? "无":response.get("transAutoIncIdx").toString());
//				ac.setInscription("支付宝实名认证—欺诈信息验证");
//				ac.setCode(response.get("code")==null ? "无":response.get("code").toString());
//				ac.setMsg(response.get("msg")==null ? "无":response.get("msg").toString());
//				ac.setSubCode(response.get("sub_code")==null ? "无":response.get("sub_code").toString());
//				ac.setSubMsg(response.get("sub_msg")==null ? "无":response.get("sub_msg").toString());
//				this.aliCertificationDao.insert(ac);
//			}
//		}
//		if((Boolean)result.get("result")){
//			//调用畅捷支付绑卡请求接口发送绑卡验证码
//			Map<String, Object> returnMap = ChanPayUtil.nmg_biz_api_auth_req(investor.getUuid(), investor.getIdcard(), username, phone, bankcard, billDevice);
//			OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
//			obt.setUuid(UUID.randomUUID().toString());
//			obt.setType(OrderinfoByThirdpartyType.CHANPAY);
//			obt.setInvestor(investor.getUuid());
//			obt.setOrderNum(returnMap.get("TrxId").toString());
//			obt.setBody("畅捷支付鉴权绑卡请求");
//			obt.setTotalFee(BigDecimal.ZERO);
//			obt.setPaySource(MD5.getMD5(investor.getUuid()));
//			obt.setStatus(returnMap.get("Status").toString());
//			obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
//				obt.setPrepayId(returnMap.get("OrderTrxid").toString());
//				result.put("result", true);
//				result.put("message", returnMap.get("RetMsg") == null ? "操作成功" : returnMap.get("RetMsg").toString());
//			} else {
//				String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
//				obt.setPrepayId(prepayId);
//				result.put("result", false);
//				String message = returnMap.get("RetMsg") == null ? "操作失败" : returnMap.get("RetMsg").toString();
//				if(message.indexOf("卡号与卡类型不匹配") > -1){
//					message = "不支持贷记卡类型！";
//				}
//				result.put("message", message);
//			}
//			
//			obt.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
////			if("CARD_IS_BIND".equals(obt.getErrCode())){
////				result.put("result", true);
////				result.put("message", "操作成功");
////			}
//			obt.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			obt.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			result.put("orderNum", obt.getUuid());
//			this.orderinfoByThirdpartyDAO.insert(obt);
			
			String codestr = Utlity.getCaptcha();
			String content = "验证码："+codestr+"，为了您的账户安全，千万不要告诉其他人，验证码将在5分钟后失效。（如非本人操作，请忽略本短信）";
			
			
			String results = SendSms.sendSms(content, phone);
			if ("0".equals(results.split("_")[0])) {
				
				MobileCode mc = new MobileCode();
				mc.setUuid(UUID.randomUUID().toString());
				mc.setCode(codestr);
				mc.setContent(content);
				mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
				mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
				mc.setMobile(phone);
				mc.setStatus(MobileCodeStatus.NORMAL);
				mc.setType(MobileCodeTypes.FUNDCODE);
				
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("mobile", mc.getMobile());
				inputParams.put("status", MobileCodeStatus.NORMAL);
				List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);

				// 如果之前存在验证码，设置验证码失效
				if (lstMobileCode != null && lstMobileCode.size() > 0) {
					for(Entity entity: lstMobileCode){
						MobileCode code = (MobileCode)entity;
						code.setStatus(MobileCodeStatus.DISABLE);
						this.mobileCodeDAO.update(code);
					}
				}
				this.mobileCodeDAO.insert(mc);
				result.put("orderNum", mc.getUuid());
				result.put("result", true);
				result.put("message", "验证码发送成功");
			} else {
				result.put("result", false);
				result.put("message", "验证码发送失败");
			}
//		}
		return result;
	}

	@Override
	public HashMap<String, Object> updateBindingBankcard(String bank, String bankcard, String phone, String orderNum,
			String scode, Investor investor, String orderNumStr) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		result.put("message", "绑定成功");
		//调用畅捷支付绑卡请求接口发送绑卡验证码
		Map<String, Object> returnMap = ChanPayUtil.nmg_api_auth_sms(orderNum, scode, orderNumStr);
		OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
		obt.setUuid(UUID.randomUUID().toString());
		obt.setType(OrderinfoByThirdpartyType.CHANPAY);
		obt.setInvestor(investor.getUuid());
		obt.setOrderNum(returnMap.get("TrxId").toString());
		obt.setBody("畅捷支付鉴权绑卡确认");
		obt.setTotalFee(BigDecimal.ZERO);
		obt.setPaySource(MD5.getMD5(investor.getUuid()));
		obt.setStatus(returnMap.get("Status").toString());
		obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
		obt.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
		obt.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
		obt.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
		if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
			obt.setPrepayId(returnMap.get("OrderTrxid").toString());
			result.put("message", returnMap.get("RetMsg") == null ? "绑定成功" : returnMap.get("RetMsg").toString());
			InvestorBankcard ib = new InvestorBankcard();
			ib.setUuid(UUID.randomUUID().toString());
			ib.setBandingtime(new Timestamp(System.currentTimeMillis()));
			ib.setBank(bank);
			ib.setBankAccountName("");
			ib.setBindingBankCard(bankcard);
			ib.setBindingCardPhone(phone);
			ib.setBindingCardCardholder(investor.getRealname());
			ib.setStatus(InvestorBankcardStatus.NORMAL);
			ib.setInvestor(investor.getUuid());
			this.investorBankcardDAO.insert(ib);
			result.put("ibInfo", ib.getUuid());
		} else {
			String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
			obt.setPrepayId(prepayId);
			result.put("result", false);
			String message = "绑定失败";
			if(obt.getErrCode().equals("F0001")){
				message = "绑卡信息有误，请重新绑定！";
			} else {
				message = returnMap.get("RetMsg") == null ? "绑定失败" : returnMap.get("RetMsg").toString().substring(0, returnMap.get("RetMsg").toString().length()-6);
			}
			result.put("message", message);
		}
		this.orderinfoByThirdpartyDAO.insert(obt);
		return result;
	}

	@Override
	public HashMap<String, Object> updateunBindingBankcard(MobileCode mc, InvestorBankcard ib,
			Investor investor, String orderNumStr) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		mc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeDAO.update(mc);
		result.put("result", true);
		result.put("message", "解绑成功");
		//调用畅捷支付解绑接口
		String bankcard = ib.getBindingBankCard();
		String cardbegin = bankcard.substring(0,6);
		String cardend = bankcard.substring(bankcard.length()-4);
		Map<String, Object> returnMap = ChanPayUtil.nmg_api_auth_unbind(investor.getUuid(), cardbegin, cardend, orderNumStr);
		OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
		obt.setUuid(UUID.randomUUID().toString());
		obt.setType(OrderinfoByThirdpartyType.CHANPAY);
		obt.setInvestor(investor.getUuid());
		obt.setOrderNum(returnMap.get("TrxId").toString());
		obt.setBody("畅捷支付鉴权解绑卡");
		obt.setTotalFee(BigDecimal.ZERO);
		obt.setPaySource(MD5.getMD5(investor.getUuid()));
		obt.setStatus(returnMap.get("Status").toString());
		obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
		obt.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
		obt.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
		obt.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
		if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
			obt.setPrepayId(returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString());
			result.put("message", returnMap.get("RetMsg") == null ? "解绑成功" : returnMap.get("RetMsg").toString());
			ib.setStatus(InvestorBankcardStatus.DISABLE);
			ib.setBindingBankCard(ib.getBindingBankCard()+"_#"+ib.getUuid());
			this.investorBankcardDAO.update(ib);
		} else {
			String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
			obt.setPrepayId(prepayId);
			result.put("result", false);
//			result.put("message", returnMap.get("RetMsg") == null ? "解绑失败" : returnMap.get("RetMsg").toString());
			result.put("message", "解绑失败");
			if("CAC_BANKCARD_NOTEXIST".equals(obt.getErrCode())){
				result.put("result", true);
				result.put("message", "解绑成功");
				ib.setStatus(InvestorBankcardStatus.DISABLE);
				ib.setBindingBankCard(ib.getBindingBankCard()+"_#"+ib.getUuid());
				this.investorBankcardDAO.update(ib);
			}
		}
		this.orderinfoByThirdpartyDAO.insert(obt);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> updateBindingAliUserInfo(Investor investor, String accessToken) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		//判断支付宝账户是否已绑定该账户
		Investor ali = this.investorDAO.getByAliUserid(investor.getAliUserid(), Investor.class);
		if(ali != null){
			if(!ali.getUuid().equals(investor.getUuid())){//其他账户已绑定，将其他账户重置清空
//				this.investorAccountHistoryService.updateOrderinfoAliTransferStatus();//查询一遍支付宝账户 转账信息--暂时不需要
//				result.put("result", false);
//				result.put("message", "该支付宝已绑定其他账户，请更换！");
				ali = this.investorDAO.get(ali.getUuid());
				ali.setAliNickname("");
				ali.setAliPhoto("");
				ali.setAliUserid("");
				this.investorDAO.update(ali);
			}
		}
		
		result = AliUtlity.getUserInfo(accessToken);
		if((Boolean)result.get("request")){
			if((Boolean)result.get("result")){
				Map<String, Object> responseMap = (Map<String, Object>) result.get("response");
				String userid = responseMap.get("user_id") == null ? "" : responseMap.get("user_id").toString();
				String photo = responseMap.get("avatar") == null ? "" : responseMap.get("avatar").toString();
				String nickname = responseMap.get("nick_name") == null ? "" : responseMap.get("nick_name").toString();
				investor.setAliPhoto(photo);
				investor.setAliUserid(userid);
				investor.setAliNickname(nickname);
				
				result.put("aliUserid", userid);
				result.put("photo", photo);
		        result.put("aliNickname", nickname);
				
				this.investorDAO.update(investor);
			}
		}
		return result;
	}

	@Override
	public Investor getByAliUserid(String userid, Class<? extends Entity> resultClass) {
		return this.investorDAO.getByAliUserid(userid, resultClass);
	}
	
	private void checkCouponStrategy(Investor investor, String status){
		//注册成功检查此处优惠券策略是否生效，将优惠券添加到用户账户中 同步生成应用内消息
		CouponStrategy cs = this.couponStrategyDAO.get(CouponStrategyUuid.REGISTERED);
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
				ic.setStatus(status);
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
	 * 余额自动转入活期理财
	 */
	public void updateCurrentAccount(){
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("flagCurrent", "1");
		inputParams.put("accountBalance", "1");
		
		List<Entity> list = this.investorDAO.getListForPage(inputParams, -1, -1, null, Investor.class);
		if(list != null && list.size() > 0){
			//获取当前净值
			FundPublish fp = this.fundPublishDAO.get(FundPublishUuid.CURRENT);
			BigDecimal netValue = fp.getNetWorth();
			
			//更新
			for(Entity e : list){
				Investor i = (Investor) e;
				BigDecimal currentAccountAdd = i.getAccountBalance().divide(netValue, 8, BigDecimal.ROUND_HALF_UP);
				
				InvestorAccountHistory iah = new InvestorAccountHistory();
				iah.setUuid(UUID.randomUUID().toString());
				iah.setInvestor(i.getUuid());
				iah.setOrderId(iah.getUuid());
				iah.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_OHER,Utlity.BILL_PAYTYPE_BALANCE,Utlity.BILL_PURPOSE_BUY));
				iah.setPay(i.getAccountBalance());
				iah.setIncome(BigDecimal.ZERO);
				iah.setPoundage(BigDecimal.ZERO);
				iah.setStatus(InvestorAccountHistoryStatus.SUCCESS);
				iah.setType(InvestorAccountHistoryType.CURRENT_BUY);
				iah.setCreatetime(new Timestamp(System.currentTimeMillis()));
				iah.setAccountBalance(BigDecimal.ZERO);
				this.investorAccountHistoryDAO.insert(iah);
				
				i.setCurrentAccount(i.getCurrentAccount().add(currentAccountAdd));
				i.setAccountBalance(BigDecimal.ZERO);
				this.investorDAO.update(i);
			}
		}
	}
	
	/**
	 * 更新活期理财
	 */
	@Override
	public void updateCurrentPay(Investor investor, InvestorAccountHistory iah) {
		this.investorAccountHistoryDAO.insert(iah);
		this.investorDAO.update(investor);
	}
	
	/**
	 * 更新昨日余额
	 */
	public void updateYesterdayAccount(){
		this.investorDAO.updateYesterdayAccount();
	}
}
