package cn.product.treasuremall.dao;

import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserHistory;

public interface AdminOffsetOrderDao extends IDao<AdminOffsetOrder> {
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
	public List<AdminOffsetOrder> getListByParams(Map<String, Object> params);
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getLeftCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<AdminOffsetOrder> getLeftListByParams(Map<String, Object> params);
	
	/**
	 * 审核入库
	 * @param aoo
	 * @param fua
	 * @param fuh
	 * @return
	 */
	public void check(AdminOffsetOrder aoo, FrontUserAccount fua, FrontUserHistory fuh);
	
	/**
	 * 邀新活动，充值返利上账
	 * @param params
	 */
	public void recommendTask(Map<String, Object> params);
}
