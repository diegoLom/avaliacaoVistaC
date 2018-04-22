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
import com.avaliacao.vista.model.Area;
import com.avaliacao.vista.model.AreaFilter;
import com.avaliacao.vista.model.Contato;
import com.avaliacao.vista.model.ErroValue;
import com.avaliacao.vista.repository.Areas;
import com.avaliacao.vista.repository.Contatos;
import com.avaliacao.vista.util.Geral;
import com.avaliacao.vista.view.ExcelView;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Controller
@RequestMapping("/areas")
public class AreaController implements WebMvcConfigurer  {

	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
	
	
	@Autowired
	private Areas areas; 
	private Collection areasFiltradas;
	
	@Autowired
    private EntityManager entityManager;
  
	@Autowired
	private Contatos contatos;

	    private Session getSession() {
	    	if(entityManager != null)
	    		return entityManager.unwrap(Session.class);
	    	
	    	return null;
	    }
	

	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelView = new ModelAndView("areas/listarAreas");
		modelView.addObject("areas", areas.findAll());
		modelView.addObject(new AreaFilter());
		
		
		return modelView;
	}
	
	 @PostMapping
	    public ModelAndView filtrar(@Valid AreaFilter area, BindingResult bindingResult) {

		  if (bindingResult.hasErrors()) {
			  
			  ModelAndView modelView = new ModelAndView("areas/listarAreas");
				modelView.addObject("areas", areas.findAll());
				modelView.addObject(area);
				
	            return modelView;
	        }else {
	        	
	        	 
	  	      	ModelAndView modelView = new ModelAndView("areas/listarAreas");
	  	      	areasFiltradas = filtrar(area);
	  			modelView.addObject("areas", areasFiltradas);
	  			modelView.addObject(area);
	  			
	             return modelView;
	        	
	       }
		
	}
	 
	
	
	
	  
    @GetMapping("/add")
    public ModelAndView add(Area area, RedirectAttributes attributes) {
         
        ModelAndView mv = new ModelAndView("areas/cadAreas");
        mv.addObject("area", area);
        
        if(attributes != null && Geral.resultadoOuNulo(area, "areaId") != null) {
	        String mensagem = "Área editada com sucesso!";
	        attributes.addFlashAttribute("mensagem", mensagem);
        }
         
        return mv;
    }
     
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes atributos) {
    	
    	Optional<Area> areaO = areas.findById(id);
    	if(areaO.get() != null) {
    	 
    	}
         
        return add(areaO.get(), atributos);
    }
     
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes atributos) {
    	
    	Optional<Area> areaO = areas.findById(id);
    	java.util.List<Contato> contatosF = contatos.findByArea(areaO.get());
    	
    	
    	String mensagem = "Empresa excluída com sucesso";
    	String tipoMensagem = "mensagem";
    	
    	if(contatosF!= null && contatosF.size() == 0) {
    		areas.delete(areaO.get());
    	}else {
    		mensagem = "Não foi possível excluir area, a mesma possível vínculo com Contatos";
    		tipoMensagem += "Erro";
    	}
    	
    	
    	atributos.addFlashAttribute(tipoMensagem,mensagem);
         
    	return "redirect:/areas";
    }
 
    @PostMapping("/save")
    public ModelAndView save(@Valid Area area, BindingResult result, RedirectAttributes atributos) {
         
        if(result.hasErrors()) {
        //	result.getAllErrors();
     
        
            return add(area, null);
        }
        
         if(area.getCodigoArea() == null) {
        	area.setDataRegistro(new Date());
        }
        
         
         atributos.addFlashAttribute("mensagem", "Área salva com sucesso");
        areas.save(area);
         
        return listar();
    }
    
    
    @GetMapping("/download")
    public ModelAndView getMyData(AreaFilter area, HttpServletRequest request, HttpServletResponse response) {
        
    	Map<String, Object> model = new HashMap<String, Object>();
    	
        model.put("results",areasFiltradas != null ? areasFiltradas : areas.findAll());
      
        

        
        LinkedHashMap<String, String> columnsTitle = new LinkedHashMap<String, String>();
        columnsTitle.put("codigoArea","Cód. Área");
        columnsTitle.put("descricaoArea", "Descrição da Área");
        columnsTitle.put("dataRegistro", "Data Cadastro");
        
        model.put("columnsTitle", columnsTitle);
        
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=area.xls" );
        
        return new ModelAndView(new ExcelView(), model);
    }
    
    
	private List<Object> filtrar(AreaFilter area){
	    	
	    	
	if(getSession() != null) {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
					
		Root<?> root = criteriaQuery.from(Area.class);
		criteriaQuery.select(root);
		
		if(area.getDataInicial() != null && area.getDataFinal() != null) {
			Predicate predicate = criteriaBuilder.between(root.get("dataRegistro"),area.getDataInicial(), area.getDataFinal());
			criteriaQuery.where(predicate);
		}
		   
		   
		if(area.getDescricaoArea() != null && !area.getDescricaoArea().isEmpty())
			criteriaQuery.where(criteriaBuilder.like(root.get("descricaoArea"), area.getDescricaoArea()+"%"));
		    
		TypedQuery<Object> typedQuery = getSession().createQuery( criteriaQuery);
		
		List<Object> resultSet = typedQuery.getResultList(); 
		   
		return resultSet;
		      
	}
	    	
		return null;
	    	
	}
     
}




