package com.dev.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.CouponDAO;
import com.dev.entity.Coupon;

@Transactional
@Repository
public class CouponDAOImpl implements CouponDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Coupon> search(String code, int start, int length) {
		// TODO Auto-generated method stub
		if(code.equals("")) {
			String jql = "select c from Coupon c join c.bill b";
			Query query = entityManager.createQuery(jql, Coupon.class);
			query.setFirstResult(start).setMaxResults(length);
			
			return query.getResultList();
		}else {
			String jql = "select c from Coupon c join c.bill b where c.code like :code";
			Query query = entityManager.createQuery(jql, Coupon.class);
			query.setParameter("code", "%" + code + "%");
			query.setFirstResult(start).setMaxResults(length);
			return query.getResultList();}
	}

	@Override
	public List<Coupon> listAll() {
		// TODO Auto-generated method stub
		String jql = "SELECT c FROM Coupon c";
		return entityManager.createQuery(jql, Coupon.class).getResultList();
	}

	@Override
	public void add(Coupon coupon) {
		// TODO Auto-generated method stub
		entityManager.persist(coupon);
	}

	@Override
	public void update(Coupon coupon) {
		// TODO Auto-generated method stub
		entityManager.merge(coupon);
	}

	@Override
	public void delete(Coupon coupon) {
		// TODO Auto-generated method stub
		entityManager.remove(coupon);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT COUNT(c) FROM Coupon c " );
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}

	@Override
	public Coupon findById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(Coupon.class, id);
	}

	@Override
	public Coupon findByCode(String code) {
		// TODO Auto-generated method stub
		String jql = "select c from Coupon c where c.code like :code";
		Query query = entityManager.createQuery(jql, Coupon.class);
		query.setParameter("code", "%" + code + "%");
		return (Coupon) query.getSingleResult();
	}

	@Override
	public void updateBill(Coupon coupon) {
		// TODO Auto-generated method stub
		
	}

}
