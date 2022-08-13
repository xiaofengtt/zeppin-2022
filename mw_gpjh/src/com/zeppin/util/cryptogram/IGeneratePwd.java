package com.zeppin.util.cryptogram;

/**
 * 生成唯一性登录密码
 * 
 * 
 */

public interface IGeneratePwd
{

	/**
	 * 生成密码
	 * 
	 * @return
	 * @throws InterruptedException 
	 */
	public String getPwd() throws InterruptedException;

	/**
	 * 返回解密的原始字符串
	 * 
	 * @param pwd
	 * @return
	 * @throws Exception 
	 */
	public String getOralString(String pwd) throws Exception;
	
	public String getSecString(String pwd) throws Exception;

}
