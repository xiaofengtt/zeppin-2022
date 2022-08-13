package cn.zeppin.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserKnowledgeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserKnowledge;
import cn.zeppin.utility.Dictionary;

public class UserKnowledgeDAO extends HibernateTemplateDAO<UserKnowledge, Integer> implements
		IUserKnowledgeDAO {


	/**
	 * 通过用户，学科获得用户的知识点信息
	 * @param id
	 * @param id2
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getUserKnowledges(Integer uid, Integer subjectId) {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> results = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select k.id, k.name, k.scode, k.level,");
//		sql.append("  ki.released_itemcount, ki.standard_released_itemcount, ");
		sql.append(" uk.last_test_item_correct_count, uk.brush_item_count, uk.correct_rate");
		sql.append(" from knowledge k ");
//		sql.append(" join knowledge_itemcount ki on k.id=ki.knowledgeid ");
		sql.append(" left join user_knowledge uk on k.id=uk.knowledge and uk.sso_user=").append(uid);
		sql.append(" where k.subject=").append(subjectId);
		sql.append(" and k.status=").append(Dictionary.KNOWLEDGE_STATUS_NOMAL);
		sql.append(" order by k.scode");
		
		List<Object[]> objs = this.getBySQL(sql.toString());
		
		for (Object[] obj : objs) {
			Map<String, Object> knowledge = new HashMap<>();
			int i = 0;
			knowledge.put("id", obj[i++]);
			knowledge.put("name", obj[i++]);
			knowledge.put("scode", obj[i++]);
			knowledge.put("level", obj[i++]);
//			knowledge.put("released_itemcount", obj[i] == null ? 0 : obj[i]); i++;
//			knowledge.put("standard_released_itemcount", obj[i] == null ? 0 : obj[i]); i++;
			knowledge.put("last_test_item_correct_count", obj[i] == null ? 0 : obj[i]); i++;
			knowledge.put("brush_item_count", obj[i] == null ? 0 : obj[i]); i++;
			knowledge.put("correct_rate", obj[i] == null ? 0d : obj[i]); i++;
			results.add(knowledge);
		}

		return results;
	}

	/**
	 * 根据唯一所以获取一条UserKnowledge信息
	 * @param uid
	 * @param knowledgeId
	 * @return
	 */
	@Override
	public UserKnowledge getByKey(Integer uid, Integer knowledgeId) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from UserKnowledge uk where 1=1");
		hql.append(" and uk.ssoUser=").append(uid);
		hql.append(" and uk.knowledge=").append(knowledgeId);
		return (UserKnowledge) this.getResultByHQL(hql.toString());
	}

}
