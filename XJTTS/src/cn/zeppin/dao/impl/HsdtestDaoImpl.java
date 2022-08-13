/**
 * 
 */
package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IHsdtestDao;
import cn.zeppin.entity.Hsdtest;

/**
 * @author Administrator
 *
 */
public class HsdtestDaoImpl extends BaseDaoImpl<Hsdtest, Integer> implements IHsdtestDao {

	public int getHsdTestCount(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Hsdtest hd where 1=1 ");

		if (map.containsKey("teacher") && map.get("teacher") != null) {
			sb.append(" and hd.teacher=").append(map.get("teacher"));
		}

		if (map.containsKey("year") && map.get("year") != null) {
			sb.append(" and hd.year=").append(map.get("year"));
		}
		
		if (map.containsKey("yearss") && map.get("yearss") != null) {
			sb.append(" and hd.year in(").append(map.get("yearss"));
			sb.append(") ");
		}

		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	public List<Hsdtest> getHsdTest(Map<String, Object> map, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from Hsdtest hd where 1=1 ");

		if (map.containsKey("teacher") && map.get("teacher") != null) {
			sb.append(" and hd.teacher=").append(map.get("teacher"));
		}

		if (map.containsKey("year") && map.get("year") != null) {
			sb.append(" and hd.year=").append(map.get("year"));
		}
		
		sb.append(" order by hd.createtime desc");
		
		return this.getListForPage(sb.toString(), offset, length, null);
	}

}
