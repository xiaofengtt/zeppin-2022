/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IHomeworkAccessoryMapDao;
import com.zeppin.entiey.HomeworkAccessoryMap;
import com.zeppin.service.IHomeworkAccessoryMapService;

/**
 * @author Administrator
 * 
 */
public class HomeworkAccessoryMapService extends
	BaseService<HomeworkAccessoryMap, Integer> implements
	IHomeworkAccessoryMapService
{
    IHomeworkAccessoryMapDao ihamDao;

    /**
     * @return the ihamDao
     */
    public IHomeworkAccessoryMapDao getIhamDao()
    {
	return ihamDao;
    }

    /**
     * @param ihamDao
     *            the ihamDao to set
     */
    public void setIhamDao(IHomeworkAccessoryMapDao ihamDao)
    {
	this.ihamDao = ihamDao;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.zeppin.service.IHomeworAccessoryMapService#getiHomeworAccessoryMapDao
     * ()
     */
    @Override
    public IHomeworkAccessoryMapDao getiHomeworkAccessoryMapDao()
    {
	// TODO Auto-generated method stub
	return getIhamDao();
    }
}
