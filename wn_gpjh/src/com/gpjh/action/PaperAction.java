package com.gpjh.action;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;

import com.gpjh.model.Answer;
import com.gpjh.model.LoginKey;
import com.gpjh.model.Paper;
import com.gpjh.model.ProjectAppNo;
import com.gpjh.model.PsqProjectMap;
import com.gpjh.model.Question;
import com.gpjh.model.Subject;
import com.gpjh.model.Submit;
import com.gpjh.model.TrainEe;
import com.gpjh.model.TrainingUnit;
import com.gpjh.service.AnswerService;
import com.gpjh.service.LoginKeyService;
import com.gpjh.service.PaperService;
import com.gpjh.service.ProjectAppNoService;
import com.gpjh.service.ProjectService;
import com.gpjh.service.PsqProjectMapService;
import com.gpjh.service.QuestionService;
import com.gpjh.service.ResultService;
import com.gpjh.service.SubjectService;
import com.gpjh.service.SubmitService;
import com.gpjh.service.TrainEeService;
import com.gpjh.service.TrainingUnitService;
import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.util.cryptogram.Escape;
import com.zeppin.util.cryptogram.generatePwd;
import com.zeppin.util.sms.SendSms4Zzx;
import com.zeppin.util.sms.SendSmsUtil;
import com.zeppin.util.sms.WriteExcel;

@Controller("PaperAction")
@Scope("prototype")
public class PaperAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(PaperAction.class);

	@Autowired
	private PaperService paperService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private LoginKeyService longinKeyService;

	@Autowired
	private ResultService resultService;

	@Autowired
	private PsqProjectMapService ppmService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SubmitService submitService;

	@Autowired
	private ProjectAppNoService projectAppNoService;

	@Autowired
	private TrainEeService trainEeService;

	@Autowired
	private TrainingUnitService trainUnitService;

	@Autowired
	private SubjectService subjectService;

	private List<Paper> paperList;

	private List<String[]> teachList;

	public List<String[]> getTeachList() {
		return teachList;
	}

	public void setTeachList(List<String[]> teachList) {
		this.teachList = teachList;
	}

	private int paperID;
	private Paper paper;
	private String qstr = "";
	private String uid = "";

	private String surveydata;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	// private ServletContext application;

	public PaperAction() {
		request = ServletActionContext.getRequest();

		// ActionContext ct = ActionContext.getContext();
		// request = (HttpServletRequest) ct
		// .get(ServletActionContext.HTTP_REQUEST);

		session = request.getSession();
		// application = session.getServletContext();
		response = ServletActionContext.getResponse();

	}

	public String doTZ() {
		/*
		 * 
		 * 跳转到通知页面
		 */
		String paperId = (String) request.getParameter("paperid");
		// System.out.println("PaperID............" + paperId);
		this.paperID = Integer.parseInt(paperId);
		return "tongzhi";

	}

	public void doSendSms() {
		//
		try {
			String pids = (String) request.getParameter("pId"); // 登录密码
			String content = (String) request.getParameter("smsContent");
			String sign = (String) request.getParameter("sign");

			String[] listpids = pids.split(",");
			generatePwd gen = new generatePwd();

			String msgformat = content + " %s " + sign;
//			String time = Utils.getBJTime();
			for (String st : listpids) {
				String lid = gen.getOralString(st);
				LoginKey lk = longinKeyService.get(lid);
				String telephone = lk.getTelephone();
				// if (Command.equals("PWD") && isCommand) {
				if (telephone == null || telephone.equals("")) {
					lk.setStatus((short) 4);
				} else {
					try {
//						String dm = gen.getSecString(lk.getId());
//						String msg = String.format(msgformat, dm);
//						sendSmsServ sendsmc = new sendSmsServ(telephone, msg);
//
//						int code = sendsmc.sendSms();
//						code = code == 0 ? 1 : 4;
//						lk.setStatus((short) code);
						String dm = gen.getSecString(lk.getId());
						String msg = String.format(msgformat, dm);
//						SendSmsUtil sm = new SendSmsUtil(telephone, msg,time);
						SendSms4Zzx sm = new SendSms4Zzx(telephone, msg);
						String str = sm.sendSms();
						short code = 1;
						if(!"0".equals(str)){
							code=4;
						}
						lk.setStatus(code);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				longinKeyService.update(lk);
			}

//			sendSmsServ sendsmc = new sendSmsServ("13811994936", "【国培办】给管理人员发送，查看短信是否发送成功！");
//			sendsmc.sendSms();
//			SendSmsUtil sm = new SendSmsUtil("13811994936,", msgformat+"——此条给管理人员发送，查看短信是否发送成功！",time);
			SendSms4Zzx sm = new SendSms4Zzx("13811994936,", msgformat+"——此条给管理人员发送，查看短信是否发送成功！");
			sm.sendSms();

			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("处理结束");
			} catch (Exception e1) {
			}

		} catch (Exception e) {
			e.printStackTrace();

			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("处理结束");
			} catch (Exception e1) {
			}

		}

	}

	public void doLookLoginKey() {

		// 用来提示是否提示登录码已经有生成是否要继续
		String paperId = (String) request.getParameter("paperid");

		Object lflag = session.getAttribute("dologin");
//		System.out.println(session.getAttribute("dologin"));
		if (lflag != null) {

			if (!lflag.toString().equals("ok")) {

				try {
					if (!lflag.toString().equals(paperId)) {
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Cache-Control", "no-cache");
						response.getWriter().write("当前有别的问卷正在生成学院登陆码");
					} else {
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Cache-Control", "no-cache");
						response.getWriter().write("当前问卷正在生成学院登陆码");
					}
					return;

				} catch (Exception e1) {
				}

			}
		}

		Object agFlag = session.getAttribute(paperId);

		if (agFlag != null) {
			try {
				if (agFlag.toString().equals("ok")) {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("当前问卷生成登陆成功");
					return;
				} else {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("当前问卷正在生成登陆码");
					return;
				}
			} catch (Exception e1) {
			}
		}

//		List<PsqProjectMap> ppm = ppmService.getListForPage("from PsqProjectMap where PSQ = " + paperId, 0, 100);
		String flag = "true";
//		for (PsqProjectMap m : ppm) {
//
//			if (m.getStatus() == 0) {
//				flag = "true";
//				break;
//			}
//
//		}

		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(flag);
		} catch (Exception e1) {
		}

	}

	// 生成登录码
	public void doLoginKey() {
		// 根据问卷id 查询出对应的项目id 在查找出人员id，生成登录码入库
		String paperId = (String) request.getParameter("paperid");
		int ipaperid = Integer.parseInt(paperId);

		try {

			// 根据问卷id去map表中去获取 帮顶的项目dw
			List<PsqProjectMap> ppm = ppmService.getListForPage("from PsqProjectMap where PSQ = " + paperId, 0, 100);
			generatePwd genpwd = new generatePwd();
			// 根据 项目Id 去项目培训表中获取人员信息以及电话信息

			session.setAttribute(paperId, "正在生成登陆码");
			session.setAttribute("dologin", paperId);

			HibernateTemplate hiber = longinKeyService.getHibeTemplate();
			java.sql.Connection conn = hiber.getSessionFactory().openSession().connection();

			PreparedStatement ps = conn
					.prepareStatement("insert into loginkey(id,psqmapid,status,creattime,telephone,TRAINEEID,PSQSTATUS) values(?,?,?,?,?,?,?)");
			int totalcount = 0;

			java.sql.Date od = new java.sql.Date(new Date().getTime());

			// System.out.println(new Date().toLocaleString());

			for (PsqProjectMap m : ppm) {

//				if (m.getStatus() == 1) {
//					continue;
//				}
//				String sql = "select te.id,te.telephone 
//				from trainee te join prochildapplyno p on te.fk_applyno= p.id 
//				join pe_pro_applyno a on p.parentid = a.id 
//				Left outer join loginkey lk on te.id = lk.traineeid and lk.psqmapid=" + ipaperid + " 
//				where lk.traineeid is null and a.id='" + parentid + "'";
//				String sql = "from TrainEe t left outer join  where FK_PRO_APPLYNO = '" + m.getProject().getId() + "'";
				String sql = "select te.id,te.telephone from PE_TRAINEE te left outer join loginkey lk on te.id=lk.traineeId and lk.psqmapid=" + ipaperid + " where lk.traineeid is null and te.FK_PRO_APPLYNO= '" + m.getProject().getId() + "'";

//				List<TrainEe> listrain = trainEeService.getListByHSQL(sql);

				// System.out.println(listrain.size() + "   " + new
				// Date().toLocaleString());

//				for (TrainEe te : listrain) {
				List li = longinKeyService.executeSQL(sql);
				for (int i = 0; i < li.size(); i++) {
					Object[] obj = (Object[]) li.get(i);
					String traineeid = obj[0].toString();
					String telephone = obj[1] == null ? "" : obj[1].toString();

					ps.setString(1, genpwd.getPwd());
					ps.setInt(2, ipaperid);
					ps.setInt(3, 2);
					ps.setDate(4, od);
					ps.setString(5, telephone);
					ps.setString(6, traineeid);
					ps.setInt(7, 2);

					ps.addBatch();

					totalcount += 1;

					if (totalcount % 10000 == 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				}

				ps.executeBatch();
				ps.clearBatch();

				// System.out.println(new Date().toLocaleString());

				m.setStatus(1);
				ppmService.update(m);

			}

			// System.out.println(new Date().toLocaleString());

			conn.commit();
			ps.close();
			// conn.close();

			session.setAttribute(paperId, "ok");
			session.removeAttribute(paperId);
			session.setAttribute("dologin", "ok");

			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write("学员登录码生成成功,共处理了" + totalcount + "条数据");

		} catch (Exception e) {
			try {
				e.printStackTrace();
				session.setAttribute(paperId, "ok");
				session.removeAttribute(paperId);
				session.setAttribute("dologin", "ok");

				// System.out.println(e.getMessage());
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(e.getMessage());
			} catch (Exception e1) {
			}
		}

	}

	public void doTel() {
		/*
		 * 短信通知参数 1. 通过问卷Id 2. proid 3. status 4. start 5. limit
		 */
		String paperId = (String) request.getParameter("paperid");

		// 项目id
		String proId = (String) request.getParameter("project");
		if (proId == null || proId.equals("")) {
			proId = "all";
		}

		// 分条件检索 id
		String unitId = (String) request.getParameter("trainingUnit");
		if (unitId == null || unitId.equals("")) {
			unitId = "all";
		}

		// 学科
		String subId = (String) request.getParameter("course"); // id
		if (subId == null || subId.equals("")) {
			subId = "all";
		}

		// 状态
		String status = (String) request.getParameter("smsStatus");
		if (status == null || status.equals("")) {
			status = "3";
		}

		// 问卷提交状态
		String psqstatus = (String) request.getParameter("paperStatus");

		if (psqstatus == null || psqstatus.equals("")) {
			psqstatus = "3";
		}

		// 是否结业
		String graduted = (String) request.getParameter("gradutedStatus");
		if (graduted == null || graduted.equals("")) {
			graduted = "all";
		}

		session.setAttribute("proId", proId);
		session.setAttribute("unitId", unitId);
		session.setAttribute("subId", subId);
		session.setAttribute("status", status);
		session.setAttribute("psqstatus", psqstatus);
		session.setAttribute("graduted", graduted);

		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}

		String ien = (String) request.getParameter("rows");
		if (ien == null || ien.equals("")) {
			ien = "30";
		}

		int start = Integer.parseInt(ist);

		int limit = 30;
		limit = (ien.equals("all") ? -1 : Integer.parseInt(ien));

		String psql = "from PsqProjectMap where PSQ = " + paperId;
		if (!proId.equals("all")) {
			psql += " and PROJECT='" + proId + "'";
		}

		// 直接根据 proid 去培训记录表中获取培训人员
		// 根据问卷id去map表中去获取 帮顶的项目
		List<PsqProjectMap> ppm = ppmService.getListForPage(psql, 0, 100);
		// 根据 项目Id 去项目培训表中获取人员信息以及电话信息
		generatePwd gen = new generatePwd();

		int totalCount = 0;
		String retJson = "";
		String proJson = "";
		String retTmpJson = "";
		for (PsqProjectMap m : ppm) {

			String sqlcount = "select count(*) from LoginKey lk where lk.traineeId in (select te.id from TrainEe te where te.proApplyNo = '"
					+ m.getProject().getId() + "'";

			if (!subId.equals("all")) {
				sqlcount += " and te.fkSubject='" + subId + "'";
			}
			if (!unitId.equals("all")) {
				sqlcount += " and te.trainingUnit='" + unitId + "'";
			}

			if (!graduted.equals("all")) {
				sqlcount += " and te.graduted='isgraduateidflag" + graduted + "'";
			}

			sqlcount += ")";

			sqlcount += " and lk.psqMap=" + paperId;

			String stst = " and lk.status=" + status;

			String psqstst = " and lk.psgStatus=" + psqstatus;

			if (!status.equals("3")) {
				// 不是所有
				sqlcount += stst;
			}

			if (!psqstatus.equals("3")) {

				sqlcount += psqstst;

			}

			String pt = "{\"id\":\"" + m.getProject().getId() + "\",\"title\":\"" + m.getProject().getName() + "\"}";

			proJson += pt + ",";

			// System.out.println(sqlcount);

			List<Object> objCount = trainEeService.findByHSQL(sqlcount);

			if (objCount.size() > 0) {
				totalCount += Integer.parseInt(objCount.get(0).toString());
				// System.out.println("条数测试" + totalCount);
			}

			String searchSql = "select l.id,p.name as username,p.telephone,pa.name as project,u.name unitname,s.name subjectname,l.status,l.psqstatus,p.fk_graduted ";
			searchSql += "from loginkey l join pe_trainee p on l.traineeid = p.id join pe_unit u on p.fk_training_unit=u.id join pe_subject s on p.fk_subject = s.id join pe_pro_applyno pa on p.fk_pro_applyno = pa.id ";
			searchSql += "where pa.id='" + m.getProject().getId() + "' and l.psqmapid=" + paperId + " ";

			// String sql = "from TrainEe te where FK_PRO_APPLYNO = '" +
			// m.getProject().getId() + "'";

			if (!subId.equals("all")) {

				// sql += " and te.fkSubject='" + subId + "'";
				searchSql += " and s.id='" + subId + "' ";
			}

			if (!unitId.equals("all")) {
				// /sql += " and te.trainingUnit='" + unitId + "'";
				searchSql += " and u.id='" + unitId + "' ";
			}

			if (!graduted.equals("all")) {
				// sql += " and te.graduted='isgraduateidflag" + graduted + "'";
				searchSql += " and p.fk_graduted='isgraduateidflag" + graduted + "' ";
			}

			String sts = " and l.status=" + status;

			String psqsts = " and l.psqstatus=" + psqstatus;

			if (!status.equals("3")) {
				// 不是所有
				searchSql += sts;
			}

			if (!psqstatus.equals("3")) {

				searchSql += psqsts;
			}

			// System.out.println(searchSql);

			List searchList = limit == -1 ? questionService.executeSQL(searchSql) : questionService.getListPage(searchSql, start, limit);
			String loginJson = "";

			for (int i = 0; i < searchList.size(); i++) {
				Object[] sarray = (Object[]) searchList.get(i);
				String key = sarray[0].toString();
				try {
					key = gen.getSecString(key);
				} catch (Exception e) {
				}
				String username = sarray[1].toString();
				String tele = sarray[2].toString();
				String projectName = sarray[3].toString();
				String unitName = sarray[4].toString();
				String subjectName = sarray[5].toString();
				String teleStatus = sarray[6].toString();
				String psqStatus = sarray[7].toString();
				String gradutedStr = sarray[8].toString();

				int stat = Integer.parseInt(teleStatus);
				String st = "未知";
				switch (stat) {
				case 1:
					st = "已发送";
					break;
				case 2:
					st = "未发送";
					break;
				case 4:
					st = "发送失败";
					break;
				case 5:
					st = "已经提交问卷";
					break;
				default:
					st = "未知";
					break;
				}

				int psqstat = Integer.parseInt(psqStatus);
				String psqst = "未知";
				switch (psqstat) {
				case 2:
					psqst = "未填写";
					break;
				case 1:
					psqst = "已填写";
					break;
				default:
					psqst = "未知";
					break;
				}

				String chgraduted = "未知";
				if (gradutedStr.equals("isgraduateidflag1")) {
					chgraduted = "未结业";
				} else if (gradutedStr.equals("isgraduateidflag2")) {
					chgraduted = "已结业";
				}

				String t = "{\"id\":\"" + key + "\",\"cell\":[\"" + key + "\",\"" + username + "\",\"" + tele + "\",\"" + projectName + "\",\""
						+ unitName + "\",\"" + subjectName + "\",\"" + st + "\",\"" + psqst + "\",\"" + chgraduted + "\"]}";

				loginJson += t + ",";

			}

			retTmpJson += loginJson;

		}

		if (!retTmpJson.equals("")) {
			retTmpJson = retTmpJson.substring(0, retTmpJson.length() - 1);
		}
		proJson = proJson.substring(0, proJson.length() - 1);

		limit = limit == -1 ? totalCount : limit;
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + retTmpJson
				+ "],\"userdata\":{\"paperid\":" + paperId + ",\"project\":[" + proJson + "]}}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
		}

	}

	// Search
	public void doHeadSearch() {
		String paperId = (String) request.getParameter("paperid");

		List<PsqProjectMap> ppm = ppmService.getListForPage("from PsqProjectMap where PSQ = " + paperId, 0, 100);

		String projectJson = "{\"id\":\"all\",\"title\":\"所有\"},";
		String trainJson = "{\"id\":\"all\",\"title\":\"所有\"},";
		String subJson = "{\"id\":\"all\",\"title\":\"所有\"},";

		Hashtable<String, String> trainhash = new Hashtable<String, String>();
		Hashtable<String, String> subjecthash = new Hashtable<String, String>();

		for (PsqProjectMap m : ppm) {

			String pt = "{\"id\":\"" + m.getProject().getId() + "\",\"title\":\"" + m.getProject().getName() + "\"}";

			projectJson += pt + ",";

			List<Object> objListTrain = trainUnitService.findByHSQL("select distinct pe.trainingUnit from TrainEe pe  where pe.proApplyNo='"
					+ m.getProject().getId() + "'");

			for (Object obj : objListTrain) {
				if (obj == null) {
					continue;
				}
				String key = obj.toString();
				if (key != null && !key.equals("") && !trainhash.containsKey(key)) {
					String tmpuname = trainUnitService.get(key).getName();
					trainhash.put(key, tmpuname);
				}

			}

			List<Object> objListsubject = subjectService.findByHSQL("select distinct pe.fkSubject from TrainEe pe  where pe.proApplyNo='"
					+ m.getProject().getId() + "'");

			for (Object obj : objListsubject) {
				if (obj == null) {
					continue;
				}
				String key = obj.toString();
				if (key != null && !key.equals("") && !subjecthash.containsKey(key)) {
					subjecthash.put(key, subjectService.get(key).getName());
				}
			}
		}

		List<TrainingUnit> listunit = trainUnitService.loadAll();
		List<Subject> listsubject = subjectService.loadAll();

		Enumeration et = trainhash.keys();

		while (et.hasMoreElements()) {
			String key = et.nextElement().toString();
			String value = trainhash.get(key);
			String te = "{\"id\":\"" + key + "\",\"title\":\"" + value + "\"}";
			trainJson += te + ",";
		}

		Enumeration st = subjecthash.keys();

		while (st.hasMoreElements()) {
			String key = st.nextElement().toString();
			String value = subjecthash.get(key);
			String te = "{\"id\":\"" + key + "\",\"title\":\"" + value + "\"}";
			subJson += te + ",";
		}

		projectJson = projectJson.substring(0, projectJson.length() - 1);
		trainJson = trainJson.substring(0, trainJson.length() - 1);
		subJson = subJson.substring(0, subJson.length() - 1);

		String retJson = "{\"preInitData\":{\"project\":[" + projectJson + "],\"trainingUnit\":[" + trainJson + "],\"course\":[" + subJson + "]}}";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
		}
	}

	// 生成excel
	public void doExcel() {

		String paperId = (String) request.getParameter("paperid");

		// 项目id
		String proId = session.getAttribute("proId").toString();
		if (proId == null || proId.equals("")) {
			proId = "all";
		}

		// 分条件检索 id
		String unitId = session.getAttribute("unitId").toString();
		if (unitId == null || unitId.equals("")) {
			unitId = "all";
		}

		// 学科
		String subId = session.getAttribute("subId").toString(); // id
		if (subId == null || subId.equals("")) {
			subId = "all";
		}

		// 状态
		String status = session.getAttribute("status").toString();
		if (status == null || status.equals("")) {
			status = "3";
		}

		// 问卷提交状态
		String psqstatus = session.getAttribute("psqstatus").toString();
		if (psqstatus == null || psqstatus.equals("")) {
			psqstatus = "3";
		}

		// 是否结业
		String graduted = session.getAttribute("graduted").toString();
		if (graduted == null || graduted.equals("")) {
			graduted = "all";
		}

		String psql = "from PsqProjectMap where PSQ = " + paperId;
		if (!proId.equals("all")) {
			psql += " and PROJECT='" + proId + "'";
		}

		List<PsqProjectMap> ppm = ppmService.getListForPage(psql, 0, 100);
		generatePwd gen = new generatePwd();

		List<String> outputString = new ArrayList<String>();

		for (PsqProjectMap m : ppm) {

			String searchSql = "select l.id,p.name as username,p.telephone,pa.name as project,u.name unitname,s.name subjectname,l.status,l.psqstatus,p.fk_graduted ";
			searchSql += "from loginkey l join pe_trainee p on l.traineeid = p.id join pe_unit u on p.fk_training_unit=u.id join pe_subject s on p.fk_subject = s.id join pe_pro_applyno pa on p.fk_pro_applyno = pa.id ";
			searchSql += "where pa.id='" + m.getProject().getId() + "' and l.psqmapid=" + paperId + " ";

			if (!subId.equals("all")) {
				searchSql += " and s.id='" + subId + "' ";
			}

			if (!unitId.equals("all")) {
				searchSql += " and u.id='" + unitId + "' ";
			}

			if (!graduted.equals("all")) {
				searchSql += " and p.fk_graduted='isgraduateidflag" + graduted + "' ";
			}

			String sts = " and l.status=" + status;
			String psqsts = " and l.psqstatus=" + psqstatus;

			if (!status.equals("3")) {
				searchSql += sts;
			}

			if (!psqstatus.equals("3")) {
				searchSql += psqsts;
			}

			// System.out.println(searchSql);

			List searchList = questionService.executeSQL(searchSql);

			for (int i = 0; i < searchList.size(); i++) {
				Object[] sarray = (Object[]) searchList.get(i);
				String key = sarray[0].toString();
				try {
					key = gen.getSecString(key);
				} catch (Exception e) {
				}
				String username = sarray[1].toString();
				String tele = sarray[2].toString();
				String projectName = sarray[3].toString();
				String unitName = sarray[4].toString();
				String subjectName = sarray[5].toString();
				String teleStatus = sarray[6].toString();
				String psqStatus = sarray[7].toString();
				String gradutedStr = sarray[8].toString();

				int stat = Integer.parseInt(teleStatus);
				String st = "未知";
				switch (stat) {
				case 1:
					st = "已发送";
					break;
				case 2:
					st = "未发送";
					break;
				case 4:
					st = "发送失败";
					break;
				case 5:
					st = "已经提交问卷";
					break;
				default:
					st = "未知";
					break;
				}

				int psqstat = Integer.parseInt(psqStatus);
				String psqst = "未知";
				switch (psqstat) {
				case 2:
					psqst = "未填写";
					break;
				case 1:
					psqst = "已填写";
					break;
				default:
					psqst = "未知";
					break;
				}

				String chgraduted = "未知";
				if (gradutedStr.equals("isgraduateidflag1")) {
					chgraduted = "未结业";
				} else if (gradutedStr.equals("isgraduateidflag2")) {
					chgraduted = "已结业";
				}

				outputString.add(key + "," + username + "," + tele + "," + projectName + "," + unitName + "," + subjectName + "," + st + "," + psqst
						+ "," + chgraduted);

			}
		}

		try {

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=download.xls");
			response.setCharacterEncoding("UTF-8");
			WriteExcel we = new WriteExcel();
			we.getExcel(outputString, response.getOutputStream());

		} catch (Exception e) {
		}

	}

	// 查询设置 教师和培训专题
	public void setTeachTheme() {

		LoginKey lk = null;

		try {
			lk = longinKeyService.get(this.uid);
		} catch (Exception e) {
		}

		if (lk != null) {
			String traineeId = lk.getTraineeId();

			TrainEe te = trainEeService.get(traineeId);

			String proId = te.getProApplyNo();
			String unitId = te.getTrainingUnit();
			String subId = te.getFkSubject();

			try {

				String sql = "select distinct c.id, c.expert_name,c.theme from pe_course_plan c join pe_pro_apply a on c.fk_pro_apply=a.id inner join pe_pro_applyno b on a.fk_applyno = b.id where ";
				sql += "a.fk_unit='" + unitId + "'";
				sql += " and  a.fk_subject='" + subId + "'";
				sql += " and b.id='" + proId + "'";

				List<String[]> tlis = new ArrayList<String[]>();
				List rs = questionService.executeSQL(sql);

				for (int i = 0; i < rs.size(); i++) {
					String[] sl = new String[2];
					Object[] objs = (Object[]) rs.get(i);
					sl[0] = objs[1].toString().trim();
					sl[1] = objs[2].toString().trim();
					tlis.add(sl);
				}

				this.setTeachList(tlis);

			} catch (Exception e) {
			}
		}

	}

	/**
	 * 编辑试卷
	 * 
	 * @return
	 */
	public String edit() {

		// HttpServletRequest request = ServletActionContext.getRequest();
		String curID = request.getParameter("curid");

		// String userId = (String)request.getSession().getAttribute("ID");
		String userId = (String) session.getAttribute("ID");

		log.info("paper " + curID + " edit");

		// if (!userId.endsWith("xjGPJH58801324") || curID == null
		// || curID.equals("")) {
		// return "cantadd";
		// }

		// System.out.println("curID...... " + curID);
		int icurID = Integer.parseInt(curID);
		Paper paper = paperService.get(icurID);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		// System.out.println(sdf.format(paper.getCreatTime()));

		this.surveydata = sdf.format(paper.getCreatTime()) + "§" + paper.getSurveydata() + "?false§" + paper.getCreator()
				+ "§0§q_12px.css§False§null";

		// System.out.println(this.surveydata);

		return "edit";
	}

	public void save() {

		// String uid = (String)request.getSession().getAttribute("ID");
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获取是否要出排名题
		boolean isShowTech = false;
		Cookie[] c = request.getCookies();
		for (Cookie cookie : c) {
			if (cookie.getName().equals("isShowTech")) {
				if (cookie.getValue().equals("true")) {
					isShowTech = true;
					cookie.setMaxAge(0);
					response.addCookie(cookie);

				}// get the cookie value
				break;
			}
		}

		// HttpServletResponse response = ServletActionContext.getResponse();

		boolean result = false;
		// 试卷ID
		String curID = request.getParameter("curID");

		String uid = request.getParameter("userguid");
		String surveydata = request.getParameter("surveydata");

		String[] b = surveydata.split("¤");

		String[] a = b[0].split("§");

		// 试卷的信息
		int icurID = Integer.parseInt(curID);
		Paper paper = paperService.get(icurID);

		if (paper != null) {
			paper.setTitle(a[0]);
			paper.setAbout(a[1]);
			paper.setType((short) 1);
			paper.setStatus((short) 1);
			paper.setId(icurID);
			paper.setCreator(uid);
			paper.setSurveydata(surveydata);

			paper = paperService.update(paper);

			// 清除掉原来的答案信息
			answerService.deleteByPaperId(paper.getId());
			// 清除原来的问题信息
			questionService.deleteByPaperId(paper.getId());

			for (int d = 1; d < b.length; d++) {// 以题目为单位分成数组

				String oneQ = b[d];

				String[] Qo = oneQ.split("§");

				String type = Qo[0];
				int index = Integer.parseInt(Qo[1]);
				String title = Qo[2];

				int arrange = -1;
				String isTongji = "false";
				if (type.equals("radio") || type.equals("check")) {
					arrange = Integer.parseInt(Qo[4].split("〒")[0]);
					isTongji = Qo[8];
				}
				int scale = Integer.parseInt(Qo[3]);
				String option = "";
				short itype = 0;
				if (type.equals("radio")) {
					itype = 2;
				} else if (type.equals("check")) {
					itype = 4;
				} else if (type.equals("question")) {
					itype = 6;
				}

				Question question = new Question();

				question.setPaper(paper);
				question.setName(title.split("〒")[0]);
				question.setInx(index);
				question.setArrange(arrange);
				question.setType(itype);
				question.setScale(scale);
				question.setHint("");
				question.setIsTongji(isTongji.equals("true") ? 1 : 0);
				question = questionService.add(question);

				if (type.equals("radio") || type.equals("check") || type.equals("question")) {

					int x = 1;

					for (int j = 14; j < Qo.length; j++) {
						option = Qo[j];
						String[] answers = option.split("〒");
						String answer_title = "";
						int score = 0;
						int jump = 0;
						String about = "";
						if (!type.equals("question")) {
							answer_title = answers[0];
							score = Integer.parseInt(answers[2]);
							jump = answers[3] == null || answers[3].equals("") ? -1 : Integer.parseInt(answers[3]);
							about = "";
							if (answers.length == 9) {
								about = answers[8];
							}

						} else {
							answer_title = "$$填空题$$";// 以名字作为识别标记
							j = Qo.length;
						}
						Answer answer = new Answer();
						answer.setPaper(paper);
						answer.setQuestion(question);
						answer.setInx(x);
						answer.setName(answer_title);
						answer.setScore(score);
						answer.setAbout(about);
						answer.setJump(jump);
						answer.setDefault(false);
						answer.setRight(false);

						answerService.add(answer);

						x++;

					}
				}
			}

			// 如果有排名题，直接添加 到最后一题
			if (isShowTech) {
				Question q = new Question();

				q.setPaper(paper);
				q.setName("排名题");
				q.setInx(b.length - 1);
				q.setArrange(0);
				q.setType((short) 7);
				q.setScale(0);
				q.setHint("");
				q.setIsTongji(0);
				q = questionService.add(q);
			}

			result = true;
		}

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result + "&" + curID);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 试卷列表
	 * 
	 * @return
	 */
	public String list() {

		String userId = (String) request.getParameter("userid");
		// request.getSession().getAttribute("ID");

		if (userId == null || userId.equals("")) {

			request.getSession().setAttribute("ID", "xjGPJH58801324");

		} else {
			request.getSession().setAttribute("ID", userId);
		}

		String cannotEditPaperID = "";
		String sql = "select p.id from Paper p where exists (select 1 from  Question q, Result r where r.question = q.id and q.paper=p.id)";
//		String sql = "select distinct paper.id from Result result, Paper paper, Question question where result.question = question.id and question.paper = paper.id";
//		String sql = "select distinct paper.id from Result result, Paper paper, Question question where result.question = question.id and question.paper = paper.id";
		List<Object> res = resultService.findByHSQL(sql);

		for (Iterator<Object> iter = res.iterator(); iter.hasNext();) {
			String record = iter.next().toString();
			cannotEditPaperID += record + ",";
		}

		sql = "from Paper where 1=1 and projectType=0  and status <> 0 order by id desc";

		List<Paper> paperList = paperService.getListForPage(sql, 0, 2000);

		sql = "from PsqProjectMap";
		List<PsqProjectMap> pms = ppmService.getListForPage(sql, 0, 2000);

		this.paperList = new ArrayList<Paper>();
		for (Paper p : paperList) {

			List<ProjectAppNo> pls = new ArrayList<ProjectAppNo>();
			for (PsqProjectMap pm : pms) {

				if (p.getId() == pm.getPaper().getId()) {
					// System.out.println(pm.getProject());
					pls.add(pm.getProject());
				}
			}
			p.setProjects(pls);

			// 给不能编辑的id设置属性
			if (cannotEditPaperID.indexOf(p.getId() + "") > 0) {
				p.setEditalbe(0);
			} else {
				p.setEditalbe(1);
			}

			this.paperList.add(p);
		}

		return "list";
	}

	/**
	 * 新建问卷
	 * 
	 * @return
	 */
	public String add() {

		// HttpServletRequest request = ServletActionContext.getRequest();
		String userId = (String) request.getSession().getAttribute("ID");

		// if (!userId.endsWith("xjGPJH58801324")) {
		// return "cantadd";
		// }

		Paper paper = new Paper();
		paper.setTitle("请输入您的问卷的标题[复制]");
		paper.setAbout("请填写关于此问卷的说明");
		paper.setStatus((short) 1);
		paper.setType((short) 1);
		paper.setClosing("");
		paper.setGotoUrl("");
		paper.setCreator(userId);
		paper.setSurveydata("请输入您的问卷的标题§请填写关于此问卷的说明§0§0§0§false§false§0¤page§1§§0§§§");

		Paper n = paperService.add(paper);

		this.paperID = n.getId();

		return "add";
	}

	/**
	 * 投票页面
	 * 
	 * @return
	 */
	public String view() {
		// HttpServletRequest request = ServletActionContext.getRequest();
		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);
		String t = request.getParameter("t");
		this.uid = (String) session.getAttribute("ID");

		paperService.get(id);

		// System.out.println("view page ==== " + id);
		this.paper = paperService.get(id);

		if (this.uid != null && !this.uid.equals("")) {
			this.setTeachTheme();
		}

		if (this.paper == null && this.paper.getStatus() != 2 && !t.equals("x123")) {
			return "novote";
		}

		String ret = "false§false¤page§1§§§¤";

		Set<Question> qs = this.paper.getQuestions();
		int i = 1;
		String qstr = "";
		for (Question q : qs) {

			String type = "";
			int itype = q.getType();
			if (itype == 0)
				continue;
			if (itype == 2) {
				type = "radio";
			} else if (itype == 4) {
				type = "check";
			} else if (itype == 6) {
				type = "question";
			}

			int inx = q.getInx();
			int arrange = q.getArrange();

			String requir = "true";
			if (itype == 4) {
				requir = "true,,";
			}

			String cp = "";
			if (q.getScale() == 0) {
				cp = "ceping";
			}

			qstr += type + "§" + inx + "§" + arrange + "§true§false§0§" + requir + "§" + cp + "§0§" + q.getScale() + "§§§";
			String anstr = "";
			Set<Answer> as = q.getAnswers();

			int j = 1;
			for (Answer a : as) {

				if (itype == 4) {
					anstr += "false〒" + a.getInx() + "〒" + a.getJump() + "〒";
				} else {
					anstr += "false〒" + a.getInx() + "〒" + a.getJump();
				}
				// anstr += anstr;
				// System.out.println(anstr);
				if (j < as.size()) {
					anstr += "§";
				}
				j++;
			}
			qstr += anstr;

			if (i < qs.size() - 1) {
				qstr += "¤";
			}
			i++;

			this.qstr = ret + qstr;
		}

		// System.out.println(this.qstr);

		return "view";
	}

	public String Number2Str(int num) {
		switch (num) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		default:
			return "一";
		}
	}

	/**
	 * 处理投票
	 * 
	 * @return
	 */
	/**
	 * 处理投票
	 * 
	 * @return
	 */
	public void process() {

		try {
			String paper_id = request.getParameter("curID");
			String submitdata = request.getParameter("submitdata");

			String loginkey = (String) request.getSession().getAttribute("ID");// "GP000100300101";

			if (loginkey == null || loginkey.equals("")) {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("1000〒您的登陆已经失效,请重新登陆填写！");
				return;
			}

			LoginKey lk = null;
			try {
				lk = longinKeyService.get(loginkey);
			} catch (Exception e) {
				e.printStackTrace();
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("1000〒您的登陆已经失效,请重新登陆填写！");
				return;
			}

			String sqlstats = "select count(*) from SUBMIT t where t.loginkey='" + loginkey + "'";
			List ls = longinKeyService.executeSQL(sqlstats);
			if (ls != null && ls.size() > 0) {
				int flag = Integer.parseInt(ls.get(0).toString());
				if (flag > 0) {
					lk.setPsgStatus(1);
					lk.setStatus((short) 1);
					longinKeyService.update(lk);
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("1000〒您已经提交过问卷，请勿重复提交！");

					return;
				}

			}

			Paper paper = new Paper();
			int pp_id = Integer.parseInt(paper_id);
			paper.setId(pp_id);
			String ip = getIpAddr(request);

			// 添加submit表
			Submit submit = new Submit();
			submit.setLoginkey(lk);
			submit.setPaper(paper);
			submit.setIp(ip);
			submit = submitService.add(submit);

			// 验证投票人数结束

			String[] datas = submitdata.split("}");

			resultService.addResult(datas, paper_id, lk, submit);

			lk.setPsgStatus(1);
			longinKeyService.update(lk);

			// 通过cookie

			Cookie[] cookies = request.getCookies();
			String voteValue = "";

			for (Cookie co : cookies) {
				if (co.getName().equals("techVoteValue")) {
					voteValue = co.getValue();
					co.setMaxAge(0);
					response.addCookie(co);
					break;
				}
			}

			voteValue = Escape.unescape(voteValue);
			voteValue = voteValue.replaceAll("\n", "");
			voteValue = voteValue.replaceAll("\t", "");
			voteValue = voteValue.trim();

			if (voteValue.equals("")) {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("1000〒成功提交问卷，感谢您的参与！");
				return;
			}

			System.out.print(lk.getId() + "    " + voteValue);

			String[] vs = voteValue.split("〒");
			String tmpvalue = "";
			String[] voteStr = new String[5];
			String[] voteth = new String[5];

			for (int i = 0; i < vs.length; i++) {
				if (vs[i].length() > 2) {
					String[] t = vs[i].substring(2).split("#");
					if (t[0].equals("请选择老师") || t[1].equals("清选择专题")) {
						voteStr[i] = "";
						voteth[i] = "";
						continue;
					} else {

						tmpvalue += "第" + Number2Str((i + 1)) + "名:" + t[0] + "," + t[1] + ";";
						voteStr[i] = t[0];
						voteth[i] = t[1];

					}
				}
			}
			if (tmpvalue != null && !tmpvalue.equals("") && tmpvalue.length() >= 2) {
				voteValue = tmpvalue.substring(0, tmpvalue.length() - 1);
			}

			String traineeId = lk.getTraineeId();

			try {

				String sql = "select count(1) c,fk_training_unit unit,fk_subject subject ,ee.fk_pro_applyno applyno ";
				sql += "from pe_trainee ee join enum_const e on ee.fk_checked_trainee=e.id ";
				sql += " join enum_const enu on enu.id=ee.fk_graduted ";
				sql += "  where e.code='65230' and enu.code='1' and ee.id='" + traineeId + "' ";
				sql += " group by ee.fk_training_unit ,ee.fk_subject,ee.fk_pro_applyno";

				List usi = questionService.executeSQL(sql);

				if (usi != null && usi.size() > 0) {

					Object[] objects = (Object[]) usi.get(0);

					String proId = objects[3].toString();
					String unitId = objects[1].toString();
					String subId = objects[2].toString();

					sql = "select distinct a.class_identifier from pe_course_plan c join pe_pro_apply a on c.fk_pro_apply=a.id inner join pe_pro_applyno b on a.fk_applyno = b.id ";
					sql += "where a.fk_unit='" + unitId + "' and  a.fk_subject='" + subId + "'  and b.id='" + proId + "'";

					List iden = questionService.executeSQL(sql);

					if (iden != null && iden.size() > 0) {

						String classId = iden.get(0).toString();

						for (int i = 0; i < 5; i++) {
							String votename = voteStr[i];
							String votethName = voteth[i];

							if (votename == null || votename.equals("") || votethName == null || votethName.equals("")) {
								continue;
							}
							sql = "select c.id from pe_course_plan c join pe_pro_apply a on c.fk_pro_apply=a.id inner join pe_pro_applyno b on a.fk_applyno = b.id ";
							sql += "where a.fk_unit='" + unitId + "' and  a.fk_subject='" + subId + "'  and b.id='" + proId + "'";
							sql += " and c.expert_name like'%" + votename + "%' and c.theme like'%" + votethName + "%'";

							List planids = questionService.executeSQL(sql);
							if (planids != null && planids.size() > 0) {

								String planid = planids.get(0).toString();
								sql = "update pe_course_plan c set ";
								switch (i) {
								case 0:
									sql += "c.FIRSTVOTE = c.FIRSTVOTE+1";
									break;
								case 1:
									sql += "c.SECONDVOTE = c.SECONDVOTE+1";
									break;
								case 2:
									sql += "c.THIRDVOTE = c.THIRDVOTE+1";
									break;
								case 3:
									sql += "c.FOUTHVOTE = c.FOUTHVOTE+1";
									break;
								case 4:
									sql += "c.FIFTHVOTE = c.FIFTHVOTE+1";
									break;

								}
								sql += " where c.id = '" + planid + "'";

								questionService.executeSQLUpdate(sql);

							}

						}
						UUID uuid = UUID.randomUUID();
						String vid = uuid.toString().replaceAll("-", "");

						sql = "insert into pr_vote_record(id,ip,USER_SESSION,CLASS_IDENTIFIER,CONTENT,TEMP2) values(";
						sql += "'" + vid + "',";
						sql += "'" + ip + "',";
						sql += "'" + session.getId() + "',";
						// sql += "to_date('" + new Date().toLocaleString() +
						// "','YYYY-MM-DD HH24:MI:SS'),";
						sql += "'" + classId + "',";
						sql += "'" + voteValue + "',";
						sql += "'" + loginkey + "')";

						questionService.executeSQLUpdate(sql);
						System.out.print("教师排名入库成功！");

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("1000〒成功提交问卷，感谢您的参与！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 改变投票状态
	 */
	public void status() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);
		String status = request.getParameter("s");

		/*
		 * String uid = (String)request.getSession().getAttribute("ID");//
		 * "GP000100300101"; List<Paper> ps =
		 * paperService.getListForPage("from Paper where creator ='" + uid +
		 * "' and status = 2", 0, 100); for(Paper p: ps){ p.setStatus((short)3);
		 * paperService.update(p); }
		 */

		Paper pp = paperService.get(id);
		pp.setStatus(Short.parseShort(status));

		paperService.update(pp);

		if (status.equals("0")) {
			// 删除投票关联的项目
			ppmService.deleteByPaperId(id);
		}

		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("true");
		} catch (Exception e) {
		}
	}

	/**
	 * 返回剩下的项目名称
	 */
	public void projects() {

		String ret = "";
		List<ProjectAppNo> ps = projectAppNoService.getListForPage("from ProjectAppNo", 0, 1000);
		List<PsqProjectMap> ppm = ppmService.getListForPage("from PsqProjectMap", 0, 1000);

		List<ProjectAppNo> leftList = ps;

		for (int i = 0; i < ppm.size(); i++) {
			PsqProjectMap pm = ppm.get(i);
			for (int j = 0; j < ps.size(); j++) {
				ProjectAppNo p = ps.get(j);
				if (p.getId() == pm.getProject().getId()) {
					leftList.remove(j);
				}
			}
		}

		for (ProjectAppNo p : leftList) {
			ret += "<li onclick=\"addP('" + p.getId() + "', this)\">" + p.getName() + "</li>";
		}

		try {
			// HttpServletResponse response =
			// ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			// System.out.println("ret ......... " + ret);
			ret = ret.equals("") ? "false" : ret;
			response.getWriter().write(ret);
		} catch (Exception e) {
		}
	}

	/**
	 * 添加/删除问卷项目
	 * 
	 * @return
	 */
	public void updateproject() {

		// HttpServletRequest request = ServletActionContext.getRequest();
		String qid = request.getParameter("qid");
		String pid = request.getParameter("pid");
		String action = request.getParameter("action");

		if (action.equals("add")) {

			String sql = "from PsqProjectMap where project = '" + pid + "' and psq = " + qid;

			int count = ppmService.queryRowCount(sql);

			if (count == 0) {

				ProjectAppNo p = projectAppNoService.get(pid);

				int qqid = Integer.parseInt(qid);
				Paper q = paperService.get(qqid);

				PsqProjectMap ppm = new PsqProjectMap();
				ppm.setPaper(q);
				ppm.setProject(p);
				ppm.setStatus(0);

				ppmService.add(ppm);

				try {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("true");
				} catch (Exception e) {
				}
			} else {
				try {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("false");
				} catch (Exception e) {
				}
			}
		} else if (action.equals("del")) {

			List<PsqProjectMap> pms = ppmService.getListForPage("from PsqProjectMap where project = '" + pid + "' and psq=" + qid, 0, 1);
			if (pms != null & pms.size() == 1) {
				PsqProjectMap ppm = pms.get(0);

				String sql = "select count(*) from result r join loginkey l on r.loginkey=l.id join pe_trainee pe on l.traineeid = pe.id  where pe.fk_pro_applyno='"
						+ pid + "' and l.psqmapid=" + qid;

				List lia = questionService.executeSQL(sql);
				int flag = Integer.parseInt(lia.get(0).toString());
				if (flag > 0) {
					try {
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("false");
					} catch (Exception e) {
					}

				} else {
					ppmService.delete(ppm);
					try {
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("true");
					} catch (Exception e) {
					}
				}
			}

		}
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getSurveydata() {
		return surveydata;
	}

	public void setSurveydata(String surveydata) {
		this.surveydata = surveydata;
	}

	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}

	public int getPaperID() {
		return paperID;
	}

	public void setPaperID(int paperID) {
		this.paperID = paperID;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public String getQstr() {
		return qstr;
	}

	public void setQstr(String qstr) {
		this.qstr = qstr;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
