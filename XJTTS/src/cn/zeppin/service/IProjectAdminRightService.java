package cn.zeppin.service;

import cn.zeppin.entity.ProjectAdminRight;

public interface IProjectAdminRightService extends IBaseService<ProjectAdminRight, Integer> {
	public int deleteByProjectAdmin(int id);
}
