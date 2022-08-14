package cn.zeppin.product.score.aotutask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformType;
import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.entity.Orderinfo.OrderinfoType;
import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.entity.SystemParam.SystemParamKey;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserHistoryCheckService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.SystemParamService;

@Component
public class UserWithdrawTask {
	
	@Autowired
	private FrontUserAccountService frontUserAccountService;
	
	@Autowired
    private FrontUserHistoryService frontUserHistoryService;
	
	@Autowired
	private FrontUserHistoryCheckService frontUserHistoryCheckService;
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private SystemParamService systemParamService;
	
	/**
	 * 处理可自动付款的订单
	 */
//	@Scheduled(cron="0 * *  * * * ")
	public void withdrawConfirmTask() {
		//检查是否默认付款为自动付款
		SystemParam defaultCA = this.systemParamService.get(SystemParamKey.WITHDRAW_DEFAULT_ACCOUNT);
		CapitalAccount ca = this.capitalAccountService.get(defaultCA.getParamValue());
		if(ca == null || !CapitalAccountStatus.NORMAL.equals(ca.getStatus())){
			return;
		}
		if(!CapitalPlatformType.REAPAL.equals(ca.getType())){
			return;
		}
		
		//取可自动付款列表
		SystemParam checkLine = this.systemParamService.get(SystemParamKey.WITHDRAW_CHECK_LINE);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", FrontUserHistoryType.USER_WITHDRAW);
		params.put("status", FrontUserHistoryStatus.NORMAL);
		params.put("checkLine", checkLine.getParamValue());
		List<FrontUserHistory> list = this.frontUserHistoryService.getListByParams(params);
		
		for(FrontUserHistory fuh : list){
			fuh.setCapitalAccount(ca.getUuid());
			fuh.setStatus(FrontUserHistoryStatus.CONFIRM);
			this.frontUserHistoryService.update(fuh);
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
			List<FrontUserHistory> list = this.frontUserHistoryService.getListByParams(params);
			if(list != null) {
				//循环处理 发起提现
				Map<String, Object> result = this.frontUserHistoryCheckService.updateWithdrawBatch(list);
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