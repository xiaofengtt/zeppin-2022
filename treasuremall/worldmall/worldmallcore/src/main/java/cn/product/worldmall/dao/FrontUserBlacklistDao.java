package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserBlacklist;

public interface FrontUserBlacklistDao extends IDao<FrontUserBlacklist>{
	
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
	public List<FrontUserBlacklist> getListByParams(Map<String, Object> params);
	
	/**
	 * 删除黑名单
	 * @param frontUserBlacklist
	 * @param fu
	 */
	public void delete(FrontUserBlacklist frontUserBlacklist, FrontUser fu);
}
