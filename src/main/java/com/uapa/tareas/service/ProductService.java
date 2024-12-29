package com.uapa.tareas.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.uapa.tareas.models.Product;
import com.uapa.tareas.utils.HibernateUtil;

public class ProductService {
	
	public void saveProduct(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error when saving product:: " + e.getMessage());
        }
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            products = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error when retrieving products:: " + e.getMessage());
        }
        return products;
    }
	
}
