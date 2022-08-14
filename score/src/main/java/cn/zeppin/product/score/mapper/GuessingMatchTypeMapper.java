package cn.zeppin.product.score.mapper;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.GuessingMatchType;
import cn.zeppin.product.score.util.MyMapper;
import cn.zeppin.product.score.vo.front.CategoryCountVO;

public interface GuessingMatchTypeMapper extends MyMapper<GuessingMatchType> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<GuessingMatchType> getListByParams(Map<String, Object> params);
	
	public List<CategoryCountVO> getCategoryList(Map<String, Object> params);
	
	public List<GuessingMatchType> getWaitingMatchTypeList();
}