package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.FrontUserBetDetail;
import cn.zeppin.product.score.vo.back.BetSumVO;

public interface FrontUserBetDetailService extends IService<FrontUserBetDetail>{
	
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
