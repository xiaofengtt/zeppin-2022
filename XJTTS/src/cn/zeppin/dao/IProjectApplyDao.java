package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;

public interface IProjectApplyDao extends IBaseDao<ProjectApply, Integer>
{

    /**
     * @param projectId
     * @param subjectid
     * @return
     */
    List<ProjectApply> getList(Integer projectId, short subjectid);

    List<ProjectApply> getList(Integer projectId);
    
    public List getProjectApplyExpertInfo(int id);
  
    /**
     * 根据项目ID获取 项目问卷满分
     * 
     * @param id
     * @return
     */
    public int getTeacherPsqMaxScore(int id);
    
    /**
     * 获取 评分
     * @param submit
     * @return
     */
    public int getScoreBySubmit(int submit);
    
    /**
     * 获取 之前评分
     * @param pid
     * @param sid
     * @param tc
     * @param psq
     * @param valuator
     * @return
     */
    public List getSubmitForExpert(int pid,int sid,int tc,int psq,int valuator);
    
    public int getExpertPsqMaxScore(int id);
    
    /**
     * 获取 项目承训单位管理员数
     * 
     * @param projectId
     * @param subjectId
     * @param collegeId
     * @return
     */
    public Integer getTrainingAdminCount(Integer projectId ,Integer subjectId ,Integer collegeId);
    
    
    /**
     * 获取 项目承训单位管理员
     * 
     * @return
     */
    public List<Object[]> getTrainingAdminList(Integer projectId ,Integer subjectId ,Integer collegeId , String sortName, String sortType,int offset, int length);
    
    /**
     * 获取 项目个数
     * 
     * @param params
     * @param lityps
     * @return
     */
    public int getProjectApplyCountByParams(Map<String, Object> params,
	    List<ProjectType> lityps);

    
    /**
     * 获取专家评审总人数
     * @param psqId
     * @param projectId
     * @param subjectId
     * @param trainingId
     * @return
     */
    public int getExpertViewCount(int psqId,int projectId,int subjectId,int trainingId);
    
    /**
     * 获取专家评审总分数
     * @param psqId
     * @param projectId
     * @param subjectId
     * @param trainingId
     * @return
     */
    public int getExpertViewTotalScore(int psqId,int projectId,int subjectId,int trainingId);
    
    /**
     * 为评审专家获取项目
     * 
     * @param offset
     * @param length
     * @return
     */
    public List getListForExpert(Map<String, Object> searchMap ,int offset,int length);
    
    /**
     * 为评审专家获取总结项目
     * 
     * @param offset
     * @param length
     * @return
     */
    public List getListForEvaluate(Map<String, Object> searchMap ,int offset,int length);
    
    /**
     * 为评审专家获取项目
     * @return
     */
    public int getCountForExpert();
    /**
     * 根据参数获取 项目列表
     * 
     * @param params
     * @param lityps
     * @return
     */
    public List getProjectApplyByParams(Map<String, Object> params,
	    Map<String, String> sortParams, List<ProjectType> lityps,
	    int offset, int length);

    public List<ProjectApply> getProjectApplyByParams(Map<String, Object> params,
	    Map<String, String> sortParams, List<ProjectType> lityps);

    /**
     * 根据培训单位获取项目信息
     * 
     * @param id
     * @return
     */
    public List<ProjectApply> getlistByTrainingCollege(Integer id);

    /**
     * @param id
     * @param start
     * @param limit
     * @param sort
     * @return
     */
    List<ProjectApply> getlistByTrainingCollege(Integer id, int start,
	    int limit, String sort);

    /**
     * @param id
     * @param start
     * @param limit
     * @param sort
     * @return
     */
    List<ProjectApply> getlistForPojectApply(Integer id, int start, int limit,
	    String sort);

    /**
     * @param id
     * @param start
     * @param limit
     * @param sort
     * @return
     */
    List<ProjectApply> getlistForRunningPojectApply(Integer id, int start, int limit,
	    String sort);
    
    /**
     * @param id
     * @param id2
     * @param id3
     * @return
     */
    ProjectApply getByParas(Integer projectId, Integer trainingCollegeId,
	    Short trainingSubjectId);

    /**
     * @param collegeId
     * @return
     */
    int getCountByTrainingCollege(Integer collegeId);
    
    /**
     * 获取审核通过的申报记录数目
     * @param collegeId
     * @return
     */
    int getCountByTrainingCollegeId(Integer collegeId);

    /**
     * @param collegeId
     * @return
     */
    List<Integer> getDistinctProjectByTrainingCollege(Integer collegeId);

    /**
     * @param id
     * @param projectId
     * @param start
     * @param i
     * @param limit
     * @param sort
     * @return
     */
    List<ProjectApply> getlistForPojectApply(Integer id, int projectId,
	    int start, int i, int limit, String sort);
    /**
     * @param id
     * @param projectId
     * @param start
     * @param i
     * @param limit
     * @param sort
     * @return
     */
    List<ProjectApply> getlistForRunningPojectApply(Integer id, int projectId,
	    int start, int i, int limit, String sort);

    /**
     * @param id
     * @param projectId
     * @param subjectId
     * @return
     */
    int getCountByTrainingCollege(Integer id, int projectId, int subjectId);


    /**
     * 通过项目ID取得已项目中标信息
     * @param projectId
     * @return
     */
	List<ProjectApply> getCheckedProjectApply(Integer projectId);

	/**
	 * 通过承训单位ID查询所申报自主报名项目信息
	 * @param trainingCollege
	 * @return
	 */
	List getListByCollege(Integer trainingCollege);
	
	/**
	 * 通过项目ID查询审核通过的自主报名项目申报记录学科信息
	 * @param projectId
	 * @param trainingCollege
	 * @return
	 */
	List getListByProject(Integer projectId, Integer trainingCollege);
	
	 /**
	  * 根据条件获取可以报名的项目信息
	  * @param params
	  * @param lityps
	  * @return
	  */
	 public List getProjectByParams(Map<String, Object> params, List<ProjectType> lityps);
	
	 /**
	  * 根据条件获取可用项目
	  * @param params
	  * @param lityps
	  * @return
	  */
	 public List getProjectByParams(Map<String, Object> params);
	 
	 public List getProjectInfoByCollege(Integer trainingCollege);
	 
	 public List getProjectApplyByParams(Map<String, Object> params, Map<String, String> sortParams, int offset, int length);
	 
	 public int getProjectApplyCountByParams(Map<String, Object> params,Map<String, String> sortParams);
	 
	 /**
	  * 获取搜索结果
	  * @param params
	  * @param sortParams
	  * @param offset
	  * @param length
	  * @return
	  */
	 public List getSearchByParams(Map<String, Object> params, Map<String, String> sortParams, int offset, int length);
	 
	 /**
	  * 获取搜索结果
	  * @param params
	  * @param sortParams
	  * @return
	  */
	 public int getSearchCountByParams(Map<String, Object> params,Map<String, String> sortParams);
	 
	 public List<ProjectApply> getProjectApplyDByParams(Map<String, Object> params, Map<String, String> sortParams, int offset, int length);
	 
	 public int getProjectApplyDCountByParams(Map<String, Object> params);
}
