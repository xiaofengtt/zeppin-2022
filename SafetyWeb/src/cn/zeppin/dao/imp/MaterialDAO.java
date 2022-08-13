/** 
 * Project Name:CETV_TEST 
 * File Name:MaterialDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IMaterialDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Material;

/**
 * ClassName: MaterialDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:38:57 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class MaterialDAO extends HibernateTemplateDAO<Material, Integer> implements IMaterialDAO {

	/**
	 * 获取材料个数
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:45:52 <br/>
	 * @param params
	 * @return
	 */
	public int getMaterialCount(HashMap<String, Object> params) {

		StringBuilder sb = new StringBuilder();

		sb.append("select count(*) from Material m where 1=1 ");

		if(params.containsKey("id")){
			sb.append(" and m.id=").append(params.get("id"));
		}
		
		if (params.containsKey("grade.id")) {
			sb.append(" and m.grade.id=").append(params.get("grade.id"));
		}

		if (params.containsKey("grade.name")) {
			sb.append(" and m.grade.name like'%").append(params.get("grade.name")).append("%'");
		}

		if (params.containsKey("subject.id")) {
			sb.append(" and m.subject.id=").append(params.get("subject.id"));
		}

		if (params.containsKey("subject.name")) {
			sb.append(" and m.subject.name like'%").append(params.get("subject.name")).append("%'");
		}

		if (params.containsKey("content")) {
			sb.append(" and m.content like'%").append(params.get("content")).append("%'");
		}

		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());

	}

	/**
	 * 获取材料列表
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 上午11:46:47 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Material> getMaterials(HashMap<String, Object> params, String sorts,int offset, int length) {

		StringBuilder sb = new StringBuilder();

		sb.append("from Material m where 1=1 ");
		
		if(params.containsKey("id")){
			sb.append(" and m.id=").append(params.get("id"));
		}

		if (params.containsKey("grade.id")) {
			sb.append(" and m.grade.id=").append(params.get("grade.id"));
		}

		if (params.containsKey("grade.name")) {
			sb.append(" and m.grade.name like'%").append(params.get("grade.name")).append("%'");
		}

		if (params.containsKey("subject.id")) {
			sb.append(" and m.subject.id=").append(params.get("subject.id"));
		}

		if (params.containsKey("subject.name")) {
			sb.append(" and m.subject.name like'%").append(params.get("subject.name")).append("%'");
		}

		if (params.containsKey("content")) {
			sb.append(" and m.content like'%").append(params.get("content")).append("%'");
		}

		return this.getByHQL(sb.toString(), offset, length);
		
	}
	
	/**
	 * 获取引用材料的试题个数
	 * @author Administrator
	 * @date: 2014年8月10日 下午2:26:45 <br/> 
	 * @param material
	 * @return
	 */
	public int getRefenceMaterialCount(Material material){
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Item t where 1=1 ");
		sb.append(" and t.material=").append(material.getId());
		
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}

}
