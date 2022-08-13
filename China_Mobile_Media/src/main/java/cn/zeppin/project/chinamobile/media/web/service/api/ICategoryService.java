/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.Category;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.service.base.IBaseService;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public interface ICategoryService extends IBaseService<Category, String> {
	
	public Category insert(Category category);
	
	public Category delete(Category category);
	
	public Category update(Category category);
	
	public Category get(String id);
	
	public List<Category> getAll();
	
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	public List<Category> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length);
	
	public Integer getChildCount(String id);
	
	public List<Entity> getListByParams(Category category, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);
	
	public List<Entity> getListByParams(Category category, @SuppressWarnings("rawtypes") Class resultClass);
	
	public Integer getCountByParams(Category category);
}
