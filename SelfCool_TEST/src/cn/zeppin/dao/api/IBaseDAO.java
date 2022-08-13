package cn.zeppin.dao.api;

import java.io.Serializable;
import java.util.List;


public interface IBaseDAO<T,PK extends Serializable> {
	
	public T save(T t);
	
	public T delete(T t);
	
	public T update(T t);
	
	public T merge(T t);
	
	public T get(PK id);
	
	public T load(PK id);
	
	public List<T> getAll();
}
