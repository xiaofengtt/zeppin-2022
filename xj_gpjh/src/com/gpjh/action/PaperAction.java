package com.gpjh.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gpjh.model.Answer;
import com.gpjh.model.LoginKey;
import com.gpjh.model.Paper;
import com.gpjh.model.Project;
import com.gpjh.model.PsqProjectMap;
import com.gpjh.model.Question;
import com.gpjh.model.Result;
import com.gpjh.model.Submit;
import com.gpjh.model.VoteCount;
import com.gpjh.service.AnswerService;
import com.gpjh.service.LoginKeyService;
import com.gpjh.service.PaperService;
import com.gpjh.service.ProjectService;
import com.gpjh.service.PsqProjectMapService;
import com.gpjh.service.QuestionService;
import com.gpjh.service.ResultService;
import com.gpjh.service.SubmitService;
import com.gpjh.service.VoteCountService;
import com.opensymphony.xwork2.ActionSupport;

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
	private VoteCountService votecountService;
	
	@Autowired
	private PsqProjectMapService ppmService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SubmitService submitService;

	private List<Paper> paperList;
	private String paperID;
	private Paper paper;
	private String qstr = "";
	private String uid = "";
	
	private String surveydata;
	
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	 //private ServletContext application;
	 
	 public PaperAction(){
		 request = ServletActionContext.getRequest();
		 session = request.getSession();
		 //application = session.getServletContext();
		 response = ServletActionContext.getResponse();
	 }
	
	/**
	 * 编辑试卷
	 * @return
	 */
	public String edit(){
		
		//HttpServletRequest request = ServletActionContext.getRequest();
		String curID = request.getParameter("curid");
		//String userId = (String)request.getSession().getAttribute("ID");
		String userId = (String)session.getAttribute("ID");
		
		log.info("paper " + curID + " edit");
				
		if(!userId.endsWith("xjGPJH58801324") || curID == null || curID.equals("")){
			return "cantadd";
		}
		
		System.out.println("curID...... " + curID);
		Paper paper = paperService.get(curID);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		System.out.println( sdf.format(paper.getCreatTime()));
		
		this.surveydata = sdf.format(paper.getCreatTime())+ "§" +paper.getSurveydata() + "?false§"+ paper.getCreator() + "§0§q_12px.css§False§null";

		System.out.println(this.surveydata);
		
		return "edit";
	}
	
	/**
	 * 保存试卷
	 */
	public void save(){
		
		//String uid = (String)request.getSession().getAttribute("ID");
		//HttpServletRequest request = ServletActionContext.getRequest();
		//HttpServletResponse response = ServletActionContext.getResponse();
		
		boolean result = false;
		// 试卷ID
		String curID = request.getParameter("curID");
		String uid = request.getParameter("userguid");
		String surveydata = request.getParameter("surveydata");
		System.out.println("uid ................ " + uid);
		//String submitType = request.getParameter("submitType");

		System.out.println("curID ======== " + curID);
		System.out.println("this.surveydata ...... " + surveydata);

		String[] b = surveydata.split("¤");

		System.out.println(b[0]);

		String[] a = b[0].split("§");

		System.out.println("_title " + a[0]);
		System.out.println("_tag " + a[1]);
		

		// 试卷的信息
		Paper paper = new Paper();
		paper = paperService.get(curID);
		if (paper != null) {
			paper.setTitle(a[0]);
			paper.setAbout(a[1]);
			paper.setType((short) 1);
			paper.setStatus((short) 1);
			paper.setId(curID);
			//paper.setCreator(userguid);
			paper.setCreator(uid);
			paper.setSurveydata(surveydata);

			paper = paperService.update(paper);

			//清除掉原来的答案信息
			answerService.deleteByPaperId(paper.getId());
			//清除原来的问题信息
			questionService.deleteByPaperId(paper.getId());
			
			for (int d = 1; d < b.length; d++) {// 以题目为单位分成数组

				System.out.println("第 " + d + "题开始..............");
				System.out.println("b[d] === " + b[d]);

				String oneQ = b[d];

				String[] Qo = oneQ.split("§");

				String type = Qo[0];
				int index = Integer.parseInt(Qo[1]);
				String title = Qo[2];

				int arrange = -1;
				String isTongji="false";
				if (type.equals("radio") || type.equals("check")) {
					arrange = Integer.parseInt(Qo[4].split("〒")[0]);
					isTongji = Qo[8];
				}
				int scale = Integer.parseInt(Qo[3]);
				String option = "";

				System.out.println("isTongji ..... " + isTongji);
				
				short itype = 0;
				if (type.equals("radio")) {
					itype = 2;
				} else if (type.equals("check")) {
					itype = 4;
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

				System.out.println("type ==== " + type);
				System.out.println("index ==== " + index);
				System.out.println("tile ==== " + title.split("〒")[0]);

				if (type.equals("radio") || type.equals("check")) {

					int x = 1;
					for (int j = 14; j < Qo.length; j++) {
						option = Qo[j];
						System.out.println("option ==== " + option);
						String[] answers = option.split("〒");
						System.out.println("length .......... " + answers.length);
						String answer_title = answers[0];
						int score = Integer.parseInt(answers[2]);
						int jump = answers[3] == null || answers[3].equals("") ? -1 : Integer.parseInt(answers[3]);
						String about = "";
						if (answers.length == 9) {
							about = answers[8];
						}

						System.out.println("about ......... " + about);
						System.out.println("title ......... " + answer_title);
						System.out.println("score ......... " + score);

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

				System.out.println("第 " + d + "题结束..............");
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
	 * @return
	 */
	public String list(){
		
		//HttpServletRequest request = ServletActionContext.getRequest();
		String userId = (String)request.getSession().getAttribute("ID");

		String cannotEditPaperID = "";
		String sql = "select distinct paper.id from Result result, Paper paper, Question question where result.question = question.id and question.paper = paper.id";
		List<Object> res = resultService.findByHSQL(sql);
		
		for (Iterator<Object> iter = res.iterator(); iter.hasNext();) {
			String record = (String) iter.next();
			cannotEditPaperID += record + ",";
		}
		
		sql = "from Paper where 1=1 and status <> 0";		
		sql += " and creator = '" + userId + "'";
		List<Paper> paperList = paperService.getListForPage(sql, 0, 20);
		
		if(userId== null || !userId.endsWith("xjGPJH58801324")){
			return "cantadd";
		}
		
		sql = "from PsqProjectMap";
		List<PsqProjectMap> pms = ppmService.getListForPage(sql, 0, 10);
		
		this.paperList = new ArrayList<Paper>();
		for(Paper p: paperList){
	
			List<Project> pls = new ArrayList<Project>();
			for(PsqProjectMap pm : pms){
				
				if(p.getId().equals(pm.getPaper().getId())){
					System.out.println(pm.getProject());
					pls.add(pm.getProject());
				}
			}
			p.setProjects(pls);
			
			//给不能编辑的id设置属性		
			if(cannotEditPaperID.indexOf(p.getId()+"") > 0){
				p.setEditalbe(0);
			} else {
				p.setEditalbe(1);
			}
			
			this.paperList.add(p);
		}

/*		
		System.out.println(res.size());
		
		for(Paper p: this.paperList){
			System.out.println(p.getTitle());		
			for(Project pp: p.getProjects()){
				System.out.println(pp.getName());
			}
		}*/
		
		
		System.out.println("have " + this.paperList.size() + " papers");
		
		return "list";
	}
	
	/**
	 * 新建问卷
	 * @return
	 */
	public String add(){
		
		//HttpServletRequest request = ServletActionContext.getRequest();
		String userId = (String)request.getSession().getAttribute("ID");
		
		if(!userId.endsWith("xjGPJH58801324")){
			return "cantadd";
		}
		
		Paper paper = new Paper();
		paper.setTitle("请输入您的问卷的标题[复制]");
		paper.setAbout("请填写关于此问卷的说明");
		paper.setStatus((short)1);
		paper.setType((short)1);
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
	 * @return
	 */
	public String view(){
		//HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		String t = request.getParameter("t");
		this.uid = (String)session.getAttribute("ID"); 
		
		paperService.get(id);

		System.out.println("view page ==== "+ id);
		this.paper = paperService.get(id);
		
		if(this.paper == null && this.paper.getStatus() != 2 && !t.equals( "x123")){
			return "novote";
		}
		
		String ret = "false§false¤page§1§§§¤";
		
		Set<Question> qs = this.paper.getQuestions();
		int i = 1;
		String qstr = "";
		for(Question q: qs){
			
			String type = "";
			int itype = q.getType();
			if(itype == 0) continue;
			if(itype == 2){
				type = "radio";
			} else if(itype == 4){
				type = "check";
			}
			
			int inx = q.getInx();
			int arrange = q.getArrange();
			
			String requir = "true";
			if(itype == 4){
				requir = "true,,";
			}
			
			String cp = "";
			if(q.getScale()== 0){
				cp = "ceping";
			}
			
			qstr += type + "§" + inx + "§" + arrange + "§true§false§0§" + requir + "§" + cp+"§0§"+ q.getScale() +"§§§";
			String anstr = "";
			Set<Answer> as = q.getAnswers();
			
			int j = 1;
			for(Answer a : as){
				
				if(itype ==4){
					anstr += "false〒" + a.getInx() + "〒" +a.getJump() + "〒";
				}else {
					anstr += "false〒" + a.getInx() + "〒" +a.getJump();
				}
				//anstr += anstr;
				System.out.println(anstr);
				if(j < as.size()){
					anstr += "§";
				}
				j++;
			}
			qstr += anstr;
			
			if(i < qs.size()-1){
				qstr += "¤";
			}
			i++;
			
			this.qstr = ret + qstr;
		}
		
		System.out.println(this.qstr);
		
		return "view";
	}
	
	/**
	 * 处理投票
	 * @return
	 */
	public void process(){

		// HttpServletRequest request = ServletActionContext.getRequest();
		// HttpServletResponse response = ServletActionContext.getResponse();
		try {
			String paper_id = request.getParameter("curID");
			String submitdata = request.getParameter("submitdata");
			String loginkey = (String) request.getSession().getAttribute("ID");// "GP000100300101";
			
			System.out.println(submitdata);

			// 验证投票人数
			VoteCount vc = votecountService.getByLoginKey(loginkey);
			LoginKey lk = longinKeyService.get(loginkey);
			if (vc == null) {
				vc = new VoteCount();
				vc.setLoninKey(lk);
				vc.setCount(0);
				votecountService.add(vc);
			}
			int count = 0, rlsum=0;
			count = vc.getCount();
			rlsum = lk.getRealSum();
			if (count >= rlsum) {
				// 已经超过最大投票人数
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("1000〒已经超过最大投票人数了");
				return;
			} 

			//更新投票数量
			vc.setCount(count + 1);
/*			System.out.println(vc.getId());
			System.out.println(vc.getLoninKey().getId());
			System.out.println(vc.getCount());*/
			votecountService.update(vc);
			
		Paper paper = new Paper();
		paper.setId(paper_id);
		String ip = getIpAddr(request);
		
		//添加submit表
		Submit submit = new Submit();
		submit.setLoginkey(lk);
		submit.setPaper(paper);
		submit.setIp(ip);
		submit = submitService.add(submit);
		
			// 验证投票人数结束

			String[] datas = submitdata.split("}");

			for (String data : datas) {
				String[] ds = data.split("\\$");
				String inx = (data.split("\\$"))[0];
				String ans = (data.split("\\$"))[1];

				String qid = "";
				//List<Question> qs = questionService.getListByHSQL("from Question where paper = "+ paper_id + " and inx = " + inx + " and type <> 0");
				
				String sql = "SELECT id FROM question WHERE psq = " + paper_id + " and inx = " + inx + " and type <> 0";
				List qs = questionService.executeSQL(sql);
				
				if (qs != null && qs.size() == 1) {
					qid = qs.get(0) + "";
					String[] as = ans.split("\\|");
					for (String a : as) {
						String aid = "";
						int score = 0;
						//List<Answer> as1 = answerService.getListByHSQL("from Answer where paper = "+ paper_id + " and question = " + qid+ " and  inx = " + a);
						
						sql = "SELECT id, score FROM answer WHERE psq = " + paper_id + " and inx = " + a + " and question =" + qid;
						List as1 = questionService.executeSQL(sql);
						
						if (as1 != null && as1.size() > 0) {
							for(int i=0;i<as1.size();i++){
							//for (Answer a1 : as1) {
								Object[] objects = (Object[])as1.get(i);
								aid = objects[0] + "";
								score = Integer.parseInt(objects[1] + "");
								Result res = new Result();
								res.setQuestion(new Question(qid));
								res.setAnswer(new Answer(aid));
								res.setLoginkey(lk);
								res.setScore(score);
								res.setSubmit(submit);
								resultService.add(res);

							}
						}

					}
				}
			}
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("1000〒成功提交问卷，感谢您的参与！");
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	/**
	 * 改变投票状态
	 */
	public void status(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		String status = request.getParameter("s");
		
/*		String uid = (String)request.getSession().getAttribute("ID");//    "GP000100300101";
		List<Paper> ps = paperService.getListForPage("from Paper where creator ='" + uid + "' and status = 2", 0, 100);
		for(Paper p: ps){
			p.setStatus((short)3);
			paperService.update(p);
		}*/
		
		Paper pp = paperService.get(id);
		pp.setStatus(Short.parseShort(status));
		
		paperService.update(pp);
		
		if(status.equals("0")){
			//删除投票关联的项目
			ppmService.deleteByPaperId(id);
		}
		
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("true");
		}catch(Exception e){}
	}


	/**
	 * 返回剩下的项目名称
	 */
	public void projects(){
		
		String ret = "";
		List<Project> ps = projectService.getListForPage("from Project", 0, 100);
		List<PsqProjectMap> ppm = ppmService.getListForPage("from PsqProjectMap", 0, 100);
		
		List<Project> leftList = ps;
		
		for(int i=0; i < ppm.size(); i++){
			PsqProjectMap pm =ppm.get(i);
			for(int j =0; j< ps.size(); j ++ ){
				Project p = ps.get(j);
				if(p.getId().equals(pm.getProject().getId())){
					leftList.remove(j);
				}
			}
		}
		
		for(Project p: leftList){
			ret += "<li onclick=\"addP('" + p.getId() + "', this)\">" + p.getName() + "</li>";
		}
		
		try{
			//HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			System.out.println("ret ......... " + ret);
			ret = ret.equals("") ? "false" : ret;
			response.getWriter().write(ret);
		}catch(Exception e){}
	}
	
	
	/**
	 * 添加/删除问卷项目
	 * @return
	 */
	public void updateproject(){
		
		//HttpServletRequest request = ServletActionContext.getRequest();
		String qid = request.getParameter("qid");			
		String pid = request.getParameter("pid");
		String action = request.getParameter("action");

		if(action.equals("add")){
			
			String sql = "from PsqProjectMap where project = " + pid + " and psq = " + qid;
			
			int count = ppmService.queryRowCount(sql);

			if( count == 0){
				Project p = projectService.get(pid);
				Paper q = paperService.get(qid);
				
				PsqProjectMap ppm = new PsqProjectMap();
				ppm.setPaper(q);
				ppm.setProject(p);
				
				ppmService.add(ppm);
				
				try{
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("true");
				}catch(Exception e){}
			} else {
				try{
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("false");
				}catch(Exception e){}
			}
		} else if(action.equals("del")){
			
			List<PsqProjectMap> pms = ppmService.getListForPage("from PsqProjectMap where project = " + pid, 0, 1);
			if(pms != null & pms.size() == 1){
				PsqProjectMap ppm = pms.get(0);
				ppmService.delete(ppm);
			}

			try{
				//HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("true");
			}catch(Exception e){}
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

	public String getPaperID() {
		return paperID;
	}

	public void setPaperID(String paperID) {
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
