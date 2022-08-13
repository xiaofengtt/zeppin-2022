/** 
 * Project Name:CETV_TEST 
 * File Name:PaperAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import cn.zeppin.access.ItemEx;
import cn.zeppin.access.PaperEx;
import cn.zeppin.access.PaperSectionEx;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.TestPaperItem;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IAreaService;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IItemTypeService;
import cn.zeppin.service.api.IPaperService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ISysUserService;
import cn.zeppin.service.api.ITestPaperItemService;
import cn.zeppin.service.api.ITestPaperSectionService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.ItemToHtml;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;
import cn.zeppin.utility.ZXingQRCode;

/**
 * ClassName: PaperAction <br/>
 * date: 2014年9月2日 下午2:34:41 <br/>
 * 
 * @author
 * @version
 * @since JDK 1.7
 */
public class PaperAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -16384589736452867L;

	private IPaperService paperService;
	private IGradeService gradeService;
	private ISubjectService subjectService;
	private ISysUserService sysUserService;
	private IAreaService areaService;
	

	private IItemService itemService;
	
	private IItemTypeService itemTypeService;
	private ITestPaperSectionService testPaperSectionService;
	private ITestPaperItemService testPaperItemService;

	private PaperEx paper;

	/**
	 * 添加一个试卷
	 */
	@SuppressWarnings("unchecked")
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "paper", type = ValueType.STRING)
	public void Add() {
		// ******************************************************
		// 1.首先用户
		// 2.验证参数
		// ******************************************************

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");
		SysUser currentUser = (SysUser) session.getAttribute("usersession");

		String paperStr = request.getParameter("paper");
		Map<String, Object> itemMap = JSONUtils.json2map(paperStr);

		Short type = Short.valueOf(itemMap.get("type").toString());
		String name = itemMap.get("name").toString();
		String source = itemMap.get("source").toString();

		int answerTime = this.getIntValue(itemMap.get("answerTime").toString(), 0);
		Short totalScore = Short.valueOf(itemMap.get("totalScore").toString());

		String cover = itemMap.get("cover").toString();
		Integer subjectId = this.getIntValue(itemMap.get("subject.id").toString(), null);
		Integer areaId = this.getIntValue(itemMap.get("area.id").toString(), null);
		String year = itemMap.get("year").toString();

		Subject subject = null;
		Area area = null;
		if (subjectId != null) {
			subject = this.getSubjectService().getSubjectById(subjectId);
		}
		if (areaId != null) {
			area = this.getAreaService().getAreaById(areaId);
		}

		// 验证用户是否有权限添加
		if (subject != null && !this.getSysUserService().isCanOpt(currentUser, subject)) {
			result.init(FAIL_STATUS, "无权添加学科：" + subject.getName() + "(id:" + subject.getId() + ")下的知识点！", null);
		} else {

			Paper paper = new Paper();
			paper.setType(type);
			paper.setName(name);
			paper.setSource(source);
			paper.setStatus(Dictionary.PAPER_STATUS_DRAFT);
			paper.setAnswerTime(answerTime);
			paper.setTotalScore(totalScore);
			paper.setCover(cover);
			paper.setSubject(subject);
			paper.setArea(area);
			paper.setYear(year);
			paper.setSysUser(currentUser);
			paper.setCreatetime(new Timestamp((new Date()).getTime()));

			if (itemMap.get("data") != null) {

				List<Map<String, Object>> listMapData = (List<Map<String, Object>>) itemMap.get("data");
				List<TestPaperSection> lstSections = new LinkedList<TestPaperSection>();

				for (Map<String, Object> secMap : listMapData) {

					Short inx = Short.valueOf(secMap.get("inx").toString());
					String sectionName = secMap.get("name").toString();
					Integer itemTypeId = this.getIntValue(secMap.get("itemType.id").toString());
					ItemType itemType = this.getItemTypeService().getItemTypeById(itemTypeId);

					TestPaperSection testPaperSection = new TestPaperSection();
					testPaperSection.setInx(inx);
					testPaperSection.setName(sectionName);
					testPaperSection.setLevel((short) 1);
					testPaperSection.setItemType(itemType);

					lstSections.add(testPaperSection);

				}

				this.getPaperService().addPaper(paper, lstSections);

			} else {
				this.getPaperService().addPaper(paper, null);
			}

			result.init(SUCCESS_STATUS, null, SerializeEntity.paper2Map(paper));
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 编辑试卷
	 * 
	 * @author Administrator
	 * @date: 2014年9月2日 下午6:00:53 <br/>
	 */
	@SuppressWarnings("unchecked")
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "paper", type = ValueType.STRING)
	public void Update() {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");
		SysUser currentUser = (SysUser) session.getAttribute("usersession");

		String paperStr = request.getParameter("paper");
		Map<String, Object> itemMap = JSONUtils.json2map(paperStr);

		int id = this.getIntValue(itemMap.get("id").toString());
		Paper paper = this.getPaperService().getPaperById(id);

		if (paper == null) {
			result.init(FAIL_STATUS, "当前试卷不存在", null);
		} else {

			Short type = Short.valueOf(itemMap.get("type").toString());
			String name = itemMap.get("name").toString();
			String source = itemMap.get("source").toString();

			int answerTime = this.getIntValue(itemMap.get("answerTime").toString(), 0);
			Short totalScore = Short.valueOf(itemMap.get("totalScore").toString());

			String cover = itemMap.get("cover").toString();
			Integer subjectId = this.getIntValue(itemMap.get("subject.id").toString(), null);
			Integer areaId = this.getIntValue(itemMap.get("area.id").toString(), null);
			String year = itemMap.get("year").toString();

			Subject subject = null;
			Area area = null;
			if (subjectId != null) {
				subject = this.getSubjectService().getSubjectById(subjectId);
			}
			if (areaId != null) {
				area = this.getAreaService().getAreaById(areaId);
			}

			// 验证用户是否有权限添加
			if (subject != null && !this.getSysUserService().isCanOpt(currentUser, subject)) {
				result.init(FAIL_STATUS, "无权添加学科：" + subject.getName() + "(id:" + subject.getId() + ")下的知识点！", null);
			} else {
				paper.setType(type);
				paper.setName(name);
				paper.setSource(source);
				paper.setAnswerTime(answerTime);
				paper.setTotalScore(totalScore);
				paper.setCover(cover);
				paper.setSubject(subject);
				paper.setArea(area);
				paper.setYear(year);

				if (itemMap.get("data") != null) {

					List<Map<String, Object>> listMapData = (List<Map<String, Object>>) itemMap.get("data");
					List<TestPaperSection> lstSections = new LinkedList<TestPaperSection>();

					for (Map<String, Object> secMap : listMapData) {

						Integer sectionId = this.getIntValue(secMap.get("id").toString(), 0);

						Short inx = Short.valueOf(secMap.get("inx").toString());
						String sectionName = secMap.get("name").toString();
						Integer itemTypeId = this.getIntValue(secMap.get("itemType.id").toString());
						ItemType itemType = this.getItemTypeService().getItemTypeById(itemTypeId);

						TestPaperSection testPaperSection = this.getTestPaperSectionService().getTestPaperSectionById(sectionId);
						testPaperSection.setInx(inx);
						testPaperSection.setName(sectionName);
						testPaperSection.setItemType(itemType);

						lstSections.add(testPaperSection);

					}
					this.getPaperService().updatePaper(paper, lstSections);

				} else {
					this.getPaperService().updatePaper(paper, null);
				}

				result.init(SUCCESS_STATUS, null, SerializeEntity.paper2Map(paper));
			}

		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 修改試卷狀態
	 * 
	 * @author Administrator
	 * @date: 2014年9月2日 下午6:37:56 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void ChangeStatus() {
		// 只是修改试卷的状态
		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		int id = this.getIntValue(request.getParameter("id"));
		Short status = Short.valueOf(request.getParameter("status"));
		Paper paper = this.getPaperService().getPaperById(id);

		try {
			

		if (paper == null) {
			result.init(FAIL_STATUS, "当前试卷不存在", null);
		} else {
			paper.setStatus(status);
			this.getPaperService().updatePaper(paper, null);

			// 更新试卷下的所有题
//			List<Item> childItems = this.getItemService().getItemsByItem(id);
			List<Item> childItems = this.getItemService().getItemByPaper(id);
			System.out.println(childItems.isEmpty()+":"+childItems.size());
			for (Item item : childItems) {
				item.setStatus(status);
				this.getItemService().updateItem(item);
			}

			result.init(SUCCESS_STATUS, "操作成功", null);
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.init(ERROR_STATUS, "操作失敗", null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载试卷设置
	 * 
	 * @author Administrator
	 * @date: 2014年9月23日 下午5:52:50 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void LoadPaperSetting() {
		// 删除只是修改试卷的状态
		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		String split = request.getParameter("split");
		split = split == null ? "." : split;

		int id = this.getIntValue(request.getParameter("id"));
		Paper paper = this.getPaperService().getPaperById(id);

		if (paper == null) {
			result.init(FAIL_STATUS, "当前试卷不存在", null);
		} else {

			Map<String, Object> sectionsMap = new HashMap<String, Object>();
			sectionsMap.put("paper.id", id);
			List<TestPaperSection> lstSections = this.getTestPaperSectionService().searchTestPaperSections(sectionsMap, "inx", -1, -1);

			Map<String, Object> map = SerializeEntity.paper2Map(paper, split);

			List<Map<String, Object>> mapSections = new LinkedList<Map<String, Object>>();
			for (TestPaperSection tmpSection : lstSections) {
				mapSections.add(SerializeEntity.testPaperSection2Map(tmpSection, split));
			}

			map.put("data", mapSections);
			result.init(SUCCESS_STATUS, "加载成功", map);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载试卷所有信息（包括设置和试题）
	 * 
	 * @author Administrator
	 * @throws IOException
	 * @date: 2014年9月23日 下午5:53:19 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String LoadPaper() throws IOException {

		int id = this.getIntValue(request.getParameter("id"));
		Paper paper = this.getPaperService().getPaperById(id);

		if (paper == null) {
			return "nopaper";
		} else {

			PaperEx paperEx = new PaperEx();
			paperEx.setId(paper.getId());
			paperEx.setAnswerTime(paper.getAnswerTime());
			if (paper.getArea() != null) {
				paperEx.setAreaName(Utlity.getAreaName(paper.getArea()));
				paperEx.setAreaId(paper.getArea().getId());
			} else {
				paperEx.setAreaName("全部");
				paperEx.setAreaId(0);
			}
			paperEx.setCover(paper.getCover());

			paperEx.setName(paper.getName());
			paperEx.setSource(paper.getSource());
			paperEx.setSourceType(Utlity.getSourceType(paper.getType()));
			paperEx.setStatus(paper.getStatus());
			paperEx.setSubjectName(paper.getSubject().getName());
			paperEx.setSubjectId(paper.getSubject().getId());
			paperEx.setTotalScore(paper.getTotalScore());
			paperEx.setType(paper.getType());
			paperEx.setYear(paper.getYear());

			Map<String, Object> sectionsMap = new HashMap<String, Object>();
			sectionsMap.put("paper.id", id);

			List<TestPaperSection> lstSections = this.getTestPaperSectionService().searchTestPaperSections(sectionsMap, "inx", -1, -1);
			for (TestPaperSection tmpSection : lstSections) {

				PaperSectionEx paperSectionEx = new PaperSectionEx();
				paperSectionEx.setId(tmpSection.getId());
				paperSectionEx.setInx(tmpSection.getInx());
				paperSectionEx.setItemTypeId(tmpSection.getItemType().getId());
				paperSectionEx.setItemTypeName(tmpSection.getItemType().getName());
				paperSectionEx.setModeType(tmpSection.getItemType().getModelType());
				paperSectionEx.setName(tmpSection.getName());
				paperSectionEx.setNumString(Utlity.numInx2Str(tmpSection.getInx()));

				Map<String, Object> searchPaperItem = new HashMap<String, Object>();
				searchPaperItem.put("paper.id", id);
				searchPaperItem.put("testPaperSection.id", tmpSection.getId());

				List<TestPaperItem> lstPaperItem = this.getTestPaperItemService().searchTestPaperItems(searchPaperItem, "", -1, -1);

				if (lstPaperItem != null && lstPaperItem.size() > 0) {
					for (TestPaperItem tmpItem : lstPaperItem) {

						ItemEx item = new ItemEx();
						item.setInx(tmpItem.getInx());
						item.setItem(tmpItem.getItem().getId());
						item.setId(tmpItem.getId());
						item.setItemModelType(tmpItem.getItem().getModelType());
//						if (tmpSection.getItemType().getIsGroup()) {
//						if (tmpSection.getItemType().getModelType() == 4) {//根据题目的modeltype和isgroup来判断是否是组合题
						if (tmpItem.getItem().getIsGroup() && tmpItem.getItem().getModelType() == 4){
							// 组合题
							List<Item> childItemList = this.getItemService().getItemsByItem(item.getItem());
							StringBuilder sb = new StringBuilder();
							
							//处理组合题题干
							Map<String, Object> mapContent = JSONUtils.json2map(tmpItem.getItem().getContentBackup());
							
							sb.append("<div class=\"question-title\">").append(mapContent.get("stem")).append("</div>");
							int serNum = 1;
							for (Item itemchild : childItemList) {
								sb.append(ItemToHtml.itemGroup2Html(itemchild, serNum));
								serNum++;
							}

							item.setHtml(sb.toString());

						} else if(!tmpItem.getItem().getIsGroup()){
							// 不是组合题
							item.setHtml(ItemToHtml.item2Html(tmpItem.getItem()));
						}

						paperSectionEx.getLstItem().add(item);

					}
				}
				paperEx.getLstSections().add(paperSectionEx);

			}

			this.setPaper(paperEx);
			return "paper";
		}

	}
	
	/**
	 * 加载试卷所有信息（包括设置和试题）
	 * 
	 * @author Administrator
	 * @throws IOException
	 * @date: 2014年9月23日 下午5:53:19 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String AuditingPaper() throws IOException {

		int id = this.getIntValue(request.getParameter("id"));
		Paper paper = this.getPaperService().getPaperById(id);

		if (paper == null) {
			return "nopaper";
		} else {

			PaperEx paperEx = new PaperEx();
			paperEx.setId(paper.getId());
			paperEx.setAnswerTime(paper.getAnswerTime());
			if (paper.getArea() != null) {
				paperEx.setAreaName(Utlity.getAreaName(paper.getArea()));
				paperEx.setAreaId(paper.getArea().getId());
			} else {
				paperEx.setAreaName("全部");
				paperEx.setAreaId(0);
			}
			paperEx.setCover(paper.getCover());

			paperEx.setName(paper.getName());
			paperEx.setSource(paper.getSource());
			paperEx.setSourceType(Utlity.getSourceType(paper.getType()));
			paperEx.setStatus(paper.getStatus());
			paperEx.setSubjectName(paper.getSubject().getName());
			paperEx.setSubjectId(paper.getSubject().getId());
			paperEx.setTotalScore(paper.getTotalScore());
			paperEx.setType(paper.getType());
			paperEx.setYear(paper.getYear());

			Map<String, Object> sectionsMap = new HashMap<String, Object>();
			sectionsMap.put("paper.id", id);

			List<TestPaperSection> lstSections = this.getTestPaperSectionService().searchTestPaperSections(sectionsMap, "inx", -1, -1);
			for (TestPaperSection tmpSection : lstSections) {

				PaperSectionEx paperSectionEx = new PaperSectionEx();
				paperSectionEx.setId(tmpSection.getId());
				paperSectionEx.setInx(tmpSection.getInx());
				paperSectionEx.setItemTypeId(tmpSection.getItemType().getId());
				paperSectionEx.setItemTypeName(tmpSection.getItemType().getName());
				paperSectionEx.setModeType(tmpSection.getItemType().getModelType());
				paperSectionEx.setName(tmpSection.getName());
				paperSectionEx.setNumString(Utlity.numInx2Str(tmpSection.getInx()));

				Map<String, Object> searchPaperItem = new HashMap<String, Object>();
				searchPaperItem.put("paper.id", id);
				searchPaperItem.put("testPaperSection.id", tmpSection.getId());

				List<TestPaperItem> lstPaperItem = this.getTestPaperItemService().searchTestPaperItems(searchPaperItem, "", -1, -1);

				if (lstPaperItem != null && lstPaperItem.size() > 0) {
					for (TestPaperItem tmpItem : lstPaperItem) {

						ItemEx item = new ItemEx();
						item.setInx(tmpItem.getInx());
						item.setItem(tmpItem.getItem().getId());
						item.setId(tmpItem.getId());
						item.setItemModelType(tmpItem.getItem().getModelType());

//						if (tmpSection.getItemType().getIsGroup()) {
//						if (tmpSection.getItemType().getModelType() == 4) {
						if (tmpItem.getItem().getIsGroup() && tmpItem.getItem().getModelType() == 4){
							// 组合题
							List<Item> childItemList = this.getItemService().getItemsByItem(item.getItem());
							StringBuilder sb = new StringBuilder();
							
							//处理组合题题干
							Map<String, Object> mapContent = JSONUtils.json2map(tmpItem.getItem().getContentBackup());
							
							sb.append("<div class=\"question-title\">").append(mapContent.get("stem")).append("</div>");
							int serNum = 1;
							for (Item itemchild : childItemList) {
								sb.append(ItemToHtml.itemGroup2Html(itemchild, serNum));
								sb.append("<dd><b>知识点：</b>").append(Utlity.getKnowledgeNaviName(itemchild.getKnowledge())).append("</dd>");
								sb.append("<dd><b>分值：</b>").append(itemchild.getDefaultScore()).append("</dd>");
								sb.append("<dd><b>试题解析：</b>").append(itemchild.getAnalysis()).append("</dd>");
								serNum++;
							}
							
							item.setHtml(sb.toString());

						} else if(!tmpItem.getItem().getIsGroup()){
							// 不是组合题
							StringBuilder sb = new StringBuilder();
							sb.append(ItemToHtml.paperItem2Html(tmpItem.getItem()));
							sb.append("<dd><b>知识点：</b>").append(Utlity.getKnowledgeNaviName(tmpItem.getItem().getKnowledge())).append("</dd>");
							sb.append("<dd><b>分值：</b>").append(tmpItem.getItem().getDefaultScore()).append("</dd>");
							sb.append("<dd><b>试题解析：</b>").append(tmpItem.getItem().getAnalysis()).append("</dd>");
							item.setHtml(sb.toString());
						}

						paperSectionEx.getLstItem().add(item);

					}
				}
				paperEx.getLstSections().add(paperSectionEx);

			}

			this.setPaper(paperEx);
			return "auditing";
		}

	}

	/**
	 * 更新试卷，主要是更新试卷的编号
	 * 
	 * @author Administrator
	 * @date: 2014年10月10日 上午10:22:32 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "papers", type = ValueType.STRING)
	public void UpdatePaper() {
		// paperid&sectionid,itemid,itemid&sectionid,itemid,itemid
		// 试卷提交
		// 拆分
		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		String papers = request.getParameter("papers");
		String[] arrayPaper = papers.split("\\$\\$");
		int paperId = this.getIntValue(arrayPaper[0], 0);
		Paper paper = this.getPaperService().getPaperById(paperId);

		if (paper == null) {
			result.init(FAIL_STATUS, "当前试卷不存在", null);
		} else {

			// 先删除 所有原纪录
			Map<String, Object> deleteMap = new HashMap<String, Object>();
			deleteMap.put("paper.id", paperId);
			this.getTestPaperItemService().deleteTestPaperItems(deleteMap);

			// 删除结束后，处理试题
			int inx = 1;
			for (int i = 1; i < arrayPaper.length; i++) {

				String[] arraySection = arrayPaper[i].split(",");
				int sectionId = this.getIntValue(arraySection[0]);
				TestPaperSection paperSection = this.getTestPaperSectionService().getTestPaperSectionById(sectionId);

				// 处理item
				for (int m = 1; m < arraySection.length; m++) {
					int itemId = this.getIntValue(arraySection[m], 0);

					Item item = this.getItemService().getItemById(itemId);

					TestPaperItem tpi = new TestPaperItem();
					tpi.setInx((short) inx);
					tpi.setPaper(paper);
					tpi.setTestPaperSection(paperSection);
					tpi.setItem(item);

					this.getTestPaperItemService().addTestPaperItem(tpi);

					inx++;
				}

			}

			result.init(SUCCESS_STATUS, "保存成功", null);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 删除试卷里某一道试题
	 * 
	 * @author Administrator
	 * @date: 2014年10月10日 下午3:15:22 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void DeletePaperItem() {
		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		int id = this.getIntValue(request.getParameter("id"), 0);

		TestPaperItem paperItem = this.getTestPaperItemService().getTestPaperItemById(id);

		if (paperItem == null) {
			result.init(FAIL_STATUS, "当前id不存在", null);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);

			this.getTestPaperItemService().deleteTestPaperItems(map);
			result.init(SUCCESS_STATUS, "移除成功", null);
		}
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 管理列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月2日 下午6:34:51 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "type", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "source", type = ValueType.STRING)
	@ActionParam(key = "answerTime", type = ValueType.NUMBER)
	@ActionParam(key = "totalScore", type = ValueType.NUMBER)
	@ActionParam(key = "cover", type = ValueType.STRING)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "area.scode", type = ValueType.STRING)
	@ActionParam(key = "year", type = ValueType.STRING)
	public void List() {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("type", request.getParameter("type"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("source", request.getParameter("source"));
		searchMap.put("answerTime", request.getParameter("answerTime"));
		searchMap.put("totalScore", request.getParameter("totalScore"));
		searchMap.put("cover", request.getParameter("cover"));
		searchMap.put("subject.id", request.getParameter("subject.id"));

		searchMap.put("area.scode", request.getParameter("area.scode"));
		searchMap.put("year", request.getParameter("year"));
		searchMap.put("status", request.getParameter("status"));

		// 组织机构 只能查看机构下的试卷
		if (currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_MANAGER || currentUser.getRole().getId() == Dictionary.USER_ROLE_EX_EDITOR) {
			searchMap.put("sysUser.organization.id", currentUser.getOrganization().getId());
		}

		int recordCount = this.getPaperService().searchPaperCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Paper> paperList = this.getPaperService().searchPaper(searchMap, sorts, offset, pagesize);
//		System.out.println(paperList.isEmpty());
		List<Map<String, Object>> dataList = new ArrayList<>();

		if (paperList != null && paperList.size() > 0) {
			for (Paper paper : paperList) {
				System.out.println(paper.getSysUser());
				Map<String, Object> mapData = SerializeEntity.paper2Map(paper, split);

				Map<String, Object> searchCount = new HashMap<String, Object>();
				searchCount.put("paper.id", paper.getId());
				int count = this.getTestPaperItemService().searchTestPaperItemsCount(searchCount);
				mapData.put("itemCount", count);

				dataList.add(mapData);
			}
		}

		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 搜索列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月2日 下午6:35:04 <br/>
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "type", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "source", type = ValueType.STRING)
	@ActionParam(key = "answerTime", type = ValueType.NUMBER)
	@ActionParam(key = "totalScore", type = ValueType.NUMBER)
	@ActionParam(key = "cover", type = ValueType.STRING)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "area.scode", type = ValueType.STRING)
	@ActionParam(key = "year", type = ValueType.STRING)
	public void Search() {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("type", request.getParameter("type"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("source", request.getParameter("source"));
		searchMap.put("answerTime", request.getParameter("answerTime"));
		searchMap.put("totalScore", request.getParameter("totalScore"));
		searchMap.put("cover", request.getParameter("cover"));
		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("area.scode", request.getParameter("area.scode"));
		searchMap.put("year", request.getParameter("year"));
		searchMap.put("status", request.getParameter("status"));

		int recordCount = this.getPaperService().searchPaperCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Paper> paperList = this.getPaperService().searchPaper(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();

		if (paperList != null && paperList.size() > 0) {
			for (Paper paper : paperList) {
				dataList.add(SerializeEntity.paper2Map(paper, split));
			}
		}

		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);

		Utlity.ResponseWrite(result, dataType, response);
	}
	
	
	@ActionParam(key = "paperid", type = ValueType.NUMBER)
	@ActionParam(key = "sectionid", type = ValueType.NUMBER)
	@ActionParam(key = "itemid", type = ValueType.NUMBER)
	public void AddExistsItem() {
		Integer sectionId = Integer.valueOf(request.getParameter("sectionid"));
		Integer itemId = Integer.valueOf(request.getParameter("itemid"));
		ActionResult result = new ActionResult();
		
		Item item = this.getItemService().getItemById(itemId);
		if(item != null){
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("item.id", request.getParameter("itemid"));
			searchMap.put("paper.id", request.getParameter("paperid"));
			searchMap.put("section.id", request.getParameter("sectionid"));
			Integer count = this.getTestPaperItemService().searchTestPaperItemsCount(searchMap);
			Short inx = (short) (count + 1);
			
			TestPaperSection tps = this.getTestPaperSectionService().getTestPaperSectionById(sectionId);
			Paper paper = tps.getPaper();
			
			TestPaperItem api = new TestPaperItem();
			api.setInx(inx);
			api.setItem(item);
			api.setPaper(paper);
			api.setTestPaperSection(tps);
			this.getTestPaperItemService().addTestPaperItem(api);
			
			result.init(SUCCESS_STATUS, "添加完成！", null);
		}else{
			result.init(FAIL_STATUS, "添加失败！", null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 每套试卷生成二维码传递到前端页面显示
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void PaperForQRCode(){
		
		int id = this.getIntValue(request.getParameter("id"));
		
//		String url = "../admin/paperTest.html?id="+id;
//		String url = "http://192.168.1.69/SelfCool_TEST/admin/paperTest.html?id="+id;
		String url = "http://192.168.1.27/SelfCool_TEST/admin/paperTest.html?id="+id;
		
//		String2QRCode.encoderQRCoder(url, response);
		ZXingQRCode.encoderQRCoder(url, response);
	}
	
	/**
	 * 加载二维码请求地址对应试卷信息
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void LoadPaperForHtml(){
		int paperId = this.getIntValue(request.getParameter("id"));
		ActionResult result = new ActionResult();
		
		Map<String, Object> searchPaperItem = new HashMap<String, Object>();
		searchPaperItem.put("paper.id", paperId);
		List<TestPaperItem> lstPaperItem = this.getTestPaperItemService().searchTestPaperItems(searchPaperItem, "", -1, -1);
		List<Map<String, Object>> lstItem = new ArrayList<Map<String,Object>>();
		
		String split = "_";
		if(!lstPaperItem.isEmpty()){
			for(TestPaperItem tpi:lstPaperItem){
				Map<String, Object> data = SerializeEntity.item2Map(tpi.getItem(), split);
				
				if (tpi.getItem().getModelType() == 4) {//修改判定条件为item.getModelType == 4
					List<Item> childItemList = this.getItemService().getItemsByItem(tpi.getItem().getId());
					if (childItemList != null && childItemList.size() > 0) {
						List<Map<String, Object>> childdataList = new ArrayList<>();
						int index = 1;
						for (Item itemChild : childItemList) {
							Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild, split);
							dataChild.put("stemindex", index);

							childdataList.add(dataChild);
							index++;
						}
						data.put("data", childdataList);
					}
				}
				lstItem.add(data);
			}
			result.init(SUCCESS_STATUS, "搜索完成！", lstItem);
			result.setTotalCount(lstPaperItem.size());

			Utlity.ResponseWrite(result, dataType, response);
		}else{
			result.init(ERROR_STATUS, "暂无数据", lstItem);
			result.setTotalCount(lstPaperItem.size());
			Utlity.ResponseWrite(result, dataType, response);
		}
		

	}
	
	public IPaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
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

	public IAreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	public ITestPaperSectionService getTestPaperSectionService() {
		return testPaperSectionService;
	}

	public void setTestPaperSectionService(ITestPaperSectionService testPaperSectionService) {
		this.testPaperSectionService = testPaperSectionService;
	}

	public IItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(IItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}

	public ITestPaperItemService getTestPaperItemService() {
		return testPaperItemService;
	}

	public void setTestPaperItemService(ITestPaperItemService testPaperItemService) {
		this.testPaperItemService = testPaperItemService;
	}

	public PaperEx getPaper() {
		return paper;
	}

	public void setPaper(PaperEx paper) {
		this.paper = paper;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

}
