/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IMessageSsoDao;
import com.zeppin.entiey.SsoMessageMap;

/**
 * @author sj
 * 
 */
public interface IMessageSsoService extends
	IBaseService<SsoMessageMap, Integer>
{
    IMessageSsoDao getIMessageSsoDao();
}
