/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IHomeworkDao;
import com.zeppin.entiey.Homework;
import com.zeppin.service.IHomeworkService;

/**
 * @author Administrator
 * 
 */
public class HomewrokService extends BaseService<Homework, Integer> implements
	IHomeworkService
{
    IHomeworkDao ihd;

    /**
     * @return the ihd
     */
    public IHomeworkDao getIhd()
    {
	return ihd;
    }

    /**
     * @param ihd
     *            the ihd to set
     */
    public void setIhd(IHomeworkDao ihd)
    {
	this.ihd = ihd;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.IHomeworkService#getIHomeworkDao()
     */
    @Override
    public IHomeworkDao getIHomeworkDao()
    {
	// TODO Auto-generated method stub
	return getIhd();
    }

}
