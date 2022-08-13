package com.zeppin.action;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

import com.opensymphony.xwork2.ActionSupport;
import com.zeppin.model.Answer;
import com.zeppin.model.LoginKey;
import com.zeppin.model.Paper;
import com.zeppin.model.PsqProjectMap;
import com.zeppin.model.Question;
import com.zeppin.model.Submit;
import com.zeppin.model.peProApplyNo;
import com.zeppin.service.AnswerService;
import com.zeppin.service.LoginKeyService;
import com.zeppin.service.PaperService;
import com.zeppin.service.PsqProjectMapService;
import com.zeppin.service.QuestionService;
import com.zeppin.service.ResultService;
import com.zeppin.service.SubmitService;
import com.zeppin.service.impl.peProApplyNoServiceImpl;
import com.zeppin.util.cryptogram.generatePwd;
import com.zeppin.util.sms.SendSms4Zzx;

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
	private SubmitService submitService;

	@Autowired
	private peProApplyNoServiceImpl peProApplyNoService;

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

	public PaperAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
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
	
	public void UpdateSubmit(){
		
		Object updateStatus = session.getAttribute("updateSubmint");
		if(updateStatus != null){
			String upStr = updateStatus.toString();
			if(upStr.equals("pedding")){
				try {
					response.setCharacterEncoding("GBK");
					response.getWriter().write("正在处理中");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				try {
					response.setCharacterEncoding("GBK");
					response.getWriter().write("处理成功");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			
			session.setAttribute("updateSubmint", "pedding");
			
			UpdateSubmit updates = new UpdateSubmit(session, submitService);
			ExecutorService ex = Executors.newFixedThreadPool(1);
			ex.execute(updates);
			
			try {
				response.setCharacterEncoding("GBK");
				response.getWriter().write("开始处理中");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		
	}
	

	public void doSendSms() {
		//
		try {
			String pids = (String) request.getParameter("pId"); // 登录密码
			String content = (String) request.getParameter("smsContent");
			String sign = (String) request.getParameter("sign");

			String[] listpids = pids.split(",");

			String msgformat = sign + content + " %s ";

			this.longinKeyService.sendSms(listpids, msgformat);

//			sendSmsServ sendsmc = new sendSmsServ("13811994936", msgformat+"——此条给管理人员发送，查看短信是否发送成功！");
//			sendsmc.sendSms();
//			String time = Utils.getBJTime();
//			SendSmsUtil sm = new SendSmsUtil("13811994936,", msgformat+"——此条给管理人员发送，查看短信是否发送成功！",time);
			SendSms4Zzx sm = new SendSms4Zzx("13811994936,", msgformat+"——此条给管理人员发送，查看短信是否发送成功！");
			sm.sendSms();
			
			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("处理结束");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();

			try {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("处理结束");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

	}

	public void doLookLoginKey() {

		// 用来提示是否提示登录码已经有生成是否要继续
		String paperId = (String) request.getParameter("paperid");

		Object lflag = session.getAttribute("dologin");

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
					e1.printStackTrace();
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
				e1.printStackTrace();
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
			e1.printStackTrace();
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

			PreparedStatement ps = conn.prepareStatement("insert into loginkey(id,psqmapid,status,creattime,telephone,TRAINEEID,PSQSTATUS) values(?,?,?,?,?,?,?)");
			int totalcount = 0;

			for (PsqProjectMap m : ppm) {

//				if (m.getStatus() == 1) {
//					continue;
//				}
				String parentid = m.getProject().getId();

				String sql = "select te.id,te.telephone from trainee te join prochildapplyno p on te.fk_applyno= p.id join pe_pro_applyno a on p.parentid = a.id Left outer join loginkey lk on te.id = lk.traineeid and lk.psqmapid=" + ipaperid + " where lk.traineeid is null and a.id='" + parentid + "'";

				List li = peProApplyNoService.executeSQL(sql);

				for (int i = 0; i < li.size(); i++) {
					Object[] obj = (Object[]) li.get(i);
					String traineeid = obj[0].toString();
					String telephone = obj[1] == null ? "" : obj[1].toString();

					ps.setString(1, genpwd.getPwd());
					ps.setInt(2, ipaperid);
					ps.setInt(3, 2);
					ps.setTimestamp(4, new Timestamp(new Date().getTime()));
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

				m.setStatus(1);
				ppmService.update(m);

			}

			conn.commit();
			ps.close();

			session.setAttribute(paperId, "ok");
			session.removeAttribute(paperId);
			session.setAttribute("dologin", "ok");

			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write("学员登录码生成成功,共处理了" + totalcount + "条数据");

		} catch (Exception e) {
			e.printStackTrace();
			try {

				session.setAttribute(paperId, "ok");
				session.removeAttribute(paperId);
				session.setAttribute("dologin", "ok");

				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace();
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

		String subProId = (String) request.getParameter("subproject");
		if (subProId == null || subProId.equals("")) {
			subProId = "all";
		}

		String provinceId = (String) request.getParameter("province");
		if (provinceId == null || provinceId.equals("")) {
			provinceId = "all";
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
		
		//ids
		String ids = (String) request.getParameter("ids");
		if (ids == null || ids.equals("")) {
			ids = "all";
		}
		
		//userName
		String userName = (String) request.getParameter("userName");
		if (userName == null || userName.equals("")) {
			userName = "all";
		}
		
		//mobile
		String mobile = (String) request.getParameter("mobile");
		if (mobile == null || mobile.equals("")) {
			mobile = "all";
		}

		session.setAttribute("proId", proId);
		session.setAttribute("subProId", subProId);
		session.setAttribute("provinceId", provinceId);
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

			String parentId = m.getProject().getId();

			String sqlcount = "select count(*) from LoginKey lk where lk.traineeId in (select e.id from trainee e join prochildapplyno pc on e.fk_applyno=pc.id join pe_pro_applyno pe on pc.parentid = pe.id where pe.id = '" + parentId + "'";

			if (!subId.equals("all")) {
				sqlcount += " and e.fk_subject='" + subId + "'";
			}
			if (!unitId.equals("all")) {
				sqlcount += " and e.fk_training_unit='" + unitId + "'";
			}

			if (!graduted.equals("all")) {
				//sqlcount += " and e.fk_graduted='isgraduateidflag" + graduted + "'";
				sqlcount += " and e.status=" + graduted + " ";
			}
			
			if(!"all".equals(mobile)){
				sqlcount += " and e.telephone='" + mobile + "'";
			}
			
			if(!"all".equals(userName)){
				sqlcount += " and e.name='" + userName + "'";
			}

			if (!provinceId.equals("all")) {
				sqlcount += " and e.fk_province='" + provinceId + "'";
			}

			sqlcount += ")";
			
			if(!"all".equals(ids)){
				sqlcount += " and lk.id='" + ids + "'";
			}

			sqlcount += " and lk.psqmapid=" + paperId;

			String stst = " and lk.status=" + status;

			String psqstst = " and lk.psqstatus=" + psqstatus;

			if (!status.equals("3")) {
				// 不是所有
				sqlcount += stst;
			}

			if (!psqstatus.equals("3")) {

				sqlcount += psqstst;

			}

			String pt = "{\"id\":\"" + m.getProject().getId() + "\",\"title\":\"" + m.getProject().getName() + "\"}";

			proJson += pt + ",";

			List objCount = peProApplyNoService.executeSQL(sqlcount);

			if (objCount.size() > 0) {
				totalCount += Integer.parseInt(objCount.get(0).toString());
			}

			String searchSql = "select l.id,e.name as username ,e.telephone,pa.name as projectname, pc.name as subprojectname,u.name as unitname ,s.name  as subjectname,l.status,l.psqstatus,e.fk_graduted,v.name,e.status as gradestatus ";
			searchSql += "from loginkey l join trainee e on l.traineeid = e.id ";
			searchSql += " join prochildapplyno pc on e.fk_applyno = pc.id ";
			searchSql += " join pe_pro_applyno pa on pc.parentid = pa.id ";
			searchSql += " join pe_unit u on e.fk_training_unit=u.id ";
			searchSql += " join pe_subject s on e.fk_subject = s.id ";
			searchSql += " join pe_province v on e.fk_province=v.id ";
			searchSql += "where pa.id='" + parentId + "' and l.psqmapid=" + paperId + " ";

			if (!subProId.equals("all")) {
				searchSql += " and pc.id='" + subProId + "' ";
			}

			if (!subId.equals("all")) {
				searchSql += " and s.id='" + subId + "' ";
			}

			if (!unitId.equals("all")) {
				searchSql += " and u.id='" + unitId + "' ";
			}

			if (!graduted.equals("all")) {
				//searchSql += " and e.fk_graduted='isgraduateidflag" + graduted + "' ";
				searchSql += " and e.status=" + graduted + " ";
			}
			if (!provinceId.equals("all")) {
				searchSql += " and e.fk_province='" + provinceId + "'";
			}
			
			if(!"all".equals(ids)){
				searchSql += " and l.id='" + ids + "'";
			}
			
			if(!"all".equals(mobile)){
				searchSql += " and e.telephone='" + mobile + "'";
			}
			
			if(!"all".equals(userName)){
				searchSql += " and e.name='" + userName + "'";
			}
			
			String sts = " and l.status=" + status;

			String psqsts = " and l.psqstatus=" + psqstatus;

			if (!status.equals("3")) {
				searchSql += sts;
			}

			if (!psqstatus.equals("3")) {
				searchSql += psqsts;
			}

			List searchList = limit == -1 ? questionService.executeSQL(searchSql) : questionService.getListPage(searchSql, start, limit);
			String loginJson = "";

			for (int i = 0; i < searchList.size(); i++) {
				Object[] sarray = (Object[]) searchList.get(i);
				String key = sarray[0].toString();
				try {
					key = gen.getSecString(key);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String username = sarray[1] == null ? "" : sarray[1].toString().trim();

				String tele = sarray[2] == null ? "" : sarray[2].toString().trim().replace('\\', ' ');

				String projectName = sarray[3] == null ? "" : sarray[3].toString().trim();
				String subprojectName = sarray[4] == null ? "" : sarray[4].toString().trim();
				String unitName = sarray[5] == null ? "" : sarray[5].toString().trim();
				String subjectName = sarray[6] == null ? "" : sarray[6].toString().trim();
				String teleStatus = sarray[7] == null ? "" : sarray[7].toString().trim();
				String psqStatus = sarray[8] == null ? "" : sarray[8].toString().trim();
				String gradutedStr = sarray[11] == null ? "" : sarray[11].toString().trim();
				String provinceName = sarray[10] == null ? "" : sarray[10].toString().trim();

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
				if (gradutedStr.equals("1")) {
					chgraduted = "未结业";
				} else if (gradutedStr.equals("2")) {
					chgraduted = "已结业";
				}

				String t = "{\"id\":\"" + key + "\",\"cell\":[\"" + key + "\",\"" + username + "\",\"" + tele + "\",\"" + projectName + "\",\"" + provinceName + "\",\"" + subprojectName + "\",\"" + unitName + "\",\"" + subjectName + "\",\"" + st + "\",\"" + psqst + "\",\"" + chgraduted + "\"]}";

				loginJson += t + ",";

			}

			retTmpJson += loginJson;

		}

		if (!retTmpJson.equals("")) {
			retTmpJson = retTmpJson.substring(0, retTmpJson.length() - 1);
		}
		if (proJson.length() > 0) {
			proJson = proJson.substring(0, proJson.length() - 1);

		}
		limit = limit == -1 ? totalCount : limit;
		int tol = totalCount / limit;
		if ((totalCount % limit) > 0) {
			tol += 1;
		}

		retJson = "{\"page\":\"" + start + "\",\"total\":\"" + tol + "\",\"records\":\"" + totalCount + "\",\"rows\":[" + retTmpJson + "],\"userdata\":{\"paperid\":" + paperId + ",\"project\":[" + proJson + "]}}";

		retJson = retJson.replace("\r", "");
		retJson = retJson.replace("\n", "");
		retJson = retJson.replace("\r\n", "");

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Search
	public void doHeadSearch() {
		String paperId = (String) request.getParameter("paperid");

		List<PsqProjectMap> ppm = ppmService.getListForPage("from PsqProjectMap where PSQ = " + paperId, 0, 100);

		String projectJson = "{\"id\":\"all\",\"title\":\"所有\"},";
		String subProjectJson = "{\"id\":\"all\",\"title\":\"所有\"},";
		String provinceJson = "{\"id\":\"all\",\"title\":\"所有\"},";
		String trainJson = "{\"id\":\"all\",\"title\":\"所有\"},";
		String subJson = "{\"id\":\"all\",\"title\":\"所有\"},";

		Hashtable<String, String> trainhash = new Hashtable<String, String>();
		Hashtable<String, String> subjecthash = new Hashtable<String, String>();
		Hashtable<String, String> provincehash = new Hashtable<String, String>();
		Hashtable<String, String> projecthast = new Hashtable<String, String>();

		for (PsqProjectMap m : ppm) {

			String parentid = m.getProject().getId();

			String pt = "{\"id\":\"" + m.getProject().getId() + "\",\"title\":\"" + m.getProject().getName() + "\"}";

			projectJson += pt + ",";

			String sql = "select t.id,t.name from prochildapplyno t where t.parentid='" + parentid + "'";

			List sproject = peProApplyNoService.executeSQL(sql);

			if (sproject != null && sproject.size() > 0) {

				for (int i = 0; i < sproject.size(); i++) {

					Object[] obj = (Object[]) sproject.get(i);
					String key = obj[0].toString();
					String name = obj[1].toString();
					if (!projecthast.containsKey(key)) {
						projecthast.put(key, name);
					}
				}

			}

			String psql = "select distinct p.id,p.name from PROCHILDAPPLYNO t join pe_province  p on t.provinceid = p.id  where t.parentid='" + parentid + "'";

			List provilist = peProApplyNoService.executeSQL(psql);
			if (provilist != null && provilist.size() > 0) {
				for (int i = 0; i < provilist.size(); i++) {
					Object[] obj = (Object[]) provilist.get(i);
					String key = obj[0].toString();
					String tmpuname = obj[1].toString();
					if (!provincehash.containsKey(key)) {
						provincehash.put(key, tmpuname);
					}
				}
			}

			String sql1 = "select distinct te.fk_training_unit,u.name " + "from trainee te join prochildapplyno p on te.fk_applyno= p.id join pe_pro_applyno a on p.parentid = a.id join pe_unit u on te.fk_training_unit = u.id " + " where a.id='" + parentid + "'";

			List objListTrain = peProApplyNoService.executeSQL(sql1);

			if (objListTrain != null && objListTrain.size() > 0) {
				for (int i = 0; i < objListTrain.size(); i++) {
					Object[] obj = (Object[]) objListTrain.get(i);
					String key = obj[0].toString();
					String tmpuname = obj[1].toString();
					if (!trainhash.containsKey(key)) {
						trainhash.put(key, tmpuname);
					}

				}
			}

			String sql2 = "select distinct te.fk_subject,u.name from trainee te join prochildapplyno p on te.fk_applyno= p.id join pe_pro_applyno a on p.parentid = a.id join pe_subject u on te.fk_subject = u.id" + " where a.id='" + parentid + "'";

			List objListsubject = peProApplyNoService.executeSQL(sql2);

			if (objListsubject != null && objListsubject.size() > 0) {
				for (int i = 0; i < objListsubject.size(); i++) {
					Object[] obj = (Object[]) objListsubject.get(i);
					String key = obj[0].toString();
					String tmpname = obj[1].toString();
					if (!subjecthash.containsKey(key)) {
						subjecthash.put(key, tmpname);
					}
				}
			}
		}

		Enumeration et = trainhash.keys();

		while (et.hasMoreElements()) {
			String key = et.nextElement().toString();
			String value = trainhash.get(key);
			String te = "{\"id\":\"" + key + "\",\"title\":\"" + value + "\"}";
			trainJson += te + ",";
		}

		Enumeration proet = provincehash.keys();

		while (proet.hasMoreElements()) {
			String key = proet.nextElement().toString();
			String value = provincehash.get(key);
			String te = "{\"id\":\"" + key + "\",\"title\":\"" + value + "\"}";
			provinceJson += te + ",";
		}

		Enumeration st = subjecthash.keys();

		while (st.hasMoreElements()) {
			String key = st.nextElement().toString();
			String value = subjecthash.get(key);
			String te = "{\"id\":\"" + key + "\",\"title\":\"" + value + "\"}";
			subJson += te + ",";
		}

		Enumeration pt = projecthast.keys();

		while (pt.hasMoreElements()) {
			String key = pt.nextElement().toString();
			String value = projecthast.get(key);
			String te = "{\"id\":\"" + key + "\",\"title\":\"" + value + "\"}";
			subProjectJson += te + ",";
		}

		projectJson = projectJson.substring(0, projectJson.length() - 1);
		trainJson = trainJson.substring(0, trainJson.length() - 1);
		subJson = subJson.substring(0, subJson.length() - 1);
		subProjectJson = subProjectJson.substring(0, subProjectJson.length() - 1);
		provinceJson = provinceJson.substring(0, provinceJson.length() - 1);

		String retJson = "{\"preInitData\":{\"project\":[" + projectJson + "],\"province\":[" + provinceJson + "],\"subproject\":[" + subProjectJson + "],\"trainingUnit\":[" + trainJson + "],\"course\":[" + subJson + "]}}";

		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(retJson);

		} catch (Exception e) {
			e.printStackTrace();
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

		// System.out.println("curID...... " + curID);
		int icurID = Integer.parseInt(curID);
		Paper paper = paperService.get(icurID);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		// System.out.println(sdf.format(paper.getCreatTime()));

		this.surveydata = sdf.format(paper.getCreatTime()) + "§" + paper.getSurveydata() + "?false§" + paper.getCreator() + "§0§q_12px.css§False§null";

		// System.out.println(this.surveydata);

		return "edit";
	}

	public void save() {

		HttpServletRequest request = ServletActionContext.getRequest();

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
			paper.setProjectType(1);

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
		//像下面这行执行效率差好几个量级的代码就不要写了！
//		String sql = "select distinct paper.id from Result result, Paper paper, Question question where result.question = question.id and question.paper = paper.id";
		List<Object> res = resultService.findByHSQL(sql);

		for (Iterator<Object> iter = res.iterator(); iter.hasNext();) {
			String record = iter.next().toString();
			cannotEditPaperID += record + ",";
		}

		sql = "from Paper where 1=1 and projectType=1  and status <> 0 order by id desc";

		List<Paper> paperList = paperService.getListForPage(sql, 0, 2000);

		sql = "from PsqProjectMap";
		List<PsqProjectMap> pms = ppmService.getListForPage(sql, 0, 2000);

		this.paperList = new ArrayList<Paper>();
		for (Paper p : paperList) {

			List<peProApplyNo> pls = new ArrayList<peProApplyNo>();
			for (PsqProjectMap pm : pms) {

				if (p.getId() == pm.getPaper().getId()) {
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
		paper.setProjectType(1);
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
//		if (!PageResultSet.isNumeric(sid)){
//			return LOGIN;
//		}
		int id = Integer.parseInt(sid);
		String t = request.getParameter("t");
		this.uid = (String) session.getAttribute("ID");

		//paperService.get(id);
		
		// System.out.println("view page ==== " + id);
		this.paper = paperService.get(id);
		

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
		long start = System.currentTimeMillis(); 
		System.out .println("-------------------------------开始提交问卷------------------------");
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
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select ").append(" te.fk_province,apply.parentid,apply.id as pid ,")
				.append(" te.fk_subject,te.fk_training_unit ")
				.append(" from TRAINEE te,PROCHILDAPPLYNO apply ")
				.append(" where  te.fk_applyno= apply.id ")
				.append(" and te.id ='").append(lk.getTraineeId()).append("'");
			
			List lste = longinKeyService.executeSQL(sb.toString());
			
			
			long end1 = System.currentTimeMillis();  
			System.out.println("运行1时间："+(start-end1)+"毫秒");
			
			String province = "";
			String project = "";
			String childProject="";
			String subject="";
			String unit="";
			if(lste!=null && !lste.isEmpty()){
				try
				{
					Object[] obj = (Object[]) lste.get(0);
					province = obj[0].toString();
					project = obj[1].toString();
					childProject = obj[2].toString();
					subject = obj[3].toString();
					unit  = obj[4].toString();
				}catch(Exception ex){
					ex.printStackTrace();
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
			
			submit.setProvince(province);
			submit.setProject(project);
			submit.setChildProject(childProject);
			submit.setSubject(subject);
			submit.setUnit(unit);
			
			submit = submitService.add(submit);

			// 验证投票人数结束

			String[] datas = submitdata.split("}");

			resultService.addResult(datas, paper_id, lk, submit);

			lk.setPsgStatus(1);
			longinKeyService.update(lk);
			
			
			long end2 = System.currentTimeMillis();  
			System.out.println("运行2时间："+(start-end2)+"毫秒");
			

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("1000〒成功提交问卷，感谢您的参与！");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out .println("-------------------------------结束提交问卷------------------------");
	}

	/**
	 * 改变投票状态
	 */
	public void status() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);
		String status = request.getParameter("s");

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
			e.printStackTrace();
		}
	}

	/**
	 * 返回剩下的项目名称
	 */
	public void projects() {

		String ret = "";
		List<peProApplyNo> ps = peProApplyNoService.getListForPage("from peProApplyNo order by year desc", 0, -1);
		List<PsqProjectMap> ppm = ppmService.getListForPage("from PsqProjectMap", 0, -1);

		List<peProApplyNo> leftList = ps;

		for (int i = 0; i < ppm.size(); i++) {
			PsqProjectMap pm = ppm.get(i);
			for (int j = 0; j < ps.size(); j++) {
				peProApplyNo p = ps.get(j);
				if (p.getId() == pm.getProject().getId()) {
					leftList.remove(j);
				}
			}
		}

		for (peProApplyNo p : leftList) {
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
			e.printStackTrace();
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

				peProApplyNo p = peProApplyNoService.get(pid);

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
					e.printStackTrace();
				}
			} else {
				try {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("false");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (action.equals("del")) {

			List<PsqProjectMap> pms = ppmService.getListForPage("from PsqProjectMap where project = '" + pid + "' and psq=" + qid, 0, 1);
			if (pms != null & pms.size() == 1) {
				PsqProjectMap ppm = pms.get(0);

				String sql = "select count(*) from result r join loginkey l on r.loginkey=l.id join trainee pe on l.traineeid = pe.id  where pe.fk_applyno='" + pid + "' and l.psqmapid=" + qid;

				List lia = questionService.executeSQL(sql);
				int flag = Integer.parseInt(lia.get(0).toString());
				if (flag > 0) {
					try {
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("false");
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					ppmService.delete(ppm);
					try {
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("true");
					} catch (Exception e) {
						e.printStackTrace();
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
