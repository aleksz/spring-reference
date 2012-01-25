package com.gmail.at.zhuikov.aleksandr.root;

import static org.hibernate.dialect.resolver.DialectFactory.buildDialect;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.gmail.at.zhuikov.aleksandr.root.domain.Item;
import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

@Configuration
public class HibernateConfiguration {

	@Value("#{dataSource}")
	private DataSource dataSource;
	
	@Bean 
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean
	public AnnotationSessionFactoryBean sessionFactory() throws HibernateException, SQLException {
		Properties props = new Properties();
		props.put("hibernate.dialect", buildDialect(props, dataSource.getConnection()).getClass().getName());
		props.put("hibernate.format_sql", "true");
		props.put("javax.persistence.validation.factory", validator());

		AnnotationSessionFactoryBean bean = new AnnotationSessionFactoryBean();
		bean.setAnnotatedClasses(new Class[] { Item.class, Order.class });
		bean.setHibernateProperties(props);
		bean.setDataSource(this.dataSource);
		bean.setSchemaUpdate(true);
		return bean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() throws HibernateException, SQLException {
		return new HibernateTransactionManager(sessionFactory().getObject());
	}

	@Bean
	public HibernateTemplate hibernateTemplate() throws HibernateException, SQLException {
		return new HibernateTemplate(sessionFactory().getObject());
	}
}
