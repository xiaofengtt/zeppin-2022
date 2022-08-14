package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.InfoMatch;
import cn.product.score.util.MyMapper;

public interface InfoMatchMapper extends MyMapper<InfoMatch> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InfoMatch> getListByParams(Map<String, Object> params);
}