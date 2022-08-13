package cn.zeppin.service.imp;

import cn.zeppin.dao.api.IResourceCustomTagDAO;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.ResourceCustomTag;
import cn.zeppin.entity.User;
import cn.zeppin.service.api.IResourceCustomTagService;
import cn.zeppin.utility.DataTimeConvert;

public class ResourceCustomTagService implements IResourceCustomTagService {

	
	private IResourceCustomTagDAO resourceCustomTagDAO;
	
	public IResourceCustomTagDAO getResourceCustomTagDAO() {
		return resourceCustomTagDAO;
	}

	public void setResourceCustomTagDAO(IResourceCustomTagDAO resourceCustomTagDAO) {
		this.resourceCustomTagDAO = resourceCustomTagDAO;
	}
	
	/**
	 * 获取
	 */
	public ResourceCustomTag getResourceCustomTag(int id)
	{
		return this.getResourceCustomTagDAO().get(id);
	}
	
	/**
	 * 添加
	 */
	public ResourceCustomTag addResourceCustomTag(ResourceCustomTag resourceCustomTag)
	{
		return this.getResourceCustomTagDAO().save(resourceCustomTag);
	}
	
	/**
	 * 修改
	 */
	public void updateResourceCustomTag(ResourceCustomTag resourceCustomTag)
	{
		this.getResourceCustomTagDAO().update(resourceCustomTag);
	}
	
	/**
	 * 删除
	 */
	public void deleteResourceCustomTag(int id)
	{
		ResourceCustomTag resourceCustomTag = this.getResourceCustomTagDAO().get(id);
		this.getResourceCustomTagDAO().delete(resourceCustomTag);
	}
	
	/**
	 * 删除资源的所有自定义标签
	 */
	public void deleteResourceCustomTagByResource(int id){
		this.getResourceCustomTagDAO().deleteResourceCustomTagByResource(id);
	}
	
	/**
	 * 更新资源自定义标签
	 */
	public void updateResourceCustomTag(User user, Resource resource, String[] customTags){
		this.getResourceCustomTagDAO().deleteResourceCustomTagByResource(resource.getId());
		if(customTags!=null && customTags.length>0){
			for(int i=0 ; i<customTags.length ; i++){
				String[] info = customTags[i].split("_");
				if(info.length == 2){
					ResourceCustomTag resourceCustomTag = new ResourceCustomTag();
					resourceCustomTag.setResource(resource);
					resourceCustomTag.setName(info[0]);
					resourceCustomTag.setValue(info[1]);
					resourceCustomTag.setUser(user);
					resourceCustomTag.setCreatetime(DataTimeConvert.getCurrentTime(""));
					this.getResourceCustomTagDAO().save(resourceCustomTag);
				}
			}
		}
	}
}
