package cn.zeppin.dao.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import cn.zeppin.dao.IProjectTypeDao;
import cn.zeppin.entity.ProjectType;

public class ProjectTypeDaoImpl extends BaseDaoImpl<ProjectType, Integer> implements IProjectTypeDao {

	/**
	 * 添加的时候，自动生成SCODE编码
	 */
	@Override
	public ProjectType add(ProjectType projectType)
	{
		// TODO Auto-generated method stub
		ProjectType result = super.add(projectType);
		String format = "0000000000";
		DecimalFormat df = new DecimalFormat(format);
		String scode = df.format(result.getId());
		if (result.getProjectType() != null){
			scode = result.getProjectType().getScode() + scode;
		}
		result.setScode(scode);
		result = this.update(result);
		return result;
	}
	
	public List<ProjectType> getProjectTypeList(List<ProjectType> lityps){
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectType pt where 1=1");
		if (lityps != null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" pt.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}
		sb.append(" order by pt.scode");
		return this.getListByHSQL(sb.toString());
	}
	
	/**
	 * 根据项目类型级别获取根项目类型
	 * @param projectType
	 * @param projectTypeLevel
	 * @return ProjectType
	 */
	public ProjectType getRootProjectType(ProjectType projectType) {
		ProjectType root = projectType;
		while (root.getProjectType() != null){
			root = root.getProjectType();
		}
		return root;
	}
	
	/**
	 * 递归获取已知项目类型的所有子项目类型
	 * @param projectType
	 * @return List<ProjectType>
	 */
	public List<ProjectType> getSubProjectType(ProjectType projectType) {
		// TODO Auto-generated method stub
		List<ProjectType> list = new ArrayList<>();
		if (projectType.getProjectTypes()!=null && projectType.getProjectTypes().size() > 0){
			for (ProjectType pt : projectType.getProjectTypes()) {
				list.addAll(getSubProjectType(pt));
			}
		}
		else {
			list.add(projectType);
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectType> findByName(String name) {
		// TODO Auto-generated method stub
		String queryString = "from ProjectType t where t.name=?";
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setParameter(0, name);

		return query.list();
	}

	@Override
	// 插入一个项目类型
	public int instByParams(Object[] pars) {
		// TODO Auto-generated method stub

		String sql = "insert into project_type(name,shortname,project_level,level,pid,area,status,creattime) values(?,?,?,?,?,?,?,?)";
		Query query = this.getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < pars.length; i++) {
			query.setParameter(i, pars[i]);
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		query.setParameter(7, df.format(ts));

		return query.executeUpdate();

	}

	@Override
	public int updateByParams(Object[] pars) {
		// TODO Auto-generated method stub
		String sql = "update project_type set name=?,shortname=?,project_level=?,area=?,status=? where id=?";
		Query query = this.getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < pars.length; i++) {
			query.setParameter(i, pars[i]);
		}
		return query.executeUpdate();
	}

	@Override
	public List<ProjectType> getProjectTypeList(List<ProjectType> lityps,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectType pt where 1=1 and status=1");
		if (lityps != null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" pt.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}
		
		if(params != null && params.size() > 0){
			if(params.get("level") != null && !"".equals(params.get("level").toString())){
				if("4".equals(params.get("level").toString())){//校本级
					sb.append(" and pt.projectLevel=5");
				}else if("2".equals(params.get("level").toString())){//地区
					sb.append(" and pt.projectLevel=3");
				}else if("3".equals(params.get("level").toString())){//县市
					sb.append(" and pt.projectLevel=4");
				}else{//自治区教育厅管理员 --国家级+自治区级项目类型
					sb.append(" and pt.projectLevel in(1,2)");
				}
			}
			
			if(params.get("pid") != null){
				if("0".equals(params.get("pid").toString())){
					sb.append(" and pt.projectType is null");
				}else{
					sb.append(" and pt.projectType=");
					sb.append(params.get("pid").toString());
				}
			}
		}
		
		sb.append(" order by pt.scode");
		return this.getListByHSQL(sb.toString());
	}

}
