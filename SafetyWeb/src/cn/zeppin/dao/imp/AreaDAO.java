package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IAreaDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Area;

public class AreaDAO extends HibernateTemplateDAO<Area, Integer> implements IAreaDAO {

	/**
	 * 获取地区信息
	 * 
	 * @author Administrator
	 * @date: 2014年9月9日 上午11:02:58 <br/>
	 * @param map
	 * @return
	 */
	public List<Area> getAreas(Map<String, Object> map) {

		StringBuilder sb = new StringBuilder();
		sb.append(" from Area a where 1=1 ");
		if (map.get("parentcode") != null) {
			sb.append(" and a.parentcode=").append(map.get("parentcode"));
		} else {
			sb.append(" and a.level=1");
		}
		
		return this.getByHQL(sb.toString());

	}
}
