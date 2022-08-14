/**
 * 
 */
package cn.zeppin.product.itic.backadmin.vo;

import cn.zeppin.product.itic.core.entity.ToutNumber;
import cn.zeppin.product.itic.core.entity.base.BaseEntity;

/**
 * @author L
 */
public class ToutNumberVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5233988491797513339L;
	
	private Integer id;
	
	private String toMobile;
	private String toTel;
	
	public ToutNumberVO(ToutNumber tr) {
		this.id = tr.getId();
		
		this.toMobile = tr.getToMobile() == null ? "" : tr.getToMobile();
		this.toTel = tr.getToTel() == null ? "" : tr.getToTel();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getToMobile() {
		return toMobile;
	}


	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}


	public String getToTel() {
		return toTel;
	}


	public void setToTel(String toTel) {
		this.toTel = toTel;
	}

}
