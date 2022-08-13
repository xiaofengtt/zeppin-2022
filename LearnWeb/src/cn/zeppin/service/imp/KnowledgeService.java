/**
 * This class is used for ...
 * 
 * @author suijing
 * @version 1.0, 2014年7月21日 下午8:06:07
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IKnowledgeDAO;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.service.api.IKnowledgeService;
import cn.zeppin.utility.Dictionary;

/**
 * @author sj
 * 
 */
public class KnowledgeService implements IKnowledgeService
{
    private IKnowledgeDAO knowledgeDAO;


	/**
	 * @return the knowledgeDAO
	 */
	public IKnowledgeDAO getKnowledgeDAO() {
		return knowledgeDAO;
	}

	/**
	 * @param knowledgeDAO the knowledgeDAO to set
	 */
	public void setKnowledgeDAO(IKnowledgeDAO knowledgeDAO) {
		this.knowledgeDAO = knowledgeDAO;
	}

	/**
	 * 通过ID获取知识点信息
	 * @author Clark
	 * @date: 2014年7月22日 下午7:23:39 <br/> 
	 * @param id
	 * @return Knowledge
	 */
	@Override
	public Knowledge getById(Integer id) {
		// TODO Auto-generated method stub
		return this.getKnowledgeDAO().get(id);
	}

	/**
	 * 添加知识点信息
	 * @author Clark
	 * @date: 2014年7月22日 下午8:02:38 <br/> 
	 * @param knowledge
	 * @return Knowledge
	 */
	@Override
	public Knowledge addKnowledge(Knowledge knowledge) {
		// TODO Auto-generated method stub
		return this.getKnowledgeDAO().save(knowledge);
	}

	/**
	 * 修改知识点信息
	 * @author Clark
	 * @date: 2014年7月23日 下午3:03:42 <br/> 
	 * @param knowledge
	 * @return
	 */
	@Override
	public Knowledge updateKnowledge(Knowledge knowledge) {
		// TODO Auto-generated method stub
		return this.getKnowledgeDAO().update(knowledge);
	}

	/**
	 * 删除知识点信息
	 * @author Clark
	 * @date: 2014年7月23日 下午3:22:37 <br/> 
	 * @param knowledge
	 * @return
	 */
	@Override
	public Knowledge deleteKnowledge(Knowledge knowledge) {
		// TODO Auto-generated method stub
		knowledge.setStatus(Dictionary.KNOWLEDGE_STATUS_CLOSED);
		return this.getKnowledgeDAO().update(knowledge);
	}

	/**
	 * 按条件查询知识点数量
	 * @author Clark
	 * @date: 2014年7月23日 下午4:09:07 <br/> 
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchKnowledgeCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getKnowledgeDAO().searchKnowledgeCount(searchMap);
	}

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
	@Override
	public List<Knowledge> searchKnowledge(Map<String, Object> searchMap,
			String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return this.getKnowledgeDAO().searchKnowledge(searchMap, sorts, offset, pagesize);
	}


}
