package com.example.identity;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(scanBasePackages = "com.example.identity")
public class IdentityApplicationTests {

//	@Bean
//	ServletWebServerFactory servletWebServerFactory() {
//		return new TomcatServletWebServerFactory();
//  	}
//
//	@Bean
//	@ConditionalOnMissingBean
//	public GraphQLTestTemplate graphQLTestUtils() {
//		return new GraphQLTestTemplate();
//	}
//
//	@Bean
//	public SchemaParserOptions schemaParserOptions(){
//		return SchemaParserOptions.newOptions().objectMapperConfigurer((mapper, context) -> {
//			mapper.registerModule(new JavaTimeModule());
//		}).build();
//	}
//
//	@Bean
//	public ObjectMapperConfigurer objectMapperConfigurer(){
//		return mapper -> mapper.registerModule(new JavaTimeModule())
//			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//	}


//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
//		dataSource.setUrl("jdbc:postgresql://localhost:5555/postgres");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		return dataSource;
//	}
//
//	@Bean
//	public EntityManager entityManager() {
//		return entityManagerFactory().getObject().createEntityManager();
//	}
//
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(dataSource());
//		em.setPackagesToScan("package.where.your.entites.like.CustSys.are.stored");
//		return em;
//	}
}
