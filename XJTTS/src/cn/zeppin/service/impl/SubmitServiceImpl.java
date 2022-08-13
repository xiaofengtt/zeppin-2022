package cn.zeppin.service.impl;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.ISubmitDao;
import cn.zeppin.dao.impl.SubmitDaoImpl;
import cn.zeppin.entity.Submit;
import cn.zeppin.service.ISubmitService;

public class SubmitServiceImpl extends BaseServiceImpl<Submit, Integer> implements ISubmitService {
	
	private ISubmitDao submitDao;

	public ISubmitDao getSubmitDao() {
		return submitDao;
	}

	public void setSubmitDao(ISubmitDao submitDao) {
		this.submitDao = submitDao;
	}

	@Override
	public Submit getSubmitByAll(int valuator, int psq, int project, short subject, int trainingCollege, int creater) {
		return this.getSubmitDao().getSubmitByAll(valuator, psq, project, subject, trainingCollege, creater);
	}

	@Override
	public int getPsqSubmitCount(HashMap<String, String> map) {
		return  this.getSubmitDao().getPsqSubmitCount(map);
	}

	@Override
	public int getExpertPsqSubmitCount(HashMap<String, String> map) {
		return  this.getSubmitDao().getExpertPsqSubmitCount(map);
	}
	
	@Override
	public List<Submit> getPsqSubmit(HashMap<String, String> map, int start, int length) {
		return  this.getSubmitDao().getPsqSubmit(map, start, length);
	}
	
	public List<Submit> getSubmitByGroup(int paperid){
		return this.getSubmitDao().getSubmitByGroup(paperid);
	}
	
	public List<Submit> getSubmitByParams(HashMap<String, String> mapParams){
		return this.getSubmitDao().getSubmitByParams(mapParams);
	}
	
	public List getExpertSubmitByParams(HashMap<String, String> mapParams){
		return this.getSubmitDao().getExpertSubmitByParams(mapParams);
	}
	
	public int getSubmitCountByProjectPsq(int psq, int projectId){
		
		return this.getSubmitDao().getSubmitCountByProjectPsq(psq, projectId);
	}

	@Override
	public int getReportPsqSubmitCount(HashMap<String, String> map) {
		return this.getSubmitDao().getReportPsqSubmitCount(map);
	}
	

}
