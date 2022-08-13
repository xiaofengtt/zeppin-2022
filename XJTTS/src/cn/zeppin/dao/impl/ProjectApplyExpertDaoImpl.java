package cn.zeppin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectApplyExpertDao;
import cn.zeppin.entity.ProjectApplyExpert;

public class ProjectApplyExpertDaoImpl extends BaseDaoImpl<ProjectApplyExpert, Integer> implements IProjectApplyExpertDao {
	public void deleteByProjectApply(int id){
		String hql="delete ProjectApplyExpert t where t.projectApply="+id;
		this.executeHSQL(hql);
	}

	public ProjectApplyExpert getProjectApplyExpertByAll(int paid,int expertid){
		String hql="from ProjectApplyExpert p where p.projectApply="+paid+" and p.projectExpert="+expertid;		
		return (ProjectApplyExpert) this.getObjectByHql(hql, null);
	}
	
	public ProjectApplyExpert getProjectApplyExpertByParams(int project , int subject , int trainingCollege,int expert){
		String hql="from ProjectApplyExpert p where p.projectApply.project="+project+" and p.projectApply.trainingSubject="+subject+" and p.projectApply.trainingCollege="+trainingCollege+" and p.projectExpert="+expert;		
		return (ProjectApplyExpert) this.getObjectByHql(hql, null);
	}
	
	public int checkProjectApplyExpert(int paid,int expertid){
		String hql="select count(*) from ProjectApplyExpert p where p.projectApply="+paid+" and p.projectExpert="+expertid;
		return Integer.valueOf(this.getObjectByHql(hql, null).toString());
	}
	
	public int getCountForExpert (Map<String,Object> searchMap){
		String hql="select count(*) from ProjectApplyExpert pae ,ProjectApply pa,Project p,Psq psq where pae.projectApply=pa.id and pa.project=p.id and p.psqByProjectJudgePsq=psq.id and psq.status=2 and pa.status in(1,2) and p.status=2";
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
	
	public List<ProjectApplyExpert> getProjectApplyExpertListByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApplyExpert t where 1=1 ");
		if(searchMap.get("projectApplys")!=null && !searchMap.get("projectApplys").equals("")){
			sb.append(" and t.projectApply in (").append(searchMap.get("projectApplys")).append(")");
		}
		if(searchMap.get("projectExperts")!=null && !searchMap.get("projectExperts").equals("")){
			sb.append(" and t.projectExpert in (").append(searchMap.get("projectExperts")).append(")");
		}
		return getListByHSQL(sb.toString());
	}
}
