/** 
 * Project Name:Self_Cool 
 * File Name:PaperService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemTypeDAO;
import cn.zeppin.dao.api.IPaperDAO;
import cn.zeppin.dao.api.ITestPaperSectionDAO;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.service.api.IPaperService;

/**
 * ClassName: PaperService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年9月2日 下午3:27:49 <br/>
 * 
 * @author jiangfei
 * @version
 * @since JDK 1.7
 */
@SuppressWarnings("unchecked")
public class PaperService implements IPaperService {

	private IPaperDAO paperDAO;
	private ITestPaperSectionDAO testPaperSectionDAO;
	private IItemTypeDAO itemTypeDAO;

	public IPaperDAO getPaperDAO() {
		return paperDAO;
	}

	public void setPaperDAO(IPaperDAO paperDAO) {
		this.paperDAO = paperDAO;
	}
	
	public ITestPaperSectionDAO getTestPaperSectionDAO() {
		return testPaperSectionDAO;
	}

	public void setTestPaperSectionDAO(ITestPaperSectionDAO testPaperSectionDAO) {
		this.testPaperSectionDAO = testPaperSectionDAO;
	}

	public IItemTypeDAO getItemTypeDAO() {
		return itemTypeDAO;
	}

	public void setItemTypeDAO(IItemTypeDAO itemTypeDAO) {
		this.itemTypeDAO = itemTypeDAO;
	}

	/**
	 * 添加一个试卷
	 */
	@Override
	public void addPaper(Paper paper, List<TestPaperSection> lstSections) {
		this.getPaperDAO().save(paper);
		if (lstSections != null && lstSections.size() > 0) {
			for (TestPaperSection pasection : lstSections) {
				pasection.setPaper(paper);
				this.getTestPaperSectionDAO().save(pasection);
			}
		}
	}

	/**
	 * 试卷平均分
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param paperId
	 * @return
	 */
	public float getPaperAverage(int paperId){
		return this.getPaperDAO().getPaperAverage(paperId);
	}
	
	/**
	 * 获取一个试卷
	 */
	@Override
	public Paper getPaperById(int id) {
		return this.getPaperDAO().get(id);
	}

	/**
	 * 更新试卷
	 * 
	 * @author Administrator
	 * @date: 2014年9月2日 下午6:32:27 <br/>
	 * @param paper
	 */
	public void updatePaper(Paper paper,List<TestPaperSection> lstSections) {
		this.getPaperDAO().updatePaper(paper);
		if (lstSections != null && lstSections.size() > 0) {
			for (TestPaperSection pasection : lstSections) {
				this.getTestPaperSectionDAO().update(pasection);
			}
		}
		this.getPaperDAO().updatePaperItem(paper);
		
	}

	/**
	 * 试卷个数
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param searchMap
	 * @return
	 */
	public int searchPaperCount(Map<String, Object> searchMap) {
		return this.getPaperDAO().searchPaperCount(searchMap);
	}

	/**
	 * 试卷列表
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:53 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<Paper> searchPaper(Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		return this.getPaperDAO().searchPaper(searchMap, sorts, offset, pagesize);
	}

	@Override
	public void addPaper(Paper paper, Map<String, Object> itemMap) {
		// TODO Auto-generated method stub
		this.getPaperDAO().save(paper);//先保存试卷
		
		if (itemMap.get("data") != null) {

			List<Map<String, Object>> listMapData = (List<Map<String, Object>>) itemMap.get("data");
//			List<TestPaperSection> lstSections = new LinkedList<TestPaperSection>();
//			List<TestPaperSection> childLstSections = new LinkedList<TestPaperSection>();

			for (Map<String, Object> secMap : listMapData) {

				Short inx = Short.valueOf(secMap.get("inx").toString());
				Short level = Short.valueOf(secMap.get("level").toString());//增加level参数
				String sectionName = secMap.get("name").toString();
//				Integer itemTypeId = this.getIntValue(secMap.get("itemType.id").toString());
				Integer itemTypeId = -1;
				if(secMap.get("itemType.id") != null && secMap.get("itemType.id").toString().length() != 0){
					itemTypeId = Integer.valueOf(secMap.get("itemType.id").toString());
				}
				ItemType itemType = this.itemTypeDAO.get(itemTypeId);

				TestPaperSection testPaperSection = new TestPaperSection();
				testPaperSection.setInx(inx);
				testPaperSection.setName(sectionName);
//				testPaperSection.setLevel((short) 1);
				testPaperSection.setLevel(level);
				testPaperSection.setItemType(itemType);
				testPaperSection.setPaper(paper);
//				this.testPaperSectionService.addTestPaperSection(testPaperSection);
				this.testPaperSectionDAO.save(testPaperSection);
//				lstSections.add(testPaperSection);
				
				List<Map<String, Object>> childMapData = (List<Map<String, Object>>)secMap.get("data");
				if(childMapData != null && !childMapData.isEmpty()){
					for(Map<String, Object> childSecMap: childMapData){
						Short childInx = Short.valueOf(childSecMap.get("inx").toString());
						Short childLevel = Short.valueOf(childSecMap.get("level").toString());//增加level参数
						String childSectionName = childSecMap.get("name").toString();
//						Integer childItemTypeId = this.getIntValue(childSecMap.get("itemType.id").toString());
						Integer childItemTypeId = -1;
						if(childSecMap.get("itemType.id") != null && childSecMap.get("itemType.id").toString().length() != 0){
							childItemTypeId = Integer.valueOf(childSecMap.get("itemType.id").toString());
						}
//						ItemType childItemType = this.getItemTypeService().getItemTypeById(childItemTypeId);
						ItemType childItemType = this.itemTypeDAO.get(childItemTypeId);

						TestPaperSection cTestPaperSection = new TestPaperSection();
						cTestPaperSection.setInx(childInx);
						cTestPaperSection.setName(childSectionName);
//						testPaperSection.setLevel((short) 1);
						cTestPaperSection.setLevel(childLevel);
						cTestPaperSection.setItemType(childItemType);
						cTestPaperSection.setTestPaperSection(testPaperSection);
						cTestPaperSection.setPaper(paper);

//						lstSections.add(testPaperSection);
//						this.testPaperSectionService.addTestPaperSection(cTestPaperSection);
						this.testPaperSectionDAO.save(cTestPaperSection);
					}
				}

			}

//			this.getPaperService().addPaper(paper, lstSections);

		} 

	}

	@Override
	public void updatePapers(Paper paper, Map<String, Object> itemMap) {
		// TODO Auto-generated method stub
		this.getPaperDAO().updatePaper(paper);
		if (itemMap.get("data") != null) {

			List<Map<String, Object>> listMapData = (List<Map<String, Object>>) itemMap.get("data");

			for (Map<String, Object> secMap : listMapData) {
				
				Integer sectionId = 0;
				if(secMap.get("id")!=null && secMap.get("id").toString().length()!=0){
					sectionId = Integer.valueOf(secMap.get("id").toString());
				}
				
				Short inx = Short.valueOf(secMap.get("inx").toString());
				Short level = Short.valueOf(secMap.get("level").toString());//增加level参数
				String sectionName = secMap.get("name").toString();
//				Integer itemTypeId = this.getIntValue(secMap.get("itemType.id").toString());
				Integer itemTypeId = -1;
				if(secMap.get("itemType.id") != null && secMap.get("itemType.id").toString().length() != 0){
					itemTypeId = Integer.valueOf(secMap.get("itemType.id").toString());
				}
				ItemType itemType = this.itemTypeDAO.get(itemTypeId);

				TestPaperSection testPaperSection = this.testPaperSectionDAO.get(sectionId);
				testPaperSection.setInx(inx);
				testPaperSection.setName(sectionName);
				testPaperSection.setLevel(level);
				testPaperSection.setItemType(itemType);
				this.testPaperSectionDAO.update(testPaperSection);
				
				List<Map<String, Object>> childMapData = (List<Map<String, Object>>)secMap.get("data");
				if(childMapData != null && !childMapData.isEmpty()){
					for(Map<String, Object> childSecMap: childMapData){
						
						Integer childSectionId = 0;
						if(childSecMap.get("id")!=null && childSecMap.get("id").toString().length()!=0){
							childSectionId = Integer.valueOf(childSecMap.get("id").toString());
						}
						Short childInx = Short.valueOf(childSecMap.get("inx").toString());
						Short childLevel = Short.valueOf(childSecMap.get("level").toString());//增加level参数
						String childSectionName = childSecMap.get("name").toString();
//						Integer childItemTypeId = this.getIntValue(childSecMap.get("itemType.id").toString());
						Integer childItemTypeId = -1;
						if(childSecMap.get("itemType.id") != null && childSecMap.get("itemType.id").toString().length() != 0){
							childItemTypeId = Integer.valueOf(childSecMap.get("itemType.id").toString());
						}
//						ItemType childItemType = this.getItemTypeService().getItemTypeById(childItemTypeId);
						ItemType childItemType = this.itemTypeDAO.get(childItemTypeId);

						TestPaperSection cTestPaperSection = this.testPaperSectionDAO.get(childSectionId);
						cTestPaperSection.setInx(childInx);
						cTestPaperSection.setName(childSectionName);
						cTestPaperSection.setLevel(childLevel);
						cTestPaperSection.setItemType(childItemType);
						cTestPaperSection.setTestPaperSection(testPaperSection);

						this.testPaperSectionDAO.update(cTestPaperSection);
					}
				}

			}


		} 
	}

	/**
	 * 查询不同试卷类型学科试卷总数
	 * @param List<Map<String,Object>>
	 * @param searchMap
	 */
	public List<Map<String,Object>> searchPaperCountListByType(Map<String, Object> searchMap){
		return this.paperDAO.searchPaperCountListByType(searchMap);
	}
	
	/**
	 * 查询试卷某部分下的所有试题
	 * @param List<Map<String,Object>>
	 * @param searchMap
	 */
	public List<Map<String, Object>> searchTestPaperSectionItemMap(Integer sectionId){
		return this.paperDAO.searchTestPaperSectionItemMap(sectionId);
	}
}
