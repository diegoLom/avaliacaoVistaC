package com.avaliacao.vista.services;

import javax.persistence.EntityManager;

public interface EntityManagerAwareValidator {
	 void setEntityManager(EntityManager entityManager); 

}
