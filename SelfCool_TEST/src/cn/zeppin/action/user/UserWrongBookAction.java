package cn.zeppin.action.user;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.SsoUserTestItemCount;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.entity.UserSubject;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.ISsoUserService;
import cn.zeppin.service.api.ISsoUserTestItemCountService;
import cn.zeppin.service.api.ISsoUserTestItemService;
import cn.zeppin.service.api.ISsoUserTestService;
import cn.zeppin.service.api.ISubjectItemTypeService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.IUserSubjectService;
import cn.zeppin.service.api.IUserWrongBookService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

public class UserWrongBookAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2810535664207302106L;
	
	private ISsoUserService ssoUserService;
	private ISubjectService subjectService;
	private IUserWrongBookService userWrongBookService;
	private IItemService itemService;
	private ISubjectItemTypeService subjectItemTypeService;
	private ISsoUserTestService ssoUserTestService;
	private ISsoUserTestItemService ssoUserTestItemService;
	private ISsoUserTestItemCountService ssoUserTestItemCountService;
	private IUserSubjectService userSubjectService;
	
	
	
	public ISsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(ISsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}
	
	
	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	public IUserWrongBookService getUserWrongBookService() {
		return userWrongBookService;
	}

	public void setUserWrongBookService(IUserWrongBookService userWrongBookService) {
		this.userWrongBookService = userWrongBookService;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	public ISubjectItemTypeService getSubjectItemTypeService() {
		return subjectItemTypeService;
	}

	public void setSubjectItemTypeService(ISubjectItemTypeService subjectItemTypeService) {
		this.subjectItemTypeService = subjectItemTypeService;
	}

	public ISsoUserTestService getSsoUserTestService() {
		return ssoUserTestService;
	}

	public void setSsoUserTestService(ISsoUserTestService ssoUserTestService) {
		this.ssoUserTestService = ssoUserTestService;
	}

	public ISsoUserTestItemService getSsoUserTestItemService() {
		return ssoUserTestItemService;
	}

	public void setSsoUserTestItemService(ISsoUserTestItemService ssoUserTestItemService) {
		this.ssoUserTestItemService = ssoUserTestItemService;
	}

	public ISsoUserTestItemCountService getSsoUserTestItemCountService() {
		return ssoUserTestItemCountService;
	}

	public void setSsoUserTestItemCountService(
			ISsoUserTestItemCountService ssoUserTestItemCountService) {
		this.ssoUserTestItemCountService = ssoUserTestItemCountService;
	}

	public IUserSubjectService getUserSubjectService() {
		return userSubjectService;
	}

	public void setUserSubjectService(IUserSubjectService userSubjectService) {
		this.userSubjectService = userSubjectService;
	}

	/**
	 * 加载错题本中的所有题
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void ItemList() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;
		
		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if (currentUser == null) {
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);
		}
		
		Integer subjectID = this.getIntValue(request.getParameter("subject.id"));
		Subject subject = this.getSubjectService().getSubjectById(subjectID);
		
		if (currentUser == null || subject == null) {
			result.init(FAIL_STATUS, "无此用户或学科！", null);
		} 
		else {
			this.session.setAttribute("user", currentUser);
			//按照错题本试题创建时间排序
			List<Map<String, Object>> wrongBookItemList = this.getUserWrongBookService().searchAllWrongItem(currentUser, subject);
			List<Map<String, Object>> dataList = new ArrayList<>();
			Short inx = 1; //生成
			for (Map<String, Object> itemCount : wrongBookItemList) {
				Map<String, Object> data = new HashMap<>();
				data.put("id", itemCount.get("id"));
				data.put("item" + split + "id", itemCount.get("id"));
				data.put("item" + split + "level", itemCount.get("level"));
				data.put("itemType" + split + "id", itemCount.get("type"));
				data.put("itemType" + split + "name", itemCount.get("typename"));
				data.put("modelType", itemCount.get("modeltype"));
				data.put("isgroup", itemCount.get("isgroup"));
				data.put("inx", inx); //题序号
				data.put("blankInx", itemCount.get("inx"));   //题上的空序号
				data.put("diffcultyLevel", itemCount.get("diffcultyLevel"));
				String diffcultyLevelCN = Utlity.getDiffcultyLevelType(Short.valueOf(itemCount.get("diffcultyLevel").toString()));
				data.put("diffcultyLevelCN", diffcultyLevelCN);
				data.put("defaultScore", itemCount.get("defaultScore"));
				data.put("sourceType", itemCount.get("sourceType"));
				data.put("source", itemCount.get("source"));
				data.put("analysis", itemCount.get("analysis"));
				data.put("material", itemCount.get("material"));
				data.put("data", JSONUtils.json2map(itemCount.get("content").toString()));
				data.put("testItemCount", itemCount.get("test_item_count"));
				data.put("testItemCorrectCount", itemCount.get("test_item_correct_count"));
				data.put("testItemWrongCount", itemCount.get("test_item_wrong_count"));
				data.put("isWrongbookItemTested", itemCount.get("is_wrongbook_item_tested"));
				data.put("lastTestCompleteType", itemCount.get("last_test_item_complete_type"));
				data.put("isWrongbookItemMastered", itemCount.get("is_wrongbook_item_mastered"));
				
				inx++;
				dataList.add(data);
			}
			
			result.init(SUCCESS_STATUS, "数据获成功", dataList);
			
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 提交错题本的做题记录
	 * 
	 * 
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
	 */
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "item.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "item.blankInx", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "isAnswered", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "completeType", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "answertime", type = ValueType.NUMBER)
	@ActionParam(key = "reference", type = ValueType.STRING)
	
	@ActionParam(key = "content", type = ValueType.STRING)
	public void SubmitItem() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if (currentUser == null) {
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);

		}
		
//		Integer subjectID = this.getIntValue(request.getParameter("subject.id"));
//		Subject subject = this.getSubjectService().getSubjectById(subjectID);

		
		int itemId = this.getIntValue(request.getParameter("item.id"));
		Item item = this.getItemService().getItemById(itemId);
		
		
		if (currentUser == null || item == null) {
			result.init(FAIL_STATUS, "无此用户或试题！", null);
		} 
		else {
			this.session.setAttribute("user", currentUser);
			Subject subject = item.getSubject();
			Map<String,Object> itemMap = new HashMap<>();
			itemMap.put("id", this.getIntValue(request.getParameter("item.id")));
			itemMap.put("blankInx", this.getIntValue(request.getParameter("item.blankInx")));
			itemMap.put("isAnswered", Short.valueOf(request.getParameter("isAnswered")));
			itemMap.put("completeType", Short.valueOf(request.getParameter("completeType")));
			itemMap.put("answertime", this.getIntValue(request.getParameter("answertime")));
			itemMap.put("reference", request.getParameter("reference"));
			itemMap.put("content", request.getParameter("content"));
			
			SsoUserTestItem ssoUserTestItem = this.getUserWrongBookService().saveWrongbookTest(currentUser, item, itemMap);
			
			List<SsoUserTestItem> ssoUserTestItemList = new ArrayList<>();
			ssoUserTestItemList.add(ssoUserTestItem);
			
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
					if ((Integer)utrc.get("id") == (Integer) tic.get("id")){
						Double utrcn = ((BigInteger) utrc.get("count")).doubleValue();
						Double ticn =  ((BigInteger) tic.get("count")).doubleValue();
						for (SubjectItemType sit : subjectItemTypeList){
							if (sit.getItemType().getId() == (Integer)utrc.get("id")){
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
			
			Double userSubjectTestProgressRankingRate = (prepareTestUserCount==0) ? 0 : (double) (userSubjectTestProgressRanking-1) / prepareTestUserCount * 100;
			
			/**
			 * 更新user_subject表的相关信息
			 */
			
			UserSubject userSubject = this.getUserSubjectService().getByKey(currentUser.getId(), subject.getId());
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
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * 递归排序;
	 */
	protected Map<String,Object> refactorTree(Map<String,Object> cur, List<Map<String,Object>> curList, String split)
	{
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		Boolean hasChild = false;
		Short curLevel = Short.valueOf(cur.get("level").toString());
		String curScode = (String) cur.get("scode");
		for(Map<String,Object> child : curList)
		{
			Short childLevel = Short.valueOf(child.get("level").toString());
			String childScode = (String) child.get("scode");
			
			if(childLevel == curLevel + 1 &&  childScode.startsWith(curScode))
			{
				hasChild=true;
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
	 * 删除错题本中的错题
	 */
	@ActionParam(key = "user.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "item.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "item.blankInx", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void DeleteItem() {
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		SsoUser currentUser = (SsoUser) session.getAttribute("user");
		if (currentUser == null) {
			int uid = this.getIntValue(request.getParameter("user.id"));
			currentUser = this.getSsoUserService().getById(uid);

		}
		
//		Integer subjectID = this.getIntValue(request.getParameter("subject.id"));
//		Subject subject = this.getSubjectService().getSubjectById(subjectID);

		
		int itemId = this.getIntValue(request.getParameter("item.id"));
		int blankInx = this.getIntValue(request.getParameter("item.blankInx"));

		SsoUserTestItemCount  ssoUserTestItemCount = this.getSsoUserTestItemCountService().getByKey(currentUser.getId(), itemId, blankInx);
		
		if (currentUser == null || ssoUserTestItemCount == null ||
				ssoUserTestItemCount.getIsWrongbookItem() != Dictionary.SSO_USER_WRONGBOOK_ITEM) {
			result.init(FAIL_STATUS, "无此用户或错题本的试题！", null);
		} else {
			Timestamp now = new Timestamp(System.currentTimeMillis());
			
			ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_MASTERED);
			ssoUserTestItemCount.setWrongbookItemMastertime(now);
			ssoUserTestItemCount = this.getSsoUserTestItemCountService().update(ssoUserTestItemCount);
			
			result.init(SUCCESS_STATUS, null, null);
		}
		
		Utlity.ResponseWrite(result, dataType, response);
	}
}
