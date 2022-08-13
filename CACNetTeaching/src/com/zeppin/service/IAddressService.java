/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IAddressDao;
import com.zeppin.entiey.DicAddress;

/**
 * @author Administrator
 * 
 */
public interface IAddressService extends IBaseService<DicAddress, Integer>
{
    IAddressDao getIAddressDao();

}
