package cn.zeppin.task;


import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.utility.Dictionary;

public class TaskJobDAO extends HibernateTemplateDAO<Object, Integer> {

	/**
	 * 计算用户学科备考的相关数据
	 * 1、计算用户在备考学科下的总刷题量   (为了保证实时性，改为每次测试完成后提交时计算)
	 * 2、计算用户在备考学科下的做题正确率  (为了保证实时性，改为每次测试完成后提交时计算)
	 * 3、计算用户在备考学科下的备考进度   (为了保证实时性，改为每次测试完成后提交时计算)
	 * 4、计算用户在备考学科下的剩余备考时间（未来最近一次考试，每天更新即可）
	 * 5、计算用户在备考学科下的备考进度排名率 (为了保证实时性，改为每次测试完成后提交时计算)
	 */
	public void calculateUserSubjectData() {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" update user_subject set next_testday_count=");
		sql.append(" datediff( ");
		sql.append(" (select min(exam_time) from subject_countdown ");
		sql.append(" where subjectid=user_subject.subject");
		sql.append(" and exam_time > now() )");
		sql.append(" , now())");
		this.executeSQL(sql.toString());
		
	}


	/**
	 * 包括知识点下的试题数和标准化试题数
	 * 考虑了知识点层级结构
	 * 考虑了试题的状态为已发布
	 */
	public void calculateAllKnowledgeItemData() {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" delete from knowledge_itemcount ");
		this.executeSQL(sql.toString());
		
		sql.delete(0, sql.length());
		sql.append( "insert into knowledge_itemcount(knowledgeid) select id from knowledge ");
		this.executeSQL(sql.toString());
		
		sql.delete(0, sql.length());
		sql.append(" update knowledge_itemcount set released_itemcount= ");
		sql.append(" (select count(*) from knowledge a, knowledge b, item i ");
		sql.append(" where b.id = i.knowladge ");
		sql.append(" and b.scode like concat(a.scode,'%') ");
		sql.append(" and knowledge_itemcount.knowledgeid = a.id ");
		sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append(" and (i.is_group=0 or (i.is_group = 1 and i.level=2))");
		sql.append(")");
		this.executeSQL(sql.toString());
		
		sql.delete(0, sql.length());
		sql.append(" update knowledge_itemcount set standard_released_itemcount= ");
		sql.append(" (select count(*) from knowledge a, knowledge b, item i, item_type it ");
		sql.append(" where b.id = i.knowladge ");
		sql.append(" and i.type=it.id ");
		sql.append(" and b.scode like concat(a.scode,'%') ");
		sql.append(" and knowledge_itemcount.knowledgeid = a.id ");
		sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append(" and it.is_standard=").append(Dictionary.ITEM_ANSWER_TYPESTANDARD);
		sql.append(" and (i.is_group=0 or (i.is_group = 1 and i.level=2))");
		sql.append(")");
		this.executeSQL(sql.toString());
		
		
	}

	
	/**
	 * 计算用户做题数据，（只考虑已回答isAnswer的内容）包括:
	 * 1、用户做每道题的总次数
	 * 2、用户做每道题中做正确的次数
	 * 3、用户做每道题中做错误的次数
	 * 4、用户做每道题最近一次的做题时间
	 * 5、用户做每道题最近一次的做题结果（是否正确）
	 * 6、用户做每道题最近一次的做题ID
	 * 
	 * 这个处理的目的是为了准备出题算法的基础数据，因出题算法中已经判断了最近一段时间出的题不会再出现，所以这个方法能够提供比较有效的策略
	 */
	public void calculateUserTestItemData() {
//		// TODO Auto-generated method stub
//		StringBuilder sql = new StringBuilder();
//		sql.append("delete from sso_user_test_item_count");
//		this.executeSQL(sql.toString());
//		
//		
//		//生成目标表主要字段
//		sql.delete(0, sql.length());
//		sql.append(" insert into sso_user_test_item_count(userid, itemid, blank_inx, last_test_item_id) ");
//		sql.append(" select sut.ssoid, suti.item, suti.blank_inx, max(suti.id)");
//		sql.append(" from sso_user_test sut, sso_user_test_item suti, item i, item_type it");
//		sql.append(" where sut.id=suti.user_test");
//		sql.append(" and suti.item=i.id");
//		sql.append(" and i.type=it.id ");
//		sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
//		sql.append(" and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
//		sql.append(" and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
//		sql.append(" and it.is_standard=").append(Dictionary.ITEM_ANSWER_TYPESTANDARD);
//		sql.append(" group by sut.ssoid, suti.item, suti.blank_inx");
//		this.executeSQL(sql.toString());
//		
//	
//		//更新用户每题最后一次做的对错状态
//		sql.delete(0, sql.length());
//		sql.append(" update sso_user_test_item_count set last_test_item_complete_type=");
//		sql.append(" (select complete_type from sso_user_test sut, sso_user_test_item suti");
//		sql.append(" where sso_user_test_item_count.last_test_item_id=suti.id");
//		sql.append(" and sut.id=suti.user_test");
//		sql.append(" and sso_user_test_item_count.userid = sut.ssoid");
//		sql.append(" and sso_user_test_item_count.itemid = suti.item");
//		sql.append(" and sso_user_test_item_count.blank_inx = suti.blank_inx");
//		sql.append(" and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
//		sql.append(" and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
//		sql.append(" )");
//		this.executeSQL(sql.toString());
//		
//		
//		//更新用户每题最后一次做的时间
//		sql.delete(0, sql.length());
//		sql.append(" update sso_user_test_item_count set last_test_item_answer_time=");
//		sql.append(" (select sut.createtime from sso_user_test sut, sso_user_test_item suti");
//		sql.append(" where sso_user_test_item_count.last_test_item_id=suti.id");
//		sql.append(" and sut.id = suti.user_test");
//		sql.append(" and sso_user_test_item_count.userid = sut.ssoid");
//		sql.append(" and sso_user_test_item_count.itemid = suti.item");
//		sql.append(" and sso_user_test_item_count.blank_inx = suti.blank_inx");
//		sql.append(" and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
//		sql.append(" and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
//		sql.append(" )");
//		this.executeSQL(sql.toString());             
//		
//		
//		//更新用户每题做过的次数
//		sql.delete(0, sql.length());
//		sql.append(" update sso_user_test_item_count set test_item_count=");
//		sql.append(" (select count(*) from sso_user_test sut, sso_user_test_item suti");
//		sql.append(" where sut.id = suti.user_test");
//		sql.append(" and sso_user_test_item_count.userid = sut.ssoid");
//		sql.append(" and sso_user_test_item_count.itemid = suti.item");
//		sql.append(" and sso_user_test_item_count.blank_inx = suti.blank_inx");
//		sql.append(" and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
//		sql.append(" and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
//		sql.append(" )");
//		this.executeSQL(sql.toString());
//		
//		//更新用户每题做正确的次数
//		sql.delete(0, sql.length());
//		sql.append(" update sso_user_test_item_count set test_item_correct_count=");
//		sql.append(" (select count(*) from sso_user_test sut, sso_user_test_item suti");
//		sql.append(" where sut.id = suti.user_test");
//		sql.append(" and sso_user_test_item_count.userid = sut.ssoid");
//		sql.append(" and sso_user_test_item_count.itemid = suti.item");
//		sql.append(" and sso_user_test_item_count.blank_inx = suti.blank_inx");
//		sql.append(" and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
//		sql.append(" and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
//		sql.append(" and suti.complete_type=").append(Dictionary.SSO_USER_TEST_ITEM_CORRECT);
//		sql.append(" )");
//		this.executeSQL(sql.toString());
//		
//		//更新用户每题做错误的次数
//		sql.delete(0, sql.length());
//		sql.append(" update sso_user_test_item_count set test_item_wrong_count=");
//		sql.append(" (select count(*) from sso_user_test sut, sso_user_test_item suti");
//		sql.append(" where sut.id = suti.user_test");
//		sql.append(" and sso_user_test_item_count.userid = sut.ssoid");
//		sql.append(" and sso_user_test_item_count.itemid = suti.item");
//		sql.append(" and sso_user_test_item_count.blank_inx = suti.blank_inx");
//		sql.append(" and suti.isanswered=").append(Dictionary.USER_TEST_ITEM_ANSWERED);
//		sql.append(" and sut.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
//		sql.append(" and suti.complete_type=").append(Dictionary.SSO_USER_TEST_ITEM_WRONG);
//		sql.append(" )");
//		this.executeSQL(sql.toString());
//		
	}

	/**
	 * 按itemType统计试题数，更新到subject_item_type表中
	 */
	public void calculateSubjectItemTypeData() {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" update subject_item_type set released_itemcount=");
		sql.append(" (select count(*) from item i ");
		sql.append("  where subject_item_type.subject = i.subject");
		sql.append("  and subject_item_type.item_type = i.type");
		sql.append("  and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		//非组合题+组合题中级别为2的，通常组合题中level=1的为材料，不计算在试题数量当中
		sql.append("  and (i.is_group=0 or (i.is_group = 1 and i.level=2))");
		sql.append(" )");
		this.executeSQL(sql.toString());
	}

	
	
}
