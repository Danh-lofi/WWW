package com.danh.spring.config;


import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.danh.spring")
@PropertySource({"classpath:persistence-mssql.properties"})
public class DemoAppConfig {

	@Autowired
	private Environment env;
	 private Logger logger = Logger.getLogger(getClass().getName());
	
	 private int getIntProperty(String propName) {

	        String propVal = env.getProperty(propName);

	        // now convert to int
	        int intPropVal = Integer.parseInt(propVal);

	        return intPropVal;
	    }

	 
	@Bean
	public DataSource myDataSource() {
		ComboPooledDataSource myDataSource = new ComboPooledDataSource();
		
		try {
			myDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }
		 // for sanity's sake, let's log url and user ... just to make sure we are reading the data
        logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
        logger.info("jdbc.user=" + env.getProperty("jdbc.user"));
        logger.info("jdbc.user=" + env.getProperty("jdbc.password"));
        
		myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		myDataSource.setUser(env.getProperty("jdbc.user"));
		myDataSource.setPassword(env.getProperty("jdbc.password"));
		
		   // set connection pool props
        myDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        myDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        myDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        myDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return myDataSource;
	}
	
	 private Properties getHibernateProperties() {

	        // set hibernate properties
	        Properties props = new Properties();

	        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
	        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

	        return props;
	    }
	 

	    @Bean
	    public LocalSessionFactoryBean sessionFactory() {

	        // create session factorys
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

	        // set the properties
	        sessionFactory.setDataSource(myDataSource());
	        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
	        sessionFactory.setHibernateProperties(getHibernateProperties());

	        return sessionFactory;
	    }
	    

	    @Bean
	    @Autowired
	    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

	        // setup transaction manager based on session factory
	        HibernateTransactionManager txManager = new HibernateTransactionManager();
	        txManager.setSessionFactory(sessionFactory);

	        return txManager;
	    }

	
	 
}
