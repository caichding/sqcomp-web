package br.ufmg.dcc.lp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Usuario implements Serializable{
	
	@Transient
	public static final int ADMIN = 1;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;
	
	private Integer papel;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getPapel() {
		return papel;
	}
	public void setPapel(Integer papel) {
		this.papel = papel;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password="
				+ password + ", papel=" + papel + "]";
	}

}
