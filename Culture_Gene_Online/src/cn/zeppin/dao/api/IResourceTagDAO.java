package cn.zeppin.dao.api;

import cn.zeppin.entity.ResourceTag;

public interface IResourceTagDAO extends IBaseDAO<ResourceTag, Integer> {

	/**
	 * 删除资源的所有标签
	 */
	public void deleteResourceTagByResource(int id);
	
}
