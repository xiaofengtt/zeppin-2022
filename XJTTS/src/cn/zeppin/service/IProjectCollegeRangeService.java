/**
 * 
 */
package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.ProjectCollegeRange;

/**
 * @author sj
 * 
 */
public interface IProjectCollegeRangeService extends
	IBaseService<ProjectCollegeRange, Integer>
{

    /**
     * @param id
     * @return
     */
    public List<ProjectCollegeRange> getListByTrainingCollege(Integer collegeId);

    public void deleteByProject(int id);
}
