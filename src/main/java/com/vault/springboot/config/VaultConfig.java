package com.vault.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {

	@Override
	public ClientAuthentication clientAuthentication() {
		return new TokenAuthentication("s.uGOYxHkZJYi6uh7iVHIx8IBM");
	}

	@Override
	public VaultEndpoint vaultEndpoint() {
		VaultEndpoint endPoint = VaultEndpoint.create("localhost", 8200);
		endPoint.setScheme("http");
		return endPoint;
	}	

	@Override
	public VaultTemplate vaultTemplate() {
		return new VaultTemplate(vaultEndpoint(), clientAuthentication());
	}

	
	
}
