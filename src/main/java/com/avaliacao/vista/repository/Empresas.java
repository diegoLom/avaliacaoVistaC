package com.avaliacao.vista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avaliacao.vista.model.Empresa;

public interface Empresas extends JpaRepository<Empresa, Long> {

}
