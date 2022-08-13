package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.JobTitle;

public interface IJobTitleDao extends IBaseDao<JobTitle, Integer> {
	
	public List<JobTitle> findByName(String value);
}
