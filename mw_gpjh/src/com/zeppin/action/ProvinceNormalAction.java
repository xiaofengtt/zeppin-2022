package com.zeppin.action;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zeppin.model.Paper;
import com.zeppin.model.Question;
import com.zeppin.service.PaperService;
import com.zeppin.service.PsqProjectMapService;
import com.zeppin.service.QuestionService;
import com.zeppin.service.ResultService;
import com.zeppin.service.SubmitService;
import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.service.impl.peProApplyNoServiceImpl;

@Controller("provinceAction")
@Scope("prototype")
public class ProvinceNormalAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private peProApplyNoServiceImpl peProApplyNoService;

	@Autowired
	private SubmitService submitService;

	@Autowired
	private ResultService resultService;

	@Autowired
	private PaperService paperService;

	@Autowired
	private PsqProjectMapService ppmService;

	@Autowired
	private QuestionService questionService;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private String subject;
	private String project;
	private String unit;
	private String userid;

	private String avgCountScore;
	private String intAvgCountScore;

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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	private String subproject;
	private String province;

	private String qid;

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
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

	public String getSubproject() {
		return subproject;
	}

	public void setSubproject(String subproject) {
		this.subproject = subproject;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	private List subjects;
	private List projects;
	private List units;
	private List subprojects;
	private List provinces;
	private List avgCountMap = new ArrayList();
	private List duibiCountMap = new ArrayList();

	private List<Question> questions;

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List getDuibiCountMap() {
		return duibiCountMap;
	}

	public void setDuibiCountMap(List duibiCountMap) {
		this.duibiCountMap = duibiCountMap;
	}

	private Hashtable<Object, Object> answerCountMap = new Hashtable<Object, Object>();

	public Hashtable<Object, Object> getAnswerCountMap() {
		return answerCountMap;
	}

	public void setAnswerCountMap(Hashtable<Object, Object> answerCountMap) {
		this.answerCountMap = answerCountMap;
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

	public List getSubprojects() {
		return subprojects;
	}

	public void setSubprojects(List subprojects) {
		this.subprojects = subprojects;
	}

	public List getAvgCountMap() {
		return avgCountMap;
	}

	public void setAvgCountMap(List avgCountMap) {
		this.avgCountMap = avgCountMap;
	}

	private Paper paper;

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public ProvinceNormalAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String huizong() {

		// 省份userid

		Date dt1 = new Date();
		

		if (session.getAttribute("unitUserId") == null) {

			String userId = request.getParameter("userid");
			session.setAttribute("unitUserId", userId);
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
					+ "where s.login_id='" + userId + "'";

			List li = peProApplyNoService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);
			}
		} else {
			String userId = request.getParameter("userid");
			String sessionUserId = session.getAttribute("unitUserId") == null ? "" : session.getAttribute("unitUserId").toString();
			if (!userId.equals(sessionUserId)) {
				session.setAttribute("unitUserId", userId);
				String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
						+ "where s.login_id='" + userId + "'";

				List li = peProApplyNoService.executeSQL(sql);

				if (li.size() > 0) {
					String unitId = li.get(0).toString();
					session.setAttribute("unitNormalId", unitId);
				} else {
					return "huizong";
				}
			}
		}

		Date dt2 = new Date();
		System.out.println("huizong:" + (dt2.getTime() - dt1.getTime()));

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");

		String provin = session.getAttribute("unitNormalId").toString();

		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";

		sql2 += " order by t.parentname desc";
		sql3 += " order by t.name desc";
		sql4 += " order by pe.name desc";
		sql5 += " order by pe.name desc";
		
		this.projects = peProApplyNoService.executeSQL(sql2);
		this.subprojects = peProApplyNoService.executeSQL(sql3);
		this.subjects = peProApplyNoService.executeSQL(sql4);
		this.units = peProApplyNoService.executeSQL(sql5);

		dt2 = new Date();
		System.out.println("huizong:" + (dt2.getTime() - dt1.getTime()));

		String hzsql = "select p.parentname as project, pe.name,pe.id ,AVG(r.score) as score "
				+ " from result r ,loginkey l,trainee t,question q,prochildapplyno p,pe_province pe "
				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_applyno=p.id and p.provinceid=pe.id "
				+ " and r.question=q.id and q.istongji = 1 and q.type=2 ";

		// String hzsql = "select r.loginkey,AVG(r.score) as score " +
		// " from result r join question q on r.question = q.id "
		// + " join loginkey l on r.loginkey = l.id " +
		// " join trainee t on l.traineeid  = t.id " +
		// " join psq p on p.id = q.psq "
		// + " join prochildapplyno pao on t.fk_applyno=pao.id " +
		// " where q.istongji = 1 and q.type=2 ";
		//
		if (project != null && !project.equals("0") && !project.equals("")) {
			hzsql += " and p.parentid= '" + project + "'";
		} else {
			return "huizong";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			hzsql += " and  t.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			hzsql += " and t.fk_training_unit='" + unit + "'";
		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			hzsql += " and p.id ='" + subproject + "'";
		}
		hzsql += " group by p.parentname, pe.name, pe.id order by score desc ";
		//
		// String sql =
		// "select pao.parentname as project, prov.name,prov.id ,AVG(fz.score) as score from loginkey l join trainee pt on l.traineeid = pt.id join("
		// + hzsql
		// +
		// ") fz on l.id = fz.loginkey join pe_unit pu on pu.id = pt.fk_training_unit"
		// + " join prochildapplyno pao on pao.id = pt.fk_applyno "
		// + " join pe_province prov on pao.provinceid = prov.id "
		// + "GROUP BY pao.parentname,prov.name,prov.id order by score desc";

		List avgCounts = resultService.executeSQL(hzsql);

		dt2 = new Date();
		System.out.println("huizong:" + (dt2.getTime() - dt1.getTime()));

		float totalScore = 0;
		DecimalFormat df = new DecimalFormat();
		String style = "0.000";
		df.applyPattern(style);

		for (int i = 0; i < avgCounts.size(); i++) {
			Object[] objects = (Object[]) avgCounts.get(i);

			float f = Float.parseFloat(objects[3].toString());
			totalScore += f;

			int flag = objects[2].toString().equals(provin) ? 1 : 0;

			Object[] data = { objects[0], objects[1], df.format(f), flag };

			this.avgCountMap.add(data);
		}

		dt2 = new Date();
		System.out.println("huizong:" + (dt2.getTime() - dt1.getTime()));

		float tmpf = totalScore / avgCounts.size();
		double avgScore = tmpf;// - tmpf % 0.5;

		this.avgCountScore = df.format(avgScore);

		style = "0";
		df.applyPattern(style);
		this.intAvgCountScore = df.format(avgScore * 10);

		dt2 = new Date();
		System.out.println("huizong:" + (dt2.getTime() - dt1.getTime()));

		return "huizong";

	}

	public String huizongprovince() {

		// 省份userid
		if (session.getAttribute("unitUserId") == null) {

			String userId = request.getParameter("userid");
			session.setAttribute("unitUserId", userId);
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
					+ "where s.login_id='" + userId + "'";

			List li = peProApplyNoService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);
			}
		} else {
			String userId = request.getParameter("userid");
			String sessionUserId = session.getAttribute("unitUserId") == null ? "" : session.getAttribute("unitUserId").toString();
			if (!userId.equals(sessionUserId)) {
				session.setAttribute("unitUserId", userId);
				String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
						+ "where s.login_id='" + userId + "'";

				List li = peProApplyNoService.executeSQL(sql);

				if (li.size() > 0) {
					String unitId = li.get(0).toString();
					session.setAttribute("unitNormalId", unitId);
				}
			}
		}

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");

		String provin = session.getAttribute("unitNormalId").toString();

		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";
		
		sql2 += " order by t.parentname desc";
		sql3 += " order by t.name desc";
		sql4 += " order by pe.name desc";
		sql5 += " order by pe.name desc";

		this.projects = peProApplyNoService.executeSQL(sql2);
		this.subprojects = peProApplyNoService.executeSQL(sql3);
		this.subjects = peProApplyNoService.executeSQL(sql4);
		this.units = peProApplyNoService.executeSQL(sql5);

		// String hzsql = "select r.loginkey,AVG(r.score) as score " +
		// " from result r join question q on r.question = q.id "
		// + " join loginkey l on r.loginkey = l.id " +
		// " join trainee t on l.traineeid  = t.id " +
		// " join psq p on p.id = q.psq "
		// + " join prochildapplyno pao on t.fk_applyno=pao.id " +
		// " where q.istongji = 1 and q.type=2 and pao.provinceid='" + provin +
		// "' ";

		String hzsql = "select p.parentname as project, u.name as unitname ,AVG(r.score) as score "
				+ " from result r ,loginkey l,trainee t,question q,prochildapplyno p,pe_province pe,pe_unit u "
				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_applyno=p.id and p.provinceid=pe.id and t.fk_training_unit=u.id "
				+ " and r.question=q.id and q.istongji = 1 and q.type=2 and p.provinceid='" + provin + "' ";

		if (project != null && !project.equals("0") && !project.equals("")) {
			hzsql += " and p.parentid= '" + project + "'";
		} else {
			return "huizongprovince";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			hzsql += " and  t.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			hzsql += " and t.fk_training_unit='" + unit + "'";
		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			hzsql += " and p.id ='" + subproject + "'";
		}
		// hzsql += " GROUP BY loginkey order by score desc";
		//
		// String sql =
		// "select pao.parentname as project,pu.name ,AVG(fz.score) as score from loginkey l join trainee pt on l.traineeid = pt.id join("
		// + hzsql +
		// ") fz on l.id = fz.loginkey join pe_unit pu on pu.id = pt.fk_training_unit"
		// + " join prochildapplyno pao on pao.id = pt.fk_applyno " +
		// " join pe_province prov on pao.provinceid = prov.id "
		// + "GROUP BY pao.parentname,pu.name order by score desc";

		hzsql += " group by p.parentname, u.name order by score desc ";

		List avgCounts = resultService.executeSQL(hzsql);

		float totalScore = 0;
		DecimalFormat df = new DecimalFormat();
		String style = "0.000";
		df.applyPattern(style);

		for (int i = 0; i < avgCounts.size(); i++) {
			Object[] objects = (Object[]) avgCounts.get(i);

			float f = Float.parseFloat(objects[2].toString());
			totalScore += f;

			Object[] data = { objects[0], objects[1], df.format(f) };

			this.avgCountMap.add(data);
		}

		session.setAttribute("huizongMap", this.avgCountMap);

		float tmpf = totalScore / avgCounts.size();
		double avgScore = tmpf;// - tmpf % 0.5;

		this.avgCountScore = df.format(avgScore);

		style = "0";
		df.applyPattern(style);
		this.intAvgCountScore = df.format(avgScore * 10);

		return "huizongprovince";

	}

	public void huizongDoExcel() {

		try {

			List huizong = (List) session.getAttribute("huizongMap");

			if (huizong != null) {

				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet1 = wb.createSheet("sheet1");
				int i = 0;
				HSSFRow rowheader = sheet1.createRow((short) i);
				i++;

				rowheader.createCell((short) 0).setCellValue("项目名称");
				rowheader.createCell((short) 1).setCellValue("培训单位");
				rowheader.createCell((short) 2).setCellValue("总体满意度");

				for (int j = 0; j < huizong.size(); j++) {

					Object[] args = (Object[]) huizong.get(j);
					HSSFRow row = sheet1.createRow((short) i);
					i++;
					row.createCell((short) 0).setCellValue(args[0].toString());
					row.createCell((short) 1).setCellValue(args[1].toString());
					row.createCell((short) 2).setCellValue(args[2].toString());

				}

				String name = "省内汇总统计";
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
			e.printStackTrace();
		}

	}

	public String paper() {

		// 省份userid
		if (session.getAttribute("unitUserId") == null) {

			String userId = request.getParameter("userid");
			session.setAttribute("unitUserId", userId);
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
					+ "where s.login_id='" + userId + "'";

			List li = peProApplyNoService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);
			}
		} else {
			String userId = request.getParameter("userid");
			String sessionUserId = session.getAttribute("unitUserId") == null ? "" : session.getAttribute("unitUserId").toString();
			if (!userId.equals(sessionUserId)) {
				session.setAttribute("unitUserId", userId);
				String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
						+ "where s.login_id='" + userId + "'";

				List li = peProApplyNoService.executeSQL(sql);

				if (li.size() > 0) {
					String unitId = li.get(0).toString();
					session.setAttribute("unitNormalId", unitId);
				}
			}
		}

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");

		String provin = session.getAttribute("unitNormalId").toString();

		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";
		
		sql2 += " order by t.parentname desc";
		sql3 += " order by t.name desc";
		sql4 += " order by pe.name desc";
		sql5 += " order by pe.name desc";

		this.projects = peProApplyNoService.executeSQL(sql2);
		this.subprojects = peProApplyNoService.executeSQL(sql3);
		this.subjects = peProApplyNoService.executeSQL(sql4);
		this.units = peProApplyNoService.executeSQL(sql5);

		Hashtable<Object, Integer> everyQuestionCount = new Hashtable<Object, Integer>();

		// 每个题的每个答案回答次数
		String sql = "select r.question,r.answer,count(*) as sum ";
		sql += " from result r join question q on r.question = q.id ";
		sql += " join loginkey l on r.loginkey = l.id ";
		sql += " join trainee t on l.traineeid  = t.id ";
		sql += " join psq p on p.id = q.psq ";
		sql += " join prochildapplyno pao on t.fk_applyno=pao.id ";

		String sqlQuestionCount = "select r.question,count(*) as sum";
		sqlQuestionCount += " from result r join question q on r.question = q.id ";
		sqlQuestionCount += " join loginkey l on r.loginkey = l.id ";
		sqlQuestionCount += " join trainee t on l.traineeid  = t.id ";
		sqlQuestionCount += " join psq p on p.id = q.psq ";
		sqlQuestionCount += " join prochildapplyno pao on t.fk_applyno=pao.id ";
		if (project != null && !project.equals("0") && !project.equals("")) {
			sql += " and pao.parentid= '" + project + "'";
			sqlQuestionCount += " and pao.parentid= '" + project + "'";
		} else {
			return "paper";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			sql += " and  t.fk_subject='" + subject + "'";
			sqlQuestionCount += " and  t.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			sql += " and t.fk_training_unit='" + unit + "'";
			sqlQuestionCount += " and t.fk_training_unit='" + unit + "'";
		}

		if (province != null && !province.equals("0") && !province.equals("")) {
			sql += " and pao.provinceid ='" + province + "'";
			sqlQuestionCount += " and pao.provinceid ='" + province + "'";
		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			sql += " and pao.id ='" + subproject + "'";
			sqlQuestionCount += " and pao.id ='" + subproject + "'";
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

		String psql = "select t.psq from PSQPROJECTMAP t where t.project='" + this.project + "'";
		List lpi = questionService.executeSQL(psql);
		int pid = 0;
		if (lpi.size() > 0) {
			pid = Integer.parseInt(lpi.get(0).toString());
		}

		this.paper = paperService.get(pid);

		return "paper";

	}

	public String duibi() {

		// 省份userid
		if (session.getAttribute("unitUserId") == null) {

			String userId = request.getParameter("userid");
			session.setAttribute("unitUserId", userId);
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
					+ "where s.login_id='" + userId + "'";

			List li = peProApplyNoService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);
			}
		} else {
			String userId = request.getParameter("userid");
			String sessionUserId = session.getAttribute("unitUserId") == null ? "" : session.getAttribute("unitUserId").toString();
			if (!userId.equals(sessionUserId)) {
				session.setAttribute("unitUserId", userId);
				String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
						+ "where s.login_id='" + userId + "'";

				List li = peProApplyNoService.executeSQL(sql);

				if (li.size() > 0) {
					String unitId = li.get(0).toString();
					session.setAttribute("unitNormalId", unitId);
				}
			}
		}

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");

		String provin = session.getAttribute("unitNormalId").toString();

		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";
		
		sql2 += " order by t.parentname desc";
		sql3 += " order by t.name desc";
		sql4 += " order by pe.name desc";
		sql5 += " order by pe.name desc";

		this.projects = peProApplyNoService.executeSQL(sql2);
		this.subprojects = peProApplyNoService.executeSQL(sql3);
		this.subjects = peProApplyNoService.executeSQL(sql4);
		this.units = peProApplyNoService.executeSQL(sql5);
		this.qid = request.getParameter("question");

		// String dbsql =
		// "select r.loginkey, q.inx as inx, q.name as qname, r.question, AVG(r.score) AS scoreavg "
		// + "from result r join answer a on r.answer = a.id " +
		// "join question q on r.question = q.id "
		// + " join loginkey l on r.loginkey = l.id " +
		// " join trainee t on l.traineeid = t.id "
		// + " join prochildapplyno pao on t.fk_applyno=pao.id" +
		// " where q.istongji = 1 ";

		String dbsql = "select p.parentname,pe.name as provincename,q.name as questionname,q.inx,pe.id,avg(r.score) as score"
				+ " from result r ,loginkey l,trainee t,question q,prochildapplyno p,pe_province pe "
				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_applyno=p.id and p.provinceid=pe.id and r.question=q.id  "
				+ " and q.istongji = 1 and q.type=2 ";

		if (project != null && !project.equals("0") && !project.equals("")) {
			dbsql += " and p.parentid= '" + project + "'";

		} else {
			return "duibi";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			dbsql += " and  t.fk_subject='" + subject + "'";

		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			dbsql += " and t.fk_training_unit='" + unit + "'";

		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			dbsql += " and p.id ='" + subproject + "'";
		}

		if (this.qid != null && !this.qid.equals("0") && !this.qid.equals("")) {
			dbsql += " and r.question = " + this.qid;
		}

		dbsql += " group by p.parentname, pe.name,q.name,q.inx, pe.id order by q.inx,score desc ";

		// String sql =
		// "select pao.parentname as project, prov.name as provincename,fz.qname,fz.inx,prov.id, AVG(fz.scoreavg) as score from loginkey l join trainee pt on l.traineeid = pt.id join ("
		// + dbsql
		// + ")fz on l.id = fz.loginkey"
		// + " join prochildapplyno pao on pao.id = pt.fk_applyno"
		// + " join pe_province prov on pao.provinceid=prov.id "
		// +
		// " GROUP BY pao.parentname,prov.name,prov.id,fz.qname,fz.inx order by fz.inx,score desc";

		List questionCounts = resultService.executeSQL(dbsql);
		for (int i = 0; i < questionCounts.size(); i++) {
			Object[] objects = (Object[]) questionCounts.get(i);

			float f = Float.parseFloat(objects[5].toString());

			int flag = objects[4].toString().equals(provin) ? 1 : 0;

			DecimalFormat df = new DecimalFormat();
			String style = "0.000";
			df.applyPattern(style);
			Object[] data = { objects[0], objects[1], objects[2], objects[3], df.format(f), flag };
			this.duibiCountMap.add(data);
		}
		String psql = "select t.psq from PSQPROJECTMAP t where t.project='" + this.project + "'";
		List lpi = questionService.executeSQL(psql);
		int pid = 0;
		if (lpi.size() > 0) {
			pid = Integer.parseInt(lpi.get(0).toString());
		}

		// this.paper = paperService.get(pid);

		this.questions = questionService.getListByHSQL("from Question where paper =" + pid + " and type =2 and istongji=1 order by inx");

		return "duibi";
	}

	public String duibip() {

		// 省份userid
		if (session.getAttribute("unitUserId") == null) {

			String userId = request.getParameter("userid");
			session.setAttribute("unitUserId", userId);
			String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
					+ "where s.login_id='" + userId + "'";

			List li = peProApplyNoService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);
			}
		} else {
			String userId = request.getParameter("userid");
			String sessionUserId = session.getAttribute("unitUserId") == null ? "" : session.getAttribute("unitUserId").toString();
			if (!userId.equals(sessionUserId)) {
				session.setAttribute("unitUserId", userId);
				String sql = "select u.fk_province from sso_user s join pe_manager p on s.login_id = p.login_id join pe_unit u on p.fk_unit=u.id "
						+ "where s.login_id='" + userId + "'";

				List li = peProApplyNoService.executeSQL(sql);

				if (li.size() > 0) {
					String unitId = li.get(0).toString();
					session.setAttribute("unitNormalId", unitId);
				}
			}
		}

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");

		String provin = session.getAttribute("unitNormalId").toString();

		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.provinceid='" + provin + "' ";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where provinceid='" + provin + "')";
		
		sql2 += " order by t.parentname desc";
		sql3 += " order by t.name desc";
		sql4 += " order by pe.name desc";
		sql5 += " order by pe.name desc";

		this.projects = peProApplyNoService.executeSQL(sql2);
		this.subprojects = peProApplyNoService.executeSQL(sql3);
		this.subjects = peProApplyNoService.executeSQL(sql4);
		this.units = peProApplyNoService.executeSQL(sql5);
		this.qid = request.getParameter("question");

		// String dbsql =
		// "select r.loginkey, q.inx as inx, q.name as qname, r.question, AVG(r.score) AS scoreavg "
		// + "from result r join answer a on r.answer = a.id " +
		// "join question q on r.question = q.id "
		// + " join loginkey l on r.loginkey = l.id " +
		// " join trainee t on l.traineeid = t.id "
		// + " join prochildapplyno pao on t.fk_applyno=pao.id" +
		// " where q.istongji = 1 and pao.provinceid='" + provin + "' ";

		String dbsql = "select p.parentname,u.name as unitname,q.name as questionname,q.inx,avg(r.score) as score "
				+ " from result r ,loginkey l,trainee t,question q,prochildapplyno p,pe_province pe,pe_unit u "
				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_applyno=p.id and p.provinceid=pe.id and r.question=q.id and t.fk_training_unit=u.id "
				+ " and q.istongji = 1 and q.type=2 and p.provinceid='" + provin + "' ";

		if (project != null && !project.equals("0") && !project.equals("")) {
			dbsql += " and p.parentid= '" + project + "'";

		} else {
			return "duibip";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			dbsql += " and  t.fk_subject='" + subject + "'";

		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			dbsql += " and t.fk_training_unit='" + unit + "'";
		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			dbsql += " and p.id ='" + subproject + "'";
		}

		if (this.qid != null && !this.qid.equals("0") && !this.qid.equals("")) {
			dbsql += " and r.question = " + this.qid;
		}

		dbsql += " group by p.parentname, u.name,q.name,q.inx order by q.inx,score desc";

		// String sql =
		// "select pao.parentname as project, pu.name as provincename,fz.qname,fz.inx, AVG(fz.scoreavg) as score from loginkey l join trainee pt on l.traineeid = pt.id join ("
		// + dbsql
		// +
		// ")fz on l.id = fz.loginkey join pe_unit pu on pu.id = pt.fk_training_unit "
		// + " join prochildapplyno pao on pao.id = pt.fk_applyno"
		// + " join pe_province prov on pao.provinceid=prov.id "
		// +
		// " GROUP BY pao.parentname,pu.name,fz.qname,fz.inx order by fz.inx,score desc";

		List questionCounts = resultService.executeSQL(dbsql);
		for (int i = 0; i < questionCounts.size(); i++) {
			Object[] objects = (Object[]) questionCounts.get(i);

			float f = Float.parseFloat(objects[4].toString());

			DecimalFormat df = new DecimalFormat();
			String style = "0.000";
			df.applyPattern(style);
			Object[] data = { objects[0], objects[1], objects[2], objects[3], df.format(f) };
			this.duibiCountMap.add(data);
		}

		session.setAttribute("duibiMap", this.duibiCountMap);

		String psql = "select t.psq from PSQPROJECTMAP t where t.project='" + this.project + "'";
		List lpi = questionService.executeSQL(psql);
		int pid = 0;
		if (lpi.size() > 0) {
			pid = Integer.parseInt(lpi.get(0).toString());
		}

		// this.paper = paperService.get(pid);

		this.questions = questionService.getListByHSQL("from Question where paper =" + pid + " and type =2 and istongji=1 order by inx");

		return "duibip";
	}

	public void duibiDoExcel() {
		try {

			List li = (List) session.getAttribute("duibiMap");

			if (li != null) {

				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet1 = wb.createSheet("sheet1");
				int i = 0;
				HSSFRow rowheader = sheet1.createRow((short) i);
				i++;

				rowheader.createCell((short) 0).setCellValue("项目名称");
				rowheader.createCell((short) 1).setCellValue("培训单位");
				rowheader.createCell((short) 2).setCellValue("总体满意度");

				int flag = 0;

				for (int j = 0; j < li.size(); j++) {

					Object[] obj = (Object[]) li.get(j);

					int inx = Integer.parseInt(obj[3].toString());
					if (inx != flag) {

						HSSFRow rowq = sheet1.createRow((short) i);
						rowq.createCell(0).setCellValue("第" + inx + "题:" + obj[2].toString());
						sheet1.addMergedRegion(new Region((short) i, (short) 0, (short) i, (short) 2));
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

				String name = "省内对比统计";
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
			e.printStackTrace();
		}
	}

}
