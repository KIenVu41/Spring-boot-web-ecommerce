package com.dev.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.ReviewDAO;
import com.dev.entity.Bill;
import com.dev.entity.Review;

@Transactional
@Repository
public class ReviewDAOImpl implements ReviewDAO {
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT COUNT(rv) FROM Review rv" );
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}

	@Override
	public List<Review> search(String name, int start, int length) {
		// TODO Auto-generated method stub
		if(name.equals("")) {
			String jql = "select rv from Review rv join rv.user u join rv.product p";
			Query query = entityManager.createQuery(jql, Review.class);
			query.setFirstResult(start).setMaxResults(length);
			
			return query.getResultList();
		}else {
			String jql = "select rv from Review rv join rv.user u join rv.product p where u.name like :uname";
			Query query = entityManager.createQuery(jql, Review.class);
			query.setParameter("uname","%"+name+"%");
			query.setFirstResult(start).setMaxResults(length);
			return query.getResultList();}
	}

	@Override
	public List<Review> listAll() {
		// TODO Auto-generated method stub
		String jql = "SELECT rv FROM Review rv";
		return entityManager.createQuery(jql, Review.class).getResultList();
	}

	@Override
	public void add(Review review) {
		// TODO Auto-generated method stub
		entityManager.persist(review);
	}

	@Override
	public void update(Review review) {
		// TODO Auto-generated method stub
		entityManager.merge(review);
	}

	@Override
	public void delete(Review review) {
		// TODO Auto-generated method stub
		entityManager.remove(review);
	}

	@Override
	public Review findById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(Review.class, id);
	}

}
