package cn.product.worldmall.mapper;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.util.MyMapper;

public interface FrontUserAccountMapper extends MyMapper<FrontUserAccount> {
	
	public Integer getCountByParams(Map<String, Object> params);
	
	public List<FrontUserAccount> getListByParams(Map<String, Object> params);
	
	/**
	 * 批量更新
	 */
	public void updateStatus(Map<String, Object> params);
	
	/**
	 * 根据ID更新对应字段信息
	 * @param frontUser
	 * @param params
	 */
	public void updateInfo(Map<String, Object> params);
}