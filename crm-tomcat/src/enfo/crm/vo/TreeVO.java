/*
 * �������� 2011-2-15
 *
 * 
 */
package enfo.crm.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangmy
 *
 * �������νṹ����Ҫ������
 */
public class TreeVO {

	private Integer id;

	private String name;
	
	private Integer parentId;
	
	private boolean open;
	
	private String zclb_bh;//�ʲ������
	private Integer zc_flag; //�ʲ���ʶ
	private Integer bottom_flag;
	private String caption;

	private String Code;
	
	private String state;
	
	private boolean isParent;
	
	private Integer productId;//��ƷId
	
	private String faq_class_no;
	
	private String faq_class_name;
	
	private List nodes = new ArrayList();
	
	
	/**
	 * @return ���� id��
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id Ҫ���õ� id��
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return ���� name��
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name Ҫ���õ� name��
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return ���� parentId��
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * @param parentId Ҫ���õ� parentId��
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return ���� nodes��
	 */
	public List getNodes() {
		return nodes;
	}
	/**
	 * @param nodes Ҫ���õ� nodes��
	 */
	public void setNodes(List nodes) {
		this.nodes = nodes;
	}
	
	/**
	 * @return ���� open��
	 */
	public boolean isOpen() {
		return open;
	}
	/**
	 * @param open Ҫ���õ� open��
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * @return ����zclb_bh
	 */
	public String getZclb_bh() {
		return zclb_bh;
	}
	/**
	 * @param zclb_bh Ҫ���õ�zclb_bh
	 */
	public void setZclb_bh(String zclb_bh) {
		this.zclb_bh = zclb_bh;
	}
	public Integer getBottom_flag() {
		return bottom_flag;
	}
	public void setBottom_flag(Integer bottom_flag) {
		this.bottom_flag = bottom_flag;
	}
	public Integer getZc_flag() {
		return zc_flag;
	}
	public void setZc_flag(Integer zc_flag) {
		this.zc_flag = zc_flag;
	}
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return Code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		Code = code;
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return Returns the isParent.
	 */
	public boolean getIsParent() {
		return isParent;
	}
	/**
	 * @param isParent The isParent to set.
	 */
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	/**
	 * @return Returns the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId The productId to set.
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * @return ���� faq_class_name��
	 */
	public String getFaq_class_name() {
		return faq_class_name;
	}
	/**
	 * @param faq_class_name Ҫ���õ� faq_class_name��
	 */
	public void setFaq_class_name(String faq_class_name) {
		this.faq_class_name = faq_class_name;
	}
	/**
	 * @return ���� faq_class_no��
	 */
	public String getFaq_class_no() {
		return faq_class_no;
	}
	/**
	 * @param faq_class_no Ҫ���õ� faq_class_no��
	 */
	public void setFaq_class_no(String faq_class_no) {
		this.faq_class_no = faq_class_no;
	}
}

