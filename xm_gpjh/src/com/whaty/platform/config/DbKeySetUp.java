package com.whaty.platform.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;


public class DbKeySetUp {
	
	/**
	 * 数据库外键和唯一性约束信息初始化
	 */
	private  static String FOREIGNKEYFILENAME="foreignKey.xml";
	
	private  static String ALTERNATEKEYFILENAME="alternateKey.xml";
	
	private String dbKeySetUpAbsPath;
	
	private Map foreignKeys;
	
	private Map alternateKeys;



	public Map getAlternateKeys() {
		return alternateKeys;
	}

	public void setAlternateKeys(Map alternateKeys) {
		this.alternateKeys = alternateKeys;
	}

	public Map getForeignKeys() {
		return foreignKeys;
	}

	public void setForeignKeys(Map foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	public DbKeySetUp() {
		super();
	}
	
	public DbKeySetUp(String dbKeySetUpAbsPath) {
		this.dbKeySetUpAbsPath = dbKeySetUpAbsPath;
		this.setForeignKeys(new HashMap());
		this.setAlternateKeys(new HashMap());
	}

	public void setUp() {
		setForeignKey();
		setAlternateKey();
	}
	
	private void setAlternateKey() {
		File file = new File(this.dbKeySetUpAbsPath
				+ DbKeySetUp.ALTERNATEKEYFILENAME);
		if (!file.exists()) {
			throw new RuntimeException("can't find the file"
					+ file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
			List list = document.selectNodes("//alternateKey");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Node node = (Node) iter.next();
				AlternateKey alternateKey = new AlternateKey();
				List colu = new ArrayList();
				alternateKey.setKeyName(((DefaultElement)node).attributeValue("name"));
				alternateKey.setTable(node.selectSingleNode("table").getText());
				List columns = node.selectSingleNode("columns").selectNodes("column");
				
				for (Iterator column = columns.iterator(); column.hasNext();) {
					Node columnNode = (Node) column.next();
					colu.add(columnNode.getText());
				}
				alternateKey.setColumns(colu);
				this.alternateKeys.put(alternateKey.getKeyName(), alternateKey);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	private void setForeignKey() {
		File file = new File(this.dbKeySetUpAbsPath
				+ DbKeySetUp.FOREIGNKEYFILENAME);
		if (!file.exists()) {
			throw new RuntimeException("can't find the file"
					+ file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
			List list = document.selectNodes("//foreignKey");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Node node = (Node) iter.next();
				ForeignKey foreignKey = new ForeignKey();
				foreignKey.setKeyName(((DefaultElement)node).attributeValue("name"));
				foreignKey.setParentTable(node.selectSingleNode("parentTable").getText());
				foreignKey.setChildTable(node.selectSingleNode("childTable").getText());
				foreignKey.setChildTableNote(node.selectSingleNode("childTableNote").getText());
				this.foreignKeys.put(foreignKey.getKeyName(), foreignKey);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
