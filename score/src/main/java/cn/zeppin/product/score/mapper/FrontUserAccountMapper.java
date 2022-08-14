package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.FrontUserAccount;
import cn.zeppin.product.score.util.MyMapper;

public interface FrontUserAccountMapper extends MyMapper<FrontUserAccount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserAccount> getListByParams(Map<String, Object> params);
	
	public FrontUserAccount getByFrontUser(String frontUser);
}