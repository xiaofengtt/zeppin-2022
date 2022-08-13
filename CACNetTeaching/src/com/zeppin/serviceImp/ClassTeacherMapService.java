/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IClassTeacherMapDao;
import com.zeppin.entiey.CoursedesignTeacherMap;
import com.zeppin.service.IClassTeacherMapService;

/**
 * @author Administrator
 * 
 */
public class ClassTeacherMapService extends
	BaseService<CoursedesignTeacherMap, Integer> implements
	IClassTeacherMapService
{
    IClassTeacherMapDao ictm;

    /**
     * @return the ictm
     */
    public IClassTeacherMapDao getIctm()
    {
	return ictm;
    }

    /**
     * @param ictm
     *            the ictm to set
     */
    public void setIctm(IClassTeacherMapDao ictm)
    {
	this.ictm = ictm;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.IClassTeacherMapService#getIClassTeacherMapDao()
     */
    @Override
    public IClassTeacherMapDao getIClassTeacherMapDao()
    {
	// TODO Auto-generated method stub
	return getIctm();
    }
}
