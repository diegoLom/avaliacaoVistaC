package com.avaliacao.vista.services;

import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy={UniqueKeyValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueKey {
	
	
	String[]columnNames();
	
	String propertyId();

    String message() default "{UniqueKey.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    boolean multiplasValidacoes() default false;

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        UniqueKey[] value();
    }

	

}



