/**  
 * This class is used for ...  
 * @author suijing
 * @version  
 *       1.0, 2014年7月29日 上午11:51:06  
 */
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Publisher;

/**
 * @author sj
 *
 */
public interface IPublisherService
{
	
	/**
	 * @param publisher
	 * @return
	 * @author suijing 2014年7月30日 下午12:55:45
	 */
	Publisher add(Publisher publisher);
	
	/**
	 * @param id
	 * @return
	 * @author suijing 2014年7月30日 下午12:55:52
	 */
	Publisher getById(int id);
	
	/**
	 * @param publisher
	 * @author suijing 2014年7月30日 下午12:55:56
	 */
	void update(Publisher publisher);
	
	/**
	 * @param publisher
	 * @author suijing 2014年7月30日 下午1:09:26
	 */
	void delete(Publisher publisher);
	
	/**
	 * @param name
	 * @param fcode
	 * @return
	 * @author suijing 2014年7月30日 下午5:51:50
	 */
	boolean isExist(String name, String fcode , Integer id);
	
	/**
	 * 出版社总数
	 * @param name
	 * @param fcode
	 * @return
	 * @author suijing 2014年7月30日 下午5:51:50
	 */
	Integer getPublisherCount(Map<String, Object> searchMap);
	
	/**
	 * 出版社总数
	 * @param name
	 * @param fcode
	 * @return
	 * @author suijing 2014年7月30日 下午5:51:50
	 */
	List<Publisher> getPublisherList(Map<String, Object> searchMap , String sorts, int offset, int pagesize);
}
