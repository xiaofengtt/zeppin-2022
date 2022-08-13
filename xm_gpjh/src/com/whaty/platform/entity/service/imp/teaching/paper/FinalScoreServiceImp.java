package com.whaty.platform.entity.service.imp.teaching.paper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PeTchRejoinRoom;
import com.whaty.platform.entity.bean.PeTchRejoinSection;
import com.whaty.platform.entity.bean.PrPriManagerEdutype;
import com.whaty.platform.entity.bean.PrPriManagerGrade;
import com.whaty.platform.entity.bean.PrPriManagerMajor;
import com.whaty.platform.entity.bean.PrPriManagerSite;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.paper.FinalScoreService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class FinalScoreServiceImp implements FinalScoreService {

	private GeneralDao generalDao;
	private MyListDAO myListDAO;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public MyListDAO getMyListDAO() {
		return myListDAO;
	}

	public void setMyListDAO(MyListDAO myListDAO) {
		this.myListDAO = myListDAO;
	}

	public String update_compose() throws EntityException{
//		得到答辩成绩占总评成绩的比率。
		float rate = Float.parseFloat(this.getMyListDAO().getSysValueByName("paperReplyRate"));
		StringBuffer sb = new StringBuffer();
		int count = 0;
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuPaper.class);
		dc.createAlias("peStudent", "peStudent");
		dc.createCriteria("prTchPaperTitle", "prTchPaperTitle").createCriteria("peSemester", "peSemester")
			.add(Restrictions.eq("flagActive", "1"));
		List stuList = new ArrayList();
		stuList = this.getGeneralDao().getList(dc);
//		if (stuList.size() < 1) {
//			return "成绩已合成！";
//		}
		for (Iterator iter = stuList.iterator(); iter.hasNext();) {
			PrTchStuPaper stu = (PrTchStuPaper) iter.next();
			double finalScore = 0.0;
			try {
				finalScore = stu.getFinalScore();
			} catch (Exception e) {
				sb.append(stu.getPeStudent().getTrueName()+"的终稿成绩未录入。<br>");
				continue;
			}
			double scoreTotal = 0.0;
			double rejoinScore = 0.0;
			//不参加答辩的总评成绩为论文写作成绩
			if (stu.getEnumConstByFlagPaperRejoin().getCode().equals("0")) {
				stu.setScoreTotal(finalScore);
				DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
				electiveDC.createCriteria("peStudent", "peStudent");
				electiveDC.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peTchCourse", "peTchCourse");
				electiveDC.add(Restrictions.eq("peStudent", stu.getPeStudent()));
				electiveDC.add(Restrictions.eq("peTchCourse.name", "毕业论文"));
				List<PrTchStuElective> list = this.getGeneralDao().getList(electiveDC);
				if(list==null||list.isEmpty()){
					sb.append("学生" + stu.getPeStudent().getTrueName() + "的毕业论文选课记录不存在。<br>");
					continue;
				}
				PrTchStuElective stuElective = list.get(0);
				
				if (stuElective.getScoreTotal()==null ||finalScore > stuElective.getScoreTotal()) {
					stuElective.setScoreTotal(finalScore);
					stuElective.setScoreExam(finalScore);
					stuElective.setScoreHomework(finalScore);
					stuElective.setScoreUsual(finalScore);
					stuElective.setEnumConstByFlagScoreStatus(this.getMyListDAO().getEnumConstByNamespaceCode("FlagScoreStatus", "1"));
					try {
						this.getGeneralDao().save(stuElective);
					} catch (RuntimeException e) {
						throw new EntityException();
					}
				}
				
				try {
					this.getGeneralDao().save(stu);
				} catch (RuntimeException e) {
					throw new EntityException();
				}
				count += 1;
				continue;
			}
			//参加答辩的 总评成绩=论文成绩*30% + 答辩成绩*70%
			
			if (stu.getRejoinScore() == null) {
				sb.append(stu.getPeStudent().getTrueName()+"的答辩成绩未录入。<br>");
				continue;
			}
			rejoinScore = stu.getRejoinScore();
			scoreTotal = rejoinScore * rate + finalScore * (1-rate);
			stu.setScoreTotal(scoreTotal);
			//合成成绩同时将论文表中论文的最终成绩同步到选课表中 ：张羽 2008-12-29
			DetachedCriteria electiveDC = DetachedCriteria.forClass(PrTchStuElective.class);
			electiveDC.createCriteria("peStudent", "peStudent");
			electiveDC.createCriteria("prTchOpencourse", "prTchOpencourse").createAlias("peTchCourse", "peTchCourse");
			electiveDC.add(Restrictions.eq("peStudent", stu.getPeStudent()));
			electiveDC.add(Restrictions.eq("peTchCourse.name", "毕业论文"));
			List<PrTchStuElective> list = this.getGeneralDao().getList(electiveDC);
			if(list==null||list.isEmpty()){
				sb.append("学生" + stu.getPeStudent().getTrueName() + "的毕业论文选课记录不存在。<br>");
				continue;
			}
			PrTchStuElective stuElective = list.get(0);
			
			//如果本次成绩大于往次成绩则更新
			if (stuElective.getScoreTotal()==null ||scoreTotal > stuElective.getScoreTotal()) {
				stuElective.setScoreTotal(scoreTotal);
				stuElective.setScoreExam(scoreTotal);
				stuElective.setScoreHomework(scoreTotal);
				stuElective.setScoreUsual(scoreTotal);
				stuElective.setEnumConstByFlagScoreStatus(this.getMyListDAO().getEnumConstByNamespaceCode("FlagScoreStatus", "1"));
				
				try {
					this.getGeneralDao().save(stuElective);
				} catch (RuntimeException e) {
					throw new EntityException();
				}
			}
			
			try {
				this.getGeneralDao().save(stu);
			} catch (RuntimeException e) {
				throw new EntityException();
			}
			count += 1;
		}
		
		
		return count + "个成绩合成成功。<br>" + sb.toString();
	}

	
	/**
	 * 保存答辩成绩
	 * @param file
	 * @return
	 * @throws EntityException
	 */
	public int saveReplyScore(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;

		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		Set<PrTchStuPaper> scoreSet = new HashSet();
		
		//添加横向权限判断。取得用户可以操作的范围				
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if(userSession==null){
			throw new EntityException("批量上传学生作业成绩失败,无法取得您的身份信息！");
		}
		
		
		SsoUser ssoUser = (SsoUser)this.getMyListDAO().getById(SsoUser.class, userSession.getSsoUser().getId());
		Set edutypes = ssoUser.getPrPriManagerEdutypes();
		Set sites = ssoUser.getPrPriManagerSites();
		Set grades = ssoUser.getPrPriManagerGrades();
		Set majors = ssoUser.getPrPriManagerMajors();

		for (int i = 1; i < rows; i++) {
			try {
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，学号为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
				dcPeStudent.add(Restrictions.eq("regNo", temp));
				
				List<PeStudent> peStudentList = this.getGeneralDao().getList(dcPeStudent);
				
				if (peStudentList==null || peStudentList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学号不存在！<br/>");
					continue;
				}

				
				PeStudent peStudent =peStudentList.get(0);
				
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp != null && !"".equals(temp)) {
					if (!peStudent.getTrueName().equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，姓名或者学号不正确！<br/>");
					continue;
					}
				}
				
				if (!peStudent.getEnumConstByFlagStudentStatus().getCode().equals("4")){
					msg.append("第" + (i + 1) + "行数据，学生不是在籍状态！<br/>");
					continue;
				}
				
				//横向权限检查。
				if(edutypes!=null&&!edutypes.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerEdutype)object).getPeEdutype().getId().equals(peStudent.getPeEdutype().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个层次没有操作权限<br/>");
						continue;
					}
				}
				if(sites!=null&&!sites.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerSite)object).getPeSite().getId().equals(peStudent.getPeSite().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个学习中心没有操作权限<br/>");
						continue;	
					}
				}
				if(grades!=null&&!grades.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerGrade)object).getPeGrade().getId().equals(peStudent.getPeGrade().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个年级没有操作权限<br/>");
						continue;	
					}
				}
				if(majors!=null&&!majors.isEmpty()) {
					boolean checked = false;
					for (Object object : edutypes) {
						if(((PrPriManagerMajor)object).getPeMajor().getId().equals(peStudent.getPeMajor().getId())){
							checked = true;
							break;
						}
					}
					if(!checked){
						msg.append("第" + (i + 1) + "行数据，您对这个专业没有操作权限<br/>");
						continue;		
					}
				}
				//以上横向权限检查。
				
				temp = sheet.getCell(2, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，论文题目不能为空！<br/>");
					continue;
				}
				
				DetachedCriteria dcPrTchStuPaper = DetachedCriteria.forClass(PrTchStuPaper.class);
				DetachedCriteria dcFlagPaperRejoin = dcPrTchStuPaper.createCriteria("enumConstByFlagPaperRejoin", "enumConstByFlagPaperRejoin"); 
				DetachedCriteria dcPrTchPaperTitle = dcPrTchStuPaper.createCriteria("prTchPaperTitle", "prTchPaperTitle");
				DetachedCriteria dcPeSemester = dcPrTchPaperTitle.createCriteria("peSemester", "peSemester");
				dcPrTchStuPaper.add(Restrictions.eq("peStudent", peStudent));
				dcPeSemester.add(Restrictions.eq("flagActive", "1"));
				PrTchStuPaper prTchStuPaper = null;
				List<PrTchStuPaper> prTchStuPaperList = this.getGeneralDao().getList(dcPrTchStuPaper);

				if (prTchStuPaperList==null || prTchStuPaperList.isEmpty()) {
					msg.append("第" + (i + 1) + "行数据，学生当前学期没有进行毕业论文！<br/>");
					continue;
				}
				prTchStuPaper = prTchStuPaperList.get(0);
				
				if(!prTchStuPaper.getPrTchPaperTitle().getTitle().equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，论文题目错误！<br/>");
					continue;
				}
				
				if(!prTchStuPaper.getEnumConstByFlagPaperRejoin().getCode().equals("1")){
					msg.append("第" + (i + 1) + "行数据，学生没有被抽取参加答辩！<br/>");
					continue;
				}
					
				temp = sheet.getCell(3, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，答辩成绩不能为空！<br/>");
					continue;
				}
				if (!temp.matches(Const.score)){
					msg.append("第" + (i + 1) + "行数据，答辩成绩格式有误！成绩只能是0至100的整数，0至1位小数<br/>");
					continue;
				}
				
				Double score;
				try {
					score = Double.parseDouble(temp);
				} catch (RuntimeException e) {
					msg.append("第" + (i + 1) + "行数据，答辩成绩格式有误！<br/>");
					continue;
				}
				
				prTchStuPaper.setRejoinScore(score);
				
				if (!scoreSet.add(prTchStuPaper)) {
					msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
					continue;
				}
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("学生答辩成绩批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PrTchStuPaper prTchStuPaper : scoreSet) {
			try {
				this.getGeneralDao().save(prTchStuPaper);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EntityException("批量上传学生答辩成绩失败");
			}
		}
		return count;
	}
	
	/**
	 * 随机抽取答辩名单
	 * @return
	 * @throws EntityException
	 */
	public String savePaperRejoin() throws EntityException{
		String message = "";//保存信息
		int totalStu = Integer.parseInt(this.getMyListDAO().getSysValueByName("paperRejoinNober"));//参加答辩学生总数
		int alreadyRejoinNum = 0; //已经被抽取参加答辩的学生人数
		int scoreGe80 = 0; //分数在80分以上并且还未被抽取答辩的学生人数
		int scoreGe60To80 = 0;//分数在60至80分之间的还未被抽取答辩的学生人数
		EnumConst enumConstByFlagPaperRejoin=this.getMyListDAO().getEnumConstByNamespaceCode("FlagPaperRejoin", "1");//抽取参加答辩
		
		//已经被抽取参加答辩的列表
		List<PrTchStuPaper> alreadyPaperRejoinList = this.alreadyPaperRejoin();
		if(alreadyPaperRejoinList != null){
			alreadyRejoinNum = alreadyPaperRejoinList.size();
		}
		if(alreadyRejoinNum>=totalStu){
			throw new EntityException("已经被抽取答辩的学生共"+alreadyRejoinNum+"人,已经达到或者超过答辩的总人数"+totalStu+"人");
		}
		
		//终稿分数在80分以上未被抽取答辩的学生
		List<PrTchStuPaper> scoreIsGoodStuList = this.scoreIsGoodStu();
		if(scoreIsGoodStuList!=null&&scoreIsGoodStuList.size()>0){
			scoreGe80 = scoreIsGoodStuList.size();
			for (PrTchStuPaper prTchStuPaper : scoreIsGoodStuList) {
				prTchStuPaper.setEnumConstByFlagPaperRejoin(enumConstByFlagPaperRejoin);
				this.getGeneralDao().save(prTchStuPaper);
			}
			message +="共有"+scoreGe80+"名终稿成绩在80分以上的学生被自动抽取参加答辩</br>";
		}
		
		if(alreadyRejoinNum+scoreGe80>=totalStu){
			message +="本次共抽取"+scoreGe80+"人参加答辩，参加答辩的总人数是"+(alreadyRejoinNum+scoreGe80)+"人</br>";
			message +="随机抽取答辩名单操作完成";
			return message;
		}
		
		//终稿分数在60-80分之间未被抽取答辩的学生
		List<PrTchStuPaper> scoreIsPassStuList = this.scoreIsPassStu();
		if(scoreIsPassStuList!=null){
			scoreGe60To80 = scoreIsPassStuList.size();
		}
		
		if(alreadyRejoinNum+scoreGe80+scoreGe60To80<=totalStu){
			if(scoreGe60To80>0){
				for (PrTchStuPaper prTchStuPaper : scoreIsPassStuList) {
					prTchStuPaper.setEnumConstByFlagPaperRejoin(enumConstByFlagPaperRejoin);
					this.getGeneralDao().save(prTchStuPaper);
				}
				message +="共有"+scoreGe60To80+"名终稿成绩在60-80分之间的学生被自动抽取参加答辩</br>";
			}
			
			message +="本次共抽取"+(scoreGe80+scoreGe60To80)+"人参加答辩，参加答辩的总人数是"+(alreadyRejoinNum+scoreGe80+scoreGe60To80)+"人</br>";
			message +="终稿成绩在60分以上的学生已经全部被抽取参加答辩</br>";
			message +="随机抽取答辩名单操作完成";
			return message;
		}else {
			//需要再抽取的人数
			scoreGe60To80 = totalStu - alreadyRejoinNum - scoreGe80;
			for(int i = 0;i<scoreGe60To80;i++){
				Random rd = new Random();
				int randInt = rd.nextInt(scoreIsPassStuList.size());//产生随机数，用来抽取答辩名单
				PrTchStuPaper prTchStuPaper = scoreIsPassStuList.get(randInt);
				prTchStuPaper.setEnumConstByFlagPaperRejoin(enumConstByFlagPaperRejoin);
				this.getGeneralDao().save(prTchStuPaper);
				scoreIsPassStuList.remove(randInt);
			}
			message +="共有"+scoreGe60To80+"名终稿成绩在60-80分之间的学生被自动抽取参加答辩</br>";
			message +="本次共抽取"+(scoreGe80+scoreGe60To80)+"人参加答辩，参加答辩的总人数是"+(alreadyRejoinNum+scoreGe80+scoreGe60To80)+"人</br>";
		}
		
		
		return message;
	}
	

	
	/**
	 * 已经被抽取参加答辩的列表
	 * @return
	 */
	private List<PrTchStuPaper> alreadyPaperRejoin(){
		DetachedCriteria dcPrTchStuPaper = DetachedCriteria.forClass(PrTchStuPaper.class);
		dcPrTchStuPaper.createCriteria("prTchPaperTitle", "prTchPaperTitle")
			.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dcPrTchStuPaper.createCriteria("enumConstByFlagPaperRejoin", "enumConstByFlagPaperRejoin").add(Restrictions.eq("code", "1"));
		List<PrTchStuPaper> list = this.getGeneralDao().getList(dcPrTchStuPaper);
		return list;
		
	}
	
	/**
	 * 终稿分数在80分以上未被抽取答辩的学生
	 * @return
	 */
	private List<PrTchStuPaper> scoreIsGoodStu(){
		DetachedCriteria dcPrTchStuPaper = DetachedCriteria.forClass(PrTchStuPaper.class);
		dcPrTchStuPaper.createCriteria("prTchPaperTitle", "prTchPaperTitle")
			.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dcPrTchStuPaper.createCriteria("enumConstByFlagPaperRejoin", "enumConstByFlagPaperRejoin").add(Restrictions.eq("code", "0"));
		dcPrTchStuPaper.add(Restrictions.ge("finalScore", 80d));
		List<PrTchStuPaper> list = this.getGeneralDao().getList(dcPrTchStuPaper);
		return list;
		
	}
	/**
	 * 终稿分数在60-80分之间未被抽取答辩的学生
	 * @return
	 */
	private List<PrTchStuPaper> scoreIsPassStu(){
		DetachedCriteria dcPrTchStuPaper = DetachedCriteria.forClass(PrTchStuPaper.class);
		dcPrTchStuPaper.createCriteria("prTchPaperTitle", "prTchPaperTitle")
			.createCriteria("peSemester", "peSemester").add(Restrictions.eq("flagActive", "1"));
		dcPrTchStuPaper.createCriteria("enumConstByFlagPaperRejoin", "enumConstByFlagPaperRejoin").add(Restrictions.eq("code", "0"));
		dcPrTchStuPaper.add(Restrictions.and(Restrictions.ge("finalScore", 60d), Restrictions.lt("finalScore", 80d)));
		List<PrTchStuPaper> list = this.getGeneralDao().getList(dcPrTchStuPaper);
		return list;
		
	}
	/**
	 *  取出当前活动的学期
	 * @return
	 */
	private PeSemester getSemester(){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeSemester.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeSemester> peSemesterList = this.getGeneralDao().getList(dc);
		PeSemester	peSemester = peSemesterList.get(0);
		return peSemester;
	}
	
	/**
	 * 自动安排答辩时间和教室
	 * @return
	 * @throws EntityException
	 */
	public String saveRejoinSection() throws EntityException{
		String message = "";
		int count = 0;
		PeSemester peSemester = this.getSemester();
		DetachedCriteria dcPeTchRejoinSection = DetachedCriteria.forClass(PeTchRejoinSection.class);
		dcPeTchRejoinSection.add(Restrictions.eq("peSemester",peSemester));
		dcPeTchRejoinSection.addOrder(Order.asc("sequence"));
		List<PeTchRejoinSection>  peTchRejoinSectionList = this.getGeneralDao().getList(dcPeTchRejoinSection);
		if(peTchRejoinSectionList==null||peTchRejoinSectionList.size()!=12){
			throw new EntityException("当前学期答辩时间段不够12个，请先设置完本学期的答辩时间段再进行安排答辩时间操作");
		}
		
		DetachedCriteria dcPeTchRejoinRoom = DetachedCriteria.forClass(PeTchRejoinRoom.class);
		dcPeTchRejoinRoom.add(Restrictions.eq("peSemester",peSemester));
		dcPeTchRejoinRoom.addOrder(Order.asc("code"));
		List<PeTchRejoinRoom>  peTchRejoinRoomList = this.getGeneralDao().getList(dcPeTchRejoinRoom);
		if(peTchRejoinRoomList==null||peTchRejoinRoomList.size()!=6){
			throw new EntityException("当前学期答辩教室不够6个，请先设置完本学期的答辩教室再进行安排答辩时间操作");
		}
		
		DetachedCriteria dcPeMajor = DetachedCriteria.forClass(PeMajor.class);
		List<PeMajor> peMajorList = this.getGeneralDao().getList(dcPeMajor);
		int i = 0 ; //12个时间段 (0-11)
		int n = 0; // 6个教室 (0-5)
		for (PeMajor peMajor : peMajorList) {
			
			//这个专业参加答辩的学生
			DetachedCriteria dcPrTchStuPaper = DetachedCriteria.forClass(PrTchStuPaper.class);
			dcPrTchStuPaper.createCriteria("prTchPaperTitle", "prTchPaperTitle").add(Restrictions.eq("peSemester", peSemester));
			DetachedCriteria dcPeStudent = dcPrTchStuPaper.createCriteria("peStudent", "peStudent");
			dcPeStudent.add(Restrictions.eq("peMajor", peMajor));
			dcPrTchStuPaper.createCriteria("enumConstByFlagPaperRejoin", "enumConstByFlagPaperRejoin").add(Restrictions.eq("code", "1"));
			dcPeStudent.createCriteria("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus").add(Restrictions.eq("code", "4"));
			
			List<PrTchStuPaper> prTchStuPaperList = this.getGeneralDao().getList(dcPrTchStuPaper);
			if(prTchStuPaperList==null||prTchStuPaperList.size()==0){
				continue;
			}
			int j = 0; //20个学生(0-19)
			for (PrTchStuPaper prTchStuPaper : prTchStuPaperList) {
				//20个学生换时间段
				if(j==20){
					j=0;
					i++;
					//12个时间段换教室
					if(i==12){
						i = 0;
						n ++ ;
					}
				}
				prTchStuPaper.setPeTchRejoinSection(peTchRejoinSectionList.get(i));
				prTchStuPaper.setPeTchRejoinRoom(peTchRejoinRoomList.get(n));
				this.getGeneralDao().save(prTchStuPaper);
				j++;
				count++;
			}
			i++;
			if(i==12){
				i = 0;
				n ++ ;
			}
			
		}
		message = "共成功为"+count + "名学生自动安排答辩时间和答辩教室";
		return message;
	}
}
