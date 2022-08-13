/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IHomeworkDao;
import com.zeppin.entiey.Homework;

/**
 * @author Administrator
 * 
 */
public interface IHomeworkService extends IBaseService<Homework, Integer>
{
    IHomeworkDao getIHomeworkDao();
}
