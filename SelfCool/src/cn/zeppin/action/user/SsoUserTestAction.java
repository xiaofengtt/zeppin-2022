package cn.zeppin.action.user;

import java.math.BigInteger;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.access.ItemEx;
import cn.zeppin.access.PaperSectionEx;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserPay;
import cn.zeppin.entity.SsoUserTest;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.SsoUserTestItemCount;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.entity.TestPaperItem;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.entity.UserSubject;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.IPaperService;
import cn.zeppin.service.api.ISsoUserPayService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISsoUserTestItemCountService;
import cn.zeppin.service.api.ISsoUserTestItemService;
import cn.zeppin.service.api.ISsoUserTestService;
import cn.zeppin.service.api.ISubjectItemTypeService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.ITestPaperItemService;
import cn.zeppin.service.api.ITestPaperSectionService;
import cn.zeppin.service.api.IUserSubjectService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.ItemToHtml;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

public class SsoUserTestAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4688622107756085930L;

	private ISsoUserService ssoUserService;
	private ISsoUserTestService ssoUserTestService;
	private ISsoUserPayService ssoUserPayService;
	private ISubjectService subjectService;
	private IItemService itemService;
	private IPaperService paperService;
	private ISsoUserTestItemService ssoUserTestItemService;
	private IKnowledgeService knowledgeService;
	private IUserSubjectService userSubjectService;
	private ISubjectItemTypeService subjectItemTypeService;
	private ISsoUserTestItemCountService ssoUserTestItemCountService;
	
	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public ISsoUserTestService getSsoUserTestService() {
		return ssoUserTestService;
	}

	public void setSsoUserTestService(ISsoUserTestService ssoUserTestService) {
		this.ssoUserTestService = ssoUserTestService;
	}

	public ISsoUserPayService getSsoUserPayService() {
		return ssoUserPayService;
	}

	public void setSsoUserPayService(ISsoUserPayService ssoUserPayService) {
		this.ssoUserPayService = ssoUserPayService;
	}

	
	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public IPaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
	}
	
	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	public ISsoUserTestItemService getSsoUserTestItemService() {
		return ssoUserTestItemService;
	}

	public void setSsoUserTestItemService(ISsoUserTestItemService ssoUserTestItemService) {
		this.ssoUserTestItemService = ssoUserTestItemService;
	}

	
	public IKnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}

	public IUserSubjectService getUserSubjectService() {
		return userSubjectService;
	}

	public void setUserSubjectService(IUserSubjectService userSubjectService) {
		this.userSubjectService = userSubjectService;
	}

	public ISubjectItemTypeService getSubjectItemTypeService() {
		return subjectItemTypeService;
	}

	public void setSubjectItemTypeService(ISubjectItemTypeService subjectItemTypeService) {
		this.subjectItemTypeService = subjectItemTypeService;
	}

	public ISsoUserTestItemCountService getSsoUserTestItemCountService() {
		return ssoUserTestItemCountService;
	}

	public void setSsoUserTestItemCountService(
			ISsoUserTestItemCountService ssoUserTestItemCountService) {
		this.ssoUserTestItemCountService = ssoUserTestItemCountService;
	}

	/**
	 * 为某个用户按学科知识点智能进行出题（选择哪些题让用户做和他们的排序，是一个非常最复杂的算法逻辑，需要考虑的因素比较多，需要理解后进行修改）
	 * 【设计思路】 首先应该计算用户该知识点下做过的题的次数以及正确的次数，根据选择最少正确次数的题的原则，在集合中进行随机出题
	 * 暂时不考虑找出的试题不够数量的情况，暂时不考虑标准化组合题的情况（如阅读理解）
	 * 一次出题中试题不能重复
	 * Ver1.0只考虑单选题，因为都是单选题，所以也不需要太复杂的算法逻辑
	 * 
	 * @throws IOException
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	public void SelectItems() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		String version = request.getParameter("version");
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if (currentUser == null) {
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);

		}
		if (currentUser == null) {
			result.init(FAIL_STATUS, "无此用户！", null);
		} else {
			this.session.setAttribute("user", currentUser);
			String kownledgeScode = null;
			if (request.getParameter("knowledge.id") != null) {
				Integer knowledgeId = this.getIntValue(request.getParameter("knowledge.id"));
				Knowledge knowledge = this.getKnowledgeService().getById(knowledgeId);
				kownledgeScode = knowledge.getScode();
			}

			

			int subjectId = this.getIntValue(request.getParameter("subject.id"));
			Subject subject = this.getSubjectService().getSubjectById(subjectId);

			if (subject != null) {

				Map<String, Object> searchMap = new HashMap<>();
				searchMap.put("subject.id", request.getParameter("subject.id"));
				searchMap.put("knowledge.scode", kownledgeScode);
				searchMap.put("user.id", currentUser.getId());
				searchMap.put("itemType.isStandard", Dictionary.ITEM_ANSWER_TYPESTANDARD);
				searchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);

				// ****为用户选择试题算法****
				List<Map<String, Object>> selectItems = this.getItemService().selectItems(searchMap, Dictionary.DEFAULT_ITEM_NUMBER);

				if (selectItems != null && selectItems.size() > 0) {
					Map<String, Object> autoPaperMap = this.getSsoUserTestService().createAutoTest(subject, currentUser, selectItems, split , version);

					result.init(SUCCESS_STATUS, "数据获成功", autoPaperMap);
					result.setTotalCount(selectItems.size());
				} else {
					result.init(FAIL_STATUS, "该学科知识点下不存在试题！", null);
				}
			} else {
				result.init(FAIL_STATUS, "学科Id信息错误！", null);
			}
		}
		Utlity.ResponseWrite(result, dataType, response);
	}

	
	/**
	 * 提交自动生成试卷（主要是单选、多选、完型、听力等客观题部分），包括试题和答案列表 
	 * @param answers 是提交答案的字符串，采用标准JSON格式
	 * 
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "ssoUserTest.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "duration", type = ValueType.NUMBER)  //单位为毫秒
	@ActionParam(key = "answers", type = ValueType.STRING, nullable = false, emptyable = false)
	/** 
	 *  answers参数介绍:
	 *  协议为JSON格式，内容是用户回答试题的数组 
	 *  ssoUserTestItem.id为用户做题的ID（题可重复做）
	 *  answertime为一道题的做题时间，单位:毫秒
	 *  blankInx为选择题用户的选项答案（可多选）
	 *  content为用户的答案内容
	 *  
	 [
	//单选题
	   {
            "ssoUserTestItem.id": 119,
            "answertime":60000,    
            "blankInx": 1,
            "reference": "2",
            "completeType": 1,
            "content":"马克思主义好",
            "isAnswered": 1
        },
        //多选题
         {
            "ssoUserTestItem.id": 119,
            "answertime":60000,   //一道题的做题时间，单位:毫秒
	    	"blankInx": 1,
	    	"reference": "1,2,3,4",
	    	"completeType": 1,
	    	"content":"马克思主义好$%@恩格斯主义好$%@列宁主义好$%@毛泽东主义好",
            "isAnswered": 1
         }
    	]
    */
	public void SubmitAutoPaper() {
		/**
		 * 方法功能说明:
		 * 1、对传入参数进行校验(待后续完善)
		 * 2、更新SsoUserTestItem信息
		 * 3、更新SsoUserTest信息
		 * 4、返回客户端需要的结果信息（总题数、正确的题数、每题是否正确、每题的内容及解析（用户选择的是什么、正确答案是什么）、按一级知识点的试题正确题数增加值、本学科学习进度值变化）
		 */
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
			//验证并获取Subject学科信息
			Integer subjectID = this.getIntValue(request.getParameter("subject.id"));
			Subject subject = this.getSubjectService().getSubjectById(subjectID);
			
			Long ssoUserTestID = this.getLongValue(request.getParameter("ssoUserTest.id"));
			Integer duration = this.getIntValue(request.getParameter("duration"));
			
			SsoUserTest ssoUserTest = this.getSsoUserTestService().getUserTestById(ssoUserTestID);
			
			UserSubject userSubject = this.getUserSubjectService().getByKey(currentUser.getId(), subject.getId());
					
			if (subject != null && ssoUserTest != null && userSubject != null
					&& currentUser.getId().equals(ssoUserTest.getSsoUser().getId())){
				ssoUserTest.setDuration(duration);
				ssoUserTest.setStatus(Dictionary.USER_TEST_STATUS_COMPLETE);
				
				/**
				 * 应首先对answers参数进行校验（待后续完善）
				 * 此处略去100行代码
				 */
				@SuppressWarnings("rawtypes")
				List<Map> answers = JSONUtils.json2list(request.getParameter("answers"), Map.class);
				
				/**
				 * 保存用户答题信息
				 */
				List<SsoUserTestItem> ssoUserTestItemList = this.getSsoUserTestService().saveSsoUserAutoTest(ssoUserTest, answers);
				
				/**
				 * 本次做题知识点掌握情况变化（多级）,每个试题只计算最后一次做题是否正确（暂以正确题数增减为主）
				 */
				// knowledge.id
				// knowledge.scode
				// knowledge.parent
				// knowledge.totalItemCount
				// knowledge.userTestItemCount
				// knowledge.userTestRightItemCount
				// knowledge.userTestRightItemIncreaseCount
				List<Map<String, Object>> knowledgeTestItemChangeList1 = this.getSsoUserTestService().
						calculateKnowledgeTestChange(currentUser, ssoUserTestItemList, subject, split);
				
				
				
				/**
				 * 计算用户该学科刷题次数（量），计算该学科题总数，该学科用户做正确次总数，计算该学科最新总正确率（学科用户做正确次总数/用户该学科刷题次数（量））
				 */
				//该用户该学科刷题次数（量）
				Integer userTotalTestItemCount = this.getSsoUserTestItemService().calculateUserTotalTestItemCount(currentUser, subject,
						Dictionary.ITEM_ANSWER_TYPESTANDARD);
				
				//该学科用户做正确次总数
				Integer userTotalRightItemCount = this.getSsoUserTestItemService().calculateUserTotalRightItemCount(currentUser, subject,
						Dictionary.ITEM_ANSWER_TYPESTANDARD);
				
				//该学科用户做题总正确率,返回百分比的数，此处乘100
				Double userBrushItemRightRate = (userTotalTestItemCount==0) ? 0 : (double) userTotalRightItemCount / userTotalTestItemCount * 100;
				
				
				/**
				 * 学习进度(该学科用户正确题总数（每个试题只计算最后一次做题是否正确）/该学科总题数)
				 */
				//该学科题总数
				Map<String, Object> totalItemCountSearchMap = new HashMap<>();
				totalItemCountSearchMap.put("subject.id", subjectID);
				totalItemCountSearchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);
				Integer totalItemCount = this.getItemService().searchItemCount(totalItemCountSearchMap);
				List<Map<String, Object>> totalItemCountList = this.getItemService().searchItemCountGroupByItemType(totalItemCountSearchMap);
				
				//该学科用户正确题总数（每个试题只计算最后一次做题是否正确）
				List<Map<String,Object>> userLastTotalRightItemCount = this.getSsoUserTestItemService().calculateUserLastTotalRightItemCountGroupByItemType(currentUser, subject);
				//该学科题型关系列表
				Map<String, Object> subjectItemTypeSearchMap = new HashMap<>();
				subjectItemTypeSearchMap.put("subject.id", subjectID);
				List<SubjectItemType> subjectItemTypeList = this.getSubjectItemTypeService().searchSubjectItemType(subjectItemTypeSearchMap);
				
				
				Double userPrepareTestProgress = 0.0;
				for(Map<String,Object> utrc : userLastTotalRightItemCount) {
					for (Map<String, Object> tic : totalItemCountList){
						if (((Integer)utrc.get("id")).equals((Integer) tic.get("id"))){
							Double utrcn = ((BigInteger) utrc.get("count")).doubleValue();
							Double ticn =  ((BigInteger) tic.get("count")).doubleValue();
							for (SubjectItemType sit : subjectItemTypeList){
								if (sit.getItemType().getId().equals((Integer)utrc.get("id"))){
									userPrepareTestProgress = userPrepareTestProgress + utrcn / ticn * sit.getProportion();
								}
							}
						}
					}
				}
				
				/**
				 * 备考进度用户排名，和排名百分比
				 */
				//还在关注考试科目的，最近一段时间有访问记录的用户，备考进度排名
				Map<String, Object> prepareTestUserCountSearchMap = new HashMap<>();
				prepareTestUserCountSearchMap.put("subject.id", subject.getId());
				prepareTestUserCountSearchMap.put("status", Dictionary.USER_SUBJECT_STATUS_NOMAL);
				
				Integer prepareTestUserCount = this.getUserSubjectService().getUserSubjectCount(prepareTestUserCountSearchMap);
				Integer userSubjectTestProgressRanking = this.getUserSubjectService().getUserSubjectTestProgressRanking(currentUser.getId(), subject.getId());
				
				Double userSubjectTestProgressRankingRate = (prepareTestUserCount==0) ? 0 : (double) (userSubjectTestProgressRanking) / prepareTestUserCount * 100;
				
				/**
				 * 更新user_subject表的相关信息
				 */
				userSubject.setBrushItemCount(userTotalTestItemCount);
				userSubject.setCorrectRate(userBrushItemRightRate);
				userSubject.setProgress(userPrepareTestProgress);
				userSubject.setRankingRate(userSubjectTestProgressRankingRate);                   //未完，待续
				userSubject = this.getUserSubjectService().updateUserSubject(userSubject);
				
				/**
				 * 组织接口返回数据格式
				 */
				//将列表数据组织为
				List<Map<String, Object>> knowledgeTestItemChangeList2 = new ArrayList<>();
				
				if(knowledgeTestItemChangeList1 != null && knowledgeTestItemChangeList1.size() > 0)
				{
					for(Map<String,Object> cur : knowledgeTestItemChangeList1)
					{
						//从1级知识点开始做递归
						if (Short.valueOf(cur.get("level").toString()) == 1) {
							Map<String,Object> curTree = this.refactorTree(cur, knowledgeTestItemChangeList1, split);
							knowledgeTestItemChangeList2.add(curTree);
						}
					}
				}

				//组织接口返回数据
				Map<String, Object> dataResult = new HashMap<>();
				dataResult.put("brushItemCount", userTotalTestItemCount);     //刷题量
				dataResult.put("brushRightItemCount", userTotalRightItemCount);  //刷题正确数
				dataResult.put("correctRate", userBrushItemRightRate);              //刷题正确率
				dataResult.put("totalItemCount", totalItemCount);                     //学科总题量
				dataResult.put("lastRightItemCount", userLastTotalRightItemCount); //已掌握题量（最后一次回答正确）
				dataResult.put("progress", userPrepareTestProgress);                       //备考进度
				dataResult.put("nextTestdayCount", userSubject.getNextTestdayCount());   //距考试天数
				
				dataResult.put("testUserCount", prepareTestUserCount);            //本次考试备考人数
				dataResult.put("ranking", userSubjectTestProgressRanking);  //用户备考进度排名
				dataResult.put("rankingRate", userSubjectTestProgressRankingRate);                //用户备考进度排名比率
				
				dataResult.put("changeKnowledges", knowledgeTestItemChangeList2);            //有变化的知识点
				
				result.init(SUCCESS_STATUS, null, dataResult);
				
			}
			else {
				result.init(FAIL_STATUS, "该用户没有考试信息", null);
			}
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 递归排序;
	 */
	protected Map<String,Object> refactorTree(Map<String,Object> cur, List<Map<String,Object>> curList, String split)
	{
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		Boolean hasChild = false;
		short curLevel = Short.valueOf(cur.get("level").toString());
		String curScode = (String) cur.get("scode");
		for(Map<String,Object> child : curList)
		{
			short childLevel = Short.valueOf(child.get("level").toString());
			String childScode = (String) child.get("scode");
			
			if(childLevel == curLevel + 1 &&  childScode.startsWith(curScode))
			{
				hasChild = true;
				childList.add(this.refactorTree(child, curList, split));
			}
		}
		//如果有孩子，则加入孩子;
		cur.put("hasChild", hasChild);
		if(hasChild)
		{
			cur.put("data",childList);
		}
		return cur;
	}
	
	/**
	 * 保存用户点击暂停的自动化问卷
	 */
	public void SaveAutoPaper() {
		
	}
	
	/**
	 * 获取学科下某题型的所有试题，一般情况下应该是主观题
	 * Ver1.2版本已经考虑了组合题情况
	 */
	@SuppressWarnings("unchecked")
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "itemType.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void ItemList() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		String version = request.getParameter("version");
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		int uid = this.getIntValue(request.getParameter("user.id"));
		Integer subjectId = this.getIntValue(request.getParameter("subject.id"));
		Integer itemTypeId = this.getIntValue(request.getParameter("itemType.id"));
		
		UserSubject userSubject = this.getUserSubjectService().getByKey(uid, subjectId);
		SubjectItemType subjectItemType = this.getSubjectItemTypeService().getByKey(subjectId, itemTypeId);
		
		if (currentUser == null || !currentUser.getId().equals(userSubject.getSsoUser().getId())) {
			currentUser = userSubject.getSsoUser();
		}
		
		if (userSubject == null || subjectItemType == null) {
			result.init(FAIL_STATUS, "无用户关注学科信息或无学科题型信息！", null);
		} 
		else {
			Subject subject = userSubject.getSubject();
			ItemType itemType = subjectItemType.getItemType();
			
			Map<String,Object> searchMap = new HashMap<>();
			searchMap.put("itemType.id", itemType.getId());
			searchMap.put("subject.id", subject.getId());
			searchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);
			
			//按level进行排序，后面的循环可以采用一些逻辑，如默认为level=2的子题在循环的最后，level=1的已经可以找到了
			List<Map<String,Object>> itemMapList = this.getItemService().searchItems(currentUser, subject, itemType);

			LinkedHashMap<Integer, Map<String, Object>> dataLinkedMap = new LinkedHashMap<>();
			
			/**
			 * 先进行level=1的试题
			 */
			for (Map<String, Object> item : itemMapList) {
				if ((Integer) item.get("level") == 1 && !(Boolean)item.get("isgroup")) {
					Map<String, Object> itemMap = new HashMap<>();
					itemMap.put("id", item.get("id"));
					itemMap.put("item" + split + "id", item.get("id"));
					itemMap.put("item" + split + "level", item.get("level"));
					itemMap.put("itemType" + split + "id", item.get("type"));
					itemMap.put("itemType" + split + "name", item.get("typename"));
					itemMap.put("modelType", item.get("modeltype"));
					itemMap.put("isgroup", item.get("isgroup"));
					itemMap.put("blankInx", item.get("inx"));
					itemMap.put("diffcultyLevel", item.get("diffcultyLevel"));
					itemMap.put("diffcultyLevelCN", Utlity.getDiffcultyLevelType(Short.valueOf(item.get("diffcultyLevel").toString())));
					itemMap.put("defaultScore", item.get("defaultScore"));
					itemMap.put("sourceType", item.get("sourceType"));
					itemMap.put("source", item.get("source"));
					itemMap.put("analysis", item.get("analysis"));
					//主观题未浏览时，返回为-1，0为浏览单为标记，1为浏览已标记
					itemMap.put("completeType", item.get("completeType") == null ? -1 : item.get("completeType"));
					if (item.get("content") != null){
						itemMap.put("data", JSONUtils.json2map(item.get("content").toString()));
					}else{
						itemMap.put("data", item.get("content"));
					}
					dataLinkedMap.put((Integer) item.get("id"), itemMap);
				}
				//组合题的材料
				else if ((Integer) item.get("level") == 1 && (Boolean)item.get("isgroup")){
					Map<String, Object> itemMap = new HashMap<>();
					itemMap.put("id", item.get("id"));
					itemMap.put("item" + split + "id", item.get("id"));
					itemMap.put("item" + split + "level", item.get("level"));
					itemMap.put("itemType" + split + "id", item.get("type"));
					itemMap.put("itemType" + split + "name", item.get("typename"));
					itemMap.put("modelType", item.get("modeltype"));
					itemMap.put("isgroup", item.get("isgroup"));
					itemMap.put("blankInx", item.get("inx"));
					itemMap.put("diffcultyLevel", item.get("diffcultyLevel"));
					itemMap.put("diffcultyLevelCN", Utlity.getDiffcultyLevelType(Short.valueOf(item.get("diffcultyLevel").toString())));
					itemMap.put("defaultScore", item.get("defaultScore"));
					itemMap.put("sourceType", item.get("sourceType"));
					itemMap.put("source", item.get("source"));
					if(version.equals("1.2.0")){
						Map<String,Object> map = (Map<String,Object>)JSONUtils.json2map(item.get("content").toString());
						itemMap.put("data", map.get("stem").toString());
					}else{
						itemMap.put("data", JSONUtils.json2map(item.get("content").toString()));
					}
					itemMap.put("children", new ArrayList<Map<String, Object>>());
					
					dataLinkedMap.put((Integer) item.get("id"), itemMap);
				}
				//组合题中level=2的试题
				else if ((Integer) item.get("level") == 2 && (Boolean)item.get("isgroup")) {
					/**
					 * 因为取得itemlist是有顺序的，level=2的都在最后，所以dataLinkedMap可以get到试题的parentitemMap对象。
					 */
					Map<String, Object> parentItemMap = dataLinkedMap.get(item.get("parent"));
					if (parentItemMap != null){
						List<Map<String, Object>> children = (List<Map<String, Object>>) parentItemMap.get("children");
						Map<String, Object> itemMap = new HashMap<>();
						itemMap.put("id", item.get("id"));
						itemMap.put("item" + split + "id", item.get("id"));
						itemMap.put("item" + split + "level", item.get("level"));
						itemMap.put("itemType" + split + "id", item.get("type"));
						itemMap.put("itemType" + split + "name", item.get("typename"));
						itemMap.put("modelType", item.get("modeltype"));
						itemMap.put("isgroup", item.get("isgroup"));
						itemMap.put("blankInx", item.get("inx"));
						itemMap.put("defaultScore", item.get("defaultScore"));
						itemMap.put("analysis", item.get("analysis"));
						itemMap.put("completeType", item.get("completeType") == null ? -1 : item.get("completeType"));
						itemMap.put("data", JSONUtils.json2map(item.get("content").toString()));
						
						children.add(itemMap);
						
					}
				}
				
			}
			
			List<Map<String, Object>> dataList = new ArrayList<>();
			for (Integer id : dataLinkedMap.keySet()) {
				dataList.add(dataLinkedMap.get(id));
			}
			
			result.init(SUCCESS_STATUS, null, dataList);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	
	/**
	 * 标记试题为已掌握/或未掌握，这里主要指主观题
	 */
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "item.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "flag", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void MasterItem(){
		ActionResult result = new ActionResult();
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		Integer uid = this.getIntValue(request.getParameter("user.id"));
		
		Integer itemId = this.getIntValue(request.getParameter("item.id"));
		
		Short masterFlag = Short.valueOf(request.getParameter("flag"));
		masterFlag = masterFlag > 0 ? (short)1 : (short)0;
		
		Item item = this.getItemService().getItemById(itemId);
		
		Integer defaultBlankID = 1;
		
		if (currentUser == null) {
			currentUser = this.getSsoUserService().getById(uid);
		}
		
		if (currentUser == null || item == null) {
			result.init(FAIL_STATUS, "无此用户或试题！", null);
		} 
		else if (item.getLevel() == 1 && item.getIsGroup()){
			result.init(FAIL_STATUS, "组合题中不可以标注一级试题（材料）信息！", null);
		}
		else {
			Subject subject = item.getSubject();
			UserSubject userSubject = this.getUserSubjectService().getByKey(uid, subject.getId());
			
			if (userSubject == null) {
				result.init(FAIL_STATUS, "该用户没有关注备考此学科!", null);
			}
			else {
				SsoUserTestItemCount ssoUserTestItemCount = this.getSsoUserTestItemCountService().getByKey(currentUser.getId(), itemId, defaultBlankID);
				Timestamp now = new Timestamp(System.currentTimeMillis());
				if (ssoUserTestItemCount == null ){
					//第一次标注
					ssoUserTestItemCount = new SsoUserTestItemCount();
					ssoUserTestItemCount.setSsoUser(currentUser);
					ssoUserTestItemCount.setItem(item);
					ssoUserTestItemCount.setBlankInx(defaultBlankID);
					ssoUserTestItemCount.setTestItemCount(0);
					ssoUserTestItemCount.setTestItemCorrectCount(0);
					ssoUserTestItemCount.setTestItemWrongCount(0);
					ssoUserTestItemCount.setLastTestItem(null);
					ssoUserTestItemCount.setLastTestItemAnswerTime(now);
					ssoUserTestItemCount.setLastTestItemCompleteType(masterFlag);
					
					ssoUserTestItemCount.setIsWrongbookItem((short) 0);
					ssoUserTestItemCount.setIsWrongbookItemTested((short) 0);
					ssoUserTestItemCount.setIsWrongbookItemMastered((short) 0);
					
					this.getSsoUserTestItemCountService().save(ssoUserTestItemCount);
					
				}
				else {
					ssoUserTestItemCount.setLastTestItemAnswerTime(now);
					ssoUserTestItemCount.setLastTestItemCompleteType(masterFlag);
					
					this.getSsoUserTestItemCountService().update(ssoUserTestItemCount);
				}
				
				
				/**
				 * 学习进度(该学科用户正确题总数（每个试题只计算最后一次做题是否正确）/该学科总题数)
				 */
				//该学科主观题总数
				Map<String, Object> totalItemCountSearchMap = new HashMap<>();
				totalItemCountSearchMap.put("subject.id", subject.getId());
				totalItemCountSearchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);
				Integer totalItemCount = this.getItemService().searchItemCount(totalItemCountSearchMap);
				List<Map<String, Object>> totalItemCountList = this.getItemService().searchItemCountGroupByItemType(totalItemCountSearchMap);
				
				//该学科用户正确题总数（每个试题只计算最后一次做题是否正确）
				List<Map<String,Object>> userLastTotalRightItemCount = this.getSsoUserTestItemService().calculateUserLastTotalRightItemCountGroupByItemType(currentUser, subject);
				//该学科题型关系列表
				Map<String, Object> subjectItemTypeSearchMap = new HashMap<>();
				subjectItemTypeSearchMap.put("subject.id", subject.getId());
				List<SubjectItemType> subjectItemTypeList = this.getSubjectItemTypeService().searchSubjectItemType(subjectItemTypeSearchMap);
				
				
				Double userPrepareTestProgress = 0.0;
				for(Map<String,Object> utrc : userLastTotalRightItemCount) {
					for (Map<String, Object> tic : totalItemCountList){
						if (((Integer)utrc.get("id")).equals((Integer) tic.get("id"))){
							Double utrcn = ((BigInteger) utrc.get("count")).doubleValue();
							Double ticn =  ((BigInteger) tic.get("count")).doubleValue();
							for (SubjectItemType sit : subjectItemTypeList){
								if (sit.getItemType().getId().equals((Integer)utrc.get("id"))){
									userPrepareTestProgress = userPrepareTestProgress + utrcn / ticn * sit.getProportion();
								}
							}
						}
					}
				}
				

				/**
				 * 备考进度用户排名，和排名百分比
				 */
				//还在关注考试科目的，最近一段时间有访问记录的用户，备考进度排名
				Map<String, Object> prepareTestUserCountSearchMap = new HashMap<>();
				prepareTestUserCountSearchMap.put("subject.id", subject.getId());
				prepareTestUserCountSearchMap.put("status", Dictionary.USER_SUBJECT_STATUS_NOMAL);
				
				Integer prepareTestUserCount = this.getUserSubjectService().getUserSubjectCount(prepareTestUserCountSearchMap);
				Integer userSubjectTestProgressRanking = this.getUserSubjectService().getUserSubjectTestProgressRanking(currentUser.getId(), subject.getId());
				
				Double userSubjectTestProgressRankingRate = (prepareTestUserCount==0) ? 0 : (double) (userSubjectTestProgressRanking) / prepareTestUserCount * 100;
				
				/**
				 * 更新user_subject表的相关信息
				 */
				userSubject.setProgress(userPrepareTestProgress);
				userSubject.setRankingRate(userSubjectTestProgressRankingRate);                   //未完，待续
				userSubject = this.getUserSubjectService().updateUserSubject(userSubject);

				//组织接口返回数据
				Map<String, Object> dataResult = new HashMap<>();
				dataResult.put("totalItemCount", totalItemCount);                     //学科总题量
				dataResult.put("lastRightItemCount", userLastTotalRightItemCount); //已掌握题量（最后一次回答正确）
				dataResult.put("progress", userPrepareTestProgress);                       //备考进度
				dataResult.put("nextTestdayCount", userSubject.getNextTestdayCount());   //距考试天数
				
				dataResult.put("testUserCount", prepareTestUserCount);            //本次考试备考人数
				dataResult.put("ranking", userSubjectTestProgressRanking);  //用户备考进度排名
				dataResult.put("rankingRate", userSubjectTestProgressRankingRate);                //用户备考进度排名比率
				
				
				result.init(SUCCESS_STATUS, null, dataResult);
			}
			
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}
	
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void SelectPaperType(){
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		Integer subjectId = this.getIntValue(request.getParameter("subject.id"));
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("subject" + split + "id",subjectId);
		
		List<Map<String, Object>> paperTypeList = this.getPaperService().searchPaperCountListByType(searchMap);
		
		result.init(SUCCESS_STATUS, null, paperTypeList);
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void PaperList(){
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
		} else {
			Integer subjectId = this.getIntValue(request.getParameter("subject.id"));
			Short type = Short.valueOf(request.getParameter("type"));
			
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("subject.id", subjectId);
			searchMap.put("type", type);
			List<Paper> paperList = this.paperService.searchPaper(searchMap, null, -1, -1);
			
			searchMap.put("user.id",currentUser.getId());
			Integer userSubjectPay = this.getSsoUserPayService().searchSsoUserPaySubject(searchMap);
			
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if(userSubjectPay > 0){
				for(Paper p : paperList){
					Map<String,Object> data= new HashMap<String,Object>();
					data.put("id", p.getId());
					data.put("type", p.getType());
					data.put("name", p.getName());
					data.put("year", p.getYear());
					data.put("isFree", p.getIsFree() == null? true : p.getIsFree());
					data.put("price", p.getPrice() == null? 0 : p.getPrice());
					data.put("totalScore", p.getTotalScore());
					data.put("useable", true);
					
					Float aveScroce = this.paperService.getPaperAverage(p.getId());
					data.put("aveScore", aveScroce);
					
					Map<String, Object> countSearchMap = new HashMap<>();
					countSearchMap.put("paper.id", p.getId());
					Integer testCount = this.ssoUserTestService.getUserTestCount(countSearchMap);
					data.put("testCount", testCount);
					
					dataList.add(data);
				}
			}else{
				List<SsoUserPay> supList = this.getSsoUserPayService().searchSsoUserPayPaper(searchMap);
				
				for(Paper p: paperList){
					Map<String,Object> data= new HashMap<String,Object>();
					data.put("id", p.getId());
					data.put("type", p.getType());
					data.put("name", p.getName());
					data.put("year", p.getYear());
					data.put("isFree", p.getIsFree() == null? true : p.getIsFree());
					data.put("price", p.getPrice() == null? 0 : p.getPrice());
					data.put("totalScore", p.getTotalScore());
					if(p.getIsFree()){
						data.put("useable", true);
					}else{
						Boolean flag = false;
						for(SsoUserPay sup : supList){
							if((int)sup.getPaper().getId() == (int)p.getId()){
								flag = true;
								break;
							}
						}
						if(flag){
							data.put("useable", true);
						}else{
							data.put("useable", false);
						}
					}
					
					Float aveScroce = this.paperService.getPaperAverage(p.getId());
					data.put("aveScore", aveScroce);
					
					Map<String, Object> countSearchMap = new HashMap<>();
					countSearchMap.put("paper.id", p.getId());
					Integer testCount = this.ssoUserTestService.getUserTestCount(countSearchMap);
					data.put("testCount", testCount);
					
					dataList.add(data);
				}
			}
			result.init(SUCCESS_STATUS, null, dataList);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	
	@ActionParam(key = "paper.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void LoadPaper() {
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
		}else{
			int id = this.getIntValue(request.getParameter("paper.id"));
			Paper paper = this.getPaperService().getPaperById(id);
	
			if (paper == null) {
				result.init(FAIL_STATUS, "无此试卷！", null);
			} else {
				Map<String,Object> paperData = this.getSsoUserTestService().createPaperTest(currentUser , paper , split);
				result.init(SUCCESS_STATUS, null, paperData);
			}
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "ssoUserTest.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "duration", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "score", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "answers", type = ValueType.STRING, nullable = false, emptyable = false)
	public void SubmitPaper() {
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
			Integer subjectID = this.getIntValue(request.getParameter("subject.id"));
			Subject subject = this.getSubjectService().getSubjectById(subjectID);
			
			Long ssoUserTestID = this.getLongValue(request.getParameter("ssoUserTest.id"));
			Integer duration = this.getIntValue(request.getParameter("duration"));
			Double score = Double.valueOf(request.getParameter("score"));
			
			SsoUserTest ssoUserTest = this.getSsoUserTestService().getUserTestById(ssoUserTestID);
			
			UserSubject userSubject = this.getUserSubjectService().getByKey(currentUser.getId(), subject.getId());
					
			if (subject != null && ssoUserTest != null && userSubject != null
					&& currentUser.getId().equals(ssoUserTest.getSsoUser().getId())){
				ssoUserTest.setDuration(duration);
				ssoUserTest.setStatus(Dictionary.USER_TEST_STATUS_COMPLETE);
				ssoUserTest.setScore(score);
				
				@SuppressWarnings("rawtypes")
				List<Map> answers = JSONUtils.json2list(request.getParameter("answers"), Map.class);
				List<SsoUserTestItem> ssoUserTestItemList = this.getSsoUserTestService().saveSsoUserTest(ssoUserTest, answers);
				
				Integer userTotalTestItemCount = this.getSsoUserTestItemService().calculateUserTotalTestItemCount(currentUser, subject,
						Dictionary.ITEM_ANSWER_TYPESTANDARD);			
				Integer userTotalRightItemCount = this.getSsoUserTestItemService().calculateUserTotalRightItemCount(currentUser, subject,
						Dictionary.ITEM_ANSWER_TYPESTANDARD);		
				Double userBrushItemRightRate = (userTotalTestItemCount==0) ? 0 : (double) userTotalRightItemCount / userTotalTestItemCount * 100;
				
				Map<String, Object> totalItemCountSearchMap = new HashMap<>();
				totalItemCountSearchMap.put("subject.id", subjectID);
				totalItemCountSearchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);
				Integer totalItemCount = this.getItemService().searchItemCount(totalItemCountSearchMap);
				List<Map<String, Object>> totalItemCountList = this.getItemService().searchItemCountGroupByItemType(totalItemCountSearchMap);
				
				List<Map<String,Object>> userLastTotalRightItemCount = this.getSsoUserTestItemService().calculateUserLastTotalRightItemCountGroupByItemType(currentUser, subject);
				Map<String, Object> subjectItemTypeSearchMap = new HashMap<>();
				subjectItemTypeSearchMap.put("subject.id", subjectID);
				List<SubjectItemType> subjectItemTypeList = this.getSubjectItemTypeService().searchSubjectItemType(subjectItemTypeSearchMap);
				
				Double userPrepareTestProgress = 0.0;
				for(Map<String,Object> utrc : userLastTotalRightItemCount) {
					for (Map<String, Object> tic : totalItemCountList){
						if (((Integer)utrc.get("id")).equals((Integer) tic.get("id"))){
							Double utrcn = ((BigInteger) utrc.get("count")).doubleValue();
							Double ticn =  ((BigInteger) tic.get("count")).doubleValue();
							for (SubjectItemType sit : subjectItemTypeList){
								if (sit.getItemType().getId().equals((Integer)utrc.get("id"))){
									userPrepareTestProgress = userPrepareTestProgress + utrcn / ticn * sit.getProportion();
								}
							}
						}
					}
				}
				
				Map<String, Object> prepareTestUserCountSearchMap = new HashMap<>();
				prepareTestUserCountSearchMap.put("subject.id", subject.getId());
				prepareTestUserCountSearchMap.put("status", Dictionary.USER_SUBJECT_STATUS_NOMAL);
				
				Integer prepareTestUserCount = this.getUserSubjectService().getUserSubjectCount(prepareTestUserCountSearchMap);
				Integer userSubjectTestProgressRanking = this.getUserSubjectService().getUserSubjectTestProgressRanking(currentUser.getId(), subject.getId());
				
				Double userSubjectTestProgressRankingRate = (prepareTestUserCount==0) ? 0 : (double) (userSubjectTestProgressRanking) / prepareTestUserCount * 100;
				
				userSubject.setBrushItemCount(userTotalTestItemCount);
				userSubject.setCorrectRate(userBrushItemRightRate);
				userSubject.setProgress(userPrepareTestProgress);
				userSubject.setRankingRate(userSubjectTestProgressRankingRate);
				userSubject = this.getUserSubjectService().updateUserSubject(userSubject);
				
				List<Map<String, Object>> knowledgeTestItemChangeList2 = new ArrayList<>();
				if(ssoUserTest.getPaper().getIsFree()){
					List<Map<String, Object>> knowledgeTestItemChangeList1 = this.getSsoUserTestService().
							calculateKnowledgeTestChange(currentUser, ssoUserTestItemList, subject, split);
					if(knowledgeTestItemChangeList1 != null && knowledgeTestItemChangeList1.size() > 0)
					{
						for(Map<String,Object> cur : knowledgeTestItemChangeList1)
						{
							if (Short.valueOf(cur.get("level").toString()) == 1) {
								Map<String,Object> curTree = this.refactorTree(cur, knowledgeTestItemChangeList1, split);
								knowledgeTestItemChangeList2.add(curTree);
							}
						}
					}
				}
				Map<String, Object> dataResult = new HashMap<>();
				dataResult.put("brushItemCount", userTotalTestItemCount);
				dataResult.put("brushRightItemCount", userTotalRightItemCount);
				dataResult.put("correctRate", userBrushItemRightRate);
				dataResult.put("totalItemCount", totalItemCount);
				dataResult.put("lastRightItemCount", userLastTotalRightItemCount);
				dataResult.put("progress", userPrepareTestProgress);
				dataResult.put("nextTestdayCount", userSubject.getNextTestdayCount());	
				dataResult.put("testUserCount", prepareTestUserCount);
				dataResult.put("ranking", userSubjectTestProgressRanking);
				dataResult.put("rankingRate", userSubjectTestProgressRankingRate);
				dataResult.put("changeKnowledges", knowledgeTestItemChangeList2);
				
				result.init(SUCCESS_STATUS, null, dataResult);
			}
			else {
				result.init(FAIL_STATUS, "该用户没有考试信息", null);
			}
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
}
