package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.OrganizationLevel;

public interface IOrganizationLevelService extends
	IBaseService<OrganizationLevel, Short>
{
    public List<OrganizationLevel> getList();

}
