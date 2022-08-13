/**
 * 
 */
package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import com.whaty.platform.entity.bean.PeJianzhang;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.FlagActiveService;

/**
 * 对于flagActive的设置操作
 * @author 李冰
 *
 */
public class FlagActiveServiceImp implements FlagActiveService {
	private GeneralDao generalDao;

	/**
	 * 学期管理，设为当前学期
	 * @param list  学期列表
	 * @param id  操作的id：this.getIds().split(",")[0]
	 */
	public void savePeSemesterFlagActive(List<PeSemester> list, String id) {
		for (PeSemester peSemester : list) {
			if (peSemester.getId().equals(id)) {
				if (peSemester.getFlagActive() == null
						|| peSemester.getFlagActive().equals("0")) {
					peSemester.setFlagActive("1");
					this.getGeneralDao().save(peSemester);
				}
			} else {
				if (peSemester.getFlagActive().equals("1")) {
					peSemester.setFlagActive("0");
					this.getGeneralDao().save(peSemester);
				}
			}
		}
	}
	
	/**
	 * 学期管理，设置为选课学期
	 * @param list  学期列表
	 * @param id  操作的id：this.getIds().split(",")[0]
	 */
	public void savePeSemesterFlagNextActive(List<PeSemester> list, String id){
		for (PeSemester peSemester : list) {
			if (peSemester.getId().equals(id)) {
				if (peSemester.getFlagNextActive() == null
						|| peSemester.getFlagNextActive().equals("0")) {
					peSemester.setFlagNextActive("1");
					this.getGeneralDao().save(peSemester);
				}
			} else {
				if (peSemester.getFlagNextActive().equals("1")) {
					peSemester.setFlagNextActive("0");
					this.getGeneralDao().save(peSemester);
				}
			}
		}
	}
	
	/**
	 * 招生考试批次管理，设置为当前批次
	 * @param list  批次列表
	 * @param id  操作的id：this.getIds().split(",")[0]
	 */
	public void savePeRecruitplanFlagActive(List<PeRecruitplan> list, String id){

		for (PeRecruitplan recruitplan : list) {
			if (recruitplan.getId().equals(id)) {
				if (recruitplan.getFlagActive().equals("0")) {
					recruitplan.setFlagActive("1");
					this.getGeneralDao().save(recruitplan);
				}
			} else {
				if (recruitplan.getFlagActive().equals("1")) {
					recruitplan.setFlagActive("0");
					this.getGeneralDao().save(recruitplan);
				}
			}
		}
	}
	
	/**
	 * 招生简章，设置为活动
	 * @param list  招生简章列表
	 * @param id  操作的id：this.getIds().split(",")[0]
	 */
	public int savePeJianzhangFlagActive(List<PeJianzhang> list, String id){
		for (PeJianzhang recJianzhang : list) {
			if (recJianzhang.getId().equals(id)) {
				if (recJianzhang.getFlagActive().equals("0")) {
					recJianzhang.setFlagActive("1");
					this.getGeneralDao().save(recJianzhang);
				}
			} else {
				if (recJianzhang.getFlagActive().equals("1")) {
					recJianzhang.setFlagActive("0");
					this.getGeneralDao().save(recJianzhang);
				}
			}
		}
		return 0;
	}
	public GeneralDao getGeneralDao() {
		return generalDao;
	}
	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
}
