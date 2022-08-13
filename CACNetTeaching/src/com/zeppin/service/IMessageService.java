/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IMessageDao;
import com.zeppin.entiey.Message;

/**
 * @author sj
 * 
 */
public interface IMessageService extends IBaseService<Message, Integer>
{
    IMessageDao getIMessageDao();

}
