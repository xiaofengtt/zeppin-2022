/*
 * Semester.java
 *
 * Created on 2004��9��24��, ����2:11
 */

package com.whaty.platform.entity.basic;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * ѧ�ڶ���
 * 
 * @author chenjian
 */
public abstract class Semester implements com.whaty.platform.Items {

	private String id = "";

	private String name = "";

	private String start_date = "";

	private String end_date = "";

	private String start_elective = "";

	private String end_elective = "";

	private String start_test = "";

	private String end_test = "";

	private int selected = 0;

	/**
	 * ���� id �Ļ�ȡ������
	 * 
	 * @return ���� id ��ֵ��
	 */
	public String getId() {
		return id;
	}

	/**
	 * ���� id �����÷�����
	 * 
	 * @param id
	 *            ���� id ����ֵ��
	 */
	public void setId(String aId) {
		id = aId;
	}

	/**
	 * ���� name �Ļ�ȡ������
	 * 
	 * @return ���� name ��ֵ��
	 */
	public String getName() {
		return name;
	}

	/**
	 * ���� name �����÷�����
	 * 
	 * @param name
	 *            ���� name ����ֵ��
	 */
	public void setName(String aName) {
		name = aName;
	}

	/**
	 * ���� start_date �Ļ�ȡ������
	 * 
	 * @return ���� start_date ��ֵ��
	 */
	public String getStart_date() {
		return start_date;
	}

	/**
	 * ���� start_date �����÷�����
	 * 
	 * @param start_date
	 *            ���� start_date ����ֵ��
	 */
	public void setStart_date(String aStart_date) {
		start_date = aStart_date;
	}

	/**
	 * ���� end_date �Ļ�ȡ������
	 * 
	 * @return ���� end_date ��ֵ��
	 */
	public String getEnd_date() {
		return end_date;
	}

	/**
	 * ���� end_date �����÷�����
	 * 
	 * @param end_date
	 *            ���� end_date ����ֵ��
	 */
	public void setEnd_date(String aEnd_date) {
		end_date = aEnd_date;
	}

	/**
	 * ���� start_elective �Ļ�ȡ������
	 * 
	 * @return ���� start_elective ��ֵ��
	 */
	public String getStart_elective() {
		return start_elective;
	}

	/**
	 * ���� start_elective �����÷�����
	 * 
	 * @param start_elective
	 *            ���� start_elective ����ֵ��
	 */
	public void setStart_elective(String aStart_elective) {
		start_elective = aStart_elective;
	}

	/**
	 * ���� end_elective �Ļ�ȡ������
	 * 
	 * @return ���� end_elective ��ֵ��
	 */
	public String getEnd_elective() {
		return end_elective;
	}

	/**
	 * ���� end_elective �����÷�����
	 * 
	 * @param end_elective
	 *            ���� end_elective ����ֵ��
	 */
	public void setEnd_elective(String end_elective) {
		this.end_elective = end_elective;
	}

	public String getEnd_test() {
		return end_test;
	}

	public void setEnd_test(String end_test) {
		this.end_test = end_test;
	}

	public String getStart_test() {
		return start_test;
	}

	public void setStart_test(String start_test) {
		this.start_test = start_test;
	}

	/**
	 * ���� selected �Ļ�ȡ������
	 * 
	 * @return ���� selected ��ֵ��
	 */
	public int getSelected() {
		return selected;
	}

	/**
	 * ���� selected �����÷�����
	 * 
	 * @param selected
	 *            ���� selected ����ֵ��
	 */
	public void setSelected(int aSelected) {
		selected = aSelected;
	}

	/**
	 * 
	 * @param major_id
	 * @param page
	 * @return
	 */
	/**
	 * ��ø�ѧ�ڿ����б�
	 * 
	 * @return �����б�
	 * @throws PlatformException
	 */
	public abstract List getCourses() throws PlatformException;

	/**
	 * Ϊ��ѧ�ڿ���
	 * 
	 * @param courses
	 * @throws PlatformException
	 */
	public abstract void addCourses(List courses) throws PlatformException;

	/**
	 * ɾ��ѧ�ڵĿ���
	 * 
	 * @param courses
	 * @throws PlatformException
	 */
	public abstract void removeCourses(List courses) throws PlatformException;

	/**
	 * ��ѧ�ڵĿ�����
	 * 
	 * @return 0Ϊû�п��Σ�����0Ϊ����
	 */
	public abstract int openCourseNum();

	/**
	 * ���ñ�ѧ��Ϊ����
	 * 
	 * @return 0Ϊ���ɹ���1Ϊ�ɹ�
	 */
	public abstract int setActive();

	/**
	 * ����ѧ��Ϊ������
	 * 
	 * @return 0Ϊ���ɹ�;1Ϊ�ɹ�
	 */
	public abstract int cancelActive();

	/**
	 * ��ļ���״̬
	 * 
	 * @return 0Ϊ���ɹ�;1Ϊ�ɹ�
	 */
	public abstract int reverseActive();

}
