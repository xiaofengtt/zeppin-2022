package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.Subject;

public interface ISubjectDao extends IBaseDao<Subject, Short>
{

	public List<Subject> findByName(String value);

}
