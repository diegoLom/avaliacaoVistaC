package com.avaliacao.vista.services;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Transactional
@Repository
public class UniqueKeyValidator implements ConstraintValidator<UniqueKey, Serializable> {

	  @Autowired
	    private EntityManager entityManager;

	    private Session getSession() {
	    	if(entityManager != null)
	    		return entityManager.unwrap(Session.class);
	    	
	    	return null;
	    }

    private String[] columnNames;
    private String propertyId;

    @Override
    public void initialize(UniqueKey constraintAnnotation) {
        this.columnNames = constraintAnnotation.columnNames();
        this.propertyId = constraintAnnotation.propertyId();
    }

    @Override
    public boolean isValid(Serializable target, ConstraintValidatorContext context) {
        Class<?> entityClass = target.getClass();
        
        if(getSession() != null) {
	        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
	
	        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
	        target.getClass().getAnnotations();
	        Root<?> root = criteriaQuery.from(entityClass);
	
	        List<Predicate> predicates = new ArrayList<Predicate> (columnNames.length);
	        
	        try {
	            for(int i=0; i<columnNames.length; i++) {
	                String propertyName = columnNames[i];
	                PropertyDescriptor desc = new PropertyDescriptor(propertyName, entityClass);
	                Method readMethod = desc.getReadMethod();
	                Object propertyValue = readMethod.invoke(target);
	                Predicate predicate = criteriaBuilder.equal(root.get(propertyName), propertyValue);
	                predicates.add(predicate);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        criteriaQuery.select(root);
	        
	        
            PropertyDescriptor desc;
			try {
				desc = new PropertyDescriptor(propertyId, entityClass);
				Method readMethod = desc.getReadMethod();
				Object propertyValue = readMethod.invoke(target);
				
				if(propertyValue != null) {
					
					   Predicate predicate = criteriaBuilder.not(root.get(propertyId).in(propertyValue));
		                predicates.add(predicate);
				}
			      
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
//	        for (Iterator iterator = predicates.iterator(); iterator.hasNext();) {
//				Predicate predicate = (Predicate) iterator.next();
//				//criteriaBuilder.and(predicate);
			
			
				criteriaQuery = criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
				
		//	}
	        
	        
	        
	        
			TypedQuery<Object> typedQuery = getSession().createQuery( criteriaQuery);
	
	        List<Object> resultSet = typedQuery.getResultList(); 
	
	        return resultSet.size() == 0;
        }
        return true;
    }
}