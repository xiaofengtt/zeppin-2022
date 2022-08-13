/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IDicTechnicalTitleDao;
import com.zeppin.entiey.DicTechnicalTiltle;
import com.zeppin.service.IDicTechnicalTitleService;

/**
 * @author Administrator
 * 
 */
public class DicTechnicalTitleService extends
	BaseService<DicTechnicalTiltle, Integer> implements
	IDicTechnicalTitleService
{
    IDicTechnicalTitleDao ittd;

    /**
     * @return ittd
     */
    public IDicTechnicalTitleDao getIttd()
    {
	return ittd;
    }

    /**
     * @param ittd
     *            要设置的 ittd
     */
    public void setIttd(IDicTechnicalTitleDao ittd)
    {
	this.ittd = ittd;
    }

    /*
     * （非 Javadoc）
     * @see
     * com.zeppin.service.IDicTechnicalTitleService#getIDicTechnicalTitleDao()
     */
    @Override
    public IDicTechnicalTitleDao getIDicTechnicalTitleDao()
    {
	// TODO 自动生成的方法存根
	return getIttd();
    }

}
