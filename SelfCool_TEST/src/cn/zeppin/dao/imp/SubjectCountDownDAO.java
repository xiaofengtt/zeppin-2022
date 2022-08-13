package cn.zeppin.dao.imp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISubjectCountDownDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SubjectCountdown;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

public class SubjectCountDownDAO extends HibernateTemplateDAO<SubjectCountdown, Integer> implements ISubjectCountDownDAO {

	/**
	 * 获取考试倒计时
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int getSubjectCountdownCount(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();

		sb.append("select count(*) from SubjectCountdown sct where 1=1 ");

		if (map.containsKey("subject.id") && map.get("subject.id") != null) {

			sb.append(" and sct.subject=").append(map.get("subject.id"));

		}

		else if (map.containsKey("status") && map.get("status") != null) {
			sb.append(" and sct.status=").append(map.get("status"));
		}

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		sb.append(" and sct.examTime > ").append("'").append(Utlity.timeSpanToDateString(ts)).append("'");

		sb.append(" order by sct.examTime asc ");

		return ((Long) this.getResultByHQL(sb.toString())).intValue();
	}

	/**
	 * 获取考试倒计时
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<SubjectCountdown> getSubjectCountdowns(Map<String, Object> map, int offset, int length) {
		StringBuilder sb = new StringBuilder();

		sb.append("from SubjectCountdown sct where 1=1 ");

		if (map.containsKey("subject.id") && map.get("subject.id") != null) {

			sb.append(" and sct.subject=").append(map.get("subject.id"));

		}

		if (map.containsKey("status") && map.get("status") != null) {
			sb.append(" and sct.status=").append(map.get("status"));
		}

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		sb.append(" and sct.examTime > ").append("'").append(Utlity.timeSpanToDateString(ts)).append("'");

		sb.append(" order by sct.examTime asc ");

		return this.getByHQL(sb.toString(), offset, length);
	}

	
	/**
	 * 获取下一次考试日期
	 * @param subjectID
	 * @return
	 */
	@Override
	public Timestamp getNextExamTime(Integer subjectID) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" select min(sct.examTime) from SubjectCountdown sct where 1=1 ");
		hql.append(" and sct.subject=").append(subjectID);
		hql.append(" and sct.examTime > now()");
		hql.append(" and sct.status=").append(Dictionary.NORMAL);
		return (Timestamp) this.getResultByHQL(hql.toString());
	}

}
