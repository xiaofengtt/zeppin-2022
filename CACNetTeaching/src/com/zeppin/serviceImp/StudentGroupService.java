/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IStudentGroupDao;
import com.zeppin.entiey.Studentgrou;
import com.zeppin.service.IStudentGroupService;

/**
 * @author Administrator
 * 
 */
public class StudentGroupService extends BaseService<Studentgrou, Integer>
	implements IStudentGroupService
{
    IStudentGroupDao isgd;

    /**
     * @return the isgd
     */
    public IStudentGroupDao getIsgd()
    {
	return isgd;
    }

    /**
     * @param isgd
     *            the isgd to set
     */
    public void setIsgd(IStudentGroupDao isgd)
    {
	this.isgd = isgd;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.IStudentGroupService#getIStudentGroupDao()
     */
    @Override
    public IStudentGroupDao getIStudentGroupDao()
    {
	// TODO Auto-generated method stub
	return getIsgd();
    }
}
