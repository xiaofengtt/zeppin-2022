/**  
 * This class is used for ...  
 * @author suijing
 * @version  
 *       1.0, 2014年7月29日 上午11:51:33  
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IPublisherDAO;
import cn.zeppin.entity.Publisher;
import cn.zeppin.service.api.IPublisherService;

/**
 * @author sj
 *
 */
public class PublisherService implements IPublisherService
{
	private IPublisherDAO publisherDAO;
	

	
	public IPublisherDAO getPublisherDAO() {
		return publisherDAO;
	}

	public void setPublisherDAO(IPublisherDAO publisherDAO) {
		this.publisherDAO = publisherDAO;
	}

	@Override
	public Publisher add(Publisher publisher)
	{
		return this.getPublisherDAO().save(publisher);
	}
	
	@Override
	public Publisher getById(int id)
	{
		return this.getPublisherDAO().get(id);
	}
	
	@Override
	public void update(Publisher publisher)
	{
		this.getPublisherDAO().update(publisher);
		
	}
	
	@Override
	public void delete(Publisher publisher)
	{
		publisher.setStatus((short) 0);
		this.getPublisherDAO().update(publisher);
	}
	
	public boolean isExist(String name, String fcode , Integer id)
	{
		return this.getPublisherDAO().isExist(name,fcode,id);
	}
	
	/**
	 * 出版社总数
	 * @param name
	 * @param fcode
	 * @return
	 * @author suijing 2014年7月30日 下午5:51:50
	 */
	public Integer getPublisherCount(Map<String, Object> searchMap){
		return this.getPublisherDAO().getPublisherCount(searchMap);
	}
	
	/**
	 * 出版社列表
	 * @param name
	 * @param fcode
	 * @return
	 * @author suijing 2014年7月30日 下午5:51:50
	 */
	public List<Publisher> getPublisherList(Map<String, Object> searchMap , String sorts, int offset, int pagesize){
		return this.getPublisherDAO().getPublisherList(searchMap , sorts , offset , pagesize);
	}
}
