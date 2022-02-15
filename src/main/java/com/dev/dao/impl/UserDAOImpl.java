package com.dev.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.UserDAO;
import com.dev.entity.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<User> search(String name, int start, int length) {
		if(name.equals("")) {
			String jql = "select u from User u";
			Query query = entityManager.createQuery(jql, User.class);
			query.setFirstResult(start).setMaxResults(length);
			
			return query.getResultList();
		}else {
			String jql = "select u from User u where name like :name";
			Query query = entityManager.createQuery(jql, User.class);
			query.setParameter("name", "%" + name + "%");
			query.setFirstResult(start).setMaxResults(length);
			return query.getResultList();}
	}


	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		entityManager.persist(user);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		entityManager.merge(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		entityManager.remove(user);
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(User.class, id);
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		String jql = "SELECT u FROM User u WHERE u.username = ?1";
		return entityManager.createQuery(jql, User.class).setParameter(1, username).getSingleResult();
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		String jql = "SELECT u FROM User u WHERE u.email = ?1";
		return entityManager.createQuery(jql, User.class).setParameter(1, email).getSingleResult();
	}

	@Override
	public void changeEnable(int id, boolean status) {
		// TODO Auto-generated method stub
		entityManager.createQuery("Update User u set u.enabled = ?1 WHERE u.id = ?2").setParameter(1, status).setParameter(2, id).executeUpdate();
	}


	@Override
	public int count() {
		Query query = entityManager.createQuery("SELECT COUNT(u) FROM User u " );
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}


	@Override
	public List<User> listAll() {
		// TODO Auto-generated method stub
		String jql = "SELECT u FROM User u";
		return entityManager.createQuery(jql, User.class).getResultList();
	}

}
