/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.ISsoDao;
import com.zeppin.entiey.Sso;
import com.zeppin.service.ISsoService;

/**
 * @author Administrator
 * 
 */
public class SsoService extends BaseService<Sso, Integer> implements
	ISsoService
{
    ISsoDao isd;

    /**
     * @return the isd
     */
    public ISsoDao getIsd()
    {
	return isd;
    }

    /**
     * @param isd
     *            the isd to set
     */
    public void setIsd(ISsoDao isd)
    {
	this.isd = isd;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.ISsoService#getISsoDao()
     */
    @Override
    public ISsoDao getISsoDao()
    {
	// TODO Auto-generated method stub
	return getIsd();
    }

}
