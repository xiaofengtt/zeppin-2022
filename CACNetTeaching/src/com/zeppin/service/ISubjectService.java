/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.ISubjectDao;
import com.zeppin.entiey.Subject;

/**
 * @author suijing
 * 
 */
public interface ISubjectService extends IBaseService<Subject, Integer>
{
    ISubjectDao getISubjectDao();
}
