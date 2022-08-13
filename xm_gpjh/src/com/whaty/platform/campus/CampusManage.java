package com.whaty.platform.campus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.AssociationMember;
import com.whaty.platform.campus.base.ClassMember;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.platform.interaction.InteractionManage;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.util.Page;
public abstract class CampusManage {

	private CampusManagerPriv priv;

	/**
	 * @return the priv
	 */
	public CampusManagerPriv getPriv() {
		return priv;
	}

	/**
	 * @param priv
	 *            the priv to set
	 */
	public void setPriv(CampusManagerPriv priv) {
		this.priv = priv;
	}

	public abstract int addClass(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract com.whaty.platform.campus.base.Class getClass(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract com.whaty.platform.campus.base.Class getClass(String id) throws PlatformException;
	
	public abstract com.whaty.platform.campus.base.Class getClassByName(String name) throws PlatformException;
	
	public abstract int delClass(String id) throws PlatformException;

	public abstract List getClassList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;
	public abstract int getClassNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int confirmClass(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int unConfirmClass(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int updateClass(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	
	public abstract int addClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract ClassMember getClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract ClassMember getClassMember(String id) throws PlatformException;
	
	
	public abstract int delClassMember(String id) throws PlatformException;

	public abstract List getClassMemberList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;
	
	public abstract int getClassMemberNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int confirmClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int unConfirmClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	public abstract int updateClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int addAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract com.whaty.platform.campus.base.Association getAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract com.whaty.platform.campus.base.Association getAssociation(String id) throws PlatformException;
	
	
	public abstract int delAssociation(String id) throws PlatformException;

	public abstract List getAssociationList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;
	public abstract int getAssociationNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int confirmAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int unConfirmAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int updateAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int addAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract AssociationMember getAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract AssociationMember getAssociationMember(String id) throws PlatformException;
	
	
	public abstract int delAssociationMember(String id) throws PlatformException;

	public abstract List getAssociationMemberList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;
	
	public abstract int getAssociationMemberNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int confirmAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int unConfirmAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	
	public abstract int updateAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;
	public abstract int addMemberToClass(String[] linkId,String memberType,String classId) throws PlatformException;
	
	public abstract int addMemberToAssociation(String[] linkId,String memberType,String AssociationId) throws PlatformException;
	
	public abstract int assignClassMemberToManager(String id) throws PlatformException;
	
	public abstract int unAssignClassMemberToManager(String id) throws PlatformException;
	
	public abstract int assignAssociationMemberToManager(String id) throws PlatformException;
	
	public abstract int unAssignAssociationMemberToManager(String id) throws PlatformException;
	
	public abstract InteractionUserPriv getInteractionUserPriv(String id);

	public abstract InteractionManage creatInteractionManage(
			InteractionUserPriv interactionUserPriv);
	public abstract int creatClassForum(String classId,String forumId) throws PlatformException;
	
	public abstract List getClassForumList(Page page,String classId,boolean isNew,int displayNum,String keyword)throws PlatformException;
	
	public abstract int getClassForumNum(String classId,String keyword)throws PlatformException;
}
