package cn.product.worldmall.dao;

import java.util.List;
import java.util.Map;

import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.entity.InternationalInfoVersion;

public interface InternationalInfoDao extends IDao<InternationalInfo> {
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
	public List<InternationalInfo> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<InternationalInfo> getCountryListByParams();
	
	/**
	 * 递归获取各级地区名称
	 * @param uuid
	 * @return
	 */
	public List<String> getFullName(String uuid);

	/**
	 * 批量更新国家渠道版本信息（使缓存自动清理生效）
	 */
	public void batchUpdate(List<InternationalInfoVersion> iivList);
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public InternationalInfo getByCode(String code);
	
}
