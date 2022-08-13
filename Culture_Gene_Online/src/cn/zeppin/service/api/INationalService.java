package cn.zeppin.service.api;

import java.util.List;

import cn.zeppin.entity.National;

public interface INationalService {
	/**
	 * 查找全部
	 */
	public List<National> findAll();
	
	/**
	 * 获取
	 */
	public National getNational(int id);
}
