/**  
* @Title: PeActualBudgetService.java
* @Package com.zeppin.service
* @author jiangfei  
*/
package com.zeppin.service;

import com.zeppin.model.PeActualBudgetNewTwo;
import com.zeppin.model.PeActualBudgetNewTwoDetail;

/**
 * @author Administrator
 *
 */
public interface PeActualBudgetNewTwoService extends BaseService<PeActualBudgetNewTwo,String> {
	
	public int addAndUpdate(boolean isadd, PeActualBudgetNewTwoDetail peFeeBudgetNewTwoDetail, PeActualBudgetNewTwo peFeeBudgetNewTwo);

}
