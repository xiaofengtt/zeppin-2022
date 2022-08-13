package com.whaty.platform.entity.util;


public class GridPage {

	private GridConfig gridConfig;
	
	private String template;
	
	public GridConfig getGridConfig() {
		return gridConfig;
	}

	public void setGridConfig(GridConfig gridConfig) {
		this.gridConfig = gridConfig;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String output() {
		return null;
	}
	
	public GridPage(GridConfig gridConfig) {
		this.gridConfig = gridConfig;
		initTemplate();
	}
	
	private void initTemplate() {
		this.setTemplate("");
	}
}
