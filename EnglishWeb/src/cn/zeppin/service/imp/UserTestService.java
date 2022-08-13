/** 
 * Project Name:CETV_TEST 
 * File Name:UserTestService.java 
 * Package Name:cn.zeppin.service.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserTestDAO;
import cn.zeppin.entity.UserTest;
import cn.zeppin.service.api.IUserTestService;

/**
 * ClassName: UserTestService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月21日 下午6:49:16 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserTestService implements IUserTestService {

	private IUserTestDAO userTestDAO;

	public IUserTestDAO getUserTestDAO() {
		return userTestDAO;
	}

	public void setUserTestDAO(IUserTestDAO userTestDAO) {
		this.userTestDAO = userTestDAO;
	}

	/**
	 * 获取UserTest
	 * 
	 * @author Administrator
	 * @date: 2014年10月27日 下午6:59:49 <br/>
	 * @param id
	 * @return
	 */
	public UserTest getUserTestById(Long id) {
		return this.getUserTestDAO().get(id);
	}

	/**
	 * 添加用户考试记录
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午7:16:01 <br/>
	 * @param ut
	 */
	public void addUserTest(UserTest ut) {
		this.getUserTestDAO().save(ut);
	}

	/**
	 * 计算个数
	 * 
	 * @author Administrator
	 * @date: 2014年10月24日 上午11:04:40 <br/>
	 * @param map
	 * @return
	 */
	public int getUserTestCount(Map<String, Object> map) {
		return this.getUserTestDAO().getUserTestCount(map);
	}

	/**
	 * 获取
	 * 
	 * @author Administrator
	 * @date: 2014年10月24日 上午11:05:06 <br/>
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTest> getUserTest(Map<String, Object> map, String sorts, int offset, int length) {
		return this.getUserTestDAO().getUserTest(map, sorts, offset, length);
	}

	/**
	 * 获取
	 * 
	 * @author Administrator
	 * @date: 2014年10月24日 上午11:05:06 <br/>
	 * @param id
	 * @return
	 */
	public UserTest getUserTestById(int id) {
		return this.getUserTestDAO().get((long) id);
	}

	/**
	 * 更新用户考试表
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:28 <br/>
	 * @param organization
	 */
	@Override
	public void updateUserTest(UserTest userTest) {
		this.getUserTestDAO().merge(userTest);
	}

	/**
	 * 获取试卷的品均值(平均时间和平均成绩)
	 * 
	 * @author Administrator
	 * @date: 2014年10月28日 下午2:48:31 <br/>
	 * @param paperId
	 * @return
	 */
	public List<Object[]> getUserTestAvgByPaper(int paperId) {
		return this.getUserTestDAO().getUserTestAvgByPaper(paperId);
	}

}
