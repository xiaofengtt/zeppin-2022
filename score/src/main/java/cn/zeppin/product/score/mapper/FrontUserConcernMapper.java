package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.FrontUserConcern;
import cn.zeppin.product.score.util.MyMapper;

public interface FrontUserConcernMapper extends MyMapper<FrontUserConcern> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserConcern> getListByParams(Map<String, Object> params);
}