package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.UserTest;

public interface IUserTestService {

	/**
	 * 添加用户考试记录
	 * 
	 * @author Administrator
	 * @date: 2014年10月21日 下午7:16:01 <br/>
	 * @param ut
	 */
	public void addUserTest(UserTest ut);
	
	/**
	 * 获取UserTest
	 * @author Administrator
	 * @date: 2014年10月27日 下午6:59:49 <br/> 
	 * @param id
	 * @return
	 */
	public UserTest getUserTestById(Long id);

	
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
	 * 获取
	 * @author Administrator
	 * @date: 2014年10月24日 上午11:05:06 <br/> 
	 * @param id
	 * @return
	 */
	public UserTest getUserTestById(int id);
	
	/**
	 * 更新用户考试表
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午4:40:28 <br/>
	 * @param organization
	 */
	public void updateUserTest(UserTest userTest);
	
	/**
	 * 获取试卷的品均值(平均时间和平均成绩)
	 * @author Administrator
	 * @date: 2014年10月28日 下午2:48:31 <br/> 
	 * @param paperId
	 * @return
	 */
	public List<Object[]> getUserTestAvgByPaper(int paperId);
}
