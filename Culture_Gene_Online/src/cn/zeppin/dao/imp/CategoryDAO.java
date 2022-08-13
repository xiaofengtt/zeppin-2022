package cn.zeppin.dao.imp;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.ICategoryDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Category;

public class CategoryDAO extends HibernateTemplateDAO<Category, Integer> implements ICategoryDAO{
	
	/**
	 * 添加
	 */
	@Override
	public Category save(Category category) {
		category.setScode("");
		Category result = super.save(category);
		String format = "0000";
		DecimalFormat df = new DecimalFormat(format);
		String scode = df.format(result.getId());
		if (result.getParent() != null) {
			scode = result.getParent().getScode() + scode;
		}
		result.setScode(scode);
		result = this.update(result);
		return result;
	}
	
	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Category where 1=1");
		if (searchMap.get("parent") != null && !searchMap.get("parent").equals("") && !searchMap.get("parent").equals("0")){
			sb.append(" and parent.id=").append(searchMap.get("parent"));
		}
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")){
			sb.append(" and level=").append(searchMap.get("level"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and name like '%").append(searchMap.get("name")).append("%'");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and status=").append(searchMap.get("status"));
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
	
	/**
	 * 通过参数取列表
	 */
	public List<Category> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append("from Category where 1=1");
		if (searchMap.get("parent") != null && !searchMap.get("parent").equals("") && !searchMap.get("parent").equals("0")){
			sb.append(" and parent.id=").append(searchMap.get("parent"));
		}
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")){
			sb.append(" and level=").append(searchMap.get("level"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and name like '%").append(searchMap.get("name")).append("%'");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and status=").append(searchMap.get("status"));
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
	public Integer getChildCount(Integer id){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Category where parent=").append(id);
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
}