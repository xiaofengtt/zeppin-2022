package cn.product.worldmall.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.dao.InternationalRateDao;
import cn.product.worldmall.dao.SystemParamDao;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.InternationalInfo.InternationalInfoStatus;
import cn.product.worldmall.entity.SystemParam.SystemParamKey;
import cn.product.worldmall.entity.InternationalRate;
import cn.product.worldmall.entity.SystemParam;
import cn.product.worldmall.service.front.FrontAreaService;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.front.InternationalInfoVO;
import cn.product.worldmall.vo.front.InternationalRateVO;

@Service("frontAreaService")
public class FrontAreaServiceImpl implements FrontAreaService{
	
	
	@Autowired
	private InternationalInfoDao areaDao;
	
	@Autowired
	private InternationalRateDao internationalRateDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String level = paramsMap.get("level") == null ? "" : paramsMap.get("level").toString();
		String pid = paramsMap.get("pid") == null ? "" : paramsMap.get("pid").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("level", level);
		searchMap.put("parent", pid);
		searchMap.put("status", InternationalInfoStatus.NORMAL);
		
		List<InternationalInfo> list = areaDao.getListByParams(searchMap);
		List<InternationalInfoVO> listvo = new ArrayList<InternationalInfoVO>();
		if(list != null && list.size() > 0) {
			for(InternationalInfo ii : list) {
				InternationalInfoVO iivo = new InternationalInfoVO(ii);
				listvo.add(iivo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}


	@Override
	public void country(InputParams params, DataResult<Object> result) {
		List<InternationalInfo> list = areaDao.getCountryListByParams();
		List<InternationalInfoVO> listvo = new ArrayList<InternationalInfoVO>();
		if(list != null && list.size() > 0) {
			for(InternationalInfo ii : list) {
				InternationalInfoVO iivo = new InternationalInfoVO(ii);
				listvo.add(iivo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}


	@Override
	public void currency(InputParams params, DataResult<Object> result) {
		
		//国际货币及汇率
		List<InternationalRate> listRate = internationalRateDao.getCurrencyListByParams();
		List<InternationalRateVO> listRatevo = new ArrayList<InternationalRateVO>();
		if(listRate != null && listRate.size() > 0){
			//获取货币符号
			//获取货币和符号
			SystemParam spcurrency = this.systemParamDao.get(SystemParamKey.INTERNATIONAL_CURRENCIES_SYMBOL);
			String symbol = "";
			
			for(InternationalRate ir : listRate) {
				InternationalRateVO irvo = new InternationalRateVO(ir); 
				if(spcurrency != null) {
					Map<String, Object> currencies = JSONUtils.json2map(spcurrency.getParamValue());
					if(currencies != null && !currencies.isEmpty()) {
						symbol = currencies.get(ir.getCurrencyCode()) == null ? "" : currencies.get(ir.getCurrencyCode()).toString();
					}
				}
				if(Utlity.checkStringNull(symbol)) {
					symbol = ir.getCurrencyCode();
				}
				irvo.setSymbol(symbol);
				listRatevo.add(irvo);
			}
		}
					
		result.setData(listRatevo);
		result.setStatus(ResultStatusType.SUCCESS);		
	}
	
}
