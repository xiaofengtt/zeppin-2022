package com.whaty.platform.campus.base;

import java.util.List;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public interface AssociationMemberList {

	public List getAssociationMembers(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	public int getAssociationMembersNum(List searchProperty) throws PlatformException;

	public List getAssociationMembers(String AssociationId,String memberType,String linkId) throws PlatformException;
}