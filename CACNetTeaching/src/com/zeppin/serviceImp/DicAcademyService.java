/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IDicAcademyDao;
import com.zeppin.entiey.DicAcademy;
import com.zeppin.service.IDicAcademyService;

/**
 * @author suijing
 * 
 */
public class DicAcademyService extends BaseService<DicAcademy, Integer>
	implements IDicAcademyService
{
    IDicAcademyDao iad;

    /**
     * @return iad
     */
    public IDicAcademyDao getIad()
    {
	return iad;
    }

    /**
     * @param iad
     *            要设置的 iad
     */
    public void setIad(IDicAcademyDao iad)
    {
	this.iad = iad;
    }

    /*
     * （非 Javadoc）
     * @see com.zeppin.service.IDicAcademyService#getIDicAcademyDao()
     */
    @Override
    public IDicAcademyDao getIDicAcademyDao()
    {
	// TODO 自动生成的方法存根
	return getIad();
    }

}
