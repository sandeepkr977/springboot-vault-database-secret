
package com.vault.springboot.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vault.springboot.pojo.VaultDatabaseCredPojo;
import com.vault.springboot.service.VaultService;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "vaultEntityManagerFactory", 
		transactionManagerRef = "vaultTransactionManager", 
		basePackages = {"com.vault.springboot.repository" })
public class MysqlDataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(MysqlDataSourceConfig.class);
	
	@Autowired
	private VaultService vaultService;
	
	@Value("${vault.driverClass}")
	private String driverClass;
	
	@Value("${vault.url}")
	private String url;

	@Bean(name = "vaultDataSource")
	public DataSource dataSource() throws Exception {
		logger.info("Inside vault datasource method :::::::::::");
		
		VaultDatabaseCredPojo pojo = vaultService.getDatabaseSecret("database/static-creds/mysql-vault-role");
	
		if(pojo.getPassword() == null || pojo.getUsername() == null)
			throw new Exception("DB credential is required");
		
		DataSource ds = DataSourceBuilder.create()
				.driverClassName(driverClass)
				.password(pojo.getPassword())
				.url(url)
				.username(pojo.getUsername())
				.build();
		return ds;
	}

	@Bean(name = "vaultEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder builder,
			@Qualifier("vaultDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).persistenceUnit("vault")
				.packages(new String[] { "com.vault.springboot.entity" }).build();
	}

	@Bean(name = "vaultTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("vaultEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}