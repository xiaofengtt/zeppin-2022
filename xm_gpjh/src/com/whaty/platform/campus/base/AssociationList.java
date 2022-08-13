package com.whaty.platform.campus.base;

import java.util.List;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public interface AssociationList {

	public List getAssociationes(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	public int getAssociationesNum(List searchProperty) throws PlatformException;

}