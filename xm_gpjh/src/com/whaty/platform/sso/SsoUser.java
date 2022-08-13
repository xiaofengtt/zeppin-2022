/**
 * 
 */
package com.whaty.platform.sso;

import com.whaty.platform.Items;
import com.whaty.platform.User;
import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 * 
 */
public abstract class SsoUser extends User implements Items {

	/**
	 * 该用户是由哪个子系统添加
	 */
	private String addSubSystem;

	/**
	 * 用户加入系统的时间
	 */
	private String registeDate;

	/**
	 * 用户的说明
	 */
	private String note;

	public String getRegisteDate() {
		return registeDate;
	}

	public void setRegisteDate(String registeDate) {
		this.registeDate = registeDate;
	}

	public String getAddSubSystem() {
		return addSubSystem;
	}

	public void setAddSubSystem(String addSubSystem) {
		this.addSubSystem = addSubSystem;
	}

	/**
	 * 由loginId初始化该类
	 * 
	 * @throws PlatformException
	 */
	public abstract void initByLoginId() throws PlatformException;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public abstract SsoUser getSsoLoginUser(String loginId)
			throws PlatformException;

}
