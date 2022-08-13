/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IDicAcademyDao;
import com.zeppin.entiey.DicAcademy;

/**
 * @author Administrator
 * 
 */
public interface IDicAcademyService extends IBaseService<DicAcademy, Integer>
{
    IDicAcademyDao getIDicAcademyDao();
}
