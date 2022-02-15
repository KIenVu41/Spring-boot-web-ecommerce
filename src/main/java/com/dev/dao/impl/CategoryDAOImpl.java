package com.dev.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.CategoryDAO;
import com.dev.entity.Category;

@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Category> search(String name, int start, int length){
		// TODO Auto-generated method stub
		if(name.equals("")) {
			String jql = "select c from Category c";
			Query query = entityManager.createQuery(jql, Category.class);
			query.setFirstResult(start).setMaxResults(length);
			
			return query.getResultList();
		}else {
			String jql = "select c from Category c where name like :name";
			Query query = entityManager.createQuery(jql, Category.class);
			query.setParameter("name", "%" + name + "%");
			query.setFirstResult(start).setMaxResults(length);
			return query.getResultList();}
	}

	@Override
	public void add(Category category) {
		// TODO Auto-generated method stub
		entityManager.persist(category);
	}

	@Override
	public void update(Category category) {
		// TODO Auto-generated method stub
		entityManager.merge(category);
	}

	@Override
	public void delete(Category categorygory) {
		// TODO Auto-generated method stub
		entityManager.remove(categorygory);;
	}

	@Override
	public Category getCategoryById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(Category.class, id);
	}

	@Override
	public int count() {
		Query query = entityManager.createQuery("SELECT COUNT(c) FROM Category c " );
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}

	@Override
	public List<Category> listAll() {
		// TODO Auto-generated method stub
		String jql = "SELECT c FROM Category c";
		return entityManager.createQuery(jql, Category.class).getResultList();
	}
}
