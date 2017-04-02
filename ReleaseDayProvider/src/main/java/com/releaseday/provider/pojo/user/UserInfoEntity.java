package com.releaseday.provider.pojo.user;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "t_user")
public class UserInfoEntity implements Serializable{

	private static final long serialVersionUID = -1002280562266584114L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 用户名
	@NotNull
	private String username;
	
	// 密码
	private String password;

	// 昵称
	private String name;

	// 用户邮箱地址
	private String email;

	// 用户账号状态, -1为拉黑, 0为未激活, 1为已激活
	private Integer status;

	// 注册验证码
	@Column(name = "validate_code")
	private String validateCode;

	// 申请注册时间
	@Column(name = "register_time")
	private Date registerTime;

	// 盐值 - 登录时使用
	private String salt;

	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 获取验证链接失效时间
	@Transient
	public Date getLastActivateTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(registerTime);
		calendar.add(Calendar.DATE, 2);

		return calendar.getTime();
	}

}
