package cn.zeppin.entity ;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * RoleFuncation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role_funcation", uniqueConstraints = @UniqueConstraint(columnNames = { "ROLE", "FUNCATION" }))
public class RoleFuncation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3943682862024827399L;
	private Integer id;
	private Role role;
	private Funcation funcation;

	// Constructors

	/** default constructor */
	public RoleFuncation() {
	}

	/** full constructor */
	public RoleFuncation(Integer id, Role role, Funcation funcation) {
		this.id = id;
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

	/**
	 * cacheKey
	 * 
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}