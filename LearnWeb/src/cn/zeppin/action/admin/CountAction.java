package cn.zeppin.action.admin;


import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.IOrganizationService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.IUserAccessLogService;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.service.api.IUserTestItemService;
import cn.zeppin.service.api.IUserTestService;
import cn.zeppin.utility.Utlity;

public class CountAction extends BaseAction {


	private static final long serialVersionUID = 8490089490576020871L;

	private IItemService itemService;
	private IOrganizationService organizationService;
	private ISubjectService subjectService;
	private IGradeService gradeService;
	private IKnowledgeService knowledgeService;
	private IUserService userService;
	private IUserAccessLogService userAccessLogService;
	private IUserTestService userTestService;
	private IUserTestItemService userTestItemService;

	public IKnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}
	
	public IUserAccessLogService getUserAccessLogService() {
		return userAccessLogService;
	}

	public void setUserAccessLogService(IUserAccessLogService userAccessLogService) {
		this.userAccessLogService = userAccessLogService;
	}
	
	public IUserService getUserService() {
		return userService;
	}

	public IUserTestService getUserTestService() {
		return userTestService;
	}

	public void setUserTestService(IUserTestService userTestService) {
		this.userTestService = userTestService;
	}

	public IUserTestItemService getUserTestItemService() {
		return userTestItemService;
	}

	public void setUserTestItemService(IUserTestItemService userTestItemService) {
		this.userTestItemService = userTestItemService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
	
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
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
	
	@SuppressWarnings({ "deprecation" })
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")	
	public void SearchTotalCount(){
		ActionResult actionResult = new ActionResult();
		List<Map<String, Object>> lstData = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		Date date=new Date();
		date.setHours(0);date.setMinutes(0);date.setSeconds(0);
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1);
		
		for(int i=0;i<10;i++){
			Map<String, Object> data = new HashMap<>();
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("level", 1);
			calendar.add(Calendar.DATE, -1);
			date=calendar.getTime();
			Timestamp ts = new Timestamp(date.getTime());
			searchMap.put("endtime", ts.toString());
			int total=this.itemService.searchItemCount(searchMap);
			data.put("date", (date.getMonth()+1)+"月"+date.getDate()+"日");
			data.put("total", total);
			lstData.add(data);
		}
		actionResult.init(SUCCESS_STATUS, "数据获成功", lstData);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}
	
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")	
	public void SearchByOrganization(){
		ActionResult actionResult = new ActionResult();
		List<Map<String, Object>> lstData = new ArrayList<>();
		List<Organization> lstOrg=new ArrayList<Organization>();
		lstOrg=this.getOrganizationService().getAllOrganization();
		for(Organization org:lstOrg){
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("sysUser.organization.id", org.getId());
			searchMap.put("level", 1);
			int itemCount=this.getItemService().searchItemCount(searchMap);
			Map<String, Object> data = SerializeEntity.organization2Map(org);
			data.put("itemCount", itemCount);	
			lstData.add(data);
		}
		actionResult.init(SUCCESS_STATUS, "数据获成功", lstData);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	public void SearchBySubject(){
		ActionResult actionResult = new ActionResult();
		List<Map<String, Object>> lstData = new ArrayList<>();
		List<Subject> lstSub=new ArrayList<Subject>();
		lstSub=this.getSubjectService().getAllSubject();
		for(Subject sub:lstSub){
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("subject.id", sub.getId());
			searchMap.put("level", 1);
			int itemCount=this.getItemService().searchItemCount(searchMap);
			Map<String, Object> data = SerializeEntity.subject2Map(sub	, ".");
			data.put("itemCount", itemCount);
			lstData.add(data);
		}
		actionResult.init(SUCCESS_STATUS, "数据获成功", lstData);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}
	
	/**
	 * 查询出所有知识点
	 * 
	 * @author Administrator
	 * @date: 2014年9月4日 下午2:22:05 <br/>
	 */
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	public void SearchByKnowledge() {

		ActionResult result = new ActionResult();
		String split =".";

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("level", 1);

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, "", 0, recordCount);
		List<Map<String, Object>> dataList = new ArrayList<>();

		if (knowledgeList != null && knowledgeList.size() > 0) {
			for (Knowledge knowledge : knowledgeList) {

				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge, split);

				Map<String, Object> hasChildCount = new HashMap<String, Object>();
				hasChildCount.put("knowledge.id", knowledge.getId());
				boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;
				data.put("haschild", hasChild);
				Map<String, Object> search = new HashMap<>();
				search.put("knowledge.scode", knowledge.getScode());
				search.put("level", 1);
				int itemCount=this.getItemService().searchItemCount(search);
				data.put("itemCount", itemCount);
				if (hasChild) {
					LoadNextKnowledge(knowledge, data, split);
				}
				dataList.add(data);

			}
		}

		result.init(SUCCESS_STATUS, "数据获成功", dataList);
		Utlity.ResponseWrite(result, dataType, response);

	}
	
	
	public void LoadNextKnowledge(Knowledge knowledge, Map<String, Object> map, String split) {

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("knowledge.id", knowledge.getId());

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, "", 0, recordCount);
		List<Map<String, Object>> dataList = new ArrayList<>();

		for (Knowledge knowle : knowledgeList) {
			Map<String, Object> data = SerializeEntity.knowledge2Map(knowle, split);
			Map<String, Object> hasChildCount = new HashMap<String, Object>();
			hasChildCount.put("knowledge.id", knowle.getId());
			boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;
			data.put("haschild", hasChild);
			Map<String, Object> search = new HashMap<>();
			search.put("knowledge.scode", knowle.getScode());
			search.put("level", 1);
			int itemCount=this.getItemService().searchItemCount(search);
			data.put("itemCount", itemCount);
			if (hasChild) {
				LoadNextKnowledge(knowle, data, split);
			}
			dataList.add(data);

		}
		map.put("data", dataList);

	}
	
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	public void SearchUserSubject(){
		ActionResult actionResult = new ActionResult();
		List<Map<String, Object>> lstData = new ArrayList<>();
		List<Subject> lstSub=new ArrayList<Subject>();
		lstSub=this.getSubjectService().getAllSubject();
		for(Subject subject:lstSub){
			Map<String, Object> searchMap = new HashMap<>();
			Map<String, Object> dataMap = new HashMap<>();
			searchMap.put("paper.subject.id", subject.getId());
			int count=this.getUserTestService().getUserTestCount(searchMap);
			dataMap.put("id", subject.getId());
			dataMap.put("name", subject.getName());
			dataMap.put("count", count);
			lstData.add(dataMap);
		}	
		actionResult.init(SUCCESS_STATUS, "数据获成功", lstData);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}
	
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	public void SearchUserGrade(){
		ActionResult result = new ActionResult();
		String split = ".";
		List<Grade> lstGrades = new ArrayList<>();
		
		HashMap<String, Object> paras = new HashMap<String, Object>();
		paras.put("level", 2);
		paras.put("parent", 29);
		int totalCount = gradeService.getCountByParas(paras, true);
		lstGrades.addAll(gradeService.getAllGradesByAdmin(0, totalCount, "", paras));

		// 把下级所有学段加载出来
		List<Map<String, Object>> lstData = new ArrayList<>();
		if (lstGrades != null && lstGrades.size() > 0) {
			for (Grade tGrade : lstGrades) {
				Map<String, Object> data = SerializeEntity.grade2Map(tGrade, split);
				
				Map<String, Object> searchMap = new HashMap<>();
				searchMap.put("user.grade.scode", tGrade.getScode());
				int totalcount=this.getUserAccessLogService().getCountByParam(searchMap);
				searchMap.put("time", "today");
				int daycount=this.getUserAccessLogService().getCountByParam(searchMap);
				
				data.put("totalcount", totalcount);
				data.put("daycount", daycount);
				lstData.add(data);
			}
		}

		result.init(SUCCESS_STATUS, "数据获成功", lstData);
		Utlity.ResponseWrite(result, dataType, response);

	}
	@AuthorityParas(denyUser = {}, errMsg = "无权访问列表", user = {}, userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	public void SearchUserKnowledge(){
		ActionResult result = new ActionResult();
		String split =".";
		
		DecimalFormat df = new DecimalFormat();
		String style = "0.0";
		df.applyPattern(style);
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("level", 1);

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, "", 0, recordCount);
		List<Map<String, Object>> dataList = new ArrayList<>();

		if (knowledgeList != null && knowledgeList.size() > 0) {
			for (Knowledge knowledge : knowledgeList) {

				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge, split);

				Map<String, Object> hasChildCount = new HashMap<String, Object>();
				hasChildCount.put("knowledge.id", knowledge.getId());
				boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;
				data.put("haschild", hasChild);
				Map<String, Object> search = new HashMap<>();
				search.put("item.knowledge.scode", knowledge.getScode());
				int totalAns =this.getUserTestItemService().getUserTestItemCount(search);
				float rate=0;
				if(totalAns!=0){
					search.put("result", "true");
					int trueAns = this.getUserTestItemService().getUserTestItemCount(search);
					rate=(float)trueAns/(float)totalAns*100;
				}
				data.put("rate",df.format(rate)+"%");
				if (hasChild) {
					LoadChildKnowledge(knowledge, data, split);
				}
				dataList.add(data);

			}
		}

		result.init(SUCCESS_STATUS, "数据获成功", dataList);
		Utlity.ResponseWrite(result, dataType, response);

	}
	public void LoadChildKnowledge(Knowledge knowledge, Map<String, Object> map, String split) {

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("knowledge.id", knowledge.getId());

		DecimalFormat df = new DecimalFormat();
		String style = "0.0";
		df.applyPattern(style);
		
		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, "", 0, recordCount);
		List<Map<String, Object>> dataList = new ArrayList<>();

		for (Knowledge knowle : knowledgeList) {
			Map<String, Object> data = SerializeEntity.knowledge2Map(knowle, split);
			Map<String, Object> hasChildCount = new HashMap<String, Object>();
			hasChildCount.put("knowledge.id", knowle.getId());
			boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;
			data.put("haschild", hasChild);
			Map<String, Object> search = new HashMap<>();
			search.put("item.knowledge.scode", knowle.getScode());
			int totalAns =this.getUserTestItemService().getUserTestItemCount(search);
			float rate=0;
			if(totalAns!=0){
				search.put("result", "true");
				int trueAns = this.getUserTestItemService().getUserTestItemCount(search);
				rate=(float)trueAns/(float)totalAns*100;
			}
			data.put("rate",df.format(rate)+"%");
			if (hasChild) {
				LoadChildKnowledge(knowle, data, split);
			}
			dataList.add(data);

		}
		map.put("data", dataList);

	}
}
