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

/**
 * UserFriends entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_friends", catalog = "cetv")
public class UserFriends implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1572756834530473798L;
	private Integer id;
	private User userByUser;
	private User userByFriend;
	private Short type;

	// Constructors

	/** default constructor */
	public UserFriends() {
	}

	/** full constructor */
	public UserFriends(User userByUser, User userByFriend) {
		this.userByUser = userByUser;
		this.userByFriend = userByFriend;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user", nullable = false)
	public User getUserByUser() {
		return this.userByUser;
	}

	public void setUserByUser(User userByUser) {
		this.userByUser = userByUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "friend", nullable = false)
	public User getUserByFriend() {
		return this.userByFriend;
	}

	public void setUserByFriend(User userByFriend) {
		this.userByFriend = userByFriend;
	}

	@Column(name = "type")
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
	

}