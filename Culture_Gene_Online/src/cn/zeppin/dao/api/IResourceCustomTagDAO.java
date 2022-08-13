package cn.zeppin.dao.api;

import cn.zeppin.entity.ResourceCustomTag;

public interface IResourceCustomTagDAO extends IBaseDAO<ResourceCustomTag, Integer> {
	
	/**
	 * 删除资源的所有自定义标签
	 */
	public void deleteResourceCustomTagByResource(int id);
}
