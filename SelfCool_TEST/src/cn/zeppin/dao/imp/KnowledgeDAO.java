/** 
 * Project Name:Self_Cool 
 * File Name:KnowladgeDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.dao.imp;
import java.util.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import cn.zeppin.utility.Dictionary;
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
	 * 获取用户知识树信息，除了知识点的信息还包括用户做题信息
	 */
	public List<Map<String,Object>> selectUserRighCount(Map<String,Object> searchMap) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		
		int userId = (int) searchMap.get("userId");
		int subjectId = (int) searchMap.get("subjectId");
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select k.id, k.name, k.level, k.scode, k.parent,");
		sql.append("  uk.last_test_item_correct_count, ki.standard_released_itemcount");
		sql.append(" from knowledge k");
		sql.append(" join knowledge_itemcount ki on k.id=ki.knowledgeid");
		sql.append(" left join user_knowledge uk on k.id=uk.knowledge and uk.sso_user=").append(userId);
		sql.append(" where k.status=").append(Dictionary.KNOWLEDGE_STATUS_NOMAL);//知识点处于有效状态；
		sql.append(" and k.subject=").append(subjectId);//指定科目的知识点；
		sql.append(" order by k.scode");
		List<Object[]> list = this.getBySQL(sql.toString());
		
		for(Object[] objs : list)
		{
			int i = 0;
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", objs[i++]);
			map.put("name", objs[i++]);
			map.put("level", objs[i++]);
			map.put("scode", objs[i++]);
			map.put("parent", objs[i++]);
			map.put("rightCount", objs[i] == null ? 0 : objs[i]); i++;
			map.put("totalCount", objs[i++]);
			
			results.add(map);
		}
		return results;
	}
	
//	public List<Map<String,Object>> selectUserRighCount(Map<String,Object> searchMap)
//	{
//		// 1,获取参数;
//		int userId = (int) searchMap.get("userId");
//		int subjectId = (int) searchMap.get("subjectId");
//
//		// 2,创建sql的工具sb,记得执行完毕立即清空sb;
//		StringBuilder sb = new StringBuilder();
//		// 2.1 随机生成三个表的名字；
//		int randomNum = new Random().nextInt(1000);
//		String itemRecords = "itemRecords" + randomNum;
//		String resultTable = "resultTable" + randomNum;
//		
//		//3,create table itemRecords that records unique user's items' information to calculate rightcount;
//		sb.append(" create temporary table ").append(itemRecords)
//		  .append(" select max(testitem.id), testitem.item, testitem.complete_type,item.knowladge")
//		  .append(" from")
//		  .append(" sso_user_test as test,")
//		  .append(" sso_user_test_item as testitem,")
//		  .append(" item as item,")
//		  .append(" item_type as itemtype")
//		  .append(" where")
//		  .append(" test.ssoid=").append(userId)//指定用户;
//		  .append(" and test.id=testitem.user_test")//由用户关联到用户做题的记录;
//		  .append(" and test.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE)
////		  .append(" and testitem.isanswered=").append(Dictionary.SSO_USER_TEST_ITEM_ANSWERED)//统计做过的题目;
//		  .append(" and testitem.item=item.id")//由题目 关联到 题目的详细信息;
//		  .append(" and item.status=").append(Dictionary.ITEM_STATUS_RELEASE)//题目已发布;
//		  .append(" and item.subject=").append(subjectId)//得到指定学科的题目;
//		  .append(" and item.type=itemtype.id")//关联题目类型;
//		  .append(" and itemtype.is_standard=").append(Dictionary.ITEM_ANSWER_TYPESTANDARD)//题目类型为 标准试题;
//		  .append(" group by testitem.item;");//同一题目如果用户做过多层次，则取最后一次;
//		
//		super.executeSQL(sb.toString());
//		this.clear(sb);
//		
//		//4,create table resultTable: return to the client;
//		sb.append(" create temporary table ").append(resultTable)
//		  .append(" select id,name,level,scode,parent,0 as rightCount")
//		  .append(" from knowledge")
//		  .append(" where status=").append(Dictionary.KNOWLEDGE_STATUS_NOMAL)//知识点处于有效状态；
//		  .append(" and subject=").append(subjectId);//指定科目的知识点；
//		
//		super.executeSQL(sb.toString());
//		this.clear(sb);
//		
//		//5,updata rightCount;
//		sb.append(" update ").append(resultTable).append(" as rt")
//		  .append(" set rt.rightCount=")
//		  .append("(")
//		  .append("select count(*) from ").append(itemRecords).append(" as itemRecords")
//		  .append(" where itemRecords.complete_type=").append(Dictionary.SSO_USER_TEST_ITEM_CORRECT)//题目的答案正确;
//		  .append(" and itemRecords.knowladge in")
//		  .append("(")
//		  .append(" select child.id from knowledge parent,knowledge child where child.scode like concat(parent.scode,'%')  and rt.id=parent.id")//得到每个知识点的子孙知识点;
//		  .append(")")
//		  .append(");");
//		
//		super.executeSQL(sb.toString());
//		this.clear(sb);
//		
//		//6,return map;
//		sb.append(" select rt.*, itemcount.standard_released_itemcount as totalCount")
//		  .append(" from ").append(resultTable).append(" as rt, knowledge_itemcount as itemcount")
//		  .append(" where rt.id=itemcount.knowledgeid");//关联knowledge_itemcount,得到totalCount;
//		
//		List<Object[]> list=super.getBySQL(sb.toString());
//		this.clear(sb);
//		
//		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
//		for(Object[] objs:list)
//		{
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put("id", objs[0]);
//			map.put("name", objs[1]);
//			map.put("level", objs[2]);
//			map.put("scode", objs[3]);
//			map.put("parent", objs[4]);
//			map.put("rightCount", objs[5]);
//			map.put("totalCount", objs[6]);
//			
//			listMap.add(map);
//		}
//		
//		//7,drop table;
//		sb.append("drop table ").append(itemRecords);
//		super.executeSQL(sb.toString());
//		this.clear(sb);
//		
//		sb.append("drop table ").append(resultTable);
//		super.executeSQL(sb.toString());
//		this.clear(sb);
//		
//		
//		
//		
//		return listMap;
//	}
	
	//清空sb；
	
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
