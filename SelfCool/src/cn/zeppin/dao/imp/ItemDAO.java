/** 
 * Project Name:Self_Cool 
 * File Name:ItemDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.zeppin.dao.api.IItemDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;
import cn.zeppin.utility.Dictionary;

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
	 * param:userId, subject:
	 * return: 获取 刷题量和 正确数;
	 */
	public Map<String,Object> selectUserSubjectItemCount(Map<String,Object> searchMap)
	{
		//1,获取参数;
		int userId=(int) searchMap.get("user.id");
		int subjectId=(int) searchMap.get("subject.id");
		
		//2,sql创建工具;
		StringBuilder sb=new StringBuilder();
		int randomNum = new Random().nextInt(1000);
		String itemRecords="itemRecords"+randomNum;
		
		
		/**
		 * 3,统计 正确数 和刷题数 的思路:
		 * 
		 * 1)如果用户一个题目做了多次，则只统计最后一次，
		 * 
		 * 2)complete_type:1 正确; isanswered:1 做过;
		 */
		//3.1 需要创建一个 只统计 用户最后一次答 相同题目的记录 的表;
		//因为需要统计同一个题的最后一次，所以需要建临时表;
		sb.append(" create temporary table ").append(itemRecords)
		  .append(" select ")
		  .append(" max(testitem.id),testitem.item, testitem.complete_type")
		  .append(" from")
		  .append(" sso_user_test as test,")
		  .append(" sso_user_test_item as testitem")
		  .append(" where")
		  .append(" test.ssoid=").append(userId)
		  .append(" and test.id=testitem.user_test")
		  .append(" and testitem.complete_type=").append(Dictionary.SSO_USER_TEST_ITEM_ANSWERED)//用户已经回答过;
		  .append(" group by testitem.item");
		
		int doneItemCount=super.executeSQL(sb.toString());
		this.clear(sb);
		
		
		//3.2 根据item表和 itemRecords表,统计答题正确数;
		sb.append(" select")
		  .append(" count(*)")
		  .append(" from")
		  .append(" item as item,")
		  .append(itemRecords).append(" as itemRecords")
		  .append(" where")
		  .append(" item.status=").append(Dictionary.ITEM_STATUS_RELEASE)//题目处于 已发布 状态;
		  .append(" and item.subject=").append(subjectId)
		  .append(" and item.id=itemRecords.item")//两个表关联;
		  .append(" and itemRecords.complete_type=").append(Dictionary.SSO_USER_TEST_ITEM_CORRECT);//用户答题正确;
	   //3.3获取 答题正确数;
	  Object rightCount=super.getResultBySQL(sb.toString());
	  this.clear(sb);
		
		
	  //4,将两个数据返回;
	  Map<String,Object> map=new HashMap<String,Object>();
	  map.put("doneItemCount", doneItemCount);
	  map.put("rightCount", rightCount);
		
	  return map;
	}
	
	
	public void clear(StringBuilder sb)
	{
		sb.delete(0, sb.length());
	}
	
	
	
	/**
	 * 按条件查询试题数量并按题型分组
	 * 
	 * @author Clark
	 * @date 2014年7月29日上午10:35:25
	 * @param searchMap
	 * @return List<count>
	 */
	public  List<Map<String, Object>> searchItemCountGroupByItemType(Map<String, Object> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append(" select i.type, count(*) from Item i where 1=1 ");
		//组合题的数量不算level=1的题记录，存储主要是材料
		sb.append(" and (i.is_group=0 or (i.is_group = 1 and i.level=2))");

		// 学科ID
		if (searchMap.get("subject.id") != null && !searchMap.get("subject.id").equals("")) {
			sb.append(" and i.subject =").append(searchMap.get("subject.id"));
		}
		// 状态
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			sb.append(" and i.status=").append(searchMap.get("status"));
		} else {
			sb.append(" and i.status!=0");
		}
		sb.append(" group by i.type");	
		List<Object[]> rst = this.getBySQL(sb.toString(), -1, -1);
		
		List<Map<String, Object>> results = new ArrayList<>();

		for (Object[] obj : rst) {
			Map<String, Object> itemMap = new HashMap<>();
			int i = 0;
			itemMap.put("id", obj[i++]);
			itemMap.put("count", obj[i++]);

			results.add(itemMap);
		}
		
		return results;
	}
	
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
		//组合题的数量不算level=1的题记录，存储主要是材料
		sb.append(" and (i.isGroup=0 or (i.isGroup = 1 and i.level=2))");
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
		// 题型ID
		if (searchMap.get("itemType.isStandard") != null && !searchMap.get("itemType.isStandard").equals("")) {
			sb.append(" and i.itemType.isStandard=").append(searchMap.get("itemType.isStandard"));
		}

		// 题型模板
		if (searchMap.get("itemType.model") != null && !searchMap.get("itemType.model").equals("")) {
			String itemTypeStr = searchMap.get("itemType.model").toString();
			if (itemTypeStr.indexOf(",") > 0) {
				sb.append(" and i.itemType.modelType in ( ").append(searchMap.get("itemType.model")).append(" ) ");
			} else {
				sb.append(" and i.itemType.modelType=").append(searchMap.get("itemType.model"));
			}
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
		// 状态
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			sb.append(" and i.status=").append(searchMap.get("status"));
		} else {
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
		// 题型模板
		if (searchMap.get("itemType.model") != null && !searchMap.get("itemType.model").equals("")) {
			String itemTypeStr = searchMap.get("itemType.model").toString();
			if (itemTypeStr.indexOf(",") > 0) {
				sb.append(" and i.itemType.modelType in ( ").append(searchMap.get("itemType.model")).append(" ) ");
			} else {
				sb.append(" and i.itemType.modelType=").append(searchMap.get("itemType.model"));
			}
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
			// sb.append(" or (i.material.content like '%").append(searchMap.get("content")).append("%'))");
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
		// 状态
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			sb.append(" and i.status=").append(searchMap.get("status"));
		} else {
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

	/**
	 * 为某个用户按学科知识点智能进行出题（选择哪些题让用户做和他们的排序，是一个非常最复杂的算法逻辑，需要考虑的因素比较多，需要理解后进行修改）
	 * 设计思路： 首先应该计算用户该知识点下做过的题的次数以及正确的次数，根据选择最少正确次数的题的原则，在集合中进行随机出题
	 * ***暂时不考虑找出的试题不够数量的情况，暂时不考虑标准化组合题的情况（如阅读理解）
	 * Ver1.0只考虑单选题，因为都是单选题，所以也不需要太复杂的算法逻辑
	 * Ver1.2考虑了组合题和其他题型
	 * 	 * Ver1.2版本中已经实现组合体的读取，逻辑如下
	 *     先按照出题算法，取出10道level=1的试题，由于组合题在sso_user_test_item_count中没有记录，需要将组合题单独计算，然后合并查询结果，进行出题
	 *     拿到出题后，循环遍历其中是否有组合题
	 *     如果只取到一个组合题，若组合题数量的试题数量大于出题数量时，以组合题数量为准（比如完形填空就是20个试题为一组）
	 *     先按上述算法取出一组单题，再按上述算法取出一道组合题，然后他们PK，如果组合题应该出现在返回试题中，则将试题进行排序，如果没有组合题或者根据算法不应返回，则直接排序
	 *     排序规则：1、单选 2、判断 3、多选  4、填空  5、组合 （以后台题型录入顺序为准, 组合题因为不是一个题型，所以放到最后）
	 *     
	 * @param searchMap
	 * @param defaultItemNumber
	 * @return List<Item>
	 */

	@Override
	public List<Map<String, Object>> selectItems(Map<String, Object> searchMap, int defaultItemNumber) {
		/**
		 * 	    searchMap.put("subject.id", request.getParameter("subject.id"));
				searchMap.put("knowledge.scode", knowledge.getScode());
				searchMap.put("user.id", currentUser.getId());
				searchMap.put("itemType.isStandard", Dictionary.ITEM_ANSWER_TYPESTANDARD);
				searchMap.put("status", Dictionary.KNOWLEDGE_STATUS_NOMAL);
		 */
		
		//先判断符合条件的试题数量是否大于出题数
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(*) from item_type it, knowledge k, item i");
		sql.append("  left outer join (test_paper_item tpi  join paper p on tpi.test_paper = p.id and p.type <> 2) on tpi.item = i.id ");
		sql.append(" where i.knowladge=k.id and i.type=it.id");
		sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		// 学科ID
		sql.append(" and i.subject=").append(searchMap.get("subject.id"));
		// 题型是标准化试题
		sql.append(" and it.is_standard=").append(searchMap.get("itemType.isStandard"));
		// 知识点scode
		if (searchMap.get("knowledge.scode") != null && !searchMap.get("knowledge.scode").equals("")) {
			sql.append(" and k.scode like '").append(searchMap.get("knowledge.scode")).append("%'");
		}
		sql.append(" and ((i.is_group=0 and i.level=1) or (i.is_group = 1 and i.level=2)) ");
		sql.append(" and (p.isfree = 1 or p.isfree is null) ");
		sql.append(" and i.id not in (");
		sql.append(" select suti.item from sso_user_test sut, sso_user_test_item suti");
		sql.append(" where sut.id = suti.user_test");
		sql.append(" and sut.createtime >= date_sub(now(),INTERVAL 15 MINUTE)");  //与定时任务周期相同
		sql.append(" and sut.ssoid=").append(searchMap.get("user.id"));
		sql.append(" and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
		sql.append(" and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
		sql.append(" )");

		Integer enableSelectItemCount = Integer.valueOf(this.getResultBySQL(sql.toString()).toString());
		
		
		//开始选择合适的试题，选择方法为：
		//1、按照每题“最少做题次数” =（做题总次数 + 做题正确次数 - 做题错误次数）的正序序列排序中，选择“最少做题次数”的试题
		//2、同等条件（“最少做题次数”）的试题，随机进行选择
		//3、最近15分钟内做过的题不再出现在可选择的序列中（如果enableSelectItemCount >= defaultItemNumber的话）
		//4、选择完成后，应该按照试题的类型进行排序，如单选放在前面，多选放在后面
		
		
//		String split = searchMap.get("split") == null ?  "." : searchMap.get("split").toString();
		/**
		 * 先计算is_group==0的简单题，然后再计算组合题的test_item_count，test_item_correct_count，test_item_wrong_count等各项平均值
		 * 然后再合并union all，再进行排序（排序算法按照计算值与随机相结合的办法），再进行前几名试题的取出
		 */	
		sql.delete(0, sql.length());
		
		sql.append(" select id, type, name, modeltype, is_group, inx, level, diffculty_level");
		sql.append(" , default_score, source_type, source, parent, content_backup, analysis");
		sql.append(" , test_item_count, test_item_correct_count, test_item_wrong_count, sortcount");
		sql.append(" from (");
		//简单题
		sql.append(" (");
		sql.append(" select i.id, i.type, it.name, i.modeltype, i.is_group, ia.inx, i.level,i.diffculty_level");
		sql.append(" ,i.default_score, i.source_type, i.source, i.parent, i.content_backup, i.analysis");
		sql.append(" ,sutic.test_item_count, sutic.test_item_correct_count, sutic.test_item_wrong_count");
		sql.append(" ,(sutic.test_item_count + sutic.test_item_correct_count - sutic.test_item_wrong_count) as sortcount");
		sql.append(" from item i ");
		sql.append(" join item_type it on i.type = it.id and it.is_standard=").append(searchMap.get("itemType.isStandard"));
		sql.append(" join item_answer ia on i.id = ia.item");

		if (searchMap.get("knowledge.scode") != null && !searchMap.get("knowledge.scode").equals("")) {
			sql.append(" join knowledge k on i.knowladge = k.id");
			sql.append(" and k.scode like '").append(searchMap.get("knowledge.scode")).append("%'");
		}
		sql.append(" left join sso_user_test_item_count sutic");  //排序左连接
		sql.append("        on i.id = sutic.itemid");
		sql.append("        and ia.inx = sutic.blank_inx");
		sql.append("        and sutic.userid=").append(searchMap.get("user.id"));
		sql.append("  left outer join (test_paper_item tpi  join paper p on tpi.test_paper = p.id and p.type <> 2) on tpi.item = i.id ");
		sql.append(" where i.subject=").append(searchMap.get("subject.id"));
		sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append(" and i.is_group=0 and i.level=1");  //简单题
		sql.append(" and (p.isfree = 1 or p.isfree is null) ");
		//如果可选择的试题数够，就不选15分钟之内做过的题
		if (enableSelectItemCount >= defaultItemNumber) {
			sql.append(" and i.id not in (");
			sql.append("     select suti.item from sso_user_test sut, sso_user_test_item suti");
			sql.append("     where sut.id = suti.user_test");
			sql.append("     and sut.createtime >= date_sub(now(), interval 15 minute)");  //与定时任务周期相同,900second
			sql.append("     and sut.ssoid=").append(searchMap.get("user.id"));
			sql.append("     and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
			sql.append("     and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
			sql.append(" )");
		}
		sql.append(" )");
		sql.append(" union all ");
		sql.append(" (");
		//组合题
		sql.append(" select i.id, i.type, it.name, i.modeltype, i.is_group, 1 as inx, i.level,i.diffculty_level");
		sql.append(" ,i.default_score, i.source_type, i.source, i.parent, i.content_backup, i.analysis");
		sql.append(" ,level1item.test_item_count, level1item.test_item_correct_count, level1item.test_item_wrong_count");
		sql.append(" ,(level1item.test_item_count + level1item.test_item_correct_count - level1item.test_item_wrong_count) as sortcount");
		sql.append(" from item i ");
		sql.append(" join item_type it on i.type = it.id and it.is_standard=").append(searchMap.get("itemType.isStandard"));
		
		if (searchMap.get("knowledge.scode") != null && !searchMap.get("knowledge.scode").equals("")) {
			sql.append(" join knowledge k on i.knowladge = k.id");
			sql.append(" and k.scode like '").append(searchMap.get("knowledge.scode")).append("%'");
		}
		sql.append("  left outer join (test_paper_item tpi  join paper p on tpi.test_paper = p.id and p.type <> 2) on tpi.item = i.id ");
		sql.append(" left join                          ");  //排序左连接
		//计算组合题level=1的答题统计平均值（按子试题记录值取平均数），取得集合
		sql.append("       (  select level2item.parent as id ");
		sql.append("                 ,avg(test_item_count) as test_item_count");
		sql.append("                 ,avg(test_item_correct_count) as test_item_correct_count");
		sql.append("                 ,avg(test_item_wrong_count) as test_item_wrong_count");
		sql.append("          from sso_user_test_item_count sutic, item level2item");
		sql.append("          where sutic.itemid=level2item.id");
		sql.append("                and level2item.is_group=1 and level2item.level=2");
		sql.append("                and level2item.subject=").append(searchMap.get("subject.id"));
		sql.append("                and level2item.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append("                and sutic.userid=").append(searchMap.get("user.id"));
		sql.append("          group by level2item.parent ");
		sql.append("       )  as level1item");
		sql.append("     on level1item.id = i.id");
		sql.append(" where i.subject=").append(searchMap.get("subject.id"));
		sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append(" and i.is_group=1 and i.level=1");  //组合题一级
		sql.append(" and (p.isfree = 1 or p.isfree is null) ");
		if (enableSelectItemCount >= defaultItemNumber) {
			sql.append(" and i.id not in (");
			sql.append("     select distinct level2item.parent as id from sso_user_test sut, sso_user_test_item suti, item level2item");
			sql.append("     where sut.id = suti.user_test and suti.item = level2item.id");
			sql.append("     and sut.createtime >= date_sub(now(), interval 15 minute)");  //与定时任务周期相同,900second
			sql.append("     and sut.ssoid=").append(searchMap.get("user.id"));
			sql.append("     and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
			sql.append("     and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
			sql.append(" )");
		}
		
		sql.append(" )");
	
		sql.append(" ) as result");
		sql.append(" order by sortcount, rand()");
		List<Object[]> rst = this.getBySQL(sql.toString(), 0, defaultItemNumber);
		List<Map<String,Object>> originalResult = new ArrayList<>();
		List<Map<String,Object>> targetResult = new ArrayList<>();
		 
		Boolean groupFlag = false;
		List<Integer> groupItemIds = new ArrayList<>();
		
		
		for (Object[] obj : rst) {
			Map<String, Object> itemMap = new HashMap<>();
			int i = 0;
			itemMap.put("id", obj[i++]);
			itemMap.put("type", obj[i++]);
			itemMap.put("typename", obj[i++]);
			itemMap.put("modeltype", obj[i++]);
			
			//用集合方式取不出Boolean，需要转换
			itemMap.put("isgroup", (Byte) obj[i++] > 0);
			groupFlag = groupFlag || ((Boolean) itemMap.get("isgroup"));
			if ((Boolean) itemMap.get("isgroup")) {
				groupItemIds.add((Integer) itemMap.get("id"));
			}
			itemMap.put("inx", obj[i++]);
			itemMap.put("level", obj[i++]);
			itemMap.put("diffcultyLevel", obj[i++]);
			
			itemMap.put("defaultScore", obj[i++]);
			itemMap.put("sourceType", obj[i++]);
			itemMap.put("source", obj[i++]);
			itemMap.put("parent", obj[i++]);
			itemMap.put("content", obj[i++]);
			itemMap.put("analysis", obj[i++]);
			itemMap.put("test_item_count", obj[i++]);
			itemMap.put("test_item_correct_count", obj[i++]);
			itemMap.put("test_item_wrong_count", obj[i++]);
			
			originalResult.add(itemMap);
		}
		
		if (groupFlag && groupItemIds.size() > 0 ) {
			//按照isgroup排序，将isgroup=true的都排在前面，便于处理
	        Collections.sort(originalResult, Collections.reverseOrder(new Comparator<Map<String,Object>>() {
	            public int compare(Map<String,Object> item0, Map<String,Object> item1) {
	            	Boolean item0isgroup = (Boolean) item0.get("isgroup");
	            	Boolean item1isgroup = (Boolean) item1.get("isgroup");
	                return item0isgroup.compareTo(item1isgroup);
	            }
	        }));
			
	        //将组合题的子试题全部加载，并计算返回试题数量，控制这个返回数量
	        //因上面进行了倒序排列，所以组合题都在列表的最前面,最优先处理，一些逻辑也可以成立
	        Integer returnItemNumber = 0;
			for (Map<String, Object> parentItemMap : originalResult){
				targetResult.add(parentItemMap);
				if ((Boolean) parentItemMap.get("isgroup")) {
					sql.delete(0, sql.length());
					sql.append(" select i.id, i.type, it.name, i.modeltype, i.is_group, ia.inx, i.level,i.diffculty_level");
					sql.append(" ,i.default_score, i.source_type, i.source, i.parent, i.content_backup, i.analysis");
					sql.append(" ,sutic.test_item_count, sutic.test_item_correct_count, sutic.test_item_wrong_count");
					sql.append(" ,(sutic.test_item_count + sutic.test_item_correct_count - sutic.test_item_wrong_count) as sortcount");
					sql.append(" from item i ");
					sql.append(" join item_type it on i.type = it.id and it.is_standard=").append(searchMap.get("itemType.isStandard"));
					sql.append(" join item_answer ia on i.id = ia.item");
					sql.append(" left join sso_user_test_item_count sutic");  //排序左连接
					sql.append("        on i.id = sutic.itemid");
					sql.append("        and ia.inx = sutic.blank_inx");
					sql.append("        and sutic.userid=").append(searchMap.get("user.id"));
					sql.append(" where i.parent=").append(parentItemMap.get("id"));
//					sql.append(" order by i.id ");
					
					List<Object[]> objs = this.getBySQL(sql.toString(), 0, defaultItemNumber);
//					List<Map<String,Object>> childrenItemList = new ArrayList<>();
					for (Object[] obj : objs) {
						Map<String, Object> childItemMap = new HashMap<>();
						int i = 0;
						childItemMap.put("id", obj[i++]);
						childItemMap.put("type", obj[i++]);
						childItemMap.put("typename", obj[i++]);
						childItemMap.put("modeltype", obj[i++]);
						childItemMap.put("isgroup", obj[i++]);

						childItemMap.put("inx", obj[i++]);
						childItemMap.put("level", obj[i++]);
						childItemMap.put("diffcultyLevel", obj[i++]);

						childItemMap.put("defaultScore", obj[i++]);
						childItemMap.put("sourceType", obj[i++]);
						childItemMap.put("source", obj[i++]);
						childItemMap.put("parent", obj[i++]);
						childItemMap.put("content", obj[i++]);
						childItemMap.put("analysis", obj[i++]);
						childItemMap.put("test_item_count", obj[i++]);
						childItemMap.put("test_item_correct_count", obj[i++]);
						childItemMap.put("test_item_wrong_count", obj[i++]);
						
						targetResult.add(childItemMap);
//						childrenItemList.add(childItemMap);
						
					}
					returnItemNumber = returnItemNumber + objs.size();
				}
				else {
					returnItemNumber ++;
				}
				/**
				 * 因为组合题被加载，所以总题数可能超过预先设计的默认返回题数很多，所以在这里尽量返回题数保证在默认值
				 */
				if (returnItemNumber >= defaultItemNumber) {
					break;
				}
			}
			
			Collections.sort(targetResult, new Comparator<Map<String,Object>>() {
	            public int compare(Map<String,Object> item0, Map<String,Object> item1) {
	            	Integer item0Level = (Integer) item0.get("level");
	            	Integer item1Level = (Integer) item1.get("level");
	            	Integer item0Id = (Integer) item0.get("id");
	            	Integer item1Id = (Integer) item1.get("id");
	            	if (item0Level.compareTo(item1Level) == 0){
	            		return item0Id.compareTo(item1Id);
	            	}
	                return item0Level.compareTo(item1Level);
	            }
	        });
		}
		else {
			targetResult.addAll(originalResult);
		}
		
		return targetResult;
		
	}
	
	

	@Override
	public List<Item> searchItemForPaper(int paperId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select i from Item i,TestPaperItem tpi,Paper p where p.id=tpi.paper and i.id=tpi.item ");
//		if (searchMap.get("paper.id") != null && !searchMap.get("paper.id").equals("")) {
//			sb.append(" and p.id=").append(searchMap.get("paper.id"));
//		}
		if(paperId >= 0){
			sb.append(" and p.id=").append(paperId);
		}
		sb.append(" order by tpi.inx");

		return this.getByHQL(sb.toString());
	}


	/**
	 * 通过学科、题型、用户获取试题相关信息
	 * 
	 * @param currentUser
	 * @param subject
	 * @param itemType
	 * @return
	 */
	@Override
	public List<Map<String, Object>> searchItems(SsoUser currentUser, Subject subject, ItemType itemType) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" select i.id, i.type, it.name, i.modeltype, i.is_group, ia.inx, i.level, i.diffculty_level");
		sql.append(" ,i.default_score, i.source_type, i.source, i.parent");
		sql.append(" , i.content_backup, i.analysis, sutic.last_test_item_complete_type");
		sql.append(" from item i ");
		sql.append(" join item_type it on i.type = it.id ");
		sql.append(" left join item_answer ia on i.id = ia.item");
		sql.append(" join knowledge k on i.knowladge = k.id");
		sql.append(" left join sso_user_test_item_count sutic");  //排序左连接
		sql.append("        on i.id = sutic.itemid");
//		sql.append("        and ia.inx = sutic.blank_inx");
		sql.append("        and sutic.blank_inx = 1");
		sql.append("        and sutic.userid = ").append(currentUser.getId());
		sql.append("  left outer join (test_paper_item tpi  join paper p on tpi.test_paper = p.id and p.type <> 2) on tpi.item = i.id");
		sql.append(" where i.subject = ").append(subject.getId());
		sql.append(" and i.type = ").append(itemType.getId());
		sql.append(" and i.status = ").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append(" and (p.isfree = 1 or p.isfree is null) ");
		sql.append(" order by i.level, k.scode");
		
		List<Object[]> rst = this.getBySQL(sql.toString(), -1, -1);
		
		
		List<Map<String, Object>> results = new ArrayList<>();

		for (Object[] obj : rst) {
			Map<String, Object> itemMap = new HashMap<>();
			int i = 0;
			itemMap.put("id", obj[i++]);
			itemMap.put("type", obj[i++]);
			itemMap.put("typename", obj[i++]);
			itemMap.put("modeltype", obj[i++]);
			itemMap.put("isgroup", obj[i++]);
			itemMap.put("inx", obj[i++]);
			itemMap.put("level", obj[i++]);
			itemMap.put("diffcultyLevel", obj[i++]);
			itemMap.put("defaultScore", obj[i++]);
			itemMap.put("sourceType", obj[i++]);
			itemMap.put("source", obj[i++]);
			itemMap.put("parent", obj[i++]);
			itemMap.put("content", obj[i++]);
			itemMap.put("analysis", obj[i++]);
			itemMap.put("completeType", obj[i++]);

			results.add(itemMap);
		}
		
		return results;
	}
	
	/**
	 * 修改一道试题状态
	 * @author Administrator
	 * @date: 2014年8月20日 上午11:47:05 <br/> 
	 * @param item
	 * @param answers
	 */
	public void updateItemStatus(Item item){
		StringBuilder sb = new StringBuilder();
		sb.append(" update item i set i.status=");
		sb.append(item.getStatus());
		sb.append(" where i.id=");
		sb.append(item.getId());
		sb.append(" or i.parent=");
		sb.append(item.getId());
		this.executeSQL(sb.toString());
	}
}
