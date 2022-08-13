/**
 * 
 */
package com.whaty.platform.entity.test;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 *
 */
public interface TestDataList {

	/**ËÑË÷¿¼ÊÔÅú´Î
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @throws PlatformException
	 */
	public List searchTestBatch(Page page, List searchproperty, List orderproperty) throws PlatformException;

	/**ËÑË÷¿¼ÊÔ³¡´Î
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @throws PlatformException
	 */
	public List searchTestSequence(Page page, List searchproperty, List orderproperty) throws PlatformException;

}
