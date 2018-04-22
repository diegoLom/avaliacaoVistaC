package com.avaliacao.vista;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication()
public class AvaliacaoVistaApplication implements ServletContextInitializer {
	
	@Autowired
	 private ErrorAttributes errorAttributes;

	@Bean
	 public AppErrorController appErrorController(){return new AppErrorController(errorAttributes);}
	
	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }


	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoVistaApplication.class, args);
	}
	
	
	
	
//	@Configuration
//	@EnableAutoConfiguration
//	@ComponentScan
//	public class Application implements ServletContextInitializer {
//
//	    public static void main(String[] args) throws Exception {
//	        SpringApplication.run(Application.class, args);
//	    }
//
//	    @Override
//	    public void onStartup(ServletContext servletContext) throws ServletException {
//	        \servletContext.getSessionCookieConfig().setName("yourCookieName");
//	    }
//
//	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		servletContext.getSessionCookieConfig().setName("avaliacaoCookie");
	}
	
	
}
