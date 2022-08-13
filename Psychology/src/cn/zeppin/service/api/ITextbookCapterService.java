/**  
 * This class is used for ...  
 * @author suijing
 * @version  
 *       1.0, 2014年7月31日 下午2:14:50  
 */
package cn.zeppin.service.api;

import java.util.HashMap;

import cn.zeppin.entity.TextbookCapter;

/**
 * @author sj
 *
 */
public interface ITextbookCapterService
{
	
	/**
	 * @param textbookCapter
	 * @return
	 * @author suijing 2014年7月31日 下午5:33:39
	 */
	TextbookCapter add(TextbookCapter textbookCapter);
	
	/**
	 * @param pid
	 * @return
	 * @author suijing 2014年7月31日 下午6:07:57
	 */
	TextbookCapter getById(int pid);
	
	/**
	 * @param oldTextbookCapter
	 * @return
	 * @author suijing 2014年8月1日 上午11:00:27
	 */
	TextbookCapter update(TextbookCapter oldTextbookCapter);
	
	/**
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @param paras
	 * @return
	 * @author suijing 2014年8月1日 上午11:30:43
	 */
	java.util.List<TextbookCapter> getAllTextbookCapter(int offset, int pageSize, String sorts, HashMap<String, Object> paras);
	
	/**
	 * @param id
	 * @return
	 * @author suijing 2014年8月1日 上午11:30:49
	 */
	boolean hasChild(Integer id);
	
	/**
	 * @param paras
	 * @param b
	 * @return
	 * @author suijing 2014年8月1日 上午11:30:53
	 */
	int getCountByParas(HashMap<String, Object> paras);
	
	/**
	 * @param id
	 * @author suijing 2014年8月1日 下午12:23:04
	 */
	void deleteById(int id);
	
}
