package com.whaty.platform.resource.util;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.resource.BasicResourceManage;
import com.whaty.platform.resource.ResourceFactory;
import com.whaty.platform.resource.basic.ResourceField;
import com.whaty.platform.resource.basic.ResourceType;

public class ResourceFunctionForDWR {
	public List getResourceFieldList(String resourceTypeId) {
		List fieldNameList = new ArrayList();
		ResourceFactory factory = ResourceFactory.getInstance();
		BasicResourceManage manage = factory.creatBasicResourceManage();
		try {
			ResourceType resourceType = manage.getResourceType(resourceTypeId);
			List fieldList = resourceType.getResourceFieldList();
			for(int i=0; i<fieldList.size(); i++) {
				ResourceField field = (ResourceField)fieldList.get(i);
				fieldNameList.add(field.getName());
			}
		} catch (NoRightException e) {
			
		}
		
		return fieldNameList;
	}
	
	public List getFieldList() {
		List fieldNameList = new ArrayList();
		ResourceFactory factory = ResourceFactory.getInstance();
		BasicResourceManage manage = factory.creatBasicResourceManage();
		try {
			ResourceType resourceType = manage.getResourceType("9");
			List fieldList = resourceType.getResourceFieldList();
			for(int i=0; i<fieldList.size(); i++) {
				ResourceField field = (ResourceField)fieldList.get(i);
				fieldNameList.add(field.getName());
			}
		} catch (NoRightException e) {
			
		}
		
		return fieldNameList;
	}
}
