package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.Subject;

public interface ISubjectService extends IBaseService<Subject, Short>
{
	public List<Subject> findByName(String value);
}
