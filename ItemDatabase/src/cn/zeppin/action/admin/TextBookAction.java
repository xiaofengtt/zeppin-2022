/**  
 * This class is used for 教材操作
 * @author suijing
 * @version  
 *       1.0, 2014年7月29日 上午11:49:57  
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.IStandardAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Publisher;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.Textbook;
import cn.zeppin.entity.TextbookCapter;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IPublisherService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ITextBookService;
import cn.zeppin.service.api.ITextbookCapterService;
import cn.zeppin.utility.DataTimeConvert;
import cn.zeppin.utility.Utlity;

/**
 * @author sj
 *
 */
public class TextBookAction extends BaseAction implements IStandardAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -559509175482230849L;
	private ITextBookService textbookService;
	private ITextbookCapterService textbookCapterService;
	private ISubjectService subjectService;
	private IPublisherService publisherService;
	private IGradeService gradeService;

	public ITextbookCapterService getTextbookCapterService() {
		return textbookCapterService;
	}

	public void setTextbookCapterService(ITextbookCapterService textbookCapterService) {
		this.textbookCapterService = textbookCapterService;
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

	public IPublisherService getPublisherService() {
		return publisherService;
	}

	public void setPublisherService(IPublisherService publisherService) {
		this.publisherService = publisherService;
	}
	
	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}

	@Override
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权查询")
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "publisher.id", type = ValueType.NUMBER)
	@ActionParam(key = "version", type = ValueType.STRING)
	@ActionParam(key = "id", type = ValueType.NUMBER)
	public void List() {
		ActionResult actionResult = new ActionResult();
		HashMap<String, Object> hashMap = getPageAndSortParas();// 获取分页及排序参数
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		String sorts = hashMap.get("sorts").toString();
		int offset = (Integer) hashMap.get("offset");
		int pageSize = (Integer) hashMap.get("pagesize");
		HashMap<String, Object> paras = getParas();
		List<Textbook> lstTextbooks = new ArrayList<Textbook>();
		lstTextbooks = textbookService.getAllByAdmin(offset, pageSize, sorts, paras);
		List<Map<String, Object>> lstData = new ArrayList<>();
		if (lstTextbooks != null && lstTextbooks.size() > 0) {
			for (Textbook textbook : lstTextbooks) {
				Map<String, Object> map = SerializeEntity.textBook2Map(textbook, split);

				HashMap<String, Object> childCount = new HashMap<String, Object>();
				childCount.put("textbook", textbook.getId());
				boolean hasChild = this.getTextbookCapterService().getCountByParas(childCount) > 0 ? true : false;
				map.put("haschild", hasChild);
				lstData.add(map);
			}
		}
		actionResult.init(SUCCESS_STATUS, "数据获成功", lstData);
		int totalCount = textbookService.getCountByParas(paras, true);
		actionResult.setTotalCount(totalCount);
		actionResult.setPageSize(pageSize);
		actionResult.setPageNum(offset);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	@Override
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "publisher.id", type = ValueType.NUMBER)
	@ActionParam(key = "version", type = ValueType.STRING)
	@ActionParam(key = "id", type = ValueType.NUMBER)
	public void Search() {
		ActionResult actionResult = new ActionResult();
		HashMap<String, Object> hashMap = getPageAndSortParas();// 获取分页及排序参数
		String sorts = hashMap.get("sorts").toString();
		int offset = (Integer) hashMap.get("offset");
		int pageSize = (Integer) hashMap.get("pagesize");
		HashMap<String, Object> paras = getParas();
		List<Textbook> lstTextbooks = new ArrayList<Textbook>();
		lstTextbooks = textbookService.getAllByUser(offset, pageSize, sorts, paras);
		List<Map<String, Object>> lstData = new ArrayList<>();
		if (lstTextbooks != null && lstTextbooks.size() > 0) {
			for (Textbook textbook : lstTextbooks) {
				Map<String, Object> map = SerializeEntity.textBook2Map(textbook);
				lstData.add(map);
			}
		}
		actionResult.init(SUCCESS_STATUS, "数据获成功", lstData);
		int totalCount = textbookService.getCountByParas(paras, false);
		actionResult.setTotalCount(totalCount);
		actionResult.setPageSize(pageSize);
		actionResult.setPageNum(offset);
		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	private HashMap<String, Object> getParas() {
		HashMap<String, Object> hMap = new HashMap<String, Object>();
		if (request.getParameterMap().containsKey("id")) {
			if (Utlity.isNumeric(request.getParameter("id"))) {
				hMap.put("id", Integer.parseInt(request.getParameter("id")));
			}
		}
		if (request.getParameterMap().containsKey("name")) {
			hMap.put("name", request.getParameter("name"));
		}
		if (request.getParameterMap().containsKey("grade.id")) {
			if (Utlity.isNumeric(request.getParameter("grade.id"))) {
				hMap.put("grade", Integer.parseInt(request.getParameter("grade.id")));
			}
		}
		if (request.getParameterMap().containsKey("subject.id")) {
			if (Utlity.isNumeric(request.getParameter("subject.id"))) {
				hMap.put("subject", Integer.parseInt(request.getParameter("subject.id")));
			}
		}
		if (request.getParameterMap().containsKey("publisher.id")) {
			hMap.put("publisher", request.getParameter("publisher.id"));
		}
		if (request.getParameterMap().containsKey("version")) {
			hMap.put("version", request.getParameter("version"));
		}
		return hMap;
	}

	@Override
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权添加教材")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "publisher.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "version", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Add() {
		Textbook textbook = null;
		ActionResult actionResult = new ActionResult();
		String name = request.getParameter("name");
		if (textbookService.isExistByName(name, null)) {

			actionResult.init(FAIL_STATUS, "名称已经存在，不能重复添加", null);

		} else {
			textbook = getFromFront(actionResult);
			if (textbook != null) {
				SysUser currentUser = (SysUser) session.getAttribute("usersession");
				textbook.setCreatetime(DataTimeConvert.getCurrentTime(""));
				textbook.setSysUser(currentUser);
				textbook = textbookService.add(textbook);
				HashMap<String, Object> data = (HashMap<String, Object>) SerializeEntity.textBook2Map(textbook);
				actionResult.init(SUCCESS, "添加教材成功", data);
			}

		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	@Override
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权编辑教材")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "publisher.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "version", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update() {
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		Textbook oldTextbook = textbookService.getById(id);
		if (oldTextbook == null) {
			actionResult.init(FAIL_STATUS, "教材不存在", null);
		} else {
			String name = request.getParameter("name");
			if (textbookService.isExistByName(name, id)) {

				actionResult.init(FAIL_STATUS, "名称已经存在，不能重复添加", null);

			} else {
				Textbook textbook = null;

				textbook = getFromFront(actionResult);
				if (textbook != null) {
					oldTextbook.setName(textbook.getName());
					oldTextbook.setGrade(textbook.getGrade());
					oldTextbook.setPublisher(textbook.getPublisher());
					oldTextbook.setVersion(textbook.getVersion());
					oldTextbook.setSubject(textbook.getSubject());
					short status = Short.parseShort(request.getParameter("status"));
					oldTextbook.setStatus(status);
					textbookService.update(oldTextbook);
					HashMap<String, Object> data = (HashMap<String, Object>) SerializeEntity.textBook2Map(oldTextbook);
					actionResult.init(SUCCESS, "编辑教材成功", data);
				}
			}

		}

		Utlity.ResponseWrite(actionResult, dataType, response);
	}

	@Override
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT", errMsg = "无权删除教材")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		ActionResult actionResult = new ActionResult();
		int id = Integer.parseInt(request.getParameter("id"));
		Textbook oldTextbook = textbookService.getById(id);
		if (oldTextbook == null) {
			actionResult.init(FAIL_STATUS, "教材不存在", null);
		} else {
			textbookService.delete(oldTextbook);// 只做逻辑删除
			actionResult.init(SUCCESS, "删除教材成功", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);

	}

	/**
	 * @param actionResult
	 * @return
	 * @author suijing 2014年7月29日 下午2:11:38
	 */
	private Textbook getFromFront(ActionResult actionResult) {
		Textbook textbook = new Textbook();
		String name = request.getParameter("name");
		textbook.setName(name);
		int subjectId = Integer.parseInt(request.getParameter("subject.id"));
		Subject subject = subjectService.getSubjectById(subjectId);
		if (subject == null) {
			actionResult.init(FAIL_STATUS, "科目不存在", null);
			return null;
		}
		textbook.setSubject(subject);
		int gradeId = Integer.parseInt(request.getParameter("grade.id"));
		Grade grade = gradeService.getById(gradeId);
		if (grade == null) {
			actionResult.init(FAIL_STATUS, "学段不存在", null);
			return null;
		}

		textbook.setGrade(grade);
		String version = request.getParameter("version");
		textbook.setVersion(version);
		Integer pid = Integer.valueOf(request.getParameter("publisher.id"));
		Publisher publisher = this.publisherService.getById(pid);
		textbook.setPublisher(publisher);

		Short status = Short.valueOf(request.getParameter("status"));
		textbook.setStatus(status);

		return textbook;
	}

	/**
	 * 搜索
	 * 
	 * @author Administrator
	 * @date: 2014年9月4日 下午3:07:51 <br/>
	 */
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	public void SearchAllTextbook() {

		ActionResult result = new ActionResult();

		String split = request.getParameter("split");
		split = split == null ? "." : split;

		HashMap<String, Object> hMap = new HashMap<String, Object>();
		if (request.getParameterMap().containsKey("grade.id")) {
			if (Utlity.isNumeric(request.getParameter("grade.id"))) {

				int gid = Integer.parseInt(request.getParameter("grade.id"));
				Grade g = this.getGradeService().getById(gid);
				if (g != null) {
					hMap.put("grade.scode", g.getScode());
				}
			}
		}
		if (request.getParameterMap().containsKey("subject.id")) {
			if (Utlity.isNumeric(request.getParameter("subject.id"))) {
				hMap.put("subject", Integer.parseInt(request.getParameter("subject.id")));
			}
		}

		int totalCount = textbookService.getCountByParas(hMap, true);
		List<Textbook> lstTextbooks = textbookService.getAllByAdmin(0, totalCount, "", hMap);
		List<Map<String, Object>> lstData = new ArrayList<>();

		if (lstTextbooks != null && lstTextbooks.size() > 0) {
			for (Textbook textbook : lstTextbooks) {

				Map<String, Object> map = SerializeEntity.textBook2Map(textbook, split);

				HashMap<String, Object> childCount = new HashMap<String, Object>();
				childCount.put("textbook", textbook.getId());
				childCount.put("level", 1);

				int childIntCount = this.getTextbookCapterService().getCountByParas(childCount);
				boolean hasChild = childIntCount > 0 ? true : false;

				map.put("haschild", hasChild);
				List<Map<String, Object>> dataList = new ArrayList<>();

				if (hasChild) {
					List<TextbookCapter> lstTextbookCapters = textbookCapterService.getAllTextbookCapter(0, childIntCount, "", childCount);

					if (lstTextbookCapters != null && lstTextbookCapters.size() > 0) {
						for (TextbookCapter tTextbookCapter : lstTextbookCapters) {

							Map<String, Object> data = SerializeEntity.textbookCapter2Map(tTextbookCapter, split);
							boolean hasChildCapter = textbookCapterService.hasChild(tTextbookCapter.getId());
							data.put("haschild", Utlity.Boolean2Integer(hasChildCapter));

							if (hasChildCapter) {
								LoadAllNextCapter(tTextbookCapter, data, split);
							}

							dataList.add(data);
						}
						map.put("childs", dataList);
					}

				} else {
					map.put("childs", dataList);
				}

				lstData.add(map);
			}
		}

		result.init(SUCCESS_STATUS, "数据获成功", lstData);
		Utlity.ResponseWrite(result, dataType, response);

	}

	private void LoadAllNextCapter(TextbookCapter textbookcapter, Map<String, Object> map, String split) {

		HashMap<String, Object> childCount = new HashMap<String, Object>();
		childCount.put("textbookCapter", textbookcapter.getId());

		int totalCount = textbookCapterService.getCountByParas(childCount);

		List<TextbookCapter> lstTextbookCapters = textbookCapterService.getAllTextbookCapter(0, totalCount, "", childCount);
		List<Map<String, Object>> dataList = new ArrayList<>();

		for (TextbookCapter tTextbookCapter : lstTextbookCapters) {

			Map<String, Object> data = SerializeEntity.textbookCapter2Map(tTextbookCapter, split);
			boolean hasChildCapter = textbookCapterService.hasChild(tTextbookCapter.getId());
			data.put("haschild", Utlity.Boolean2Integer(hasChildCapter));

			if (hasChildCapter) {
				LoadAllNextCapter(tTextbookCapter, data, split);
			}

			dataList.add(data);
		}

		map.put("data", dataList);

	}

}
