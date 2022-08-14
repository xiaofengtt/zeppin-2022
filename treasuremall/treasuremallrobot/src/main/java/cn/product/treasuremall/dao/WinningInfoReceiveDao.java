package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.WinningInfoReceive;

public interface WinningInfoReceiveDao extends IDao<WinningInfoReceive>{
	
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
	public List<WinningInfoReceive> getListByParams(Map<String, Object> params);
	
	/**
	 * 实物认领
	 * @param fua
	 * @param fuh
	 * @param wir
	 */
	public void insertReceive(FrontUserAccount fua, FrontUserHistory fuh, WinningInfoReceive wir, WinningInfo wi);
	
	/**
	 * 重置认领
	 * @param wir
	 * @param wi
	 */
	public void deleteReceive(WinningInfoReceive wir, WinningInfo wi);
}
