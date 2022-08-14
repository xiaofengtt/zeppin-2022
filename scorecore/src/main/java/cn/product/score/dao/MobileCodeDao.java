package cn.product.score.dao;

import java.util.List;
import java.util.Map;

import cn.product.score.entity.MobileCode;

/**
 * 
 */
public interface MobileCodeDao extends IDao<MobileCode> {
	
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
	public List<MobileCode> getListByParams(Map<String, Object> params);
	
	/**
	 * 插入
	 * @param mc
	 */
	public void insertMobileCode(MobileCode mc);
}
