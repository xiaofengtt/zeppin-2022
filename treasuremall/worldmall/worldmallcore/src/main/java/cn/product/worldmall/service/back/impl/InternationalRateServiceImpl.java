package cn.product.worldmall.service.back.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.InternationalRateDao;
import cn.product.worldmall.dao.InternationalRateHistoryDao;
import cn.product.worldmall.entity.InternationalRate;
import cn.product.worldmall.entity.InternationalRate.InternationalRateStatus;
import cn.product.worldmall.entity.InternationalRateHistory.InternationalRateHistoryStatus;
import cn.product.worldmall.entity.rate.DailyCurrencyRate;
import cn.product.worldmall.entity.InternationalRateHistory;
import cn.product.worldmall.service.back.InternationalRateService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.InternationalRateHistoryVO;

@Service("internationalRateService")
public class InternationalRateServiceImpl implements InternationalRateService{
	
	@Autowired
	private InternationalRateDao internationalRateDao;
	
	@Autowired
	private InternationalRateHistoryDao internationalRateHistoryDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String currencyCode = paramsMap.get("currencyCode") == null ? "" : paramsMap.get("currencyCode").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("currencyCode", currencyCode);
		searchMap.put("status", status);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的信息的总数
		Integer totalResultCount = internationalRateDao.getCountByParams(searchMap);
		//查询符合条件的信息列表
		List<InternationalRate> list = internationalRateDao.getListByParams(searchMap);
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
		InternationalRate internationalRate = internationalRateDao.get(uuid);
		if (internationalRate == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		result.setData(internationalRate);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}
	
	/**
	 * 按时间倒序获取历史数据-用以确认和查看
	 */
	@Override
	public void checkList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String dailyDate = paramsMap.get("dailyDate") == null ? "" : paramsMap.get("dailyDate").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("dailyDate", dailyDate);
		searchMap.put("status", status);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的信息的总数
		Integer totalResultCount = internationalRateHistoryDao.getCountByParams(searchMap);
		//查询符合条件的信息列表
		List<InternationalRateHistory> list = internationalRateHistoryDao.getLessInfoListByParams(searchMap);
		List<InternationalRateHistoryVO> listvo = new ArrayList<InternationalRateHistoryVO>();
		if(list != null && list.size() > 0) {
			for(InternationalRateHistory irh : list) {
				InternationalRateHistoryVO irhvo = new InternationalRateHistoryVO(irh);
				listvo.add(irhvo);
			}
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}
	
	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String dataInfo = paramsMap.get("dataInfo") == null ? "" : paramsMap.get("dataInfo").toString();
		if(Utlity.checkStringNull(dataInfo)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("汇率信息参数不能为空！");
			return;
		}
		
		try {
			InternationalRateHistory irh = this.internationalRateHistoryDao.get(uuid);
			if(irh != null && InternationalRateHistoryStatus.NORMAL.equals(irh.getStatus())) {
				irh.setDataInfo(dataInfo);
				irh.setUpdatetime(new Timestamp(System.currentTimeMillis()));
				irh.setStatus(InternationalRateHistoryStatus.CHECKED);
				
				List<InternationalRate> listAll = this.internationalRateDao.getCurrencyListByParams();
				Map<String, InternationalRate> mapAll = new HashMap<String, InternationalRate>();
				
				if(listAll != null && listAll.size() > 0) {
					for(InternationalRate ir : listAll) {
						mapAll.put(ir.getUuid(), ir);
					}
				}
				
				List<InternationalRate> listInsert = new ArrayList<InternationalRate>();
				List<InternationalRate> listUpdate = new ArrayList<InternationalRate>();
				List<DailyCurrencyRate> list = JSONUtils.json2list(dataInfo, DailyCurrencyRate.class);
				for(DailyCurrencyRate dcr : list) {
					InternationalRate ir = this.internationalRateDao.getByCurrency(dcr.getCurrencyCode());
					if(ir != null) {
						if(mapAll.containsKey(ir.getUuid())) {//存在则队列中删除
							mapAll.remove(ir.getUuid());
						}
						
						ir.setUpdatetime(irh.getUpdatetime());
						ir.setStatus(InternationalRateStatus.PUBLISH);
//						ir.setInternationalInfo(internationalInfo);//可空
						ir.setCurrencyCode(dcr.getCurrencyCode());
						ir.setCurrencyName(dcr.getCurrencyName());
						ir.setBaseCurrency(dcr.getBaseCurrency());
						ir.setRealRate(BigDecimal.valueOf(Double.valueOf(dcr.getRealRate())));
						ir.setCheckRate(BigDecimal.valueOf(Double.valueOf(dcr.getBaseRate())));
						listUpdate.add(ir);
					} else {
						ir = new InternationalRate();
						ir.setUuid(UUID.randomUUID().toString());
						ir.setCreatetime(new Timestamp(System.currentTimeMillis()));
						ir.setUpdatetime(irh.getUpdatetime());
						ir.setStatus(InternationalRateStatus.PUBLISH);
//						ir.setInternationalInfo(internationalInfo);//可空
						ir.setCurrencyCode(dcr.getCurrencyCode());
						ir.setCurrencyName(dcr.getCurrencyName());
						ir.setBaseCurrency(dcr.getBaseCurrency());
						ir.setRealRate(BigDecimal.valueOf(Double.valueOf(dcr.getRealRate())));
						ir.setCheckRate(BigDecimal.valueOf(Double.valueOf(dcr.getBaseRate())));
						listInsert.add(ir);
					}
				}
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("listInsert", listInsert);
				dataMap.put("listUpdate", listUpdate);
				dataMap.put("mapDelete", mapAll);
				dataMap.put("internationalRateHistory", irh);
				this.internationalRateDao.updateDaily(dataMap);
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("操作成功！");
				return;
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("历史汇率信息有误！");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("汇率信息更新异常！");
			return;
		}
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		/*
		 * */
		String internationalInfo = paramsMap.get("internationalInfo") == null ? "" : paramsMap.get("internationalInfo").toString();
		String currencyCode = paramsMap.get("currencyCode") == null ? "" : paramsMap.get("currencyCode").toString();
		String currencyName = paramsMap.get("currencyName") == null ? "" : paramsMap.get("currencyName").toString();
		String baseCurrency = paramsMap.get("baseCurrency") == null ? "" : paramsMap.get("baseCurrency").toString();
		String realRate = paramsMap.get("realRate") == null ? "" : paramsMap.get("realRate").toString();
		
		try {
			if(this.internationalRateDao.getByCurrency(currencyCode) != null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("币种已存在");
				return;
			}
			//创建信息
			InternationalRate ir = new InternationalRate();
			ir.setUuid(UUID.randomUUID().toString());
			ir.setCreatetime(new Timestamp(System.currentTimeMillis()));
			ir.setUpdatetime(ir.getCreatetime());
			ir.setStatus(InternationalRateStatus.NORMAL);
			ir.setInternationalInfo(internationalInfo);
			ir.setCurrencyCode(currencyCode);
			ir.setCurrencyName(currencyName);
			ir.setBaseCurrency(baseCurrency);
			if(Utlity.checkStringNull(realRate)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("请填写正确的汇率信息");
				return;
			}
			ir.setRealRate(BigDecimal.valueOf(Double.valueOf(realRate)));
			ir.setCheckRate(BigDecimal.valueOf(Double.valueOf(realRate)));
			internationalRateDao.insert(ir);
			
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
		String internationalInfo = paramsMap.get("internationalInfo") == null ? "" : paramsMap.get("internationalInfo").toString();
		String baseCurrency = paramsMap.get("baseCurrency") == null ? "" : paramsMap.get("baseCurrency").toString();
		String realRate = paramsMap.get("realRate") == null ? "" : paramsMap.get("realRate").toString();
		
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		try {
			//获取信息
			InternationalRate ir = internationalRateDao.get(uuid);
			if(ir != null && uuid.equals(ir.getUuid())){
				
				if(InternationalRateStatus.DELETE.equals(ir.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("操作无效，已删除的货币汇率！");
					return;
				}
				
				if(InternationalRateStatus.PUBLISH.equals(ir.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("操作无效，不能修改已发布的货币汇率！");
					return;
				}
				
				//修改信息
				ir.setUpdatetime(new Timestamp(System.currentTimeMillis()));
				ir.setInternationalInfo(internationalInfo);
				ir.setBaseCurrency(baseCurrency);
				if(Utlity.checkStringNull(realRate)) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("请填写正确的汇率信息");
					return;
				}
				ir.setRealRate(BigDecimal.valueOf(Double.valueOf(realRate)));
				ir.setCheckRate(BigDecimal.valueOf(Double.valueOf(realRate)));
				internationalRateDao.update(ir);
				
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
		InternationalRate internationalRate = internationalRateDao.get(uuid);
		if(internationalRate != null && uuid.equals(internationalRate.getUuid())){
			
			if(InternationalRateStatus.PUBLISH.equals(internationalRate.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("操作无效，不能删除已发布的货币汇率！");
				return;
			}
			
			//删除信息
			internationalRate.setStatus(InternationalRateStatus.DELETE);
			internationalRateDao.update(internationalRate);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("删除成功");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();	
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();		
		try {
			//获取信息
			InternationalRate internationalRate = internationalRateDao.get(uuid);
			if(internationalRate != null && uuid.equals(internationalRate.getUuid())){
				
				if(InternationalRateStatus.DELETE.equals(internationalRate.getStatus())) {
					result.setStatus(ResultStatusType.FAILED);
					result.setMessage("操作无效，已删除的货币汇率！");
					return;
				}
				//修改信息
				internationalRate.setStatus(status);
				
				internationalRateDao.update(internationalRate);
				
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
}
