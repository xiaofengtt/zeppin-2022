package com.whaty.platform.campus.base;

import java.util.List;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public interface ClassList {

	public List getClasses(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	public int getClassesNum(List searchProperty) throws PlatformException;

}