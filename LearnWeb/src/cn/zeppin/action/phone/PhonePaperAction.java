/** 
 * Project Name:CETV_TEST 
 * File Name:PhonePaperAction.java 
 * Package Name:cn.zeppin.action.phone 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.phone;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.zeppin.access.UserTestCountEx;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemAnswer;
import cn.zeppin.entity.Medal;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.User;
import cn.zeppin.entity.UserTest;
import cn.zeppin.entity.UserTestItem;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.IMedalService;
import cn.zeppin.service.api.IPaperService;
import cn.zeppin.service.api.ITestPaperItemService;
import cn.zeppin.service.api.ITextbookCapterService;
import cn.zeppin.service.api.IUserKnowledgeDegreeService;
import cn.zeppin.service.api.IUserService;
import cn.zeppin.service.api.IUserTestItemService;
import cn.zeppin.service.api.IUserTestService;
import cn.zeppin.service.api.IUserTextbookcapterDegreeService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: PhonePaperAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月21日 下午4:37:33 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class PhonePaperAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3054883231660104139L;

	private List<Map<String, Object>> itemData;

	private IItemService itemService;
	private ITextbookCapterService textbookCapterService;
	private IPaperService paperService;
	private IGradeService gradeService;
	private IKnowledgeService knowledgeService;
	private ITestPaperItemService testPaperItemService;
	private IUserTestService userTestService;
	private IUserTestItemService userTestItemService;
	private IUserKnowledgeDegreeService userKnowledgeDegreeService;
	private IUserTextbookcapterDegreeService userTextbookcapterDegreeService;
	private IMedalService medalService;
	
	private IUserService  userService;

	private Map<String, Object> submitMap;
	private List<Paper> lstPapers;
	private HashMap<Integer, UserTest> cureentTest;
	private int groupPaperId;
	private Long usertestId;

	private int paperId;
	private int usertId;
	private short clientType;

	private Map<Integer, Object> currentAnswer;

	private UserTestCountEx currentUserTest;


	/**
	 * 心理测评入口
	 * 
	 * @author Administrator
	 * @date: 2014年11月2日 下午2:06:27 <br/>
	 */
	@ActionParam(key = "paper.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String Psychology() {
		User user = (User) session.getAttribute("userphone");

		if (user != null) {

			int paperId = this.getIntValue(request.getParameter("paper.id"));
			Short typeId = Short.valueOf(request.getParameter("type"));
			Paper paper = this.getPaperService().getPaperById(paperId);

			// 入库到用户考试记录表
			UserTest ut = new UserTest();
			ut.setUser(user);
			ut.setPaper(paper);
			ut.setStatus(0);
			ut.setDuration(0l);
			ut.setScore(0.0);

			this.getUserTestService().addUserTest(ut);

			this.groupPaperId = paper.getId();
			this.usertestId = ut.getId();
			this.clientType = typeId;

			return "paper";
		} else {
			return "logout";
		}
	}

	/**
	 * 加载试题
	 * 
	 * @author Administrator
	 * @throws IOException
	 * @date: 2014年10月24日 上午10:53:19 <br/>
	 */
	@ActionParam(key = "paper.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "usertest.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String LoadItem() throws IOException {

		User user = (User) session.getAttribute("userphone");

		if (user != null) {

			this.paperId = this.getIntValue(request.getParameter("paper.id"));
			this.usertId = this.getIntValue(request.getParameter("usertest.id"));
			this.clientType = Short.valueOf(request.getParameter("clienttype"));

			String split = "_";
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("paper.id", request.getParameter("paper.id"));
			itemData = new LinkedList<Map<String, Object>>();
			List<Item> itemList = this.getItemService().getItemsForPhone(searchMap);

			if (itemList != null && itemList.size() > 0) {
				for (Item item : itemList) {

					Map<String, Object> data = SerializeEntity.item2Map(item, split);

					if (item.getIsGroup()) {
						List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
						if (childItemList != null && childItemList.size() > 0) {
							List<Map<String, Object>> childdataList = new LinkedList<Map<String, Object>>();
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
					itemData.add(data);

				}
			}
			return "initAnswer";
		} else {
			return "logout";
		}
	}

	/**
	 * 提交试卷
	 * 
	 * @author Administrator
	 * @date: 2014年10月24日 上午10:53:19 <br/>
	 */
	@SuppressWarnings("unchecked")
	public String Submit() {

		User user = (User) session.getAttribute("userphone");
		if (user != null) {

			int Score = 0;
			String questionItem = request.getParameter("json");
			submitMap = JSONUtils.json2map(questionItem);

			this.usertId = Integer.valueOf(submitMap.get("usertest.id").toString());
			this.clientType = Short.valueOf(submitMap.get("clienttype").toString());

			List<Map<String, Object>> itemList = (List<Map<String, Object>>) submitMap.get("answers");
			UserTest ut = this.getUserTestService().getUserTestById(Integer.valueOf(submitMap.get("usertest.id").toString()));
			int index = 1;
			for (Map<String, Object> itemMap : itemList) {
				Item item = this.getItemService().getItemById(Integer.valueOf(itemMap.get("item.id").toString()));
				if (item.getItemType().getModelType() == 1 || item.getItemType().getModelType() == 3 || item.getItemType().getModelType() == 5) {
					Map<String, Object> searchMap = new HashMap<>();
					searchMap.put("item.id", Integer.valueOf(itemMap.get("item.id").toString()));
					List<ItemAnswer> answers = this.getItemService().getItemAnswerByParam(searchMap);
					ItemAnswer answer = answers.get(0);
					UserTestItem uti = new UserTestItem();
					uti.setItem(item);
					uti.setAnswertime(Integer.valueOf(itemMap.get("answertime").toString()));
					uti.setUserTest(ut);
					uti.setItemAnswer(answer);
					uti.setContent(itemMap.get("inx").toString());
					uti.setInx(index);
					if (answer.getReference().equals(itemMap.get("inx").toString())) {
						Score = Score + answer.getScore();
						uti.setScore(answer.getScore());
					} else {
						uti.setScore(0);
					}
					this.getUserTestItemService().addUserTestItem(uti);
					index++;
				} else if (item.getItemType().getModelType() == 2) {
					Map<String, Object> searchMap = new HashMap<>();
					searchMap.put("item.id", Integer.valueOf(itemMap.get("item.id").toString()));
					List<ItemAnswer> answers = this.getItemService().getItemAnswerByParam(searchMap);
					List<Object> content = (List<Object>) itemMap.get("content");
					for (int i = 0; i < answers.size(); i++) {
						UserTestItem uti = new UserTestItem();
						uti.setItem(item);
						uti.setAnswertime(Integer.valueOf(itemMap.get("answertime").toString()));
						uti.setUserTest(ut);
						uti.setItemAnswer(answers.get(i));
						uti.setInx(index);

						if (i < content.size()) {
							uti.setContent(content.get(i).toString().trim());
							if (content.get(i).toString().trim().equals(answers.get(i).getContent().trim())) {
								Score = Score + answers.get(i).getScore();
								uti.setScore(answers.get(i).getScore());
							} else {
								uti.setScore(0);
							}
						} else {
							uti.setScore(0);
							uti.setContent("");
						}

						this.getUserTestItemService().addUserTestItem(uti);
						index++;
					}
				}
			}
			ut.setDuration(Long.valueOf(submitMap.get("answertime").toString()));
			ut.setStatus(1);
			ut.setScore((double) Score);
			ut.setEndtime(new Timestamp((new Date()).getTime()));
			this.getUserTestService().updateUserTest(ut);
			
			User tmuser = this.getUserService().getUserById(user.getId());
			tmuser.setScore(tmuser.getScore()+(long)Score);
			this.getUserService().updateUser(tmuser);
			
			return "initParese";
		} else {
			return "logout";
		}
	}

	/**
	 * 解析用户试卷等分统计情况
	 * 
	 * @author Administrator
	 * @date: 2014年10月27日 下午5:00:43 <br/>
	 */
	@ActionParam(key = "type", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "usertest.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String ParesePaper() {

		User user = (User) session.getAttribute("userphone");
		if (user != null) {

			short type = Short.valueOf(request.getParameter("type"));
			long userTestId = this.getIntValue(request.getParameter("usertest.id"), 0);
			this.clientType = type;
			this.usertestId = userTestId;

			// 其余客户端入口
			// 只要分析出总分在什么范围来判断
			// 根据不同clientType 来判断不同的业务
			UserTest ut = this.getUserTestService().getUserTestById(userTestId);
			Paper paper = ut.getPaper();
			UserTestCountEx utce = new UserTestCountEx();
			utce.setMedalName(Utlity.getOtherTypeName(type));

			if ((paper.getTotalScore() * 0.8) <= ut.getScore()) {
				// 可以获得勋章
				utce.setMedalType(Utlity.getOtherType2Type(type));
				utce.setMedal(1);

				// 入库
				Map<String, Object> smap = new HashMap<String, Object>();
				smap.put("type", type);
				smap.put("user.id", user.getId());

				int count = this.getMedalService().getMedalCount(smap);
				if (count == 0) {
					Medal medal = new Medal();
					medal.setType((int) type);
					medal.setUser(user);
					this.getMedalService().addMedal(medal);
				}

			} else {
				utce.setMedal(0);
				String message = getMedalString(utce.getMedalName(), ut.getScore(), paper.getTotalScore());
				utce.setMedalString(message);
			}

			this.setCurrentUserTest(utce);

			return "medal";


		} else {
			return "logout";
		}

	}


	/**
	 * 错题回看
	 * 
	 * @author Administrator
	 * @throws IOException
	 * @date: 2014年10月31日 下午6:39:07 <br/>
	 */
	@ActionParam(key = "usertest.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String LoadAnswerShow() throws IOException {
		User user = (User) session.getAttribute("userphone");
		if (user != null) {
			long userTestId = this.getIntValue(request.getParameter("usertest.id"), 0);
			this.usertestId = userTestId;

			UserTest ut = this.getUserTestService().getUserTestById(userTestId);
			Paper paper = ut.getPaper();

			String split = "_";
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("paper.id", paper.getId());
			itemData = new LinkedList<Map<String, Object>>();
			List<Item> itemList = this.getItemService().getItemsForPhone(searchMap);

			if (itemList != null && itemList.size() > 0) {
				for (Item item : itemList) {

					Map<String, Object> data = SerializeEntity.item2Map(item, split);

					if (item.getIsGroup()) {
						List<Item> childItemList = this.getItemService().getItemsByItem(item.getId());
						if (childItemList != null && childItemList.size() > 0) {
							List<Map<String, Object>> childdataList = new LinkedList<Map<String, Object>>();
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
					itemData.add(data);
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usertest.id", userTestId);
			List<UserTestItem> lstUtis = this.getUserTestItemService().getUserTestItem(map, null, -1, -1);

			currentAnswer = new HashMap<Integer, Object>();
			for (UserTestItem uti : lstUtis) {
				int itemId = uti.getItem().getId();
				if (!currentAnswer.containsKey(itemId)) {
					// 填空
					if (uti.getItem().getItemType().getModelType() == Dictionary.ITEM_MODEL_TYPE_PACK) {
						HashMap<Short, String> htll = new HashMap<Short, String>();
						htll.put(uti.getItemAnswer().getInx(), uti.getContent());
						currentAnswer.put(itemId, htll);
					} else {
						currentAnswer.put(itemId, uti.getContent());
					}
				} else {
					// 填空
					if (uti.getItem().getItemType().getModelType() == Dictionary.ITEM_MODEL_TYPE_PACK) {
						@SuppressWarnings("unchecked")
						HashMap<Short, String> htll = (HashMap<Short, String>) currentAnswer.get(itemId);
						htll.put(uti.getItemAnswer().getInx(), uti.getContent());
					}
				}
			}

			return "answershow";
		} else {
			return "logout";
		}
	}

	private String getMedalString(String type, double score, short totalScore) {
		if ((totalScore * 0.8) > score && score >= (totalScore * 0.5)) {
			return type + "一般,";
		} else if ((totalScore * 0.5) > score && score >= (totalScore * 0.2)) {
			return type + "较差";
		} else if ((totalScore * 0.2) > score) {
			return type + "很差";
		} else {
			return type + "很好";
		}
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
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

	public IGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(IGradeService gradeService) {
		this.gradeService = gradeService;
	}

	public IKnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}

	public ITestPaperItemService getTestPaperItemService() {
		return testPaperItemService;
	}

	public void setTestPaperItemService(ITestPaperItemService testPaperItemService) {
		this.testPaperItemService = testPaperItemService;
	}

	public IUserTestService getUserTestService() {
		return userTestService;
	}

	public void setUserTestService(IUserTestService userTestService) {
		this.userTestService = userTestService;
	}

	public IUserTestItemService getUserTestItemService() {
		return userTestItemService;
	}

	public void setUserTestItemService(IUserTestItemService userTestItemService) {
		this.userTestItemService = userTestItemService;
	}

	public List<Paper> getLstPapers() {
		return lstPapers;
	}

	public void setLstPapers(List<Paper> lstPapers) {
		this.lstPapers = lstPapers;
	}

	public HashMap<Integer, UserTest> getCureentTest() {
		return cureentTest;
	}

	public void setSubmitMap(Map<String, Object> submitMap) {
		this.submitMap = submitMap;
	}

	public Map<String, Object> getSubmitMap() {
		return submitMap;
	}

	public void setCureentTest(HashMap<Integer, UserTest> cureentTest) {
		this.cureentTest = cureentTest;
	}

	public List<Map<String, Object>> getItemData() {
		return itemData;
	}

	public void setItemData(List<Map<String, Object>> itemData) {
		this.itemData = itemData;
	}

	public int getGroupPaperId() {
		return groupPaperId;
	}

	public void setGroupPaperId(int groupPaperId) {
		this.groupPaperId = groupPaperId;
	}

	public IUserKnowledgeDegreeService getUserKnowledgeDegreeService() {
		return userKnowledgeDegreeService;
	}

	public void setUserKnowledgeDegreeService(IUserKnowledgeDegreeService userKnowledgeDegreeService) {
		this.userKnowledgeDegreeService = userKnowledgeDegreeService;
	}

	public IUserTextbookcapterDegreeService getUserTextbookcapterDegreeService() {
		return userTextbookcapterDegreeService;
	}

	public void setUserTextbookcapterDegreeService(IUserTextbookcapterDegreeService userTextbookcapterDegreeService) {
		this.userTextbookcapterDegreeService = userTextbookcapterDegreeService;
	}

	public Long getUsertestId() {
		return usertestId;
	}

	public void setUsertestId(Long usertestId) {
		this.usertestId = usertestId;
	}

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public int getUsertId() {
		return usertId;
	}

	public void setUsertId(int usertId) {
		this.usertId = usertId;
	}

	public short getClientType() {
		return clientType;
	}

	public void setClientType(short clientType) {
		this.clientType = clientType;
	}

	public UserTestCountEx getCurrentUserTest() {
		return currentUserTest;
	}

	public void setCurrentUserTest(UserTestCountEx currentUserTest) {
		this.currentUserTest = currentUserTest;
	}

	public Map<Integer, Object> getCurrentAnswer() {
		return currentAnswer;
	}

	public void setCurrentAnswer(Map<Integer, Object> currentAnswer) {
		this.currentAnswer = currentAnswer;
	}

	public IMedalService getMedalService() {
		return medalService;
	}

	public void setMedalService(IMedalService medalService) {
		this.medalService = medalService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
