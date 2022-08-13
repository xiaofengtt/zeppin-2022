package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.Result;

public interface IResultDao extends IBaseDao<Result, Integer> {
	
	public int getResultCount(int paperid,int pid,int tcid,int tsid, int qid,int aid);
	
	public int getResultCount(int paperid,int pid,int tcid,int tsid, int qid,int aid,int classes);
	
	public List getResultScore(int submit, int psq);

}
