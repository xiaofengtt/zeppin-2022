package com.whaty.platform.entity.dao.recruit;

import java.util.List;

import com.whaty.platform.entity.bean.PrRecExamStu;
import com.whaty.platform.entity.dao.AbstractEntityDao;

public interface PrRecExamStuDAO extends AbstractEntityDao<PrRecExamStu,String> {
	public List getRoomNo(String pesitemanager);

}
