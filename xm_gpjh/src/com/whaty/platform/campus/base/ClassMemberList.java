package com.whaty.platform.campus.base;

import java.util.List;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public interface ClassMemberList {

	public List getClassMembers(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	public int getClassMembersNum(List searchProperty) throws PlatformException;

	public List getClassMembers(String classId,String memberType,String linkId) throws PlatformException;
}