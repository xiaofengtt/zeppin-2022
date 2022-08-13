package cn.zeppin.project.chinamobile.media.web.dao.base;

import java.io.Serializable;
import java.util.List;

/**  
 * 顶级DAO接口, 定义通用方法并在基础BaseDAO类中实现。继承该接口的子接口可只定义自己特定的方法，简化编程。
 * 
 * @author Clark.R 2016-03-29 
 * @version  
 * @since JDK 1.8
 */
public interface IBaseDAO<T, PK extends Serializable> {
	
	public T insert(T t);
	
	public T delete(T t);
	
	public T update(T t);
	
	public T merge(T t);
	
	public T get(PK id);
	
	public T load(PK id);
	
	public List<T> getAll();
}
