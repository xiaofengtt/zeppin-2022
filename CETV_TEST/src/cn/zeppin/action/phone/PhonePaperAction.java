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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.zeppin.access.UserTestCountEx;
import cn.zeppin.access.UserTestItemEx;
import cn.zeppin.access.UserTestKnowledgeEx;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemAnswer;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Medal;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.TestPaperItem;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.entity.TextbookCapter;
import cn.zeppin.entity.User;
import cn.zeppin.entity.UserKnowledgeDegree;
import cn.zeppin.entity.UserTest;
import cn.zeppin.entity.UserTestItem;
import cn.zeppin.entity.UserTextbookcapterDegree;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IItemService;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.IMedalService;
import cn.zeppin.service.api.IPaperService;
import cn.zeppin.service.api.ITestPaperItemService;
import cn.zeppin.service.api.ITextbookCapterService;
import cn.zeppin.service.api.IUserKnowledgeDegreeService;
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

	private Map<String, Object> submitMap;
	private List<Paper> lstPapers;
	private HashMap<Integer, UserTest> cureentTest;
	private int groupPaperId;
	private int knowledgeId;
	private int textbookId;
	private Long usertestId;

	private int paperId;
	private int usertId;
	private short clientType;
	private int subjectId;

	private Map<Integer, Object> currentAnswer;

	private UserTestCountEx currentUserTest;

	/**
	 * 加载试卷列表(初中英语和初中数学)历届真题
	 * 
	 * @author Administrator
	 * @date: 2014年10月24日 上午10:53:19 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	public String LoadPaper() {
		User user = (User) session.getAttribute("userphone");

		if (user != null) {

			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("subject.id", request.getParameter("subject.id"));
			// searchMap.put("grade.id", user.getGrade().getId());
			searchMap.put("grade.scode", user.getGrade().getScode());
			searchMap.put("type", "0,1");
			searchMap.put("status", Dictionary.PAPER_STATUS_RELEASE);

			int subjectId = this.getIntValue(request.getParameter("subject.id"), 0);
			this.subjectId = subjectId;

			lstPapers = this.getPaperService().searchPaper(searchMap, null, -1, -1);

			if (lstPapers != null && lstPapers.size() > 0) {
				cureentTest = new HashMap<Integer, UserTest>();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user.id", user.getId());
				map.put("paper.type", "0,1");

				List<UserTest> tlstut = this.getUserTestService().getUserTest(map, null, -1, -1);
				if (tlstut != null && tlstut.size() > 0) {
					for (UserTest ut : tlstut) {
						if (!cureentTest.containsKey(ut.getPaper().getId())) {
							cureentTest.put(ut.getPaper().getId(), ut);
						}
					}
				}
			}

			return "paperlist";
		} else {
			return "logout";
		}
	}

	/**
	 * 自动组卷(教材版本和知识点)
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午5:18:30 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "knowledge.id", type = ValueType.NUMBER)
	@ActionParam(key = "textbookCapter.id", type = ValueType.NUMBER)
	public String GroupPaper() {

		// ========================================
		// 流程
		// 1.先搜索出5道试题
		// 2.先创建试卷
		// 1.为试卷创建一个目录
		// 2.保存试卷
		// 3.保存试题
		// 3.入库到用户考试记录表
		// 4.入库到 知识点或者章节掌握情况表中
		// ========================================

		User user = (User) session.getAttribute("userphone");
		if (user != null) {
			int subjectId = this.getIntValue(request.getParameter("subject.id"), 0);
			int knowledgeId = this.getIntValue(request.getParameter("knowledge.id"), 0);
			int textbookCapterId = this.getIntValue(request.getParameter("textbookCapter.id"), 0);

			this.subjectId = subjectId;

			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("level", 1);
			Grade sgrade = user.getGrade();
			if (sgrade != null) {
				searchMap.put("grade.scode", sgrade.getScode());
			} else {
				searchMap.put("grade.scode", null);
			}
			Knowledge sknowledge = this.getKnowledgeService().getById(knowledgeId);
			if (sknowledge != null) {
				searchMap.put("knowledge.scode", sknowledge.getScode());
			} else {
				searchMap.put("knowledge.scode", null);
			}
			TextbookCapter stextBookCapter = this.getTextbookCapterService().getById(textbookCapterId);
			if (stextBookCapter != null) {
				searchMap.put("textbookCapter.scode", stextBookCapter.getScode());
			} else {
				searchMap.put("textbookCapter.scode", null);
			}

			searchMap.put("subject.id", subjectId);
			// searchMap.put("status", Dictionary.ITEM_STATUS_RELEASE);

			int itemCount = this.getItemService().searchItemCount(searchMap);
			if (itemCount > 0) {

				int rnd = new Random().nextInt(itemCount);
				if (rnd < 0)
					rnd = 0;
				List<Item> itemList = this.getItemService().searchItem(searchMap, "createtime desc", rnd, 5);
				int totalScore = 0;
				for (Item item : itemList) {
					int tscore = item.getDefaultScore() == null ? 0 : item.getDefaultScore();
					tscore = tscore < 0 ? 0 : tscore;
					totalScore += tscore;
				}

				Paper paper = new Paper();
				paper.setType((short) 5);
				paper.setName("自动组卷");
				paper.setSource("自动组卷");
				paper.setAnswerTime(15);
				paper.setTotalScore((short) totalScore);
				paper.setStatus(Dictionary.PAPER_STATUS_AUDITING);
				Subject sub = new Subject();
				sub.setId(subjectId);
				paper.setSubject(sub);
				paper.setGrade(sgrade);

				List<TestPaperSection> lstSections = new LinkedList<TestPaperSection>();
				TestPaperSection testPaperSection = new TestPaperSection();
				testPaperSection.setInx((short) 1);
				testPaperSection.setName("自动组卷目录");
				testPaperSection.setLevel((short) 1);
				lstSections.add(testPaperSection);

				this.getPaperService().addPaper(paper, lstSections);

				for (int i = 0; i < itemList.size(); i++) {

					Item item = itemList.get(i);
					TestPaperItem tpi = new TestPaperItem();
					tpi.setInx((short) (i + 1));
					tpi.setPaper(paper);
					tpi.setTestPaperSection(testPaperSection);
					tpi.setItem(item);

					this.getTestPaperItemService().addTestPaperItem(tpi);
				}

				// 入库到用户考试记录表
				UserTest ut = new UserTest();
				ut.setUser(user);
				ut.setPaper(paper);
				ut.setStatus(0);
				ut.setDuration(0l);
				ut.setScore(0.0);

				this.getUserTestService().addUserTest(ut);

				this.knowledgeId = knowledgeId;
				this.textbookId = textbookCapterId;
				this.groupPaperId = paper.getId();
				this.usertestId = ut.getId();
				this.clientType = Dictionary.CLIENT_TYPE_A;

				if (knowledgeId > 0) {

					UserKnowledgeDegree ukld = new UserKnowledgeDegree();
					ukld.setKnowledge(sknowledge);
					ukld.setUser(user);
					ukld.setUserTest(ut);
					ukld.setDegree(0);
					this.getUserKnowledgeDegreeService().addUserKnowledgeDegree(ukld);

					return "knowledge";

				} else if (textbookCapterId > 0) {

					UserTextbookcapterDegree utcd = new UserTextbookcapterDegree();
					utcd.setTextbookCapter(stextBookCapter);
					utcd.setUser(user);
					utcd.setUserTest(ut);
					utcd.setDegree(0);
					this.getUserTextbookcapterDegreeService().addUserTextbookcapterDegree(utcd);

					return "textbook";

				} else {
					return "nopaper";
				}
			} else {
				return "nopaper";
			}
		} else {
			return "logout";
		}

	}

	/**
	 * 智能组卷
	 * 
	 * @author Administrator
	 * @date: 2014年10月29日 下午5:20:54 <br/>
	 * @return
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String RandomPaper() {

		User user = (User) session.getAttribute("userphone");
		if (user != null) {
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("subject.id", request.getParameter("subject.id"));
			searchMap.put("grade.scode", user.getGrade().getScode());
			searchMap.put("type", "2,3,4");
			searchMap.put("status", 3);

			int subjectId = this.getIntValue(request.getParameter("subject.id"), 0);
			this.subjectId = subjectId;

			int itemCount = this.getPaperService().searchPaperCount(searchMap);
			if (itemCount > 0) {

				int rnd = new Random().nextInt(itemCount);

				if (rnd < 0)
					rnd = 0;
				List<Paper> lstps = this.getPaperService().searchPaper(searchMap, null, rnd, 1);
				if (lstps != null && lstps.size() > 0) {

					Paper paper = lstps.get(0);
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
					this.clientType = Dictionary.CLIENT_TYPE_A;

					return "paper";

				} else {
					return "nopaper";
				}
			} else {
				return "nopaper";
			}
		} else {
			return "logout";
		}

	}

	/**
	 * （初中英语与初中数学）试卷跳转
	 * 
	 * @author Administrator
	 * @date: 2014年10月29日 下午3:01:17 <br/>
	 * @return
	 */
	@ActionParam(key = "paper.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public String JumpPaper() {

		// 学生回答试卷跳转
		User user = (User) session.getAttribute("userphone");

		if (user != null) {

			int paperId = this.getIntValue(request.getParameter("paper.id"));
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
			this.clientType = Dictionary.CLIENT_TYPE_A;

			return "paper";

		} else {
			return "logout";
		}
	}

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

		//
		// type 表示 2中类型 的统计方式
		//
		// ====================================================
		//
		// 更具试卷的类型分析不同的数据
		// 1.分析得分统计
		// 2.分析答题时间统计
		//
		// 获取试卷的每题对错列表
		//
		// 分析知识点掌握情况 ==>针对试卷的情况
		//
		// ====================================================
		//

		User user = (User) session.getAttribute("userphone");
		if (user != null) {

			short type = Short.valueOf(request.getParameter("type"));
			long userTestId = this.getIntValue(request.getParameter("usertest.id"), 0);
			this.clientType = type;
			this.usertestId = userTestId;

			if (type == Dictionary.CLIENT_TYPE_A) {

				// 分析 初中数学与初中英语 类试卷
				UserTest ut = this.getUserTestService().getUserTestById(userTestId);
				Paper paper = ut.getPaper();

				UserTestCountEx utce = new UserTestCountEx();
				utce.setSubjectId(session.getAttribute("subject.id").toString());
				utce.setUsertestId(ut.getId());
				utce.setPaperId(paper.getId());

				utce.setTotalScore(paper.getTotalScore()); // 总分数
				utce.setAnswerTime(paper.getAnswerTime()); // 总时间
				utce.setScore(ut.getScore()); // 得分
				float tduration = (float) ut.getDuration() / 60;
				if (tduration < 1) {
					tduration = 1;
				}
				utce.setTime((long) tduration); // 用时

				utce.setStrScore(Utlity.getFloat2StrNormal(ut.getScore()));

				// 分析平均值
				List<Object[]> lsobject = this.getUserTestService().getUserTestAvgByPaper(paper.getId());
				if (lsobject != null && lsobject.size() > 0) {
					Object[] objs = lsobject.get(0);
					if (objs[0] != null) {
						float timeavg = Float.valueOf(objs[0].toString());
						timeavg = Utlity.getFloat2(timeavg, 1);

						utce.setAvgTime(timeavg);

						float tt = timeavg / 60;
						if (tt < 1) {
							String s = timeavg + "秒";
							utce.setStrAvgTime(s);
						} else {
							tt = Utlity.getFloat2(tt, 0);
							String s = (int) tt + "分钟";
							utce.setStrAvgTime(s);
						}

					}
					if (objs[1] != null) {
						float avgscore = Float.valueOf(objs[1].toString());
						avgscore = Utlity.getFloat2(avgscore, 1);
						utce.setAvgScore(avgscore);

						utce.setStrAvgScore(Utlity.getFloat2Str(avgscore));
					}
				}

				// 分析人数
				Map<String, Object> smap = new HashMap<String, Object>();
				smap.put("paper.id", paper.getId());
				int totalCount = this.getUserTestService().getUserTestCount(smap);

				smap.put("duration", ut.getDuration());
				int durationCount = this.getUserTestService().getUserTestCount(smap);

				smap.remove("duration");
				smap.put("score", ut.getScore());
				int scoreCount = this.getUserTestService().getUserTestCount(smap);

				float lowerscore = (float) scoreCount / totalCount;
				lowerscore = Utlity.getFloat2(lowerscore, 2);

				float lowertime = (float) durationCount / totalCount;
				lowertime = Utlity.getFloat2(lowertime, 2);

				utce.setLowerScore(lowerscore);
				utce.setLowerTime(lowertime);
				utce.setStrLowerSvore(Utlity.getFloat2Str(lowerscore));
				utce.setStrLowerTime(Utlity.getFloat2Str(lowertime));

				// 把每题记录正确与对错
				// 分析每道题的对错
				// 分析用时
				// 分析知识点

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("usertest.id", userTestId);
				List<UserTestItem> lstUtis = this.getUserTestItemService().getUserTestItem(map, null, -1, -1);

				int indexflag = 1;
				for (UserTestItem uti : lstUtis) {

					int itemId = uti.getItem().getId();
					UserTestItemEx utiex = null;
					if (!utce.getMapUti().containsKey(itemId)) {

						utiex = new UserTestItemEx();
						utiex.setItemId(itemId);
						utiex.setInx(indexflag);
						utiex.setScore(uti.getScore());

						utce.setTotalItem(utce.getTotalItem() + 1);
						if (uti.getScore() == uti.getItem().getDefaultScore()) {

							utiex.setFlag(true);
							utce.setOkItem(utce.getOkItem() + 1);

						} else {
							utiex.setFlag(false);
						}

					} else {

						utiex = utce.getMapUti().get(itemId);
						int tscore = utiex.getScore() + uti.getScore();
						if (tscore == uti.getItem().getDefaultScore()) {

							utiex.setFlag(true);
							utce.setOkItem(utce.getOkItem() + 1);

						} else {
							utiex.setFlag(false);
						}

						utiex.setScore(tscore);
					}

					// 分析知识点

					int knowledgeId = 0;
					if (uti.getItem().getParent() != null) {
						if (uti.getItem().getParent().getKnowledge() != null) {
							knowledgeId = uti.getItem().getParent().getKnowledge().getId();
						} else if (uti.getItem().getKnowledge() != null) {
							knowledgeId = uti.getItem().getKnowledge().getId();
						}
					} else {
						if (uti.getItem().getKnowledge() != null) {
							knowledgeId = uti.getItem().getKnowledge().getId();
						}
					}

					if (knowledgeId > 0) {
						if (!utce.getLstUtk().containsKey(knowledgeId)) {
							UserTestKnowledgeEx utkex = new UserTestKnowledgeEx();
							utkex.setKnowledgeId(knowledgeId);
							utkex.setKnowledgeName(uti.getItem().getKnowledge().getName());
							utkex.setTotalCount(utkex.getTotalCount() + 1);
							if (utiex.isFlag()) {
								utkex.setOkCount(utkex.getOkCount() + 1);
							}

							float fprent = Utlity.getFloat2((float) utkex.getOkCount() / utkex.getTotalCount(), 2);
							utkex.setPrecent(fprent);
							utkex.setStrPrecent(Utlity.getFloat2Str(fprent));

							utce.getLstUtk().put(knowledgeId, utkex);

						} else {
							UserTestKnowledgeEx utkex = utce.getLstUtk().get(knowledgeId);

							if (!utce.getMapUti().containsKey(itemId)) {
								utkex.setTotalCount(utkex.getTotalCount() + 1);
							}

							if (utiex.isFlag()) {
								utkex.setOkCount(utkex.getOkCount() + 1);
							}

							float fprent = Utlity.getFloat2((float) utkex.getOkCount() / utkex.getTotalCount(), 2);
							utkex.setPrecent(fprent);
							utkex.setStrPrecent(Utlity.getFloat2Str(fprent));
						}

					}

					if (!utce.getMapUti().containsKey(itemId)) {
						utce.getMapUti().put(itemId, utiex);
						indexflag++;
					}

				}

				this.setCurrentUserTest(utce);
				return "standpaper";
			} else {

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

			}
		} else {
			return "logout";
		}

	}

	/**
	 * 加载近期得分信息
	 * 
	 * @author Administrator
	 * @date: 2014年10月31日 下午2:38:36 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	public void LoadScore() {

		User user = (User) session.getAttribute("userphone");
		ActionResult result = new ActionResult();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user.id", user.getId());
		map.put("paper.type", Dictionary.PAPER_TYPE_ALL);
		map.put("paper.subject.id", request.getParameter("subject.id"));

		List<UserTest> tlstut = this.getUserTestService().getUserTest(map, null, 0, 15);

		List<Map<String, Object>> resLst = new ArrayList<Map<String, Object>>();
		for (UserTest ut : tlstut) {
			Map<String, Object> tmap = new HashMap<String, Object>();
			tmap.put("time", Utlity.timeSpanToString(ut.getStarttime()));
			tmap.put("score", Utlity.getFloat2StrNormal(ut.getScore()));

			resLst.add(tmap);

		}

		result.init(SUCCESS_STATUS, null, resLst);

		Utlity.ResponseWrite(result, dataType, response);

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

	public int getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(int knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public int getTextbookId() {
		return textbookId;
	}

	public void setTextbookId(int textbookId) {
		this.textbookId = textbookId;
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

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

}
