package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.Grade;

public interface IGradeDao extends IBaseDao<Grade, Short> {
	public List<Grade> findByName(String value);
}
