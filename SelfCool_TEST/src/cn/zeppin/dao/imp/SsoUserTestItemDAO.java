package cn.zeppin.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISsoUserTestItemDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.Subject;
import cn.zeppin.utility.Dictionary;

public class SsoUserTestItemDAO extends HibernateTemplateDAO<SsoUserTestItem, Long> implements ISsoUserTestItemDAO {
	
	/**
	 * 获取用户做题个数
	 * 
	 * @param map
	 * @return
	 */
	public int getUserTestItemCountByMap(Map<String, Object> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from SsoUserTestItem s where 1=1 ");
		
		if (searchMap.get("user.id") != null && !searchMap.get("user.id").equals("")) {
			sb.append(" and s.ssoUserTest.ssoUser=").append(searchMap.get("user.id"));
		}
		
		if (searchMap.get("subject.id") != null && !searchMap.get("subject.id").equals("")) {
			sb.append(" and s.item.subject=").append(searchMap.get("subject.id"));
		}
		
		if (searchMap.get("knowledge.scode") != null && !searchMap.get("knowledge.scode").equals("")) {
			sb.append(" and s.item.knowledge.scode like '").append(searchMap.get("knowledge.scode")).append("%'");
		}
		
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")) {
			sb.append(" and s.item.status=").append(searchMap.get("status"));
		}
		
		if (searchMap.get("itemType.isStandard") != null && !searchMap.get("itemType.isStandard").equals("")) {
			sb.append(" and s.item.itemType.isStandard=").append(searchMap.get("itemType.isStandard"));
		}
		
		if (searchMap.get("completeType") != null && !searchMap.get("completeType").equals("")) {
			sb.append(" and s.completeType=").append(searchMap.get("completeType"));
		}		
		
		if (searchMap.get("ssoUserTest.status") != null && !searchMap.get("ssoUserTest.status").equals("")) {
			sb.append(" and s.ssoUserTest.status=").append(searchMap.get("ssoUserTest.status"));
		}	
		
		return ((Long) this.getResultByHQL(sb.toString())).intValue();
		
	}

	/**
	 * 获取列表
	 * @param map
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<SsoUserTestItem> getUserTestItemsByMap(Map<String, Object> searchMap, int offset, int length){
		
		StringBuilder hql = new StringBuilder();
		
		hql.append(" from SsoUserTestItem suti where 1=1 ");
		
		if(searchMap.containsKey("usertest.id")){
			hql.append(" and suti.ssoUserTest = ").append(searchMap.get("usertest.id"));
		}
		
		if(searchMap.containsKey("completeType")){
			hql.append(" and suti.completeType = ").append(searchMap.get("completeType"));
		}
		
		hql.append(" order by suti.inx ");
		
		
		return this.getByHQL(hql.toString(), offset, length);
		
	}

	
	/**
	 * 该学科用户正确题总数（每个试题只计算最后一次做题是否正确）
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public Integer calculateUserLastTotalRightItemCount(SsoUser currentUser, Subject subject, Short isStandard){
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		
		
		sql.append(" select count(*) from sso_user_test_item_count sutic, item i, item_type it ");
		sql.append(" where sutic.itemid=i.id and i.type=it.id");
		sql.append(" and sutic.userid=").append(currentUser.getId());
		sql.append(" and i.subject=").append(subject.getId());
		sql.append(" and it.is_standard=").append(isStandard);
		sql.append(" and sutic.last_test_item_complete_type=").append(Dictionary.SSO_USER_TEST_ITEM_CORRECT);
//		sql.append(" SELECT count(*) from");
		//确保在一次测试中出的题不会重复，该算法才有效，否则应该在SSO_USER_TEST_ITEM中加入答题创建时间
//		sql.append("  (SELECT MAX(suti.id) as last_id");
//		sql.append(" FROM SSO_USER_TEST sut, SSO_USER_TEST_ITEM suti, ITEM i, ITEM_TYPE it ");
//		sql.append(" WHERE sut.ID=suti.USER_TEST ");
//		sql.append(" AND suti.ITEM=i.ID ");
//		sql.append(" AND i.TYPE=it.ID");
//		sql.append(" AND sut.SSOID=").append(currentUser.getId()).append(" ");
//		sql.append(" AND i.SUBJECT=").append(subject.getId()).append(" ");
//		//条件为题做正确了
//		sql.append(" AND suti.COMPLETE_TYPE=").append(Dictionary.SSO_USER_TEST_ITEM_CORRECT).append(" ");
//		//正确题总数关心试题的状态是否发布或停用，计算进度时不能超过总（已发布）的题数
//		sql.append(" AND i.STATUS=").append(Dictionary.ITEM_STATUS_RELEASE).append(" ");
//		//计算用户提交答题的记录
//		sql.append(" AND sut.STATUS=").append(Dictionary.USER_TEST_STATUS_COMPLETE).append(" ");
//
//		sql.append(" AND it.IS_STANDARD=").append(isStandard).append(" ");
//		sql.append(" GROUP BY suti.ITEM ) aliasname");


		return Integer.valueOf(this.getResultBySQL(sql.toString()).toString());
	}

	/**
	 * 该学科用户正确题总数按题型分组列表（每个试题只计算最后一次做题是否正确）
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public List<Map<String,Object>> calculateUserLastTotalRightItemCountGroupByItemType(SsoUser currentUser, Subject subject){
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		
		
		sql.append(" select it.id , count(*) from sso_user_test_item_count sutic, item i, item_type it ");
		sql.append(" where sutic.itemid=i.id and i.type=it.id");
		sql.append(" and sutic.userid=").append(currentUser.getId());
		sql.append(" and i.subject=").append(subject.getId());
		sql.append(" and sutic.last_test_item_complete_type=").append(Dictionary.SSO_USER_TEST_ITEM_CORRECT);
		sql.append(" group by it.id");

		List<Object[]> rst = this.getBySQL(sql.toString(), -1, -1);
		
		
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
	 * 通过本次做题记录取用户上一次做题记录（用于与最后一次成绩做对比）
	 * @param ssoUserTestItem
	 * @return
	 */
	@Override
	public SsoUserTestItem getPreviousSsoUserTestItem(SsoUserTestItem ssoUserTestItem) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from SsoUserTestItem suti where 1=1 ");
		sb.append(" and suti.ssoUserTest.ssoUser=").append(ssoUserTestItem.getSsoUserTest().getSsoUser().getId());
		sb.append(" and suti.item=").append(ssoUserTestItem.getItem().getId());
		sb.append(" and suti.blankInx=").append(ssoUserTestItem.getBlankInx());
		sb.append(" and suti.ssoUserTest.status=").append(Dictionary.USER_TEST_STATUS_COMPLETE);
		//不能是自己本身
		sb.append(" and suti.id <>").append(ssoUserTestItem.getId());
		sb.append(" order by suti.ssoUserTest.createtime desc");
		//取倒序符合条件不是自己本身的第一条记录
//		sb.append(" limit 0,1");
		List<SsoUserTestItem> resultList = this.getByHQL(sb.toString(), 0, 1);
		if (resultList.size() > 0) {
			return resultList.get(0);
		}
		return null; 
	}
	
}
