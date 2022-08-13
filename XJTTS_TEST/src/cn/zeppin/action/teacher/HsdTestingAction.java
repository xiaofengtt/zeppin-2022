/**
 * 
 */
package cn.zeppin.action.teacher;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import Organization.TeacherTrainingRecordsEx;
import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Hsdtest;
import cn.zeppin.entity.Hsdtestscore;
import cn.zeppin.entity.Result;
import cn.zeppin.entity.Submit;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.service.IHsdtestService;
import cn.zeppin.service.IHsdtestscoreService;
import cn.zeppin.service.IResultService;
import cn.zeppin.service.ISubmitService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.utility.DictionyMap;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 *
 */
public class HsdTestingAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5395655880066370354L;

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	private IHsdtestscoreService iHsdTestScoreService;
	private IHsdtestService iHsdTestService;
	private ITeacherService iTeacherService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;
	private ISubmitService iSubmitService;
	private IResultService iResultService;

	private List<TeacherTrainingRecordsEx> onSubmitList;

	public HsdTestingAction() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 录入教师测评结果
	 */
	@SuppressWarnings("deprecation")
	public void addHsdTest() {
		try {
			int year = new Date().getYear() + 1900;
			String asw = request.getParameter("asw");
			String score = request.getParameter("score");
			String st = request.getParameter("st");
			String reason = request.getParameter("reason");
			String recommend = request.getParameter("recommend");

			UserSession us = (UserSession) session.getAttribute("teachersession");

			// 判断当年已经回答过几次
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("teacher", us.getId());
			map.put("year", year);

			int count = this.getiHsdTestService().getHsdTestCount(map);
			if (count >= 3) {
				// 已经超过3次，不能回答
			} else {

				String[] scoreArray = score.split(",");

				float jssy1 = Float.valueOf(scoreArray[0]);
				float jhyzb1 = Float.valueOf(scoreArray[1]);
				float zzygl1 = Float.valueOf(scoreArray[2]);
				float pgyzd1 = Float.valueOf(scoreArray[3]);
				float xxyfz1 = Float.valueOf(scoreArray[4]);

				float jssy2 = Float.valueOf(scoreArray[5]);
				float jhyzb2 = Float.valueOf(scoreArray[6]);
				float zzygl2 = Float.valueOf(scoreArray[7]);
				float pgyzd2 = Float.valueOf(scoreArray[8]);
				float xxyfz2 = Float.valueOf(scoreArray[9]);

				Hsdtestscore hdscore1 = new Hsdtestscore();
				hdscore1.setAssess(pgyzd1);
				hdscore1.setLearn(xxyfz1);
				hdscore1.setOrganize(zzygl1);
				hdscore1.setPlane(jhyzb1);
				hdscore1.setSkill(jssy1);

				Hsdtestscore hdscore2 = new Hsdtestscore();
				hdscore2.setAssess(pgyzd2);
				hdscore2.setLearn(xxyfz2);
				hdscore2.setOrganize(zzygl2);
				hdscore2.setPlane(jhyzb2);
				hdscore2.setSkill(jssy2);

				this.getiHsdTestScoreService().add(hdscore1);
				this.getiHsdTestScoreService().add(hdscore2);

				Hsdtest ht = new Hsdtest();
				ht.setAsw(asw);
				ht.setSuggest("");
				ht.setHsdtestscoreByOptimize(hdscore1);
				ht.setHsdtestscoreByChange(hdscore2);
				ht.setReason(reason);
				ht.setRecommend(recommend);
				ht.setSt(st);
				ht.setYear(year);

				Teacher t = new Teacher();
				t.setId(us.getId());
				ht.setTeacher(t);

				ht.setCreatetime(new Timestamp(new Date().getTime()));

				this.getiHsdTestService().add(ht);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 获取列表
	 * 
	 * @return
	 */
	public void getHsdTest() {

		UserSession us = (UserSession) session.getAttribute("teachersession");

		if(us == null){
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"FAILED\",");
			sb.append("\"message\":\"用户未登录!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
			return;
		}
		
		// 分页
		String ist = (String) request.getParameter("page");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "1";
		}
		int start = Integer.parseInt(ist);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("teacher", us.getId());

		int records = this.getiHsdTestService().getHsdTestCount(map);
		int totalPage = (int) Math.ceil((float) records / DictionyMap.maxPageSize);
		int offset = (start - 1) * DictionyMap.maxPageSize;

		List<Hsdtest> lidata = this.getiHsdTestService().getHsdTest(map, offset, DictionyMap.maxPageSize);

		if (lidata == null || lidata.size() == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"没有搜索到数据!\"");
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

			Hsdtest ht = lidata.get(i);
			Hsdtestscore hdscore1 = ht.getHsdtestscoreByOptimize();
			Hsdtestscore hdscore2 = ht.getHsdtestscoreByChange();

			String score = hdscore1.getSkill() + "," + hdscore1.getPlane() + "," + hdscore1.getOrganize() + "," + hdscore1.getAssess() + "," + hdscore1.getLearn() + ",";
			score += hdscore2.getSkill() + "," + hdscore2.getPlane() + "," + hdscore2.getOrganize() + "," + hdscore2.getAssess() + "," + hdscore2.getLearn();

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"id\":\"" + ht.getId() + "\",");
			sb.append("\"year\":\"" + ht.getYear() + "\",");
			sb.append("\"createtime\":\"" + Utlity.timeSpanToString(ht.getCreatetime()) + "\",");
			sb.append("\"asw\":\"" + ht.getAsw() + "\",");
			sb.append("\"st\":\"" + ht.getSt() + "\",");
			sb.append("\"reason\":\"" + ht.getReason() + "\",");
			sb.append("\"recommend\":\"" + ht.getRecommend() + "\",");
			sb.append("\"score\":\"" + score + "\"");
			sb.append("},");

			sbPack.append(sb.toString());

		}

		if (lidata != null && lidata.size() > 0) {
			sbPack.delete(sbPack.length() - 1, sbPack.length());
		}
		sbPack.append("]}");

		Utlity.ResponseWrite(sbPack.toString(), "application/json", response);

	}

	/**
	 * 获取某次测评的详细数据
	 */
	public void getHsdTestInfo() {

		String id = request.getParameter("id");
		int i_id = Integer.valueOf(id);

		Hsdtest ht = this.getiHsdTestService().get(i_id);

		if (ht != null) {

			Hsdtestscore hdscore1 = ht.getHsdtestscoreByOptimize();
			Hsdtestscore hdscore2 = ht.getHsdtestscoreByChange();

			String score = hdscore1.getSkill() + "," + hdscore1.getPlane() + "," + hdscore1.getOrganize() + "," + hdscore1.getAssess() + "," + hdscore1.getLearn() + ",";
			score += hdscore2.getSkill() + "," + hdscore2.getPlane() + "," + hdscore2.getOrganize() + "," + hdscore2.getAssess() + "," + hdscore2.getLearn();

			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"OK\",");
			sb.append("\"id\":\"" + ht.getId() + "\",");
			sb.append("\"asw\":\"" + ht.getAsw() + "\",");
			sb.append("\"st\":\"" + ht.getSt() + "\",");
			sb.append("\"reason\":\"" + ht.getReason() + "\",");
			sb.append("\"recommend\":\"" + ht.getRecommend() + "\",");
			sb.append("\"score\":\"" + score + "\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);

		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"没有搜索到数据!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}

	}

	/**
	 * 检测 是否可作答测评
	 */
	@SuppressWarnings("deprecation")
	public void checkHsdTest() {
		int year = new Date().getYear() + 1900;
		UserSession us = (UserSession) session.getAttribute("teachersession");

		// 判断当年已经回答过几次
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("teacher", us.getId());
		map.put("year", year);

		int count = this.getiHsdTestService().getHsdTestCount(map);
		if (count >= 3) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"每年只可测评3次，今年测评次数已用完!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"status\":\"OK\",");
			sb.append("\"message\":\"可以测评!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}
	
	/**
	 * 教师培训问卷（学后评价模块问卷）
	 */
	@SuppressWarnings("rawtypes")
	public void getTrainingRecords(){
		UserSession us = (UserSession) session.getAttribute("teachersession");
		StringBuilder sb = new StringBuilder();
		if(us != null){
			
			String mobile = us.getMobile();
	
			List<TeacherTrainingRecords> liT = this.getiTeacherTrainingRecordsService().getTeacherTrainingRecordsByMobile(mobile);
			
			if(liT != null && liT.size() > 0){
				int totalCount = liT.size();
				int isFinished = 0;
				int noFinished = 0;
				
				sb.append("{");
				sb.append("\"Result\":\"OK\"");
				sb.append(",");
				sb.append("\"Message\":\"查询完毕\"");
				sb.append(",");
				sb.append("\"Records\":[");
				for(TeacherTrainingRecords ttr: liT){
					sb.append("{");
					sb.append("\"id\":\""+ttr.getId()+"\"");
					sb.append(",");
					sb.append("\"projectName\":\""+ttr.getProject().getName()+"\"");
					sb.append(",");
					sb.append("\"subjectName\":\""+ttr.getTrainingSubject().getName()+"\"");
					sb.append(",");
					sb.append("\"trainingCollege\":\""+ttr.getTrainingCollege().getName()+"\"");
					
					int tid = ttr.getTeacher().getId();
					int pid = ttr.getProject().getId();
					short sid = ttr.getTrainingSubject().getId();
					int tcid = ttr.getTrainingCollege().getId();
					int paperid = 0;
					if (ttr.getProject().getPsqByEvaluationTrainingPsq() != null) {
						paperid = ttr.getProject().getPsqByEvaluationTrainingPsq().getId();
						
					} else {
						paperid = 0;
						
					}
	
					int isRecords = 0;
					String starCount = "";
					String createTime = "";
					Submit sub = this.getiSubmitService().getSubmitByAll(tid, paperid, pid, sid, tcid, tid);
					if (sub == null) {
						isRecords = 0;
						noFinished++;
					} else {
						createTime = Utlity.timeSpanToString(sub.getCreatetime());
						List lstResult = this.iResultService.getResultScore(sub.getId(), sub.getPsq().getId());
						int totalScore = lstResult.size()*5;
						int score = 0;
						for(int i = 0; i < lstResult.size(); i++){
							Object[] obj = (Object[])lstResult.get(i);
							Result result = (Result) obj[0];
							score += result.getScore();
						}
						DecimalFormat df = new DecimalFormat("0.0");
						starCount = df.format((score/(Math.ceil(totalScore/5))));
						
						isRecords = 1;
						isFinished++;
					}
					sb.append(",");
					sb.append("\"isRecords\":\""+isRecords+"\"");
					sb.append(",");
					sb.append("\"starCount\":\""+starCount+"\"");
					sb.append(",");
					sb.append("\"createTime\":\""+createTime+"\"");
					
					sb.append("},");
				}
				sb.delete(sb.length()-1, sb.length());
				sb.append("]");
				sb.append(",");
				sb.append("\"TotalCount\":\""+totalCount+"\"");
				sb.append(",");
				sb.append("\"IsFinished\":\""+isFinished+"\"");
				sb.append(",");
				sb.append("\"NoFinished\":\""+noFinished+"\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}else{
				sb.append("{");
				sb.append("\"Result\":\"EMPTY\",");
				sb.append("\"Message\":\"暂无数据！\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);
			}
		}else{
			sb.append("{");
			sb.append("\"Result\":\"FAILED\",");
			sb.append("\"Message\":\"用户未登录！\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}


	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public IHsdtestscoreService getiHsdTestScoreService() {
		return iHsdTestScoreService;
	}

	public void setiHsdTestScoreService(IHsdtestscoreService iHsdTestScoreService) {
		this.iHsdTestScoreService = iHsdTestScoreService;
	}

	public IHsdtestService getiHsdTestService() {
		return iHsdTestService;
	}

	public void setiHsdTestService(IHsdtestService iHsdTestService) {
		this.iHsdTestService = iHsdTestService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public List<TeacherTrainingRecordsEx> getOnSubmitList() {
		return onSubmitList;
	}

	public void setOnSubmitList(List<TeacherTrainingRecordsEx> onSubmitList) {
		this.onSubmitList = onSubmitList;
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

}
