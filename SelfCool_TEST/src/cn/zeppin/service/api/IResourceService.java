/**  
 * This class is used for ...  
 * @author suijing
 * @version  
 *       1.0, 2014年7月24日 下午7:00:51  
 */
package cn.zeppin.service.api;

import cn.zeppin.entity.Resource;

/**
 * @author sj
 * 
 */
public interface IResourceService {

	/**
	 * 通过id获取资源
	 * 
	 * @param id
	 * @return
	 * @author suijing 2014年7月25日 上午11:41:50
	 */
	Resource getById(int id);

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @return
	 * @author suijing 2014年7月25日 上午11:53:05
	 */
	Resource add(Resource resource);

	/**
	 * @param resource
	 * @author suijing 2014年7月25日 上午11:57:36
	 */
	void update(Resource resource);

	/**
	 * @param id
	 * @author suijing 2014年7月25日 下午12:21:15
	 */
	void delById(int id);

}
