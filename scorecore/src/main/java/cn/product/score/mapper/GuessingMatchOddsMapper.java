package cn.product.score.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.product.score.entity.GuessingMatchOdds;
import cn.product.score.util.MyMapper;

public interface GuessingMatchOddsMapper extends MyMapper<GuessingMatchOdds> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<GuessingMatchOdds> getListByParams(Map<String, Object> params);
	
	public void deleteByGuessingMatchType(@Param("guessingMatchType") String guessingMatchType);
}