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
	 * ??????????????????????????????
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
			result.init(FAIL_STATUS, "????????????????????????", null);
		} 
		else {
			this.session.setAttribute("user", currentUser);
			//???????????????????????????????????????
			List<Map<String, Object>> wrongBookItemList = this.getUserWrongBookService().searchAllWrongItem(currentUser, subject);
			List<Map<String, Object>> dataList = new ArrayList<>();
			Short inx = 1; //??????
			for (Map<String, Object> itemCount : wrongBookItemList) {
				Map<String, Object> data = new HashMap<>();
				data.put("id", itemCount.get("id"));
				data.put("item" + split + "id", itemCount.get("id"));
				data.put("item" + split + "level", itemCount.get("level"));
				data.put("itemType" + split + "id", itemCount.get("type"));
				data.put("itemType" + split + "name", itemCount.get("typename"));
				data.put("modelType", itemCount.get("modeltype"));
				data.put("isgroup", itemCount.get("isgroup"));
				data.put("inx", inx); //?????????
				data.put("blankInx", itemCount.get("inx"));   //??????????????????
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
			
			result.init(SUCCESS_STATUS, "???????????????", dataList);
			
		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * ??????????????????????????????
	 * 
	 * 
	 	//?????????
	   {
            "ssoUserTestItem.id": 119,
            "answertime":60000,    
            "blankInx": 1,
            "reference": "2",
            "completeType": 1,
            "content":"??????????????????",
            "isAnswered": 1
        },
        //?????????
         {
            "ssoUserTestItem.id": 119,
            "answertime":60000,   //?????????????????????????????????:??????
	    	"blankInx": 1,
	    	"reference": "1,2,3,4",
	    	"completeType": 1,
	    	"content":"??????????????????$%@??????????????????$%@???????????????$%@??????????????????",
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
			result.init(FAIL_STATUS, "????????????????????????", null);
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
			 * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????/???????????????????????????????????????
			 */
			//???????????????????????????????????????
			Integer userTotalTestItemCount = this.getSsoUserTestItemService().calculateUserTotalTestItemCount(currentUser, subject,
					Dictionary.ITEM_ANSWER_TYPESTANDARD);
			
			//?????????????????????????????????
			Integer userTotalRightItemCount = this.getSsoUserTestItemService().calculateUserTotalRightItemCount(currentUser, subject,
					Dictionary.ITEM_ANSWER_TYPESTANDARD);
			
			//?????????????????????????????????,?????????????????????????????????100
			Double userBrushItemRightRate = (userTotalTestItemCount==0) ? 0 : (double) userTotalRightItemCount / userTotalTestItemCount * 100;

			/**
			 * ????????????(???????????????????????????????????????????????????????????????????????????????????????/??????????????????)
			 */
			//??????????????????
			Map<String, Object> totalItemCountSearchMap = new HashMap<>();
			totalItemCountSearchMap.put("subject.id", subject.getId());
			totalItemCountSearchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);
			Integer totalItemCount = this.getItemService().searchItemCount(totalItemCountSearchMap);
			List<Map<String, Object>> totalItemCountList = this.getItemService().searchItemCountGroupByItemType(totalItemCountSearchMap);
			
			//???????????????????????????????????????????????????????????????????????????????????????
			List<Map<String,Object>> userLastTotalRightItemCount = this.getSsoUserTestItemService().calculateUserLastTotalRightItemCountGroupByItemType(currentUser, subject);
			//???????????????????????????
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
			 * ?????????????????????????????????????????????
			 */
			//?????????????????????????????????????????????????????????????????????????????????????????????
			Map<String, Object> prepareTestUserCountSearchMap = new HashMap<>();
			prepareTestUserCountSearchMap.put("subject.id", subject.getId());
			prepareTestUserCountSearchMap.put("status", Dictionary.USER_SUBJECT_STATUS_NOMAL);
			
			Integer prepareTestUserCount = this.getUserSubjectService().getUserSubjectCount(prepareTestUserCountSearchMap);
			Integer userSubjectTestProgressRanking = this.getUserSubjectService().getUserSubjectTestProgressRanking(currentUser.getId(), subject.getId());
			
			Double userSubjectTestProgressRankingRate = (prepareTestUserCount==0) ? 0 : (double) (userSubjectTestProgressRanking-1) / prepareTestUserCount * 100;
			
			/**
			 * ??????user_subject??????????????????
			 */
			
			UserSubject userSubject = this.getUserSubjectService().getByKey(currentUser.getId(), subject.getId());
			userSubject.setBrushItemCount(userTotalTestItemCount);
			userSubject.setCorrectRate(userBrushItemRightRate);
			userSubject.setProgress(userPrepareTestProgress);
			userSubject.setRankingRate(userSubjectTestProgressRankingRate);                   //???????????????
			userSubject = this.getUserSubjectService().updateUserSubject(userSubject);
			
			
			/**
			 * ??????????????????????????????
			 */
			//????????????????????????
			List<Map<String, Object>> knowledgeTestItemChangeList2 = new ArrayList<>();
			
			if(knowledgeTestItemChangeList1 != null && knowledgeTestItemChangeList1.size() > 0)
			{
				for(Map<String,Object> cur : knowledgeTestItemChangeList1)
				{
					//???1???????????????????????????
					if (Short.valueOf(cur.get("level").toString()) == 1) {
						Map<String,Object> curTree = this.refactorTree(cur, knowledgeTestItemChangeList1, split);
						knowledgeTestItemChangeList2.add(curTree);
					}
				}
			}

			//????????????????????????
			Map<String, Object> dataResult = new HashMap<>();
			dataResult.put("brushItemCount", userTotalTestItemCount);     //?????????
			dataResult.put("brushRightItemCount", userTotalRightItemCount);  //???????????????
			dataResult.put("correctRate", userBrushItemRightRate);              //???????????????
			dataResult.put("totalItemCount", totalItemCount);                     //???????????????
			dataResult.put("lastRightItemCount", userLastTotalRightItemCount); //?????????????????????????????????????????????
			dataResult.put("progress", userPrepareTestProgress);                       //????????????
			dataResult.put("nextTestdayCount", userSubject.getNextTestdayCount());   //???????????????
			
			dataResult.put("testUserCount", prepareTestUserCount);            //????????????????????????
			dataResult.put("ranking", userSubjectTestProgressRanking);  //????????????????????????
			dataResult.put("rankingRate", userSubjectTestProgressRankingRate);                //??????????????????????????????
			
			dataResult.put("changeKnowledges", knowledgeTestItemChangeList2);            //?????????????????????
			
			result.init(SUCCESS_STATUS, null, dataResult);

		}
		Utlity.ResponseWrite(result, dataType, response);
	}
	
	/**
	 * ????????????;
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
		//?????????????????????????????????;
		cur.put("hasChild", hasChild);
		if(hasChild)
		{
			cur.put("data",childList);
		}
		return cur;
	}

	/**
	 * ???????????????????????????
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
			result.init(FAIL_STATUS, "????????????????????????????????????", null);
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
