/**
 * This class is used for ...
 * 
 * @author Clark
 * @version 1.0, 2014年7月21日 下午8:05:05
 */
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Knowledge;



public interface IKnowledgeService
{

	/**
	 * 通过ID获取知识点信息
	 * @author Clark
	 * @date: 2014年7月22日 下午7:23:39 <br/> 
	 * @param id
	 * @return Knowledge
	 */
	public Knowledge getById(Integer id);

	/**
	 * 添加知识点信息
	 * @author Clark
	 * @date: 2014年7月22日 下午8:02:38 <br/> 
	 * @param knowledge
	 * @return Knowledge
	 */
	public Knowledge addKnowledge(Knowledge knowledge);

	/**
	 * 修改知识点信息
	 * @author Clark
	 * @date: 2014年7月23日 下午3:03:42 <br/> 
	 * @param knowledge
	 * @return
	 */
	public Knowledge updateKnowledge(Knowledge knowledge);

	/**
	 * 删除知识点信息
	 * @author Clark
	 * @date: 2014年7月23日 下午3:22:37 <br/> 
	 * @param knowledge
	 * @return
	 */
	public Knowledge deleteKnowledge(Knowledge knowledge);

	/**
	 * 按条件查询知识点数量
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:07 <br/> 
	 * @param searchMap
	 * @return count
	 */
	public int searchKnowledgeCount(Map<String, Object> searchMap);

	/**
	 * 按条件查询知识点列表
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:44 <br/> 
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Knowledge>
	 */
	public List<Knowledge> searchKnowledge(Map<String, Object> searchMap, String sorts, int offset, int pagesize);

}
