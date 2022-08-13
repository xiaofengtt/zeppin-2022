/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.ICourseScheduleDao;
import com.zeppin.entiey.Courseschedule;

/**
 * @author Administrator
 * 
 */
public interface ICourseScheduleService extends
	IBaseService<Courseschedule, Integer>
{
    ICourseScheduleDao getICourseScheduleDao();
}
