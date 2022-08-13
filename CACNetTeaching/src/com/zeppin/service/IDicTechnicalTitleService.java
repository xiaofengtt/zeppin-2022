/**
 * 
 */
package com.zeppin.service;

import com.zeppin.dao.IDicTechnicalTitleDao;
import com.zeppin.entiey.DicTechnicalTiltle;

/**
 * @author suijing
 * 
 */
public interface IDicTechnicalTitleService extends
	IBaseService<DicTechnicalTiltle, Integer>
{
    IDicTechnicalTitleDao getIDicTechnicalTitleDao();
}
