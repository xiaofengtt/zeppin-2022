package com.gpjh.action;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gpjh.model.Paper;
import com.gpjh.model.Project;
import com.gpjh.model.Question;
import com.gpjh.model.Result;
import com.gpjh.model.Subject;
import com.gpjh.model.Submit;
import com.gpjh.model.TrainingUnit;
import com.gpjh.service.PaperService;
import com.gpjh.service.ProjectService;
import com.gpjh.service.QuestionService;
import com.gpjh.service.ResultService;
import com.gpjh.service.SubjectService;
import com.gpjh.service.SubmitService;
import com.gpjh.service.TrainingUnitService;
import com.gpjh.utils.PageInfo;
import com.opensymphony.xwork2.ActionSupport;

@Controller("ReportAction")
@Scope("prototype")
public class ReportAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(ReportAction.class);

	@Autowired
	private SubmitService submitService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TrainingUnitService trainingUnitService;

	@Autowired
	private ResultService resultService;

	@Autowired
	private PaperService paperService;

	@Autowired
	private QuestionService questionService;

	private List<Submit> submits;
	private List<Result> results;

	private Submit submit;

	private PageInfo pageInfo;

	private Paper paper;

	private List<Subject> subjects;
	private List<Project> projects;
	private List<TrainingUnit> units;
	private List<Question> questions;

	private String subject;
	private String project;
	private String unit;
	private String qid;

	private String avgCountScore;
	private String intAvgCountScore;

	private int countVotesum;
	private String wclPercent;

	private Hashtable<String, String[]> infoDic = new Hashtable<String, String[]>();
	private Hashtable<Object, Object> answerCountMap = new Hashtable<Object, Object>();
	private List avgCountMap = new ArrayList();
	private List duibiCountMap = new ArrayList();
	private List wclCountMap = new ArrayList();

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	// private ServletContext application;

	public ReportAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		// application = session.getServletContext();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 
	 * @return
	 */
	public String summary() {
		String pid = request.getParameter("pid");
		String page = request.getParameter("page");

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		int pageId = 1;
		if (page != null)
			pageId = Integer.parseInt(page);
		int pageSize = 20;
		System.out.println("pageId ......... " + pageId);

		// 通过loginkey解析出项目、承办单位、科目信息
		String sql = "SELECT p.name AS project, s.name AS SUBJECT, t.name AS unit, l.id FROM loginkey l, project p, subject s, trainingunit t WHERE l.project = p.id AND l.subject = s.id AND l.trainingunit = t.id AND l.id IN(SELECT DISTINCT loginkey FROM submit WHERE psq = "
				+ pid + ")";
		List subObjs = submitService.executeSQL(sql);

		for (int i = 0; i < subObjs.size(); i++) {
			Object[] objects = (Object[]) subObjs.get(i);

			String loginKey = (String) objects[3];
			String[] info = { (String) objects[0], (String) objects[1], (String) objects[2] };

			this.infoDic.put(loginKey, info);
		}

		// 拼装sql语句
		sql = "from Submit where psq = " + pid;
		if (project != null && !project.equals("0") && !project.equals("")) {
			String fproid = project.format("%04d", Integer.parseInt(project));
			sql += " and SUBSTRING(loginkey, 3,4) = '" + fproid + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			String fsub = project.format("%03d", Integer.parseInt(subject));
			sql += " and SUBSTRING(loginkey, 10,3) = '" + fsub + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			String funi = project.format("%03d", Integer.parseInt(unit));
			sql += " and SUBSTRING(loginkey, 7,3) = '" + funi + "'";
		}

		sql += " order by creattime desc";
		// 数据信息
		this.submits = submitService.getListForPage(sql, pageId, pageSize);

		// 分页信息
		int totalRow = submitService.queryRowCount(sql);
		this.pageInfo = new PageInfo(totalRow, pageSize, pageId);

		this.paper = paperService.get(pid);
		// 字典信息
		this.subjects = subjectService.findAll();
		this.projects = projectService.findAll();
		this.units = trainingUnitService.findAll();

		return "summary";
	}

	/**
	 * 每个人的答卷结果
	 * 
	 * @return
	 */
	public String detail() {

		String submit = request.getParameter("submit");
		String pid = request.getParameter("pid");

		// 通过loginkey解析出项目、承办单位、科目信息
		String sql = "SELECT p.name AS project, s.name AS SUBJECT, t.name AS unit, l.id FROM loginkey l, project p, subject s, trainingunit t WHERE l.project = p.id AND l.subject = s.id AND l.trainingunit = t.id AND l.id IN(SELECT DISTINCT loginkey FROM submit WHERE psq = "
				+ pid + ")";
		List subObjs = submitService.executeSQL(sql);

		for (int i = 0; i < subObjs.size(); i++) {
			Object[] objects = (Object[]) subObjs.get(i);

			String loginKey = (String) objects[3];
			String[] info = { (String) objects[0], (String) objects[1], (String) objects[2] };

			this.infoDic.put(loginKey, info);
		}

		this.submit = submitService.get(submit);

		String hql = "from Result where submit = " + submit + " order by question.inx asc ";
		this.results = resultService.getListByHSQL(hql);

		return "detail";
	}

	/**
	 * 每张试卷的统计
	 * 
	 * @return
	 */
	public String paper() {

		String pid = request.getParameter("pid");

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		session.setAttribute("paperProject", project);
		session.setAttribute("paperSubject", subject);
		session.setAttribute("paperUnit", unit);

		Hashtable<Object, Integer> everyQuestionCount = new Hashtable<Object, Integer>();

		// 每个题的每个答案回答次数
		String sql = "SELECT r.question, r.answer, COUNT(*) AS SUM FROM result r, question q, psq p WHERE r.question = q.id AND q.psq = p.id AND p.id= "
				+ pid;

		String sqlQuestionCount = "SELECT r.question, COUNT(*) AS SUM FROM result r, question q, psq p WHERE r.question = q.id AND q.psq = p.id AND p.id= "
				+ pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			String fproid = project.format("%04d", Integer.parseInt(project));
			sql += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
			sqlQuestionCount += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			String fsub = project.format("%03d", Integer.parseInt(subject));
			sql += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
			sqlQuestionCount += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			String funi = project.format("%03d", Integer.parseInt(unit));
			sql += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
			sqlQuestionCount += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
		}

		sql += " GROUP BY question, answer";
		sqlQuestionCount += " GROUP BY question";

		List questionCounts = resultService.executeSQL(sqlQuestionCount);
		for (int i = 0; i < questionCounts.size(); i++) {
			Object[] objects = (Object[]) questionCounts.get(i);
			everyQuestionCount.put(objects[0], Integer.parseInt(objects[1].toString()));
		}

		List questionAnswerCounts = resultService.executeSQL(sql);
		for (int i = 0; i < questionAnswerCounts.size(); i++) {
			Object[] objects = (Object[]) questionAnswerCounts.get(i);

			// System.out.println(objects[0]);
			// System.out.println(objects[1] + " .... " + objects[2] +
			// " ........ " + everyQuestionCount.get(objects[0]));

			float f = (float) Integer.parseInt(objects[2].toString()) / everyQuestionCount.get(objects[0]);
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumIntegerDigits(3);
			nf.setMaximumFractionDigits(1);

			Object[] data = { objects[2], nf.format(f) };

			this.answerCountMap.put(objects[0] + "_" + objects[1], data);
		}

		// 字典信息
		this.subjects = subjectService.findAll();
		this.projects = projectService.findAll();
		this.units = trainingUnitService.findAll();

		this.paper = paperService.get(pid);

		return "paper";
	}

	/**
	 * 汇总统计
	 * 
	 * @return
	 */
	public String huizong() {

		String pid = request.getParameter("pid");

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		// 通过loginkey解析出项目、承办单位、科目信息
		String sql = "SELECT p.name AS project, s.name AS SUBJECT, t.name AS unit, l.id FROM loginkey l, project p, subject s, trainingunit t WHERE l.project = p.id AND l.subject = s.id AND l.trainingunit = t.id AND l.id IN(SELECT DISTINCT loginkey FROM submit WHERE psq = "
				+ pid + ")";
		List subObjs = submitService.executeSQL(sql);

		for (int i = 0; i < subObjs.size(); i++) {
			Object[] objects = (Object[]) subObjs.get(i);

			String loginKey = (String) objects[3];
			String[] info = { (String) objects[0], (String) objects[1], (String) objects[2] };

			this.infoDic.put(loginKey, info);
		}

		String hzsql = "SELECT loginkey, AVG(score) as score FROM result r, question q WHERE r.question = q.id and q.istongji =1 AND q.psq = " + pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			String fproid = project.format("%04d", Integer.parseInt(project));
			// sql += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
			hzsql += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			String fsub = project.format("%03d", Integer.parseInt(subject));
			// sql += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
			hzsql += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			String funi = project.format("%03d", Integer.parseInt(unit));
			// sql += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
			hzsql += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
		}

		hzsql += " GROUP BY loginkey order by score desc";

		List avgCounts = resultService.executeSQL(hzsql);
		float totalScore = 0;
		for (int i = 0; i < avgCounts.size(); i++) {
			Object[] objects = (Object[]) avgCounts.get(i);

			// System.out.println(objects[0] + " .... " + objects[1]);
			float f = Float.parseFloat(objects[1].toString());
			totalScore += f;
			DecimalFormat df = new DecimalFormat();
			String style = "0.0";
			df.applyPattern(style);

			Object[] data = { objects[0], df.format(f) };

			this.avgCountMap.add(data);
		}

		float tmpf = totalScore / avgCounts.size();
		double avgScore = tmpf - tmpf % 0.5;
		DecimalFormat df = new DecimalFormat();
		String style = "0.0";
		df.applyPattern(style);

		this.avgCountScore = df.format(avgScore);

		style = "0";
		df.applyPattern(style);
		this.intAvgCountScore = df.format(avgScore * 10);

		this.paper = paperService.get(pid);
		// 字典信息
		this.subjects = subjectService.findAll();
		this.projects = projectService.findAll();
		this.units = trainingUnitService.findAll();
		return "huizong";
	}

	/**
	 * 对比统计
	 * 
	 * @return
	 */
	public String duibi() {

		String pid = request.getParameter("pid");

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.qid = request.getParameter("question");

		// 通过loginkey解析出项目、承办单位、科目信息
		String sql = "SELECT p.name AS project, s.name AS SUBJECT, t.name AS unit, l.id FROM loginkey l, project p, subject s, trainingunit t WHERE l.project = p.id AND l.subject = s.id AND l.trainingunit = t.id AND l.id IN(SELECT DISTINCT loginkey FROM submit WHERE psq = "
				+ pid + ")";
		List subObjs = submitService.executeSQL(sql);

		for (int i = 0; i < subObjs.size(); i++) {
			Object[] objects = (Object[]) subObjs.get(i);

			String loginKey = (String) objects[3];
			String[] info = { (String) objects[0], (String) objects[1], (String) objects[2] };

			this.infoDic.put(loginKey, info);
		}

		// String dbsql =
		// "SELECT r.loginkey, q.inx, r.question, AVG(r.score) FROM result r, answer a, question q WHERE a.question = q.id AND r.answer = a.id  AND a.psq = "
		// + pid;
		String dbsql = "SELECT r.loginkey, q.inx, q.name, r.question, AVG(r.score) AS scoreavg FROM result r, answer a, question q WHERE a.question = q.id AND r.answer = a.id  and q.istongji = 1 AND a.psq = "
				+ pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			String fproid = project.format("%04d", Integer.parseInt(project));
			// sql += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
			dbsql += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			String fsub = project.format("%03d", Integer.parseInt(subject));
			// sql += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
			dbsql += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			String funi = project.format("%03d", Integer.parseInt(unit));
			// sql += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
			dbsql += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
		}

		if (this.qid != null && !this.qid.equals("0") && !this.qid.equals("")) {
			dbsql += " and r.question = " + this.qid;
		}

		dbsql += "   GROUP BY  r.`LOGINKEY` , r.`QUESTION`, q.inx, q.name ORDER BY q.inx, scoreavg desc";

		List questionCounts = resultService.executeSQL(dbsql);
		for (int i = 0; i < questionCounts.size(); i++) {
			Object[] objects = (Object[]) questionCounts.get(i);

			float f = Float.parseFloat(objects[4].toString());
			DecimalFormat df = new DecimalFormat();
			String style = "0.0";
			df.applyPattern(style);

			// System.out.println(objects[0] + " .... " + objects[1] +
			// " ........ " + objects[3] + " ........ " +df.format(f));

			Object[] data = { objects[0], objects[1], objects[2], objects[3], df.format(f) };
			this.duibiCountMap.add(data);
		}

		this.paper = paperService.get(pid);

		// 字典信息
		this.subjects = subjectService.findAll();
		this.projects = projectService.findAll();
		this.units = trainingUnitService.findAll();
		this.questions = questionService.getListByHSQL("from Question where paper =" + pid + " and type <> 0");

		return "duibi";
	}

	public void addSubject() {
		Subject sb1 = new Subject();
		sb1.setName("学科教师教学技能提升项目");
		subjectService.add(sb1);

		Subject sb2 = new Subject();
		sb2.setName("小学班主任教师能力提升项目");
		subjectService.add(sb2);

		Subject sb3 = new Subject();
		sb3.setName("初中班主任教师发送能力提升项目");
		subjectService.add(sb3);
	}

	public void ProjectExcel() {
		String pro = session.getAttribute("paperProject") == null ? "" : session.getAttribute("paperProject").toString();
		String sub = session.getAttribute("paperSubject") == null ? "" : session.getAttribute("paperSubject").toString();
		String unit1 = session.getAttribute("paperUnit") == null ? "" : session.getAttribute("paperUnit").toString();
		String pid = request.getParameter("pid");

		String quesql = "SELECT qu.id AS qus ,qu.name,an.id AS ans,an.inx as ainx,qu.inx as qinx FROM psq pq,question qu,answer an " + " "
				+ " WHERE pq.id=qu.psq AND qu.id = an.question AND qu.istongji=1 AND  pq.id=" + pid + " order by qinx";

		List queObjs = submitService.executeSQL(quesql);

		Hashtable<String, Object> quesdict = new Hashtable<String, Object>();

		for (int i = 0; i < queObjs.size(); i++) {
			Object[] qs = (Object[]) queObjs.get(i);
			String qid = qs[0].toString();
			String qname = qs[1].toString();
			String aid = qs[2].toString();
			String ainx = qs[3].toString();
			String qinx = qs[4].toString();

			if (quesdict.containsKey(qid)) {

				Object[] t = (Object[]) quesdict.get(qid);
				Hashtable<String, String> ht = (Hashtable<String, String>) t[1];
				if (!ht.containsKey(aid)) {
					ht.put(aid, ainx);
				}

			} else {
				Object[] t = new Object[2];
				t[0] = "第" + qinx + "题:" + qname;
				Hashtable<String, String> ht = new Hashtable<String, String>();
				ht.put(aid, ainx);
				t[1] = ht;
				quesdict.put(qid, t);
			}
		}

		String pcsql = "SELECT lk.id, t.count/lk.realsum AS percent "
				+ "FROM loginkey lk, (SELECT loginkey, COUNT(*) AS COUNT FROM submit r WHERE  r.psq=" + pid;
		String sql = "SELECT rs.loginkey,rs.question,rs.answer,t.id,t.name as unit,sb.id,sb.name as subject "
				+ "FROM submit s,result rs,loginkey l,trainingunit t,subject sb "
				+ "WHERE s.id=rs.submit AND rs.loginkey=l.id AND l.trainingunit=t.id AND l.subject=sb.id AND s.psq=" + pid;

		if (pro != null && !pro.equals("0") && !pro.equals("")) {
			String fproid = project.format("%04d", Integer.parseInt(pro));
			pcsql += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
			sql += " and SUBSTRING(rs.loginkey, 3,4) = '" + fproid + "'";
		}

		if (sub != null && !sub.equals("0") && !sub.equals("")) {
			String fsub = project.format("%03d", Integer.parseInt(sub));
			pcsql += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
			sql += " and SUBSTRING(rs.loginkey, 10,3) = '" + fsub + "'";
		}

		if (unit1 != null && !unit1.equals("0") && !unit1.equals("")) {
			String funi = project.format("%03d", Integer.parseInt(unit1));
			pcsql += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
			sql += " and SUBSTRING(rs.loginkey, 7,3) = '" + funi + "'";
		}

		pcsql += " GROUP BY loginkey) t WHERE lk.id = t.loginkey ORDER BY percent DESC";
		sql += " ORDER BY rs.question";

		List pcObjs = submitService.executeSQL(pcsql);
		Hashtable<String, Float> pcdict = new Hashtable<String, Float>();
		for (int i = 0; i < pcObjs.size(); i++) {
			Object[] t = (Object[]) pcObjs.get(i);
			pcdict.put(t[0].toString(), Float.parseFloat(t[1].toString()));
		}

		Hashtable<String, Object> rsdict = new Hashtable<String, Object>();

		List rsObjs = submitService.executeSQL(sql);

		for (int i = 0; i < rsObjs.size(); i++) {
			Object[] t = (Object[]) rsObjs.get(i);
			String loginKey = t[0].toString();
			String qid = t[1].toString();
			String aid = t[2].toString();
			String unit = t[4].toString();
			String subject = t[6].toString();

			if (rsdict.containsKey(qid)) {
				if (quesdict.containsKey(qid)) {

					Hashtable<String, Object> arsdict = (Hashtable<String, Object>) rsdict.get(qid);
					Object[] t1 = (Object[]) quesdict.get(qid);
					if (arsdict.containsKey(loginKey)) {
						List<Object> m = (List<Object>) arsdict.get(loginKey);

						Hashtable<String, String> ht = (Hashtable<String, String>) t1[1];
						if (ht.containsKey(aid)) {
							int inx = Integer.parseInt(ht.get(aid));
							int index = 4 + inx - 1;

							if (index >= m.size()) {
								m.add(1);
							} else {
								int c = Integer.parseInt(m.get(index).toString());
								m.set(index, c + 1);
							}
						}
					} else {

						List<Object> m = new ArrayList<Object>();
						m.add(t1[0]); // 题目
						m.add(subject);
						m.add(unit);
						if (pcdict.containsKey(loginKey)) {
							m.add(pcdict.get(loginKey));
							Hashtable<String, String> ht = (Hashtable<String, String>) t1[1];
							if (ht.containsKey(aid)) {
								// int inx = Integer.parseInt(ht.get(aid));
								m.add(1);
								m.add(0);
								m.add(0);
								m.add(0);
								m.add(0);
							}
							arsdict.put(loginKey, m);
						} else {
							// System.out.println("2" + loginKey + " " + m[0]);
						}

					}
				}

			} else {

				if (quesdict.containsKey(qid)) {

					Hashtable<String, Object> arsdict = new Hashtable<String, Object>();
					List<Object> m = new ArrayList<Object>();
					Object[] t1 = (Object[]) quesdict.get(qid);
					m.add(t1[0]); // 题目
					m.add(subject);
					m.add(unit);
					if (pcdict.containsKey(loginKey)) {

						m.add(pcdict.get(loginKey));
						Hashtable<String, String> ht = (Hashtable<String, String>) t1[1];
						if (ht.containsKey(aid)) {
							// int inx = Integer.parseInt(ht.get(aid));
							m.add(1);
							m.add(0);
							m.add(0);
							m.add(0);
							m.add(0);
						}
						arsdict.put(loginKey, m);
						rsdict.put(qid, arsdict);

					} else {
						// System.out.println("1" + loginKey + " " + m[0]);
					}

				}

			}

		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;

		sheet1.setColumnWidth((short) 0, (short) 400 * 15);
		sheet1.setColumnWidth((short) 1, (short) 300 * 20);

		Enumeration e2 = rsdict.keys();
		while (e2.hasMoreElements()) {
			String key = e2.nextElement().toString();

			HSSFRow row1 = sheet1.createRow((short) i);
			sheet1.addMergedRegion(new Region((short) i, (short) 3, (short) i, (short) 12));

			i = i + 1;

			sheet1.addMergedRegion(new Region((short) i, (short) 3, (short) i, (short) 4));
			sheet1.addMergedRegion(new Region((short) i, (short) 5, (short) i, (short) 6));
			sheet1.addMergedRegion(new Region((short) i, (short) 7, (short) i, (short) 8));
			sheet1.addMergedRegion(new Region((short) i, (short) 9, (short) i, (short) 10));
			sheet1.addMergedRegion(new Region((short) i, (short) 11, (short) i, (short) 12));

			HSSFRow row2 = sheet1.createRow((short) i);
			row2.createCell((short) 3).setCellValue("第一选项");
			row2.createCell((short) 5).setCellValue("第二选项");
			row2.createCell((short) 7).setCellValue("第三选项");
			row2.createCell((short) 9).setCellValue("第四选项");
			row2.createCell((short) 11).setCellValue("第五选项");

			i = i + 1;
			HSSFRow rowheader = sheet1.createRow((short) i);
			rowheader.createCell((short) 0).setCellValue("学科");
			rowheader.createCell((short) 1).setCellValue("承训单位");
			rowheader.createCell((short) 2).setCellValue("完成率");
			rowheader.createCell((short) 3).setCellValue("人数");
			rowheader.createCell((short) 4).setCellValue("百分比");
			rowheader.createCell((short) 5).setCellValue("人数");
			rowheader.createCell((short) 6).setCellValue("百分比");
			rowheader.createCell((short) 7).setCellValue("人数");
			rowheader.createCell((short) 8).setCellValue("百分比");
			rowheader.createCell((short) 9).setCellValue("人数");
			rowheader.createCell((short) 10).setCellValue("百分比");
			rowheader.createCell((short) 11).setCellValue("人数");
			rowheader.createCell((short) 12).setCellValue("百分比");

			i = i + 1;

			Hashtable<String, Object> arsdict = (Hashtable<String, Object>) rsdict.get(key);
			DecimalFormat fnum = new DecimalFormat("0.00%");
			Enumeration e1 = arsdict.keys();

			while (e1.hasMoreElements()) {

				String key1 = e1.nextElement().toString();

				List<Object> t = (List<Object>) arsdict.get(key1);

				row1.createCell((short) 3).setCellValue(t.get(0).toString());

				int n1 = t.get(4) == null ? 0 : Integer.parseInt(t.get(4).toString());
				int n2 = t.get(5) == null ? 0 : Integer.parseInt(t.get(5).toString());
				int n3 = t.get(6) == null ? 0 : Integer.parseInt(t.get(6).toString());
				int n4 = t.get(7) == null ? 0 : Integer.parseInt(t.get(7).toString());
				int n5 = t.get(8) == null ? 0 : Integer.parseInt(t.get(8).toString());

				int total = n1 + n2 + n3 + n4 + n5;

				float f1 = (float) n1 / total;
				float f2 = (float) n2 / total;
				float f3 = (float) n3 / total;
				float f4 = (float) n4 / total;
				float f5 = (float) n5 / total;
				float f6 = Float.parseFloat(t.get(3).toString());

				HSSFRow row3 = sheet1.createRow((short) i);

				row3.createCell((short) 0).setCellValue(t.get(1).toString());
				row3.createCell((short) 1).setCellValue(t.get(2).toString());
				row3.createCell((short) 2).setCellValue(fnum.format(f6));
				row3.createCell((short) 3).setCellValue(n1);
				row3.createCell((short) 4).setCellValue(fnum.format(f1));
				row3.createCell((short) 5).setCellValue(n2);
				row3.createCell((short) 6).setCellValue(fnum.format(f2));
				row3.createCell((short) 7).setCellValue(n3);
				row3.createCell((short) 8).setCellValue(fnum.format(f3));
				row3.createCell((short) 9).setCellValue(n4);
				row3.createCell((short) 10).setCellValue(fnum.format(f4));
				row3.createCell((short) 11).setCellValue(n5);
				row3.createCell((short) 12).setCellValue(fnum.format(f5));

				i = i + 1;

			}
		}

		Paper p = this.paperService.get(pid);
		String name = p.getTitle();
		String filename = pid + name;

		try {
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
			response.setCharacterEncoding("UTF-8");

			OutputStream os = response.getOutputStream();
			os.flush();
			wb.write(os);
			os.close();

		} catch (Exception e) {
		}
	}

	/**
	 * 完成率统计
	 * 
	 * @return
	 */
	public String wanchenglv() {
		String pid = request.getParameter("pid");

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.qid = request.getParameter("question");

		// 通过loginkey解析出项目、承办单位、科目信息
		String sql = "SELECT p.name AS project, s.name AS SUBJECT, t.name AS unit, l.id FROM loginkey l, project p, subject s, trainingunit t WHERE l.project = p.id AND l.subject = s.id AND l.trainingunit = t.id AND l.id IN(SELECT DISTINCT loginkey FROM submit WHERE psq = "
				+ pid + ")";
		List subObjs = submitService.executeSQL(sql);

		for (int i = 0; i < subObjs.size(); i++) {
			Object[] objects = (Object[]) subObjs.get(i);

			String loginKey = (String) objects[3];
			String[] info = { (String) objects[0], (String) objects[1], (String) objects[2] };

			this.infoDic.put(loginKey, info);
		}

		String wclsql = "SELECT lk.id, lk.plansum, lk.realsum, t.count, t.count/lk.realsum AS percent FROM loginkey lk, (SELECT loginkey, COUNT(*) AS COUNT FROM submit r WHERE psq = "
				+ pid;
		if (project != null && !project.equals("0") && !project.equals("")) {
			String fproid = project.format("%04d", Integer.parseInt(project));
			// sql += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
			wclsql += " and SUBSTRING(r.loginkey, 3,4) = '" + fproid + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			String fsub = project.format("%03d", Integer.parseInt(subject));
			// sql += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
			wclsql += " and SUBSTRING(r.loginkey, 10,3) = '" + fsub + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			String funi = project.format("%03d", Integer.parseInt(unit));
			// sql += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
			wclsql += " and SUBSTRING(r.loginkey, 7,3) = '" + funi + "'";
		}

		wclsql += " GROUP BY loginkey) t WHERE lk.id = t.loginkey order by percent desc";

		System.out.println(wclsql);

		float countRealsum = 0;
		// int countVotesum = 0;
		List questionCounts = resultService.executeSQL(wclsql);
		for (int i = 0; i < questionCounts.size(); i++) {
			Object[] objects = (Object[]) questionCounts.get(i);

			float f = Float.parseFloat(objects[4].toString());
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumIntegerDigits(3);
			nf.setMaximumFractionDigits(1);

			System.out.println(objects[0] + " .... " + objects[1] + " ........ " + objects[2] + "........... " + objects[3] + " ........ "
					+ nf.format(f));
			countRealsum += Float.parseFloat(objects[2].toString());
			countVotesum += Float.parseFloat(objects[3].toString());

			Object[] data = { objects[0], objects[1], objects[2], objects[3], nf.format(f) };
			this.wclCountMap.add(data);
		}

		float f = countVotesum / countRealsum;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(3);
		nf.setMaximumFractionDigits(1);

		this.wclPercent = nf.format(f);

		this.paper = paperService.get(pid);
		// 字典信息
		this.subjects = subjectService.findAll();
		this.projects = projectService.findAll();
		this.units = trainingUnitService.findAll();

		return "wanchenglv";
	}

	public List<Submit> getSubmits() {
		return submits;
	}

	public void setSubmits(List<Submit> submits) {
		this.submits = submits;
	}

	public Hashtable<String, String[]> getInfoDic() {
		return infoDic;
	}

	public void setInfoDic(Hashtable<String, String[]> infoDic) {
		this.infoDic = infoDic;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<TrainingUnit> getUnits() {
		return units;
	}

	public void setUnits(List<TrainingUnit> units) {
		this.units = units;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public Submit getSubmit() {
		return submit;
	}

	public void setSubmit(Submit submit) {
		this.submit = submit;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public Hashtable<Object, Object> getAnswerCountMap() {
		return answerCountMap;
	}

	public void setAnswerCountMap(Hashtable<Object, Object> answerCountMap) {
		this.answerCountMap = answerCountMap;
	}

	public List getDuibiCountMap() {
		return duibiCountMap;
	}

	public void setDuibiCountMap(List duibiCountMap) {
		this.duibiCountMap = duibiCountMap;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public List getAvgCountMap() {
		return avgCountMap;
	}

	public void setAvgCountMap(List avgCountMap) {
		this.avgCountMap = avgCountMap;
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

	public List getWclCountMap() {
		return wclCountMap;
	}

	public void setWclCountMap(List wclCountMap) {
		this.wclCountMap = wclCountMap;
	}

	public int getCountVotesum() {
		return countVotesum;
	}

	public void setCountVotesum(int countVotesum) {
		this.countVotesum = countVotesum;
	}

	public String getWclPercent() {
		return wclPercent;
	}

	public void setWclPercent(String wclPercent) {
		this.wclPercent = wclPercent;
	}

}