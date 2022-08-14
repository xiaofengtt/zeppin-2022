package cn.product.payment.aotutask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.payment.dao.StatisticsChannelDao;
import cn.product.payment.dao.StatisticsCompanyDao;

@Component
public class DailyStatisticsTask{
	
	@Autowired
	private StatisticsCompanyDao statisticsCompanyDao;
	
	@Autowired
	private StatisticsChannelDao statisticsChannelDao;
	
	/**
	 * 每日财务统计相关操作
	 */
//	@Scheduled(cron="20 1 *  * * * ")
	public void statisticsDaily() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//取昨日
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String dailyDate = sdf.format(calendar.getTime());
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("dailyDate", dailyDate);
		Integer companyCount = this.statisticsCompanyDao.getCountByParams(searchMap);
		if(companyCount == 0){
			//昨日商户统计不存在则生成商户统计
			this.statisticsCompanyDao.statisticsDaily(dailyDate);
		}
		Integer channelCount = this.statisticsChannelDao.getCountByParams(searchMap);
		if(channelCount == 0){
			//昨日渠道统计不存在则生成渠道统计
			this.statisticsChannelDao.statisticsDaily(dailyDate);
		}
	}
}