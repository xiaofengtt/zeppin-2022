package cn.zeppin.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.zeppin.dao.IAnswerDao;
import cn.zeppin.dao.IPsqDao;
import cn.zeppin.dao.IQuestionDao;
import cn.zeppin.entity.Answer;
import cn.zeppin.entity.Psq;
import cn.zeppin.entity.Question;
import cn.zeppin.service.IPsqService;

public class PsqServiceImpl extends BaseServiceImpl<Psq, Integer> implements IPsqService {

	private IPsqDao psqDao;
	private IQuestionDao questionDao;
	private IAnswerDao answerDao;

	public IPsqDao getPsqDao() {
		return psqDao;
	}

	public void setPsqDao(IPsqDao psqDao) {
		this.psqDao = psqDao;
	}

	public IQuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(IQuestionDao questionDao) {
		this.questionDao = questionDao;
	}
	
	public IAnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(IAnswerDao answerDao) {
		this.answerDao = answerDao;
	}
	
	public int getSubmitCount(int psqid,int projectId,int trainingSubjectId,int trainingCollegeId){
		return this.psqDao.getSubmitCount(psqid, projectId, trainingSubjectId, trainingCollegeId);
	}
	
	public Psq copy(Psq paper){
		Psq npaper = new Psq();
		npaper.setAbout(paper.getAbout());
		npaper.setClosing(paper.getClosing());
		npaper.setCreator(paper.getCreator());
		npaper.setCreattime(new Timestamp(new Date().getTime()));
		npaper.setGotourl(paper.getGotourl());
		npaper.setStatus((short) 1);
		npaper.setSurveydata(paper.getSurveydata());
		npaper.setTitle(paper.getTitle());
		npaper.setType(paper.getType());
		npaper.setTheway(paper.getTheway());
		npaper = this.add(npaper);
		Set<Question> qSet = paper.getQuestions();
		for(Question q : qSet){
			Question nq = new Question();
			nq.setArrange(q.getArrange());
			nq.setHint(q.getHint());
			nq.setInx(q.getInx());
			nq.setIsCount(q.getIsCount());
			nq.setIsmust(q.getIsmust());
			nq.setName(q.getName());
			nq.setPsq(npaper);
			nq.setScale(q.getScale());
			nq.setType(q.getType());
			nq = this.questionDao.add(nq);
			Set<Answer> aSet = q.getAnswers();
			for(Answer a : aSet){
				Answer na = new Answer();
				na.setAbout(a.getAbout());
				na.setInx(a.getInx());
				na.setIsdefault(a.getIsdefault());
				na.setIsright(a.getIsright());
				na.setJump(a.getJump());
				na.setName(a.getName());
				na.setPic(a.getPic());
				na.setPsq(npaper);
				na.setQuestion(nq);
				na.setScore(a.getScore());
				na = this.answerDao.add(na);
			}
		}
		return npaper;
	}
	
	@Override
	public List<Psq> getPsqByType(short type) {
		return this.getPsqDao().getPsqByType(type);
	}

	@Override
	public List<Psq> getPsqByTypePage(short type, Map<String,Object> map, int offset, int length) {
		return this.getPsqDao().getPsqByTypePage(type, map, offset, length);
	}

	@Override
	public int getPsqByTypeCount(short type, Map<String,Object> map) {
		return this.getPsqDao().getPsqByTypeCount(type, map);
	}

	@Override
	public HashMap<String, String[]> getPsqPaper(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqPaper(map);
	}

	@Override
	public HashMap<String, String[]> getPsqExpertPaper(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqExpertPaper(map);
	}
	
	@Override
	public List getPsqSummary(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqSummary(map);
	}

	@Override
	public List getPsqContrast(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqContrast(map);
	}

	@Override
	public List getPsqManyiLv(HashMap<String, String> map, String tableName) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqManyiLv(map, tableName);
	}
	
	@Override
	public List getExpertPsqManyiLv(HashMap<String, String> map, String tableName) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getExpertPsqManyiLv(map, tableName);
	}
	
	@Override
	public List getPsqSearchTraining(String projectId) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqSearchTraining(projectId);
	}
	
	/**
	 * 根据map获取试卷列表
	 * @param map
	 * @return
	 */
	public List searchPsqByParams(HashMap<String, String> searchMap){
		return this.getPsqDao().searchPsqByParams(searchMap);
	}

}
