package com.whaty.platform.entity.service.basic;

import java.util.List;

import com.whaty.platform.entity.bean.PeJianzhang;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSemester;

/**
 * 对于flagActive的设置操作
 * @author 李冰
 *
 */
public interface FlagActiveService {
	
	/**
	 * 学期管理，设为当前学期
	 * @param list  学期列表
	 * @param id  操作的id：this.getIds().split(",")[0]
	 */
	public void savePeSemesterFlagActive(List<PeSemester> list, String id);
	
	/**
	 * 学期管理，设置为选课学期
	 * @param list  学期列表
	 * @param id  操作的id：this.getIds().split(",")[0]
	 */
	public void savePeSemesterFlagNextActive(List<PeSemester> list, String id);
	
	/**
	 * 招生考试批次管理，设置为当前批次
	 * @param list  批次列表
	 * @param id  操作的id：this.getIds().split(",")[0]
	 */
	public void savePeRecruitplanFlagActive(List<PeRecruitplan> list, String id);
	
	/**
	 * 招生简章，设置为活动
	 * @param list  招生简章列表
	 * @param id  操作的id：this.getIds().split(",")[0]
	 */
	public int savePeJianzhangFlagActive(List<PeJianzhang> list, String id);
}
