package tk.xdevcloud.medicalcore.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Table(name = "admins")
public class User implements Serializable {

	private static final long serialVersionUID = 1324239837257225366L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@org.hibernate.annotations.Type(type = "pg-uuid")
	private UUID uuid;

	public User() {
		super();
	}

	public User(String username, String password) {
		
		this.username = username;
		this.password = password;

	}

	public void setPassword(String password) {

		this.password = password;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return password;

	}

	public String getUsername() {

		return username;
	}
	
	public Integer getId() {
		
		return id;
	}

}
