package com.avaliacao.vista.services;

import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

public class ConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {
	
	private EntityManagerFactory entityManagerFactory;

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        T instance = null;

        try {
            instance = key.newInstance();
        } catch (Exception e) { 
            // could not instantiate class
            e.printStackTrace();
        }

        if(EntityManagerAwareValidator.class.isAssignableFrom(key)) {
            EntityManagerAwareValidator validator = (EntityManagerAwareValidator) instance;
            validator.setEntityManager(entityManagerFactory.createEntityManager());
        }

        return instance;
    }


	@Override
	public void releaseInstance(ConstraintValidator<?, ?> arg0) {
		// TODO Auto-generated method stub

	}

}
