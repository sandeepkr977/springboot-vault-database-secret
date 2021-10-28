package com.vault.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;

import com.vault.springboot.pojo.VaultDatabaseCredPojo;

@Service
public class VaultService {

	@Autowired
	private VaultTemplate template;
	
	public VaultDatabaseCredPojo getDatabaseSecret(String path) {
		return new VaultDatabaseCredPojo(template.read(path));
	}

}
