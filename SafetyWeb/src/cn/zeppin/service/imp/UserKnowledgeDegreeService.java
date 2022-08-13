package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserKnowledgeDegreeDAO;
import cn.zeppin.entity.UserKnowledgeDegree;
import cn.zeppin.service.api.IUserKnowledgeDegreeService;

public class UserKnowledgeDegreeService implements IUserKnowledgeDegreeService {

	private IUserKnowledgeDegreeDAO userKnowledgeDegreeDAO;

	public IUserKnowledgeDegreeDAO getUserKnowledgeDegreeDAO() {
		return userKnowledgeDegreeDAO;
	}

	public void setUserKnowledgeDegreeDAO(IUserKnowledgeDegreeDAO userKnowledgeDegreeDAO) {
		this.userKnowledgeDegreeDAO = userKnowledgeDegreeDAO;
	}
	
	/**
	 * 添加
	 * @author Administrator
	 * @date: 2014年10月26日 下午5:18:20 <br/> 
	 * @param ukld
	 */
	public void addUserKnowledgeDegree(UserKnowledgeDegree ukld){
		this.getUserKnowledgeDegreeDAO().addUserKnowledgeDegree(ukld);
	}

	/**
	 * 计算count
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午6:08:06 <br/>
	 * @param map
	 * @return
	 */
	public int getUserKnowledgeDegreeCount(Map<String, Object> map) {
		return this.getUserKnowledgeDegreeDAO().getUserKnowledgeDegreeCount(map);
	}

	/**
	 * 获取
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午6:08:44 <br/>
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserKnowledgeDegree> getUserKnowledgeDegree(Map<String, Object> map, String sort, int offset, int length) {
		return this.getUserKnowledgeDegreeDAO().getUserKnowledgeDegree(map, sort, offset, length);
	}

}
