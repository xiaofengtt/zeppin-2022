package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IProjectApplyDao;
import cn.zeppin.dao.IProjectApplyExpertDao;
import cn.zeppin.dao.IProjectExpertDao;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectApplyExpert;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.service.IProjectApplyExpertService;

public class ProjectApplyExpertServiceImpl extends BaseServiceImpl<ProjectApplyExpert, Integer> implements IProjectApplyExpertService {

	private IProjectApplyExpertDao projectApplyExpertDao;
	private IProjectApplyDao projectApplyDao;
	private IProjectExpertDao projectExpertDao;

	public IProjectApplyExpertDao getProjectApplyExpertDao() {
		return projectApplyExpertDao;
	}

	public void setProjectApplyExpertDao(IProjectApplyExpertDao projectApplyExpertDao) {
		this.projectApplyExpertDao = projectApplyExpertDao;
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
	
	public ProjectApplyExpert getProjectApplyExpertByAll(int paid,int expertid){
		return this.projectApplyExpertDao.getProjectApplyExpertByAll(paid,expertid);
	}
	
	public ProjectApplyExpert getProjectApplyExpertByParams(int project , int subject , int trainingCollege,int expert){
		return this.projectApplyExpertDao.getProjectApplyExpertByParams(project,subject,trainingCollege,expert);
	}
	
	public int checkProjectApplyExpert(int paid,int expertid){
		return this.projectApplyExpertDao.checkProjectApplyExpert(paid,expertid);
	}
	
	public void deleteByProjectApply(int id){
		this.projectApplyExpertDao.deleteByProjectApply(id);
	}
	
	public int getCountForExpert (Map<String,Object> searchMap){
		return this.projectApplyExpertDao.getCountForExpert(searchMap);
	}
	
	public List<ProjectApplyExpert> getProjectApplyExpertListByParams(HashMap<String,String> searchMap){
		return this.projectApplyExpertDao.getProjectApplyExpertListByParams(searchMap);
	}
	
	public void addProjectApplyExperts(String paids , String expertids){
		HashMap<String,String> searchMap = new HashMap<String,String>();
		searchMap.put("projectApplys", paids);
		searchMap.put("projectExperts", expertids);
		List<ProjectApplyExpert> lipae = this.projectApplyExpertDao.getProjectApplyExpertListByParams(searchMap);
		
		HashMap<String,Object> paSearchMap = new HashMap<String,Object>();
		paSearchMap.put("ids", paids);
		List<ProjectApply> lipa = this.projectApplyDao.getProjectApplyByParams(paSearchMap, null, null);
		
		HashMap<String,String> peSearchMap = new HashMap<String,String>();
		peSearchMap.put("ids", expertids);
		List<ProjectExpert> lipe = this.projectExpertDao.getProjectExpertListByParams(peSearchMap);
		
		HashMap<Integer,List<Integer>> existMap = new HashMap<Integer,List<Integer>>();
		if(lipae!=null && lipae.size()>0){
			for(ProjectApplyExpert pae : lipae){
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
						ProjectApplyExpert npae = new ProjectApplyExpert();
						npae.setProjectApply(pa);
						npae.setProjectExpert(pe);
						npae.setStatus((short) 0);
						this.add(npae);
					}
				}
			}else{
				for(ProjectExpert pe : lipe){
					ProjectApplyExpert npae = new ProjectApplyExpert();
					npae.setProjectApply(pa);
					npae.setProjectExpert(pe);
					npae.setStatus((short) 0);
					this.add(npae);
				}
			}
		}
		
		
	}

	@Override
	public ProjectApplyExpert add(ProjectApplyExpert t) {
		return projectApplyExpertDao.add(t);
	}

	@Override
	public ProjectApplyExpert update(ProjectApplyExpert t) {
		return projectApplyExpertDao.update(t);
	}

	@Override
	public void delete(ProjectApplyExpert t) {
		projectApplyExpertDao.delete(t);
	}

	@Override
	public ProjectApplyExpert load(Integer id) {
		return projectApplyExpertDao.load(id);
	}

	@Override
	public ProjectApplyExpert get(Integer id) {
		return projectApplyExpertDao.get(id);
	}

	@Override
	public List<ProjectApplyExpert> loadAll() {
		return projectApplyExpertDao.loadAll();
	}

	@Override
	public List<ProjectApplyExpert> findAll() {
		return projectApplyExpertDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		return projectApplyExpertDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectApplyExpert> getListForPage(String hql, int offset, int length) {
		return projectApplyExpertDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		projectApplyExpertDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectApplyExpert> getListByHSQL(String hql) {
		return projectApplyExpertDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		return projectApplyExpertDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		projectApplyExpertDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		return projectApplyExpertDao.getListPage(sql, offset, length, objParas);
	}


}
