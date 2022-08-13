package com.whaty.platform.campus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.AssociationMember;
import com.whaty.platform.campus.base.ClassMember;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.platform.util.Page;

public abstract class CampusNormalManage {

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
	
	
	public abstract List getClassList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;
	public abstract int getClassNum(HttpServletRequest request)
			throws PlatformException;
	
	public abstract List getClassMemberList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;
	
	public abstract int getClassMemberNum(HttpServletRequest request)
			throws PlatformException;
	
	public abstract ClassMember getClassMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract ClassMember getClassMember(String id) throws PlatformException;
	
	public abstract int addAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract com.whaty.platform.campus.base.Association getAssociation(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract com.whaty.platform.campus.base.Association getAssociation(String id) throws PlatformException;
	
	
	public abstract List getAssociationList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;
	public abstract int getAssociationNum(HttpServletRequest request)
			throws PlatformException;
	
	public abstract List getAssociationMemberList(Page page,
			HttpServletRequest request, HttpServletResponse response)
			throws PlatformException;
	
	public abstract int getAssociationMemberNum(HttpServletRequest request)
			throws PlatformException;
	
	public abstract AssociationMember getAssociationMember(HttpServletRequest request,
			HttpServletResponse response) throws PlatformException;

	public abstract AssociationMember getAssociationMember(String id) throws PlatformException;
	
	public abstract List getCreateNewAssociationList() throws PlatformException;
	
	public abstract List getTopAssociationList(Page page) throws PlatformException;
	
	public abstract int	getTopAssociationListNum() throws PlatformException;
	
	public abstract List getClassForumList(Page page,String classId,boolean isNew,int displayNum,String keyword)throws PlatformException;
	
	public abstract int getClassForumNum(String classId,String keyword)throws PlatformException;
	
	public abstract boolean isCurrentClassMember(String classId,String memberId)throws PlatformException;
}
