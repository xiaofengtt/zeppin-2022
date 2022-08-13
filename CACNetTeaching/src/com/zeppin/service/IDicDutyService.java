/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IDicDutyDao;
import com.zeppin.entiey.DicDuty;

/**
 * @author Administrator
 * 
 */
public interface IDicDutyService extends IBaseService<DicDuty, Integer>
{
    IDicDutyDao getiDicDutyDao();
}
