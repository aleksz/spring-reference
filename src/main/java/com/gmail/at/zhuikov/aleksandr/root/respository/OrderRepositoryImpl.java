package com.gmail.at.zhuikov.aleksandr.root.respository;

import static org.hibernate.criterion.DetachedCriteria.forClass;

import java.util.List;

import javax.inject.Inject;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

@Repository
public class OrderRepositoryImpl extends HibernateDaoSupport implements OrderRepository {

	@Inject
	public OrderRepositoryImpl(HibernateTemplate hibernateTemplate) {
		setHibernateTemplate(hibernateTemplate);
	}
	
	@Override
	@Transactional
	public void update(Order order) {
		getHibernateTemplate().saveOrUpdate(order);
	}

	@Override
	public Order load(long id) {
		return getHibernateTemplate().load(Order.class, id);
	}

	@Override
	@Transactional
	public void delete(Order order) {
		getHibernateTemplate().delete(order);
	}

	@Override
	@Transactional
	public void save(Order order) {
		getHibernateTemplate().save(order);
	}

	@Override
	public List<Order> getAll() {
		return getHibernateTemplate().findByCriteria(forClass(Order.class));
	}
}
