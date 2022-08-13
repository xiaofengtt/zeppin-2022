package cn.zeppin.dao.impl;

import cn.zeppin.dao.IAnswerDao;
import cn.zeppin.entity.Answer;

public class AnswerDaoImpl extends BaseDaoImpl<Answer, Integer> implements IAnswerDao {

	@Override
	public void deleteByPaperId(int paperid) {
		String hql = "delete Answer where psq=" + paperid;
		this.executeHSQL(hql);
	}

}
