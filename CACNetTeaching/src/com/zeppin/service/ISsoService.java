/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.ISsoDao;
import com.zeppin.entiey.Sso;

/**
 * @author Administrator
 * 
 */
public interface ISsoService extends IBaseService<Sso, Integer>
{
    ISsoDao getISsoDao();
}
