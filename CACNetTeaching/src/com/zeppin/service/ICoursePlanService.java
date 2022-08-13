/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.ICoursePlanDao;
import com.zeppin.entiey.Coursedesign;

/**
 * @author Administrator
 * 
 */
public interface ICoursePlanService extends IBaseService<Coursedesign, Integer>
{
    ICoursePlanDao getICoursePlanDao();
}
