package cn.product.payment.service.system.impl;

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
import cn.product.payment.dao.CompanyDao;
import cn.product.payment.dao.StatisticsCompanyDao;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.StatisticsCompany.StatisticsCompanyType;
import cn.product.payment.service.system.SystemStatisticsCompanyService;
import cn.product.payment.util.Utlity;

/**
 * 平台商户统计
 */
@Service("systemStatisticsCompanyService")
public class SystemStatisticsCompanyServiceImpl implements SystemStatisticsCompanyService{
	
	@Autowired
    private StatisticsCompanyDao statisticsCompanyDao;

	@Autowired
    private CompanyDao companyDao;
	
	/**
	 * 取按日统计
	 */
	@Override
	public void dailyList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String company = paramsMap.get("company") == null ? null : paramsMap.get("company").toString();
		String sort = paramsMap.get("sort") == null ? null : paramsMap.get("sort").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("company", company);
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		searchMap.put("sort", sort);
		List<Map<String, Object>> list = this.statisticsCompanyDao.getDailyStatisticsByParams(searchMap);
		if(list.size() == 0){
			//没数据返回空
			result.setData(null);
			result.setStatus(ResultStatusType.SUCCESS);
		}
		
		//生成按日统计Map
		Map<String ,Map<String,Object>> dataMap = new HashMap<>();
		//循环所有数据
		for(Map<String, Object> map : list){
			if(dataMap.get(map.get("dailyDate").toString()) == null){
				//新日期新建Key
				dataMap.put(map.get("dailyDate").toString(), new HashMap<String,Object>());
			}
			//插入该日统计数据
			dataMap.get(map.get("dailyDate").toString()).put(map.get("type").toString() + "_amount", map.get("amount"));
			dataMap.get(map.get("dailyDate").toString()).put(map.get("type").toString() + "_poundage", map.get("poundage"));
		}
		
		//生成封装结构（按交易类型的list的map）
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
		
		//确定起止日期
		Date startDate, endDate;
		//起
		if(starttime != null){
			//有就用
			startDate = Utlity.stringToDate(starttime);
		}else{
			//没有用第一条数据时间
			startDate = Utlity.stringToDate(list.get(0).get("dailyDate").toString());
		}
		//止
		if(endtime != null){
			//有就用
			endDate = Utlity.stringToDate(endtime);
		}else{
			//没有用最后一条数据时间
			endDate = Utlity.stringToDate(list.get(list.size() - 1).get("dailyDate").toString());
		}
		
		//起止日历
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalandar = Calendar.getInstance();
		endCalandar.setTime(endDate);
		
		//日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//循环起止日间日期
		for(;!startCalendar.after(endCalandar);startCalendar.add(Calendar.DATE, 1)){
			String dateStr = sdf.format(startCalendar.getTime());
			resultMap.get("dailyDate").add(dateStr);
			if(dataMap.get(dateStr) != null){
				//该日有数据塞入数据
				resultMap.get(StatisticsCompanyType.COMPANY_RECHARGE + "_amount").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.COMPANY_RECHARGE + "_amount")));
				resultMap.get(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount")));
				resultMap.get(StatisticsCompanyType.USER_RECHARGE + "_amount").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.USER_RECHARGE + "_amount")));
				resultMap.get(StatisticsCompanyType.USER_WITHDRAW + "_amount").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.USER_WITHDRAW + "_amount")));
				resultMap.get(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage")));
				resultMap.get(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage")));
				resultMap.get(StatisticsCompanyType.USER_RECHARGE + "_poundage").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.USER_RECHARGE + "_poundage")));
				resultMap.get(StatisticsCompanyType.USER_WITHDRAW + "_poundage").add(Utlity.formatMoney(dataMap.get(dateStr).get(StatisticsCompanyType.USER_WITHDRAW + "_poundage")));
			}else{
				//该日没数据塞0
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
	
	/**
	 * 取安商户统计
	 */
	@Override
	public void companyList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String sort = paramsMap.get("sort") == null ? null : paramsMap.get("sort").toString();
		String starttime = paramsMap.get("starttime") == null ? null : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? null : paramsMap.get("endtime").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		searchMap.put("sort", sort);
		List<Map<String, Object>> list = this.statisticsCompanyDao.getCompanyStatisticsByParams(searchMap);
		if(list.size() == 0){
			//没数据返回空
			result.setData(null);
			result.setStatus(ResultStatusType.SUCCESS);
		}
		
		//生成按商户统计Map
		Map<String ,Map<String,Object>> dataMap = new HashMap<>();
		//循环数据
		for(Map<String, Object> map : list){
			if(dataMap.get(map.get("company").toString()) == null){
				//新商户 新建key
				dataMap.put(map.get("company").toString(), new HashMap<String,Object>());
			}
			//插入商户统计数据
			dataMap.get(map.get("company").toString()).put(map.get("type").toString() + "_amount", map.get("amount"));
			dataMap.get(map.get("company").toString()).put(map.get("type").toString() + "_poundage", map.get("poundage"));
		}
		
		//生成封装结构（每个商户的统计信息构成的Map的List）
		List<Map<String, Object>> resultList = new ArrayList<>();
		for(String company : dataMap.keySet()){
			Map<String, Object> resultData = new HashMap<String, Object>();
			//塞入商户信息
			Company com = this.companyDao.get(company);
			resultData.put("uuid", com.getUuid());
			resultData.put("name", com.getName());
			resultData.put("code", com.getCode());
			resultData.put("status", com.getStatus());
			resultData.put("createtime", com.getCreatetime());
			
			//塞入统计数据
			resultData.put(StatisticsCompanyType.COMPANY_RECHARGE + "_amount", Utlity.formatMoney(dataMap.get(company).get(StatisticsCompanyType.COMPANY_RECHARGE + "_amount")));
			resultData.put(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount", Utlity.formatMoney(dataMap.get(company).get(StatisticsCompanyType.COMPANY_WITHDRAW + "_amount")));
			resultData.put(StatisticsCompanyType.USER_RECHARGE + "_amount", Utlity.formatMoney(dataMap.get(company).get(StatisticsCompanyType.USER_RECHARGE + "_amount")));
			resultData.put(StatisticsCompanyType.USER_WITHDRAW + "_amount", Utlity.formatMoney(dataMap.get(company).get(StatisticsCompanyType.USER_WITHDRAW + "_amount")));
			resultData.put(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage", Utlity.formatMoney(dataMap.get(company).get(StatisticsCompanyType.COMPANY_RECHARGE + "_poundage")));
			resultData.put(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage", Utlity.formatMoney(dataMap.get(company).get(StatisticsCompanyType.COMPANY_WITHDRAW + "_poundage")));
			resultData.put(StatisticsCompanyType.USER_RECHARGE + "_poundage", Utlity.formatMoney(dataMap.get(company).get(StatisticsCompanyType.USER_RECHARGE + "_poundage")));
			resultData.put(StatisticsCompanyType.USER_WITHDRAW + "_poundage", Utlity.formatMoney(dataMap.get(company).get(StatisticsCompanyType.USER_WITHDRAW + "_poundage")));
			resultList.add(resultData);
		}
		
		result.setData(resultList);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
