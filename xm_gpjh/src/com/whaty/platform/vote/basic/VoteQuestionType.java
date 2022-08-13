package com.whaty.platform.vote.basic;

import java.util.ArrayList;
import java.util.List;
import com.whaty.platform.vote.util.exception.VoteException;

public class VoteQuestionType {
	public static String SINGLE="SINGLE";
	public static String MULTI="MULTI";
	
	public static String typeShow(String type) throws VoteException
	{
		if(type!=null && type.equals(SINGLE))
			return "单选题";
		else if(type!=null && type.equals(MULTI))
			return "多选题";
		else
			throw new VoteException("vote question type error!");
	}
	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(SINGLE);
		list.add(MULTI);
		return list;
	}
}
