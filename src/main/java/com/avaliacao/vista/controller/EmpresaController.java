package com.avaliacao.vista.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.management.relation.RelationNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.avaliacao.vista.AppErrorController;
import com.avaliacao.vista.model.Empresa;
import com.avaliacao.vista.model.EmpresaFilter;
import com.avaliacao.vista.model.ErroValue;
import com.avaliacao.vista.repository.Empresas;
import com.avaliacao.vista.view.ExcelView;

@Controller
@RequestMapping("/empresas")
public class EmpresaController implements WebMvcConfigurer  {

	
	@Autowired
	private Empresas empresas; 
	private Collection empresasFiltradas;
	
	  @Autowired
	    private EntityManager entityManager;

	    private Session getSession() {
	    	if(entityManager != null)
	    		return entityManager.unwrap(Session.class);
	    	
	    	return null;
	    }
	

	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelView = new ModelAndView("empresas/listarEmpresas");
		modelView.addObject("empresas", empresas.findAll());
		modelView.addObject(new EmpresaFilter());
		
		
		return modelView;
	}
	
	 @PostMapping
	    public ModelAndView filtrar(@Valid EmpresaFilter empresa, BindingResult bindingResult) {

		  if (bindingResult.hasErrors()) {
			  
			  ModelAndView modelView = new ModelAndView("empresas/listarEmpresas");
				modelView.addObject("empresas", empresas.findAll());
				modelView.addObject(empresa);
				
	            return modelView;
	        }else {
	        	
	        	 
	  	      	ModelAndView modelView = new ModelAndView("empresas/listarEmpresas");
	  	      	empresasFiltradas = filtrar(empresa);
	  			modelView.addObject("empresas", empresasFiltradas);
	  			modelView.addObject(empresa);
	  			
	             return modelView;
	        	
	       }
		
	}
	 
	
	 @GetMapping("/add")
    public ModelAndView add(Empresa empresa) {
         
        ModelAndView mv = new ModelAndView("empresas/cadEmpresas");
        mv.addObject("empresa", empresa);
         
        return mv;
    }
     
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
    	
    	Optional<Empresa> empresaO = empresas.findById(id);
    	if(empresaO.get() != null) {
    	 
    	}
         
        return add(empresaO.get());
    }
     
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
    	
    	Optional<Empresa> empresaO = empresas.findById(id);
    	empresas.delete(empresaO.get());
         
        return listar();
    }
 
    @PostMapping("/save")
    public ModelAndView save(@Valid Empresa empresa, BindingResult result) {
         
        if(result.hasErrors()) {
        //	result.getAllErrors();
        
            return add(empresa);
        }
        
         if(empresa.getCodigoEmpresa() == null) {
        	empresa.setDataRegistro(new Date());
        }
        
        empresas.save(empresa);
         
        return listar();
    }
    
    
    @GetMapping("/download")
    public ModelAndView getMyData(EmpresaFilter Empresa, HttpServletRequest request, HttpServletResponse response) {
        
    	Map<String, Object> model = new HashMap<String, Object>();
    	
        model.put("results",empresasFiltradas != null ? empresasFiltradas : empresas.findAll());
      
        LinkedHashMap<String, String> columnsTitle = new LinkedHashMap<String, String>();
        columnsTitle.put("codigoEmpresa","Código");
        columnsTitle.put("nomeEmpresa", "Nome");
        columnsTitle.put("cnpj", "CNPJ");
        columnsTitle.put("dataRegistro", "Data Cadastro");
        
        model.put("columnsTitle", columnsTitle);
        
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=Empresa.xls" );
        
        return new ModelAndView(new ExcelView(), model);
    }
    
    
	private List<Object> filtrar(EmpresaFilter empresa){
	    	
	    	
	if(getSession() != null) {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
					
		Root<?> root = criteriaQuery.from(Empresa.class);
		criteriaQuery.select(root);
		
		if(empresa.getDataInicial() != null && empresa.getDataFinal() != null) {
			Predicate predicate = criteriaBuilder.between(root.get("dataRegistro"),empresa.getDataInicial(), empresa.getDataFinal());
			criteriaQuery.where(predicate);
		}
		   
		   
		if(empresa.getNomeEmpresa() != null && !empresa.getNomeEmpresa().isEmpty())
			criteriaQuery.where(criteriaBuilder.equal(root.get("nomeempresa"), empresa.getNomeEmpresa()));
		
		if(empresa.getCnpj() != null )
			criteriaQuery.where(criteriaBuilder.equal(root.get("cnpj"), empresa.getCnpj()));
		
		    
		TypedQuery<Object> typedQuery = getSession().createQuery( criteriaQuery);
		
		List<Object> resultSet = typedQuery.getResultList(); 
		   
		return resultSet;
		      
	}
	    	
		return null;
	    	
	}
     
}




