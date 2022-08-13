/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IAccessoryCourseMapDao;
import com.zeppin.entiey.CourseCoursewareMap;
import com.zeppin.service.IAccessoryCourseMapService;

/**
 * @author Administrator
 * 
 */
public class AccessoryCourseMapService extends
	BaseService<CourseCoursewareMap, Integer> implements
	IAccessoryCourseMapService
{
    IAccessoryCourseMapDao iacmd;

    /**
     * @return the iacmd
     */
    public IAccessoryCourseMapDao getIacmd()
    {
	return iacmd;
    }

    /**
     * @param iacmd
     *            the iacmd to set
     */
    public void setIacmd(IAccessoryCourseMapDao iacmd)
    {
	this.iacmd = iacmd;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zeppin.service.IAccocessoryCourseMapService#getIAccocessoryCourseMapDao
     * ()
     */
    @Override
    public IAccessoryCourseMapDao getIAccocessoryCourseMapDao()
    {
	// TODO Auto-generated method stub
	return getIacmd();
    }
}
