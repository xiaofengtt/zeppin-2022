package cn.zeppin.service.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemAnswerDAO;
import cn.zeppin.dao.api.IKnowledgeDAO;
import cn.zeppin.dao.api.IPaperDAO;
import cn.zeppin.dao.api.ISsoUserTestDAO;
import cn.zeppin.dao.api.ISsoUserTestItemCountDAO;
import cn.zeppin.dao.api.ISsoUserTestItemDAO;
import cn.zeppin.dao.api.ITestPaperItemDAO;
import cn.zeppin.dao.api.ITestPaperSectionDAO;
import cn.zeppin.dao.api.IUserKnowledgeDAO;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTest;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.SsoUserTestItemCount;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.TestPaperItem;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.entity.UserKnowledge;
import cn.zeppin.service.api.ISsoUserTestService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

public class SsoUserTestService implements ISsoUserTestService {

	private ISsoUserTestDAO ssoUserTestDAO;
	private IPaperDAO paperDAO;
	private IKnowledgeDAO knowledgeDAO;

	private IItemAnswerDAO itemAnswerDAO;
	private ITestPaperSectionDAO testPaperSectionDAO;
	private ITestPaperItemDAO testPaperItemDAO;
	private ISsoUserTestItemDAO ssoUserTestItemDAO;
	private IUserKnowledgeDAO userKnowledgeDAO;
	private ISsoUserTestItemCountDAO ssoUserTestItemCountDAO;
	

	public ISsoUserTestDAO getSsoUserTestDAO() {
		return ssoUserTestDAO;
	}

	public void setSsoUserTestDAO(ISsoUserTestDAO ssoUserTestDAO) {
		this.ssoUserTestDAO = ssoUserTestDAO;
	}
	
	public IPaperDAO getPaperDAO() {
		return paperDAO;
	}

	public void setPaperDAO(IPaperDAO paperDAO) {
		this.paperDAO = paperDAO;
	}

	public IKnowledgeDAO getKnowledgeDAO() {
		return knowledgeDAO;
	}

	public void setKnowledgeDAO(IKnowledgeDAO knowledgeDAO) {
		this.knowledgeDAO = knowledgeDAO;
	}

	public IItemAnswerDAO getItemAnswerDAO() {
		return itemAnswerDAO;
	}

	public void setItemAnswerDAO(IItemAnswerDAO itemAnswerDAO) {
		this.itemAnswerDAO = itemAnswerDAO;
	}

	
	public ITestPaperSectionDAO getTestPaperSectionDAO() {
		return testPaperSectionDAO;
	}

	public void setTestPaperSectionDAO(ITestPaperSectionDAO testPaperSectionDAO) {
		this.testPaperSectionDAO = testPaperSectionDAO;
	}

	public ITestPaperItemDAO getTestPaperItemDAO() {
		return testPaperItemDAO;
	}

	public void setTestPaperItemDAO(ITestPaperItemDAO testPaperItemDAO) {
		this.testPaperItemDAO = testPaperItemDAO;
	}

	public ISsoUserTestItemDAO getSsoUserTestItemDAO() {
		return ssoUserTestItemDAO;
	}

	public void setSsoUserTestItemDAO(ISsoUserTestItemDAO ssoUserTestItemDAO) {
		this.ssoUserTestItemDAO = ssoUserTestItemDAO;
	}

	public IUserKnowledgeDAO getUserKnowledgeDAO() {
		return userKnowledgeDAO;
	}

	public void setUserKnowledgeDAO(IUserKnowledgeDAO userKnowledgeDAO) {
		this.userKnowledgeDAO = userKnowledgeDAO;
	}

	public ISsoUserTestItemCountDAO getSsoUserTestItemCountDAO() {
		return ssoUserTestItemCountDAO;
	}

	public void setSsoUserTestItemCountDAO(ISsoUserTestItemCountDAO ssoUserTestItemCountDAO) {
		this.ssoUserTestItemCountDAO = ssoUserTestItemCountDAO;
	}

	/**
	 * 获取 考试记录
	 * @param id
	 * @return
	 */
	public SsoUserTest getUserTestById(Long id){
		return this.getSsoUserTestDAO().get(id);
	}

	/**
	 * 添加一个用户考试记录（答题）
	 * 
	 * @param sut
	 * @return
	 */
	public void addUserTest(SsoUserTest sut) {
		 this.getSsoUserTestDAO().save(sut);
	}
	
	/**
	 * 更新
	 * @param sut
	 */
	public void updateUserTest(SsoUserTest sut){
		this.getSsoUserTestDAO().update(sut);
	}

	/**
	 * 获取 用户答题记录数
	 * 
	 * @param map
	 * @return
	 */
	public int getUserTestCount(Map<String, Object> map) {
		return this.getSsoUserTestDAO().getUserTestCount(map);
	}

	/**
	 * 获取用户答题记录
	 * 
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<SsoUserTest> getUserTestByMap(Map<String, Object> map, String sorts, int offset, int length) {
		return this.getSsoUserTestDAO().getUserTestByMap(map, sorts, offset, length);
	}

	
	/**
	 * 根据试题列表生成一次智能出题的考试
	 * @param subject
	 * @param knowledge
	 * @param currentUser
	 * @param selectItems
	 * @return autoPaperMap
	 */
	@Override
	public Map<String, Object> createAutoTest(Subject subject, SsoUser currentUser, 
			List<Map<String, Object>> selectItems, String split , String version) {
		// TODO Auto-generated method stub
		
		Map<String, Object> autoPaperMap = new HashMap<>();
				
		//自动生成一张临时试卷
		Paper autoPaper = new Paper();
		autoPaper.setType(Dictionary.PAPER_TYPE_AUTOCREATE);
		autoPaper.setName("智能练习");
		autoPaper.setSource("AutoCreate");
		autoPaper.setAnswerTime(-1);
		autoPaper.setTotalScore((short) -1);
		autoPaper.setSubject(subject);
		
		/**
		 * 因为Paper.sysUser是必填字段，所以指定了一个数据，这个数据系统中如果没有就会发生外键错误
		 * 已经将此字段修改为可空字段了 rongjingfeng
		 */
//		SysUser sysUser = new SysUser();
//		sysUser.setId(1);
//		autoPaper.setSysUser(sysUser);
		
		autoPaper.setCreatetime(new Timestamp(System.currentTimeMillis()));
		autoPaper.setStatus(Dictionary.PAPER_STATUS_RELEASE);
		autoPaper = this.getPaperDAO().save(autoPaper);
		
		//自动生成一个试卷片段
		TestPaperSection testPaperSection = new TestPaperSection();
		testPaperSection.setPaper(autoPaper);
		testPaperSection.setInx((short) 1);
		testPaperSection.setName("默认片段");
		testPaperSection.setLevel((short) 1);
		testPaperSection = this.getTestPaperSectionDAO().save(testPaperSection);
		
		// 自动生成用户答卷记录
		SsoUserTest ssoUsertest = new SsoUserTest();
		ssoUsertest.setCreatetime(new Timestamp(System.currentTimeMillis()));
		ssoUsertest.setPaper(autoPaper);
		ssoUsertest.setSsoUser(currentUser);
		ssoUsertest.setStatus(Dictionary.USER_TEST_STATUS_START);
		ssoUsertest = this.getSsoUserTestDAO().save(ssoUsertest);
		
		autoPaperMap.put("ssoUserTest"+split+"id", ssoUsertest.getId());  //这是
		autoPaperMap.put("paper"+split+"id", autoPaper.getId());
		
		List<Map<String, Object>> itemMapList = new ArrayList<>();
		LinkedHashMap<Integer, Map<String, Object>> dataLinkedMap = new LinkedHashMap<>();
		
		Short inx = 1; //生成
		for (Map<String, Object> originalItemMap : selectItems) {
			Item item = new Item();
			item.setId((int) originalItemMap.get("id"));
			//简单题
			if ((Integer) originalItemMap.get("level") == 1 && !(Boolean)originalItemMap.get("isgroup")) {
				//生成试卷试题关联信息
				TestPaperItem testPaperItem = new TestPaperItem();
				/**
				 * 因为后面还会对返回结果排序，所以这里testPaperItem的inx保存的可能跟最终呈现的json顺序不一样。
				 */
				testPaperItem.setItem(item);
				testPaperItem.setInx(inx);
				testPaperItem.setPaper(autoPaper);
				testPaperItem.setTestPaperSection(testPaperSection);
				testPaperItem = this.getTestPaperItemDAO().save(testPaperItem);

				//预先生成用户答题信息，用户如果中途放弃后，为还能继续上次的位置继续答题做数据准备
				SsoUserTestItem ssoUserTestItem = new SsoUserTestItem();
				ssoUserTestItem.setItem(item);
				ssoUserTestItem.setBlankInx(Integer.valueOf(originalItemMap.get("inx").toString()));
				ssoUserTestItem.setSsoUserTest(ssoUsertest);
				ssoUserTestItem.setIsAnswered((short) 0);
				
				ssoUserTestItem = this.getSsoUserTestItemDAO().save(ssoUserTestItem);
				
				Map<String, Object> itemMap = new HashMap<>();
				itemMap.put("id", ssoUserTestItem.getId());
				itemMap.put("item" + split + "id", originalItemMap.get("id"));
				itemMap.put("item" + split + "level", originalItemMap.get("level"));
				itemMap.put("itemType" + split + "id", originalItemMap.get("type"));
				itemMap.put("itemType" + split + "name", originalItemMap.get("typename"));
				itemMap.put("modelType", originalItemMap.get("modeltype"));
				itemMap.put("isgroup", originalItemMap.get("isgroup"));
				itemMap.put("blankInx", originalItemMap.get("inx"));
				itemMap.put("diffcultyLevel", originalItemMap.get("diffcultyLevel"));
				itemMap.put("diffcultyLevelCN", Utlity.getDiffcultyLevelType(Short.valueOf(originalItemMap.get("diffcultyLevel").toString())));
				itemMap.put("defaultScore", originalItemMap.get("defaultScore"));
				itemMap.put("sourceType", originalItemMap.get("sourceType"));
				itemMap.put("source", originalItemMap.get("source"));
				itemMap.put("analysis", originalItemMap.get("analysis"));
				if (originalItemMap.get("content") != null){
					itemMap.put("data", JSONUtils.json2map(originalItemMap.get("content").toString()));
				}else{
					itemMap.put("data", originalItemMap.get("content"));
				}
				itemMap.put("testItemCount", originalItemMap.get("test_item_count") == null ? 0 : originalItemMap.get("test_item_count"));
				itemMap.put("testItemCorrectCount", originalItemMap.get("test_item_correct_count") == null ? 0 : originalItemMap.get("test_item_correct_count"));
				itemMap.put("testItemWrongCount", originalItemMap.get("test_item_wrong_count") == null ? 0 : originalItemMap.get("test_item_wrong_count"));
				
				dataLinkedMap.put((Integer) originalItemMap.get("id"), itemMap);
				
				inx++;
				
			}
			//组合题的材料
			else if ((Integer) originalItemMap.get("level") == 1 && (Boolean)originalItemMap.get("isgroup")  && !version.equals("1.2.0")) {
				//生成试卷试题关联信息
				TestPaperItem testPaperItem = new TestPaperItem();				
				/**
				 * 因为后面还会对返回结果排序，所以这里testPaperItem的inx保存的可能跟最终呈现的json顺序不一样。
				 */
				testPaperItem.setItem(item);
				testPaperItem.setInx(inx);
				testPaperItem.setPaper(autoPaper);
				testPaperItem.setTestPaperSection(testPaperSection);
				testPaperItem = this.getTestPaperItemDAO().save(testPaperItem);

				Map<String, Object> itemMap = new HashMap<>();
				itemMap.put("id", originalItemMap.get("id"));
				itemMap.put("item" + split + "id", originalItemMap.get("id"));
				itemMap.put("item" + split + "level", originalItemMap.get("level"));
				itemMap.put("itemType" + split + "id", originalItemMap.get("type"));
				itemMap.put("itemType" + split + "name", originalItemMap.get("typename"));
				itemMap.put("modelType", originalItemMap.get("modeltype"));
				itemMap.put("isgroup", originalItemMap.get("isgroup"));
				itemMap.put("blankInx", originalItemMap.get("inx"));
				itemMap.put("diffcultyLevel", originalItemMap.get("diffcultyLevel"));
				itemMap.put("diffcultyLevelCN", Utlity.getDiffcultyLevelType(Short.valueOf(originalItemMap.get("diffcultyLevel").toString())));
				itemMap.put("defaultScore", originalItemMap.get("defaultScore"));
				itemMap.put("sourceType", originalItemMap.get("sourceType"));
				itemMap.put("source", originalItemMap.get("source"));
				itemMap.put("data", JSONUtils.json2map(originalItemMap.get("content").toString()));
				itemMap.put("children", new ArrayList<Map<String, Object>>());
				
				dataLinkedMap.put((Integer) originalItemMap.get("id"), itemMap);
				
				inx++;
				
			}
			//组合题中level=2的试题
			else if ((Integer) originalItemMap.get("level") == 2 && (Boolean)originalItemMap.get("isgroup")  && !version.equals("1.2.0")) {

				//预先生成用户答题信息，用户如果中途放弃后，为还能继续上次的位置继续答题做数据准备
				SsoUserTestItem ssoUserTestItem = new SsoUserTestItem();
				ssoUserTestItem.setItem(item);
				ssoUserTestItem.setBlankInx(Integer.valueOf(originalItemMap.get("inx").toString()));
				ssoUserTestItem.setSsoUserTest(ssoUsertest);
				ssoUserTestItem.setIsAnswered((short) 0);

				ssoUserTestItem = this.getSsoUserTestItemDAO().save(ssoUserTestItem);
				
				
				/**
				 * 因为取得itemlist是有顺序的，level=2的都在最后，所以dataLinkedMap可以get到试题的parentitemMap对象。
				 */
				Map<String, Object> parentItemMap = dataLinkedMap.get(originalItemMap.get("parent"));
				if (parentItemMap != null){
					List<Map<String, Object>> children = (List<Map<String, Object>>) parentItemMap.get("children");
					Map<String, Object> itemMap = new HashMap<>();
					itemMap.put("id", ssoUserTestItem.getId());
					itemMap.put("item" + split + "id", originalItemMap.get("id"));
					itemMap.put("item" + split + "level", originalItemMap.get("level"));
					itemMap.put("itemType" + split + "id", originalItemMap.get("type"));
					itemMap.put("itemType" + split + "name", originalItemMap.get("typename"));
					itemMap.put("modelType", originalItemMap.get("modeltype"));
					itemMap.put("isgroup", originalItemMap.get("isgroup"));
					itemMap.put("blankInx", originalItemMap.get("inx"));
					itemMap.put("defaultScore", originalItemMap.get("defaultScore"));
					itemMap.put("analysis", originalItemMap.get("analysis"));
					itemMap.put("data", JSONUtils.json2map(originalItemMap.get("content").toString()));
					itemMap.put("testItemCount", originalItemMap.get("test_item_count") == null ? 0 : originalItemMap.get("test_item_count"));
					itemMap.put("testItemCorrectCount", originalItemMap.get("test_item_correct_count") == null ? 0 : originalItemMap.get("test_item_correct_count"));
					itemMap.put("testItemWrongCount", originalItemMap.get("test_item_wrong_count") == null ? 0 : originalItemMap.get("test_item_wrong_count"));
			
					children.add(itemMap);
					
				}
			}
		}
		for (Integer id : dataLinkedMap.keySet()) {
			itemMapList.add(dataLinkedMap.get(id));
		}

		Collections.sort(itemMapList, new Comparator<Map<String,Object>>() {
			public int compare(Map<String,Object> item0, Map<String,Object> item1) {
				Boolean item0isgroup = (Boolean) item0.get("isgroup");
				Boolean item1isgroup = (Boolean) item1.get("isgroup");
				Integer item0Type = (Integer) item0.get("itemType" + split + "id");
				Integer item1Type = (Integer) item1.get("itemType" + split + "id");
				if (item0isgroup.compareTo(item1isgroup) == 0) {
					return item0Type.compareTo(item1Type);
				}
				return item0isgroup.compareTo(item1isgroup);
			}
		});
		
		
		
		
		autoPaperMap.put("items", itemMapList);
		return autoPaperMap;
	}

	
	/**
	 * 保存自动化试卷
	 * @param ssoUserTest
	 * @param answers
	 *  answers参数介绍:
	 *  协议为JSON格式，内容是用户回答试题的数组 
	 *  ssoUserTestItem.id为用户做题的ID（题可重复做）
	 *  answertime为一道题的做题时间，单位:毫秒
	 *  blankInx为题的每个空index
	 *  reference为答案，多选用逗号分隔
	 *  content为用户的答案内容
	 *  isAnswers是否回答了该题
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SsoUserTestItem> saveSsoUserAutoTest(SsoUserTest ssoUserTest, List<Map> answers) {
		// TODO Auto-generated method stub
		List<SsoUserTestItem> results = new ArrayList<>();
		ssoUserTest = this.getSsoUserTestDAO().update(ssoUserTest);
		
		
		
		/**
		 * 保存试题
		 */
		for(Map<String, Object> answerMap: answers) {
			
			/**
			 * 保存做题记录
			 */
			Long ssoUserTestItemID = Long.valueOf(answerMap.get("ssoUserTestItem.id").toString());
			Integer answertime = (Integer) answerMap.get("answertime");
//			Integer blankInx =  (Integer) answerMap.get("blankInx");
			String reference = (String) answerMap.get("reference");
			Short completeType = Short.valueOf(answerMap.get("completeType").toString());
			String content = (String) answerMap.get("content");
			Short isAnswered = Short.valueOf(answerMap.get("isAnswered").toString());
			
			
			SsoUserTestItem ssoUserTestItem = this.getSsoUserTestItemDAO().get(ssoUserTestItemID);
			ssoUserTestItem.setReference(reference);
			ssoUserTestItem.setCompleteType(completeType == null ? Dictionary.SSO_USER_TEST_ITEM_WRONG : completeType);
			ssoUserTestItem.setContent(content);
			ssoUserTestItem.setAnswertime(answertime);
			ssoUserTestItem.setIsAnswered(isAnswered);
			ssoUserTestItem = this.getSsoUserTestItemDAO().update(ssoUserTestItem);
			
			
			/**
			 * 计算用户试题统计信息
			 */
			Integer userId = ssoUserTest.getSsoUser().getId();
			Integer itemId =  ssoUserTestItem.getItem().getId();
			Integer blankInx = ssoUserTestItem.getBlankInx();
			SsoUserTestItemCount ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().getByKey(userId, itemId, blankInx);
			
			Timestamp now = new Timestamp(System.currentTimeMillis());
			//当已经有用户做此题记录的时候
			if (ssoUserTestItemCount != null) {
				ssoUserTestItemCount.setLastTestItem(ssoUserTestItem);
				
				ssoUserTestItemCount.setLastTestItemAnswerTime(now);
				ssoUserTestItemCount.setLastTestItemCompleteType(ssoUserTestItem.getCompleteType());
				
				if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_WRONG) {
					ssoUserTestItemCount.setTestItemWrongCount(ssoUserTestItemCount.getTestItemWrongCount() + 1);
					//错题本标记为true，是否在错题本中做过和是否已掌握均并保持不变
					ssoUserTestItemCount.setIsWrongbookItem(Dictionary.SSO_USER_WRONGBOOK_ITEM);
					//错题本中已经标记了删除（已掌握）的，又做错了，标记取消，并且算作新增加到错题本中的试题
					if (ssoUserTestItemCount.getIsWrongbookItemMastered() == Dictionary.SSO_USER_WRONGBOOK_ITEM_MASTERED) {
						ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
						ssoUserTestItemCount.setWrongbookItemMastertime(null);
						ssoUserTestItemCount.setWrongbookItemCreatetime(now);
					}
				}
				else if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_CORRECT) {
					ssoUserTestItemCount.setTestItemCorrectCount(ssoUserTestItemCount.getTestItemCorrectCount() + 1);
				}
				
				ssoUserTestItemCount.setTestItemCount(ssoUserTestItemCount.getTestItemCount() + 1);
				
				ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().update(ssoUserTestItemCount);
			}
			//用户第一次做此题,添加一条记录
			else {
				ssoUserTestItemCount = new SsoUserTestItemCount();
				ssoUserTestItemCount.setSsoUser(ssoUserTest.getSsoUser());
				ssoUserTestItemCount.setItem(ssoUserTestItem.getItem());
				ssoUserTestItemCount.setBlankInx(blankInx);
				
				ssoUserTestItemCount.setLastTestItem(ssoUserTestItem);
				ssoUserTestItemCount.setLastTestItemAnswerTime(new Timestamp(System.currentTimeMillis()));
				ssoUserTestItemCount.setLastTestItemCompleteType(ssoUserTestItem.getCompleteType());
				if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_WRONG) {
					ssoUserTestItemCount.setTestItemCorrectCount(0);
					ssoUserTestItemCount.setTestItemWrongCount(1);
					//新增加到错题本，错题本标记为true
					ssoUserTestItemCount.setIsWrongbookItem(Dictionary.SSO_USER_WRONGBOOK_ITEM);
					ssoUserTestItemCount.setWrongbookItemCreatetime(now);
					ssoUserTestItemCount.setIsWrongbookItemTested(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTTEST);
					ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);

				}
				else if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_CORRECT) {
					ssoUserTestItemCount.setTestItemCorrectCount(1);
					ssoUserTestItemCount.setTestItemWrongCount(0);
					//错题本标记为false
					ssoUserTestItemCount.setIsWrongbookItem(Dictionary.NOT_SSO_USER_WRONGBOOK_ITEM);
					ssoUserTestItemCount.setWrongbookItemCreatetime(now);
					ssoUserTestItemCount.setIsWrongbookItemTested(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTTEST);
					ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
				}
				ssoUserTestItemCount.setTestItemCount(1);
				
				ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().save(ssoUserTestItemCount);
				
				
				//用户第一次做该题，如果做错了，进入错题本
			}
			
			
			results.add(ssoUserTestItem);
		}
		
		return results;
	}

	
	/**
	 * 本次做题一级知识点掌握情况变化,每个试题只计算最后一次做题是否正确（暂以正确题数增减为主）
	 * 
	 */
	// knowledge.id(一级)
    // knowledge.name
	// knowledge.totalItemCount
	// knowledge.userTestItemTotalCount
	// knowledge.userTestRightItemTotalCount
	// knowledge.userTestRightItemChangeCount
	// knowledge.changeFlag
	@Override
	public List<Map<String, Object>> calculateKnowledgeTestChange(SsoUser ssoUser, 
			List<SsoUserTestItem> ssoUserTestItemList, Subject subject, String split) {
		// TODO Auto-generated method stub
		
		Integer userID = ssoUser.getId();
		
		// 计算knowledge.userTestRightItemIncreaseCount
//		Map<String, Object> knowledgeSearchMap = new HashMap<>();
//		knowledgeSearchMap.put("subject.id", subject.getId());
//		knowledgeSearchMap.put("level", 1);
//		knowledgeSearchMap.put("status", Dictionary.KNOWLEDGE_STATUS_NOMAL);
		
		List<Map<String, Object>> subjectKnowledges = this.getUserKnowledgeDAO().getUserKnowledges(
				userID, subject.getId());
		
		LinkedHashMap<String, Map<String, Object>> results = new LinkedHashMap<>();
//		List<Map<String,Map<String,Object>>> result = new ArrayList<>();
		
		
		//初始化返回值
		for (Map<String, Object> knowledge : subjectKnowledges) {
			Map<String,Object> knowledgeChangeMap = new HashMap<>();
			knowledgeChangeMap.put("id", knowledge.get("id"));
			knowledgeChangeMap.put("name", knowledge.get("name"));
			knowledgeChangeMap.put("level", knowledge.get("level"));
			knowledgeChangeMap.put("scode", knowledge.get("scode"));
			knowledgeChangeMap.put("changeFlag", false);
			knowledgeChangeMap.put("rightChangeCount", 0);
			knowledgeChangeMap.put("rightCount", knowledge.get("last_test_item_correct_count"));
			knowledgeChangeMap.put("testTotalCount", knowledge.get("brush_item_count"));
//			knowledgeChangeMap.put("totalItemCount", knowledge.get("standard_released_itemcount"));
			
					
			
			results.put(knowledge.get("id").toString(), knowledgeChangeMap);
		}
		//做的题导致的知识点相关数据的变化
		for(SsoUserTestItem ssoUserTestItem: ssoUserTestItemList) {
			SsoUserTestItem previousSsoUserTestItem = this.getSsoUserTestItemDAO().getPreviousSsoUserTestItem(ssoUserTestItem);
			
			/**
			 * 对于同一道题有几种情况判断知识点是否正确题数发生变化（只记住有变化的知识点）
			 * 1、本次做题正确，上次做题错误     ——该用户该知识点正确题    +1
			 * 2、本次做题正确，上次做题也正确    ——该用户该知识点正确题数  0
			 * 3、本次做题错误，上次做题也错误    ——该用户该知识点正确题数  0
			 * 4、本次做题错误，上次做题正确     ——该用户该知识点正确题    -1
			 * 5、本次做题正确，以前没做过该题   ——该用户该知识点正确题     +1
			 * 6、本次做题错误，以前没做过该题   ——该用户该知识点正确题     0
			 */
			Integer changeNum = 0;
			if (previousSsoUserTestItem == null || previousSsoUserTestItem.getCompleteType() == null ||
					previousSsoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_WRONG) 
			{
				if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_CORRECT) {
					changeNum = 1;
				}
			}
			else if (previousSsoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_CORRECT) {
				if (ssoUserTestItem.getCompleteType() == null ||
						ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_WRONG ) 
				{
					changeNum = -1;
				}
			}
			
			String itemKnowledgeScode = ssoUserTestItem.getItem().getKnowledge().getScode();
			
			for (Map<String, Object> knowledge: subjectKnowledges) {
				//该知识点的上级知识点全部都包括了
				if (itemKnowledgeScode.startsWith(knowledge.get("scode").toString())){
//					Map<String, Map<String,>> resultData = new HashMap<>();
					Map<String,Object> knowledgeChangeMap = results.get(knowledge.get("id").toString());
					
					//设置知识点变化标记
					knowledgeChangeMap.put("changeFlag", true);
					
					//更新知识点正确题数变化
					Integer rightChangeCount = (Integer) knowledgeChangeMap.get("rightChangeCount");
					Integer rightCount =  (Integer) knowledgeChangeMap.get("rightCount");
					Integer testTotalCount =  (Integer) knowledgeChangeMap.get("testTotalCount");
					knowledgeChangeMap.put("rightChangeCount", rightChangeCount + changeNum);
					knowledgeChangeMap.put("rightCount", rightCount + changeNum);
					knowledgeChangeMap.put("testTotalCount", testTotalCount + 1);
				}
			}
			
		}
		
		//将有变化的knowledge重新封装成List, 并更新数据库
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (Iterator<String> it =  results.keySet().iterator(); it.hasNext();) {
			 String key = it.next();
			 if ((boolean) results.get(key).get("changeFlag")){
				 Map<String, Object> userKnowledgeMap = results.get(key);

				 Integer knowledgeId = Integer.valueOf(userKnowledgeMap.get("id").toString());
				 
				 UserKnowledge userKnowledge = this.getUserKnowledgeDAO().getByKey(userID, knowledgeId);
				 /**
				  * 如果user_knowledge的表中没有user的knowledge信息，则添加，否则只需更新即可
				  */
				 if (userKnowledge == null) {
					 userKnowledge = new UserKnowledge();
					 
					 Knowledge knowledge = new Knowledge();
					 knowledge.setId(knowledgeId);
					 userKnowledge.setKnowledge(knowledge);
					 
					 userKnowledge.setSsoUser(ssoUser);
					 
					 Integer rightCount =  (Integer) userKnowledgeMap.get("rightCount");
					 Integer testTotalCount =  (Integer) userKnowledgeMap.get("testTotalCount");
					 userKnowledge.setLastTestItemCorrectCount(rightCount);
					 userKnowledge.setBrushItemCount(testTotalCount);
					 userKnowledge.setCorrectRate(0d); //暂时先不计算
					 
					 userKnowledge = this.getUserKnowledgeDAO().save(userKnowledge);
				 }
				 else {
					 Integer rightCount =  (Integer) userKnowledgeMap.get("rightCount");
					 Integer testTotalCount =  (Integer) userKnowledgeMap.get("testTotalCount");
					 userKnowledge.setLastTestItemCorrectCount(rightCount);
					 userKnowledge.setBrushItemCount(testTotalCount);
					 userKnowledge.setCorrectRate(0d); //暂时先不计算

					 userKnowledge = this.getUserKnowledgeDAO().update(userKnowledge);
				 }
				 //返回信息添加结构
				 resultList.add(userKnowledgeMap);
			 }
			 
		}
		
		
		return resultList;
	}

	/**
	 * 试卷出题
	 * @param subject 
	 * @return dataMap
	 */
	public Map<String, Object> createPaperTest(SsoUser currentUser , Paper paper , String split) {
		Map<String,Object> paperData = new HashMap<String,Object>(); 
		paperData.put("id", paper.getId());
		paperData.put("answerTime", paper.getAnswerTime());
		if (paper.getArea() != null) {
			paperData.put("area" + split + "id", paper.getArea().getId());
			paperData.put("area" + split + "name", Utlity.getAreaName(paper.getArea()));
		} else {
			paperData.put("area" + split + "id", 0);
			paperData.put("area" + split + "name", "全部");
		}
		paperData.put("cover", paper.getCover());
		paperData.put("name", paper.getName());
		paperData.put("source", paper.getSource());
		paperData.put("sourceType", Utlity.getSourceType(paper.getType()));
		paperData.put("status", paper.getStatus());
		paperData.put("subject" + split + "name", paper.getSubject().getName());
		paperData.put("subject" + split + "id",paper.getSubject().getId());
		paperData.put("totalScore", paper.getTotalScore());
		paperData.put("type", paper.getType());
		paperData.put("year", paper.getYear());
		paperData.put("price", paper.getPrice());
		paperData.put("isFree", paper.getIsFree());
		
		SsoUserTest ssoUsertest = new SsoUserTest();
		ssoUsertest.setCreatetime(new Timestamp(System.currentTimeMillis()));
		ssoUsertest.setPaper(paper);
		ssoUsertest.setSsoUser(currentUser);
		ssoUsertest.setStatus(Dictionary.USER_TEST_STATUS_START);
		ssoUsertest = this.getSsoUserTestDAO().save(ssoUsertest);
		paperData.put("ssoUserTest"+split+"id", ssoUsertest.getId());
		
		List<Map<String,Object>> sectionsDataList = new ArrayList<Map<String,Object>>();
		Map<String, Object> sectionsSearchMap = new HashMap<String, Object>();
		sectionsSearchMap.put("paper.id", paper.getId());
		List<TestPaperSection> sectionList = this.getTestPaperSectionDAO().searchTestPaperSections(sectionsSearchMap, "inx", -1, -1);//查询level==1的目录
		
		for (TestPaperSection section : sectionList) {
			Map<String,Object> paperSectionData = new HashMap<String,Object>(); 
			paperSectionData.put("id", section.getId());
			paperSectionData.put("inx", section.getInx());
			if(section.getItemType() != null){
				paperSectionData.put("itemType", section.getItemType().getName());
			}else{
				paperSectionData.put("itemType", "混合题型");
			}
			paperSectionData.put("description", section.getName());

			List<Map<String, Object>> itemMapList = this.getPaperDAO().searchTestPaperSectionItemMap(section.getId());
			LinkedHashMap<Integer, Map<String, Object>> dataLinkedMap = new LinkedHashMap<>();
			Short inx = 1; //生成
			for (Map<String, Object> item : itemMapList) {
				Item aitem = new Item();
				aitem.setId((int) item.get("id"));
				if ((Integer) item.get("level") == 1 && !(Boolean)item.get("isgroup")) {
					SsoUserTestItem ssoUserTestItem = new SsoUserTestItem();
					ssoUserTestItem.setItem(aitem);
					ssoUserTestItem.setBlankInx(1);
					ssoUserTestItem.setSsoUserTest(ssoUsertest);
					ssoUserTestItem.setIsAnswered((short) 0);
					System.out.print(inx);
					ssoUserTestItem = this.getSsoUserTestItemDAO().save(ssoUserTestItem);
					
					Map<String, Object> itemMap = new HashMap<>();
					itemMap.put("id", ssoUserTestItem.getId());
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
					if (item.get("content") != null){
						itemMap.put("data", JSONUtils.json2map(item.get("content").toString()));
					}else{
						itemMap.put("data", item.get("content"));
					}
					dataLinkedMap.put((Integer) item.get("id"), itemMap);
					inx++;
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
					itemMap.put("data", JSONUtils.json2map(item.get("content").toString()));
					itemMap.put("children", new ArrayList<Map<String, Object>>());
					
					dataLinkedMap.put((Integer) item.get("id"), itemMap);
					inx++;
				}
				//组合题中level=2的试题
				else if ((Integer) item.get("level") == 2 && (Boolean)item.get("isgroup")) {
					/**
					 * 因为取得itemlist是有顺序的，level=2的都在最后，所以dataLinkedMap可以get到试题的parentitemMap对象。
					 */
					SsoUserTestItem ssoUserTestItem = new SsoUserTestItem();
					ssoUserTestItem.setItem(aitem);
					ssoUserTestItem.setBlankInx(1);
					ssoUserTestItem.setSsoUserTest(ssoUsertest);
					ssoUserTestItem.setIsAnswered((short) 0);

					ssoUserTestItem = this.getSsoUserTestItemDAO().save(ssoUserTestItem);
					
					Map<String, Object> parentItemMap = dataLinkedMap.get(item.get("parent"));
					if (parentItemMap != null){
						List<Map<String, Object>> children = (List<Map<String, Object>>) parentItemMap.get("children");
						Map<String, Object> itemMap = new HashMap<>();
						itemMap.put("id", ssoUserTestItem.getId());
						itemMap.put("item" + split + "id", item.get("id"));
						itemMap.put("item" + split + "level", item.get("level"));
						itemMap.put("itemType" + split + "id", item.get("type"));
						itemMap.put("itemType" + split + "name", item.get("typename"));
						itemMap.put("modelType", item.get("modeltype"));
						itemMap.put("isgroup", item.get("isgroup"));
						itemMap.put("blankInx", item.get("inx"));
						itemMap.put("defaultScore", item.get("defaultScore"));
						itemMap.put("analysis", item.get("analysis"));
						itemMap.put("data", JSONUtils.json2map(item.get("content").toString()));	
						children.add(itemMap);
						
					}
				}
			}
			
			List<Map<String, Object>> itemDataList = new ArrayList<>();
			for (Integer itemId : dataLinkedMap.keySet()) {
				itemDataList.add(dataLinkedMap.get(itemId));
			}
			
			paperSectionData.put("itemList", itemDataList);
			sectionsDataList.add(paperSectionData);
		}
		paperData.put("sectionList", sectionsDataList);
		return paperData;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SsoUserTestItem> saveSsoUserTest(SsoUserTest ssoUserTest, List<Map> answers) {
		List<SsoUserTestItem> results = new ArrayList<>();
		ssoUserTest = this.getSsoUserTestDAO().update(ssoUserTest);
		
		for(Map<String, Object> answerMap: answers) {
			
			Long ssoUserTestItemID = Long.valueOf(answerMap.get("ssoUserTestItem.id").toString());
			Integer answertime = (Integer) answerMap.get("answertime");
			String reference = (String) answerMap.get("reference");
			Short completeType = Short.valueOf(answerMap.get("completeType").toString());
			String content = (String) answerMap.get("content");
			Short isAnswered = Short.valueOf(answerMap.get("isAnswered").toString());
			Double score =  Double.valueOf(answerMap.get("score").toString());
			
			
			SsoUserTestItem ssoUserTestItem = this.getSsoUserTestItemDAO().get(ssoUserTestItemID);
			ssoUserTestItem.setReference(reference);
			ssoUserTestItem.setCompleteType(completeType == null ? Dictionary.SSO_USER_TEST_ITEM_WRONG : completeType);
			ssoUserTestItem.setContent(content);
			ssoUserTestItem.setAnswertime(answertime);
			ssoUserTestItem.setIsAnswered(isAnswered);
			ssoUserTestItem.setScore(score);
			ssoUserTestItem = this.getSsoUserTestItemDAO().update(ssoUserTestItem);
			
			
			if(ssoUserTestItem.getItem().getModelType() != 6){
				Integer userId = ssoUserTest.getSsoUser().getId();
				Integer itemId =  ssoUserTestItem.getItem().getId();
				Integer blankInx = ssoUserTestItem.getBlankInx();
				SsoUserTestItemCount ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().getByKey(userId, itemId, blankInx);
				
				Timestamp now = new Timestamp(System.currentTimeMillis());
				if (ssoUserTestItemCount != null) {
					ssoUserTestItemCount.setLastTestItem(ssoUserTestItem);
					
					ssoUserTestItemCount.setLastTestItemAnswerTime(now);
					ssoUserTestItemCount.setLastTestItemCompleteType(ssoUserTestItem.getCompleteType());
					
					if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_WRONG) {
						ssoUserTestItemCount.setTestItemWrongCount(ssoUserTestItemCount.getTestItemWrongCount() + 1);
						ssoUserTestItemCount.setIsWrongbookItem(Dictionary.SSO_USER_WRONGBOOK_ITEM);
						if (ssoUserTestItemCount.getIsWrongbookItemMastered() == Dictionary.SSO_USER_WRONGBOOK_ITEM_MASTERED) {
							ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
							ssoUserTestItemCount.setWrongbookItemMastertime(null);
							ssoUserTestItemCount.setWrongbookItemCreatetime(now);
						}
					}
					else if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_CORRECT) {
						ssoUserTestItemCount.setTestItemCorrectCount(ssoUserTestItemCount.getTestItemCorrectCount() + 1);
					}
					
					ssoUserTestItemCount.setTestItemCount(ssoUserTestItemCount.getTestItemCount() + 1);
					
					ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().update(ssoUserTestItemCount);
				}
				else {
					ssoUserTestItemCount = new SsoUserTestItemCount();
					ssoUserTestItemCount.setSsoUser(ssoUserTest.getSsoUser());
					ssoUserTestItemCount.setItem(ssoUserTestItem.getItem());
					ssoUserTestItemCount.setBlankInx(blankInx);
					
					ssoUserTestItemCount.setLastTestItem(ssoUserTestItem);
					ssoUserTestItemCount.setLastTestItemAnswerTime(new Timestamp(System.currentTimeMillis()));
					ssoUserTestItemCount.setLastTestItemCompleteType(ssoUserTestItem.getCompleteType());
					if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_WRONG) {
						ssoUserTestItemCount.setTestItemCorrectCount(0);
						ssoUserTestItemCount.setTestItemWrongCount(1);
						ssoUserTestItemCount.setIsWrongbookItem(Dictionary.SSO_USER_WRONGBOOK_ITEM);
						ssoUserTestItemCount.setWrongbookItemCreatetime(now);
						ssoUserTestItemCount.setIsWrongbookItemTested(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTTEST);
						ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
	
					}
					else if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_CORRECT) {
						ssoUserTestItemCount.setTestItemCorrectCount(1);
						ssoUserTestItemCount.setTestItemWrongCount(0);
						ssoUserTestItemCount.setIsWrongbookItem(Dictionary.NOT_SSO_USER_WRONGBOOK_ITEM);
						ssoUserTestItemCount.setWrongbookItemCreatetime(now);
						ssoUserTestItemCount.setIsWrongbookItemTested(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTTEST);
						ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
					}
					ssoUserTestItemCount.setTestItemCount(1);				
					ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().save(ssoUserTestItemCount);
				}
			}
			
			
			results.add(ssoUserTestItem);
		}
		return results;
	}
}
