/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IEduSystemDao;
import com.zeppin.entiey.Edusystem;
import com.zeppin.service.IEduSystemService;

/**
 * @author Administrator
 * 
 */
public class EduSystemService extends BaseService<Edusystem, Integer> implements
	IEduSystemService
{
    IEduSystemDao ied;

    /**
     * @return ied
     */
    public IEduSystemDao getIed()
    {
	return ied;
    }

    /**
     * @param ied
     *            要设置的 ied
     */
    public void setIed(IEduSystemDao ied)
    {
	this.ied = ied;
    }

    /*
     * （非 Javadoc）
     * @see com.zeppin.service.IEduSystemService#getiEduSystemDao()
     */
    @Override
    public IEduSystemDao getiEduSystemDao()
    {
	// TODO 自动生成的方法存根
	return getIed();
    }

}
