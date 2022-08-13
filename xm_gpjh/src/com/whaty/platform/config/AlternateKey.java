package com.whaty.platform.config;

import java.util.List;

public class AlternateKey {
	
	private String keyName;
	
	private String table;
	
	private List columns;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List getColumns() {
		return columns;
	}

	public void setColumns(List columns) {
		this.columns = columns;
	}

}
