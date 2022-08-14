package com.product.worldpay.aotutask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.product.worldpay.dao.ChannelAccountDailyDao;
import com.product.worldpay.dao.ChannelAccountDao;

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
		this.channelAccountDailyDao.clearAll();
		this.channelAccountDao.rebootAllSuspend();
	}
}