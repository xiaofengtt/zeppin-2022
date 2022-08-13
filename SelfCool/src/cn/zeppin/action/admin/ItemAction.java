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
import cn.zeppin.service.api.IPaperService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISsoUserTestItemService;
import cn.zeppin.service.api.ISsoUserTestService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ITaskService;
import cn.zeppin.service.api.ITestPaperItemService;
import cn.zeppin.service.api.ITestPaperSectionService;
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

	private IPaperService paperService;
	private ITestPaperSectionService testPaperSectionService;
	private ITestPaperItemService testPaperItemService;
	private ISsoUserService ssoUserService;
	private ITaskService taskService;

	private ISsoUserTestService ssoUserTestService;
	private ISsoUserTestItemService ssoUserTestItemService;
	
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
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "questionItem", type = ValueType.STRING, nullable = false, emptyable = false)
	public void AddJson() throws JsonProcessingException, IOException, ParserException {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");
		SysUser currentUser = (SysUser) session.getAttribute("usersession");

		// 获取数据
		String questionItem = Utlity.parseHtmlMark(request.getParameter("questionItem"));
//		System.out.println(questionItem);
		Map<String, Object> itemMap = JSONUtils.json2map(questionItem);

		// 试题类型
		Integer itemTypeID = this.getIntValue(itemMap.get("itemType.id").toString(), null);
		
		// 试题模板类型
		Integer itemModelType = this.getIntValue(itemMap.get("itemModelType").toString(), null);
		
		// 来源
		Short sourceType = Short.valueOf(itemMap.get("sourceType").toString());
		String source = itemMap.get("source").toString();
		Short diffcultyLevel = Short.valueOf(itemMap.get("diffcultyLevel").toString());

		// 学科，知识点
		Integer subjectID = this.getIntValue(itemMap.get("subject.id").toString(), null);
		Integer knowledgeID = this.getIntValue(itemMap.get("knowledge.id").toString(), null);
		
		
		Integer paperId = this.getIntValue(itemMap.get("paper.id"), null);
		Integer paperSectionId = this.getIntValue(itemMap.get("paperSection.id"), null);
		// 学科，知识点
		Grade grade = null;
		Subject subject = null;
		TextbookCapter textbookCapter = null;
		Knowledge knowledge = null;

		if (subjectID != null) {
			subject = this.getSubjectService().getSubjectById(subjectID);
		}
		if (knowledgeID != null) {
			knowledge = this.getKnowledgeService().getById(knowledgeID);
		}else{
			result.init(ERROR_STATUS, "知识点不能为空", null);
			Utlity.ResponseWrite(result, dataType, response);
			return;
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
		item.setModelType(itemModelType);
		
		item.setSysUser(currentUser);
		item.setCreatetime(new Timestamp((new Date()).getTime()));

		// 判断是否是组合题
//		if (!itemType.getIsGroup()) {
		if(itemModelType != 4){//4是组合题 !=4 非组合题
			// 不是组合题 ===> 每次都是一道题一道题添加
			Map<String, Object> mapData = (Map<String, Object>) data;
			List<ItemAnswer> listItemAns = new ArrayList<ItemAnswer>();
			
			String analysis = itemMap.get("analysis").toString();
			item.setAnalysis(analysis);//非组合题 解析绑定在题目上
			item.setIsGroup(false);
			
			if(itemModelType == 6){//不是组合题,是问答题时
				item.setItemType(itemType);
				
				Map<String, Object> mapContent = new HashMap<String, Object>();
				mapContent.put("stem", mapData.get("stem"));
				mapContent.put("score", mapData.get("score"));
				
				StringBuilder sb = new StringBuilder();
				sb.append(HtmlHelper.parseString2Html(mapData.get("stem").toString()));
				
				// itemContent还需要处理、过滤、转XML
				String contentXml = JSONUtils.obj2json(mapContent);
				item.setContentBackup(contentXml);
				item.setContent(Utlity.parseHtmlMark(sb.toString()));
				
				//问答题存入默认分值
				Integer score = this.getIntValue(mapData.get("score").toString(), 0);
				item.setDefaultScore(score);
				
				// 入库
				this.getItemService().addItem(item, null);
				
			}else{//不是组合题,不是问答题时

				map2Item(mapData, item, listItemAns);
	
				// 入库
				this.getItemService().addItem(item, listItemAns);
			}

		} else {
			// 组合题,多道题
			String material = this.getStrValue(itemMap.get("material").toString(), null);

			//父题题干转JSON存储
			Map<String, Object> mapPContent = new HashMap<String, Object>();
			mapPContent.put("stem", material);
			String contentPXml = JSONUtils.obj2json(mapPContent);
			
			// 先将父试题入库，然后在处理
			item.setItemType(itemType);
			item.setIsGroup(true);
			item.setContentBackup(contentPXml);
			item.setContent(HtmlHelper.parseString2Html(material));

			// 入库
			this.getItemService().addItem(item, null);

			List<Map<String, Object>> listMapData = (List<Map<String, Object>>) data;
			int totalScore = 0;
			for (Map<String, Object> mapData : listMapData) {

				List<ItemAnswer> listItemAns = new ArrayList<ItemAnswer>();
				Item itemChild = new Item();
				itemChild.setParent(item);
				itemChild.setLevel(2);
				
				String analysis = mapData.get("analysis").toString();//组合题 解析绑定在问题上
				itemChild.setAnalysis(analysis);
				
				itemChild.setSource(source);//来源出处
				
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
				
				//获取子题的题目类型
				Integer itemChildTypeID = this.getIntValue(mapData.get("itemType.id").toString(), null);
				ItemType itemChildType = this.getItemTypeService().getItemTypeById(itemChildTypeID);
				
				//子题的模板类型是相关的题型
				Integer modelType = Integer.parseInt(mapData.get("modelType").toString());
				itemChild.setModelType(modelType);
				
				//组合题的isGroup字段要统一
				itemChild.setIsGroup(item.getIsGroup());
				
				if(modelType == 6){//处理问答题
					itemChild.setItemType(itemChildType);
					
					Map<String, Object> mapContent = new HashMap<String, Object>();
					mapContent.put("stem", mapData.get("stem"));
					mapContent.put("score", mapData.get("score"));
					
					StringBuilder sb = new StringBuilder();
					sb.append(HtmlHelper.parseString2Html(mapData.get("stem").toString()));
					
					// itemContent还需要处理、过滤、转XML
					String contentXml = JSONUtils.obj2json(mapContent);
					itemChild.setContentBackup(contentXml);
					itemChild.setContent(Utlity.parseHtmlMark(sb.toString()));
					
					//问答题存入默认分值
					Integer score = this.getIntValue(mapData.get("score").toString(), 0);
					itemChild.setDefaultScore(score);
					
					// 入库
					this.getItemService().addItem(itemChild, null);
					totalScore += itemChild.getDefaultScore();
				}else{//处理非问答题
					
					map2Item(mapData, itemChild, listItemAns);

					// 入库
					this.getItemService().addItem(itemChild, listItemAns);
					totalScore += itemChild.getDefaultScore();
				}
			}
			item.setDefaultScore(totalScore);
			this.getItemService().updateItem(item);
		}

		 //处理试卷录入
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
//		if(item.getModelType() == 4){//添加条件 item.getModelType == 4
//			item.setIsGroup(true);
//		}else{
//			item.setIsGroup(false);
//		}
//		item.setIsGroup(itemType.getIsGroup());

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
		item.setContent(Utlity.parseHtmlMark(sb.toString()));

		// 处理答案结果
		List<Map<String, Object>> listResults = (List<Map<String, Object>>) mapData.get("results");

		for (int i = 0; i < listResults.size(); i++) {
			Map<String, Object> resultMap = listResults.get(i);

			ItemAnswer ianswer = new ItemAnswer();

			Short mce = Short.valueOf(resultMap.get("mce").toString());
			ianswer.setInx(mce);

			ianswer.setType(Dictionary.ITEM_ANSWER_TYPESTANDARD);
			ianswer.setReference(resultMap.get("inx").toString());// 选项
			ianswer.setContent(Utlity.parseHtmlMark(resultMap.get("content").toString()));// 答案

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
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "questionItem", type = ValueType.STRING, nullable = false, emptyable = false)
	public void UpdateJson() throws JsonProcessingException, IOException, ParserException {

		ActionResult result = new ActionResult();
		String dataType = request.getParameter("datatype");

		String questionItem = Utlity.parseHtmlMark(request.getParameter("questionItem"));
		Map<String, Object> itemMap = JSONUtils.json2map(questionItem);

		Integer itemId = this.getIntValue(itemMap.get("id").toString(), 0);

		Item item = this.getItemService().getItemById(itemId);
		if (item == null) {
			result.init(FAIL_STATUS, "当前试题不存在", null);
		} else {

//			if (item.getStatus() != Dictionary.ITEM_STATUS_DRAFT && item.getStatus() != Dictionary.ITEM_STATUS_STOP) {
//				result.init(FAIL_STATUS, "当前试题状态不可编辑", null);
//			} else {

				// 来源 难易程度
				Short sourceType = Short.valueOf(itemMap.get("sourceType").toString());
				String source = itemMap.get("source").toString();
				Short diffcultyLevel = Short.valueOf(itemMap.get("diffcultyLevel").toString());

				Integer subjectID = this.getIntValue(itemMap.get("subject.id").toString(), null);
				Integer knowledgeID = this.getIntValue(itemMap.get("knowledge.id").toString(), null);
				

				Integer paperId = this.getIntValue(itemMap.get("paper.id"), null);
				Integer paperSectionId = this.getIntValue(itemMap.get("paperSection.id"), null);
				// 学科,知识点
				Grade grade = null;
				Subject subject = null;
				TextbookCapter textbookCapter = null;
				Knowledge knowledge = null;

				if (subjectID != null) {
					subject = this.getSubjectService().getSubjectById(subjectID);
				}
				if (knowledgeID != null) {
					knowledge = this.getKnowledgeService().getById(knowledgeID);
				}else{
					result.init(ERROR_STATUS, "知识点不能为空", null);
					Utlity.ResponseWrite(result, dataType, response);
					return;
				}

				item.setSource(source);
				item.setSourceType(sourceType);
				item.setDiffcultyLevel(diffcultyLevel);

				item.setGrade(grade);
				item.setSubject(subject);
				item.setTextbookCapter(textbookCapter);
				item.setKnowledge(knowledge);

				
				//编辑过的题目,均为草稿状态
				item.setStatus(Dictionary.ITEM_STATUS_DRAFT);
				
				Object data = itemMap.get("data");

				if (item.getModelType() != 4) {//!=4即为非组合题
					//不是组合题时,获取解析
					String analysis = itemMap.get("analysis").toString();
					item.setAnalysis(analysis);
					
					Map<String, Object> mapData = (Map<String, Object>) data;
					List<ItemAnswer> listItemAns = new ArrayList<ItemAnswer>();
					
					if(item.getModelType() == 6){//不是组合题,是问答题时
						
						Map<String, Object> mapContent = new HashMap<String, Object>();
						mapContent.put("stem", mapData.get("stem"));
						mapContent.put("score", mapData.get("score"));
						
						StringBuilder sb = new StringBuilder();
						sb.append(HtmlHelper.parseString2Html(mapData.get("stem").toString()));
						
						// itemContent还需要处理、过滤、转XML
						String contentXml = JSONUtils.obj2json(mapContent);
						item.setContentBackup(contentXml);
						item.setContent(Utlity.parseHtmlMark(sb.toString()));
						
						//问答题存入默认分值
						Integer score = this.getIntValue(mapData.get("score").toString(), 0);
						item.setDefaultScore(score);
						
						// 入库
						this.getItemService().updateItem(item, null);
						
					}else{//不是组合题,不是问答题时
						map2Item(mapData, item, listItemAns);
	
						this.getItemService().updateItem(item, listItemAns);
					}
				} else {
					// 组合题,多道题
					String material = this.getStrValue(itemMap.get("material").toString(), null);

					// 先将父试题更新，然后在处理
					if (material != null) {
						//父题题干转JSON存储
						Map<String, Object> mapPContent = new HashMap<String, Object>();
						mapPContent.put("stem", material);
						String contentPXml = JSONUtils.obj2json(mapPContent);
						
						item.setContentBackup(contentPXml);
						item.setContent(HtmlHelper.parseString2Html(material));
					}
					this.getItemService().updateItem(item, null);

					List<Map<String, Object>> listMapData = (List<Map<String, Object>>) data;
					int totalScore = 0;
					for (Map<String, Object> mapData : listMapData) {

						List<ItemAnswer> listItemAns = new ArrayList<ItemAnswer>();
						Item itemChild = new Item();
						itemChild.setParent(item);
						itemChild.setLevel(2);

						String analysis = mapData.get("analysis").toString();//组合题 解析绑定在问题上
						itemChild.setAnalysis(analysis);
						
						itemChild.setSource(source);//来源出处
						
						// 数据库必填项
						itemChild.setDiffcultyLevel(diffcultyLevel);
						itemChild.setSourceType(sourceType);
						itemChild.setStatus(Dictionary.ITEM_STATUS_DRAFT);

						itemChild.setSysUser(item.getSysUser());
						itemChild.setCreatetime(new Timestamp((new Date()).getTime()));
						itemChild.setKnowledge(knowledge);
						itemChild.setSubject(subject);

						//获取子题的题目类型
						Integer itemChildTypeID = this.getIntValue(mapData.get("itemType.id").toString(), null);
						ItemType itemChildType = this.getItemTypeService().getItemTypeById(itemChildTypeID);
						
						//子题的模板类型是相关的题型
						Integer modelType = Integer.parseInt(mapData.get("modelType").toString());
						itemChild.setModelType(modelType);
						
						itemChild.setIsGroup(item.getIsGroup());
						
						if(itemChildType.getModelType() == 6){//处理问答题
							itemChild.setItemType(itemChildType);
							
							Map<String, Object> mapContent = new HashMap<String, Object>();
							mapContent.put("stem", mapData.get("stem"));
							mapContent.put("score", mapData.get("score"));
							
							StringBuilder sb = new StringBuilder();
							sb.append(HtmlHelper.parseString2Html(mapData.get("stem").toString()));
							
							// itemContent还需要处理、过滤、转XML
							String contentXml = JSONUtils.obj2json(mapContent);
							itemChild.setContentBackup(contentXml);
							itemChild.setContent(Utlity.parseHtmlMark(sb.toString()));
							
							//问答题存入默认分值
							Integer score = this.getIntValue(mapData.get("score").toString(), 0);
							itemChild.setDefaultScore(score);
							
							// 入库
							this.getItemService().addItem(itemChild, null);
							totalScore += itemChild.getDefaultScore();
						}else{
							map2Item(mapData, itemChild, listItemAns);

							// 入库
							this.getItemService().addItem(itemChild, listItemAns);
							totalScore += itemChild.getDefaultScore();
						}
						
						

					}
					item.setDefaultScore(totalScore);
					this.getItemService().updateItem(item);

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

//			}

		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 改变试题的状态
	 * 
	 * @author Administrator
	 * @date: 2014年8月20日 下午12:53:22 <br/>
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
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
			this.getItemService().updateItemStatus(item);

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
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
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
			if (item.getModelType() == 4) {//新增条件 && item.getModeType() == 4 为组合题
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
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
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

		Integer knowledgeId = this.getIntValue(request.getParameter("knowledge.id"), 0);
		Knowledge sknowledge = this.getKnowledgeService().getById(knowledgeId);
		if (sknowledge != null) {
			searchMap.put("knowledge.scode", sknowledge.getScode());
		} else {
			searchMap.put("knowledge.scode", null);
		}

		searchMap.put("subject.id", request.getParameter("subject.id"));
		searchMap.put("status", request.getParameter("status"));
		searchMap.put("sysUser.organization.id", currentUser.getOrganization().getId());

		

		List<Item> itemList = this.getItemService().searchItem(searchMap, sorts, offset, pagesize);
		searchMap.remove("level");
		
		int recordCount = this.getItemService().searchItemCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
		
		searchMap.put("createtime", DataTimeConvert.DateToString(new Date(), "yyyy-MM-dd"));
		
		int tadyCount = this.getItemService().searchItemCount(searchMap);

		List<Map<String, Object>> dataList = new ArrayList<>();

		if (itemList != null && itemList.size() > 0) {
			for (Item item : itemList) {
//				if(item.getModelType() == 6){
//					String contentbackup = "{\"stem\":\""+item.getContentBackup()+"\"}";
//					item.setContentBackup(contentbackup);
//					this.getItemService().updateItem(item);
//				}
				Map<String, Object> data = SerializeEntity.item2Map(item, split);

//				Map<String, Object> SearchAnswer = new HashMap<>();
//				SearchAnswer.put("item.id", item.getId());
//				int answerCount = this.getUserTestItemService().getUserTestItemCount(SearchAnswer);
//				float rate = 0;
//				if (answerCount != 0) {
//					SearchAnswer.put("result", "true");
//					int trueCount = this.getUserTestItemService().getUserTestItemCount(SearchAnswer);
//					rate = (float) trueCount / (float) answerCount * 100;
//				}
//				data.put("answerCount", answerCount);
//				data.put("rate", df.format(rate) + "%");

				if (item.getModelType() == 4) {//修改判定条件为item.getModelType == 4
					List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
					if (childItemList != null && childItemList.size() > 0) {
						List<Map<String, Object>> childdataList = new ArrayList<>();
						int index = 1;
						for (Item itemChild : childItemList) {
							Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild, split);
							dataChild.put("stemindex", index);

//							Map<String, Object> SearchChildAnswer = new HashMap<>();
//							SearchChildAnswer.put("item.id", itemChild.getId());
//							answerCount = this.getUserTestItemService().getUserTestItemCount(SearchChildAnswer);
//							float childRate = 0;
//							if (answerCount != 0) {
//								SearchChildAnswer.put("result", "true");
//								int childTrueCount = this.getUserTestItemService().getUserTestItemCount(SearchChildAnswer);
//								childRate = (float) childTrueCount / (float) answerCount * 100;
//							}
//							dataChild.put("answerCount", answerCount);
//							dataChild.put("rate", df.format(childRate) + "%");
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
	 * 加入组合题后的搜索逻辑（按题干）
	 * 搜索所有题目中包含相关题干内容的题目信息，先不划分是否是组合题
	 * 搜索结果List中判断是否是组合题，若果是组合题的小题，则获取到它的父题以及父题下所有的子题
	 * 
	 * 加入按照ID查询后的逻辑
	 * 增加回传参数searchType 区分搜题干和搜编号
	 * 判断回传参数是否是题目ID
	 * 判断搜索出来的题目是否是组合题，如果是组合题的子题 则获取它的父题以及父题下所有的子题
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
	@ActionParam(key = "searchType", type = ValueType.STRING)
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
//		searchMap.put("level", 1);
		
		String searchType = this.getStrValue(request.getParameter("searchType"));
		
		//搜题干
		if(searchType != null && !"".equals(searchType) && "content".equals(searchType)){
			String content = request.getParameter("content");
			
			searchMap.put("content", content);
	
			Integer knowledgeId = this.getIntValue(request.getParameter("knowledge.id"), 0);
			Knowledge sknowledge = this.getKnowledgeService().getById(knowledgeId);
			if (sknowledge != null) {
				searchMap.put("knowledge.scode", sknowledge.getScode());
			} else {
				searchMap.put("knowledge.scode", null);
			}
	
			searchMap.put("subject.id", request.getParameter("subject.id"));
			searchMap.put("status", request.getParameter("status"));
	
	
	
			List<Item> itemList = this.getItemService().searchItem(searchMap, sorts, offset, pagesize);
			List<Map<String, Object>> dataList = new ArrayList<>();
	
			
			if (itemList != null && itemList.size() > 0) {
				Map<String, Object> data = new HashMap<String, Object>();
				for (Item item : itemList) {

					if(!item.getIsGroup()){
						data = SerializeEntity.item2Map(item, split);
					}
					if(item.getLevel() == 2){//是组合题子题
						data = SerializeEntity.item2Map(item.getParent(), split);
						List<Item> childItemList = this.getItemService().getItemsByItem(item.getParent().getId());
						if (childItemList != null && childItemList.size() > 0) {
							List<Map<String, Object>> childdataList = new ArrayList<>();
							for (Item itemChild : childItemList) {
								Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild, split);
								childdataList.add(dataChild);
							}
							data.put("data", childdataList);
						}
					} else if (item.getIsGroup() && item.getLevel() == 1) {//是组合题父题
						data = SerializeEntity.item2Map(item, split);
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
			
			int recordCount = this.getItemService().searchItemCount(searchMap);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			
			searchMap.put("createtime", DataTimeConvert.DateToString(new Date(), "yyyy-MM-dd"));
			
			int tadyCount = this.getItemService().searchItemCount(searchMap);
			
			result.init(SUCCESS_STATUS, "搜索完成！", dataList);
			result.setPageCount(pageCount);
			result.setPageNum(pagenum);
			result.setPageSize(pagesize);
			result.setTotalCount(recordCount);
			result.put("todayCount", tadyCount);
			
			Utlity.ResponseWrite(result, dataType, response);
			
		} else if (searchType != null && !"".equals(searchType) && "itemID".equals(searchType)) {//搜题号
			
			List<Map<String, Object>> dataList = new ArrayList<>();
			
			Integer itemID = this.getIntValue(request.getParameter("content"), 0);
			Item item = this.itemService.getItemById(itemID);
			Map<String, Object> data = new HashMap<String, Object>();
			if(!item.getIsGroup()){
				data = SerializeEntity.item2Map(item, split);
			}
			int recordCount = 0;
			if(item.getLevel() == 2){//是组合题子题
				data = SerializeEntity.item2Map(item.getParent(), split);
				List<Item> childItemList = this.getItemService().getItemsByItem(item.getParent().getId());
				if (childItemList != null && childItemList.size() > 0) {
					List<Map<String, Object>> childdataList = new ArrayList<>();
					for (Item itemChild : childItemList) {
						Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild, split);
						childdataList.add(dataChild);
					}
					data.put("data", childdataList);
				}
				recordCount = childItemList.size();
			} else if (item.getIsGroup() && item.getLevel() == 1) {//是组合题父题
				data = SerializeEntity.item2Map(item, split);
				List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
				if (childItemList != null && childItemList.size() > 0) {
					List<Map<String, Object>> childdataList = new ArrayList<>();
					for (Item itemChild : childItemList) {
						Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild, split);
						childdataList.add(dataChild);
					}
					data.put("data", childdataList);
				}
				recordCount = childItemList.size();
			}
			
			dataList.add(data);
			int pageCount = (int) Math.ceil((double) recordCount / pagesize);
			
			result.init(SUCCESS_STATUS, "搜索完成！", dataList);
			result.setPageNum(pagenum);
			result.setPageSize(pagesize);
			result.setTotalCount(recordCount);
			result.setPageCount(pageCount);
			
			Utlity.ResponseWrite(result, dataType, response);
		}
		
//		String dataType = request.getParameter("datatype");
//		ActionResult result = new ActionResult();
//		String split = request.getParameter("split");
//		split = split == null ? "." : split;
//
//		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
//		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
//		int offset = (pagenum - 1) * pagesize;
//		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
//
//		Map<String, Object> searchMap = new HashMap<>();
//		searchMap.put("id", request.getParameter("id"));
//		searchMap.put("itemType.id", request.getParameter("itemType.id"));
//		searchMap.put("sourcetype", request.getParameter("sourcetype"));
//		searchMap.put("diffcultyLevel", request.getParameter("diffcultyLevel"));
//		searchMap.put("source", request.getParameter("source"));
//		searchMap.put("level", 1);
//
//		String content = request.getParameter("content");
//
//		searchMap.put("content", content);
//
//		Integer knowledgeId = this.getIntValue(request.getParameter("knowledge.id"), 0);
//		Knowledge sknowledge = this.getKnowledgeService().getById(knowledgeId);
//		if (sknowledge != null) {
//			searchMap.put("knowledge.scode", sknowledge.getScode());
//		} else {
//			searchMap.put("knowledge.scode", null);
//		}
//
//		searchMap.put("subject.id", request.getParameter("subject.id"));
//		searchMap.put("status", request.getParameter("status"));
//
//
//
//		List<Item> itemList = this.getItemService().searchItem(searchMap, sorts, offset, pagesize);
//		List<Map<String, Object>> dataList = new ArrayList<>();
//
//		if (itemList != null && itemList.size() > 0) {
//			for (Item item : itemList) {
//
//				Map<String, Object> data = SerializeEntity.item2Map(item, split);
//
//				if (item.getModelType() == 4) {//修改判定条件item.getModelType == 4
//					List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
//					if (childItemList != null && childItemList.size() > 0) {
//						List<Map<String, Object>> childdataList = new ArrayList<>();
//						for (Item itemChild : childItemList) {
//							Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild, split);
//							childdataList.add(dataChild);
//						}
//						data.put("data", childdataList);
//					}
//				}
//
//				dataList.add(data);
//
//			}
//		}
//		
//		searchMap.remove("level");
//		int recordCount = this.getItemService().searchItemCount(searchMap);
//		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
//		
//		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
//		result.setPageCount(pageCount);
//		result.setPageNum(pagenum);
//		result.setPageSize(pagesize);
//		result.setTotalCount(recordCount);
//
//		Utlity.ResponseWrite(result, dataType, response);

	}
	
	
//	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	@ActionParam(key = "number", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	public void List() throws IOException {
//
//		ActionResult result = new ActionResult();
//
//		Integer subjectId = Integer.valueOf(request.getParameter("subject.id"));
//		Integer number = Integer.valueOf(request.getParameter("number"));
//
//		Map<String, Object> searchMap = new HashMap<>();
//		searchMap.put("level", 1);
//		searchMap.put("subject.id", subjectId);
//		// searchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);
//
//		int itemCount = this.getItemService().searchItemCount(searchMap);
//		if (itemCount > 0) {
//
//			int rnd = new Random().nextInt(itemCount);
//
//			if (itemCount - rnd < number) {
//				rnd = rnd - (number - (itemCount - rnd));
//			}
//			if (rnd < 0) {
//				rnd = 0;
//			}
//			List<Item> itemList = this.getItemService().searchItem(searchMap, "createtime asc", rnd, number);
//			LinkedList<Map<String, Object>> itemData = new LinkedList<Map<String, Object>>();
//
//			if (itemList != null && itemList.size() > 0) {
//				for (Item item : itemList) {
//					Map<String, Object> data = SerializeEntity.item2Map(item);
//					if (item.getIsGroup()) {
//						List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
//						if (childItemList != null && childItemList.size() > 0) {
//							List<Map<String, Object>> childdataList = new LinkedList<Map<String, Object>>();
//							int index = 1;
//							for (Item itemChild : childItemList) {
//								Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild);
//								dataChild.put("stemindex", index);
//								childdataList.add(dataChild);
//								index++;
//							}
//							data.put("data", childdataList);
//						}
//					}
//					itemData.add(data);
//				}
//
//				result.init(SUCCESS_STATUS, "加载试题成功！", itemData);
//			} else {
//				result.init(FAIL_STATUS, "当前学科下不存在试题", null);
//			}
//		} else {
//			result.init(FAIL_STATUS, "当前学科下不存在试题", null);
//		}
//
//		String dataType = request.getParameter("datatype");
//		Utlity.ResponseWrite(result, dataType, response);
//	}

//	/**
//	 * 自动出题
//	 * 
//	 * @throws IOException
//	 */
//	@SuppressWarnings("unused")
//	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	@ActionParam(key = "task.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	@ActionParam(key = "number", type = ValueType.NUMBER)
//	public void AutoItems() throws IOException {
//
//		// ======================================================//
//		// 自动出题
//		// 1.根据任务题型来获取试题
//		// 2.设置试卷信息
//		// =======================================================//
//
//		ActionResult result = new ActionResult();
//
//		int subjectId = this.getIntValue(this.request.getParameter("subject.id"));
//		int taskId = this.getIntValue(this.request.getParameter("task.id"));
//		int number = this.getIntValue(this.request.getParameter("number"), Dictionary.DEFAULT_ITEM_NUMBER);
//
//		Map<String, Object> searchMap = new HashMap<>();
//		searchMap.put("level", 1);
//		searchMap.put("subject.id", subjectId);
//
//		// 以后可能是根据任务题型来获取
//		searchMap.put("itemType.model", Dictionary.ITEM_TYPE_MODEL_SINGLE + "," + Dictionary.ITEM_TYPE_MODEL_MULTI);
//		// searchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);
//
//		int itemCount = this.getItemService().searchItemCount(searchMap);
//		if (itemCount > 0) {
//
//			int rnd = new Random().nextInt(itemCount);
//
//			if (itemCount - rnd < number) {
//				rnd = rnd - (number - (itemCount - rnd));
//			}
//			if (rnd < 0) {
//				rnd = 0;
//			}
//
//			List<Item> itemList = this.getItemService().searchItem(searchMap, "createtime asc", rnd, number);
//			LinkedList<Map<String, Object>> itemData = new LinkedList<Map<String, Object>>();
//
//			if (itemList != null && itemList.size() > 0) {
//
//				for (Item item : itemList) {
//
//					Map<String, Object> data = SerializeEntity.item2Map(item);
//					if (item.getIsGroup()) {
//						List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
//						if (childItemList != null && childItemList.size() > 0) {
//							List<Map<String, Object>> childdataList = new LinkedList<Map<String, Object>>();
//							int index = 1;
//							for (Item itemChild : childItemList) {
//								Map<String, Object> dataChild = SerializeEntity.item2Map(itemChild);
//								dataChild.put("stemindex", index);
//								childdataList.add(dataChild);
//								index++;
//							}
//							data.put("data", childdataList);
//						}
//					}
//					itemData.add(data);
//				}
//
//				result.init(SUCCESS_STATUS, "加载试题成功！", itemData);
//
//			} else {
//				result.init(FAIL_STATUS, "当前学科下不存在试题", null);
//			}
//
//		} else {
//			result.init(FAIL_STATUS, "当前学科下不存在试题", null);
//		}
//
//		String dataType = request.getParameter("datatype");
//		Utlity.ResponseWrite(result, dataType, response);
//
//	}
//
//	/**
//	 * 提交答卷
//	 * 
//	 */
//	@SuppressWarnings({ "unchecked"
//		, "unused" })
//	@ActionParam(key = "answers", type = ValueType.STRING, nullable = false, emptyable = false)
//	
//	/**
//	 *  answers 参数格式说明
// 		{
//		    "uid": 0,
//		    "subject.id": 0,
//		    "paper.id": 0,
//		    "task.id": 0,
//		    "answers": [
//		        {
//		            "item.id": 0,
//		            "answertime": 0,
//		            "inx": "",
//		            "content": ""
//		        },
//		        {
//		            "item.id": 0,
//		            "answertime": 0,
//		            "inx": "",
//		            "content": ""
//		        },
//		        {
//		            "item.id": 0,
//		            "answertime": 0,
//		            "inx": "",
//		            "content": ""
//		        },
//		        {
//		            "item.id": 0,
//		            "answertime": 0,
//		            "inx": "",
//		            "content": ""
//		        }
//		    ]
//		}
//	 */
//	public void CommitAnswers() {
//
//		// =================================================//
//		// 1.提并入库
//		// 根据任务类型，需判断是否正确
//		// 2.计算知识点掌握程度
//		// 3.计算进度
//		// =================================================//
//		
//		Map<String, Object> submitMap = JSONUtils.json2map(request.getParameter("answers"));
//		String s_uid = submitMap.get("uid").toString();
//		String s_subjectId = submitMap.get("subject.id").toString();
//		String s_paperId = submitMap.get("paper.id").toString();
//		String s_taskId = submitMap.get("task.id").toString();
//
//		List<Map<String, Object>> itemList = (List<Map<String, Object>>) submitMap.get("answers");
//
//		ActionResult result = new ActionResult();
//
//		// ===================用户信息============================//
//
//		SsoUser ssoUser = (SsoUser) this.session.getAttribute("user");
//		int uid = this.getIntValue(s_uid, 0);
//
//		if (ssoUser == null) {
//			ssoUser = this.getSsoUserService().getById(uid);
//		}
//
//		// ====================学科信息=======================================//
//
//		int subjectId = this.getIntValue(s_subjectId, 0);
//		Subject subject = this.getSubjectService().getSubjectById(subjectId);
//
//		// ==================任务信息===========================================//
//
//		int taskId = this.getIntValue(s_taskId, 0);
//		Task task = this.getTaskService().getById(taskId);
//
//		// ===================试卷信息====================================//
//
//		int paperId = this.getIntValue(s_paperId, 0);
//		Paper paper = this.getPaperService().getPaperById(paperId);
//
//		if (ssoUser == null) {
//
//			result.init(ERROR_STATUS, "用户未登录", null);
//
//		} else if (subject == null) {
//			
//			result.init(ERROR_STATUS, "未选择学科", null);
//
//		} else {
//
//			// ========是否要将试题添加到 试卷关系表中===============//
//			boolean isInsertPaper = false;
//			TestPaperSection testPaperSection = null;
//
//			if (paper == null) {
//
//				// 不是以试卷答题，先生成一张 “自动组卷” 的试卷
//
//				isInsertPaper = true;
//
//				paper = new Paper();
//				paper.setType((short) 0);
//				paper.setName("自动组卷");
//				paper.setSource("自动组卷");
//				paper.setAnswerTime(15);
//				paper.setTotalScore((short) 0);
//				paper.setStatus(Dictionary.PAPER_STATUS_AUDITING);
//				paper.setSubject(subject);
//				paper.setGrade(null);
//
//				List<TestPaperSection> lstSections = new LinkedList<TestPaperSection>();
//
//				testPaperSection = new TestPaperSection();
//				testPaperSection.setInx((short) 1);
//				testPaperSection.setName("自动组卷目录");
//				testPaperSection.setLevel((short) 1);
//				lstSections.add(testPaperSection);
//
//				this.getPaperService().addPaper(paper, lstSections);
//			}
//
//			// 添加用户答题记录
//			SsoUserTest usertest = new SsoUserTest();
//			usertest.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			usertest.setDuration(0);
//			usertest.setPaper(paper);
//			usertest.setScore((double) 0.0);
//			usertest.setSsoUser(ssoUser);
//			usertest.setStatus(Dictionary.USER_TEST_STATUS_START);
//
//			this.getSsoUserTestService().addUserTest(usertest);
//
//			// 循环处理 试题 答题信息
//			int index = 0;
//			int Score = 0;
//			int AnswerTime = 0;
//
//			List<Map<String, Object>> items = new LinkedList<Map<String, Object>>();
//
//			for (Map<String, Object> map : itemList) {
//
//				String s_itemId = map.get("item.id").toString();
//				String s_answertime = map.get("answertime").toString();
//				String s_inx = map.get("inx").toString();
//				String s_content = map.get("content").toString();
//
//				int itemId = this.getIntValue(s_itemId, 0);
//				Item item = this.getItemService().getItemById(itemId);
//				if (item != null) {
//
//					if (isInsertPaper) {
//						// 需要将记录与试卷关联
//						TestPaperItem tpi = new TestPaperItem();
//						tpi.setInx((short) (index + 1));
//						tpi.setPaper(paper);
//						tpi.setTestPaperSection(testPaperSection);
//						tpi.setItem(item);
//
//						this.getTestPaperItemService().addTestPaperItem(tpi);
//					}
//
//					// 将数据入库到用户答题记录里面
//					// 应根据任务 的回答类型
//
//					if (item.getItemType().getModelType() == Dictionary.ITEM_TYPE_MODEL_SINGLE || item.getItemType().getModelType() == Dictionary.ITEM_TYPE_MODEL_JUDGE || item.getItemType().getModelType() == Dictionary.ITEM_TYPE_MODEL_MULTI) {
//
//						// ====================单选，多选，判断========================//
//
//						Map<String, Object> searchMap = new HashMap<>();
//						searchMap.put("item.id", itemId);
//						List<ItemAnswer> answers = this.getItemService().getItemAnswerByParam(searchMap);
//						ItemAnswer answer = answers.get(0);
//
//						SsoUserTestItem uti = new SsoUserTestItem();
//						uti.setItem(item);
//						uti.setAnswertime(Integer.valueOf(s_answertime));
//
//						AnswerTime = AnswerTime + Integer.valueOf(s_answertime);
//						uti.setItemAnswer(answer);
//
//						uti.setContent(s_inx);
//						uti.setInx(index + 1);
//
//						if (answer.getReference().equals(s_inx)) {
//
//							Score = Score + answer.getScore();
//							uti.setScore((double) answer.getScore());
//							uti.setCompleteType(Dictionary.SSO_USER_TEST_ITEM_OK);
//
//						} else {
//
//							uti.setScore(0d);
//							uti.setCompleteType(Dictionary.SSO_USER_TEST_ITEM_ERROR);
//
//						}
//
//						this.getSsoUserTestItemService().addUserTestItem(uti);
//
//						items.add(SerializeEntity.userTestItem2Map(uti));
//					}
//
//					index++;
//
//				} else {
//					// 不存在 试题
//				}
//
//			}
//
//			// 设置总时间和总分
//			usertest.setDuration(AnswerTime);
//			usertest.setScore((double) Score);
//			this.getSsoUserTestService().updateUserTest(usertest);
//
//			Map<String, Object> retMap = SerializeEntity.userTest2Map(usertest);
//			retMap.put("items", items);
//
//			result.init(SUCCESS_STATUS, "处理成功", retMap);
//
//		}
//
//		String dataType = request.getParameter("datatype");
//		Utlity.ResponseWrite(result, dataType, response);
//
//	}
//
//	/**
//	 * 试题解析
//	 * 
//	 * @throws IOException
//	 */
//	@ActionParam(key = "usertest.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
//	public void ParseItems() throws IOException {
//
//		// ======================试题解析=============================//
//		// 1.根据type 是错题解析还是全题解析
//		// ===========================================================//
//
//		ActionResult result = new ActionResult();
//
//		Long usertestId = Long.parseLong(request.getParameter("usertest.id"));
//		Short type = Short.parseShort(request.getParameter("type"));
//
//		Map<String, Object> searchMap = new Hashtable<String, Object>();
//		searchMap.put("usertest.id", usertestId);
//		if (type == Dictionary.SSO_USER_TEST_ITEM_ERROR) {
//			// ===============查看错题解析==========================//
//			searchMap.put("completeType", type);
//		}
//
//		// =============================查询出答题记录=============================//
//
//		List<SsoUserTestItem> lstUsts = this.getSsoUserTestItemService().getUserTestItemsByMap(searchMap, -1, -1);
//
//		if (lstUsts != null && lstUsts.size() > 0) {
//
//			List<Map<String, Object>> lstData = new LinkedList<Map<String, Object>>();
//
//			for (SsoUserTestItem suti : lstUsts) {
//
//				Item item = suti.getItem();
//
//				if (item.getParent() != null) {
//
//					// 这个是材料题的子题，稍後在處理
//
//				} else {
//
//					Map<String, Object> itemData = SerializeEntity.item2Map(item);
//					itemData.put("completeType", suti.getCompleteType());
//					itemData.put("completeContent", suti.getContent());
//
//					lstData.add(itemData);
//
//				}
//
//			}
//
//			result.init(SUCCESS_STATUS, "数据加载成功", lstData);
//
//		} else {
//			result.init(FAIL_STATUS, "没有答题记录", null);
//		}
//
//		String dataType = request.getParameter("datatype");
//		Utlity.ResponseWrite(result, dataType, response);
//
//	}

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
	
	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	

	public ISsoUserTestService getSsoUserTestService() {
		return ssoUserTestService;
	}

	public void setSsoUserTestService(ISsoUserTestService ssoUserTestService) {
		this.ssoUserTestService = ssoUserTestService;
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
}
