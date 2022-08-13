package cn.zeppin.service.imp;

import java.util.Map;

import cn.zeppin.dao.api.IUserTextbookDAO;
import cn.zeppin.entity.UserTextbook;
import cn.zeppin.service.api.IUserTextbookService;

public class UserTextbookService implements IUserTextbookService {

	private IUserTextbookDAO userTextbookDAO;

	public IUserTextbookDAO getUserTextbookDAO() {
		return userTextbookDAO;
	}

	public void setUserTextbookDAO(IUserTextbookDAO userTextbookDAO) {
		this.userTextbookDAO = userTextbookDAO;
	}

	/**
	 * 搜索UserTextbook
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:44:41 <br/>
	 * @param map
	 * @return
	 */
	public UserTextbook getUserTextbookByMap(Map<String, Object> map) {

		return this.getUserTextbookDAO().getUserTextbookByMap(map);
	}

	/**
	 * 添加UserTextbook
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:44:56 <br/>
	 * @param book
	 */
	public void addUserTextbook(UserTextbook book) {
		this.getUserTextbookDAO().addUserTextbook(book);
	}

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午3:45:10 <br/>
	 * @param book
	 */
	public void deleteUserTextbook(UserTextbook book) {
		this.getUserTextbookDAO().deleteUserTextbook(book);

	}

}
