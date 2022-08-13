package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.JobTitle;

public interface IJobTitleService extends IBaseService<JobTitle, Integer>
{
    public JobTitle getJobTitleById(String id);

    public List<JobTitle> findByName(String value); 
}
