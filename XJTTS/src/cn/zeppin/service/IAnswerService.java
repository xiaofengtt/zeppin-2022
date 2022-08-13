package cn.zeppin.service;

import cn.zeppin.entity.Answer;

public interface IAnswerService extends IBaseService<Answer, Integer> {
	public void deleteByPaperId(int paperid);
}
