package com.dev.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.ProductDAO;
import com.dev.entity.Product;

@Repository
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Product> search(String name, int start, int length, int mode) {
		if(name.equals("")) {
			String jql = "select p from Product p join p.category c ";
			Query query = entityManager.createQuery(jql, Product.class);
			query.setFirstResult(start).setMaxResults(length);
			
			return query.getResultList();
		}else {
			String jql = "select p from Product p join p.category c where p.name like :pname";
			Query query = entityManager.createQuery(jql, Product.class);
			query.setParameter("pname", "%" + name + "%");
			query.setFirstResult(start).setMaxResults(length);
			return query.getResultList();}
	}
	
	
	@Override
	public void add(Product product) {
		// TODO Auto-generated method stub
		entityManager.persist(product);
	}

	@Override
	public void update(Product product) {
		// TODO Auto-generated method stub
		entityManager.merge(product);
	}

	@Override
	public void delete(Product product) {
		// TODO Auto-generated method stub
		entityManager.remove(product);
	}

	@Override
	public Product getProductById(int id) {
		// TODO Auto-generated method stub	
		String hql="SELECT p FROM Product p join p.category c"
				+ " where p.id =:pId";
		return entityManager.createQuery(hql,Product.class).setParameter("pId", id).getSingleResult();
	}

	@Override
	public int countAll() {
		Query query = entityManager.createQuery("SELECT COUNT(p) FROM Product p" );
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}

	@Override
	public List<Product> listAll() {
		// TODO Auto-generated method stub
		String jql = "SELECT p FROM Product p";
		return entityManager.createQuery(jql, Product.class).getResultList();
	}


	@Override
	public int countByName(String name) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT COUNT(p) FROM Product p where p.name like :pname" );
		query.setParameter("pname", "%" + name + "%");
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}


	@Override
	public List<Product> findByCategory(String name, int start, int length) {
		// TODO Auto-generated method stub
		String jql = "select p from Product p join p.category c where c.name like :cname";
		Query query = entityManager.createQuery(jql, Product.class);
		query.setParameter("cname", "%" + name + "%");
		query.setFirstResult(start).setMaxResults(length);
		return query.getResultList();}


	@Override
	public List<Product> getLatest(int start, int length) {
		// TODO Auto-generated method stub
		String jql = "select p from Product p join p.category c order by p.id desc";
		Query query = entityManager.createQuery(jql, Product.class);
		query.setFirstResult(start).setMaxResults(length);
		return query.getResultList();}
	
}
