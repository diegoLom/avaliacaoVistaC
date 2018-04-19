package com.avaliacao.vista.controller;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaliacao.vista.model.Area;
import com.avaliacao.vista.model.Contato;
import com.avaliacao.vista.model.ContatoFilter;
import com.avaliacao.vista.model.Empresa;
import com.avaliacao.vista.repository.Areas;
import com.avaliacao.vista.repository.Contatos;
import com.avaliacao.vista.repository.Empresas;
import com.avaliacao.vista.util.Geral;
import com.avaliacao.vista.view.ExcelView;
import com.avaliacao.vista.view.PdfView;

@Controller
@RequestMapping("/contatos")
public class ContatoController {
	
	
	
	@Autowired
	private Contatos contatos;
	
	@Autowired
	private Empresas empresas; 
	
	@Autowired
	private Areas areas; 
	
	
	private Collection contatosFiltradas;
	
	  @Autowired
	    private EntityManager entityManager;

	    private Session getSession() {
	    	if(entityManager != null)
	    		return entityManager.unwrap(Session.class);
	    	
	    	return null;
	    }
	

	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelView = new ModelAndView("contatos/listarContatos");
		modelView.addObject("contatos", contatos.findAll());
		
		modelView.addObject(new ContatoFilter());
		
		
		
		return modelView;
	}
	
	 @PostMapping
	    public ModelAndView filtrar(@Valid ContatoFilter area, BindingResult bindingResult) {

		  if (bindingResult.hasErrors()) {
			  
			  ModelAndView modelView = new ModelAndView("contatos/listarContatos");
				modelView.addObject("contatos", contatos.findAll());
				modelView.addObject(area);
				
	            return modelView;
	        }else {
	        	ModelAndView modelView = new ModelAndView("contatos/listarContatos");
	  	      	contatosFiltradas = filtrar(area);
	  			modelView.addObject("contatos", contatosFiltradas);
	  			modelView.addObject(area);
	  			
	             return modelView;
	        	
	       }
		
	}
	 
	
	
	
	  
    @GetMapping("/add")
    public ModelAndView add(Contato contato) {
         
        ModelAndView mv = new ModelAndView("contatos/cadContatos");
        if(contato.getCodigoContato() == null) {
	        contato.setArea(new Area());
	        contato.setEmpresa(new Empresa());
        }
        mv.addObject("contato", contato);
        mv.addObject("empresas", empresas.findAll());
        mv.addObject("areas", areas.findAll());
        
//        if(Geral.resultadoOuNulo(contato, "area.codigoArea") != null) {
//        	mv.addObject("areaId", contato.getArea().getCodigoArea());
//        }
//        
//        if(Geral.resultadoOuNulo(contato, "empresa.codigoEmpresa") != null) {
//        	mv.addObject("empresaId", contato.getEmpresa().getCodigoEmpresa());
//        }
         
        return mv;
    }
     
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
    	
    	Optional<Contato> areaO = contatos.findById(id);
    	
    	
        return add(areaO.get());
    }
     
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
    	
    	Optional<Contato> areaO = contatos.findById(id);
    	contatos.delete(areaO.get());
         
        return listar();
    }
 
    @PostMapping("/save")
    public ModelAndView save(@Valid Contato contato, BindingResult result) {
         
        if(result.hasErrors()) {
        //	result.getAllErrors();
     
        
            return add(contato);
        }
        
         if(contato.getCodigoContato() == null) {
        	contato.setDataRegistro(new Date());
        }
        
        contatos.save(contato);
         
        return listar();
    }
    
    
    @GetMapping("/download")
    public ModelAndView getMyData(ContatoFilter area, HttpServletRequest request, HttpServletResponse response) {
        
    	Map<String, Object> model = new HashMap<String, Object>();
    	
        model.put("results",contatosFiltradas != null ? contatosFiltradas : contatos.findAll());
      
              
        LinkedHashMap<String, String> columnsTitle = new LinkedHashMap<String, String>();
        
    	 columnsTitle.put("codigoContato","Código");   
     	 columnsTitle.put("nomeContato","Nome"); 
     	 columnsTitle.put("cpf", "CPF"); 
     	 columnsTitle.put("telefoneResidencial" ,"Tel. Residencial");
     	 columnsTitle.put("telefoneCelular" ,"Tel. Celular");
     	 columnsTitle.put("email" ,"Email");
     	  
     	 
			
        /*columnsTitle.put("empresa.cnpj","CNPJ Empresa");
        columnsTitle.put("empresa.nomeEmpresa","Nome da Empresa");
        columnsTitle.put("nomeContato","Nome Contato");
        columnsTitle.put("cpf", "CPF");
        columnsTitle.put("telefoneResidencial" , "Telefone Residencial");
        columnsTitle.put("telefoneCelular" , "Celular");
        columnsTitle.put("email" , "Email");
        columnsTitle.put("area.descricaoArea" , "Área"); **/
        
    	
        model.put("columnsTitle", columnsTitle);
        
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=contatos.xls" );
        
        return new ModelAndView(new ExcelView(), model);
    }
    
    @GetMapping("/downloadRelatorioE")
    public ModelAndView getMyDataE(ContatoFilter area, HttpServletRequest request, HttpServletResponse response) {
        
    	Map<String, Object> model = new HashMap<String, Object>();
    	
        model.put("results",contatosFiltradas != null ? contatosFiltradas : contatos.findAll());
      
              
         
        
        LinkedHashMap<String, String> columnsTitle = new LinkedHashMap<String, String>();
        
        columnsTitle.put("empresa.cnpj","CNPJ Empresa");
        columnsTitle.put("empresa.nomeEmpresa","Nome da Empresa");
        columnsTitle.put("nomeContato","Nome Contato");
        columnsTitle.put("cpfFormatado", "CPF");
        columnsTitle.put("telefoneResidencialFormatado" , "Telefone Residencial");
        columnsTitle.put("telefoneCelularFormatado" , "Celular");
        columnsTitle.put("email" , "Email");
        columnsTitle.put("area.descricaoArea" , "Área"); 
        
    	
        model.put("columnsTitle", columnsTitle);
        
        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=contatos.xls" );
        
        return new ModelAndView(new ExcelView(), model);
    }
    
    @GetMapping("/prepararDownload")
	public ModelAndView prepararDownload() {
		ModelAndView modelView = new ModelAndView("relatorios/relContatos");
		//modelView.addObject("contatos", contatos.findAll());
		
		modelView.addObject(new ContatoFilter());
		
		
		return modelView;
	}
    
    
    @PostMapping("/downloadRelatorio")
    public ModelAndView getMyDataP(ContatoFilter area, HttpServletRequest request, HttpServletResponse response) {
        
    	Map<String, Object> model = new HashMap<String, Object>();
    	contatosFiltradas = filtrar(area);
    	
        model.put("results",contatosFiltradas);
      
              
        LinkedHashMap<String, String> columnsTitle = new LinkedHashMap<String, String>();
        
        columnsTitle.put("empresa.cnpj","CNPJ Empresa");
        columnsTitle.put("empresa.nomeEmpresa","Nome da Empresa");
        columnsTitle.put("nomeContato","Nome Contato");
        columnsTitle.put("cpfFormatado", "CPF");
        columnsTitle.put("telefoneResidencialFormatado" , "Telefone Residencial");
        columnsTitle.put("telefoneCelularFormatado" , "Celular");
        columnsTitle.put("email" , "Email");
        columnsTitle.put("area.descricaoArea" , "Área"); 
        
    	
        model.put("columnsTitle", columnsTitle);
        
        response.setContentType( "application/pdf" );
        response.setHeader( "Content-disposition", "attachment; filename=contatos.pdf" );
        
        return new ModelAndView(new PdfView(), model);
    }
    
	private List<Object> filtrar(ContatoFilter contato){
	    	
	    	
	if(getSession() != null) {
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
					
		Root<?> root = criteriaQuery.from(Contato.class);
		criteriaQuery.select(root);
		
		if(contato.getDataInicial() != null && contato.getDataFinal() != null) {
			Predicate predicate = criteriaBuilder.between(root.get("dataRegistro"),contato.getDataInicial(), contato.getDataFinal());
			criteriaQuery.where(predicate);
		}
		
		Object cpf = Geral.resultadoOuNulo(contato, "contato.cpf");
		if(cpf != null)
			criteriaQuery.where(criteriaBuilder.equal(root.get("cpf"), cpf));
		
		Object nomeContato = Geral.resultadoOuNulo(contato, "contato.nomeContato");
		if(nomeContato != null && !nomeContato.toString().isEmpty())
			criteriaQuery.where(criteriaBuilder.like(root.get("nomeContato"), contato.toString()+"%"));
		
		Object cnpj = Geral.resultadoOuNulo(contato, "contato.empresa.cnpj");
		if(cnpj != null)
			criteriaQuery.where(criteriaBuilder.equal(root.get("empresa.cnpj"), cnpj));
		
		Object nomeEmpresa = Geral.resultadoOuNulo(contato, "contato.empresa.nomeEmpresa");
		if(nomeEmpresa != null && !nomeEmpresa.toString().isEmpty())
			criteriaQuery.where(criteriaBuilder.like(root.get("empresa.nomeEmpresa"), nomeEmpresa+"%"));
		
		Object area = Geral.resultadoOuNulo(contato, "contato.area.descricaoArea");
		if(area != null && !area.toString().isEmpty())
			criteriaQuery.where(criteriaBuilder.like(root.get("area.descricaoArea"), area+"%"));
		
		TypedQuery<Object> typedQuery = getSession().createQuery( criteriaQuery);
		
		List<Object> resultSet = typedQuery.getResultList(); 
		   
		return resultSet;
		      
	}
	    	
		return null;
	    	
	}
	
	

	


}
