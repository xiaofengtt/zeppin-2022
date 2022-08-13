/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.dao.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.Category;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.base.IBaseDAO;

/**
 * @author Clark.R 2016年3月29日
 *
 */
public interface ICategoryDAO extends IBaseDAO<Category, String> {
	
	public Integer getCountByParams(HashMap<String,String> searchMap);
	
	public List<Category> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length);
	
	public Integer getChildCount(String id);
	
	public List<Entity> getListByParams(Category category, String sorts, Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass);
	
	public List<Entity> getListByParams(Category category, @SuppressWarnings("rawtypes") Class resultClass);
	
	public Integer getCountByParams(Category category);
}
