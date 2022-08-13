package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IInformationDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Information;

public class InformationDAO extends HibernateTemplateDAO<Information, Integer> implements IInformationDAO {

	/**
	 * 
	 * @param map
	 * @return
	 */
	public int getInformationCount(Map<String, Object> map) {

		StringBuilder hql = new StringBuilder();

		hql.append("select count(*) from Information im where 1=1 ");

		if (map.containsKey("id") && map.get("id") != null) {
			hql.append(" and im.id=").append(map.get("id"));
		}

		if (map.containsKey("title") && map.get("title") != null) {
			hql.append(" and im.title like'%").append(map.get("title")).append("%'");
		}

		if (map.containsKey("status") && map.get("status") != null) {
			hql.append(" and im.status=").append(map.get("status"));
		}
		if (map.containsKey("subject.id") && map.get("subject.id") != null) {
			hql.append(" and im.subject in (").append(map.get("subject.id")).append(")");
		}

		if (map.containsKey("time") && map.get("time") != null) {
			hql.append(" and im.releasetime > ").append("'").append(map.get("time")).append("'");
		}

		Object result = this.getResultByHQL(hql.toString());
		if (result != null) {
			return Integer.valueOf(result.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Information> getInformations(Map<String, Object> map, String sorts, int offset, int length) {
		StringBuilder hql = new StringBuilder();

		hql.append("from Information im where 1=1 ");

		if (map.containsKey("id") && map.get("id") != null) {
			hql.append(" and im.id=").append(map.get("id"));
		}

		if (map.containsKey("title") && map.get("title") != null) {
			hql.append(" and im.title like'%").append(map.get("title")).append("%'");
		}

		if (map.containsKey("status") && map.get("status") != null) {
			hql.append(" and im.status=").append(map.get("status"));
		}
		
		if (map.containsKey("subject.id") && map.get("subject.id") != null) {
			hql.append(" and im.subject in (").append(map.get("subject.id")).append(")");
		}

		if (map.containsKey("time") && map.get("time") != null) {
			hql.append(" and im.releasetime > ").append("'").append(map.get("time")).append("'");
		}

		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				hql.append(comma);
				hql.append("im.").append(sort);
				comma = ",";
			}
		}
		else{
			hql.append(" order by im.createtime desc");
		}

		return this.getByHQL(hql.toString(), offset, length);
	}

}
