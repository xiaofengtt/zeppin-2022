/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IDicMajorDao;
import com.zeppin.entiey.DicMajor;
import com.zeppin.service.IDicMajorService;

/**
 * @author Administrator
 * 
 */
public class DicMajorService extends BaseService<DicMajor, Integer> implements
	IDicMajorService
{
    IDicMajorDao imd;

    /**
     * @return imd
     */
    public IDicMajorDao getImd()
    {
	return imd;
    }

    /**
     * @param imd
     *            要设置的 imd
     */
    public void setImd(IDicMajorDao imd)
    {
	this.imd = imd;
    }

    /*
     * （非 Javadoc）
     * @see com.zeppin.service.IDicMajorService#getIDicMajorDao()
     */
    @Override
    public IDicMajorDao getIDicMajorDao()
    {
	// TODO 自动生成的方法存根
	return getImd();
    }
}
