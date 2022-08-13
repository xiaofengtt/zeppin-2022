/** 
 * Project Name:Self_Cool  
 * File Name:ManagerAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Role;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.SysUserGrade;
import cn.zeppin.entity.SysUserSubject;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.ISysUserGradeService;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.service.api.ISysUserSubjectService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/** 
 * ClassName: ManagerAction <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月20日 下午7:05:24 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
public class ManagerAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;

	private ISysUserService sysUserService;
	private ISysUserSubjectService sysUserSubjectService;
	private ISysUserGradeService sysUserGradeService;
	private IGradeService gradeService;
	
	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public ISysUserSubjectService getSysUserSubjectService() {
		return sysUserSubjectService;
	}

	public void setSysUserSubjectService(ISysUserSubjectService sysUserSubjectService) {
		this.sysUserSubjectService = sysUserSubjectService;
	}

	public ISysUserGradeService getSysUserGradeService() {
		return sysUserGradeService;
	}

	public void setSysUserGradeService(ISysUserGradeService sysUserGradeService) {
		this.sysUserGradeService = sysUserGradeService;
	}

	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}
	
	/**
	 * 添加运营管理者信息
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "phone", type = ValueType.PHONE, nullable = false, emptyable = false)
	@ActionParam(key = "email", type = ValueType.EMAIL, nullable = false, emptyable = false)
	public void Add(){
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String[] subjects = request.getParameterValues("subjects");
		String[] grades = request.getParameterValues("grades");
		if (getSysUserService().getSysUser(email) != null){
			result.init(FAIL_STATUS, "已存在该邮箱的用户！", null);
		}
		else if (getSysUserService().getSysUser(phone)!= null){
			result.init(FAIL_STATUS, "已存在该手机的用户！", null);
		}
		else {
			SysUser sysUser = new SysUser();
			sysUser.setRole(new Role());
			sysUser.getRole().setId(Dictionary.USER_ROLE_MANAGER);
			sysUser.setEmail(email);
			sysUser.setPhone(phone);
			sysUser.setName(name);
			sysUser.setStatus(Dictionary.USER_STATUS_NOMAL);
			sysUser.setPassword(phone.substring(phone.length()-6, phone.length()));
			sysUser.setOrganization(currentUser.getOrganization()); //本部运营管理者与超级管理员的机构保持一致
			sysUser.setSysUser(currentUser); //创建人
			sysUser.setCreatetime(new Timestamp((new Date()).getTime()));
			
			List<Subject> subjectList = new ArrayList<>();
			List<Grade> gradeList = new ArrayList<>();
			// 添加编辑学科权限
			if (subjects != null) {
				for (String subjectID : subjects) {
					Subject subject = new Subject();
					subject.setId(Integer.valueOf(subjectID));
					subjectList.add(subject);
				}
			}
			// 添加编辑学段权限
			if (grades != null) {
				for (String gradeID : grades) {
					Grade grade = new Grade();
					grade.setId(Integer.valueOf(gradeID));
					gradeList.add(grade);
				}
			}
			this.getSysUserService().addSysUser(sysUser, subjectList, gradeList);
			Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
			result.init(SUCCESS_STATUS, "添加运营管理者信息成功！", data);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 删除运营管理者用户
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete(){
		ActionResult result = new ActionResult();
		Integer userID = Integer.valueOf(request.getParameter("id"));
		SysUser sysUser = this.getSysUserService().getSysUserById(userID);
		if (sysUser != null && sysUser.getRole().getId() == Dictionary.USER_ROLE_MANAGER){
			this.getSysUserService().deleteSysUser(sysUser);
			Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
			result.init(SUCCESS_STATUS, "删除运营管理者成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的运营管理者ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 加载运营管理者信息，一般用于修改的时候load
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load(){
		ActionResult result = new ActionResult();
		Integer userID = Integer.valueOf(request.getParameter("id"));
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		SysUser sysUser = this.getSysUserService().getSysUserById(userID);
		if (sysUser != null && sysUser.getRole().getId() == Dictionary.USER_ROLE_MANAGER) {
			Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
			// load 学科权限
			List<Map<String, Object>> subjectList = new ArrayList<>();
			List<SysUserSubject> sysUserSubjects = this.getSysUserSubjectService().getSysUserSubjects(sysUser);
			for (SysUserSubject sysUserSubject : sysUserSubjects) {
				Map<String, Object> subject = new HashMap<>();
				subject.put("subject" + split + "id", sysUserSubject.getSubject().getId());
				subject.put("subject" + split + "name", sysUserSubject.getSubject().getName());
				subjectList.add(subject);
			}

			// load 学段权限
			List<Map<String, Object>> gradeList = new ArrayList<>();
			List<SysUserGrade> sysUserGrades = this.getSysUserGradeService().getSysUserGrades(sysUser);
			for (SysUserGrade sysUserGrade : sysUserGrades) {
				Map<String, Object> grade = new HashMap<>();
				grade.put("grade" + split + "id", sysUserGrade.getGrade().getId());
				String gradeName = this.getGradeService().getGradeFullName(sysUserGrade.getGrade());
				grade.put("grade" + split + "name", gradeName);
				gradeList.add(grade);
			}
			// 返回内容加入权限
			data.put("subjects", subjectList);
			data.put("grades", gradeList);
			result.init(SUCCESS_STATUS, "加载运营管理者信息成功！", data);
		}
		else {
			result.init(FAIL_STATUS, "无效的ID！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

	/**
	 * 修改运营管理者信息
	 * @author Clark
	 * @date: 2014年7月20日 下午6:41:58 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "phone", type = ValueType.PHONE, nullable = false, emptyable = false)
	@ActionParam(key = "email", type = ValueType.EMAIL, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update(){
		ActionResult result = new ActionResult();
		Integer userID = Integer.valueOf(request.getParameter("id"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String[] subjects = request.getParameterValues("subjects");
		String[] grades = request.getParameterValues("grades");
		Short status = Short.valueOf(request.getParameter("status"));
//		if (getSysUserService().getSysUser(email) != null && getSysUserService().getSysUser(email).getId()!=userID ){
//			result.init(FAIL_STATUS, "已存在该邮箱的用户！", null);
//		}
//		else if (getSysUserService().getSysUser(phone)!= null && getSysUserService().getSysUser(phone).getId()!=userID){
//			result.init(FAIL_STATUS, "已存在该手机的用户！", null);
//		}else{
			SysUser sysUser = this.getSysUserService().getSysUserById(userID);
			if (sysUser != null && sysUser.getRole().getId() == Dictionary.USER_ROLE_MANAGER){
				sysUser.setEmail(email);
				sysUser.setPhone(phone);
				sysUser.setName(name);
				sysUser.setStatus(status);
				List<Subject> subjectList = new ArrayList<>();
				List<Grade> gradeList = new ArrayList<>();
				// 编辑学科权限
				if (subjects != null) {
					for (String subjectID : subjects) {
						Subject subject = new Subject();
						subject.setId(Integer.valueOf(subjectID));
						subjectList.add(subject);
					}
				}
				// 编辑学段权限
				if (grades != null) {
					for (String gradeID : grades) {
						Grade grade = new Grade();
						grade.setId(Integer.valueOf(gradeID));
						gradeList.add(grade);
					}
				}
				this.getSysUserService().updateSysUser(sysUser,subjectList,gradeList); 
				Map<String, Object> data = SerializeEntity.sysUser2Map(sysUser);
				result.init(SUCCESS_STATUS, "修改运营管理者信息成功！", data);
			}
			else {
				result.init(FAIL_STATUS, "无效的运营管理者ID信息！", null);
			}
//		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);		
	}

	/**
	 * 搜索
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "email", type = ValueType.STRING)
	@ActionParam(key = "phone", type = ValueType.STRING)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "organization.id", type = ValueType.NUMBER)
	@ActionParam(key = "organization.name", type = ValueType.STRING)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "sysUser.name", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List(){
		
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("email", request.getParameter("email"));
		searchMap.put("phone", request.getParameter("phone"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("organization.id", request.getParameter("organization.id"));
		searchMap.put("organization.name", request.getParameter("organization.name"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));
		searchMap.put("sysUser.name", request.getParameter("sysUser.name"));
		
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		
		int recordCount =  getSysUserService().searchManagerCount(currentUser, searchMap);
		int pageCount = (int) Math.ceil((double) recordCount/pagesize);

		List<SysUser> editorList = getSysUserService().searchManager(currentUser, searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (editorList != null && editorList.size() > 0){
			for (SysUser user : editorList){
				Map<String, Object> data = SerializeEntity.sysUser2Map(user);
				//加自定义属性
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);	
	}

}
