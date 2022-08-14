package cn.product.score.dao.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.score.controller.base.TransactionException;
import cn.product.score.dao.AdminDao;
import cn.product.score.dao.BankDao;
import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.CapitalAccountHistoryDao;
import cn.product.score.dao.CapitalPlatformDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserBankcardDao;
import cn.product.score.dao.FrontUserDao;
import cn.product.score.dao.FrontUserHistoryCheckDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.OrderinfoDao;
import cn.product.score.dao.PayNotifyInfoDao;
import cn.product.score.entity.Bank;
import cn.product.score.entity.CapitalAccount;
import cn.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.score.entity.CapitalAccountHistory;
import cn.product.score.entity.CapitalAccountHistory.CapitalAccountHistoryStatus;
import cn.product.score.entity.CapitalAccountHistory.CapitalAccountHistoryType;
import cn.product.score.entity.CapitalPlatform;
import cn.product.score.entity.FrontUser;
import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserBankcard;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.entity.FrontUserHistoryCheck;
import cn.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckType;
import cn.product.score.entity.Orderinfo;
import cn.product.score.entity.Orderinfo.OrderinfoStatus;
import cn.product.score.entity.PayNotifyInfo;
import cn.product.score.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.product.score.entity.PayNotifyInfo.PayNotifyInfoStatus;
import cn.product.score.mapper.FrontUserHistoryCheckMapper;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;
import cn.product.score.util.alipay.AliUtlity;
import cn.product.score.util.reapal.ReapalConfig;
import cn.product.score.util.reapal.ReapalUtlity;
import cn.product.score.util.reapal.data.WithdrawData;
import cn.product.score.util.reapal.data.WithdrawData.WithdrawDateCurrency;
import cn.product.score.util.reapal.data.WithdrawData.WithdrawDateProperties;
import cn.product.score.util.reapal.data.WithdrawDataArray;
import cn.product.score.vo.back.StatusCountVO;

@Component
public class FrontUserHistoryCheckDaoImpl implements FrontUserHistoryCheckDao{
	
	@Autowired
	private FrontUserHistoryCheckMapper frontUserHistoryCheckMapper;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private OrderinfoDao orderinfoDao;
	
	@Autowired
    private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private CapitalPlatformDao capitalPlatformDao;
	
	@Autowired
    private CapitalAccountHistoryDao capitalAccountHistoryDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private PayNotifyInfoDao payNotifyInfoDao;
	
	@Autowired
	private FrontUserBankcardDao frontUserBankcardDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserHistoryCheckMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserHistoryCheck> getListByParams(Map<String, Object> params) {
		return frontUserHistoryCheckMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserHistoryCheck",key="'frontUserHistoryCheck:' + #key")
	public FrontUserHistoryCheck get(String key) {
		return frontUserHistoryCheckMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserHistoryCheck frontUserHistoryCheck) {
		return frontUserHistoryCheckMapper.insert(frontUserHistoryCheck);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserHistoryCheck", key = "'frontUserHistoryCheck:' + #key")})
	public int delete(String key) {
		return frontUserHistoryCheckMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserHistoryCheck", key = "'frontUserHistoryCheck:' + #frontUserHistoryCheck.uuid")})
	public int update(FrontUserHistoryCheck frontUserHistoryCheck) {
		return frontUserHistoryCheckMapper.updateByPrimaryKey(frontUserHistoryCheck);
	}

	@Override
	public List<StatusCountVO> getStatusList(Map<String, Object> params) {
		return frontUserHistoryCheckMapper.getStatusList(params);
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserHistoryCheck", key = "'frontUserHistoryCheck:' + #frontUserHistoryCheck.uuid")})
	public void checkRecharge(FrontUserHistoryCheck frontUserHistoryCheck) {
		if(FrontUserHistoryCheckType.CONFIRM.equals(frontUserHistoryCheck.getType())){
			FrontUserHistory frontUserHistory = JSONUtils.json2obj(frontUserHistoryCheck.getValue(), FrontUserHistory.class);
			
			FrontUserHistory fuh = this.frontUserHistoryDao.get(frontUserHistory.getUuid());
			FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
			Orderinfo order = this.orderinfoDao.get(fuh.getOrderId());
			CapitalAccount ca = this.capitalAccountDao.get(fuh.getCapitalAccount());
			
			//订单
			order.setStatus(OrderinfoStatus.SUCCESS);
			
			//用户账户
			fua.setBalanceFree(fua.getBalanceFree().add(fuh.getIncome()).subtract(fuh.getPoundage()));
			
			//渠道账户
			ca.setBalance(ca.getBalance().add(fuh.getIncome()).subtract(fuh.getPoundage()));
			ca.setDailySum(ca.getDailySum().add(fuh.getIncome()).subtract(fuh.getPoundage()));
			if(ca.getDailyMax().abs().compareTo(ca.getDailySum().abs()) <= 0){
				ca.setStatus(CapitalAccountStatus.DISABLE);
			}
			
			//渠道账户流水儿
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setCapitalAccount(ca.getUuid());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(order.getUuid());
			cah.setOrderNum(order.getOrderNum());
			cah.setOrderType(order.getType());
			cah.setBalance(ca.getBalance());
			cah.setIncome(fuh.getIncome());
			cah.setPay(BigDecimal.ZERO);
			cah.setPoundage(fuh.getPoundage());
			cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
			cah.setTransData(fuh.getTransData());
			cah.setFrontUser(fua.getFrontUser());
			cah.setFrontUserHistory(fuh.getUuid());
			cah.setOperator(frontUserHistoryCheck.getCreator());
			cah.setOperattime(frontUserHistoryCheck.getChecktime());
			cah.setStatus(CapitalAccountHistoryStatus.SUCCESS);
			cah.setCreatetime(frontUserHistoryCheck.getChecktime());
			
			//用户流水儿
			fuh.setBalance(fua.getBalanceFree());
			fuh.setCapitalAccountHistory(cah.getUuid());
			fuh.setOperator(frontUserHistoryCheck.getCreator());
			fuh.setOperattime(frontUserHistoryCheck.getChecktime());
			fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
			
			this.orderinfoDao.update(order);
			this.frontUserAccountDao.update(fua);
			this.capitalAccountDao.update(ca);
			this.capitalAccountHistoryDao.insert(cah);
			this.frontUserHistoryDao.update(fuh);
			this.update(frontUserHistoryCheck);
		}else if(FrontUserHistoryCheckType.ADD.equals(frontUserHistoryCheck.getType())){
			FrontUserHistory fuh = JSONUtils.json2obj(frontUserHistoryCheck.getValue(), FrontUserHistory.class);
			fuh.setUuid(UUID.randomUUID().toString());
			
			FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
			CapitalAccount ca = this.capitalAccountDao.get(fuh.getCapitalAccount());
			CapitalPlatform cp = this.capitalPlatformDao.get(ca.getCapitalPlatform());
			
			//订单
			Orderinfo order = new Orderinfo();
			order.setUuid(UUID.randomUUID().toString());
			order.setType(cp.getType());
			order.setMessage("");
			order.setFrontUser(fua.getFrontUser());
			order.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_BACKADMIN,Utlity.getOrderTypeByPlatformType(cp.getType()),Utlity.BILL_PURPOSE_RECHARGE));
			order.setFee(fuh.getIncome());
			order.setStatus(OrderinfoStatus.SUCCESS);
			order.setCreatetime(frontUserHistoryCheck.getChecktime());
			
			//用户账户
			fua.setBalanceFree(fua.getBalanceFree().add(fuh.getIncome()).subtract(fuh.getPoundage()));
			
			//渠道账户
			ca.setBalance(fua.getBalanceFree().add(fuh.getIncome()).subtract(fuh.getPoundage()));
			
			//渠道账户流水儿
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setCapitalAccount(ca.getUuid());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(order.getUuid());
			cah.setOrderNum(order.getOrderNum());
			cah.setOrderType(order.getType());
			cah.setBalance(ca.getBalance());
			cah.setIncome(fuh.getIncome());
			cah.setPay(BigDecimal.ZERO);
			cah.setPoundage(fuh.getPoundage());
			cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
			cah.setTransData(fuh.getTransData());
			cah.setFrontUser(fua.getFrontUser());
			cah.setFrontUserHistory(fuh.getUuid());
			cah.setOperator(frontUserHistoryCheck.getCreator());
			cah.setOperattime(frontUserHistoryCheck.getChecktime());
			cah.setStatus(CapitalAccountHistoryStatus.SUCCESS);
			cah.setCreatetime(frontUserHistoryCheck.getChecktime());
			
			//用户流水儿
			fuh.setOrderId(order.getUuid());
			fuh.setOrderNum(order.getOrderNum());
			fuh.setOrderType(order.getType());
			fuh.setBalance(fua.getBalanceFree());
			fuh.setPay(BigDecimal.ZERO);
			fuh.setCapitalAccountHistory(cah.getUuid());
			fuh.setOperator(frontUserHistoryCheck.getCreator());
			fuh.setOperattime(frontUserHistoryCheck.getChecktime());
			fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
			fuh.setCreatetime(frontUserHistoryCheck.getChecktime());
			
			frontUserHistoryCheck.setFrontUserHistory(fuh.getUuid());
			
			this.orderinfoDao.insert(order);
			this.frontUserAccountDao.update(fua);
			this.capitalAccountDao.update(ca);
			this.capitalAccountHistoryDao.insert(cah);
			this.frontUserHistoryDao.insert(fuh);
			this.update(frontUserHistoryCheck);
		}
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserHistoryCheck", key = "'frontUserHistoryCheck:' + #frontUserHistoryCheck.uuid")})
	public void checkWithdraw(FrontUserHistoryCheck frontUserHistoryCheck) {
		FrontUserHistory fuh = this.frontUserHistoryDao.get(frontUserHistoryCheck.getFrontUserHistory());
		FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
		Orderinfo order = this.orderinfoDao.get(fuh.getOrderId());
		
		if(FrontUserHistoryCheckType.CONFIRM.equals(frontUserHistoryCheck.getType())){
			FrontUserHistory fuht = JSONUtils.json2obj(frontUserHistoryCheck.getValue(), FrontUserHistory.class);
			CapitalAccount ca = this.capitalAccountDao.get(fuht.getCapitalAccount());
			
			//订单
			order.setStatus(OrderinfoStatus.SUCCESS);
			
			//用户账户
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuh.getPay()));
			
			//渠道账户
			ca.setBalance(ca.getBalance().subtract(fuh.getPay().subtract(fuh.getPoundage())));
			ca.setDailySum(ca.getDailySum().subtract(fuh.getIncome()).subtract(fuh.getPoundage()));
			
			//渠道账户流水儿
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setCapitalAccount(ca.getUuid());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(order.getUuid());
			cah.setOrderNum(order.getOrderNum());
			cah.setOrderType(order.getType());
			cah.setBalance(ca.getBalance());
			cah.setIncome(BigDecimal.ZERO);
			cah.setPay(fuh.getPay().subtract(fuh.getPoundage()));
			cah.setPoundage(fuh.getPoundage());
			cah.setType(CapitalAccountHistoryType.USER_WITHDRAW);
			cah.setTransData(fuh.getTransData());
			cah.setFrontUser(fua.getFrontUser());
			cah.setFrontUserHistory(fuh.getUuid());
			cah.setOperator(frontUserHistoryCheck.getCreator());
			cah.setOperattime(frontUserHistoryCheck.getChecktime());
			cah.setStatus(CapitalAccountHistoryStatus.SUCCESS);
			cah.setCreatetime(frontUserHistoryCheck.getChecktime());
			
			//用户流水儿
			fuh.setBalance(fua.getBalanceFree());
			fuh.setCapitalAccount(ca.getUuid());
			fuh.setCapitalAccountHistory(cah.getUuid());
			fuh.setOperator(frontUserHistoryCheck.getCreator());
			fuh.setOperattime(frontUserHistoryCheck.getChecktime());
			fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
			fuh.setCreatetime(frontUserHistoryCheck.getChecktime());
			
			this.orderinfoDao.update(order);
			this.frontUserAccountDao.update(fua);
			this.capitalAccountDao.update(ca);
			this.capitalAccountHistoryDao.update(cah);
			this.frontUserHistoryDao.update(fuh);
			this.update(frontUserHistoryCheck);
		}else if(FrontUserHistoryCheckType.APPLY.equals(frontUserHistoryCheck.getType())){
			FrontUserHistory fuht = JSONUtils.json2obj(frontUserHistoryCheck.getValue(), FrontUserHistory.class);
			fuh.setCapitalAccount(fuht.getCapitalAccount());
			fuh.setStatus(FrontUserHistoryStatus.CONFIRM);
			fuh.setOperator(frontUserHistoryCheck.getCreator());
			fuh.setOperattime(frontUserHistoryCheck.getChecktime());
			
			this.frontUserHistoryDao.update(fuh);
			this.update(frontUserHistoryCheck);
		}else if(FrontUserHistoryCheckType.ROLLBACK.equals(frontUserHistoryCheck.getType())){
			//订单
			order.setStatus(OrderinfoStatus.CLOSE);
			
			//流水
			fuh.setStatus(FrontUserHistoryStatus.CLOSE);
			fuh.setOperator(frontUserHistoryCheck.getCreator());
			fuh.setOperattime(frontUserHistoryCheck.getChecktime());
			
			//用户账户
			fua.setBalanceFree(fua.getBalanceFree().add(fuh.getPay()));
			fua.setBalanceLock(fua.getBalanceLock().subtract(fuh.getPay()));
			
			this.orderinfoDao.update(order);
			this.frontUserAccountDao.update(fua);
			this.frontUserHistoryDao.update(fuh);
			this.update(frontUserHistoryCheck);
		}else if(FrontUserHistoryCheckType.DELETE.equals(frontUserHistoryCheck.getType())){
			//订单
			order.setStatus(OrderinfoStatus.DELETE);
			
			//流水
			fuh.setStatus(FrontUserHistoryStatus.DELETE);
			
			this.orderinfoDao.update(order);
			this.frontUserHistoryDao.update(fuh);
			this.update(frontUserHistoryCheck);
		}
	}
	
	@Override
	@Transactional
	public HashMap<String, Object> rechargeNoticeByAlipay(Map<String, String> params) throws UnsupportedEncodingException, TransactionException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String message = "ok";
		Boolean resultFlag = true;
		//解析参数
		/*
		 * 1、封装异步通知信息对象并存储
		 * 2、处理对应逻辑：TRADE_SUCCESS--更新交易记录状态，更新订单状态，增加渠道账户流水，增加取到账户余额 
		 * 其他状态对应处理 TRADE_CLOSED--更新交易记录状态，更新订单状态 TRADE_FINISHED--确认交易记录及订单状态并处理
		 */
		PayNotifyInfo pni = new PayNotifyInfo();
		pni.setUuid(UUID.randomUUID().toString());
		pni.setCreatetime(new Timestamp(System.currentTimeMillis()));
		pni.setPayType(PayNotifyInfoPayType.RECHARGE_ALIPAY_COM);
		pni.setSource(JSONUtils.obj2json(params));
		pni.setStatus(PayNotifyInfoStatus.SUCCESS);
		
		//商户交易流水号
		String out_trade_no = new String(params.get("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
		//支付宝交易号
		String trade_no = new String(params.get("trade_no").getBytes("ISO-8859-1"),"UTF-8");
	
		//交易状态
		String trade_status = new String(params.get("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		
		//公共参数
		String passback_params = new String(params.get("passback_params").getBytes("ISO-8859-1"),"UTF-8");
		
		pni.setOrderNum(out_trade_no);
		
		FrontUserHistory fuh = null;
		if(!Utlity.checkStringNull(passback_params)) {
			fuh = this.frontUserHistoryDao.get(passback_params);
		} else {
			message = "账单错误A";
			throw new TransactionException(message);
		}
		if(fuh == null) {
			message = "账单错误B";
			throw new TransactionException(message);
		}
		if(!FrontUserHistoryStatus.NORMAL.equals(fuh.getStatus())) {
			message = "账单已处理完毕！不要重复处理";
			throw new TransactionException(message);
		}
			
		Orderinfo oi = this.orderinfoDao.get(fuh.getOrderId());
		if(oi == null) {
			message = "账单错误C";
			throw new TransactionException(message);
		}
		FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
		if(fua == null) {
			message = "账单错误D";
			throw new TransactionException(message);
		}
		CapitalAccount ca = this.capitalAccountDao.get(fuh.getCapitalAccount());
		if(ca == null) {
			message = "账单错误F";
			throw new TransactionException(message);
		}
		if(trade_status.equals(AliUtlity.TRADE_SUCCESS) || trade_status.equals(AliUtlity.TRADE_FINISHED)) {//交易成功
			fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
			oi.setStatus(OrderinfoStatus.SUCCESS);
			oi.setPrepayId(trade_no);
			
			//用户账户
			fua.setBalanceFree(fua.getBalanceFree().add(fuh.getIncome()).subtract(fuh.getPoundage()));
			
			//渠道账户
			ca.setBalance(ca.getBalance().add(fuh.getIncome()).subtract(fuh.getPoundage()));
			ca.setDailySum(ca.getDailySum().add(fuh.getIncome()).subtract(fuh.getPoundage()));
			if(ca.getDailyMax().abs().compareTo(ca.getDailySum().abs()) <= 0){
				ca.setStatus(CapitalAccountStatus.DISABLE);
			}
			
			//渠道账户流水儿
			CapitalAccountHistory cah = new CapitalAccountHistory();
			cah.setUuid(UUID.randomUUID().toString());
			cah.setCapitalAccount(ca.getUuid());
			cah.setCapitalPlatform(ca.getCapitalPlatform());
			cah.setOrderId(oi.getUuid());
			cah.setOrderNum(oi.getOrderNum());
			cah.setOrderType(oi.getType());
			cah.setBalance(ca.getBalance());
			cah.setIncome(fuh.getIncome());
			cah.setPay(BigDecimal.ZERO);
			cah.setPoundage(fuh.getPoundage());
			cah.setType(CapitalAccountHistoryType.USER_RECHARGE);
			cah.setTransData(fuh.getTransData());
			cah.setFrontUser(fua.getFrontUser());
			cah.setFrontUserHistory(fuh.getUuid());
			cah.setStatus(CapitalAccountHistoryStatus.SUCCESS);
			cah.setCreatetime(fuh.getCreatetime());
			
			this.capitalAccountHistoryDao.insert(cah);
			this.capitalAccountDao.update(ca);
			this.frontUserAccountDao.update(fua);
			this.frontUserHistoryDao.update(fuh);
			this.orderinfoDao.update(oi);
			
		} else if(trade_status.equals(AliUtlity.TRADE_CLOSED)) {//交易关闭
			fuh.setStatus(FrontUserHistoryStatus.CLOSE);
			oi.setStatus(OrderinfoStatus.CLOSE);
			oi.setPrepayId(trade_no);
			
			this.frontUserHistoryDao.update(fuh);
			this.orderinfoDao.update(oi);
			
		} 
//		else if(trade_status.equals(AliUtlity.TRADE_FINISHED)) {//交易完成--不处理
//			
//		} 
		resultMap.put("message", message);
		resultMap.put("result", resultFlag);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void rechargeTask4Alipay(Map<String, Object> params) {
		if(params != null) {
			if(params.containsKey("fuaMap")) {
				Map<String, FrontUserAccount> fuaMap = (Map<String, FrontUserAccount>) params.get("fuaMap");
				for(FrontUserAccount fua : fuaMap.values()) {
					this.frontUserAccountDao.update(fua);
				}
			}
			if (params.containsKey("caMap")) {
				Map<String, CapitalAccount> caMap = (Map<String, CapitalAccount>) params.get("caMap");
				for(CapitalAccount ca : caMap.values()) {
					this.capitalAccountDao.update(ca);
				}
			}
			if (params.containsKey("cahList")) {
				List<CapitalAccountHistory> cahList = (List<CapitalAccountHistory>) params.get("cahList");
				for(CapitalAccountHistory cah : cahList) {
					this.capitalAccountHistoryDao.insert(cah);
				}
			}
			if (params.containsKey("orderList")) {
				List<Orderinfo> orderList = (List<Orderinfo>) params.get("orderList");
				for(Orderinfo order : orderList) {
					this.orderinfoDao.update(order);
				}
			}
			if (params.containsKey("fuhList")) {
				List<FrontUserHistory> fuhList = (List<FrontUserHistory>) params.get("fuhList");
				for(FrontUserHistory fuh : fuhList) {
					this.frontUserHistoryDao.update(fuh);
				}
			}
		}
	}
	
	@Override
	@Transactional
	public HashMap<String, Object> updateWithdrawBatch(List<FrontUserHistory> list) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		Integer count = 0;
		for(FrontUserHistory fuh : list){
			FrontUserBankcard fub = this.frontUserBankcardDao.get(fuh.getFrontUserBankcard());
			String bankcardnum = fub.getAccountNumber().split("_#")[0];
			Bank bank = this.bankDao.get(fub.getBank());
			FrontUser fu = this.frontUserDao.get(fuh.getFrontUser());
			BigDecimal pay = fuh.getPay();
			Orderinfo order = this.orderinfoDao.get(fuh.getOrderId());//获取订单信息
			
			Map<String, Object> resultBalance = ReapalUtlity.get_balance_Query();
			String balance = resultBalance.get("balance") == null ? "0" : resultBalance.get("balance").toString();
			BigDecimal accountTotal = BigDecimal.valueOf(Double.valueOf(balance));
			if(pay.add(fuh.getPoundage()).compareTo(accountTotal) == 1){
				order.setStatus(OrderinfoStatus.FAILED);
				order.setCreatetime(new Timestamp(System.currentTimeMillis()));
				order.setMessage("融宝提现账户余额不足！");
			} else {
				//银行付款接口调用 返回成功后
				WithdrawData wd = new WithdrawData(1, bankcardnum, fub.getAccountHolder(), bank.getName(), "", "", WithdrawDateProperties.PRIVATE, pay.subtract(fuh.getPoundage()).setScale(2, BigDecimal.ROUND_HALF_UP), WithdrawDateCurrency.CNY, "", "", fub.getPhone(), "身份证", fu.getIdcard(), "", fuh.getOrderNum(), "提现");
				
				List<WithdrawData> content = new ArrayList<WithdrawData>();
				content.add(wd);
				WithdrawDataArray wda = new WithdrawDataArray(content);
				wda.setBatch_no(fuh.getOrderNum());
				wda.setTrans_time(Utlity.timeSpanToString(fuh.getCreatetime()));
				
				Map<String, Object> result2 = ReapalUtlity.withdrawBatchSubmit(wda);
				order.setStatus(result2.get("result_code").toString());
				order.setCreatetime(new Timestamp(System.currentTimeMillis()));
				order.setMessage(result2.get("result_msg") == null ? "" : result2.get("result_msg").toString());
			}
			
			if(!ReapalConfig.REAPAL_CHECKED.equals(order.getStatus()) && !ReapalConfig.REAPAL_SUCCESS.equals(order.getStatus())){
				fuh.setStatus(FrontUserHistoryStatus.WARNNING);;//接口调用失败 进入人工处理流程，设置处理状态为未处理
				
				count++;
			} else {
				fuh.setStatus(FrontUserHistoryStatus.TRANSACTING);
			}
			this.orderinfoDao.insert(order);
			this.frontUserHistoryDao.update(fuh);
		}
		result.put("result", resultFlag);
		result.put("message", message);
		result.put("count", count);
		return result;
	}

	@Override
	@Transactional
	public HashMap<String, Object> insertReapalNotice4Pay(Map<String, Object> map)
			throws ParseException, TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
			String resultArr[] = data.split(",");
			String status = "";
			if("成功".equals(resultArr[13])){
				status = OrderinfoStatus.SUCCESS;
			} else {
				status = OrderinfoStatus.FAILED;
			}
			String reason = "";
			if(resultArr.length > 14){
				reason = resultArr[14];
			}
			
			String orderNum = resultArr[12];
			String fee = resultArr[9];
			
			BigDecimal total_fee = BigDecimal.valueOf(Double.parseDouble(fee));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderNum", orderNum);
			List<FrontUserHistory> list = this.frontUserHistoryDao.getListByParams(params);
			
			if(list != null && list.size() > 0){
				FrontUserHistory fuh = list.get(0);
				if(fuh == null){
					message = "订单不存在！"; 
					throw new TransactionException(message);
				}
				Orderinfo order = this.orderinfoDao.get(fuh.getOrderId());
				if(order == null){
					message = "订单异常！"; 
					throw new TransactionException(message);
				}
				
				if(!OrderinfoStatus.SUCCESS.equals(order.getStatus())){
					order.setStatus(status);
					order.setMessage(reason == null ? "" : reason);
				}
				
				if(!FrontUserHistoryStatus.SUCCESS.equals(fuh.getStatus())){
					
					if(!OrderinfoStatus.SUCCESS.equals(status)){//不是成功状态 算支付失败
						fuh.setStatus(FrontUserHistoryStatus.WARNNING);
						this.frontUserHistoryDao.update(fuh);
						this.orderinfoDao.update(order);
						
						message = "OK";
						resultFlag = true;
						result.put("result", resultFlag);
						result.put("message", message);
						return result;
					} else {//成功
						fuh.setStatus(FrontUserHistoryStatus.SUCCESS);
						if(!FrontUserHistoryType.USER_WITHDRAW.equals(fuh.getType())){
							message = "账单类型错误";
							throw new TransactionException(message);
						}
						//扣减用户冻结金额
						FrontUserAccount fua = this.frontUserAccountDao.get(fuh.getFrontUserAccount());
						CapitalAccount ca = this.capitalAccountDao.get(fuh.getCapitalAccount());
						
						//用户账户
						fua.setBalanceLock(fua.getBalanceLock().subtract(fuh.getPay()));
						
						//渠道账户
						ca.setBalance(ca.getBalance().subtract(total_fee));
						ca.setDailySum(ca.getDailySum().subtract(total_fee));
						
						//渠道账户流水儿
						CapitalAccountHistory cah = new CapitalAccountHistory();
						cah.setUuid(UUID.randomUUID().toString());
						cah.setCapitalAccount(ca.getUuid());
						cah.setCapitalPlatform(ca.getCapitalPlatform());
						cah.setOrderId(order.getUuid());
						cah.setOrderNum(order.getOrderNum());
						cah.setOrderType(order.getType());
						cah.setBalance(ca.getBalance());
						cah.setIncome(BigDecimal.ZERO);
						cah.setPay(total_fee);
						cah.setPoundage(fuh.getPoundage());
						cah.setType(CapitalAccountHistoryType.USER_WITHDRAW);
						cah.setTransData(fuh.getTransData());
						cah.setFrontUser(fua.getFrontUser());
						cah.setFrontUserHistory(fuh.getUuid());
						cah.setStatus(CapitalAccountHistoryStatus.SUCCESS);
						cah.setCreatetime(fuh.getCreatetime());
						
						//用户流水儿
						fuh.setBalance(fua.getBalanceFree());
						fuh.setCapitalAccount(ca.getUuid());
						fuh.setCapitalAccountHistory(cah.getUuid());
						
						this.frontUserAccountDao.update(fua);
						this.capitalAccountDao.update(ca);
						this.capitalAccountHistoryDao.update(cah);
					}
					this.frontUserHistoryDao.update(fuh);
					this.orderinfoDao.update(order);
					
				} else {
					message = "状态错误";
					throw new TransactionException(message);
				}
			} else {
				message = "账单错误";
				throw new TransactionException(message);
			}
			LoggerFactory.getLogger(getClass()).info("成功");
			result.put("result", resultFlag);
			result.put("message", message);
			return result;
		} else {
			message = "发生错误";
			throw new TransactionException(message);
		}
	}
}
