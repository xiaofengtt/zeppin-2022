package cn.zeppin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.entity.base.BaseEntity;

@Entity
@Table(name = "role_funcation", uniqueConstraints = @UniqueConstraint(columnNames = {
		"ROLE", "FUNCATION" }))
public class RoleFuncation extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -60688530296148105L;
	private Integer id;
	private Role role;
	private Funcation funcation;

	// Constructors

	/** default constructor */
	public RoleFuncation() {
	}

	/** full constructor */
	public RoleFuncation(Role role, Funcation funcation) {
		this.role = role;
		this.funcation = funcation;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FUNCATION", nullable = false)
	public Funcation getFuncation() {
		return this.funcation;
	}

	public void setFuncation(Funcation funcation) {
		this.funcation = funcation;
	}

	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}