package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SysUser;

@SuppressWarnings("rawtypes")
public interface ISysUserService extends IBaseService<SysUser, Integer> {
	public List findByAll(Object[] pars,Integer role);
	
	/**
	 * 按身份证号查询教师信息是否存在
	 * @param pars
	 * @return
	 */
	public List findByIdCard(Object[] pars);
	
	/**
	 * 通过条件获取列表
	 * @param pars
	 * @return
	 */
	public List findByParams(Map<String, Object> params);
}
