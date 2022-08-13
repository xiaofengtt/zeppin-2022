package cn.zeppin.dao;

import cn.zeppin.entity.Answer;

public interface IAnswerDao extends IBaseDao<Answer, Integer> {
	
	public void deleteByPaperId(int paperid);

}
