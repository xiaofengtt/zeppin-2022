/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.project.chinamobile.media.core.entity.Category;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.ICategoryDAO;
import cn.zeppin.project.chinamobile.media.web.dao.base.BaseDAO;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Repository
public class CategoryDAO extends BaseDAO<Category, String> implements ICategoryDAO {


	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Category where 1=1");
		if (searchMap.get("parent") != null && !searchMap.get("parent").equals("")){
			sb.append(" and parent='").append(searchMap.get("parent")+"'");
		}
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")){
			sb.append(" and level=").append(searchMap.get("level"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and name like '%").append(searchMap.get("name")).append("%'");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and status='").append(searchMap.get("status")+"'");
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
	
	/**
	 * 通过参数取列表
	 */
	public List<Category> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append("from Category where 1=1");
		if (searchMap.get("parent") != null && !searchMap.get("parent").equals("")){
			sb.append(" and parent='").append(searchMap.get("parent")+"'");
		}
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")){
			sb.append(" and level=").append(searchMap.get("level"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and name like '%").append(searchMap.get("name")).append("%'");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and status='").append(searchMap.get("status")+"'");
		}
		
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" ").append(sort);
				comma = ",";
			}
		}
		if(offset!=null && length!=null){
			return this.getByHQL(sb.toString(), offset, length);
		}else{
			return this.getByHQL(sb.toString());
		}
	}

	/**
	 * 获取子分类数量
	 */
	public Integer getChildCount(String id){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Category where parent='").append(id+"'");
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}

	@Override
	public List<Entity> getListByParams(Category category, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder();
		sb.append("select id,name,level,scode,parent,status from category where 1=1");
		if(category.getId() != null && !"".equals(category.getId())){
			sb.append(" and id='"+category.getId()+"'");
		}
		
		if(category.getLevel() != null && !"".equals(category.getLevel())){
			sb.append(" and level="+category.getLevel());
		}
		
		if(category.getName() != null && !"".equals(category.getName())){
			sb.append(" and name='"+category.getName()+"'");
		}
		
		if(category.getScode() != null && !"".equals(category.getScode())){
			sb.append(" and scode like '%"+category.getScode()+"%'");
		}
		
		if(category.getParent() != null && !"".equals(category.getParent())){
			sb.append(" and parent='"+category.getParent()+"'");
		}
		
		if(category.getStatus() != null && !"".equals(category.getStatus())){
			sb.append(" and status='"+category.getStatus()+"'");
		}else{
			sb.append(" and status in('normal','stopped')");
		}
		
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" ").append(sort);
				comma = ",";
			}
		}
		
		List<Entity> results = this.getBySQL(sb.toString(), offset, length, null, resultClass);
		
		return results;
	}

	@Override
	public Integer getCountByParams(Category category) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Category where 1=1");
		
		if(category.getId() != null && !"".equals(category.getId())){
			sb.append(" and id='"+category.getId()+"'");
		}
		
		if(category.getLevel() != null && !"".equals(category.getLevel())){
			sb.append(" and level="+category.getLevel());
		}
		
		if(category.getName() != null && !"".equals(category.getName())){
			sb.append(" and name='"+category.getName()+"'");
		}
		
		if(category.getScode() != null && !"".equals(category.getScode())){
			sb.append(" and scode like '%"+category.getScode()+"%'");
		}
		
		if(category.getParent() != null && !"".equals(category.getParent())){
			sb.append(" and parent='"+category.getParent()+"'");
		}
		
		if(category.getStatus() != null && !"".equals(category.getStatus())){
			sb.append(" and status='"+category.getStatus()+"'");
		}else{
			sb.append(" and status in('normal','stopped')");
		}
		
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}

	@Override
	public List<Entity> getListByParams(Category category, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select id,name from category where 1=1");
		if(category.getId() != null && !"".equals(category.getId())){
			sb.append(" and id='"+category.getId()+"'");
		}
		
		if(category.getLevel() != null && !"".equals(category.getLevel())){
			sb.append(" and level="+category.getLevel());
		}
		
		if(category.getName() != null && !"".equals(category.getName())){
			sb.append(" and name='"+category.getName()+"'");
		}
		
		if(category.getScode() != null && !"".equals(category.getScode())){
			sb.append(" and scode like '%"+category.getScode()+"%'");
		}
		
		if(category.getParent() != null && !"".equals(category.getParent())){
			sb.append(" and parent='"+category.getParent()+"'");
		}
		
		if(category.getStatus() != null && !"".equals(category.getStatus())){
			sb.append(" and status='"+category.getStatus()+"'");
		}else{
			sb.append(" and status in('normal','stopped')");
		}
		
		sb.append(" order by createtime asc");
		
		List<Entity> results = this.getBySQL(sb.toString(), -1, -1, null, resultClass);
		
		return results;
	}
}
