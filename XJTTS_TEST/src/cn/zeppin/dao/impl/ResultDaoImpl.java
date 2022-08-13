package cn.zeppin.dao.impl;

import java.util.List;

import cn.zeppin.dao.IResultDao;
import cn.zeppin.entity.Result;

public class ResultDaoImpl extends BaseDaoImpl<Result, Integer> implements IResultDao {

	public int getResultCount(int paperid,int pid,int tcid,int tsid, int qid,int aid){
		StringBuilder sb=new StringBuilder();
		sb.append("select count(*) from submit s,result r where r.submit=s.id ");
		sb.append(" and s.project=");sb.append(pid);
		sb.append(" and s.subject=");sb.append(tsid);
		sb.append(" and s.training_college=");sb.append(tcid);
		sb.append(" and s.psq=");sb.append(paperid);
		sb.append(" and r.question=");sb.append(qid);
		sb.append(" and r.answer=");sb.append(aid);
		Object result = this.getObjectBySql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}
	
	public int getResultCount(int paperid,int pid,int tcid,int tsid, int qid,int aid,int classes){
		StringBuilder sb=new StringBuilder();
		sb.append("select count(*) from submit s,result r,teacher_training_records ttr where r.submit=s.id and s.uuid = ttr.uuid ");
		sb.append(" and s.project=");sb.append(pid);
		sb.append(" and s.subject=");sb.append(tsid);
		sb.append(" and s.training_college=");sb.append(tcid);
		sb.append(" and s.psq=");sb.append(paperid);
		sb.append(" and r.question=");sb.append(qid);
		sb.append(" and r.answer=");sb.append(aid);
		if(classes > 0){
			sb.append(" and ttr.classes=").append(classes);
		}
		
		Object result = this.getObjectBySql(sb.toString(), null);
		return Integer.parseInt(result.toString());
	}
	
	public List getResultScore(int submit, int psq){
		
		StringBuilder sb=new StringBuilder();
		sb.append(" from Result r,Submit s,Question q,Psq p where 1=1 and r.submit=s.id and s.psq=p.id and r.question=q.id ");
		if(submit>0){
			sb.append(" and s.id="+submit);
		}
		if(psq>0){
			sb.append(" and s.psq="+psq);
		}
		sb.append(" and q.isCount=1");
		
		return this.getListByHSQL(sb.toString());
	}
	
}
