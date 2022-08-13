/** 
 * Project Name:ItemDatabase 
 * File Name:TestPaperSectionAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.util.HashMap;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.service.api.IItemTypeService;
import cn.zeppin.service.api.IPaperService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ITestPaperItemService;
import cn.zeppin.service.api.ITestPaperSectionService;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: TestPaperSectionAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年9月3日 下午3:26:13 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class TestPaperSectionAction extends BaseAction {

	private ITestPaperSectionService testPaperSectionService;
	private ISubjectService subjectService;
	private IItemTypeService itemTypeService;
	private IPaperService paperService;
	private ITestPaperItemService testPaperItemService;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8248188495215015032L;

	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "paper.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		String id = request.getParameter("id");
		String paperId = request.getParameter("paper.id");

		TestPaperSection paperSection = this.getTestPaperSectionService().getTestPaperSectionById(this.getIntValue(id, 0));
		if (paperSection == null) {
			result.init(FAIL_STATUS, "当前试卷目录不存在", null);
		} else {
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("paper.id", paperId);
			searchMap.put("testPaperSection.id", id);

			int count = this.getTestPaperItemService().searchTestPaperItemsCount(searchMap);
			if (count > 0) {
				result.init(FAIL_STATUS, "当前试卷目录下存在试题，删除不了", null);
			} else {
				this.getTestPaperSectionService().deleteTestPaperSection(paperSection);
				result.init(SUCCESS_STATUS, "删除成功", null);
			}
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	public ITestPaperSectionService getTestPaperSectionService() {
		return testPaperSectionService;
	}

	public void setTestPaperSectionService(ITestPaperSectionService testPaperSectionService) {
		this.testPaperSectionService = testPaperSectionService;
	}

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public IItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(IItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}

	public IPaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
	}

	public ITestPaperItemService getTestPaperItemService() {
		return testPaperItemService;
	}

	public void setTestPaperItemService(ITestPaperItemService testPaperItemService) {
		this.testPaperItemService = testPaperItemService;
	}

}
