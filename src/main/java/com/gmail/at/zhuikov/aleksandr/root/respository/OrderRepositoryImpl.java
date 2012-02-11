package com.gmail.at.zhuikov.aleksandr.root.respository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

	@PersistenceContext
    private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@Override
	@Transactional
	public void update(Order order) {
		em.persist(order);
	}

	@Override
	public Order load(long id) {
		return em.find(Order.class, id);
	}

	@Override
	@Transactional
	public void delete(Order order) {
		em.remove(order);
	}

	@Override
	@Transactional
	public void save(Order order) {
		em.persist(order);
	}

	@Override
	public List<Order> getAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		query.from(Order.class);
		return em.createQuery(query).getResultList();
	}
}
