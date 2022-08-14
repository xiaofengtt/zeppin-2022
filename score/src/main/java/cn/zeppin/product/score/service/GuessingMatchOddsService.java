package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.vo.back.GuessingMatchOddsChangeVO;

public interface GuessingMatchOddsService extends IService<GuessingMatchOdds>{
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<GuessingMatchOdds> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据竞猜类型删除赔率
	 * @param guessingMatchType
	 */
	public void deleteByGuessingMatchType(String guessingMatchType);
	
	/**
	 * 赔率监控修改赔率
	 * @param changevoList
	 */
	public void updateOdds(List<GuessingMatchOddsChangeVO> changevoList);
}
