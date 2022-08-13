/**
 * 
 */
package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.ProjectCollegeRange;

/**
 * @author sj
 * 
 */
public interface IProjectCollegeRangeDao extends
	IBaseDao<ProjectCollegeRange, Integer>
{

    /**
     * @param collegeId
     * @return
     */
    public List<ProjectCollegeRange> getListByTrainingCollege(Integer collegeId);
    
    public void deleteByProject(int id);

}
