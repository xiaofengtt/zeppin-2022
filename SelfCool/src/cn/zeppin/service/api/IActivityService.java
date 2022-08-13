package cn.zeppin.service.api;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Activity;

public interface IActivityService {
	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param Activity
	 */
	public void addActivity(Activity activity);

	/**
	 * 更新
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 上午11:27:43 <br/>
	 * @param Activity
	 */
	public void updateActivity(Activity activity);

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/>
	 * @param Activity
	 */
	public void deleteActivity(Activity activity);
	
	/**
	 * 根据 id获取
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	public Activity getActivityById(Integer id);
	
	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param searchMap
	 * @return
	 */
	public int getActivityCountByParams(HashMap<String, Object> searchMap);

	/**
	 * 获取分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param sorts
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Activity> getActivityListByParams(HashMap<String, Object> params, String sorts, int offset, int length);
}
