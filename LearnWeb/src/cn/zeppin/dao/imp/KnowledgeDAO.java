/** 
 * Project Name:CETV_TEST 
 * File Name:KnowladgeDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IKnowledgeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Knowledge;

/** 
 * ClassName: KnowladgeDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年6月10日 下午9:31:43 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class KnowledgeDAO extends HibernateTemplateDAO<Knowledge, Integer> implements IKnowledgeDAO{
	
	/**
	 * 添加知识点
	 * @author Clark
	 * @date: 2014年7月22日 下午8:02:38 <br/> 
	 * @param knowledge
	 * @return Knowledge
	 */
	@Override
	public Knowledge save(Knowledge knowledge) {
		Knowledge result = super.save(knowledge);
		String format = "0000000000";
		DecimalFormat df = new DecimalFormat(format);
		String scode = df.format(result.getId());
		if (result.getKnowledge()!= null){
			scode = result.getKnowledge().getScode() + scode;
		}
		result.setScode(scode);
		result = this.update(result);
		return result;
	}

	/**
	 * 按条件查询知识点数量
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:07 <br/> 
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchKnowledgeCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from Knowledge k where 1=1 ");
		if (searchMap.get("id") != null  && !searchMap.get("id").equals("")){
			hql.append(" and k.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("level") != null  && !searchMap.get("level").equals("")){
			hql.append(" and k.level=").append(searchMap.get("level"));
		}
		if (searchMap.get("knowledge.id") != null  && !searchMap.get("knowledge.id").equals("")){
			hql.append(" and k.knowledge=").append(searchMap.get("knowledge.id"));
		}
		if (searchMap.get("grade.id") != null  && !searchMap.get("grade.id").equals("")){
			hql.append(" and k.grade.id=").append(searchMap.get("grade.id"));
		}
		if (searchMap.get("subject.id") != null  && !searchMap.get("subject.id").equals("")){
			hql.append(" and k.subject.id=").append(searchMap.get("subject.id"));
		}
		if (searchMap.get("name") != null  && !searchMap.get("name").equals("")){
			hql.append(" and k.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("sysUser.id") != null  && !searchMap.get("sysUser.id").equals("")){
			hql.append(" and k.sysUser.id=").append(searchMap.get("sysUser.id"));
		}
		if (searchMap.get("status") != null  && !searchMap.get("status").equals("")){
			hql.append(" and k.status=").append(searchMap.get("status"));
		}
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}

	/**
	 * 按条件查询知识点列表
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/> 
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Knowledge>
	 */
	@Override
	public List<Knowledge> searchKnowledge(Map<String, Object> searchMap,
			String sorts, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" select k from Knowledge k where 1=1 ");
		if (searchMap.get("id") != null  && !searchMap.get("id").equals("")){
			hql.append(" and k.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("level") != null  && !searchMap.get("level").equals("")){
			hql.append(" and k.level=").append(searchMap.get("level"));
		}
		if (searchMap.get("knowledge.id") != null  && !searchMap.get("knowledge.id").equals("")){
			hql.append(" and k.knowledge.id=").append(searchMap.get("knowledge.id"));
		}
		if (searchMap.get("grade.id") != null  && !searchMap.get("grade.id").equals("")){
			hql.append(" and k.grade.id=").append(searchMap.get("grade.id"));
		}
		if (searchMap.get("subject.id") != null  && !searchMap.get("subject.id").equals("")){
			hql.append(" and k.subject.id=").append(searchMap.get("subject.id"));
		}
		if (searchMap.get("name") != null  && !searchMap.get("name").equals("")){
			hql.append(" and k.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("sysUser.id") != null  && !searchMap.get("sysUser.id").equals("")){
			hql.append(" and k.sysUser.id=").append(searchMap.get("sysUser.id"));
		}
		if (searchMap.get("status") != null  && !searchMap.get("status").equals("")){
			hql.append(" and k.status=").append(searchMap.get("status"));
		}
		//排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				hql.append(comma);
				hql.append("k.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(hql.toString(), offset, length);
	}

}
