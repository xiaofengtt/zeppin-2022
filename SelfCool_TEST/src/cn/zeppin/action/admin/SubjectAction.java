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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.CategoryRetrieve;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Retrieve;
import cn.zeppin.entity.RetrieveType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectCountdown;
import cn.zeppin.entity.SubjectRetrieve;
import cn.zeppin.entity.UserSubject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.ICategoryRetrieveService;
import cn.zeppin.service.api.ICategoryService;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IRetrieveService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISubjectCountDownService;
import cn.zeppin.service.api.ISubjectRetrieveService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.IUserSubjectService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: ManagerAction <br/>
 * date: 2014年7月20日 下午7:05:24 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class SubjectAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;

	private ISubjectService subjectService;
	private ICategoryService categoryService;
	private IGradeService gradeService;
	private ISubjectCountDownService subjectCountDownService;
	private ICategoryRetrieveService categoryRetrieveService;
	private IRetrieveService retrieveService;
	private ISubjectRetrieveService subjectRetrieveService;
	private IUserSubjectService userSubjectService;

	private ISsoUserService ssoUserService;

	private Hashtable<RetrieveType, List<Retrieve>> hashRetrieves = new Hashtable<RetrieveType, List<Retrieve>>();
	private Hashtable<Integer, Integer> hashSubRete = new Hashtable<Integer, Integer>();
	private int subjectId;

	/**
	 * 添加学科
	 * 
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "shortname", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		Integer categoryID = Integer.valueOf(request.getParameter("category.id"));
		String shortName = this.request.getParameter("shortname");
		short status = Short.valueOf(this.request.getParameter("status"));

		Subject subject = new Subject();
		subject.setSysUser(currentUser);
		subject.setName(name);
		subject.setStatus(status);
		subject.setCategory(this.getCategoryService().getCategoryById(categoryID));
		subject.setShortname(shortName);
		subject.setCreatetime(new Timestamp(System.currentTimeMillis()));

		// 设置学段，目前不需要
		if (request.getParameterMap().containsKey("grade.id")) {
			if (Utlity.isNumeric(request.getParameter("grade.id"))) {
				Integer gradeID = Integer.parseInt(request.getParameter("grade.id"));
				Grade grade = this.getGradeService().getGradeById(gradeID);
				if (grade == null) {
					// 学段不存在，原来的不做修改
				} else {
					subject.setGrade(grade);
				}
			}
		}

		// 如果需要设置 检索条件的

		this.getSubjectService().addSubject(subject);

		Map<String, Object> data = SerializeEntity.subject2Map(subject);
		result.init(SUCCESS_STATUS, "添加学科成功！", data);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除学科
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		ActionResult result = new ActionResult();
		Integer subjectID = Integer.valueOf(request.getParameter("id"));
		Subject subject = this.getSubjectService().getSubjectById(subjectID);

		if (subject != null) {
			this.getSubjectService().deleteSubject(subject);
			Map<String, Object> data = SerializeEntity.subject2Map(subject);
			result.init(SUCCESS_STATUS, "删除分类科目检索范围成功！", data);
		} else {
			result.init(FAIL_STATUS, "无效的分类科目检索范围ID信息！", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载学科，一般用于修改的时候load
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
//	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {
		ActionResult result = new ActionResult();
		Integer subjectID = Integer.valueOf(request.getParameter("id"));
		String split = this.request.getParameter("split");
		split = split == null ? "_" : split;

		Subject subject = this.getSubjectService().getSubjectById(subjectID);

		if (subject != null) {
			Map<String, Object> data = SerializeEntity.subject2Map(subject, split);
			result.init(SUCCESS_STATUS, "加载学科信息成功！", data);
		} else {
			result.init(FAIL_STATUS, "无效的学科ID信息！", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 修改学科
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午6:41:58 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "category.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "shortname", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update() {

		ActionResult result = new ActionResult();
		int subjectID = this.getIntValue(request.getParameter("id"));
		String name = request.getParameter("name");
		Integer categoryID = Integer.valueOf(request.getParameter("category.id"));
		String shortName = this.request.getParameter("shortname");
		short status = Short.valueOf(this.request.getParameter("status"));

		Subject subject = this.getSubjectService().getSubjectById(subjectID);
		if (subject != null) {

			subject.setName(name);
			subject.setCategory(this.getCategoryService().getCategoryById(categoryID));
			subject.setShortname(shortName);
			subject.setStatus(status);

			if (request.getParameterMap().containsKey("grade.id")) {
				if (Utlity.isNumeric(request.getParameter("grade.id"))) {
					Integer gradeID = Integer.parseInt(request.getParameter("grade.id"));
					Grade grade = this.getGradeService().getGradeById(gradeID);
					subject.setGrade(grade);
				}
			}

			this.getSubjectService().updateSubject(subject);
			Map<String, Object> data = SerializeEntity.subject2Map(subject);
			result.init(SUCCESS_STATUS, "修改学科信息成功！", data);

		} else {
			result.init(FAIL_STATUS, "无效的学科ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 学科列表
	 */
//	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "category.id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "shortname", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {

		ActionResult result = new ActionResult();

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("shortname", request.getParameter("shortname"));
		searchMap.put("category.id", request.getParameter("category.id"));
		searchMap.put("grade.id", request.getParameter("grade.id"));

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.getSubjectService().getSubjectCountByParams(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Subject> subjectList = getSubjectService().getSubjectByParams(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (subjectList != null && subjectList.size() > 0) {
			for (Subject subject : subjectList) {

				Map<String, Object> data = SerializeEntity.subject2Map(subject);

				Map<String, Object> countDownMap = new HashMap<String, Object>();
				countDownMap.put("subject.id", subject.getId());
				countDownMap.put("status", Dictionary.SUBJECT_COUNT_DOWN_STATUS_NOMAL);
				List<SubjectCountdown> listCountDown = this.getSubjectCountDownService().getSubjectCountdowns(countDownMap, -1, -1);
				if (listCountDown != null && !listCountDown.isEmpty()) {
					data.put("isExamtime", true);
					data.put("examtime", Utlity.timeSpanToDateString(listCountDown.get(0).getExamTime()));
					data.put("examtimeId", listCountDown.get(0).getId());
				} else {
					data.put("isExamtime", false);
					data.put("examtime", "暂无考试时间");
					data.put("examtimeId", -1);
				}

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
	
	/**
	 * 学科列表
	 */
	@ActionParam(key = "category.id", type = ValueType.NUMBER)
	@ActionParam(key = "uid", type = ValueType.NUMBER, emptyable = false, nullable = false)
	public void UserList() {

		ActionResult result = new ActionResult();
		
		
		SsoUser ssoUser = (SsoUser) this.session.getAttribute("user");

		int uid = this.getIntValue(request.getParameter("uid"));

		if (ssoUser == null) {
			ssoUser = this.getSsoUserService().getById(uid);
			this.session.setAttribute("user", ssoUser);
		}

		// ======================================================================================//
		// 1.先查询出与用户关联的学科，用于判断用户是否有关联过改学科，状态为停用的不做查询。
		// 2.生成MAP，用于key查找
		// ======================================================================================//

		Map<String, Object> userSubjectMap = new Hashtable<String, Object>();
		userSubjectMap.put("user.id", ssoUser.getId());
		userSubjectMap.put("status", Dictionary.NORMAL);

		List<UserSubject> userSubLists = this.getUserSubjectService().getUserSubjectsByMap(userSubjectMap, null, -1, -1);
		Map<Integer, Object> userSubMap = new Hashtable<Integer, Object>();
		for (UserSubject us : userSubLists) {
			if (!userSubMap.containsKey(us.getSubject().getId())) {
				userSubMap.put(us.getSubject().getId(), 1);
			}
		}

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("category.id", request.getParameter("category.id"));

		String[] retrieves = this.request.getParameterValues("retrieve.id");
		if (retrieves != null && retrieves.length > 0) {
			searchMap.put("retrieve.id", retrieves);
		}

		List<Subject> subjectList = getSubjectService().getSubjectByParams(searchMap, null, -1, -1);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (subjectList != null && subjectList.size() > 0) {
			for (Subject subject : subjectList) {

				Map<String, Object> data = SerializeEntity.subject2Map(subject);

				Map<String, Object> retrieveMap = new Hashtable<String, Object>();
				retrieveMap.put("subject.id", subject.getId());

				List<SubjectRetrieve> lsSrs = this.getSubjectRetrieveService().getSubjectRetrieves(retrieveMap);
				if (lsSrs != null && !lsSrs.isEmpty()) {
					StringBuilder retrieveName = new StringBuilder();
					retrieveName.append("(");
					for (SubjectRetrieve sr : lsSrs) {
						retrieveName.append(sr.getRetrieve().getName()).append(",");
					}
					retrieveName.delete(retrieveName.length() - 1, retrieveName.length());
					retrieveName.append(")");

					data.put("name", subject.getName() + retrieveName.toString());
				}

				if (userSubMap.containsKey(subject.getId())) {
					data.put("choose", 1);
				} else {
					data.put("choose", 0);
				}

				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 跳转设置检索分类值
	 * 
	 * @return
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String FoRetrieve() {

		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
		Subject subject = this.getSubjectService().getSubjectById(subjectId);
		this.setSubjectId(subjectId);

		// 加载 初始值
		Category category = subject.getCategory();
		List<CategoryRetrieve> listCategoryRetrieve = this.getCategoryRetrieveService().getCategoryRetrieves(category);

		if (listCategoryRetrieve != null && !listCategoryRetrieve.isEmpty()) {

			for (CategoryRetrieve cr : listCategoryRetrieve) {

				RetrieveType rt = cr.getRetrieveType();

				Map<String, Object> map = new Hashtable<String, Object>();
				map.put("retrieveType.id", rt.getId());
				map.put("status", Dictionary.RETRIEVE_STATUS_NOMAL);

				List<Retrieve> lstRetrieves = this.getRetrieveService().searchRetrieves(map, null, -1, -1);

				this.getHashRetrieves().put(rt, lstRetrieves);

			}

		}

		// 学科所选的值
		Map<String, Object> srMap = new Hashtable<String, Object>();
		srMap.put("subject.id", subjectId);

		List<SubjectRetrieve> lstSr = this.getSubjectRetrieveService().getSubjectRetrieves(srMap);
		for (SubjectRetrieve sr : lstSr) {
			int type = sr.getRetrieve().getRetrieveType().getId();
			int value = sr.getRetrieve().getId();
			this.getHashSubRete().put(type, value);
		}

		return "subRetrieve";
	}

	/**
	 * 添加学科Retrieve
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void AddRetrieve() {

		ActionResult result = new ActionResult();

		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
		Subject subject = this.getSubjectService().getSubjectById(subjectId);

		String[] retrieves = this.request.getParameterValues("retrieveId");

		List<Retrieve> lsRes = new ArrayList<Retrieve>();

		for (String re : retrieves) {

			int reId = this.getIntValue(re, -1);
			Retrieve rete = this.getRetrieveService().getRetrieveById(reId);
			if (rete != null) {
				lsRes.add(rete);
			}

		}

		this.getSubjectRetrieveService().addSubjectRetrieves(subject, lsRes);

		result.init(SUCCESS_STATUS, "添加成功", null);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 搜索 学科 根据 知识分类分组
	 * 
	 * @author jiangfei
	 * @date: 2014年8月26日 下午5:26:24 <br/>
	 */
	@SuppressWarnings("unchecked")
	public void SearchAllSubject() {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		List<Subject> liS = new ArrayList<Subject>();

		// ====================判断角色的学科权限=============================
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		if (currentUser.getRole().getId() != Dictionary.USER_ROLE_SUPERADMIN && session.getAttribute("usersubject") != null) {

			List<Subject> tmpSubject = (List<Subject>) session.getAttribute("usersubject");
			liS.addAll(tmpSubject);

		} else {
			HashMap<String, Object> ht = new HashMap<>();
			// ht.put("status", Dictionary.SUBJECT_STATUS_NOMAL);

			int records = this.getSubjectService().getSubjectCountByParams(ht);
			liS.addAll(this.getSubjectService().getSubjectByParams(ht, null, 0, records));

		}

		LinkedHashMap<Category, List<Subject>> mapSubject = new LinkedHashMap<Category, List<Subject>>();

		for (Subject subject : liS) {

			if (mapSubject.containsKey(subject.getCategory())) {

				LinkedList<Subject> lisubject = (LinkedList<Subject>) mapSubject.get(subject.getCategory());
				lisubject.add(subject);

			} else {

				LinkedList<Subject> lisubject = new LinkedList<Subject>();
				lisubject.add(subject);
				mapSubject.put(subject.getCategory(), lisubject);

			}

		}

		List<Map<String, Object>> liM = new ArrayList<>();
		Iterator<Entry<Category, List<Subject>>> iterator = mapSubject.entrySet().iterator();

		while (iterator.hasNext()) {

			Entry<Category, List<Subject>> entry = iterator.next();

			Map<String, Object> map = SerializeEntity.category2Map(entry.getKey(), split);
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();

			for (Subject sub : entry.getValue()) {
				listmap.add(SerializeEntity.subject2Map(sub, split));
			}
			map.put("data", listmap);

			liM.add(map);

		}

		result.init(SUCCESS_STATUS, null, liM);
		Utlity.ResponseWrite(result, dataType, response);

	}

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public ICategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}

	public ISubjectCountDownService getSubjectCountDownService() {
		return subjectCountDownService;
	}

	public void setSubjectCountDownService(ISubjectCountDownService subjectCountDownService) {
		this.subjectCountDownService = subjectCountDownService;
	}

	public ICategoryRetrieveService getCategoryRetrieveService() {
		return categoryRetrieveService;
	}

	public void setCategoryRetrieveService(ICategoryRetrieveService categoryRetrieveService) {
		this.categoryRetrieveService = categoryRetrieveService;
	}

	public IRetrieveService getRetrieveService() {
		return retrieveService;
	}

	public void setRetrieveService(IRetrieveService retrieveService) {
		this.retrieveService = retrieveService;
	}

	public Hashtable<RetrieveType, List<Retrieve>> getHashRetrieves() {
		return hashRetrieves;
	}

	public void setHashRetrieves(Hashtable<RetrieveType, List<Retrieve>> hashRetrieves) {
		this.hashRetrieves = hashRetrieves;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public ISubjectRetrieveService getSubjectRetrieveService() {
		return subjectRetrieveService;
	}

	public void setSubjectRetrieveService(ISubjectRetrieveService subjectRetrieveService) {
		this.subjectRetrieveService = subjectRetrieveService;
	}

	public Hashtable<Integer, Integer> getHashSubRete() {
		return hashSubRete;
	}

	public void setHashSubRete(Hashtable<Integer, Integer> hashSubRete) {
		this.hashSubRete = hashSubRete;
	}

	public IUserSubjectService getUserSubjectService() {
		return userSubjectService;
	}

	public void setUserSubjectService(IUserSubjectService userSubjectService) {
		this.userSubjectService = userSubjectService;
	}

	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}


}
