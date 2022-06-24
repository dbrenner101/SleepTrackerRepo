package com.brenner.sleeptracker.data.entities;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entity representing an Account. As implemented, defaults all Accounts to USER role
 *
 * @author dbrenner
 *
 */
@Entity
@Table(name="accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "account_id")
	private Integer accountId;
	
	@Column(nullable = false, unique = true)
	@NotBlank
	@Size(max = 20)
	private String username;
	
	@Column(nullable = false)
	@NotBlank
	@Size(max = 120)
	private String password;
	
	private String role = "USER";
	
	@OneToOne(optional = true, fetch = FetchType.LAZY)
	private UserProfile userProfile;
	
	/**
	 * 
	 */
	public Account() {}
	
	/**
	 * 
	 * @param accountId
	 * @param username
	 * @param password
	 */
	public Account(Integer accountId, String username, String password) {
		this.accountId = accountId;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 
	 * @param accountId
	 * @param username
	 * @param password
	 * @param userProfile
	 */
	public Account(Integer accountId, String username, String password, UserProfile userProfile) {
		this.accountId = accountId;
		this.username = username;
		this.password = password;
		this.userProfile = userProfile;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, accountId, userProfile, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(password, other.password) && Objects.equals(accountId, other.accountId)
				&& Objects.equals(userProfile, other.userProfile) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [userId=" + accountId + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", userProfile=" + userProfile + "]";
	}
	
}
