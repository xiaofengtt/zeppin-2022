package com.product.worldpay.service.store.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.ChannelDao;
import com.product.worldpay.dao.StatisticsCompanyDao;
import com.product.worldpay.entity.Channel;
import com.product.worldpay.entity.CompanyAdmin;
import com.product.worldpay.service.store.StoreStatisticsCompanyService;
import com.product.worldpay.util.Utlity;

@Service("storeStatisticsCompanyService")
public class StoreStatisticsCompanyServiceImpl implements StoreStatisticsCompanyService{
	
	@Autowired
    private StatisticsCompanyDao statisticsCompanyDao;
	
	@Autowired
    private ChannelDao channelDao;
	
	@Override
	public void dailyList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String sort = paramsMap.get("sort") == null ? null : paramsMap.get("sort").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		CompanyAdmin admin = (CompanyAdmin) paramsMap.get("companyAdmin");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", admin.getCompany());
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		searchMap.put("sort", sort);
		List<Map<String, Object>> list = this.statisticsCompanyDao.getDailyStatisticsByParams(searchMap);
		if(list.size() == 0){
			result.setData(null);
			result.setStatus(ResultStatusType.SUCCESS);
		}
		
		Map<String ,Map<String,Object>> dataMap = new HashMap<>();
		Map<String, String> rowMap = new HashMap<>();
		for(Map<String, Object> map : list){
			if(dataMap.get(map.get("dailyDate").toString()) == null){
				dataMap.put(map.get("dailyDate").toString(), new HashMap<String,Object>());
			}
			String channelUuid = map.get("channel").toString();
			Channel channel = this.channelDao.get(channelUuid);
			
			dataMap.get(map.get("dailyDate").toString()).put(channel.getName() + " Volume", map.get("amount"));
			dataMap.get(map.get("dailyDate").toString()).put(channel.getName() + " Poundage", map.get("poundage"));
			rowMap.put(channel.getName() + " Volume", "");
			rowMap.put(channel.getName() + " Poundage", "");
		}
		
		Map<String, List<Object>> resultMap = new HashMap<>();
		resultMap.put("dailyDate", new ArrayList<Object>());
		for(String key: rowMap.keySet()){
			resultMap.put(key, new ArrayList<Object>());
		}
		
		Date startDate, endDate;
		if(starttime != null){
			startDate = Utlity.stringToDate(starttime);
		}else{
			startDate = Utlity.stringToDate(list.get(0).get("dailyDate").toString());
		}
		if(endtime != null){
			endDate = Utlity.stringToDate(endtime);
		}else{
			endDate = Utlity.stringToDate(list.get(list.size() - 1).get("dailyDate").toString());
		}
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalandar = Calendar.getInstance();
		endCalandar.setTime(endDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(;!startCalendar.after(endCalandar);startCalendar.add(Calendar.DATE, 1)){
			String dateStr = sdf.format(startCalendar.getTime());
			resultMap.get("dailyDate").add(dateStr);
			if(dataMap.get(dateStr) != null){
				for(String key : rowMap.keySet()){
					resultMap.get(key).add(Utlity.formatMoney(dataMap.get(dateStr).get(key)));
				}
			}else{
				for(String key : rowMap.keySet()){
					resultMap.get(key).add(BigDecimal.ZERO.setScale(2));
				}
			}
		}
		
		result.setData(resultMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
