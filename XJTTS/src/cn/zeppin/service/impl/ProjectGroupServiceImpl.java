package cn.zeppin.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.dao.IAssignTeacherCheckDao;
import cn.zeppin.dao.IAssignTeacherTaskDao;
import cn.zeppin.dao.IDocumentDao;
import cn.zeppin.dao.IProjectApplyDao;
import cn.zeppin.dao.IProjectCollegeRangeDao;
import cn.zeppin.dao.IProjectDao;
import cn.zeppin.dao.IProjectGroupDao;
import cn.zeppin.dao.IProjectSubjectRangeDao;
import cn.zeppin.dao.ITeacherTrainingRecordsDao;
import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectCollegeRange;
import cn.zeppin.entity.ProjectGroup;
import cn.zeppin.entity.ProjectSubjectRange;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IProjectGroupService;
import cn.zeppin.utility.Utlity;

@SuppressWarnings("rawtypes")
public class ProjectGroupServiceImpl extends BaseServiceImpl<ProjectGroup, Integer> implements IProjectGroupService {

	private IProjectGroupDao projectGroupDao;
	private IDocumentDao documentDao;
	private IProjectSubjectRangeDao projectSubjectRangeDao;
	private IProjectCollegeRangeDao iprojectCollegeRangeDao;
	private IProjectDao projectDao;
	private IProjectApplyDao projectApplyDao;
	private IAssignTeacherTaskDao assignTeacherTaskDao;
	private IAssignTeacherCheckDao assignTeacherCheckDao;
	private ITeacherTrainingRecordsDao teacherTrainingRecordsDao;


	public IProjectGroupDao getProjectGroupDao() {
		return projectGroupDao;
	}
	

	public void setProjectGroupDao(IProjectGroupDao projectGroupDao) {
		this.projectGroupDao = projectGroupDao;
	}
	

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}
	
	
	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}
	
	
	public IProjectSubjectRangeDao getProjectSubjectRangeDao() {
		return projectSubjectRangeDao;
	}
	
	
	public void setProjectSubjectRangeDao(
			IProjectSubjectRangeDao projectSubjectRangeDao) {
		this.projectSubjectRangeDao = projectSubjectRangeDao;
	}
	

	public IProjectDao getProjectDao() {
		return projectDao;
	}
	

	public void setProjectDao(IProjectDao projectDao) {
		this.projectDao = projectDao;
	}


	public IProjectCollegeRangeDao getIprojectCollegeRangeDao() {
		return iprojectCollegeRangeDao;
	}
	

	public void setIprojectCollegeRangeDao(
			IProjectCollegeRangeDao iprojectCollegeRangeDao) {
		this.iprojectCollegeRangeDao = iprojectCollegeRangeDao;
	}
	

	public IProjectApplyDao getProjectApplyDao() {
		return projectApplyDao;
	}

	


	public void setProjectApplyDao(IProjectApplyDao projectApplyDao) {
		this.projectApplyDao = projectApplyDao;
	}

	


	public IAssignTeacherTaskDao getAssignTeacherTaskDao() {
		return assignTeacherTaskDao;
	}
	
	


	public void setAssignTeacherTaskDao(IAssignTeacherTaskDao assignTeacherTaskDao) {
		this.assignTeacherTaskDao = assignTeacherTaskDao;
	}
	
	


	public ITeacherTrainingRecordsDao getTeacherTrainingRecordsDao() {
		return teacherTrainingRecordsDao;
	}

	


	public void setTeacherTrainingRecordsDao(
			ITeacherTrainingRecordsDao teacherTrainingRecordsDao) {
		this.teacherTrainingRecordsDao = teacherTrainingRecordsDao;
	}
	
	


	public IAssignTeacherCheckDao getAssignTeacherCheckDao() {
		return assignTeacherCheckDao;
	}
	
	


	public void setAssignTeacherCheckDao(
			IAssignTeacherCheckDao assignTeacherCheckDao) {
		this.assignTeacherCheckDao = assignTeacherCheckDao;
	}
	
	


	@Override
	public ProjectGroup add(ProjectGroup t) {
		return projectGroupDao.add(t);
	}

	@Override
	public ProjectGroup update(ProjectGroup t) {
		return projectGroupDao.update(t);
	}

	@Override
	public void delete(ProjectGroup t) {
		projectGroupDao.delete(t);
	}

	@Override
	public ProjectGroup load(Integer id) {
		return projectGroupDao.load(id);
	}

	@Override
	public ProjectGroup get(Integer id) {
		return projectGroupDao.get(id);
	}

	@Override
	public List<ProjectGroup> loadAll() {
		return projectGroupDao.loadAll();
	}

	@Override
	public List<ProjectGroup> findAll() {
		return projectGroupDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		return projectGroupDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectGroup> getListForPage(String hql, int offset, int length) {
		return projectGroupDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		projectGroupDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectGroup> getListByHSQL(String hql) {
		return projectGroupDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		return projectGroupDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		projectGroupDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		return projectGroupDao.getListPage(sql, offset, length, objParas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.IProjectLevelService#getList()
	 */
	@Override
	public List<ProjectGroup> getList() {
		String hqlsString = "from ProjectGroup order by id";
		List<ProjectGroup> tlst = new ArrayList<ProjectGroup>();
		tlst = getListByHSQL(hqlsString);
		return tlst;
	}


	@Override
	public String addStageProject(Project project, List<ProjectApply> lstProjectApplys, Map<String, Object> params) {
		String status = "OK";
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserSession us = (UserSession) session.getAttribute("usersession");
		
		try {
			String method = "";
			if(params.containsKey("method") && params.get("method") != null){
				method = params.get("method").toString();
			} else {
				status = "操作方法有误";
				return status;
			}
			Project projectNew = null;
			if(!"".equals(method) && "add".equals(method)){
				projectNew = new Project();
				if(project != null){
					/*
					 * 1、复制项目信息入库：包括project、ProjectSubjectRange、ProjectCollegeRange、document、redHeadDocument
					 */
					
					String year = "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			        Date date = new Date();
			        year = sdf.format(date);
					projectNew.setYear(year);//当前自然年
					projectNew.setTraintype(project.getTraintype());
					projectNew.setTimeup(project.getTimeup());
					projectNew.setEnrollType(project.getEnrollType());
					projectNew.setFunds(project.getFunds());
					projectNew.setFundsType(project.getFundsType());
					projectNew.setNumber(project.getNumber());
					projectNew.setSubjectMax(project.getSubjectMax());
					
					projectNew.setRemark(project.getRemark());
					projectNew.setProjectType(project.getProjectType());
					
					/*
					 * 1.1 相关文件复制存储
					 */
					Document doc = new Document();
					if(project.getDocument() != null){
						Document document = project.getDocument();
						doc.setCreater(document.getCreater());
						doc.setCreatetime(document.getCreatetime());
						doc.setName(document.getName());
						doc.setResourcePath(document.getResourcePath());
						doc.setResourceType(document.getResourceType());
						doc.setResourceUrl(document.getResourceUrl());
						doc.setSize(document.getSize());
						doc.setTitle(document.getTitle());
						doc.setType(document.getType());
						document = this.documentDao.add(doc);
						projectNew.setDocument(document);
					}

					if(project.getRedHeadDocument() != null){
						Document redHeadDocument = project.getRedHeadDocument();
						doc.setCreater(redHeadDocument.getCreater());
						doc.setCreatetime(redHeadDocument.getCreatetime());
						doc.setName(redHeadDocument.getName());
						doc.setResourcePath(redHeadDocument.getResourcePath());
						doc.setResourceType(redHeadDocument.getResourceType());
						doc.setResourceUrl(redHeadDocument.getResourceUrl());
						doc.setSize(redHeadDocument.getSize());
						doc.setTitle(redHeadDocument.getTitle());
						doc.setType(redHeadDocument.getType());
						redHeadDocument = this.documentDao.add(doc);
						projectNew.setRedHeadDocument(redHeadDocument);
					}
					
					projectNew.setIsTest(project.getIsTest());
					projectNew.setIsAdvance(project.getIsAdvance());
					
					projectNew.setRestrictSubject(project.getRestrictSubject());
					projectNew.setRestrictCollege(project.getRestrictCollege());
					
					
					projectNew.setStatus((short) 1);
					projectNew.setCreator(us.getId());
					
					projectNew.setEndtime(project.getEndtime());//教师报名截止日期（先报后分项目）
					projectNew.setProjectCycle(project.getProjectCycle());
					/*
					 * 1.2 多阶段项目分组
					 */
					if(project.getProjectGroup() != null){
						projectNew.setProjectGroup(project.getProjectGroup());
					}else{
						ProjectGroup pg = new ProjectGroup();
						pg.setName(project.getName());
						pg = this.projectGroupDao.add(pg);
						project.setProjectGroup(pg);
						projectNew.setProjectGroup(pg);
					}
					
					/*
					 * 1.3多阶段项目分期
					 */
					if(project.getIndex() != null && project.getIndex() == 0){
						project.setIndex((short)1);
						projectNew.setIndex((short)2);
						projectNew.setName(project.getName()+"第2期");
						projectNew.setShortname(project.getShortname()+"第2期");
						project.setName(project.getName()+"第1期");
						project.setShortname(project.getShortname()+"第1期");
						
					}else{
						StringBuilder sbHql = new StringBuilder();
						sbHql.append("select count(*) from Project t where 1=1 ");
						sbHql.append(" and t.projectGroup="+project.getProjectGroup().getId());
						Object result = this.projectDao.getObjectByHql(sbHql.toString(), null);
						int totalCount = Integer.parseInt(result.toString());
						
						projectNew.setIndex((short)(totalCount + 1));
						projectNew.setName(project.getName().substring(0, project.getName().length()-3)+"第"+projectNew.getIndex()+"期");
						projectNew.setShortname(project.getShortname().substring(0, project.getShortname().length()-3)+"第"+projectNew.getIndex()+"期");
					}
					this.projectDao.update(project);
					projectNew = this.projectDao.add(projectNew);
					
					/*
					 * 1.4 复制学科和承训院校范围
					 */
					if(project.getRestrictSubject()){
						Set<ProjectSubjectRange> projectSubjectRange = project.getProjectSubjectRanges();
						for(ProjectSubjectRange psr:projectSubjectRange){
							ProjectSubjectRange psrNew = new ProjectSubjectRange();
							psrNew.setTrainingSubject(psr.getTrainingSubject());
							psrNew.setProject(projectNew);
							psrNew.setCreator(us.getId());
							this.projectSubjectRangeDao.add(psrNew);
						}
					}
					
					if(project.getRestrictCollege()){
						Set<ProjectCollegeRange> projectCollegeRange = project.getProjectCollegeRanges();
						for(ProjectCollegeRange pcr : projectCollegeRange){
							ProjectCollegeRange pcrNew = new ProjectCollegeRange();
							pcrNew.setTrainingCollege(pcr.getTrainingCollege());
							pcrNew.setProject(projectNew);
							pcrNew.setCreator(us.getId());
							this.iprojectCollegeRangeDao.add(pcrNew);
						}
						
					}
					
					
				}else{
					status = "项目信息有误";
					return status;
				}
			} else if(!"".equals(method) && "update".equals(method)){//补录继承
				if(params.containsKey("nextProject") && params.get("nextProject") != null){
					projectNew = (Project)params.get("nextProject");
				}else{
					status = "项目信息有误";
					return status;
				}
			} else {
				status = "操作方法有误";
				return status;
			}

			
			
			if(lstProjectApplys != null && lstProjectApplys.size() > 0){
				/*
				 * 2、申报结果复制 projectApply 
				 */
				for(ProjectApply pa : lstProjectApplys){
					TrainingSubject ts = pa.getTrainingSubject();
					TrainingCollege tc = pa.getTrainingCollege();
					ProjectApply paNew = new ProjectApply();
					
					paNew.setProject(projectNew);
					paNew.setTrainingCollege(tc);
					paNew.setTrainingSubject(ts);
					
//					if(params.containsKey("trainingStarttime") && params.get("trainingStarttime") != null){
//						paNew.setTrainingStarttime(Timestamp.valueOf(params.get("trainingStarttime") + " 00:00:00"));
//					}
//					if(params.containsKey("trainingEndtime") && params.get("trainingEndtime") != null){
//						paNew.setTrainingEndtime(Timestamp.valueOf(params.get("trainingEndtime") + " 00:00:00"));
//					}
//					if(params.containsKey("enrollEndTime") && params.get("enrollEndTime") != null){
//						paNew.setEnrollEndTime(Timestamp.valueOf(params.get("enrollEndTime") + " 00:00:00"));
//					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					paNew.setTrainingStarttime(Timestamp.valueOf(sdf.format(new Date())));
					paNew.setTrainingEndtime(Timestamp.valueOf(sdf.format(new Date())));
					if(project.getEnrollType() == 2){
						
						String times = sdf.format(new Date());
						Timestamp tst = Timestamp.valueOf(times);
						paNew.setEnrollEndTime(tst);
					}
					paNew.setTrainingClasshour(pa.getTrainingClasshour());
					paNew.setTrainingOnlineHour(pa.getTrainingOnlineHour());
					
					//20180608新增5类学时信息同步
					paNew.setStudyhour(pa.getStudyhour());
					paNew.setApproveNumber(pa.getApproveNumber());

					paNew.setContacts(pa.getContacts());
					paNew.setPhone(pa.getPhone());
					
					paNew.setApprover(pa.getApprover());//审核人
					
					paNew.setCreator(us.getId());
					paNew.setStatus((short) 2);
					paNew.setType((short) 4);
					paNew.setStudyhour(pa.getStudyhour());
					paNew.setCredit(pa.getCredit());
					paNew.setProjectCycle(projectNew.getProjectCycle());

					this.projectApplyDao.add(paNew);
					
					/*
					 * 3 名额分配任务复制入库
					 */
//					if(projectNew.getEnrollType() == 1){
					StringBuilder sbSql = new StringBuilder();
					sbSql.append(" from AssignTeacherTask att where 1=1 ");
					sbSql.append(" and att.project="+project.getId());
					sbSql.append(" and att.trainingSubject="+ts.getId());
					sbSql.append(" and att.trainingCollege="+tc.getId());
					
					List<AssignTeacherTask> lstAssigns = (List<AssignTeacherTask>) this.assignTeacherTaskDao.getListByHSQL(sbSql.toString());
					if(lstAssigns != null && lstAssigns.size() > 0){
						for(AssignTeacherTask att: lstAssigns){
							AssignTeacherTask attNew = new AssignTeacherTask();
							attNew.setProject(projectNew);
							attNew.setTrainingCollege(tc);
							attNew.setTrainingSubject(ts);
							attNew.setOrganizationByGOrganization(att.getOrganizationByGOrganization());
							attNew.setOrganizationByPOrganization(att.getOrganizationByPOrganization());
							attNew.setLevel(att.getLevel());
							
							Calendar c = Calendar.getInstance();
							c.add(Calendar.DAY_OF_MONTH, 30);
							String v = Utlity.timeSpanToDateString(c.getTime()) + " 23:59:59";
							Timestamp ts1 = Timestamp.valueOf(v);
							attNew.setTimeup(ts1);
							attNew.setStatus((short)2);
							attNew.setTeacherNumber(att.getTeacherNumber());
							attNew.setReceiveFlag(att.getReceiveFlag());
							attNew.setCreator(us.getId());
							
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String time = df.format(new Date());
							Timestamp ts2 = Timestamp.valueOf(time);
							attNew.setCreattime(ts2);
							this.assignTeacherTaskDao.add(attNew);
						}
					}
//					}
					
					/*
					 * 4 对应学员信息复制入库
					 * 查询出符合条件学员：最终审核通过的教师
					 */
					int totalCount = this.teacherTrainingRecordsDao.getAduTeacherCount(project.getId(), Integer.parseInt(ts.getId().toString()), tc.getId(), -1, 0);
					List<TeacherTrainingRecords> lstRecords = this.teacherTrainingRecordsDao.getListForPage(project.getId(), ts.getId(), tc.getId(), 0, 0, totalCount, "r.id");
					
					if(lstRecords != null && lstRecords.size() > 0){
						for(TeacherTrainingRecords ttr : lstRecords){
							TeacherTrainingRecords ttrNew = new TeacherTrainingRecords();
							// 增加一条记录
							ttrNew.setProject(projectNew);
							ttrNew.setTrainingSubject(ts);
							ttrNew.setTrainingCollege(tc);

							ttrNew.setTeacher(ttr.getTeacher());
							ttrNew.setCreator(us.getId());

//							Organization taddOra = this.iOrganization.get(us.getOrganization());
							ttrNew.setOrganization(ttr.getOrganization());

							ttrNew.setCheckStatus(ttr.getCheckStatus());
							String vid = Utlity.getUuidPwd();
							ttrNew.setUuid(vid);
							ttrNew.setIsPrepare(false);

							// 其他状态
							ttrNew.setFinalStatus((short) 2);
							ttrNew.setTrainingStatus((short) 1);
							
							//重置替换状态
							ttrNew.setReplaceStatus((short)0);
							ttrNew.setReplaceTeacher(0);
							
							//教师冗余字段
							ttrNew.setTeacherOrganization(ttr.getTeacherOrganization());
							ttrNew.setJobTitle(ttr.getJobTitle());
							ttrNew.setPolitics(ttr.getPolitics());
							ttrNew.setEductionBackground(ttr.getEductionBackground());
							ttrNew.setSubject(ttr.getSubject());
							ttrNew.setGrade(ttr.getGrade());
							ttrNew.setLanguage(ttr.getLanguage());
							ttrNew.setProjectCycle(projectNew.getProjectCycle());
							
							ttrNew = this.teacherTrainingRecordsDao.add(ttrNew);
							
							AssignTeacherCheck atc = new AssignTeacherCheck();
							atc.setChecker(us.getId());
							atc.setTeacherTrainingRecords(ttrNew);
							atc.setType((short) 1);
							this.assignTeacherCheckDao.add(atc);
							
							AssignTeacherCheck atcPass = new AssignTeacherCheck();
							atcPass.setTeacherTrainingRecords(ttrNew);
							atcPass.setType((short) 2);
							atcPass.setChecker(us.getId());
							this.assignTeacherCheckDao.add(atcPass);
							
						}
						
					}
					
				}
				
			}else{
				status = "项目申报信息有误";
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			status = "操作异常";
			return status;
		}
		
		
		return status;
	}

}
