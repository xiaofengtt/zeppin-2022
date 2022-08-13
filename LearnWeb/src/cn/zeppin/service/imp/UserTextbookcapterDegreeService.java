package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserTextbookcapterDegreeDAO;
import cn.zeppin.entity.UserTextbookcapterDegree;
import cn.zeppin.service.api.IUserTextbookcapterDegreeService;

public class UserTextbookcapterDegreeService implements IUserTextbookcapterDegreeService {

	private IUserTextbookcapterDegreeDAO userTextbookcapterDegreeDAO;

	public IUserTextbookcapterDegreeDAO getUserTextbookcapterDegreeDAO() {
		return userTextbookcapterDegreeDAO;
	}

	public void setUserTextbookcapterDegreeDAO(IUserTextbookcapterDegreeDAO userTextbookcapterDegreeDAO) {
		this.userTextbookcapterDegreeDAO = userTextbookcapterDegreeDAO;
	}

	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:21 <br/>
	 * @param utbDegree
	 */
	public void addUserTextbookcapterDegree(UserTextbookcapterDegree utbDegree) {
		this.getUserTextbookcapterDegreeDAO().addUserTextbookcapterDegree(utbDegree);

	}

	/**
	 * 计算数量
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:32 <br/>
	 * @param map
	 * @return
	 */
	public int getUserTextbookcapterDegreeCount(Map<String, Object> map) {
		return this.getUserTextbookcapterDegreeDAO().getUserTextbookcapterDegreeCount(map);
	}

	/**
	 * 获取列表
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:46 <br/>
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTextbookcapterDegree> getUserTextbookcapterDegreeByMap(Map<String, Object> map, String sort, int offset, int length) {
		
		return this.getUserTextbookcapterDegreeDAO().getUserTextbookcapterDegreeByMap(map, sort, offset, length);
		
	}

	/**
	 * by id
	 * 
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:57 <br/>
	 * @param id
	 * @return
	 */
	public UserTextbookcapterDegree getUserTextbookcapterDegreeById(Long id) {

		return this.getUserTextbookcapterDegreeDAO().getUserTextbookcapterDegreeById(id);

	}

}
