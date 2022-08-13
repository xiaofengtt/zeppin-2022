package cn.zeppin.service.api;

import cn.zeppin.entity.Resource;
import cn.zeppin.entity.ResourceTag;
import cn.zeppin.entity.User;

public interface IResourceTagService {
	/**
	 * 获取
	 */
	public ResourceTag getResourceTag(int id);
	
	/**
	 * 添加
	 */
	public ResourceTag addResourceTag(ResourceTag resourceTag);
	
	/**
	 * 更新
	 */
	public void updateResourceTag(ResourceTag resourceTag);
	
	/**
	 * 删除
	 */
	public void deleteResourceTag(int id);
	
	/**
	 * 删除资源的所有标签
	 */
	public void deleteResourceTagByResource(int id);
	
	/**
	 * 更新资源标签
	 */
	public void updateResourceTag(User user, Resource resource,String[] tags);
}
