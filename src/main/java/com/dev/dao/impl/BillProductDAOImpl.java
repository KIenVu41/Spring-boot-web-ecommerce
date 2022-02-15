package com.dev.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.BillProductDAO;
import com.dev.entity.Bill;
import com.dev.entity.BillProduct;


@Transactional
@Repository
public class BillProductDAOImpl implements BillProductDAO{
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void add(BillProduct billProduct) {
		// TODO Auto-generated method stub
		entityManager.persist(billProduct);
	}

	@Override
	public void update(BillProduct billProduct) {
		// TODO Auto-generated method stub
		entityManager.merge(billProduct);
	}

	@Override
	public void delete(BillProduct billProduct) {
		// TODO Auto-generated method stub
		entityManager.remove(billProduct);
	}

	@Override
	public BillProduct findById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(BillProduct.class, id);
	}

	@Override
	public List<BillProduct> pagination(int id, int start, int length) {
		// TODO Auto-generated method stub
			String jql = "select bp from BillProduct bp join bp.product p join bp.bill b where b.id = ?1";
			Query query = entityManager.createQuery(jql, BillProduct.class);
			query.setParameter(1, id);
			query.setFirstResult(start).setMaxResults(length);
			return query.getResultList();}
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT COUNT(bp) FROM BillProduct bp " );
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}
}
