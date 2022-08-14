package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.InfoMatch;
import cn.zeppin.product.score.util.MyMapper;

public interface InfoMatchMapper extends MyMapper<InfoMatch> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<InfoMatch> getListByParams(Map<String, Object> params);
}