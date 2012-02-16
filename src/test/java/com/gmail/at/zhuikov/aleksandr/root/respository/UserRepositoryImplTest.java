package com.gmail.at.zhuikov.aleksandr.root.respository;

import static junit.framework.Assert.assertEquals;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.at.zhuikov.aleksandr.root.domain.User;
import com.gmail.at.zhuikov.aleksandr.root.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/root-context.xml")
@Transactional
public class UserRepositoryImplTest {

	@Inject UserRepository repository;
	@PersistenceContext EntityManager em;
	
	@Test
	public void findsUserById() {
		User user = new User("username");
		em.persist(user);
		assertEquals(user, repository.findOne("username"));
	}

	@Test
	public void saveUser() {
		User user = new User("username");
		repository.save(user);
		assertEquals(user, em.find(User.class, "username"));
	}
}
