package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.Result;
import cn.zeppin.entity.Submit;

public interface IResultService extends IBaseService<Result, Integer> {

	public int getResultCount(int paperid,int pid,int tcid,int tsid, int qid,int aid);
	
	public int getResultCount(int paperid,int pid,int tcid,int tsid, int qid,int aid,int classes);
	
	public List getResultScore(int submit, int psq);
	
//	public void addResult(String submitdata,int teacherTotal,String paper_id,Submit submit);
	
}
