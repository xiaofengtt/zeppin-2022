/**
 * Project Name:Self_Cool File Name:IGradeService.java Package
 * Name:cn.zeppin.service.api Copyright (c) 2014, Zeppin All Rights Reserved.
 * 
 */
package cn.zeppin.service.api;
import cn.zeppin.entity.Grade;

/**
 * ClassName: IGradeService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月14日 下午7:30:21 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public interface IGradeService {

	/**
	 * 递归得到学段全称
	 * 
	 * @author Clark
	 * @date: 2014年7月14日 下午7:32:20 <br/>
	 * @param grade
	 * @return
	 */
	public String getGradeFullName(Grade grade);

	/**
	 * 根据id来获取grade
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:04:58 <br/>
	 * @param id
	 * @return
	 */
	public Grade getGradeById(Integer id);
}
