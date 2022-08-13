/**  
* @Title: PeActualBudgetService.java
* @Package com.zeppin.service
* @author jiangfei  
*/
package com.zeppin.service;

import com.zeppin.model.PeActualBudgetNew;
import com.zeppin.model.PeActualBudgetNewDetail;

/**
 * @author Administrator
 *
 */
public interface PeActualBudgetNewService extends BaseService<PeActualBudgetNew,String> {
	
	public int addAndUpdate(boolean isadd, PeActualBudgetNewDetail peFeeBudgetNewDetail, PeActualBudgetNew peFeeBudgetNew);

}
