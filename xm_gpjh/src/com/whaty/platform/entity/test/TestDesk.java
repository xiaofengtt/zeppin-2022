/**
 * 
 */
package com.whaty.platform.entity.test;

/**该类描述了座位信息
 * @author chenjian
 *
 */
public abstract class TestDesk {
	
	private String id;
	
	/**该参数描述了按照考场排列的座位号
	 * 
	 */
	private int NumByRoom; 

	/**该参数描述了按照课程排列的座位号
	 * 
	 */
	private int NumByCourse;
	
	/**
	 * 该参数描述了该座位对应的考场
	 */
	private TestRoom testRoom;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumByCourse() {
		return NumByCourse;
	}

	public void setNumByCourse(int numByCourse) {
		NumByCourse = numByCourse;
	}

	public int getNumByRoom() {
		return NumByRoom;
	}

	public void setNumByRoom(int numByRoom) {
		NumByRoom = numByRoom;
	}

	public TestRoom getTestRoom() {
		return testRoom;
	}

	public void setTestRoom(TestRoom testRoom) {
		this.testRoom = testRoom;
	}
	
	
}
