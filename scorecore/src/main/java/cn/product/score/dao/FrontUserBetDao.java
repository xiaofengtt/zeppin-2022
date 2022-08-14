package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserAccount;
import cn.product.score.entity.FrontUserBet;
import cn.product.score.entity.FrontUserBetDetail;
import cn.product.score.entity.FrontUserHistory;
import cn.product.score.vo.back.StatusCountVO;

public interface FrontUserBetDao extends IDao<FrontUserBet>{
	
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
	public List<FrontUserBet> getListByParams(Map<String, Object> params);
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	public List<StatusCountVO> getStatusList();
	
	/**
	 * 确认投注出票
	 * @param frontUserBet
	 */
	public void comfirmFrontUserBet(FrontUserBet frontUserBet);
	
	/**
	 * 投注退款
	 * @param frontUserBet
	 */
	public void refundFrontUserBet(FrontUserBet frontUserBet);
	
	/**
	 * 竞猜数据入库
	 * @param fua
	 * @param fub
	 * @param listFubd
	 */
	public void bet(FrontUserAccount fua, FrontUserBet fub, List<FrontUserBetDetail> listFubd);
	
	/**
	 * 开奖入库
	 * @param fua
	 * @param fub
	 * @param fuh
	 */
	public void lottery(FrontUserAccount fua, FrontUserBet fub, FrontUserHistory fuh);
}
