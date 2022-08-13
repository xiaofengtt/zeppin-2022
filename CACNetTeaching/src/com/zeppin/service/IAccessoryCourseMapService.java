/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IAccessoryCourseMapDao;
import com.zeppin.entiey.CourseCoursewareMap;

/**
 * @author Administrator
 * 
 */
public interface IAccessoryCourseMapService extends
	IBaseService<CourseCoursewareMap, Integer>
{
    IAccessoryCourseMapDao getIAccocessoryCourseMapDao();
}
