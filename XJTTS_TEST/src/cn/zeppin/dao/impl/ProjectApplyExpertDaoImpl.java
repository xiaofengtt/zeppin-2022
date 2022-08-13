package cn.zeppin.dao.impl;

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
	
	public int checkProjectApplyExpert(int paid,int expertid){
		String hql="select count(*) from ProjectApplyExpert p where p.projectApply="+paid+" and p.projectExpert="+expertid;
		return Integer.valueOf(this.getObjectByHql(hql, null).toString());
	}
	
	public int getCountForExpert (int expertid){
		String hql="select count(*) from ProjectApplyExpert pae ,ProjectApply pa where pae.projectApply=pa.id and pa.status=1 and pae.projectExpert="+expertid;
		return Integer.valueOf(this.getObjectByHql(hql, null).toString());
	}
	
}
