package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.Question;

public interface IQuestionDao extends IBaseDao<Question, Integer> {
	public void deleteByPaperId(int paperid);
	public List getPsqQuestion(int paperid);
	public List getPsqNotCountQuestion(int paperid);
}
