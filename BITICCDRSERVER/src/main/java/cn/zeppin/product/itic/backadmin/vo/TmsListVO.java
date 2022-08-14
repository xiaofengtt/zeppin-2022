/**
 * 
 */
package cn.zeppin.product.itic.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.itic.core.entity.TmsList;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationStatus;
import cn.zeppin.product.itic.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.CdrUtlity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author L
 */
public class TmsListVO extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6723305311721217199L;

	private Integer id;
	
	private String status;
	private String statusCN;
	
	private Timestamp createtime;
	private String createtimeCN;
	
	private String toMobile;
	private String tcPhone;
	private String tcTel;
	private String toTel;
	
	private String startcallertime;//主叫应答时间
	private String abstartcalltime;//通话开始时间
	private String abstopcalltime;//通话结束时间
	private String callerduration;//主叫通话时长（单位：秒）
	
	private String callerrelcause;//主叫结束原因
	private String callerrelcauseCN;
	
	private String calledduration;//被叫通话时长
	private String calledrelcause;//被叫结束原因
	private String calledrelcauseCN;
	
	private String srfmsgid;//通话录音路径
	private String realpath;//本地通话录音存储路径(判断是否为空字符串 如果为空 显示“未下载” 否则“下载”并可以点击下载)
	
	private Integer custId;
	private String custName;
	
	private Integer opCode;
	private String opName;
	
	public TmsListVO(TmsList tr) {
		this.id = tr.getId();
		this.status = tr.getStatus();
		if(TnumberRelationStatus.NORMAL.equals(tr.getStatus())) {
			this.statusCN = "正常";
		} else if(TnumberRelationStatus.UNABLE.equals(tr.getStatus())) {
			this.statusCN = "失效";
		} else if(TnumberRelationStatus.DELETED.equals(tr.getStatus())) {
			this.statusCN = "已删除";
		}
		
		this.createtime = tr.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(tr.getCreatetime());
		
		this.toMobile = tr.getCallernum() == null ? "" : tr.getCallernum();
		this.toTel = tr.getCalleddisplaynumber() == null ? "" : tr.getCalleddisplaynumber();
		this.tcPhone = tr.getCallednum() == null ? "" : tr.getCallednum();
		this.tcTel = tr.getCallerdisplaynumber() == null ? "" : tr.getCallerdisplaynumber();
		
		this.startcallertime = tr.getStartcallertime() == null ? "" : tr.getStartcallertime();
		this.abstartcalltime = tr.getAbstartcalltime() == null ? "" : tr.getAbstartcalltime();
		this.abstopcalltime = tr.getAbstopcalltime() == null ? "" : tr.getAbstopcalltime();
		this.callerduration = tr.getCallerduration() == null ? "" : tr.getCallerduration();
		
		this.callerrelcause = tr.getCallerrelcause() == null ? "" : tr.getCallerrelcause();
		this.callerrelcauseCN = CdrUtlity.getReason(this.callerrelcause);
		
		this.calledduration = tr.getCalledduration() == null ? "" : tr.getCalledduration();
		this.calledrelcause = tr.getCalledrelcause() == null ? "" : tr.getCalledrelcause();
		this.calledrelcauseCN = CdrUtlity.getReason(this.calledrelcause);
		
		this.srfmsgid = tr.getSrfmsgid() == null ? "" : tr.getSrfmsgid();
		
		this.realpath = tr.getRealpath() == null ? "" : "realpath";
		
		this.custId = tr.getFkTcustomers();
		this.custName = "";
		this.opCode = tr.getFkToperator();
		this.opName = "";
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusCN() {
		return statusCN;
	}


	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}


	public Timestamp getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}


	public String getCreatetimeCN() {
		return createtimeCN;
	}


	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}


	public Integer getCustId() {
		return custId;
	}


	public void setCustId(Integer custId) {
		this.custId = custId;
	}


	public String getCustName() {
		return custName;
	}


	public void setCustName(String custName) {
		this.custName = custName;
	}


	public Integer getOpCode() {
		return opCode;
	}


	public void setOpCode(Integer opCode) {
		this.opCode = opCode;
	}


	public String getOpName() {
		return opName;
	}


	public void setOpName(String opName) {
		this.opName = opName;
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


	public String getTcPhone() {
		return tcPhone;
	}


	public void setTcPhone(String tcPhone) {
		this.tcPhone = tcPhone;
	}


	public String getTcTel() {
		return tcTel;
	}


	public void setTcTel(String tcTel) {
		this.tcTel = tcTel;
	}


	public String getStartcallertime() {
		return startcallertime;
	}


	public void setStartcallertime(String startcallertime) {
		this.startcallertime = startcallertime;
	}


	public String getAbstartcalltime() {
		return abstartcalltime;
	}


	public void setAbstartcalltime(String abstartcalltime) {
		this.abstartcalltime = abstartcalltime;
	}


	public String getAbstopcalltime() {
		return abstopcalltime;
	}


	public void setAbstopcalltime(String abstopcalltime) {
		this.abstopcalltime = abstopcalltime;
	}


	public String getCallerduration() {
		return callerduration;
	}


	public void setCallerduration(String callerduration) {
		this.callerduration = callerduration;
	}


	public String getCallerrelcause() {
		return callerrelcause;
	}


	public void setCallerrelcause(String callerrelcause) {
		this.callerrelcause = callerrelcause;
	}


	public String getCalledduration() {
		return calledduration;
	}


	public void setCalledduration(String calledduration) {
		this.calledduration = calledduration;
	}


	public String getCalledrelcause() {
		return calledrelcause;
	}


	public void setCalledrelcause(String calledrelcause) {
		this.calledrelcause = calledrelcause;
	}


	public String getSrfmsgid() {
		return srfmsgid;
	}


	public void setSrfmsgid(String srfmsgid) {
		this.srfmsgid = srfmsgid;
	}


	public String getRealpath() {
		return realpath;
	}


	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}


	public String getCallerrelcauseCN() {
		return callerrelcauseCN;
	}


	public void setCallerrelcauseCN(String callerrelcauseCN) {
		this.callerrelcauseCN = callerrelcauseCN;
	}


	public String getCalledrelcauseCN() {
		return calledrelcauseCN;
	}


	public void setCalledrelcauseCN(String calledrelcauseCN) {
		this.calledrelcauseCN = calledrelcauseCN;
	}

}
