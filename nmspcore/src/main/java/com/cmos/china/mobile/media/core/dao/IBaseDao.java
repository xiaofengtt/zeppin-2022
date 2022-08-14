package com.cmos.china.mobile.media.core.dao;

import java.util.List;
import java.util.Map;

/**
 * Dao接口基类
 */
public interface IBaseDao {
	/**
	 * 根据Id获取对象
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param id
	 *            主键
	 * @return Object对象
	 */
	public Object queryForObject(String sqlId, String id);

	/**
	 * 根据Id获取对象
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param id
	 *            主键
	 * @param cls
	 *            返回的对象Class
	 * @return cls对应的类
	 */
	public <T> T queryForObject(String sqlId, String id, Class<T> cls);

	/**
	 * 根据条件获取对象
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @return
	 */
	public Object queryForObject(String sqlId, Map<String, Object> params);

	/**
	 * 根据条件获取对象
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @param cls
	 *            返回的对象Class
	 * @return cls对应的类
	 */
	public <T> T queryForObject(String sqlId, Map<String, Object> params,
			Class<T> cls);

	/**
	 * 获取数据总条�?
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @return 条数
	 */
	public int getTotalCount(String sqlId, Map<String, Object> params);

	/**
	 * 查询列表
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @param cls
	 *            返回的对象Class
	 * @return 列表<cls对应的类>
	 */
	public <T> List<T> queryForList(String sqlId, Map<String, Object> params,
			Class<T> cls);

	/**
	 * 查询列表
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param params
	 *            参数
	 * @return 列表
	 */
	public List<Map<String, Object>> queryForList(String sqlId,
			Map<String, Object> param);

	/**
	 * 修改数据
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param object
	 *            对象
	 * @return 修改的行�?
	 */
	public int update(String sqlId, Object object);

	/**
	 * 插入数据
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param object
	 *            待插入的对象
	 * @return 主键
	 */
	public int insert(String sqlId, Object object);

	/**
	 * 删除数据
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param id
	 *            主键
	 * @return 主键
	 */
	public int delete(String sqlId, String id);

	/**
	 * 删除数据
	 * 
	 * @param sqlId
	 *            脚本编号
	 * @param map
	 *            待删除的对象
	 * @return 主键
	 */
	public int delete(String sqlId, Map<String, Object> map);

}