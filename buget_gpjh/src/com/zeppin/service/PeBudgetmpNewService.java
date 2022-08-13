/**  
* @Title: PeBudgetmpService.java
* @Package com.zeppin.service
* @author jiangfei  
*/
package com.zeppin.service;

import com.zeppin.model.PeBudgetmpNew;
import com.zeppin.model.PeBudgetmpNewDetail;

/**
 * @author Administrator
 *
 */
public interface PeBudgetmpNewService extends BaseService<PeBudgetmpNew,String> {
	
	public int addAndUpdate(boolean isadd,PeBudgetmpNewDetail peFeeBudgetNewDetail, PeBudgetmpNew peFeeBudget);

}
