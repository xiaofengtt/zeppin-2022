package com.whaty.platform.entity.service.information;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteAnswer;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.exception.EntityException;

/**
 * 投票
 */
public interface PeVotePaperService {

	public void operateRadioAnswer(PrVoteAnswer answer,String item);
	
	public void operateCheckBoxAnswer(PrVoteAnswer answer, String[] items);
	
	public void copyVotePaper(PeVotePaper votePaper,PeProApplyno peProApplyno);
	
	public void saveRecord(PrVoteRecord record,List<PrVoteQuestion> questList,HttpServletRequest request,String[]voteArray)throws EntityException;
}
