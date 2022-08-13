package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.ProjectLevel;

public interface IProjectLevelService extends
	IBaseService<ProjectLevel, Short>
{

    public List<ProjectLevel> getList();

}
