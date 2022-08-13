/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.ICourseScheduleDao;
import com.zeppin.entiey.Courseschedule;
import com.zeppin.service.ICourseScheduleService;

/**
 * @author Administrator
 * 
 */
public class CourseScheduleService extends BaseService<Courseschedule, Integer>
	implements ICourseScheduleService
{
    ICourseScheduleDao icsd;

    /**
     * @return the icsd
     */
    public ICourseScheduleDao getIcsd()
    {
	return icsd;
    }

    /**
     * @param icsd
     *            the icsd to set
     */
    public void setIcsd(ICourseScheduleDao icsd)
    {
	this.icsd = icsd;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.ICourseScheduleService#getICourseScheduleDao()
     */
    @Override
    public ICourseScheduleDao getICourseScheduleDao()
    {
	// TODO Auto-generated method stub
	return getIcsd();
    }
}
