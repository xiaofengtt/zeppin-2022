/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IDicDutyDao;
import com.zeppin.entiey.DicDuty;
import com.zeppin.service.IDicDutyService;

/**
 * @author Administrator
 * 
 */
public class DicDutyService extends BaseService<DicDuty, Integer> implements
	IDicDutyService
{

    IDicDutyDao idd;

    /**
     * @return idd
     */
    public IDicDutyDao getIdd()
    {
	return idd;
    }

    /**
     * @param idd
     *            要设置的 idd
     */
    public void setIdd(IDicDutyDao idd)
    {
	this.idd = idd;
    }

    /*
     * （非 Javadoc）
     * @see com.zeppin.service.IDicDutyService#getiDicDutyDao()
     */
    @Override
    public IDicDutyDao getiDicDutyDao()
    {
	// TODO 自动生成的方法存根
	return getIdd();
    }

}
