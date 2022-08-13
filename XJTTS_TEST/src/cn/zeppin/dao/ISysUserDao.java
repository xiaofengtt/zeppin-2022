package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.SysUser;

@SuppressWarnings("rawtypes")
public interface ISysUserDao extends IBaseDao<SysUser, Integer> {

	public List findByAll(Object[] pars);
	
	/**
	 * 通过身份证号查询教师信息
	 * @param pars
	 * @return
	 */
	public List findByIdCard(Object[] pars);
}
