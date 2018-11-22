package com.crt.tag.web.ui.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = DataSourceConfig1.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory1")
public class DataSourceConfig1 {
	
    static final String PACKAGE = "com.crt.tag.web.ui.modules.tag.repository";
    @Value("${spring.datasource1.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource1.url}")
    private String url;
    @Value("${spring.datasource1.username}")
    private String userName;
    @Value("${spring.datasource1.password}")
    private String password;
    
    @Value("${spring.datasource1.hikari.connectionTimeout}")
    private long connectionTimeout;
    @Value("${spring.datasource1.hikari.idleTimeout}")
    private long idleTimeout;
    @Value("${spring.datasource1.hikari.maxLifetime}")
    private long maxLifetime;
    @Value("${spring.datasource1.hikari.maximumPoolSize}")
    private int maximumPoolSize;

    @Bean(name = "dataSource1")
    @Primary
    public DataSource dataSource1() {
    	 HikariConfig config = new HikariConfig();  
         config.setDriverClassName(driverClassName);  
         config.setJdbcUrl(url);  
         config.setUsername(userName);  
         config.setPassword(password);  
         config.setConnectionTimeout(connectionTimeout);   
         config.setIdleTimeout(idleTimeout);  
         config.setMaxLifetime(maxLifetime);  
         config.setMaximumPoolSize(maximumPoolSize);  
           
         HikariDataSource dataSource = new HikariDataSource(config);  
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory1")
    @Primary
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("dataSource1") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        try {
            sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
            
    		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
    				.getResources("classpath:mapper/tag/*.xml"));
            return sessionFactory.getObject();  
        } catch (Exception e) {
            throw new RuntimeException(e);  
        }
        
    }
    
    @Bean(name = "transactionManager1")
    @Primary
    public DataSourceTransactionManager transactionManager1() {
        return new DataSourceTransactionManager(dataSource1());
    }
    
}
