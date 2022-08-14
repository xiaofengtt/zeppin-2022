package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.Ethnic;

public interface IEthnicDao extends IBaseDAO<Ethnic, Short> {
	public List<Ethnic> getList();
}
