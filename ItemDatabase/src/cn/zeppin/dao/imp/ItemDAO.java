/** 
 * Project Name:ItemDatabase 
 * File Name:ItemDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Item;

/**
 * ClassName: ItemDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月10日 下午9:26:34 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class ItemDAO extends HibernateTemplateDAO<Item, Integer> implements IItemDAO {

	/**
	 * 按条件查询试题数量
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchItemCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from Item i where 1=1 ");
		// ID
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			sb.append(" and i.id=").append(searchMap.get("id"));
		}
		// 学段ID
		if (searchMap.get("grade.id") != null && !searchMap.get("grade.id").equals("")) {
			sb.append(" and i.grade.id=").append(searchMap.get("grade.id"));
		}
		// level
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")) {
			sb.append(" and i.level=").append(searchMap.get("level"));
		}
		// 学段scode
		if (searchMap.get("grade.scode") != null && !searchMap.get("grade.scode").equals("")) {
			sb.append(" and i.grade.scode like'").append(searchMap.get("grade.scode")).append("%'");
		}

		// 学科ID
		if (searchMap.get("subject.id") != null && !searchMap.get("subject.id").equals("")) {
			sb.append(" and i.subject.id=").append(searchMap.get("subject.id"));
		}
		// 题型ID
		if (searchMap.get("itemType.id") != null && !searchMap.get("itemType.id").equals("")) {
			sb.append(" and i.itemType.id=").append(searchMap.get("itemType.id"));
		}
		// 知识点ID
		if (searchMap.get("knowledge.id") != null && !searchMap.get("knowledge.id").equals("")) {
			sb.append(" and i.knowledge.id=").append(searchMap.get("knowledge.id"));
		}
		// 知识点scode
		if (searchMap.get("knowledge.scode") != null && !searchMap.get("knowledge.scode").equals("")) {
			sb.append(" and i.knowledge.scode like '").append(searchMap.get("knowledge.scode")).append("%'");
		}
		// 按题的内容进行搜索
		if (searchMap.get("content") != null && !searchMap.get("content").equals("")) {
			sb.append(" and (i.content like '%").append(searchMap.get("content")).append("%')");
		}
		// 教材ID
		if (searchMap.get("textbookCapter.id") != null && !searchMap.get("textbookCapter.id").equals("")) {
			sb.append(" and i.textbookCapter.id=").append(searchMap.get("textbookCapter.id"));
		}
		// 教材scode
		if (searchMap.get("textbookCapter.scode") != null && !searchMap.get("textbookCapter.scode").equals("")) {
			sb.append(" and i.textbookCapter.scode like '").append(searchMap.get("textbookCapter.scode")).append("%'");
		}
		// 来源类型（真题、模拟考试、教材配套）
		if (searchMap.get("sourcetype") != null && !searchMap.get("sourcetype").equals("")) {
			sb.append(" and i.sourceType=").append(searchMap.get("sourcetype"));
		}
		// 来源
		if (searchMap.get("source") != null && !searchMap.get("source").equals("")) {
			sb.append(" and i.source like '%").append(searchMap.get("source")).append("%'");
		}
		// 创建者机构（用于合作机构搜索）
		if (searchMap.get("sysUser.organization.id") != null && !searchMap.get("sysUser.organization.id").equals("")) {
			sb.append(" and i.sysUser.organization.id=").append(searchMap.get("sysUser.organization.id"));
		}
		// 开始时间
		if (searchMap.get("startTime") != null && !searchMap.get("startTime").equals("")) {
			sb.append(" and i.updatetime > '").append(searchMap.get("startTime")).append("'");
		}
		
		// 是否组合题
		if (searchMap.get("isgroup") != null && !searchMap.get("isgroup").equals("")) {
			sb.append(" and i.isGroup=").append(searchMap.get("isgroup"));
		}
		
		// 出版社外部
		if (searchMap.get("publisherFcode") != null && !searchMap.get("publisherFcode").equals("")) {
			sb.append(" and i.textbookCapter.textbook.publisher.fcode='").append(searchMap.get("publisherFcode")).append("'");
		}
		
		// 学科外部
		if (searchMap.get("subjectFcode") != null && !searchMap.get("subjectFcode").equals("")) {
			sb.append(" and i.subject.fcode='").append(searchMap.get("subjectFcode")).append("'");
		}
		
		// 年级外部
		if (searchMap.get("gradeFcode") != null && !searchMap.get("gradeFcode").equals("")) {
			sb.append(" and i.grade.fcode like '%").append(searchMap.get("gradeFcode")).append("%'");
		}
		
		// 是否判断未更新已删除
		if (searchMap.get("isdelete") != null && !searchMap.get("isdelete").equals("") && searchMap.get("startTime") != null && !searchMap.get("startTime").equals("")) {
			sb.append(" and (i.status in (1,3) or i.createtime <= '").append(searchMap.get("startTime")).append("')");
		}
		
		// 状态
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			sb.append(" and i.status=").append(searchMap.get("status"));
		} else if (searchMap.get("statusRange") != null && !searchMap.get("statusRange").equals("")) {
			sb.append(" and i.status in ").append(searchMap.get("statusRange"));
		}else{
			sb.append(" and i.status!=0");
		}
		
		// 时间
		if (searchMap.get("createtime") != null && !searchMap.get("createtime").equals("")) {
			sb.append(" and i.createtime>='").append(searchMap.get("createtime")).append("'");
		}
		if (searchMap.get("endtime") != null && !searchMap.get("endtime").equals("")) {
			sb.append(" and i.createtime<='").append(searchMap.get("endtime")).append("'");
		}
		return ((Long) this.getResultByHQL(sb.toString())).intValue();
	}

	/**
	 * 按条件查询试题列表
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/>
	 * @param searchMap
	 * @return List<Item>
	 */
	@Override
	public List<Item> getItemsForPhone(Map<String, Object> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append(" select i from Item i,TestPaperItem tpi,Paper p where p.id=tpi.paper and i.id=tpi.item ");
		if (searchMap.get("paper.id") != null && !searchMap.get("paper.id").equals("")) {
			sb.append(" and p.id=").append(searchMap.get("paper.id"));
		}
		
		sb.append(" order by tpi.inx");
		
		return this.getByHQL(sb.toString());
	}
	
	/**
	 * 按条件查询试题列表
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Item>
	 */
	@Override
	public List<Item> searchItem(Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from Item i where 1=1 ");
		// ID
		if (searchMap.get("id") != null && !searchMap.get("id").equals("")) {
			sb.append(" and i.id=").append(searchMap.get("id"));
		}
		// 学段ID
		if (searchMap.get("grade.id") != null && !searchMap.get("grade.id").equals("")) {
			sb.append(" and i.grade.id=").append(searchMap.get("grade.id"));
		}
		// 学段scode
		if (searchMap.get("grade.scode") != null && !searchMap.get("grade.scode").equals("")) {
			sb.append(" and i.grade.scode like'").append(searchMap.get("grade.scode")).append("%'");
		}
		// level
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")) {
			sb.append(" and i.level=").append(searchMap.get("level"));
		}
		// 学科ID
		if (searchMap.get("subject.id") != null && !searchMap.get("subject.id").equals("")) {
			sb.append(" and i.subject.id=").append(searchMap.get("subject.id"));
		}
		// 题型ID
		if (searchMap.get("itemType.id") != null && !searchMap.get("itemType.id").equals("")) {
			sb.append(" and i.itemType.id=").append(searchMap.get("itemType.id"));
		}
		// 知识点ID
		if (searchMap.get("knowledge.id") != null && !searchMap.get("knowledge.id").equals("")) {
			sb.append(" and i.knowledge.id=").append(searchMap.get("knowledge.id"));
		}
		// 知识点scode
		if (searchMap.get("knowledge.scode") != null && !searchMap.get("knowledge.scode").equals("")) {
			sb.append(" and i.knowledge.scode like '").append(searchMap.get("knowledge.scode")).append("%'");
		}
		// 按题的内容进行搜索
		if (searchMap.get("content") != null && !searchMap.get("content").equals("")) {
			sb.append(" and (i.content like '%").append(searchMap.get("content")).append("%')");
		}
		// 教材ID
		if (searchMap.get("textbookCapter.id") != null && !searchMap.get("textbookCapter.id").equals("")) {
			sb.append(" and i.textbookCapter.id=").append(searchMap.get("textbookCapter.id"));
		}
		// 教材scode
		if (searchMap.get("textbookCapter.scode") != null && !searchMap.get("textbookCapter.scode").equals("")) {
			sb.append(" and i.textbookCapter.scode like '").append(searchMap.get("textbookCapter.scode")).append("%'");
		}
		// 来源类型（真题、模拟考试、教材配套）
		if (searchMap.get("sourcetype") != null && !searchMap.get("sourcetype").equals("")) {
			sb.append(" and i.sourceType=").append(searchMap.get("sourcetype"));
		}
		// 来源
		if (searchMap.get("source") != null && !searchMap.get("source").equals("")) {
			sb.append(" and i.source like '%").append(searchMap.get("source")).append("%'");
		}
		// 创建者机构（用于合作机构搜索）
		if (searchMap.get("sysUser.organization.id") != null && !searchMap.get("sysUser.organization.id").equals("")) {
			sb.append(" and i.sysUser.organization.id=").append(searchMap.get("sysUser.organization.id"));
		}
		// 难易级别
		if (searchMap.get("diffcultyLevel") != null && !searchMap.get("diffcultyLevel").equals("")) {
			sb.append(" and i.diffcultyLevel=").append(searchMap.get("diffcultyLevel"));
		}
		
		// 开始时间
		if (searchMap.get("startTime") != null && !searchMap.get("startTime").equals("")) {
			sb.append(" and i.updatetime > '").append(searchMap.get("startTime")).append("'");
		}
		
		// 是否组合题
		if (searchMap.get("isgroup") != null && !searchMap.get("isgroup").equals("")) {
			sb.append(" and i.isGroup=").append(searchMap.get("isgroup"));
		}
		
		// 出版社外部
		if (searchMap.get("publisherFcode") != null && !searchMap.get("publisherFcode").equals("")) {
			sb.append(" and i.textbookCapter.textbook.publisher.fcode='").append(searchMap.get("publisherFcode")).append("'");
		}
		
		// 学科外部
		if (searchMap.get("subjectFcode") != null && !searchMap.get("subjectFcode").equals("")) {
			sb.append(" and i.subject.fcode='").append(searchMap.get("subjectFcode")).append("'");
		}
		
		// 年级外部
		if (searchMap.get("gradeFcode") != null && !searchMap.get("gradeFcode").equals("")) {
			sb.append(" and i.grade.fcode like '%").append(searchMap.get("gradeFcode")).append("%'");
		}
		
		// 是否判断未更新已删除
		if (searchMap.get("isdelete") != null && !searchMap.get("isdelete").equals("") && searchMap.get("startTime") != null && !searchMap.get("startTime").equals("")) {
			sb.append(" and (i.status in (1,3) or i.createtime <= '").append(searchMap.get("startTime")).append("')");
		}
				
		// 状态
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			sb.append(" and i.status=").append(searchMap.get("status"));
		} else if (searchMap.get("statusRange") != null && !searchMap.get("statusRange").equals("")) {
			sb.append(" and i.status in ").append(searchMap.get("statusRange"));
		}else{
			sb.append(" and i.status!=0");
		}
		
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("i.").append(sort);
				comma = ",";
			}
		}

		return this.getByHQL(sb.toString(), offset, pagesize);
	}

	/**
	 * 获取下级所有试题
	 * 
	 * @author jiangfei
	 * @date: 2014年9月2日 下午12:03:55 <br/>
	 * @param item
	 * @return
	 */
	public List<Item> getItemsByItem(int item) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from Item i where 1=1 ");
		sb.append(" and i.parent=").append(item);

		return this.getByHQL(sb.toString());
	}

}
