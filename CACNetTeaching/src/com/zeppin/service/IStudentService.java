/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IStudentDao;
import com.zeppin.entiey.Student;

/**
 * @author Administrator
 * 
 */
public interface IStudentService extends IBaseService<Student, Integer>
{
    IStudentDao getIStudentDao();
}
