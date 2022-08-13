/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.ISgroupMapDao;
import com.zeppin.entiey.GroupStudenMap;

/**
 * @author Administrator
 * 
 */
public interface ISgroupMapService extends
	IBaseService<GroupStudenMap, Integer>
{
    ISgroupMapDao getISgroupMapDao();
}
