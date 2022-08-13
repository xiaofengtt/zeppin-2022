package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.Politics;

public interface IPoliticsDao extends IBaseDao<Politics, Integer> {

	 public List<Politics> getPoliticsByWight();
}
