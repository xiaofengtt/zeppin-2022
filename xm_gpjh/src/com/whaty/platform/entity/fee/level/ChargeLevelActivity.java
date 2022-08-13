/**
 * 
 */
package com.whaty.platform.entity.fee.level;

import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public interface ChargeLevelActivity {
	
	public void setBatchChargeLevel(String siteId,String majorId,String edutypeId,String gradeId,Map chargeLevel);

	public void setBatchChargeLevel(String[] siteId,String[] majorId,String[] edutypeId,String[] gradeId,Map chargeLevel) throws PlatformException;
}
