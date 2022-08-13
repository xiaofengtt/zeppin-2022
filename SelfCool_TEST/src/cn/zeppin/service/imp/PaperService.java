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

import cn.zeppin.dao.api.IPaperDAO;
import cn.zeppin.dao.api.ITestPaperSectionDAO;
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
public class PaperService implements IPaperService {

	private IPaperDAO paperDAO;
	private ITestPaperSectionDAO testPaperSectionDAO;

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

}
