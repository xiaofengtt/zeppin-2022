package cn.product.worldmall.service.back.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.StatisticsFinanceDailyDao;
import cn.product.worldmall.entity.StatisticsFinanceDaily;
import cn.product.worldmall.service.back.StatisticsFinanceDailyService;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("statisticsFinanceDailyService")
public class StatisticsFinanceDailyServiceImpl implements StatisticsFinanceDailyService{
	
	@Autowired
	private StatisticsFinanceDailyDao statisticsFinanceDailyDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String starttime = paramsMap.get("starttime") == null ? "" : paramsMap.get("starttime").toString();
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("starttime", starttime);
		searchMap.put("endtime", endtime);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		Integer totalResultCount = statisticsFinanceDailyDao.getCountByParams(searchMap);
		List<StatisticsFinanceDaily> list = statisticsFinanceDailyDao.getListByParams(searchMap);
		
		
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}
}
