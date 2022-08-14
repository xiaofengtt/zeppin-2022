/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.IBaseService;

/**
 * 
 * 
 * @author Clark.R
 * @since 2016年11月26日
 */
public interface IBkAreaService extends IBaseService<BkArea, String> {
	
    /**
     * 通过主键得到对象
     * @param uuid
     * @return
     */
	public BkArea get(String uuid);

	/**
	 * 添加省份地区
	 * @param bkController
	 * @return
	 */
	public BkArea insert(BkArea area);
	
	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return
	 */
	public Integer getCount(Map<String, String> inputParams);
	
	/**
	 * 根据参数查询结果列表
	 * @param inputParams
	 * @return
	 */
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass);

	
	/**
	 * 获取所有省份地区信息
	 * @param resultClass
	 * @return
	 */
	public List<Entity> getAll(Class<? extends Entity> resultClass);
	
	/**
	 * 获取地区全称
	 * @param uuid
	 * @return
	 */
	public List<String> getFullName(String uuid);
	
}
