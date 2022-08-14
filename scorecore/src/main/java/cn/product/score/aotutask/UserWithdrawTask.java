package cn.product.score.aotutask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserHistoryCheckDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.SystemParamDao;
import cn.product.score.entity.CapitalAccount;
import cn.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.entity.Orderinfo.OrderinfoType;
import cn.product.score.entity.SystemParam;
import cn.product.score.entity.SystemParam.SystemParamKey;

@Component
public class UserWithdrawTask {
	
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private FrontUserHistoryCheckDao frontUserHistoryCheckDao;
	
	@Autowired
    private CapitalAccountDao capitalAccountDao;
	
	@Autowired
    private SystemParamDao systemParamDao;
	
	/**
	 * 处理可自动付款的订单
	 */
//	@Scheduled(cron="0 * *  * * * ")
	public void withdrawConfirmTask() {
		//检查是否默认付款为自动付款
		SystemParam defaultCA = this.systemParamDao.get(SystemParamKey.WITHDRAW_DEFAULT_ACCOUNT);
		CapitalAccount ca = this.capitalAccountDao.get(defaultCA.getParamValue());
		if(ca == null || !CapitalAccountStatus.NORMAL.equals(ca.getStatus())){
			return;
		}
		if(!CapitalPlatformType.REAPAL.equals(ca.getType())){
			return;
		}
		
		//取可自动付款列表
		SystemParam checkLine = this.systemParamDao.get(SystemParamKey.WITHDRAW_CHECK_LINE);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", FrontUserHistoryType.USER_WITHDRAW);
		params.put("status", FrontUserHistoryStatus.NORMAL);
		params.put("checkLine", checkLine.getParamValue());
		List<FrontUserHistory> list = this.frontUserHistoryDao.getListByParams(params);
		
		for(FrontUserHistory fuh : list){
			fuh.setCapitalAccount(ca.getUuid());
			fuh.setStatus(FrontUserHistoryStatus.CONFIRM);
			this.frontUserHistoryDao.update(fuh);
		}
	}
	
	/**
	 * 处理融宝支付 提现订单
	 */
//	@Scheduled(cron="5 * *  * * * ")
	public void withdrawByReapal() {
		try {
			//查询管理员确认后的融宝支付 提现订单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", FrontUserHistoryStatus.CONFIRM);
			params.put("orderType", OrderinfoType.REAPAL);
			params.put("type", FrontUserHistoryType.USER_WITHDRAW);
			List<FrontUserHistory> list = this.frontUserHistoryDao.getListByParams(params);
			if(list != null) {
				//循环处理 发起提现
				Map<String, Object> result = this.frontUserHistoryCheckDao.updateWithdrawBatch(list);
				if((Boolean)result.get("result")){
					Integer count = Integer.parseInt(result.get("count").toString());
					LoggerFactory.getLogger(getClass()).info("本次处理成功，共"+count+"条重试失败");
				} else {
					LoggerFactory.getLogger(getClass()).info("处理失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}