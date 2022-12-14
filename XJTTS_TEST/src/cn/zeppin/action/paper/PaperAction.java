package cn.zeppin.action.paper;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Answer;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Psq;
import cn.zeppin.entity.Question;
import cn.zeppin.entity.Result;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.Submit;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeachingGrade;
import cn.zeppin.entity.TeachingLanguage;
import cn.zeppin.entity.TeachingSubject;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IAnswerService;
import cn.zeppin.service.IProjectService;
import cn.zeppin.service.IPsqService;
import cn.zeppin.service.IQuestionService;
import cn.zeppin.service.IResultService;
import cn.zeppin.service.ISubjectService;
import cn.zeppin.service.ISubmitService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.service.ITrainingSubjectService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.DictionyMap.ROLEENUM;

import com.opensymphony.xwork2.ActionSupport;

public class PaperAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(PaperAction.class);

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IProjectService iProjectService; // ????????????
	private IAnswerService iAnswerService;
	private IQuestionService iQuestionService;
	private IPsqService iPsqService;
	private ISubmitService iSubmitService;
	private IResultService iResultService;
	private ITrainingSubjectService iTrainingSubjectService;
	private ITrainingCollegeService iTrainingCollegeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecords;

	private int paperID;
	private int psqtype;
	private String qstr;
	private String uid;
	private String surveydata;
	private Psq paper;
	
	private int valuator;
	private int projectid;
	private short subjectid;
	private int trainingCollege;
	private int type;
	private int psq;

	private List<Psq> learnList;
	private List<Psq> teachList;
	private List<Psq> reviewList;
	private HashMap<Integer, Object> resultHashMap;
	private String paperView;

	// ?????????????????????
	private List<Project> searchReportTask;

	public PaperAction() {
	}

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	public String choosePaper() {

		initServlert();

		String id = request.getParameter("id");
		if (id != null) {

			TeacherTrainingRecords ttr = this.getiTeacherTrainingRecords().get(Integer.valueOf(id));

			if (ttr != null) {
				// ???????????????????????????
				Teacher t = ttr.getTeacher();

				// ????????????????????????
				UserSession us = new UserSession();
				// ????????? session???
				us.setId(t.getId());
				us.setIdcard(t.getIdcard());
				us.setMobile(t.getMobile());
				us.setEmail(t.getEmail());
				us.setRole((short) 3);
				us.setName(t.getName());
				us.setOrganization(t.getOrganization().getId());

				Organization oz = t.getOrganization();
				if (oz != null) {
					us.setOrganizationLevel(oz.getOrganizationLevel().getLevel());
					us.setSchool(oz.getIsschool());
				}

				us.setCreateuser(0);
				us.setLevel(0);
				us.setStatus(t.getStatus());
				session.setAttribute("papersession", us);
				session.setAttribute("paperUuid", ttr.getUuid());

				valuator = t.getId();
				projectid = ttr.getProject().getId();
				subjectid = ttr.getTrainingSubject().getId();
				this.trainingCollege = ttr.getTrainingCollege().getId();
				this.type = 3;
				if (ttr.getProject().getPsqByEvaluationTrainingPsq() != null) {
					this.psq = ttr.getProject().getPsqByEvaluationTrainingPsq().getId();
				} else {
					this.psq = 0;
				}
				return "paper";

			} else {
				return "novote";
			}

		} else {
			return "novote";
		}

	}

	/**
	 * ???????????? ??????3??? 1.???????????? 2.????????? 3.?????????
	 * 
	 * @return
	 */
	public String list() {

		/**
		 * ??????????????????????????? ?????????????????????????????????????????????
		 */

		initServlert();

		// ???????????????????????? ????????????
		String type = request.getParameter("type");
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN") || ist.equals("0")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);
		int offset = (start - 1) * DictionyMap.maxPageSize;
		int length = DictionyMap.maxPageSize;

		// ??????????????????????????????
		short stype = Short.valueOf(type);

		switch (stype) {

		case 1:
			this.reviewList = this.getiPsqService().getPsqByTypePage(stype, offset, length);
			return "listexpert";
		case 2:
			this.teachList = this.getiPsqService().getPsqByTypePage(stype, offset, length);
			return "listteacher";
		case 3:
			this.learnList = this.getiPsqService().getPsqByTypePage(stype, offset, length);
			return "list";
		default:
			this.learnList = this.getiPsqService().getPsqByTypePage(stype, offset, length);
			return "list";
		}
	}

	/**
	 * ???????????? ??????3??? 1.???????????? 2.????????? 3.?????????
	 * 
	 * @return
	 */
	public String add() {

		/**
		 * ????????????????????????????????????????????????????????????????????????
		 */

		initServlert();

		// ???????????????????????? ????????????
		String type = request.getParameter("type");

		if (type == null) {
			// ??????type?????????????????????
		}

		int itype = Integer.valueOf(type);

		UserSession us = (UserSession) session.getAttribute("usersession");

		Psq paper = new Psq();
		paper.setTitle("??????????????????????????????[??????]");
		paper.setAbout("?????????????????????????????????");
		paper.setStatus((short) 1);
		paper.setType((short) itype);
		paper.setClosing("");
		paper.setGotourl("");
		paper.setCreator(us.getId());
		paper.setSurveydata("???????????????????????????????????????????????????????????????????0??0??0??false??false??0??page??1????0??????");
		paper.setTheway((short) 1);

		this.getiPsqService().add(paper);

		this.paperID = paper.getId();
		this.psqtype = itype;

		return "add";
	}

	/**
	 * ????????????
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String view() {

		initServlert();

		/**
		 * ?????????????????? ?????????????????? ?????????????????? ???????????????????????????????????????????????????????????????
		 */
		UserSession us = (UserSession) session.getAttribute("papersession");

		String temp = request.getParameter("t");
		if (temp != null || us == null) {
			// ??????????????????
			us = (UserSession) session.getAttribute("usersession");
		}

		String sid = request.getParameter("id");
		sid = sid == null ? "0" : sid;
		sid = sid.equals("") ? "0" : sid;

		int id = Integer.parseInt(sid);
		String papertype = request.getParameter("type");

		this.paper = this.getiPsqService().get(id);

		// ??????????????????????????????
		if (this.paper == null) {
			return "novote";
		} else {

			if (this.paper.getStatus() != 2) {
				// ????????????????????????????????????
				ROLEENUM ROLE = ROLEENUM.valueof(us.getRole());
				if (us != null && ROLE == ROLEENUM.ADMIN) {
					// ??????????????????
					// ???????????????????????????????????????
				} else {
					return "novote";
				}
			}
		}

		// ?????????????????????
		ROLEENUM ROLE = ROLEENUM.valueof(us.getRole());
		if (us != null && us.getId().intValue() == this.paper.getCreator().intValue() && ROLE == ROLEENUM.ADMIN) {
			this.uid = "success";
		} else {
			this.uid = "fail";
		}

		// ?????????????????????????????????
		if (this.uid.equals("fail")) {
			String valuator = request.getParameter("valuator");
			valuator = valuator == null ? "0" : valuator;
			int valuator_id = Integer.valueOf(valuator);

			String projectId = request.getParameter("pid");
			projectId = projectId == null ? "0" : projectId;
			int projectId_id = Integer.valueOf(projectId);

			String subjectId = request.getParameter("sid");
			subjectId = subjectId == null ? "0" : subjectId;
			short subjectId_id = Short.valueOf(subjectId);

			String trainingCollege = request.getParameter("tc");
			trainingCollege = trainingCollege == null ? "0" : trainingCollege;
			int trainingCollege_id = Integer.valueOf(trainingCollege);

			Submit submit = this.getiSubmitService().getSubmitByAll(valuator_id, id, projectId_id, subjectId_id, trainingCollege_id, us.getId());

			if (submit != null) {
				// ?????????????????????
				if (papertype != null && !papertype.equals(DictionyMap.PSQ_TYPE_TRAINING)) {
					this.paperView = "edit";
					resultHashMap = new HashMap<>();
					for (Result r : submit.getResults()) {
						int quid = r.getQuestion().getId();
						if (resultHashMap.containsKey(quid)) {

							HashMap<Integer, Object> ht = (HashMap<Integer, Object>) resultHashMap.get(quid);
							if (r.getQuestion().getType() == 6) {
								ht.put(0, r.getContent());
							} else {
								ht.put(r.getAnswer().getId(), r.getAnswer().getId());
							}

						} else {

							HashMap<Integer, Object> ht = new HashMap<>();
							if (r.getQuestion().getType() == 6) {
								ht.put(0, r.getContent());
							} else {
								ht.put(r.getAnswer().getId(), r.getAnswer().getId());
							}
							resultHashMap.put(r.getQuestion().getId(), ht);
						}
					}

				} else {
					return "okvote";
				}
			} else {
				this.paperView = "add";
			}

		}

		String ret = "false??false??page??1????????";

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

			qstr += type + "??" + inx + "??" + arrange + "??true??false??0??" + requir + "??" + cp + "??0??" + q.getScale() + "??????";
			String anstr = "";
			Set<Answer> as = q.getAnswers();

			int j = 1;
			for (Answer a : as) {

				if (itype == 4) {
					anstr += "false???" + a.getInx() + "???" + a.getJump() + "???";
				} else {
					anstr += "false???" + a.getInx() + "???" + a.getJump();
				}
				if (j < as.size()) {
					anstr += "??";
				}
				j++;
			}
			qstr += anstr;

			if (i < qs.size() - 1) {
				qstr += "??";
			}
			i++;

			this.qstr = ret + qstr;
		}

		return "view";
	}

	/**
	 * ????????????
	 * 
	 * @return
	 */
	public String edit() {

		initServlert();
		String paperId = request.getParameter("curid");

		int ipaperId = Integer.parseInt(paperId);

		Psq paper = iPsqService.get(ipaperId);

		// ????????????????????????????????????
		// ????????????????????????????????????????????????

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		this.surveydata = sdf.format(paper.getCreattime()) + "??" + paper.getSurveydata() + "?false??" + paper.getCreator() + "??0??q_12px.css??False??null";

		return "edit";
	}

	/**
	 * ????????????
	 */
	public void save() {

		initServlert();
		boolean result = false;
		// ??????ID
		String curID = request.getParameter("curID");
		String surveydata = request.getParameter("surveydata");

		String[] b = surveydata.split("??");

		String[] a = b[0].split("??");

		// ???????????????
		int icurID = Integer.parseInt(curID);
		Psq paper = this.getiPsqService().get(icurID);

		if (paper != null) {
			paper.setTitle(a[0]);
			paper.setAbout(a[1]);
			paper.setSurveydata(surveydata);
			
			paper = this.getiPsqService().update(paper);

			// ??????????????????????????????
			this.getiAnswerService().deleteByPaperId(paper.getId());
			// ???????????????????????????
			this.getiQuestionService().deleteByPaperId(paper.getId());

			for (int d = 1; d < b.length; d++) {// ??????????????????????????????

				String oneQ = b[d];

				String[] Qo = oneQ.split("??");

				String type = Qo[0];
				int index = Integer.parseInt(Qo[1]);
				String title = Qo[2];

				int arrange = -1;
				String isTongji = "false";
				if (type.equals("radio") || type.equals("check")) {
					arrange = Integer.parseInt(Qo[4].split("???")[0]);
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

				question.setPsq(paper);
				question.setName(title.split("???")[0]);
				question.setInx(index);
				question.setArrange((short) arrange);
				question.setType(itype);
				question.setScale(scale);
				question.setHint("");
				question.setIsCount(isTongji.equals("true") ? true : false);
				question.setIsmust(true);
				question = this.getiQuestionService().add(question);

				if (type.equals("radio") || type.equals("check") || type.equals("question")) {

					int x = 1;

					for (int j = 14; j < Qo.length; j++) {
						option = Qo[j];
						String[] answers = option.split("???");
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
							answer_title = "$$?????????$$";// ???????????????????????????
							j = Qo.length;
						}
						Answer answer = new Answer();
						answer.setPsq(paper);
						answer.setQuestion(question);
						answer.setInx(x);
						answer.setName(answer_title);
						answer.setScore(score);
						answer.setAbout(about);
						answer.setJump(jump);
						answer.setIsdefault(false);
						answer.setIsright(false);

						this.getiAnswerService().add(answer);

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
	 * ???????????? ????????? 1.???????????? 2.????????? 3.?????????
	 * 
	 * @return
	 */
	public void process() {

		try {

			initServlert();

			UserSession us = null;

			String paper_id = request.getParameter("curID");
			String submitdata = request.getParameter("submitdata");

			String valuator = request.getParameter("valuator");
			int valuator_id = Integer.valueOf(valuator);

			String projectId = request.getParameter("pid");
			int projectId_id = Integer.valueOf(projectId);

			String subjectId = request.getParameter("sid");
			short subjectId_id = Short.valueOf(subjectId);

			String trainingCollege = request.getParameter("tc");
			int trainingCollege_id = Integer.valueOf(trainingCollege);

			String type = request.getParameter("type");
			if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {
				us = (UserSession) session.getAttribute("papersession");
			} else {
				us = (UserSession) session.getAttribute("usersession");
			}

			int pp_id = Integer.parseInt(paper_id);
			Psq paper = this.getiPsqService().get(pp_id);

			// ????????????????????????????????????????????????

			// ??????submit???
			Submit submit = this.getiSubmitService().getSubmitByAll(valuator_id, pp_id, projectId_id, subjectId_id, trainingCollege_id, us.getId());

			if (submit == null) {
				submit = new Submit();

				submit.setValuator(valuator_id);
				submit.setCreater(us.getId());

				submit.setPsq(paper);

				Project p = this.getiProjectService().get(projectId_id);
				submit.setProject(p);

				TrainingSubject ts = this.getiTrainingSubjectService().get(subjectId_id);
				submit.setTrainingSubject(ts);

				TrainingCollege tc = this.getiTrainingCollegeService().get(trainingCollege_id);
				submit.setTrainingCollege(tc);

				String ip = getIpAddr(request);
				submit.setIp(ip);

				if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {
					String uuid = session.getAttribute("paperUuid").toString();
					submit.setUuid(uuid);
				}

				this.getiSubmitService().add(submit);

			} else {
				// ????????????

				if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("1000???????????????????????????,???????????????????????????");
					return;
				} else {
					// ????????????submit
					this.getiSubmitService().delete(submit);

					submit = new Submit();

					submit.setValuator(valuator_id);
					submit.setCreater(us.getId());

					submit.setPsq(paper);

					Project p = this.getiProjectService().get(projectId_id);
					submit.setProject(p);

					TrainingSubject ts = this.getiTrainingSubjectService().get(subjectId_id);
					submit.setTrainingSubject(ts);

					TrainingCollege tc = this.getiTrainingCollegeService().get(trainingCollege_id);
					submit.setTrainingCollege(tc);

					String ip = getIpAddr(request);
					submit.setIp(ip);

					if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {
						String uuid = session.getAttribute("paperUuid").toString();
						submit.setUuid(uuid);
					}

					this.getiSubmitService().add(submit);
				}
			}

			// ??????????????????
			int teacherTotal = 0;
			int anscount = 0;

			String[] datas = submitdata.split("}");

			for (String data : datas) {
				String[] ds = data.split("\\$");

				String inx = (data.split("\\$"))[0];
				String ans = "";
				if (ds.length > 1) {
					ans = (data.split("\\$"))[1];
				}

				String qid = "";

				String sql = "SELECT id FROM question WHERE psq = " + paper_id + " and inx = " + inx + " and type <> 0";
				List qs = this.getiQuestionService().executeSQL(sql, null);

				if (qs != null && qs.size() == 1) {
					qid = qs.get(0) + "";
					String[] as = ans.split("\\|");
					for (String a : as) {
						String contentString = "";
						String aid = "";
						int score = 0;
						boolean isWord = false;

						try {
							int temp = Integer.parseInt(a);// ????????????,????????????

							sql = "SELECT id, score FROM answer WHERE psq = " + paper_id + " and inx = " + a + " and question =" + qid;
						} catch (Exception ex) {
							// ??????????????????
							contentString = a;
							isWord = true;
							sql = "SELECT id, score FROM answer WHERE psq = " + paper_id + " and name='$$?????????$$'" + " and question =" + qid;
						}

						List as1 = this.getiQuestionService().executeSQL(sql, null);

						if (as1 != null && as1.size() > 0) {
							for (int i = 0; i < as1.size(); i++) {

								Object[] objects = (Object[]) as1.get(i);
								aid = objects[0] + "";
								score = Integer.parseInt(objects[1] + "");
								Result res = new Result();
								int qqid = Integer.parseInt(qid);
								res.setQuestion(this.getiQuestionService().get(qqid));
								int aaid = Integer.parseInt(aid);
								res.setAnswer(this.getiAnswerService().get(aaid));

								res.setScore(score);
								res.setSubmit(submit);
								res.setContent(contentString);

								this.getiResultService().add(res);
								if (score > 0) {
									teacherTotal += score;
									anscount += 1;
								}
							}
						}

					}
				}
			}

			// ????????????????????????????????????????????????????????????????????????????????????
			if (type.equals(DictionyMap.PSQ_TYPE_TEACHER)) {
				// ?????????
				// ?????? ??????id ??????id ?????????id ????????????id ????????? ??????????????????

				TeacherTrainingRecords ttr = this.getiTeacherTrainingRecords().getTeacherTrainingRecord(projectId, subjectId, trainingCollege, valuator);

				float f = (float) teacherTotal;

				BigDecimal big = new BigDecimal(f);
				f = big.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

				ttr.setTrainingScore(f);
				ttr.setRegistrant(us.getId());
				ttr.setRegisttime(new Timestamp(new Date().getTime()));

				this.getiTeacherTrainingRecords().update(ttr);

			} else if (type.equals(DictionyMap.PSQ_TYPE_EXPERT)) {
				// ?????????
			}

			response.setCharacterEncoding("UTF-8");
			if(us.getRole()==4){
				response.getWriter().write("1000?????????????????????????????????????????????");
			}else{
				response.getWriter().write("1000?????????????????????????????????????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ????????????
	 */
	public void changeStatus() {
		/**
		 * 1.?????? 2.?????? 3.??????
		 */

		initServlert();

		String paperId = request.getParameter("id");
		String status = request.getParameter("s");
		String type = request.getParameter("t");
		int ipaperId = Integer.valueOf(paperId);
		short sstatus = Short.valueOf(status);

		Psq paper = this.getiPsqService().get(ipaperId);

		StringBuilder sb = new StringBuilder();
		sb.append("{");

		if (status.equals(DictionyMap.PSQ_DEL)) {
			// ???????????????????????????????????????????????????????????????????????????????????????????????????
			if (paper.getStatus().toString().equals(DictionyMap.PSQ_RELEASE)) {
				// ????????????
				sb.append("\"status\":false");
				sb.append(",");
				sb.append("\"message\":\"" + "???????????????????????????????????????" + "\"");

			} else {
				if (paper.getSubmits().size() > 0) {
					// ????????????????????????
					sb.append("\"status\":false");
					sb.append(",");
					sb.append("\"message\":\"" + "?????????????????????????????????????????????" + "\"");

				} else {
					// ??????????????????
					// ??????
					if (paper.getProjectsForProjectJudgePsq().size() > 0) {

						sb.append("\"status\":false");
						sb.append(",");
						sb.append("\"message\":\"" + "??????????????????????????????????????????" + "\"");

					} else if (paper.getProjectsForEvaluationTeacherPsq().size() > 0) {

						sb.append("\"status\":false");
						sb.append(",");
						sb.append("\"message\":\"" + "??????????????????????????????????????????" + "\"");

					} else if (paper.getProjectsForEvaluationTrainingPsq().size() > 0) {

						sb.append("\"status\":false");
						sb.append(",");
						sb.append("\"message\":\"" + "??????????????????????????????????????????" + "\"");

					} else {

						this.getiPsqService().delete(paper);

						sb.append("\"status\":true");
						sb.append(",");
						sb.append("\"message\":\"" + "???????????????" + "\"");
					}
				}
			}
		} else {
			// ????????????

			if (status.equals(DictionyMap.PSQ_RELEASE)) {

				// ??????
				boolean flag = true;
				if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {

					if (paper.getProjectsForEvaluationTrainingPsq().size() == 0) {
						flag = false;
					}

				} else if (type.equals(DictionyMap.PSQ_TYPE_TEACHER)) {

					if (paper.getProjectsForEvaluationTeacherPsq().size() == 0) {
						flag = false;
					}

				} else if (type.equals(DictionyMap.PSQ_TYPE_EXPERT)) {

					if (paper.getProjectsForProjectJudgePsq().size() == 0) {
						flag = false;
					}

				}

				if (flag) {
					paper.setStatus(sstatus);
					this.getiPsqService().update(paper);

					sb.append("\"status\":true");
					sb.append(",");
					sb.append("\"message\":\"" + "???????????????" + "\"");
				} else {
					sb.append("\"status\":false");
					sb.append(",");
					sb.append("\"message\":\"" + "?????????????????????????????????" + "\"");
				}

			} else if (status.equals(DictionyMap.PSQ_STOP)) {
				paper.setStatus(sstatus);
				this.getiPsqService().update(paper);

				sb.append("\"status\":true");
				sb.append(",");
				sb.append("\"message\":\"" + "???????????????" + "\"");
			}
		}

		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	/**
	 * ???????????????????????????
	 */
	public void getPsqProjects() {

		initServlert();
		// ???????????????????????????
		// ???????????????????????? ????????????
		String type = request.getParameter("type");

		List<Project> lips = this.getiProjectService().getProjectByTypes(type);

		StringBuilder sb = new StringBuilder();
		sb.append("[");

		if (lips != null && lips.size() > 0) {
			for (Project p : lips) {
				String sr = "{\"id\":" + p.getId().toString() + ",\"name\":\"" + p.getName() + "\"}";
				sb.append(sr);
				sb.append(",");
			}

			sb.delete(sb.length() - 1, sb.length());
		}

		sb.append("]");
		Utlity.ResponseWrite(sb.toString(), "application/json", response);
	}

	/**
	 * ?????????????????????????????????
	 */
	public void changePsqProject() {
		initServlert();
		try {
			String paperId = request.getParameter("psqid");
			String status = request.getParameter("s");
			String type = request.getParameter("type");
			String projectId = request.getParameter("projectid");
			int pp_id = Integer.valueOf(projectId);
			Project p = this.getiProjectService().get(pp_id);

			int p_id = Integer.valueOf(paperId);
			Psq pq = this.getiPsqService().get(p_id);

			if (status.equals(DictionyMap.PSQ_PROJECT_ADD)) {

				if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {

					p.setPsqByEvaluationTrainingPsq(pq);

				} else if (type.equals(DictionyMap.PSQ_TYPE_TEACHER)) {

					p.setPsqByEvaluationTeacherPsq(pq);

				} else if (type.equals(DictionyMap.PSQ_TYPE_EXPERT)) {

					p.setPsqByProjectJudgePsq(pq);

				}

			} else if (status.equals(DictionyMap.PSQ_PROJECT_DEL)) {

				if (pq.getStatus().toString().equals(DictionyMap.PSQ_RELEASE)) {
					// ??????????????????

					StringBuilder sb = new StringBuilder();
					sb.append("{").append("\"Result\":\"ERROR\",").append("\"Message\":\"?????????????????? ???????????????\"").append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
					return;

				} else {

					// ??????????????????????????????????????????????????????????????????
					int count = this.getiSubmitService().getSubmitCountByProjectPsq(p_id, pp_id);

					if (count > 0) {
						// ???????????????????????????????????????

						StringBuilder sb = new StringBuilder();
						sb.append("{").append("\"Result\":\"ERROR\",").append("\"Message\":\"???????????????????????? ???????????????\"").append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
						return;
					} else {
						if (type.equals(DictionyMap.PSQ_TYPE_TRAINING)) {

							p.setPsqByEvaluationTrainingPsq(null);

						} else if (type.equals(DictionyMap.PSQ_TYPE_TEACHER)) {

							p.setPsqByEvaluationTeacherPsq(null);

						} else if (type.equals(DictionyMap.PSQ_TYPE_EXPERT)) {
							p.setPsqByProjectJudgePsq(null);
						}
					}
				}
			}

			this.getiProjectService().update(p);

			StringBuilder sb = new StringBuilder();
			sb.append("{").append("\"Result\":\"OK\",").append("\"Message\":\"OK\"").append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} catch (Exception ex) {
			ex.printStackTrace();
			StringBuilder sb = new StringBuilder();
			sb.append("{").append("\"Result\":\"ERROR\",").append("\"Message\":\"????????????\"").append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

	/**
	 * ????????????????????????
	 */
	public void getPageJson() {

		initServlert();

		// ???????????????????????? ????????????
		String type = request.getParameter("type");
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN") || ist.equals("0")) {
			ist = "1";
		}

		int start = Integer.parseInt(ist);
		short stype = Short.valueOf(type);

		int count = this.getiPsqService().getPsqByTypeCount(stype);
		int totalPage = (int) Math.ceil((float) count / DictionyMap.maxPageSize);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"currentPage\":" + (start) + ",");
		sb.append("\"totalPage\":" + totalPage);
		sb.append("}");

		Utlity.ResponseWrite(sb.toString(), "application/json", response);

	}

	/**
	 * ???????????????????????????
	 * 
	 * @return
	 */
	public String getReportUserList() {

		initServlert();

		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");
		this.searchReportTask = this.iProjectService.getProjectByStatus(DictionyMap.releaseProject, lityps);

		return "userlist";

	}

	/**
	 * ?????????????????????
	 * 
	 * @throws IOException
	 */
	public void outputUserList() throws IOException {

		initServlert();

		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;
		String ridcard = request.getParameter("idcard");
		String classes = request.getParameter("classes");
		trainingUnit = trainingUnit == null ? "0" : classes;
		int offset = 0;
		int number = this.iTeacherTrainingRecords.getAduTeacherCountByIdcard(Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit),Integer.valueOf(classes),ridcard);
		List lidata = this.iTeacherTrainingRecords.getAduTeacherByIdcard(Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit),Integer.valueOf(classes),ridcard, offset, number);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "???????????????");
		HSSFRow row = s.createRow(0);
		String title[] = { "?????????", "????????????", "????????????", "????????????", "?????????", "??????", "?????????" };
		for (int j = 0; j < title.length; j++) {
			row.createCell(j).setCellValue(title[j]);
		}
		int t = 0;
		for (int i = 0; i < lidata.size(); i++) {
			Object[] obj = (Object[]) lidata.get(i);
			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			Teacher th = (Teacher) obj[1];
			t++;
			row = s.createRow(t);
			String loginKey = ttRecord.getUuid();
			String projectName = ttRecord.getProject().getName();
			String subjectName = ttRecord.getTrainingSubject().getName();
			String trainingName = ttRecord.getTrainingCollege().getName();
			String idcard = ttRecord.getTeacher().getIdcard();
			String name = ttRecord.getTeacher().getName();
			String mobile = ttRecord.getTeacher().getMobile();

			String tInfo[] = { loginKey, projectName, trainingName, subjectName, idcard, name, mobile };
			for (int j = 0; j < tInfo.length; j++) {
				row.createCell(j).setCellValue(tInfo[j]);
			}
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=loginlist.xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
	}

	/**
	 * ???????????????????????????
	 */
	public void searchUserList() {

		initServlert();

		@SuppressWarnings("unchecked")
		List<ProjectType> lityps = (List<ProjectType>) session.getAttribute("lstProjectType");

		// ??????????????????
		String projectid = request.getParameter("projectName");
		projectid = projectid == null ? "0" : projectid;

		String subjectid = request.getParameter("subjectName");
		subjectid = subjectid == null ? "0" : subjectid;

		String trainingUnit = request.getParameter("trainingUnit");
		trainingUnit = trainingUnit == null ? "0" : trainingUnit;

		String idcard = request.getParameter("idcard");
		String classes = request.getParameter("classes");
		classes = classes == null ? "0" : classes;
		// ??????
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);

		int records = this.iTeacherTrainingRecords.getAduTeacherCountByIdcard(Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit),Integer.valueOf(classes),idcard);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List lidata = this.iTeacherTrainingRecords.getAduTeacherByIdcard(Integer.valueOf(projectid), Integer.valueOf(subjectid), Integer.valueOf(trainingUnit), Integer.valueOf(classes),idcard, offset, DictionyMap.maxPageSize);

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"?????????????????????!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}

		StringBuilder sbPack = new StringBuilder();
		sbPack.append("{");
		sbPack.append("\"status\":\"OK\"");

		sbPack.append(",");
		sbPack.append("\"page\":{");
		sbPack.append("\"currentPage\":" + start + ",");
		sbPack.append("\"totalPage\":" + totalPage);
		sbPack.append("}");

		sbPack.append(",");
		sbPack.append("\"records\":[");

		for (int i = 0; i < lidata.size(); i++) {

			Object[] obj = (Object[]) lidata.get(i);

			TeacherTrainingRecords ttRecord = (TeacherTrainingRecords) obj[0];
			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"id\":\"" + ttRecord.getId() + "\",");

			sb.append("\"loginKey\":\"" + ttRecord.getUuid() + "\",");
			sb.append("\"projectName\":\"" + ttRecord.getProject().getName() + "\",");
			sb.append("\"subjectName\":\"" + ttRecord.getTrainingSubject().getName() + "\",");
			sb.append("\"trainingName\":\"" + ttRecord.getTrainingCollege().getName() + "\",");
			sb.append("\"idcard\":\"" + ttRecord.getTeacher().getIdcard() + "\",");
			sb.append("\"name\":\"" + ttRecord.getTeacher().getName() + "\",");
			sb.append("\"mobile\":\"" + ttRecord.getTeacher().getMobile() + "\"");

			sb.append("},");
			sbPack.append(sb.toString());

		}

		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);

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

	// ????????????

	public IProjectService getiProjectService() {
		return iProjectService;
	}

	public void setiProjectService(IProjectService iProjectService) {
		this.iProjectService = iProjectService;
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

	public int getPaperID() {
		return paperID;
	}

	public void setPaperID(int paperID) {
		this.paperID = paperID;
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

	public String getSurveydata() {
		return surveydata;
	}

	public void setSurveydata(String surveydata) {
		this.surveydata = surveydata;
	}

	public List<Psq> getLearnList() {
		return learnList;
	}

	public void setLearnList(List<Psq> learnList) {
		this.learnList = learnList;
	}

	public List<Psq> getTeachList() {
		return teachList;
	}

	public void setTeachList(List<Psq> teachList) {
		this.teachList = teachList;
	}

	public List<Psq> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<Psq> reviewList) {
		this.reviewList = reviewList;
	}

	public Psq getPaper() {
		return paper;
	}

	public void setPaper(Psq paper) {
		this.paper = paper;
	}

	public List<Project> getSearchReportTask() {
		return searchReportTask;
	}

	public void setSearchReportTask(List<Project> searchReportTask) {
		this.searchReportTask = searchReportTask;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecords() {
		return iTeacherTrainingRecords;
	}

	public void setiTeacherTrainingRecords(ITeacherTrainingRecordsService iTeacherTrainingRecords) {
		this.iTeacherTrainingRecords = iTeacherTrainingRecords;
	}

	public int getPsqtype() {
		return psqtype;
	}

	public void setPsqtype(int psqtype) {
		this.psqtype = psqtype;
	}

	public HashMap<Integer, Object> getResultHashMap() {
		return resultHashMap;
	}

	public void setResultHashMap(HashMap<Integer, Object> resultHashMap) {
		this.resultHashMap = resultHashMap;
	}

	public String getPaperView() {
		return paperView;
	}

	public void setPaperView(String paperView) {
		this.paperView = paperView;
	}

	public int getValuator() {
		return valuator;
	}

	public void setValuator(int valuator) {
		this.valuator = valuator;
	}

	public int getProjectid() {
		return projectid;
	}

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public short getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(short subjectid) {
		this.subjectid = subjectid;
	}

	public int getTrainingCollege() {
		return trainingCollege;
	}

	public void setTrainingCollege(int trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPsq() {
		return psq;
	}

	public void setPsq(int psq) {
		this.psq = psq;
	}
	
	

}
