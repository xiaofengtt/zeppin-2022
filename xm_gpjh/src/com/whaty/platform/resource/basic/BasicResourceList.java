package com.whaty.platform.resource.basic;

import java.util.List;

public interface BasicResourceList {

	/**
	 * �����Դ��������
	 * 
	 * @param searchproperty
	 * @return ��Դ��������
	 */
	public int getResourceTypesNum(List searchproperty);

	/**
	 * �����Դ����
	 * 
	 * @param searchproperty
	 * @return ��Դ����
	 */
	public int getResourcesNum(List searchproperty);

	/**
	 * �����Դ�����б�
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��Դ�����б�
	 */
	public List getResourceTypes(List searchproperty, List orderproperty);

	/**
	 * �����Դ�б�
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��Դ�б�
	 */
	public List getResources(List searchproperty, List orderproperty);

	/**
	 * �����ԴĿ¼�б�
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getResourceDirs(List searchproperty, List orderproperty);

	/**
	 * Ϊ�û�ָ����ԴĿ¼Ȩ��
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
	 * ����keyId��ȡ��Ӧ����ԴĿ¼
	 * @param keyId
	 * @return
	 */
	public ResourceDir getResourceDirByKeyId(String keyId);

}
