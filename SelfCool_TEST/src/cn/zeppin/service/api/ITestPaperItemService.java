package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TestPaperItem;

public interface ITestPaperItemService {

	/**
	 * 计算试卷题数个数
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public int searchTestPaperItemsCount(Map<String, Object> map);

	/**
	 * 计算试卷题数列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public List<TestPaperItem> searchTestPaperItems(Map<String, Object> map, String sorts, int offset, int length);
	
	
	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年9月24日 下午12:51:55 <br/> 
	 * @param map
	 */
	public void deleteTestPaperItems(Map<String, Object> map);

	/**
	 * 获取
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:57 <br/>
	 * @param id
	 * @return
	 */
	public TestPaperItem getTestPaperItemById(int id);

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:45 <br/>
	 * @param paperSection
	 */
	public void addTestPaperItem(TestPaperItem paperItem);

	/**
	 * 更新
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:58:07 <br/>
	 * @param paperSection
	 */
	public void updateTestPaperItem(TestPaperItem paperItem);

}
