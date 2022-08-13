/** 
 * Project Name:CETV_TEST 
 * File Name:UserAction.java 
 * Package Name:cn.zeppin.action.phone 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.Textbook;
import cn.zeppin.entity.User;
import cn.zeppin.entity.UserTextbook;
import cn.zeppin.service.api.IAreaService;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ITextBookService;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.service.api.IUserTextbookService;

/**
 * ClassName: UserAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月21日 下午2:26:25 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2085276363514278538L;

	private IUserService userService;
	private ITextBookService textbookService;
	private IAreaService areaService;
	private ISubjectService subjectService;
	private IGradeService gradeService;
	private IUserTextbookService userTextbookService;

	private int subjectId;
	private	Map<Integer,String> gradeMap;
	private	Map<Integer,String> areaMap;
	private	Map<Integer,Map<String,String>> textbookMap;
	
	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	public Map<Integer,Map<String,String>> getTextbookMap() {
		return textbookMap;
	}

	public void setTextbookMap(Map<Integer,Map<String,String>> textbookMap) {
		this.textbookMap = textbookMap;
	}
	
	public Map<Integer,String> getAreaMap() {
		return areaMap;
	}

	public void setAreaMap(Map<Integer,String> areaMap) {
		this.areaMap = areaMap;
	}
	
	public Map<Integer,String> getGradeMap() {
		return gradeMap;
	}

	public void setGradeMap(Map<Integer,String> gradeMap) {
		this.gradeMap = gradeMap;
	}
	
	public IAreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ITextBookService getTextbookService() {
		return textbookService;
	}

	public void setTextbookService(ITextBookService textbookService) {
		this.textbookService = textbookService;
	}

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}

	public IUserTextbookService getUserTextbookService() {
		return userTextbookService;
	}

	public void setUserTextbookService(IUserTextbookService userTextbookService) {
		this.userTextbookService = userTextbookService;
	}

	/**
	 * 选择年级
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午2:29:41 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String SearchGrade(){
		subjectId=this.getIntValue(request.getParameter("subject.id"));
		Grade grade=this.getSubjectService().getSubjectById(this.getIntValue(request.getParameter("subject.id"), 0)).getGrade();
		List<Grade> listGrade =new ArrayList<Grade>();
		listGrade.add(grade);
		this.gradeMap= new HashMap<Integer,String>();
		getLastLevel(listGrade);
		return "initGrade";
	}
	
	void getLastLevel(List<Grade> listGrade){
		for(Grade grade:listGrade){
			if(this.getGradeService().hasChild(grade.getId())){
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("scode", grade.getScode());
				searchMap.put("level", grade.getLevel()+1);
				List<Grade> listG=this.getGradeService().getGradeByParam(searchMap);
				getLastLevel(listG);
			}else{
				gradeMap.put(grade.getId(), this.getGradeService().getGradeFullName(grade));
			}
		}
	}
	
	/**
	 * 选择地区
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午2:29:41 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String SearchArea(){
		subjectId=this.getIntValue(request.getParameter("subject.id"));
		Map<String, Object> searchMap = new HashMap<String, Object>();
//		searchMap.put("level", 1);
		List<Area> areaList=this.getAreaService().getAreas(searchMap);
		this.areaMap= new HashMap<Integer,String>();
		for(Area area:areaList){
			areaMap.put(area.getId(), area.getName());
		}		
		return "initArea";
	}
	
	/**
	 * 选择教材
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午2:29:41 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String SearchTextbook(){
		subjectId=this.getIntValue(request.getParameter("subject.id"));
		User user = (User) session.getAttribute("userphone");
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("subject",subjectId);
		searchMap.put("grade", user.getGrade().getId());
		List<Textbook> TextbookList=this.getTextbookService().getTextbookByParam(searchMap);
		this.textbookMap= new HashMap<Integer,Map<String,String>>();
		for(Textbook tb:TextbookList){
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("name", tb.getName());
			dataMap.put("publisher", tb.getPublisher());
			dataMap.put("version", tb.getVersion());
			if(tb.getResource()!=null){
				dataMap.put("picture", tb.getResource().getPath());
			}else{
				dataMap.put("picture","img/m/t1.jpg");
			}
			textbookMap.put(tb.getId(), dataMap);
		}
		return "initTextbook";
	}
	
	/**
	 * 更新用户年级
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午2:29:41 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String UpdateGrade() {
		User user = (User) session.getAttribute("userphone");
		subjectId=this.getIntValue(request.getParameter("subject.id"), 0);
		int gradeId = this.getIntValue(request.getParameter("grade.id"), 0);
		Grade grade = this.getGradeService().getById(gradeId);
		user.setGrade(grade);
		this.getUserService().updateUser(user);
		return "gradeUpdate";
	}
	
	/**
	 * 更新用户地区
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午2:29:41 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "area.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String UpdateArea() {
		User user = (User) session.getAttribute("userphone");
		subjectId=this.getIntValue(request.getParameter("subject.id"), 0);
		int areaId = this.getIntValue(request.getParameter("area.id"), 0);
		Area area = this.getAreaService().getAreaById(areaId);
		user.setArea(area);
		this.getUserService().updateUser(user);
		return "areaUpdate";
	}
	
	/**
	 * 更新用户教材
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午2:29:41 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "textbook.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String UpdateTextbook() {
		
		User user = (User) session.getAttribute("userphone");

		subjectId = this.getIntValue(request.getParameter("subject.id"), 0);
		int textbookId = this.getIntValue(request.getParameter("textbook.id"), 0);

		Textbook tb = this.getTextbookService().getById(textbookId);
		Subject sub = this.getSubjectService().getSubjectById(subjectId);

		this.getUserService().updateUser(user);

		// 跟新教材信息
		// 获取已有的教材版本信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user.id", user.getId());
		map.put("grade.id", user.getGrade().getId());
		map.put("subject.id", subjectId);

		UserTextbook userTB = this.getUserTextbookService().getUserTextbookByMap(map);
		if (userTB != null) {
			this.getUserTextbookService().deleteUserTextbook(userTB);
		}
		UserTextbook utb = new UserTextbook();
		utb.setGrade(user.getGrade());
		utb.setSubject(sub);
		utb.setTextbook(tb);
		utb.setUser(user);
		
		this.getUserTextbookService().addUserTextbook(utb);
		return "textbookUpdate";

	}
	
	/**
	 * 更新用户基本信息
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午2:29:41 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "textbook.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "area.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void UpdateSetting() {
		/**
		 * ================================================== 
		 * 1.设置用户的学段 2.设置教材
		 * 3.设置地区
		 * 
		 * 1.先设置用户学段与地区信息，最后在设置用户教材与知识点信息
		 * ==================================================
		 */

		User user = (User) session.getAttribute("userphone");

		int subjectId = this.getIntValue(request.getParameter("subject.id"), 0);
		int gradeId = this.getIntValue(request.getParameter("grade.id"), 0);
		int textbookId = this.getIntValue(request.getParameter("textbook.id"), 0);
		int areaId = this.getIntValue(request.getParameter("area.id"), 0);

		Grade grade = this.getGradeService().getById(gradeId);
		Area area = this.getAreaService().getAreaById(areaId);
		Textbook tb = this.getTextbookService().getById(textbookId);
		Subject sub = this.getSubjectService().getSubjectById(subjectId);

		user.setArea(area);
		user.setGrade(grade);

		this.getUserService().updateUser(user);

		// 跟新教材信息
		// 获取已有的教材版本信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user.id", user.getId());
		map.put("grade.id", gradeId);
		map.put("subject.id", subjectId);

		UserTextbook userTB = this.getUserTextbookService().getUserTextbookByMap(map);
		if (userTB != null) {
			this.getUserTextbookService().deleteUserTextbook(userTB);
		}
		UserTextbook utb = new UserTextbook();
		utb.setGrade(grade);
		utb.setSubject(sub);
		utb.setTextbook(tb);
		utb.setUser(user);
		
		this.getUserTextbookService().addUserTextbook(utb);
		

	}

}
