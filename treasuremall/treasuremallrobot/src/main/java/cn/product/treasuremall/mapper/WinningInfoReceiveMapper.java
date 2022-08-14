package cn.product.treasuremall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.WinningInfoReceive;
import cn.product.treasuremall.util.MyMapper;

public interface WinningInfoReceiveMapper extends MyMapper<WinningInfoReceive> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<WinningInfoReceive> getListByParams(Map<String, Object> params);
}

