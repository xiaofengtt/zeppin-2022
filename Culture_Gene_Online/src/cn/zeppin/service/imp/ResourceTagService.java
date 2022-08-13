package cn.zeppin.service.imp;

import cn.zeppin.dao.api.IResourceTagDAO;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.ResourceTag;
import cn.zeppin.entity.User;
import cn.zeppin.service.api.IResourceTagService;
import cn.zeppin.utility.DataTimeConvert;

public class ResourceTagService implements IResourceTagService {

	
	private IResourceTagDAO resourceTagDAO;
	
	public IResourceTagDAO getResourceTagDAO() {
		return resourceTagDAO;
	}

	public void setResourceTagDAO(IResourceTagDAO resourceTagDAO) {
		this.resourceTagDAO = resourceTagDAO;
	}
	
	/**
	 * 获取
	 */
	public ResourceTag getResourceTag(int id)
	{
		return this.getResourceTagDAO().get(id);
	}
	
	/**
	 * 添加
	 */
	public ResourceTag addResourceTag(ResourceTag resourceTag)
	{
		return this.getResourceTagDAO().save(resourceTag);
	}
	
	/**
	 * 修改
	 */
	public void updateResourceTag(ResourceTag resourceTag)
	{
		this.getResourceTagDAO().update(resourceTag);
	}
	
	/**
	 * 删除
	 */
	public void deleteResourceTag(int id)
	{
		ResourceTag resourceTag = this.getResourceTagDAO().get(id);
		this.getResourceTagDAO().delete(resourceTag);
	}
	
	/**
	 * 删除资源的所有标签
	 */
	public void deleteResourceTagByResource(int id){
		this.getResourceTagDAO().deleteResourceTagByResource(id);
	}
	
	/**
	 * 更新资源标签
	 */
	public void updateResourceTag(User user, Resource resource, String[] tags){
		this.getResourceTagDAO().deleteResourceTagByResource(resource.getId());
		if(tags!=null && tags.length>0){
			for(int i=0 ; i<tags.length ; i++){
				String tagName = tags[i].trim();
				ResourceTag resourceTag = new ResourceTag();
				resourceTag.setResource(resource);
				resourceTag.setTag(tagName);
				resourceTag.setUser(user);
				resourceTag.setCreatetime(DataTimeConvert.getCurrentTime(""));
				this.getResourceTagDAO().save(resourceTag);
			}
		}
	}
}
