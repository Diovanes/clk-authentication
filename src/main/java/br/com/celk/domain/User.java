package br.com.celk.domain;

import java.time.LocalDateTime;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.celk.config.Constants;
		

@Entity
@Table(name = "clk_user")
@NamedQuery(name = "user.byLogin", query = "SELECT u FROM User u where u.login = :login", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Cacheable
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Size(max = 50)
	@Column(name = "name", length = 50)
	private String name;

	@NotNull
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 60)
	@Column(length = 60, unique = true, nullable = false)
	private String login;

	@JsonIgnore
	@NotNull
	@Size(max = 60)
	@Column(name = "password", length = 60, nullable = false)
	private String pass;
	
	@JsonIgnore
	@NotNull
	@Size(max = 120)
	@Column(name = "password_hash", length = 120, nullable = false)
	private String pass_hash;
	
	@JsonIgnore
	@NotNull
	@Size(max = 120)
	@Column(name = "salt", length = 120, nullable = false)
	private String salt;
	
	@JsonIgnore
	@NotNull
	@Column(name = "iterations", nullable = false)
	private Integer iterations;
	
	@JsonIgnore
	@NotNull
	@Size(max = 100)
	@Column(name = "role", length = 100, nullable = false)
	private String role;

	@JsonIgnore
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@JsonIgnore
	@Column(name = "updated_at", length = 50)
	private LocalDateTime updatedAt;
	
	public User(@Size(max = 50) String name,
			@NotNull @Pattern(regexp = "^[_.@A-Za-z0-9-]*$") 
			@Size(min = 1, max = 60) String login,
			@NotNull @Size(max = 60) String pass, 
			@NotNull @Size(max = 100) String role) {
		
		super();
		this.name = name;
		this.login = login;
		this.pass = pass;
		this.role = role;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public User() {
		super();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPass_hash() {
		return pass_hash;
	}

	public void setPass_hash(String pass_hash) {
		this.pass_hash = pass_hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getIterations() {
		return iterations;
	}

	public void setIterations(Integer iterations) {
		this.iterations = iterations;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", login=" + login + ", pass=" + pass + ", pass_hash=" + pass_hash
				+ ", salt=" + salt + ", iterations=" + iterations + ", role=" + role + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}


}
