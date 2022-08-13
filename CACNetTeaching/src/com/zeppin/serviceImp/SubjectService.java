/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.ISubjectDao;
import com.zeppin.entiey.Subject;
import com.zeppin.service.ISubjectService;

/**
 * @author suijing
 * 
 */
public class SubjectService extends BaseService<Subject, Integer> implements
	ISubjectService
{
    ISubjectDao isd;

    /**
     * @return isd
     */
    public ISubjectDao getIsd()
    {
	return isd;
    }

    /**
     * @param isd
     *            要设置的 isd
     */
    public void setIsd(ISubjectDao isd)
    {
	this.isd = isd;
    }

    /*
     * （非 Javadoc）
     * @see com.zeppin.service.ISubjectService#getISubjectDao()
     */
    @Override
    public ISubjectDao getISubjectDao()
    {
	// TODO 自动生成的方法存根
	return getIsd();
    }

}
