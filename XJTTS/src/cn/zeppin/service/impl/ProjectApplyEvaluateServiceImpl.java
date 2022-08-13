package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectApplyDao;
import cn.zeppin.dao.IProjectApplyEvaluateDao;
import cn.zeppin.dao.IProjectExpertDao;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectApplyEvaluate;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.service.IProjectApplyEvaluateService;

public class ProjectApplyEvaluateServiceImpl extends BaseServiceImpl<ProjectApplyEvaluate, Integer> implements IProjectApplyEvaluateService {

	private IProjectApplyEvaluateDao projectApplyEvaluateDao;
	private IProjectApplyDao projectApplyDao;
	private IProjectExpertDao projectExpertDao;
	
	public IProjectApplyEvaluateDao getProjectApplyEvaluateDao() {
		return projectApplyEvaluateDao;
	}

	public void setProjectApplyEvaluateDao(IProjectApplyEvaluateDao projectApplyEvaluateDao) {
		this.projectApplyEvaluateDao = projectApplyEvaluateDao;
	}
	
	public IProjectApplyDao getProjectApplyDao() {
		return projectApplyDao;
	}

	public void setProjectApplyDao(IProjectApplyDao projectApplyDao) {
		this.projectApplyDao = projectApplyDao;
	}
	
	public IProjectExpertDao getProjectExpertDao() {
		return projectExpertDao;
	}

	public void setProjectExpertDao(IProjectExpertDao projectExpertDao) {
		this.projectExpertDao = projectExpertDao;
	}
	
	public ProjectApplyEvaluate getProjectApplyEvaluateByAll(int paid,int expertid){
		return this.projectApplyEvaluateDao.getProjectApplyEvaluateByAll(paid,expertid);
	}
	
	public ProjectApplyEvaluate getProjectApplyEvaluateByParams(int project , int subject , int trainingCollege,int expert){
		return this.projectApplyEvaluateDao.getProjectApplyEvaluateByParams(project,subject,trainingCollege,expert);
	}
	
	public int checkProjectApplyEvaluate(int paid,int expertid){
		return this.projectApplyEvaluateDao.checkProjectApplyEvaluate(paid,expertid);
	}
	
	public void deleteByProjectApply(int id){
		this.projectApplyEvaluateDao.deleteByProjectApply(id);
	}
	
	public int getCountForExpert (Map<String,Object> searchMap){
		return this.projectApplyEvaluateDao.getCountForExpert(searchMap);
	}

	public List<ProjectApplyEvaluate> getProjectApplyEvaluateListByParams(HashMap<String,String> searchMap){
		return this.projectApplyEvaluateDao.getProjectApplyEvaluateListByParams(searchMap);
	}
	
	public void addProjectApplyEvaluates(String paids , String expertids){
		HashMap<String,String> searchMap = new HashMap<String,String>();
		searchMap.put("projectApplys", paids);
		searchMap.put("projectExperts", expertids);
		List<ProjectApplyEvaluate> lipae = this.projectApplyEvaluateDao.getProjectApplyEvaluateListByParams(searchMap);
		
		HashMap<String,Object> paSearchMap = new HashMap<String,Object>();
		paSearchMap.put("ids", paids);
		List<ProjectApply> lipa = this.projectApplyDao.getProjectApplyByParams(paSearchMap, null, null);
		
		HashMap<String,String> peSearchMap = new HashMap<String,String>();
		peSearchMap.put("ids", expertids);
		List<ProjectExpert> lipe = this.projectExpertDao.getProjectExpertListByParams(peSearchMap);
		
		HashMap<Integer,List<Integer>> existMap = new HashMap<Integer,List<Integer>>();
		if(lipae!=null && lipae.size()>0){
			for(ProjectApplyEvaluate pae : lipae){
				Integer paid = pae.getProjectApply().getId();
				Integer peid = pae.getProjectExpert().getId();
				if(existMap.get(paid) != null){
					existMap.get(paid).add(peid);
				}else{
					List<Integer> lipeid = new ArrayList<Integer>();
					lipeid.add(peid);
					existMap.put(paid, lipeid);
				}
			}
		}
		
		for(ProjectApply pa : lipa){
			Integer paid = pa.getId();
			if(existMap.get(paid)!=null){
				List<Integer> peids = existMap.get(paid);
				for(ProjectExpert pe : lipe){
					if(!peids.contains(pe.getId())){
						ProjectApplyEvaluate npae = new ProjectApplyEvaluate();
						npae.setProjectApply(pa);
						npae.setProjectExpert(pe);
						npae.setStatus((short) 0);
						this.add(npae);
					}
				}
			}else{
				for(ProjectExpert pe : lipe){
					ProjectApplyEvaluate npae = new ProjectApplyEvaluate();
					npae.setProjectApply(pa);
					npae.setProjectExpert(pe);
					npae.setStatus((short) 0);
					this.add(npae);
				}
			}
		}
		
		
	}
	
	@Override
	public ProjectApplyEvaluate add(ProjectApplyEvaluate t) {
		return projectApplyEvaluateDao.add(t);
	}

	@Override
	public ProjectApplyEvaluate update(ProjectApplyEvaluate t) {
		return projectApplyEvaluateDao.update(t);
	}

	@Override
	public void delete(ProjectApplyEvaluate t) {
		projectApplyEvaluateDao.delete(t);
	}

	@Override
	public ProjectApplyEvaluate load(Integer id) {
		return projectApplyEvaluateDao.load(id);
	}

	@Override
	public ProjectApplyEvaluate get(Integer id) {
		return projectApplyEvaluateDao.get(id);
	}

	@Override
	public List<ProjectApplyEvaluate> loadAll() {
		return projectApplyEvaluateDao.loadAll();
	}

	@Override
	public List<ProjectApplyEvaluate> findAll() {
		return projectApplyEvaluateDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		return projectApplyEvaluateDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectApplyEvaluate> getListForPage(String hql, int offset, int length) {
		return projectApplyEvaluateDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		projectApplyEvaluateDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectApplyEvaluate> getListByHSQL(String hql) {
		return projectApplyEvaluateDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		return projectApplyEvaluateDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		projectApplyEvaluateDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		return projectApplyEvaluateDao.getListPage(sql, offset, length, objParas);
	}


}
