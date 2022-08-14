/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.InvestorIdcardImg;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe 2016年2月8日
 * @description 【数据对象】支行信息
 */

public class InvestorIdcardImgVO extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8221448582461544864L;
	private String uuid;
	private String imgface;
	private String imgfaceurl;
	private String imgback;
	private String imgbackurl;
	private String reason;
	private String status;
	private String creator;
	private Timestamp createtime;
	private String createtimeCN;
	
	private String checker;
	private String checkerName;
	private Timestamp checktime;
	private String checktimeCN;
	
	private String name;
	private String idcard;
	
	public InvestorIdcardImgVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InvestorIdcardImgVO(InvestorIdcardImg idcardimg){
		this.uuid = idcardimg.getUuid();
		this.imgface = idcardimg.getImgface();
		this.imgback = idcardimg.getImgback();
		this.reason = idcardimg.getReason();
		this.status = idcardimg.getStatus();
		this.creator = idcardimg.getCreator();
		this.createtime = idcardimg.getCreatetime();
		this.createtimeCN = idcardimg.getCreatetime() == null ? "" : Utlity.timeSpanToString(idcardimg.getCreatetime());
		this.checker = idcardimg.getChecker() == null ? "" : idcardimg.getChecker();
		this.checktime = idcardimg.getChecktime();
		if(idcardimg.getChecktime() != null){
			this.checktimeCN = Utlity.timeSpanToString(idcardimg.getChecktime());
		} else {
			this.checktimeCN = "";
		}
		
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getImgface() {
		return imgface;
	}

	public void setImgface(String imgface) {
		this.imgface = imgface;
	}

	public String getImgfaceurl() {
		return imgfaceurl;
	}

	public void setImgfaceurl(String imgfaceurl) {
		this.imgfaceurl = imgfaceurl;
	}

	public String getImgback() {
		return imgback;
	}

	public void setImgback(String imgback) {
		this.imgback = imgback;
	}

	public String getImgbackurl() {
		return imgbackurl;
	}

	public void setImgbackurl(String imgbackurl) {
		this.imgbackurl = imgbackurl;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getChecktimeCN() {
		return checktimeCN;
	}

	public void setChecktimeCN(String checktimeCN) {
		this.checktimeCN = checktimeCN;
	}

	
	public String getCheckerName() {
		return checkerName;
	}
	

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	
}
