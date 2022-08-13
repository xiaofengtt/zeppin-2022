/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IStudentDao;
import com.zeppin.entiey.Student;
import com.zeppin.service.IStudentService;

/**
 * @author Administrator
 * 
 */
public class StudentService extends BaseService<Student, Integer> implements
	IStudentService
{
    IStudentDao isd;

    /**
     * @return isd
     */
    public IStudentDao getIsd()
    {
	return isd;
    }

    /**
     * @param isd
     *            要设置的 isd
     */
    public void setIsd(IStudentDao isd)
    {
	this.isd = isd;
    }

    /*
     * （非 Javadoc）
     * @see com.zeppin.service.IStudentService#getStudentDao()
     */
    @Override
    public IStudentDao getIStudentDao()
    {
	// TODO 自动生成的方法存根
	return getIsd();
    }

}
