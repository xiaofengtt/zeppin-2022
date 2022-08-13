/**
 * 
 */
package cn.zeppin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.dao.IProjectCollegeRangeDao;
import cn.zeppin.entity.ProjectCollegeRange;

/**
 * @author sj
 * 
 */
public class ProjectCollegeRangeDaoImpl extends
	BaseDaoImpl<ProjectCollegeRange, Integer> implements
	IProjectCollegeRangeDao
{

	public void deleteByProject(int id){
		String hql = "delete ProjectCollegeRange t where t.project=" + id;
		this.executeHSQL(hql);
	}
	
	
	
    @Override
    public List<ProjectCollegeRange> getListByTrainingCollege(Integer collegeId)
    {
	// TODO Auto-generated method stub
	List<ProjectCollegeRange> lstCollegeRanges = new ArrayList<>();
	String hqlString = "from ProjectCollegeRange where trainingCollege="
		+ collegeId;
	lstCollegeRanges = getListByHSQL(hqlString);
	return lstCollegeRanges;
    }

}
