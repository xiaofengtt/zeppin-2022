package cn.product.score.aotutask;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.product.score.dao.CapitalAccountDao;
import cn.product.score.dao.FrontUserAccountDao;
import cn.product.score.dao.FrontUserHistoryCheckDao;
import cn.product.score.dao.FrontUserHistoryDao;
import cn.product.score.dao.SystemParamDao;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryStatus;
import cn.product.score.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.score.entity.SystemParam;
import cn.product.score.entity.SystemParam.SystemParamKey;
import cn.product.score.util.Utlity;

@Component
public class UserRechargeTask {
	
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
	 * 自动关闭超时订单
	 */
//	@Scheduled(cron="0 0/5 *  * * * ")
	public void rechargeCloseTask() {
		SystemParam timeout = this.systemParamDao.get(SystemParamKey.RECHARGE_TIMEOUT);
		Double time = (Double.valueOf(timeout.getParamValue()) * 60 * 1000);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() - time.intValue());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", FrontUserHistoryType.USER_RECHARGE);
		params.put("status", FrontUserHistoryStatus.NORMAL);
		params.put("timeout", Utlity.timeSpanToString(timestamp));
		List<FrontUserHistory> list = this.frontUserHistoryDao.getListByParams(params);
		
		for(FrontUserHistory fuh : list){
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("frontUserHistory", fuh.getUuid());
			int count = this.frontUserHistoryCheckDao.getCountByParams(searchMap);
			if(count == 0){
				fuh.setStatus(FrontUserHistoryStatus.CLOSE);
				this.frontUserHistoryDao.update(fuh);
			}
		}
	}
	

	/**
	 * 日累计清零
	 */
//	@Scheduled(cron="0 0 0  * * * ")
	public void capitalAccountDailyTask() {
		this.capitalAccountDao.dailyRefrush();
	}
}