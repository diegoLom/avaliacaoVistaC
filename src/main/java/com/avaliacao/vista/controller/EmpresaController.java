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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.avaliacao.vista.AppErrorController;
import com.avaliacao.vista.model.Contato;
import com.avaliacao.vista.model.Empresa;
import com.avaliacao.vista.model.EmpresaFilter;
import com.avaliacao.vista.model.ErroValue;
import com.avaliacao.vista.repository.Contatos;
import com.avaliacao.vista.repository.Empresas;
import com.avaliacao.vista.util.Geral;
import com.avaliacao.vista.view.ExcelView;

@Controller
@RequestMapping("/empresas")
public class EmpresaController implements WebMvcConfigurer  {

	
	@Autowired
	private Empresas empresas; 
	
	@Autowired
	private Contatos contatos; 
	
	
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
    public ModelAndView add(Empresa empresa, RedirectAttributes attributes) {
         
        ModelAndView mv = new ModelAndView("empresas/cadEmpresas");
        mv.addObject("empresa", empresa);
        
        if(attributes != null && empresa.getCodigoEmpresa() != null) {
        	String mensagem = "Empresa editada com sucesso";
        	attributes.addFlashAttribute("attributes",mensagem);
        }
         
        return mv;
    }
     
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes attributes) {
    	
    	Optional<Empresa> empresaO = empresas.findById(id);
    	if(empresaO.get() != null) {
    	 
    	}
         
        return add(empresaO.get(), attributes);
    }
     
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
    	
    	Optional<Empresa> empresaO = empresas.findById(id);
    	
    	java.util.List<Contato> contatosF = contatos.findByEmpresa(empresaO.get());
    	
    	
    	String mensagem = "Empresa excluída com sucesso";
    	String tipoMensagem = "mensagem";
    	
    	if(contatosF!= null && contatosF.size() == 0) {
    		empresas.delete(empresaO.get());
    	}else {
    		mensagem = "Não foi possível excluir empresa, a mesma possível vínculo com Contatos";
    		tipoMensagem += "Erro";
    	}
    	
    	
    	attributes.addFlashAttribute(tipoMensagem,mensagem);
         
    	
    	
        return "redirect:/empresas";
    }
 
    @PostMapping("/save")
    public ModelAndView save(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
         
        if(result.hasErrors()) {
        //	result.getAllErrors();
        
            return add(empresa, null);
        }
        
         if(empresa.getCodigoEmpresa() == null) {
        	empresa.setDataRegistro(new Date());
        }
        
        empresas.save(empresa);
        
        attributes.addFlashAttribute("mensagem", "Empresa salva com sucesso");
         
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
		   
		Object nomeEmpresa = Geral.resultadoOuNulo(empresa, "empresa.nomeEmpresa"); 
		if(nomeEmpresa != null && !nomeEmpresa.toString().isEmpty() )
			criteriaQuery.where(criteriaBuilder.like(root.get("nomeEmpresa"), nomeEmpresa.toString()+"%"));
		
		
		Object cnpj = Geral.resultadoOuNulo(empresa, "empresa.cnpj"); 
		if(cnpj != null )
			criteriaQuery.where(criteriaBuilder.equal(root.get("cnpj"), cnpj));
		
		    
		TypedQuery<Object> typedQuery = getSession().createQuery( criteriaQuery);
		
		List<Object> resultSet = typedQuery.getResultList(); 
		   
		return resultSet;
		      
	}
	    	
		return null;
	    	
	}
     
}




