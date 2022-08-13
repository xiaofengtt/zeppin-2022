/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.ICoursePlanDao;
import com.zeppin.entiey.Coursedesign;
import com.zeppin.service.ICoursePlanService;

/**
 * @author Administrator
 * 
 */
public class CoursePlanService extends BaseService<Coursedesign, Integer>
	implements ICoursePlanService
{
    ICoursePlanDao ipd;

    /**
     * @return the ipd
     */
    public ICoursePlanDao getIpd()
    {
	return ipd;
    }

    /**
     * @param ipd
     *            the ipd to set
     */
    public void setIpd(ICoursePlanDao ipd)
    {
	this.ipd = ipd;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.ICoursePlanService#getICoursePlanDao()
     */
    @Override
    public ICoursePlanDao getICoursePlanDao()
    {
	// TODO Auto-generated method stub
	return getIpd();
    }

}
