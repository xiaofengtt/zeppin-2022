/**
 * 
 */
package com.whaty.platform.entity.test;

/**������������λ��Ϣ
 * @author chenjian
 *
 */
public abstract class TestDesk {
	
	private String id;
	
	/**�ò��������˰��տ������е���λ��
	 * 
	 */
	private int NumByRoom; 

	/**�ò��������˰��տγ����е���λ��
	 * 
	 */
	private int NumByCourse;
	
	/**
	 * �ò��������˸���λ��Ӧ�Ŀ���
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
