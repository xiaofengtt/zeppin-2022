package cn.product.treasuremall.vo.front;

import java.io.Serializable;

public class CategoryCountVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5174085213569649938L;
	
	private String category;
	private String categoryName;
	private Integer count;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
