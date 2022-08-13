/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.ISgroupMapDao;
import com.zeppin.entiey.GroupStudenMap;
import com.zeppin.service.ISgroupMapService;

/**
 * @author Administrator
 * 
 */
public class SgroupMapService extends BaseService<GroupStudenMap, Integer>
	implements ISgroupMapService
{
    ISgroupMapDao ismdDao;

    /**
     * @return the ismdDao
     */
    public ISgroupMapDao getIsmdDao()
    {
	return ismdDao;
    }

    /**
     * @param ismdDao
     *            the ismdDao to set
     */
    public void setIsmdDao(ISgroupMapDao ismdDao)
    {
	this.ismdDao = ismdDao;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.ISgroupMapService#getISgroupMapDao()
     */
    @Override
    public ISgroupMapDao getISgroupMapDao()
    {
	// TODO Auto-generated method stub
	return getIsmdDao();
    }
}
