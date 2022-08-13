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
	 * ���û������ĸ���ϵͳ���
	 */
	private String addSubSystem;

	/**
	 * �û�����ϵͳ��ʱ��
	 */
	private String registeDate;

	/**
	 * �û���˵��
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
	 * ��loginId��ʼ������
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
