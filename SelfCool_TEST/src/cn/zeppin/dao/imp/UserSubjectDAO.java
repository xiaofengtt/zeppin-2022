package cn.zeppin.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserSubjectDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserSubject;
import cn.zeppin.utility.Dictionary;

public class UserSubjectDAO extends HibernateTemplateDAO<UserSubject, Integer> implements IUserSubjectDAO {

	/**
	 * 获取用户绑定学科个数
	 */
	@Override
	public int getUserSubjectCount(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserSubject us where 1=1 ");

		if (map.containsKey("user.id") && map.get("user.id") != null) {
			sb.append(" and us.ssoUser=").append(map.get("user.id"));
		}
		if (map.containsKey("subject.id") && map.get("subject.id") != null) {
			sb.append(" and us.subject=").append(map.get("subject.id"));
		}
		if (map.containsKey("status") && map.get("status") != null) {
			sb.append(" and us.status=").append(map.get("status"));
		}

		Object result = this.getResultByHQL(sb.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 获取用户绑定学科:并统计出 每个学科的刷题量， 正确率，备考进度;
	 * 
	 * @param map
	 * @return
	 */
	public List<UserSubject> getUserSubjectsByMap(Map<String, Object> map, String sorts, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from UserSubject us where 1=1 ");

		if (map.containsKey("user.id") && map.get("user.id") != null) {
			sb.append(" and us.ssoUser=").append(map.get("user.id"));
		}
		if (map.containsKey("subject.id") && map.get("subject.id") != null) {
			sb.append(" and us.subject=").append(map.get("subject.id"));
		}
		if (map.containsKey("status") && map.get("status") != null) {
			sb.append(" and us.status=").append(map.get("status"));
		}
		
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append("us.").append(sort);
				comma = ",";
			}
		}
		return this.getByHQL(sb.toString(), offset, length);

	}

	
	/**
	 * 取客户端学科列表，除了基本字段还包括备考进度、刷题量、正确率、剩余备考天数、击败人数百分比
	 * @param uid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getUserSubjects(Integer uid) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" select us.subject, s.name, us.progress, us.brush_item_count,");
		sql.append(" us.next_testday_count, us.correct_rate, us.ranking_rate");
		sql.append(" from user_subject us, subject s");
		sql.append(" where us.subject=s.id");
		sql.append(" and us.sso_user=").append(uid);
		sql.append(" and us.status=").append(Dictionary.USER_SUBJECT_STATUS_NOMAL);
		sql.append(" order by us.createtime");
		
		List<Object[]> objs = this.getBySQL(sql.toString());
		List<Map<String, Object>> userSubjects = new ArrayList<>();
		
		for (Object[] obj : objs) {
			Map<String, Object> userSubject =  new HashMap<>();  
			int i = 0;
//			userSubject.put("id", obj[i++]);
			userSubject.put("subjectid", obj[i++]);
			userSubject.put("subjectname", obj[i++]);
			userSubject.put("progress", obj[i++]);
			userSubject.put("brushItemCount", obj[i++]);
			userSubject.put("nextTestdayCount", obj[i++]);
			userSubject.put("correctRate", obj[i++]);
			userSubject.put("rankingRate", obj[i++]);
			
			userSubjects.add(userSubject);
		}
		
		return userSubjects;
	}

	/**
	 * 通过唯一索引主键来获取一个用户关注学科
	 * @param id
	 * @param subjectID
	 * @return
	 */
	@Override
	public UserSubject getByKey(Integer uid, Integer subjectid) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from UserSubject us where 1=1");
		hql.append(" and us.ssoUser=").append(uid);
		hql.append(" and us.subject=").append(subjectid);
		return (UserSubject) this.getResultByHQL(hql.toString());
	}

	/**
	 * 获取用户在某个学科下载备考进度排名
	 * @param id
	 * @param id2
	 * @return
	 */
	@Override
	public int getUserSubjectTestProgressRanking(Integer uid, Integer subjectId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		
//		hibernate不支持如下写法
//		sql.append(" select orderNo from (");
//		sql.append(" select (@rowNum:=@rowNum+1) orderNo, subject, sso_user, progress");
//		sql.append(" from user_subject us, (select (@rowNum:=0)) b");
//		sql.append("  where us.subject=").append(subjectId);
//		sql.append(" and us.status=").append(Dictionary.USER_SUBJECT_STATUS_NOMAL);
//		sql.append("  order by us.progress desc) t");
//		sql.append("  where sso_user=").append(uid);
		
		sql.append(" select count(*) from user_subject us");
		sql.append(" where us.subject=").append(subjectId);
		sql.append(" and us.status=").append(Dictionary.USER_SUBJECT_STATUS_NOMAL);
		sql.append(" and us.progress > (");
		sql.append(" select us1.progress from user_subject us1");
		sql.append(" where us1.subject=").append(subjectId);
		sql.append(" and us1.sso_user=").append(uid);
		sql.append(" )");
		
		return Integer.valueOf(this.getResultBySQL(sql.toString()).toString()) + 1;
	}

}
