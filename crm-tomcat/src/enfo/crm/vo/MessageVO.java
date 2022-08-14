package enfo.crm.vo;

/**
 *  
 * @author Felix
 * @since 2008-5-20
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class MessageVO {
	private Integer book_code = new Integer(0);

		private Integer serial_no = new Integer(0);
		private Integer end_date = new Integer(0);
		private Integer input_man = new Integer(0);
		private Integer rec_no = new Integer(0);
		private Integer op_code = null;
		private Integer read_flag = null;
		private Integer tast_type = null;	
		private String title = ""; //	Varchar	24		N		科目
		private String info_STR = ""; //	Varchar	24		N		科目
		private String op_name = ""; //	Varchar	24		N		科目	
		private java.sql.Timestamp input_time;
		private String menu_id="";
		private Integer read_times=null;
		private Integer status = null; //状态 1正常，2关闭		
		private Integer df_serial_no = null; //对方ID
		private Integer flag = null; //标志1删除，2关闭 3重开
	private String busi_id="";
		

	/**
	 * @return
	 */
	public Integer getBook_code() {
		return book_code;
	}

		/**
		 * @return
		 */
		public Integer getDf_serial_no() {
			return df_serial_no;
		}

		/**
		 * @return
		 */
		public Integer getEnd_date() {
			return end_date;
		}

		/**
		 * @return
		 */
		public Integer getFlag() {
			return flag;
		}

		/**
		 * @return
		 */
		public String getInfo_STR() {
			return info_STR;
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
		public java.sql.Timestamp getInput_time() {
			return input_time;
		}

		/**
		 * @return
		 */
		public String getMenu_id() {
			return menu_id;
		}

		/**
		 * @return
		 */
		public Integer getOp_code() {
			return op_code;
		}

		/**
		 * @return
		 */
		public String getOp_name() {
			return op_name;
		}

		/**
		 * @return
		 */
		public Integer getRead_flag() {
			return read_flag;
		}

		/**
		 * @return
		 */
		public Integer getRead_times() {
			return read_times;
		}

		/**
		 * @return
		 */
		public Integer getRec_no() {
			return rec_no;
		}

		/**
		 * @return
		 */
		public Integer getSerial_no() {
			return serial_no;
		}

		/**
		 * @return
		 */
		public Integer getStatus() {
			return status;
		}

		/**
		 * @return
		 */
		public Integer getTast_type() {
			return tast_type;
		}

		/**
		 * @return
		 */
		public String getTitle() {
			return title;
		}

	/**
	 * @param integer
	 */
	public void setBook_code(Integer integer) {
		book_code = integer;
	}

		/**
		 * @param integer
		 */
		public void setDf_serial_no(Integer integer) {
			df_serial_no = integer;
		}

		/**
		 * @param integer
		 */
		public void setEnd_date(Integer integer) {
			end_date = integer;
		}

		/**
		 * @param integer
		 */
		public void setFlag(Integer integer) {
			flag = integer;
		}

		/**
		 * @param string
		 */
		public void setInfo_STR(String string) {
			info_STR = string;
		}

		/**
		 * @param integer
		 */
		public void setInput_man(Integer integer) {
			input_man = integer;
		}

		/**
		 * @param timestamp
		 */
		public void setInput_time(java.sql.Timestamp timestamp) {
			input_time = timestamp;
		}

		/**
		 * @param string
		 */
		public void setMenu_id(String string) {
			menu_id = string;
		}

		/**
		 * @param integer
		 */
		public void setOp_code(Integer integer) {
			op_code = integer;
		}

		/**
		 * @param string
		 */
		public void setOp_name(String string) {
			op_name = string;
		}

		/**
		 * @param integer
		 */
		public void setRead_flag(Integer integer) {
			read_flag = integer;
		}

		/**
		 * @param integer
		 */
		public void setRead_times(Integer integer) {
			read_times = integer;
		}

		/**
		 * @param integer
		 */
		public void setRec_no(Integer integer) {
			rec_no = integer;
		}

		/**
		 * @param integer
		 */
		public void setSerial_no(Integer integer) {
			serial_no = integer;
		}

		/**
		 * @param integer
		 */
		public void setStatus(Integer integer) {
			status = integer;
		}

		/**
		 * @param integer
		 */
		public void setTast_type(Integer integer) {
			tast_type = integer;
		}

		/**
		 * @param string
		 */
		public void setTitle(String string) {
			title = string;
		}

	/**
	 * @return
	 */
	public String getBusi_id() {
		return busi_id;
	}

	/**
	 * @param string
	 */
	public void setBusi_id(String string) {
		busi_id = string;
	}

}
