package cn.product.worldmall.aotutask;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserRechargeOrderDao;
import cn.product.worldmall.dao.FrontUserWithdrawOrderDao;
import cn.product.worldmall.dao.StatisticsFinanceDailyDao;
import cn.product.worldmall.dao.WinningInfoDao;
import cn.product.worldmall.entity.StatisticsFinanceDaily;
import cn.product.worldmall.entity.base.Constants;

@Component
public class StatisticsTask {
	
	@Autowired
	private StatisticsFinanceDailyDao statisticsFinanceDailyDao;
	
	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Autowired
	private FrontUserWithdrawOrderDao frontUserWithdrawOrderDao;
	
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
	private WinningInfoDao winningInfoDao;
	
	
	/**
	 * 处理可自动付款的订单
	 */
	@Scheduled(cron="10 0 *  * * * ")
	public void financeDailyTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		String endDate = sdf.format(calendar.getTime());
		calendar.add(Calendar.DATE, -30);
		String startDate = sdf.format(calendar.getTime());
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", startDate);
		searchMap.put("endtime", endDate);
		
		List<StatisticsFinanceDaily> dailyList = this.statisticsFinanceDailyDao.getListByParams(searchMap);
		
		List<String> dateStrList = new ArrayList<String>();
		calendar = Calendar.getInstance();
		for(int i=0; i<30; i++){
			dateStrList.add(sdf.format(calendar.getTime()));
			calendar.add(Calendar.DATE, -1);
		}
		
		List<StatisticsFinanceDaily> listUpdate = new ArrayList<StatisticsFinanceDaily>();
		List<StatisticsFinanceDaily> listInsert = new ArrayList<StatisticsFinanceDaily>();
		
		for(String dateStr : dateStrList){
			BigDecimal dPayment = this.frontUserHistoryDao.getAmountByParams(dateStr, Constants.ORDER_TYPE_USER_PAYMENT);
			BigDecimal recharge = this.frontUserRechargeOrderDao.getAmountByParams(dateStr);
			BigDecimal withdraw = this.frontUserWithdrawOrderDao.getAmountByParams(dateStr);
			BigDecimal winning = this.winningInfoDao.getWinningByDate(dateStr);
			BigDecimal delivery = this.winningInfoDao.getDeliveryByDate(dateStr);
			Boolean flag = false;
			for(StatisticsFinanceDaily sfd : dailyList){
				if(dateStr.equals(sfd.getDailyDate())){//若存在，则更新
					flag = true;
					sfd.setDailyDate(dateStr);
					sfd.setdPayment(dPayment);
					sfd.setRecharge(recharge);
					sfd.setWithdraw(withdraw);
					sfd.setWinning(winning);
					sfd.setDelivery(delivery);
					listUpdate.add(sfd);
					break;
				}
			}
			
			if(!flag){
				StatisticsFinanceDaily sfd = new StatisticsFinanceDaily();
				sfd.setUuid(UUID.randomUUID().toString());
				sfd.setDailyDate(dateStr);
				sfd.setdPayment(dPayment);
				sfd.setRecharge(recharge);
				sfd.setWithdraw(withdraw);
				sfd.setWinning(winning);
				sfd.setDelivery(delivery);
				
				listInsert.add(sfd);
			}
		}
		this.statisticsFinanceDailyDao.insert(listUpdate, listInsert);
	}
}
