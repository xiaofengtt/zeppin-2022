package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.text.StrBuilder;
import org.hibernate.Query;

import cn.zeppin.dao.IProjectApplyDao;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectType;

public class ProjectApplyDaoImpl extends BaseDaoImpl<ProjectApply, Integer>
		implements IProjectApplyDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.IProjectApplyDao#getList(java.lang.Integer, short)
	 */
	@Override
	public List<ProjectApply> getList(Integer projectId, short subjectid) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply where status=2 and project="
				+ projectId + " and trainingSubject=" + subjectid;

		return getListByHSQL(hqlString);
	}

	public List getProjectApplyExpertInfo(int id){
		StringBuilder sb=new StringBuilder();
		sb.append("from ProjectApply pa,Project p,Submit s where pa.project=p.id and pa.project=s.project and pa.trainingSubject=s.trainingSubject and pa.trainingCollege=s.trainingCollege and p.psqByProjectJudgePsq=s.psq and pa.id=");
		sb.append(id);
		return this.getListByHSQL(sb.toString());
	}
    public List getSubmitForExpert(int pid,int sid,int tc,int psq,int valuator){
    	StringBuilder sb=new StringBuilder();
    	sb.append("from Submit s where s.project=");
    	sb.append(pid);
    	sb.append(" and s.trainingSubject=");
    	sb.append(sid);
    	sb.append(" and s.trainingCollege=");
    	sb.append(tc);
    	sb.append(" and s.psq=");
    	sb.append(psq);
    	sb.append(" and s.valuator=");
    	sb.append(valuator);
    	return this.getListByHSQL(sb.toString());
    }
	
    public int getScoreBySubmit(int submit){
    	String sql="SELECT SUM(score) from result r where r.submit="+submit;
    	return Integer.valueOf(this.getObjectBySql(sql, null).toString());
    }
	
    public List getListForExpert(int expertid,int offset,int length){
    	StringBuilder sb = new StringBuilder();
    	sb.append("from ProjectApply pa,ProjectApplyExpert pae where pa.id=pae.projectApply and pa.status=1 and pae.projectExpert=");
    	sb.append(expertid);
    	return this.getListForPage(sb.toString(), offset, length);
    }
    
    public int getCountForExpert(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("select count(*) from ProjectApply t where t.status=1");
    	Object result = this.getObjectByHql(sb.toString(), null);
    	return Integer.parseInt(result.toString());  	
    }
    
	public int getTeacherPsqMaxScore(int id) {
		String sql = "SELECT SUM(s) FROM ( SELECT MAX(SCORE) as s FROM project pr , psq ps , question q ,answer a WHERE pr.EVALUATION_TEACHER_PSQ=ps.ID and q.PSQ=ps.ID and a.QUESTION=q.id and q.IS_COUNT=1 and pr.ID="+id+" GROUP BY a.QUESTION ) as z;";
		int maxScore = Integer.valueOf(this.getObjectBySql(sql, null).toString());
		return maxScore;
	}
	
	public int getExpertPsqMaxScore(int id) {
		String sql = "SELECT SUM(s) FROM ( SELECT MAX(SCORE) as s FROM project pr , psq ps , question q ,answer a WHERE pr.PROJECT_JUDGE_PSQ=ps.ID and q.PSQ=ps.ID and a.QUESTION=q.id and q.IS_COUNT=1 and pr.ID="+id+" GROUP BY a.QUESTION ) as z;";
		int maxScore = Integer.valueOf(this.getObjectBySql(sql, null).toString());
		return maxScore;
	}

	 /**
     * 获取 项目承训单位管理员数
     * 
     * @param projectId
     * @param subjectId
     * @param collegeId
     * @return
     */
    public Integer getTrainingAdminCount(Integer projectId ,Integer subjectId ,Integer collegeId){
    	StringBuilder sb = new StringBuilder();
    	sb.append("select count(distinct ta.id) from training_admin ta ");
		sb.append(" left join training_admin_authority taa on ta.id = taa.training_admin and taa.project=").append(projectId);
		sb.append(" where ta.status = 1 and ta.organization=").append(collegeId);
		sb.append(" and (ta.restrict_right=0 or (taa.project=").append(projectId).append(" and (taa.training_subject is null or taa.training_subject=").append(subjectId).append(")))");
		Object result = this.getObjectBySql(sb.toString(), null);
		return Integer.parseInt(result.toString());
    }
    
    
    /**
     * 获取 项目承训单位管理员
     * 
     * @return
     */
    public List<Object[]> getTrainingAdminList(Integer projectId ,Integer subjectId ,Integer collegeId , String sortName, String sortType,int offset, int length){
    	StringBuilder sb = new StringBuilder();
		sb.append("select distinct ta.id , ta.name , ta.mobile , ta.sex , e.name as ename, tc.name as tcname, ta.department , ta.phone ,ta.job_duty , taa.classes from training_admin ta ");
		sb.append(" join ethnic e on e.id = ta.nationally ");
		sb.append(" join training_college tc on tc.id = ta.organization");
		sb.append(" left join training_admin_authority taa on ta.id = taa.training_admin and taa.project=").append(projectId);
		sb.append(" where ta.status = 1 and ta.organization=").append(collegeId);
		sb.append(" and (ta.restrict_right=0 or (taa.project=").append(projectId).append(" and (taa.training_subject is null or taa.training_subject=").append(subjectId).append(")))");
		// 排序 参数
		if (sortName != null && !sortName.equals("") && sortType != null && !sortType.equals("")) {
			sb.append(" order by ta." + sortName + " " + sortType);
		}
		return this.getListPage(sb.toString(), offset, length, null);
    }
	
	
	public List<ProjectApply> getList(Integer projectId) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply where status=2 and project="
				+ projectId;

		return getListByHSQL(hqlString);
	}

	@Override
	public int getProjectApplyCountByParams(Map<String, Object> params,
			List<ProjectType> lityps) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ProjectApply t,Project p where t.project=p.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append("and t.project = " + params.get("projectId"));
			}
			
			if(params.get("enrollType") != null){
				sb.append("and p.enrollType = " + params.get("enrollType"));
			}
			if(params.get("status") != null){
				sb.append("and t.status = " + params.get("status"));
			}
		}

		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	public int getExpertViewCount(int psqId,int projectId,int subjectId,int trainingId){
    	StringBuilder sb = new StringBuilder();
    	sb.append("select count(*) from Submit s where");
    	sb.append(" s.psq=");sb.append(psqId);
    	sb.append(" and s.project=");sb.append(projectId);
    	sb.append(" and s.trainingSubject=");sb.append(subjectId);
    	sb.append(" and s.trainingCollege=");sb.append(trainingId);
    	int count = Integer.parseInt(getObjectByHql(sb.toString(), null).toString());
    	return count;
    }
    

    public int getExpertViewTotalScore(int psqId,int projectId,int subjectId,int trainingId){
    	StringBuilder sb = new StringBuilder();
    	sb.append("select sum(r.score) from Submit s, Result r where s.id=r.submit");
    	sb.append(" and s.psq=");sb.append(psqId);
    	sb.append(" and s.project=");sb.append(projectId);
    	sb.append(" and s.trainingSubject=");sb.append(subjectId);
    	sb.append(" and s.trainingCollege=");sb.append(trainingId);
    	int sum = Integer.parseInt(getObjectByHql(sb.toString(), null).toString());
    	return sum;
    }
	
	@Override
	public List getProjectApplyByParams(Map<String, Object> params,
			Map<String, String> sortParams, List<ProjectType> lityps,
			int offset, int length) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApply t,Project p where t.project=p.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append("and t.project = " + params.get("projectId"));
			}
			if(params.get("enrollType") != null){
				sb.append("and p.enrollType = " + params.get("enrollType"));
			}
			if(params.get("year") != null){
				sb.append("and p.year = '" + params.get("year") + "'");
			}
			if(params.get("status") != null){
				sb.append("and t.status = " + params.get("status"));
			}
		}
		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				if (key.endsWith("0")) {
					String tkey = key.substring(0, key.length() - 1);
					sb.append("p." + tkey + " " + value + ",");
				} else {
					sb.append("t." + key + " " + value + ",");
				}
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public List getProjectApplyByParams(Map<String, Object> params,
			Map<String, String> sortParams, List<ProjectType> lityps) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApply t,Project p where t.project=p.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
		}
		if (lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" p.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}

		if (sortParams != null && sortParams.size() > 0) {
			sb.append(" order by ");
			for (String key : sortParams.keySet()) {
				String value = sortParams.get(key);
				if (key.endsWith("0")) {
					String tkey = key.substring(0, key.length() - 1);
					sb.append("p." + tkey + " " + value + ",");
				} else {
					sb.append("t." + key + " " + value + ",");
				}
			}
			sb.delete(sb.length() - 1, sb.length());
		}

		return this.getListByHSQL(sb.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.dao.IProjectApplyDao#getlistByTrainingCollege(java.lang.Integer
	 * )
	 */
	@Override
	public List<ProjectApply> getlistByTrainingCollege(Integer id) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply where TrainingCollege=" + id;
		return getListByHSQL(hqlString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.dao.IProjectApplyDao#getlistByTrainingCollege(java.lang.Integer
	 * , int, int, java.lang.String)
	 */
	@Override
	public List<ProjectApply> getlistByTrainingCollege(Integer id, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply where status=2 and trainingCollege="
				+ id;
		if (!sort.equals(null) && !sort.equals("")) {
			hqlString += " order by " + sort;
		}
		return getListForPage(hqlString, start, limit, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.dao.IProjectApplyDao#getlistForPojectApply(java.lang.Integer,
	 * int, int, java.lang.String)
	 */
	@Override
	public List<ProjectApply> getlistForPojectApply(Integer id, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply where  trainingCollege=" + id;
		if (!sort.equals(null) && !sort.equals("")) {
			hqlString += " order by " + sort;
		}
		return getListForPage(hqlString, start, limit, null);
	}

	
	public List<ProjectApply> getlistForRunningPojectApply(Integer id, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply where status=2 and trainingCollege=" + id;
		if (!sort.equals(null) && !sort.equals("")) {
			hqlString += " order by " + sort;
		}
		return getListForPage(hqlString, start, limit, null);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.dao.IProjectApplyDao#getByParas(java.lang.Integer,
	 * java.lang.Integer, java.lang.Short)
	 */
	@Override
	public ProjectApply getByParas(Integer projectId,
			Integer trainingCollegeId, Short trainingSubjectId) {
		// TODO Auto-generated method stub
		ProjectApply projectApply = new ProjectApply();
		String hqlString = "from ProjectApply where project=" + projectId
				+ " and trainingCollege=" + trainingCollegeId
				+ " and trainingSubject=" + trainingSubjectId;
		List<ProjectApply> lstProjectApplies = getListByHSQL(hqlString);
		if (lstProjectApplies != null) {
			if (lstProjectApplies.size() > 0) {
				projectApply = lstProjectApplies.get(0);
			}
		}

		return projectApply;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.dao.IProjectApplyDao#getCountByTrainingCollege(java.lang.Integer
	 * )
	 */
	@Override
	public int getCountByTrainingCollege(Integer collegeId) {
		// TODO Auto-generated method stub
		String hqlString = "select count(*) from ProjectApply where trainingCollege="
				+ collegeId;
		int count = Integer
				.parseInt(getObjectByHql(hqlString, null).toString());
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.dao.IProjectApplyDao#getDistinctProjectByTrainingCollege(java
	 * .lang.Integer)
	 */
	@Override
	public List<Integer> getDistinctProjectByTrainingCollege(Integer collegeId) {
		// TODO Auto-generated method stub

		String sqlString = "SELECT DISTINCT `PROEJCT` FROM `project_apply` WHERE TRAINING_COLLEGE= "
				+ collegeId;
		List projectsList = getListBySQL(sqlString, null);
		return projectsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.dao.IProjectApplyDao#getlistForPojectApply(java.lang.Integer,
	 * int, int, int, int, java.lang.String)
	 */
	@Override
	public List<ProjectApply> getlistForPojectApply(Integer id, int projectId,
			int sid, int i, int limit, String sort) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply where  trainingCollege=" + id;

		if (projectId != -1) {

			hqlString += " and project=" + projectId;
		}
		if (sid != -1) {

			hqlString += " and trainingSubject=" + sid;
		}

		if (!sort.equals(null) && !sort.equals("")) {
			hqlString += " order by " + sort;
		}
		return getListForPage(hqlString, i, limit, null);
	}

	public List<ProjectApply> getlistForRunningPojectApply(Integer id, int projectId,
			int sid, int i, int limit, String sort) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply where status=2 and trainingCollege=" + id;

		if (projectId != -1) {

			hqlString += " and project=" + projectId;
		}
		if (sid != -1) {

			hqlString += " and trainingSubject=" + sid;
		}

		if (!sort.equals(null) && !sort.equals("")) {
			hqlString += " order by " + sort;
		}
		return getListForPage(hqlString, i, limit, null);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.dao.IProjectApplyDao#getCountByTrainingCollege(java.lang.Integer
	 * , int, int)
	 */
	@Override
	public int getCountByTrainingCollege(Integer id, int projectId,
			int subjectId) {
		// TODO Auto-generated method stub
		String hqlString = "select count(*) from ProjectApply where trainingCollege="
				+ id;
		if (projectId != -1) {

			hqlString += " and project=" + projectId;
		}
		if (subjectId != -1) {

			hqlString += " and trainingSubject=" + subjectId;
		}

		int count = Integer
				.parseInt(getObjectByHql(hqlString, null).toString());
		return count;
	}

	/**
	 * 通过项目ID取得已项目中标信息
	 * 
	 * @param projectId
	 * @return
	 */
	@Override
	public List<ProjectApply> getCheckedProjectApply(Integer projectId) {
		// TODO Auto-generated method stub
		String hqlString = "from ProjectApply pa where pa.status=2 and pa.project="
				+ projectId;
		hqlString += " order by pa.project desc, pa.trainingSubject, pa.trainingCollege";
		return getListByHSQL(hqlString);
	}

	@Override
	public List getListByCollege(Integer trainingCollege) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApply pa,Project p where pa.project=p.id and pa.status=2 and p.enrollType=2 and p.traintype=1");
		if(trainingCollege>0 && trainingCollege != null){
			sb.append(" and pa.trainingCollege=");
			sb.append(trainingCollege);
		}
		sb.append("group by pa.project");
		
		return getListByHSQL(sb.toString());
	}

	@Override
	public List getListByProject(Integer projectId, Integer trainingCollege) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApply pa,Project p where pa.project=p.id and pa.status=2 and p.enrollType=2 and p.traintype=1");
		if(projectId > 0 && projectId != null){
			sb.append(" and pa.project=");
			sb.append(projectId);
		}
		
		if(trainingCollege>0 && trainingCollege != null){
			sb.append(" and pa.trainingCollege=");
			sb.append(trainingCollege);
		}
		
		return getListByHSQL(sb.toString());
	}

	@Override
	public List getProjectByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApply t,Project p where t.project=p.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append("and t.project = " + params.get("projectId"));
			}
			if(params.get("enrollType") != null){
				sb.append("and p.enrollType = " + params.get("enrollType"));
			}
			if(params.get("status") != null){
				sb.append("and t.status = " + params.get("status"));
			}
			if(params.get("year") != null){
				sb.append("and p.year = '" + params.get("year") + "'");
			}
		}
		sb.append(" group by p.id");
		sb.append(" order by t.id desc");
		
		Query query = getCurrentSession().createQuery(sb.toString());
		
		List result = query.list();
		return result;
	}
	
	@Override
	public List getProjectByParams(Map<String, Object> params,
			List<ProjectType> lityps) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApply t,Project p where t.project=p.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append("and t.project = " + params.get("projectId"));
			}
			if(params.get("enrollType") != null){
				sb.append("and p.enrollType = " + params.get("enrollType"));
			}
			if(params.get("status") != null){
				sb.append("and t.status = " + params.get("status"));
			}
			if(params.get("trainingCollege") != null){
				sb.append("and t.trainingCollege = " + params.get("trainingCollege"));
			}
		}
		if (lityps != null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" p.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
		}
		
//		sb.append(" group by t.project");
		if(params.get("group") != null){
			sb.append(" group by " + params.get("group"));
		}
		sb.append(" order by t.id desc");
		
		Query query = getCurrentSession().createQuery(sb.toString());
		
		List result = query.list();
		return result;
	}

	@Override
	public List getProjectInfoByCollege(Integer trainingCollege) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
//		sb.append("from ProjectApply pa,Project p where pa.project=p.id and p.status=2 and pa.status=2 and p.enrollType=2 ");
		sb.append("from ProjectApply pa,Project p where pa.project=p.id and p.status=2 and pa.status=2 ");
		if(trainingCollege>0 && trainingCollege != null){
			sb.append(" and pa.trainingCollege=");
			sb.append(trainingCollege);
		}
//		sb.append(" group by pa.project");
		
		sb.append(" order by pa.creattime desc");
		
		return getListByHSQL(sb.toString());
	}
	
	
}
