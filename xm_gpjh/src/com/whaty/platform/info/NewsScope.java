package com.whaty.platform.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <p>
 * NewsScope �������ŷ�����Χ,����һ���ַ� Ȼ����Խ��ͣ��洢�� HashMap ֮��<br>
 * ����������ƣ� |teachers,002,003,004,45632||sites,3423,423,123123,43234| <br>
 * ����<br>
 * ÿ��������ʼ�ͽ����� '|',<br>
 * ��ʼ��ǽ��һ����������,����ͳһ�ø���<br>
 * ÿһ������ֵ֮ǰ��һ���<br>
 * ��ע����������ֵ�к���"all"���� �������û������
 * </p>
 */
public class NewsScope {

	private String propertyString;

	private Map properties;

	/**
	 * ���캯��ͨ��һ���ַ����õ�һ��Map, ÿ��key��Ӧ��һ����Ӧ��������ƣ�value��Ӧ����һ��list
	 * 
	 * @param propertyString
	 *            һ������ַ����������Ʒ�Χ
	 */
	public NewsScope(String propertyString) {
		this.propertyString = propertyString;
		this.properties = parse(propertyString);
	}

	/**
	 * �õ�һ��Map, ÿ��key��Ӧ��һ����Ӧ��������ƣ�value��Ӧ����һ��list
	 * 
	 * @param propertyString
	 *            һ������ַ����������Ʒ�Χ
	 * @return
	 */
	public static Map parse(String propertyString) {
		if (propertyString == null)
			return null;
		StringTokenizer st = new StringTokenizer(propertyString, "|");
		Map properties = null;
		while (st.hasMoreTokens()) {
			StringTokenizer st2 = new StringTokenizer(st.nextToken(), ",");
			boolean isFirst = true;
			String name = "";
			List list = new ArrayList();
			while (st2.hasMoreTokens()) {
				if (isFirst) {
					name = st2.nextToken();
					isFirst = false;
				} else {
					list.add(st2.nextToken());
				}
			}
			// ��������ֵ�а�"all"�������û������
			if (list.size() > 0 && !list.contains("all")) {
				if (properties == null)
					properties = new HashMap();
				properties.put(name, list);
			}
		}
		return properties;
	}

	/**
	 * 
	 * @return һ������ַ�
	 */
	public String getPropertyString() {
		return this.propertyString;
	}

	/**
	 * ����һ������ַ�ͬʱ�����һ��Map
	 * 
	 * @param propertyString
	 */
	public void setPropertyString(String propertyString) {
		this.propertyString = propertyString;
		this.properties = parse(propertyString);
	}

	/**
	 * �õ�һ���ɹ����ַ�����4��Map
	 * 
	 * @return
	 */
	public Map getProperties() {
		return properties;
	}

	/**
	 * �̳и���toString()
	 */
	public String toString() {
		String out = "";
		out += "propertyString=" + propertyString + "\n";
		out += "properties:" + "\n";
		if (properties == null || properties.size() == 0)
			out += "null";
		else {
			Set set = properties.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String temp = (String) it.next();
				out += "  " + temp + "=" + properties.get(temp) + "\n";
			}
		}
		return out;
	}

	public static void main(String[] args) {
		String temp = "|managers,all||teachers,002,003,004,45632||||sites,3423,423,123123,43234|";
		NewsScope ns = new NewsScope(temp);
		System.out.println(ns);
	}
}
