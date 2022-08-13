package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Paper;
import cn.zeppin.entity.TestPaperSection;

/**
 * 
 * ClassName: IPaperService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年9月2日 下午3:28:14 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IPaperService {

	/**
	 * 添加一个试卷
	 * 
	 * @author Administrator
	 * @date: 2014年9月2日 下午3:29:16 <br/>
	 * @param paper
	 */
	public void addPaper(Paper paper, List<TestPaperSection> lstSections);

	/**
	 * 获取试卷
	 * 
	 * @author Administrator
	 * @date: 2014年9月2日 下午6:27:41 <br/>
	 * @param id
	 */
	public Paper getPaperById(int id);

	/**
	 * 更新试卷
	 * 
	 * @author Administrator
	 * @date: 2014年9月2日 下午6:32:27 <br/>
	 * @param paper
	 */
	public void updatePaper(Paper paper,List<TestPaperSection> lstSections);

	/**
	 * 试卷个数
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param searchMap
	 * @return
	 */
	public int searchPaperCount(Map<String, Object> searchMap);

	/**
	 * 试卷平均分
	 * 
	 * @author jiangfei
	 * @date: 2014年9月3日 上午11:01:45 <br/>
	 * @param paperId
	 * @return
	 */
	public float getPaperAverage(int paperId);
	
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
	public List<Paper> searchPaper(Map<String, Object> searchMap, String sorts, int offset, int pagesize);
	
	/**
	 * 添加试卷
	 * @param paper
	 * @param secMap
	 */
	public void addPaper(Paper paper, Map<String, Object> secMap);
	
	/**
	 * 修改试卷
	 * @param paper
	 * @param secMap
	 */
	public void updatePapers(Paper paper, Map<String, Object> secMap);

	/**
	 * 查询不同试卷类型学科试卷总数
	 * @param List<Map<String,Object>>
	 * @param searchMap
	 */
	public List<Map<String,Object>> searchPaperCountListByType(Map<String, Object> searchMap);
	
	/**
	 * 查询试卷某部分下的所有试题
	 * @param List<Map<String,Object>>
	 * @param sectionId
	 */
	public List<Map<String, Object>> searchTestPaperSectionItemMap(Integer sectionId);
}
