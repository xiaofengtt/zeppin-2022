package cn.zeppin.action.phone;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.zeppin.access.KnowledgeEx;
import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.User;
import cn.zeppin.entity.UserKnowledgeDegree;
import cn.zeppin.service.api.IGradeService;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.service.api.ISubjectService;
import cn.zeppin.service.api.IUserKnowledgeDegreeService;
import cn.zeppin.utility.Utlity;

public class PhoneKnowledgeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7712212223961520310L;

	private IKnowledgeService knowledgeService;
	private IGradeService gradeService;
	private ISubjectService subjectService;
	private IUserKnowledgeDegreeService userKnowledgeDegreeService;

	private List<KnowledgeEx> lstKnowledges;
	private HashMap<Integer, Float> currentDegree;
	private int subjectId;

	@ActionParam(key = "subject.id", type = ValueType.NUMBER)
	public String LoadKnowledge() {
		User user = (User) session.getAttribute("userphone");
		if (user != null) {
			Map<String, Object> searchMap = new HashMap<>();
			// searchMap.put("grade.id", user.getGrade().getId());
			searchMap.put("subject.id", request.getParameter("subject.id"));
			searchMap.put("level", 1);

			this.subjectId = this.getIntValue(request.getParameter("subject.id"));

			int recordCount = this.getKnowledgeService().searchKnowledgeCount(searchMap);
			List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, "", 0, recordCount);
			lstKnowledges = new LinkedList<KnowledgeEx>();

			if (knowledgeList != null && knowledgeList.size() > 0) {
				for (Knowledge kl : knowledgeList) {

					KnowledgeEx klex = new KnowledgeEx();
					klex.setId(kl.getId());
					klex.setName(kl.getName());

					// 看看下级知识点
					Map<String, Object> hasChildCount = new HashMap<String, Object>();
					hasChildCount.put("knowledge.id", kl.getId());
					boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;

					if (hasChild) {
						p_loadKnowledge(kl, klex);
					}

					lstKnowledges.add(klex);

				}

				// 查询知识点的完成情况
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user.id", user.getId());
				map.put("subject.id", request.getParameter("subject.id"));

				List<UserKnowledgeDegree> lstUkld = this.getUserKnowledgeDegreeService().getUserKnowledgeDegree(map, "", -1, -1);
				currentDegree = new HashMap<Integer, Float>();

				for (UserKnowledgeDegree ukd : lstUkld) {

					if (ukd.getUserTest() != null) {
						
						if (ukd.getUserTest().getScore() != null 
								&& ukd.getUserTest().getPaper().getTotalScore() != null 
								&& ukd.getUserTest().getPaper().getTotalScore() > 0) {

							double f = ukd.getUserTest().getScore() / ukd.getUserTest().getPaper().getTotalScore();
							float ff = Utlity.getFloat2(f, 2);

							if (currentDegree.containsKey(ukd.getKnowledge().getId())) {
								float tf = currentDegree.get(ukd.getKnowledge().getId());
								if (ff > tf) {
									currentDegree.put(ukd.getKnowledge().getId(), ff);
								}
							} else {
								currentDegree.put(ukd.getKnowledge().getId(), ff);
							}

						} else {
							if (!currentDegree.containsKey(ukd.getKnowledge().getId())) {
								currentDegree.put(ukd.getKnowledge().getId(), 0f);
							}
						}
					} else {
						if (!currentDegree.containsKey(ukd.getKnowledge().getId())) {
							currentDegree.put(ukd.getKnowledge().getId(), 0f);
						}
					}

				}

			}
			return "know";
		} else {
			return "logout";
		}

	}
	
	public String formatDouble(double s) {
		DecimalFormat fmt = new DecimalFormat("#0%");
		return fmt.format(s);
	}

	private void p_loadKnowledge(Knowledge kl, KnowledgeEx klex) {

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("knowledge.id", kl.getId());

		List<Knowledge> knowledgeList = this.getKnowledgeService().searchKnowledge(searchMap, "", -1, -1);

		if (knowledgeList != null && knowledgeList.size() > 0) {
			for (Knowledge klt : knowledgeList) {

				KnowledgeEx klext = new KnowledgeEx();
				klext.setId(klt.getId());
				klext.setName(klt.getName());

				// 看看下级知识点
				Map<String, Object> hasChildCount = new HashMap<String, Object>();
				hasChildCount.put("knowledge.id", klt.getId());
				boolean hasChild = this.getKnowledgeService().searchKnowledgeCount(hasChildCount) > 0 ? true : false;

				if (hasChild) {
					p_loadKnowledge(klt, klext);
				}

				klex.getChilds().add(klext);
			}
		}

	}

	public IKnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	public void setKnowledgeService(IKnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
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

	public IUserKnowledgeDegreeService getUserKnowledgeDegreeService() {
		return userKnowledgeDegreeService;
	}

	public void setUserKnowledgeDegreeService(IUserKnowledgeDegreeService userKnowledgeDegreeService) {
		this.userKnowledgeDegreeService = userKnowledgeDegreeService;
	}

	public List<KnowledgeEx> getLstKnowledges() {
		return lstKnowledges;
	}

	public void setLstKnowledges(List<KnowledgeEx> lstKnowledges) {
		this.lstKnowledges = lstKnowledges;
	}

	public HashMap<Integer, Float> getCurrentDegree() {
		return currentDegree;
	}

	public void setCurrentDegree(HashMap<Integer, Float> currentDegree) {
		this.currentDegree = currentDegree;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

}
