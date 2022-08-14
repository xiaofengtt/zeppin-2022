package cn.product.treasuremall.service.back.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserDailyDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserRechargeOrderDao;
import cn.product.treasuremall.service.back.UserDailyService;
import cn.product.treasuremall.util.Utlity;

@Service("userDailyService")
public class UserDailyServiceImpl implements UserDailyService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserDailyDao frontUserDailyDao;

	@Autowired
	private FrontUserRechargeOrderDao frontUserRechargeOrderDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String starttime = paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		List<Map<String, Object>> activeList = this.frontUserDailyDao.getActiveListByParams(searchMap);
		
		searchMap.put("starttime", starttime + " 00:00:00");
		searchMap.put("endtime", endtime + " 23:59:59");
		List<Map<String, Object>> registList = this.frontUserDao.getRegistListByParams(searchMap);
		List<Map<String, Object>> firstList = this.frontUserRechargeOrderDao.getFirstListByParams(searchMap);
				
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			startDate = Utlity.stringToDate(starttime);
			endDate = Utlity.stringToDate(endtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalandar = Calendar.getInstance();
		endCalandar.setTime(endDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(;!startCalendar.after(endCalandar);startCalendar.add(Calendar.DATE, 1)){
			String dateStr = sdf.format(startCalendar.getTime());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dailyDate", dateStr);
			map.put("regist", 0);
			map.put("first", 0);
			map.put("active", 0);
			
			for(Map<String, Object> registDate : registList){
				if(dateStr.equals(registDate.get("dailyDate").toString())){
					map.put("regist", registDate.get("count"));
				}
			}
			
			for(Map<String, Object> firstDate : firstList){
				if(dateStr.equals(firstDate.get("dailyDate").toString())){
					map.put("first", firstDate.get("count"));
				}
			}
			
			for(Map<String, Object> activeDate : activeList){
				if(dateStr.equals(activeDate.get("dailyDate").toString())){
					map.put("active", activeDate.get("count"));
				}
			}
			
			resultList.add(map);
		}
		
		result.setData(resultList);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
