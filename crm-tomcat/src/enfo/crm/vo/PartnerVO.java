/*
 * 创建日期 2010-3-2
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

/**
 * @author taochen
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PartnerVO {
		private java.lang.Integer partn_id;
		private java.lang.String partn_no;
		private java.lang.String partn_name;	
		private String partn_tel;
		private String post_address;
		private String post_address2;
		private String post_code;
		private String post_code2;		
		private java.lang.String card_type;
		private java.lang.String card_type_name;
		private java.lang.String card_id;
		private Integer card_valid_date; //客户身份证 有效期限 8位日期表示
	    private String country; //客户国籍（9901）
		private Integer birthday;
		private Integer age;
		private Integer sex;
		private String sex_name;
		private String o_tel;
		private String h_tel;
		private String mobile;
		private String bp;
		private String fax;
		private String e_mail;	
		private Integer partn_type;
		private String jg_partn_type;
		private String touch_type;
		private String touch_type_Name;
		private String summary;
		private String legal_man;
		private String legal_man_address;
		private String contract_man;
		private Integer service_man;
		private String voc_type;
		private String report_type;
		private Integer partn_type2_flag;
		private Integer cust_id;
		private Integer input_man;
	
		/**
		 * @return
		 */
		public Integer getAge() {
			return age;
		}

		/**
		 * @return
		 */
		public Integer getBirthday() {
			return birthday;
		}

		/**
		 * @return
		 */
		public String getBp() {
			return bp;
		}

		/**
		 * @return
		 */
		public java.lang.String getCard_id() {
			return card_id;
		}

		/**
		 * @return
		 */
		public java.lang.String getCard_type() {
			return card_type;
		}

		/**
		 * @return
		 */
		public java.lang.String getCard_type_name() {
			return card_type_name;
		}

		/**
		 * @return
		 */
		public Integer getCard_valid_date() {
			return card_valid_date;
		}

		/**
		 * @return
		 */
		public String getContract_man() {
			return contract_man;
		}

		/**
		 * @return
		 */
		public String getCountry() {
			return country;
		}

		/**
		 * @return
		 */
		public Integer getCust_id() {
			return cust_id;
		}

		/**
		 * @return
		 */
		public String getE_mail() {
			return e_mail;
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
		public String getH_tel() {
			return h_tel;
		}

		/**
		 * @return
		 */
		public Integer getInput_man() {
			return input_man;
		}

		/**
		 * @return
		 */
		public String getJg_partn_type() {
			return jg_partn_type;
		}

		/**
		 * @return
		 */
		public String getLegal_man() {
			return legal_man;
		}

		/**
		 * @return
		 */
		public String getLegal_man_address() {
			return legal_man_address;
		}

		/**
		 * @return
		 */
		public String getMobile() {
			return mobile;
		}

		/**
		 * @return
		 */
		public String getO_tel() {
			return o_tel;
		}

		/**
		 * @return
		 */
		public java.lang.Integer getPartn_id() {
			return partn_id;
		}

		/**
		 * @return
		 */
		public java.lang.String getPartn_name() {
			return partn_name;
		}

		/**
		 * @return
		 */
		public java.lang.String getPartn_no() {
			return partn_no;
		}

		/**
		 * @return
		 */
		public String getPartn_tel() {
			return partn_tel;
		}

		/**
		 * @return
		 */
		public String getPost_address() {
			return post_address;
		}

		/**
		 * @return
		 */
		public String getPost_address2() {
			return post_address2;
		}

		/**
		 * @return
		 */
		public String getPost_code() {
			return post_code;
		}

		/**
		 * @return
		 */
		public String getPost_code2() {
			return post_code2;
		}

		/**
		 * @return
		 */
		public String getReport_type() {
			return report_type;
		}

		/**
		 * @return
		 */
		public Integer getService_man() {
			return service_man;
		}

		/**
		 * @return
		 */
		public Integer getSex() {
			return sex;
		}

		/**
		 * @return
		 */
		public String getSex_name() {
			return sex_name;
		}

		/**
		 * @return
		 */
		public String getSummary() {
			return summary;
		}

		/**
		 * @return
		 */
		public String getTouch_type() {
			return touch_type;
		}

		/**
		 * @return
		 */
		public String getTouch_type_Name() {
			return touch_type_Name;
		}

		/**
		 * @return
		 */
		public String getVoc_type() {
			return voc_type;
		}

		/**
		 * @param integer
		 */
		public void setAge(Integer integer) {
			age = integer;
		}

		/**
		 * @param integer
		 */
		public void setBirthday(Integer integer) {
			birthday = integer;
		}

		/**
		 * @param string
		 */
		public void setBp(String string) {
			bp = string;
		}

		/**
		 * @param string
		 */
		public void setCard_id(java.lang.String string) {
			card_id = string;
		}

		/**
		 * @param string
		 */
		public void setCard_type(java.lang.String string) {
			card_type = string;
		}

		/**
		 * @param string
		 */
		public void setCard_type_name(java.lang.String string) {
			card_type_name = string;
		}

		/**
		 * @param integer
		 */
		public void setCard_valid_date(Integer integer) {
			card_valid_date = integer;
		}

		/**
		 * @param string
		 */
		public void setContract_man(String string) {
			contract_man = string;
		}

		/**
		 * @param string
		 */
		public void setCountry(String string) {
			country = string;
		}

		/**
		 * @param integer
		 */
		public void setCust_id(Integer integer) {
			cust_id = integer;
		}

		/**
		 * @param string
		 */
		public void setE_mail(String string) {
			e_mail = string;
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
		public void setH_tel(String string) {
			h_tel = string;
		}

		/**
		 * @param integer
		 */
		public void setInput_man(Integer integer) {
			input_man = integer;
		}

		/**
		 * @param string
		 */
		public void setJg_partn_type(String string) {
			jg_partn_type = string;
		}

		/**
		 * @param string
		 */
		public void setLegal_man(String string) {
			legal_man = string;
		}

		/**
		 * @param string
		 */
		public void setLegal_man_address(String string) {
			legal_man_address = string;
		}

		/**
		 * @param string
		 */
		public void setMobile(String string) {
			mobile = string;
		}

		/**
		 * @param string
		 */
		public void setO_tel(String string) {
			o_tel = string;
		}

		/**
		 * @param integer
		 */
		public void setPartn_id(java.lang.Integer integer) {
			partn_id = integer;
		}

		/**
		 * @param string
		 */
		public void setPartn_name(java.lang.String string) {
			partn_name = string;
		}

		/**
		 * @param string
		 */
		public void setPartn_no(java.lang.String string) {
			partn_no = string;
		}

		/**
		 * @param string
		 */
		public void setPartn_tel(String string) {
			partn_tel = string;
		}

		/**
		 * @param string
		 */
		public void setPost_address(String string) {
			post_address = string;
		}

		/**
		 * @param string
		 */
		public void setPost_address2(String string) {
			post_address2 = string;
		}

		/**
		 * @param string
		 */
		public void setPost_code(String string) {
			post_code = string;
		}

		/**
		 * @param string
		 */
		public void setPost_code2(String string) {
			post_code2 = string;
		}

		/**
		 * @param string
		 */
		public void setReport_type(String string) {
			report_type = string;
		}

		/**
		 * @param integer
		 */
		public void setService_man(Integer integer) {
			service_man = integer;
		}

		/**
		 * @param integer
		 */
		public void setSex(Integer integer) {
			sex = integer;
		}

		/**
		 * @param string
		 */
		public void setSex_name(String string) {
			sex_name = string;
		}

		/**
		 * @param string
		 */
		public void setSummary(String string) {
			summary = string;
		}

		/**
		 * @param string
		 */
		public void setTouch_type(String string) {
			touch_type = string;
		}

		/**
		 * @param string
		 */
		public void setTouch_type_Name(String string) {
			touch_type_Name = string;
		}

		/**
		 * @param string
		 */
		public void setVoc_type(String string) {
			voc_type = string;
		}
		/**
		 * @return
		 */
		public Integer getPartn_type2_flag() {
			return partn_type2_flag;
		}

		/**
		 * @param integer
		 */
		public void setPartn_type2_flag(Integer integer) {
			partn_type2_flag = integer;
		}

		/**
		 * @return
		 */
		public Integer getPartn_type() {
			return partn_type;
		}

		/**
		 * @param integer
		 */
		public void setPartn_type(Integer integer) {
			partn_type = integer;
		}

}
