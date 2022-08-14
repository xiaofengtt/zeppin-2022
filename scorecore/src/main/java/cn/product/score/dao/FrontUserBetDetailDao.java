package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserBetDetail;
import cn.product.score.vo.back.BetSumVO;

public interface FrontUserBetDetailDao extends IDao<FrontUserBetDetail>{
	
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
	public List<FrontUserBetDetail> getListByParams(Map<String, Object> params);
	
	/**
	 * 获取投注额
	 * @return
	 */
	public List<BetSumVO> getBetSumList(Map<String, Object> params);
}
