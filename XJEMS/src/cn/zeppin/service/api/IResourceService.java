package cn.zeppin.service.api;

import cn.zeppin.entity.Resource;

/**
 * 
 */
public interface IResourceService {

	/**
	 * 通过id获取资源
	 * 
	 * @param id
	 * @return
	 */
	Resource getById(int id);

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @return
	 */
	Resource add(Resource resource);

	/**
	 * @param resource
	 */
	void update(Resource resource);

	/**
	 * @param id
	 */
	void delById(int id);

}
