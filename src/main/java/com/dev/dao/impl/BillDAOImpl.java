package com.dev.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.BillDAO;
import com.dev.entity.Bill;


@Transactional
@Repository
public class BillDAOImpl implements BillDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Bill> search(String name, int start, int length) {
		// TODO Auto-generated method stub
		if(name.equals("")) {
			String jql = "select b from Bill b join b.user u";
			Query query = entityManager.createQuery(jql, Bill.class);
			query.setFirstResult(start).setMaxResults(length);
			
			return query.getResultList();
		}else {
			String jql = "select b from Bill b join b.user u where u.name like :uname";
			Query query = entityManager.createQuery(jql, Bill.class);
			query.setParameter("uname","%"+name+"%");
			query.setFirstResult(start).setMaxResults(length);
			return query.getResultList();}
	}

	@Override
	public List<Bill> listAll() {
		// TODO Auto-generated method stub
		String jql = "SELECT b FROM Bill b";
		return entityManager.createQuery(jql, Bill.class).getResultList();
	}

	@Override
	public int add(Bill bill) {
		// TODO Auto-generated method stub
		entityManager.persist(bill);
		return bill.getId();
	}

	@Override
	public void update(Bill bill) {
		// TODO Auto-generated method stub
		entityManager.merge(bill);
	}

	@Override
	public void delete(Bill bill) {
		// TODO Auto-generated method stub
		entityManager.remove(bill);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT COUNT(b) FROM Bill b " );
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}

	@Override
	public Bill findById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(Bill.class, id);
	}

}
