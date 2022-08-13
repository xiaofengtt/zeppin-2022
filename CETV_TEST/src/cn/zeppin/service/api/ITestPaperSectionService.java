package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TestPaperSection;

public interface ITestPaperSectionService {
	
	/**
	 * 计算试卷目录个数
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public int searchTestPaperSectionsCount(Map<String, Object> map);

	/**
	 * 计算试卷目录列表
	 * 
	 * @author Administrator
	 * @date: 2014年9月3日 下午3:53:53 <br/>
	 * @param map
	 * @return
	 */
	public List<TestPaperSection> searchTestPaperSections(Map<String, Object> map,String sorts,int offset,int length);
	
	/**
	 * 获取
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:57 <br/> 
	 * @param id
	 * @return
	 */
	public TestPaperSection getTestPaperSectionById(int id);
	
	/**
	 * 添加
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:45 <br/> 
	 * @param paperSection
	 */
	public void addTestPaperSection(TestPaperSection paperSection);
	
	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:42:45 <br/> 
	 * @param paperSection
	 */
	public void deleteTestPaperSection(TestPaperSection paperSection);
	
	/**
	 * 跟新
	 * @author Administrator
	 * @date: 2014年9月3日 下午4:58:07 <br/> 
	 * @param paperSection
	 */
	public void updateTestPaperSection(TestPaperSection paperSection);
	
}
