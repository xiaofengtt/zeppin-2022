package cn.product.payment.service.store.impl;

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

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.StatisticsCompanyDao;
import cn.product.payment.entity.CompanyAdmin;
import cn.product.payment.entity.StatisticsCompany.StatisticsCompanyType;
import cn.product.payment.service.store.StoreStatisticsCompanyService;
import cn.product.payment.util.Utlity;

/**
 * 商户统计
 * @author Administrator
 *
 */
@Service("storeStatisticsCompanyService")
public class StoreStatisticsCompanyServiceImpl implements StoreStatisticsCompanyService{
	
	@Autowired
    private StatisticsCompanyDao statisticsCompanyDao;
	
	/**
	 * 获取每日统计
	 */
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
			//无统计数据
			result.setData(null);
			result.setStatus(ResultStatusType.SUCCESS);
		}
		
		//处理数据 按日期 - 种类 
		Map<String ,Map<String,Object>> dataMap = new HashMap<>();
		for(Map<String, Object> map : list){
			if(dataMap.get(map.get("dailyDate").toString()) == null){
				dataMap.put(map.get("dailyDate").toString(), new HashMap<String,Object>());
			}
			dataMap.get(map.get("dailyDate").toString()).put(map.get("type").toString() + "_amount", map.get("amount"));
			dataMap.get(map.get("dailyDate").toString()).put(map.get("type").toString() + "_poundage", map.get("poundage"));
		}
		
		//基础数据结构 每种类型的数据 单独封装成一个List
		Map<String, List<Object>> resultMap = new HashMap<>();
		resultMap.put("dailyDate", new ArrayList<Object>());
		resultMap.put(StatisticsCompanyType.COMPANY_RECHARGE + "_amount", new ArrayList<Object>());
		resultMap.put(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount", new ArrayList<Object>());
		resultMap.put(StatisticsCompanyType.USER_RECHARGE + "_amount", new ArrayList<Object>());
		resultMap.put(StatisticsCompanyType.USER_WITHDRAW + "_amount", new ArrayList<Object>());
		resultMap.put(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage", new ArrayList<Object>());
		resultMap.put(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage", new ArrayList<Object>());
		resultMap.put(StatisticsCompanyType.USER_RECHARGE + "_poundage", new ArrayList<Object>());
		resultMap.put(StatisticsCompanyType.USER_WITHDRAW + "_poundage", new ArrayList<Object>());
		
		//计算开始结束日期
		Date startDate, endDate;
		if(starttime != null){
			//有参用参数
			startDate = Utlity.stringToDate(starttime);
		}else{
			//无参 最早一条时间
			startDate = Utlity.stringToDate(list.get(0).get("dailyDate").toString());
		}
		if(endtime != null){
			//有参用参数
			endDate = Utlity.stringToDate(endtime);
		}else{
			//无参取昨天
			endDate = Utlity.stringToDate(list.get(list.size() - 1).get("dailyDate").toString());
		}
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalandar = Calendar.getInstance();
		endCalandar.setTime(endDate);
		
		//格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		//循环日期
		for(;!startCalendar.after(endCalandar);startCalendar.add(Calendar.DATE, 1)){
			//插格式化日期
			String dateStr = sdf.format(startCalendar.getTime());
			resultMap.get("dailyDate").add(dateStr);
			if(dataMap.get(dateStr) != null){
				//有数据插数据
				resultMap.get(StatisticsCompanyType.COMPANY_RECHARGE + "_amount").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.COMPANY_RECHARGE + "_amount")));
				resultMap.get(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount")));
				resultMap.get(StatisticsCompanyType.USER_RECHARGE + "_amount").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.USER_RECHARGE + "_amount")));
				resultMap.get(StatisticsCompanyType.USER_WITHDRAW + "_amount").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.USER_WITHDRAW + "_amount")));
				resultMap.get(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage")));
				resultMap.get(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage")));
				resultMap.get(StatisticsCompanyType.USER_RECHARGE + "_poundage").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.USER_RECHARGE + "_poundage")));
				resultMap.get(StatisticsCompanyType.USER_WITHDRAW + "_poundage").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.USER_WITHDRAW + "_poundage")));
			}else{
				//没数据插0
				resultMap.get(StatisticsCompanyType.COMPANY_RECHARGE + "_amount").add(BigDecimal.ZERO.setScale(2));
				resultMap.get(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount").add(BigDecimal.ZERO.setScale(2));
				resultMap.get(StatisticsCompanyType.USER_RECHARGE + "_amount").add(BigDecimal.ZERO.setScale(2));
				resultMap.get(StatisticsCompanyType.USER_WITHDRAW + "_amount").add(BigDecimal.ZERO.setScale(2));
				resultMap.get(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage").add(BigDecimal.ZERO.setScale(2));
				resultMap.get(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage").add(BigDecimal.ZERO.setScale(2));
				resultMap.get(StatisticsCompanyType.USER_RECHARGE + "_poundage").add(BigDecimal.ZERO.setScale(2));
				resultMap.get(StatisticsCompanyType.USER_WITHDRAW + "_poundage").add(BigDecimal.ZERO.setScale(2));
			}
		}
		
		result.setData(resultMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
