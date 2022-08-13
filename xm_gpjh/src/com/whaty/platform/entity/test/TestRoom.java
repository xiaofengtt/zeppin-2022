/**
 * 
 */
package com.whaty.platform.entity.test;

/**该类描述了考场信息
 * @author chenjian
 *
 */
public abstract class TestRoom {

	/**
	 * 考场标识Id
	 */
	private String id;
	
	/**
	 * 考场名称
	 */
	private String title;
	
	/**
	 * 对应考试课程下的顺序编号
	 */
	private int SeqInCourse;
	
	/**
	 * 对应的考试课程
	 */
	private TestCourse testCourse;
	
	/**
	 * 对应某场考试下的顺序标号
	 */
	private int SeqAll;
	
	/**
	 * 对应的某场考试
	 */
	private TestSequence testSequence;
	
	/**该考场对应的物理地址
	 * 
	 */
	private String address;
	
	
	/**
	 * 对应的考点
	 */
	private TestSite testSite;
	
	
}
