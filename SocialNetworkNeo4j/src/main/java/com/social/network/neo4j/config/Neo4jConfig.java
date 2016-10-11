package com.social.network.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories("com.social.network.neo4j.repository")
public class Neo4jConfig extends Neo4jConfiguration implements TransactionManagementConfigurer {

	@Override
	public SessionFactory getSessionFactory() {
		return new SessionFactory("com.social.network.neo4j.domain");
	}

	@Bean
	public Neo4jTransactionManager txName() throws Exception {
		return new Neo4jTransactionManager(super.getSession());

	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		try {
			return txName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
