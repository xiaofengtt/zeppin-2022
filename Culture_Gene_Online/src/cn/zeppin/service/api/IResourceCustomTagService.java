package cn.zeppin.service.api;

import cn.zeppin.entity.Resource;
import cn.zeppin.entity.ResourceCustomTag;
import cn.zeppin.entity.User;

public interface IResourceCustomTagService {
	/**
	 * 获取
	 */
	public ResourceCustomTag getResourceCustomTag(int id);
	
	/**
	 * 添加
	 */
	public ResourceCustomTag addResourceCustomTag(ResourceCustomTag resourceCustomTag);
	
	/**
	 * 更新
	 */
	public void updateResourceCustomTag(ResourceCustomTag resourceCustomTag);
	
	/**
	 * 删除
	 */
	public void deleteResourceCustomTag(int id);
	
	/**
	 * 删除资源的所有自定义标签
	 */
	public void deleteResourceCustomTagByResource(int id);
	
	/**
	 * 更新资源自定义标签
	 */
	public void updateResourceCustomTag(User user, Resource resource,String[] customTags);
}
