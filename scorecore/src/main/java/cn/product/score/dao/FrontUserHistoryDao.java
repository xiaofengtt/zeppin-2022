package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserHistory;
import cn.product.score.vo.back.StatusCountVO;

public interface FrontUserHistoryDao extends IDao<FrontUserHistory>{
	
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
	public List<FrontUserHistory> getListByParams(Map<String, Object> params);
	
	/**
	 * 用户充值入库
	 * @param fuh
	 * @param type
	 * @return
	 */
	public void recharge(FrontUserHistory fuh, String type);
	
	/**
	 * 用户提现入库
	 * @param fuh
	 * @return
	 */
	public void withdraw(FrontUserHistory fuh);
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	public List<StatusCountVO> getStatusList(Map<String, Object> params);
}
