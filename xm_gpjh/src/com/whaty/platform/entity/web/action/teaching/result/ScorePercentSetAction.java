package com.whaty.platform.entity.web.action.teaching.result;

import java.math.BigDecimal;

import org.hibernate.criterion.DetachedCriteria;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.examScore.ExamScoreComposeService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ScorePercentSetAction extends MyBaseAction<PeTchCourse> {

	private ExamScoreComposeService examScoreComposeService;
	private String scoreExamRate;
	private String scoreHomeworkRate;
	private String scoreUsualRate;
	private String peGradeName;
	private String peEdutypeName;
	private String peMajorName;
	private String peCourseName;
	
	public String getPeCourseName() {
		return peCourseName;
	}

	public void setPeCourseName(String peCourseName) {
		this.peCourseName = peCourseName;
	}

	public String getPeEdutypeName() {
		return peEdutypeName;
	}

	public void setPeEdutypeName(String peEdutypeName) {
		this.peEdutypeName = peEdutypeName;
	}

	public String getPeGradeName() {
		return peGradeName;
	}

	public void setPeGradeName(String peGradeName) {
		this.peGradeName = peGradeName;
	}

	public String getPeMajorName() {
		return peMajorName;
	}

	public void setPeMajorName(String peMajorName) {
		this.peMajorName = peMajorName;
	}

	public String getScoreExamRate() {
		return scoreExamRate;
	}

	public void setScoreExamRate(String scoreExamRate) {
		this.scoreExamRate = scoreExamRate;
	}

	public String getScoreHomeworkRate() {
		return scoreHomeworkRate;
	}

	public void setScoreHomeworkRate(String scoreHomeworkRate) {
		this.scoreHomeworkRate = scoreHomeworkRate;
	}

	public String getScoreUsualRate() {
		return scoreUsualRate;
	}

	public void setScoreUsualRate(String scoreUsualRate) {
		this.scoreUsualRate = scoreUsualRate;
	}

	public ExamScoreComposeService getExamScoreComposeService() {
		return examScoreComposeService;
	}

	public void setExamScoreComposeService(
			ExamScoreComposeService examScoreComposeService) {
		this.examScoreComposeService = examScoreComposeService;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().setTitle(this.getText("课程成绩合成参数"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名"), "name");
//		this.getGridConfig().addColumn(this.getText("系统成绩"),
//				"scoreSystemRate");
		this.getGridConfig().addColumn(this.getText("平时成绩"),
				"scoreUsualRate");
		this.getGridConfig().addColumn(this.getText("作业成绩"),
				"scoreHomeworkRate");
		this.getGridConfig().addColumn(this.getText("考试成绩"),
				"scoreExamRate");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTchCourse.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/scorePercentSet";
	}


	public void setBean(PeTchCourse instance) {
		super.superSetBean(instance);
	}
	
	public PeTchCourse getBean(){
		return super.superGetBean();
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourse.class);
		return dc;
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		BigDecimal b1 = new BigDecimal(this.getBean().getScoreHomeworkRate().toString());
		BigDecimal b2 = new BigDecimal(this.getBean().getScoreUsualRate().toString());
		BigDecimal b3 = new BigDecimal(this.getBean().getScoreExamRate().toString());
		if (b1.add(b2).add(b3).doubleValue() != 1) {
			throw new EntityException("三种比例之和应该等于 1 。");
		}
	}

	public String compose() {
		this.setTogo("back");
		try {
			String result = this.getExamScoreComposeService().saveCompose();
			this.setMsg(result);
		} catch (Exception e) {
			if( e.getMessage()!=null){
			this.setMsg("成绩合成失败。<br>" + e.getMessage());
			} else{
				this.setMsg("成绩合成失败。<br>");
			}
			e.printStackTrace();
		}
		return "msg";
	}
	
	public String composeSingle() {
		this.setTogo("back");
		try {
			String result = this.getExamScoreComposeService().saveComposeSingle(this.getPeCourseName(),this.getScoreExamRate(),this.getScoreHomeworkRate(),this.getScoreUsualRate(),
				this.getPeEdutypeName(),this.getPeGradeName(),this.getPeMajorName());
			this.setMsg(result);
		} catch(Exception e) {
			if( e.getMessage()!=null){
				this.setMsg("成绩合成失败。<br>" + e.getMessage());
				} else{
					this.setMsg("成绩合成失败。<br>");
				}
			e.printStackTrace();
		}
		return "msg";
	}
}
