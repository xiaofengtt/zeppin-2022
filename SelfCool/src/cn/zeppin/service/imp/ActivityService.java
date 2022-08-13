package cn.zeppin.service.imp;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.api.IActivityDAO;
import cn.zeppin.entity.Activity;
import cn.zeppin.service.api.IActivityService;
import cn.zeppin.utility.Dictionary;

public class ActivityService implements IActivityService {
	
	private IActivityDAO activityDAO;

	public IActivityDAO getActivityDAO() {
		return activityDAO;
	}

	public void setActivityDAO(IActivityDAO activityDAO) {
		this.activityDAO = activityDAO;
	}
	
	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param Activity
	 */
	@Override
	public void addActivity(Activity activity) {
		this.getActivityDAO().save(activity);
	}
	
	/**
	 * 修改
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午4:57:31 <br/>
	 * @param Activity
	 */
	@Override
	public void updateActivity(Activity activity) {
		this.getActivityDAO().update(activity);
	}

	/**
	 * 删除
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:02:09 <br/> 
	 * @param Activity
	 */
	@Override
	public void deleteActivity(Activity activity) {
		activity.setStatus(Dictionary.ACTIVITY_STATUS_CLOSED);
		this.getActivityDAO().update(activity);
	}
	
	/**
	 * 根据id获取
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:12:39 <br/>
	 * @param id
	 * @return
	 */
	@Override
	public Activity getActivityById(Integer id) {
		return this.getActivityDAO().get(id);
	}

	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年7月14日 下午5:30:41 <br/>
	 * @param params
	 * @return
	 */
	@Override
	public int getActivityCountByParams(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getActivityDAO().getActivityCountByParams(params);
	}

	/**
	 * 获取分页列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月15日 下午3:19:03 <br/>
	 * @param params
	 * @param offset
	 * @param length
	 * @return
	 */
	@Override
	public List<Activity> getActivityListByParams(HashMap<String, Object> params, String sorts, int offset, int length) {
		// TODO Auto-generated method stub
		return this.getActivityDAO().getActivityListByParams(params, sorts, offset, length);
	}

}
