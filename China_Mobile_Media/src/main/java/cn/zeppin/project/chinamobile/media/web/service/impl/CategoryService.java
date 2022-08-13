/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.Category;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.ICategoryDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.ICategoryService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Service
public class CategoryService extends BaseService implements ICategoryService {
	
	@Autowired
	private ICategoryDAO categoryDAO;

	public Category insert(Category category) {
		return this.categoryDAO.insert(category);
	}

	public Category delete(Category category) {
		return this.categoryDAO.delete(category);
	}

	public Category update(Category category) {
		int count = this.categoryDAO.getChildCount(category.getId());
		if(count>0){
			HashMap<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("parent", category.getId());
			List<Category> list = this.categoryDAO.getListByParams(searchMap, "id", -1, -1);
			if(list != null && !list.isEmpty()){
				for(Category cat : list){
					cat.setStatus(category.getStatus());
					this.categoryDAO.update(cat);
				}
			}
		}
		return this.categoryDAO.update(category);
	}

	public Category get(String id) {
		return this.categoryDAO.get(id);
	}

	public List<Category> getAll() {
		return this.categoryDAO.getAll();
	}

	@Override
	public Integer getCountByParams(HashMap<String, String> searchMap) {
		// TODO Auto-generated method stub
		return this.categoryDAO.getCountByParams(searchMap);
	}

	@Override
	public List<Category> getListByParams(HashMap<String, String> searchMap,
			String sorts, Integer offset, Integer length) {
		// TODO Auto-generated method stub
		return this.categoryDAO.getListByParams(searchMap, sorts, offset, length);
	}

	@Override
	public Integer getChildCount(String id) {
		// TODO Auto-generated method stub
		return this.categoryDAO.getChildCount(id);
	}

	@Override
	public List<Entity> getListByParams(Category category, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		return this.categoryDAO.getListByParams(category, sorts, offset, length, resultClass);
	}

	@Override
	public Integer getCountByParams(Category category) {
		// TODO Auto-generated method stub
		return this.categoryDAO.getCountByParams(category);
	}

	@Override
	public List<Entity> getListByParams(Category category, @SuppressWarnings("rawtypes")  Class resultClass) {
		// TODO Auto-generated method stub
		return this.categoryDAO.getListByParams(category, resultClass);
	}

}
