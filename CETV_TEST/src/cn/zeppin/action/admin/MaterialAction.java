/** 
 * Project Name:CETV_TEST 
 * File Name:MaterialAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.util.*;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.IStandardAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Material;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IMaterialService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: MaterialAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月15日 下午6:19:08 <br/>
 * 
 * @author jiangfei
 * @version
 * @since JDK 1.7
 */
public class MaterialAction extends BaseAction implements IStandardAction {

	private static final long serialVersionUID = -8372655813996883923L;

	private ISubjectService subjectService;
	private IGradeService gradeService;
	private IMaterialService materialService;

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

	public IMaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(IMaterialService materialService) {
		this.materialService = materialService;
	}

	/**
	 * 添加 材料
	 */
	@Override
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "content", type = ValueType.STRING)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	public void Add() {

		// =========================================
		// 1.获取输入参数
		// 2.判断学科和学段是否存在
		// 3.将数据入库返回id
		// =========================================

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		int gradeId = this.getIntValue(this.request.getParameter("grade.id"));
		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
		String content = this.getStrValue(this.request.getParameter("content"));

		Material material = new Material();
		material.setContent(content);
		material.setContentBackup(content);

		Grade grade = this.getGradeService().getById(gradeId);
		Subject subject = this.getSubjectService().getSubjectById(subjectId);

		if (grade != null) {
			material.setGrade(grade);
		}

		if (subject != null) {
			material.setSubject(subject);
		}

		this.getMaterialService().addMaterial(material);

		Map<String, Object> data = SerializeEntity.material2Map(material);
		result.init(SUCCESS_STATUS, "添加成功", data);

		Utlity.ResponseWrite(result, dataType, response);

	}

	@Override
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "content", type = ValueType.STRING)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	public void Update() {

		// ==============================================
		// 1.
		// ==============================================

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		int id = this.getIntValue(this.request.getParameter("id"));
		int gradeId = this.getIntValue(this.request.getParameter("grade.id"));
		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
		String content = this.getStrValue(this.request.getParameter("content"));

		Material material = this.getMaterialService().getMaterialById(id);

		if (material != null) {

			material.setContent(content);
			material.setContentBackup(content);

			Grade grade = this.getGradeService().getById(gradeId);
			Subject subject = this.getSubjectService().getSubjectById(subjectId);

			if (grade != null) {
				material.setGrade(grade);
			}

			if (subject != null) {
				material.setSubject(subject);
			}

			this.getMaterialService().addMaterial(material);

			Map<String, Object> data = SerializeEntity.material2Map(material);

			result.init(SUCCESS_STATUS, "更新成功", data);

		} else {
			result.init(FAIL_STATUS, "不存在当前材料id", null);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	@Override
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		int id = this.getIntValue(this.request.getParameter("id"));

		Material material = this.getMaterialService().getMaterialById(id);

		if (material != null) {

			// 查看是否可以删除
			int refenceMaterialCount = this.getMaterialService().getRefenceMaterialCount(material);
			if (refenceMaterialCount > 0) {
				result.init(FAIL_STATUS, "当前材料存在试题，不能删除", null);
			} else {
				this.getMaterialService().deleteMaterial(material);
				result.init(SUCCESS_STATUS, "删除成功", null);
			}

		} else {
			result.init(FAIL_STATUS, "不存在当前材料id", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 获取list
	 */
	@Override
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "content", type = ValueType.STRING)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.name", type = ValueType.STRING)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.name", type = ValueType.STRING)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		String id = request.getParameter("id");
		String content = request.getParameter("content");
		String subjectId = request.getParameter("subject.id");
		String subjectName = request.getParameter("subject.name");
		String gradeId = request.getParameter("grade.id");
		String gradeName = request.getParameter("grade.name");

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		HashMap<String, Object> params = new HashMap<String, Object>();
		if (id != null) {
			params.put("id", id);
		}
		if (content != null) {
			params.put("content", content);
		}
		if (subjectId != null) {
			params.put("subject.id", subjectId);
		}
		if (subjectName != null) {
			params.put("subject.name", subjectName);
		}
		if (gradeId != null) {
			params.put("grade.id", gradeId);
		}
		if (gradeName != null) {
			params.put("grade.name", gradeName);
		}

		int records = this.getMaterialService().getMaterialCount(params);
		List<Material> liT = this.getMaterialService().getMaterials(params, sorts, offset, pagesize);
		List<Map<String, Object>> liM = new ArrayList<>();

		for (Material material : liT) {
			Map<String, Object> data = SerializeEntity.material2Map(material);
			liM.add(data);
		}

		result.init(SUCCESS_STATUS, null, liM);
		result.setTotalCount(records);

		Utlity.ResponseWrite(result, dataType, response);

	}

	@Override
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "content", type = ValueType.STRING)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.name", type = ValueType.STRING)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.name", type = ValueType.STRING)
	public void Search() {
		
		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		String id = request.getParameter("id");
		String content = request.getParameter("content");
		String subjectId = request.getParameter("subject.id");
		String subjectName = request.getParameter("subject.name");
		String gradeId = request.getParameter("grade.id");
		String gradeName = request.getParameter("grade.name");

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		HashMap<String, Object> params = new HashMap<String, Object>();
		if (id != null) {
			params.put("id", id);
		}
		if (content != null) {
			params.put("content", content);
		}
		if (subjectId != null) {
			params.put("subject.id", subjectId);
		}
		if (subjectName != null) {
			params.put("subject.name", subjectName);
		}
		if (gradeId != null) {
			params.put("grade.id", gradeId);
		}
		if (gradeName != null) {
			params.put("grade.name", gradeName);
		}

		int records = this.getMaterialService().getMaterialCount(params);
		List<Material> liT = this.getMaterialService().getMaterials(params, sorts, offset, pagesize);
		List<Map<String, Object>> liM = new ArrayList<>();

		for (Material material : liT) {
			Map<String, Object> data = SerializeEntity.material2Map(material);
			liM.add(data);
		}

		result.init(SUCCESS_STATUS, null, liM);
		result.setTotalCount(records);

		Utlity.ResponseWrite(result, dataType, response);

	}

}
