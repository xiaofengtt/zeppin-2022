/**
 * 
 */
package com.whaty.platform.entity.test;

/**���������˿�����Ϣ
 * @author chenjian
 *
 */
public abstract class TestRoom {

	/**
	 * ������ʶId
	 */
	private String id;
	
	/**
	 * ��������
	 */
	private String title;
	
	/**
	 * ��Ӧ���Կγ��µ�˳����
	 */
	private int SeqInCourse;
	
	/**
	 * ��Ӧ�Ŀ��Կγ�
	 */
	private TestCourse testCourse;
	
	/**
	 * ��Ӧĳ�������µ�˳����
	 */
	private int SeqAll;
	
	/**
	 * ��Ӧ��ĳ������
	 */
	private TestSequence testSequence;
	
	/**�ÿ�����Ӧ�������ַ
	 * 
	 */
	private String address;
	
	
	/**
	 * ��Ӧ�Ŀ���
	 */
	private TestSite testSite;
	
	
}
