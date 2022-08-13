/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.action.admin
 * SubjectItemTypeAction
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IItemTypeService;
import cn.zeppin.service.api.ISubjectItemTypeService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.utility.Utlity;

/**
 * @author Clark 下午4:02:40
 */
public class SubjectItemTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8974816209865224021L;

	private IItemTypeService itemTypeService;
	private ISubjectItemTypeService subjectItemTypeService;
	private ISubjectService subjectService;

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

	public ISubjectItemTypeService getSubjectItemTypeService() {
		return subjectItemTypeService;
	}

	public void setSubjectItemTypeService(ISubjectItemTypeService subjectItemTypeService) {
		this.subjectItemTypeService = subjectItemTypeService;
	}

	/**
	 * 学科下的题型管理
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "itemtypes", type = ValueType.NUMBER)
	public void Update() {
		ActionResult result = new ActionResult();
		Integer subjectID = this.getIntValue(request.getParameter("subject.id"));
		String[] itemtypes = request.getParameterValues("itemtypes");
		Subject subject = this.getSubjectService().getSubjectById(subjectID);
		if (subject != null) {
			List<ItemType> itemTypeList = new ArrayList<>();
			if (itemtypes != null) {
				for (String itemtypeid : itemtypes) {
					ItemType itemType = this.getItemTypeService().getItemTypeById(Integer.valueOf(itemtypeid));
					if (itemType != null) {
						itemTypeList.add(itemType);
					}
				}
			}
			List<SubjectItemType> subjectItemTypes = this.getSubjectItemTypeService().updateSubjectItemType(subject, itemTypeList);
			List<Map<String, Object>> dataList = new ArrayList<>();
			if (subjectItemTypes != null && subjectItemTypes.size() > 0) {
				for (SubjectItemType subjectItemType : subjectItemTypes) {
					Map<String, Object> data = SerializeEntity.subjectItemType2Map(subjectItemType);
					dataList.add(data);
				}
			}
			result.init(SUCCESS_STATUS, "更新学科题型成功！", dataList);
		} else {
			result.init(FAIL_STATUS, "学科ID信息不正确!", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 按学科搜索题型，按题型搜索学科
	 * 
	 * @author Clark
	 * @date 2014年7月30日下午5:14:52
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Search() {
		ActionResult result = new ActionResult();
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("subject.id", request.getParameter("subject.id"));
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		List<SubjectItemType> subjectItemTypes = this.getSubjectItemTypeService().searchSubjectItemType(searchMap);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (subjectItemTypes != null && subjectItemTypes.size() > 0) {
			for (SubjectItemType subjectItemType : subjectItemTypes) {
				Map<String, Object> data = SerializeEntity.subjectItemType2Map(subjectItemType, split);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
}
