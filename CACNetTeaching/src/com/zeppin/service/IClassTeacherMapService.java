/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IClassTeacherMapDao;
import com.zeppin.entiey.CoursedesignTeacherMap;

/**
 * @author Administrator
 * 
 */
public interface IClassTeacherMapService extends
	IBaseService<CoursedesignTeacherMap, Integer>
{
    IClassTeacherMapDao getIClassTeacherMapDao();
}
