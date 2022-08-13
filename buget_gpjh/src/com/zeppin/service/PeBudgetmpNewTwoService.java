/**  
* @Title: PeBudgetmpService.java
* @Package com.zeppin.service
* @author jiangfei  
*/
package com.zeppin.service;

import com.zeppin.model.PeBudgetmpNewTwo;
import com.zeppin.model.PeBudgetmpNewTwoDetail;

/**
 * @author Administrator
 *
 */
public interface PeBudgetmpNewTwoService extends BaseService<PeBudgetmpNewTwo,String> {
	
	public int addAndUpdate(boolean isadd,PeBudgetmpNewTwoDetail peFeeBudgetNewTwoDetail, PeBudgetmpNewTwo peFeeBudgetNewTwo);

}
