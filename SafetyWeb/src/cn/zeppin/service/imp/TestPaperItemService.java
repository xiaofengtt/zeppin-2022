package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ITestPaperItemDAO;
import cn.zeppin.entity.TestPaperItem;
import cn.zeppin.service.api.ITestPaperItemService;

public class TestPaperItemService implements ITestPaperItemService {

	private ITestPaperItemDAO testPaperItemDAO;

	public ITestPaperItemDAO getTestPaperItemDAO() {
		return testPaperItemDAO;
	}

	public void setTestPaperItemDAO(ITestPaperItemDAO testPaperItemDAO) {
		this.testPaperItemDAO = testPaperItemDAO;
	}

	/**
	 * 计算试卷目录个数
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public int searchTestPaperItemsCount(Map<String, Object> map) {
		return this.getTestPaperItemDAO().searchTestPaperItemsCount(map);
	}

	/**
	 * 计算试卷目录列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public List<TestPaperItem> searchTestPaperItems(Map<String, Object> map, String sorts, int offset, int length) {
		return this.getTestPaperItemDAO().searchTestPaperItems(map, sorts, offset, length);
	}

	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年9月24日 下午12:51:55 <br/> 
	 * @param map
	 */
	public void deleteTestPaperItems(Map<String, Object> map){
		this.getTestPaperItemDAO().deleteTestPaperItems(map);
	}
	
	
	/**
	 * 获取
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:57 <br/>
	 * @param id
	 * @return
	 */
	public TestPaperItem getTestPaperItemById(int id) {
		return this.getTestPaperItemDAO().get(id);
	}

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:45 <br/>
	 * @param paperSection
	 */
	public void addTestPaperItem(TestPaperItem paperItem) {
		this.getTestPaperItemDAO().save(paperItem);
	}

	/**
	 * 跟新
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:58:07 <br/>
	 * @param paperSection
	 */
	public void updateTestPaperItem(TestPaperItem paperItem) {
		this.getTestPaperItemDAO().update(paperItem);
	}

}
