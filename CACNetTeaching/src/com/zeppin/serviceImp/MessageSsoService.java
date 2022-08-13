/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IMessageSsoDao;
import com.zeppin.entiey.SsoMessageMap;
import com.zeppin.service.IMessageSsoService;

/**
 * @author sj
 * 
 */
public class MessageSsoService extends BaseService<SsoMessageMap, Integer>
	implements IMessageSsoService
{
    IMessageSsoDao imsdD;

    /**
     * @return the imsdD
     */
    public IMessageSsoDao getImsdD()
    {
	return imsdD;
    }

    /**
     * @param imsdD
     *            the imsdD to set
     */
    public void setImsdD(IMessageSsoDao imsdD)
    {
	this.imsdD = imsdD;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.IMessageSsoService#getIMessageSsoDao()
     */
    @Override
    public IMessageSsoDao getIMessageSsoDao()
    {
	// TODO Auto-generated method stub
	return getImsdD();
    }
}
