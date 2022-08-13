package com.whaty.platform.resource;

import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.resource.basic.Resource;
import com.whaty.platform.resource.basic.ResourceDir;
import com.whaty.platform.resource.basic.ResourceType;

public abstract class BasicResourceManage {

	/** Creates a new instance of ResourceManage */
	public BasicResourceManage() {
	}

	/**
	 * ��ȡ��Դ������Ŀ
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract int getResourceTypesNum() throws NoRightException;

	/**
	 * @param id
	 * @return ��Դ���Ͷ���
	 * @throws NoRightException
	 */
	public abstract ResourceType getResourceType(String id)
			throws NoRightException;

	/**
	 * ��ȡ��Դ�����б�
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract List getResourceTypeList() throws NoRightException;

	/**
	 * @param id
	 * @return ��Դ���Ͷ���
	 * @throws NoRightException
	 */
	public abstract int addResourceType(String name, String note,
			String status, String xml) throws NoRightException,
			PlatformException;

	/**
	 * �����ԴĿ¼
	 * 
	 * @param name
	 * @param parent
	 * @param note
	 * @param status
	 * @param isInherit
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addResourceDir(String name, String parent, String note,
			String status, String isInherit) throws NoRightException,
			PlatformException;

	/**
	 * @param name
	 * @param parent
	 * @param note
	 * @param status
	 * @param isInherit
	 * @param keyId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addResourceDir(String name, String parent, String note,
			String status, String isInherit, String keyId)
			throws NoRightException, PlatformException;

	/**
	 * ��ȡ��Դ��
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract int getResourcesNum() throws NoRightException;

	/**
	 * @param id
	 * @return ��Դ����
	 * @throws NoRightException
	 */
	public abstract Resource getResource(String id) throws NoRightException;

	/**
	 * ��ȡ��Դ�б�
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract List getResourceList() throws NoRightException;

	/**
	 * @param title
	 * @param language
	 * @param description
	 * @param keywords
	 * @param creatUser
	 * @param typeId
	 * @param dirId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addResource(String title, String language,
			String description, String keywords, String creatUser,
			String typeId, String dirId, String xml) throws NoRightException,
			PlatformException;

	/**
	 * ɾ����Դ����
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteResource(String id) throws NoRightException,
			PlatformException;

	/**
	 * ɾ����Դ���Ͷ���
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteResourceType(String id) throws NoRightException,
			PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param note
	 * @param status
	 * @param xml
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateResourceType(String id, String name, String note,
			String status, String xml) throws NoRightException,
			PlatformException;

	/**
	 * @param id
	 * @param title
	 * @param language
	 * @param description
	 * @param keywords
	 * @param creatUser
	 * @param typeId
	 * @param dir_id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateResource(String id, String title,
			String language, String description, String keywords,
			String creatUser, String typeId, String dir_id)
			throws NoRightException, PlatformException;

	/**
	 * ������Դ
	 * 
	 * @param id
	 * @param title
	 * @param language
	 * @param description
	 * @param keywords
	 * @param creatUser
	 * @param typeId
	 * @param dir_id
	 * @param xml
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateResource(String id, String title,
			String language, String description, String keywords,
			String creatUser, String typeId, String dir_id, String xml)
			throws NoRightException, PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param parent
	 * @param note
	 * @param status
	 * @param isInherit
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateResourceDir(String id, String name,
			String parent, String note, String status, String isInherit)
			throws NoRightException, PlatformException;

	/**
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteResourceDir(String id) throws NoRightException,
			PlatformException;

	/**
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract ResourceDir getResourceDir(String id)
			throws NoRightException, PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param parent
	 * @param note
	 * @param status
	 * @param isinherit
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getResourceDirs(String id, String name, String parent,
			String note, String status, String isinherit)
			throws NoRightException, PlatformException;

	/**
	 * @param id
	 * @param title
	 * @param language
	 * @param description
	 * @param keywords
	 * @param creatUser
	 * @param type_id
	 * @param dir_id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getResources(String id, String title, String language,
			String description, String keywords, String creatUser,
			String type_id, String dir_id) throws NoRightException,
			PlatformException;

	/**
	 * @param userList
	 * @param resourceDirList
	 * @return
	 */
	public abstract int setResourceRight(List userList, List resourceDirList);

	/**
	 * @param userId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getResourceDirListByUserId(String userId)
			throws NoRightException, PlatformException;

	/**
	 * �û�userId�Ƿ��в鿴��ԴĿ¼dirId��Ȩf
	 * 
	 * @param userId
	 * @param dirId
	 * @return
	 */
	public abstract boolean hasRight(String userId, String dirId);

	/**
	 * @param session
	 * @return
	 * @throws PlatformException
	 */
//	public abstract WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException;

	/**
	 * ���keyId��ȡ��Ӧ����ԴĿ¼
	 * @param keyId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract ResourceDir getResourceDirByKeyId(String keyId)
			throws NoRightException, PlatformException;
}
