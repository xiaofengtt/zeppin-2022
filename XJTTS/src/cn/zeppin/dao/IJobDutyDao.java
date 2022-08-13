package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.JobDuty;

public interface IJobDutyDao extends IBaseDao<JobDuty, Integer> {

	public List<JobDuty> findByName(String value);
}
