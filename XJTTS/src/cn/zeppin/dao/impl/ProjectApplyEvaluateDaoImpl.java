package cn.zeppin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectApplyEvaluateDao;
import cn.zeppin.entity.ProjectApplyEvaluate;

public class ProjectApplyEvaluateDaoImpl extends BaseDaoImpl<ProjectApplyEvaluate, Integer> implements IProjectApplyEvaluateDao {
	public void deleteByProjectApply(int id){
		String hql="delete ProjectApplyEvaluate t where t.projectApply="+id;
		this.executeHSQL(hql);
	}

	public ProjectApplyEvaluate getProjectApplyEvaluateByAll(int paid,int expertid){
		String hql="from ProjectApplyEvaluate p where p.projectApply="+paid+" and p.projectExpert="+expertid;		
		return (ProjectApplyEvaluate) this.getObjectByHql(hql, null);
	}
	
	public ProjectApplyEvaluate getProjectApplyEvaluateByParams(int project , int subject , int trainingCollege,int expert){
		String hql="from ProjectApplyEvaluate p where p.projectApply.project="+project+" and p.projectApply.trainingSubject="+subject+" and p.projectApply.trainingCollege="+trainingCollege+" and p.projectExpert="+expert;		
		return (ProjectApplyEvaluate) this.getObjectByHql(hql, null);
	}
	
	public int checkProjectApplyEvaluate(int paid,int expertid){
		String hql="select count(*) from ProjectApplyEvaluate p where p.projectApply="+paid+" and p.projectExpert="+expertid;
		return Integer.valueOf(this.getObjectByHql(hql, null).toString());
	}
	
	public int getCountForExpert (Map<String,Object> searchMap){
		String hql="select count(*) from ProjectApplyEvaluate pae ,ProjectApply pa where pae.projectApply=pa.id and pa.status=2";
		if(searchMap.get("expertid")!=null){
			hql = hql + " and pae.projectExpert=" + searchMap.get("expertid");
		}
		if(searchMap.get("year")!=null){
			hql = hql + " and pa.project.year=" + searchMap.get("year");
		}
		if(searchMap.get("project")!=null){
			hql = hql + " and pa.project=" + searchMap.get("project");
		}
		if(searchMap.get("subject")!=null){
			hql = hql + " and pa.trainingSubject=" + searchMap.get("subject");
		}
		if(searchMap.get("status")!=null){
			hql = hql + " and pae.status=" + searchMap.get("status");
		}
		return Integer.valueOf(this.getObjectByHql(hql, null).toString());
	}
	
	public List<ProjectApplyEvaluate> getProjectApplyEvaluateListByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApplyEvaluate t where 1=1 ");
		if(searchMap.get("projectApplys")!=null && !searchMap.get("projectApplys").equals("")){
			sb.append(" and t.projectApply in (").append(searchMap.get("projectApplys")).append(")");
		}
		if(searchMap.get("projectExperts")!=null && !searchMap.get("projectExperts").equals("")){
			sb.append(" and t.projectExpert in (").append(searchMap.get("projectExperts")).append(")");
		}
		return getListByHSQL(sb.toString());
	}
}
