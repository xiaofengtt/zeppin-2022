package cn.zeppin.dao;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SysUser;

@SuppressWarnings("rawtypes")
public interface ISysUserDao extends IBaseDao<SysUser, Integer> {

	public List findByAll(Object[] pars,Integer role);
	
	/**
	 * 通过身份证号查询教师信息
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
