/**
 * 
 */
package cn.zeppin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.dao.IProjectSubjectRangeDao;
import cn.zeppin.entity.ProjectSubjectRange;

/**
 * @author sj
 * 
 */
public class ProjectSubjectRangeDaoImpl extends
	BaseDaoImpl<ProjectSubjectRange, Integer> implements
	IProjectSubjectRangeDao
{

	public void deleteByProject(int id){
		String hql = "delete ProjectSubjectRange t where t.project=" + id;
		this.executeHSQL(hql);
	}
	
	
    @Override
    public List<ProjectSubjectRange> getListByTrainingSubject(Integer subjectId)
    {
	// TODO Auto-generated method stub
	List<ProjectSubjectRange> lstSubjectRanges = new ArrayList<>();
	String hqlString = "from ProjectSubjectRange where trainingSubject="
		+ subjectId;
	lstSubjectRanges = getListByHSQL(hqlString);
	return lstSubjectRanges;
    }

    public List<ProjectSubjectRange> getListByProject(int projectid)
    {
	// TODO Auto-generated method stub
	List<ProjectSubjectRange> lstSubjectRanges = new ArrayList<>();
	String hqlString = "from ProjectSubjectRange where project="+ projectid;
	lstSubjectRanges = getListByHSQL(hqlString);
	return lstSubjectRanges;
    }
    
}
