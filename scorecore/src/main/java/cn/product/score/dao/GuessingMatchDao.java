package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.GuessingMatch;
import cn.product.score.entity.GuessingMatchOdds;
import cn.product.score.entity.GuessingMatchType;
import cn.product.score.vo.back.StatusCountVO;

public interface GuessingMatchDao extends IDao<GuessingMatch>{
	
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
	public List<GuessingMatch> getListByParams(Map<String, Object> params);
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	public List<StatusCountVO> getStatusList();
	
	/**
	 * 删除竞猜赛事
	 * @param guessingMatch
	 */
	public void deleteGuessingMatch(GuessingMatch guessingMatch);
	
	/**
	 * 获取结束投注的比赛
	 */
	public List<GuessingMatch> getWaitingMatchList();
	
	/**
	 * 更新赛事及赛事类型
	 * @param guessingMatch
	 * @param gmtList
	 */
	public void updateGuessingMatch(GuessingMatch guessingMatch, List<GuessingMatchType> gmtList);
	
	/**
	 * 结算竞猜赛事
	 * @param guessingMatch
	 * @param gmtList
	 * @param gmoList
	 */
	public void finishGuessingMatch(GuessingMatch guessingMatch, List<GuessingMatchType> gmtList, List<GuessingMatchOdds> gmoList);
}
