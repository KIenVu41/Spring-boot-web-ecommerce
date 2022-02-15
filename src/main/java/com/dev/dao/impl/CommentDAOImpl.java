package com.dev.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.CommentDAO;
import com.dev.entity.Comment;
import com.dev.entity.Product;

@Transactional
@Repository
public class CommentDAOImpl implements CommentDAO{
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Comment> search(String name, int start, int length) {
		// TODO Auto-generated method stub
		if(name.equals("")) {
			String jql = "select c from Comment c join c.user u join c.product p";
			Query query = entityManager.createQuery(jql, Comment.class);
			query.setFirstResult(start).setMaxResults(length);
			
			return query.getResultList();
		}else {
			String jql = "select c from Comment c join c.user u join c.product p where u.name like :uname";
			Query query = entityManager.createQuery(jql, Comment.class);
			query.setParameter("uname","%"+name+"%");
			query.setFirstResult(start).setMaxResults(length);
			return query.getResultList();}
	}

	@Override
	public List<Comment> listAll() {
		// TODO Auto-generated method stub
		String jql = "SELECT c FROM Comment c";
		return entityManager.createQuery(jql, Comment.class).getResultList();
	}

	@Override
	public void add(Comment comment) {
		// TODO Auto-generated method stub
		entityManager.persist(comment);
	}

	@Override
	public void update(Comment comment) {
		// TODO Auto-generated method stub
		entityManager.merge(comment);
	}

	@Override
	public void delete(Comment comment) {
		// TODO Auto-generated method stub
		entityManager.remove(comment);
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery("SELECT COUNT(c) FROM Comment c" );
		return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
	}

	@Override
	public Comment findById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(Comment.class, id);
	}

	@Override
	public List<Comment> findByProductId(int id) {
		// TODO Auto-generated method stub
		String jql = "select c from Comment c where c.product.id = ?1";
		Query query = entityManager.createQuery(jql, Comment.class);
		query.setParameter(1, id);
		return query.getResultList();}

}
