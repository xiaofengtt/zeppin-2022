package cn.zeppin.product.score.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.controller.base.TransactionException;
import cn.zeppin.product.score.entity.Bank;
import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.zeppin.product.score.entity.CapitalAccountHistory;
import cn.zeppin.product.score.entity.CapitalAccountHistory.CapitalAccountHistoryStatus;
import cn.zeppin.product.score.entity.CapitalAccountHistory.CapitalAccountHistoryType;
import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.entity.FrontUserBankcard;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.entity.FrontUserHistoryCheck;
import cn.zeppin.product.score.entity.FrontUserHistoryCheck.FrontUserHistoryCheckType;
import cn.zeppin.product.score.entity.Orderinfo;
import cn.zeppin.product.score.entity.Orderinfo.OrderinfoStatus;
import cn.zeppin.product.score.entity.PayNotifyInfo;
import cn.zeppin.product.score.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.zeppin.product.score.entity.PayNotifyInfo.PayNotifyInfoStatus;
import cn.zeppin.product.score.mapper.FrontUserHistoryCheckMapper;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.BankService;
import cn.zeppin.product.score.service.CapitalAccountHistoryService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.CapitalPlatformService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserBankcardService;
import cn.zeppin.product.score.service.FrontUserHistoryCheckService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.OrderinfoService;
import cn.zeppin.product.score.service.PayNotifyInfoService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.util.alipay.AliUtlity;
import cn.zeppin.product.score.util.reapal.ReapalConfig;
import cn.zeppin.product.score.util.reapal.ReapalUtlity;
import cn.zeppin.product.score.util.reapal.data.WithdrawData;
import cn.zeppin.product.score.util.reapal.data.WithdrawDataArray;
import cn.zeppin.product.score.util.reapal.data.WithdrawData.WithdrawDateCurrency;
import cn.zeppin.product.score.util.reapal.data.WithdrawData.WithdrawDateProperties;
import cn.zeppin.product.score.vo.back.StatusCountVO;

@Service("frontUserHistoryCheckService")
public class FrontUserHistoryCheckServiceImpl implements FrontUserHistoryCheckService{
	
	@Autowired
	private FrontUserHistoryCheckMapper frontUserHistoryCheckMapper;
	
	@Autowired
    private FrontUserAccountService frontUserAccountService;
	
	@Autowired
    private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
    private OrderinfoService orderinfoService;
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private CapitalPlatformService capitalPlatformService;
	
	@Autowired
    private CapitalAccountHistoryService capitalAccountHistoryService;
	
	@Autowired
    private AdminService adminService;
	
	@Autowired
    private PayNotifyInfoService payNotifyInfoService;
	
	@Autowired
	private FrontUserBankcardService frontUserBankcardService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private FrontUserService frontUserService;
	
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
			
			FrontUserHistory fuh = this.frontUserHistoryService.get(frontUserHistory.getUuid());
			FrontUserAccount fua = this.frontUserAccountService.get(fuh.getFrontUserAccount());
			Orderinfo order = this.orderinfoService.get(fuh.getOrderId());
			CapitalAccount ca = this.capitalAccountService.get(fuh.getCapitalAccount());
			
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
			
			this.orderinfoService.update(order);
			this.frontUserAccountService.update(fua);
			this.capitalAccountService.update(ca);
			this.capitalAccountHistoryService.insert(cah);
			this.frontUserHistoryService.update(fuh);
			this.update(frontUserHistoryCheck);
		}else if(FrontUserHistoryCheckType.ADD.equals(frontUserHistoryCheck.getType())){
			FrontUserHistory fuh = JSONUtils.json2obj(frontUserHistoryCheck.getValue(), FrontUserHistory.class);
			fuh.setUuid(UUID.randomUUID().toString());
			
			FrontUserAccount fua = this.frontUserAccountService.get(fuh.getFrontUserAccount());
			CapitalAccount ca = this.capitalAccountService.get(fuh.getCapitalAccount());
			CapitalPlatform cp = this.capitalPlatformService.get(ca.getCapitalPlatform());
			
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
			
			this.orderinfoService.insert(order);
			this.frontUserAccountService.update(fua);
			this.capitalAccountService.update(ca);
			this.capitalAccountHistoryService.insert(cah);
			this.frontUserHistoryService.insert(fuh);
			this.update(frontUserHistoryCheck);
		}
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value = "frontUserHistoryCheck", key = "'frontUserHistoryCheck:' + #frontUserHistoryCheck.uuid")})
	public void checkWithdraw(FrontUserHistoryCheck frontUserHistoryCheck) {
		FrontUserHistory fuh = this.frontUserHistoryService.get(frontUserHistoryCheck.getFrontUserHistory());
		FrontUserAccount fua = this.frontUserAccountService.get(fuh.getFrontUserAccount());
		Orderinfo order = this.orderinfoService.get(fuh.getOrderId());
		
		if(FrontUserHistoryCheckType.CONFIRM.equals(frontUserHistoryCheck.getType())){
			FrontUserHistory fuht = JSONUtils.json2obj(frontUserHistoryCheck.getValue(), FrontUserHistory.class);
			CapitalAccount ca = this.capitalAccountService.get(fuht.getCapitalAccount());
			
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
			
			this.orderinfoService.update(order);
			this.frontUserAccountService.update(fua);
			this.capitalAccountService.update(ca);
			this.capitalAccountHistoryService.update(cah);
			this.frontUserHistoryService.update(fuh);
			this.update(frontUserHistoryCheck);
		}else if(FrontUserHistoryCheckType.APPLY.equals(frontUserHistoryCheck.getType())){
			FrontUserHistory fuht = JSONUtils.json2obj(frontUserHistoryCheck.getValue(), FrontUserHistory.class);
			fuh.setCapitalAccount(fuht.getCapitalAccount());
			fuh.setStatus(FrontUserHistoryStatus.CONFIRM);
			fuh.setOperator(frontUserHistoryCheck.getCreator());
			fuh.setOperattime(frontUserHistoryCheck.getChecktime());
			
			this.frontUserHistoryService.update(fuh);
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
			
			this.orderinfoService.update(order);
			this.frontUserAccountService.update(fua);
			this.frontUserHistoryService.update(fuh);
			this.update(frontUserHistoryCheck);
		}else if(FrontUserHistoryCheckType.DELETE.equals(frontUserHistoryCheck.getType())){
			//订单
			order.setStatus(OrderinfoStatus.DELETE);
			
			//流水
			fuh.setStatus(FrontUserHistoryStatus.DELETE);
			
			this.orderinfoService.update(order);
			this.frontUserHistoryService.update(fuh);
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
			fuh = this.frontUserHistoryService.get(passback_params);
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
			
		Orderinfo oi = this.orderinfoService.get(fuh.getOrderId());
		if(oi == null) {
			message = "账单错误C";
			throw new TransactionException(message);
		}
		FrontUserAccount fua = this.frontUserAccountService.get(fuh.getFrontUserAccount());
		if(fua == null) {
			message = "账单错误D";
			throw new TransactionException(message);
		}
		CapitalAccount ca = this.capitalAccountService.get(fuh.getCapitalAccount());
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
			
			this.capitalAccountHistoryService.insert(cah);
			this.capitalAccountService.update(ca);
			this.frontUserAccountService.update(fua);
			this.frontUserHistoryService.update(fuh);
			this.orderinfoService.update(oi);
			
		} else if(trade_status.equals(AliUtlity.TRADE_CLOSED)) {//交易关闭
			fuh.setStatus(FrontUserHistoryStatus.CLOSE);
			oi.setStatus(OrderinfoStatus.CLOSE);
			oi.setPrepayId(trade_no);
			
			this.frontUserHistoryService.update(fuh);
			this.orderinfoService.update(oi);
			
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
					this.frontUserAccountService.update(fua);
				}
			}
			if (params.containsKey("caMap")) {
				Map<String, CapitalAccount> caMap = (Map<String, CapitalAccount>) params.get("caMap");
				for(CapitalAccount ca : caMap.values()) {
					this.capitalAccountService.update(ca);
				}
			}
			if (params.containsKey("cahList")) {
				List<CapitalAccountHistory> cahList = (List<CapitalAccountHistory>) params.get("cahList");
				for(CapitalAccountHistory cah : cahList) {
					this.capitalAccountHistoryService.insert(cah);
				}
			}
			if (params.containsKey("orderList")) {
				List<Orderinfo> orderList = (List<Orderinfo>) params.get("orderList");
				for(Orderinfo order : orderList) {
					this.orderinfoService.update(order);
				}
			}
			if (params.containsKey("fuhList")) {
				List<FrontUserHistory> fuhList = (List<FrontUserHistory>) params.get("fuhList");
				for(FrontUserHistory fuh : fuhList) {
					this.frontUserHistoryService.update(fuh);
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
			FrontUserBankcard fub = this.frontUserBankcardService.get(fuh.getFrontUserBankcard());
			String bankcardnum = fub.getAccountNumber().split("_#")[0];
			Bank bank = this.bankService.get(fub.getBank());
			FrontUser fu = this.frontUserService.get(fuh.getFrontUser());
			BigDecimal pay = fuh.getPay();
			Orderinfo order = this.orderinfoService.get(fuh.getOrderId());//获取订单信息
			
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
			this.orderinfoService.insert(order);
			this.frontUserHistoryService.update(fuh);
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
			List<FrontUserHistory> list = this.frontUserHistoryService.getListByParams(params);
			
			if(list != null && list.size() > 0){
				FrontUserHistory fuh = list.get(0);
				if(fuh == null){
					message = "订单不存在！"; 
					throw new TransactionException(message);
				}
				Orderinfo order = this.orderinfoService.get(fuh.getOrderId());
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
						this.frontUserHistoryService.update(fuh);
						this.orderinfoService.update(order);
						
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
						FrontUserAccount fua = this.frontUserAccountService.get(fuh.getFrontUserAccount());
						CapitalAccount ca = this.capitalAccountService.get(fuh.getCapitalAccount());
						
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
						
						this.frontUserAccountService.update(fua);
						this.capitalAccountService.update(ca);
						this.capitalAccountHistoryService.update(cah);
					}
					this.frontUserHistoryService.update(fuh);
					this.orderinfoService.update(order);
					
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
