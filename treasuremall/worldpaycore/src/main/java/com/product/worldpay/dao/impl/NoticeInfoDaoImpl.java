package com.product.worldpay.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.product.worldpay.dao.NoticeInfoDao;
import com.product.worldpay.entity.NoticeInfo;
import com.product.worldpay.mapper.NoticeInfoMapper;

/**
 */
@Component
public class NoticeInfoDaoImpl implements NoticeInfoDao{
	
	@Autowired
	private NoticeInfoMapper NoticeInfoMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return NoticeInfoMapper.getCountByParams(params);
	}
	
	@Override
	public List<NoticeInfo> getListByParams(Map<String, Object> params) {
		return NoticeInfoMapper.getListByParams(params);
	}
	
    @Override
	public NoticeInfo get(String key) {
		return NoticeInfoMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(NoticeInfo NoticeInfo) {
		return NoticeInfoMapper.insert(NoticeInfo);
	}

	@Override
	public int delete(String key) {
		return NoticeInfoMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int update(NoticeInfo NoticeInfo) {
		return NoticeInfoMapper.updateByPrimaryKey(NoticeInfo);
	}
}
