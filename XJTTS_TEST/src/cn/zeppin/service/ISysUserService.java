package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.SysUser;

@SuppressWarnings("rawtypes")
public interface ISysUserService extends IBaseService<SysUser, Integer> {
	public List findByAll(Object[] pars);
	
	/**
	 * 按身份证号查询教师信息是否存在
	 * @param pars
	 * @return
	 */
	public List findByIdCard(Object[] pars);
}
