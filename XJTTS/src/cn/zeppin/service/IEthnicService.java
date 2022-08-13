package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.Ethnic;

public interface IEthnicService extends IBaseService<Ethnic, Short> {
	public List<Ethnic> getEthnicByWight();
}
