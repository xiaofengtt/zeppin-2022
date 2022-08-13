/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IStudentGroupDao;
import com.zeppin.entiey.Studentgrou;

/**
 * @author Administrator
 * 
 */
public interface IStudentGroupService extends
	IBaseService<Studentgrou, Integer>
{
    IStudentGroupDao getIStudentGroupDao();
}
