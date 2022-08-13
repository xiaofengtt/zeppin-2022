/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.action.admin
 * ItemAction
 */
package cn.zeppin.action.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlparser.util.ParserException;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemAnswer;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.TestPaperItem;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.entity.TextbookCapter;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IItemTypeService;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.IMaterialService;
import cn.zeppin.service.api.IPaperService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ITestPaperItemService;
import cn.zeppin.service.api.ITestPaperSectionService;
import cn.zeppin.service.api.ITextbookCapterService;
import cn.zeppin.service.api.IUserTestItemService;
import cn.zeppin.utility.DataTimeConvert;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.HtmlHelper;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

/**
 * @author Clark 下午12:04:56
 */
public class ItemAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6684872250053826721L;

	private IItemService itemService;
	private IItemTypeService itemTypeService;
	private IGradeService gradeService;
	private ISubjectService subjectService;
	private IKnowledgeService knowledgeService;
	private ITextbookCapterService textbookCapterService;
	private IMaterialService materialService;

	private IUserTestItemService userTestItemService;
	private IPaperService paperService;
	private ITestPaperSectionService testPaperSectionService;
	private ITestPaperItemService testPaperItemService;

	/**
	 * 添加一道试题
	 * 
	 * @author jiangfei
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws ParserException
	 * @date: 2014年8月18日 下午8:30:20 <br/>
	 */
	@SuppressWarnings({ "unchecked" })
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "questionItem", type = ValueType.STRING, nullable = false, emptyable = false)
	public void AddJson() throws JsonProcessingException, IOException, ParserException {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");
		SysUser currentUser = (SysUser) session.getAttribute("usersession");

		// 获取数据
		String questionItem = request.getParameter("questionItem");
		Map<String, Object> itemMap = JSONUtils.json2map(questionItem);

		// 试题类型
		Integer itemTypeID = this.getIntValue(itemMap.get("itemType.id").toString(), null);

		// 来源
		Short sourceType = Short.valueOf(itemMap.get("sourceType").toString());
		String source = itemMap.get("source").toString();
		Short diffcultyLevel = Short.valueOf(itemMap.get("diffcultyLevel").toString());

		// 学段，学科，章节,知识点
		Integer gradeID = this.getIntValue(itemMap.get("grade.id").toString(), null);
		Integer subjectID = this.getIntValue(itemMap.get("subject.id").toString(), null);
		Integer textbookCapterID = this.getIntValue(itemMap.get("textbookCapter.id").toString(), null);
		Integer knowledgeID = this.getIntValue(itemMap.get("knowledge.id").toString(), null);

		Integer paperId = this.getIntValue(itemMap.get("paper.id"), null);
		Integer paperSectionId = this.getIntValue(itemMap.get("paperSection.id"), null);

		// 学段，学科，章节,知识点
		Grade grade = null;
		Subject subject = null;
		TextbookCapter textbookCapter = null;
		Knowledge knowledge = null;

		if (gradeID != null) {
			grade = this.getGradeService().getById(gradeID);
		}
		if (subjectID != null) {
			subject = this.getSubjectService().getSubjectById(subjectID);
		}
		if (textbookCapterID != null) {
			textbookCapter = this.getTextbookCapterService().getById(textbookCapterID);
		}
		if (knowledgeID != null) {
			knowledge = this.getKnowledgeService().getById(knowledgeID);
		}

		ItemType itemType = this.getItemTypeService().getItemTypeById(itemTypeID);
		Object data = itemMap.get("data");

		Item item = new Item();
		item.setSource(source);
		item.setSourceType(sourceType);
		item.setDiffcultyLevel(diffcultyLevel);
		item.setStatus(Dictionary.ITEM_STATUS_DRAFT);
		item.setLevel(1);

		item.setGrade(grade);
		item.setSubject(subject);
		item.setTextbookCapter(textbookCapter);
		item.setKnowledge(knowledge);

		item.setSysUser(currentUser);
		item.setCreatetime(new Timestamp((new Date()).getTime()));

		// 判断是否是组合题
		if (!itemType.getIsGroup()) {
			// 不是组合题 ===> 每次都是一道题一道题添加
			Map<String, Object> mapData = (Map<String, Object>) data;
			List<ItemAnswer> listItemAns = new ArrayList<ItemAnswer>();
			map2Item(mapData, item, listItemAns);

			// 入库
			this.getItemService().addItem(item, listItemAns);

		} else {
			// 组合题,多道题
			String material = this.getStrValue(itemMap.get("material").toString(), null);

			// 先将父试题入库，然后在处理
			item.setItemType(itemType);
			item.setIsGroup(itemType.getIsGroup());
			item.setContentBackup(material);
			item.setContent(HtmlHelper.parseString2Html(material));

			// 入库
			this.getItemService().addItem(item, null);

			List<Map<String, Object>> listMapData = (List<Map<String, Object>>) data;

			for (Map<String, Object> mapData : listMapData) {

				List<ItemAnswer> listItemAns = new ArrayList<ItemAnswer>();
				Item itemChild = new Item();
				itemChild.setParent(item);
				itemChild.setLevel(2);

				// 数据库必填项
				itemChild.setDiffcultyLevel(diffcultyLevel);
				itemChild.setSourceType(sourceType);
				itemChild.setStatus(Dictionary.ITEM_STATUS_DRAFT);
				
				itemChild.setGrade(grade);
				itemChild.setSubject(subject);
				itemChild.setTextbookCapter(textbookCapter);
				itemChild.setKnowledge(knowledge);

				itemChild.setSysUser(currentUser);
				itemChild.setCreatetime(new Timestamp((new Date()).getTime()));

				map2Item(mapData, itemChild, listItemAns);

				// 入库
				this.getItemService().addItem(itemChild, listItemAns);
			}
		}

		// 处理试卷录入
		if (paperId != null && paperSectionId != null) {
			Paper paper = this.getPaperService().getPaperById(paperId);
			TestPaperSection paperSection = this.getTestPaperSectionService().getTestPaperSectionById(paperSectionId);

			Map<String, Object> searchCount = new HashMap<String, Object>();
			searchCount.put("paper.id", paperId);
			// searchCount.put("testPaperSection.id", paperSectionId);

			int count = this.getTestPaperItemService().searchTestPaperItemsCount(searchCount) + 1;

			TestPaperItem tpi = new TestPaperItem();
			tpi.setInx((short) count);
			tpi.setPaper(paper);
			tpi.setTestPaperSection(paperSection);
			tpi.setItem(item);

			this.getTestPaperItemService().addTestPaperItem(tpi);

		}

		result.init(SUCCESS_STATUS, "试题添加成功", null);
		Utlity.ResponseWrite(result, dataType, response);

	}

	@SuppressWarnings({ "unchecked" })
	private void map2Item(Map<String, Object> mapData, Item item, List<ItemAnswer> listAnswer) throws JsonProcessingException, IOException, ParserException {

		Integer itemTypeID = this.getIntValue(mapData.get("itemType.id").toString(), null);
		ItemType itemType = this.getItemTypeService().getItemTypeById(itemTypeID);

		item.setItemType(itemType);
		item.setIsGroup(itemType.getIsGroup());

		// 处理Stem、Options、Results。
		Map<String, Object> mapContent = new HashMap<String, Object>();
		mapContent.put("stem", mapData.get("stem"));
		
		mapContent.put("options", mapData.get("options"));
		mapContent.put("results", mapData.get("results"));

		StringBuilder sb = new StringBuilder();
		sb.append(HtmlHelper.parseString2Html(mapData.get("stem").toString()));
		sb.append(HtmlHelper.parseObject2Html(mapData.get("options")));

		// itemContent还需要处理、过滤、转XML
		String contentXml = JSONUtils.obj2json(mapContent);
		item.setContentBackup(contentXml);
		item.setContent(sb.toString().replaceAll("&nbsp;", " "));

		// 处理答案结果
		List<Map<String, Object>> listResults = (List<Map<String, Object>>) mapData.get("results");

		for (int i = 0; i < listResults.size(); i++) {
			Map<String, Object> resultMap = listResults.get(i);

			ItemAnswer ianswer = new ItemAnswer();

			Short mce = Short.valueOf(resultMap.get("mce").toString());
			ianswer.setInx(mce);

			ianswer.setType(Dictionary.ITEM_ANSWER_TYPESTANDARD);
			ianswer.setReference(resultMap.get("inx").toString());// 选项
			ianswer.setContent(resultMap.get("content").toString());// 答案
			
			ianswer.setScore(this.getIntValue(resultMap.get("score").toString(), 0));
			listAnswer.add(ianswer);
		}

	}

	/**
	 * 编辑修改试题
	 * 
	 * @author Administrator
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws ParserException
	 * @date: 2014年8月20日 上午10:55:09 <br/>
	 */
	@SuppressWarnings("unchecked")
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "questionItem", type = ValueType.STRING, nullable = false, emptyable = false)
	public void UpdateJson() throws JsonProcessingException, IOException, ParserException {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		String questionItem = request.getParameter("questionItem");
		Map<String, Object> itemMap = JSONUtils.json2map(questionItem);

		Integer itemId = this.getIntValue(itemMap.get("id").toString(), 0);

		Item item = this.getItemService().getItemById(itemId);
		if (item == null) {
			result.init(FAIL_STATUS, "当前试题不存在", null);
		} else {

			if (item.getStatus() != Dictionary.ITEM_STATUS_DRAFT && item.getStatus() != Dictionary.ITEM_STATUS_STOP) {
				result.init(FAIL_STATUS, "当前试题状态不可编辑", null);
			} else {

				// 来源 难易程度
				Short sourceType = Short.valueOf(itemMap.get("sourceType").toString());
				String source = itemMap.get("source").toString();
				Short diffcultyLevel = Short.valueOf(itemMap.get("diffcultyLevel").toString());

				// 学段，学科，章节,知识点
				Integer gradeID = this.getIntValue(itemMap.get("grade.id").toString(), null);
				Integer subjectID = this.getIntValue(itemMap.get("subject.id").toString(), null);
				Integer textbookCapterID = this.getIntValue(itemMap.get("textbookCapter.id").toString(), null);
				Integer knowledgeID = this.getIntValue(itemMap.get("knowledge.id").toString(), null);

				Integer paperId = this.getIntValue(itemMap.get("paper.id"), null);
				Integer paperSectionId = this.getIntValue(itemMap.get("paperSection.id"), null);

				// 学段，学科，章节,知识点
				Grade grade = null;
				Subject subject = null;
				TextbookCapter textbookCapter = null;
				Knowledge knowledge = null;

				if (gradeID != null) {
					grade = this.getGradeService().getById(gradeID);
				}
				if (subjectID != null) {
					subject = this.getSubjectService().getSubjectById(subjectID);
				}
				if (textbookCapterID != null) {
					textbookCapter = this.getTextbookCapterService().getById(textbookCapterID);
				}
				if (knowledgeID != null) {
					knowledge = this.getKnowledgeService().getById(knowledgeID);
				}

				item.setSource(source);
				item.setSourceType(sourceType);
				item.setDiffcultyLevel(diffcultyLevel);

				item.setGrade(grade);
				item.setSubject(subject);
				item.setTextbookCapter(textbookCapter);
				item.setKnowledge(knowledge);

				Object data = itemMap.get("data");

				if (!item.getIsGroup()) {

					Map<String, Object> mapData = (Map<String, Object>) data;
					List<ItemAnswer> listItemAns = new ArrayList<ItemAnswer>();

					map2Item(mapData, item, listItemAns);

					this.getItemService().updateItem(item, listItemAns);

				} else {
					// 组合题,多道题
					String material = this.getStrValue(itemMap.get("material").toString(), null);

					// 先将父试题更新，然后在处理
					if (material != null) {
						item.setContentBackup(material);
						item.setContent(HtmlHelper.parseString2Html(material));
					}
					this.getItemService().updateItem(item, null);

					List<Map<String, Object>> listMapData = (List<Map<String, Object>>) data;

					for (Map<String, Object> mapData : listMapData) {

						List<ItemAnswer> listItemAns = new ArrayList<ItemAnswer>();
						Item itemChild = new Item();
						itemChild.setParent(item);
						itemChild.setLevel(2);

						// 数据库必填项
						itemChild.setDiffcultyLevel(diffcultyLevel);
						itemChild.setSourceType(sourceType);
						itemChild.setStatus(Dictionary.ITEM_STATUS_DRAFT);

						itemChild.setSysUser(item.getSysUser());
						itemChild.setCreatetime(new Timestamp((new Date()).getTime()));

						map2Item(mapData, itemChild, listItemAns);

						// 入库
						this.getItemService().addItem(itemChild, listItemAns);
					}

				}

				result.init(SUCCESS_STATUS, "试题编辑成功", null);

				// 处理试卷录入
				if (paperId != null && paperSectionId != null) {

					Map<String, Object> deleteMap = new HashMap<String, Object>();
					deleteMap.put("paper.id", paperId);
					deleteMap.put("testPaperSection.id", paperSectionId);
					deleteMap.put("item.id", itemId);

					List<TestPaperItem> lstItem = this.getTestPaperItemService().searchTestPaperItems(deleteMap, null, -1, -1);
					if (lstItem != null && lstItem.size() > 0) {
						TestPaperItem tpi = lstItem.get(0);
						this.getTestPaperItemService().updateTestPaperItem(tpi);
					}

				}

			}

		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 改变试题的状态
	 * 
	 * @author Administrator
	 * @date: 2014年8月20日 下午12:53:22 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void ChangeStatus() {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		int itemId = this.getIntValue(request.getParameter("id"));
		Short status = Short.valueOf(request.getParameter("status"));
		Item item = this.getItemService().getItemById(itemId);

		if (item != null) {

			item.setStatus(status);
			this.getItemService().updateItem(item);

			result.init(SUCCESS_STATUS, Utlity.getStatusName(status), null);

		} else {
			result.init(FAIL_STATUS, "不存在id：" + itemId + " 试题", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载试题
	 * 
	 * @author jiangfei
	 * @throws IOException
	 * @date: 2014年8月18日 下午3:23:56 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void LoadItem() throws IOException {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		int itemId = this.getIntValue(request.getParameter("id"));
		Item item = this.getItemService().getItemById(itemId);

		if (item != null) {
			Map<String, Object> map = SerializeEntity.item2Map(item, split);

			// 判断是否是组合题
			if (item.getIsGroup()) {
				List<Item> childItem = this.getItemService().getItemsByItem(itemId);
				if (childItem != null && childItem.size() > 0) {

					List<Map<String, Object>> childData = new ArrayList<Map<String, Object>>();
					for (Item child : childItem) {
						childData.add(SerializeEntity.item2Map(child, split));
					}
					map.put("data", childData);

				}
			}

			result.init(SUCCESS_STATUS, null, map);

		} else {
			result.init(FAIL_STATUS, "不存在id：" + itemId + " 试题", null);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 管理列表
	 * 
	 * @author jiangfei
	 * @throws IOException
	 * @date: 2014年8月20日 下午12:15:15 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "itemType.id", type = ValueType.NUMBER)
	@ActionParam(key = "sourcetype", type = ValueType.NUMBER)
	@ActionParam(key = "diffcultyLevel", type = ValueType.NUMBER)
	@ActionParam(key = "source", type = ValueType.STRING)
	@ActionParam(key = "content", type = ValueType.STRING)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "textbookCapter.id", type = ValueType.NUMBER)
	@ActionParam(key = "textbook.id", type = ValueType.NUMBER)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() throws IOException {

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();
		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		DecimalFormat df = new DecimalFormat();
		String style = "0.0";
		df.applyPattern(style);
		
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("itemType.id", request.getParameter("itemType.id"));
		searchMap.put("sourcetype", request.getParameter("sourcetype"));
		searchMap.put("diffcultyLevel", request.getParameter("diffcultyLevel"));
		searchMap.put("source", request.getParameter("source"));
		searchMap.put("level", 1);
		String content = request.getParameter("content");

		searchMap.put("content", content);

		// searchMap.put("grade.id", request.getParameter("grade.id"));
		Integer gradeId = this.getIntValue(request.getParameter("grade.id"), 0);
		Grade sgrade = this.getGradeService().getById(gradeId);
		if (sgrade != null) {
			searchMap.put("grade.scode", sgrade.getScode());
		} else {
			searchMap.put("grade.scode", null);
		}

		// searchMap.put("knowledge.id", request.getParameter("knowledge.id"));
		Integer knowledgeId = this.getIntValue(request.getParameter("knowledge.id"), 0);
		Knowledge sknowledge = this.getKnowledgeService().getById(knowledgeId);
		if (sknowledge != null) {
			searchMap.put("knowledge.scode", sknowledge.getScode());
		} else {
			searchMap.put("knowledge.scode", null);
		}

		// searchMap.put("textbookCapter.id",request.getParameter("textbookCapter.id"));
		Integer textbookCapterId = this.getIntValue(request.getParameter("textbookCapter.id"), 0);
		TextbookCapter stextBookCapter = this.getTextbookCapterService().getById(textbookCapterId);
		if (stextBookCapter != null) {
			searchMap.put("textbookCapter.scode", stextBookCapter.getScode());
		} else {
			searchMap.put("textbookCapter.scode", null);
		}

		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("status", request.getParameter("status"));
		searchMap.put("sysUser.organization.id", currentUser.getOrganization().getId());

		int recordCount = this.getItemService().searchItemCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Item> itemList = this.getItemService().searchItem(searchMap, sorts, offset, pagesize);

		searchMap.put("createtime", DataTimeConvert.DateToString(new Date(), "yyyy-MM-dd"));
		int tadyCount = this.getItemService().searchItemCount(searchMap);

		List<Map<String, Object>> dataList = new ArrayList<>();

		if (itemList != null && itemList.size() > 0) {
			for (Item item : itemList) {

				Map<String, Object> data = SerializeEntity.item2Map(item, split);
				
				Map<String, Object> SearchAnswer = new HashMap<>();
				SearchAnswer.put("item.id", item.getId());
				int answerCount=this.getUserTestItemService().getUserTestItemCount(SearchAnswer);
				float rate=0;
				if(answerCount!=0){
					SearchAnswer.put("result", "true");
					int trueCount=this.getUserTestItemService().getUserTestItemCount(SearchAnswer);
					rate=(float)trueCount/(float)answerCount*100;
				}
				data.put("answerCount", answerCount);
				data.put("rate",df.format(rate)+"%");
				
				if (item.getIsGroup()) {
					List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
					if (childItemList != null && childItemList.size() > 0) {
						List<Map<String, Object>> childdataList = new ArrayList<>();
						int index = 1;
						for (Item itemChild : childItemList) {
							Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild, split);
							dataChild.put("stemindex", index);
							
							Map<String, Object> SearchChildAnswer = new HashMap<>();
							SearchChildAnswer.put("item.id", itemChild.getId());
							answerCount=this.getUserTestItemService().getUserTestItemCount(SearchChildAnswer);
							float childRate=0;
							if(answerCount!=0){
								SearchChildAnswer.put("result", "true");
								int childTrueCount=this.getUserTestItemService().getUserTestItemCount(SearchChildAnswer);
								childRate=(float)childTrueCount/(float)answerCount*100;
							}
							dataChild.put("answerCount", answerCount);
							dataChild.put("rate",df.format(childRate)+"%");
							childdataList.add(dataChild);
							index++;
						}
						data.put("data", childdataList);
					}
				}

				dataList.add(data);

			}
		}

		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);
		result.put("todayCount", tadyCount);

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 搜索列表
	 * 
	 * @author Administrator
	 * @throws IOException
	 * @date: 2014年8月20日 下午12:15:18 <br/>
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "itemType.id", type = ValueType.NUMBER)
	@ActionParam(key = "sourcetype", type = ValueType.NUMBER)
	@ActionParam(key = "diffcultyLevel", type = ValueType.NUMBER)
	@ActionParam(key = "source", type = ValueType.STRING)
	@ActionParam(key = "content", type = ValueType.STRING)
	@ActionParam(key = "grade.id", type = ValueType.NUMBER)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	@ActionParam(key = "textbookCapter.id", type = ValueType.NUMBER)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void Search() throws IOException {

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
		searchMap.put("itemType.id", request.getParameter("itemType.id"));
		searchMap.put("sourcetype", request.getParameter("sourcetype"));
		searchMap.put("diffcultyLevel", request.getParameter("diffcultyLevel"));
		searchMap.put("source", request.getParameter("source"));
		searchMap.put("level", 1);

		String content = request.getParameter("content");

		searchMap.put("content", content);

		// searchMap.put("grade.id", request.getParameter("grade.id"));
		Integer gradeId = this.getIntValue(request.getParameter("grade.id"), 0);
		Grade sgrade = this.getGradeService().getById(gradeId);
		if (sgrade != null) {
			searchMap.put("grade.scode", sgrade.getScode());
		} else {
			searchMap.put("grade.scode", null);
		}

		// searchMap.put("knowledge.id", request.getParameter("knowledge.id"));
		Integer knowledgeId = this.getIntValue(request.getParameter("knowledge.id"), 0);
		Knowledge sknowledge = this.getKnowledgeService().getById(knowledgeId);
		if (sknowledge != null) {
			searchMap.put("knowledge.scode", sknowledge.getScode());
		} else {
			searchMap.put("knowledge.scode", null);
		}

		// searchMap.put("textbookCapter.id",request.getParameter("textbookCapter.id"));
		Integer textbookCapterId = this.getIntValue(request.getParameter("textbookCapter.id"), 0);
		TextbookCapter stextBookCapter = this.getTextbookCapterService().getById(textbookCapterId);
		if (stextBookCapter != null) {
			searchMap.put("textbookCapter.scode", stextBookCapter.getScode());
		} else {
			searchMap.put("textbookCapter.scode", null);
		}

		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("status", request.getParameter("status"));

		int recordCount = this.getItemService().searchItemCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Item> itemList = this.getItemService().searchItem(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();

		if (itemList != null && itemList.size() > 0) {
			for (Item item : itemList) {

				Map<String, Object> data = SerializeEntity.item2Map(item, split);

				if (item.getIsGroup()) {
					List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
					if (childItemList != null && childItemList.size() > 0) {
						List<Map<String, Object>> childdataList = new ArrayList<>();
						for (Item itemChild : childItemList) {
							Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild, split);
							childdataList.add(dataChild);
						}
						data.put("data", childdataList);
					}
				}

				dataList.add(data);

			}
		}

		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);

		Utlity.ResponseWrite(result, dataType, response);

	}

	public IMaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(IMaterialService materialService) {
		this.materialService = materialService;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	public IItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(IItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
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

	public IKnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}

	public ITextbookCapterService getTextbookCapterService() {
		return textbookCapterService;
	}

	public void setTextbookCapterService(ITextbookCapterService textbookCapterService) {
		this.textbookCapterService = textbookCapterService;
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

	public ITestPaperSectionService getTestPaperSectionService() {
		return testPaperSectionService;
	}

	public void setTestPaperSectionService(ITestPaperSectionService testPaperSectionService) {
		this.testPaperSectionService = testPaperSectionService;
	}

	public IUserTestItemService getUserTestItemService() {
		return userTestItemService;
	}

	public void setUserTestItemService(IUserTestItemService userTestItemService) {
		this.userTestItemService = userTestItemService;
	}
}
