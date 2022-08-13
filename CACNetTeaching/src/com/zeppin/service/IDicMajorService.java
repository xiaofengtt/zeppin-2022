/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IDicMajorDao;
import com.zeppin.entiey.DicMajor;

/**
 * @author Administrator
 * 
 */
public interface IDicMajorService extends IBaseService<DicMajor, Integer>
{
    IDicMajorDao getIDicMajorDao();

}
