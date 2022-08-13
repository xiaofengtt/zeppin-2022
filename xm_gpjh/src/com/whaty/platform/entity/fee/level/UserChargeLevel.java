/**
 * 
 */
package com.whaty.platform.entity.fee.level;

import java.util.HashMap;
import java.util.Map;

import com.whaty.platform.User;

/**
 * @author chenjian
 *
 */
public abstract class UserChargeLevel {

	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public abstract void setChargeLevel(Map chargeLevel);
	
	public void setChargeLevel(String chargeType,String chargeValue) {
		
		Map chargeLevel=new HashMap();
		chargeLevel.put(chargeType, chargeValue);
		setChargeLevel(chargeLevel);
	}
	
	public abstract Map getAllChargeLevel();
	
	public abstract String getChargeLevel(String chargeType);
	
}
