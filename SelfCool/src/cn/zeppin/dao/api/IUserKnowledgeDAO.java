package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserKnowledge;

public interface IUserKnowledgeDAO extends IBaseDAO<UserKnowledge, Integer>{

	/**
	 * 通过用户，学科获得用户的知识点信息
	 * @param split 
	 * @param id
	 * @param id2
	 * @return
	 */
	public List<Map<String, Object>> getUserKnowledges(Integer uid, Integer subjectId);

	/**
	 * 根据唯一所以获取一条UserKnowledge信息
	 * @param uid
	 * @param knowledgeId
	 * @return
	 */
	public UserKnowledge getByKey(Integer uid, Integer knowledgeId);
	
}
