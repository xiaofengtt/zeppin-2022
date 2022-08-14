package cn.product.worldmall.aotutask;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.product.worldmall.dao.InternationalRateDao;
import cn.product.worldmall.dao.InternationalRateHistoryDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.InternationalRate;
import cn.product.worldmall.entity.InternationalRate.InternationalRateStatus;
import cn.product.worldmall.entity.InternationalRateHistory;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.entity.InternationalRateHistory.InternationalRateHistoryStatus;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.base.Constants;
import cn.product.worldmall.entity.rate.DailyCurrencyRate;
import cn.product.worldmall.util.ExchangeRateGetUtil;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;

@Component
public class DictGetDailyTask {
	public static final Logger log= LoggerFactory.getLogger(ExchangeRateGetUtil.class);
	
	@Autowired
	private InternationalRateHistoryDao internationalRateHistoryDao;
	
	@Autowired
	private InternationalRateDao internationalRateDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	/**
	 * 查询汇率
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron="10 0/30 4  * * * ")
	public void resetRanklist() {
		try {
			Map<String, Object> resultMap = ExchangeRateGetUtil.latestInfo();
			if(resultMap != null) {
				Boolean request = (Boolean) resultMap.get("request");
				if(request) {//请求正常
					String resultStr = resultMap.get("result").toString();
					log.info("--------------111-------------------:"+resultStr);
					Map<String, Object> requestMap = JSONUtils.json2map(resultStr);
					Boolean error = requestMap.get("error") == null ? false : (Boolean)requestMap.get("error");
					if(!error) {//数据正常
						//查询本日是否已拉取数据
						String today = Utlity.timestampFormat(new Timestamp(System.currentTimeMillis()), "yyyy-MM-dd");
						boolean isUpdate = false;
						InternationalRateHistory irh = this.internationalRateHistoryDao.getByDailyDate(today);
						if(irh != null) {//存在则更新
							isUpdate = true;
							//若数据已确认，则结束
							if(InternationalRateHistoryStatus.CHECKED.equals(irh.getStatus())) {
								return;
							}
						} else {
							irh = new InternationalRateHistory();
							irh.setCreatetime(new Timestamp(System.currentTimeMillis()));
							irh.setUuid(UUID.randomUUID().toString());
						}
						irh.setStatus(InternationalRateHistoryStatus.NORMAL);
						irh.setSourceStr(resultStr);
						irh.setDailyDate(today);
						
						Long currenttime = requestMap.get("timestamp") == null ? 0L : Long.valueOf(requestMap.get("timestamp").toString() + "000");
						irh.setUpdatetime(new Timestamp(currenttime));
						
						List<DailyCurrencyRate> listRate = new ArrayList<DailyCurrencyRate>();
						Map<String, Object> rates = (Map<String, Object>) requestMap.get("rates");
						
						//系统参数控制要更新的数据
						SystemParam currencies = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES);//系统设置的参数值
						String[] currency = currencies.getParamValue().split("\\|");
						for(String key : currency) {
							if(Constants.currences.containsKey(key)) {
								DailyCurrencyRate dcr = new DailyCurrencyRate();
								dcr.setCurrencyCode(key);
								dcr.setCurrencyName(Constants.currences.get(key));

								log.info("--------------222-------------------:"+key+"--"+Constants.currences.get(key));
								String rate = rates.get(key) == null ? "" : rates.get(key).toString();
								if(Utlity.checkStringNull(rate)) {
									continue;
								}
								dcr.setRealRate(rate);
								dcr.setBaseRate(rate);
								dcr.setBaseCurrency(Constants.BASE_CURRENCY);
								listRate.add(dcr);
							} else {
								log.info("--------------币种-------------------:"+key+"--未初始化进系统！");
							}
						}
//						for(Map.Entry<String, String> entry : Constants.currences.entrySet()) {
//							DailyCurrencyRate dcr = new DailyCurrencyRate();
//							dcr.setCurrencyCode(entry.getKey());
//							dcr.setCurrencyName(entry.getValue());
//
//							log.info("--------------222-------------------:"+entry.getKey()+"--"+entry.getValue());
//							String rate = rates.get(entry.getKey()) == null ? "" : rates.get(entry.getKey()).toString();
//							if(Utlity.checkStringNull(rate)) {
//								continue;
//							}
//							dcr.setRate(rate);
//							dcr.setBaseCurrency(Constants.BASE_CURRENCY);
//							listRate.add(dcr);
//						}
						String dataInfo = JSONUtils.obj2json(listRate);
						irh.setDataInfo(dataInfo);
						
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("isUpdate", isUpdate);
						params.put("internationalRateHistory", irh);
						this.internationalRateHistoryDao.dailyUpdate(params);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 自动确认更新过来的汇率信息
	 */
//	@Scheduled(cron="0 0/2 *  * * * ")
	public void resetRankInfo() {
		String today = Utlity.timestampFormat(new Timestamp(System.currentTimeMillis()), "yyyy-MM-dd");
		InternationalRateHistory irh = this.internationalRateHistoryDao.getByDailyDate(today);
		if(irh != null && InternationalRateHistoryStatus.NORMAL.equals(irh.getStatus())) {//存在则更新
			irh.setStatus(InternationalRateHistoryStatus.CHECKED);
			List<InternationalRate> listInsert = new ArrayList<InternationalRate>();
			List<InternationalRate> listUpdate = new ArrayList<InternationalRate>();
			List<DailyCurrencyRate> list = JSONUtils.json2list(irh.getDataInfo(), DailyCurrencyRate.class);
			for(DailyCurrencyRate dcr : list) {
				InternationalRate ir = this.internationalRateDao.getByCurrency(dcr.getCurrencyCode());
				if(ir != null) {
					ir.setUpdatetime(irh.getUpdatetime());
					ir.setStatus(InternationalRateStatus.PUBLISH);
//					ir.setInternationalInfo(internationalInfo);//可空
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
//					ir.setInternationalInfo(internationalInfo);//可空
					ir.setCurrencyCode(dcr.getCurrencyCode());
					ir.setCurrencyName(dcr.getCurrencyName());
					ir.setBaseCurrency(dcr.getBaseCurrency());
					ir.setRealRate(BigDecimal.valueOf(Double.valueOf(dcr.getRealRate())));
					ir.setCheckRate(BigDecimal.valueOf(Double.valueOf(dcr.getBaseRate())));
					listInsert.add(ir);
				}
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("listInsert", listInsert);
			params.put("listUpdate", listUpdate);
			params.put("internationalRateHistory", irh);
			this.internationalRateDao.updateDaily(params);
		}
	}
	
	public static void main(String[] args) {
		String str = "JPY|USD|GBP|CAD|AUD|NZD|SGD|SAR|INR|PHP|MYR|HKD|TWD";
		String[] strArr = str.split("\\|");
		for(String s : strArr) {
			System.out.println(s);
		}
	}
}


/*
 * Free, Reliable Currency Converter API（免费账户，一天更新一次，一个月免费1000次，免费账户不支持https） 
 * Forex API: Realtime Forex Quotes（免费账户，每分钟更新一次，一天免费1000次，支持https） 
 * Foreign exchange rates and currency conversion JSON API (需要注册，有免费套餐，支持 https，Pricing Plans - Fixer) 
 * YQL Console（雅虎的汇率接口，最近一年服务不可用的概率挺高的） 
 * Open Exchange Rates(免费账户点击这里注册，每月1000次免费调用；收费用户最高可小时更新) 
 * 一般来说，法币汇率比较稳定，可以一天获取一次数据，如果你的应用需要依赖高实时性的，那就花些钱，或者是去外汇交易所拉去实时数据。
 */
 
