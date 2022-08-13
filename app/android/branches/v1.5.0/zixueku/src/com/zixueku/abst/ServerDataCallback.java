package com.zixueku.abst;
/** 
 * 类说明 
 * 服务器返回数据后的回调接口,需自行实现
 * @author  Email: huangweidong@zeppin.cn
 * @version V1.0  创建时间：2015-2-5 上午10:11:35 
 */
public abstract interface ServerDataCallback <T>{
	public abstract void processData(T paramObject, boolean paramBoolean);
	
}
