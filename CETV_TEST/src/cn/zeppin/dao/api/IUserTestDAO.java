/** 
 * Project Name:CETV_TEST 
 * File Name:IUserTestDAO.java 
 * Package Name:cn.zeppin.dao.api 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserTest;

/**
 * ClassName: IUserTestDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月13日 下午4:59:17 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public interface IUserTestDAO extends IBaseDAO<UserTest, Long> {
	
	/**
	 * 添加用户考试记录
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午7:16:01 <br/>
	 * @param ut
	 */
	public void addUserTest(UserTest ut);
	
	/**
	 * 计算个数
	 * @author Administrator
	 * @date: 2014年10月24日 上午11:04:40 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserTestCount(Map<String, Object> map);

	/**
	 * 获取
	 * @author Administrator
	 * @date: 2014年10月24日 上午11:05:06 <br/> 
	 * @param map
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTest> getUserTest(Map<String, Object> map, String sorts, int offset, int length);
	
	/**
	 * 获取试卷的品均值(平均时间和平均成绩)
	 * @author Administrator
	 * @date: 2014年10月28日 下午2:48:31 <br/> 
	 * @param paperId
	 * @return
	 */
	public List<Object[]> getUserTestAvgByPaper(int paperId);
	
}
