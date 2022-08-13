package cn.zeppin.dao.impl;
	
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectDao;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.dataTimeConvertUtility;

public class ProjectDaoImpl extends BaseDaoImpl<Project, Integer> implements IProjectDao {

	/**
	 * 获取 项目管理员个数
	 * 
	 * @param us
	 * @return
	 */
	public int getProjectAdminCount(String organizationScode , Integer organizationLevel , List<Integer> projectTypeList){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct pa.id) from project_admin pa ");
		sb.append(" join organization o on pa.organization = o.id and o.scode like '%").append(organizationScode).append("%'");
		sb.append(" and o.level in (").append(organizationLevel).append(",").append(organizationLevel + 1).append(")");
		sb.append(" left join project_admin_right par on par.project_admin = pa.id");
		sb.append(" where pa.status=1 and (pa.restrict_right=0 or par.project_type in (");
		for(Integer id : projectTypeList){
			sb.append(id).append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append(")) ");
		Object result = this.getObjectBySql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}
	
	/**
	 * 获取 项目管理员列表
	 * 
	 * @param us
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getProjectAdminList(String organizationScode , Integer organizationLevel , List<Integer> projectTypeList){
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct pa.id , pa.name , pa.mobile , pa.sex , e.name as ename, o.name as oname, pa.department , pa.phone ,pa.job_duty from project_admin pa ");
		sb.append(" join organization o on pa.organization = o.id and o.scode like '%").append(organizationScode).append("%'");
		sb.append(" and o.level=").append(organizationLevel + 1);
		sb.append(" left join project_admin_right par on par.project_admin = pa.id");
		sb.append(" join ethnic e on e.id = pa.nationally ");
		sb.append(" where pa.status=1 and (par.project_type is null or par.project_type in (");
		for(Integer id : projectTypeList){
			sb.append(id).append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append(")) ");
		sb.append(" order by pa.organization");
		return this.getListBySQL(sb.toString(), null);
	}
	
	/**
	 * 获取 项目统计分析列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getProjectAnalysis( List<ProjectType> lityps){
		StringBuilder sb = new StringBuilder();
		sb.append("select p.year , pt.id as projectType, count(distinct p.id) as projectNum , count(distinct pa.subject) as subjectNum ");
		sb.append(" from project_apply pa, project p ,project_type pt");
		sb.append(" where pa.proejct=p.id and p.type=pt.id and pa.status=2");
		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" pt.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}
		sb.append(" group by p.year , pt.id");
		return this.getListBySQL(sb.toString(), null);
	}
	
	
	@Override
	public List<Project> getProjectByStatus(short status, List<ProjectType> lityps) {

		StringBuilder sb = new StringBuilder();
		sb.append("from Project t where t.status=");
		sb.append(status);

		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}
		sb.append(" order by t.id desc");
		
		return this.getListByHSQL(sb.toString());

	}

	@Override
	public int getProjectCountByParams(Map<String, Object> params, List<ProjectType> lityps) {

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Project t where 1=1");

		if (params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (key.equals("redHeadDocument")) {
					sb.append(" and t.redHeadDocument is not null");
				} else if (value instanceof String) {
					sb.append(" and t." + key + "='" + value + "'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				}
			}
		}

		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());

	}

	@Override
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, List<ProjectType> lityps, int offset, int length) {

		StringBuilder sb = new StringBuilder();
		sb.append("from Project t where 1=1");

		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (key.equals("redHeadDocument")) {
					sb.append(" and t.redHeadDocument is not null");
				} else if (value instanceof String) {
					sb.append(" and t." + key + " like '%" + value + "%'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				} 
			}
		}

		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				sb.append("t." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListForPage(sb.toString(), offset, length);
	}

	@SuppressWarnings("unchecked")
	public List<String> getProjectYearList(){
		String sql = "select year from project group by year order by year desc";
		return this.getListBySQL(sql, null);
	}
	
	@Override
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, List<ProjectType> lityps) {
		StringBuilder sb = new StringBuilder();
		sb.append("from Project t where 1=1");

		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {//redHeadDocument
				Object value = params.get(key);
				if(key.equals("projectType.scode")){
					sb.append(" and t.projectType.scode like '%").append(value).append("%'");
				} else if( key.equals("projectgroup")){
					sb.append(" and t.projectGroup is null");
				} else if (value instanceof String) {
					sb.append(" and t." + key + "='" + value + "'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				} else if (value instanceof Short) {
					sb.append(" and t." + key + "=" + value + "");
				}
			}
		}

		if (lityps!=null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				sb.append("t." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListByHSQL(sb.toString());

	}

	@SuppressWarnings("rawtypes")
	public List getProjectForTrainingCollege(Map<String, Object> params, Map<String, String> sortParams,int id,Date date, int offset, int length){
		StringBuilder sb = new StringBuilder();
		sb.append("from Project p,ProjectCollegeRange pcr where p.status=2 and p.timeup>='");
		try {
			sb.append(dataTimeConvertUtility.DateToString(date, ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sb.append("' and (p.restrictCollege=0 or ( p.restrictCollege=1 and pcr.project=p.id and pcr.trainingCollege=");
		sb.append(id);
		sb.append(") ) group by p.id ");
		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				sb.append("p." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		return this.getListForPage(sb.toString(), offset, length);
	}
	
	
	/**
	 * @param params
	 * @param sortParams
	 * @param date
	 * @param projectStatus
	 * @param offset
	 * @param maxpagesize
	 * @return
	 * @throws ParseException
	 */
	public List<Project> getProjectByParams(Map<String, Object> params, Map<String, String> sortParams, Date date, int projectStatus, int offset, int maxpagesize) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("from Project t where 1=1 and restrictCollege=0 and status=" + projectStatus + " and timeup>='" + dataTimeConvertUtility.DateToString(date, "") + "' ");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (value instanceof String) {
					sb.append(" and t." + key + "='" + value + "'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				}
			}
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				sb.append("t." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListForPage(sb.toString(), offset, maxpagesize);
	}

	@Override
	public List<Project> getProjectByTypes(String type) {

		StringBuilder sb = new StringBuilder();
		
		if(type==null){
			type="";
		}

		sb.append("from Project t where 1=1 and t.status=" + DictionyMap.releaseProject);
		if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {
			
			sb.append(" and t.psqByEvaluationTrainingPsq is null ");
			
		} else if (type.equals(DictionyMap.PSQ_TYPE_TEACHER)) {
			
			sb.append(" and t.psqByEvaluationTeacherPsq is null ");
			
		} else if (type.equals(DictionyMap.PSQ_TYPE_EXPERT)) {
			
			sb.append(" and t.psqByProjectJudgePsq is null ");
			
		}

		return this.getListByHSQL(sb.toString());
	}

	/**
	 * 根据项目类型和项目年度获取所有项目
	 * @param string
	 * @param leafTypes
	 * @return 
	 */
	public List<Project> getProjectList(String year, List<ProjectType> leafTypes) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" from Project p where 1=1 ");
		sb.append(" and p.year='").append(year).append("'");
		if (leafTypes != null && leafTypes.size() > 0){
			sb.append(" and p.projectType in (");
			String comba = "";
			for (ProjectType pt : leafTypes) {
				sb.append(comba);
				sb.append(pt.getId());
				comba = ",";
			}
			sb.append(")");
		}
		return this.getListByHSQL(sb.toString());
	}

	@Override
	public List<Project> getProjectByStatusAndType(short status,
			List<ProjectType> lityps, short enrollType, String organization) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from Project t,ProjectAdmin pa where t.creator=pa.id and t.status=");
		sb.append(status);
		if(organization != null && !"".equals(organization)){
			sb.append(" and pa.organization in(");
			sb.append(organization);
			sb.append(")");
		}
		sb.append(" and t.enrollType=");
		sb.append(enrollType);

		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}
		sb.append(" order by t.id desc");
		
		return this.getListByHSQL(sb.toString());
	}

	@Override
	public int getProjectCountByParams(short status, List<ProjectType> lityps,
			short enrollType, String organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Project t,ProjectAdmin pa where 1=1");
		sb.append(" and t.creator=pa.id");
		if(organization != null && !"".equals(organization)){
			sb.append(" and pa.organization in(");
			sb.append(organization);
			sb.append(")");
		}
		sb.append(" and t.status=");
		sb.append(status);
		sb.append(" and t.enrollType=");
		sb.append(enrollType);
		
		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	@Override
	public List<Project> getProjectByGroup(Integer groupId) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from Project t where 1=1 ");
		sb.append(" and t.projectGroup=");
		sb.append(groupId);
		
		sb.append(" order by t.id desc");
		
		return this.getListByHSQL(sb.toString());
	}

	@Override
	public int getProjectCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct d.title) from Project t,Document d where 1=1 and t.redHeadDocument=d.id and t.projectType.projectLevel<3");

		if (params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (key.equals("redHeadDocument")) {
					sb.append(" and t.redHeadDocument is not null");
				} else if (value instanceof String) {
					sb.append(" and d." + key + " like '%" + value + "%'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				}
			}
		}

//		sb.append(" group by d.title");

		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getProjectByParams(Map<String, Object> params,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select d.id,d.title,d.resourcePath,d.createtime from Project t,Document d where 1=1 and t.redHeadDocument=d.id and t.projectType.projectLevel<3");

		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (key.equals("redHeadDocument")) {
					sb.append(" and t.redHeadDocument is not null");
				} else if (value instanceof String) {
					sb.append(" and d." + key + " like '%" + value + "%'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				} 
			}
		}
		
		sb.append(" group by d.title");

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				sb.append("d." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

//		return this.getListForPage(sb.toString(), offset, length);
		return getListForPage(sb.toString(), offset, length);
	}

	@Override
	public List<Project> getProjectListByParams(Map<String, Object> params,
			Map<String, String> sortParams, List<ProjectType> lityps,
			int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from Project t,ProjectAdmin pa where 1=1 and t.creator=pa.id ");

		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if("projectCycle".equals(key)) {//周期划分
					sb.append(" and t.projectCycle =" + value);
				} else 	if (key.equals("redHeadDocument")) {
					sb.append(" and t.redHeadDocument is not null");
				} else if (key.equals("level")) {
//					sb.append(" and t." + key + "=" + value + "");
				} else if (key.equals("organization")) {
					if(params.get("organization") != null && !"0".equals(params.get("organization").toString())){
						if(params.get("level") != null && !"0".equals(params.get("level").toString())){
							if("3".equals(params.get("level").toString())){//校级管理员可以查看其下级单位的项目
								sb.append(" and ( pa.organization="+params.get("organization").toString()
										+" or pa.organization.organization="+params.get("organization").toString()+")");
							}else{
								sb.append(" and pa.organization=");
								sb.append(params.get("organization"));
							}
						}else{
							sb.append(" and pa.organization=");
							sb.append(params.get("organization"));
						}
					}
				} else if (value instanceof String) {
					sb.append(" and t." + key + " like '%" + value + "%'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				} 
			}
		}

		if (lityps != null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				sb.append("t." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getProjectListCountByParams(Map<String, Object> params,
			List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Project t,ProjectAdmin pa where 1=1 and t.creator=pa.id ");

		if (params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if("projectCycle".equals(key)) {//周期划分
					sb.append(" and t.projectCycle =" + value);
				} else 	if (key.equals("redHeadDocument")) {
					sb.append(" and t.redHeadDocument is not null");
				} else if (key.equals("level")) {
//					sb.append(" and t." + key + "=" + value + "");
				} else if (key.equals("organization")) {
					if(params.get("organization") != null && !"0".equals(params.get("organization").toString())){
						if(params.get("level") != null && !"0".equals(params.get("level").toString())){
							if("3".equals(params.get("level").toString())){//校级管理员可以查看其下级单位的项目
								sb.append(" and ( pa.organization="+params.get("organization").toString()
										+" or pa.organization.organization="+params.get("organization").toString()+")");
							}else{
								sb.append(" and pa.organization=");
								sb.append(params.get("organization"));
							}
						}else{
							sb.append(" and pa.organization=");
							sb.append(params.get("organization"));
						}
					}
				}else if (value instanceof String) {
					sb.append(" and t." + key + " like '%" + value + "%'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				}
			}
		}

		if (lityps != null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}
		

		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	@Override
	public List<Project> getProjectByTypes(String type, int organization) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		if(type==null){
			type="";
		}

		sb.append("select distinct t from Project t,ProjectAdmin pa where 1=1 and t.creator=pa.id and t.status=" + DictionyMap.releaseProject);
		if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {
			
			sb.append(" and t.psqByEvaluationTrainingPsq is null ");
			
		} else if (type.equals(DictionyMap.PSQ_TYPE_TEACHER)) {
			
			sb.append(" and t.psqByEvaluationTeacherPsq is null ");
			
		} else if (type.equals(DictionyMap.PSQ_TYPE_EXPERT)) {
			
			sb.append(" and t.psqByProjectJudgePsq is null ");
			
		}
		if(organization > 0){
			sb.append(" and pa.organization="+organization);
		}

		return this.getListByHSQL(sb.toString());
	}

	@Override
	public int getProjectListCountByParams4LevelThree(
			Map<String, Object> params, List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Project t,ProjectAdmin pa where 1=1 and t.creator=pa.id ");

		if (params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (key.equals("redHeadDocument")) {
					sb.append(" and t.redHeadDocument is not null");
				} else if (key.equals("level")) {
//							sb.append(" and t." + key + "=" + value + "");
				} else if (key.equals("organization")) {
					if(params.get("organization") != null && !"0".equals(params.get("organization").toString())){
						sb.append(" and pa.organization=");
						sb.append(params.get("organization"));
					}
				}else if (value instanceof String) {
					sb.append(" and t." + key + " like '%" + value + "%'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				}
			}
		}

		if (lityps != null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}
		

		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	@Override
	public List getProjectListByParams4LevelThree(Map<String, Object> params,
			Map<String, String> sortParams, List<ProjectType> lityps,
			int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from Project t,ProjectAdmin pa where 1=1 and t.creator=pa.id ");

		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (key.equals("redHeadDocument")) {
					sb.append(" and t.redHeadDocument is not null");
				} else if (key.equals("level")) {
//							sb.append(" and t." + key + "=" + value + "");
				} else if (key.equals("organization")) {
					if(params.get("organization") != null && !"0".equals(params.get("organization").toString())){
						sb.append(" and pa.organization=");
						sb.append(params.get("organization"));
					}
				} else if (value instanceof String) {
					sb.append(" and t." + key + " like '%" + value + "%'");
				} else if (value instanceof Integer) {
					sb.append(" and t." + key + "=" + value + "");
				} 
			}
		}

		if (lityps != null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				sb.append("t." + key + " " + value + ",");
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListForPage(sb.toString(), offset, length);
	}




}
