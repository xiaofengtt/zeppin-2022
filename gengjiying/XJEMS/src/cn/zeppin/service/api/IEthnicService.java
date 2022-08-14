package cn.zeppin.service.api;

import java.util.List;

import cn.zeppin.entity.Ethnic;

public interface IEthnicService {
	public List<Ethnic> getEthnicByWight();
	
	/**
	 * 通过id获取资源
	 * 
	 * @param id
	 * @return
	 */
	Ethnic getById(short id);

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @return
	 */
	Ethnic add(Ethnic ethnic);

	/**
	 * @param resource
	 */
	void update(Ethnic ethnic);

	/**
	 * @param id
	 */
	void delById(Ethnic ethnic);
}
