package com.avaliacao.vista.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avaliacao.vista.model.Area;
import com.avaliacao.vista.model.Contato;
import com.avaliacao.vista.model.Empresa;

public interface Contatos extends JpaRepository<Contato, Long> {

	
	public List<Contato> findByArea(Area area);
	public List<Contato> findByEmpresa(Empresa empresa);
	
	
	
}
