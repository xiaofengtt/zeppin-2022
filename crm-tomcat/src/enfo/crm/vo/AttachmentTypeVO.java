/*
 * �������� 2011-4-13
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author my
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class AttachmentTypeVO {

	private Integer attachment_type_id;
	private String attachment_type_name;
	private String attachment_type_summary;
	private Integer is_necessary;
	private Integer df_table_id;
	private String df_table_name;
	
	

	/**
	 * @return
	 */
	public Integer getAttachmentType_id() {
		return attachment_type_id;
	}

	/**
	 * @return
	 */
	public String getAttachmentType_name() {
		return attachment_type_name;
	}

	/**
	 * @return
	 */
	public String getAttachmentTypeSummary() {
		return attachment_type_summary;
	}


	/**
	 * @param integer
	 */
	public void setAttachmentType_id(Integer integer) {
		attachment_type_id = integer;
	}

	/**
	 * @param string
	 */
	public void setAttachmentType_name(String string) {
		attachment_type_name = string;
	}

	/**
	 * @param string
	 */
	public void setAttachmentTypeSummary(String string) {
		attachment_type_summary = string;
	}
	
	public Integer getIS_Necessary() {
		return is_necessary;
	}

	public void setIS_Necessary(Integer is_necessary) {
		this.is_necessary = is_necessary;
	}

	public Integer getDF_Table_id() {
		return df_table_id;
	}

	public void setDF_Table_id(Integer df_table_id) {
		this.df_table_id = df_table_id;
	}
	
	public String getDF_Table_name() {
		return df_table_name;
	}
	
	public void setDF_Table_name(String string) {
		df_table_name = string;
	}

}
