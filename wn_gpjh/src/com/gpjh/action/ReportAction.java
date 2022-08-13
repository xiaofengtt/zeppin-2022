package com.gpjh.action;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.gpjh.model.Question;
import com.gpjh.model.Result;
import com.gpjh.model.Submit;
import com.gpjh.service.PaperService;
import com.gpjh.service.ProjectAppNoService;
import com.gpjh.service.ProjectService;
import com.gpjh.service.PsqProjectMapService;
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
	private ProjectAppNoService projectAppNoService;

	@Autowired
	private TrainingUnitService trainingUnitService;

	@Autowired
	private ResultService resultService;

	@Autowired
	private PaperService paperService;

	@Autowired
	private PsqProjectMapService ppmService;

	@Autowired
	private QuestionService questionService;

	private List<Submit> submits;
	private List<Result> results;

	private Submit submit;

	private PageInfo pageInfo;

	private Paper paper;

	private List subjects;
	private List projects;
	private List units;

	private List<Question> questions;

	private String subject;
	private String project;
	private String unit;
	private String qid;

	private String avgCountScore;
	private String intAvgCountScore;

	private int countVotesum;
	private int countHasSms;
	private String wclPercent;
	private float fCompletePer;

	private Hashtable<String, String[]> infoDic = new Hashtable<String, String[]>();
	private List<String[]> lstInfo = new ArrayList<String[]>();

	public List<String[]> getLstInfo() {
		return lstInfo;
	}

	public void setLstInfo(List<String[]> lstInfo) {
		this.lstInfo = lstInfo;
	}

	private Hashtable<Object, Object> answerCountMap = new Hashtable<Object, Object>();
	private Hashtable<String, String> quAvgmap = new Hashtable<String, String>();

	public Hashtable<String, String> getQuAvgmap() {
		return quAvgmap;
	}

	public void setQuAvgmap(Hashtable<String, String> quAvgmap) {
		this.quAvgmap = quAvgmap;
	}

	private List avgCountMap = new ArrayList();
	private List duibiCountMap = new ArrayList();
	private List wclCountMap = new ArrayList();
	private List projectes = new ArrayList();

	private List rcountList;

	public List getRcountList() {
		return rcountList;
	}

	public void setRcountList(List rcountList) {
		this.rcountList = rcountList;
	}

	public List getProjectes() {
		return projectes;
	}

	public void setProjectes(List projectes) {
		this.projectes = projectes;
	}

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
	 * 原始数据
	 * 
	 * @return
	 */
	public String summary() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		String page = request.getParameter("page");

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		session.setAttribute("summaryPid", spid);
		session.setAttribute("summaryProject", this.project);
		session.setAttribute("summarySubject", this.subject);
		session.setAttribute("summaryUnit", this.unit);

		int pageId = 1;
		if (page != null)
			pageId = Integer.parseInt(page);
		int pageSize = 20;

		String sql = "SELECT pao.name as project , ps.name as SUBJECT ,pu.name as unit,l.id FROM loginkey l,pe_trainee pe,pe_unit pu,pe_subject ps,pe_pro_applyno pao WHERE l.traineeid = pe.id and pe.fk_subject = ps.id and pe.fk_training_unit=pu.id and pe.fk_pro_applyno = pao.id and l.id in (SELECT DISTINCT loginkey FROM submit WHERE psq ="
				+ pid + ")";

		List subObjs = submitService.executeSQL(sql);

		for (int i = 0; i < subObjs.size(); i++) {
			Object[] objects = (Object[]) subObjs.get(i);

			String loginKey = (String) objects[3];
			String[] info = { (String) objects[0], (String) objects[1], (String) objects[2] };

			this.infoDic.put(loginKey, info);
		}

		// 拼装sql语句
		sql = "from Submit t where psq = " + pid;
		sql += " and t.loginkey in (select distinct l.id from LoginKey l,TrainEe p,ProjectAppNo pa where l.traineeId = p.id and pa.id = p.proApplyNo ";
		if (project != null && !project.equals("0") && !project.equals("")) {
			sql += " and pa.id = '" + project + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			sql += "and p.fkSubject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			sql += "and p.trainingUnit='" + unit + "'";
		}

		sql += ")";

		sql += " order by creattime desc";
		// 数据信息
		this.submits = submitService.getListForPage(sql, pageId, pageSize);

		// 分页信息
		int totalRow = submitService.queryRowCount(sql);
		this.pageInfo = new PageInfo(totalRow, pageSize, pageId);

		this.paper = paperService.get(pid);
		// 字典信息
		this.subjects = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_subject pe join pe_trainee te on pe.id = te.fk_subject  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq="
						+ spid + ")");
		this.projects = questionService
				.executeSQL("select distinct pa.id,pa.name from pe_pro_applyno pa ,psqprojectmap pm where pa.id = pm.project and pm.psq=" + spid);
		this.units = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_unit pe join pe_trainee te on pe.id = te.fk_training_unit  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq ="
						+ spid + ")");

		return "summary";
	}

	public String showAllAdvice() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		String sql = "select  r.content from result r,question q,psq p,loginkey l,pe_trainee pe WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and r.question = q.id AND q.psq = p.id AND q.type=6 and p.id="
				+ spid;

		if (project != null && !project.equals("0") && !project.equals("")) {

			sql += " and pe.fk_pro_applyno ='" + project + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {

			sql += "and pe.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {

			sql += " and pe.fk_training_unit='" + unit + "'";
		}

		this.rcountList = questionService.executeSQL(sql);
		this.paper = paperService.get(pid);

		return "advice";
	}

	/**
	 * 每个人的答卷结果
	 * 
	 * @return
	 */
	public String detail() {

		String ssubmit = request.getParameter("sub");
		int submit = Integer.parseInt(ssubmit);
		String pid = request.getParameter("pid");

		// 通过loginkey解析出项目、承办单位、科目信息
		String sql = "SELECT pao.name as project , ps.name as SUBJECT ,pu.name as unit,l.id FROM loginkey l,pe_trainee pe,pe_unit pu,pe_subject ps,pe_pro_applyno pao WHERE l.traineeid = pe.id and pe.fk_subject = ps.id and pe.fk_training_unit=pu.id and pe.fk_pro_applyno = pao.id and l.id in (SELECT DISTINCT loginkey FROM submit WHERE psq ="
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

	public void summaryDoExcel() {

		try

		{
			String spid = session.getAttribute("summaryPid").toString();
			String project = session.getAttribute("summaryProject") == null ? "" : session.getAttribute("summaryProject").toString();
			String subject = session.getAttribute("summarySubject") == null ? "" : session.getAttribute("summarySubject").toString();
			String unit = session.getAttribute("summaryUnit") == null ? "" : session.getAttribute("summaryUnit").toString();

			String sql = "select r.loginkey,s.ip,s.creattime ,q.inx as qinx,a.inx as ainx,q.type,r.content as rcontent"
					+ " from submit s,result r,question q ,answer a,loginkey l ,pe_trainee pe "
					+ "where s.loginkey = r.loginkey and r.question = q.id and r.answer = a.id and s.loginkey=l.id and l.traineeid=pe.id and  "
					+ " s.psq=" + spid;

			String endSql = "select s.loginkey,to_char(pr.content) as content " + "from submit s , pr_vote_record pr ,loginkey l ,pe_trainee pe"
					+ " where s.loginkey = l.id and l.traineeid = pe.id and s.loginkey = pr.temp2 and  s.psq=" + spid;

			if (project != null && !project.equals("0") && !project.equals("")) {
				sql += " and pe.fk_pro_applyno = '" + project + "'";
				endSql += " and pe.fk_pro_applyno = '" + project + "'";
			}

			if (subject != null && !subject.equals("0") && !subject.equals("")) {
				sql += "and pe.fk_subject='" + subject + "'";
				endSql += "and pe.fk_subject='" + subject + "'";
			}

			if (unit != null && !unit.equals("0") && !unit.equals("")) {
				sql += "and pe.fk_training_unit='" + unit + "'";
				endSql += "and pe.fk_training_unit='" + unit + "'";
			}

			List li = questionService.executeSQL(sql);

			String headSql = "select q.inx,q.type from question q where  q.type!=0 and q.psq=" + spid + " order by q.inx";

			List hli = questionService.executeSQL(headSql);

			List endLi = questionService.executeSQL(endSql);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("sheet1");
			int i = 0;
			HSSFRow rowheader = sheet1.createRow((short) i);
			i++;

			rowheader.createCell((short) 0).setCellValue("登陆标识码");
			rowheader.createCell((short) 1).setCellValue("ip地址");
			rowheader.createCell((short) 2).setCellValue("投票时间");

			int k = 3;
			int endcol = hli.size() + 2;

			for (int j = 0; j < hli.size(); j++) {

				Object[] obj = (Object[]) hli.get(j);
				String qinx = obj[0].toString();
				String qtype = obj[1].toString();
				if (qtype.equals("6")) {
					rowheader.createCell((short) k).setCellValue("培训意见");
				} else if (qtype.equals("7")) {
					rowheader.createCell((short) k).setCellValue("教师与专题");

				} else {
					rowheader.createCell((short) k).setCellValue("问题" + qinx);
				}

				k++;
			}

			Hashtable<String, HSSFRow> ht = new Hashtable<String, HSSFRow>();

			for (int j = 0; j < li.size(); j++) {
				Object[] obj = (Object[]) li.get(j);
				String loginkey = obj[0].toString();

				String ip = obj[1].toString();
				String date = obj[2].toString();
				int qinx = Integer.parseInt(obj[3].toString());
				String ainx = obj[4].toString();
				String type = obj[5].toString();
				String prcontent = obj[6] == null ? "" : obj[6].toString();

				if (ht.containsKey(loginkey)) {
					HSSFRow row = ht.get(loginkey);
					int col = qinx + 2;
					if (type.equals("6")) {
						row.createCell(col).setCellValue(prcontent);
					} else if (type.equals("7")) {
						row.createCell(col).setCellValue(prcontent);
					} else {
						row.createCell(col).setCellValue(ainx);
					}

				} else {

					HSSFRow row = sheet1.createRow((short) i);
					i++;
					ht.put(loginkey, row);
					row.createCell((short) 0).setCellValue(loginkey);
					row.createCell((short) 1).setCellValue(ip);
					row.createCell((short) 2).setCellValue(date);
					int col = qinx + 2;
					if (type.equals("6")) {
						row.createCell(col).setCellValue(prcontent);
					} else if (type.equals("7")) {
						row.createCell(col).setCellValue(prcontent);
					} else {
						row.createCell(col).setCellValue(ainx);
					}
				}

			}

			for (int j = 0; j < endLi.size(); j++) {
				Object[] obj = (Object[]) endLi.get(j);
				String loginkey = obj[0].toString();
				String content = obj[1] == null ? "" : obj[1].toString();
				if (ht.containsKey(loginkey)) {
					HSSFRow row = ht.get(loginkey);
					row.createCell(endcol).setCellValue(content);
				}
			}

			int pid = Integer.parseInt(spid);
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
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 每张试卷的统计
	 * 
	 * @return
	 */
	public String paper() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		session.setAttribute("paperPid", spid);
		session.setAttribute("paperProject", this.project);
		session.setAttribute("paperSubject", this.subject);
		session.setAttribute("paperUnit", this.unit);

		Hashtable<Object, Integer> everyQuestionCount = new Hashtable<Object, Integer>();

		// 每个题的每个答案回答次数
		String sql = "SELECT r.question, r.answer, COUNT(*) AS SUM FROM result r, question q, psq p,loginkey l,pe_trainee pe WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and r.question = q.id AND q.psq = p.id AND p.id= "
				+ pid;

		String sqlQuestionCount = "SELECT r.question, COUNT(*) AS SUM FROM result r, question q, psq p ,loginkey l,pe_trainee pe WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and r.question = q.id AND q.psq = p.id AND p.id= "
				+ pid;

		if (project != null && !project.equals("0") && !project.equals("")) {

			sql += " and pe.fk_pro_applyno ='" + project + "'";
			sqlQuestionCount += " and pe.fk_pro_applyno ='" + project + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			sql += "and pe.fk_subject='" + subject + "'";
			sqlQuestionCount += "and pe.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			sql += " and pe.fk_training_unit='" + unit + "'";
			sqlQuestionCount += " and pe.fk_training_unit='" + unit + "'";
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

			float f = (float) Integer.parseInt(objects[2].toString()) / everyQuestionCount.get(objects[0]);
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumIntegerDigits(3);
			nf.setMaximumFractionDigits(1);

			Object[] data = { objects[2], nf.format(f) };

			this.answerCountMap.put(objects[0] + "_" + objects[1], data);
		}

		// 字典信息
		this.subjects = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_subject pe join pe_trainee te on pe.id = te.fk_subject  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq="
						+ spid + ")");
		this.projects = questionService
				.executeSQL("select distinct pa.id,pa.name from pe_pro_applyno pa ,psqprojectmap pm where pa.id = pm.project and pm.psq=" + spid);
		this.units = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_unit pe join pe_trainee te on pe.id = te.fk_training_unit  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq ="
						+ spid + ")");

		this.paper = paperService.get(pid);

		return "paper";
	}

	/**
	 * 汇总统计
	 * 
	 * @return
	 */
	public String huizong() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		session.setAttribute("huizongSpid", spid);

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		String hzsql = "SELECT loginkey, AVG(score) as score FROM result r, question q,loginkey l,pe_trainee pe WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and r.question = q.id and q.istongji =1 and q.type=2 AND q.psq = "
				+ pid;

		if (project != null && !project.equals("0") && !project.equals("")) {

			hzsql += " and pe.fk_pro_applyno ='" + project + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {

			hzsql += "and pe.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {

			hzsql += " and pe.fk_training_unit='" + unit + "'";
		}

		hzsql += " GROUP BY loginkey order by score desc";

		String sql = "select pao.name as project, pu.name unit ,AVG(fz.score) as score from loginkey l join pe_trainee pt on l.traineeid = pt.id join("
				+ hzsql
				+ ") fz on l.id = fz.loginkey join pe_unit pu on pu.id = pt.fk_training_unit"
				+ " join pe_pro_applyno pao on pao.id = pt.fk_pro_applyno " + "GROUP BY pu.name,pao.name order by score desc";

		List avgCounts = resultService.executeSQL(sql);

		float totalScore = 0;

		for (int i = 0; i < avgCounts.size(); i++) {
			Object[] objects = (Object[]) avgCounts.get(i);

			float f = Float.parseFloat(objects[2].toString());
			totalScore += f;
			DecimalFormat df = new DecimalFormat();
			String style = "0.000";
			df.applyPattern(style);

			Object[] data = { objects[0], objects[1], df.format(f) };

			this.avgCountMap.add(data);
		}

		session.setAttribute("huizongMap", this.avgCountMap);

		float tmpf = totalScore / avgCounts.size();
		double avgScore = tmpf;// - tmpf % 0.5;
		DecimalFormat df = new DecimalFormat();
		String style = "0.000";
		df.applyPattern(style);

		this.avgCountScore = df.format(avgScore);

		style = "0";
		df.applyPattern(style);
		this.intAvgCountScore = df.format(avgScore * 10);

		this.paper = paperService.get(pid);

		// 字典信息
		this.subjects = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_subject pe join pe_trainee te on pe.id = te.fk_subject  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq="
						+ spid + ")");
		this.projects = questionService
				.executeSQL("select distinct pa.id,pa.name from pe_pro_applyno pa ,psqprojectmap pm where pa.id = pm.project and pm.psq=" + spid);
		this.units = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_unit pe join pe_trainee te on pe.id = te.fk_training_unit  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq ="
						+ spid + ")");

		return "huizong";
	}

	public void huizongDoExcel() {

		try {

			List huizong = (List) session.getAttribute("huizongMap");
			String spid = session.getAttribute("huizongSpid").toString();

			if (huizong != null) {

				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet1 = wb.createSheet("sheet1");
				int i = 0;
				HSSFRow rowheader = sheet1.createRow((short) i);
				i++;

				rowheader.createCell((short) 0).setCellValue("项目名称");
				rowheader.createCell((short) 1).setCellValue("承训机构");
				rowheader.createCell((short) 2).setCellValue("总体满意度");

				for (int j = 0; j < huizong.size(); j++) {

					Object[] args = (Object[]) huizong.get(j);
					HSSFRow row = sheet1.createRow((short) i);
					i++;
					row.createCell((short) 0).setCellValue(args[0].toString());
					row.createCell((short) 1).setCellValue(args[1].toString());
					row.createCell((short) 2).setCellValue(args[2].toString());

				}

				String name = " huizong";
				String filename = spid + name;

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
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
		}

	}

	public void huizongDoAllExcel() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		String hzsql = "select p.name as project,s.name as subject ,u.name as unitname,AVG(r.score) as score "
				+ " from result r ,loginkey l,pe_trainee t,question q,pe_pro_applyno p,pe_subject s,pe_unit u "
				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_pro_applyno=p.id and t.fk_training_unit=u.id and t.fk_subject=s.id"
				+ " and r.question=q.id and q.istongji = 1 and q.type=2 and  q.psq =" + pid + " group by p.name,s.name,u.name";

		List li = questionService.executeSQL(hzsql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("项目名称");
		rowheader.createCell((short) 1).setCellValue("学科");
		rowheader.createCell((short) 2).setCellValue("承训单位");
		rowheader.createCell((short) 3).setCellValue("满意度");

		DecimalFormat df = new DecimalFormat();
		String style = "0.000";
		df.applyPattern(style);

		for (int k = 0; k < li.size(); k++) {

			Object[] args = (Object[]) li.get(k);
			HSSFRow row = sheet1.createRow((short) i);
			i++;

			float f = Float.parseFloat(args[3].toString());

			row.createCell((short) 0).setCellValue(args[0].toString());
			row.createCell((short) 1).setCellValue(args[1].toString());
			row.createCell((short) 2).setCellValue(args[2].toString());
			row.createCell((short) 3).setCellValue(df.format(f));

		}

		String name = "huizong";
		String filename = name;

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
			e.printStackTrace();
		}

	}

	/**
	 * 对比统计
	 * 
	 * @return
	 */
	public String duibi() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		session.setAttribute("duibiSpid", spid);

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.qid = request.getParameter("question");

		String dbsql = "SELECT r.loginkey, q.inx as inx, q.name as qname, r.question, AVG(r.score) AS scoreavg FROM result r, answer a, question q,loginkey l,pe_trainee pe WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and a.question = q.id AND r.answer = a.id  and q.istongji = 1 AND a.psq = "
				+ pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			dbsql += " and pe.fk_pro_applyno ='" + project + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			dbsql += "and pe.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			dbsql += " and pe.fk_training_unit='" + unit + "'";
		}

		if (this.qid != null && !this.qid.equals("0") && !this.qid.equals("")) {
			dbsql += " and r.question = " + this.qid;
		}

		dbsql += "   GROUP BY  r.LOGINKEY , r.QUESTION, q.inx, q.name ORDER BY q.inx, scoreavg desc";

		String sql = "select pao.name as project, pu.name as unit ,fz.qname,fz.inx, AVG(fz.scoreavg) as score from loginkey l join pe_trainee pt on l.traineeid = pt.id join ("
				+ dbsql
				+ ")fz on l.id = fz.loginkey"
				+ " join pe_unit pu on pu.id = pt.fk_training_unit"
				+ " join pe_pro_applyno pao on pao.id = pt.fk_pro_applyno" + " GROUP BY pu.name,pao.name,fz.qname,fz.inx order by fz.inx,score desc";

		String questionSql = "select fz.qname,fz.inx, AVG(fz.scoreavg) as score from loginkey l  join (" + dbsql + ")fz on l.id = fz.loginkey "
				+ " GROUP BY fz.qname,fz.inx order by fz.inx,score desc";

		DecimalFormat df = new DecimalFormat();
		String style = "0.000";
		df.applyPattern(style);

		List quCount = resultService.executeSQL(questionSql);
		for (int i = 0; i < quCount.size(); i++) {
			Object[] obj = (Object[]) quCount.get(i);
			String key = obj[1].toString();
			float f = Float.parseFloat(obj[2].toString());
			String value = df.format(f);
			quAvgmap.put(key, value);
		}

		session.setAttribute("duibiAvgMap", quAvgmap);

		List questionCounts = resultService.executeSQL(sql);
		for (int i = 0; i < questionCounts.size(); i++) {
			Object[] objects = (Object[]) questionCounts.get(i);

			float f = Float.parseFloat(objects[4].toString());

			Object[] data = { objects[0], objects[1], objects[2], objects[3], df.format(f) };
			this.duibiCountMap.add(data);
		}

		session.setAttribute("duibiMap", this.duibiCountMap);

		this.paper = paperService.get(pid);

		// 字典信息
		this.subjects = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_subject pe join pe_trainee te on pe.id = te.fk_subject  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq="
						+ spid + ")");
		this.projects = questionService
				.executeSQL("select distinct pa.id,pa.name from pe_pro_applyno pa ,psqprojectmap pm where pa.id = pm.project and pm.psq=" + spid);
		this.units = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_unit pe join pe_trainee te on pe.id = te.fk_training_unit  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq ="
						+ spid + ")");

		this.questions = questionService.getListByHSQL("from Question where paper =" + pid + " and type =2 and istongji=1");

		return "duibi";
	}

	public void duibiDoExcel() {
		try {

			List li = (List) session.getAttribute("duibiMap");
			Hashtable<String, String> hst = (Hashtable<String, String>) session.getAttribute("duibiAvgMap");
			String spid = session.getAttribute("duibiSpid").toString();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("sheet1");
			int i = 0;
			HSSFRow rowheader = sheet1.createRow((short) i);
			i++;

			rowheader.createCell((short) 0).setCellValue("项目名称");
			rowheader.createCell((short) 1).setCellValue("承训机构");
			rowheader.createCell((short) 2).setCellValue("总体满意度");

			int flag = 0;

			for (int j = 0; j < li.size(); j++) {

				Object[] obj = (Object[]) li.get(j);

				int inx = Integer.parseInt(obj[3].toString());
				if (inx != flag) {

					HSSFRow rowq = sheet1.createRow((short) i);
					rowq.createCell(0).setCellValue("第" + inx + "题:" + obj[2].toString());
					sheet1.addMergedRegion(new Region((short) i, (short) 0, (short) i, (short) 1));
					rowq.createCell(2).setCellValue(hst.get(inx + ""));
					i++;

					for (int k = 0; k < li.size(); k++) {
						Object[] objk = (Object[]) li.get(k);

						int tinx = Integer.parseInt(objk[3].toString());
						if (tinx == inx) {
							HSSFRow row = sheet1.createRow((short) i);

							row.createCell((short) 0).setCellValue(objk[0].toString());
							row.createCell((short) 1).setCellValue(objk[1].toString());
							row.createCell((short) 2).setCellValue(objk[4].toString());
							i++;
						}
					}

				}

				flag = inx;

			}

			String name = " duibi";
			String filename = spid + name;

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
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 完成率统计
	 * 
	 * @return
	 */
	public String wanchenglv() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);
		String CompletePer = request.getParameter("completePer");
		fCompletePer = 100;
		if (CompletePer != "" && CompletePer != null) {
			try {
				fCompletePer = Float.parseFloat(CompletePer);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		this.project = request.getParameter("project");

		String sqlString = "";
		/*
		 * this.subject = request.getParameter("subject"); this.unit =
		 * request.getParameter("unit"); this.qid =
		 * request.getParameter("question");
		 */
		// 通过pid找到项目

		sqlString = "select po.id,po.name from psqprojectmap pm join pe_pro_applyno po  on pm.project=po.id and pm.psq=" + pid;
		projectes = submitService.executeSQL(sqlString);
		if (this.project == null) {
			Object[] objects = (Object[]) projectes.get(0);
			this.project = objects[0].toString();

		}

		// 解析出项目、承办单位、科目信息 短信发送成功的人。 已经结业的人 已经提交的人
		// 填表人数，项目人员总数
		/*
		 * String sqlcount =
		 * "select  count(*) , status from (SELECT pao.name as project , ps.name as SUBJECT ,pu.name as unit , l.id,l.psqstatus as status FROM loginkey l,pe_trainee pe,pe_unit pu,pe_subject ps,pe_pro_applyno pao WHERE pao.id='"
		 * + project +
		 * "' and  l.traineeid = pe.id and pe.fk_subject = ps.id and pe.fk_training_unit=pu.id and pe.fk_pro_applyno = pao.id and l.id in (SELECT id FROM loginkey where loginkey.psqmapid ="
		 * + pid + ")) group by status order by status "; List subObjs =
		 * submitService.executeSQL(sqlcount); this.countVotesum = 0;
		 * this.wclPercent = "0"; if (subObjs.size() > 1) { this.countVotesum =
		 * Integer.parseInt(((Object[]) subObjs.get(0))[0] .toString());// 填写问卷的
		 * int totle = Integer.parseInt(((Object[]) subObjs.get(1))[0]
		 * .toString());// 未填写问卷的 DecimalFormat dFormat = new
		 * DecimalFormat("0.00"); this.wclPercent = String.valueOf(dFormat
		 * .format(((double) 100*countVotesum / (countVotesum + totle))));
		 */

		// 先找出问卷下的所有项目及承训单位和科目
		DecimalFormat dFormat = new DecimalFormat("0.00");
		this.countHasSms = 0;
		this.countVotesum = 0;
		String sql = "select  distinct project,unit,subject from (SELECT pao.name as project , ps.name as SUBJECT ,pu.name as unit , l.id,l.status FROM loginkey l,pe_trainee pe,pe_unit pu,pe_subject ps,pe_pro_applyno pao WHERE pao.id='"
				+ project
				+ "' and  l.traineeid = pe.id and pe.fk_subject = ps.id and pe.fk_training_unit=pu.id and pe.fk_pro_applyno = pao.id and l.psqmapid ="
				+ pid + ")order by unit ";
		List subObjs = submitService.executeSQL(sql);
		if (subObjs.size() > 0) {
			for (int i = 0; i < subObjs.size(); i++) {
				Object[] objects = (Object[]) subObjs.get(i);
				String tempString = "select  count(*) , status from (SELECT pao.name as project , ps.name as SUBJECT ,pu.name as unit , l.id,l.psqstatus as status FROM loginkey l,pe_trainee pe,pe_unit pu,pe_subject ps,pe_pro_applyno pao WHERE pao.id='"
						+ project
						+ "' and pu.name='"
						+ (String) objects[1]
						+ "' and ps.name='"
						+ (String) objects[2]
						+ "'  and  l.traineeid = pe.id and pe.fk_subject = ps.id and pe.fk_training_unit=pu.id and pe.fk_pro_applyno = pao.id and l.psqmapid ="
						+ pid + ") group by status order by status";
				List tempObjs = new ArrayList();
				tempObjs = submitService.executeSQL(tempString);
				int votedSum = 0;// 已投票的人数（本班级的）
				int tempTotle = 0;// 所有人数（本班级的）
				int hasSms = 0; // 已发送短信人数
				int hasGranty = 0;// 已毕业人数
				double per = 0;

				// 如只有一项说明投票人数为0
				if (tempObjs.size() == 1) {
					tempTotle = Integer.parseInt(((Object[]) tempObjs.get(0))[0].toString());

				} else if (tempObjs.size() == 2) {
					votedSum = Integer.parseInt(((Object[]) tempObjs.get(0))[0].toString());
					this.countVotesum += votedSum;
					tempTotle = Integer.parseInt(((Object[]) tempObjs.get(1))[0].toString()) + votedSum;
					// per = (double) votedSum / tempTotle;
				}
				// 已发送短信
				tempString = "select  count(*)  from (SELECT pao.name as project , ps.name as SUBJECT ,pu.name as unit , l.id,l.psqstatus as status FROM loginkey l,pe_trainee pe,pe_unit pu,pe_subject ps,pe_pro_applyno pao WHERE pao.id='"
						+ project
						+ "' and pu.name='"
						+ (String) objects[1]
						+ "' and ps.name='"
						+ (String) objects[2]
						+ "'  and  l.traineeid = pe.id and pe.fk_subject = ps.id and pe.fk_training_unit=pu.id and pe.fk_pro_applyno = pao.id and l.psqmapid ="
						+ pid + " and l.status=1)";
				List tempObjsHasSms = new ArrayList();
				tempObjsHasSms = submitService.executeSQL(tempString);

				hasSms = ((BigDecimal) tempObjsHasSms.get(0)).intValue();
				this.countHasSms += hasSms;
				if (hasSms != 0) {
					per = ((double) votedSum / hasSms) * 100;
				}

				// 已结业
				tempString = "select  count(*)  from (SELECT pao.name as project , ps.name as SUBJECT ,pu.name as unit , l.id,l.psqstatus as status FROM loginkey l,pe_trainee pe,pe_unit pu,pe_subject ps,pe_pro_applyno pao WHERE pao.id='"
						+ project
						+ "' and pu.name='"
						+ (String) objects[1]
						+ "' and ps.name='"
						+ (String) objects[2]
						+ "' and pe.fk_graduted='isgraduateidflag2'  and  l.traineeid = pe.id and pe.fk_subject = ps.id and pe.fk_training_unit=pu.id and pe.fk_pro_applyno = pao.id and l.psqmapid ="
						+ pid + " )";
				List tempObjsHas = new ArrayList();
				tempObjsHas = submitService.executeSQL(tempString);

				hasGranty = ((BigDecimal) tempObjsHas.get(0)).intValue();

				String[] info = { (String) objects[0], (String) objects[1], (String) objects[2], String.valueOf(votedSum), String.valueOf(hasSms),
						String.valueOf(hasGranty), String.valueOf(tempTotle), dFormat.format(per) };
				if (per <= fCompletePer) {
					this.infoDic.put(String.valueOf(i), info);
					lstInfo.add(info);
				}
			}
		}

		// this.mapInfoDic = getSortedHashtableByValue(this.infoDic);
		if (countHasSms > 0) {
			this.wclPercent = dFormat.format(((double) this.countVotesum / this.countHasSms) * 100);
		}
		this.paper = paperService.get(pid);
		// 对hashtable排序
		/*
		 * for (Iterator it = this.infoDic.keySet().iterator(); it.hasNext();) {
		 * String kString = (String) it.next(); String[] object =
		 * this.infoDic.get(kString); lstInfo.add(object); }
		 */

		// 排序
		class compareObject implements Comparator {

			@Override
			public int compare(Object o1, Object o2) {
				String[] temp1 = (String[]) o1;
				String[] temp2 = (String[]) o2;
				int flag = 0;
				if (Float.parseFloat(temp1[7]) > Float.parseFloat(temp2[7])) {
					flag = 1;
				} else if (Float.parseFloat(temp1[7]) < Float.parseFloat(temp2[7])) {
					flag = -1;
				}
				return (flag);

			}

		}

		compareObject co = new compareObject();
		Collections.sort(lstInfo, co);

		/*
		 * for (int i = 0; i < lstInfo.size(); i++) { String[] temp1 =
		 * lstInfo.get(i); for (int j = 0; j < i; j++) { String[] temp2 =
		 * lstInfo.get(j); if (Float.parseFloat(temp1[7]) <
		 * Float.parseFloat(temp2[7])) { String[] tempString = temp1; temp1 =
		 * temp2; temp2 = tempString; } } }
		 */

		session.setAttribute("wanchenglvList", lstInfo);
		session.setAttribute("wanchenglvPid", spid);

		return "wanchenglv";

	}

	public void wanchenglvDoExcel() {

		try {

			String spid = session.getAttribute("wanchenglvPid").toString();
			// int pid = Integer.parseInt(spid);
			// String CompletePer =
			// session.getAttribute("wanchenglvPer").toString();
			// String project =
			// session.getAttribute("wanchengProject").toString();
			//
			// float fCompletePer = 0;
			// if (CompletePer != "" && CompletePer != null) {
			// try {
			// fCompletePer = Float.parseFloat(CompletePer);
			// } catch (Exception e) {
			// }
			// }

			List<String[]> lsinfo = (List<String[]>) session.getAttribute("wanchenglvList");

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("sheet1");
			int i = 0;
			HSSFRow rowheader = sheet1.createRow((short) i);
			i++;

			rowheader.createCell((short) 0).setCellValue("项目名称");
			rowheader.createCell((short) 1).setCellValue("承训机构");
			rowheader.createCell((short) 2).setCellValue("学科");
			rowheader.createCell((short) 3).setCellValue("投票人数");
			rowheader.createCell((short) 4).setCellValue("已发短信人数");
			rowheader.createCell((short) 5).setCellValue("已经结业人数");
			rowheader.createCell((short) 6).setCellValue("总人数");
			rowheader.createCell((short) 7).setCellValue("投票完成比例(%)");

			for (int j = 0; j < lsinfo.size(); j++) {

				String[] args = lsinfo.get(j);
				HSSFRow row = sheet1.createRow((short) i);
				i++;
				row.createCell((short) 0).setCellValue(args[0]);
				row.createCell((short) 1).setCellValue(args[1]);
				row.createCell((short) 2).setCellValue(args[2]);
				row.createCell((short) 3).setCellValue(args[3]);
				row.createCell((short) 4).setCellValue(args[4]);
				row.createCell((short) 5).setCellValue(args[5]);
				row.createCell((short) 6).setCellValue(args[6]);
				row.createCell((short) 7).setCellValue(args[7]);

			}

			String name = " wanchenglv";
			String filename = spid + name;

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
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String manyilv() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");

		String msql = "select r.loginkey as id,sum(r.score) as jscore" + " from result r join question q on r.question = q.id "
				+ " join loginkey l on r.loginkey = l.id " + " join pe_trainee pe on l.traineeid = pe.id "
				+ " where q.istongji=1 and q.type=2  and r.score>=4 and q.psq= " + pid;

		String tsql = "select r.loginkey as id,sum(r.score) as tscore" + " from result r join question q on r.question = q.id "
				+ " join loginkey l on r.loginkey = l.id " + " join pe_trainee pe on l.traineeid = pe.id "
				+ " where q.istongji=1 and q.type=2 and q.psq= " + pid;

		if (project != null && !project.equals("0") && !project.equals("")) {

			msql += " and pe.fk_pro_applyno ='" + project + "'";
			tsql += " and pe.fk_pro_applyno ='" + project + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {

			msql += "and pe.fk_subject='" + subject + "'";
			tsql += "and pe.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {

			msql += " and pe.fk_training_unit='" + unit + "'";
			tsql += " and pe.fk_training_unit='" + unit + "'";
		}

		msql += " group by r.loginkey ";
		tsql += " group by r.loginkey ";

		String hsql = "select fm.id as loginkey,(fm.jscore/ft.tscore) as score " + " from (" + msql + ") fm join (" + tsql
				+ ") ft  on fm.id = ft.id order by score";

		String sql = "select pao.name as project, pu.name unit ,AVG(fz.score) as score from loginkey l join pe_trainee pt on l.traineeid = pt.id join("
				+ hsql
				+ ") fz on l.id = fz.loginkey join pe_unit pu on pu.id = pt.fk_training_unit"
				+ " join pe_pro_applyno pao on pao.id = pt.fk_pro_applyno " + "GROUP BY pu.name,pao.name order by score desc";

		List avgCounts = resultService.executeSQL(sql);

		float totalScore = 0;

		for (int i = 0; i < avgCounts.size(); i++) {
			Object[] objects = (Object[]) avgCounts.get(i);

			float f = Float.parseFloat(objects[2].toString());
			totalScore += f;
			DecimalFormat df = new DecimalFormat();
			String style = "0.00%";
			df.applyPattern(style);

			Object[] data = { objects[0], objects[1], df.format(f) };

			this.avgCountMap.add(data);
		}

		session.setAttribute("manyilvmap", this.avgCountMap);

		float tmpf = totalScore / avgCounts.size();
		double avgScore = tmpf;// - tmpf % 0.5;
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);

		this.avgCountScore = df.format(avgScore);

		style = "0";
		df.applyPattern(style);
		this.intAvgCountScore = df.format(avgScore * 10);

		this.paper = paperService.get(pid);

		// 字典信息
		this.subjects = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_subject pe join pe_trainee te on pe.id = te.fk_subject  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq="
						+ spid + ")");
		this.projects = questionService
				.executeSQL("select distinct pa.id,pa.name from pe_pro_applyno pa ,psqprojectmap pm where pa.id = pm.project and pm.psq=" + spid);
		this.units = questionService
				.executeSQL("select distinct pe.id,pe.name from pe_unit pe join pe_trainee te on pe.id = te.fk_training_unit  where te.fk_pro_applyno in (select distinct pm.project from psqprojectmap pm where pm.psq ="
						+ spid + ")");

		return "manyilv";
	}

	public void manyilvDoExcel() {
		try {

			List huizong = (List) session.getAttribute("manyilvmap");

			if (huizong != null) {

				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet1 = wb.createSheet("sheet1");
				int i = 0;
				HSSFRow rowheader = sheet1.createRow((short) i);
				i++;

				rowheader.createCell((short) 0).setCellValue("项目名称");
				rowheader.createCell((short) 1).setCellValue("承训机构");
				rowheader.createCell((short) 2).setCellValue("总体满意度");

				for (int j = 0; j < huizong.size(); j++) {

					Object[] args = (Object[]) huizong.get(j);
					HSSFRow row = sheet1.createRow((short) i);
					i++;
					row.createCell((short) 0).setCellValue(args[0].toString());
					row.createCell((short) 1).setCellValue(args[1].toString());
					row.createCell((short) 2).setCellValue(args[2].toString());

				}

				String name = "manyilv";
				String filename = name;

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
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
		}
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

	public List getSubjects() {
		return subjects;
	}

	public void setSubjects(List subjects) {
		this.subjects = subjects;
	}

	public List getProjects() {
		return projects;
	}

	public void setProjects(List projects) {
		this.projects = projects;
	}

	public List getUnits() {
		return units;
	}

	public void setUnits(List units) {
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