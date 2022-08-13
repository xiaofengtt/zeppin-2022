/** 
 * Project Name:ItemDatabase 
 * File Name:TestPaperSectionService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITestPaperSectionDAO;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.service.api.ITestPaperSectionService;

/**
 * ClassName: TestPaperSectionService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年9月3日 下午3:19:43 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class TestPaperSectionService implements ITestPaperSectionService {

	private ITestPaperSectionDAO testPaperSectionDAO;

	public ITestPaperSectionDAO getTestPaperSectionDAO() {
		return testPaperSectionDAO;
	}

	public void setTestPaperSectionDAO(ITestPaperSectionDAO testPaperSectionDAO) {
		this.testPaperSectionDAO = testPaperSectionDAO;
	}

	/**
	 * 计算试卷目录个数
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public int searchTestPaperSectionsCount(Map<String, Object> map) {
		return this.getTestPaperSectionDAO().searchTestPaperSectionsCount(map);
	}

	/**
	 * 计算试卷目录列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public List<TestPaperSection> searchTestPaperSections(Map<String, Object> map, String sorts, int offset, int length) {
		return this.getTestPaperSectionDAO().searchTestPaperSections(map, sorts, offset, length);
	}

	/**
	 * 获取
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:57 <br/>
	 * @param id
	 * @return
	 */
	public TestPaperSection getTestPaperSectionById(int id) {
		return this.getTestPaperSectionDAO().get(id);
	}

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:45 <br/>
	 * @param paperSection
	 */
	public void addTestPaperSection(TestPaperSection paperSection) {
		this.getTestPaperSectionDAO().save(paperSection);
		// 修改scode
		String str = String.format("%010d", paperSection.getId());
		if (paperSection.getTestPaperSection() != null) {
			str = paperSection.getTestPaperSection().getScode() + str;
		}
		paperSection.setScode(str);
		this.updateTestPaperSection(paperSection);
	}

	/**
	 * 跟新
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:58:07 <br/>
	 * @param paperSection
	 */
	public void updateTestPaperSection(TestPaperSection paperSection) {
		this.getTestPaperSectionDAO().update(paperSection);
	}

	@Override
	public void deleteTestPaperSection(TestPaperSection paperSection) {
		this.getTestPaperSectionDAO().delete(paperSection);
	}

}
