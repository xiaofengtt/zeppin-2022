package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Information;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IInformationService;
import cn.zeppin.service.api.IResourceService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.UserSubject;
import cn.zeppin.service.api.IUserSubjectService;

public class InformationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5420919819680441057L;

	private IInformationService informationService;
	private IResourceService resourceService;
	private ISubjectService subjectService;
	private IUserSubjectService userSubjectService;

	
	/**
	 * 资讯提示count
	 */
	@ActionParam(key = "time", type = ValueType.STRING, nullable = false, emptyable = false)
	public void TipCount() {

		ActionResult result = new ActionResult();
		SsoUser ssoUser = (SsoUser) this.session.getAttribute("user");

		// =============================================================//
		// 1.首先查询出用户关联的考试科目
		// 2.根据考试科目查询资讯条数
		// =============================================================//

		Map<String, Object> userSubjectMap = new Hashtable<String, Object>();
		userSubjectMap.put("user.id", ssoUser.getId());
		userSubjectMap.put("status", Dictionary.NORMAL);

		List<UserSubject> userSubLists = this.getUserSubjectService().getUserSubjectsByMap(userSubjectMap, null, -1, -1);

		if (userSubLists != null && userSubLists.size() > 0) {

			String time = this.request.getParameter("time");

			StringBuilder subSb = new StringBuilder();

			for (UserSubject us : userSubLists) {
				subSb.append(us.getSubject().getId()).append(",");
			}

			subSb.delete(subSb.length() - 1, subSb.length());

			HashMap<String, Object> searchMap = new HashMap<>();
			// searchMap.put("status", Dictionary.INFORMATION_STATUS_RELEASE);
			searchMap.put("time", time);
			if (subSb.length() > 0) {
				searchMap.put("subject.id", subSb.toString());
			}

			int recordCount = this.getInformationService().getInformationCount(searchMap);

			result.init(SUCCESS_STATUS, null, recordCount);

		} else {
			result.init(SUCCESS_STATUS, "没有添加备考记录", 0);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}
	
	/**
	 * 
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "resource.id", type = ValueType.NUMBER)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "title", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "abstract", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "content", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {

		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
		int thumbPicId = this.getIntValue(this.request.getParameter("resource.id"));
		Short type = Short.valueOf(this.request.getParameter("type"));
		String title = this.request.getParameter("title");
		String abstract_ = this.request.getParameter("abstract");
		String content = this.request.getParameter("content");
		Short status = Short.valueOf(this.request.getParameter("status"));

		Subject subject = this.getSubjectService().getSubjectById(subjectId);
		Resource resThumb = this.getResourceService().getById(thumbPicId);

		Information information = new Information();
		information.setAbstract_(abstract_);
		information.setContent(content);
		information.setCreatetime(new Timestamp(System.currentTimeMillis()));
		information.setFavoritCount(0);
		information.setResource(resThumb);
		information.setRetweetCount(0);
		information.setStatus(status);
		information.setSubject(subject);
		information.setTitle(title);
		information.setType(type);

		this.getInformationService().addInformation(information);

		ActionResult result = new ActionResult();
		Map<String, Object> dataMap = SerializeEntity.information2Map(information);

		result.init(SUCCESS_STATUS, "添加成功", dataMap);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 更新
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "resource.id", type = ValueType.NUMBER)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "title", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "abstract", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "content", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update() {

		int id = this.getIntValue(this.request.getParameter("id"));
		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
		int thumbPicId = this.getIntValue(this.request.getParameter("resource.id"));
		Short type = Short.valueOf(this.request.getParameter("type"));
		String title = this.request.getParameter("title");
		String abstract_ = this.request.getParameter("abstract");
		String content = this.request.getParameter("content");
		Short status = Short.valueOf(this.request.getParameter("status"));

		ActionResult result = new ActionResult();

		Information information = this.getInformationService().getInformationById(id);
		if (information != null) {

			Subject subject = this.getSubjectService().getSubjectById(subjectId);
			Resource resThumb = this.getResourceService().getById(thumbPicId);

			information.setType(type);
			information.setTitle(title);
			information.setAbstract_(abstract_);

			information.setContent(content);

			if (resThumb != null) {
				information.setResource(resThumb);
			}

			if (subject != null) {
				information.setSubject(subject);
			}
			information.setStatus(status);

			this.getInformationService().updateInformation(information);

			Map<String, Object> dataMap = SerializeEntity.information2Map(information);

			result.init(SUCCESS_STATUS, "修改成功", dataMap);
		} else {
			result.init(ERROR_STATUS, "不存在当前资讯", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 修改资讯状态
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void ChangeStatus() {

		int id = this.getIntValue(this.request.getParameter("id"));
		Short status = Short.valueOf(this.request.getParameter("status"));

		ActionResult result = new ActionResult();

		Information information = this.getInformationService().getInformationById(id);
		if (information != null) {

			information.setStatus(status);

			this.getInformationService().updateInformation(information);

			result.init(SUCCESS_STATUS, "修改成功", null);

		} else {
			result.init(ERROR_STATUS, "不存在当前资讯", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 删除
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		int id = this.getIntValue(this.request.getParameter("id"));

		ActionResult result = new ActionResult();

		Information information = this.getInformationService().getInformationById(id);
		if (information != null) {

			this.getInformationService().deleteInformation(information);

			result.init(SUCCESS_STATUS, "停用成功", null);

		} else {
			result.init(ERROR_STATUS, "不存在当前资讯", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 加载
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {

		int id = this.getIntValue(this.request.getParameter("id"));
		String split = request.getParameter("split");

		ActionResult result = new ActionResult();

		Information information = this.getInformationService().getInformationById(id);
		if (information != null) {

			Map<String, Object> data = SerializeEntity.information2Map(information, split);
			result.init(SUCCESS_STATUS, "加载业务成功！", data);

		} else {
			result.init(ERROR_STATUS, "不存在当前资讯", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 获取
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "title", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void List() {
		ActionResult result = new ActionResult();

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("title", request.getParameter("title"));
		searchMap.put("status", request.getParameter("status"));

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.getInformationService().getInformationCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Information> infoLists = this.getInformationService().getInformations(searchMap, sorts, offset, pagesize);

		List<Map<String, Object>> dataList = new ArrayList<>();
		if (infoLists != null && infoLists.size() > 0) {

			for (Information info : infoLists) {
				Map<String, Object> data = SerializeEntity.information2Map(info);
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

	public IInformationService getInformationService() {
		return informationService;
	}

	public void setInformationService(IInformationService informationService) {
		this.informationService = informationService;
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	public IUserSubjectService getUserSubjectService() {
		return userSubjectService;
	}

	public void setUserSubjectService(IUserSubjectService userSubjectService) {
		this.userSubjectService = userSubjectService;
	}

}
