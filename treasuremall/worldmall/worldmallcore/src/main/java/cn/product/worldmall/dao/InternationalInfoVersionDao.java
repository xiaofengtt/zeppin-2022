package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.InternationalInfoVersion;

public interface InternationalInfoVersionDao extends IDao<InternationalInfoVersion> {
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
	public List<InternationalInfoVersion> getListByParams(Map<String, Object> params);
	
	/**
	 * 条件获取数据
	 * @param channel
	 * @param version
	 * @return
	 */
	public InternationalInfoVersion getByInternationalInfoVersion(String code, String channel, String version);

	/**
	 * 批量更新国家信息（使缓存自动清理生效）
	 */
	public void batchUpdate(List<InternationalInfo> iivList);
	
}
