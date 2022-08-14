package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Version;
import cn.zeppin.product.score.util.MyMapper;

public interface VersionMapper extends MyMapper<Version> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<Version> getListByParams(Map<String, Object> params);
}

