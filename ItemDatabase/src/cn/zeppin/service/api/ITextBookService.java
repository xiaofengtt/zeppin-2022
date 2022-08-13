/**  
 * This class is used for ...  
 * @author suijing
 * @version  
 *       1.0, 2014年7月29日 上午11:51:06  
 */
package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Textbook;

/**
 * @author sj
 *
 */
public interface ITextBookService
{
	
	/**
	 * @param textbook
	 * @return
	 * @author suijing 2014年7月30日 下午12:55:45
	 */
	Textbook add(Textbook textbook);
	
	/**
	 * @param id
	 * @return
	 * @author suijing 2014年7月30日 下午12:55:52
	 */
	Textbook getById(int id);
	
	/**
	 * @param textbook
	 * @author suijing 2014年7月30日 下午12:55:56
	 */
	void update(Textbook textbook);
	
	/**
	 * @param oldTextbook
	 * @author suijing 2014年7月30日 下午1:09:26
	 */
	void delete(Textbook oldTextbook);
	
	/**
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @param paras
	 * @return
	 * @author suijing 2014年7月30日 下午4:13:39
	 */
	List<Textbook> getAllByAdmin(int offset, int pageSize, String sorts, HashMap<String, Object> paras);
	
	/**
	 * @param paras
	 * @param b
	 * @return
	 * @author suijing 2014年7月30日 下午4:13:46
	 */
	int getCountByParas(HashMap<String, Object> paras, boolean b);
	
	/**
	 * @param offset
	 * @param pageSize
	 * @param sorts
	 * @param paras
	 * @return
	 * @author suijing 2014年7月30日 下午4:14:57
	 */
	List<Textbook> getAllByUser(int offset, int pageSize, String sorts, HashMap<String, Object> paras);
	
	/**
	 * @param searchMap
	 * @return
	 * @author suijing 2014年7月30日 下午4:14:57
	 */
	List<Textbook> getTextbookByParam(Map<String, Object> searchMap);
	
	/**
	 * @param name
	 * @return
	 * @author suijing 2014年7月30日 下午5:51:50
	 */
	boolean isExistByName(String name, Integer id);
	
}
