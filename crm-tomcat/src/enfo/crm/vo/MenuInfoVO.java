/*
 * �������� 2008-5-26
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author guifeng
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class MenuInfoVO {
	private String menu_id;//�˵����
	private String menu_name;//�˵�����
	private String menu_info;//�˵�˵��
	private String parent_id;//���˵���
	private Integer bottom_flag;//��ͼ��˵���־1��0��
	private String url;
	private Integer tree_order;//���������ֶ�
	private Integer user_id;//�û�ID��Ϊ0��ʾ�ò˵������û������ã�ĳ���û�����Ҫʱ�������ֶ�����Ϊ�������û�ID�ķ�0ֵ����
	private Integer inputman;
	/**
	 * @return
	 */
	public Integer getBottom_flag() {
		return bottom_flag;
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
	public String getMenu_info() {
		return menu_info;
	}

	/**
	 * @return
	 */
	public String getMenu_name() {
		return menu_name;
	}

	/**
	 * @return
	 */
	public String getParent_id() {
		return parent_id;
	}

	/**
	 * @return
	 */
	public Integer getTree_order() {
		return tree_order;
	}

	/**
	 * @return
	 */
	public String getUrl() {
		return url;
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
	public void setBottom_flag(Integer integer) {
		bottom_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setMenu_id(String string) {
		menu_id = string;
	}

	/**
	 * @param string
	 */
	public void setMenu_info(String string) {
		menu_info = string;
	}

	/**
	 * @param string
	 */
	public void setMenu_name(String string) {
		menu_name = string;
	}

	/**
	 * @param string
	 */
	public void setParent_id(String string) {
		parent_id = string;
	}

	/**
	 * @param integer
	 */
	public void setTree_order(Integer integer) {
		tree_order = integer;
	}

	/**
	 * @param string
	 */
	public void setUrl(String string) {
		url = string;
	}

	/**
	 * @param integer
	 */
	public void setUser_id(Integer integer) {
		user_id = integer;
	}

}
