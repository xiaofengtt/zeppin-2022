package com.whaty.platform.entity.web.action.publicCourse;

import com.whaty.platform.entity.web.action.MyBaseAction;

public class ArticleAction extends MyBaseAction{

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("文章管理"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("文章标量"), "namee");
		this.getGridConfig().addColumn(this.getText("文章类型"), "trueName");
		this.getGridConfig().addColumn(this.getText("发布日期"), "trueDate");
		this.getGridConfig().addColumn(this.getText("文章内容"), "trueText", false, true, false, "TextEditor",false, 2000);
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/publicCourse/article";
		
	}

}
