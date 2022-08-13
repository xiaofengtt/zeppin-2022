package cn.zeppin.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectRetrieveDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.Retrieve;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SubjectRetrieve;
import cn.zeppin.utility.Dictionary;

public class SubjectRetrieveDAO extends HibernateTemplateDAO<SubjectRetrieve, Integer> implements ISubjectRetrieveDAO {

	/**
	 * 获取
	 */
	@Override
	public List<SubjectRetrieve> getSubjectRetrieves(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("from SubjectRetrieve sr where 1=1 ");

		if (map.containsKey("subject.id") && map.get("subject.id") != null) {
			sb.append(" and sr.subject = ").append(map.get("subject.id"));
		}
		
		if (map.containsKey("retrieve.id") && map.get("retrieve.id") != null) {
			sb.append(" and sr.retrieve = ").append(map.get("retrieve.id"));
		}

		return this.getByHQL(sb.toString());
	}

	/**
	 * 删除
	 */
	@Override
	public void delSubjectRetrieves(int subjectId) {
		String sql = "delete from SubjectRetrieve sr where sr.subject=" + subjectId;

		this.executeHQL(sql);

	}

	/**
	 * 获取用户可添加关注的学科列表
	 * @param ssoUser
	 * @param category
	 * @param retrieves
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getSubjectRetrieves(SsoUser ssoUser,
			Category category, List<Retrieve> retrieves) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> results = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select s.id, s.name, s.shortname, sr.retrieveid, us.sso_user");
		sql.append(" from subject s");
		sql.append(" join subject_retrieve sr on s.id=sr.subjectid");
		if (retrieves.size() > 0) {
			sql.append(" and sr.retrieveid in (");
			String comma = "";
			for (Retrieve retrieve : retrieves) {
				sql.append(comma);
				sql.append(retrieve.getId());
				comma = ",";
			}			
			sql.append(")");
		}
		/**
		 * 左关联用户学科表，如果us.sso_user有数据，则用户关联了此学科;否则，用户没关联此学科
		 */
		sql.append(" left join user_subject us on s.id=us.subject");
		sql.append("   and us.sso_user=").append(ssoUser.getId());
		//刨除已取消关注的user_subject
		sql.append("   and us.status=").append(Dictionary.USER_SUBJECT_STATUS_NOMAL);
		sql.append(" where s.category=").append(category.getId());
		sql.append(" and s.status=").append(Dictionary.SUBJECT_STATUS_NOMAL);
		sql.append(" order by s.id");
		
		List<Object[]> objs = this.getBySQL(sql.toString());
		for (Object[] obj : objs) {
			Map<String, Object> subjectRetrieve = new HashMap<>();
			int i=0;
			subjectRetrieve.put("id", obj[i++]);
			subjectRetrieve.put("name", obj[i++]);
			subjectRetrieve.put("shortname", obj[i++]);
			subjectRetrieve.put("retrieveid", obj[i++]);
			subjectRetrieve.put("isSelected", obj[i] != null);
			
			results.add(subjectRetrieve);
		}
		return results;
	}

}
