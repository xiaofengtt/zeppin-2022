package cn.zeppin.action.paper;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.apache.struts2.ServletActionContext;
import org.htmlparser.util.ParserException;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Answer;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.Psq;
import cn.zeppin.entity.Question;
import cn.zeppin.entity.Result;
import cn.zeppin.entity.Submit;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAnswerService;
import cn.zeppin.service.IProjectAdminService;
import cn.zeppin.service.IProjectExpertService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IPsqService;
import cn.zeppin.service.IQuestionService;
import cn.zeppin.service.IResultService;
import cn.zeppin.service.ISubmitService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 统计接口 是多列模式
 * 
 * @author Administrator
 * 
 */
public class ReportExpertAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectService iProjectService; // 项目信息
	private IProjectExpertService iProjectExpertService; 
	private IProjectAdminService iProjectAdminService; 
	private IAnswerService iAnswerService;
	private IQuestionService iQuestionService;
	private IPsqService iPsqService;
	private ISubmitService iSubmitService;
	private IResultService iResultService;
	private ITrainingSubjectService iTrainingSubjectService;
	private ITrainingCollegeService iTrainingCollegeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecords;

	private String avgCountScore;
	private String intAvgCountScore;

	private Psq psq;
	private Submit submit;
	private HashMap<String, String[]> paperHashMap = new HashMap<>();
	private List summaryList = new ArrayList<>();
	private List contrastList = new ArrayList<>();
	private List manyiList = new ArrayList<>();

	private List<Submit> dataList = new ArrayList<>();
	private List<ProjectExpert> expertList = new ArrayList<>();
	
	private String expertName;
	private String tproject;
	private String tsubject;
	private String tcollege;
	private String tquestion;
	private HashMap<String, String> tprojects = new HashMap<>();
	private HashMap<String, String> trainingSubject = new HashMap<>();
	private HashMap<String, String> trainingCollege = new HashMap<>();
	private HashMap<String, String> mapQuestion = new HashMap<>();

	public ReportExpertAction() {

		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String getReportExpertData() {

		String psqid = request.getParameter("paperid"); // 问卷id
		String projectId = request.getParameter("pid");
		String trainingSubjectId = request.getParameter("stid"); // 培训科目id
		String trainingCollegeId = request.getParameter("tcid"); // 承训单位id
		String type = request.getParameter("type");
		
		this.tsubject = trainingSubjectId;
		this.tcollege = trainingCollegeId;
		this.tproject = projectId;

		String ist = (String) request.getParameter("page");

		if (ist == null || ist.equals("") || ist.equals("NaN") || ist.equals("0")) {
			ist = "1";
		}

		HashMap<String, String> mapParams = new HashMap<>();
		mapParams.put("paperid", psqid);
		mapParams.put("pid", projectId);
		mapParams.put("stid", trainingSubjectId);
		mapParams.put("tcid", trainingCollegeId);

		int start = Integer.parseInt(ist);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		int length = DictionyMap.maxPageSize;

		dataList = this.getiSubmitService().getPsqSubmit(mapParams, offset, length);
		this.psq = this.getiPsqService().get(Integer.valueOf(psqid));
		for(int i=0;i<dataList.size();i++){
			int eid=dataList.get(i).getCreater();
			ProjectExpert pe=this.iProjectExpertService.get(eid);
			this.expertList.add(pe);
		}
		Iterator<Project> it = null;
		if(type.equals("4")){
			it = this.psq.getProjectsForProjectSummarizePsq().iterator();
		}else{
			it = this.psq.getProjectsForProjectJudgePsq().iterator();
		}
		while (it.hasNext()) {
			Project p = it.next();

			this.tprojects.put(p.getId().toString(), p.getName());

			List li = this.getiPsqService().getPsqSearchTraining(p.getId().toString());
			if (li != null && li.size() > 0) {
				for (int i = 0; i < li.size(); i++) {
					Object[] objects = (Object[]) li.get(i);
					if (!trainingSubject.containsKey(objects[0].toString())) {
						trainingSubject.put(objects[0].toString(), objects[1].toString());
					}

					if (!trainingCollege.containsKey(objects[2].toString())) {
						trainingCollege.put(objects[2].toString(), objects[3].toString());
					}
				}
			}
		}

		return "data";
	}

	/**
	 * 每题统计 每个问卷关联一个项目 可以根据学科以及
	 */
	public String getReportExpertPaper() {

		String psqid = request.getParameter("paperid"); // 问卷id
		String projectId = request.getParameter("pid");
		String trainingSubjectId = request.getParameter("stid"); // 培训科目id
		String trainingCollegeId = request.getParameter("tcid"); // 承训单位id
		String type = request.getParameter("type");

		this.tsubject = trainingSubjectId;
		this.tcollege = trainingCollegeId;
		this.tproject = projectId;

		HashMap<String, String> mapParams = new HashMap<>();
		mapParams.put("paperid", psqid);
		mapParams.put("pid", projectId);
		mapParams.put("stid", trainingSubjectId);
		mapParams.put("tcid", trainingCollegeId);

		paperHashMap = this.getiPsqService().getPsqExpertPaper(mapParams);
		this.psq = this.getiPsqService().get(Integer.valueOf(psqid));

		Iterator<Project> it = null;
		if(type.equals("4")){
			it = this.psq.getProjectsForProjectSummarizePsq().iterator();
		}else{
			it = this.psq.getProjectsForProjectJudgePsq().iterator();
		}
		while (it.hasNext()) {
			Project p = it.next();

			this.tprojects.put(p.getId().toString(), p.getName());

			List li = this.getiPsqService().getPsqSearchTraining(p.getId().toString());
			if (li != null && li.size() > 0) {
				for (int i = 0; i < li.size(); i++) {
					Object[] objects = (Object[]) li.get(i);
					if (!trainingSubject.containsKey(objects[0].toString())) {
						trainingSubject.put(objects[0].toString(), objects[1].toString());
					}

					if (!trainingCollege.containsKey(objects[2].toString())) {
						trainingCollege.put(objects[2].toString(), objects[3].toString());
					}
				}
			}
		}

		return "paper";
	}
	
	public String detail() {

		String ssubmit = request.getParameter("sub");
		int submitid = Integer.parseInt(ssubmit);
		String pid = request.getParameter("pid");
		int p_pid = Integer.valueOf(pid);

		this.psq = this.getiPsqService().get(p_pid);
		this.submit = this.getiSubmitService().get(submitid);
		this.expertName=this.iProjectExpertService.get(submit.getValuator()).getName();
		return "detail";
	}

	public void getReportDataPageJson() {

		String psqid = request.getParameter("paperid"); // 问卷id
		String projectId = request.getParameter("pid");
		String trainingSubjectId = request.getParameter("stid"); // 培训科目id
		String trainingCollegeId = request.getParameter("tcid"); // 承训单位id

		String ist = (String) request.getParameter("page");

		if (ist == null || ist.equals("") || ist.equals("NaN") || ist.equals("0")) {
			ist = "1";
		}

		HashMap<String, String> mapParams = new HashMap<>();
		mapParams.put("paperid", psqid);
		mapParams.put("pid", projectId);
		mapParams.put("stid", trainingSubjectId);
		mapParams.put("tcid", trainingCollegeId);

		int start = Integer.parseInt(ist);

		int count = this.getiSubmitService().getReportPsqSubmitCount(mapParams);
		int totalPage = (int) Math.ceil((float) count / DictionyMap.maxPageSize);
		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + totalPage);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	/**
	 * 汇总统计
	 * 
	 * @return
	 */
	public String getReportExpertSummary() {

		String psqid = request.getParameter("paperid"); // 问卷id
		String projectId = request.getParameter("pid");
		String trainingSubjectId = request.getParameter("stid"); // 培训科目id
		String trainingCollegeId = request.getParameter("tcid"); // 承训单位id
		String type = request.getParameter("type");
		this.tsubject = trainingSubjectId;
		this.tcollege = trainingCollegeId;
		this.tproject = projectId;

		HashMap<String, String> mapParams = new HashMap<>();
		mapParams.put("paperid", psqid);
		mapParams.put("pid", projectId);
		mapParams.put("stid", trainingSubjectId);
		mapParams.put("tcid", trainingCollegeId);

		List li = this.getiPsqService().getPsqSummary(mapParams);

		if (li != null && li.size() > 0) {

			DecimalFormat df = new DecimalFormat();
			String style = "0.0";
			df.applyPattern(style);
			float totalScore = 0;

			for (int i = 0; i < li.size(); i++) {
				Object[] objects = (Object[]) li.get(i);
				float f = Float.parseFloat(objects[3].toString());
				int number=this.iPsqService.getSubmitCount(Integer.valueOf(objects[7].toString()),Integer.valueOf(objects[4].toString()),Integer.valueOf(objects[5].toString()),Integer.valueOf(objects[6].toString()));
				f=f/number;
				totalScore += f;
				HashMap<String, String> searchMap = new HashMap<>();
				searchMap.put("pid", objects[4].toString());
				searchMap.put("stid", objects[5].toString());
				searchMap.put("tcid", objects[6].toString());
				searchMap.put("paperid", objects[7].toString());
				List<Object[]> lio = this.iPsqService.searchPsqByParams(searchMap);
				String info = "";
				for(Object[] obj : lio){
					info = info + obj[0] + ":" +obj[1]+"分";
					info += " ";
				}
				Object[] data = { objects[0], objects[1], objects[2], df.format(f) , info};

				this.summaryList.add(data);
			}

			float tmpf = totalScore / li.size();
			double avgScore = tmpf;
			this.avgCountScore = df.format(avgScore);

			style = "0";
			df.applyPattern(style);
			this.intAvgCountScore = df.format(avgScore * 10);
		}

		this.psq = this.getiPsqService().get(Integer.valueOf(psqid));

		Iterator<Project> it = null;
		if(type.equals("4")){
			it = this.psq.getProjectsForProjectSummarizePsq().iterator();
		}else{
			it = this.psq.getProjectsForProjectJudgePsq().iterator();
		}
		while (it.hasNext()) {
			Project p = it.next();

			this.tprojects.put(p.getId().toString(), p.getName());

			li = this.getiPsqService().getPsqSearchTraining(p.getId().toString());
			if (li != null && li.size() > 0) {
				for (int i = 0; i < li.size(); i++) {
					Object[] objects = (Object[]) li.get(i);
					if (!trainingSubject.containsKey(objects[0].toString())) {
						trainingSubject.put(objects[0].toString(), objects[1].toString());
					}

					if (!trainingCollege.containsKey(objects[2].toString())) {
						trainingCollege.put(objects[2].toString(), objects[3].toString());
					}
				}
			}
		}

		return "summary";
	}

	/**
	 * 完成率分析
	 * 
	 * @return
	 */
	public String getReportExpertManyilv() {

		UserSession us = (UserSession) session.getAttribute("usersession");

		String psqid = request.getParameter("paperid"); // 问卷id
		String projectId = request.getParameter("pid");
		String trainingSubjectId = request.getParameter("stid"); // 培训科目id
		String trainingCollegeId = request.getParameter("tcid"); // 承训单位id
		String type = request.getParameter("type");
		this.tsubject = trainingSubjectId;
		this.tcollege = trainingCollegeId;
		this.tproject = projectId;
		String tmpProjects = "";

		this.psq = this.getiPsqService().get(Integer.valueOf(psqid));

		Iterator<Project> it = null;
		if(type.equals("4")){
			it = this.psq.getProjectsForProjectSummarizePsq().iterator();
		}else{
			it = this.psq.getProjectsForProjectJudgePsq().iterator();
		}
		while (it.hasNext()) {
			Project p = it.next();
			tmpProjects += p.getId() + ",";
			this.tprojects.put(p.getId().toString(), p.getName());

			List li = this.getiPsqService().getPsqSearchTraining(p.getId().toString());
			if (li != null && li.size() > 0) {
				for (int i = 0; i < li.size(); i++) {
					Object[] objects = (Object[]) li.get(i);
					if (!trainingSubject.containsKey(objects[0].toString())) {
						trainingSubject.put(objects[0].toString(), objects[1].toString());
					}

					if (!trainingCollege.containsKey(objects[2].toString())) {
						trainingCollege.put(objects[2].toString(), objects[3].toString());
					}
				}
			}
		}
		if (tmpProjects.length() > 0) {
			tmpProjects = tmpProjects.substring(0, tmpProjects.length() - 1);
		}

		HashMap<String, String> mapParams = new HashMap<>();
		if (projectId != null && !projectId.equals("0")) {
			mapParams.put("pid", projectId);
		} else {
			mapParams.put("pid", tmpProjects);
		}
		mapParams.put("stid", trainingSubjectId);
		mapParams.put("tcid", trainingCollegeId);
		mapParams.put("psqid", psqid);
		mapParams.put("type", type);
		String tableName = "am" + us.getId().toString();

		List li = this.getiPsqService().getExpertPsqManyiLv(mapParams, tableName);
		if (li != null && li.size() > 0) {

			float totalScore = 0;
			float realScore = 0;

			DecimalFormat df = new DecimalFormat();
			String style = "0.0%";
			df.applyPattern(style);

			for (int i = 0; i < li.size(); i++) {
				Object[] objects = (Object[]) li.get(i);
				float f = Float.parseFloat(objects[8].toString());
				float t = Float.parseFloat(objects[6].toString());
				float r = Float.parseFloat(objects[7].toString());
				totalScore += t;
				realScore += r;
				Object[] data = { objects[0], objects[1], objects[2], objects[3], objects[4], objects[5], objects[6], objects[7], df.format(f) };

				this.manyiList.add(data);
			}

			float tmpf = realScore / totalScore;
			double avgScore = tmpf;
			this.avgCountScore = df.format(avgScore);
		}

		return "manyilv";
	}

	@SuppressWarnings("rawtypes")
	public void outputExpertPaperResult() throws IOException, ParserException {
		String paperid = request.getParameter("paperid");
		String projectId = request.getParameter("pid");
		String trainingSubjectId = request.getParameter("stid"); // 培训科目id
		String trainingCollegeId = request.getParameter("tcid"); // 承训单位id

		HashMap<String, String> mapParams = new HashMap<>();
		mapParams.put("paperid", paperid);
		mapParams.put("pid", projectId);
		mapParams.put("stid", trainingSubjectId);
		mapParams.put("tcid", trainingCollegeId);

		DecimalFormat fnum = new DecimalFormat("0.00%");
		List questions = this.getiQuestionService().getPsqQuestion(Integer.valueOf(paperid));
		List<Submit> pts = this.iSubmitService.getSubmitByParams(mapParams);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "统计结果");
		HSSFRow row = s.createRow(0);
		int r = 0;
		for (int i = 0; i < questions.size(); i++) {
			Question question = (Question) questions.get(i);
			row = s.createRow(r);
			String title = "第" + question.getInx() + "题:" + cn.zeppin.utility.cutHtml.parseString2Html(question.getName());
			s.addMergedRegion(new Region((short) r, (short) 4, (short) r, (short) 13));
			row.createCell(4).setCellValue(title);
			r++;
			row = s.createRow(r);
			Set<Answer> ans = question.getAnswers();
			List<String> titlend = new ArrayList<String>();
			titlend.add("");
			titlend.add("");
			titlend.add("");
			titlend.add("");
			int t = 0;
			for (Iterator<Answer> answers = ans.iterator(); answers.hasNext();) {
				Answer answer = answers.next();
				titlend.add(answer.getName());
				titlend.add(answer.getName());
				s.addMergedRegion(new Region((short) r, (short) (4 + t * 2), (short) r, (short) (5 + t * 2)));
				t++;
			}
			for (int j = 0; j < titlend.size(); j++) {
				row.createCell(j).setCellValue(titlend.get(j));
			}
			r++;
			row = s.createRow(r);
			List<String> titlerd = new ArrayList<String>();
			titlerd.add("项目名称");
			titlerd.add("承训单位");
			titlerd.add("学科");
			titlerd.add("完成率");
			for (int k = 0; k < ans.size(); k++) {
				titlerd.add("人数");
				titlerd.add("百分比");
			}
			for (int j = 0; j < titlerd.size(); j++) {
				row.createCell(j).setCellValue(titlerd.get(j));
			}
			r++;
			row = s.createRow(r);
			for (int p = 0; p < pts.size(); p++) {
				List<Integer> countList = new ArrayList<Integer>();
				Submit ptsubmit = pts.get(p);
				Project project = ptsubmit.getProject();
				TrainingCollege tc = ptsubmit.getTrainingCollege();
				TrainingSubject ts = ptsubmit.getTrainingSubject();

				int pid = project.getId();
				int tcid = tc.getId();
				int tsid = ts.getId();

				int totalNum = this.iProjectExpertService.getExpertByPapperCount(pid, tsid, tcid);

				HashMap<String, String> map = new HashMap<>();
				map.put("paperid", paperid);
				map.put("pid", pid + "");
				map.put("tcid", tcid + "");
				map.put("stid", tsid + "");

				int sumCount = this.iSubmitService.getExpertPsqSubmitCount(map);
				for (Iterator<Answer> answers = ans.iterator(); answers.hasNext();) {
					Answer answer = answers.next();
					int aid = answer.getId();
					int count = this.iResultService.getResultCount(Integer.valueOf(paperid), pid, tcid, tsid, question.getId(), aid);
					countList.add(count);
				}

				List<String> data = new ArrayList<String>();
				data.add(project.getName());
				data.add(tc.getName());
				data.add(ts.getName());
				if (totalNum > 0) {
					data.add(fnum.format((float) sumCount / (float) totalNum));
				} else {
					data.add("0.00%");
				}
				for (int d : countList) {
					data.add(d + "");
					if (sumCount > 0) {
						data.add(fnum.format((float) d / (float) sumCount));
					} else {
						data.add("0.00%");
					}
				}
				for (int z = 0; z < data.size(); z++) {
					row.createCell(z).setCellValue(data.get(z));
				}
				r++;
				row = s.createRow(r);
			}
		}
		List ncQuestions = this.getiQuestionService().getPsqNotCountQuestion(Integer.valueOf(paperid));
		if (ncQuestions!=null && ncQuestions.size() >0){
			for(Object q : ncQuestions){
				Question ncQuestion = (Question) q;
				
				r++;
				row = s.createRow(r);
				String title = "第" + ncQuestion.getInx() + "题:" + cn.zeppin.utility.cutHtml.parseString2Html(ncQuestion.getName());
				s.addMergedRegion(new Region((short) r, (short) 4, (short) r, (short) 13));
				row.createCell(4).setCellValue(title);
				
				
				r++;
				row = s.createRow(r);
				List<String> ncTitle = new ArrayList<String>();
				ncTitle.add("项目名称");
				ncTitle.add("承训单位");
				ncTitle.add("学科");
				ncTitle.add("评审专家");
				ncTitle.add("评审内容");
				for (int j = 0; j < ncTitle.size(); j++) {
					row.createCell(j).setCellValue(ncTitle.get(j));
				}
				
				HashMap<String, String> searchMap = new HashMap<>();
				searchMap.put("paperid", paperid);
				searchMap.put("pid", projectId);
				searchMap.put("stid", trainingSubjectId);
				searchMap.put("tcid", trainingCollegeId);
				searchMap.put("questionid", ncQuestion.getId() + "");
				List<Object[]> ncSubmits = this.iSubmitService.getExpertSubmitByParams(searchMap);
				for(Object[] nc : ncSubmits){
					r++;
					row = s.createRow(r);
					
					Submit submit = (Submit)nc[0];
					Result result = (Result)nc[1];
					List<String> data = new ArrayList<String>();
					data.add(submit.getProject().getName());
					data.add(submit.getTrainingCollege().getName());
					data.add(submit.getTrainingSubject().getName());
					ProjectExpert pe = this.iProjectExpertService.get(submit.getCreater());
					if(pe!=null){
						data.add(pe.getName());
					}else{
						ProjectAdmin pa = this.iProjectAdminService.get(submit.getCreater());
						if(pa!=null){
							data.add(pa.getName());
						}else{
							data.add("专家");
						}
					}
					data.add(result.getContent());
					for (int j = 0; j < data.size(); j++) {
						row.createCell(j).setCellValue(data.get(j));
					}
				}
				r++;
				row = s.createRow(r);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=PaperResult.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}
	
	
	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
	}

	public IProjectExpertService getiProjectExpertService() {
		return iProjectExpertService;
	}

	public void setiProjectExpertService(IProjectExpertService iProjectExpertService) {
		this.iProjectExpertService = iProjectExpertService;
	}
	
	public IProjectAdminService getiProjectAdminService() {
		return iProjectAdminService;
	}

	public void setiProjectAdminService(IProjectAdminService iProjectAdminService) {
		this.iProjectAdminService = iProjectAdminService;
	}
	
	public IAnswerService getiAnswerService() {
		return iAnswerService;
	}

	public void setiAnswerService(IAnswerService iAnswerService) {
		this.iAnswerService = iAnswerService;
	}

	public IQuestionService getiQuestionService() {
		return iQuestionService;
	}

	public void setiQuestionService(IQuestionService iQuestionService) {
		this.iQuestionService = iQuestionService;
	}

	public IPsqService getiPsqService() {
		return iPsqService;
	}

	public void setiPsqService(IPsqService iPsqService) {
		this.iPsqService = iPsqService;
	}

	public ISubmitService getiSubmitService() {
		return iSubmitService;
	}

	public void setiSubmitService(ISubmitService iSubmitService) {
		this.iSubmitService = iSubmitService;
	}

	public IResultService getiResultService() {
		return iResultService;
	}

	public void setiResultService(IResultService iResultService) {
		this.iResultService = iResultService;
	}

	public ITrainingSubjectService getiTrainingSubjectService() {
		return iTrainingSubjectService;
	}

	public void setiTrainingSubjectService(ITrainingSubjectService iTrainingSubjectService) {
		this.iTrainingSubjectService = iTrainingSubjectService;
	}

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

	public HashMap<String, String[]> getPaperHashMap() {
		return paperHashMap;
	}

	public void setPaperHashMap(HashMap<String, String[]> paperHashMap) {
		this.paperHashMap = paperHashMap;
	}

	public Psq getPsq() {
		return psq;
	}

	public void setPsq(Psq psq) {
		this.psq = psq;
	}

	public List getSummaryList() {
		return summaryList;
	}

	public void setSummaryList(List summaryList) {
		this.summaryList = summaryList;
	}

	public String getAvgCountScore() {
		return avgCountScore;
	}

	public void setAvgCountScore(String avgCountScore) {
		this.avgCountScore = avgCountScore;
	}

	public String getIntAvgCountScore() {
		return intAvgCountScore;
	}

	public void setIntAvgCountScore(String intAvgCountScore) {
		this.intAvgCountScore = intAvgCountScore;
	}

	public List getContrastList() {
		return contrastList;
	}

	public void setContrastList(List contrastList) {
		this.contrastList = contrastList;
	}

	public List getManyiList() {
		return manyiList;
	}

	public void setManyiList(List manyiList) {
		this.manyiList = manyiList;
	}

	public List<Submit> getDataList() {
		return dataList;
	}

	public void setDataList(List<Submit> dataList) {
		this.dataList = dataList;
	}

	public List<ProjectExpert> getExpertList() {
		return expertList;
	}

	public void setExpertList(List<ProjectExpert> expertList) {
		this.expertList = expertList;
	}
	
	public String getTsubject() {
		return tsubject;
	}

	public void setTsubject(String tsubject) {
		this.tsubject = tsubject;
	}

	public String getTcollege() {
		return tcollege;
	}

	public void setTcollege(String tcollege) {
		this.tcollege = tcollege;
	}

	public String getTquestion() {
		return tquestion;
	}

	public void setTquestion(String tquestion) {
		this.tquestion = tquestion;
	}

	public HashMap<String, String> getTrainingSubject() {
		return trainingSubject;
	}

	public void setTrainingSubject(HashMap<String, String> trainingSubject) {
		this.trainingSubject = trainingSubject;
	}

	public HashMap<String, String> getTrainingCollege() {
		return trainingCollege;
	}

	public void setTrainingCollege(HashMap<String, String> trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	public HashMap<String, String> getMapQuestion() {
		return mapQuestion;
	}

	public void setMapQuestion(HashMap<String, String> mapQuestion) {
		this.mapQuestion = mapQuestion;
	}

	public Submit getSubmit() {
		return submit;
	}

	public void setSubmit(Submit submit) {
		this.submit = submit;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	
	public String getTproject() {
		return tproject;
	}

	public void setTproject(String tproject) {
		this.tproject = tproject;
	}

	public HashMap<String, String> getTprojects() {
		return tprojects;
	}

	public void setTprojects(HashMap<String, String> tprojects) {
		this.tprojects = tprojects;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecords() {
		return iTeacherTrainingRecords;
	}

	public void setiTeacherTrainingRecords(ITeacherTrainingRecordsService iTeacherTrainingRecords) {
		this.iTeacherTrainingRecords = iTeacherTrainingRecords;
	}

}
