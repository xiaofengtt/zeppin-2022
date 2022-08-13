package com.zixueku.entity;
/** 
 * 类说明 
 * @author  Email: huangweidong@zeppin.cn
 * @version V1.0  创建时间：2015-2-4 下午7:13:17 
 */
public enum ParserType {
	 PRIMITIVE,//原始数据，不做处理
	USER_DEFINED,  //用户自定义类型
	USER_DEFINED2, //用户自定义类型2，类型中包含泛型
    LIST_USER_DEFINED,  //用户自定义类型的List,List<user_defined>
    LIST,
    MAP;
}
