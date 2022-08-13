/**
 * 
 */
package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 教师培训报名筛选器
 * @author Administrator
 *
 */
@Entity
@Table(name = "teacher_signup_sizer")
public class TeacherSignupSizer implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7758267714871175144L;
	private Integer id;
    private String name;
    private Short status;
    private String actionScope;//作用域
    private String condition;//基础信息筛选条件
    private String reactionScope;//重复报名筛选条件
    private Timestamp createtime;
    private Integer weight;//权重
	
    public TeacherSignupSizer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherSignupSizer(Integer id, String name, Short status,
			String actionScope, String condition, Timestamp createtime, Integer weight, String reactionScope) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.actionScope = actionScope;
		this.condition = condition;
		this.createtime = createtime;
		this.weight = weight;
		this.reactionScope = reactionScope;
	}

	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "ACTION_SCOPE", nullable = false)
	public String getActionScope() {
		return actionScope;
	}

	public void setActionScope(String actionScope) {
		this.actionScope = actionScope;
	}

	@Column(name = "CONDITIONS", nullable = false)
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "WEIGHT", nullable = false)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	
	@Column(name = "RE_ACTION_SCOPE", nullable = false)
	public String getReactionScope() {
		return reactionScope;
	}
	

	public void setReactionScope(String reactionScope) {
		this.reactionScope = reactionScope;
	}
    
    
}
