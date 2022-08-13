/**
 * 
 */
package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.ProjectSubjectRange;

/**
 * @author sj
 * 
 */
public interface IProjectSubjectRangeDao extends
	IBaseDao<ProjectSubjectRange, Integer>
{
	public void deleteByProject(int id);
    /**
     * @param subjectId
     * @return
     */
    public List<ProjectSubjectRange> getListByTrainingSubject(Integer subjectId);
    /**
     * @param collegeid
     * @return
     */
    public List<ProjectSubjectRange> getListByProject(int projectid);

}
