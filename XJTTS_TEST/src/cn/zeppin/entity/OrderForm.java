package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.websocket.ClientEndpoint;

@Entity
@Table(name = "order_form")
public class OrderForm implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4156889435017728694L;
	
	private Integer id;
	private Integer teacherid;
	private String teacherName;
	private String teacherIdCard;
	private String formId;
	private Timestamp createtime;
	private Short status;
	private Double funds;
	
	
	public OrderForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderForm(Integer teacherid, String teacherName,
			String teacherIdCard, String formId, Timestamp createtime,
			Short status, Double funds) {
		super();
		this.teacherid = teacherid;
		this.teacherName = teacherName;
		this.teacherIdCard = teacherIdCard;
		this.formId = formId;
		this.createtime = createtime;
		this.status = status;
		this.funds = funds;
	}
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "teacher_id", nullable = false)
	public Integer getTeacherid() {
		return teacherid;
	}
	public void setTeacherid(Integer teacherid) {
		this.teacherid = teacherid;
	}
	
	@Column(name = "teacher_name", nullable = false)
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	@Column(name = "teacher_idcard", nullable = false)
	public String getTeacherIdCard() {
		return teacherIdCard;
	}
	public void setTeacherIdCard(String teacherIdCard) {
		this.teacherIdCard = teacherIdCard;
	}
	
	@Column(name = "form_id", nullable = false)
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	
	@Column(name = "funds", nullable = false)
	public Double getFunds() {
		return funds;
	}
	public void setFunds(Double funds) {
		this.funds = funds;
	}
	
	
}
