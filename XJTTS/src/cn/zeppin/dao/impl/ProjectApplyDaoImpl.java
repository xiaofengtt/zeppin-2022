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
	
    public List getListForExpert(Map<String, Object> searchMap,int offset,int length){
    	StringBuilder sb = new StringBuilder();
    	sb.append("from ProjectApply pa,ProjectApplyExpert pae,Project p,Psq psq where pa.id=pae.projectApply and pa.project=p.id and p.psqByProjectJudgePsq=psq.id and psq.status=2 and pa.status in(1,2) and p.status=2 ");
    	if(searchMap.get("expertid")!=null){
			sb.append(" and pae.projectExpert=").append(searchMap.get("expertid"));
		}
		if(searchMap.get("year")!=null){
			sb.append(" and pa.project.year=").append(searchMap.get("year"));
		}
		if(searchMap.get("project")!=null){
			sb.append(" and pa.project=").append(searchMap.get("project"));
		}
		if(searchMap.get("subject")!=null){
			sb.append(" and pa.trainingSubject=").append(searchMap.get("subject"));
		}
		if(searchMap.get("status")!=null){
			sb.append(" and pae.status=").append(searchMap.get("status"));
		}
    	return this.getListForPage(sb.toString(), offset, length);
    }
    
    public List getListForEvaluate(Map<String, Object> searchMap,int offset,int length){
    	StringBuilder sb = new StringBuilder();
    	sb.append("from ProjectApply pa,ProjectApplyEvaluate pae where pa.id=pae.projectApply and pa.status=2");
    	if(searchMap.get("expertid")!=null){
			sb.append(" and pae.projectExpert=").append(searchMap.get("expertid"));
		}
		if(searchMap.get("year")!=null){
			sb.append(" and pa.project.year=").append(searchMap.get("year"));
		}
		if(searchMap.get("project")!=null){
			sb.append(" and pa.project=").append(searchMap.get("project"));
		}
		if(searchMap.get("subject")!=null){
			sb.append(" and pa.trainingSubject=").append(searchMap.get("subject"));
		}
		if(searchMap.get("status")!=null){
			sb.append(" and pae.status=").append(searchMap.get("status"));
		}
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
		Object score = this.getObjectBySql(sql, null);
		if(score == null){
			score="0";
		}
		int maxScore = Integer.valueOf(score.toString());
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
		sb.append("select count(*) from ProjectApply t,Project p,ProjectAdmin pa where t.project=p.id and p.creator=pa.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append(" and t.project = " + params.get("projectId"));
			}
			if (params.get("subjectId") != null) {
				sb.append(" and t.trainingSubject = " + params.get("subjectId"));
			}
			if (params.get("projectType.scode") != null) {
				sb.append(" and p.projectType.scode like '%" + params.get("projectType.scode") + "%'");
			}
			if(params.get("enrollType") != null){
				sb.append(" and p.enrollType = " + params.get("enrollType"));
			}
			if(params.get("status") != null){
				sb.append(" and t.status = " + params.get("status"));
			}
			if(params.get("year") != null){
				sb.append(" and p.year = '" + params.get("year") + "'");
			}
			
			if(params.get("organization") != null && !"".equals(params.get("organization").toString())){
				sb.append(" and pa.organization=");
				sb.append(params.get("organization"));
			}
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
		sb.append("from ProjectApply t,Project p,ProjectAdmin pa where t.project=p.id and p.creator=pa.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append(" and t.project = " + params.get("projectId"));
			}
			if (params.get("projectType.scode") != null) {
				sb.append(" and p.projectType.scode like '%" + params.get("projectType.scode") + "%'");
			}
			if (params.get("subjectId") != null) {
				sb.append(" and t.trainingSubject = " + params.get("subjectId"));
			}
			if(params.get("enrollType") != null){
				sb.append(" and p.enrollType = " + params.get("enrollType"));
			}
			if(params.get("year") != null){
				sb.append(" and p.year = '" + params.get("year") + "'");
			}
			if(params.get("status") != null){
				sb.append(" and t.status = " + params.get("status"));
			}
			if(params.get("organization") != null && !"".equals(params.get("organization").toString())){
				sb.append(" and pa.organization=");
				sb.append(params.get("organization"));
			}
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

		return this.getListForPage(sb.toString(), offset, length);
	}

	@Override
	public List<ProjectApply> getProjectApplyByParams(Map<String, Object> params,
			Map<String, String> sortParams, List<ProjectType> lityps) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ProjectApply t where 1=1 ");

		if (params != null && params.size() > 0) {
			if (params.get("year")!=null && !params.get("year").equals("0")){
				sb.append(" and t.project.year=").append((params.get("year")));
			}
			if (params.get("project")!=null && !params.get("project").equals("0")){
				sb.append(" and t.project=").append((params.get("project")));
			}
			if (params.get("trainingSubject")!=null && !params.get("trainingSubject").equals("0")){
				sb.append(" and t.trainingSubject=").append((params.get("trainingSubject")));
			}
			if (params.get("trainingCollege")!=null && !params.get("trainingCollege").equals("0")){
				sb.append(" and t.trainingCollege=").append((params.get("trainingCollege")));
			}
			if (params.get("status")!=null && !params.get("status").equals("-1")){
				sb.append(" and t.status=").append((params.get("status")));
			}
			if (params.get("ids")!=null && !params.get("ids").equals("")){
				sb.append(" and t.id in (").append(params.get("ids")).append(")");
			}
		}
		if (lityps != null && lityps.size() > 0) {
			StringBuilder sbTyps = new StringBuilder();
			sbTyps.append(" and (");
			for (ProjectType pt : lityps) {
				sbTyps.append(" t.project.projectType.scode like '%").append(pt.getScode()).append("%' or");
			}
			sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
			sbTyps.append(") ");
			sb.append(sbTyps);
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
		sb.append("from ProjectApply pa,Project p where pa.project=p.id and pa.status=2 and p.enrollType=2 and p.traintype in(1,3)");
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
		sb.append("from ProjectApply pa,Project p where pa.project=p.id and pa.status=2 and p.enrollType=2 and p.traintype in(1,3)");
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
				sb.append(" and t.project = " + params.get("projectId"));
			}
			if(params.get("enrollType") != null){
				sb.append(" and p.enrollType = " + params.get("enrollType"));
			}
			if(params.get("status") != null){
				sb.append(" and t.status = " + params.get("status"));
			}
			if(params.get("year") != null){
				sb.append(" and p.year = '" + params.get("year") + "'");
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
				sb.append(" and t.project = " + params.get("projectId"));
			}
			if(params.get("enrollType") != null){
				sb.append(" and p.enrollType = " + params.get("enrollType"));
			}
			if(params.get("status") != null){
				sb.append(" and t.status = " + params.get("status"));
			}
			if(params.get("trainingCollege") != null){
				sb.append(" and t.trainingCollege = " + params.get("trainingCollege"));
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

	@Override
	public int getCountByTrainingCollegeId(Integer collegeId) {
		// TODO Auto-generated method stub
				String hqlString = "select count(*) from ProjectApply where status=2 and trainingCollege="
						+ collegeId;
				int count = Integer
						.parseInt(getObjectByHql(hqlString, null).toString());
				return count;
	}

	@Override
	public List getProjectApplyByParams(Map<String, Object> params,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select d.id,d.title,d.resourcePath,d.createtime,t.id from ProjectApply t,Project p,Document d where t.project=p.id and t.documentByStartMessage=d.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append(" and t.project = " + params.get("projectId"));
			}
			if(params.get("titles") != null){
				sb.append(" and d.title like '%" + params.get("titles") + "%'");
			}
			if(params.get("status") != null){
				sb.append(" and t.status = " + params.get("status"));
				sb.append(" and p.status = " + params.get("status"));
			}
			if(params.get("trainingCollege") != null){
				sb.append(" and t.trainingCollege = " + params.get("trainingCollege"));
			}
			
			sb.append(" and p.projectType.projectLevel<3");
			if(params.get("projecttype") != null){
				sb.append(" and p.projectType.scode like '" + params.get("projecttype")+"%'");
			}
		}
		
		sb.append(" group by d.title");
//		if(params.get("group") != null){
//			sb.append(" group by " + params.get("group"));
//		}
		sb.append(" order by d.createtime desc");
		
//		Query query = getCurrentSession().createQuery(sb.toString());
//		
//		List result = query.list();
//		return result;
		return getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getProjectApplyCountByParams(Map<String, Object> params,
			Map<String, String> sortParams) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(distinct d.title) from ProjectApply t,Project p,Document d where t.project=p.id and t.documentByStartMessage=d.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append(" and t.project = " + params.get("projectId"));
			}
			if(params.get("titles") != null){
				sb.append(" and d.title like '%" + params.get("titles") + "%'");
			}
			if(params.get("status") != null){
				sb.append(" and t.status = " + params.get("status"));
				sb.append(" and p.status = " + params.get("status"));
			}
			if(params.get("trainingCollege") != null){
				sb.append(" and t.trainingCollege = " + params.get("trainingCollege"));
			}
			
			sb.append(" and p.projectType.projectLevel<3");
			if(params.get("projecttype") != null){
				sb.append(" and p.projectType.scode like '" + params.get("projecttype")+"%'");
			}
		}
		
//		sb.append(" group by d.title");
//		if(params.get("group") != null){
//			sb.append(" group by " + params.get("group"));
//		}
		sb.append(" order by d.createtime desc");
		
		Object result = this.getObjectByHql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	@Override
	public List getSearchByParams(Map<String, Object> params,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select w.pid,w.id,w.title,w.type,w.createtime from webdocs w where 1=1 ");
		
		if(params != null && params.size() > 0){
			//搜索参数
			if(params.get("titles") != null){
				sb.append(" and w.title like '%" + params.get("titles") + "%'");
			}
//			sb.append("");
			
		}
		
		sb.append(" group by w.title");
		sb.append(" order by w.createtime desc");
		
		Query query= getCurrentSession().createSQLQuery(sb.toString())
				.setFirstResult(offset)
				.setMaxResults(length);
		List result = query.list();
		return result;
	}

	@Override
	public int getSearchCountByParams(Map<String, Object> params,
			Map<String, String> sortParams) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(distinct w.title) from webdocs w where 1=1");
		
		if(params != null && params.size() > 0){
			//搜索参数
			if(params.get("titles") != null){
				sb.append(" and w.title like '%" + params.get("titles") + "%'");
			}
//			sb.append("");
			
		}
		
		sb.append(" order by w.createtime desc");
		
		Object result = this.getObjectBySql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}

	@Override
	public List<ProjectApply> getProjectApplyDByParams(Map<String, Object> params,
			Map<String, String> sortParams, int offset, int length) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select t from ProjectApply t,Project p,ProjectAdmin pa where t.project=p.id and p.creator=pa.id ");

		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append(" and t.project = " + params.get("projectId"));
			}
			if(params.get("subjectId") != null){
				sb.append(" and t.trainingSubject = " + params.get("subjectId"));
			}
			if(params.get("status") != null){
				sb.append(" and t.status = " + params.get("status"));
			}
			if(params.get("trainingCollege") != null){
				sb.append(" and t.trainingCollege = " + params.get("trainingCollege"));
			}
			
			if(params.get("year") != null){
				sb.append(" and p.year = '" + params.get("year") + "'");
			}
			
			if(params.get("organization") != null && !"".equals(params.get("organization").toString())){
				sb.append(" and pa.organization=");
				sb.append(params.get("organization"));
			}
		}
		if (params.containsKey("lityps") && params.get("lityps") != null) {
			@SuppressWarnings("unchecked")
			List<ProjectType> lityps = (List<ProjectType>) params.get("lityps");
			if(lityps.size() > 0){
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%").append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		
		sb.append(" order by t.id desc");
		
		return getListForPage(sb.toString(), offset, length);
	}

	@Override
	public int getProjectApplyDCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) from ProjectApply t,Project p,ProjectAdmin pa where t.project=p.id and p.creator=pa.id ");
		if (params != null && params.size() > 0) {
			// 搜索参数
			if (params.get("projectId") != null) {
				sb.append(" and t.project = " + params.get("projectId"));
			}
			if(params.get("subjectId") != null){
				sb.append(" and t.trainingSubject = " + params.get("subjectId"));
			}
			if(params.get("status") != null){
				sb.append(" and t.status = " + params.get("status"));
			}
			if(params.get("trainingCollege") != null){
				sb.append(" and t.trainingCollege = " + params.get("trainingCollege"));
			}
			
			if(params.get("year") != null){
				sb.append(" and p.year = '" + params.get("year") + "'");
			}
			
			if(params.get("organization") != null && !"".equals(params.get("organization").toString())){
				sb.append(" and pa.organization=");
				sb.append(params.get("organization"));
			}
		}
		if (params.containsKey("lityps") && params.get("lityps") != null) {
			@SuppressWarnings("unchecked")
			List<ProjectType> lityps = (List<ProjectType>) params.get("lityps");
			if(lityps.size() > 0){
				StringBuilder sbTyps = new StringBuilder();
				sbTyps.append(" and (");
				for (ProjectType pt : lityps) {
					sbTyps.append(" p.projectType.scode like '%").append(pt.getScode()).append("%' or");
				}
				sbTyps.delete(sbTyps.length() - 2, sbTyps.length());
				sbTyps.append(") ");
				sb.append(sbTyps);
			}
		}
		Object result = this.getObjectByHql(sb.toString(), null);
    	return Integer.parseInt(result.toString());
	}
	
	
}
