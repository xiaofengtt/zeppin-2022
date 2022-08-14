package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.Method;

public interface MethodService extends IService<Method> {
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
    public List<Method> getListByParams(Map<String, Object> params);
    
    /**
     * 获取用户权限
     * @param admin
     * @return
     */
    public List<Method> getListByAdmin(String admin);
    
    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions();
}
