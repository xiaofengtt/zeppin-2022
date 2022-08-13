package com.zeppin.action;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.zeppin.model.Paper;
import com.zeppin.model.Question;
import com.zeppin.model.Result;
import com.zeppin.model.Submit;
import com.zeppin.service.PaperService;
import com.zeppin.service.PsqProjectMapService;
import com.zeppin.service.QuestionService;
import com.zeppin.service.ResultService;
import com.zeppin.service.SubmitService;
import com.zeppin.service.impl.peProApplyNoServiceImpl;
import com.zeppin.util.PageInfo;
import com.opensymphony.xwork2.ActionSupport;

@Controller("ReportAction")
@Scope("prototype")
public class ReportAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(ReportAction.class);

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

	@Autowired
	private peProApplyNoServiceImpl peProApplynoService;

	private List<Submit> submits;
	private List<Result> results;

	private List lisubmits;

	public List getLisubmits() {
		return lisubmits;
	}

	public void setLisubmits(List lisubmits) {
		this.lisubmits = lisubmits;
	}

	private Submit submit;

	private PageInfo pageInfo;
	private Paper paper;

	private List subjects;
	private List projects;
	private List units;
	private List subprojects;
	private List provinces;

	public List getProvinces() {
		return provinces;
	}

	public void setProvinces(List provinces) {
		this.provinces = provinces;
	}

	public List getSubprojects() {
		return subprojects;
	}

	public void setSubprojects(List subprojects) {
		this.subprojects = subprojects;
	}

	private List<Question> questions;

	private String subject;
	private String project;
	private String unit;
	private String qid;
	private String subproject;
	private String province;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSubproject() {
		return subproject;
	}

	public void setSubproject(String subproject) {
		this.subproject = subproject;
	}

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

	public ReportAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
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
		this.subproject = request.getParameter("subproject");
		this.province = request.getParameter("province");

		session.setAttribute("summaryPid", spid);
		session.setAttribute("summaryProject", this.project);
		session.setAttribute("summarySubject", this.subject);
		session.setAttribute("summaryUnit", this.unit);
		session.setAttribute("summarySubProject", this.subproject);
		session.setAttribute("summaryProvince", this.province);

		int pageId = 1;
		if (page != null)
			pageId = Integer.parseInt(page);
		int pageSize = 20;

		String sql = "";

		// 拼装sql语句
		String sqlcount = "select count(*) from submit s";
		sql = "select s.id,s.loginkey,to_char(s.creattime,'yyyy-MM-dd HH24:mi:ss') as createtime,s.ip,pro.name as project,peu.name as unit ,pes.name as subject from submit s";
		sql += " join loginkey log on s.loginkey = log.id join trainee te on log.traineeid=te.id join prochildapplyno pro on te.fk_applyno = pro.id ";
		sql += " join pe_unit peu on te.fk_training_unit=peu.id join pe_subject pes on te.fk_subject=pes.id ";
		sql += " where s.psq=" + spid;
		sqlcount += " where s.psq=" + spid;
		sql += " and s.loginkey in (select l.id from loginkey l join trainee t on l.traineeid = t.id join prochildapplyno pao on t.fk_applyno = pao.id where 1=1";
		sqlcount += " and s.loginkey in (select l.id from loginkey l join trainee t on l.traineeid = t.id join prochildapplyno pao on t.fk_applyno = pao.id where 1=1";

		if (project != null && !project.equals("0") && !project.equals("")) {
			sql += " and pao.parentid= '" + project + "'";
			sqlcount += " and pao.parentid= '" + project + "'";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			sql += " and  t.fk_subject='" + subject + "'";
			sqlcount += " and  t.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			sql += " and t.fk_training_unit='" + unit + "'";
			sqlcount += " and t.fk_training_unit='" + unit + "'";
		}

		if (province != null && !province.equals("0") && !province.equals("")) {
			sql += " and pao.provinceid ='" + province + "'";
			sqlcount += " and pao.provinceid ='" + province + "'";
		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			sql += " and pao.id ='" + subproject + "'";
			sqlcount += " and pao.id ='" + subproject + "'";
		}

		sql += ")";
		sqlcount += ")";

		sql += " order by createtime desc";
		sqlcount += " order by creattime desc";

		int totalRow = Integer.parseInt(peProApplynoService.executeSQL(sqlcount).get(0).toString());

		this.lisubmits = peProApplynoService.getListPage(sql, pageId, pageSize);

		this.pageInfo = new PageInfo(totalRow, pageSize, pageId);

		this.paper = paperService.get(pid);
		String sql1 = "select distinct t.provinceid,p.name from PROCHILDAPPLYNO t join PE_PROVINCE p on t.provinceid = p.id where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";
		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq=" + pid
				+ ")";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		// 字典信息
		this.provinces = peProApplynoService.executeSQL(sql1);
		this.projects = peProApplynoService.executeSQL(sql2);
		this.subprojects = peProApplynoService.executeSQL(sql3);
		this.subjects = peProApplynoService.executeSQL(sql4);
		this.units = peProApplynoService.executeSQL(sql5);

		return "summary";
	}

	public String showAllAdvice() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");
		this.province = request.getParameter("province");

		String sql = "select  r.content from result r,question q,psq p,loginkey l,trainee pe ,prochildapplyno pao "
				+ " WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and pe.fk_applyno = pao.id and r.question = q.id AND q.psq = p.id AND q.type=6 and p.id="
				+ spid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			sql += " and pao.parentid= '" + project + "'";

		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			sql += " and  pe.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			sql += " and pe.fk_training_unit='" + unit + "'";
		}

		if (province != null && !province.equals("0") && !province.equals("")) {
			sql += " and pao.provinceid ='" + province + "'";
		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			sql += " and pao.id ='" + subproject + "'";
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
		String sql = "SELECT pao.name as project , ps.name as SUBJECT ,pu.name as unit,l.id "
				+ " FROM loginkey l,trainee pe,pe_unit pu,pe_subject ps,prochildapplyno pao "
				+ " WHERE l.traineeid = pe.id and pe.fk_subject = ps.id and pe.fk_training_unit=pu.id and pe.fk_applyno = pao.id and l.id in (SELECT DISTINCT loginkey FROM submit WHERE psq ="
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

		try {

			String spid = session.getAttribute("summaryPid").toString();
			String project = session.getAttribute("summaryProject") == null ? "" : session.getAttribute("summaryProject").toString();
			String subject = session.getAttribute("summarySubject") == null ? "" : session.getAttribute("summarySubject").toString();
			String unit = session.getAttribute("summaryUnit") == null ? "" : session.getAttribute("summaryUnit").toString();
			String subproject = session.getAttribute("summarySubProject") == null ? "" : session.getAttribute("summarySubProject").toString();
			String province = session.getAttribute("summaryProvince") == null ? "" : session.getAttribute("summaryProvince").toString();

			String sql = "select r.loginkey,s.ip,s.creattime ,q.inx as qinx,a.inx as ainx,q.type,r.content as rcontent"
					+ " from submit s,result r,question q ,answer a,loginkey l ,trainee pe ,prochildapplyno pao "
					+ "where s.loginkey = r.loginkey and r.question = q.id and r.answer = a.id and s.loginkey=l.id and l.traineeid=pe.id and pe.fk_applyno=pao.id and  "
					+ " s.psq=" + spid;

			if (project != null && !project.equals("0") && !project.equals("")) {
				sql += " and pao.parentid= '" + project + "'";
			}

			if (subject != null && !subject.equals("0") && !subject.equals("")) {
				sql += " and  pe.fk_subject='" + subject + "'";
			}

			if (unit != null && !unit.equals("0") && !unit.equals("")) {
				sql += " and pe.fk_training_unit='" + unit + "'";
			}

			if (province != null && !province.equals("0") && !province.equals("")) {
				sql += " and pao.provinceid ='" + province + "'";
			}

			if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
				sql += " and pao.id ='" + subproject + "'";
			}

			List li = questionService.executeSQL(sql);

			String headSql = "select q.inx,q.type from question q where  q.type!=0 and q.psq=" + spid + " order by q.inx";

			List hli = questionService.executeSQL(headSql);

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
		this.subproject = request.getParameter("subproject");
		this.province = request.getParameter("province");

		Hashtable<Object, Integer> everyQuestionCount = new Hashtable<Object, Integer>();

		// 每个题的每个答案回答次数
		String sql = "select r.question,r.answer,count(*) as sum ";
		sql += " from result r join question q on r.question = q.id ";
		sql += " join loginkey l on r.loginkey = l.id ";
		sql += " join trainee t on l.traineeid  = t.id ";
		sql += " join psq p on p.id = q.psq ";
		sql += " join prochildapplyno pao on t.fk_applyno=pao.id ";
		sql += " where p.id =" + pid;

		String sqlQuestionCount = "select r.question,count(*) as sum";
		sqlQuestionCount += " from result r join question q on r.question = q.id ";
		sqlQuestionCount += " join loginkey l on r.loginkey = l.id ";
		sqlQuestionCount += " join trainee t on l.traineeid  = t.id ";
		sqlQuestionCount += " join psq p on p.id = q.psq ";
		sqlQuestionCount += " join prochildapplyno pao on t.fk_applyno=pao.id ";
		sqlQuestionCount += " where p.id =" + pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			sql += " and pao.parentid= '" + project + "'";
			sqlQuestionCount += " and pao.parentid= '" + project + "'";
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
			sql += " and pao.provinceid ='" + subproject + "'";
			sqlQuestionCount += " and pao.provinceid ='" + subproject + "'";
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

		String sql1 = "select distinct t.provinceid,p.name from PROCHILDAPPLYNO t join PE_PROVINCE p on t.provinceid = p.id where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";
		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq=" + pid
				+ ")";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		// 字典信息
		this.provinces = peProApplynoService.executeSQL(sql1);
		this.projects = peProApplynoService.executeSQL(sql2);
		this.subprojects = peProApplynoService.executeSQL(sql3);
		this.subjects = peProApplynoService.executeSQL(sql4);
		this.units = peProApplynoService.executeSQL(sql5);

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

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");
		this.province = request.getParameter("province");

		// String hzsql = "select r.loginkey,AVG(r.score) as score " +
		// " from result r join question q on r.question = q.id "
		// + " join loginkey l on r.loginkey = l.id " +
		// " join trainee t on l.traineeid  = t.id " +
		// " join psq p on p.id = q.psq "
		// + " join prochildapplyno pao on t.fk_applyno=pao.id " +
		// " where q.istongji = 1 and q.type=2 and  p.id =" + pid;

		String hzsql = "select p.parentname as project, pe.name,AVG(r.score) as score "
				+ " from result r ,loginkey l,trainee t,question q,prochildapplyno p,pe_province pe "
				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_applyno=p.id and p.provinceid=pe.id "
				+ " and r.question=q.id and q.istongji = 1 and q.type=2 and  q.psq =" + pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			hzsql += " and p.parentid= '" + project + "'";
		} else {
			hzsql += " and p.parentid in (select project from psqprojectmap where psq=" + pid + ") ";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			hzsql += " and  t.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			hzsql += " and t.fk_training_unit='" + unit + "'";
		}

		if (province != null && !province.equals("0") && !province.equals("")) {
			hzsql += " and p.provinceid ='" + province + "'";
		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			hzsql += " and p.id ='" + subproject + "'";
		}

		hzsql += " group by p.parentname, pe.name order by score desc ";

		// hzsql += " GROUP BY loginkey order by score desc";
		//
		// String sql =
		// "select pao.parentname as project, prov.name ,AVG(fz.score) as score from loginkey l join trainee pt on l.traineeid = pt.id join("
		// + hzsql
		// +
		// ") fz on l.id = fz.loginkey join pe_unit pu on pu.id = pt.fk_training_unit"
		// + " join prochildapplyno pao on pao.id = pt.fk_applyno "
		// + " join pe_province prov on pao.provinceid = prov.id "
		// + "GROUP BY pao.parentname,prov.name order by score desc";

		List avgCounts = resultService.executeSQL(hzsql);

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

		String sql1 = "select distinct t.provinceid,p.name from PROCHILDAPPLYNO t join PE_PROVINCE p on t.provinceid = p.id where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";
		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq=" + pid
				+ ")";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		// 字典信息
		this.provinces = peProApplynoService.executeSQL(sql1);
		this.projects = peProApplynoService.executeSQL(sql2);
		this.subprojects = peProApplynoService.executeSQL(sql3);
		this.subjects = peProApplynoService.executeSQL(sql4);
		this.units = peProApplynoService.executeSQL(sql5);

		return "huizong";
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
				rowheader.createCell((short) 1).setCellValue("省份");
				rowheader.createCell((short) 2).setCellValue("总体满意度");

				for (int j = 0; j < huizong.size(); j++) {

					Object[] args = (Object[]) huizong.get(j);
					HSSFRow row = sheet1.createRow((short) i);
					i++;
					row.createCell((short) 0).setCellValue(args[0].toString());
					row.createCell((short) 1).setCellValue(args[1].toString());
					row.createCell((short) 2).setCellValue(args[2].toString());

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

		} catch (Exception e) {
		}

	}

	public void huizongDoAllExcel() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		String hzsql = "select p.parentname as project, pe.name as provincename,p.name as projectname ,s.name as subject ,u.name as unitname,AVG(r.score) as score "
				+ " from result r ,loginkey l,trainee t,question q,prochildapplyno p,pe_province pe,pe_subject s,pe_unit u "
				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_applyno=p.id and p.provinceid=pe.id and t.fk_training_unit=u.id and t.fk_subject=s.id"
				+ " and r.question=q.id and q.istongji = 1 and q.type=2 and  q.psq =" + pid + " group by p.parentname,pe.name,p.name,s.name,u.name";

		List li = peProApplynoService.executeSQL(hzsql);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		int i = 0;
		HSSFRow rowheader = sheet1.createRow((short) i);
		i++;

		rowheader.createCell((short) 0).setCellValue("项目名称");
		rowheader.createCell((short) 1).setCellValue("省份");
		rowheader.createCell((short) 2).setCellValue("子项目");
		rowheader.createCell((short) 3).setCellValue("学科");
		rowheader.createCell((short) 4).setCellValue("承训单位");
		rowheader.createCell((short) 5).setCellValue("满意度");

		DecimalFormat df = new DecimalFormat();
		String style = "0.000";
		df.applyPattern(style);

		for (int k = 0; k < li.size(); k++) {

			Object[] args = (Object[]) li.get(k);
			HSSFRow row = sheet1.createRow((short) i);
			i++;

			float f = Float.parseFloat(args[5].toString());

			row.createCell((short) 0).setCellValue(args[0].toString());
			row.createCell((short) 1).setCellValue(args[1].toString());
			row.createCell((short) 2).setCellValue(args[2].toString());
			row.createCell((short) 3).setCellValue(args[3].toString());
			row.createCell((short) 4).setCellValue(args[4].toString());
			row.createCell((short) 5).setCellValue(df.format(f));

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

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");
		this.province = request.getParameter("province");
		this.qid = request.getParameter("question");

		// String dbsql1 =
		// "select r.loginkey, q.inx as inx, q.name as qname, r.question, AVG(r.score) AS scoreavg "
		// + "from result r join answer a on r.answer = a.id " +
		// "join question q on r.question = q.id "
		// + " join loginkey l on r.loginkey = l.id " +
		// " join trainee t on l.traineeid = t.id "
		// + " join prochildapplyno pao on t.fk_applyno=pao.id" +
		// " where q.istongji = 1 and a.psq =" + pid;

		String dbsql = "select p.parentname,pe.name as provincename,q.name as questionname,q.inx,avg(r.score) as score"
				+ " from result r ,loginkey l,trainee t,question q,prochildapplyno p,pe_province pe "
				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_applyno=p.id and p.provinceid=pe.id and r.question=q.id  "
				+ " and q.istongji = 1 and q.type=2 and q.psq =" + pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			dbsql += " and p.parentid= '" + project + "'";

		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			dbsql += " and  t.fk_subject='" + subject + "'";

		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			dbsql += " and t.fk_training_unit='" + unit + "'";

		}

		if (province != null && !province.equals("0") && !province.equals("")) {
			dbsql += " and p.provinceid ='" + province + "'";

		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			dbsql += " and p.id ='" + subproject + "'";
		}

		if (this.qid != null && !this.qid.equals("0") && !this.qid.equals("")) {
			dbsql += " and r.question = " + this.qid;
		}

		dbsql += " group by p.parentname, pe.name,q.name,q.inx order by q.inx,score desc ";

		// dbsql +=
		// "   GROUP BY  r.LOGINKEY , r.QUESTION, q.inx, q.name ORDER BY q.inx, scoreavg desc";
		//
		// String sql =
		// "select pao.parentname as project, prov.name as provincename ,fz.qname,fz.inx, AVG(fz.scoreavg) as score from loginkey l join trainee pt on l.traineeid = pt.id join ("
		// + dbsql
		// + ")fz on l.id = fz.loginkey"
		// + " join prochildapplyno pao on pao.id = pt.fk_applyno"
		// + " join pe_province prov on pao.provinceid=prov.id "
		// +
		// " GROUP BY pao.parentname,prov.name,fz.qname,fz.inx order by fz.inx,score desc";

		String questionSql = "select q.name,q.inx,avg(r.score) as score from result r,question q where r.question = q.id and q.istongji=1 and q.type=2 and q.psq="
				+ pid + " group by q.name,q.inx order by  q.inx";

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

		List questionCounts = resultService.executeSQL(dbsql);
		for (int i = 0; i < questionCounts.size(); i++) {
			Object[] objects = (Object[]) questionCounts.get(i);

			float f = Float.parseFloat(objects[4].toString());
			Object[] data = { objects[0], objects[1], objects[2], objects[3], df.format(f) };
			this.duibiCountMap.add(data);
		}

		session.setAttribute("duibiMap", this.duibiCountMap);

		this.paper = paperService.get(pid);

		String sql1 = "select distinct t.provinceid,p.name from PROCHILDAPPLYNO t join PE_PROVINCE p on t.provinceid = p.id where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";
		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq=" + pid
				+ ")";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		// 字典信息
		this.provinces = peProApplynoService.executeSQL(sql1);
		this.projects = peProApplynoService.executeSQL(sql2);
		this.subprojects = peProApplynoService.executeSQL(sql3);
		this.subjects = peProApplynoService.executeSQL(sql4);
		this.units = peProApplynoService.executeSQL(sql5);

		this.questions = questionService.getListByHSQL("from Question where paper =" + pid + " and type =2 and istongji=1");

		return "duibi";
	}

	public void duibiDoExcel() {
		try {

			List li = (List) session.getAttribute("duibiMap");
			Hashtable<String, String> hst = (Hashtable<String, String>) session.getAttribute("duibiAvgMap");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("sheet1");
			int i = 0;
			HSSFRow rowheader = sheet1.createRow((short) i);
			i++;

			rowheader.createCell((short) 0).setCellValue("项目名称");
			rowheader.createCell((short) 1).setCellValue("省份");
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

			String name = "duibi";
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
				e.printStackTrace();
			}
		}
		this.project = request.getParameter("project");
		this.province = request.getParameter("province");

		String sqlString = "select po.id,po.name from psqprojectmap pm join pe_pro_applyno po  on pm.project=po.id and pm.psq=" + pid;
		projectes = submitService.executeSQL(sqlString);

		String sql1 = "select distinct t.provinceid,p.name from PROCHILDAPPLYNO t join PE_PROVINCE p on t.provinceid = p.id where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";
		this.provinces = peProApplynoService.executeSQL(sql1);

		String sql = "select pao.parentname, prov.name, l.status,l.psqstatus,count(*) ";
		sql += " from loginkey l join trainee t on l.traineeid=t.id ";
		sql += " join prochildapplyno pao on t.fk_applyno = pao.id ";
		sql += " join pe_province prov on pao.provinceid = prov.id ";
		sql += " where pao.parentid in (select project from psqprojectmap where psq = " + pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			sql += " and project= '" + project + "'";
		}
		sql += ")";

		if (province != null && !province.equals("0") && !province.equals("")) {
			sql += " and pao.provinceid ='" + province + "'";

		}

		sql += " group by pao.parentname, prov.name, l.status,l.psqstatus";
		sql += " order by l.status,l.psqstatus";

		List groupList = peProApplynoService.executeSQL(sql);

		Hashtable<String, Hashtable<String, int[]>> ht = new Hashtable<String, Hashtable<String, int[]>>();

		for (int i = 0; i < groupList.size(); i++) {
			Object[] obj = (Object[]) groupList.get(i);
			String pro = obj[0].toString();
			String province = obj[1].toString();
			String status = obj[2].toString();
			String psqstatus = obj[3].toString();
			String count = obj[4].toString();

			if (ht.containsKey(pro)) {

				Hashtable<String, int[]> ht1 = ht.get(pro);

				if (ht1.containsKey(province)) {

					int[] ci = ht1.get(province);
					if (status.equals("1")) {
						// 已经发送的
						if (psqstatus.equals("1")) {
							// 已经填写的
							ci[0] += Integer.parseInt(count);
						}
						ci[1] += Integer.parseInt(count);
					}
					ci[2] += Integer.parseInt(count);

				} else {
					int[] ci = new int[3];
					if (status.equals("1")) {
						// 已经发送的
						if (psqstatus.equals("1")) {
							// 已经填写的
							ci[0] = Integer.parseInt(count);
						}
						ci[1] = Integer.parseInt(count);
					}
					ci[2] = Integer.parseInt(count);
					ht1.put(province, ci);
				}

			} else {
				Hashtable<String, int[]> ht1 = new Hashtable<String, int[]>();
				int[] ci = new int[3];
				if (status.equals("1")) {
					// 已经发送的

					if (psqstatus.equals("1")) {
						// 已经填写的
						ci[0] = Integer.parseInt(count);
					}

					ci[1] = Integer.parseInt(count);

				}
				ci[2] = Integer.parseInt(count);

				ht1.put(province, ci);
				ht.put(pro, ht1);

			}

		}

		Enumeration et = ht.keys();
		int countyi = 0;
		int countyt = 0;
		DecimalFormat dFormat = new DecimalFormat("0.00");

		while (et.hasMoreElements()) {
			String key = et.nextElement().toString();
			Hashtable<String, int[]> value = ht.get(key);

			Enumeration et1 = value.keys();
			while (et1.hasMoreElements()) {
				String key1 = et1.nextElement().toString();
				int[] ci = value.get(key1);

				int yi = ci[0];
				int ys = ci[1];
				int yt = ci[2];
				countyi += yi;
				countyt += ys;
				this.countVotesum += yi;

				double dl = 0.00;
				try {
					if (ys == 0 || yi == 0) {
						dl = 0.00;
					} else {
						dl = ((double) yi / ys) * 100;
					}
				} catch (Exception e) {
					e.printStackTrace();
					dl = 0.00;
				}
				String[] as = new String[] { key, key1, String.valueOf(yi), String.valueOf(ys), String.valueOf(yt), dFormat.format(dl) };
				this.lstInfo.add(as);
			}
		}

		class compareObject implements Comparator {

			@Override
			public int compare(Object o1, Object o2) {
				try {
					String[] temp1 = (String[]) o1;
					String[] temp2 = (String[]) o2;
					int flag = 0;
					if (Double.parseDouble(temp1[5]) > Double.parseDouble(temp2[5])) {
						flag = 1;
					} else if (Double.parseDouble(temp1[5]) < Double.parseDouble(temp2[5])) {
						flag = -1;
					}
					return (flag);
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}

			}

		}

		if (countyi > 0) {
			this.wclPercent = dFormat.format(((double) countyi / countyt) * 100);
		} else {
			this.wclPercent = "0.00";
		}

		compareObject co = new compareObject();
		Collections.sort(lstInfo, co);

		session.setAttribute("wanchenglvList", lstInfo);

		return "wanchenglv";

	}

	public void chouyanlv() {

		try {

			String spid = request.getParameter("pid");
			int pid = Integer.parseInt(spid);

			String sql = "select pao.parentname, prov.name as provincename,pao.name as projectname,s.name subjectname,u.name unitname ,l.status,l.psqstatus,count(*) "
					+ " from loginkey l join trainee t on l.traineeid=t.id "
					+ " join prochildapplyno pao on t.fk_applyno = pao.id "
					+ " join pe_province prov on pao.provinceid = prov.id "
					+ " join pe_subject s on t.fk_subject=s.id "
					+ "join pe_unit u on t.fk_training_unit=u.id "
					+ " where pao.parentid in (select project from psqprojectmap where psq = "
					+ pid
					+ ")" + " group by pao.parentname, prov.name,pao.name,s.name,u.name, l.status,l.psqstatus " + " order by l.status,l.psqstatus ";

			List groupList = peProApplynoService.executeSQL(sql);
			Hashtable<String, int[]> ht = new Hashtable<String, int[]>();

			for (int i = 0; i < groupList.size(); i++) {
				Object[] obj = (Object[]) groupList.get(i);
				String parentname = obj[0].toString();
				String province = obj[1].toString();
				String projectname = obj[2].toString();
				String subjectname = obj[3].toString();
				String unitname = obj[4].toString();
				String status = obj[5].toString();
				String psqstatus = obj[6].toString();
				String count = obj[7].toString();

				String key = parentname + "〒" + province + "〒" + projectname + "〒" + subjectname + "〒" + unitname;

				if (ht.containsKey(key)) {

					int[] ci = ht.get(key);
					if (status.equals("1")) {
						// 已经发送的
						if (psqstatus.equals("1")) {
							// 已经填写的
							ci[0] += Integer.parseInt(count);
						}
						ci[1] += Integer.parseInt(count);
					}
					ci[2] += Integer.parseInt(count);

				} else {
					int[] ci = new int[3];

					if (status.equals("1")) {
						// 已经发送的
						if (psqstatus.equals("1")) {
							// 已经填写的
							ci[0] = Integer.parseInt(count);
						}
						ci[1] = Integer.parseInt(count);
					}
					ci[2] = Integer.parseInt(count);

					ht.put(key, ci);
				}

			}

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("sheet1");
			int i = 0;
			HSSFRow rowheader = sheet1.createRow((short) i);
			i++;

			rowheader.createCell((short) 0).setCellValue("项目名称");
			rowheader.createCell((short) 1).setCellValue("省份");
			rowheader.createCell((short) 2).setCellValue("子项目");
			rowheader.createCell((short) 3).setCellValue("学科");
			rowheader.createCell((short) 4).setCellValue("培训单位");
			rowheader.createCell((short) 5).setCellValue("投票人数");
			rowheader.createCell((short) 6).setCellValue("已发短信人数");
			rowheader.createCell((short) 7).setCellValue("已经结业人数");
			rowheader.createCell((short) 8).setCellValue("投票完成比例(%)");

			Enumeration et = ht.keys();
			DecimalFormat dFormat = new DecimalFormat("0.00");

			while (et.hasMoreElements()) {

				String key = et.nextElement().toString();
				int[] ci = ht.get(key);

				int yi = ci[0];
				int ys = ci[1];
				int yt = ci[2];

				double dl = 0.00;
				try {
					if (ys == 0 || yi == 0) {
						dl = 0.00;
					} else {
						dl = ((double) yi / ys) * 100;
					}
				} catch (Exception e) {
					e.printStackTrace();
					dl = 0.00;
				}

				String[] vls = key.split("〒");
				String parentname = vls[0].toString();
				String province = vls[1].toString();
				String projectname = vls[2].toString();
				String subjectname = vls[3].toString();
				String unitname = vls[4].toString();
				String cyl = dFormat.format(dl);

				HSSFRow row = sheet1.createRow((short) i);
				i++;

				row.createCell((short) 0).setCellValue(parentname);
				row.createCell((short) 1).setCellValue(province);
				row.createCell((short) 2).setCellValue(projectname);
				row.createCell((short) 3).setCellValue(subjectname);
				row.createCell((short) 4).setCellValue(unitname);
				row.createCell((short) 5).setCellValue(yi);
				row.createCell((short) 6).setCellValue(ys);
				row.createCell((short) 7).setCellValue(yt);
				row.createCell((short) 8).setCellValue(cyl);

			}

			String name = "详细抽样率";
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void wanchenglvDoExcel() {

		try {

			List<String[]> lsinfo = (List<String[]>) session.getAttribute("wanchenglvList");

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("sheet1");
			int i = 0;
			HSSFRow rowheader = sheet1.createRow((short) i);
			i++;

			rowheader.createCell((short) 0).setCellValue("项目名称");
			rowheader.createCell((short) 1).setCellValue("省份");
			rowheader.createCell((short) 2).setCellValue("投票人数");
			rowheader.createCell((short) 3).setCellValue("已发短信人数");
			rowheader.createCell((short) 4).setCellValue("已经结业人数");
			rowheader.createCell((short) 5).setCellValue("总人数");
			rowheader.createCell((short) 6).setCellValue("投票完成比例(%)");

			for (int j = 0; j < lsinfo.size(); j++) {

				String[] args = lsinfo.get(j);
				HSSFRow row = sheet1.createRow((short) i);
				i++;
				row.createCell((short) 0).setCellValue(args[0]);
				row.createCell((short) 1).setCellValue(args[1]);
				row.createCell((short) 2).setCellValue(args[2]);
				row.createCell((short) 3).setCellValue(args[3]);
				row.createCell((short) 4).setCellValue(args[4]);
				row.createCell((short) 5).setCellValue(args[4]);
				row.createCell((short) 6).setCellValue(args[5]);

			}

			String name = "wanchenglv";
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

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 满意率
	 * @return
	 */
	public String manyilv() {

		String spid = request.getParameter("pid");
		int pid = Integer.parseInt(spid);

		this.project = request.getParameter("project");
		this.subject = request.getParameter("subject");
		this.unit = request.getParameter("unit");
		this.subproject = request.getParameter("subproject");
		this.province = request.getParameter("province");

		String hzsql = "select r.loginkey as id, count(r.score) as jscore" + " from result r join question q on r.question = q.id "
				+ " join loginkey l on r.loginkey = l.id " + " join trainee t on l.traineeid  = t.id " + " join psq p on p.id = q.psq "
				+ " join prochildapplyno pao on t.fk_applyno=pao.id " + " where q.istongji = 1 and q.type=2 and r.score>=4 and  p.id =" + pid;

		String htsql = "select r.loginkey as id, count(r.score) as tscore" + " from result r join question q on r.question = q.id "
				+ " join loginkey l on r.loginkey = l.id " + " join trainee t on l.traineeid  = t.id " + " join psq p on p.id = q.psq "
				+ " join prochildapplyno pao on t.fk_applyno=pao.id " + " where q.istongji = 1 and q.type=2 and  p.id =" + pid;

		if (project != null && !project.equals("0") && !project.equals("")) {
			hzsql += " and pao.parentid= '" + project + "'";
			htsql += " and pao.parentid= '" + project + "'";
		} else {
			hzsql += " and pao.parentid in (select project from psqprojectmap where psq=" + pid + ") ";
			htsql += " and pao.parentid in (select project from psqprojectmap where psq=" + pid + ") ";
		}

		if (subject != null && !subject.equals("0") && !subject.equals("")) {
			hzsql += " and  t.fk_subject='" + subject + "'";
			htsql += " and  t.fk_subject='" + subject + "'";
		}

		if (unit != null && !unit.equals("0") && !unit.equals("")) {
			hzsql += " and t.fk_training_unit='" + unit + "'";
			htsql += " and t.fk_training_unit='" + unit + "'";
		}

		if (province != null && !province.equals("0") && !province.equals("")) {
			hzsql += " and pao.provinceid ='" + province + "'";
			htsql += " and pao.provinceid ='" + province + "'";
		}

		if (subproject != null && !subproject.equals("0") && !subproject.equals("")) {
			hzsql += " and pao.provinceid ='" + subproject + "'";
			htsql += " and pao.provinceid ='" + subproject + "'";
		}
		hzsql += " GROUP BY loginkey ";
		htsql += " GROUP BY loginkey ";

		String hsql = "select fm.id as loginkey,(fm.jscore/ft.tscore) as score " + " from (" + hzsql + ") fm join (" + htsql
				+ ") ft  on fm.id = ft.id order by score";

		String sql = "select pao.parentname as project, prov.name ,AVG(fz.score) as score from loginkey l join trainee pt on l.traineeid = pt.id join("
				+ hsql
				+ ") fz on l.id = fz.loginkey join pe_unit pu on pu.id = pt.fk_training_unit"
				+ " join prochildapplyno pao on pao.id = pt.fk_applyno "
				+ " join pe_province prov on pao.provinceid = prov.id "
				+ "GROUP BY pao.parentname,prov.name order by score desc";

		List avgCounts = resultService.executeSQL(sql);

		float totalScore = 0;
		DecimalFormat df = new DecimalFormat();
		String style = "0.00%";
		df.applyPattern(style);

		for (int i = 0; i < avgCounts.size(); i++) {
			Object[] objects = (Object[]) avgCounts.get(i);

			float f = Float.parseFloat(objects[2].toString());
			totalScore += f;

			Object[] data = { objects[0], objects[1], df.format(f) };

			this.avgCountMap.add(data);
		}

		session.setAttribute("manyilvmap", this.avgCountMap);

		float tmpf = totalScore / avgCounts.size();
		double avgScore = tmpf;// - tmpf % 0.5;

		this.avgCountScore = df.format(avgScore);

		style = "0";
		df.applyPattern(style);
		this.intAvgCountScore = df.format(avgScore * 10);

		this.paper = paperService.get(pid);

		String sql1 = "select distinct t.provinceid,p.name from PROCHILDAPPLYNO t join PE_PROVINCE p on t.provinceid = p.id where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";
		String sql2 = "select distinct t.parentid,t.parentname from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq="
				+ pid + ")";

		String sql3 = "select distinct t.id,t.name from prochildapplyno t where t.parentid in (select project from psqprojectmap where psq=" + pid
				+ ")";

		String sql4 = "select distinct pe.id,pe.name from pe_subject pe join trainee te on pe.id = te.fk_subject ";
		sql4 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		String sql5 = "select distinct pe.id,pe.name from pe_unit pe join trainee te on pe.id = te.fk_training_unit ";
		sql5 += "where te.fk_applyno in (select pao.id from prochildapplyno pao where pao.parentid in (select m.project from psqprojectmap m where m.psq="
				+ pid + "))";

		// 字典信息
		this.provinces = peProApplynoService.executeSQL(sql1);
		this.projects = peProApplynoService.executeSQL(sql2);
		this.subprojects = peProApplynoService.executeSQL(sql3);
		this.subjects = peProApplynoService.executeSQL(sql4);
		this.units = peProApplynoService.executeSQL(sql5);

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
				rowheader.createCell((short) 1).setCellValue("省份");
				rowheader.createCell((short) 2).setCellValue("总体满意率");

				for (int j = 0; j < huizong.size(); j++) {

					Object[] args = (Object[]) huizong.get(j);
					HSSFRow row = sheet1.createRow((short) i);
					i++;
					row.createCell((short) 0).setCellValue(args[0].toString());
					row.createCell((short) 1).setCellValue(args[1].toString());
					row.createCell((short) 2).setCellValue(args[2].toString());

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

		} catch (Exception e) {
			e.printStackTrace();
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