/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IAddressDao;
import com.zeppin.entiey.DicAddress;
import com.zeppin.service.IAddressService;

/**
 * @author suijing
 * 
 */
public class AddressService extends BaseService<DicAddress, Integer> implements
	IAddressService
{
    IAddressDao iAddressDao;

    /**
     * @return the iAddressDao
     */
    public IAddressDao getiAddressDao()
    {
	return iAddressDao;
    }

    /**
     * @param iAddressDao
     *            the iAddressDao to set
     */
    public void setiAddressDao(IAddressDao iAddressDao)
    {
	this.iAddressDao = iAddressDao;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.IAddressService#getIAddressDao()
     */
    @Override
    public IAddressDao getIAddressDao()
    {
	// TODO Auto-generated method stub
	return getiAddressDao();
    }

}
