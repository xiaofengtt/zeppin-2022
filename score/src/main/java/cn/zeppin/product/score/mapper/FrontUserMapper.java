package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.util.MyMapper;

public interface FrontUserMapper extends MyMapper<FrontUser> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUser> getListByParams(Map<String, Object> params);
}