package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.Grade;

public interface IGradeService extends IBaseService<Grade, Short> {
	public List<Grade> findByName(String value);
}
