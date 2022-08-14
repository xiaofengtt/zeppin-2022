package cn.product.worldmall.aotutask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.FrontUserRanklistDao;

@Component
public class RanklistTask {
	
	@Autowired
	private FrontUserRanklistDao frontUserRanklistDao;
	

	/**
	 * 重置排行榜数据
	 */
	@Scheduled(cron="0 0/2 *  * * * ")
	public void resetRanklist() {
		try {
			this.frontUserRanklistDao.truncate();
			this.frontUserRanklistDao.insertInfoList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}