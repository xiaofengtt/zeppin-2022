package cn.zeppin.service.impl;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.IPsqDao;
import cn.zeppin.entity.Psq;
import cn.zeppin.service.IPsqService;

public class PsqServiceImpl extends BaseServiceImpl<Psq, Integer> implements IPsqService {

	private IPsqDao psqDao;

	public IPsqDao getPsqDao() {
		return psqDao;
	}

	public void setPsqDao(IPsqDao psqDao) {
		this.psqDao = psqDao;
	}

	public int getSubmitCount(int psqid,int projectId,int trainingSubjectId,int trainingCollegeId){
		return this.psqDao.getSubmitCount(psqid, projectId, trainingSubjectId, trainingCollegeId);
	}
	
	@Override
	public List<Psq> getPsqByType(short type) {
		return this.getPsqDao().getPsqByType(type);
	}

	@Override
	public List<Psq> getPsqByTypePage(short type, int offset, int length) {
		return this.getPsqDao().getPsqByTypePage(type, offset, length);
	}

	@Override
	public int getPsqByTypeCount(short type) {
		return this.getPsqDao().getPsqByTypeCount(type);
	}

	@Override
	public HashMap<String, String[]> getPsqPaper(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqPaper(map);
	}

	@Override
	public List getPsqSummary(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqSummary(map);
	}

	@Override
	public List getPsqContrast(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqContrast(map);
	}

	@Override
	public List getPsqManyiLv(HashMap<String, String> map, String tableName) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqManyiLv(map, tableName);
	}
	
	@Override
	public List getExpertPsqManyiLv(HashMap<String, String> map, String tableName) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getExpertPsqManyiLv(map, tableName);
	}
	
	@Override
	public List getPsqSearchTraining(String projectId) {
		// TODO Auto-generated method stub
		return this.getPsqDao().getPsqSearchTraining(projectId);
	}
	
	

}
