/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IClassMapDao;
import com.zeppin.entiey.Classmap;
import com.zeppin.service.IClassmapService;

/**
 * @author Administrator
 * 
 */
public class ClassMapService extends BaseService<Classmap, Integer> implements
	IClassmapService
{
    IClassMapDao icm;

    /**
     * @return the icm
     */
    public IClassMapDao getIcm()
    {
	return icm;
    }

    /**
     * @param icm
     *            the icm to set
     */
    public void setIcm(IClassMapDao icm)
    {
	this.icm = icm;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.IClassmapService#getiClassMapDao()
     */
    @Override
    public IClassMapDao getiClassMapDao()
    {
	// TODO Auto-generated method stub
	return getIcm();
    }
}
