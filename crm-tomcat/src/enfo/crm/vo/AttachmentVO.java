package enfo.crm.vo;

public class AttachmentVO {
	private Integer attachment_id;//附件id
    private Integer df_talbe_id;//对应表id
    private String df_table_name;//对应表说明
    private Integer df_serial_no;//对应表记录序号
    private String save_name;//保存路径+文件名
    private String origin_name;//原始文件名
    private Integer file_size;//文件大小
    private String description;//描述
    private Integer input_man;
    private String temp_df_id;//附件所属对象临时ID(如果为联合主键可以用“;”分隔,待唯一主键确定后清空此字段)
    
	public Integer getAttachment_id() {
		return attachment_id;
	}
	public void setAttachment_id(Integer attachment_id) {
		this.attachment_id = attachment_id;
	}
	public Integer getDf_talbe_id() {
		return df_talbe_id;
	}
	public void setDf_talbe_id(Integer df_talbe_id) {
		this.df_talbe_id = df_talbe_id;
	}
	public String getDf_table_name() {
		return df_table_name;
	}
	public void setDf_table_name(String df_table_name) {
		this.df_table_name = df_table_name;
	}
	public Integer getDf_serial_no() {
		return df_serial_no;
	}
	public void setDf_serial_no(Integer df_serial_no) {
		this.df_serial_no = df_serial_no;
	}
	public String getSave_name() {
		return save_name;
	}
	public void setSave_name(String save_name) {
		this.save_name = save_name;
	}
	public String getOrigin_name() {
		return origin_name;
	}
	public void setOrigin_name(String origin_name) {
		this.origin_name = origin_name;
	}
	public Integer getFile_size() {
		return file_size;
	}
	public void setFile_size(Integer file_size) {
		this.file_size = file_size;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	public String getTemp_df_id() {
		return temp_df_id;
	}
	public void setTemp_df_id(String temp_df_id) {
		this.temp_df_id = temp_df_id;
	}
}
