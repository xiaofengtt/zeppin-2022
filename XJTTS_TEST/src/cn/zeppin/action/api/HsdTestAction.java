package cn.zeppin.action.api;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Hsdtest;
import cn.zeppin.entity.Hsdtestscore;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.service.IHsdtestService;
import cn.zeppin.service.IHsdtestscoreService;
import cn.zeppin.service.ITeacherService;
import cn.zeppin.service.ITeacherTrainingRecordsService;
import cn.zeppin.service.ITrainingCollegeService;
import cn.zeppin.utility.Utlity;

import com.opensymphony.xwork2.ActionSupport;

public class HsdTestAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3365984307155018928L;

	private HttpServletRequest request;
	private HttpServletResponse response;

	private IHsdtestscoreService iHsdTestScoreService;
	private IHsdtestService iHsdTestService;
	private ITeacherService iTeacherService;
	private ITrainingCollegeService iTrainingCollegeService;
	private ITeacherTrainingRecordsService iTeacherTrainingRecordsService;

	public HsdTestAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}

	/**
	 * 获取教师测评结果信息
	 */
	@SuppressWarnings("deprecation")
	public void getTeacherHsd() {

		// ===============================
		// 1.参数承训机构id
		// 2.教师身份证号
		// ===============================
		String trainingCollege = request.getParameter("trainingCollegeId");
		String teacherCard = request.getParameter("teacherCard");
		String s_year = request.getParameter("year");

		StringBuilder sb = new StringBuilder();

		// 验证 承训机构
		if (trainingCollege == null || teacherCard == null || trainingCollege.equals("")) {
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"参数信息有错!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else if (!Utlity.checkIdCard(teacherCard)) {
			// 身份证不合法
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"身份证不合法!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			TeacherTrainingRecords ttr = this.getiTeacherTrainingRecordsService().getTeacherTrainingRecordsByIdCard(Integer.valueOf(trainingCollege), teacherCard);
			if (ttr == null) {

				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"当前培训项目中，不存在此教师信息!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);

			} else {

				int year = new Date().getYear() + 1900;

				if (Utlity.isNumeric(s_year)) {
					year = Integer.parseInt(s_year);
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("teacher", ttr.getTeacher().getId());
				map.put("year", year);

				List<Hsdtest> lidata = this.getiHsdTestService().getHsdTest(map, -1, -1);

				if (lidata == null || lidata.size() == 0) {
					sb.append("{");
					sb.append("\"status\":\"WARN\",");
					sb.append("\"message\":\"没有搜索到数据!\",");
					sb.append("\"url\":\"http://www.xjjspxgl.com/teacher/\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				} else {

					sb.append("{");
					sb.append("\"status\":\"OK\"");
					sb.append(",");
					sb.append("\"records\":[");

					for (int i = 0; i < lidata.size(); i++) {

						Hsdtest ht = lidata.get(i);
						Hsdtestscore hdscore1 = ht.getHsdtestscoreByOptimize();
						Hsdtestscore hdscore2 = ht.getHsdtestscoreByChange();

						String score = hdscore1.getSkill() + "," + hdscore1.getPlane() + "," + hdscore1.getOrganize() + "," + hdscore1.getAssess() + "," + hdscore1.getLearn() + ",";
						score += hdscore2.getSkill() + "," + hdscore2.getPlane() + "," + hdscore2.getOrganize() + "," + hdscore2.getAssess() + "," + hdscore2.getLearn();

						StringBuilder sbt = new StringBuilder();
						sbt.append("{");
						sbt.append("\"id\":\"" + ht.getId() + "\",");
						sbt.append("\"year\":\"" + ht.getYear() + "\",");
						sbt.append("\"createtime\":\"" + Utlity.timeSpanToString(ht.getCreatetime()) + "\",");
						sbt.append("\"asw\":\"" + ht.getAsw() + "\",");
						sbt.append("\"st\":\"" + ht.getSt() + "\",");
						sbt.append("\"reason\":\"" + ht.getReason() + "\",");
						sbt.append("\"recommend\":\"" + ht.getRecommend() + "\",");
						sbt.append("\"score\":\"" + score + "\"");
						sbt.append("},");

						sb.append(sbt.toString());

					}

					if (lidata != null && lidata.size() > 0) {
						sb.delete(sb.length() - 1, sb.length());
					}
					sb.append("]}");

					Utlity.ResponseWrite(sb.toString(), "application/json", response);

				}

			}

		}

	}

	public void getTrainingCollegeHsd() {
		String tcName = request.getParameter("searchName") == null ? "" : request.getParameter("searchName");

		StringBuilder sb = new StringBuilder();
		List<TrainingCollege> tclist = this.iTrainingCollegeService.getTrainingCollegeByName(tcName);
		if (tclist == null || tclist.size() == 0) {
			sb.append("{");
			sb.append("\"status\":\"WARN\",");
			sb.append("\"message\":\"没有搜索到数据!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		} else {
			sb.append("{");
			sb.append("\"status\":\"OK\"");
			sb.append(",");
			sb.append("\"records\":[");
			for (int i = 0; i < tclist.size(); i++) {
				TrainingCollege tc = tclist.get(i);

				StringBuilder sbt = new StringBuilder();
				sbt.append("{");
				sbt.append("\"id\":\"" + tc.getId() + "\",");
				sbt.append("\"name\":\"" + tc.getName() + "\",");
				sbt.append("\"contacts\":\"" + tc.getContacts() + "\",");
				sbt.append("\"phone\":\"" + tc.getPhone() + "\"");
				sbt.append("},");

				sb.append(sbt.toString());
			}
			if (tclist != null && tclist.size() > 0) {
				sb.delete(sb.length() - 1, sb.length());
			}
			sb.append("]}");

			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
	}

	/**
	 * 导入教师测评结果
	 */
	public void importTeacherHsd() {
		StringBuilder sb = new StringBuilder();

		try {

			int year = Integer.valueOf(request.getParameter("year"));
			String teacherCard = request.getParameter("teacherCard");
			String asw = request.getParameter("asw");
			String score = request.getParameter("score");
			String st = request.getParameter("st");
			String reason = request.getParameter("reason");
			String recommend = request.getParameter("recommend");

			if (teacherCard == null || asw == null || score == null || st == null || reason == null || recommend == null) {

				sb.append("{");
				sb.append("\"status\":\"ERROR\",");
				sb.append("\"message\":\"参数信息有错!\"");
				sb.append("}");
				Utlity.ResponseWrite(sb.toString(), "application/json", response);

			} else {

				Teacher us = this.iTeacherService.getTeacherByIdCard(teacherCard);

				if (us != null) {

					// 判断当年已经回答过几次
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("teacher", us.getId());
					map.put("year", year);

					int count = this.getiHsdTestService().getHsdTestCount(map);
					if (count >= 3) {
						// 已经超过3次，不能回答
						sb.append("{");
						sb.append("\"status\":\"ERROR\",");
						sb.append("\"message\":\"已经超过3次，不能导入!\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);
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

						sb.append("{");
						sb.append("\"status\":\"OK\",");
						sb.append("\"message\":\"插入成功!\"");
						sb.append("}");
						Utlity.ResponseWrite(sb.toString(), "application/json", response);

					}
				} else {
					sb.append("{");
					sb.append("\"status\":\"ERROR\",");
					sb.append("\"message\":\"不存在此教师，教师身份证不存在!\"");
					sb.append("}");
					Utlity.ResponseWrite(sb.toString(), "application/json", response);
				}
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			sb.append("{");
			sb.append("\"status\":\"ERROR\",");
			sb.append("\"message\":\"请检查数据是否合法!\"");
			sb.append("}");
			Utlity.ResponseWrite(sb.toString(), "application/json", response);
		}
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

	public ITeacherService getiTeacherService() {
		return iTeacherService;
	}

	public void setiTeacherService(ITeacherService iTeacherService) {
		this.iTeacherService = iTeacherService;
	}

	public ITeacherTrainingRecordsService getiTeacherTrainingRecordsService() {
		return iTeacherTrainingRecordsService;
	}

	public void setiTeacherTrainingRecordsService(ITeacherTrainingRecordsService iTeacherTrainingRecordsService) {
		this.iTeacherTrainingRecordsService = iTeacherTrainingRecordsService;
	}

	public ITrainingCollegeService getiTrainingCollegeService() {
		return iTrainingCollegeService;
	}

	public void setiTrainingCollegeService(ITrainingCollegeService iTrainingCollegeService) {
		this.iTrainingCollegeService = iTrainingCollegeService;
	}

}
