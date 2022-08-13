package cn.zeppin.dao.impl;

import java.util.List;

import cn.zeppin.dao.IQuestionDao;
import cn.zeppin.entity.Question;

public class QuestionDaoImpl extends BaseDaoImpl<Question, Integer> implements IQuestionDao {

	@Override
	public void deleteByPaperId(int paperid) {
		String hql = "delete Question where psq=" + paperid;
		this.executeHSQL(hql);
	}
	
	public List getPsqQuestion(int paperid){
		String hql ="from Question where psq="+ paperid+" and isCount=1";
		return this.getListByHSQL(hql);
	}

	public List getPsqNotCountQuestion(int paperid){
		String hql ="from Question where psq="+ paperid+" and isCount=0 and type>0 ";
		return this.getListByHSQL(hql);
	}
}
