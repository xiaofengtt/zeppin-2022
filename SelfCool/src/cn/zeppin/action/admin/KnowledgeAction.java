/**
 * Project Name:Self_Cool File Name:GradeAction.java Package
 * Name:cn.zeppin.action.admin Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */

package cn.zeppin.action.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISsoUserTestItemService;
import cn.zeppin.service.api.ISsoUserTestService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * This class is used for 知识点操作
 * 
 * @author Clark
 * @version 1.0, 2014年7月21日 下午7:51:58
 */
public class KnowledgeAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7223737324274167354L;
	private IKnowledgeService knowledgeService;
	private IGradeService gradeService;
	private ISubjectService subjectService;
	private ISysUserService sysUserService;
	private IItemService itemService;
	private ISsoUserService ssoUserService;
	private ISsoUserTestItemService ssoUserTestItemService;
	private ISsoUserTestService ssoUserTestService;
	
	

	
	public ISsoUserTestService getSsoUserTestService() {
		return ssoUserTestService;
	}

	public void setSsoUserTestService(ISsoUserTestService ssoUserTestService) {
		this.ssoUserTestService = ssoUserTestService;
	}

	public IKnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}

	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public ISsoUserTestItemService getSsoUserTestItemService() {
		return ssoUserTestItemService;
	}

	public void setSsoUserTestItemService(ISsoUserTestItemService ssoUserTestItemService) {
		this.ssoUserTestItemService = ssoUserTestItemService;
	}

	@ActionParam(key = "grade.id", type = ActionParam.ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ActionParam.ValueType.NUMBER)
	public void SearchAllKnowledge() {
		ActionResult result = new ActionResult();
		String split = this.request.getParameter("split");
		split = split == null ? "." : split;

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("grade.id", this.request.getParameter("grade.id"));
		searchMap.put("subject.id", this.request.getParameter("subject.id"));
		searchMap.put("level", Integer.valueOf(1));
		searchMap.put("status", Integer.valueOf(1));//只能查询状态正常的知识点
		//查询出一级知识点的个数；
		int recordCount = getKnowledgeService().searchKnowledgeCount(searchMap);
		//查询出所有的一级知识点；
		List<Knowledge> knowledgeList = getKnowledgeService().searchKnowledge(searchMap,"", 0, recordCount);
		//最后要返回的数据；
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();

		if ((knowledgeList != null) && (knowledgeList.size() > 0)) {
			//遍历一级知识点；
			for (Knowledge knowledge : knowledgeList) {
				//本次一级知识点转化为map;
				Map<String,Object> data = SerializeEntity.knowledge2Map(knowledge, split);

				Map<String,Object> hasChildCount = new HashMap<String,Object>();
				hasChildCount.put("knowledge.id", knowledge.getId());
				hasChildCount.put("status", Integer.valueOf(1));//只能查询状态正常的知识点
				//处理本次一级知识点下是否还有子知识点；
				boolean hasChild = getKnowledgeService().searchKnowledgeCount(
						hasChildCount) > 0;
				data.put("haschild", Boolean.valueOf(hasChild));
				if (hasChild) {
					//如果有，则处理本次一级知识点下的子知识点；
					LoadNextKnowledge(knowledge, data, split);
				}
				dataList.add(data);
			}

		}

		result.init("success", "数据获成功", dataList);
		Utlity.ResponseWrite(result, this.dataType, this.response);
	}
	/**
	 * 
	 * @param knowledge:要处理该知识点下的子孙知识点;
	 * @param map:knowledge的map数据；
	 * @param split
	 */
	public void LoadNextKnowledge(Knowledge knowledge, Map<String, Object> map,
			String split) {
		Map<String, Object> searchMap = new HashMap<>();
		//父知识点的id;
		searchMap.put("knowledge.id", knowledge.getId());
		searchMap.put("status", Integer.valueOf(1));//只能查询状态正常的知识点
		
		int recordCount = getKnowledgeService().searchKnowledgeCount(searchMap);
		List<Knowledge> knowledgeList = getKnowledgeService().searchKnowledge(searchMap,
				"", 0, recordCount);
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();

		for (Knowledge knowle : knowledgeList) {
			Map<String,Object> data = SerializeEntity.knowledge2Map(knowle, split);

			Map<String,Object> hasChildCount = new HashMap<String,Object>();
			hasChildCount.put("knowledge.id", knowle.getId());
			hasChildCount.put("status", Integer.valueOf(1));//只能查询状态正常的知识点
			boolean hasChild = getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0;
			data.put("haschild", Boolean.valueOf(hasChild));

			if (hasChild) {
				LoadNextKnowledge(knowle, data, split);
			}
			dataList.add(data);
		}

		map.put("data", dataList);
	}
	
	
	/**
	 * 添加知识点信息
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午6:32:36 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	public void Add() {

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		Integer gradeID = 0; // Integer.valueOf(request.getParameter("grade.id"));
		Integer subjectID = Integer.valueOf(request.getParameter("subject.id"));
		Grade grade = this.getGradeService().getGradeById(gradeID);

		Subject subject = this.getSubjectService().getSubjectById(subjectID);
		Integer pid = this.getIntValue(request.getParameter("knowledge.id"), null);

		if (subject == null) {
			result.init(FAIL_STATUS, "无此学科！", null);
		} else if (!this.getSysUserService().isCanOpt(currentUser, grade)) {
			result.init(FAIL_STATUS, "无权添加学段：" + grade.getName() + "(id:" + grade.getId() + ")下的知识点！", null);
		} else if (!this.getSysUserService().isCanOpt(currentUser, subject)) {
			result.init(FAIL_STATUS, "无权添加学科：" + subject.getName() + "(id:" + subject.getId() + ")下的知识点！", null);
		} else {
			Knowledge knowledge = new Knowledge();
			knowledge.setName(name);
			knowledge.setGrade(grade);
			knowledge.setSubject(subject);
			knowledge.setStatus(Dictionary.KNOWLEDGE_STATUS_NOMAL);
			knowledge.setSysUser(currentUser);
			knowledge.setCreatetime(new Timestamp((new Date()).getTime()));
			Knowledge parent = null;
			if (pid != null) {
				parent = this.getKnowledgeService().getById(pid);
			}
			knowledge.setKnowledge(parent);
			knowledge.setLevel((parent == null) ? (short) 1 : (short) (parent.getLevel() + 1));
			knowledge = this.getKnowledgeService().addKnowledge(knowledge);
			Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);
			result.init(SUCCESS_STATUS, "添加知识点" + name + "成功！", data);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 修改知识点信息
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午3:50:12 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	public void Update() {
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		Integer knowledgeID = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		Integer gradeID = 0;// Integer.valueOf(request.getParameter("grade.id"));
		Integer subjectID = Integer.valueOf(request.getParameter("subject.id"));
		Grade grade = this.getGradeService().getGradeById(gradeID);

		Subject subject = this.getSubjectService().getSubjectById(subjectID);

		if (subject == null) {
			result.init(FAIL_STATUS, "无此学科！", null);
		} else if (!this.getSysUserService().isCanOpt(currentUser, grade)) {
			result.init(FAIL_STATUS, "无权添加学段：" + grade.getName() + "(id:" + grade.getId() + ")下的知识点！", null);
		} else if (!this.getSysUserService().isCanOpt(currentUser, subject)) {
			result.init(FAIL_STATUS, "无权添加学科：" + subject.getName() + "(id:" + subject.getId() + ")下的知识点！", null);
		} else {
			Knowledge knowledge = this.getKnowledgeService().getById(knowledgeID);
			if (knowledge != null) {
				knowledge.setName(name);
				knowledge.setGrade(grade);
				knowledge.setSubject(subject);
				if (status != null) {
					Short shStatus = Short.valueOf(status);
					knowledge.setStatus(shStatus);
				}
				knowledge = this.getKnowledgeService().updateKnowledge(knowledge);
				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);
				result.init(SUCCESS_STATUS, "编辑知识点" + name + "成功！", data);
			} else {
				result.init(FAIL_STATUS, "无此知识点！", null);
			}
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除知识点信息
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午3:50:29 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		ActionResult result = new ActionResult();
		Integer knowledgeID = Integer.valueOf(request.getParameter("id"));
		Knowledge knowledge = this.getKnowledgeService().getById(knowledgeID);
		if (knowledge != null) {
			knowledge = this.getKnowledgeService().deleteKnowledge(knowledge);
			Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);
			result.init(SUCCESS_STATUS, "删除知识点" + knowledge.getName() + "成功！", data);
		} else {
			result.init(FAIL_STATUS, "无此知识点！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 后台管理知识点列表
	 * 
	 * @author Clark
	 * @date: 2014年7月24日 下午12:39:21 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {
		ActionResult result = new ActionResult();
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("level", request.getParameter("level"));
		searchMap.put("knowledge.id", request.getParameter("knowledge.id"));
		searchMap.put("grade.id", request.getParameter("grade.id"));
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (knowledgeList != null && knowledgeList.size() > 0) {
			for (Knowledge knowledge : knowledgeList) {
				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);

				Map<String, Object> hasChildCount = new HashMap<String, Object>();
				hasChildCount.put("knowledge.id", knowledge.getId());
				boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;
				data.put("haschild", hasChild);

				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, null, dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 查询知识点信息(这个接口只提供状态为正常的知识点数据)
	 * 
	 * @author Clark
	 * @date: 2014年7月23日 下午3:50:43 <br/>
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "level", type = ValueType.NUMBER)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "sysUser.id", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void Search() {
		ActionResult result = new ActionResult();

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("level", request.getParameter("level"));
		searchMap.put("knowledge.id", request.getParameter("knowledge.id"));
		searchMap.put("grade.id", request.getParameter("grade.id"));
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("sysUser.id", request.getParameter("sysUser.id"));

		// 对外接口不需要判断用户认证，不返回逻辑删除（停用）的信息
		searchMap.put("status", Dictionary.KNOWLEDGE_STATUS_NOMAL);

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (knowledgeList != null && knowledgeList.size() > 0) {
			for (Knowledge knowledge : knowledgeList) {
				Map<String, Object> data = SerializeEntity.knowledge2Map(knowledge);
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
	 * 知识点导航
	 * 
	 * @author Administrator
	 * @date: 2014年8月27日 下午12:20:16 <br/>
	 */
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	public void LoadKnowledgeNav() {

		String id = request.getParameter("knowledge.id");
		int i_id = this.getIntValue(id, 0);
		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		Knowledge grade = this.getKnowledgeService().getById(i_id);
		if (grade == null) {
			result.init(FAIL_STATUS, null, null);
		} else {
			LinkedList<Knowledge> lnklist = new LinkedList<Knowledge>();
			lnklist.add(grade);

			while (grade.getKnowledge() != null) {
				grade = grade.getKnowledge();
				lnklist.add(grade);
			}
			List<Map<String, Object>> liM = new ArrayList<>();
			int i = lnklist.size() - 1;
			for (; i >= 0; i--) {
				Knowledge cate = lnklist.get(i);
				Map<String, Object> data = SerializeEntity.knowledge2Map(cate);
				liM.add(data);
			}
			result.init(SUCCESS_STATUS, null, liM);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 查询出所有知识点、知识点题总数、用户所有知识点的做过的题数，用户这个知识点的能力情况
	 * 此接口面向外部应用
	 * 
	 * @author Administrator 20150403 modify by rongjingfeng 进行了性能优化，一次取库
	 * @date: 2014年9月4日 下午2:22:05 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void TreeForUser() {

		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if (currentUser == null) {
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);

		}
		if (currentUser == null) {
			result.init(FAIL_STATUS, "无此用户！", null);
		} 
		else {
			this.session.setAttribute("user", currentUser);

			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("subjectId", Integer.parseInt(request.getParameter("subject.id")));
			searchMap.put("userId", Integer.parseInt(request.getParameter("user.id")));
			searchMap.put("status", Dictionary.KNOWLEDGE_STATUS_NOMAL);

			//dataList保存待会儿 排好层级结构的knowledge;
			List<Map<String, Object>> dataList = new ArrayList<>();
			//后台统计好数据的knowlege;
			List<Map<String,Object>> list=this.getKnowledgeService().selectUserRighCount(searchMap);
			//递归组成树形结构;
			if(list!=null && list.size()>0)
			{
				for(Map<String,Object> cur:list)
				{
					byte curLevel=(byte) cur.get("level");
					if(curLevel==1)
					{
						Map<String,Object> curTree=this.refactorTree(cur,list);
					    
						dataList.add(curTree);
					}
				}
			}
			result.init(SUCCESS_STATUS, "数据获成功", dataList);
		}
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 递归排序;
	 */
	protected Map<String,Object> refactorTree(Map<String,Object> cur, List<Map<String,Object>> curList)
	{
		Map<String,Object> result=new HashMap<String,Object>();
		//当前节点的信息;
		result.put("id", cur.get("id"));
		result.put("name", cur.get("name"));
		result.put("level", cur.get("level"));
		result.put("scode", cur.get("scode"));
		result.put("itemCount", cur.get("totalCount"));//总题数
		result.put("rightCount", cur.get("rightCount"));//最后一次做对的题数
		
		//孩子节点；
		List<Map<String,Object>> childList=new ArrayList<Map<String,Object>>();
		boolean hasChild=false;
		byte curLevel=(byte) cur.get("level");
		String curScode=(String) cur.get("scode");
		for(Map<String,Object> child:curList)
		{
			byte childLevel=(byte) child.get("level");
			String childScode=(String) child.get("scode");
			
			if(childLevel==curLevel+1 &&  childScode.startsWith(curScode))
			{
				hasChild=true;
				childList.add(this.refactorTree(child, curList));
			}
		}
		//如果有孩子，则加入孩子;
		result.put("hasChild", hasChild);
		if(hasChild)
		{
			result.put("data",childList);
		}
		return result;
	}
	
	
//	/**
//	 * 为某个用户按学科知识点智能进行出题（选择哪些题让用户做和他们的排序，是一个非常最复杂的算法逻辑，需要考虑的因素比较多，需要理解后进行修改） 
//	 * 设计思路：
//	 * 首先应该计算用户该知识点下做过的题的次数以及正确的次数，根据选择最少正确次数的题的原则，在集合中进行随机出题
//	 * ***暂时不考虑找出的试题不够数量的情况，暂时不考虑标准化组合题的情况（如阅读理解） ——rongjingfeng
//	 * Ver1.0只考虑单选题，因为都是单选题，所以也不需要太复杂的算法逻辑
//	 * @throws IOException 
//	 */
//	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	public void SelectItemsForUser() {
//		ActionResult result = new ActionResult();
//		String split = request.getParameter("split");
//		split = split == null ? "." : split;
//
//		SsoUser currentUser = (SsoUser) session.getAttribute("user");
//		if (currentUser == null) {
//			int uid = this.getIntValue(request.getParameter("user.id"));
//			currentUser = this.getSsoUserService().getById(uid);
//
//		}
//		if (currentUser == null) {
//			result.init(FAIL_STATUS, "无此用户！", null);
//		} 
//		else {
//			this.session.setAttribute("user", currentUser);
//			
//			Integer knowledgeId = this.getIntValue(request.getParameter("knowledge.id"));
//			Knowledge knowledge = this.getKnowledgeService().getById(knowledgeId);
//			
//			if (knowledge != null) {
//
//				Map<String, Object> searchMap = new HashMap<>();
//				searchMap.put("subject.id", request.getParameter("subject.id"));
//				searchMap.put("knowledge.scode", knowledge.getScode());
//				searchMap.put("user.id",  currentUser.getId());
//				searchMap.put("itemType.isStandard",  Dictionary.ITEM_ANSWER_TYPESTANDARD);
//				searchMap.put("status", Dictionary.KNOWLEDGE_STATUS_NOMAL);
//				
//				List<Item> selectItems = this.getItemService().selectItems(searchMap, Dictionary.DEFAULT_ITEM_NUMBER); 
//				List<Map<String, Object>> dataList = new ArrayList<>();
//				
//				if (selectItems != null && selectItems.size() > 0) {
//					for (Item item : selectItems) {
//						Map<String, Object> data = SerializeEntity.item2Map(item);
//						dataList.add(data);
//					}
//					result.init(SUCCESS_STATUS, "数据获成功", dataList);
//					result.setTotalCount(selectItems.size());
//				}
//				else {
//					result.init(FAIL_STATUS, "该学科知识点下不存在试题！", null);
//				}
//			}
//			else {
//				result.init(FAIL_STATUS, "知识点ID错误！", null);
//			}
//		}
//		Utlity.ResponseWrite(result, dataType, response);
//	}
//	
	
	
}
