package com.whaty.platform.resource.basic;

import java.util.List;

public abstract class ResourceRight {
	
	/**
	 * 获取具有访问dir权限的用户列表
	 * 
	 * @param dir
	 * @return
	 */
	public abstract List getDirUser(ResourceDir dir);

	/**
	 * 获取user具有访问权限的ResourceDir
	 * 
	 * @param user
	 * @return
	 */
	public abstract List getUserDir(ResourceUser user);

	/**
	 * 使user具有访问dir的权限
	 * 
	 * @param user
	 * @param dir
	 */
	public abstract void setUserDir(ResourceUser user, ResourceDir dir);

	/**
	 * 删除user对dir的访问权限
	 * 
	 * @param user
	 * @param dir
	 */
	public abstract void delUserDir(ResourceUser user, ResourceDir dir);

	/**
	 * 将srcUser的权限复制给destUser
	 * 
	 * @param srcUser
	 * @param destUser
	 */
	public abstract void copyUserDir(ResourceUser srcUser, ResourceUser destUser);
}
