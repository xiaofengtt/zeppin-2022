package cn.product.treasuremall.service.back.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.RobotWinningRateDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.RobotWinningRate;
import cn.product.treasuremall.entity.RobotWinningRate.RobotWinningRateStatus;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.service.back.RobotWinningRateService;
import cn.product.treasuremall.util.Utlity;

@Service("robotWinningRateService")
public class RobotWinningRateServiceImpl implements RobotWinningRateService{
	
	@Autowired
	private RobotWinningRateDao robotWinningRateDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", status);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的信息的总数
		Integer totalResultCount = robotWinningRateDao.getCountByParams(searchMap);
		//查询符合条件的信息列表
		List<RobotWinningRate> list = robotWinningRateDao.getListByParams(searchMap);
		result.setData(list);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取信息
		RobotWinningRate rwr = robotWinningRateDao.get(uuid);
		if (rwr == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		result.setData(rwr);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		/*
		 * */
		String goodsPriceMin = paramsMap.get("goodsPriceMin") == null ? "" : paramsMap.get("goodsPriceMin").toString();
		String goodsPriceMax = paramsMap.get("goodsPriceMax") == null ? "" : paramsMap.get("goodsPriceMax").toString();
		String winningRate = paramsMap.get("winningRate") == null ? "" : paramsMap.get("winningRate").toString();
		
		try {
			//创建信息
			RobotWinningRate rwr = new RobotWinningRate();
			rwr.setUuid(UUID.randomUUID().toString());
			rwr.setStatus(RobotWinningRateStatus.NORMAL);
			rwr.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			rwr.setGameType(Constants.GAME_TYPE_TRADITION);
			if(!Utlity.checkStringNull(goodsPriceMax)) {
				rwr.setGoodsPriceMax(BigDecimal.valueOf(Double.valueOf(goodsPriceMax)));
			}
			rwr.setGoodsPriceMin(BigDecimal.valueOf(Double.valueOf(goodsPriceMin)));
			rwr.setWinningRate(BigDecimal.valueOf(Double.valueOf(winningRate)));
			
			robotWinningRateDao.insert(rwr);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String goodsPriceMin = paramsMap.get("goodsPriceMin") == null ? "" : paramsMap.get("goodsPriceMin").toString();
		String goodsPriceMax = paramsMap.get("goodsPriceMax") == null ? "" : paramsMap.get("goodsPriceMax").toString();
		String winningRate = paramsMap.get("winningRate") == null ? "" : paramsMap.get("winningRate").toString();
		
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		try {
			//获取信息
			RobotWinningRate rwr = robotWinningRateDao.get(uuid);
			if(rwr != null && uuid.equals(rwr.getUuid())){
				
				//修改信息
				if(!Utlity.checkStringNull(goodsPriceMax)) {
					rwr.setGoodsPriceMax(BigDecimal.valueOf(Double.valueOf(goodsPriceMax)));
				}
				rwr.setGoodsPriceMin(BigDecimal.valueOf(Double.valueOf(goodsPriceMin)));
				rwr.setWinningRate(BigDecimal.valueOf(Double.valueOf(winningRate)));
				
				robotWinningRateDao.update(rwr);
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("保存成功");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该条数据不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
		
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取信息
		RobotWinningRate rwr = robotWinningRateDao.get(uuid);
		if(rwr != null && uuid.equals(rwr.getUuid())){
			//删除信息
			rwr.setStatus(RobotWinningRateStatus.DELETE);
			robotWinningRateDao.update(rwr);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("删除成功");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}
}
