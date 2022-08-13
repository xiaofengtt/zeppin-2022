/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.ITeacherDao;
import com.zeppin.entiey.Teacher;

/**
 * @author Administrator
 * 
 */
public interface ITeachereService extends IBaseService<Teacher, Integer>
{
    ITeacherDao getITeacherDao();

}
