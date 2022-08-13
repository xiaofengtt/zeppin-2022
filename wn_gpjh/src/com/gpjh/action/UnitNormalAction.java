package com.gpjh.action;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gpjh.model.Paper;
import com.gpjh.service.PaperService;
import com.gpjh.service.ProjectAppNoService;
import com.gpjh.service.ProjectService;
import com.gpjh.service.PsqProjectMapService;
import com.gpjh.service.QuestionService;
import com.gpjh.service.ResultService;
import com.gpjh.service.SubjectService;
import com.gpjh.service.SubmitService;
import com.gpjh.service.TrainingUnitService;
import com.opensymphony.xwork2.ActionSupport;

@Controller("UnitNormalAction")
@Scope("prototype")
public class UnitNormalAction extends ActionSupport {

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

	private Hashtable<Object, Object> answerCountMap = new Hashtable<Object, Object>();
	private List duibiCountMap = new ArrayList();

	public List getDuibiCountMap() {
		return duibiCountMap;
	}

	public void setDuibiCountMap(List duibiCountMap) {
		this.duibiCountMap = duibiCountMap;
	}

	private Paper paper;

	public Hashtable<Object, Object> getAnswerCountMap() {
		return answerCountMap;
	}

	public void setAnswerCountMap(Hashtable<Object, Object> answerCountMap) {
		this.answerCountMap = answerCountMap;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public UnitNormalAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		// application = session.getServletContext();
		response = ServletActionContext.getResponse();
	}

	public String forward() {

		 if (session.getAttribute("unitUserId") == null) {
		String userId = request.getParameter("userid");
		session.setAttribute("unitUserId", userId);
		 }
		return "forward";

	}

	public String forwardToPaper() {

		 if (session.getAttribute("unitUserId") == null) {

		String userId = request.getParameter("userid");
		session.setAttribute("unitUserId", userId);
		 }
		return "forwardPaper";

	}

	public String forwardToDuibi() {

		 if (session.getAttribute("unitUserId") == null) {

		String userId = request.getParameter("userid");
		session.setAttribute("unitUserId", userId);
		 }
		return "forwardDuibi";

	}

	// 初始化培训项目和学科
	public void getInitProgame() {

		try {

			String UserId = session.getAttribute("unitUserId").toString();

			String sql = "select p.fk_unit from sso_user s join pe_manager p on s.login_id = p.login_id " + "where s.login_id='" + UserId + "'";

			List li = questionService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);

				sql = "select distinct a.id as projectId,a.name as projectName  " + " from PE_PRO_APPLY t join Pe_Unit u on t.fk_unit = u.id "
						+ " join pe_pro_applyno a on t.fk_applyno = a.id " + " join pe_subject ps on t.fk_subject = ps.id " + "where t.fk_unit='"
						+ unitId + "'" + " and t.fk_check_final='402880962a8ea45a012a8eaa43520003'";
				sql += " order by a.name desc";
				List ali = questionService.executeSQL(sql);
				if (ali.size() > 0) {
					String str = "[";
					String pt = "";

					for (int i = 0; i < ali.size(); i++) {
						Object[] obj = (Object[]) ali.get(i);
						String pid = obj[0].toString();
						String pname = obj[1].toString();
						pt += "{\"pid\":\"" + pid + "\",\"pname\":\"" + pname + "\"},";
					}
					if (pt.length() > 0) {
						pt = pt.substring(0, pt.length() - 1);
					}
					str += pt + "]";

					try {

						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Cache-Control", "no-cache");
						response.getWriter().write(str);
						return;

					} catch (Exception e) {
					}

				}

			}

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("false");

			} catch (Exception e1) {
			}

		} catch (Exception e) {
			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("false");

			} catch (Exception e1) {
			}
		}

	}

	public void selectSubject() {
		try {
			String projectId = request.getParameter("projectid").toString();
			String unitId = session.getAttribute("unitNormalId").toString();

			String sql = "select distinct ps.id ,ps.name " + "from PE_PRO_APPLY t join Pe_Unit u on t.fk_unit = u.id "
					+ "join pe_subject ps on t.fk_subject = ps.id" + " where t.fk_unit='" + unitId + "' and t.fk_applyno='" + projectId + "' "
					+ " and t.fk_check_final='402880962a8ea45a012a8eaa43520003'";
			sql += " order by ps.name desc";
			List ali = questionService.executeSQL(sql);
			if (ali.size() > 0) {
				String str = "[";
				String pt = "";

				for (int i = 0; i < ali.size(); i++) {
					Object[] obj = (Object[]) ali.get(i);
					String pid = obj[0].toString();
					String pname = obj[1].toString();
					pt += "{\"sid\":\"" + pid + "\",\"sname\":\"" + pname + "\"},";
				}
				if (pt.length() > 0) {
					pt = pt.substring(0, pt.length() - 1);
				}
				str += pt + "]";

				try {

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write(str);

				} catch (Exception e) {
				}

			}

		} catch (Exception e) {
			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("false");

			} catch (Exception e1) {
			}
		}
	}

	public void selectQuestion() {
		String projectId = request.getParameter("projectid").toString();
		String sql = "select q.id,q.name from question q where q.istongji=1 and q.type=2 and q.psq in (select psq from psqprojectmap where project='"
				+ projectId + "')";
		sql += " order by q.name desc";
		List ali = questionService.executeSQL(sql);

		if (ali.size() > 0) {
			String str = "[";
			String pt = "";

			for (int i = 0; i < ali.size(); i++) {
				Object[] obj = (Object[]) ali.get(i);
				String pid = obj[0].toString();
				String pname = obj[1].toString();
				pt += "{\"sid\":\"" + pid + "\",\"sname\":\"" + pname + "\"},";
			}
			if (pt.length() > 0) {
				pt = pt.substring(0, pt.length() - 1);
			}
			str += pt + "]";

			try {

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(str);

			} catch (Exception e) {
			}

		}

	}

	public void huizong() {

		String projectId = request.getParameter("projectid");
		String subjectId = request.getParameter("subjectid");

		if (session.getAttribute("unitNormalId") == null) {
			String UserId = session.getAttribute("unitUserId").toString();

			String sql = "select p.fk_unit from sso_user s join pe_manager p on s.login_id = p.login_id " + "where s.login_id='" + UserId + "'";

			List li = questionService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);
			}
		}

		String unitId = session.getAttribute("unitNormalId").toString();

		 String hzsql =
		 "SELECT loginkey, AVG(score) as score FROM result r, question q,loginkey l,pe_trainee pe"
		 +
		 " WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and r.question = q.id and q.istongji =1 and q.type=2 ";

//		String hzsql = "select p.name as project,u.name as unitname ,u.id ,avg(r.score) as score "
//				+ " from result r ,loginkey l,pe_trainee t ,pe_pro_applyno p,pe_unit u "
//				+ " where r.loginkey=l.id and l.traineeid=t.id and t.fk_pro_applyno=p.id and t.fk_training_unit=u.id ";

		if (projectId != null && !projectId.equals("0") && !projectId.equals("")) {

			hzsql += " and pe.fk_pro_applyno ='" + projectId + "'";
		}

		if (subjectId != null && !subjectId.equals("0") && !subjectId.equals("")) {

			hzsql += "and pe.fk_subject='" + subjectId + "'";
		}
		
		hzsql = hzsql + " GROUP BY loginkey order by score desc";
//		hzsql += " group by p.name,u.name,u.id order by score ";

		 String sql =
		 "select pao.name as project, pu.name unit ,pu.id ,AVG(fz.score) as score from loginkey l join pe_trainee pt on l.traineeid = pt.id join("
		 + hzsql
		 +
		 ") fz on l.id = fz.loginkey join pe_unit pu on pu.id = pt.fk_training_unit"
		 + " join pe_pro_applyno pao on pao.id = pt.fk_pro_applyno " +
		 "GROUP BY pu.name,pao.name,pu.id  order by score desc";

		List avgCounts = resultService.executeSQL(sql);
		DecimalFormat df = new DecimalFormat();
		String style = "0.000";
		df.applyPattern(style);

		String retJson = "[";
		String str = "";

		for (int i = 0; i < avgCounts.size(); i++) {
			Object[] obj = (Object[]) avgCounts.get(i);
			String projectName = obj[0].toString();
			String unitName = obj[1].toString();
			float f = Float.parseFloat(obj[3].toString());

			if (obj[2].toString().equals(unitId)) {
				String s = "{\"projectName\":\"" + projectName + "\",\"unitName\":\"" + unitName + "\",\"score\":\"" + df.format(f)
						+ "\",\"flag\":\"1" + "\"}";
				str += s + ",";
			} else {
				String s = "{\"projectName\":\"" + projectName + "\",\"unitName\":\"" + unitName + "\",\"score\":\"" + df.format(f)
						+ "\",\"flag\":\"0" + "\"}";
				str += s + ",";
			}
		}
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		retJson += str + "]";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
		}

	}

	public String paper() {

		String projectId = request.getParameter("projectid");
		String subjectId = request.getParameter("subjectid");

		if (session.getAttribute("unitNormalId") == null) {
			String UserId = session.getAttribute("unitUserId").toString();

			String sql = "select p.fk_unit from sso_user s join pe_manager p on s.login_id = p.login_id " + "where s.login_id='" + UserId + "'";

			List li = questionService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);
			}
		}

		String unitId = session.getAttribute("unitNormalId").toString();

		Hashtable<Object, Integer> everyQuestionCount = new Hashtable<Object, Integer>();

		// 每个题的每个答案回答次数
		String sql = "SELECT r.question, r.answer, COUNT(*) AS SUM FROM result r, question q, psq p,loginkey l,pe_trainee pe WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and r.question = q.id AND q.psq = p.id ";

		String sqlQuestionCount = "SELECT r.question, COUNT(*) AS SUM FROM result r, question q, psq p ,loginkey l,pe_trainee pe WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and r.question = q.id AND q.psq = p.id ";

		if (projectId != null && !projectId.equals("0") && !projectId.equals("")) {

			sql += " and pe.fk_pro_applyno ='" + projectId + "'";
			sqlQuestionCount += " and pe.fk_pro_applyno ='" + projectId + "'";
		}

		if (subjectId != null && !subjectId.equals("0") && !subjectId.equals("")) {
			sql += " and pe.fk_subject='" + subjectId + "'";
			sqlQuestionCount += " and pe.fk_subject='" + subjectId + "'";
		}

		if (unitId != null && !unitId.equals("0") && !unitId.equals("")) {
			sql += " and pe.fk_training_unit='" + unitId + "'";
			sqlQuestionCount += " and pe.fk_training_unit='" + unitId + "'";
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
		
//		System.out.println("size:--"+answerCountMap.size());
		
		String psql = "select t.psq from PSQPROJECTMAP t where t.project='" + projectId + "'";
		List lpi = questionService.executeSQL(psql);
		int pid = 0;
		if (lpi.size() > 0) {
			pid = Integer.parseInt(lpi.get(0).toString());
		}

		this.paper = paperService.get(pid);
		return "forwardPaper";

	}

	public String duibi() {

		String projectId = request.getParameter("projectid");
		String subjectId = request.getParameter("subjectid");
		String question = request.getParameter("questionid");

		if (session.getAttribute("unitNormalId") == null) {
			String UserId = session.getAttribute("unitUserId").toString();

			String sql = "select p.fk_unit from sso_user s join pe_manager p on s.login_id = p.login_id " + "where s.login_id='" + UserId + "'";

			List li = questionService.executeSQL(sql);

			if (li.size() > 0) {
				String unitId = li.get(0).toString();
				session.setAttribute("unitNormalId", unitId);
			}
		}

		String unitId = session.getAttribute("unitNormalId").toString();

		String dbsql = "SELECT r.loginkey, q.inx as inx, q.name as qname, r.question, AVG(r.score) AS scoreavg "
				+ " FROM result r, answer a, question q,loginkey l,pe_trainee pe "
				+ " WHERE r.loginkey = l.id and l.TRAINEEID = pe.id and a.question = q.id AND r.answer = a.id and q.type=2  and q.istongji = 1 ";
		if (projectId != null && !projectId.equals("0") && !projectId.equals("")) {
			dbsql += " and pe.fk_pro_applyno ='" + projectId + "'";
		}

		if (subjectId != null && !subjectId.equals("0") && !subjectId.equals("")) {
			dbsql += "and pe.fk_subject='" + subjectId + "'";
		}

		if (question != null && !question.equals("0") && !question.equals("")) {
			dbsql += " and r.question = " + question;
		}

		dbsql += " GROUP BY  r.LOGINKEY , r.QUESTION, q.inx, q.name ORDER BY q.inx, scoreavg desc";

		String sql = "select pao.name as project, pu.name as unit,pu.id ,fz.qname,fz.inx, AVG(fz.scoreavg) as score from loginkey l join pe_trainee pt on l.traineeid = pt.id join ("
				+ dbsql
				+ ")fz on l.id = fz.loginkey"
				+ " join pe_unit pu on pu.id = pt.fk_training_unit"
				+ " join pe_pro_applyno pao on pao.id = pt.fk_pro_applyno"
				+ " GROUP BY pu.name,pu.id,pao.name,fz.qname,fz.inx order by fz.inx,score desc";

		DecimalFormat df = new DecimalFormat();
		String style = "0.000";
		df.applyPattern(style);

		List questionCounts = resultService.executeSQL(sql);
		for (int i = 0; i < questionCounts.size(); i++) {
			Object[] objects = (Object[]) questionCounts.get(i);

			float f = Float.parseFloat(objects[5].toString());
			;

			int flag = objects[2].toString().equals(unitId) ? 1 : 0;

			Object[] data = { objects[0], objects[1], objects[3], objects[4], df.format(f), flag };
			this.duibiCountMap.add(data);
		}

		return "forwardDuibi";

	}

}
