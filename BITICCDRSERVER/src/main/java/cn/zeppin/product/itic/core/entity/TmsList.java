/**
 * 
 */
package cn.zeppin.product.itic.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;

/**
 * @author L
 *
 * 
 * @description 【数据对象】TMS_LIST入库
 */
@Entity
@Table(name = "TMS_LIST", uniqueConstraints = {@UniqueConstraint(columnNames = "callid")})
public class TmsList extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141633696155630930L;

	private Integer id;
	private String servicename;
	private String messageid;
	private String servicekey;
	private String callid;
	private String callernum;
	private String callednum;
	private String middlenumber;
	private String callerdisplaynumber;
	private String calleddisplaynumber;
	private String callerstreamno;
	private String startcallertime;
	private String abstartcalltime;
	private String abstopcalltime;
	private String callerduration;
	private String callercost;
	private String callerrelcause;
	private String callerorirescode;
	private String calledstreamno;
	private String startcalledtime;
	private String calledduration;
	private String calledcost;
	private String calledrelcause;
	private String calledorirescode;
	private String srfmsgid;
	private String chargenumber;
	private String callerrelreason;
	private String calledrelreason;
	private String msserver;
	private String middlestarttime;
	private String middlecalltime;
	private String duration;
	private String costcount;
	
	private String realpath;//本地存储路径
	private String status;
	private Timestamp createtime;
	
	private Integer fkTcustomers;
	private Integer fkToperator;
	
	
	public static class TmsListStatus {
		public final static String NORMAL = "normal";
		public final static String UNABLE = "unable";
		public final static String DELETED = "deleted";
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "STATUS", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "SERVICENAME", nullable = false, length = 20)
	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	@Column(name = "MESSAGEID", nullable = false, length = 64)
	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	@Column(name = "SERVICEKEY", nullable = false, length = 20)
	public String getServicekey() {
		return servicekey;
	}

	public void setServicekey(String servicekey) {
		this.servicekey = servicekey;
	}

	@Column(name = "CALLID", nullable = false, length = 50)
	public String getCallid() {
		return callid;
	}

	public void setCallid(String callid) {
		this.callid = callid;
	}

	@Column(name = "CALLERNUM", nullable = false, length = 20)
	public String getCallernum() {
		return callernum;
	}

	public void setCallernum(String callernum) {
		this.callernum = callernum;
	}

	@Column(name = "CALLEDNUM", nullable = false, length = 20)
	public String getCallednum() {
		return callednum;
	}

	public void setCallednum(String callednum) {
		this.callednum = callednum;
	}

	@Column(name = "middlenumber", nullable = false, length = 50)
	public String getMiddlenumber() {
		return middlenumber;
	}

	public void setMiddlenumber(String middlenumber) {
		this.middlenumber = middlenumber;
	}

	@Column(name = "CALLERDISPLAYNUMBER", nullable = false, length = 50)
	public String getCallerdisplaynumber() {
		return callerdisplaynumber;
	}

	public void setCallerdisplaynumber(String callerdisplaynumber) {
		this.callerdisplaynumber = callerdisplaynumber;
	}

	@Column(name = "CALLEDDISPLAYNUMBER", length = 50)
	public String getCalleddisplaynumber() {
		return calleddisplaynumber;
	}

	public void setCalleddisplaynumber(String calleddisplaynumber) {
		this.calleddisplaynumber = calleddisplaynumber;
	}

	@Column(name = "CALLERSTREAMNO", nullable = false, length = 50)
	public String getCallerstreamno() {
		return callerstreamno;
	}

	public void setCallerstreamno(String callerstreamno) {
		this.callerstreamno = callerstreamno;
	}

	@Column(name = "STARTCALLERTIME", length = 50)
	public String getStartcallertime() {
		return startcallertime;
	}

	public void setStartcallertime(String startcallertime) {
		this.startcallertime = startcallertime;
	}

	@Column(name = "ABSTARTCALLTIME", length = 30)
	public String getAbstartcalltime() {
		return abstartcalltime;
	}

	public void setAbstartcalltime(String abstartcalltime) {
		this.abstartcalltime = abstartcalltime;
	}

	@Column(name = "ABSTOPCALLTIME", length = 30)
	public String getAbstopcalltime() {
		return abstopcalltime;
	}

	public void setAbstopcalltime(String abstopcalltime) {
		this.abstopcalltime = abstopcalltime;
	}

	@Column(name = "CALLERDURATION", length = 20)
	public String getCallerduration() {
		return callerduration;
	}

	public void setCallerduration(String callerduration) {
		this.callerduration = callerduration;
	}

	@Column(name = "CALLERCOST", length = 20)
	public String getCallercost() {
		return callercost;
	}

	public void setCallercost(String callercost) {
		this.callercost = callercost;
	}

	@Column(name = "CALLERRELCAUSE", nullable = false, length = 10)
	public String getCallerrelcause() {
		return callerrelcause;
	}

	public void setCallerrelcause(String callerrelcause) {
		this.callerrelcause = callerrelcause;
	}

	@Column(name = "CALLERORIRESCODE", length = 50)
	public String getCallerorirescode() {
		return callerorirescode;
	}

	public void setCallerorirescode(String callerorirescode) {
		this.callerorirescode = callerorirescode;
	}

	@Column(name = "CALLEDSTREAMNO", length = 50)
	public String getCalledstreamno() {
		return calledstreamno;
	}

	public void setCalledstreamno(String calledstreamno) {
		this.calledstreamno = calledstreamno;
	}

	@Column(name = "STARTCALLEDTIME", length = 50)
	public String getStartcalledtime() {
		return startcalledtime;
	}

	public void setStartcalledtime(String startcalledtime) {
		this.startcalledtime = startcalledtime;
	}

	@Column(name = "CALLEDDURATION", length = 50)
	public String getCalledduration() {
		return calledduration;
	}

	public void setCalledduration(String calledduration) {
		this.calledduration = calledduration;
	}

	@Column(name = "CALLEDCOST", length = 50)
	public String getCalledcost() {
		return calledcost;
	}

	public void setCalledcost(String calledcost) {
		this.calledcost = calledcost;
	}

	@Column(name = "CALLEDRELCAUSE", length = 50)
	public String getCalledrelcause() {
		return calledrelcause;
	}

	public void setCalledrelcause(String calledrelcause) {
		this.calledrelcause = calledrelcause;
	}

	@Column(name = "CALLEDORIRESCODE", length = 50)
	public String getCalledorirescode() {
		return calledorirescode;
	}

	public void setCalledorirescode(String calledorirescode) {
		this.calledorirescode = calledorirescode;
	}

	@Column(name = "srfmsgid", length = 200)
	public String getSrfmsgid() {
		return srfmsgid;
	}

	public void setSrfmsgid(String srfmsgid) {
		this.srfmsgid = srfmsgid;
	}

	@Column(name = "CHARGENUMBER", length = 50)
	public String getChargenumber() {
		return chargenumber;
	}

	public void setChargenumber(String chargenumber) {
		this.chargenumber = chargenumber;
	}

	@Column(name = "CALLERRELREASON", length = 150)
	public String getCallerrelreason() {
		return callerrelreason;
	}

	public void setCallerrelreason(String callerrelreason) {
		this.callerrelreason = callerrelreason;
	}

	@Column(name = "CALLEDRELREASON", length = 150)
	public String getCalledrelreason() {
		return calledrelreason;
	}

	public void setCalledrelreason(String calledrelreason) {
		this.calledrelreason = calledrelreason;
	}

	@Column(name = "MSSERVER", length = 50)
	public String getMsserver() {
		return msserver;
	}

	public void setMsserver(String msserver) {
		this.msserver = msserver;
	}

	@Column(name = "MIDDLESTARTTIME", nullable = false, length = 50)
	public String getMiddlestarttime() {
		return middlestarttime;
	}

	public void setMiddlestarttime(String middlestarttime) {
		this.middlestarttime = middlestarttime;
	}

	@Column(name = "MIDDLECALLTIME", nullable = false, length = 50)
	public String getMiddlecalltime() {
		return middlecalltime;
	}

	public void setMiddlecalltime(String middlecalltime) {
		this.middlecalltime = middlecalltime;
	}

	@Column(name = "DURATION", nullable = false, length = 15)
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "COSTCOUNT", nullable = false, length = 10)
	public String getCostcount() {
		return costcount;
	}

	public void setCostcount(String costcount) {
		this.costcount = costcount;
	}

	@Column(name = "REALPATH", length = 200)
	public String getRealpath() {
		return realpath;
	}

	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}
	
	@Column(name = "FK_TCUSTOMERS")
	public Integer getFkTcustomers() {
		return fkTcustomers;
	}

	public void setFkTcustomers(Integer fkTcustomers) {
		this.fkTcustomers = fkTcustomers;
	}

	@Column(name = "FK_TOPERATOR")
	public Integer getFkToperator() {
		return fkToperator;
	}

	public void setFkToperator(Integer fkToperator) {
		this.fkToperator = fkToperator;
	}
}
