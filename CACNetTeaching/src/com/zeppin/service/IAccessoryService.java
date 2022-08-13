/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IAccessoryDao;
import com.zeppin.entiey.Accessory;

/**
 * @author Administrator
 * 
 */
public interface IAccessoryService extends IBaseService<Accessory, Integer>
{
    IAccessoryDao getAccessoryDao();

}
