/**
 * 
 */
package com.whaty.platform.entity.user;

import com.whaty.platform.User;

/**
 * @author chenjian
 *
 */
public  class EntityUser extends User {
	
	private String nickname;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	

}
