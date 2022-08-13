/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IAccessoryDao;
import com.zeppin.entiey.Accessory;
import com.zeppin.service.IAccessoryService;

/**
 * @author Administrator
 * 
 */
public class AccessroyService extends BaseService<Accessory, Integer> implements
	IAccessoryService
{
    public IAccessoryDao iaDao;

    /**
     * @return iaDao
     */
    public IAccessoryDao getIaDao()
    {
	return iaDao;
    }

    /**
     * @param iaDao
     *            要设置的 iaDao
     */
    public void setIaDao(IAccessoryDao iaDao)
    {
	this.iaDao = iaDao;
    }

    /*
     * （非 Javadoc）
     * @see com.zeppin.service.IAccessoryService#getAccessoryDao()
     */
    @Override
    public IAccessoryDao getAccessoryDao()
    {
	// TODO 自动生成的方法存根
	return getIaDao();
    }

}
