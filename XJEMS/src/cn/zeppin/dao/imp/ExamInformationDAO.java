package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IExamInformationDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.ExamInformation;

/** 
 * ClassName: ExamInformationDAO <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 */
public class ExamInformationDAO extends HibernateTemplateDAO<ExamInformation, Integer> implements IExamInformationDAO{

	@Override
	public List<ExamInformation> searchExamInformation(Map<String, Object> searchMap, String sorts, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from ExamInformation su where 1=1 ");
		if (searchMap.get("id") != null  && !searchMap.get("id").equals("")){
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("name") != null  && !searchMap.get("name").equals("")){
			hql.append(" and su.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("statusNormal") != null && !searchMap.get("statusNormal").equals("")){
			hql.append(" and su.status <> 0");
		}
		if (searchMap.get("currenttime") != null && !searchMap.get("currenttime").equals("")){
			hql.append(" and su.examStarttime < '").append(searchMap.get("currenttime")+"'");
			hql.append(" and su.examEndtime >= '").append(searchMap.get("currenttime")+"'");
		}
		
		//排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			hql.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				hql.append(comma);
				hql.append("su.").append(sort);
				comma = ",";
			}
			
		} else {
			hql.append(" order by su.createtime");
		}
		return this.getByHQL(hql.toString(), offset, length);
	}
	
	@Override
	public int searchExamInformationCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" select count(*) from ExamInformation su where 1=1 ");
		if (searchMap.get("id") != null  && !searchMap.get("id").equals("")){
			hql.append(" and su.id=").append(searchMap.get("id"));
		}
		if (searchMap.get("name") != null  && !searchMap.get("name").equals("")){
			hql.append(" and su.name like '%").append(searchMap.get("name")).append("%' ");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			hql.append(" and su.status=").append(searchMap.get("status"));
		}
		if (searchMap.get("statusNormal") != null && !searchMap.get("statusNormal").equals("")){
			hql.append(" and su.status <> 0");
		}
		if (searchMap.get("currenttime") != null && !searchMap.get("currenttime").equals("")){
			hql.append(" and su.examStarttime < '").append(searchMap.get("currenttime")+"'");
			hql.append(" and su.examEndtime >= '").append(searchMap.get("currenttime")+"'");
		}
		return ((Long) this.getResultByHQL(hql.toString())).intValue();
	}
	

}
