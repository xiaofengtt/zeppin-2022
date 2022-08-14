package cn.product.payment.aotutask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.ChannelAccountDailyDao;
import cn.product.payment.dao.ChannelAccountDao;

@Component
public class DailyTask {
	
	@Autowired
	private ChannelAccountDao channelAccountDao;
	
	@Autowired
	private ChannelAccountDailyDao channelAccountDailyDao;
	
	/**
	 * 每日限额相关操作
	 */
//	@Scheduled(cron="0 0 0  * * * ")
	public void processChannelAccountDaily() {
		//更新每日限额
		this.channelAccountDailyDao.clearAll();
		//重开因每日限额满导致的账号暂停
		this.channelAccountDao.rebootAllSuspend();
	}
}