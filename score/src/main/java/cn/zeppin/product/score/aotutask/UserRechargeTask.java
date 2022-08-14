package cn.zeppin.product.score.aotutask;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.score.entity.FrontUserHistory;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.zeppin.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.entity.SystemParam.SystemParamKey;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.FrontUserAccountService;
import cn.zeppin.product.score.service.FrontUserHistoryCheckService;
import cn.zeppin.product.score.service.FrontUserHistoryService;
import cn.zeppin.product.score.service.SystemParamService;
import cn.zeppin.product.score.util.Utlity;

@Component
public class UserRechargeTask {
	
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
	 * 自动关闭超时订单
	 */
//	@Scheduled(cron="0 0/5 *  * * * ")
	public void rechargeCloseTask() {
		SystemParam timeout = this.systemParamService.get(SystemParamKey.RECHARGE_TIMEOUT);
		Double time = (Double.valueOf(timeout.getParamValue()) * 60 * 1000);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() - time.intValue());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", FrontUserHistoryType.USER_RECHARGE);
		params.put("status", FrontUserHistoryStatus.NORMAL);
		params.put("timeout", Utlity.timeSpanToString(timestamp));
		List<FrontUserHistory> list = this.frontUserHistoryService.getListByParams(params);
		
		for(FrontUserHistory fuh : list){
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUserHistory", fuh.getUuid());
			int count = this.frontUserHistoryCheckService.getCountByParams(searchMap);
			if(count == 0){
				fuh.setStatus(FrontUserHistoryStatus.CLOSE);
				this.frontUserHistoryService.update(fuh);
			}
		}
	}
	

	/**
	 * 日累计清零
	 */
//	@Scheduled(cron="0 0 0  * * * ")
	public void capitalAccountDailyTask() {
		this.capitalAccountService.dailyRefrush();
	}
}