/**
 * 
 */
package com.zeppin.serviceImp;

import com.zeppin.dao.IMessageDao;
import com.zeppin.entiey.Message;
import com.zeppin.service.IMessageService;

/**
 * @author sj
 * 
 */
public class MessageService extends BaseService<Message, Integer> implements
	IMessageService
{
    IMessageDao imd;

    /**
     * @return the imd
     */
    public IMessageDao getImd()
    {
	return imd;
    }

    /**
     * @param imd
     *            the imd to set
     */
    public void setImd(IMessageDao imd)
    {
	this.imd = imd;
    }

    /*
     * (non-Javadoc)
     * @see com.zeppin.service.IMessageService#getIMessageDao()
     */
    @Override
    public IMessageDao getIMessageDao()
    {
	// TODO Auto-generated method stub
	return getImd();
    }
}
