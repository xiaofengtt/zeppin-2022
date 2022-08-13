package cn.zeppin.service.api;

import java.util.Map;

import cn.zeppin.entity.Version;

public interface IVersionService {

	/**
	 * 查询版本
	 * @param version
	 */
	Version getVersionByParams(Map<String, Object> map);
	

}
