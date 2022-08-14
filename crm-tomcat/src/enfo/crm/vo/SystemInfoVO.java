/*
 * 创建日期 2008-5-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SystemInfoVO {
	private Integer user_id;
	private String user_name;
	private String backUp_URl;
	private String address;
	private String post;
	private String tel;
	private String fax;
	
	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @return
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @return
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @return
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address = string;
	}


	/**
	 * @param string
	 */
	public void setFax(String string) {
		fax = string;
	}

	/**
	 * @param string
	 */
	public void setPost(String string) {
		post = string;
	}

	/**
	 * @param string
	 */
	public void setTel(String string) {
		tel = string;
	}

	/**
	 * @param string
	 */
	public void setUser_name(String string) {
		user_name = string;
	}

	/**
	 * @return
	 */
	public Integer getUser_id() {
		return user_id;
	}

	/**
	 * @param integer
	 */
	public void setUser_id(Integer integer) {
		user_id = integer;
	}

	/**
	 * @return
	 */
	public String getBackUp_URl() {
		return backUp_URl;
	}

	/**
	 * @param string
	 */
	public void setBackUp_URl(String string) {
		backUp_URl = string;
	}

}
