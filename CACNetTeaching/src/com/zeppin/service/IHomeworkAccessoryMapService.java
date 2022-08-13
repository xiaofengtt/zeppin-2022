/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IHomeworkAccessoryMapDao;
import com.zeppin.entiey.HomeworkAccessoryMap;

/**
 * @author Administrator
 * 
 */
public interface IHomeworkAccessoryMapService extends
	IBaseService<HomeworkAccessoryMap, Integer>
{
    IHomeworkAccessoryMapDao getiHomeworkAccessoryMapDao();
}
