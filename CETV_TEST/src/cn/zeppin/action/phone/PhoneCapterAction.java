/** 
 * Project Name:CETV_TEST 
 * File Name:PhoneCapterAction.java 
 * Package Name:cn.zeppin.action.phone 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.phone;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.zeppin.access.TextbookCapterEx;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Textbook;
import cn.zeppin.entity.TextbookCapter;
import cn.zeppin.entity.User;
import cn.zeppin.entity.UserTextbook;
import cn.zeppin.entity.UserTextbookcapterDegree;
import cn.zeppin.service.api.ITextBookService;
import cn.zeppin.service.api.ITextbookCapterService;
import cn.zeppin.service.api.IUserTextbookService;
import cn.zeppin.service.api.IUserTextbookcapterDegreeService;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: PhoneCapterAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月23日 上午11:51:15 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class PhoneCapterAction extends BaseAction {

	// 章节信息

	/**
	 * 
	 */
	private static final long serialVersionUID = 8864803363276018315L;

	private ITextBookService textbookService;
	private ITextbookCapterService textbookCapterService;
	private IUserTextbookcapterDegreeService userTextbookcapterDegreeService;
	private IUserTextbookService userTextbookService;

	private List<TextbookCapterEx> lstTextbookCs;
	private HashMap<Integer, Float> currentDegree;
	private int subjectId;

	/**
	 * 加载出教材的所有章节，并加载出对应章节所完成试卷回答的信息
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午12:03:50 <br/>
	 */
	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	public String LoadCapter() {

		User user = (User) session.getAttribute("userphone");
		if (user != null) {
			int subjectId = this.getIntValue(request.getParameter("subject.id"), 0);
			this.subjectId = subjectId;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user.id", user.getId());
			map.put("grade.id", user.getGrade().getId());
			map.put("subject.id", subjectId);

			UserTextbook userTB = this.getUserTextbookService().getUserTextbookByMap(map);

			if (userTB != null) {
				Textbook textbook = userTB.getTextbook();

				lstTextbookCs = new LinkedList<TextbookCapterEx>();

				HashMap<String, Object> childCount = new HashMap<String, Object>();
				childCount.put("textbook", textbook.getId());
				childCount.put("level", 1);

				int childIntCount = this.getTextbookCapterService().getCountByParas(childCount);

				if (childIntCount > 0) {
					List<TextbookCapter> lstTextbookCapters = textbookCapterService.getAllTextbookCapter(0, childIntCount, "", childCount);

					for (TextbookCapter capter : lstTextbookCapters) {

						TextbookCapterEx capterEx = new TextbookCapterEx();
						capterEx.setId(capter.getId());
						capterEx.setName(capter.getName());

						boolean hasChildCapter = textbookCapterService.hasChild(capter.getId());
						if (hasChildCapter) {
							p_loadCapter(capter, capterEx);
						}
						this.lstTextbookCs.add(capterEx);

					}

					// 查询当前教材对应章节已经完成情况
					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("user.id", user.getId());
					searchMap.put("textbook.id", textbook.getId());

					List<UserTextbookcapterDegree> lstDegrees = this.getUserTextbookcapterDegreeService().getUserTextbookcapterDegreeByMap(searchMap, "", -1, -1);

					currentDegree = new HashMap<Integer, Float>();
					for (UserTextbookcapterDegree utDegree : lstDegrees) {

						if (utDegree.getUserTest() != null) {
							if (utDegree.getUserTest().getScore() != null && utDegree.getUserTest().getPaper().getTotalScore() != null && utDegree.getUserTest().getPaper().getTotalScore() > 0) {

								double f = utDegree.getUserTest().getScore() / utDegree.getUserTest().getPaper().getTotalScore();
								float ff = Utlity.getFloat2(f, 2);

								if (currentDegree.containsKey(utDegree.getTextbookCapter().getId())) {
									float tf = currentDegree.get(utDegree.getTextbookCapter().getId());
									if (ff > tf) {
										currentDegree.put(utDegree.getTextbookCapter().getId(), ff);
									}
								} else {
									currentDegree.put(utDegree.getTextbookCapter().getId(), ff);
								}

							} else {
								if (!currentDegree.containsKey(utDegree.getTextbookCapter().getId())) {
									currentDegree.put(utDegree.getTextbookCapter().getId(), 0f);
								}
							}
						} else {
							if (!currentDegree.containsKey(utDegree.getTextbookCapter().getId())) {
								currentDegree.put(utDegree.getTextbookCapter().getId(), 0f);
							}
						}

					}

				}

				return "capter";
			} else {
				return "logout";
			}
		} else {
			return "logout";
		}

	}

	public String formatDouble(double s) {
		DecimalFormat fmt = new DecimalFormat("#0%");
		return fmt.format(s);
	}

	private void p_loadCapter(TextbookCapter capter, TextbookCapterEx capterEx) {

		HashMap<String, Object> childCount = new HashMap<String, Object>();
		childCount.put("textbookCapter", capter.getId());

		int totalCount = textbookCapterService.getCountByParas(childCount);
		List<TextbookCapter> lstTextbookCapters = textbookCapterService.getAllTextbookCapter(0, totalCount, "", childCount);

		for (TextbookCapter cap : lstTextbookCapters) {

			TextbookCapterEx capEx = new TextbookCapterEx();
			capEx.setId(cap.getId());
			capEx.setName(cap.getName());

			boolean hasChildCapter = textbookCapterService.hasChild(cap.getId());
			if (hasChildCapter) {
				p_loadCapter(cap, capEx);
			}
			capterEx.getChilds().add(capEx);
		}

	}

	public ITextBookService getTextbookService() {
		return textbookService;
	}

	public void setTextbookService(ITextBookService textbookService) {
		this.textbookService = textbookService;
	}

	public ITextbookCapterService getTextbookCapterService() {
		return textbookCapterService;
	}

	public void setTextbookCapterService(ITextbookCapterService textbookCapterService) {
		this.textbookCapterService = textbookCapterService;
	}

	public IUserTextbookcapterDegreeService getUserTextbookcapterDegreeService() {
		return userTextbookcapterDegreeService;
	}

	public void setUserTextbookcapterDegreeService(IUserTextbookcapterDegreeService userTextbookcapterDegreeService) {
		this.userTextbookcapterDegreeService = userTextbookcapterDegreeService;
	}

	public List<TextbookCapterEx> getLstTextbookCs() {
		return lstTextbookCs;
	}

	public void setLstTextbookCs(List<TextbookCapterEx> lstTextbookCs) {
		this.lstTextbookCs = lstTextbookCs;
	}

	public HashMap<Integer, Float> getCurrentDegree() {
		return currentDegree;
	}

	public void setCurrentDegree(HashMap<Integer, Float> currentDegree) {
		this.currentDegree = currentDegree;
	}

	public IUserTextbookService getUserTextbookService() {
		return userTextbookService;
	}

	public void setUserTextbookService(IUserTextbookService userTextbookService) {
		this.userTextbookService = userTextbookService;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

}
