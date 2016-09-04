package com.social.network.domain.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.social.network.domain.model.enums.Role;
import com.social.network.domain.util.Constants;

/**
 * @author andrii.yadykin
 *
 */
@Entity
@Table(name = "account")
@NamedQuery(name = Constants.FIND_ACCOUNT_BY_EMAIL, query = "from Account a where a.email = :email")
public class Account implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    //@Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Cascade({CascadeType.SAVE_UPDATE})
    @OneToOne( fetch = FetchType.LAZY)
    private User user;

    @Transient
    private Set<GrantedAuthority> authorities = null;

    public Account() {
    }

    public Account(String email, String password, Role role, User user) {
	this.email = email;
	this.password = password;
	this.role = role;
	this.user = user;
    }

    public long getAccountId() {
	return accountId;
    }

    public void setAccountId(long accountId) {
	this.accountId = accountId;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public Role getRole() {
	return role;
    }
    
    public String getRoleName() {
        return role.name();
    }

    public void setRole(Role role) {
	this.role = role;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities)
    {
        this.authorities=authorities;
    }

    @Override
    public String getUsername() {
	return email;
    }

    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return true;
    }

    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", email=" + email + ", password= *****, role=" + role
                + ", user=" + user + ", authorities=" + authorities + "]";
    }

}
