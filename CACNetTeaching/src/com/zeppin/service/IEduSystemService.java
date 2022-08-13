/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IEduSystemDao;
import com.zeppin.entiey.Edusystem;

/**
 * @author suijing
 * 
 */
public interface IEduSystemService extends IBaseService<Edusystem, Integer>
{
    IEduSystemDao getiEduSystemDao();

}
