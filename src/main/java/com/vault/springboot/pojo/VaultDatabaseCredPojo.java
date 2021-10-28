package com.vault.springboot.pojo;

import java.io.Serializable;
import java.util.Map;

import org.springframework.vault.support.VaultResponse;

public class VaultDatabaseCredPojo implements Serializable {
	
	private String username;
	private String password;
	private Integer ttl;
	private Integer rotationPeriod;
	private String lastVaultRotation;
	
	public VaultDatabaseCredPojo() {
		super();
	}
	
	public VaultDatabaseCredPojo(VaultResponse response) {
		Map<String,Object> data = response.getData();
		if(data != null) {
			this.username = data.containsKey("username") ? (String) data.get("username") : null;
			this.password = data.containsKey("password") ? (String) data.get("password") : null;
			this.ttl = data.containsKey("ttl") ? (Integer) data.get("ttl") : null;
			this.rotationPeriod = data.containsKey("rotation_period") ? (Integer) data.get("rotation_period") : null;
			this.lastVaultRotation = data.containsKey("last_vault_rotation") ? (String) data.get("last_vault_rotation") : null;
		}
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

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

	public Integer getRotationPeriod() {
		return rotationPeriod;
	}

	public void setRotationPeriod(Integer rotationPeriod) {
		this.rotationPeriod = rotationPeriod;
	}

	public String getLastVaultRotation() {
		return lastVaultRotation;
	}

	public void setLastVaultRotation(String lastVaultRotation) {
		this.lastVaultRotation = lastVaultRotation;
	}
	
}
