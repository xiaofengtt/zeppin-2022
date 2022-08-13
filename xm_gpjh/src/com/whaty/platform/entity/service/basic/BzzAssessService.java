package com.whaty.platform.entity.service.basic;


import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzSuggestion;

public interface BzzAssessService {

	public 	PeBzzSuggestion getPeBzzSuggestion(String sugid);

	public void updatepeBzzSuggestion(PeBzzSuggestion peBzzSuggestion);

	public void updatePeBzzAssess(PeBzzAssess assess);

	

}
