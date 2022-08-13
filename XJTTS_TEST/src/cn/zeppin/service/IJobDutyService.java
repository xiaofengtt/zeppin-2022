package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.JobDuty;

public interface IJobDutyService extends IBaseService<JobDuty, Integer>
{
    public JobDuty getJobDutyById(String id);

    public List<JobDuty> findByName(String value); 
}
