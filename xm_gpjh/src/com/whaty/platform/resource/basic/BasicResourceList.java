package com.whaty.platform.resource.basic;

import java.util.List;

public interface BasicResourceList {

	/**
	 * 获得资源类型数量
	 * 
	 * @param searchproperty
	 * @return 资源类型数量
	 */
	public int getResourceTypesNum(List searchproperty);

	/**
	 * 获得资源数量
	 * 
	 * @param searchproperty
	 * @return 资源数量
	 */
	public int getResourcesNum(List searchproperty);

	/**
	 * 获得资源类型列表
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return 资源类型列表
	 */
	public List getResourceTypes(List searchproperty, List orderproperty);

	/**
	 * 获得资源列表
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return 资源列表
	 */
	public List getResources(List searchproperty, List orderproperty);

	/**
	 * 获得资源目录列表
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getResourceDirs(List searchproperty, List orderproperty);

	/**
	 * 为用户指定资源目录权限
	 * 
	 * @param userList
	 * @param resourceDirList
	 * @return
	 */
	public int setResourceRight(List userList, List resourceDirList);

	/**
	 * @param userId
	 * @return
	 */
	public List searchResourceDirListByUserId(String userId);
	
	/**
	 * 根据keyId获取对应的资源目录
	 * @param keyId
	 * @return
	 */
	public ResourceDir getResourceDirByKeyId(String keyId);

}
