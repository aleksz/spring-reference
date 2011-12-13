package com.gmail.at.root;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.gmail.at.servlet.Item;
import com.gmail.at.servlet.Order;

@Configuration
public class HibernateConfiguration {

	@Value("#{dataSource}")
	private DataSource dataSource;

	@Bean
	public AnnotationSessionFactoryBean sessionFactory() {
		Properties props = new Properties();
		props.put("hibernate.dialect", H2Dialect.class.getName());
		props.put("hibernate.format_sql", "true");

		AnnotationSessionFactoryBean bean = new AnnotationSessionFactoryBean();
		bean.setAnnotatedClasses(new Class[] { Item.class, Order.class });
		bean.setHibernateProperties(props);
		bean.setDataSource(this.dataSource);
		bean.setSchemaUpdate(true);
		return bean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		return new HibernateTransactionManager(sessionFactory().getObject());
	}

	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory().getObject());
	}
}
