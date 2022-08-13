/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IClassMapDao;
import com.zeppin.entiey.Classmap;

/**
 * @author Administrator
 * 
 */
public interface IClassmapService extends IBaseService<Classmap, Integer>
{
    IClassMapDao getiClassMapDao();

}
