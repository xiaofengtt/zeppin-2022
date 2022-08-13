package com.whaty.platform.resource.basic;

import java.util.List;

public abstract class ResourceRight {
	
	/**
	 * ��ȡ���з���dirȨ�޵��û��б�
	 * 
	 * @param dir
	 * @return
	 */
	public abstract List getDirUser(ResourceDir dir);

	/**
	 * ��ȡuser���з���Ȩ�޵�ResourceDir
	 * 
	 * @param user
	 * @return
	 */
	public abstract List getUserDir(ResourceUser user);

	/**
	 * ʹuser���з���dir��Ȩ��
	 * 
	 * @param user
	 * @param dir
	 */
	public abstract void setUserDir(ResourceUser user, ResourceDir dir);

	/**
	 * ɾ��user��dir�ķ���Ȩ��
	 * 
	 * @param user
	 * @param dir
	 */
	public abstract void delUserDir(ResourceUser user, ResourceDir dir);

	/**
	 * ��srcUser��Ȩ�޸��Ƹ�destUser
	 * 
	 * @param srcUser
	 * @param destUser
	 */
	public abstract void copyUserDir(ResourceUser srcUser, ResourceUser destUser);
}
