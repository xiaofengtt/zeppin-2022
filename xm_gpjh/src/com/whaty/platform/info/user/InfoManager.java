/**
 * 
 */
package com.whaty.platform.info.user;

import com.whaty.platform.Items;
import com.whaty.platform.User;

/**
 * @author chenjian
 * 
 */
public abstract class InfoManager extends User implements Items {

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
