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
	 * 跟新试卷
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

}
