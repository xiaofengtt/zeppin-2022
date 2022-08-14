package cn.product.treasuremall.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.treasuremall.dao.PayNotifyInfoDao;
import cn.product.treasuremall.entity.PayNotifyInfo;
import cn.product.treasuremall.entity.PayNotifyInfo.PayNotifyInfoStatus;
import cn.product.treasuremall.mapper.PayNotifyInfoMapper;
import cn.product.treasuremall.util.Utlity;

/**
 */
@Component
public class PayNotifyInfoDaoImpl implements PayNotifyInfoDao{
	
	@Autowired
	private PayNotifyInfoMapper payNotifyInfoMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return payNotifyInfoMapper.getCountByParams(params);
	}
	
	@Override
	public List<PayNotifyInfo> getListByParams(Map<String, Object> params) {
		return payNotifyInfoMapper.getListByParams(params);
	}
	
    @Override
	public PayNotifyInfo get(String key) {
		return payNotifyInfoMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(PayNotifyInfo payNotifyInfo) {
		return payNotifyInfoMapper.insert(payNotifyInfo);
	}

	@Override
	public int delete(String key) {
		return payNotifyInfoMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int update(PayNotifyInfo payNotifyInfo) {
		return payNotifyInfoMapper.updateByPrimaryKey(payNotifyInfo);
	}

	@Override
	@Transactional
	public HashMap<String, Object> insertPayNotifyInfo(Map<String, Object> map, String type) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
			PayNotifyInfo rni = new PayNotifyInfo();
			rni.setUuid(UUID.randomUUID().toString());
			rni.setCreatetime(new Timestamp(System.currentTimeMillis()));
			rni.setPayType(type);
			rni.setSource(data);
			rni.setStatus(PayNotifyInfoStatus.UNPRO);
			String resultArr[] = data.split(",");
			String orderNum = resultArr[12];
			rni.setOrderNum(orderNum);
			this.payNotifyInfoMapper.insert(rni);
			LoggerFactory.getLogger(getClass()).info("成功");
		} else {
			LoggerFactory.getLogger(getClass()).info("失败");
			resultFlag = false;
			message = "数据错误";
		}
		result.put("result", resultFlag);
		result.put("message", message);
		return result;
	}

	@Override
	@Transactional
	public void updateAll(List<PayNotifyInfo> listUpdate) {
		if(listUpdate != null && listUpdate.size() > 0){
			for(PayNotifyInfo pni : listUpdate){
				this.payNotifyInfoMapper.updateByPrimaryKey(pni);
			}
		}		
	}
}
