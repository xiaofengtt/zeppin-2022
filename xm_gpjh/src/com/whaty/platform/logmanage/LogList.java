/**
 * 
 */
package com.whaty.platform.logmanage;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * @author wq
 * 
 */
public interface LogList {

	/**
	 * @param searchproperty
	 * @param orderporperty
	 * @return
	 */
	public abstract int searchLogEntitiesNum(List searchproperty,
			boolean history);

	/**
	 * @param page
	 * @param searchproperty
	 * @param orderporperty
	 * @return
	 */
	public abstract List searchLogEntities(Page page, List searchproperty,
			List orderporperty, boolean history);

	/**
	 * @param searchproperty
	 */
	public abstract int backUpLogEntities(List searchproperty);
	
	
	public abstract int addLogEntities(String id, String userid,
			String operateTime, String behavior,
			String status, String notes, String logtype, String priority)throws PlatformException;
}
